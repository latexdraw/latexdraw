package net.sf.latexdraw.glib.views.jfx;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.synchroniser.ViewsSynchroniserHandler;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LNumber;

import org.malai.action.Action;
import org.malai.action.ActionHandler;
import org.malai.action.ActionsRegistry;
import org.malai.mapping.ActiveUnary;
import org.malai.mapping.IUnary;
import org.malai.presentation.ConcretePresentation;
import org.malai.properties.Zoomable;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Defines a canvas that draw the drawing and manages the selected shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-10-15<br>
 * @author Arnaud BLOUIN
 * @since 4.0
 */
public class Canvas extends javafx.scene.canvas.Canvas implements ConcretePresentation, ActionHandler, Zoomable, ViewsSynchroniserHandler {
	/** The margin used to surround the drawing. */
	public static final int MARGINS = 2500;

	/** The origin of the drawing in the whole drawing area. */
	public static final IPoint ORIGIN = ShapeFactory.createPoint(MARGINS, MARGINS);
	
	/** The model of the view. */
	protected final IDrawing drawing;
	
	/** The zoom applied on the canvas. */
	protected final IUnary<Double> zoom;
	
	/** The current page of the canvas. */
	protected Page page;
	
	/** The border of the drawing. */
	protected final Rectangle2D border;
	
	/** The magnetic grid of the canvas. */
	protected final MagneticGridImpl magneticGrid;
	
	/** Defined whether the canvas has been modified. */
	protected boolean modified;
	
	/** The temporary view that the canvas may contain. */
	protected final IUnary<IViewShape> tempView;
	
	
	/**
	 * Creates the canvas.
	 */
	public Canvas() {
		super();
		setWidth(986.0);
		setHeight(765.0);
		modified	 = false;
		drawing		 = ShapeFactory.createDrawing();
		zoom		 = new ActiveUnary<>(1.);
		tempView	 = new ActiveUnary<>();
		page 		 = Page.USLETTER;
		border		 = new Rectangle2D.Double();
		magneticGrid = new MagneticGridImpl(this);
		
//		FlyweightThumbnail.setCanvas(this);
		ActionsRegistry.INSTANCE.addHandler(this);
//		borderIns.addEventable(this);
	}

	
	/**
	 * Repaints the canvas.
	 * @param withZoom True: zoom will be activated.
	 * @param withGrid True: the grid will be painted.
	 */
	public void repaint(final boolean withZoom, final boolean withGrid) {
		final GraphicsContext gc = getGraphicsContext2D();
		final double zoomValue 	 = getZoom();
		final boolean mustZoom 	= withZoom && !LNumber.equalsDouble(zoomValue, 1.);

		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, getWidth(), getHeight());

		// Displaying the magnetic grid.
		if(withGrid && magneticGrid.isGridDisplayed())
    		magneticGrid.paint(gc);

		gc.translate(ORIGIN.getX(), ORIGIN.getY());

		if(mustZoom)
			gc.scale(zoomValue, zoomValue);

		page.paint(gc, ShapeFactory.createPoint());

		// Getting the clip must be done here to consider the scaling and translation.
//		final Rectangle clip = gc.getClipBounds();

//		synchronized(views){
//			for(final IViewShape view : views)
//	    		view.paint(g, clip);
//		}

//    	if(temp!=null)
//    		temp.paint(g, clip);

		if(mustZoom)
			gc.scale(1./zoomValue, 1./zoomValue);

//    	borderIns.paint(g);

