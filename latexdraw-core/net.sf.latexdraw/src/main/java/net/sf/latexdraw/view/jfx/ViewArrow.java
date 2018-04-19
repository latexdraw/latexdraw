/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import java.util.Objects;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.view.GenericViewArrow;

/**
 * The JFX view of an arrow.
 * @author Arnaud Blouin
 */
public class ViewArrow extends Group implements GenericViewArrow {
	public static final String ID = "arrow"; //NON-NLS

	final IArrow arrow;
	final Path path;
	final Ellipse ellipse;
	final Arc arc;

	/**
	 * Creates the view.
	 * @param model The arrow. Cannot be null.
	 * @throws NullPointerException if the given arrow is null.
	 */
	ViewArrow(final IArrow model) {
		super();
		setId(ID);
		arrow = Objects.requireNonNull(model);
		path = new Path();
		ellipse = new Ellipse();
		arc = new Arc();
		getChildren().add(path);
		getChildren().add(ellipse);
		getChildren().add(arc);
		enableShape(false, false, false);
	}


	/**
	 * Method to enable some of the JFX shapes that represent arrows.
	 */
	private void enableShape(final boolean enablePath, final boolean enableArc, final boolean enableEll) {
		path.setVisible(enablePath);
		path.setDisable(!enablePath);
		ellipse.setVisible(enableEll);
		ellipse.setDisable(!enableEll);
		arc.setVisible(enableArc);
		arc.setDisable(!enableArc);
	}


	@Override
	public IArrow getArrow() {
		return arrow;
	}

	@Override
	public void updatePath(final boolean isShadow) {
		path.setStrokeLineCap(StrokeLineCap.BUTT);
		path.getElements().clear();
		path.fillProperty().unbind();
		path.strokeWidthProperty().unbind();
		path.getTransforms().clear();
		ellipse.getTransforms().clear();
		arc.getTransforms().clear();

		GenericViewArrow.super.updatePath(isShadow);

		final ILine line = arrow.getArrowLine();
		final double lineAngle = (-line.getLineAngle() + Math.PI * 2d) % (Math.PI * 2d);

		if(arrow.getArrowStyle() != ArrowStyle.NONE && !MathUtils.INST.equalsDouble(lineAngle, 0d)) {
			final Rotate rotate = new Rotate(Math.toDegrees(lineAngle), 0d, 0d);
			path.getTransforms().add(rotate);
			ellipse.getTransforms().add(rotate);
			arc.getTransforms().add(rotate);
		}
	}

	@Override
	public void setTranslation(final double tx, final double ty) {
		final Translate translate = new Translate(tx, ty);
		ellipse.getTransforms().add(0, translate);
		path.getTransforms().add(0, translate);
		arc.getTransforms().add(0, translate);
	}

	@Override
	public void createMoveTo(final double x, final double y) {
		path.getElements().add(new MoveTo(x, y));
		enableShape(true, false, false);
	}

	@Override
	public void createLineTo(final double x, final double y) {
		path.getElements().add(new LineTo(x, y));
	}

	@Override
	public void createClosePath() {
		path.getElements().add(new ClosePath());
	}

	@Override
	public void createArc(final double cx, final double cy, final double rx, final double ry, final double angle, final double length,
						final ObservableValue<Color> strokeProp, final ObservableDoubleValue strokeWidthProp) {
		arc.setCenterX(cx);
		arc.setCenterY(cy);
		arc.setRadiusX(rx);
		arc.setRadiusY(ry);
		arc.setStartAngle(angle);
		arc.setLength(length);
		arc.strokeProperty().unbind();
		arc.strokeProperty().bind(strokeProp);
		arc.strokeWidthProperty().unbind();
		arc.strokeWidthProperty().bind(strokeWidthProp);
		arc.setFill(null);
		enableShape(false, true, false);
	}

	@Override
	public void createCircle(final double cx, final double cy, final double r) {
		ellipse.setCenterX(cx);
		ellipse.setCenterY(cy);
		ellipse.setRadiusX(r);
		ellipse.setRadiusY(r);
		enableShape(false, false, true);
	}

	@Override
	public void setPathStrokeWidth(final ObservableDoubleValue widthProp) {
		path.strokeWidthProperty().unbind();
		path.strokeWidthProperty().bind(widthProp);
	}

	@Override
	public void setPathFill(final ObservableValue<Color> fill) {
		path.fillProperty().unbind();
		if(fill == null) {
			path.setFill(null);
		}else {
			path.fillProperty().bind(fill);
		}
	}

	@Override
	public void setCircleStrokeBinding(final ObservableValue<Color> stroke) {
		ellipse.strokeProperty().unbind();
		ellipse.strokeProperty().bind(stroke);
	}

	@Override
	public void setCircleFillBinding(final ObservableValue<Color> fill) {
		ellipse.fillProperty().unbind();
		ellipse.fillProperty().bind(fill);
	}

	@Override
	public void setCircleFill(final Color fill) {
		ellipse.fillProperty().unbind();
		ellipse.setFill(fill);
	}

	@Override
	public void setCircleStroke(final Color stroke) {
		ellipse.strokeProperty().unbind();
		ellipse.setStroke(stroke);
	}

	@Override
	public void setCircleStrokeWidth(final double width) {
		ellipse.strokeWidthProperty().unbind();
		ellipse.setStrokeWidth(width);
	}

	@Override
	public void setPathStroke(final ObservableValue<Color> stroke) {
		path.strokeProperty().unbind();
		path.strokeProperty().bind(stroke);
	}

	@Override
	public void setRotation180() {
		final Rotate rotate = new Rotate(180d, 0d, 0d);
		path.getTransforms().add(rotate);
		ellipse.getTransforms().add(rotate);
		arc.getTransforms().add(rotate);
	}

	public void flush() {
		path.strokeProperty().unbind();
		path.fillProperty().unbind();
		path.strokeWidthProperty().unbind();
		arc.strokeProperty().unbind();
		arc.strokeWidthProperty().unbind();
		ellipse.strokeProperty().unbind();
		ellipse.fillProperty().unbind();
		getChildren().clear();
	}
}
