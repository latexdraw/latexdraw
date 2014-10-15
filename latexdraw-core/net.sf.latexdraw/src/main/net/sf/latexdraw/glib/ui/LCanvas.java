package net.sf.latexdraw.glib.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.views.Java2D.impl.FlyweightThumbnail;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.ToolTipable;
import net.sf.latexdraw.instruments.Border;
import net.sf.latexdraw.mapping.ViewList2TooltipableList;
import net.sf.latexdraw.ui.TextAreaAutoSize;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LNumber;

import org.malai.action.Action;
import org.malai.action.ActionsRegistry;
import org.malai.mapping.ActiveArrayList;
import org.malai.mapping.ActiveUnary;
import org.malai.mapping.IActiveList;
import org.malai.mapping.IUnary;
import org.malai.mapping.MappingRegistry;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;
import org.malai.swing.widget.MPanel;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Defines a canvas that draw the drawing and manages the selected shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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

	/** The margin used to surround the drawing. */
	public static final int MARGINS = 2500;

	/** The origin of the drawing in the whole drawing area. */
	public static final IPoint ORIGIN = ShapeFactory.createPoint(MARGINS, MARGINS);

	/** The shapes of the canvas. */
	protected final IActiveList<IViewShape> views;

	/** The temporary view that the canvas may contain. */
	protected final IUnary<IViewShape> tempView;

	/**
	 * This list contains a subset of the list 'view'. It contains the tooltipable views of 'views'.
	 * This attribute is used only to avoid a full exploration of the list 'views' (that can be huge).
	 */
	protected final List<ToolTipable> tooltipableView;

	/** The border of the drawing. */
	protected Rectangle2D border;

	/** The zoom applied on the canvas. */
	protected final IUnary<Double> zoom;

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

	/** Defined if the canvas has been modified. */
	protected boolean modified;

	/** The model of the view. */
	protected IDrawing drawing;



	/**
	 * Creates an initialises a canvas.
	 * @param theDrawing The model of the canvas.
	 * @since 3.0
	 */
	public LCanvas(final IDrawing theDrawing){
		super(true, true);

		drawing				= theDrawing;
		modified			= false;
		userSelectionBorder	= null;
		borderIns			= new Border(this);
		border				= new Rectangle2D.Double();
		views 				= new ActiveArrayList<>();
		tooltipableView		= new ArrayList<>();
		tempView			= new ActiveUnary<>();
		zoom				= new ActiveUnary<>(1.);

		FlyweightThumbnail.setCanvas(this);
		ActionsRegistry.INSTANCE.addHandler(this);
		borderIns.addEventable(this);

		setFocusable(true);
		setDoubleBuffered(true);
		setBackground(Color.WHITE);

		// Adding a mapping between the views and its subset containing only tooltipable views.
		MappingRegistry.REGISTRY.addMapping(new ViewList2TooltipableList(views, tooltipableView));

		addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(final MouseEvent e) {
				// Workaround to assure that the canvas always has the focus.
				// This fix must not be applied when the current focused component is the text setter.
				if(!(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() instanceof TextAreaAutoSize))
					LCanvas.this.requestFocusInWindow();
			}
		});

		// Adding a kind of instrument that manages the tooltips.
		addMouseMotionListener(new TooltipDisplayer());
		update();
	}



	@Override
	public void reinit() {
		synchronized(views){views.clear();}
		zoom.setValue(1.);
		update();
	}


	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
    	if(g instanceof Graphics2D)
    		paintViews((Graphics2D)g, true, true);
	}


	@Override
	public void paintViews(final Graphics2D g, final boolean withZoom, final boolean withGrid) {
		final IViewShape temp 	= getTempView();
		final double zoomValue 	= getZoom();
		final boolean mustZoom 	= withZoom && !LNumber.equalsDouble(zoomValue, 1.);

		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, alphaInterpolValue);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, 	   colorRenderingValue);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 	   antiAliasingValue);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, 		   renderingValue);

		g.translate(ORIGIN.getX(), ORIGIN.getY());

		if(mustZoom)
			g.scale(zoomValue, zoomValue);

		// Getting the clip must be done here to consider the scaling and translation.
		final Rectangle clip = g.getClipBounds();

		synchronized(views){
			for(final IViewShape view : views)
	    		view.paint(g, clip);
		}

    	if(temp!=null)
    		temp.paint(g, clip);

    	if(userSelectionBorder!=null) {
    		g.setStroke(STROKE_USER_SELECTION_BORDER);
    		g.setColor(Color.GRAY);
    		g.draw(userSelectionBorder);
    	}

		if(mustZoom)
			g.scale(1./zoomValue, 1./zoomValue);

    	borderIns.paint(g);

    	g.translate(-ORIGIN.getX(), -ORIGIN.getY());
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
		final IViewShape temp = getTempView();

		if(views.isEmpty() && temp==null)
			border.setFrame(0., 0., 0., 0.);
		else {
			double minX = Double.MAX_VALUE;
			double minY = Double.MAX_VALUE;
			double maxX = Double.MIN_VALUE;
			double maxY = Double.MIN_VALUE;

			Rectangle2D bounds;
			synchronized(views){
				for(final IViewShape view : views) {
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
		//
	}


	@Override
	public IViewShape getViewAt(final double x, final double y) {
		if(!GLibUtilities.isValidPoint(x, y))
			return null;

		final double x2 = x/getZoom();
		final double y2 = y/getZoom();
		IViewShape view = null;
		synchronized(views){
			int i=views.size()-1;

			while(i>=0 && view==null)
				if(views.get(i).contains(x2, y2))
					view = views.get(i);
				else
					i--;
		}

		return view;
	}


	@Override
	public Pickable getPickableAt(final double x, final double y){
		final double x2 = x-ORIGIN.getX();
		final double y2 = y-ORIGIN.getY();
		Pickable pickable = borderIns.getPickableAt(x2, y2);

		if(pickable==null)
			pickable = getViewAt(x2, y2);

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
		if(z<=getMaxZoom() && z>=getMinZoom() && !LNumber.equalsDouble(z,zoom.getValue())) {
			final double oldZoom = zoom.getValue();
			zoom.setValue(z);

			if(GLibUtilities.isValidCoordinate(x) && GLibUtilities.isValidCoordinate(y)) {
				final double dx = (z-oldZoom)*(x-ORIGIN.getX())/oldZoom;
				final double dy = (z-oldZoom)*(y-ORIGIN.getY())/oldZoom;
				final Point pt = scrollpane.getViewport().getViewPosition();
				pt.x += dx;
				pt.y += dy;
				getScrollpane().getViewport().setViewPosition(pt);
			}

			borderIns.update();
			update();
			setModified(true);
		}
	}


	@Override
	public List<IViewShape> getViews() {
		return views;
	}


	@Override
	public IViewShape getTempView() {
		return tempView.getValue();
	}


	@Override
	public void setTempView(final IViewShape view) {
		tempView.setValue(view);
	}

	/**
	 * @return The unary relation that contains the temporary view.
	 * @since 3.0
	 */
	public IUnary<IViewShape> getUnaryTempView() {
		return tempView;
	}

	@Override
	public IPoint getTopRightDrawingPoint() {
		return ShapeFactory.createPoint(border.getMaxX(), border.getMinY());
	}

	@Override
	public IPoint getBottomLeftDrawingPoint() {
		return ShapeFactory.createPoint(border.getMinX(), border.getMaxY());
	}


	@Override
	public IPoint getOriginDrawingPoint() {
		return ShapeFactory.createPoint(border.getMinX(), border.getCenterY());
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
			final String ns = nsURI==null || nsURI.isEmpty() ? "" : nsURI + ':'; //$NON-NLS-1$
			elt = document.createElement(ns + LNamespace.XML_ZOOM);
	        elt.appendChild(document.createTextNode(String.valueOf(getZoom())));
	        root.appendChild(elt);
		}

//		magneticGrid.save(generalPreferences, nsURI, document, root);
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
						renderingValue = Boolean.valueOf(content);
					else if(name.endsWith(LNamespace.XML_COLOR_RENDERING))
						colorRenderingValue = Boolean.valueOf(content);
					else if(name.endsWith(LNamespace.XML_ALPHA_INTER))
						alphaInterpolValue = Boolean.valueOf(content);
					else if(name.endsWith(LNamespace.XML_ANTI_ALIAS))
						antiAliasingValue = Boolean.valueOf(content);
				} else
					if(name.endsWith(LNamespace.XML_ZOOM))
						setZoom(Double.NaN, Double.NaN, Double.parseDouble(node.getTextContent()));
			} // if
		}// for

