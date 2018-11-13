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
package net.sf.latexdraw.model.impl;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import javafx.geometry.Point3D;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.CircleArc;
import net.sf.latexdraw.model.api.shape.Color;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Factory;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Line;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Rhombus;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.Triangle;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of the abstract factory.
 * @author Arnaud Blouin
 */
public class ShapeFactoryImpl implements Factory {
	/** The map that maps types to creation operations. */
	private final Map<Class<?>, Supplier<Shape>> factoryMap;

	public ShapeFactoryImpl() {
		super();
		factoryMap = new HashMap<>();
		factoryMap.put(CircleArc.class, () -> createCircleArc());
		factoryMap.put(CircleArcImpl.class, () -> createCircleArc());
		factoryMap.put(Circle.class, () -> createCircle());
		factoryMap.put(CircleImpl.class, () -> createCircle());
		factoryMap.put(Ellipse.class, () -> createEllipse());
		factoryMap.put(EllipseImpl.class, () -> createEllipse());
		factoryMap.put(Square.class, () -> createSquare());
		factoryMap.put(SquareImpl.class, () -> createSquare());
		factoryMap.put(Rectangle.class, () -> createRectangle());
		factoryMap.put(RectangleImpl.class, () -> createRectangle());
		factoryMap.put(Triangle.class, () -> createTriangle());
		factoryMap.put(TriangleImpl.class, () -> createTriangle());
		factoryMap.put(Rhombus.class, () -> createRhombus());
		factoryMap.put(RhombusImpl.class, () -> createRhombus());
		factoryMap.put(Polyline.class, () -> createPolyline(Collections.emptyList()));
		factoryMap.put(PolylineImpl.class, () -> createPolyline(Collections.emptyList()));
		factoryMap.put(Polygon.class, () -> createPolygon(Collections.emptyList()));
		factoryMap.put(PolygonImpl.class, () -> createPolygon(Collections.emptyList()));
		factoryMap.put(Axes.class, () -> createAxes(createPoint()));
		factoryMap.put(AxesImpl.class, () -> createAxes(createPoint()));
		factoryMap.put(Grid.class, () -> createGrid(createPoint()));
		factoryMap.put(GridImpl.class, () -> createGrid(createPoint()));
		factoryMap.put(BezierCurve.class, () -> createBezierCurve(Collections.emptyList()));
		factoryMap.put(BezierCurveImpl.class, () -> createBezierCurve(Collections.emptyList()));
		factoryMap.put(Dot.class, () -> createDot(createPoint()));
		factoryMap.put(DotImpl.class, () -> createDot(createPoint()));
		factoryMap.put(Freehand.class, () -> createFreeHand(Collections.emptyList()));
		factoryMap.put(FreehandImpl.class, () -> createFreeHand(Collections.emptyList()));
		factoryMap.put(Group.class, () -> createGroup());
		factoryMap.put(GroupImpl.class, () -> createGroup());
		factoryMap.put(Picture.class, () -> createPicture(createPoint()));
		factoryMap.put(PictureImpl.class, () -> createPicture(createPoint()));
		factoryMap.put(net.sf.latexdraw.model.api.shape.Text.class, () -> createText());
		factoryMap.put(TextImpl.class, () -> createText());
		factoryMap.put(Plot.class, () -> createPlot(createPoint(), 1d, 10d, "x", false)); //NON-NLS
		factoryMap.put(PlotImpl.class, () -> createPlot(createPoint(), 1d, 10d, "x", false)); //NON-NLS
	}


	@Override
	public @NotNull <T extends Shape> Optional<T> newShape(final @NotNull Class<T> shapeClass) {
		try {
			return Optional.ofNullable(shapeClass.cast(factoryMap.get(shapeClass).get()));
		}catch(final ClassCastException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			return Optional.empty();
		}
	}

	@Override
	public @NotNull Group createGroup(final Shape sh) {
		final Group gp = createGroup();
		if(sh != null) {
			gp.addShape(sh);
		}
		return gp;
	}

	@Override
	public @NotNull Color createColorFX(final javafx.scene.paint.Color col) {
		if(col == null) {
			throw new IllegalArgumentException();
		}
		return createColor(col.getRed(), col.getGreen(), col.getBlue(), col.getOpacity());
	}

	@Override
	public @NotNull Color createColorInt(final int r, final int g, final int b, final int a) {
		return createColor(r / 255.0, g / 255.0, b / 255.0, a / 255.0);
	}

