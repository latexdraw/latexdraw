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

import java.util.Objects;
import javafx.scene.Group;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * The JFX view of an arrow.
 * @author Arnaud Blouin
 */
public class ViewArrow extends Group {
	final IArrow arrow;
	final Path path;
	final Group additionalShapes;

	/**
	 * Creates the view.
	 * @param model The arrow. Cannot be null.
	 * @throws NullPointerException if the given arrow is null.
	 */
	ViewArrow(final IArrow model) {
		super();
		arrow = Objects.requireNonNull(model);
		path = new Path();
		additionalShapes = new Group();
		getChildren().add(path);
		getChildren().add(additionalShapes);
	}


//	public void paint(final Color fColour, final boolean asShadow) {
//		if(!arrow.hasStyle()) return;
//
//		final ILine arrowLine = arrow.getArrowLine();
//		if(arrowLine == null) return;
//
//		final IPoint pt1 = arrowLine.getPoint1();
//		final double lineAngle = arrowLine.getLineAngle();
//		final double lineB = arrowLine.getB();
//		final double c2x;
//		final double c2y;
//		final double c3x;
//		final double c3y;
//
//		if(LNumber.equalsDouble(Math.abs(lineAngle), Math.PI / 2.)) {
//			final double cx = pt1.getX();
//			final double cy = pt1.getY();
//			c2x = Math.cos(lineAngle) * cx - Math.sin(lineAngle) * cy;
//			c2y = Math.sin(lineAngle) * cx + Math.cos(lineAngle) * cy;
//			c3x = Math.cos(-lineAngle) * (cx - c2x) - Math.sin(-lineAngle) * (cy - c2y);
//			c3y = Math.sin(-lineAngle) * (cx - c2x) + Math.cos(-lineAngle) * (cy - c2y);
//		}else {
//			c2x = -Math.sin(lineAngle) * lineB;
//			c2y = Math.cos(lineAngle) * lineB;
//			c3x = Math.cos(-lineAngle) * -c2x - Math.sin(-lineAngle) * (lineB - c2y);
//			c3y = Math.sin(-lineAngle) * -c2x + Math.cos(-lineAngle) * (lineB - c2y);
//		}
//
//		if(!LNumber.equalsDouble(lineAngle % (Math.PI * 2.), 0.)) {
//			g.rotate(lineAngle);
//			g.translate(c3x, c3y);
//		}
//
//		paintArrow(fColour, asShadow);
//
//		if(!LNumber.equalsDouble(lineAngle % (Math.PI * 2.), 0.)) {
//			g.translate(-c3x, -c3y);
//			g.rotate(-lineAngle);
//		}
//	}
//
//
//	protected void paintCircle(final Graphics2D g, final Color fillColour, final Color lineColour) {
//		g.setColor(fillColour);
//		g.fill(path);
//		g.setColor(lineColour);
//		g.draw(path);
//	}
//
//
//	protected void paintDisk(final Graphics2D g, final Color lineColour) {
//		g.setColor(lineColour);
//		g.setStroke(new BasicStroke((float) model.getShape().getThickness(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
//		g.fill(path);
//		g.draw(path);
//	}
//
//
//	protected void paintRoundBracket(final Graphics2D g, final Color lineColor) {
//		g.setColor(lineColor);
//		g.setStroke(new BasicStroke((float) model.getShape().getThickness(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
//		g.draw(path);
//	}
//
//
//	protected void paintBarBracket(final Graphics2D g, final Color lineColor) {
//		g.setStroke(new BasicStroke((float) model.getShape().getFullThickness(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
//		g.setColor(lineColor);
//		g.draw(path);
//	}
//
//
//	protected void paintArrow(final Color lineColor) {
//		g.setColor(lineColor);
//		g.fill(path);
//	}
//
//	protected void paintArrow(final Graphics2D g, final Color fColour, final boolean asShadow) {
//		final IShape shape = model.getShape();
//		final Color lineColor = asShadow ? shape.getShadowCol() : shape.getLineColour();
//		final Color fillColor = asShadow ? shape.getShadowCol() : fColour;
//
//		switch(model.getArrowStyle()) {
//			case LEFT_DBLE_ARROW:
//			case RIGHT_DBLE_ARROW:
//			case RIGHT_ARROW:
//			case LEFT_ARROW:
//				paintArrow(g, lineColor);
//				break;
//			case CIRCLE_END:
//			case CIRCLE_IN:
//				paintCircle(g, fillColor, lineColor);
//				break;
//			case DISK_END:
//			case DISK_IN:
//				paintDisk(g, lineColor);
//				break;
//			case LEFT_ROUND_BRACKET:
//			case RIGHT_ROUND_BRACKET:
//				paintRoundBracket(g, lineColor);
//				break;
//			case BAR_END:
//			case BAR_IN:
//			case LEFT_SQUARE_BRACKET:
//			case RIGHT_SQUARE_BRACKET:
//				paintBarBracket(g, lineColor);
//				break;
//			case ROUND_IN:
//			case ROUND_END:
//				g.setColor(lineColor);
//				g.setStroke(new BasicStroke((float) model.getShape().getFullThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
//				g.draw(path);
//				break;
//			case SQUARE_END:
//				g.setColor(lineColor);
//				g.setStroke(new BasicStroke((float) model.getShape().getFullThickness(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
//				g.draw(path);
//				break;
//			case NONE:
//				break;
//		}
//	}


