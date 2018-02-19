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

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.StrokeLineCap;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * The JFX shape view for Bézier curves.
 * @author Arnaud Blouin
 */
public class ViewBezierCurve extends ViewPathShape<IBezierCurve> {
	protected final ViewArrowableTraitPath<IBezierCurve> viewArrows;
	protected final Group showPoint;
	protected final CubicCurveTo closingCurve;
	protected final Line lastShPtsline;
	protected final ChangeListener<Boolean> openUpdate;

	/**
	 * Creates the view.
	 * @param shape The model.
	 */
	ViewBezierCurve(final IBezierCurve shape) {
		super(shape);
		viewArrows = new ViewArrowableTraitPath<>(this);
		showPoint = new Group();

		final MoveTo moveTo = ViewFactory.INSTANCE.createMoveTo(0d, 0d);
		moveTo.xProperty().bind(shape.getPtAt(0).xProperty());
		moveTo.yProperty().bind(shape.getPtAt(0).yProperty());
		border.getElements().add(moveTo);

		addCurveTo(shape.getPtAt(1), model.getFirstCtrlPtAt(0), model.getFirstCtrlPtAt(1));

		IntStream.range(2, shape.getNbPoints()).forEach(index -> addCurveTo(shape.getPtAt(index), model.getSecondCtrlPtAt(index - 1),
			model.getFirstCtrlPtAt(index)));

		closingCurve = addCurveTo(shape.getPtAt(0), model.getSecondCtrlPtAt(-1), model.getSecondCtrlPtAt(0));

		openUpdate = (observable, oldValue, newValue) -> updateOpen(newValue);
		shape.openedProperty().addListener(openUpdate);
		updateOpen(model.isOpened());
		getChildren().add(showPoint);
		getChildren().add(viewArrows);
		viewArrows.updateAllArrows();
		showPoint.visibleProperty().bind(shape.showPointProperty());

		lastShPtsline = createLine(model.getSecondCtrlPtAt(model.getSecondCtrlPts().size() - 1), model.getSecondCtrlPtAt(0));
		showPoint.getChildren().addAll(lastShPtsline);
		lastShPtsline.visibleProperty().bind(shape.openedProperty().not().and(shape.showPointProperty()));
		bindShowPoints();

		// Building the shadow
		shadow.getElements().addAll(border.getElements());
		// Building the double borders
		dblBorder.getElements().addAll(border.getElements());
	}

	/**
	 * Sub routine that creates and binds show points.
	 */
	private void bindShowPoints() {
		showPoint.getChildren().addAll(Stream.concat(Stream.concat(model.getPoints().stream(), model.getFirstCtrlPts().stream()), model.getSecondCtrlPts()
			.stream()).map(pt -> {
			final Ellipse dot = new Ellipse();
			dot.fillProperty().bind(Bindings.createObjectBinding(() -> model.getLineColour().toJFX(), model.lineColourProperty()));
			dot.centerXProperty().bind(pt.xProperty());
			dot.centerYProperty().bind(pt.yProperty());
			dot.radiusXProperty().bind(Bindings.createDoubleBinding(() -> (model.getArrowAt(0).getDotSizeDim() +
				model.getArrowAt(0).getDotSizeNum() * model.getFullThickness()) / 2d, model.thicknessProperty(), model.dbleBordProperty(),
				model.dbleBordSepProperty(), model.getArrowAt(0).dotSizeDimProperty(), model.getArrowAt(0).dotSizeNumProperty()));
			dot.radiusYProperty().bind(dot.radiusXProperty());
			return dot;
		}).collect(Collectors.toList()));

		showPoint.getChildren().addAll(IntStream.range(0, model.getFirstCtrlPts().size()).
			mapToObj(i -> createLine(model.getFirstCtrlPtAt(i), model.getSecondCtrlPtAt(i))).collect(Collectors.toList()));

		showPoint.getChildren().addAll(IntStream.range(1, model.getFirstCtrlPts().size() - 1).
			mapToObj(i -> createLine(model.getSecondCtrlPtAt(i), model.getFirstCtrlPtAt(i + 1))).collect(Collectors.toList()));

		showPoint.getChildren().addAll(createLine(model.getFirstCtrlPtAt(0), model.getFirstCtrlPtAt(1)));

		// Hiding points on arrows
		showPoint.getChildren().stream().filter(node -> node instanceof Ellipse && ((Ellipse) node).getCenterX() == model.getPtAt(0).getX() &&
			((Ellipse) node).getCenterY() == model.getPtAt(0).getY()).findAny().
			ifPresent(ell -> ell.visibleProperty().bind(model.getArrowAt(0).styleProperty().isEqualTo(ArrowStyle.NONE)));

		showPoint.getChildren().stream().filter(node -> node instanceof Ellipse && ((Ellipse) node).getCenterX() == model.getPtAt(-1).getX() &&
			((Ellipse) node).getCenterY() == model.getPtAt(-1).getY()).findAny().
			ifPresent(ell -> ell.visibleProperty().bind(model.getArrowAt(-1).styleProperty().isEqualTo(ArrowStyle.NONE)));
	}

