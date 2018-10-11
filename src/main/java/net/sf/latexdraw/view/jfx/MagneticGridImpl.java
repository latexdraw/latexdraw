/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import java.util.List;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implementation of a magnetic grid.
 * @author Arnaud Blouin
 */
public class MagneticGridImpl extends Path implements MagneticGrid {
	/** The canvas that paints the grid. */
	private final Canvas canvas;
	/** Allows to know if the grid is magnetic or not. */
	private boolean isMagnetic;
	/** defines the spacing between the lines of the grid. */
	private int gridSpacing;
	/** The style of the grid. */
	private GridStyle style;
	/** Defined if the canvas has been modified. */
	private boolean modified;


	/**
	 * Creates the magnetic grid.
	 * @param canv The canvas in which the grid will work.
	 * @throws NullPointerException if the given parameters are not valid.
	 */
	public MagneticGridImpl(final Canvas canv) {
		super();
		modified = false;
		canvas = canv;
		reinitGrid();
		setStroke(new Color(0d, 0d, 1d, 0.3d));
		setStrokeWidth(1);
		canvas.zoomProperty().addListener((observable, oldValue, newValue) -> update());
	}


	@Override
	public void update() {
		if(isDisable()) {
			return;
		}

		getElements().clear();

		switch(style) {
			case STANDARD:
				double ppc = canvas.getPPCDrawing();
				if(ScaleRuler.getUnit() == Unit.INCH) {
					ppc *= PSTricksConstants.INCH_VAL_CM;
				}

//				paintSubLines(getElements(), canvas.getLayoutBounds());
				paintMainLines(getElements(), ppc);
				break;
			case CUSTOMISED:
				paintMainLines(getElements(), gridSpacing);
				break;
			case NONE:
				break;
		}
	}


	private void createLine(final List<PathElement> elts, final double x1, final double y1, final double x2, final double y2) {
		elts.add(new MoveTo(x1, y1));
		elts.add(new LineTo(x2, y2));
	}


	private void paintSubLines(final List<PathElement> elts) {
		double pixPerCm10 = canvas.getPPCDrawing() / 10d;

		if(ScaleRuler.getUnit() == Unit.INCH) {
			pixPerCm10 *= PSTricksConstants.INCH_VAL_CM;
		}

		if(Double.compare(pixPerCm10, 4d) > 0d) {
			final double height = canvas.getPrefHeight();
			final double width = canvas.getPrefWidth();

			for(double i = 0d; i < width; i += pixPerCm10) {
				createLine(elts, i, 0d, i, height);
			}

			for(double j = 0d; j < height; j += pixPerCm10) {
				createLine(elts, 0d, j, width, j);
			}
		}
	}


	private void paintMainLines(final List<PathElement> elts, final double gap) {
		final double height = canvas.getPrefHeight();
		final double width = canvas.getPrefWidth();

		for(double i = 0d; i < width; i += gap) {
			createLine(elts, i, 0d, i, height);
		}

		for(double j = 0d; j < height; j += gap) {
			createLine(elts, 0d, j, width, j);
		}
	}


	@Override
	public IPoint getTransformedPointToGrid(final Point3D pt) {
		if(isMagnetic() && isGridDisplayed()) {
			final IPoint point = ShapeFactory.INST.createPoint(pt.getX(), pt.getY());
			final double modulo = getMagneticGridGap();
			double x = point.getX();
			double y = point.getY();
			int base = (int) ((x / modulo) * modulo);

			if(x > modulo) {
				x %= (int) modulo;
			}

			double res = modulo - x;
			x = base;

			if(res < modulo / 2d) {
				x += modulo;
			}

			point.setX((int) x);
			base = (int) ((point.getY() / modulo) * modulo);

			if(y > modulo) {
				y %= (int) modulo;
			}

			res = modulo - y;
			y = base;

			if(res < modulo / 2d) {
				y += modulo;
			}

			point.setY((int) y);

			return point;
		}

		return ShapeFactory.INST.createPoint(pt.getX(), pt.getY());
	}


	@Override
	public double getMagneticGridGap() {
		double gap;

		if(isPersonalGrid()) {
			gap = getGridSpacing();
		}else {
			final double ppc = canvas.getPPCDrawing();
			gap = ScaleRuler.getUnit() == Unit.CM ? ppc / 10d : ppc * PSTricksConstants.INCH_VAL_CM / 10d;
			gap = gap - (int) gap > 0.5 ? (int) gap + 1d : (int) gap;
		}

		return gap;
	}


	@Override
	public void reinitGrid() {
		setGridStyle(GridStyle.CUSTOMISED);
		setGridSpacing(20);
		setMagnetic(true);
	}


	@Override
	public boolean isMagnetic() {
		return isMagnetic;
	}


	@Override
	public void setMagnetic(final boolean isMag) {
		if(isMagnetic != isMag) {
			isMagnetic = isMag;
			setModified(true);
		}
	}


	@Override
	public int getGridSpacing() {
		return gridSpacing;
	}


	@Override
	public void setGridSpacing(final int spacing) {
		if(spacing > 1 && gridSpacing != spacing) {
			gridSpacing = spacing;
			setModified(true);
		}
	}


	@Override
	public boolean isPersonalGrid() {
		return style == GridStyle.CUSTOMISED;
	}


	@Override
	public GridStyle getGridStyle() {
		return style;
	}


	@Override
	public void setGridStyle(final GridStyle gridStyle) {
		if(gridStyle != null && gridStyle != style) {
			style = gridStyle;
			setModified(true);
		}
	}


	@Override
	public boolean isGridDisplayed() {
		return style != GridStyle.NONE;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(final boolean modif) {
		modified = modif;
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		if(document == null || root == null) {
			return;
		}

		final String ns = generalPreferences ? "" : LPath.INSTANCE.getNormaliseNamespaceURI(nsURI); //NON-NLS
		Element elt = document.createElement(ns + LNamespace.XML_MAGNETIC_GRID_STYLE);
		elt.setTextContent(getGridStyle().toString());
		root.appendChild(elt);
		elt = document.createElement(ns + LNamespace.XML_GRID_GAP);
		elt.setTextContent(String.valueOf(getGridSpacing()));
		root.appendChild(elt);
		elt = document.createElement(ns + LNamespace.XML_MAGNETIC_GRID);
		elt.setTextContent(String.valueOf(isMagnetic()));
		root.appendChild(elt);
	}


	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		final NodeList nl = meta.getChildNodes();
		final String uri = nsURI == null ? "" : nsURI; //NON-NLS
		Node node;
		String name;

		for(int i = 0, size = nl.getLength(); i < size; i++) {
			node = nl.item(i);

			// Must be a latexdraw tag.
			if(node != null && uri.equals(node.getNamespaceURI())) {
				name = node.getNodeName();

				if(name.endsWith(LNamespace.XML_GRID_GAP)) {
					setGridSpacing(Integer.parseInt(node.getTextContent()));
				}else {
					if(name.endsWith(LNamespace.XML_MAGNETIC_GRID)) {
						setMagnetic(Boolean.parseBoolean(node.getTextContent()));
					}else {
						if(name.endsWith(LNamespace.XML_MAGNETIC_GRID_STYLE)) {
							setGridStyle(GridStyle.getStylefromName(node.getTextContent()).orElse(GridStyle.STANDARD));
						}
					}
				}
			}
		}
	}
}
