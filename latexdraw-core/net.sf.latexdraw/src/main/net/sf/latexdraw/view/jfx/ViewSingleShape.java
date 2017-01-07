/*
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 */
package net.sf.latexdraw.view.jfx;

import java.awt.geom.Point2D;
import javafx.beans.binding.Bindings;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The base class of a JFX single shape view.
 * @param <S> The type of the model.
 * @param <T> The type of the JFX shape used to draw the view.
 */
public abstract class ViewSingleShape<S extends ISingleShape, T extends Shape> extends ViewShape<S> {
	protected final @NonNull T border;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewSingleShape(final @NonNull S sh) {
		super(sh);

		border = createJFXShape();
		getChildren().add(border);

		border.setStrokeLineJoin(StrokeLineJoin.MITER);

		if(model.isThicknessable()) {
			border.strokeWidthProperty().bind(model.thicknessProperty());
		}

		if(model.isLineStylable()) {
			model.linestyleProperty().addListener((obj, oldVal, newVal) -> updateLineStyle());
			model.dashSepBlackProperty().addListener((obj, oldVal, newVal) -> updateLineStyle());
			model.dashSepWhiteProperty().addListener((obj, oldVal, newVal) -> updateLineStyle());
			updateLineStyle();
		}

		if(model.isFillable()) {
			model.fillingProperty().addListener((obs, oldVal, newVal) -> border.setFill(getFillingPaint(newVal)));
			border.setFill(getFillingPaint(model.getFillingStyle()));
		}

		border.strokeProperty().bind(Bindings.createObjectBinding(() -> model.getLineColour().toJFX(), model.lineColourProperty()));

		bindBorderMovable();
	}

	protected abstract @NonNull T createJFXShape();

	private Paint getFillingPaint(final FillingStyle style) {
		switch(style) {
			case NONE:
				if(model.hasShadow() && model.shadowFillsShape()) return model.getFillingCol().toJFX();
				return null;
			case PLAIN: return model.getFillingCol().toJFX();
			case GRAD: return computeGradient();
			case CLINES_PLAIN:
			case HLINES_PLAIN:
			case VLINES_PLAIN:
			case CLINES:
			case VLINES:
			case HLINES: return null;
			default: return null;
		}
	}

	private LinearGradient computeGradient() {
		final IPoint tl = model.getTopLeftPoint();
		final IPoint br = model.getBottomRightPoint();
		IPoint pt1 = ShapeFactory.INST.createPoint((tl.getX() + br.getX()) / 2d, tl.getY());
		IPoint pt2 = ShapeFactory.INST.createPoint((tl.getX() + br.getX()) / 2d, br.getY());
		double angle = model.getGradAngle() % (2d * Math.PI);
		double gradMidPt = model.getGradMidPt();

		if(angle < 0d) angle = 2d * Math.PI + angle;

		if(angle >= Math.PI) {
			gradMidPt = 1d - gradMidPt;
			angle -= Math.PI;
		}

		if(MathUtils.INST.equalsDouble(angle, 0d)) {
			if(gradMidPt < 0.5) pt1.setY(pt2.getY() - Point2D.distance(pt2.getX(), pt2.getY(), (tl.getX() + br.getX()) / 2d, br.getY()));

			pt2.setY(tl.getY() + (br.getY() - tl.getY()) * gradMidPt);
		}else {
			if(MathUtils.INST.equalsDouble(angle % (Math.PI / 2d), 0d)) {
				pt1 = ShapeFactory.INST.createPoint(tl.getX(), (tl.getY() + br.getY()) / 2d);
				pt2 = ShapeFactory.INST.createPoint(br.getX(), (tl.getY() + br.getY()) / 2d);

				if(gradMidPt < 0.5)
					pt1.setX(pt2.getX() - Point2D.distance(pt2.getX(), pt2.getY(), br.getX(), (tl.getY() + br.getY()) / 2d));

				pt2.setX(tl.getX() + (br.getX() - tl.getX()) * gradMidPt);
			}else {
				final IPoint cg = model.getGravityCentre();
				final ILine l2;
				final ILine l;

				pt1 = pt1.rotatePoint(cg, -angle);
				pt2 = pt2.rotatePoint(cg, -angle);
				l = ShapeFactory.INST.createLine(pt1, pt2);

				if(angle >= 0d && angle < Math.PI / 2d) l2 = l.getPerpendicularLine(tl);
				else l2 = l.getPerpendicularLine(ShapeFactory.INST.createPoint(tl.getX(), br.getY()));

				pt1 = l.getIntersection(l2);
				final double distance = Point2D.distance(cg.getX(), cg.getY(), pt1.getX(), pt1.getY());
				l.setX1(pt1.getX());
				l.setY1(pt1.getY());
				final IPoint[] pts = l.findPoints(pt1, 2d * distance * gradMidPt);
				pt2 = pts[0];

				if(gradMidPt < 0.5) pt1 = pt1.rotatePoint(model.getGravityCentre(), Math.PI);
			}
		}

		return new LinearGradient(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY(), false, CycleMethod.NO_CYCLE, new Stop(0, model.getGradColStart().toJFX()), new Stop(1, model.getGradColEnd().toJFX()));
	}

	private void bindBorderMovable() {
		if(model.isBordersMovable()) {
			border.strokeTypeProperty().bind(Bindings.createObjectBinding(() -> {
				switch(model.getBordersPosition()) {
					case INTO: return StrokeType.INSIDE;
					case MID: return StrokeType.CENTERED;
					case OUT: return StrokeType.OUTSIDE;
					default: return StrokeType.INSIDE;
				}
			}, model.borderPosProperty()));
		}
	}

	private void updateLineStyle() {
		final LineStyle newVal = model.getLineStyle();

		switch(newVal) {
			case DASHED:
				border.setStrokeLineCap(StrokeLineCap.BUTT);
				border.getStrokeDashArray().clear();
				border.getStrokeDashArray().addAll(model.getDashSepBlack(), model.getDashSepWhite());
				break;
			case DOTTED:// FIXME problem when dotted line + INTO/OUT border position.
				final double dotSep = model.getDotSep() + (model.hasDbleBord() ? model.getThickness() * 2d + model.getDbleBordSep() : model.getThickness());
				border.setStrokeLineCap(StrokeLineCap.ROUND);
				border.getStrokeDashArray().clear();
				border.getStrokeDashArray().addAll(0d, dotSep);
				break;
			case SOLID:
				border.setStrokeLineCap(StrokeLineCap.SQUARE);
				border.getStrokeDashArray().clear();
				break;
		}
	}

	public T getBorder() {
		return border;
	}
}
