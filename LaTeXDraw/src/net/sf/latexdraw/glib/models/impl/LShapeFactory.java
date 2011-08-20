package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPicture;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRhombus;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.ISquare;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.ITriangle;

/**
 * This factory creates shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 04/02/2010<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public class LShapeFactory implements IShapeFactory {
	/**
	 * Creates the factory.
	 * @since 3.0
	 */
	public LShapeFactory() {
		super();
	}


	@Override
	public <T extends IShape> T newShape(final Class<T> shapeClass) {
		if(shapeClass==null)
			return null;
		if(shapeClass.equals(ICircleArc.class) || shapeClass.equals(LCircleArc.class))
			return shapeClass.cast(createCircleArc(true));
		if(shapeClass.equals(ICircle.class) || shapeClass.equals(LCircle.class))
			return shapeClass.cast(createCircle(true));
		if(shapeClass.equals(IEllipse.class) || shapeClass.equals(LEllipse.class))
			return shapeClass.cast(createEllipse(true));
		if(shapeClass.equals(ISquare.class) || shapeClass.equals(LSquare.class))
			return shapeClass.cast(createSquare(true));
		if(shapeClass.equals(IRectangle.class) || shapeClass.equals(LRectangle.class))
			return shapeClass.cast(createRectangle(true));
		if(shapeClass.equals(ITriangle.class) || shapeClass.equals(LTriangle.class))
			return shapeClass.cast(createTriangle(true));
		if(shapeClass.equals(IRhombus.class) || shapeClass.equals(LRhombus.class))
			return shapeClass.cast(createRhombus(true));
		if(shapeClass.equals(IPolyline.class) || shapeClass.equals(LPolyline.class))
			return shapeClass.cast(createPolyline(true));
		if(shapeClass.equals(IPolygon.class) || shapeClass.equals(LPolygon.class))
			return shapeClass.cast(createPolygon(true));
		if(shapeClass.equals(IAxes.class) || shapeClass.equals(LAxes.class))
			return shapeClass.cast(createAxes(true, createPoint()));
		if(shapeClass.equals(IGrid.class) || shapeClass.equals(LGrid.class))
			return shapeClass.cast(createGrid(true, createPoint()));
		if(shapeClass.equals(IBezierCurve.class) || shapeClass.equals(LBezierCurve.class))
			return shapeClass.cast(createBezierCurve(true));
		if(shapeClass.equals(IDot.class) || shapeClass.equals(LDot.class))
			return shapeClass.cast(createDot(createPoint(), true));
		if(shapeClass.equals(IFreehand.class) || shapeClass.equals(LFreehand.class))
			return shapeClass.cast(createFreeHand(createPoint(), true));
		if(shapeClass.equals(IGroup.class) || shapeClass.equals(LGroup.class))
			return shapeClass.cast(createGroup(true));
		if(shapeClass.equals(IPicture.class) || shapeClass.equals(LPicture.class))
			return shapeClass.cast(createPicture(true, createPoint(), "")); //$NON-NLS-1$
		if(shapeClass.equals(IText.class) || shapeClass.equals(LText.class))
			return shapeClass.cast(createText(true));
		return null;
	}

	@Override
	public IDrawing createDrawing() {
		return new LDrawing();
	}

	@Override
	public IArrow createArrow(final IArrow arrow, final IShape owner) {
		return new LArrow(arrow, owner);
	}

	@Override
	public IArrow createArrow(final IShape owner) {
		return new LArrow(owner);
	}

	@Override
	public IAxes createAxes(final boolean isUniqueID, final IPoint pt) {
		return new LAxes(isUniqueID, pt);
	}

	@Override
	public IDot createDot(final IPoint pt, final boolean isUniqueID) {
		return new LDot(pt, isUniqueID);
	}

	@Override
	public IBezierCurve createBezierCurve(final boolean isUniqueID) {
		return new LBezierCurve(isUniqueID);
	}

	@Override
	public IBezierCurve createBezierCurve(final IPoint point, final IPoint point2, final boolean uniqueID) {
		return new LBezierCurve(point, point2, uniqueID);
	}

	@Override
	public IEllipse createEllipse(final IPoint tl, final IPoint br, final boolean isUniqueID) {
		return new LEllipse(tl, br, isUniqueID);
	}

	@Override
	public IEllipse createEllipse(final boolean isUniqueID) {
		return new LEllipse(isUniqueID);
	}

	@Override
	public ITriangle createTriangle(final IPoint pos, final double width, final double height, final boolean uniqueID) {
		return new LTriangle(pos, width, height, uniqueID);
	}

	@Override
	public ITriangle createTriangle(final boolean isUniqueID) {
		return new LTriangle(isUniqueID);
	}

	@Override
	public IRhombus createRhombus(final IPoint centre, final double width, final double height, final boolean uniqueID) {
		return new LRhombus(centre, width, height, uniqueID);
	}

	@Override
	public IRhombus createRhombus(final boolean isUniqueID) {
		return new LRhombus(isUniqueID);
	}

	@Override
	public IPicture createPicture(final boolean isUniqueID, final IPoint pt, final String pathSource) {
		return new LPicture(isUniqueID, pt, pathSource);
	}

	@Override
	public IGrid createGrid(final boolean isUniqueID, final IPoint pt) {
		return new LGrid(isUniqueID, pt);
	}

	@Override
	public IFreehand createFreeHand(final IPoint pt, final boolean uniqueID) {
		return new LFreehand(pt, uniqueID);
	}

	@Override
	public ICircle createCircle(final IPoint pt, final double radius, final boolean isUniqueID) {
		return new LCircle(pt, radius, isUniqueID);
	}

	@Override
	public ICircle createCircle(final boolean isUniqueID) {
		return new LCircle(isUniqueID);
	}

	@Override
	public IGroup createGroup(final boolean uniqueID) {
		return new LGroup(uniqueID);
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
	public IPolyline createPolyline(final boolean uniqueID) {
		return new LPolyline(uniqueID);
	}

	@Override
	public IPolyline createPolyline(final IPoint point, final IPoint point2, final boolean uniqueID) {
		return new LPolyline(point, point2, uniqueID);
	}

	@Override
	public IPolygon createPolygon(final boolean uniqueID) {
		return new LPolygon(uniqueID);
	}

	@Override
	public IPolygon createPolygon(final IPoint point, final IPoint point2, final boolean uniqueID) {
		return new LPolygon(point, point2, uniqueID);
	}

	@Override
	public IRectangle createRectangle(final boolean uniqueID) {
		return new LRectangle(uniqueID);
	}

	@Override
	public IRectangle createRectangle(final IPoint pos, final double width, final double height, final boolean uniqueID) {
		return new LRectangle(pos, width, height, uniqueID);
	}

	@Override
	public IRectangle createRectangle(final IPoint tl, final IPoint br, final boolean uniqueID) {
		return new LRectangle(tl, br, uniqueID);
	}

	@Override
	public IText createText(final boolean isUniqueID) {
		return new LText(isUniqueID);
	}

	@Override
	public IText createText(final boolean isUniqueID, final IPoint pt, final String text) {
		return new LText(isUniqueID, pt, text);
	}

	@Override
	public ISquare createSquare(final boolean isUniqueID) {
		return new LSquare(isUniqueID);
	}

	@Override
	public ISquare createSquare(final IPoint pos, final double width, final boolean isUniqueID) {
		return new LSquare(pos, width, isUniqueID);
	}


	@Override
	public ICircleArc createCircleArc(final IPoint tl, final IPoint br, final boolean uniqueID) {
		return new LCircleArc(tl, br, uniqueID);
	}


	@Override
	public ICircleArc createCircleArc(final boolean isUniqueID) {
		return new LCircleArc(isUniqueID);
	}


	@Override
	public IShape duplicate(final IShape shape) {
		return shape==null ? null : shape.duplicate();
	}
}
