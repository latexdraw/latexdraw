/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX shape view for Bézier curves.
 * @author Arnaud Blouin
 */
public class ViewBezierCurve extends ViewPathShape<IBezierCurve> {
	final MoveTo moveTo;
	final List<CubicCurveTo> curvesTo;
	final Group showPoint;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewBezierCurve(final @NonNull IBezierCurve sh) {
		super(sh);
		ObservableList<PathElement> elts = border.getElements();
		showPoint = new Group();
		moveTo =  ViewFactory.INSTANCE.createMoveTo(0d, 0d);
		moveTo.xProperty().bind(sh.getPtAt(0).xProperty());
		moveTo.yProperty().bind(sh.getPtAt(0).yProperty());
		elts.add(moveTo);

		curvesTo = new ArrayList<>();
		addCurveTo(sh.getPtAt(1), model.getFirstCtrlPtAt(1), model.getFirstCtrlPtAt(1));

		IntStream.range(2, sh.getNbPoints()).forEach(index ->
			addCurveTo(sh.getPtAt(index), model.getSecondCtrlPtAt(index-1), model.getFirstCtrlPtAt(index)));

		model.getPoints().addListener((ListChangeListener.Change<? extends IPoint> c) -> {
			while(c.next()) {
				if(c.wasAdded()) {
					c.getAddedSubList().forEach(pt -> {
						addCurveTo(pt, model.getSecondCtrlPtAt(model.getNbPoints()-2), model.getFirstCtrlPtAt(model.getNbPoints()-1));
					});
				}
			}
		});
	}

	private void addCurveTo(final IPoint pt, final IPoint ctrl1, final IPoint ctrl2) {
		CubicCurveTo curveto = ViewFactory.INSTANCE.createCubicCurveTo(0d, 0d, 0d, 0d, 0d, 0d);
		curveto.xProperty().bind(pt.xProperty());
		curveto.yProperty().bind(pt.yProperty());
		curveto.controlX1Property().bind(ctrl1.xProperty());
		curveto.controlY1Property().bind(ctrl1.yProperty());
		curveto.controlX2Property().bind(ctrl2.xProperty());
		curveto.controlY2Property().bind(ctrl2.yProperty());
		curvesTo.add(curveto);
		border.getElements().add(curveto);
	}


	@Override
	public void flush() {
		moveTo.xProperty().unbind();
		moveTo.yProperty().unbind();
		curvesTo.forEach(to -> {
			to.xProperty().unbind();
			to.yProperty().unbind();
			to.controlX1Property().unbind();
			to.controlX2Property().unbind();
			to.controlY1Property().unbind();
			to.controlY2Property().unbind();
		});
		curvesTo.clear();
		super.flush();
	}

