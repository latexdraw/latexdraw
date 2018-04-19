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
package net.sf.latexdraw.models.impl;

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
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;

/**
 * An implementation of the abstract factory.
 * @author Arnaud Blouin
 */
public class LShapeFactory implements IShapeFactory {
	/** The map that maps types to creation operations. */
	final Map<Class<?>, Supplier<IShape>> factoryMap;

	public LShapeFactory() {
		super();
		factoryMap = new HashMap<>();
		factoryMap.put(ICircleArc.class, () -> createCircleArc());
		factoryMap.put(LCircleArc.class, () -> createCircleArc());
		factoryMap.put(ICircle.class, () -> createCircle());
		factoryMap.put(LCircle.class, () -> createCircle());
		factoryMap.put(IEllipse.class, () -> createEllipse());
		factoryMap.put(LEllipse.class, () -> createEllipse());
		factoryMap.put(ISquare.class, () -> createSquare());
		factoryMap.put(LSquare.class, () -> createSquare());
		factoryMap.put(IRectangle.class, () -> createRectangle());
		factoryMap.put(LRectangle.class, () -> createRectangle());
		factoryMap.put(ITriangle.class, () -> createTriangle());
		factoryMap.put(LTriangle.class, () -> createTriangle());
		factoryMap.put(IRhombus.class, () -> createRhombus());
		factoryMap.put(LRhombus.class, () -> createRhombus());
		factoryMap.put(IPolyline.class, () -> createPolyline(Collections.emptyList()));
		factoryMap.put(LPolyline.class, () -> createPolyline(Collections.emptyList()));
		factoryMap.put(IPolygon.class, () -> createPolygon(Collections.emptyList()));
		factoryMap.put(LPolygon.class, () -> createPolygon(Collections.emptyList()));
		factoryMap.put(IAxes.class, () -> createAxes(createPoint()));
		factoryMap.put(LAxes.class, () -> createAxes(createPoint()));
		factoryMap.put(IGrid.class, () -> createGrid(createPoint()));
		factoryMap.put(LGrid.class, () -> createGrid(createPoint()));
		factoryMap.put(IBezierCurve.class, () -> createBezierCurve(Collections.emptyList()));
		factoryMap.put(LBezierCurve.class, () -> createBezierCurve(Collections.emptyList()));
		factoryMap.put(IDot.class, () -> createDot(createPoint()));
		factoryMap.put(LDot.class, () -> createDot(createPoint()));
		factoryMap.put(IFreehand.class, () -> createFreeHand(Collections.emptyList()));
		factoryMap.put(LFreehand.class, () -> createFreeHand(Collections.emptyList()));
		factoryMap.put(IGroup.class, () -> createGroup());
		factoryMap.put(LGroup.class, () -> createGroup());
		factoryMap.put(IPicture.class, () -> createPicture(createPoint()));
		factoryMap.put(LPicture.class, () -> createPicture(createPoint()));
		factoryMap.put(IText.class, () -> createText());
		factoryMap.put(LText.class, () -> createText());
		factoryMap.put(IPlot.class, () -> createPlot(createPoint(), 1d, 10d, "x", false));
		factoryMap.put(LPlot.class, () -> createPlot(createPoint(), 1d, 10d, "x", false));
	}


	@Override
	public <T extends IShape> Optional<T> newShape(final Class<T> shapeClass) {
		if(shapeClass == null) {
			return Optional.empty();
		}
		try {
			return Optional.ofNullable(shapeClass.cast(factoryMap.get(shapeClass).get()));
		}catch(final ClassCastException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			return Optional.empty();
		}
	}

	@Override
	public IGroup createGroup(final IShape sh) {
		final IGroup gp = createGroup();
		if(sh != null) {
			gp.addShape(sh);
		}
		return gp;
	}

	@Override
	public Color createColorFX(final javafx.scene.paint.Color col) {
		if(col == null) {
			throw new IllegalArgumentException();
		}
		return createColor(col.getRed(), col.getGreen(), col.getBlue(), col.getOpacity());
	}

	@Override
	public Color createColorAWT(final java.awt.Color col) {
		if(col == null) {
			throw new IllegalArgumentException();
		}
		return createColorInt(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha());
	}