	private void updatePathDiskCircleEnd(final double xRot, final double yRot) {
		final double lineWidth = arrow.getShape().getFullThickness();
		final double arrowRadius = arrow.getRoundShapedArrowRadius();
		final Ellipse ell = new Ellipse(arrowRadius * 2d - lineWidth, arrowRadius * 2d - lineWidth);
		ell.setLayoutX(xRot - arrowRadius + lineWidth / 2d);
		ell.setLayoutY(yRot - arrowRadius + lineWidth / 2d);
		additionalShapes.getChildren().add(ell);
	}


	private void updatePathDiskCircleIn(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final double arrowRadius = arrow.getRoundShapedArrowRadius();
		final double lineWidth = arrow.getShape().getFullThickness();
		double x = xRot + lineWidth / 2d;

		if(!isArrowInPositiveDirection(pt1, pt2)) {
			x -= 2d * arrowRadius;
		}

		final Ellipse ell = new Ellipse(arrowRadius * 2d - lineWidth, arrowRadius * 2d - lineWidth);
		ell.setLayoutX(x);
		ell.setLayoutY(yRot - arrowRadius + lineWidth / 2d);
		additionalShapes.getChildren().add(ell);
	}


	private void updatePathRightLeftSquaredBracket(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final boolean invert = arrow.isInverted();
		final double[] xs = new double[2];
		final double[] ys = new double[2];
		final double lineWidth = arrow.getShape().getFullThickness();
		double lgth = arrow.getBracketShapedArrowLength() + arrow.getShape().getFullThickness() / 2d;

		if((!isArrowInPositiveDirection(pt1, pt2) || invert) && (isArrowInPositiveDirection(pt1, pt2) || !invert)) {
			lgth *= -1d;
		}

		updatePathBarIn(xRot, yRot, pt1, pt2, xs, ys);

		final double x3 = xs[0] + lgth;
		final double x4 = xs[1] + lgth;

		path.getElements().add(new MoveTo(xs[0], ys[0] + lineWidth / 2d));
		path.getElements().add(new LineTo(x3, ys[0] + lineWidth / 2d));
		path.getElements().add(new MoveTo(xs[1], ys[1] - lineWidth / 2d));
		path.getElements().add(new LineTo(x4, ys[1] - lineWidth / 2d));
	}


	private void updatePathBarIn(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2, final double[] xs, final double[] ys) {
		final double width = arrow.getBarShapedArrowWidth();
		final double lineWidth = arrow.getShape().getThickness();
		final double dec = isArrowInPositiveDirection(pt1, pt2) ? lineWidth / 2d : -lineWidth / 2d;
		xs[0] = xRot + dec;
		xs[1] = xRot + dec;
		ys[0] = yRot - width / 2d;
		ys[1] = yRot + width / 2d;

		path.getElements().add(new MoveTo(xs[0], ys[0]));
		path.getElements().add(new LineTo(xs[1], ys[1]));
	}


