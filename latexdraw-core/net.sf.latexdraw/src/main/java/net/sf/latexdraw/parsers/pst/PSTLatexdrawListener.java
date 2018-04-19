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
package net.sf.latexdraw.parsers.pst;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.geometry.Point2D;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.antlr.v4.runtime.Token;

public class PSTLatexdrawListener extends PSTCtxListener {
	private final Deque<IGroup> shapes;
	Point2D psCustomLatestPt;

	public PSTLatexdrawListener() {
		super();
		shapes = new ArrayDeque<>();
		PSTContext.PPC = IShape.PPC;
		psCustomLatestPt = new Point2D(0d, 0d);
	}

	@Override
	public void enterPstCode(final net.sf.latexdraw.parsers.pst.PSTParser.PstCodeContext ctx) {
		addGroup();
	}

	@Override
	public void enterPstBlock(final net.sf.latexdraw.parsers.pst.PSTParser.PstBlockContext ctx) {
		addGroup();
	}

	@Override
	public void exitPstBlock(final net.sf.latexdraw.parsers.pst.PSTParser.PstBlockContext ctx) {
		flatLastGroup();
		addParsedText(ctx.pstctx);
	}

	@Override
	public void exitPstCode(final net.sf.latexdraw.parsers.pst.PSTParser.PstCodeContext ctx) {
		addParsedText(ctx.pstctx);
		flatLastGroup();
	}

	private void addGroup() {
		shapes.push(ShapeFactory.INST.createGroup());
	}

	private void flatLastGroup() {
		if(shapes.size() > 1) {
			final IGroup last = shapes.pop();
			shapes.peek().getShapes().addAll(last.getShapes());
			last.clear();
		}
	}