    	gc.translate(-ORIGIN.getX(), -ORIGIN.getY());
	}
	
	public IPoint getOrigin() {
		return ORIGIN;
	}
	
	@Override
	public int getPPCDrawing() {
		return IShape.PPC;
	}
	
	public void centreViewport() {
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				final MoveCamera action = new MoveCamera();
//				action.setScrollPane(getScrollpane());
//				action.setPx(ORIGIN.getX()+page.getWidth()*IShape.PPC/2.);
//				action.setPy(ORIGIN.getY()+getVisibleBound().getHeight()/2.-IShape.PPC);
//				if(action.canDo())
//					action.doIt();
//				action.flush();
//			}
//		});
	}

	
	
	@Override
	public void update() {
		updateBorder();
		updatePreferredSize();
		repaint(true, true);
//		revalidate();
	}
	

	/**
	 * Updates the size of the canvas.
	 */
	public void updatePreferredSize() {
		final double zoomValue = getZoom();
		setWidth(Math.max(border.getWidth(), page.getWidth())*zoomValue+MARGINS*2);
		setHeight(Math.max(border.getHeight(), page.getHeight())*zoomValue+MARGINS*2);
	}
	
	
	public void updateBorder() {
//		final IViewShape temp = getTempView();

//		if(views.isEmpty() && temp==null)
//			border.setFrame(0., 0., 0., 0.);
//		else {
//			double minX = Double.MAX_VALUE;
//			double minY = Double.MAX_VALUE;
//			double maxX = Double.MIN_VALUE;
//			double maxY = Double.MIN_VALUE;
//
//			Rectangle2D bounds;
//			synchronized(views){
//				for(final IViewShape view : views) {
//					bounds = view.getBorder();
//
//					if(bounds.getMinX()<minX)
//						minX = bounds.getMinX();
//
//					if(bounds.getMinY()<minY)
//						minY = bounds.getMinY();
//
//					if(bounds.getMaxX()>maxX)
//						maxX = bounds.getMaxX();
//
//					if(bounds.getMaxY()>maxY)
//						maxY = bounds.getMaxY();
//				}
//			}
//
//			if(temp!=null) {
//				bounds = temp.getBorder();
//
//				if(bounds.getMinX()<minX)
//					minX = bounds.getMinX();
//
//				if(bounds.getMinY()<minY)
//					minY = bounds.getMinY();
//
//				if(bounds.getMaxX()>maxX)
//					maxX = bounds.getMaxX();
//
//				if(bounds.getMaxY()>maxY)
//					maxY = bounds.getMaxY();
//			}
//
//			border.setFrame(minX, minY, maxX-minX, maxY-minY);
//		}
		border.setFrame(0,0,1,1);
	}
	

	@Override
	public double getZoom() {
		return zoom.getValue();
	}


	@Override public void onActionExecuted(final Action a) {
		update();
	}

	@Override public void onUndoableAdded(final Undoable u) {/* Nothing to do. */ }
	@Override public void onUndoableCleared() {/* Nothing to do. */ }
	@Override public void onUndoableRedo(final Undoable u) {/* Nothing to do. */ }
	@Override public void onUndoableUndo(final Undoable u) {/* Nothing to do. */ }
	@Override public void onActionAborted(final Action a) {/* Nothing to do. */ }
	@Override public void onActionAdded(final Action a) {/* Nothing to do. */ }
	@Override public void onActionCancelled(final Action a) {/* Nothing to do. */ }
	@Override public void onActionDone(final Action a) {/* Nothing to do. */ }


	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		if(document==null || root==null) return ;

		Element elt;

		if(!generalPreferences) {
			final String ns = nsURI==null || nsURI.isEmpty() ? "" : nsURI + ':'; //$NON-NLS-1$
			elt = document.createElement(ns + LNamespace.XML_ZOOM);
	        elt.appendChild(document.createTextNode(String.valueOf(getZoom())));
	        root.appendChild(elt);
		}

		magneticGrid.save(generalPreferences, nsURI, document, root);
	}


	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		if(meta==null) return ;
		// Getting the list of meta information tags.
		final NodeList nl = meta.getChildNodes();
		Node node;
		int i;
		final int size = nl.getLength();
		final String uri = nsURI==null ? "" : nsURI; //$NON-NLS-1$
		String name;

		// For each meta information tag.
		for(i=0; i<size; i++) {
			node = nl.item(i);

			// Must be a latexdraw tag.
			if(node!=null && uri.equals(node.getNamespaceURI())) {
				name = node.getNodeName();

				if(!generalPreferences && name.endsWith(LNamespace.XML_ZOOM))
					setZoom(Double.NaN, Double.NaN, Double.parseDouble(node.getTextContent()));
			} // if
		}// for

		magneticGrid.load(generalPreferences, nsURI, meta);
	}


	@Override
	public boolean isModified() {
		return modified || magneticGrid.isModified();
	}


	@Override
	public void setModified(final boolean modif) {
		modified = modif;
		if(!modif)
			magneticGrid.setModified(false);
	}


	@Override
	public void reinit() {
//		synchronized(views){views.clear();}
		zoom.setValue(1.);
		update();		
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
	public Point2D getZoomedPoint(final double x, final double y) {
		final double zoomValue = zoom.getValue();
		return new Point2D.Double(x/zoomValue, y/zoomValue);
	}


	@Override
	public Point2D getZoomedPoint(final Point pt) {
		return pt==null ? new Point2D.Double() : getZoomedPoint(pt.x, pt.y);
	}


	@Override
	public void setZoom(final double x, final double y, final double z) {
		if(z<=getMaxZoom() && z>=getMinZoom() && !LNumber.equalsDouble(z,zoom.getValue())) {
//			final double oldZoom = zoom.getValue();
			zoom.setValue(z);

//			if(GLibUtilities.isValidCoordinate(x) && GLibUtilities.isValidCoordinate(y)) {
//				final double dx = (z-oldZoom)*(x-ORIGIN.getX())/oldZoom;
//				final double dy = (z-oldZoom)*(y-ORIGIN.getY())/oldZoom;
//				final Point pt = scrollpane.getViewport().getViewPosition();
//				pt.x += dx;
//				pt.y += dy;
//				getScrollpane().getViewport().setViewPosition(pt);
//			}
//
//			borderIns.update();
			update();
			setModified(true);
		}
	}
	
	
	/**
	 * Converts the given point in the coordinate system based on the canvas' origin.
	 * The given point must be in the coordinate system of a container widget (the top-left point is the origin).
	 * @param pt The point to convert.
	 * @return The converted point or null if the given point is null.
	 */
	public IPoint convertToOrigin(final IPoint pt) {
		final IPoint convertion;
		if(pt==null) convertion = null;
		else {
			convertion = ShapeFactory.createPoint(pt);
			convertion.translate(-ORIGIN.getX(), -ORIGIN.getY());
		}
		return convertion;
	}
	
	
	/**
	 * @return The model of the canvas.
	 */
	public IDrawing getDrawing() {
		return drawing;
	}
	
	
	/**
	 * Sets the temporary view.
	 * @param view The new temporary view.
	 */
	public void setTempView(final IViewShape view) {
		tempView.setValue(view);
	}
}