	private void updatePathBarEnd(final double xRot, final double yRot) {
		final double width = arrow.getBarShapedArrowWidth();

		path.getElements().add(new MoveTo(xRot, yRot - width / 2d));
		path.getElements().add(new LineTo(xRot, yRot + width / 2d));
	}


	private void updatePathArrow(final double x1, final double y1, final double x2, final double y2, final double x3, final double y3, final double x4, final double y4) {
		path.getElements().add(new MoveTo(x1, y1));
		path.getElements().add(new LineTo(x2, y2));
		path.getElements().add(new LineTo(x3, y3));
		path.getElements().add(new LineTo(x4, y4));
		path.getElements().add(new ClosePath());
	}


	private void updatePathRightLeftArrow(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final boolean invert = arrow.isInverted();
		final double width = arrow.getArrowShapedWidth();
		double length = arrow.getArrowLength() * width;
		double inset = arrow.getArrowInset() * length;
		double x = xRot;

		if(invert) {
			x += isArrowInPositiveDirection(pt1, pt2) ? length : -length;
		}

		if((!isArrowInPositiveDirection(pt1, pt2) || invert) && (isArrowInPositiveDirection(pt1, pt2) || !invert)) {
			length *= -1d;
			inset *= -1d;
		}

		updatePathArrow(x, yRot, x + length, yRot - width / 2., x + length - inset, yRot, x + length, yRot + width / 2d);
	}


	private boolean isArrowInPositiveDirection(final IPoint pt1, final IPoint pt2) {
		return pt1.getX() < pt2.getX() || pt1.getX() == pt2.getX() && pt1.getY() < pt2.getY();
	}


	private void updatePathRoundLeftRightBracket(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final boolean invert = arrow.isInverted();
		final double width = arrow.getBarShapedArrowWidth();
		final double lgth = arrow.getRBracketNum() * width;
		final double xarc = arrow.isInverted() ? xRot : xRot + arrow.getShape().getThickness() / 2d;
		final double widtharc = lgth * 2d + (invert ? arrow.getShape().getThickness() / 2d : 0d);
		final Arc arc = new Arc(xarc, yRot - width / 2d, widtharc, width, 130d, 100d);

		if((!isArrowInPositiveDirection(pt1, pt2) || invert) && (isArrowInPositiveDirection(pt1, pt2) || !invert)) {
			final double rotX = Math.cos(Math.PI) * xRot - Math.sin(Math.PI) * yRot;
			final double rotY = Math.sin(Math.PI) * xRot + Math.cos(Math.PI) * yRot;

			arc.setTranslateX(xRot - rotX);
			arc.setTranslateY(yRot - rotY);
			arc.setRotate(Math.PI);
		}

		additionalShapes.getChildren().add(arc);
	}


	private void updatePathDoubleLeftRightArrow(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final boolean invert = arrow.isInverted();
		final double width = arrow.getArrowShapedWidth();
		double length = arrow.getArrowLength() * width;
		double inset = arrow.getArrowInset() * length;
		double x = xRot;

		if(invert) {
			x += isArrowInPositiveDirection(pt1, pt2) ? 2d * length : -2d * length;
		}

		if((!isArrowInPositiveDirection(pt1, pt2) || invert) && (isArrowInPositiveDirection(pt1, pt2) || !invert)) {
			length *= -1d;
			inset *= -1d;
		}

		updatePathArrow(x, yRot, x + length, yRot - width / 2d, x + length - inset, yRot, x + length, yRot + width / 2d);
		updatePathArrow(x + length, yRot, x + 2d * length, yRot - width / 2d, x + 2d * length - inset, yRot, x + 2d * length, yRot + width / 2d);
		final double x2 = x + length - inset;
		final double x2bis = x + 2d * length - inset;

		path.getElements().add(new LineTo(x2, yRot));
		path.getElements().add(new MoveTo(x2bis, yRot));
	}


	private void updatePathSquareRoundEnd(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		path.getElements().add(new LineTo(pt1.getX() < pt2.getX() ? xRot + 1d : xRot - 1d, yRot));
		path.getElements().add(new MoveTo(xRot, yRot));
	}


	private void updatePathRoundIn(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final double lineWidth = isArrowInPositiveDirection(pt1, pt2) ? arrow.getShape().getFullThickness() : -arrow.getShape().getFullThickness();
		final double x = xRot + lineWidth / 2d;

		path.getElements().add(new MoveTo(x, yRot));
		path.getElements().add(new LineTo(x, yRot));
	}


