package net.sf.latexdraw.glib.views.jfx;

import java.awt.geom.Rectangle2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LNumber;

import org.malai.action.Action;
import org.malai.action.ActionHandler;
import org.malai.action.ActionsRegistry;
import org.malai.mapping.ActiveUnary;
import org.malai.mapping.IUnary;
import org.malai.undo.Undoable;

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
 * 2014-10-15<br>
 * @author Arnaud BLOUIN
 * @since 4.0
 */
public class Canvas extends javafx.scene.canvas.Canvas implements ActionHandler {
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
	
	
	/**
	 * Creates the canvas.
	 */
	public Canvas() {
		super();
		
		drawing		 = ShapeFactory.createDrawing();
		zoom		 = new ActiveUnary<>(1.);
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

	
	
	/**
	 * Updates the size, the border of the canvas and repaints it.
	 */
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
	

	/**
	 * @return The zoom level of the canvas.
	 */
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
}
