package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.glib.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewPlot;

/**
 * Defines a view for ps plot figures.<br>
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
 * 2014-11-01<br>
 * @author Arnaud BLOUIN
 * @since 3.3
 */
public class LPlotView extends LShapeView<IPlot> implements IViewPlot {
	protected LPolylineView lineView;
	protected LPolygonView polygonView;
	protected LBezierCurveView curveView;
	protected List<LDotView> dotsView;

	/**
	 * Creates a view of the given model and initialises the Java2D view.
	 * @param model The shape model.
	 * @throws IllegalArgumentException If <code>model</code> is null.
	 */
	protected LPlotView(final IPlot model) {
		super(model);
		update();
	}


	@Override
	public void paint(final Graphics2D g, final Rectangle clip) {
		switch(shape.getPlotStyle()) {
			case LINE: lineView.paint(g, clip); break;
			case CURVE: curveView.paint(g, clip); break;
			case ECURVE: curveView.paint(g, clip); break;
			case CCURVE: curveView.paint(g, clip); break;
			case DOTS: for(LDotView v:dotsView) v.paint(g, clip); break;
			case POLYGON: polygonView.paint(g, clip); break;
		}
	}

	@Override
	public void updatePath() {
		final double minX = shape.getPlotMinX();
		final double maxX = shape.getPlotMaxX();
		final double step = shape.getPlottingStep();
		final double posX = shape.getPosition().getX();
		final double posY = shape.getPosition().getY();

		switch(shape.getPlotStyle()) {
			case LINE: updateLine(posX, posY, minX, maxX, step); break;
			case CURVE: updateCurve(posX, posY, minX, maxX, step); break;
			case ECURVE: updateCurve(posX, posY, minX+step, maxX-step, step); break;
			case CCURVE: updateCurve(posX, posY, minX, maxX, step); break;
			case DOTS: updatePoints(posX, posY, minX, maxX, step); break;
			case POLYGON: updatePolygon(posX, posY, minX, maxX, step); break;
		}
	}


	private void fillPoints(final IModifiablePointsShape sh, final double posX, final double posY, 
							final double minX, final double maxX, final double step) {
		final double xs = shape.getXScale();
		final double ys = shape.getYScale();

		if(shape.isPolar()) {
			for(double x=minX; x<=maxX; x+=step) {
	  			final double radius = shape.getY(x);
	  			final double angle = Math.toRadians(x);
	  			final double x1 = radius*Math.cos(angle);
	  			final double y1 = -radius*Math.sin(angle);
	  			sh.addPoint(ShapeFactory.createPoint(x1*IShape.PPC*xs+posX, y1*IShape.PPC*xs+posY));
	  		}
		}else
			for(double x=minX; x<=maxX; x+=step)
  				sh.addPoint(ShapeFactory.createPoint(x*IShape.PPC*xs+posX, -shape.getY(x)*IShape.PPC*ys+posY));
	}


	private void updatePoints(final double posX, final double posY, final double minX, final double maxX, final double step) {
		final IPolyline pl = ShapeFactory.createPolyline();
		fillPoints(pl, posX, posY, minX, maxX, step);
		
		if(dotsView==null) dotsView = new ArrayList<>();
		else {
			for(LDotView v:dotsView) v.flush();
			dotsView.clear();
		}
		
		for(IPoint pt : pl.getPoints()) {
			IDot dot = ShapeFactory.createDot(pt);
			dot.copy(shape);
			LDotView v = new LDotView(dot);
			dotsView.add(v);
			v.update();
		}
	}

	
	private void updatePolygon(final double posX, final double posY, final double minX, final double maxX, final double step) {
		final IPolygon pg = ShapeFactory.createPolygon();
		pg.copy(shape);
		if(polygonView!=null) polygonView.flush();
		polygonView = new LPolygonView(pg);
		fillPoints(pg, posX, posY, minX, maxX, step);
		polygonView.update();
	}
	
	
	private void updateLine(final double posX, final double posY, final double minX, final double maxX, final double step) {
		final IPolyline pl = ShapeFactory.createPolyline();
		if(lineView!=null) lineView.flush();
		fillPoints(pl, posX, posY, minX, maxX, step);
		pl.copy(shape);
		lineView = new LPolylineView(pl);
		lineView.update();
	}


