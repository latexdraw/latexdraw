package net.sf.latexdraw.view.jfx;

import java.awt.geom.Point2D;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.Unit;

import org.eclipse.jdt.annotation.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implementation of a magnetic grid.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
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
 * 2014-10-15<br>
 * @author Arnaud BLOUIN
 * @version 4.0
 */
class MagneticGridImpl implements MagneticGrid {
	/** Allows to know if the grid is magnetic or not. */
	protected boolean isMagnetic;

	/** defines the spacing between the lines of the grid. */
	protected int gridSpacing;

	/** The style of the grid. */
	protected GridStyle style;

	/** The canvas that paints the grid. */
	@NonNull protected final Canvas canvas;

	/** Defined if the canvas has been modified. */
	protected boolean modified;


	/**
	 * Creates the magnetic grid.
	 * @param canvas The canvas in which the grid will work.
	 * @throws NullPointerException if the given parameters are not valid.
	 */
	protected MagneticGridImpl(@NonNull final Canvas canvas) {
		super();
		modified = false;
		this.canvas	= canvas;
		reinitGrid();
	}


	/**
	 * Paints the magnetic grid is activated.
	 * @param graph The graphics in which the grid will be drawn.
	 */
	protected void paint(final GraphicsContext graph) {
		if(!isGridDisplayed()) return;
//		final Rectangle clip = graph.getClipBounds();
//		if(clip==null) return;

		graph.setStroke(Color.BLACK);
		graph.setLineWidth(0.1);

		switch(style) {
			case STANDARD:
				double ppc = canvas.getPPCDrawing();
				if(ScaleRuler.getUnit()==Unit.INCH)
					ppc*=PSTricksConstants.INCH_VAL_CM;

				paintSubLines(graph, canvas.getLayoutBounds());//FIXME clip
				paintMainLines(graph, ppc, canvas.getLayoutBounds());
				break;
			case CUSTOMISED:
				paintMainLines(graph, gridSpacing, canvas.getLayoutBounds());
				break;
			case NONE: break;
		}
	}



	protected void paintSubLines(final GraphicsContext graph, final Bounds clip) {
		double pixPerCm10 = canvas.getPPCDrawing()*canvas.getZoom()/10.;
		if(ScaleRuler.getUnit()==Unit.INCH)
			pixPerCm10*=PSTricksConstants.INCH_VAL_CM;

		if(Double.compare(pixPerCm10, 4.)>0) {
			final double xMinclip = Math.floor(clip.getMinX()/pixPerCm10)*pixPerCm10-clip.getMinX();
			final double yMinclip = Math.floor(clip.getMinY()/pixPerCm10)*pixPerCm10-clip.getMinY();
			final double xMaxclip = clip.getMaxX();
			final double yMaxclip = clip.getMaxY();
			final double minX 	  = clip.getMinX();
			final double minY	  = clip.getMinY();

    		for(double i=pixPerCm10-1+xMinclip+minX+canvas.getOrigin().getX()%pixPerCm10; i<xMaxclip; i+=pixPerCm10)
				graph.strokeLine(i, minY, i, yMaxclip);

    		for(double i=pixPerCm10-1+yMinclip+minY+canvas.getOrigin().getY()%pixPerCm10; i<yMaxclip; i+=pixPerCm10)
				graph.strokeLine(minX, i, xMaxclip, i);
    	}
	}



	protected void paintMainLines(final GraphicsContext graph, final double gap, final Bounds clip) {
		final double gap2 	  = gap*canvas.getZoom();
		final double xMinclip = Math.floor(clip.getMinX()/gap2)*gap2-clip.getMinX();
		final double yMinclip = Math.floor(clip.getMinY()/gap2)*gap2-clip.getMinY();
		final double xMaxclip = clip.getMaxX();
		final double yMaxclip = clip.getMaxY();
		final double minX 	  = clip.getMinX();
		final double minY	  = clip.getMinY();

		for(double i=gap2-1+xMinclip+minX+canvas.getOrigin().getX()%gap2; i<xMaxclip; i+=gap2) {
			graph.strokeLine(i, minY, i, yMaxclip);
		}

		for(double j=gap2-1+yMinclip+minY+canvas.getOrigin().getY()%gap2; j<yMaxclip; j+=gap2) {
			graph.strokeLine(minX, j, xMaxclip, j);
		}
	}


	@Override
	public IPoint getTransformedPointToGrid(final Point2D pt) {
	   	if(isMagnetic() && isGridDisplayed()) {
	   		final IPoint point  = ShapeFactory.createPoint(pt.getX(), pt.getY());
    		final double modulo = getMagneticGridGap();
    		double x 		= point.getX();
    		double y 		= point.getY();
    		int base 		= (int)((int)(x/modulo)*modulo);

    		if(x>modulo) x %= (int) modulo;

    		double res = modulo-x;
    		x = base;

    		if(res<modulo/2.) x+=modulo;

    		point.setX((int)x);
    		base = (int)((int)(point.getY()/modulo)*modulo);

    		if(y>modulo) y %= (int) modulo;

    		res = modulo-y;
    		y = base;

    		if(res<modulo/2.) y+=modulo;

    		point.setY((int)y);

    		return point;
    	}

	   	return ShapeFactory.createPoint(pt.getX(), pt.getY());
	}



	@Override
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


	@Override
	public void reinitGrid() {
		setStyle(GridStyle.CUSTOMISED);
		setGridSpacing(20);
		setMagnetic(true);
	}


	@Override
	public boolean isMagnetic() {
		return isMagnetic;
	}


	@Override
	public void setMagnetic(final boolean isMagnetic) {
		if(this.isMagnetic!=isMagnetic) {
			this.isMagnetic = isMagnetic;
			setModified(true);
		}
	}


	@Override
	public int getGridSpacing() {
		return gridSpacing;
	}


	@Override
	public void setGridSpacing(final int gridSpacing) {
		if(gridSpacing>1 && this.gridSpacing!=gridSpacing) {
			this.gridSpacing = gridSpacing;
			setModified(true);
		}
	}


	@Override
	public boolean isPersonalGrid() {
		return style==GridStyle.CUSTOMISED;
	}


	@Override
	public GridStyle getStyle() {
		return style;
	}


	@Override
	public void setStyle(final GridStyle style) {
		if(style!=null && style!=this.style) {
			this.style = style;
			setModified(true);
		}
	}


	@Override
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
					setStyle(GridStyle.getStylefromName(node.getTextContent()).orElse(GridStyle.STANDARD));
			}
		}
	}
}
