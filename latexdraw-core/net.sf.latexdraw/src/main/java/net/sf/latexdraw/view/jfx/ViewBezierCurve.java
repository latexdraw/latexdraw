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
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * The JFX shape view for Bézier curves.
 * @author Arnaud Blouin
 */
public class ViewBezierCurve extends ViewPathShape<IBezierCurve> {
	protected final ViewArrowableTraitPath<IBezierCurve> viewArrows = new ViewArrowableTraitPath<>(this);

	final MoveTo moveTo;
	final List<CubicCurveTo> curvesTo;
	final Group showPoint;
	final CubicCurveTo closingCurve;
	final ChangeListener<Boolean> openUpdate;

	/**
	 * Creates the view.
	 * @param shape The model.
	 */
	ViewBezierCurve(final IBezierCurve shape) {
		super(shape);
		showPoint = new Group();
		moveTo = ViewFactory.INSTANCE.createMoveTo(0d, 0d);
		moveTo.xProperty().bind(shape.getPtAt(0).xProperty());
		moveTo.yProperty().bind(shape.getPtAt(0).yProperty());
		border.getElements().add(moveTo);

		curvesTo = new ArrayList<>();
		addCurveTo(shape.getPtAt(1), model.getFirstCtrlPtAt(0), model.getFirstCtrlPtAt(1));

		IntStream.range(2, shape.getNbPoints()).forEach(index -> addCurveTo(shape.getPtAt(index), model.getSecondCtrlPtAt(index - 1),
			model.getFirstCtrlPtAt(index)));

		closingCurve = addCurveTo(shape.getPtAt(0), model.getSecondCtrlPtAt(-1), model.getSecondCtrlPtAt(0));

		openUpdate = (observable, oldValue, newValue) -> updateOpen(newValue);
		shape.openedProperty().addListener(openUpdate);
		updateOpen(model.isOpened());
		getChildren().add(viewArrows);
		viewArrows.updateAllArrows();
	}

	private void updateOpen(final boolean open) {
		if(open) {
			border.getElements().remove(closingCurve);
		}else {
			if(!border.getElements().contains(closingCurve)) {
				border.getElements().add(closingCurve);
			}
		}
	}

	private CubicCurveTo addCurveTo(final IPoint pt, final IPoint ctrl1, final IPoint ctrl2) {
		final CubicCurveTo curveto = ViewFactory.INSTANCE.createCubicCurveTo(0d, 0d, 0d, 0d, 0d, 0d);
		curveto.xProperty().bind(pt.xProperty());
		curveto.yProperty().bind(pt.yProperty());
		curveto.controlX1Property().bind(ctrl1.xProperty());
		curveto.controlY1Property().bind(ctrl1.yProperty());
		curveto.controlX2Property().bind(ctrl2.xProperty());
		curveto.controlY2Property().bind(ctrl2.yProperty());
		curvesTo.add(curveto);
		border.getElements().add(curveto);
		return curveto;
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
		model.openedProperty().removeListener(openUpdate);
		super.flush();
	}

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
}
