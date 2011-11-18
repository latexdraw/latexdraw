package net.sf.latexdraw.instruments;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.handlers.ArcAngleHandler;
import net.sf.latexdraw.glib.handlers.FrameArcHandler;
import net.sf.latexdraw.glib.handlers.IHandler;
import net.sf.latexdraw.glib.handlers.MovePtHandler;
import net.sf.latexdraw.glib.handlers.RotationHandler;
import net.sf.latexdraw.glib.handlers.ScaleHandler;
import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.Position;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewArc;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewBezierCurve;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewModifiablePtsShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewRectangle;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.mapping.Shape2BorderMapping;

import org.malai.instrument.Instrument;
import org.malai.mapping.MappingRegistry;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;
import org.malai.properties.Zoomable;

/**
 * This instrument manages the selected views.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
 * 10/27/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Border extends Instrument implements Picker {
	/** The stroke uses by the border to display its bounding rectangle. */
	public static final BasicStroke STROKE = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, new float[] { 7f, 7f}, 0);

	/** The selected views. */
	protected List<IViewShape<?>> selection;

	/** The rectangle uses to show the selection. */
	protected Rectangle2D border;

	/** The handlers that scale shapes. */
	protected List<IHandler> scaleHandlers;

	/** The handlers that move points. */
	protected List<IHandler> mvPtHandlers;

	/** The handlers that move first control points. */
	protected List<IHandler> ctrlPt1Handlers;

	/** The handlers that move second control points. */
	protected List<IHandler> ctrlPt2Handlers;

	/** The handler that sets the arc frame. */
	protected IHandler frameArcHandler;

	/** The handler that sets the start angle of an arc. */
	protected ArcAngleHandler arcHandlerStart;

	/** The handler that sets the end angle of an arc. */
	protected ArcAngleHandler arcHandlerEnd;

	/** The handler that rotates shapes. */
	protected IHandler rotHandler;

	/** The object that contain the zoom used to display shapes and thus the border. */
	protected Zoomable zoomable;



	/**
	 * Creates and initialises the border.
	 * @since 3.0
	 */
	public Border(final Zoomable zoomable) {
		super();

		if(zoomable==null)
			throw new IllegalArgumentException();

		this.zoomable	= zoomable;
		selection 		= new ArrayList<IViewShape<?>>();
		border	  		= new Rectangle2D.Double();
		scaleHandlers  	= new ArrayList<IHandler>();

		// Initialisation of the handlers that are always used.
		scaleHandlers.add(new ScaleHandler(Position.NW));
		scaleHandlers.add(new ScaleHandler(Position.NORTH));
		scaleHandlers.add(new ScaleHandler(Position.NE));
		scaleHandlers.add(new ScaleHandler(Position.WEST));
		scaleHandlers.add(new ScaleHandler(Position.EAST));
		scaleHandlers.add(new ScaleHandler(Position.SW));
		scaleHandlers.add(new ScaleHandler(Position.SOUTH));
		scaleHandlers.add(new ScaleHandler(Position.SE));
		rotHandler 		= new RotationHandler();
	}


	@Override
	public void reinit() {
		selection.clear();
		border.setFrame(0., 0., 1., 1.);
	}

	@Override
	public boolean isActivated() {
		return super.isActivated() && selection.size()>0;
	}


	/**
	 * Updates the bounding rectangle using the selected views.
	 * @since 3.0
	 */
	public void update() {
		final double zoomLevel = zoomable.getZoom();
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		Rectangle2D bounds;

		for(final IViewShape<?> view : selection) {
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

		border.setFrame(minX*zoomLevel, minY*zoomLevel, (maxX-minX)*zoomLevel, (maxY-minY)*zoomLevel);
		updateHandlersPosition();
	}


	/**
	 * Updates the position of the handlers.
	 * @since 3.0
	 */
	private void updateHandlersPosition() {
		for(final IHandler handler : scaleHandlers)
			handler.updateFromShape(border);

		rotHandler.setPoint(border.getMaxX(), border.getMinY());

		if(isFrameArcHandlerShowable()) {
			if(frameArcHandler==null)
				frameArcHandler	= new FrameArcHandler();
			frameArcHandler.updateFromShape(border);
		}

		updateArcHandlers();
		updateMvHandlers();
		updateCtrlMvHandlers();
	}


	/**
	 * Updates the arc handlers.
	 * @since 3.0
	 */
	private void updateArcHandlers() {
		if(isArcHandlerShowable()) {
			if(arcHandlerEnd==null) {
				arcHandlerStart = new ArcAngleHandler(true);
				arcHandlerEnd 	= new ArcAngleHandler(false);
			}

			final IArc arc = ((IViewArc)selection.get(0)).getShape();
			arcHandlerStart.updateFromArc(arc, zoomable.getZoom());
			arcHandlerEnd.updateFromArc(arc, zoomable.getZoom());
		}
	}


	/**
	 * Updates the handlers that move control points.
	 * @since 3.0
	 */
	private void updateCtrlMvHandlers() {
		if(isCtrlPtMvHandlersShowable()) {
			final double zoom	  = zoomable.getZoom();
			final IBezierCurve bc = ((IViewBezierCurve)selection.get(0)).getShape();
			final int nbPts 	  = bc.getNbPoints();
			IPoint pt;

			// Lazy initialisation
			if(ctrlPt1Handlers==null) {
				ctrlPt1Handlers = new ArrayList<IHandler>();
				ctrlPt2Handlers = new ArrayList<IHandler>();
			}

			// Adding missing handlers.
			if(ctrlPt1Handlers.size()<nbPts)
				for(int i=ctrlPt1Handlers.size(); i<nbPts; i++) {
					ctrlPt1Handlers.add(new MovePtHandler());
					ctrlPt2Handlers.add(new MovePtHandler());
				}
			// Removing extra handlers.
			else if(ctrlPt1Handlers.size()>nbPts)
				while(ctrlPt1Handlers.size()>nbPts) {
					ctrlPt1Handlers.remove(0);
					ctrlPt2Handlers.remove(0);
				}

			// Updating handlers.
			for(int i=0, size=ctrlPt1Handlers.size(); i<size; i++) {
				pt = bc.getFirstCtrlPtAt(i);
				ctrlPt1Handlers.get(i).setPoint(pt.getX()*zoom, pt.getY()*zoom);
				pt = bc.getSecondCtrlPtAt(i);
				ctrlPt2Handlers.get(i).setPoint(pt.getX()*zoom, pt.getY()*zoom);
			}
		}
	}


	/**
	 * Updates the handlers that move points.
	 * @since 3.0
	 */
	private void updateMvHandlers() {
		if(isPtMvHandlersShowable()) {
			final IModifiablePointsShape pts = ((IViewModifiablePtsShape<IModifiablePointsShape>)selection.get(0)).getShape();
			final int nbPts 				 = pts.getNbPoints();
			final double zoom	  			 = zoomable.getZoom();
			IPoint pt;

			if(mvPtHandlers==null)
				mvPtHandlers = new ArrayList<IHandler>();

			if(mvPtHandlers.size()<nbPts)
				for(int i=mvPtHandlers.size(); i<nbPts; i++)
					mvPtHandlers.add(new MovePtHandler());
			else if(mvPtHandlers.size()>nbPts)
					while(mvPtHandlers.size()>nbPts)
						mvPtHandlers.remove(0);

			for(int i=0, size=mvPtHandlers.size(); i<size; i++) {
				pt = pts.getPtAt(i);
				mvPtHandlers.get(i).setPoint(pt.getX()*zoom, pt.getY()*zoom);
			}

		}
	}


	/**
	 * Paints the border if activated.
	 * @param g The graphics in which the border is painted.
	 * @since 3.0
	 */
	public void paint(final Graphics2D g) {
		if(isActivated()) {
			g.setColor(Color.GRAY);
			g.setStroke(STROKE);
			g.draw(border);
			paintHandlers(g);
		}
	}


	/**
	 * Paints the required handlers.
	 */
	private void paintHandlers(final Graphics2D g) {
		for(final IHandler handler : scaleHandlers)
			handler.paint(g);

		rotHandler.paint(g);

		if(isFrameArcHandlerShowable())
			frameArcHandler.paint(g);

		if(isArcHandlerShowable()) {
			arcHandlerStart.paint(g);
			arcHandlerEnd.paint(g);
		}

		if(isPtMvHandlersShowable()) {
			for(final IHandler mvHandler : mvPtHandlers)
				mvHandler.paint(g);

			if(isCtrlPtMvHandlersShowable())
				for(int i=0, size=ctrlPt1Handlers.size(); i<size; i++) {
					ctrlPt1Handlers.get(i).paint(g);
					ctrlPt2Handlers.get(i).paint(g);
				}
		}
	}



	/**
	 * @return True if the control move point handlers can be painted.
	 */
	protected boolean isCtrlPtMvHandlersShowable() {
		return selection.size()==1 && selection.get(0) instanceof IViewBezierCurve;
	}


	/**
	 * @return True if the move point handlers can be painted.
	 */
	protected boolean isPtMvHandlersShowable() {
		return selection.size()==1 && selection.get(0) instanceof IViewModifiablePtsShape<?>;
	}


	/**
	 * @return True if the arc handlers can be painted.
	 */
	protected boolean isArcHandlerShowable() {
		return selection.size()==1 && selection.get(0) instanceof IViewArc;
	}


	/**
	 * @return True if the frame arc handler can be painted.
	 */
	protected boolean isFrameArcHandlerShowable() {
		return selection.size()==1 && selection.get(0) instanceof IViewRectangle;
	}



	/**
	 * Adds the given shape to the selection. If the instrument is
	 * activated and the addition is performed, the instrument is updated.
	 * @param view The view to add. If null, nothing is done.
	 * @since 3.0
	 */
	public void add(final IViewShape<?> view) {
		if(view!=null && selection.add(view) && isActivated()) {
			// The border is updated only if the view has been added and
			// the border is activated.
			update();
			MappingRegistry.REGISTRY.addMapping(new Shape2BorderMapping(MappingRegistry.REGISTRY.getSourceFromTarget(view, IShape.class), this));
		}
	}



	/**
	 * Removes the given view from the selection. If the instrument is
	 * activated and the removal is performed, the instrument is updated.
	 * @param view The view to remove. If null or it is not
	 * already in the selection, nothing is performed.
	 * @since 3.0
	 */
	public void remove(final IViewShape<?> view) {
		if(view!=null && isActivated() && selection.remove(view)) {
			MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view, IShape.class), Shape2BorderMapping.class);
			update();
		}
	}


	/**
	 * @return the selected views. Cannot be null.
	 * @since 3.0
	 */
	public List<IViewShape<?>> getSelection() {
		return selection;
	}


	@Override
	protected void initialiseLinks() {
		// Nothing to do for the moment.
	}


	/**
	 * Removes all the selected views.
	 * @since 3.0
	 */
	public void clear() {
		if(!selection.isEmpty()) {
			for(IViewShape<?> view : selection)
				MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view, IShape.class), Shape2BorderMapping.class);

			selection.clear();

			if(isActivated())
				update();
		}
	}


	@Override
	public Pickable getPickableAt(final double x, final double y) {
		final double zoom = zoomable.getZoom();
		final double x2 = x*zoom;
		final double y2 = y*zoom;
		Pickable pickable = getHandlerAt(x2, y2, scaleHandlers);

		if(pickable==null && rotHandler.contains(x2, y2))
			pickable = rotHandler;

		if(pickable==null)
			pickable = getHandlerAt(x2, y2, mvPtHandlers);

		if(pickable==null)
			pickable = getHandlerAt(x2, y2, ctrlPt1Handlers);

		if(pickable==null)
			pickable = getHandlerAt(x2, y2, ctrlPt2Handlers);

		if(pickable==null && frameArcHandler!=null && frameArcHandler.contains(x2, y2))
			pickable = frameArcHandler;

		if(pickable==null && arcHandlerStart!=null && arcHandlerStart.contains(x2, y2))
			pickable = arcHandlerStart;

		if(pickable==null && arcHandlerEnd!=null && arcHandlerEnd.contains(x2, y2))
			pickable = arcHandlerEnd;

		return pickable;
	}



	private IHandler getHandlerAt(final double x, final double y, final List<IHandler> handlers)  {
		if(handlers!=null)
			for(final IHandler handler : handlers)
				if(handler.contains(x, y))
					return handler;

		return null;
	}


	@Override
	public Picker getPickerAt(final double x, final double y) {
		// No picker for the border.
		return null;
	}


	@Override
	public Point2D getRelativePoint(final double x, final double y, final Object o) {
		return new Point2D.Double(x, y);
	}


	@Override
	public boolean contains(final Object obj) {
		// Supposing that there is no handler outside the border.
		return obj instanceof IHandler;
	}
}