	//	@Override
	//	protected void setPath(final boolean close) {
	//		if(shape.getNbPoints()<2) return ;
	//
	//		final List<IPoint> pts 		= shape.getPoints();
	//		final List<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
	//		final List<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
	//		IPoint ctrl1;
	//		final int size;
	//		final double[] coords = updatePoint4Arrows(pts.get(0).getX(), pts.get(0).getY(), shape.getArrowAt(0));
	//		final double[] coords2;
	//		if(pts.size()==2)
	//			// In this case the first curve contains the first and last points that must be modified.
	//			coords2 = updatePoint4Arrows(pts.get(1).getX(),pts.get(1).getY(), shape.getArrowAt(-1));
	//		else coords2 = new double[]{pts.get(1).getX(),pts.get(1).getY()};
	//
	//		path.reset();
	//		path.moveTo(coords[0], coords[1]);
	//		path.curveTo(ctrlPts1.get(0).getX(), ctrlPts1.get(0).getY(),
	//			ctrlPts1.get(1).getX(), ctrlPts1.get(1).getY(),
	//			coords2[0], coords2[1]);
	//
	//		if(shape.isClosed())
	//			size = pts.size();
	//		else size = pts.size()-1;
	//
	//		for(int i=2; i<size; i++) {
	//			ctrl1 = ctrlPts2.get(i-1);
	//			path.curveTo(ctrl1.getX(), ctrl1.getY(),
	//				ctrlPts1.get(i).getX(), ctrlPts1.get(i).getY(),
	//				pts.get(i).getX(), pts.get(i).getY());
	//		}
	//
	//		if(shape.isClosed()) {
	//			final IPoint ctrl1b = ctrlPts1.get(0).centralSymmetry(pts.get(0));
	//			final IPoint ctrl2b = ctrlPts1.get(ctrlPts1.size()-1).centralSymmetry(pts.get(pts.size()-1));
	//			path.curveTo(ctrl2b.getX(), ctrl2b.getY(), ctrl1b.getX(), ctrl1b.getY(), pts.get(0).getX(), pts.get(0).getY());
	//			path.closePath();
	//		}else {
	//			if(pts.size()>2) {
	//				ctrl1 = ctrlPts2.get(size-1);
	//				path.curveTo(ctrl1.getX(), ctrl1.getY(), ctrlPts1.get(size).getX(), ctrlPts1.get(size).getY(), pts.get(size).getX(),  pts.get(size).getY());
	//			}
	//		}
	//	}

//	@Override
//	public void paintShowPointsDots(final Graphics2D g) {
//		final boolean isClosed		= shape.isClosed();
//		final IArrow arr1			= shape.getArrowAt(0);
//		final boolean arrow1Drawable 		= arr1.hasStyle() && shape.getNbPoints()>1;
//		final boolean arrow2Drawable 		= shape.getArrowAt(-1).hasStyle() && shape.getNbPoints()>1 && !isClosed;
//		final int size 				= shape.getNbPoints();
//		final List<IPoint> pts 		= shape.getPoints();
//		final List<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
//		final List<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
//		final double width 			= arr1.getDotSizeDim() + arr1.getDotSizeNum()*shape.getThickness();
//		final Ellipse2D.Double d 			= new Ellipse2D.Double(0, 0, width, width);
//		int i;
//
//		g.setColor(shape.getLineColour());
//
//		if(!arrow1Drawable || isClosed)
//			fillCircle(d, pts.get(0), width, g);
//
//		if(!arrow2Drawable || isClosed)
//			fillCircle(d, pts.get(size-1), width, g);
//
//		for(i=1; i<size-1; i++) {
//			fillCircle(d, pts.get(i), width, g);
//			fillCircle(d, ctrlPts2.get(i), width, g);
//		}
//
//		for(i=0; i<size; i++)
//			fillCircle(d, ctrlPts1.get(i), width, g);
//
//		if(shape.isClosed()) {
//			fillCircle(d, ctrlPts2.get(ctrlPts2.size()-1), width, g);
//			fillCircle(d, ctrlPts2.get(0), width, g);
//		}
//	}
//
//
//
//	private void fillCircle(final Ellipse2D ell, final IPoint pt, final double width, final Graphics2D g) {
//		ell.setFrame(pt.getX()-width/2., pt.getY()-width/2., width, width);
//		g.fill(ell);
//	}
//
//
//
//	private void paintLine(final Line2D line, final IPoint pt1, final IPoint pt2, final Graphics2D g) {
//		line.setLine(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
//		g.draw(line);
//	}
//
//
//	@Override
//	public void paintShowPointsLines(final Graphics2D g) {
//		final int size 				= shape.getNbPoints();
//		final List<IPoint> pts 		= shape.getPoints();
//		final List<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
//		final List<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
//		final float thick 			= (float)(shape.hasDbleBord()? shape.getDbleBordSep()+shape.getThickness()*2. : shape.getThickness());
//		final Line2D.Double line 			= new Line2D.Double();
//		int i;
//
//		g.setStroke(new BasicStroke(thick/2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
//			1.f, new float[]{(float)shape.getDashSepBlack(), (float)shape.getDashSepWhite()}, 0));
//		g.setColor(shape.getLineColour());
//
//		for(i=3; i<size; i+=2) {
//			paintLine(line, pts.get(i-1), ctrlPts2.get(i-1), g);
//			paintLine(line, ctrlPts2.get(i-1), ctrlPts1.get(i), g);
//			paintLine(line, ctrlPts1.get(i), pts.get(i), g);
//		}
//
//		for(i=2; i<size; i+=2) {
//			paintLine(line, pts.get(i-1), ctrlPts2.get(i-1), g);
//			paintLine(line, ctrlPts2.get(i-1), ctrlPts1.get(i), g);
//			paintLine(line, ctrlPts1.get(i), pts.get(i), g);
//		}
//
//		if(shape.isClosed()) {
//			paintLine(line, pts.get(size-1), ctrlPts2.get(size-1), g);
//			paintLine(line, ctrlPts2.get(size-1), ctrlPts2.get(0), g);
//			paintLine(line, ctrlPts2.get(0), pts.get(0), g);
//		}
//
//		paintLine(line, pts.get(0), ctrlPts1.get(0), g);
//		paintLine(line, ctrlPts1.get(0), ctrlPts1.get(1), g);
//		paintLine(line, ctrlPts1.get(1), pts.get(1), g);
//	}
//
//
//	protected static double[] updatePoint4Arrows(final double x, final double y, final IArrow arr) {
//		final double[] coords = {x, y};
//
//		if(arr.getArrowStyle().isReducingShape()) {
//			final ILine line = arr.getArrowLine();
//
//			if(line!=null) {
//				final IPoint[] ps = line.findPoints(line.getPoint1(), arr.getArrowShapeLength()/2.);
//				if(ps!=null) {
//					if(line.isInSegment(ps[0])) {
//						coords[0] = ps[0].getX();
//						coords[1] = ps[0].getY();
//					}else {
//						coords[0] = ps[1].getX();
//						coords[1] = ps[1].getY();
//					}
//				}
//			}
//		}
//		return coords;
//	}
}