	private void updateCurve(final double posX, final double posY, final double minX, final double maxX, final double step) {
		// The algorithm follows this definition:
		//https://stackoverflow.com/questions/15864441/how-to-make-a-line-curve-through-points
		final double scale = 0.33;
		final IBezierCurve bc = ShapeFactory.createBezierCurve();
		if(curveView!=null) curveView.flush();
		curveView = new LBezierCurveView(bc);
		fillPoints(bc, posX, posY, minX, maxX, step);
		if(shape.getPlotStyle()==IPlotProp.PlotStyle.CCURVE)
			bc.setIsClosed(true);
		else bc.setIsClosed(false);
		bc.copy(shape);
		int i=0;
		final int last = bc.getPoints().size()-1;

		for(IPoint pt : bc.getPoints()) {
			final IPoint p1 = pt;
			if(i==0) {
				final IPoint p2 = bc.getPtAt(i+1);
				final IPoint tangent = p2.substract(p1);
				final IPoint q1 =  p1.add(tangent.zoom(scale));
				bc.setXFirstCtrlPt(q1.getX(), i);
				bc.setYFirstCtrlPt(q1.getY(), i);
			}else if(i==last) {
				final IPoint p0 = bc.getPtAt(i-1);
				final IPoint tangent = p1.substract(p0);
				final IPoint q0 =  p1.substract(tangent.zoom(scale));
				bc.setXFirstCtrlPt(q0.getX(), i);
				bc.setYFirstCtrlPt(q0.getY(), i);
			}else {
				final IPoint p0 = bc.getPtAt(i-1);
				final IPoint p2 = bc.getPtAt(i+1);
				final IPoint tangent = p2.substract(p0).normalise();
				final IPoint q0 = p1.substract(tangent.zoom(scale*p1.substract(p0).magnitude()));
//				val q1 = p1.substract(tangent.zoom(scale*p2.substract(p1).magnitude))
				bc.setXFirstCtrlPt(q0.getX(), i);
				bc.setYFirstCtrlPt(q0.getY(), i);
//				shape.setXSecondCtrlPt(q1.getX, i)
//				shape.setYSecondCtrlPt(q1.getY, i)
			}
			i++;
		}
		bc.updateSecondControlPoints();
		curveView.update();
	}


	@Override
	public Rectangle2D getBorder() {
		switch(shape.getPlotStyle()) {
			case CCURVE: return curveView.getBorder();
			case CURVE: return curveView.getBorder();
			case ECURVE: return curveView.getBorder();
			case LINE: return lineView.getBorder();
			case POLYGON: return polygonView.getBorder();
			case DOTS:
				Rectangle2D rec = dotsView.get(0).getBorder();
				
				for(int i=1, size=dotsView.size(); i<size; i++)
					rec = rec.createUnion(dotsView.get(i).getBorder());
				
				return rec;
			default: return new Rectangle2D.Double();
		}
	}


	@Override
	public boolean intersects(final Rectangle2D rec) {
		switch(shape.getPlotStyle()) {
			case CCURVE: return curveView.intersects(rec);
			case CURVE: return curveView.intersects(rec);
			case DOTS:
				for(LDotView v:dotsView) if(v.intersects(rec)) return true;
				return false;
			case ECURVE: return curveView.intersects(rec);
			case LINE: return lineView.intersects(rec);
			case POLYGON: return polygonView.intersects(rec);
			default: return false;
		}
	}


	@Override
	public boolean contains(final double px, final double py) {
		switch(shape.getPlotStyle()) {
			case CCURVE: return curveView.contains(px, py);
			case CURVE: return curveView.contains(px, py);
			case DOTS: return getBorder().contains(px, py);
			case ECURVE: return curveView.contains(px, py);
			case LINE: return lineView.contains(px, py);
			case POLYGON: return polygonView.contains(px, py);
			default: return false;
		}
	}


	@Override
	public void updateBorder() {
		switch(shape.getPlotStyle()){
			case CCURVE: curveView.updateBorder(); break;
			case CURVE: curveView.updateBorder(); break;
			case ECURVE: curveView.updateBorder(); break;
			case LINE: lineView.updateBorder(); break;
			case POLYGON: polygonView.updateBorder(); break;
			case DOTS: for(LDotView v:dotsView) v.updateBorder(); break;
		}
	}


	@Override
	public void flush() {
		super.flush();
		if(lineView!=null)  lineView.flush();
		if(curveView!=null) curveView.flush();
		if(dotsView!=null)  {
			for(LDotView v:dotsView) v.flush();
			dotsView.clear();
		}
		if(polygonView!=null) polygonView.flush();
	}

	@Override
	public void updateDblePathInside(){/**/}
	@Override
	public void updateDblePathMiddle(){/**/}
	@Override
	public void updateDblePathOutside(){/**/}
	@Override
	public void updateGeneralPathInside(){/**/}
	@Override
	public void updateGeneralPathMiddle(){/**/}
	@Override
	public void updateGeneralPathOutside(){/**/}
}
