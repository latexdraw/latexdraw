package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.util.Objects;

import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewArrow;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a view of an arrow.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 08/03/2011<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LArrowView implements IViewArrow {
	/** The arrow model to view. */
	protected IArrow model;

	/** The path of the view. */
	protected Path2D path;


	/**
	 * Creates the view of the arrow.
	 * @param model The model of the arrow.
	 * @throws IllegalArgumentException If the given arrow is null.
	 * @since 3.0
	 */
	protected LArrowView(final IArrow model) {
		super();
		this.model = Objects.requireNonNull(model);
		path	   = new Path2D.Double();
	}


	@Override
	public void paint(final Graphics2D g, final Color fColour, final boolean asShadow) {
		if(!model.hasStyle()) return ;
		final ILine arrowLine 	= model.getArrowLine();
		if(arrowLine==null) return;
		final IPoint pt1 		= arrowLine.getPoint1();
		final double lineAngle	= arrowLine.getLineAngle();
		final double lineB	  	= arrowLine.getB();
		double c2x, c2y, c3x, c3y;

		if(LNumber.INSTANCE.equals(Math.abs(lineAngle), Math.PI/2.)) {
			final double cx = pt1.getX();
			final double cy = pt1.getY();
			c2x = Math.cos(lineAngle)*cx - Math.sin(lineAngle)*cy;
			c2y = Math.sin(lineAngle)*cx + Math.cos(lineAngle)*cy;
			c3x = Math.cos(-lineAngle)*(cx-c2x) - Math.sin(-lineAngle)*(cy-c2y);
			c3y = Math.sin(-lineAngle)*(cx-c2x) + Math.cos(-lineAngle)*(cy-c2y);
		}
		else {
			c2x  = -Math.sin(lineAngle)*lineB;
			c2y  = Math.cos(lineAngle)*lineB;
			c3x  = Math.cos(-lineAngle)*-c2x - Math.sin(-lineAngle)*(lineB-c2y);
			c3y  = Math.sin(-lineAngle)*-c2x + Math.cos(-lineAngle)*(lineB-c2y);
		}

		if(!LNumber.INSTANCE.equals(lineAngle%(Math.PI*2.),0.)) {
			g.rotate(lineAngle);
			g.translate(c3x,c3y);
		}

		paintArrow(g, fColour, asShadow);

		if(!LNumber.INSTANCE.equals(lineAngle%(Math.PI*2.),0.)) {
			g.translate(-c3x,-c3y);
			g.rotate(-lineAngle);
		}
	}


	protected void paintCircle(final Graphics2D g, final Color fillColour, final Color lineColour) {
		g.setColor(fillColour);
		g.fill(path);
		g.setColor(lineColour);
		g.draw(path);
	}


	protected void paintDisk(final Graphics2D g, final Color lineColour) {
		g.setColor(lineColour);
		g.fill(path);
		g.draw(path);
	}


	protected void paintRoundBracket(final Graphics2D g, final Color lineColor) {
		g.setColor(lineColor);
		g.setStroke(new BasicStroke((float)model.getShape().getThickness(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		g.draw(path);
	}


	protected void paintBarBracket(final Graphics2D g, final Color lineColor) {
		g.setStroke(new BasicStroke((float)model.getShape().getFullThickness(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		g.setColor(lineColor);
		g.draw(path);
	}


	protected void paintArrow(final Graphics2D g, final Color lineColor) {
		g.setColor(lineColor);
		g.fill(path);// FIXME to see if double arrow same than in v2
	}

	protected void paintArrow(final Graphics2D g, final Color fColour, final boolean asShadow) {
		final IShape shape	  = model.getShape();
		final Color lineColor = asShadow ? shape.getShadowCol() : shape.getLineColour();
		final Color fillColor = asShadow ? shape.getShadowCol() : fColour;

		switch(model.getArrowStyle()) {
			case LEFT_DBLE_ARROW		:
			case RIGHT_DBLE_ARROW		:
			case RIGHT_ARROW			:
			case LEFT_ARROW				: paintArrow(g, lineColor); break;
			case CIRCLE_END				:
			case CIRCLE_IN				: paintCircle(g, fillColor, lineColor); break;
			case DISK_END				:
			case DISK_IN				: paintDisk(g, lineColor); break;
			case LEFT_ROUND_BRACKET		:
			case RIGHT_ROUND_BRACKET	: paintRoundBracket(g, lineColor); break;
			case BAR_END				:
			case BAR_IN					:
			case LEFT_SQUARE_BRACKET	:
			case RIGHT_SQUARE_BRACKET	: paintBarBracket(g, lineColor); break;
			case ROUND_IN				:
			case ROUND_END				:
				g.setColor(lineColor);
				g.setStroke(new BasicStroke((float)model.getShape().getFullThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
				g.draw(path);
				break;
			case SQUARE_END				:
				g.setColor(lineColor);
				g.setStroke(new BasicStroke((float)model.getShape().getFullThickness(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
				g.draw(path);
				break;
			case NONE: break;
		}
	}


	protected void updatePathDiskCircleEnd(final double xRot, final double yRot) {
		final double lineWidth	 = model.getShape().getThickness();
		final double arrowRadius = model.getRoundShapedArrowRadius();
		LEllipseView.setEllipsePath(path, xRot - arrowRadius+lineWidth/2., yRot - arrowRadius+lineWidth/2., arrowRadius*2.-lineWidth, arrowRadius*2.-lineWidth);
	}



	protected void updatePathDiskCircleIn(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final double arrowRadius = model.getRoundShapedArrowRadius();
		final double lineWidth	 = model.getShape().getThickness();
		double x			 	 = xRot+lineWidth/2.;

		if(!isArrowInPositiveDirection(pt1, pt2))
			x -=2.*arrowRadius;

		LEllipseView.setEllipsePath(path, x, yRot-arrowRadius+lineWidth/2., arrowRadius*2.-lineWidth, arrowRadius*2.-lineWidth);
	}



	protected void updatePathRightLeftSquaredBracket(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final boolean invert	= model.isInverted();
		final double[] xs 		= new double[2];
		final double[] ys 		= new double[2];
		final double lineWidth	= model.getShape().getFullThickness();
		double lgth 			= model.getBracketShapedArrowLength()+model.getShape().getFullThickness()/2.;
		double x				= xRot;

		if((!isArrowInPositiveDirection(pt1, pt2) || invert) && (isArrowInPositiveDirection(pt1, pt2) || !invert))
			lgth *= -1.;

		updatePathBarIn(x, yRot, pt1, pt2, xs, ys);

		final double x3 = xs[0]+lgth;
		final double x4 = xs[1]+lgth;

		path.moveTo(xs[0], ys[0]+lineWidth/2.);
		path.lineTo(x3, ys[0]+lineWidth/2.);
		path.moveTo(xs[1], ys[1]-lineWidth/2.);
		path.lineTo(x4, ys[1]-lineWidth/2.);
	}


	protected void updatePathBarIn(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2, final double[] xs, final double[] ys) {
		final double width 		= model.getBarShapedArrowWidth();
		final double lineWidth	= model.getShape().getThickness();
		final double dec		= isArrowInPositiveDirection(pt1, pt2) ? lineWidth/2. : -lineWidth/2.;
		xs[0] = xRot+dec;
		xs[1] = xRot+dec;
		ys[0] = yRot-width/2.;
		ys[1] = yRot+width/2.;

		path.moveTo(xs[0], ys[0]);
		path.lineTo(xs[1], ys[1]);
	}



	protected void updatePathBarEnd(final double xRot, final double yRot) {
		final double width = model.getBarShapedArrowWidth();

		path.moveTo(xRot, yRot-width/2.);
		path.lineTo(xRot, yRot+width/2.);
	}


	private void updatePathArrow(final double x1, final double y1, final double x2, final double y2,
								 final double x3, final double y3, final double x4, final double y4) {
		path.moveTo(x1, y1);
		path.lineTo(x2, y2);
		path.lineTo(x3, y3);
		path.lineTo(x4, y4);
		path.closePath();
	}


	protected void updatePathRightLeftArrow(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final boolean invert= model.isInverted();
		final double width  = model.getArrowShapedWidth();
		double length 		= model.getArrowLength()*width;
		double inset  		= model.getArrowInset()*length;
		double x			= xRot;

		if(invert)
			x += isArrowInPositiveDirection(pt1, pt2) ? length : -length;

		if((!isArrowInPositiveDirection(pt1, pt2) || invert) && (isArrowInPositiveDirection(pt1, pt2) || !invert)) {
			length *= -1.;
			inset *= -1.;
		}

		updatePathArrow(x, yRot, x+length, yRot-width/2., x+length-inset, yRot, x+length, yRot+width/2.);
	}



	private boolean isArrowInPositiveDirection(final IPoint pt1, final IPoint pt2) {
		return pt1.getX()<pt2.getX() || (pt1.getX()==pt2.getX() && pt1.getY()<pt2.getY());
	}


	protected void updatePathRoundLeftRightBracket(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final boolean invert	= model.isInverted();
		final double width 		= model.getBarShapedArrowWidth();
		final double lgth  		= model.getRBracketNum()*width;
		final double xarc  		= model.isInverted() ? xRot : xRot+model.getShape().getThickness()/2.;
		final double widtharc 	= lgth*2. + (invert ? model.getShape().getThickness()/2. : 0.);
		Shape s = new Arc2D.Double(xarc, yRot-width/2., widtharc, width, 130, 100, Arc2D.OPEN);

		if((!isArrowInPositiveDirection(pt1, pt2) || invert) && (isArrowInPositiveDirection(pt1, pt2) || !invert)) {
			double cx = xRot, cy = yRot;
			double rotX = Math.cos(Math.PI)*cx - Math.sin(Math.PI)*cy;
			double rotY = Math.sin(Math.PI)*cx + Math.cos(Math.PI)*cy;

			AffineTransform at = AffineTransform.getTranslateInstance(cx-rotX, cy-rotY);
			at.rotate(Math.PI);
			s = at.createTransformedShape(s);
		}

		path.append(s, false);
	}



	protected void updatePathDoubleLeftRightArrow(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final boolean invert= model.isInverted();
		final double width  = model.getArrowShapedWidth();
		double length = model.getArrowLength()*width;
		double inset  = model.getArrowInset()*length;
		double x = xRot;

		if(invert)
			x += isArrowInPositiveDirection(pt1, pt2) ? 2.*length : -2.*length;

		if((!isArrowInPositiveDirection(pt1, pt2) || invert) && (isArrowInPositiveDirection(pt1, pt2) || !invert)) {
			length *= -1.;
			inset *= -1.;
		}

		updatePathArrow(x, yRot, x+length, yRot-width/2., x+length-inset, yRot, x+length, yRot+width/2.);
		updatePathArrow(x+length, yRot, x+2.*length, yRot-width/2., x+2.*length-inset, yRot, x+2.*length, yRot+width/2.);
		final double x2 	= x+length-inset;
		final double x2bis 	= x+2.*length-inset;

		path.lineTo(x2, yRot);
		path.moveTo(x2bis, yRot);
	}



	protected void updatePathSquareRoundEnd(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		path.lineTo(pt1.getX()<pt2.getX() ? xRot+1. : xRot-1., yRot);
		path.moveTo(xRot, yRot);
	}



	protected void updatePathRoundIn(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final double lineWidth 	= isArrowInPositiveDirection(pt1, pt2) ? model.getShape().getFullThickness() : -model.getShape().getFullThickness();
		final double x 			= xRot+lineWidth/2.;

		path.moveTo(x, yRot);
		path.lineTo(x, yRot);
	}


	/**
	 * Updates the path of the arrow.
	 * @since 3.0
	 */
	@Override
	public void updatePath() {
		path.reset();

		if(model.getArrowStyle()==ArrowStyle.NONE || model.getShape().getNbPoints()<2) return;
		final ILine arrowLine 	= model.getArrowLine();
		if(arrowLine==null) return;
		double xRot, yRot;
		final double lineAngle	= arrowLine.getLineAngle();
		final IPoint pt1 		= arrowLine.getPoint1();
		final IPoint pt2 		= arrowLine.getPoint2();
		final double lineB	  	= arrowLine.getB();

		if(LNumber.INSTANCE.equals(Math.abs(lineAngle), Math.PI/2.) || LNumber.INSTANCE.equals(Math.abs(lineAngle), 0.)) {
			xRot = pt1.getX();
			yRot = pt1.getY();
		} else {
			xRot = Math.cos(-lineAngle)*pt1.getX()-Math.sin(-lineAngle)*(pt1.getY()-lineB);
			yRot = Math.sin(-lineAngle)*pt1.getX()+Math.cos(-lineAngle)*(pt1.getY()-lineB)+lineB;
		}

		switch(model.getArrowStyle()) {
			case BAR_END			: updatePathBarEnd(xRot, yRot); break;
			case BAR_IN				: updatePathBarIn(xRot, yRot, pt1, pt2, new double[2], new double[2]); break;
			case CIRCLE_END			:
			case DISK_END			: updatePathDiskCircleEnd(xRot, yRot); break;
			case CIRCLE_IN			:
			case DISK_IN			: updatePathDiskCircleIn(xRot, yRot, pt1, pt2); break;
			case RIGHT_ARROW		:
			case LEFT_ARROW			: updatePathRightLeftArrow(xRot, yRot, pt1, pt2); break;
			case RIGHT_DBLE_ARROW	:
			case LEFT_DBLE_ARROW	: updatePathDoubleLeftRightArrow(xRot, yRot, pt1, pt2); break;
			case RIGHT_ROUND_BRACKET:
			case LEFT_ROUND_BRACKET	: updatePathRoundLeftRightBracket(xRot, yRot, pt1, pt2); break;
			case LEFT_SQUARE_BRACKET:
			case RIGHT_SQUARE_BRACKET:updatePathRightLeftSquaredBracket(xRot, yRot, pt1, pt2); break;
			case SQUARE_END			:
			case ROUND_END			: updatePathSquareRoundEnd(xRot, yRot, pt1, pt2); break;
			case ROUND_IN			: updatePathRoundIn(xRot, yRot, pt1, pt2); break;
			case NONE				: break;
		}
	}


	@Override
	public IArrow getModel() {
		return model;
	}


	@Override
	public Path2D getPath() {
		return path;
	}
}