	@Override
	public Color createColorInt(final int r, final int g, final int b, final int a) {
		return createColor(r / 255.0, g / 255.0, b / 255.0, a / 255.0);
	}

	@Override
	public Color createColorInt(final int r, final int g, final int b) {
		return createColorInt(r, g, b, 255);
	}

	@Override
	public Color createColorHSB(final double h, final double s, final double b) {
		final javafx.scene.paint.Color col = javafx.scene.paint.Color.hsb(h, s, b);
		return createColor(col.getRed(), col.getGreen(), col.getBlue(), col.getOpacity());
	}

	@Override
	public Color createColor(final double r, final double g, final double b, final double o) {
		return new ColorImpl(r, g, b, o);
	}

	@Override
	public Color createColor(final double r, final double g, final double b) {
		return createColor(r, g, b, 1.0);
	}

	@Override
	public Color createColor() {
		return createColor(1.0, 1.0, 1.0, 1.0);
	}

	@Override
	public IPlot createPlot(final IPoint pos, final double minX, final double maxX, final String eq, final boolean polar) {
		return new LPlot(pos, minX, maxX, eq, polar);
	}

	@Override
	public IPoint createPoint(final Point2D pt) {
		return pt == null ? createPoint() : createPoint(pt.getX(), pt.getY());
	}

	@Override
	public IPoint createPoint(final javafx.geometry.Point2D pt) {
		return pt == null ? createPoint() : createPoint(pt.getX(), pt.getY());
	}

	@Override
	public IPoint createPoint(final Point3D pt) {
		return pt == null ? createPoint() : createPoint(pt.getX(), pt.getY());
	}

	@Override
	public IDrawing createDrawing() {
		return new LDrawing();
	}

	@Override
	public IArrow createArrow(final IArrow arrow, final IArrowableSingleShape owner) {
		return new LArrow(arrow, owner);
	}

	@Override
	public IArrow createArrow(final IArrowableSingleShape owner) {
		return new LArrow(owner);
	}

	@Override
	public IAxes createAxes(final IPoint pt) {
		return new LAxes(pt);
	}

	@Override
	public IDot createDot(final IPoint pt) {
		return new LDot(pt);
	}

	@Override
	public IBezierCurve createBezierCurve(final List<IPoint> pts) {
		return new LBezierCurve(pts);
	}

	@Override
	public IBezierCurve createBezierCurve(final List<IPoint> pts, final List<IPoint> ctrlpts) {
		return new LBezierCurve(pts, ctrlpts);
	}

	@Override
	public IBezierCurve createBezierCurveFrom(final IBezierCurve bc, final IPoint pointToAdd) {
		if(bc == null || !MathUtils.INST.isValidPt(pointToAdd)) {
			return null;
		}
		final List<IPoint> pts = new ArrayList<>(bc.getPoints());
		final List<IPoint> ptsCtrl = new ArrayList<>(bc.getFirstCtrlPts());
		pts.add(pointToAdd);
		ptsCtrl.add(createPoint(pointToAdd.getX(), pointToAdd.getY() + IBezierCurve.DEFAULT_POSITION_CTRL));

		final IBezierCurve copy = new LBezierCurve(pts, ptsCtrl);
		copy.copy(bc);
		return copy;
	}

	@Override
	public IEllipse createEllipse(final IPoint tl, final IPoint br) {
		return new LEllipse(tl, br);
	}

	@Override
	public IEllipse createEllipse() {
		return new LEllipse();
	}

	@Override
	public ITriangle createTriangle(final IPoint pos, final double width, final double height) {
		return new LTriangle(pos, width, height);
	}

	@Override
	public ITriangle createTriangle() {
		return new LTriangle();
	}

	@Override
	public IRhombus createRhombus(final IPoint centre, final double width, final double height) {
		return new LRhombus(centre, width, height);
	}

	@Override
	public IRhombus createRhombus() {
		return new LRhombus();
	}

	@Override
	public IPicture createPicture(final IPoint pt) {
		return new LPicture(pt);
	}

