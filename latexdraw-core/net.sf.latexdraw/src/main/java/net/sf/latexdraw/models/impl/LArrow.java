/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.impl;

import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	private final ObjectProperty<ArrowStyle> style;
	/** The latex parameter arrowSize num. */
	private final DoubleProperty arrowSizeDim;
	/** The latex parameter arrowSize num. */
	private final DoubleProperty arrowSizeNum;
	/** The length of the arrow. */
	private final DoubleProperty arrowLength;
	/** The inset of the arrow. */
	private final DoubleProperty arrowInset;
	/** The latex parameter dotsize dim. */
	private final DoubleProperty dotSizeDim;
	/** The latex parameter dotsize num. */
	private final DoubleProperty dotSizeNum;
	/** The latex parameter tbarsize num. */
	private final DoubleProperty tBarSizeDim;
	/** The latex parameter tbarsize num. */
	private final DoubleProperty tBarSizeNum;
	/** The latex parameter bracket num. */
	private final DoubleProperty bracketNum;
	/** The latex parameter rbracket num. */
	private final DoubleProperty rBracketNum;
	/** The owner of the arrow. */
	private final IArrowableSingleShape owner;

	private Runnable onChanged;


	/**
	 * Creates an arrow.
	 * @param arrowOwner The shape that contains the arrow.
	 * @throws NullPointerException If the given arrowable shape is null.
	 */
	LArrow(final IArrowableSingleShape arrowOwner) {
		super();
		owner = Objects.requireNonNull(arrowOwner);
		style = new SimpleObjectProperty<>(ArrowStyle.NONE);
		arrowInset = new SimpleDoubleProperty(0d);
		arrowLength = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_LENGTH);
		arrowSizeDim = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_SIZE_DIM * IShape.PPC);
		arrowSizeNum = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_SIZE_NUM);
		dotSizeDim = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM * IShape.PPC);
		dotSizeNum = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM);
		tBarSizeDim = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_TBARSIZE_DIM * IShape.PPC);
		tBarSizeNum = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_TBARSIZE_NUM);
		bracketNum = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_BRACKET_LGTH);
		rBracketNum = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_RBRACKET_LGTH);
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
		if(model == null) {
			return;
		}
		arrowInset.set(model.getArrowInset());
		arrowLength.set(model.getArrowLength());
		arrowSizeDim.set(model.getArrowSizeDim());
		arrowSizeNum.set(model.getArrowSizeNum());
		bracketNum.set(model.getBracketNum());
		dotSizeDim.set(model.getDotSizeDim());
		dotSizeNum.set(model.getDotSizeNum());
		rBracketNum.set(model.getRBracketNum());
		style.set(model.getArrowStyle());
		tBarSizeDim.set(model.getTBarSizeDim());
		tBarSizeNum.set(model.getTBarSizeNum());
	}

	@Override
	public double getLineThickness() {
		return owner.getFullThickness();
	}

	@Override
	public double getRoundShapedArrowRadius() {
		return (dotSizeDim.get() + dotSizeNum.get() * getLineThickness()) / 2d;
	}


	@Override
	public double getBarShapedArrowWidth() {
		return tBarSizeDim.get() + tBarSizeNum.get() * getLineThickness();
	}


	@Override
	public double getBracketShapedArrowLength() {
		return bracketNum.get() * getBarShapedArrowWidth();
	}


	@Override
	public double getArrowShapeLength() {
		switch(style.get()) {
			case LEFT_ARROW:
			case RIGHT_ARROW:
				return getArrowShapedWidth() * arrowLength.get();
			case LEFT_DBLE_ARROW:
			case RIGHT_DBLE_ARROW:
				return getArrowShapedWidth() * arrowLength.get() * 2d;
			case ROUND_IN:
				return getShape().getFullThickness() / 2d;
			case LEFT_SQUARE_BRACKET:
			case RIGHT_SQUARE_BRACKET:
				return getShape().getFullThickness() / 2d;
			case CIRCLE_IN:
			case DISK_IN:
				return getRoundShapedArrowRadius();
			default:
				return 0d;
		}
	}


	@Override
	public double getArrowShapedWidth() {
		return arrowSizeNum.get() * getLineThickness() + arrowSizeDim.get();
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
		return arrowInset.get();
	}

	@Override
	public double getArrowLength() {
		return arrowLength.get();
	}

	@Override
	public double getArrowSizeDim() {
		return arrowSizeDim.get();
	}

	@Override
	public double getArrowSizeNum() {
		return arrowSizeNum.get();
	}

	@Override
	public ArrowStyle getArrowStyle() {
		return style.get();
	}

	@Override
	public double getBracketNum() {
		return bracketNum.get();
	}

	@Override
	public double getDotSizeDim() {
		return dotSizeDim.get();
	}

	@Override
	public double getDotSizeNum() {
		return dotSizeNum.get();
	}

	@Override
	public double getRBracketNum() {
		return rBracketNum.get();
	}

	@Override
	public IArrowableSingleShape getShape() {
		return owner;
	}

	@Override
	public double getTBarSizeDim() {
		return tBarSizeDim.get();
	}

	@Override
	public double getTBarSizeNum() {
		return tBarSizeNum.get();
	}

	@Override
	public boolean isInverted() {
		return isLeftArrow() == style.get().isRightStyle();
	}

	@Override
	public boolean isLeftArrow() {
		return owner.getArrowIndex(this) % 2 == 0;
	}

	@Override
	public boolean hasStyle() {
		return style.get() != ArrowStyle.NONE;
	}

	@Override
	public void setArrowInset(final double inset) {
		if(inset >= 0d) {
			arrowInset.set(inset);
			notifyOnChanged();
		}
	}

	@Override
	public void setArrowLength(final double lgth) {
		if(lgth >= 0d) {
			arrowLength.set(lgth);
			notifyOnChanged();
		}
	}

	@Override
	public void setArrowSizeDim(final double size) {
		if(size > 0d) {
			arrowSizeDim.set(size);
			notifyOnChanged();
		}
	}

	@Override
	public void setArrowSizeNum(final double size) {
		if(size >= 0d) {
			arrowSizeNum.set(size);
			notifyOnChanged();
		}
	}


	@Override
	public void setArrowStyle(final ArrowStyle arrowStyle) {
		if(arrowStyle != null) {
			style.set(arrowStyle);
			notifyOnChanged();
		}
	}

	@Override
	public void setBracketNum(final double brack) {
		if(brack >= 0d) {
			bracketNum.set(brack);
			notifyOnChanged();
		}
	}

	@Override
	public void setDotSizeDim(final double dot) {
		if(dot > 0d) {
			dotSizeDim.set(dot);
			notifyOnChanged();
		}
	}

	@Override
	public void setDotSizeNum(final double dot) {
		if(dot >= 0.1) {
			dotSizeNum.set(dot);
			notifyOnChanged();
		}
	}

	@Override
	public void setRBracketNum(final double brack) {
		if(brack >= 0d) {
			rBracketNum.set(brack);
			notifyOnChanged();
		}
	}

	@Override
	public void setTBarSizeDim(final double tbarSizeDim) {
		if(tbarSizeDim > 0d) {
			tBarSizeDim.set(tbarSizeDim);
			notifyOnChanged();
		}
	}

	@Override
	public void setTBarSizeNum(final double tBarSizeNum) {
		if(tBarSizeNum >= 0d) {
			this.tBarSizeNum.set(tBarSizeNum);
			notifyOnChanged();
		}
	}

	@Override
	public ObjectProperty<ArrowStyle> styleProperty() {
		return style;
	}

	@Override
	public DoubleProperty arrowSizeDimProperty() {
		return arrowSizeDim;
	}

	@Override
	public DoubleProperty arrowSizeNumProperty() {
		return arrowSizeNum;
	}

	@Override
	public DoubleProperty arrowLengthProperty() {
		return arrowLength;
	}

	@Override
	public DoubleProperty arrowInsetProperty() {
		return arrowInset;
	}

	@Override
	public DoubleProperty dotSizeDimProperty() {
		return dotSizeDim;
	}

	@Override
	public DoubleProperty dotSizeNumProperty() {
		return dotSizeNum;
	}

	@Override
	public DoubleProperty tBarSizeDimProperty() {
		return tBarSizeDim;
	}

	@Override
	public DoubleProperty tBarSizeNumProperty() {
		return tBarSizeNum;
	}

	@Override
	public DoubleProperty bracketNumProperty() {
		return bracketNum;
	}

	@Override
	public DoubleProperty rBracketNumProperty() {
		return rBracketNum;
	}

	//TODO remove
	private void notifyOnChanged() {
		if(onChanged != null) {
			onChanged.run();
		}
	}
}