//		magneticGrid.load(generalPreferences, nsURI, meta);
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
	 * @return The unary relation that contains the zoom value.
	 * @since 3.0
	 */
	public IUnary<Double> getZoomUnary() {
		return zoom;
	}


	@Override
	public void setAntiAliasing(final Object antiAliasingVal) {
		if(antiAliasingVal!=null)
			antiAliasingValue = antiAliasingVal;
	}

	@Override
	public void setRendering(final Object renderingVal) {
		if(renderingVal!=null)
			renderingValue = renderingVal;
	}

	@Override
	public void setColorRendering(final Object colorRenderingVal) {
		if(colorRenderingVal!=null)
			colorRenderingValue = colorRenderingVal;
	}

	@Override
	public void setAlphaInterpolation(final Object alphaInterpolVal) {
		if(alphaInterpolVal!=null)
			alphaInterpolValue = alphaInterpolVal;
	}


	@Override
	public boolean isModified() {
		return modified;// || magneticGrid.isModified();
	}

	@Override
	public void setModified(final boolean modif) {
		modified = modif;
//		if(!modif)
//			magneticGrid.setModified(false);
	}


	@Override
	public IDrawing getDrawing() {
		return drawing;
	}


	@Override
	public Point2D getZoomedPoint(final double x, final double y) {
		final double zoomValue = zoom.getValue();
		return new Point2D.Double(x/zoomValue, y/zoomValue);
	}


	@Override
	public Point2D getZoomedPoint(final Point pt) {
		return pt==null ? new Point2D.Double() : getZoomedPoint(pt.x, pt.y);
	}


	@Override
	public void onActionCancelled(final Action action) {
		// Nothing to do.
	}

	@Override
	public void onActionAdded(final Action action) {
		// Nothing to do.
	}

	@Override
	public void onActionAborted(final Action action) {
		// Nothing to do.
	}

	@Override
	public void onActionExecuted(final Action action) {
		update();
	}

	@Override
	public void onUndoableCleared() {
		// Nothing to do.
	}

	@Override
	public void onUndoableAdded(final Undoable undoable) {
		// Nothing to do.
	}

	@Override
	public void onUndoableUndo(final Undoable undoable) {
		// Nothing to do.
	}

	@Override
	public void onUndoableRedo(final Undoable undoable) {
		// Nothing to do.
	}

	@Override
	public void onActionDone(final Action action) {
		// Nothing to do.
	}


	/**
	 * This kind of instrument manages the tooltips displayed on some views.
	 */
	protected class TooltipDisplayer implements MouseMotionListener{

        @Override
		public void mouseMoved(final MouseEvent e) {
			boolean again = true;
			ToolTipable tooltipable;
			final double x = e.getX()/getZoom();
			final double y = e.getY()/getZoom();

			for(int i=0, size=tooltipableView.size(); i<size && again; i++) {
				tooltipable = tooltipableView.get(i);

				if(tooltipable.isToolTipVisible(x, y)) {
					final String text = tooltipable.getToolTip();
					setToolTipText(text==null || text.isEmpty() ? null : text);
					again = false;
				}
			}

			if(again)
				setToolTipText(null);
		}
		@Override
		public void mouseDragged(final MouseEvent e) { /* Nothing to do. */ }
	}


	@Override
	public double getZoomIncrement() {
		return 0.05;
	}


	@Override
	public double getMaxZoom() {
		return 4.5;
	}


	@Override
	public double getMinZoom() {
		return 0.1;
	}

	@Override
	public IPoint getOrigin() {
		return ORIGIN;
	}

	@Override
	public Rectangle getVisibleBound() {
		return getVisibleRect();
	}

	@Override
	public IPoint convertToOrigin(final IPoint pt) {
		final IPoint convertion;
		if(pt==null) convertion = null;
		else {
			convertion = ShapeFactory.createPoint(pt);
			convertion.translate(-ORIGIN.getX(), -ORIGIN.getY());
		}
		return convertion;
	}
}