	@Override
	public IGrid createGrid(final IPoint pt) {
		return new LGrid(pt);
	}

	@Override
	public IFreehand createFreeHand(final List<IPoint> pts) {
		return new LFreehand(pts);
	}

	@Override
	public IFreehand createFreeHandFrom(final IFreehand sh, final IPoint pointToAdd) {
		if(sh == null || !MathUtils.INST.isValidPt(pointToAdd)) {
			return null;
		}
		final List<IPoint> pts = new ArrayList<>(sh.getPoints());
		pts.add(pointToAdd);
		final IFreehand copy = createFreeHand(pts);
		copy.copy(sh);
		return copy;
	}

	@Override
	public ICircle createCircle(final IPoint pt, final double radius) {
		return new LCircle(pt, radius);
	}

	@Override
	public ICircle createCircle() {
		return createCircle(ShapeFactory.INST.createPoint(), 10);
	}

	@Override
	public IGroup createGroup() {
		return new LGroup();
	}

	@Override
	public ILine createLine(final double x1, final double y1, final double x2, final double y2) {
		return new LLine(x1, y1, x2, y2);
	}

	@Override
	public ILine createLine(final double b, final IPoint p1) {
		return new LLine(b, p1);
	}

	@Override
	public ILine createLine(final IPoint p1, final IPoint p2) {
		return new LLine(p1, p2);
	}

	@Override
	public IPoint createPoint() {
		return new LPoint();
	}

	@Override
	public IPoint createPoint(final double x, final double y) {
		return new LPoint(x, y);
	}

	@Override
	public IPoint createPoint(final IPoint pt) {
		return new LPoint(pt);
	}

	@Override
	public IPolyline createPolyline(final List<IPoint> pts) {
		return new LPolyline(pts);
	}

	@Override
	public IPolyline createPolylineFrom(final IPolyline sh, final IPoint pointToAdd) {
		if(sh == null || !MathUtils.INST.isValidPt(pointToAdd)) {
			return null;
		}
		final List<IPoint> pts = new ArrayList<>(sh.getPoints());
		pts.add(pointToAdd);
		final IPolyline copy = createPolyline(pts);
		copy.copy(sh);
		return copy;
	}

	@Override
	public IPolygon createPolygon(final List<IPoint> pts) {
		return new LPolygon(pts);
	}

	@Override
	public IPolygon createPolygonFrom(final IPolygon sh, final IPoint pointToAdd) {
		if(sh == null || !MathUtils.INST.isValidPt(pointToAdd)) {
			return null;
		}
		final List<IPoint> pts = new ArrayList<>(sh.getPoints());
		pts.add(pointToAdd);
		final IPolygon copy = createPolygon(pts);
		copy.copy(sh);
		return copy;
	}

	@Override
	public IRectangle createRectangle() {
		return createRectangle(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(1, 1));
	}

	@Override
	public IRectangle createRectangle(final IPoint pos, final double width, final double height) {
		return createRectangle(pos, ShapeFactory.INST.createPoint(pos.getX() + width, pos.getY() + height));
	}

	@Override
	public IRectangle createRectangle(final IPoint tl, final IPoint br) {
		return new LRectangle(tl, br);
	}

	@Override
	public IText createText() {
		return new LText();
	}

	@Override
	public IText createText(final IPoint pt, final String text) {
		return new LText(pt, text);
	}

	@Override
	public ISquare createSquare() {
		return createSquare(ShapeFactory.INST.createPoint(), 1);
	}

	@Override
	public ISquare createSquare(final IPoint pos, final double width) {
		return new LSquare(pos, width);
	}

	@Override
	public ICircleArc createCircleArc(final IPoint pos, final double width) {
		return new LCircleArc(pos, width);
	}

	@Override
	public ICircleArc createCircleArc() {
		return createCircleArc(ShapeFactory.INST.createPoint(), 1);
	}

	@Override
	public <T extends IShape> T duplicate(final T shape) {
		if(shape == null) {
			return null;
		}
		final IShape dup = shape.duplicate();
		if(dup != null && dup.getClass().isAssignableFrom(shape.getClass())) {
			return (T) dup;
		}
		return null;
	}
}