	private void updateOpen(final boolean open) {
		if(open) {
			border.getElements().remove(closingCurve);
			shadow.getElements().remove(closingCurve);
			dblBorder.getElements().remove(closingCurve);
		}else {
			if(!border.getElements().contains(closingCurve)) {
				border.getElements().add(closingCurve);
				shadow.getElements().add(closingCurve);
				dblBorder.getElements().add(closingCurve);
			}
		}
	}

	private Line createLine(final IPoint p1, final IPoint p2) {
		final Line line = new Line();
		line.startXProperty().bind(p1.xProperty());
		line.startYProperty().bind(p1.yProperty());
		line.endXProperty().bind(p2.xProperty());
		line.endYProperty().bind(p2.yProperty());
		line.strokeWidthProperty().bind(Bindings.createDoubleBinding(() -> model.getFullThickness() / 2d, model.thicknessProperty(),
			model.dbleBordSepProperty(), model.dbleBordProperty()));
		line.strokeProperty().bind(Bindings.createObjectBinding(() -> model.getLineColour().toJFX(), model.lineColourProperty()));
		line.setStrokeLineCap(StrokeLineCap.BUTT);
		line.getStrokeDashArray().clear();
		line.getStrokeDashArray().addAll(model.getDashSepBlack(), model.getDashSepWhite());
		return line;
	}

	private CubicCurveTo addCurveTo(final IPoint pt, final IPoint ctrl1, final IPoint ctrl2) {
		final CubicCurveTo curveto = ViewFactory.INSTANCE.createCubicCurveTo(0d, 0d, 0d, 0d, 0d, 0d);
		curveto.xProperty().bind(pt.xProperty());
		curveto.yProperty().bind(pt.yProperty());
		curveto.controlX1Property().bind(ctrl1.xProperty());
		curveto.controlY1Property().bind(ctrl1.yProperty());
		curveto.controlX2Property().bind(ctrl2.xProperty());
		curveto.controlY2Property().bind(ctrl2.yProperty());
		border.getElements().add(curveto);
		return curveto;
	}


	@Override
	public void flush() {
		unbindShowPoints();
		border.getElements().forEach(elt -> ViewFactory.INSTANCE.flushPathElement(elt));
		shadow.getElements().forEach(elt -> ViewFactory.INSTANCE.flushPathElement(elt));
		dblBorder.getElements().forEach(elt -> ViewFactory.INSTANCE.flushPathElement(elt));
		model.openedProperty().removeListener(openUpdate);
		model.openedProperty().unbind();
		lastShPtsline.visibleProperty().unbind();
		super.flush();
	}

	private void unbindShowPoints() {
		showPoint.getChildren().stream().filter(node -> node instanceof Ellipse).map(node -> (Ellipse) node).forEach(ell -> {
			ell.fillProperty().unbind();
			ell.centerXProperty().unbind();
			ell.centerYProperty().unbind();
			ell.radiusXProperty().unbind();
			ell.radiusYProperty().unbind();
			ell.visibleProperty().unbind();
		});

		showPoint.getChildren().stream().filter(node -> node instanceof Line).map(node -> (Line) node).forEach(line -> {
			line.startXProperty().unbind();
			line.startYProperty().unbind();
			line.endXProperty().unbind();
			line.endYProperty().unbind();
			line.strokeWidthProperty().unbind();
			line.strokeProperty().unbind();
		});
	}
}
