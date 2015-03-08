package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.views.PlotViewHelper;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewPlot;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a view for ps plot figures.<br>
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
 * 2014-11-01<br>
 * @author Arnaud BLOUIN
 * @since 3.3
 */
public class LPlotView extends LShapeView<IPlot> implements IViewPlot {
	private LPolylineView lineView;
	private LPolygonView polygonView;
	private LBezierCurveView curveView;
	private List<LDotView> dotsView;

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
			case DOTS:
				final IPoint vectorTrans = beginRotation(g);
				for(LDotView v:dotsView) v.paint(g, clip); 
				if(vectorTrans!=null)
					endRotation(g, vectorTrans);
				break;
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
	
	private void updatePoints(final double posX, final double posY, final double minX, final double maxX, final double step) {
		if(dotsView==null) dotsView = new ArrayList<>();
		else {
			for(LDotView v:dotsView) v.flush();
			dotsView.clear();
		}
		
		final List<IDot> dots = PlotViewHelper.INSTANCE.updatePoints(shape, posX, posY, minX, maxX, step);
		
		for(IDot dot : dots) {
			LDotView v = new LDotView(dot);
			dotsView.add(v);
			v.update();
		}
	}
	
	
	private void updatePolygon(final double posX, final double posY, final double minX, final double maxX, final double step) {
		polygonView = new LPolygonView(PlotViewHelper.INSTANCE.updatePolygon(shape, posX, posY, minX, maxX, step));
		polygonView.update();
	}
	
	
	private void updateLine(final double posX, final double posY, final double minX, final double maxX, final double step) {
		lineView = new LPolylineView(PlotViewHelper.INSTANCE.updateLine(shape, posX, posY, minX, maxX, step));
		lineView.update();
	}
	
	
	private void updateCurve(final double posX, final double posY, final double minX, final double maxX, final double step) {
		curveView = new LBezierCurveView(PlotViewHelper.INSTANCE.updateCurve(shape, posX, posY, minX, maxX, step));
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
				final double rota = shape.getRotationAngle();
				Rectangle2D rec = dotsView.get(0).getBorder();
				
				if(LNumber.equalsDouble(rota, 0.)) {
					for(int i=1, size=dotsView.size(); i<size; i++)
						rec = rec.createUnion(dotsView.get(i).getBorder());
				}else {
					final IPoint tl = shape.getTopLeftPoint();
					final IPoint br = shape.getBottomRightPoint();
					rec = getRotatedShape2D(rota, rec, tl, br).getBounds2D();
					
					for(int i=1, size=dotsView.size(); i<size; i++)
						rec = rec.createUnion(getRotatedShape2D(rota, dotsView.get(i).getBorder(), tl, br).getBounds2D());
				}
				
				return rec;
		}
		return new Rectangle2D.Double();
	}


	@Override
	public boolean intersects(final Rectangle2D rec) {
		switch(shape.getPlotStyle()) {
			case CCURVE: return curveView.intersects(rec);
			case CURVE: return curveView.intersects(rec);
			case DOTS:
				final double rota = shape.getRotationAngle();
				if(LNumber.equalsDouble(rota, 0.)) {
					for(LDotView v:dotsView) if(v.intersects(rec)) return true;
				}
				else {
					final IPoint tl = shape.getTopLeftPoint();
					final IPoint br = shape.getBottomRightPoint();
					for(int i=0, size=dotsView.size(); i<size; i++)
						if(getRotatedShape2D(rota, dotsView.get(i).getBorder(), tl, br).intersects(rec))
							return true;
				}
				return false;
			case ECURVE: return curveView.intersects(rec);
			case LINE: return lineView.intersects(rec);
			case POLYGON: return polygonView.intersects(rec);
		}
		return false;
	}


	@Override
	public boolean contains(final double px, final double py) {
		switch(shape.getPlotStyle()) {
			case CCURVE: return curveView.contains(px, py);
			case CURVE: return curveView.contains(px, py);
			case DOTS: 
				final double rota = shape.getRotationAngle();
				if(LNumber.equalsDouble(rota, 0.)) {
					for(IViewShape v : dotsView) if(v.contains(px, py)) return true;
				}
				else {
					final IPoint tl = shape.getTopLeftPoint();
					final IPoint br = shape.getBottomRightPoint();
					for(int i=0, size=dotsView.size(); i<size; i++)
						if(getRotatedShape2D(rota, dotsView.get(i).getBorder(), tl, br).contains(px, py))
							return true;
				}
				return false;
			case ECURVE: return curveView.contains(px, py);
			case LINE: return lineView.contains(px, py);
			case POLYGON: return polygonView.contains(px, py);
		}
		return false;
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
