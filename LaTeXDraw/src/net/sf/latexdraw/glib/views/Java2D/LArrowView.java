package net.sf.latexdraw.glib.views.Java2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a view of an arrow.<br>
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
 * 08/03/2011<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public class LArrowView {
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
	public LArrowView(final IArrow model) {
		super();

		if(model==null)
			throw new IllegalArgumentException();

		this.model = model;
		path	   = new Path2D.Double();
	}


	public void paint(final Graphics2D g, final Color fColour, final boolean asShadow) {
		if(model.getArrowStyle()==ArrowStyle.NONE) return ;

		final ILine arrowLine 	= model.getArrowLine();
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
		g.setStroke(new BasicStroke((float)model.getShape().getThickness(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		g.setColor(lineColor);
		g.draw(path);//FIXME in v2, the stroke was changed there for brackets.
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
			case BAR_END				:
				g.setColor(lineColor);
				g.draw(path);
				break;
			case CIRCLE_END				:
			case CIRCLE_IN				: paintCircle(g, fillColor, lineColor); break;
			case DISK_END				:
			case DISK_IN				: paintDisk(g, lineColor); break;
			case LEFT_ROUND_BRACKET		:
			case RIGHT_ROUND_BRACKET	: paintRoundBracket(g, lineColor); break;
			case BAR_IN					:
			case LEFT_SQUARE_BRACKET	:
			case RIGHT_SQUARE_BRACKET	: paintBarBracket(g, lineColor); break;
			case ROUND_IN				:
			case ROUND_END				:
				g.setColor(lineColor);
				g.setStroke(new BasicStroke((float)model.getShape().getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
				g.draw(path);
				break;
			case SQUARE_END				:
				g.setColor(lineColor);
				g.setStroke(new BasicStroke((float)model.getShape().getThickness(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
				g.draw(path);
				break;
			case NONE: break;
		}
	}


	protected void updatePathDiskCircleEnd(final double xRot, final double yRot) {
		final double lineWidth	 = model.getShape().getThickness();
		final double arrowRadius = model.getRoundShapedArrowRadius();
		LEllipseView.setEllipsePath(path, xRot - arrowRadius+lineWidth/2., yRot - arrowRadius+lineWidth/2., arrowRadius*2-lineWidth, arrowRadius*2-lineWidth);
	}



	protected void updatePathDiskCircleIn(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final double arrowRadius = model.getRoundShapedArrowRadius();
		final double lineWidth	 = model.getShape().getThickness();
		final double x;

		if(pt1.getX()<pt2.getX()) x = xRot+lineWidth/2.;
		else x = xRot-2*arrowRadius+lineWidth/2.;

		LEllipseView.setEllipsePath(path, x, yRot-arrowRadius+lineWidth/2., arrowRadius*2-lineWidth, arrowRadius*2-lineWidth);
	}



	protected void updatePathRightLeftSquaredBracket(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2, final boolean isLeft) {
		final double[] xs = new double[2];
		final double[] ys = new double[2];
		final double lineWidth	= model.getShape().getThickness();
		final double lgth = model.getBracketShapedArrowLength();
		final double x3;
		final double x4;

		updatePathBarIn(xRot, yRot, pt1, pt2, xs, ys);

		if((pt1.getX()<pt2.getX() && isLeft) || (pt1.getX()>=pt2.getX() && !isLeft)) {
			x3 = xs[0]+lgth;
			x4 = xs[1]+lgth;
		}else {
			x3 = xs[0]-lgth;
			x4 = xs[1]-lgth;
		}

		path.moveTo(xs[0], ys[0]+lineWidth/2.);
		path.lineTo(x3, ys[0]+lineWidth/2.);
		path.moveTo(xs[1], ys[1]-lineWidth/2.);
		path.lineTo(x4, ys[1]-lineWidth/2.);
	}



	protected void updatePathBarIn(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2, final double[] xs, final double[] ys) {
		final double width 		= model.getBarShapedArrowWidth();
		final double lineWidth	= model.getShape().getThickness();
		xs[0] = xRot;
		xs[1] = xRot;
		ys[0] = yRot-width/2.;
		ys[1] = yRot+width/2.;

		if(pt1.getX()<pt2.getX()) {
			xs[0] += lineWidth/2.;
			xs[1] += lineWidth/2.;
		} else {
			xs[0] -= lineWidth/2.;
			xs[1] -= lineWidth/2.;
		}

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


	protected void updatePathRightLeftArrow(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2, final boolean isLeft) {
		final double width  = model.getArrowShapedWidth();
		final double length = model.getArrowLength()*width;
		final double inset  = model.getArrowInset()*length;

		if(isLeft)
			if(pt1.getX()<pt2.getX())
				updatePathArrow(xRot, yRot, xRot+length, yRot-width/2., xRot+length-inset, yRot, xRot+length, yRot+width/2.);
			else
				updatePathArrow(xRot, yRot, xRot-length, yRot-width/2., xRot-length+inset, yRot, xRot-length, yRot+width/2.);
		else
			if(pt1.getX()<pt2.getX())
				updatePathArrow(xRot+length, yRot, xRot, yRot-width/2., xRot+inset, yRot, xRot, yRot+width/2.);
			else
				updatePathArrow(xRot-length, yRot, xRot, yRot-width/2., xRot-inset, yRot, xRot, yRot+width/2.);
	}



//	protected void updatePathRoundLeftRightBracket(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2, final boolean isLeft) {
//		final double width 		= model.getBarShapedArrowWidth();
//		final double lgth  		= model.getRBracketNum()*width;
//		final double xarc  		= model.isInverted() ? xRot : xRot+model.getShape().getThickness()/2.;//FIXME isInverted.
//		final double widtharc 	= lgth*2 + (model.isInverted() ? model.getShape().getThickness()/2. : 0.);
//		Shape s = new Arc2D.Double(xarc, yRot-width/2., widtharc, width, 130, 100, Arc2D.OPEN);

		//FIXME
//		if( (position==pt1 && pt1.x<pt2.x && currentArrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE)) ||
//			(position==pt1 && pt1.x>=pt2.x && currentArrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE)) ||
//			(position==pt2 && pt2.x<=pt1.x && currentArrowStyle.equals(PSTricksConstants.LRBRACKET_STYLE)) ||
//			(position==pt2 && pt2.x>pt1.x && currentArrowStyle.equals(PSTricksConstants.RRBRACKET_STYLE)))
//		{
//			double cx = xRot, cy = yRot;
//			double rotX = Math.cos(Math.PI)*cx - Math.sin(Math.PI)*cy;
//			double rotY = Math.sin(Math.PI)*cx + Math.cos(Math.PI)*cy;
//
//			AffineTransform at = AffineTransform.getTranslateInstance(cx-rotX, cy-rotY);
//			at.rotate(Math.PI);
//			s = at.createTransformedShape(s);
//		}
//	}



	protected void updatePathDoubleLeftRightArrow(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2, final boolean isLeft) {
		final double width  = model.getArrowShapedWidth();
		final double length = model.getArrowLength()*width;
		final double inset  = model.getArrowInset()*length;
		final double x2;
		final double x2bis;

		if(isLeft)
			if(pt1.getX()<pt2.getX()) {
				updatePathArrow(xRot, yRot, xRot+length, yRot-width/2., xRot+length-inset, yRot, xRot+length, yRot+width/2.);
				updatePathArrow(xRot+length, yRot, xRot+2*length, yRot-width/2., xRot+2*length-inset, yRot, xRot+2*length, yRot+width/2.);
				x2 	  = xRot+length-inset;
				x2bis = xRot+2*length-inset;
			}else{
				updatePathArrow(xRot, yRot, xRot-length, yRot-width/2., xRot-length+inset, yRot, xRot-length, yRot+width/2.);
				updatePathArrow(xRot-length, yRot, xRot-2*length, yRot-width/2., xRot-2*length+inset, yRot, xRot-2*length, yRot+width/2.);
				x2 	  = xRot-length+inset;
				x2bis = xRot-2*length+inset;
			}
		else
			if(pt1.getX()<pt2.getX()) {
				updatePathArrow(xRot+length, yRot, xRot, yRot-width/2., xRot+inset, yRot, xRot, yRot+width/2.);
				updatePathArrow(xRot+2*length, yRot, xRot+length, yRot-width/2., xRot+length+inset, yRot, xRot+length, yRot+width/2.);
				x2 	  = xRot+inset;
				x2bis = xRot+length+inset;
			}else{
				updatePathArrow(xRot-length, yRot, xRot, yRot-width/2., xRot-inset, yRot, xRot, yRot+width/2.);
				updatePathArrow(xRot-2*length, yRot, xRot-length, yRot-width/2., xRot-length-inset, yRot, xRot-length, yRot+width/2.);
				x2 	  = xRot-inset;
				x2bis = xRot-length-inset;
			}

		path.lineTo(x2, yRot);
		path.moveTo(x2bis, yRot);
	}



	protected void updatePathSquareRoundEnd(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		path.lineTo(pt1.getX()<pt2.getX() ? xRot+1 : xRot-1, yRot);
		path.moveTo(xRot, yRot);
	}



	protected void updatePathRoundIn(final double xRot, final double yRot, final IPoint pt1, final IPoint pt2) {
		final double x1;
		final double x2;
		final double lineWidth = model.getShape().getThickness();

		if(pt1.getX()<pt2.getX()) {
			x1 = xRot+lineWidth/2.;
			x2 = xRot+lineWidth/2.;
		} else {
			x1 = xRot-lineWidth/2.;
			x2 = xRot-lineWidth/2.;
		}

		path.lineTo(x1, yRot);
		path.moveTo(x2, yRot);
	}



	public void updatePath() {
		double xRot, yRot;
		final ILine arrowLine 	= model.getArrowLine();
		final double lineAngle	= arrowLine.getLineAngle();
		final IPoint pt1 		= arrowLine.getPoint1();
		final IPoint pt2 		= arrowLine.getPoint2();
		final double lineB	  	= arrowLine.getB();

		path.reset();

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
			case LEFT_ARROW			: updatePathRightLeftArrow(xRot, yRot, pt1, pt2, true); break;
			case LEFT_DBLE_ARROW	: updatePathDoubleLeftRightArrow(xRot, yRot, pt1, pt2, true); break;
			case LEFT_ROUND_BRACKET	: /*TODO*/ break;
			case LEFT_SQUARE_BRACKET: updatePathRightLeftSquaredBracket(xRot, yRot, pt1, pt2, true); break;
			case RIGHT_ARROW		: updatePathRightLeftArrow(xRot, yRot, pt1, pt2, false); break;
			case RIGHT_DBLE_ARROW	: updatePathDoubleLeftRightArrow(xRot, yRot, pt1, pt2, false); break;
			case RIGHT_ROUND_BRACKET: /*TODO*/ break;
			case RIGHT_SQUARE_BRACKET: updatePathRightLeftSquaredBracket(xRot, yRot, pt1, pt2, false); break;
			case SQUARE_END			:
			case ROUND_END			: updatePathSquareRoundEnd(xRot, yRot, pt1, pt2); break;
			case ROUND_IN			: updatePathRoundIn(xRot, yRot, pt1, pt2); break;
			case NONE: break;
		}
	}
}