	@Override
	public @NotNull Color createColorInt(final int r, final int g, final int b) {
		return createColorInt(r, g, b, 255);
	}

	@Override
	public @NotNull Color createColorHSB(final double h, final double s, final double b) {
		final javafx.scene.paint.Color col = javafx.scene.paint.Color.hsb(h, s, b);
		return createColor(col.getRed(), col.getGreen(), col.getBlue(), col.getOpacity());
	}

	@Override
	public @NotNull Color createColor(final double r, final double g, final double b, final double o) {
		return new ColorImpl(r, g, b, o);
	}

	@Override
	public @NotNull Color createColor(final double r, final double g, final double b) {
		return createColor(r, g, b, 1.0);
	}

	@Override
	public @NotNull Color createColor() {
		return createColor(1.0, 1.0, 1.0, 1.0);
	}

	@Override
	public @NotNull Plot createPlot(final Point pos, final double minX, final double maxX, final String eq, final boolean polar) {
		return new PlotImpl(pos, minX, maxX, eq, polar);
	}

	@Override
	public @NotNull Point createPoint(final Point2D pt) {
		return pt == null ? createPoint() : createPoint(pt.getX(), pt.getY());
	}

	@Override
	public @NotNull Point createPoint(final javafx.geometry.Point2D pt) {
		return pt == null ? createPoint() : createPoint(pt.getX(), pt.getY());
	}

	@Override
	public @NotNull Point createPoint(final Point3D pt) {
		return pt == null ? createPoint() : createPoint(pt.getX(), pt.getY());
	}

	@Override
	public @NotNull Drawing createDrawing() {
		return new DrawingImpl();
	}

	@Override
	public @NotNull Arrow createArrow(final Arrow arrow, final ArrowableSingleShape owner) {
		return new ArrowImpl(arrow, owner);
	}

	@Override
	public @NotNull Arrow createArrow(final ArrowableSingleShape owner) {
		return new ArrowImpl(owner);
	}

	@Override
	public @NotNull Axes createAxes(final Point pt) {
		return new AxesImpl(pt);
	}

	@Override
	public @NotNull Dot createDot(final Point pt) {
		return new DotImpl(pt);
	}

	@Override
	public @NotNull BezierCurve createBezierCurve(final @NotNull List<Point> pts) {
		return new BezierCurveImpl(pts);
	}

	@Override
	public @NotNull BezierCurve createBezierCurve(final @NotNull List<Point> pts, final @NotNull List<Point> ctrlpts) {
		return new BezierCurveImpl(pts, ctrlpts);
	}

	@Override
	public @NotNull BezierCurve createBezierCurveFrom(final BezierCurve bc, final Point pointToAdd) {
		if(bc == null || !MathUtils.INST.isValidPt(pointToAdd)) {
			throw new IllegalArgumentException();
		}
		final List<Point> pts = new ArrayList<>(bc.getPoints());
		final List<Point> ptsCtrl = new ArrayList<>(bc.getFirstCtrlPts());
		pts.add(pointToAdd);
		ptsCtrl.add(createPoint(pointToAdd.getX(), pointToAdd.getY() + BezierCurve.DEFAULT_POSITION_CTRL));

		final BezierCurve copy = new BezierCurveImpl(pts, ptsCtrl);
		copy.copy(bc);
		return copy;
	}

	@Override
	public @NotNull Ellipse createEllipse(final Point tl, final Point br) {
		return new EllipseImpl(tl, br);
	}

	@Override
	public @NotNull Ellipse createEllipse() {
		return new EllipseImpl();
	}

	@Override
	public @NotNull Triangle createTriangle(final Point pos, final double width, final double height) {
		return new TriangleImpl(pos, width, height);
	}

	@Override
	public @NotNull Triangle createTriangle() {
		return new TriangleImpl();
	}

	@Override
	public @NotNull Rhombus createRhombus(final Point centre, final double width, final double height) {
		return new RhombusImpl(centre, width, height);
	}

	@Override
	public @NotNull Rhombus createRhombus() {
		return new RhombusImpl();
	}

	@Override
	public @NotNull Picture createPicture(final Point pt) {
		return new PictureImpl(pt);
	}

	@Override
	public @NotNull Grid createGrid(final Point pt) {
		return new GridImpl(pt);
	}

	@Override
	public @NotNull Freehand createFreeHand(final @NotNull List<Point> pts) {
		return new FreehandImpl(pts);
	}

