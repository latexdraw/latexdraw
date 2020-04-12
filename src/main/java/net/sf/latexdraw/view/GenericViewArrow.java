/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.Line;
import net.sf.latexdraw.model.api.shape.Point;

/**
 * A generic implementation trait to render arrows.
 * @author Arnaud Blouin
 */
public interface GenericViewArrow {
	Arrow getArrow();

	default void updatePathRightLeftSquaredBracket(final boolean isShadow) {
		final Arrow arrow = getArrow();
		final double halflineWidth = arrow.getShape().getFullThickness() / 2d;
		final double lgth = arrow.getBracketShapedArrowLength() + halflineWidth;
		final double width = arrow.getBarShapedArrowWidth();
		final double y0 = -width / 2d;
		final double y1 = width / 2d;

		createMoveTo(-lgth, y0 + halflineWidth);
		createLineTo(0d, y0 + halflineWidth);
		createLineTo(0d, y1 - halflineWidth);
		createLineTo(-lgth, y1 - halflineWidth);

		setPathFill(null);
		setPathStroke(createLineOrShadColBinding(isShadow));
		setPathStrokeWidth(createFullThickBinding());

		if(arrow.isInverted()) {
			setRotation180();
		}
	}

	default void updatePathBarIn(final Point pt1, final Point pt2, final boolean isShadow) {
		final Arrow arrow = getArrow();
		final double width = arrow.getBarShapedArrowWidth();
		final double lineWidth = arrow.getShape().getThickness();
		final double dec = isArrowInPositiveDirection(pt1, pt2) ? lineWidth / 2d : -lineWidth / 2d;
		createMoveTo(dec, -width / 2d);
		createLineTo(dec, width / 2d);
		setPathStrokeWidth(createFullThickBinding());
		setPathStroke(createLineOrShadColBinding(isShadow));
	}

	default DoubleBinding createFullThickBinding() {
		return Bindings.createDoubleBinding(() -> getArrow().getShape().getFullThickness(), getArrow().getShape().thicknessProperty(),
			getArrow().getShape().dbleBordSepProperty(), getArrow().getShape().dbleBordProperty());
	}

	default void updatePathBarEnd(final boolean isShadow) {
		final Arrow arrow = getArrow();
		final double width = arrow.getBarShapedArrowWidth();
		createMoveTo(0d, -width / 2d);
		createLineTo(0d, width / 2d);
		setPathStrokeWidth(createFullThickBinding());
		setPathStroke(createLineOrShadColBinding(isShadow));
	}

	default void updatePathArrow(final double x1, final double y1, final double x2, final double y2, final double x3, final double y3,
							final double x4, final double y4) {
		createMoveTo(x1, y1);
		createLineTo(x2, y2);
		createLineTo(x3, y3);
		createLineTo(x4, y4);
		createClosePath();
	}

	default void updatePathRightLeftArrow(final boolean isShadow) {
		final Arrow arrow = getArrow();
		final double width = arrow.getArrowShapedWidth();
		final double length = arrow.getArrowLength() * width;
		final double inset = arrow.getArrowInset() * length;
		final double x;

		if(arrow.isInverted()) {
			x = length;
		}else {
			x = 0d;
		}

		updatePathArrow(x, 0d, x - length, -width / 2d, x - length - inset, 0d, x - length, width / 2d);
		setPathFill(createLineOrShadColBinding(isShadow));
		setPathStroke(createLineOrShadColBinding(isShadow));

		if(arrow.isInverted()) {
			setRotation180();
		}
	}

	default void updatePathRoundLeftRightBracket(final boolean isShadow) {
		final Arrow arrow = getArrow();
		final double width = arrow.getBarShapedArrowWidth();
		final double widtharc = arrow.getRBracketNum() * width * 2d;
		final double xarc = -widtharc / 2d;
		final double angle = -50d;

		createArc(xarc, 0d, widtharc / 2d, width / 2d, angle, 100d, createLineOrShadColBinding(isShadow), arrow.getShape().thicknessProperty());

		if(arrow.isInverted()) {
			setRotation180();
		}
	}

	default void updatePathDoubleLeftRightArrow(final boolean isShadow) {
		final Arrow arrow = getArrow();
		final double width = arrow.getArrowShapedWidth();
		final double length = arrow.getArrowLength() * width;
		final double inset = arrow.getArrowInset() * length;
		final double x;

		if(arrow.isInverted()) {
			x = 2d * length;
		}else {
			x = 0d;
		}

		updatePathArrow(x, 0d, x - length, -width / 2d, x - length - inset, 0d, x - length, width / 2d);
		updatePathArrow(x - length, 0d, x - 2d * length, -width / 2d, x - 2d * length - inset, 0d, x - 2d * length, width / 2d);
		final double x2 = x - length - inset;
		final double x2bis = x - 2d * length - inset;

		createMoveTo(x2, 0d);
		createLineTo(x2bis, 0d);
		setPathFill(createLineOrShadColBinding(isShadow));
		setPathStroke(createLineOrShadColBinding(isShadow));

		if(arrow.isInverted()) {
			setRotation180();
		}
	}

	default void updatePathSquareEnd(final Point pt1, final Point pt2, final boolean isShadow) {
		createMoveTo(0d, 0d);
		createLineTo(pt1.getX() < pt2.getX() ? 1d : -1d, 0d);
		setPathStrokeWidth(createFullThickBinding());
		setPathStroke(createLineOrShadColBinding(isShadow));
	}

	default void updatePathRoundEnd(final boolean isShadow) {
		createCircle(0d, 0d, getArrow().getShape().getFullThickness() / 2d);
		setStrokeFillDiskCircle(isShadow);
	}


