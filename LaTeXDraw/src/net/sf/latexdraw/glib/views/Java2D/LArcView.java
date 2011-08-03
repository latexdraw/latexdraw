package net.sf.latexdraw.glib.views.Java2D;

import net.sf.latexdraw.glib.models.interfaces.IArc;

/**
 * Defines a view of the IArc model.<br>
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
 * 03/20/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class LArcView extends LEllipseView<IArc> {
//	/** This handler helps to change the starting angle. */
//	protected ArcAngleHandler startHandler;
//
//	/** This handler helps to change the ending angle. */
//	protected ArcAngleHandler endHandler;



	/**
	 * Creates an initialises the Java view of a LArc.
	 * @param model The model to view.
	 * @since 3.0
	 */
	public LArcView(final IArc model) {
		super(model);

		update();
	}



	@Override
	protected void updateGeneralPathInside() {
		// TODO Auto-generated method stub
		super.updateGeneralPathInside();
	}



	@Override
	protected void updateGeneralPathMiddle() {
		// TODO Auto-generated method stub
		super.updateGeneralPathMiddle();
	}



	@Override
	protected void updateGeneralPathOutside() {
		// TODO Auto-generated method stub
		super.updateGeneralPathOutside();
	}



//	@Override
//	public void update() {
//		super.update();
//
//		IArc arc  = (IArc)shape;
//		IPoint gc = arc.getGravityCentre();
//		IPoint pt = arc.getStartPoint();
//
//		startHandler.setCentre((gc.getX()+pt.getX())/2.,(gc.getY()+pt.getY())/2.);
//		pt = arc.getEndPoint();
//		endHandler.setCentre((gc.getX()+pt.getX())/2.,(gc.getY()+pt.getY())/2.);
//	}
//
//
//
//	@Override
//	public void paint(Graphics2D g) {
//		super.paint(g);
//
//		if(isSelected) {
//			IPoint p = beginRotation(g);
//
//			startHandler.paint(g);
//			endHandler.paint(g);
//
//			if(p!=null)
//				endRotation(g, p);
//		}
//	}
//
//
//
//	@Override
//	public Shape createShape2D() {
//		return createMiddleShape2D();
//	}
//
//
//	@Override
//	public Shape createInsideShape2D() {
//		return createMiddleShape2D();
//	}
//
//
//	@Override
//	public Shape createOutsideShape2D() {
//		return createMiddleShape2D();
//	}
//
//
//	@Override
//	public Shape createMiddleShape2D() {
//		IArc arc 	= (IArc)shape;
//		IPoint tl 	= shape.getTopLeftPoint();
//		double startAngle 	= arc.getAngleStart();
//		double endAngle 	= arc.getAngleEnd();
//		double start = Math.toDegrees(arc.getAngleStart());
//		double end   = startAngle > endAngle ? Math.toDegrees(2*Math.PI - startAngle+endAngle) :
//											   Math.toDegrees(endAngle - startAngle);
//
//		return new Arc2D.Double(tl.getX(), tl.getY(), arc.getA()*2., arc.getB()*2., start, end, getType(arc));
//	}
//
//
//
//	/**
//	 * @return The Java2D type of the arc: open, chord or pie.
//	 * @since 3.0
//	 */
//	public static int getType(final IArc arc) {
//		if(arc == null)
//			return Arc2D.OPEN;
//
//		switch(arc.getType()) {
//			default:
//			case ARC:
//				return Arc2D.OPEN;
//
//			case CHORD:
//				return Arc2D.CHORD;
//
//			case WEDGE:
//				return Arc2D.PIE;
//		}
//	}
//
//
//
//	@Override
//	public Shape[] createDbleBorderOutside(Shape classicBord) {
//		return null;
//	}
//
//
//	@Override
//	public Shape[] createDbleBorderInside(Shape classicBord) {
//		return null;
//	}
//
//
//	@Override
//	public Shape[] createDbleBorderMiddle(Shape classicBord) {
//		return null;
//	}
//
//
//
//	@Override
//	public AbstractHandler getHandler(IPoint pt) {
//		AbstractHandler handler = super.getHandler(pt);
//
//		if(handler==null) {
//			IPoint p_ = pt.rotatePoint(getGravityCentre(), -shape.getRotationAngle());
//
//			if(startHandler.isIn(p_))
//				handler = startHandler;
//			else
//				if(endHandler.isIn(p_))
//					handler = endHandler;
//		}
//
//		return handler;
//	}
//
//
//
//	/**
//	 * Sets the starting or ending angle of the arc.
//	 * @param isStartAngle True: the starting angle will be set. Otherwise it is the ending angle.
//	 * @param pos The new position of the angle.
//	 * @since 3.0
//	 */
//	public void setAngle(boolean isStartAngle, IPoint pos)
//	{
//		if(!GLibUtilities.INSTANCE.isValidPoint(pos))
//			return ;
//
//		IArc arc = (IArc)shape;
//
//		ILine line = new LLine(arc.getGravityCentre(), pos);
//		IPoint[] inters = arc.getIntersection(line);
//
//		if(inters==null)
//			return ;
//
//		IPoint inter = inters.length==1 ? inters[0] :
//					   inters[0].distance(pos)<inters[1].distance(pos) ? inters[0] : inters[1];
//
//		double angle = Math.acos((inter.getX()-arc.getGravityCentre().getX())/arc.getA());
//
//		if(pos.getY()>arc.getGravityCentre().getY())
//			angle = 2*Math.PI - angle;
//
//		if(isStartAngle)
//			arc.setAngleStart(angle);
//		else
//			arc.setAngleEnd(angle);
//
//		update();
//	}
}
