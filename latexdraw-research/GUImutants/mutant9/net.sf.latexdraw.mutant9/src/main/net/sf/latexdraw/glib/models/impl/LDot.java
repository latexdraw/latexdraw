package net.sf.latexdraw.glib.models.impl;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a model of a dot.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/05/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LDot extends LPositionShape implements IDot {
	/** The current style of the dot. */
	protected DotStyle style;

	/** The radius of the dot. */
	protected double radius;


	/**
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @param pt The centre of the dot.
	 */
	protected LDot(final IPoint pt, final boolean isUniqueID) {
		super(isUniqueID, pt);

		style = DotStyle.DOT;
		radius = 20.;
	}


	@Override
	public IDot duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof IDot ? (IDot)sh : null;
	}


	@Override
	public Color getFillingCol() {
		return isFillable() ? super.getFillingCol() : Color.BLACK;
	}


	/**
	 * Initialises a dot using a centre point.
	 * @param pt The centre of the dot.
	 */
	public LDot(final IPoint pt) {
		this(pt, false);
	}


	@Override
	public DotStyle getDotStyle() {
		return style;
	}


	@Override
	public double getRadius() {
		return radius;
	}


	@Override
	public void setDotStyle(final DotStyle style) {
		if(style != null)
			this.style = style;
	}


	@Override
	public void setRadius(final double radius) {
		if(radius > 0. && GLibUtilities.INSTANCE.isValidCoordinate(radius))
			this.radius = radius;
	}


	/**
	 * Returns the radius computed using a new position (value) and an axe (isX).
	 * @param value The new X or Y coordinate of the extremity of the dot.
	 * @param isX  True: the value will be considered on the X-axe. Otherwise, on the Y-axe.
	 * @return The new radius.
	 * @since 3.0
	 */
	protected double getNewRadius(final double value, final boolean isX) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(value))
			return isX ? Math.abs(getPosition().getX() - value) : Math.abs(getPosition().getY() - value);

		return Double.NaN;
	}


	@Override
	public void mirrorHorizontal(final IPoint origin) {
		// Nothing to do for the dot.
	}



	@Override
	public void mirrorVertical(final IPoint origin) {
		// Nothing to do for the dot.
	}


	@Override
	public void setX(final double x) {
		points.get(0).setX(x);
	}



	@Override
	public void setY(final double y) {
		points.get(0).setY(y);
	}



	@Override
	public double getX() {
		return points.get(0).getX();
	}



	@Override
	public double getY() {
		return points.get(0).getY();
	}



	@Override
	public void scale(final double x, final double y, final Position pos, final Rectangle2D bound) {
		setRadius(radius * Math.max(x/bound.getWidth(), y/bound.getHeight()));
	}


	@Override
	public IPoint getPosition() {
		// The position of the dot is its centre.
		return points.get(0);
	}



	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IDot){
			final IDot dot = (IDot)sh;

			setDotStyle(dot.getDotStyle());
			setRadius(dot.getRadius());
		}
	}



	@Override
	public IPoint getBottomLeftPoint() {
		final IPoint tl = new LPoint();
		final IPoint br = new LPoint();
		getTopLeftBottomRightPoints(tl, br);

		return new LPoint(tl.getX(), br.getY());
	}



	@Override
	public IPoint getBottomRightPoint() {
		final IPoint br = new LPoint();
		getTopLeftBottomRightPoints(new LPoint(), br);

		return br;
	}



	@Override
	public IPoint getTopLeftPoint() {
		final IPoint tl = new LPoint();
		getTopLeftBottomRightPoints(tl, new LPoint());

		return tl;
	}



	@Override
	public IPoint getTopRightPoint() {
		final IPoint tl = new LPoint();
		final IPoint br = new LPoint();
		getTopLeftBottomRightPoints(tl, br);

		return new LPoint(br.getX(), tl.getY());
	}



	/**
	 * Gives the top-left point and the bottom-right point of the dot
	 * considering its current style.
	 * @param tl The top-left point to set. Must not be null.
	 * @param br The bottom-right point to set. Must not be null.
	 * @throws NullPointerException If tl or br is null.
	 * @since 3.0
	 */
	protected void getTopLeftBottomRightPoints(final IPoint tl, final IPoint br) {
		final IPoint centre = getPosition();
		final double x = centre.getX();
		final double y = centre.getY();
		final double tlx = x - radius;
		final double tly = y - radius;
		final double brx = x + radius;
		final double bry = y + radius;
		final double dec = 2. * radius / THICKNESS_O_STYLE_FACTOR;

		// Each dot shape has a special shape computed from the parameters
		// defined below.
		switch(style){
			case ASTERISK:// TODO: to check, I do not think it works.
				final double radiusAst = tly + radius / 5. - (bry - radius / 5.) / 2. + dec;
				tl.setX(Math.cos(7 * Math.PI / 6.) * radiusAst + x);
				tl.setY(tly + radius / 5. - dec);
				br.setX(Math.cos(Math.PI / 6.) * radiusAst + x);
				br.setY(bry - radius / 5. + dec);
				break;
			case BAR:
				// The thickness of the bar.
				final double barThickness = radius / 8.;
				tl.setX(x - barThickness);
				tl.setY(tly);
				br.setX(x + barThickness);
				// TODO: check if it is not radius*(1/1.875+1/8.): the bar
				// thickness may be used into radius/1.875
				br.setY(bry + radius / 1.875);
				break;
			case DIAMOND:
			case FDIAMOND:
				final double p = 2. * Math.abs(tlx - brx) / (2. * Math.sin(GOLDEN_ANGLE)) * Math.cos(GOLDEN_ANGLE);
				final double x1 = brx - 1.5 * dec;
				final double x2 = tlx + 1.5 * dec;
				tl.setX(x1 < x2 ? x1 : x2);
				tl.setY((tly + bry) / 2. + p / 2. - 1.5 * dec);
				br.setX(x1 > x2 ? x1 : x2);
				br.setY((tly + bry) / 2. - p / 2. + 1.5 * dec);
				break;
			case FPENTAGON:
			case PENTAGON:
				final double dist = radius + dec;
				final double xValue = Math.sin(2. * Math.PI / 5.) * dist;
				tl.setX(-xValue + x);
				tl.setY(tly - dec);
				br.setX(xValue + x);
				br.setY(0.25 * (Math.sqrt(5.) + 1.) * dist + y + dec);
				break;
			case FSQUARE:
			case SQUARE:// TODO may be wrong, to compare with 2.0.
				tl.setX(tlx);
				tl.setY(tly);
				br.setX(brx);
				br.setY(bry);
				break;
			case FTRIANGLE:
			case TRIANGLE:
				tl.setX(tlx - 0.3 * dec);
				tl.setY(tly - 1.5 * dec);
				br.setX(brx + 0.3 * dec);
				br.setY(bry - 3. * dec);
				break;
			case DOT:
			case O:
			case OPLUS:
			case OTIMES:
				tl.setX(tlx);
				tl.setY(tly);
				br.setX(brx);
				br.setY(bry);
				break;
			case PLUS:// TODO may be wrong, to compare with 2.0.
				final double plusGap = radius / 80.;
				tl.setX(tlx - plusGap);
				tl.setY(tly - plusGap);
				br.setX(brx + plusGap);
				br.setY(bry + plusGap);
				break;
			case X:// TODO may be wrong, to compare with 2.0.
				final double crossGap = radius / 5.;
				tl.setX(tlx - crossGap);
				tl.setY(tly - crossGap);
				br.setX(brx + crossGap);
				br.setY(bry + crossGap);
				break;
		}
	}



	@Override
	public boolean isFillable() {
		return style.isFillable();
	}



	@Override
	public boolean isFilled() {
		return isFillable() || style == DotStyle.FDIAMOND || style == DotStyle.FPENTAGON || style == DotStyle.FSQUARE
				|| style == DotStyle.FTRIANGLE || style==DotStyle.DOT;
	}


	@Override
	public IPoint getLazyTopLeftPoint() {
		final IPoint centre = getPosition();
		return new LPoint(centre.getX() - radius / 2., centre.getY() - radius / 2.);
	}



	@Override
	public IPoint getLazyBottomRightPoint() {
		final IPoint centre = getPosition();
		return new LPoint(centre.getX() + radius / 2., centre.getY() + radius / 2.);
	}



	@Override
	public double getPlusGap() {
		return radius / 160.;
	}



	@Override
	public double getCrossGap() {
		return radius / 10.;
	}



	@Override
	public double getBarGap() {
		return radius / 3.75;
	}



	@Override
	public double getBarThickness() {
		return radius / 8.;
	}



	@Override
	public double getGeneralGap() {
		return radius / IDot.THICKNESS_O_STYLE_FACTOR;
	}



	@Override
	public double getOGap() {
		final double dec = style==DotStyle.O ? 3.6 : 2.6;
		return radius * (0.1 / dec) * 2;
	}


	@Override
	public Color getDotFillingCol() {
		return getFillingCol();
	}


	@Override
	public void setDotFillingCol(final Color value) {
		setFillingCol(value);
	}
}
