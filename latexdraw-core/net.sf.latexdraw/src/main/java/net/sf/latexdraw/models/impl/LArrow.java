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

import java.util.Objects;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * An implementation of an arrow.
 * @author Arnaud Blouin
 */
class LArrow implements IArrow {
	/** The style of the arrow. */
	private ArrowStyle style;
	/** The latex parameter arrowSize num. */
	private double arrowSizeDim;
	/** The latex parameter arrowSize num. */
	private double arrowSizeNum;
	/** The length of the arrow. */
	private double arrowLength;
	/** The inset of the arrow. */
	private double arrowInset;
	/** The latex parameter dotsize dim. */
	private double dotSizeDim;
	/** The latex parameter dotsize num. */
	private double dotSizeNum;
	/** The latex parameter tbarsize num. */
	private double tBarSizeDim;
	/** The latex parameter tbarsize num. */
	private double tBarSizeNum;
	/** The latex parameter bracket num. */
	private double bracketNum;
	/** The latex parameter rbracket num. */
	private double rBracketNum;
	/** The owner of the arrow. */
	private IArrowableSingleShape owner;

	private Runnable onChanged;


	/**
	 * Creates an arrow.
	 * @param arrowOwner The shape that contains the arrow.
	 * @throws NullPointerException If the given arrowable shape is null.
	 */
	LArrow(final IArrowableSingleShape arrowOwner) {
		super();
		owner = Objects.requireNonNull(arrowOwner);
		style = ArrowStyle.NONE;
		arrowInset = 0d;
		arrowLength = PSTricksConstants.DEFAULT_ARROW_LENGTH;
		arrowSizeDim = PSTricksConstants.DEFAULT_ARROW_SIZE_DIM * IShape.PPC;
		arrowSizeNum = PSTricksConstants.DEFAULT_ARROW_SIZE_NUM;
		dotSizeDim = PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM * IShape.PPC;
		dotSizeNum = PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM;
		tBarSizeDim = PSTricksConstants.DEFAULT_ARROW_TBARSIZE_DIM * IShape.PPC;
		tBarSizeNum = PSTricksConstants.DEFAULT_ARROW_TBARSIZE_NUM;
		bracketNum = PSTricksConstants.DEFAULT_ARROW_BRACKET_LGTH;
		rBracketNum = PSTricksConstants.DEFAULT_ARROW_RBRACKET_LGTH;
	}


	/**
	 * Creates an arrow from an other arrow.
	 * @param arrow The arrow to copy.
	 * @throws NullPointerException If the given arrow is null.
	 */
	LArrow(final IArrow arrow, final IArrowableSingleShape owner) {
		this(owner);
		copy(Objects.requireNonNull(arrow));
	}


	@Override
	public void copy(final IArrow model) {
		if(model == null) return;
		arrowInset = model.getArrowInset();
		arrowLength = model.getArrowLength();
		arrowSizeDim = model.getArrowSizeDim();
		arrowSizeNum = model.getArrowSizeNum();
		bracketNum = model.getBracketNum();
		dotSizeDim = model.getDotSizeDim();
		dotSizeNum = model.getDotSizeNum();
		rBracketNum = model.getRBracketNum();
		style = model.getArrowStyle();
		tBarSizeDim = model.getTBarSizeDim();
		tBarSizeNum = model.getTBarSizeNum();
	}

	@Override
	public double getLineThickness() {
		return owner.getFullThickness();
	}

	@Override
	public double getRoundShapedArrowRadius() {
		return (dotSizeDim + dotSizeNum * getLineThickness()) / 2d;
	}


	@Override
	public double getBarShapedArrowWidth() {
		return tBarSizeDim + tBarSizeNum * getLineThickness();
	}


	@Override
	public double getBracketShapedArrowLength() {
		return bracketNum * getBarShapedArrowWidth();
	}


	@Override
	public double getArrowShapeLength() {
		switch(style) {
			case LEFT_ARROW:
			case RIGHT_ARROW:
				return getArrowShapedWidth() * arrowLength;
			case LEFT_DBLE_ARROW:
			case RIGHT_DBLE_ARROW:
				return getArrowShapedWidth() * arrowLength * 2d;
			case ROUND_IN:
				return (getDotSizeDim() + getDotSizeNum() * getLineThickness()) / 2d;
			case LEFT_SQUARE_BRACKET:
			case RIGHT_SQUARE_BRACKET:
				return bracketNum * getBarShapedArrowWidth();
			case CIRCLE_IN:
			case DISK_IN:
				return getRoundShapedArrowRadius();
			default:
				return 0d;
		}
	}


	@Override
	public double getArrowShapedWidth() {
		return arrowSizeNum * getLineThickness() + arrowSizeDim;
	}

	@Override
	public void setOnArrowChanged(final Runnable changed) {
		onChanged = changed;
	}

	@Override
	public ILine getArrowLine() {
		return owner.getArrowLine(owner.getArrowIndex(this));
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
	public IArrowableSingleShape getShape() {
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
		return isLeftArrow() == style.isRightStyle();
	}

	@Override
	public boolean isLeftArrow() {
		return owner.getArrowIndex(this) % 2 == 0;
	}

	@Override
	public boolean hasStyle() {
		return style != ArrowStyle.NONE;
	}

	@Override
	public void setArrowInset(final double inset) {
		if(inset >= 0d) {
			arrowInset = inset;
			notifyOnChanged();
		}
	}

	@Override
	public void setArrowLength(final double lgth) {
		if(lgth >= 0d) {
			arrowLength = lgth;
			notifyOnChanged();
		}
	}

	@Override
	public void setArrowSizeDim(final double size) {
		if(size > 0d) {
			arrowSizeDim = size;
			notifyOnChanged();
		}
	}

	@Override
	public void setArrowSizeNum(final double size) {
		if(size >= 0d) {
			arrowSizeNum = size;
			notifyOnChanged();
		}
	}


	@Override
	public void setArrowStyle(final ArrowStyle arrowStyle) {
		if(arrowStyle != null) {
			style = arrowStyle;
			notifyOnChanged();
		}
	}

	@Override
	public void setBracketNum(final double brack) {
		if(brack >= 0d) {
			bracketNum = brack;
			notifyOnChanged();
		}
	}

	@Override
	public void setDotSizeDim(final double dot) {
		if(dot > 0d) {
			dotSizeDim = dot;
			notifyOnChanged();
		}
	}

	@Override
	public void setDotSizeNum(final double dot) {
		if(dot >= 0.1) {
			dotSizeNum = dot;
			notifyOnChanged();
		}
	}

	@Override
	public void setRBracketNum(final double brack) {
		if(brack >= 0d) {
			rBracketNum = brack;
			notifyOnChanged();
		}
	}

	@Override
	public void setTBarSizeDim(final double tbarSizeDim) {
		if(tbarSizeDim > 0d) {
			tBarSizeDim = tbarSizeDim;
			notifyOnChanged();
		}
	}

	@Override
	public void setTBarSizeNum(final double tBarSizeNum) {
		if(tBarSizeNum >= 0d) {
			this.tBarSizeNum = tBarSizeNum;
			notifyOnChanged();
		}
	}

	private void notifyOnChanged() {
		if(onChanged != null) {
			onChanged.run();
		}
	}
}
