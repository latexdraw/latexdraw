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

import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
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
			stream = Streams.concat(Stream.of(ShapeFactory.INST.createPoint(ctx.pstctx.originToPoint())), stream);
		}

		shapes.add(createLine(starredCmd(ctx.cmd), stream.collect(Collectors.toList()), ctx.pstctx, false));
	}

	@Override
	public void exitPsframe(final net.sf.latexdraw.parsers.pst.PSTParser.PsframeContext ctx) {
		final IRectangle rec = ShapeFactory.INST.createRectangle();
		rec.setLineArc(ctx.pstctx.frameArc);
		setRectangularShape(rec, ctx.p1, ctx.p2, ctx.pstctx, ctx.cmd);
		shapes.add(rec);
	}


	private void setRectangularShape(final IRectangularShape sh, final net.sf.latexdraw.parsers.pst.PSTParser.CoordContext c1, final net.sf.latexdraw.parsers.pst.PSTParser.CoordContext c2, final PSTContext ctx,
									 final Token cmd) {
		final IPoint pt1;
		final IPoint pt2;

		if(c2 == null) {
			pt1 = ShapeFactory.INST.createPoint(ctx.originToPoint());
			pt2 = coordToPoint(c1, ctx);
		}else {
			pt1 = coordToPoint(c1, ctx);
			pt2 = coordToPoint(c2, ctx);
		}

		// The x-coordinates of pt1 must be lower than pt2 one.
		if(pt1.getX() > pt2.getX()) {
			final double tmp = pt1.getX();
			pt1.setX(pt2.getX());
			pt2.setX(tmp);
		}

		// The y-coordinates of pt1 must be lower than pt2 one.
		if(pt1.getY() < pt2.getY()) {
			final double tmp = pt1.getY();
			pt1.setY(pt2.getY());
			pt2.setY(tmp);
		}

		sh.setPosition(pt1.getX(), pt1.getY());
		sh.setWidth(Math.max(0.1, Math.abs(pt2.getX() - pt1.getX())));
		sh.setHeight(Math.max(0.1, Math.abs(pt2.getY() - pt1.getY())));
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