	@Override
	public @NotNull Freehand createFreeHandFrom(final Freehand sh, final Point pointToAdd) {
		if(sh == null || !MathUtils.INST.isValidPt(pointToAdd)) {
			throw new IllegalArgumentException();
		}
		final List<Point> pts = new ArrayList<>(sh.getPoints());
		pts.add(pointToAdd);
		final Freehand copy = createFreeHand(pts);
		copy.copy(sh);
		return copy;
	}

	@Override
	public @NotNull Circle createCircle(final Point pt, final double radius) {
		return new CircleImpl(pt, radius);
	}

	@Override
	public @NotNull Circle createCircle() {
		return createCircle(ShapeFactory.INST.createPoint(), 10);
	}

	@Override
	public @NotNull Group createGroup() {
		return new GroupImpl();
	}

	@Override
	public @NotNull Line createLine(final double x1, final double y1, final double x2, final double y2) {
		return new LineImpl(x1, y1, x2, y2);
	}

	@Override
	public @NotNull Line createLine(final double b, final Point p1) {
		return new LineImpl(b, p1);
	}

	@Override
	public @NotNull Line createLine(final Point p1, final Point p2) {
		return new LineImpl(p1, p2);
	}

	@Override
	public @NotNull Point createPoint() {
		return new PointImpl();
	}

	@Override
	public @NotNull Point createPoint(final double x, final double y) {
		return new PointImpl(x, y);
	}

	@Override
	public @NotNull Point createPoint(final Point pt) {
		return new PointImpl(pt);
	}

	@Override
	public @NotNull Polyline createPolyline(final @NotNull List<Point> pts) {
		return new PolylineImpl(pts);
	}

	@Override
	public @NotNull Polyline createPolylineFrom(final Polyline sh, final Point pointToAdd) {
		if(sh == null || !MathUtils.INST.isValidPt(pointToAdd)) {
			throw new IllegalArgumentException();
		}
		final List<Point> pts = new ArrayList<>(sh.getPoints());
		pts.add(pointToAdd);
		final Polyline copy = createPolyline(pts);
		copy.copy(sh);
		return copy;
	}

	@Override
	public @NotNull Polygon createPolygon(final @NotNull List<Point> pts) {
		return new PolygonImpl(pts);
	}

	@Override
	public @NotNull Polygon createPolygonFrom(final Polygon sh, final Point pointToAdd) {
		if(sh == null || !MathUtils.INST.isValidPt(pointToAdd)) {
			throw new IllegalArgumentException();
		}
		final List<Point> pts = new ArrayList<>(sh.getPoints());
		pts.add(pointToAdd);
		final Polygon copy = createPolygon(pts);
		copy.copy(sh);
		return copy;
	}

	@Override
	public @NotNull Rectangle createRectangle() {
		return createRectangle(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(1, 1));
	}

	@Override
	public @NotNull Rectangle createRectangle(final Point pos, final double width, final double height) {
		return createRectangle(pos, ShapeFactory.INST.createPoint(pos.getX() + width, pos.getY() + height));
	}

	@Override
	public @NotNull Rectangle createRectangle(final Point tl, final Point br) {
		return new RectangleImpl(tl, br);
	}

	@Override
	public @NotNull net.sf.latexdraw.model.api.shape.Text createText() {
		return new TextImpl();
	}

	@Override
	public @NotNull net.sf.latexdraw.model.api.shape.Text createText(final Point pt, final String text) {
		return new TextImpl(pt, text);
	}

	@Override
	public @NotNull Square createSquare() {
		return createSquare(ShapeFactory.INST.createPoint(), 1);
	}

	@Override
	public @NotNull Square createSquare(final Point pos, final double width) {
		return new SquareImpl(pos, width);
	}

	@Override
	public @NotNull CircleArc createCircleArc(final Point pos, final double width) {
		return new CircleArcImpl(pos, width);
	}

	@Override
	public @NotNull CircleArc createCircleArc() {
		return createCircleArc(ShapeFactory.INST.createPoint(), 1);
	}

	@Override
	public @NotNull <T extends Shape> Optional<T> duplicate(final T shape) {
		if(shape == null) {
			return Optional.empty();
		}
		final Shape dup = shape.duplicate();
		if(dup.getClass().isAssignableFrom(shape.getClass())) {
			return Optional.of((T) dup);
		}
		return Optional.empty();
	}
}
