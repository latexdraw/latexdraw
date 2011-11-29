package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.Shape;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewModifiablePtsShape;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines an abstract view of the IModifiablePointsShape model.<br>
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
 * 03/18/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
abstract class LModifiablePointsShapeView<S extends IModifiablePointsShape> extends LShapeView<S> implements IViewModifiablePtsShape {
	/**
	 * Initialises the view of a IModifiablePointsShape.
	 */
	protected LModifiablePointsShapeView(final S model) {
		super(model);
	}


	/**
	 * Update the path of the multi-point shape.
	 * @param close True: the shape will be closed.
	 * @since 3.0
	 */
	protected void setPath(final boolean close) {
		final List<IPoint> pts = shape.getPoints();

		path.reset();

		if(pts.size()>0) {
			final int size 	= pts.size();
			IPoint pt		= pts.get(0);
			// We must check if all the points of the shape are equals because if it
			// is the case the shape will not be visible.
			final double firstX = pt.getX();
			final double firstY = pt.getY();
			double sumX	= firstX;
			double sumY	= firstY;

			path.moveTo(firstX, firstY);

			for(int i=1; i<size; i++) {
				pt = pts.get(i);
				path.lineTo(pt.getX(), pt.getY());
				// To check the points position, all the X coordinates are added together
				// and at then end we compare if the first X coordinate equals to the average of the X
				// coordinate (idem from Y coordinates). If it is the case, all the point are equals.
				sumX += pt.getX();
				sumY += pt.getY();
			}

			// Checking the equality between points of the shape.
			if(LNumber.INSTANCE.equals(firstX, sumX/size) && LNumber.INSTANCE.equals(firstY, sumY/size)) {
				// If they are all equals, we draw a tiny but visible line.
				path.reset();
				path.moveTo(firstX, firstY);
				path.lineTo(firstX+1, firstY);
			}
			else
				if(close)
					path.closePath();
		}
	}


	@Override
	public void updateBorder() {
		Shape sh;

		if(LNumber.INSTANCE.equals(shape.getRotationAngle(), 0.))
			sh = path;
		else
			sh = getRotatedShape2D();

		border.setFrame(getStroke().createStrokedShape(sh).getBounds2D());
	}


	@Override
	protected void updateDblePathInside() {
		updateDblePathMiddle();
	}


	@Override
	protected void updateDblePathMiddle() {
		updateGeneralPathMiddle();
	}


	@Override
	protected void updateDblePathOutside() {
		updateDblePathMiddle();
	}


	@Override
	protected void updateGeneralPathInside() {
		updateGeneralPathMiddle();
	}


	@Override
	protected void updateGeneralPathMiddle() {
		setPath(true);
	}


	@Override
	protected void updateGeneralPathOutside() {
		updateGeneralPathMiddle();
	}

//	@Override
//	public void update() {
//		super.update();
//
//		// Updating the handlers.
//		if(mvHandlers!=null) {
//			int size = shape.getNbPoints();
//			int diff = size-mvHandlers.size();
//
//			if(diff<0)// There is too many handlers.
//				for(int i=diff; i>0; i++)
//					mvHandlers.remove(0);
//			else
//				if(diff>0) // There is not enough handlers.
//					for(int i=0; i<diff; i++)
//						mvHandlers.add(new MovePtHandler(new LPoint(), this));
//
//			for(int i=0; i<size; i++)// The centre of the handlers must be the same points of the shape.
//				mvHandlers.elementAt(i).setCentre(shape.getPtAt(i));
//		}
//	}


//
//	@Override
//	public AbstractHandler getHandler(IPoint pt) {
//		AbstractHandler handler = super.getHandler(pt);
//
//		if(handler==null) {
//			int i=0, size = mvHandlers.size();
//			IPoint p_ = pt.rotatePoint(getGravityCentre(), -shape.getRotationAngle());
//
//			while(i<size && handler==null)
//				if(mvHandlers.elementAt(i).isIn(p_))
//					handler = mvHandlers.elementAt(i);
//				else
//					i++;
//		}
//
//		return handler;
//	}
//
//
//	/**
//	 * Moves a point of the shape to a new position.
//	 * @param point The point to move: this point must be the SAME that one of the
//	 * point of the shape.
//	 * @param newPosition The new position. The point is not replaced, only the coordinates are copied.
//	 * @return True if the point has been moved.
//	 * @since 3.0
//	 */
//	public boolean movePoint(IPoint point, IPoint newPosition) {
//		if(!GLibUtilities.INSTANCE.isValidPoint(newPosition))
//			return false;
//
//		IModifiablePointsShape sh = (IModifiablePointsShape) shape;
//		int index = shape.getPoints().indexOf(point);
//
//		if(index==-1)
//			return false;
//
//		sh.setPoint(newPosition, index);
//		shape.update();
//		update();
//
//		return true;
//	}
}
