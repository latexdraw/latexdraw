package net.sf.latexdraw.glib.models.impl;

import java.util.Objects;

import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

/**
 * Defines a model of an arrow.<br>
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
 * 02/14/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LArrow implements IArrow {
	/** The style of the arrow. */
	protected ArrowStyle style;

	/** The latex parameter arrowSize num. */
	protected double arrowSizeDim;

	/** The latex parameter arrowSize num. */
	protected double arrowSizeNum;

	/** The length of the arrow. */
	protected double arrowLength;

	/** The inset of the arrow. */
	protected double arrowInset;

	/** The latex parameter dotsize dim. */
	protected double dotSizeDim;

	/** The latex parameter dotsize num. */
	protected double dotSizeNum;

	/** The latex parameter tbarsize num. */
	protected double tBarSizeDim;

	/** The latex parameter tbarsize num. */
	protected double tBarSizeNum;

	/** The latex parameter bracket num. */
	protected double bracketNum;

	/** The latex parameter rbracket num. */
	protected double rBracketNum;

	/** The owner of the arrow. */
	protected IShape owner;



	/**
	 * Creates an arrow.
	 * @param owner The shape that contains the arrow.
	 */
	protected LArrow(final IShape owner) {
		super();
		this.owner 	 = Objects.requireNonNull(owner);
		style		 = ArrowStyle.NONE;
		arrowInset   = 0.;
		arrowLength  = PSTricksConstants.DEFAULT_ARROW_LENGTH;
		arrowSizeDim = PSTricksConstants.DEFAULT_ARROW_SIZE_DIM*IShape.PPC;
		arrowSizeNum = PSTricksConstants.DEFAULT_ARROW_SIZE_NUM;
		dotSizeDim   = PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM*IShape.PPC;
		dotSizeNum   = PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM;
		tBarSizeDim  = PSTricksConstants.DEFAULT_ARROW_TBARSIZE_DIM*IShape.PPC;
		tBarSizeNum  = PSTricksConstants.DEFAULT_ARROW_TBARSIZE_NUM;
		bracketNum   = PSTricksConstants.DEFAULT_ARROW_BRACKET_LGTH;
		rBracketNum  = PSTricksConstants.DEFAULT_ARROW_RBRACKET_LGTH;
	}


	/**
	 * Creates an arrow from an other arrow.
	 * @param arrow The arrow to copy.
	 * @throws IllegalArgumentException If the given arrow is null.
	 */
	protected LArrow(final IArrow arrow, final IShape owner) {
		this(owner);

		if(arrow==null)
			throw new IllegalArgumentException();

		copy(arrow);
	}


	@Override
	public void copy(final IArrow model) {
		if(model!=null) {
			arrowInset 		= model.getArrowInset();
			arrowLength 	= model.getArrowLength();
			arrowSizeDim 	= model.getArrowSizeDim();
			arrowSizeNum 	= model.getArrowSizeNum();
			bracketNum 		= model.getBracketNum();
			dotSizeDim 		= model.getDotSizeDim();
			dotSizeNum 		= model.getDotSizeNum();
			rBracketNum 	= model.getRBracketNum();
			style 			= model.getArrowStyle();
			tBarSizeDim 	= model.getTBarSizeDim();
			tBarSizeNum 	= model.getTBarSizeNum();
		}
	}


	@Override
	public double getRoundShapedArrowRadius() {
		return (dotSizeDim+dotSizeNum*owner.getFullThickness())/2.;
	}


	@Override
	public double getBarShapedArrowWidth() {
		return tBarSizeDim + tBarSizeNum*owner.getFullThickness();
	}


	@Override
	public double getBracketShapedArrowLength() {
		return bracketNum*getBarShapedArrowWidth();
	}


	@Override
	public double getArrowShapeLength() {
		switch(style) {
			case LEFT_ARROW:
			case RIGHT_ARROW:
			case LEFT_DBLE_ARROW:
			case RIGHT_DBLE_ARROW: return getArrowShapedWidth()*arrowLength;
			case ROUND_IN: return (getDotSizeDim()+getDotSizeNum()*owner.getFullThickness())/2.;
			case LEFT_SQUARE_BRACKET:
			case RIGHT_SQUARE_BRACKET: return bracketNum*getBarShapedArrowWidth();
			case CIRCLE_IN:
			case DISK_IN: return getRoundShapedArrowRadius();
			default: return 0;//TODO
		}
	}


	@Override
	public double getArrowShapedWidth() {
		return arrowSizeNum*owner.getFullThickness()+arrowSizeDim;
	}


	@Override
	public ILine getArrowLine() {
		return owner.getArrowLine(this);
	}

	@Override
	public double getArrowInset() {
		return arrowInset;
	}

	@Override
	public double getArrowLength() {
		return arrowLength;
	}

	@Override
	public double getArrowSizeDim() {
		return arrowSizeDim;
	}

	@Override
	public double getArrowSizeNum() {
		return arrowSizeNum;
	}

	@Override
	public ArrowStyle getArrowStyle() {
		return style;
	}

	@Override
	public double getBracketNum() {
		return bracketNum;
	}

	@Override
	public double getDotSizeDim() {
		return dotSizeDim;
	}

	@Override
	public double getDotSizeNum() {
		return dotSizeNum;
	}

	@Override
	public double getRBracketNum() {
		return rBracketNum;
	}

	@Override
	public IShape getShape() {
		return owner;
	}

	@Override
	public double getTBarSizeDim() {
		return tBarSizeDim;
	}

	@Override
	public double getTBarSizeNum() {
		return tBarSizeNum;
	}

	@Override
	public boolean isInverted() {
		final boolean isLeft = isLeftArrow();
		final boolean isRightStyle = style.isRightStyle();
		return (isLeft && isRightStyle)  || (!isLeft && !isRightStyle);
	}

	@Override
	public boolean isLeftArrow() {
		return owner.getArrows().indexOf(this)==0;
	}

	@Override
	public boolean hasStyle() {
		return style!=ArrowStyle.NONE;
	}

	@Override
	public void setArrowInset(final double inset) {
		if(inset>=0)
			arrowInset = inset;
	}

	@Override
	public void setArrowLength(final double lgth) {
		if(lgth>=0)
			arrowLength = lgth;
	}

	@Override
	public void setArrowSizeDim(final double arrowSizeDim) {
		if(arrowSizeDim>0.)
			this.arrowSizeDim = arrowSizeDim;
	}

	@Override
	public void setArrowSizeNum(final double arrowSizeNum) {
		if(arrowSizeNum>=0.)
			this.arrowSizeNum = arrowSizeNum;
	}


	@Override
	public void setArrowStyle(final ArrowStyle arrowStyle) {
		if(arrowStyle!=null)
			style = arrowStyle;
	}

	@Override
	public void setBracketNum(final double bracketNum) {
		if(bracketNum>=0.)
			this.bracketNum = bracketNum;
	}

	@Override
	public void setDotSizeDim(final double dotSizeDim) {
		if(dotSizeDim>0.)
			this.dotSizeDim = dotSizeDim;
	}

	@Override
	public void setDotSizeNum(final double dotSizeNum) {
		if(dotSizeNum>=0.1)
			this.dotSizeNum = dotSizeNum;
	}

	@Override
	public void setRBracketNum(final double rBracketNum) {
		if(rBracketNum>=0.)
			this.rBracketNum = rBracketNum;
	}

	@Override
	public void setTBarSizeDim(final double tbarSizeDim) {
		if(tbarSizeDim>0.)
			tBarSizeDim = tbarSizeDim;
	}

	@Override
	public void setTBarSizeNum(final double tBarSizeNum) {
		if(tBarSizeNum>=0.)
			this.tBarSizeNum = tBarSizeNum;
	}

	@Override
	public void setShape(final IShape shape) {
		if(shape!=null)
			owner = shape;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(arrowInset);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(arrowLength);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(arrowSizeDim);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(arrowSizeNum);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(bracketNum);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dotSizeDim);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dotSizeNum);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rBracketNum);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		result = prime * result + ((style == null) ? 0 : style.hashCode());
		temp = Double.doubleToLongBits(tBarSizeDim);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tBarSizeNum);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof IArrow)) return false;
		IArrow other = (IArrow)obj;
		if(Double.doubleToLongBits(arrowInset) != Double.doubleToLongBits(other.getArrowInset()))
			return false;
		if(Double.doubleToLongBits(arrowLength) != Double.doubleToLongBits(other.getArrowLength()))
			return false;
		if(Double.doubleToLongBits(arrowSizeDim) != Double.doubleToLongBits(other.getArrowSizeDim()))
			return false;
		if(Double.doubleToLongBits(arrowSizeNum) != Double.doubleToLongBits(other.getArrowSizeNum()))
			return false;
		if(Double.doubleToLongBits(bracketNum) != Double.doubleToLongBits(other.getBracketNum()))
			return false;
		if(Double.doubleToLongBits(dotSizeDim) != Double.doubleToLongBits(other.getDotSizeDim()))
			return false;
		if(Double.doubleToLongBits(dotSizeNum) != Double.doubleToLongBits(other.getDotSizeNum()))
			return false;
		if(Double.doubleToLongBits(rBracketNum) != Double.doubleToLongBits(other.getRBracketNum()))
			return false;
		if(style != other.getArrowStyle())
			return false;
		if(Double.doubleToLongBits(tBarSizeDim) != Double.doubleToLongBits(other.getTBarSizeDim()))
			return false;
		if(Double.doubleToLongBits(tBarSizeNum) != Double.doubleToLongBits(other.getTBarSizeNum()))
			return false;
		return true;
	}
}
