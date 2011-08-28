package net.sf.latexdraw.glib.views.Java2D;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

/**
 * Defines a view of the model IBeziershape.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 03/01/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LBezierCurveView extends LModifiablePointsShapeView<IBezierCurve> {
	public static final BasicStroke STROKE_CTRL_LINES = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.f, new float[]{5f, 3f}, 0f);


	/**
	 * Creates an initialises the Java view of a LBeziershape.
	 * @param model The model to view.
	 * @since 3.0
	 */
	public LBezierCurveView(final IBezierCurve model) {
		super(model);
		update();
	}


	@Override
	public void paintShowPointsDots(final Graphics2D g) {//FIXME to add into the path not into the graphics
		final boolean isClosed		= shape.isClosed();
		final IArrow arr1			= shape.getArrowAt(0);
		boolean arrow1Drawable 		= arr1.hasStyle() && shape.getNbPoints()>1;
		boolean arrow2Drawable 		= shape.getArrowAt(1).hasStyle() && shape.getNbPoints()>1 && !isClosed;
		final int size 				= shape.getNbPoints();
		final List<IPoint> pts 		= shape.getPoints();
		final List<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
		final List<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
		final double width 			= arr1.getDotSizeDim() + arr1.getDotSizeNum()*shape.getThickness();
		Ellipse2D.Double d 			= new Ellipse2D.Double(0, 0, width, width);
		int i;

		g.setColor(shape.getLineColour());

		if(!arrow1Drawable || isClosed)
			fillCircle(d, pts.get(0), width, g);

		if(!arrow2Drawable || isClosed)
			fillCircle(d, pts.get(size-1), width, g);

		for(i=1; i<size-1; i++) {
			fillCircle(d, pts.get(i), width, g);
			fillCircle(d, ctrlPts2.get(i), width, g);
		}

		for(i=0; i<size; i++)
			fillCircle(d, ctrlPts1.get(i), width, g);

		if(shape.isClosed()) {
			fillCircle(d, ctrlPts2.get(ctrlPts2.size()-1), width, g);
			fillCircle(d, ctrlPts2.get(0), width, g);
		}
	}



	private void fillCircle(final Ellipse2D ell, final IPoint pt, final double width, final Graphics2D g) {
		ell.setFrame(pt.getX()-width/2., pt.getY()-width/2., width, width);
		g.fill(ell);
	}



	private void paintLine(final Line2D line, final IPoint pt1, final IPoint pt2, final Graphics2D g) {
		line.setLine(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
		g.draw(line);
	}


	@Override
	public void paintShowPointsLines(final Graphics2D g) {
		final int size 				= shape.getNbPoints();
		final List<IPoint> pts 		= shape.getPoints();
		final List<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
		final List<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
		final float thick 			= (float)(shape.hasDbleBord()? shape.getDbleBordSep()+shape.getThickness()*2. : shape.getThickness());
		Line2D.Double line 			= new Line2D.Double();
		int i;

		g.setStroke(new BasicStroke(thick/2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
					1.f, new float[]{(float)shape.getDashSepBlack(), (float)shape.getDashSepWhite()}, 0));
		g.setColor(shape.getLineColour());

		for(i=3; i<size; i+=2) {
			paintLine(line, pts.get(i-1), ctrlPts2.get(i-1), g);
			paintLine(line, ctrlPts2.get(i-1), ctrlPts1.get(i), g);
			paintLine(line, ctrlPts1.get(i), pts.get(i), g);
		}

		for(i=2; i<size; i+=2) {
			paintLine(line, pts.get(i-1), ctrlPts2.get(i-1), g);
			paintLine(line, ctrlPts2.get(i-1), ctrlPts1.get(i), g);
			paintLine(line, ctrlPts1.get(i), pts.get(i), g);
		}

		if(shape.isClosed()) {
			paintLine(line, pts.get(size-1), ctrlPts2.get(size-1), g);
			paintLine(line, ctrlPts2.get(size-1), ctrlPts2.get(0), g);
			paintLine(line, ctrlPts2.get(0), pts.get(0), g);
		}

		paintLine(line, pts.get(0), ctrlPts1.get(0), g);
		paintLine(line, ctrlPts1.get(0), ctrlPts1.get(1), g);
		paintLine(line, ctrlPts1.get(1), pts.get(1), g);
	}



//
//	@Override
//	public void paint(Graphics2D g)
//	{
//		super.paint(g);
//
//		if(isSelected) {
//			AbstractHandler handler1, handler2;
//			IPoint p 			= beginRotation(g);
//			Line2D.Double line 	= new Line2D.Double();
//
//			for(int i=0, size = firstCtrlHandlers.size(); i<size; i++) {
//				handler1 = firstCtrlHandlers.elementAt(i);
//				handler1.paint(g);
//				handler2 = secondCtrlHandlers.elementAt(i);
//				handler2.paint(g);
//				g.setColor(Color.GRAY);
//				g.setStroke(STROKE_CTRL_LINES);
//				line.setLine(handler1.getCentre().toPoint2D(), handler2.getCentre().toPoint2D());
//				g.draw(line);
//			}
//
//			if(p!=null)
//				endRotation(g, p);
//		}
//	}



//	@Override
//	public void update()
//	{
//		super.update();
//
//		IBezierCurve curve 		= (IBezierCurve)shape;
//		int size 				= shape.getNbPoints(), i;
//		Vector<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
//		Vector<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
//		int diff 				= size-firstCtrlHandlers.size();
//
//		if(diff<0)// There is too many handlers.
//			for(i=diff; i>0; i++) {
//				firstCtrlHandlers.remove(0);
//				secondCtrlHandlers.remove(0);
//			}
//		else
//			if(diff>0) // There is not enough handlers.
//				for(i=0; i<diff; i++) {
//					firstCtrlHandlers.add(new CtrlPointHandler(new LPoint(), this));
//					secondCtrlHandlers.add(new CtrlPointHandler(new LPoint(), this));
//				}
//
//		for(i=0; i<size; i++) {
//			firstCtrlHandlers.elementAt(i).setCentre(ctrlPts1.elementAt(i));
//			secondCtrlHandlers.elementAt(i).setCentre(ctrlPts2.elementAt(i));
//		}
//	}


//
//	/**
//	 * Moves the given control point to a new position.
//	 * @param point The control point to move.
//	 * @param newPosition The new position.
//	 * @since 3.0
//	 */
//	public boolean moveCtrlPoint(IPoint point, IPoint newPosition)
//	{
//		if(!GLibUtilities.INSTANCE.isValidPoint(newPosition))
//			return false;
//
//		IBezierCurve curve = (IBezierCurve)shape;
//		boolean again = true;
//		Vector<IPoint> pts 		= shape.getPoints();
//		Vector<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
//		Vector<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
//		int i = 0, size = ctrlPts1.size();
//
//		while(i<size && again)
//			if(ctrlPts1.elementAt(i)==point)
//				again = false;
//			else
//				i++;
//
//		if(again) {
//			i=0;
//
//			while(i<size && again)
//				if(ctrlPts2.elementAt(i)==point)
//					again = false;
//				else
//					i++;
//
//			if(again)
//				return false;
//
//			point.setPoint(newPosition);
//			ctrlPts1.elementAt(i).setPoint(point.centralSymmetry(pts.elementAt(i)));
//			update();
//		}
//		else {
//			point.setPoint(newPosition);
//			ctrlPts2.elementAt(i).setPoint(point.centralSymmetry(pts.elementAt(i)));
//			update();
//		}
//
//		return true;
//	}


	@Override
	protected void setPath(final boolean close) {
		final List<IPoint> pts 		= shape.getPoints();
		final List<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
		final List<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
		IPoint ctrl1;

		path.reset();
		path.moveTo(pts.get(0).getX(), pts.get(0).getY());
		path.curveTo(ctrlPts1.get(0).getX(), ctrlPts1.get(0).getY(),
				   ctrlPts1.get(1).getX(), ctrlPts1.get(1).getY(),
				   pts.get(1).getX(), pts.get(1).getY());

		for(int i=2, size=pts.size(); i<size; i++) {
			ctrl1 = ctrlPts2.get(i-1);
			path.curveTo(ctrl1.getX(), ctrl1.getY(),
					   ctrlPts1.get(i).getX(), ctrlPts1.get(i).getY(),
					   pts.get(i).getX(), pts.get(i).getY());
		}

		if(shape.isClosed()) {
			IPoint ctrl1b = ctrlPts1.get(0).centralSymmetry(pts.get(0));
			IPoint ctrl2b = ctrlPts1.get(ctrlPts1.size()-1).centralSymmetry(pts.get(pts.size()-1));
			path.curveTo(ctrl2b.getX(), ctrl2b.getY(), ctrl1b.getX(), ctrl1b.getY(), pts.get(0).getX(), pts.get(0).getY());
			path.closePath();
		}
	}
}
