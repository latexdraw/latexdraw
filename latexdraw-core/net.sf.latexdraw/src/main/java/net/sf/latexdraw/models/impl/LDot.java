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
package net.sf.latexdraw.models.impl;

import java.awt.geom.Rectangle2D;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IDotProp;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.Position;
import net.sf.latexdraw.view.latex.DviPsColors;

/**
 * An implementation of a dot.
 * @author Arnaud Blouin
 */
class LDot extends LPositionShape implements IDot {
	/** The current style of the dot. */
	private final ObjectProperty<DotStyle> style;
	/** The radius of the dot. */
	private final DoubleProperty diametre;


	/**
	 * @param pt The centre of the dot.
	 */
	LDot(final IPoint pt) {
		super(pt);
		style = new SimpleObjectProperty<>(DotStyle.DOT);
		diametre = new SimpleDoubleProperty(40d);
	}

	@Override
	public Color getFillingCol() {
		return isFillable() ? super.getFillingCol() : DviPsColors.BLACK;
	}

	@Override
	public DotStyle getDotStyle() {
		return style.get();
	}

	@Override
	public void setDotStyle(final DotStyle dotStyle) {
		if(dotStyle != null) {
			style.set(dotStyle);
		}
	}

	@Override
	public double getDiametre() {
		return diametre.get();
	}

	@Override
	public void setDiametre(final double diam) {
		if(diam > 0d && MathUtils.INST.isValidCoord(diam)) {
			diametre.set(diam);
		}
	}

	/**
	 * Returns the radius computed using a new position (value) and an axe (isX).
	 * @param value The new X or Y coordinate of the extremity of the dot.
	 * @param isX True: the value will be considered on the X-axe. Otherwise, on the Y-axe.
	 * @return The new radius.
	 */
	protected double getNewRadius(final double value, final boolean isX) {
		if(MathUtils.INST.isValidCoord(value)) {
			return isX ? Math.abs(getPosition().getX() - value) : Math.abs(getPosition().getY() - value);
		}

		return Double.NaN;
	}

	@Override
	public void mirrorHorizontal(final double x) {
		if(MathUtils.INST.isValidCoord(x)) {
			setPosition(getPosition().horizontalSymmetry(x));
		}
	}

	@Override
	public void mirrorVertical(final double y) {
		if(MathUtils.INST.isValidCoord(y)) {
			setPosition(getPosition().verticalSymmetry(y));
		}
	}

	@Override
	public double getX() {
		return points.get(0).getX();
	}

	@Override
	public void setX(final double x) {
		points.get(0).setX(x);
	}

	@Override
	public double getY() {
		return points.get(0).getY();
	}

	@Override
	public void setY(final double y) {
		points.get(0).setY(y);
	}

	@Override
	public void scale(final double prevWidth, final double prevHeight, final Position pos, final Rectangle2D bound) {
		scaleWithRatio(prevWidth, prevHeight, pos, bound);
	}

	@Override
	public void scaleWithRatio(final double prevWidth, final double prevHeight, final Position pos, final Rectangle2D bound) {
		setDiametre(getDiametre() * Math.max(prevWidth / bound.getWidth(), prevHeight / bound.getHeight()));
	}

