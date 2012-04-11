package net.sf.latexdraw.instruments;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.actions.MoveCtrlPoint;
import net.sf.latexdraw.actions.MovePointShape;
import net.sf.latexdraw.actions.RotateShapes;
import net.sf.latexdraw.actions.ScaleShapes;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.handlers.ArcAngleHandler;
import net.sf.latexdraw.glib.handlers.CtrlPointHandler;
import net.sf.latexdraw.glib.handlers.IHandler;
import net.sf.latexdraw.glib.handlers.MovePtHandler;
import net.sf.latexdraw.glib.handlers.RotationHandler;
import net.sf.latexdraw.glib.handlers.ScaleHandler;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.glib.models.interfaces.IControlPointShape;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.Position;
import net.sf.latexdraw.glib.ui.ICanvas;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewArc;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewBezierCurve;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewModifiablePtsShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.mapping.Shape2BorderMapping;

import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.DnD;
import org.malai.mapping.MappingRegistry;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;

/**
 * This instrument manages the selected views.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
	protected List<IViewShape> selection;

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

//	/** The handler that sets the arc frame. */
//	protected FrameArcHandler frameArcHandler;

	/** The handler that sets the start angle of an arc. */
	protected ArcAngleHandler arcHandlerStart;

	/** The handler that sets the end angle of an arc. */
	protected ArcAngleHandler arcHandlerEnd;

	/** The handler that rotates shapes. */
	protected IHandler rotHandler;

	/** The canvas that contains the border. */
	protected ICanvas canvas;



	/**
	 * Creates and initialises the border.
	 * @param canvas The canvas that contains the border.
	 * @since 3.0
	 */
	public Border(final ICanvas canvas) {
		super();

		if(canvas==null)
			throw new IllegalArgumentException();

		this.canvas		= canvas;
		selection 		= new ArrayList<IViewShape>();
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
	public void interimFeedback() {
		canvas.setCursor(Cursor.getDefaultCursor());
	}


	/**
	 * Updates the bounding rectangle using the selected views.
	 * @since 3.0
	 */
	public void update() {
		if(selection.isEmpty())
			border.setFrame(0, 0, 1, 1);
		else {
			final double zoomLevel = canvas.getZoom();
			double minX = Double.MAX_VALUE;
			double minY = Double.MAX_VALUE;
			double maxX = Double.MIN_VALUE;
			double maxY = Double.MIN_VALUE;
			Rectangle2D bounds;

			for(final IViewShape view : selection) {
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
	}


	/**
	 * Updates the position of the handlers.
	 * @since 3.0
	 */
	private void updateHandlersPosition() {
		for(final IHandler handler : scaleHandlers)
			handler.updateFromShape(border);

		rotHandler.setPoint(border.getMaxX(), border.getMinY());

//		if(isFrameArcHandlerShowable()) {
//			if(frameArcHandler==null)
//				frameArcHandler	= new FrameArcHandler();
//			frameArcHandler.updateFromLineArcShape((ILineArcShape)selection.get(0).getShape());
//		}

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

			final IShape sh = selection.get(0).getShape();

			if(sh instanceof IArc) {
				final IArc arc = (IArc)sh;
				arcHandlerStart.updateFromArc(arc, canvas.getZoom());
				arcHandlerEnd.updateFromArc(arc, canvas.getZoom());
			}
		}
	}


	/**
	 * Updates the handlers that move control points.
	 * @since 3.0
	 */
	private void updateCtrlMvHandlers() {
		if(isCtrlPtMvHandlersShowable()) {
			final IShape sh = selection.get(0).getShape();

			if(sh instanceof IControlPointShape)
				// Lazy initialisation
				initialiseCtrlMvHandlers((IControlPointShape)sh);
		}
	}


	private void initialiseCtrlMvHandlers(final IControlPointShape cps) {
		final double zoom = canvas.getZoom();
		final int nbPts   = cps.getNbPoints();
		IPoint pt;

		if(ctrlPt1Handlers==null) {
			ctrlPt1Handlers = new ArrayList<IHandler>();
			ctrlPt2Handlers = new ArrayList<IHandler>();
		}

		// Adding missing handlers.
		if(ctrlPt1Handlers.size()<nbPts)
			for(int i=ctrlPt1Handlers.size(); i<nbPts; i++) {
				ctrlPt1Handlers.add(new CtrlPointHandler(i));
				ctrlPt2Handlers.add(new CtrlPointHandler(i));
			}
		// Removing extra handlers.
		else if(ctrlPt1Handlers.size()>nbPts)
			while(ctrlPt1Handlers.size()>nbPts) {
				ctrlPt1Handlers.remove(0);
				ctrlPt2Handlers.remove(0);
			}

		// Updating handlers.
		for(int i=0, size=ctrlPt1Handlers.size(); i<size; i++) {
			pt = cps.getFirstCtrlPtAt(i);
			ctrlPt1Handlers.get(i).setPoint(pt.getX()*zoom, pt.getY()*zoom);
			pt = cps.getSecondCtrlPtAt(i);
			ctrlPt2Handlers.get(i).setPoint(pt.getX()*zoom, pt.getY()*zoom);
		}
	}



	/**
	 * Updates the handlers that move points.
	 * @since 3.0
	 */
	private void updateMvHandlers() {
		if(isPtMvHandlersShowable()) {
			final IShape sh = selection.get(0).getShape();

			if(sh instanceof IModifiablePointsShape) {
				final IModifiablePointsShape pts = (IModifiablePointsShape)sh;
				final int nbPts 				 = pts.getNbPoints();
				final double zoom	  			 = canvas.getZoom();
				IPoint pt;

				if(mvPtHandlers==null)
					mvPtHandlers = new ArrayList<IHandler>();

				if(mvPtHandlers.size()<nbPts)
					for(int i=mvPtHandlers.size(); i<nbPts; i++)
						mvPtHandlers.add(new MovePtHandler(i));
				else if(mvPtHandlers.size()>nbPts)
						while(mvPtHandlers.size()>nbPts)
							mvPtHandlers.remove(0);

				for(int i=0, size=mvPtHandlers.size(); i<size; i++) {
					pt = pts.getPtAt(i);
					mvPtHandlers.get(i).setPoint(pt.getX()*zoom, pt.getY()*zoom);
				}
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

//		if(isFrameArcHandlerShowable())
//			frameArcHandler.paint(g);

		if(isArcHandlerShowable()) {
			arcHandlerStart.paint(g);
			arcHandlerEnd.paint(g);
		}

		if(isPtMvHandlersShowable()) {
			for(final IHandler mvHandler : mvPtHandlers)
				mvHandler.paint(g);

			if(isCtrlPtMvHandlersShowable()) {
				for(final IHandler handler : ctrlPt1Handlers)
					handler.paint(g);
				for(final IHandler handler : ctrlPt2Handlers)
					handler.paint(g);
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
		return selection.size()==1 && selection.get(0) instanceof IViewModifiablePtsShape;
	}


	/**
	 * @return True if the arc handlers can be painted.
	 */
	protected boolean isArcHandlerShowable() {
		return selection.size()==1 && selection.get(0) instanceof IViewArc;
	}


//	/**
//	 * @return True if the frame arc handler can be painted.
//	 */
//	protected boolean isFrameArcHandlerShowable() {
//		return selection.size()==1 && selection.get(0).getShape() instanceof ILineArcShape;
//	}



	/**
	 * Adds the given shape to the selection. If the instrument is
	 * activated and the addition is performed, the instrument is updated.
	 * @param view The view to add. If null, nothing is done.
	 * @since 3.0
	 */
	public void add(final IViewShape view) {
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
	public void remove(final IViewShape view) {
		if(view!=null && isActivated() && selection.remove(view)) {
			MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view, IShape.class), Shape2BorderMapping.class);
			update();
		}
	}


	/**
	 * @return the selected views. Cannot be null.
	 * @since 3.0
	 */
	public List<IViewShape> getSelection() {
		return selection;
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new DnD2Scale(this));
			addLink(new DnD2MovePoint(this));
			addLink(new DnD2MoveCtrlPoint(this));
			addLink(new DnD2Rotate(this));
		}catch(final InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(final IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * Removes all the selected views.
	 * @since 3.0
	 */
	public void clear() {
		if(!selection.isEmpty()) {
			for(final IViewShape view : selection)
				MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view, IShape.class), Shape2BorderMapping.class);

			selection.clear();
			setActivated(false);
		}
	}


	@Override
	public Pickable getPickableAt(final double x, final double y) {
		Pickable pickable;

		if(activated) {
			final double zoom = canvas.getZoom();
			final double x2 = x*zoom;
			final double y2 = y*zoom;
			pickable = getHandlerAt(x2, y2, scaleHandlers);

			if(pickable==null && rotHandler.contains(x2, y2))
				pickable = rotHandler;

			if(pickable==null)
				pickable = getHandlerAt(x2, y2, mvPtHandlers);

			if(pickable==null)
				pickable = getHandlerAt(x2, y2, ctrlPt1Handlers);

			if(pickable==null)
				pickable = getHandlerAt(x2, y2, ctrlPt2Handlers);

//			if(pickable==null && frameArcHandler!=null && frameArcHandler.contains(x2, y2))
//				pickable = frameArcHandler;

			if(pickable==null && arcHandlerStart!=null && arcHandlerStart.contains(x2, y2))
				pickable = arcHandlerStart;

			if(pickable==null && arcHandlerEnd!=null && arcHandlerEnd.contains(x2, y2))
				pickable = arcHandlerEnd;
		} else pickable = null;

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



	/**
	 * This link maps a DnD interaction on a rotation handler to an action that rotates the selected shapes.
	 */
	private static class DnD2Rotate extends Link<RotateShapes, DnD, Border> {
		/** The point corresponding to the 'press' position. */
		protected IPoint p1;

		/** The gravity centre used for the rotation. */
		protected IPoint gc;


		protected DnD2Rotate(final Border ins) throws InstantiationException, IllegalAccessException {
			super(ins, true, RotateShapes.class, DnD.class);
		}

		@Override
		public void initAction() {
			final IDrawing drawing = instrument.canvas.getDrawing();

			p1 = instrument.canvas.getMagneticGrid().getTransformedPointToGrid(instrument.canvas.getZoomedPoint(interaction.getStartPt()));
			gc = drawing.getSelection().getGravityCentre();
			action.setGc(gc);
			action.setDrawing(drawing);
			action.setShape(drawing.getSelection().duplicate());
		}


		@Override
		public void updateAction() {
			final IPoint p2 = instrument.canvas.getMagneticGrid().getTransformedPointToGrid(instrument.canvas.getZoomedPoint(interaction.getEndPt()));
			action.setRotationAngle(gc.computeRotationAngle(p1, p2));
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getStartObject()==instrument.rotHandler;
		}
	}



	/**
	 * This link maps a DnD interaction on a move control point handler to an action that moves the selected control point.
	 */
	private static class DnD2MoveCtrlPoint extends Link<MoveCtrlPoint, DnD, Border> {
		/** The original coordinates of the moved point. */
		protected IPoint sourcePt;

		protected DnD2MoveCtrlPoint(final Border ins) throws InstantiationException, IllegalAccessException {
			super(ins, true, MoveCtrlPoint.class, DnD.class);
		}


		@Override
		public void initAction() {
			final IGroup group = instrument.canvas.getDrawing().getSelection();

			if(group.size()==1 && group.getShapeAt(0) instanceof IControlPointShape) {
				final CtrlPointHandler handler = getCtrlPtHandler();
				sourcePt = DrawingTK.getFactory().createPoint(handler.getCentre());
				action.setIndexPt(handler.getIndexPt());
				action.setShape((IControlPointShape)group.getShapeAt(0));
				action.setIsFirstCtrlPt(instrument.ctrlPt1Handlers.contains(interaction.getStartObject()));
			}
		}


		@Override
		public void updateAction() {
			super.updateAction();

			final Point startPt = interaction.getStartPt();
			final Point endPt 	= interaction.getEndPt();
			final double x 		= sourcePt.getX() + endPt.getX()-startPt.getX();
			final double y 		= sourcePt.getY() + endPt.getY()-startPt.getY();

			action.setNewCoord(instrument.canvas.getMagneticGrid().getTransformedPointToGrid(instrument.canvas.getZoomedPoint(x, y)));
		}


		@Override
		public boolean isConditionRespected() {
			return getCtrlPtHandler()!=null;
		}


		/**
		 * @return The selected move control point handler or null.
		 * @since 3.0
		 */
		private CtrlPointHandler getCtrlPtHandler() {
			final Object obj = interaction.getStartObject();
			CtrlPointHandler handler = null;

			if(obj instanceof CtrlPointHandler) {
				handler = (CtrlPointHandler)(instrument.ctrlPt1Handlers.contains(obj) ? obj : null);
				if(handler==null)
					handler = (CtrlPointHandler)(instrument.ctrlPt2Handlers.contains(obj) ? obj : null);
			}

			return handler;
		}
	}



	/**
	 * This link maps a DnD interaction on a move point handler to an action that moves the selected point.
	 */
	private static class DnD2MovePoint extends Link<MovePointShape, DnD, Border> {
		/** The original coordinates of the moved point. */
		protected IPoint sourcePt;

		protected DnD2MovePoint(final Border ins) throws InstantiationException, IllegalAccessException {
			super(ins, true, MovePointShape.class, DnD.class);
		}

		@Override
		public void initAction() {
			final IGroup group = instrument.canvas.getDrawing().getSelection();

			if(group.size()==1 && group.getShapeAt(0) instanceof IModifiablePointsShape) {
				final MovePtHandler handler = getMovePtHandler();
				sourcePt = DrawingTK.getFactory().createPoint(handler.getCentre());
				action.setIndexPt(handler.getIndexPt());
				action.setShape((IModifiablePointsShape)group.getShapeAt(0));
			}
		}


		@Override
		public void updateAction() {
			super.updateAction();

			final Point startPt = interaction.getStartPt();
			final Point endPt 	= interaction.getEndPt();
			final double x 		= sourcePt.getX() + endPt.getX()-startPt.getX();
			final double y 		= sourcePt.getY() + endPt.getY()-startPt.getY();

			action.setNewCoord(instrument.canvas.getMagneticGrid().getTransformedPointToGrid(instrument.canvas.getZoomedPoint(x, y)));
		}


		@Override
		public boolean isConditionRespected() {
			return getMovePtHandler()!=null;
		}


		/**
		 * @return The selected move point handler or null.
		 * @since 3.0
		 */
		private MovePtHandler getMovePtHandler() {
			final Object obj = interaction.getStartObject();
			return obj instanceof MovePtHandler && instrument.mvPtHandlers.contains(obj) ? (MovePtHandler)obj : null;
		}
	}



	/**
	 * This link maps a DnD interaction on a scale handler to an action that scales the selection.
	 */
	private static class DnD2Scale extends Link<ScaleShapes, DnD, Border> {
		/** The point corresponding to the 'press' position. */
		protected IPoint p1;

		/** The x gap (gap between the pressed position and the targeted position) of the X-scaling. */
		protected double xGap;

		/** The y gap (gap between the pressed position and the targeted position) of the Y-scaling. */
		protected double yGap;


		protected DnD2Scale(final Border ins) throws InstantiationException, IllegalAccessException {
			super(ins, true, ScaleShapes.class, DnD.class);
		}


		private void setXGap(final Position refPosition, final IPoint tl, final IPoint br) {
			switch(refPosition) {
				case NW: case SW: case WEST: xGap = p1.getX() - br.getX(); break;
				case NE: case SE: case EAST: xGap = tl.getX() - p1.getX(); break;
				default: xGap = 0.;
			}
		}

		private void setYGap(final Position refPosition, final IPoint tl, final IPoint br) {
			switch(refPosition) {
				case NW: case NE: case NORTH: yGap = p1.getY() - br.getY(); break;
				case SW: case SE: case SOUTH: yGap = tl.getY() - p1.getY(); break;
				default: yGap = 0.;
			}
		}


		@Override
		public void initAction() {
			final IDrawing drawing = instrument.canvas.getDrawing();
			final Position refPosition = getScaleHandler().getPosition().getOpposite();
			final IPoint br = drawing.getSelection().getBottomRightPoint();
			final IPoint tl = drawing.getSelection().getTopLeftPoint();

			p1 = instrument.canvas.getMagneticGrid().getTransformedPointToGrid(instrument.canvas.getZoomedPoint(interaction.getStartPt()));

			setXGap(refPosition, tl, br);
			setYGap(refPosition, tl, br);
			action.setDrawing(drawing);
			action.setShape((IGroup)drawing.getSelection().duplicate());
			action.setRefPosition(refPosition);
		}


		@Override
		public void updateAction() {
			super.updateAction();

			final IPoint pt = instrument.canvas.getMagneticGrid().getTransformedPointToGrid(instrument.canvas.getZoomedPoint(interaction.getEndPt()));
			final Position refPosition = action.getRefPosition();

			if(refPosition.isSouth())
				action.setNewY(pt.getY() + yGap);
			else if(refPosition.isNorth())
				action.setNewY(pt.getY() - yGap);

			if(refPosition.isWest())
				action.setNewX(pt.getX() - xGap);
			else if(refPosition.isEast())
				action.setNewX(pt.getX() + xGap);
		}


		@Override
		public boolean isConditionRespected() {
			return getScaleHandler()!=null;
		}


		@Override
		public void interimFeedback() {
			super.interimFeedback();
			switch(action.getRefPosition()) {
				case EAST: instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR)); break;
				case NE: instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR)); break;
				case NORTH: instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR)); break;
				case NW: instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR)); break;
				case SE: instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR)); break;
				case SOUTH: instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR)); break;
				case SW: instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR)); break;
				case WEST: instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR)); break;
			}
		}


		private ScaleHandler getScaleHandler() {
			final Object obj = interaction.getStartObject();
			return obj instanceof ScaleHandler && instrument.scaleHandlers.contains(obj) ? (ScaleHandler)obj : null;
		}
	}
}
