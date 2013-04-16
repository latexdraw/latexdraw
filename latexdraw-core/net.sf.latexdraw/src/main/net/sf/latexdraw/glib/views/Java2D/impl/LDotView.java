package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewDot;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a view of the IDot model.<br>
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
 * 04/04/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LDotView extends LShapeView<IDot> implements IViewDot {
	/**
	 * Initialises the view of the dot.
	 * @param model The dot model.
	 * @since 3.0
	 */
	protected LDotView(final IDot model) {
		super(model);

		update();
	}


	@Override
	public BasicStroke getStroke() {
		final BasicStroke stroke;

		switch(shape.getDotStyle()) {
			case FTRIANGLE	:
			case TRIANGLE	:
			case FPENTAGON	:
			case PENTAGON	:
			case FDIAMOND	:
			case O 			:
			case DIAMOND	:
			case ASTERISK	: stroke = new BasicStroke((float)shape.getGeneralGap(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER); break;
			case BAR		: stroke = new BasicStroke((float)shape.getBarThickness(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER); break;
			case DOT 		: stroke = new BasicStroke((float)(shape.getRadius()/IDot.THICKNESS_O_STYLE_FACTOR), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER); break;
			case OPLUS		:
			case FSQUARE	:
			case OTIMES		:
			case SQUARE		: stroke = new BasicStroke((float)shape.getGeneralGap(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER); break;
			case PLUS		: stroke = new BasicStroke((float)(shape.getRadius()/IDot.PLUS_COEFF_WIDTH), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER); break;
			case X			: stroke = new BasicStroke((float)shape.getCrossGap(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER); break;
			default			: stroke = null;
		}

		return stroke;
	}


	@Override
	public void paintBorders(final Graphics2D g) {
		// Nothing to do.
	}

	@Override
	public void paint(final Graphics2D g) {
		switch(shape.getDotStyle()) {
			case DIAMOND	:
			case O 			:
			case PENTAGON	:
			case SQUARE		:
			case TRIANGLE	: paintDotShape(g, true, shape.getFillingCol()); break;
			case DOT 		:
			case FDIAMOND	:
			case FPENTAGON	:
			case FSQUARE	:
			case FTRIANGLE	: paintDotShape(g, true, shape.getLineColour()); break;
			case ASTERISK	:
			case BAR		:
			case OPLUS		:
			case OTIMES		:
			case PLUS		:
			case X			: paintDotShape(g, false, null); break;
		}
	}


	private void paintDotShape(final Graphics2D g, final boolean toFill, final Color fillCol) {
		final BasicStroke stroke = getStroke();

		if(toFill) {
			g.setColor(fillCol);
			g.fill(path);
		}

		if(stroke!=null) {
			g.setStroke(stroke);
			g.setColor(shape.getLineColour());
			g.draw(path);
		}
	}


	protected void setPathOTime() {
		final IPoint centre = shape.getPosition();
		final IPoint br		= shape.getLazyBottomRightPoint();
		final IPoint tl		= shape.getLazyTopLeftPoint();
		final double dec 	= shape.getGeneralGap();
		final double tlx	= tl.getX();
		final double tly 	= tl.getY();
		final double brx 	= br.getX();
		final double bry 	= br.getY();

		path.reset();
		setPathOLikeDot(shape.getOGap());

		IPoint p1 = DrawingTK.getFactory().createPoint((tlx+brx)/2., tly+dec*2);
		IPoint p2 = DrawingTK.getFactory().createPoint((tlx+brx)/2., bry-dec*2);
		p1 = p1.rotatePoint(centre, Math.PI/4.);
		p2 = p2.rotatePoint(centre, Math.PI/4.);

		path.moveTo(p1.getX(), p1.getY());
		path.lineTo(p2.getX(), p2.getY());

		p1.setPoint(tlx+dec*2, (tly+bry)/2.);
		p2.setPoint(brx-dec*2, (tly+bry)/2.);
		p1 = p1.rotatePoint(centre, Math.PI/4.);
		p2 = p2.rotatePoint(centre, Math.PI/4.);

		path.moveTo(p1.getX(), p1.getY());
		path.lineTo(p2.getX(), p2.getY());
	}


	protected void setPathOPlus() {
		final double dec 	= shape.getGeneralGap();
		final IPoint br		= shape.getLazyBottomRightPoint();
		final IPoint tl		= shape.getLazyTopLeftPoint();

		path.reset();
		setPathOLikeDot(shape.getOGap());
		path.moveTo((tl.getX()+br.getX())/2., tl.getY()+dec*2);
		path.lineTo((tl.getX()+br.getX())/2., br.getY()-dec*2);
		path.moveTo(tl.getX()+dec*2, (tl.getY()+br.getY())/2.);
		path.lineTo(br.getX()-dec*2, (tl.getY()+br.getY())/2.);
	}


	protected void setPathO() {
		path.reset();
		setPathOLikeDot(shape.getOGap());
	}


	private void setPathOLikeDot(final double dec) {
		final IPoint tl	= shape.getLazyTopLeftPoint();

		LEllipseView.setEllipsePath(path, tl.getX()+dec/2., tl.getY()+dec/2., shape.getRadius()-dec, shape.getRadius()-dec);
	}


	protected void setPathBar() {
		final IPoint br	= shape.getLazyBottomRightPoint();
		final IPoint tl	= shape.getLazyTopLeftPoint();

		path.reset();
		path.moveTo((tl.getX()+br.getX())/2., tl.getY()+shape.getBarThickness()/2.);
		path.lineTo((tl.getX()+br.getX())/2., br.getY()+shape.getBarGap());
	}


	protected void setPathPlus() {
		final double plusGap= shape.getPlusGap();
		final IPoint br		= shape.getLazyBottomRightPoint();
		final IPoint tl		= shape.getLazyTopLeftPoint();

		path.reset();
		path.moveTo((tl.getX()+br.getX())/2., tl.getY()-plusGap);
		path.lineTo((tl.getX()+br.getX())/2., br.getY()+plusGap);
		path.moveTo(tl.getX()-plusGap, (tl.getY()+br.getY())/2.);
		path.lineTo(br.getX()+plusGap, (tl.getY()+br.getY())/2.);
	}


	protected void setPathSquare() {
		final double dec 	= shape.getRadius()/IDot.THICKNESS_O_STYLE_FACTOR;
		final IPoint tl		= shape.getLazyTopLeftPoint();
		final double width 	= shape.getRadius()-dec*3;
		final double x 		= tl.getX()+dec+dec/2.;
		final double y 		= tl.getY()+dec+dec/2.;

		path.reset();
		path.moveTo(x, y);
		path.lineTo(x+width, y);
		path.lineTo(x+width, y+width);
		path.lineTo(x, y+width);
		path.closePath();
	}


	protected void setPathX() {
		final IPoint br		 = shape.getLazyBottomRightPoint();
		final IPoint tl		 = shape.getLazyTopLeftPoint();
		final double crossGap= shape.getCrossGap();

		path.reset();
		path.moveTo(tl.getX()+crossGap, tl.getY()+crossGap);
		path.lineTo(br.getX()-crossGap, br.getY()-crossGap);
		path.moveTo(br.getX()-crossGap, tl.getY()+crossGap);
		path.lineTo(tl.getX()+crossGap, br.getY()-crossGap);
	}


	protected void setPathAsterisk() {
		final IPoint br		 = shape.getLazyBottomRightPoint();
		final IPoint tl		 = shape.getLazyTopLeftPoint();
		final double width   = shape.getRadius();
		final double dec 	 = width/IDot.THICKNESS_O_STYLE_FACTOR;
		final double xCenter = (tl.getX()+br.getX())/2.;
		final double yCenter = (tl.getY()+br.getY())/2.;
		final double radius  = Math.abs(tl.getY()+width/10.-(br.getY()-width/10.))/2. + dec;

		path.reset();
		path.moveTo(xCenter, tl.getY()+width/10.-dec);
		path.lineTo(xCenter, br.getY()-width/10.+dec);
		path.moveTo(Math.cos(Math.PI/6.)*radius+xCenter, radius/2. + yCenter);
		path.lineTo(Math.cos(7*Math.PI/6.)*radius+xCenter, Math.sin(7*Math.PI/6.)*radius+yCenter);
		path.moveTo(Math.cos(5*Math.PI/6.)*radius+xCenter, Math.sin(5*Math.PI/6.)*radius+yCenter);
		path.lineTo(Math.cos(11*Math.PI/6.)*radius+xCenter, Math.sin(11*Math.PI/6.)*radius+yCenter);
	}



	/**
	 * Creates a diamond (one of the possibles shapes of a dot).
	 */
	protected void setPathDiamond() {
		final IPoint tl		= shape.getLazyTopLeftPoint();
		final IPoint br		= shape.getLazyBottomRightPoint();
		final double dec 	= shape.getRadius()/IDot.THICKNESS_O_STYLE_FACTOR;
		// This diamond is a golden diamond
		// cf. http://mathworld.wolfram.com/GoldenRhombus.html
		final double midY 	= (tl.getY()+br.getY())/2;
		final double a 		= Math.abs(tl.getX()-br.getX())/(2.*Math.sin(IShape.GOLDEN_ANGLE));
		final double p 		= 2.*a*Math.cos(IShape.GOLDEN_ANGLE);
		final double x1 	= br.getX()-dec-.5*dec;
		final double x3 	= tl.getX()+dec+.5*dec;

		path.reset();
		path.moveTo((x1+x3)/2., midY+p/2.-dec-.5*dec);
		path.lineTo(x1, midY);
		path.lineTo((x1+x3)/2., midY-p/2.+dec+.5*dec);
		path.lineTo(x3, midY);
		path.closePath();
	}



	/**
	 * Creates a pentagon (one of the possibles shapes of a dot)
	 */
	protected void setPathPentagon() {
		final double dec 	= shape.getRadius()/IDot.THICKNESS_O_STYLE_FACTOR;
		final IPoint tl		= shape.getLazyTopLeftPoint();
		final IPoint br		= shape.getLazyBottomRightPoint();
		final double yCenter= (tl.getY()+br.getY())/2. - dec;
		final double xCenter= (tl.getX()+br.getX())/2.;
		final double dist 	= Math.abs(tl.getY()-br.getY())/2. + dec;
		final double c1 	= 0.25*(Math.sqrt(5)-1.)*dist;
		final double s1 	= Math.sin(2*Math.PI/5.)*dist;
		final double c2 	= 0.25*(Math.sqrt(5)+1.)*dist;
		final double s2 	= Math.sin(4*Math.PI/5.)*dist;

		path.reset();
		path.moveTo(xCenter, tl.getY()-dec);
		path.lineTo(s1 + xCenter, -c1 + yCenter + dec);
		path.lineTo(s2 + xCenter, c2 + yCenter + dec);
		path.lineTo(-s2 + xCenter, c2 + yCenter + dec);
		path.lineTo(-s1 + xCenter, -c1 + yCenter + dec);
		path.closePath();
	}



	/**
	 * Creates a triangle (one of the possibles shapes of a dot).
	 */
	protected void setPathTriangle() {
		final IPoint tl	 = shape.getLazyTopLeftPoint();
		final double dec = shape.getRadius()/IDot.THICKNESS_O_STYLE_FACTOR;
		final IPoint br	 = shape.getLazyBottomRightPoint();

		path.reset();
		path.moveTo((br.getX() + tl.getX())/2., tl.getY()-1.5*dec);
		path.lineTo(tl.getX()-0.3*dec, br.getY()-3*dec);
		path.lineTo(br.getX()+0.3*dec, br.getY()-3*dec);
		path.closePath();
	}



	@Override
	public void updatePath() {
		switch(shape.getDotStyle()) {
			case ASTERISK	: setPathAsterisk();break;
			case BAR		: setPathBar();		break;
			case DIAMOND	: setPathDiamond();	break;
			case O			:
			case DOT		: setPathO();		break;
			case FDIAMOND	: setPathDiamond();	break;
			case PENTAGON	:
			case FPENTAGON	: setPathPentagon();break;
			case SQUARE		:
			case FSQUARE	: setPathSquare();	break;
			case TRIANGLE	:
			case FTRIANGLE	: setPathTriangle();break;
			case OPLUS		: setPathOPlus();	break;
			case OTIMES		: setPathOTime();	break;
			case PLUS		: setPathPlus();	break;
			case X			: setPathX();		break;
		}
	}


	@Override
	public void updateBorder() {
		final double angle = shape.getRotationAngle();

		if(LNumber.INSTANCE.equals(angle, 0.)) {
			Rectangle2D rec = path.getBounds2D();
			BasicStroke stroke = getStroke();
			// The stroke must be used for defining the border of the dot.
			if(stroke!=null)
				rec = rec.createUnion(stroke.createStrokedShape(path).getBounds2D());

			border.setFrame(rec);
		}
		else {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException());
			//TODO
		}
	}


	@Override
	protected void updateDblePathInside() {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathMiddle() {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathOutside() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathInside() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathMiddle() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathOutside() {
		// Nothing to do.
	}
}