	public void updatePath() {
		path.getElements().clear();
		additionalShapes.getChildren().clear();

		final ILine arrowLine = arrow.getArrowLine();

		if(arrow.getArrowStyle() == ArrowStyle.NONE || arrowLine == null) {
			return;
		}

		if(!arrow.hasStyle()) return;

		final double xRot;
		final double yRot;
		final double lineAngle = arrowLine.getLineAngle();
		final IPoint pt1 = arrowLine.getPoint1();
		final IPoint pt2 = arrowLine.getPoint2();
		final double lineB = arrowLine.getB();
		final double c2x;
		final double c2y;
		final double c3x;
		final double c3y;

		if(MathUtils.INST.equalsDouble(Math.abs(lineAngle), Math.PI / 2d)) {
			final double cx = pt1.getX();
			final double cy = pt1.getY();
			xRot = cx;
			yRot = cy;
			c2x = Math.cos(lineAngle) * cx - Math.sin(lineAngle) * cy;
			c2y = Math.sin(lineAngle) * cx + Math.cos(lineAngle) * cy;
			c3x = Math.cos(-lineAngle) * (cx - c2x) - Math.sin(-lineAngle) * (cy - c2y);
			c3y = Math.sin(-lineAngle) * (cx - c2x) + Math.cos(-lineAngle) * (cy - c2y);
		}else {
			c2x = -Math.sin(lineAngle) * lineB;
			c2y = Math.cos(lineAngle) * lineB;
			c3x = Math.cos(-lineAngle) * -c2x - Math.sin(-lineAngle) * (lineB - c2y);
			c3y = Math.sin(-lineAngle) * -c2x + Math.cos(-lineAngle) * (lineB - c2y);
			xRot = Math.cos(-lineAngle) * pt1.getX() - Math.sin(-lineAngle) * (pt1.getY() - lineB);
			yRot = Math.sin(-lineAngle) * pt1.getX() + Math.cos(-lineAngle) * (pt1.getY() - lineB) + lineB;
		}

		switch(arrow.getArrowStyle()) {
			case BAR_END:
				updatePathBarEnd(xRot, yRot);
				break;
			case BAR_IN:
				updatePathBarIn(xRot, yRot, pt1, pt2, new double[2], new double[2]);
				break;
			case CIRCLE_END:
			case DISK_END:
				updatePathDiskCircleEnd(xRot, yRot);
				break;
			case CIRCLE_IN:
			case DISK_IN:
				updatePathDiskCircleIn(xRot, yRot, pt1, pt2);
				break;
			case RIGHT_ARROW:
			case LEFT_ARROW:
				updatePathRightLeftArrow(xRot, yRot, pt1, pt2);
				break;
			case RIGHT_DBLE_ARROW:
			case LEFT_DBLE_ARROW:
				updatePathDoubleLeftRightArrow(xRot, yRot, pt1, pt2);
				break;
			case RIGHT_ROUND_BRACKET:
			case LEFT_ROUND_BRACKET:
				updatePathRoundLeftRightBracket(xRot, yRot, pt1, pt2);
				break;
			case LEFT_SQUARE_BRACKET:
			case RIGHT_SQUARE_BRACKET:
				updatePathRightLeftSquaredBracket(xRot, yRot, pt1, pt2);
				break;
			case SQUARE_END:
			case ROUND_END:
				updatePathSquareRoundEnd(xRot, yRot, pt1, pt2);
				break;
			case ROUND_IN:
				updatePathRoundIn(xRot, yRot, pt1, pt2);
				break;
			case NONE:
				break;
		}

		if(!MathUtils.INST.equalsDouble(lineAngle % (Math.PI * 2d), 0d)) {
			path.setRotate(Math.toDegrees(lineAngle));
//			path.setTranslateX(c3x);
//			path.setTranslateY(c3y);
			additionalShapes.setRotate(Math.toDegrees(lineAngle));
//			additionalShapes.setTranslateX(c3x);
//			additionalShapes.setTranslateY(c3y);
		}
	}


	public void flush() {
		getChildren().clear();
	}
}