	@Override
	public IPoint getPosition() {
		// The position of the dot is its centre.
		return points.get(0);
	}

	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh != null && sh.isTypeOf(IDotProp.class)) {
			final IDotProp dot = (IDotProp) sh;

			setDotStyle(dot.getDotStyle());
			setDiametre(dot.getDiametre());
			setDotFillingCol(dot.getDotFillingCol());
		}
	}

	@Override
	public IPoint getBottomLeftPoint() {
		final IPoint tl = ShapeFactory.INST.createPoint();
		final IPoint br = ShapeFactory.INST.createPoint();
		getTopLeftBottomRightPoints(tl, br);
		return ShapeFactory.INST.createPoint(tl.getX(), br.getY());
	}

	@Override
	public IPoint getBottomRightPoint() {
		final IPoint br = ShapeFactory.INST.createPoint();
		getTopLeftBottomRightPoints(ShapeFactory.INST.createPoint(), br);
		return br;
	}

	@Override
	public IPoint getTopLeftPoint() {
		final IPoint tl = ShapeFactory.INST.createPoint();
		getTopLeftBottomRightPoints(tl, ShapeFactory.INST.createPoint());
		return tl;
	}

	@Override
	public IPoint getTopRightPoint() {
		final IPoint tl = ShapeFactory.INST.createPoint();
		final IPoint br = ShapeFactory.INST.createPoint();
		getTopLeftBottomRightPoints(tl, br);
		return ShapeFactory.INST.createPoint(br.getX(), tl.getY());
	}

	/**
	 * Gives the top-left point and the bottom-right point of the dot
	 * considering its current style.
	 * @param tl The top-left point to set. Must not be null.
	 * @param br The bottom-right point to set. Must not be null.
	 * @throws NullPointerException If tl or br is null.
	 * @since 3d
	 */
	protected void getTopLeftBottomRightPoints(final IPoint tl, final IPoint br) {
		final IPoint centre = getPosition();
		final double x = centre.getX();
		final double y = centre.getY();
		final double diam = getDiametre();
		final double tlx = x - diam;
		final double tly = y - diam;
		final double brx = x + diam;
		final double bry = y + diam;
		final double dec = 2d * diam / THICKNESS_O_STYLE_FACTOR;

		// Each dot shape has a special shape computed from the parameters
		// defined below.
		switch(getDotStyle()) {
			case ASTERISK:// TODO: to check, I do not think it works.
				final double radiusAst = tly + diam / 5d - (bry - diam / 5d) / 2d + dec;
				tl.setX(Math.cos(7d * Math.PI / 6d) * radiusAst + x);
				tl.setY(tly + diam / 5d - dec);
				br.setX(Math.cos(Math.PI / 6d) * radiusAst + x);
				br.setY(bry - diam / 5d + dec);
				break;
			case BAR:
				// The thickness of the bar.
				final double barThickness = diam / 8d;
				tl.setX(x - barThickness);
				tl.setY(tly);
				br.setX(x + barThickness);
				// TODO: check if it is not radius*(1/1.875+1/8.): the bar
				// thickness may be used into radius/1.875
				br.setY(bry + diam / 1.875);
				break;
			case DIAMOND:
			case FDIAMOND:
				final double p = 2d * Math.abs(tlx - brx) / (2d * Math.sin(GOLDEN_ANGLE)) * Math.cos(GOLDEN_ANGLE);
				final double x1 = brx - 1.5 * dec;
				final double x2 = tlx + 1.5 * dec;
				tl.setX(x1 < x2 ? x1 : x2);
				tl.setY((tly + bry) / 2d + p / 2d - 1.5 * dec);
				br.setX(x1 > x2 ? x1 : x2);
				br.setY((tly + bry) / 2d - p / 2d + 1.5 * dec);
				break;
			case FPENTAGON:
			case PENTAGON:
				final double dist = diam + dec;
				final double xValue = Math.sin(2d * Math.PI / 5d) * dist;
				tl.setX(-xValue + x);
				tl.setY(tly - dec);
				br.setX(xValue + x);
				br.setY(0.25 * (Math.sqrt(5d) + 1d) * dist + y + dec);
				break;
			case FSQUARE:
			case SQUARE:// TODO may be wrong, to compare with 2d.
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
				br.setY(bry - 3d * dec);
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
			case PLUS:// TODO may be wrong, to compare with 2d.
				final double plusGap = diam / 80d;
				tl.setX(tlx - plusGap);
				tl.setY(tly - plusGap);
				br.setX(brx + plusGap);
				br.setY(bry + plusGap);
				break;
			case X:// TODO may be wrong, to compare with 2d.
				final double crossGap = diam / 5d;
				tl.setX(tlx - crossGap);
				tl.setY(tly - crossGap);
				br.setX(brx + crossGap);
				br.setY(bry + crossGap);
				break;
		}
	}

	@Override
	public boolean isFillable() {
		return getDotStyle().isFillable();
	}

	@Override
	public boolean isFilled() {
		final DotStyle dotStyle = getDotStyle();
		return isFillable() || dotStyle == DotStyle.FDIAMOND || dotStyle == DotStyle.FPENTAGON || dotStyle == DotStyle.FSQUARE ||
			dotStyle == DotStyle.FTRIANGLE || dotStyle == DotStyle.DOT;
	}

	@Override
	public IPoint getLazyTopLeftPoint() {
		final IPoint centre = getPosition();
		final double diam = getDiametre();
		return ShapeFactory.INST.createPoint(centre.getX() - diam / 2d, centre.getY() - diam / 2d);
	}

	@Override
	public IPoint getLazyBottomRightPoint() {
		final IPoint centre = getPosition();
		final double diam = getDiametre();
		return ShapeFactory.INST.createPoint(centre.getX() + diam / 2d, centre.getY() + diam / 2d);
	}

	@Override
	public double getPlusGap() {
		return getDiametre() / 160d;
	}

	@Override
	public double getCrossGap() {
		return getDiametre() / 10d;
	}

	@Override
	public double getBarGap() {
		return getDiametre() / 3.75;
	}

	@Override
	public double getBarThickness() {
		return getDiametre() / 8d;
	}

	@Override
	public double getGeneralGap() {
		return getDiametre() / IDot.THICKNESS_O_STYLE_FACTOR;
	}

	@Override
	public double getOGap() {
		final double dec = getDotStyle() == DotStyle.O ? 3.6 : 2.6;
		return getDiametre() * (0.1 / dec) * 2d;
	}

	@Override
	public Color getDotFillingCol() {
		return getFillingCol();
	}

	@Override
	public void setDotFillingCol(final Color value) {
		setFillingCol(value);
	}

	@Override
	public ObjectProperty<DotStyle> styleProperty() {
		return style;
	}

	@Override
	public DoubleProperty diametreProperty() {
		return diametre;
	}
}
