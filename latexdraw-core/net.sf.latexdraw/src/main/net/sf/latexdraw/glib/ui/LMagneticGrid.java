package net.sf.latexdraw.glib.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Objects;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.ui.ScaleRuler.Unit;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LResources;

import org.malai.preferences.Preferenciable;
import org.malai.properties.Modifiable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class defines a magnetic grid.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 01/21/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LMagneticGrid implements Preferenciable, Modifiable {
	/**
	 * This enumeration contains the different style
	 * of a magnetic grid.
	 */
	public static enum GridStyle {
		CUSTOMISED {
			@Override
			public String getLabel() {
				return LResources.LABEL_DISPLAY_PERSO_GRID;
			}
		},
		STANDARD {
			@Override
			public String getLabel() {
				return LResources.LABEL_DISPLAY_GRID;
			}
		},
		NONE {
			@Override
			public String getLabel() {
				return "None"; //$NON-NLS-1$
			}
		};

		/**
		 * @return The label of the style.
		 * @since 3.0
		 */
		public abstract String getLabel();


		/**
		 * Searches the style which label matches the given name.
		 * @param name The name of the style to find.
		 * @return The found style or null.
		 * @since 3.0
		 */
		public static GridStyle getStylefromName(final String name) {
			for(GridStyle style : values())
				if(style.toString().equals(name))
					return style;
			return null;
		}

		/**
		 * Searches the style which label matches the given label. Warning,
		 * labels change depending on the language.
		 * @param label The label of the style to find.
		 * @return The found style or null.
		 * @since 3.0
		 */
		public static GridStyle getStyleFromLabel(final String label) {
			for(GridStyle style : values())
				if(style.getLabel().equals(label))
					return style;
			return null;
		}
	}

	/** The stroke of the grid. */
	public static final BasicStroke STROKE = new BasicStroke(0, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);

	/** Allows to know if the grid is magnetic or not. */
	protected boolean isMagnetic;

	/** defines the spacing between the lines of the grid. */
	protected int gridSpacing;

	/** The style of the grid. */
	protected GridStyle style;

	/** The canvas that paints the grid. */
	protected ICanvas canvas;

	/** Defined if the canvas has been modified. */
	protected boolean modified;


	/**
	 * Creates the magnetic grid.
	 * @param canvas The canvas in which the grid will work.
	 * @throws NullPointerException if the given parameters are not valid.
	 * @since 3.1
	 */
	public LMagneticGrid(final ICanvas canvas) {
		super();
		modified = false;
		this.canvas	= Objects.requireNonNull(canvas);
		reinitGrid();
	}


	/**
	 * Paints the magnetic grid is activated.
	 * @param graph The graphics in which the grid will be drawn.
	 * @since 3.0
	 */
	public void paint(final Graphics2D graph) {
		if(!isGridDisplayed()) return;
		final Rectangle clip = graph.getClipBounds();
		if(clip==null) return;

		graph.setColor(Color.BLACK);
		graph.setStroke(STROKE);

		switch(style) {
			case STANDARD:
				double ppc = canvas.getPPCDrawing();
				if(ScaleRuler.getUnit()==Unit.INCH)
					ppc*=PSTricksConstants.INCH_VAL_CM;

				paintSubLines(graph, clip);
				paintMainLines(graph, ppc, clip);
				break;
			case CUSTOMISED:
				paintMainLines(graph, gridSpacing, clip);
				break;
			case NONE: break;
		}
	}



	protected void paintSubLines(final Graphics2D graph, final Rectangle clip) {
		double pixPerCm10 = canvas.getPPCDrawing()*canvas.getZoom()/10.;
		if(ScaleRuler.getUnit()==Unit.INCH)
			pixPerCm10*=PSTricksConstants.INCH_VAL_CM;

		if(Double.compare(pixPerCm10, 4.)>0) {
			final Line2D line = new Line2D.Double();
			final double xMinclip = Math.floor(clip.getMinX()/pixPerCm10)*pixPerCm10-clip.getMinX();
			final double yMinclip = Math.floor(clip.getMinY()/pixPerCm10)*pixPerCm10-clip.getMinY();
			final double xMaxclip = clip.getMaxX();
			final double yMaxclip = clip.getMaxY();
			final double minX 	  = clip.getMinX();
			final double minY	  = clip.getMinY();

    		for(double i=pixPerCm10-1+xMinclip+minX+canvas.getOrigin().getX()%pixPerCm10; i<xMaxclip; i+=pixPerCm10) {
				line.setLine(i, minY, i, yMaxclip);
				graph.draw(line);
			}

    		for(double i=pixPerCm10-1+yMinclip+minY+canvas.getOrigin().getY()%pixPerCm10; i<yMaxclip; i+=pixPerCm10) {
				line.setLine(minX, i, xMaxclip, i);
				graph.draw(line);
			}
    	}
	}



	protected void paintMainLines(final Graphics2D graph, final double gap, final Rectangle clip) {
		final double gap2 	  = gap*canvas.getZoom();
		final double xMinclip = Math.floor(clip.getMinX()/gap2)*gap2-clip.getMinX();
		final double yMinclip = Math.floor(clip.getMinY()/gap2)*gap2-clip.getMinY();
		final double xMaxclip = clip.getMaxX();
		final double yMaxclip = clip.getMaxY();
		final double minX 	  = clip.getMinX();
		final double minY	  = clip.getMinY();
		final Line2D line 	  = new Line2D.Double();

		for(double i=gap2-1+xMinclip+minX+canvas.getOrigin().getX()%gap2; i<xMaxclip; i+=gap2) {
			line.setLine(i, minY, i, yMaxclip);
			graph.draw(line);
		}

		for(double j=gap2-1+yMinclip+minY+canvas.getOrigin().getY()%gap2; j<yMaxclip; j+=gap2) {
			line.setLine(minX, j, xMaxclip, j);
			graph.draw(line);
		}
	}


	/**
	 * Transform a point to another "stick" to the magnetic grid.
	 * @since 1.8
	 * @param pt The point to transform.
	 * @return The transformed point or if there is no magnetic grid, a clone of the given point.
	 */
	public IPoint getTransformedPointToGrid(final Point2D pt) {
	   	if(isMagnetic() && isGridDisplayed()) {
	   		final IPoint point  = ShapeFactory.createPoint(pt.getX(), pt.getY());
    		final double modulo = getMagneticGridGap();
    		double x 		= point.getX();
    		double y 		= point.getY();
    		int base 		= (int)((int)(x/modulo)*modulo);

    		if(x>modulo) x = x%(int)modulo;

    		double res = modulo-x;
    		x = base;

    		if(res<modulo/2.) x+=modulo;

    		point.setX((int)x);
    		base = (int)((int)(point.getY()/modulo)*modulo);

    		if(y>modulo) y = y%(int)modulo;

    		res = modulo-y;
    		y = base;

    		if(res<modulo/2.) y+=modulo;

    		point.setY((int)y);

    		return point;
    	}

	   	return ShapeFactory.createPoint(pt.getX(), pt.getY());
	}



	/**
	 * @return The gap between the lines of the magnetic grid.
	 * @since 1.9
	 */
	public double getMagneticGridGap() {
		double gap;

		if(isPersonalGrid())
			gap = getGridSpacing();
		else {
			final double ppc = canvas.getPPCDrawing();
			gap = ScaleRuler.getUnit()==Unit.CM ? ppc/10. : ppc*PSTricksConstants.INCH_VAL_CM/10.;
			gap = gap-(int)gap>0.5? (int)gap+1 : (int)gap;
		}

		return gap;
	}


	/**
	 * Reinitialises the magnetic grid.
	 * @since 3.0
	 */
	public void reinitGrid() {
		setStyle(GridStyle.CUSTOMISED);
		setGridSpacing(20);
		setMagnetic(true);
	}


	/**
	 * @return True: the grid is magnetic.
	 * @since 2.0.0
	 */
	public boolean isMagnetic() {
		return isMagnetic;
	}


	/**
	 * @param isMagnetic True: the grid will be magnetic.
	 * @since 2.0.0
	 */
	public void setMagnetic(final boolean isMagnetic) {
		if(this.isMagnetic!=isMagnetic) {
			this.isMagnetic = isMagnetic;
			setModified(true);
		}
	}


	/**
	 * @return The new spacing between lines of the personal grid.
	 * @since 3.0
	 */
	public int getGridSpacing() {
		return gridSpacing;
	}


	/**
	 * @param gridSpacing The new spacing between lines of the personal grid.
	 * @since 3.0
	 */
	public void setGridSpacing(final int gridSpacing) {
		if(gridSpacing>1 && this.gridSpacing!=gridSpacing) {
			this.gridSpacing = gridSpacing;
			setModified(true);
		}
	}


	/**
	 * @return True: the grid is magnetic.
	 * @since 2.0.0
	 */
	public boolean isPersonalGrid() {
		return style==GridStyle.CUSTOMISED;
	}


	/**
	 * @return The style of the magnetic grid.
	 * @since 2.0.0
	 */
	public GridStyle getStyle() {
		return style;
	}


	/**
	 * @param style The new style of the grid. If null, nothing is performed.
	 * @since 3.0
	 */
	public void setStyle(final GridStyle style) {
		if(style!=null && style!=this.style) {
			this.style = style;
			setModified(true);
		}
	}


	/**
	 * @return True: The magnetic grid must be displayed.
	 * @since 2.0.0
	 */
	public boolean isGridDisplayed() {
		return style!=GridStyle.NONE;
	}

	@Override
	public void setModified(final boolean modified) {
		this.modified = modified;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		if(document==null || root==null) return ;

		final String ns = generalPreferences ? "" : LPath.INSTANCE.getNormaliseNamespaceURI(nsURI); //$NON-NLS-1$
		Element elt = document.createElement(ns + LNamespace.XML_MAGNETIC_GRID_STYLE);
        elt.setTextContent(getStyle().toString());
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
		final String uri = nsURI==null ? "" : nsURI; //$NON-NLS-1$
		Node node;
		String name;

		for(int i=0, size=nl.getLength(); i<size; i++) {
			node = nl.item(i);

			// Must be a latexdraw tag.
			if(node!=null && uri.equals(node.getNamespaceURI())) {
				name = node.getNodeName();

				if(name.endsWith(LNamespace.XML_GRID_GAP))
					setGridSpacing(Integer.parseInt(node.getTextContent()));
				else if(name.endsWith(LNamespace.XML_MAGNETIC_GRID))
					setMagnetic(Boolean.parseBoolean(node.getTextContent()));
				else if(name.endsWith(LNamespace.XML_MAGNETIC_GRID_STYLE))
					setStyle(GridStyle.getStylefromName(node.getTextContent()));
				// To keep compatibility with latexdraw 2.0
				//TODO to remove with these tokens in a future release (in a couple of years).
				else if(name.endsWith(LNamespace.XML_DISPLAY_GRID)) {
					if(!Boolean.parseBoolean(node.getTextContent()))
						setStyle(GridStyle.NONE);
				} else if(name.endsWith(LNamespace.XML_CLASSIC_GRID))
					setStyle(Boolean.parseBoolean(node.getTextContent()) ? GridStyle.STANDARD : GridStyle.CUSTOMISED);
			}
		}
	}
}