	default void updatePathRoundIn(final Point pt1, final Point pt2, final boolean isShadow) {
		final Arrow arrow = getArrow();
		final double lineWidth = arrow.getShape().getFullThickness() / 2d;
		createCircle(isArrowInPositiveDirection(pt1, pt2) ? lineWidth : -lineWidth, 0d, lineWidth);
		setStrokeFillDiskCircle(isShadow);
	}

	default void updatePathDiskCircleEnd(final boolean isShadow) {
		final Arrow arrow = getArrow();
		final double lineWidth = arrow.getShape().getFullThickness();
		final double arrowRadius = arrow.getRoundShapedArrowRadius();
		createCircle(0d, 0d, arrowRadius - lineWidth / 2d);
		setCircleStrokeWidth(lineWidth);
		setStrokeFillDiskCircle(isShadow);
	}


	default void updatePathDiskCircleIn(final Point pt1, final Point pt2, final boolean isShadow) {
		final Arrow arrow = getArrow();
		final double arrowRadius = arrow.getRoundShapedArrowRadius();
		final double lineWidth = arrow.getShape().getFullThickness();
		createCircle(isArrowInPositiveDirection(pt1, pt2) ? arrowRadius : -arrowRadius, 0d, arrowRadius - lineWidth / 2d);
		setCircleStrokeWidth(lineWidth);
		setStrokeFillDiskCircle(isShadow);
	}

	default boolean isArrowInPositiveDirection(final Point pt1, final Point pt2) {
		return pt1.getX() < pt2.getX() || (MathUtils.INST.equalsDouble(pt1.getX(), pt2.getX()) && pt1.getY() < pt2.getY());
	}

	default ObjectBinding<Color> createLineOrShadColBinding(final boolean isShadow) {
		if(isShadow) {
			return Bindings.createObjectBinding(() -> getArrow().getShape().getShadowCol().toJFX(), getArrow().getShape().shadowColProperty());
		}
		return Bindings.createObjectBinding(() -> getArrow().getShape().getLineColour().toJFX(), getArrow().getShape().lineColourProperty());
	}

	default ObjectBinding<Color> createFillOrShadColBinding(final boolean isShadow) {
		if(isShadow) {
			return Bindings.createObjectBinding(() -> getArrow().getShape().getShadowCol().toJFX(), getArrow().getShape().shadowColProperty());
		}
		return Bindings.createObjectBinding(() -> getArrow().getShape().getFillingCol().toJFX(), getArrow().getShape().fillingColProperty());
	}

	default void setStrokeFillDiskCircle(final boolean isShadow) {
		final Arrow arrow = getArrow();

		if(arrow.getArrowStyle() == ArrowStyle.ROUND_END || arrow.getArrowStyle() == ArrowStyle.ROUND_IN) {
			setCircleStroke(null);
		}else {
			setCircleStrokeBinding(createLineOrShadColBinding(isShadow));
		}

		if(arrow.getArrowStyle() == ArrowStyle.CIRCLE_IN || arrow.getArrowStyle() == ArrowStyle.CIRCLE_END) {
			if(arrow.getShape().isFillable()) {
				setCircleFillBinding(createFillOrShadColBinding(isShadow));
			}else {
				setCircleFill(Color.WHITE);
			}
		}else {
			setCircleFillBinding(createLineOrShadColBinding(isShadow));
		}
	}

	default void updatePath(final boolean isShadow) {
		final Arrow arrow = getArrow();
		final Line arrowLine = arrow.getArrowLine();

		if(arrowLine == null || arrow.getArrowStyle() == ArrowStyle.NONE || !arrow.hasStyle()) {
			return;
		}

		final Point pt1 = arrowLine.getPoint1();
		final Point pt2 = arrowLine.getPoint2();

		setTranslation(pt1.getX(), pt1.getY());

		switch(arrow.getArrowStyle()) {
			case BAR_END -> updatePathBarEnd(isShadow);
			case BAR_IN -> updatePathBarIn(pt1, pt2, isShadow);
			case CIRCLE_END, DISK_END -> updatePathDiskCircleEnd(isShadow);
			case CIRCLE_IN, DISK_IN -> updatePathDiskCircleIn(pt1, pt2, isShadow);
			case RIGHT_ARROW, LEFT_ARROW -> updatePathRightLeftArrow(isShadow);
			case RIGHT_DBLE_ARROW, LEFT_DBLE_ARROW -> updatePathDoubleLeftRightArrow(isShadow);
			case RIGHT_ROUND_BRACKET, LEFT_ROUND_BRACKET -> updatePathRoundLeftRightBracket(isShadow);
			case LEFT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET -> updatePathRightLeftSquaredBracket(isShadow);
			case SQUARE_END -> updatePathSquareEnd(pt1, pt2, isShadow);
			case ROUND_END -> updatePathRoundEnd(isShadow);
			case ROUND_IN -> updatePathRoundIn(pt1, pt2, isShadow);
			case NONE -> {
			}
		}
	}

	void setTranslation(final double tx, final double ty);

	void createMoveTo(final double x, final double y);

	void createLineTo(final double x, final double y);

	void createClosePath();

	void createArc(final double cx, final double cy, final double rx, final double ry, final double angle, final double length,
			ObservableValue<Color> strokeProp, ObservableDoubleValue strokeWidthProp);

	void createCircle(final double cx, final double cy, final double r);

	void setPathStrokeWidth(final ObservableDoubleValue widthProp);

	void setPathFill(final ObservableValue<Color> fill);

	void setCircleStrokeBinding(ObservableValue<Color> stroke);

	void setCircleFillBinding(ObservableValue<Color> fill);

	void setCircleFill(Color fill);

	void setCircleStroke(Color stroke);

	void setCircleStrokeWidth(final double width);

	void setPathStroke(final ObservableValue<Color> stroke);

	void setRotation180();
}
