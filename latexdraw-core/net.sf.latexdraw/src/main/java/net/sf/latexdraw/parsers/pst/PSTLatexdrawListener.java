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
package net.sf.latexdraw.parsers.pst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.geometry.Point2D;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.util.Tuple;
import org.antlr.v4.runtime.Token;

public class PSTLatexdrawListener extends PSTCtxListener {
	private final List<IShape> shapes;

	public PSTLatexdrawListener() {
		super();
		shapes = new ArrayList<>();
		PSTContext.PPC = IShape.PPC;
	}

	@Override
	public void exitPsline(final net.sf.latexdraw.parsers.pst.PSTParser.PslineContext ctx) {
		Stream<IPoint> stream = ctx.pts.stream().map(node -> ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(node)));

		if(ctx.pts.size() == 1) {
			stream = Stream.concat(Stream.of(ShapeFactory.INST.createPoint(ctx.pstctx.originToPoint())), stream);
		}

		shapes.add(createLine(ctx.pstctx.starredCmd(ctx.cmd), stream.collect(Collectors.toList()), ctx.pstctx, false));
	}

	@Override
	public void exitPsqline(final net.sf.latexdraw.parsers.pst.PSTParser.PsqlineContext ctx) {
		shapes.add(createLine(false, Arrays.asList(ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(ctx.p1)),
			ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(ctx.p2))), ctx.pstctx, false));
	}

	@Override
	public void exitPsframe(final net.sf.latexdraw.parsers.pst.PSTParser.PsframeContext ctx) {
		final IRectangle rec = ShapeFactory.INST.createRectangle();
		final Tuple<IPoint, IPoint> pts = getRectangularPoints(ctx.p1, ctx.p2, ctx.pstctx);
		rec.setLineArc(ctx.pstctx.frameArc);

		// The x-coordinates of pt1 must be lower than pt2 one.
		if(pts.a.getX() > pts.b.getX()) {
			final double tmp = pts.a.getX();
			pts.a.setX(pts.b.getX());
			pts.b.setX(tmp);
		}

		// The y-coordinates of pt1 must be lower than pt2 one.
		if(pts.a.getY() < pts.b.getY()) {
			final double tmp = pts.a.getY();
			pts.a.setY(pts.b.getY());
			pts.b.setY(tmp);
		}

		setRectangularShape(rec, pts.a.getX(), pts.a.getY(), Math.abs(pts.b.getX() - pts.a.getX()),
			Math.abs(pts.b.getY() - pts.a.getY()), ctx.pstctx, ctx.cmd);
		shapes.add(rec);
	}

	@Override
	public void exitPsdot(final net.sf.latexdraw.parsers.pst.PSTParser.PsdotContext ctx) {
		setDot(ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(ctx.pt)), ctx.pstctx, ctx.pstctx.starredCmd(ctx.cmd));
	}

	@Override
	public void exitPsdots(final net.sf.latexdraw.parsers.pst.PSTParser.PsdotsContext ctx) {
		ctx.pts.forEach(pt -> setDot(ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(pt)), ctx.pstctx, ctx.pstctx.starredCmd(ctx.cmd)));
	}

	@Override
	public void exitPsellipse(final net.sf.latexdraw.parsers.pst.PSTParser.PsellipseContext ctx) {
		final IEllipse ell = ShapeFactory.INST.createEllipse();
		final Tuple<IPoint, IPoint> pts = getRectangularPoints(ctx.p1, ctx.p2, ctx.pstctx);
		setRectangularShape(ell, pts.a.getX() - pts.b.getX(), pts.a.getY() - pts.b.getY(), Math.abs(pts.b.getX() * 2d),
			Math.abs(pts.b.getY() * 2d), ctx.pstctx, ctx.cmd);
		shapes.add(ell);
	}

	@Override
	public void exitPsdiamond(final net.sf.latexdraw.parsers.pst.PSTParser.PsdiamondContext ctx) {
		final IRhombus rhombus = ShapeFactory.INST.createRhombus();
		final Tuple<IPoint, IPoint> pts = getRectangularPoints(ctx.p1, ctx.p2, ctx.pstctx);
		setRectangularShape(rhombus, pts.a.getX() - pts.b.getX(), pts.a.getY() - pts.b.getY(), Math.abs(pts.b.getX() * 2d),
			Math.abs(pts.b.getY() * 2d), ctx.pstctx, ctx.cmd);

		if(!MathUtils.INST.equalsDouble(ctx.pstctx.gangle, 0d)) {
			rhombus.setRotationAngle(rhombus.getRotationAngle() - Math.toRadians(ctx.pstctx.gangle));
		}

		shapes.add(rhombus);
	}

	@Override
	public void exitPscircle(final net.sf.latexdraw.parsers.pst.PSTParser.PscircleContext ctx) {
		final ICircle circle = ShapeFactory.INST.createCircle();
		setCircle(circle, ctx.pstctx.coordToAdjustedPoint(ctx.centre), ctx.pstctx.valDimtoDouble(ctx.bracketValueDim().valueDim()) * PSTContext.PPC,
			ctx.pstctx, ctx.pstctx.starredCmd(ctx.cmd));
		shapes.add(circle);
	}

	@Override
	public void exitPsqdisk(final net.sf.latexdraw.parsers.pst.PSTParser.PsqdiskContext ctx) {
		final ICircle circle = ShapeFactory.INST.createCircle();
		setCircle(circle, ctx.pstctx.coordToAdjustedPoint(ctx.coord()), ctx.pstctx.valDimtoDouble(ctx.bracketValueDim().valueDim()) * PSTContext.PPC,
			ctx.pstctx, true);
		shapes.add(circle);
	}

	@Override
	public void exitPstriangle(final net.sf.latexdraw.parsers.pst.PSTParser.PstriangleContext ctx) {
		final ITriangle triangle = ShapeFactory.INST.createTriangle();
		final Tuple<IPoint, IPoint> pts = getRectangularPoints(ctx.p1, ctx.p2, ctx.pstctx);
		setRectangularShape(triangle, pts.a.getX() - pts.b.getX() / 2d, pts.a.getY(), Math.abs(pts.b.getX()), Math.abs(pts.b.getY()), ctx.pstctx, ctx.cmd);

		if(!MathUtils.INST.equalsDouble(ctx.pstctx.gangle, 0d)) {
			final IPoint gc = triangle.getGravityCentre();
			final IPoint newGc = gc.rotatePoint(pts.a, Math.toRadians(-ctx.pstctx.gangle));
			triangle.setRotationAngle(triangle.getRotationAngle() + Math.toRadians(ctx.pstctx.gangle));
			triangle.translate(newGc.getX() - gc.getX(), newGc.getY() - gc.getY());
		}

		// If the height is negative, the position and the rotation of the triangle changes.
		if(pts.b.getY() > 0) {
			triangle.setRotationAngle(triangle.getRotationAngle() + Math.PI);
			triangle.translate(0, triangle.getHeight());
		}

		shapes.add(triangle);
	}

	@Override
	public void exitPspolygon(final net.sf.latexdraw.parsers.pst.PSTParser.PspolygonContext ctx) {
		Stream<IPoint> stream = ctx.ps.stream().map(node -> ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(node)));

		stream = Stream.concat(Stream.of(ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(ctx.p1))), stream);

		if(ctx.ps.size() == 1) {
			stream = Stream.concat(Stream.of(ShapeFactory.INST.createPoint(ctx.pstctx.originToPoint())), stream);
		}

		final IPolygon pol = ShapeFactory.INST.createPolygon(stream.collect(Collectors.toList()));

		setShapeParameters(pol, ctx.pstctx);

		if(ctx.pstctx.starredCmd(ctx.cmd)) {
			setShapeForStar(pol);
		}

		shapes.add(pol);
	}

	@Override
	public void exitPswedge(final net.sf.latexdraw.parsers.pst.PSTParser.PswedgeContext ctx) {
		final ICircleArc arc = ShapeFactory.INST.createCircleArc();
		setArc(arc, ArcStyle.WEDGE, ctx.pos, ctx.radius.valueDim(), ctx.angle1.valueDim(), ctx.angle2.valueDim(), ctx.pstctx, ctx.cmd);
		shapes.add(arc);
	}

	@Override
	public void exitPsarc(final net.sf.latexdraw.parsers.pst.PSTParser.PsarcContext ctx) {
		final ICircleArc arc = ShapeFactory.INST.createCircleArc();
		setArc(arc, ArcStyle.ARC, ctx.pos, ctx.radius.valueDim(), ctx.angle1.valueDim(), ctx.angle2.valueDim(), ctx.pstctx, ctx.cmd);
		shapes.add(arc);
	}

	@Override
	public void exitPsarcn(final net.sf.latexdraw.parsers.pst.PSTParser.PsarcnContext ctx) {
		final ICircleArc arc = ShapeFactory.INST.createCircleArc();
		ctx.pstctx.arrowLeft = ArrowStyle.getArrowStyle(ctx.pstctx.arrowLeft).getOppositeArrowStyle().getPSTToken();
		ctx.pstctx.arrowRight = ArrowStyle.getArrowStyle(ctx.pstctx.arrowRight).getOppositeArrowStyle().getPSTToken();
		setArc(arc, ArcStyle.ARC, ctx.pos, ctx.radius.valueDim(), ctx.angle2.valueDim(), ctx.angle1.valueDim(), ctx.pstctx, ctx.cmd);
		shapes.add(arc);
	}

	@Override
	public void exitPsbezier(final net.sf.latexdraw.parsers.pst.PSTParser.PsbezierContext ctx) {
		// Transforming all the parsed points
		Stream<IPoint> stream = IntStream.range(0, ctx.p1.size()).mapToObj(i -> Stream.of(ctx.p1.get(i), ctx.p2.get(i), ctx.p3.get(i))).
			flatMap(s -> s).map(pt -> ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(pt)));

		// Managing the optional last point
		if(ctx.p4 == null) {
			stream = Stream.concat(Stream.of(ShapeFactory.INST.createPoint(ctx.pstctx.originToPoint())), stream);
		}else {
			stream = Stream.concat(stream, Stream.of(ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(ctx.p4))));
		}

		final List<IPoint> allPts = stream.collect(Collectors.toList());
		// Point 1: shape point, point 2 : second control point (ignored), point 3 : first control point
		final List<IPoint> pts = IntStream.range(0, allPts.size()).filter(i -> i % 3 == 0).mapToObj(i -> allPts.get(i)).collect(Collectors.toList());
		final List<IPoint> ctrls = IntStream.range(2, allPts.size()).filter(i -> i % 3 == 2).mapToObj(i -> allPts.get(i)).collect(Collectors.toList());

		if(allPts.size() > 1) {
			ctrls.add(0, allPts.get(1));
		}

		boolean closed = false;

		// Closing the shape
		if(pts.size() > 2 && pts.get(0).equals(pts.get(pts.size() - 1))) {
			pts.remove(pts.size() - 1);
			ctrls.remove(ctrls.size() - 1);
			closed = true;
		}

		final IBezierCurve bc = ShapeFactory.INST.createBezierCurve(pts);
		bc.setIsClosed(closed);
		setShapeParameters(bc, ctx.pstctx);
		setArrows(bc, ctx.pstctx);

		// Setting the control points
		IntStream.range(0, bc.getFirstCtrlPts().size()).forEach(i -> bc.getFirstCtrlPtAt(i).setPoint(ctrls.get(i)));

		// Updating the second control points to the first ones
		bc.updateSecondControlPoints();

		if(ctx.pstctx.starredCmd(ctx.cmd)) {
			setShapeForStar(bc);
		}
		shapes.add(bc);
	}

	@Override
	public void exitPsaxes(final net.sf.latexdraw.parsers.pst.PSTParser.PsaxesContext ctx) {
		final IAxes axes = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		final IPoint gridstart;
		final IPoint gridend;

		if(ctx.p3 == null) {
			if(ctx.p2 == null) {
				if(ctx.p1 == null) {
					gridstart = ShapeFactory.INST.createPoint(Math.round(ctx.pstctx.pictureSWPt.getX()), Math.round(ctx.pstctx.pictureSWPt.getY()));
					gridend = ShapeFactory.INST.createPoint(Math.round(ctx.pstctx.pictureNEPt.getX()), Math.round(ctx.pstctx.pictureNEPt.getY()));
				}else {
					gridstart = ShapeFactory.INST.createPoint();
					gridend = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p1));
				}
			}else {
				gridstart = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p1));
				gridend = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p2));
			}
		}else {
			gridstart = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p2));
			gridend = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p3));
		}

		setArrows(axes, ctx.pstctx);
		setStdGridParams(ctx.pstctx.ox, ctx.pstctx.oy, axes, ctx.pstctx);
		setShapeParameters(axes, ctx.pstctx);
		axes.setAxesStyle(ctx.pstctx.axesStyle);
		axes.setTicksDisplayed(ctx.pstctx.ticks);
		axes.setLabelsDisplayed(ctx.pstctx.labels);
		axes.setTicksStyle(ctx.pstctx.ticksStyle);
		axes.setIncrementX(ctx.pstctx.dxIncrement);
		axes.setIncrementY(ctx.pstctx.dyIncrement);
		axes.setDistLabelsX(ctx.pstctx.dxLabelDist);
		axes.setDistLabelsY(ctx.pstctx.dyLabelDist);
		axes.setShowOrigin(ctx.pstctx.showOrigin);
		axes.setGridEndX(gridend.getX());
		axes.setGridEndY(gridend.getY());
		axes.setGridStartX(gridstart.getX());
		axes.setGridStartY(gridstart.getY());
		axes.setPosition(ShapeFactory.INST.createPoint(0d, 0d));
		shapes.add(axes);
	}

	@Override
	public void exitPsgrid(final net.sf.latexdraw.parsers.pst.PSTParser.PsgridContext ctx) {
		final IGrid grid = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		final IPoint gridStart;
		final IPoint gridEnd;
		final IPoint pos;
		boolean isGridXLabelInverted = false;
		boolean isGridYLabelInverted = false;

		if(ctx.p3 == null) {
			if(ctx.p2 == null) {
				if(ctx.p1 == null) {
					gridStart = ShapeFactory.INST.createPoint(Math.round(ctx.pstctx.pictureSWPt.getX()), Math.round(ctx.pstctx.pictureSWPt.getY()));
					gridEnd = ShapeFactory.INST.createPoint(Math.round(ctx.pstctx.pictureNEPt.getX()), Math.round(ctx.pstctx.pictureNEPt.getY()));
					pos = ShapeFactory.INST.createPoint();
					grid.setPosition(0d, 0d);
					grid.setLabelsSize(0);
				}else {
					pos = ShapeFactory.INST.createPoint();
					gridStart = ShapeFactory.INST.createPoint();
					gridEnd = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p1));
				}
			}else {
				pos = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p1));
				gridStart = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p1));
				gridEnd = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p2));
			}
		}else {
			pos = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p1));
			gridStart = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p2));
			gridEnd = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p3));
		}

		if(gridStart.getX() >= gridEnd.getX()) {
			final double tmp = gridEnd.getX();
			gridEnd.setX(gridStart.getX());
			gridStart.setX(tmp);
			isGridXLabelInverted = true;
		}

		if(gridStart.getY() >= gridEnd.getY()) {
			final double tmp = gridEnd.getY();
			gridEnd.setY(gridStart.getY());
			gridStart.setY(tmp);
			isGridYLabelInverted = true;
		}

		setStdGridParams(pos.getX(), pos.getY(), grid, ctx.pstctx);
		setShapeParameters(grid, ctx.pstctx);
		grid.setPosition(0d, 0d);
		grid.setUnit(ctx.pstctx.unit);
		grid.setGridDots((int) ctx.pstctx.gridDots);
		grid.setGridLabelsColour(ctx.pstctx.gridlabelcolor);
		grid.setLabelsSize((int) (ctx.pstctx.gridLabel * IShape.PPC));
		grid.setGridWidth(Math.abs(ctx.pstctx.gridWidth * IShape.PPC));
		grid.setSubGridColour(ctx.pstctx.subGridCol);
		grid.setSubGridDiv((int) ctx.pstctx.subGridDiv);
		grid.setSubGridDots((int) ctx.pstctx.subGridDots);
		grid.setSubGridWidth(Math.abs(ctx.pstctx.subGridWidth * IShape.PPC));
		grid.setLineColour(ctx.pstctx.gridColor);
		grid.setXLabelSouth(!isGridYLabelInverted);
		grid.setYLabelWest(!isGridXLabelInverted);
		grid.setGridEndX(gridEnd.getX());
		grid.setGridEndY(gridEnd.getY());
		grid.setGridStartX(gridStart.getX());
		grid.setGridStartY(gridStart.getY());
		shapes.add(grid);
	}

	@Override
	public void exitPsframebox(final net.sf.latexdraw.parsers.pst.PSTParser.PsframeboxContext ctx) {
	}

	@Override
	public void exitPsdblframebox(final net.sf.latexdraw.parsers.pst.PSTParser.PsdblframeboxContext ctx) {
	}

	@Override
	public void exitPsshadowbox(final net.sf.latexdraw.parsers.pst.PSTParser.PsshadowboxContext ctx) {
	}

	@Override
	public void exitPscirclebox(final net.sf.latexdraw.parsers.pst.PSTParser.PscircleboxContext ctx) {
	}

	@Override
	public void exitPsovalbox(final net.sf.latexdraw.parsers.pst.PSTParser.PsovalboxContext ctx) {
	}

	@Override
	public void exitPsdiabox(final net.sf.latexdraw.parsers.pst.PSTParser.PsdiaboxContext ctx) {
	}

	@Override
	public void exitPsplot(final net.sf.latexdraw.parsers.pst.PSTParser.PsplotContext ctx) {
	}

	@Override
	public void exitPscustom(final net.sf.latexdraw.parsers.pst.PSTParser.PscustomContext ctx) {
	}


	/**
	 * Sets the parameters of std grids (axes and grids).
	 */
	private void setStdGridParams(final double originX, final double originY, final IStandardGrid grid, final PSTContext ctx) {
		grid.setLineColour(ctx.gridColor);
		grid.setOriginX(originX);
		grid.setOriginY(originY);
	}


	/**
	 * Creates an arc using the given parameters.
	 */
	private void setArc(final ICircleArc arc, final ArcStyle arcType, final net.sf.latexdraw.parsers.pst.PSTParser.CoordContext posRaw,
						final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext radius, final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext a1,
						final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext a2, final PSTContext ctx, final Token cmd) {
		final Point2D centre = ctx.coordToAdjustedPoint(posRaw);
		final double angle1 = ctx.valDimtoDouble(a1);
		final double angle2 = ctx.valDimtoDouble(a2);
		final double width = Math.abs(ctx.valDimtoDouble(radius) * IShape.PPC) * 2d;

		arc.setAngleStart(Math.toRadians(angle1));
		arc.setAngleEnd(Math.toRadians(angle2));
		arc.setWidth(width);
		arc.setPosition(centre.getX() - width / 2d, centre.getY() + width / 2d);
		arc.setArcStyle(arcType);
		setArrows(arc, ctx);
		setShapeParameters(arc, ctx);

		if(ctx.starredCmd(cmd)) {
			setShapeForStar(arc);
		}
	}


	/**
	 * Initialises a circle.
	 */
	private void setCircle(final ICircle circle, final Point2D centre, final double radius, final PSTContext ctx, final boolean starred) {
		final double width = Math.max(0.1, radius) * 2d;
		circle.setWidth(width);
		circle.setPosition(centre.getX() - width / 2d, centre.getY() + width / 2d);
		setShapeParameters(circle, ctx);

		if(starred) {
			setShapeForStar(circle);
		}
	}


	/**
	 * Initialises a dot shape.
	 * @param pt The point of the dot.
	 * @param ctx The PST context.
	 * @param starred If a starred command.
	 */
	private void setDot(final IPoint pt, final PSTContext ctx, final boolean starred) {
		final IDot dot = ShapeFactory.INST.createDot(pt);
		final double dotSizeDim = ctx.arrowDotSize.a + ctx.arrowDotSize.b < 0d ? Math.abs(ctx.arrowDotSize.a) : ctx.arrowDotSize.a;
		final double dotSizeNum = ctx.arrowDotSize.a + ctx.arrowDotSize.b < 0d ? Math.abs(ctx.arrowDotSize.a) : ctx.arrowDotSize.b;

		dot.setDiametre((dotSizeDim + dotSizeNum * ctx.lineWidth) * IShape.PPC * ctx.dotScale.a);
		setShapeParameters(dot, ctx);
		dot.setRotationAngle(dot.getRotationAngle() + Math.toRadians(ctx.dotAngle));
		dot.setDotStyle(ctx.dotStyle);

		if(starred) {
			setShapeForStar(dot);
		}

		shapes.add(dot);
	}

	private Tuple<IPoint, IPoint> getRectangularPoints(final net.sf.latexdraw.parsers.pst.PSTParser.CoordContext c1,
													   final net.sf.latexdraw.parsers.pst.PSTParser.CoordContext c2, final PSTContext ctx) {
		final IPoint pt1;
		final IPoint pt2;

		if(c2 == null) {
			pt1 = ShapeFactory.INST.createPoint(ctx.originToPoint());
			pt2 = ShapeFactory.INST.createPoint(ctx.coordToAdjustedPoint(c1));
		}else {
			pt1 = ShapeFactory.INST.createPoint(ctx.coordToAdjustedPoint(c1));
			pt2 = ShapeFactory.INST.createPoint(ctx.coordToAdjustedPoint(c2));
		}

		return new Tuple<>(pt1, pt2);
	}

	private void setRectangularShape(final IRectangularShape sh, final double x, final double y, final double width, final double height,
									 final PSTContext ctx, final Token cmd) {
		sh.setPosition(x, y);
		sh.setWidth(Math.max(0.1, width));
		sh.setHeight(Math.max(0.1, height));
		setShapeParameters(sh, ctx);

		if(ctx.starredCmd(cmd)) {
			setShapeForStar(sh);
		}
	}


	/**
	 * Creates and initialises a polyline shape.
	 */
	private IPolyline createLine(final boolean hasStar, List<IPoint> points, final PSTContext ctx, final boolean qObject) {
		final IPolyline line = ShapeFactory.INST.createPolyline(points);

		setShapeParameters(line, ctx);
		setArrows(line, ctx);

		if(qObject) {
			line.setHasShadow(false);
			line.setHasDbleBord(false);
			line.setFillingStyle(FillingStyle.NONE);
			line.setArrowStyle(ArrowStyle.NONE, 0);
			line.setArrowStyle(ArrowStyle.NONE, -1);
		}

		if(hasStar) {
			setShapeForStar(line);
		}

		return line;
	}


	/**
	 * Configures the given shape to fit stared command (e.g. psellipse*).
	 */
	private void setShapeForStar(final IShape sh) {
		sh.setFillingStyle(FillingStyle.PLAIN);
		sh.setFillingCol(sh.getLineColour());
		sh.setBordersPosition(BorderPos.INTO);
		sh.setLineStyle(LineStyle.SOLID);
		sh.setHasShadow(false);
		sh.setHasDbleBord(false);
	}

	/**
	 * Sets the common shape's parameters.
	 */
	private void setShapeParameters(final IShape sh, final PSTContext ctx) {
		sh.setRotationAngle(ctx.rputAngle);

		if(ctx.strokeopacity < 1d) {
			sh.setLineColour(ShapeFactory.INST.createColor(ctx.lineColor.getR(), ctx.lineColor.getG(), ctx.lineColor.getB(), ctx.strokeopacity));
		}else {
			sh.setLineColour(ctx.lineColor);
		}

		if(sh.isThicknessable()) {
			sh.setThickness(ctx.lineWidth * IShape.PPC);
		}

		if(sh.isBordersMovable()) {
			sh.setBordersPosition(ctx.borderPos);
		}

		if(sh.isLineStylable()) {
			sh.setLineStyle(ctx.lineStyle);
		}

		if(sh.isDbleBorderable()) {
			sh.setHasDbleBord(ctx.dbleLine);
			sh.setDbleBordCol(ctx.dbleColor);
			sh.setDbleBordSep(ctx.dbleSep * IShape.PPC);
		}

		if(sh.isShadowable()) {
			sh.setHasShadow(ctx.shadow);
			sh.setShadowAngle(Math.toRadians(ctx.shadowAngle));
			sh.setShadowCol(ctx.shadowCol);
			sh.setShadowSize(ctx.shadowSize * IShape.PPC);
		}

		if(sh.isInteriorStylable()) {
			if(ctx.opacity < 1d) {
				sh.setFillingCol(ShapeFactory.INST.createColor(ctx.fillColor.getR(), ctx.fillColor.getG(), ctx.fillColor.getB(), ctx.opacity));
			}else {
				sh.setFillingCol(ctx.fillColor);
			}

			sh.setFillingStyle(ctx.fillingStyle);
			sh.setGradAngle(Math.toRadians(ctx.gradAngle));
			sh.setGradColEnd(ctx.gradEnd);
			sh.setGradColStart(ctx.gradBegin);
			sh.setGradMidPt(ctx.gradMidPoint);
			sh.setHatchingsAngle(Math.toRadians(ctx.hatchAngle));
			sh.setHatchingsCol(ctx.hatchCol);
			sh.setHatchingsSep(ctx.hatchSep * IShape.PPC);
			sh.setHatchingsWidth(ctx.hatchWidth * IShape.PPC);
		}
	}

	/**
	 * Sets the arrows' parameters.
	 */
	private void setArrows(final IArrowableSingleShape sh, final PSTContext ctx) {
		sh.setArrowSizeDim(ctx.arrowSize.a * IShape.PPC);
		sh.setArrowSizeNum(ctx.arrowSize.b);
		sh.setArrowLength(ctx.arrowLgth);
		sh.setArrowInset(ctx.arrowInset);
		sh.setTBarSizeDim(ctx.arrowTBar.a * IShape.PPC);
		sh.setTBarSizeNum(ctx.arrowTBar.b);
		sh.setBracketNum(ctx.arrowBrLgth);
		sh.setRBracketNum(ctx.arrowrBrLgth);
		sh.setArrowStyle(ArrowStyle.getArrowStyle(ctx.arrowLeft), 0);
		sh.setArrowStyle(ArrowStyle.getArrowStyle(ctx.arrowRight), 1);
	}

	public List<IShape> getShapes() {
		return shapes;
	}
}
