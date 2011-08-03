package net.sf.latexdraw.glib.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.Java2D.IShapeView;
import net.sf.latexdraw.instruments.Border;
import net.sf.latexdraw.util.LNamespace;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.eseo.malai.action.Action;
import fr.eseo.malai.action.ActionsRegistry;
import fr.eseo.malai.mapping.ActiveSingleton;
import fr.eseo.malai.mapping.ISingleton;
import fr.eseo.malai.picking.Pickable;
import fr.eseo.malai.picking.Picker;
import fr.eseo.malai.properties.Zoomable;
import fr.eseo.malai.widget.MPanel;

/**
 * Defines a canvas that draw the drawing and manages the selected shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/09/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class LCanvas extends MPanel implements ICanvas {
	/** This stroke is used to show the rectangle made by the user to select some shapes. */
	public static final BasicStroke STROKE_USER_SELECTION_BORDER = new BasicStroke(1f, BasicStroke.CAP_BUTT,
												BasicStroke.JOIN_MITER, 1f, new float[] { 7f, 7f}, 0);

	private static final long serialVersionUID = 1L;

	/** The shapes of the canvas. */
	protected List<IShapeView<?>> views;

	/** The temporary view that the canvas may contain. */
	protected ISingleton<IShapeView<?>> tempView;

	/** The border of the drawing. */
	protected Rectangle2D border;

	/** The zoom applied on the canvas. */
	protected ISingleton<Double> zoom;

	/** The value of the antialiasing (cf. RenderingHints.VALUE_ANTIALIAS_ON/OFF) */
	protected Object antiAliasingValue;

	/** The value of the rendering (cf. RenderingHints.VALUE_RENDER_QUALITY/SPEED) */
	protected Object renderingValue;

	/** The value of the colour rendering (cf. RenderingHints.VALUE_COLOR_RENDER_QUALITY/SPEED) */
	protected Object colorRenderingValue;

	/** The value of the alpha-interpolation ( cf. RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY/SPEED) */
	protected Object alphaInterpolValue;

	/** This rectangle is used as interim feedback to show the rectangle made by the user to select some shapes. */
	protected Rectangle2D userSelectionBorder;

	/** The instrument that manages selected views. */
	protected Border borderIns;

	/** The magnetic grid of the canvas. */
	protected LMagneticGrid magneticGrid;

	/** Defined if the canvas has been modified. */
	protected boolean modified;



	/**
	 * Creates an initialises a canvas.
	 * @since 3.0
	 */
	public LCanvas(){
		super(true, true);

		modified			= false;
		userSelectionBorder	= null;
		borderIns			= new Border();
		border				= new Rectangle2D.Double();
		views 				= new ArrayList<IShapeView<?>>();
		tempView			= new ActiveSingleton<IShapeView<?>>();
		zoom				= new ActiveSingleton<Double>();

		ActionsRegistry.INSTANCE.addHandler(this);

		setFocusable(true);
		setDoubleBuffered(true);

		magneticGrid = new LMagneticGrid(0, 0, this);

		// Maybe the magnetic must be updated when the canvas dimensions changes.
		addComponentListener(new ComponentAdapter() {
            @Override
			public void componentResized(final ComponentEvent event){
    			magneticGrid.setSize(getWidth(), getHeight());
    			repaint();
            }
        });

		reinit();
	}



	@Override
	public void reinit() {
		views.clear();
		views = new ArrayList<IShapeView<?>>();
		zoom.setValue(1.);
		update();
	}



	@Override
	public void paint(final Graphics g) {
    	g.setColor(Color.WHITE);
    	g.fillRect(0, 0, getWidth(), getHeight());

    	if(g instanceof Graphics2D)
    		paintViews((Graphics2D) g, true, true);

		g.dispose();
	}




	@Override
	public void paintViews(final Graphics2D g, final boolean withZoom, final boolean withGrid) {
		final IShapeView<?> temp = getTempView();

		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, alphaInterpolValue);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, 	   colorRenderingValue);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 	   antiAliasingValue);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, 		   renderingValue);

		// Displaying the magnetic grid.
		if(withGrid && magneticGrid.isGridDisplayed()) {
			// Updating the dimension of the grid.
//			int height = getHeight();
//			int width  = getWidth();
//
//    		if(magneticGrid.getHeight()<height || magneticGrid.getWidth()<width)
//    			magneticGrid.setSize(width, height);

    		magneticGrid.paint(g);
		}

		if(withZoom) {
			final double zoomValue = getZoom();
			g.scale(zoomValue, zoomValue);
		}

		for(IShapeView<?> view : views)
    		view.paint(g);

    	if(temp!=null)
    		temp.paint(g);

    	if(userSelectionBorder!=null) {
    		g.setStroke(STROKE_USER_SELECTION_BORDER);
    		g.setColor(Color.GRAY);
    		g.draw(userSelectionBorder);
    	}

    	borderIns.paint(g);
	}




	@Override
	public void update() {
		updateBorder();
		updatePreferredSize();
		repaint();
		revalidate();
	}




	@Override
	public void updateBorder() {
		final IShapeView<?> temp = getTempView();

		if(views.isEmpty() && temp==null)
			border.setFrame(0., 0., 0., 0.);
		else {
			double minX = Double.MAX_VALUE;
			double minY = Double.MAX_VALUE;
			double maxX = Double.MIN_VALUE;
			double maxY = Double.MIN_VALUE;

			Rectangle2D bounds;

			for(IShapeView<?> view : views) {
				bounds = view.getBorder();

				if(bounds.getMinX()<minX)
					minX = bounds.getMinX();

				if(bounds.getMinY()<minY)
					minY = bounds.getMinY();

				if(bounds.getMaxX()>maxX)
					maxX = bounds.getMaxX();

				if(bounds.getMaxY()>maxY)
					maxY = bounds.getMaxY();
			}

			if(temp!=null) {
				bounds = temp.getBorder();

				if(bounds.getMinX()<minX)
					minX = bounds.getMinX();

				if(bounds.getMinY()<minY)
					minY = bounds.getMinY();

				if(bounds.getMaxX()>maxX)
					maxX = bounds.getMaxX();

				if(bounds.getMaxY()>maxY)
					maxY = bounds.getMaxY();
			}

			border.setFrame(minX, minY, maxX-minX, maxY-minY);
		}
	}




	@Override
	public void updatePreferredSize() {
		final double zoomValue = getZoom();
		setPreferredSize(new Dimension((int)((border.getMaxX()+10)*zoomValue), (int)((border.getMaxY()+10)*zoomValue)));
	}




	@Override
	public IShapeView<?> getViewAt(final double x, final double y) {
		if(!GLibUtilities.INSTANCE.isValidPoint(x, y))
			return null;

		IShapeView<?> view = null;
		int i=views.size()-1;

		while(i>=0 && view==null)
			if(views.get(i).contains(x, y))
				view = views.get(i);
			else
				i--;

		return view;
	}



	@Override
	public Pickable getPickableAt(final double x, final double y){
		final Pickable pickable = getViewAt(x, y);

		return pickable==null ? contains((int)x, (int)y) ? this : null : pickable;
	}


	@Override
	public Picker getPickerAt(final double x, final double y){
		return null;
	}


	@Override
	public double getZoom() {
		return zoom.getValue();
	}



	@Override
	public void setZoom(final double x, final double y, final double z) {
		if(z<=Zoomable.MAX_ZOOM && z>=Zoomable.MIN_ZOOM) {
			zoom.setValue(z);
			update();
			setModified(true);
		}
	}



	@Override
	public List<IShapeView<?>> getViews() {
		return views;
	}



	@Override
	public IShapeView<?> getTempView() {
		return tempView.getValue();
	}



	@Override
	public void setTempView(final IShapeView<?> view) {
		tempView.setValue(view);
	}


	/**
	 * @return The singleton that contains the temporary view.
	 * @since 3.0
	 */
	public ISingleton<IShapeView<?>> getSingletonTempView() {
		return tempView;
	}



	@Override
	public void onAction(final Action action, final ActionEvent evt) {
		update();
	}



	@Override
	public void onActionExecuted(final Action action) {
		update();
	}



	@Override
	public IPoint getTopRightDrawingPoint() {
		return DrawingTK.getFactory().createPoint(border.getMaxX(), border.getMinY());
	}



	@Override
	public IPoint getBottomLeftDrawingPoint() {
		return DrawingTK.getFactory().createPoint(border.getMinX(), border.getMaxY());
	}



	@Override
	public IPoint getOriginDrawingPoint() {
		return DrawingTK.getFactory().createPoint(border.getMinX(), border.getCenterY());
	}



	@Override
	public int getPPCDrawing() {
		return IShape.PPC;
	}



	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		if(document==null || root==null)
			return ;

		Element elt;

		if(generalPreferences) {
            elt = document.createElement(LNamespace.XML_RENDERING);
            elt.setTextContent(String.valueOf(renderingValue==RenderingHints.VALUE_RENDER_QUALITY));
            root.appendChild(elt);

            elt = document.createElement(LNamespace.XML_COLOR_RENDERING);
            elt.setTextContent(String.valueOf(colorRenderingValue==RenderingHints.VALUE_COLOR_RENDER_QUALITY));
            root.appendChild(elt);

            elt = document.createElement(LNamespace.XML_ALPHA_INTER);
            elt.setTextContent(String.valueOf(alphaInterpolValue==RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));
            root.appendChild(elt);

            elt = document.createElement(LNamespace.XML_ANTI_ALIAS);
            elt.setTextContent(String.valueOf(antiAliasingValue==RenderingHints.VALUE_ANTIALIAS_ON));
            root.appendChild(elt);
		} else {
			final String ns = nsURI==null || nsURI.length()==0 ? "" : nsURI + ':'; //$NON-NLS-1$
			elt = document.createElement(ns + LNamespace.XML_ZOOM);
	        elt.appendChild(document.createTextNode(String.valueOf(getZoom())));
	        root.appendChild(elt);
		}
	}



	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		if(meta==null)
			return ;
		// Getting the list of meta information tags.
		final NodeList nl = meta.getChildNodes();
		Node node;
		int i;
		final int size = nl.getLength();
		final String uri = nsURI==null ? "" : nsURI; //$NON-NLS-1$
		String name;
		String content;

		// For each meta information tag.
		for(i=0; i<size; i++) {
			node 	= nl.item(i);

			// Must be a latexdraw tag.
			if(node!=null && uri.equals(node.getNamespaceURI())) {
				name 	= node.getNodeName();
				content = node.getTextContent();

				if(generalPreferences) {
					if(name.endsWith(LNamespace.XML_RENDERING))
						renderingValue = Boolean.valueOf(content).booleanValue();
					else if(name.endsWith(LNamespace.XML_COLOR_RENDERING))
						colorRenderingValue = Boolean.valueOf(content).booleanValue();
					else if(name.endsWith(LNamespace.XML_COLOR_RENDERING))
						colorRenderingValue = Boolean.valueOf(content).booleanValue();
					else if(name.endsWith(LNamespace.XML_ALPHA_INTER))
						alphaInterpolValue = Boolean.valueOf(content).booleanValue();
					else if(name.endsWith(LNamespace.XML_ANTI_ALIAS))
						antiAliasingValue = Boolean.valueOf(content).booleanValue();
				} else
					if(name.endsWith(LNamespace.XML_ZOOM))
						setZoom(0, 0, Double.valueOf(node.getTextContent()).doubleValue());
			} // if
		}// for
	}


	@Override
	public boolean contains(final double x, final double y) {
		return contains((int)x, (int)y);
	}


	@Override
	public Picker getPicker() {
		return this;
	}


	@Override
	public void setTempUserSelectionBorder(final Rectangle2D rect) {
		userSelectionBorder = rect;
	}


	@Override
	public void refresh() {
		repaint();
	}


	@Override
	public Border getBorderInstrument() {
		return borderIns;
	}


	/**
	 * @return The singleton that contains the zoom value.
	 * @since 3.0
	 */
	public ISingleton<Double> getZoomSingleton() {
		return zoom;
	}


	@Override
	public LMagneticGrid getMagneticGrid() {
		return magneticGrid;
	}


	@Override
	public void setAntiAliasing(final Object antiAliasingValue) {
		if(antiAliasingValue!=null)
			this.antiAliasingValue = antiAliasingValue;
	}

	@Override
	public void setRendering(final Object renderingValue) {
		if(renderingValue!=null)
			this.renderingValue = renderingValue;
	}

	@Override
	public void setColorRendering(final Object colorRenderingValue) {
		if(colorRenderingValue!=null)
			this.colorRenderingValue = colorRenderingValue;
	}

	@Override
	public void setAlphaInterpolation(final Object alphaInterpolValue) {
		if(alphaInterpolValue!=null)
			this.alphaInterpolValue = alphaInterpolValue;
	}


	@Override
	public boolean isModified() {
		return modified || magneticGrid.isModified();
	}

	@Override
	public void setModified(final boolean modified) {
		this.modified = modified;
		magneticGrid.setModified(modified);
	}
}
