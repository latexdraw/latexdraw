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
import java.util.stream.Stream;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.util.Tuple;
import org.antlr.v4.runtime.Token;

public class PSTLatexdrawListener extends PSTCtxListener {
	private final List<IShape> shapes;

	public PSTLatexdrawListener() {
		super();
		shapes = new ArrayList<>();
	}

	@Override
	double getPPC() {
		return IShape.PPC;
	}

	@Override
	public void exitPsline(final net.sf.latexdraw.parsers.pst.PSTParser.PslineContext ctx) {
		Stream<IPoint> stream = ctx.pts.stream().map(node -> coordToPoint(node, ctx.pstctx));

		if(ctx.pts.size() == 1) {
			stream = Stream.concat(Stream.of(ShapeFactory.INST.createPoint(ctx.pstctx.originToPoint())), stream);
		}

		shapes.add(createLine(starredCmd(ctx.cmd), stream.collect(Collectors.toList()), ctx.pstctx, false));
	}

	@Override
	public void exitPsqline(final net.sf.latexdraw.parsers.pst.PSTParser.PsqlineContext ctx) {
		shapes.add(createLine(false, Arrays.asList(coordToPoint(ctx.p1, ctx.pstctx), coordToPoint(ctx.p2, ctx.pstctx)),
			ctx.pstctx, false));
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
		setDot(coordToPoint(ctx.pt, ctx.pstctx), ctx.pstctx, starredCmd(ctx.cmd));
	}

	@Override
	public void exitPsdots(final net.sf.latexdraw.parsers.pst.PSTParser.PsdotsContext ctx) {
		ctx.pts.forEach(pt -> setDot(coordToPoint(pt, ctx.pstctx), ctx.pstctx, starredCmd(ctx.cmd)));
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
	}

	@Override
	public void exitPscircle(final net.sf.latexdraw.parsers.pst.PSTParser.PscircleContext ctx) {
		final ICircle circle = ShapeFactory.INST.createCircle();
		setCircle(circle, coordToPoint(ctx.centre, ctx.pstctx), valDimtoDouble(ctx.bracketValueDim().valueDim()) * getPPC(), ctx.pstctx, starredCmd(ctx.cmd));
		shapes.add(circle);
	}

	@Override
	public void exitPsqdisk(final net.sf.latexdraw.parsers.pst.PSTParser.PsqdiskContext ctx) {
		final ICircle circle = ShapeFactory.INST.createCircle();
		setCircle(circle, coordToPoint(ctx.coord(), ctx.pstctx), valDimtoDouble(ctx.bracketValueDim().valueDim()) * getPPC(), ctx.pstctx, true);
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
		Stream<IPoint> stream = ctx.ps.stream().map(node -> coordToPoint(node, ctx.pstctx));

		stream = Stream.concat(Stream.of(coordToPoint(ctx.p1, ctx.pstctx)), stream);

		if(ctx.ps.size() == 1) {
			stream = Stream.concat(Stream.of(ShapeFactory.INST.createPoint(ctx.pstctx.originToPoint())), stream);
		}

		final IPolygon pol = ShapeFactory.INST.createPolygon(stream.collect(Collectors.toList()));

		setShapeParameters(pol, ctx.pstctx);

		if(starredCmd(ctx.cmd)) {
			setShapeForStar(pol);
		}

		shapes.add(pol);
	}

	@Override
	public void exitPsbezier(final net.sf.latexdraw.parsers.pst.PSTParser.PsbezierContext ctx) {
	}

	@Override
	public void exitPsaxes(final net.sf.latexdraw.parsers.pst.PSTParser.PsaxesContext ctx) {
	}

	@Override
	public void exitPsgrid(final net.sf.latexdraw.parsers.pst.PSTParser.PsgridContext ctx) {
	}

	@Override
	public void exitPsarc(final net.sf.latexdraw.parsers.pst.PSTParser.PsarcContext ctx) {
	}

	@Override
	public void exitPscurve(final net.sf.latexdraw.parsers.pst.PSTParser.PscurveContext ctx) {
	}

	@Override
	public void exitPsecurve(final net.sf.latexdraw.parsers.pst.PSTParser.PsecurveContext ctx) {
	}

	@Override
	public void exitPsccurve(final net.sf.latexdraw.parsers.pst.PSTParser.PsccurveContext ctx) {
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

	@Override
	public void exitPspictureBlock(final net.sf.latexdraw.parsers.pst.PSTParser.PspictureBlockContext ctx) {
	}


	/**
	 * Initialises a circle.
	 */
	private void setCircle(final ICircle circle, final IPoint centre, final double radius, final PSTContext ctx, final boolean starred) {
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
			pt2 = coordToPoint(c1, ctx);
		}else {
			pt1 = coordToPoint(c1, ctx);
			pt2 = coordToPoint(c2, ctx);
		}

		return new Tuple<>(pt1, pt2);
	}

	private void setRectangularShape(final IRectangularShape sh, final double x, final double y, final double width, final double height,
									 final PSTContext ctx, final Token cmd) {
		sh.setPosition(x, y);
		sh.setWidth(Math.max(0.1, width));
		sh.setHeight(Math.max(0.1, height));
		setShapeParameters(sh, ctx);

		if(starredCmd(cmd)) {
			setShapeForStar(sh);
		}
	}


	/**
	 * Creates and initialises a polyline shape.
	 */
	private IPolyline createLine(final boolean hasStar, List<IPoint> points, final PSTContext ctx, final boolean qObject) {
		final IPolyline line = ShapeFactory.INST.createPolyline(points);

		setShapeParameters(line, ctx);
		setArrows(line, false, ctx);

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

	private IPoint coordToPoint(final net.sf.latexdraw.parsers.pst.PSTParser.CoordContext coord, final PSTContext ctx) {
		if(coord == null) return ShapeFactory.INST.createPoint(
			PSTContext.doubleUnitToUnit(ctx.originX.a, ctx.originX.b), PSTContext.doubleUnitToUnit(ctx.originY.a, ctx.originY.b));
		return ShapeFactory.INST.createPoint(fromXvalDimToCoord(coord.x, ctx), fromYvalDimToCoord(coord.y, ctx));
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
	private void setArrows(final IArrowableSingleShape sh, final boolean invert, final PSTContext ctx) {
		sh.setArrowSizeDim(ctx.arrowSize.a * IShape.PPC);
		sh.setArrowSizeNum(ctx.arrowSize.b);
		sh.setArrowLength(ctx.arrowLgth);
		sh.setArrowInset(ctx.arrowInset);
		sh.setTBarSizeDim(ctx.arrowTBar.a * IShape.PPC);
		sh.setTBarSizeNum(ctx.arrowTBar.b);
		sh.setBracketNum(ctx.arrowBrLgth);
		sh.setRBracketNum(ctx.arrowrBrLgth);

		if(invert) {
			sh.setArrowStyle(ArrowStyle.getArrowStyle(ctx.arrowRight), 0);
			sh.setArrowStyle(ArrowStyle.getArrowStyle(ctx.arrowLeft), 1);
		}else {
			sh.setArrowStyle(ArrowStyle.getArrowStyle(ctx.arrowLeft), 0);
			sh.setArrowStyle(ArrowStyle.getArrowStyle(ctx.arrowRight), 1);
		}
	}

	public List<IShape> getShapes() {
		return shapes;
	}
}