	private void addParsedText(final PSTContext ctx) {
		if(!ctx.textParsed.isEmpty()) {
			final String txt = ctx.textParsed.stream().collect(Collectors.joining(" "));
			final IText text = ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(), txt);
			setShapeParameters(text, ctx);
			text.setLineColour(ctx.textColor);
			text.setTextPosition(TextPosition.getTextPosition(ctx.textPosition));
			shapes.peek().addShape(text);
		}
	}

	@Override
	public void exitPsline(final net.sf.latexdraw.parsers.pst.PSTParser.PslineContext ctx) {
		Stream<IPoint> stream = ctx.pts.stream().map(node -> ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(node)));

		if(ctx.pts.size() == 1) {
			stream = Stream.concat(Stream.of(ShapeFactory.INST.createPoint(ctx.pstctx.originToPoint())), stream);
		}

		shapes.peek().addShape(createLine(ctx.pstctx.starredCmd(ctx.cmd), stream.collect(Collectors.toList()), ctx.pstctx, false));
	}

	@Override
	public void exitPsqline(final net.sf.latexdraw.parsers.pst.PSTParser.PsqlineContext ctx) {
		shapes.peek().addShape(createLine(false, Arrays.asList(ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(ctx.p1)),
			ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(ctx.p2))), ctx.pstctx, true));
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
		shapes.peek().addShape(rec);
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
		shapes.peek().addShape(ell);
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

		shapes.peek().addShape(rhombus);
	}

	@Override
	public void exitPscircle(final net.sf.latexdraw.parsers.pst.PSTParser.PscircleContext ctx) {
		final ICircle circle = ShapeFactory.INST.createCircle();
		setCircle(circle, ctx.pstctx.coordToAdjustedPoint(ctx.centre), ctx.pstctx.valDimtoDouble(ctx.bracketValueDim().valueDim()) * PSTContext.PPC,
			ctx.pstctx, ctx.pstctx.starredCmd(ctx.cmd));
		shapes.peek().addShape(circle);
	}

	@Override
	public void exitPsqdisk(final net.sf.latexdraw.parsers.pst.PSTParser.PsqdiskContext ctx) {
		final ICircle circle = ShapeFactory.INST.createCircle();
		setCircle(circle, ctx.pstctx.coordToAdjustedPoint(ctx.coord()), ctx.pstctx.valDimtoDouble(ctx.bracketValueDim().valueDim()) * PSTContext.PPC,
			ctx.pstctx, true);
		shapes.peek().addShape(circle);
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

		shapes.peek().addShape(triangle);
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

		shapes.peek().addShape(pol);
	}

	@Override
	public void exitPswedge(final net.sf.latexdraw.parsers.pst.PSTParser.PswedgeContext ctx) {
		final ICircleArc arc = ShapeFactory.INST.createCircleArc();
		setArc(arc, ArcStyle.WEDGE, ctx.pos, ctx.radius.valueDim(), ctx.angle1.valueDim(), ctx.angle2.valueDim(), ctx.pstctx, ctx.cmd);
		shapes.peek().addShape(arc);
	}

	@Override
	public void exitPsarc(final net.sf.latexdraw.parsers.pst.PSTParser.PsarcContext ctx) {
		final ICircleArc arc = ShapeFactory.INST.createCircleArc();
		setArc(arc, ArcStyle.ARC, ctx.pos, ctx.radius.valueDim(), ctx.angle1.valueDim(), ctx.angle2.valueDim(), ctx.pstctx, ctx.cmd);
		shapes.peek().addShape(arc);
	}

	@Override
	public void exitPsarcn(final net.sf.latexdraw.parsers.pst.PSTParser.PsarcnContext ctx) {
		final ICircleArc arc = ShapeFactory.INST.createCircleArc();
		ctx.pstctx.arrowLeft = ArrowStyle.getArrowStyle(ctx.pstctx.arrowLeft).getOppositeArrowStyle().getPSTToken();
		ctx.pstctx.arrowRight = ArrowStyle.getArrowStyle(ctx.pstctx.arrowRight).getOppositeArrowStyle().getPSTToken();
		setArc(arc, ArcStyle.ARC, ctx.pos, ctx.radius.valueDim(), ctx.angle2.valueDim(), ctx.angle1.valueDim(), ctx.pstctx, ctx.cmd);
		shapes.peek().addShape(arc);
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
		bc.setOpened(!closed);
		setShapeParameters(bc, ctx.pstctx);
		setArrows(bc, ctx.pstctx);

		// Setting the control points
		IntStream.range(0, bc.getFirstCtrlPts().size()).forEach(i -> bc.getFirstCtrlPtAt(i).setPoint(ctrls.get(i)));

		// Updating the second control points to the first ones
		bc.updateSecondControlPoints();

		if(ctx.pstctx.starredCmd(ctx.cmd)) {
			setShapeForStar(bc);
		}
		shapes.peek().addShape(bc);
	}

	@Override
	public void exitPsaxes(final net.sf.latexdraw.parsers.pst.PSTParser.PsaxesContext ctx) {
		final IAxes axes = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		final IPoint gridstart;
		final IPoint gridend;
		final IPoint position;

		if(ctx.p3 == null) {
			if(ctx.p2 == null) {
				position = ShapeFactory.INST.createPoint();
				gridstart = ShapeFactory.INST.createPoint();
				gridend = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p1));
			}else {
				position = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p1).multiply(IShape.PPC));
				gridstart = ShapeFactory.INST.createPoint();
				gridend = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p2).subtract(ctx.pstctx.coordToRawPoint(ctx.p1)));
			}
		}else {
			position = ShapeFactory.INST.createPoint();
			gridstart = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p2));
			gridend = ShapeFactory.INST.createPoint(ctx.pstctx.coordToRawPoint(ctx.p3));
		}

		setArrows(axes, ctx.pstctx);
		setStdGridParams(ctx.pstctx.ox, ctx.pstctx.oy, axes, ctx.pstctx);
		setShapeParameters(axes, ctx.pstctx);
		axes.setAxesStyle(AxesStyle.getStyle(ctx.pstctx.axesStyle));
		axes.setTicksDisplayed(PlottingStyle.getStyle(ctx.pstctx.ticks));
		axes.setLabelsDisplayed(PlottingStyle.getStyle(ctx.pstctx.labels));
		axes.setTicksStyle(TicksStyle.getStyle(ctx.pstctx.ticksStyle));
		axes.setIncrementX(ctx.pstctx.dxIncrement);
		axes.setIncrementY(ctx.pstctx.dyIncrement);
		axes.setDistLabelsX(ctx.pstctx.dxLabelDist);
		axes.setDistLabelsY(ctx.pstctx.dyLabelDist);
		axes.setShowOrigin(ctx.pstctx.showOrigin);
		axes.setGridEndX(gridend.getX());
		axes.setGridEndY(gridend.getY());
		axes.setGridStartX(gridstart.getX());
		axes.setGridStartY(gridstart.getY());
		axes.setPosition(position);
		shapes.peek().addShape(axes);
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
		shapes.peek().addShape(grid);
	}

	@Override
	public void exitClosepath(final net.sf.latexdraw.parsers.pst.PSTParser.ClosepathContext ctx) {
		final IFreehand fh = ShapeFactory.INST.createFreeHand(Collections.emptyList());
		fh.setOpened(false);
		shapes.peek().addShape(fh);
	}

	@Override
	public void exitCurveto(final net.sf.latexdraw.parsers.pst.PSTParser.CurvetoContext ctx) {
		shapes.peek().addShape(createFreeHand(false, ctx.pstctx, ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(ctx.p3))));
	}

	@Override
	public void exitLineto(final net.sf.latexdraw.parsers.pst.PSTParser.LinetoContext ctx) {
		shapes.peek().addShape(createFreeHand(true, ctx.pstctx, ShapeFactory.INST.createPoint(ctx.pstctx.coordToAdjustedPoint(ctx.coord()))));
	}

	@Override
	public void exitMoveto(final net.sf.latexdraw.parsers.pst.PSTParser.MovetoContext ctx) {
		psCustomLatestPt = ctx.pstctx.coordToAdjustedPoint(ctx.coord());
	}

	@Override
	public void enterPscustom(final net.sf.latexdraw.parsers.pst.PSTParser.PscustomContext ctx) {
		shapes.peek().addShape(ShapeFactory.INST.createGroup());
	}

	@Override
	public void exitPscustom(final net.sf.latexdraw.parsers.pst.PSTParser.PscustomContext ctx) {
		final IGroup customshapes = shapes.peek();

		if(ctx.pstctx.starredCmd(ctx.cmd)) {
			setShapeForStar(customshapes);
		}

		IFreehand fh = null;
		final IGroup gp = ShapeFactory.INST.createGroup();
		// The different created freehand shapes must be merged into a single one.
		for(final IShape sh : customshapes.getShapes()) {
			if(sh instanceof IFreehand) {
				final IFreehand ifh = (IFreehand) sh;
				if(fh == null) {
					gp.addShape(ifh);
					fh = ifh;
					fh.setInterval(1); // This shape is now the reference shape used for the merge.
				}else {
					if(ifh.getNbPoints() == 0) {
						// If the shape has a single point, it means it is a closepath command
						fh.setOpened(ifh.isOpened());
					}else {
						// Otherwise, the shape has two points. So, we take the last one and add it to the first shape.
						final IFreehand fh2 = ShapeFactory.INST.createFreeHandFrom(fh, ifh.getPtAt(ifh.getNbPoints() - 1));
						gp.getShapes().set(gp.getShapes().indexOf(fh), fh2);
						fh = fh2;
						fh.setType(ifh.getType());
					}
				}
			}
		}

		customshapes.getShapes().setAll(gp.getShapes());
		gp.getShapes().clear();
		psCustomLatestPt = new Point2D(0d, 0d);
	}

	@Override
	public void exitPsframebox(final net.sf.latexdraw.parsers.pst.PSTParser.PsframeboxContext ctx) {
		ctx.pstctx.textParsed.add(ctx.getText());
	}

	@Override
	public void exitPsdblframebox(final net.sf.latexdraw.parsers.pst.PSTParser.PsdblframeboxContext ctx) {
		ctx.pstctx.textParsed.add(ctx.getText());
	}

	@Override
	public void exitPsshadowbox(final net.sf.latexdraw.parsers.pst.PSTParser.PsshadowboxContext ctx) {
		ctx.pstctx.textParsed.add(ctx.getText());
	}

	@Override
	public void exitPscirclebox(final net.sf.latexdraw.parsers.pst.PSTParser.PscircleboxContext ctx) {
		ctx.pstctx.textParsed.add(ctx.getText());
	}

	@Override
	public void exitPsovalbox(final net.sf.latexdraw.parsers.pst.PSTParser.PsovalboxContext ctx) {
		ctx.pstctx.textParsed.add(ctx.getText());
	}

	@Override
	public void exitPsdiabox(final net.sf.latexdraw.parsers.pst.PSTParser.PsdiaboxContext ctx) {
		ctx.pstctx.textParsed.add(ctx.getText());
	}

	@Override
	public void exitPsplot(final net.sf.latexdraw.parsers.pst.PSTParser.PsplotContext ctx) {
		final double v1 = ctx.pstctx.numberToDouble(ctx.x0);
		final double v2 = ctx.pstctx.numberToDouble(ctx.x1);
		final String fct = ctx.fct.stream().map(elt -> elt.getText()).collect(Collectors.joining(" "));
		final IPlot plot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), v1 < v2 ? v1 : v2, v1 < v2 ? v2 : v1, fct, ctx.pstctx.polarPlot);
		final double dotSizeDim = ctx.pstctx.arrowDotSize.a + ctx.pstctx.arrowDotSize.b < 0d ? Math.abs(ctx.pstctx.arrowDotSize.a) : ctx.pstctx.arrowDotSize.a;
		final double dotSizeNum = ctx.pstctx.arrowDotSize.b + ctx.pstctx.arrowDotSize.b < 0d ? Math.abs(ctx.pstctx.arrowDotSize.b) : ctx.pstctx.arrowDotSize.b;

		setShapeParameters(plot, ctx.pstctx);
		plot.setNbPlottedPoints(ctx.pstctx.plotPoints);
		plot.setPlotStyle(PlotStyle.getPlotStyle(ctx.pstctx.plotStyle));
		plot.setXScale(ctx.pstctx.xUnit);
		plot.setYScale(ctx.pstctx.yUnit);
		plot.setDiametre((dotSizeDim + dotSizeNum * ctx.pstctx.lineWidth) * IShape.PPC * ctx.pstctx.dotScale.a);
		plot.setDotStyle(DotStyle.getStyle(ctx.pstctx.dotStyle));

		if(ctx.pstctx.starredCmd(ctx.cmd)) {
			setShapeForStar(plot);
		}

		shapes.peek().addShape(plot);
	}

	@Override
	public void exitTextcolor(final net.sf.latexdraw.parsers.pst.PSTParser.TextcolorContext ctx) {
		DviPsColors.INSTANCE.getColour(ctx.name.getText()).ifPresent(colour -> shapes.peek().setLineColour(colour));
	}

	@Override
	public void exitColor(final net.sf.latexdraw.parsers.pst.PSTParser.ColorContext ctx) {
		DviPsColors.INSTANCE.getColour(ctx.name.getText()).ifPresent(colour -> ctx.pstctx.textColor = colour);
	}

	@Override
	public void exitRput(final net.sf.latexdraw.parsers.pst.PSTParser.RputContext ctx) {
		final Point2D coord = ctx.pstctx.coordToAdjustedPoint(ctx.coord());
		shapes.peek().translate(coord.getX(), coord.getY());
	}

	@Override
	public void exitText(final net.sf.latexdraw.parsers.pst.PSTParser.TextContext ctx) {
		ctx.pstctx.textParsed.add(ctx.getText());
	}

	@Override
	public void exitPstribox(final net.sf.latexdraw.parsers.pst.PSTParser.PstriboxContext ctx) {
		ctx.pstctx.textParsed.add(ctx.getText());
	}

	@Override
	public void exitDefinecolor(final net.sf.latexdraw.parsers.pst.PSTParser.DefinecolorContext ctx) {
		Color colour = null;

		switch(ctx.colortype.getText()) {
			case "rgb":
				if(ctx.NUMBER().size() == 3) {
					colour = ShapeFactory.INST.createColor(ctx.pstctx.numberToDouble(ctx.NUMBER(0).getSymbol()),
								ctx.pstctx.numberToDouble(ctx.NUMBER(1).getSymbol()),
								ctx.pstctx.numberToDouble(ctx.NUMBER(2).getSymbol()));
				}
				break;
			case "RGB":
				if(ctx.NUMBER().size() == 3) {
					colour = DviPsColors.INSTANCE.convertRGB2rgb(ctx.pstctx.numberToDouble(ctx.NUMBER(0).getSymbol()),
								ctx.pstctx.numberToDouble(ctx.NUMBER(1).getSymbol()),
								ctx.pstctx.numberToDouble(ctx.NUMBER(2).getSymbol()));
				}
				break;
			case "gray":
				colour = DviPsColors.INSTANCE.convertgray2rgb(ctx.pstctx.numberToDouble(ctx.NUMBER(0).getSymbol()));
				break;
			case "HTML":
				colour = DviPsColors.INSTANCE.convertHTML2rgb(ctx.HEXA().getText());
				break;
			case "cmyk":
				if(ctx.NUMBER().size() == 4) {
					colour = DviPsColors.INSTANCE.convertcmyk2rgb(ctx.pstctx.numberToDouble(ctx.NUMBER(0).getSymbol()),
						ctx.pstctx.numberToDouble(ctx.NUMBER(1).getSymbol()),
						ctx.pstctx.numberToDouble(ctx.NUMBER(2).getSymbol()),
						ctx.pstctx.numberToDouble(ctx.NUMBER(3).getSymbol()));
				}
				break;
			case "cmy":
				if(ctx.NUMBER().size() == 3) {
					colour = ShapeFactory.INST.createColor(1d - ctx.pstctx.numberToDouble(ctx.NUMBER(0).getSymbol()),
						1d - ctx.pstctx.numberToDouble(ctx.NUMBER(1).getSymbol()),
						1d - ctx.pstctx.numberToDouble(ctx.NUMBER(2).getSymbol()));
				}
				break;
			case "hsb":
				if(ctx.NUMBER().size() == 3) {
					colour = ShapeFactory.INST.createColorHSB(ctx.pstctx.numberToDouble(ctx.NUMBER(0).getSymbol()),
						ctx.pstctx.numberToDouble(ctx.NUMBER(1).getSymbol()),
						ctx.pstctx.numberToDouble(ctx.NUMBER(2).getSymbol()));
				}
				break;
		}

		if(colour != null) {
			DviPsColors.INSTANCE.addUserColour(colour, ctx.name.getText());
		}
	}

	@Override
	public void exitUnknowncmds(final net.sf.latexdraw.parsers.pst.PSTParser.UnknowncmdsContext ctx) {
		ctx.pstctx.textParsed.add(ctx.getText());
	}

	private IFreehand createFreeHand(final boolean isLine, final PSTContext ctx, final IPoint pt) {
		final IFreehand freeHand = ShapeFactory.INST.createFreeHand(Arrays.asList(ShapeFactory.INST.createPoint(psCustomLatestPt), pt));

		if(isLine) {
			freeHand.setType(FreeHandStyle.LINES);
		}else {
			freeHand.setType(FreeHandStyle.CURVES);
		}

		setShapeParameters(freeHand, ctx);
		psCustomLatestPt = new Point2D(pt.getX(), pt.getY());
		return freeHand;
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
		dot.setDotStyle(DotStyle.getStyle(ctx.dotStyle));

		if(starred) {
			setShapeForStar(dot);
		}

		shapes.peek().addShape(dot);
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
	private IPolyline createLine(final boolean hasStar, final List<IPoint> points, final PSTContext ctx, final boolean qObject) {
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
			sh.setBordersPosition(BorderPos.getStyle(ctx.dimen));
		}

		if(sh.isLineStylable()) {
			sh.setLineStyle(LineStyle.getStyle(ctx.lineStyle));
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

			sh.setFillingStyle(FillingStyle.getStyleFromLatex(ctx.fillingStyle));
			sh.setGradAngle(Math.toRadians(ctx.gradAngle));
			sh.setGradColEnd(ctx.gradEnd);
			sh.setGradColStart(ctx.gradBegin);
			sh.setGradMidPt(ctx.gradMidPoint);
			sh.setHatchingsAngle(Math.toRadians(ctx.hatchAngle));
			sh.setHatchingsCol(ctx.hatchCol);
			sh.setHatchingsSep(ctx.hatchSep * IShape.PPC);
			sh.setHatchingsWidth(ctx.hatchWidth * IShape.PPC);
		}

		if(sh.isShowPtsable()) {
			sh.setShowPts(ctx.showPoints);
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
		return shapes.isEmpty() ? Collections.emptyList() : shapes.peek().getShapes();
	}
}
