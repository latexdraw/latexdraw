/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.impl;

import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import net.sf.latexdraw.model.api.shape.Line;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of an arrow.
 * @author Arnaud Blouin
 */
class ArrowImpl implements Arrow {
	/** The style of the arrow. */
	private final @NotNull ObjectProperty<ArrowStyle> style;
	/** The latex parameter arrowSize num. */
	private final @NotNull DoubleProperty arrowSizeDim;
	/** The latex parameter arrowSize num. */
	private final @NotNull DoubleProperty arrowSizeNum;
	/** The length of the arrow. */
	private final @NotNull DoubleProperty arrowLength;
	/** The inset of the arrow. */
	private final @NotNull DoubleProperty arrowInset;
	/** The latex parameter dotsize dim. */
	private final @NotNull DoubleProperty dotSizeDim;
	/** The latex parameter dotsize num. */
	private final @NotNull DoubleProperty dotSizeNum;
	/** The latex parameter tbarsize num. */
	private final @NotNull DoubleProperty tBarSizeDim;
	/** The latex parameter tbarsize num. */
	private final @NotNull DoubleProperty tBarSizeNum;
	/** The latex parameter bracket num. */
	private final @NotNull DoubleProperty bracketNum;
	/** The latex parameter rbracket num. */
	private final @NotNull DoubleProperty rBracketNum;
	/** The owner of the arrow. */
	private final @NotNull ArrowableSingleShape owner;


	/**
	 * Creates an arrow.
	 * @param arrowOwner The shape that contains the arrow.
	 * @throws NullPointerException If the given arrowable shape is null.
	 */
	ArrowImpl(final ArrowableSingleShape arrowOwner) {
		super();
		owner = Objects.requireNonNull(arrowOwner);
		style = new SimpleObjectProperty<>(ArrowStyle.NONE);
		arrowInset = new SimpleDoubleProperty(0d);
		arrowLength = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_LENGTH);
		arrowSizeDim = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_SIZE_DIM * Shape.PPC);
		arrowSizeNum = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_SIZE_NUM);
		dotSizeDim = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM * Shape.PPC);
		dotSizeNum = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM);
		tBarSizeDim = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_TBARSIZE_DIM * Shape.PPC);
		tBarSizeNum = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_TBARSIZE_NUM);
		bracketNum = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_BRACKET_LGTH);
		rBracketNum = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_RBRACKET_LGTH);
	}


	/**
	 * Creates an arrow from an other arrow.
	 * @param arrow The arrow to copy.
	 * @throws NullPointerException If the given arrow is null.
	 */
	ArrowImpl(final Arrow arrow, final ArrowableSingleShape owner) {
		this(owner);
		copy(Objects.requireNonNull(arrow));
	}


	@Override
	public void copy(final Arrow model) {
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
	public void bindFrom(final @NotNull Arrow arrow) {
		if(arrow == this) {
			return;
		}

		arrowInset.bind(arrow.arrowInsetProperty());
		arrowLength.bind(arrow.arrowLengthProperty());
		arrowSizeDim.bind(arrow.arrowSizeDimProperty());
		arrowSizeNum.bind(arrow.arrowSizeNumProperty());
		bracketNum.bind(arrow.bracketNumProperty());
		dotSizeDim.bind(arrow.dotSizeDimProperty());
		dotSizeNum.bind(arrow.dotSizeNumProperty());
		rBracketNum.bind(arrow.rBracketNumProperty());
		tBarSizeDim.bind(arrow.tBarSizeDimProperty());
		tBarSizeNum.bind(arrow.tBarSizeNumProperty());
	}

	@Override
	public void onChanges(final Runnable onChange) {
		final ChangeListener<Number> listener = (observable, oldValue, newValue) -> onChange.run();
		arrowInset.addListener(listener);
		arrowLength.addListener(listener);
		arrowSizeDim.addListener(listener);
		arrowSizeNum.addListener(listener);
		bracketNum.addListener(listener);
		dotSizeDim.addListener(listener);
		dotSizeNum.addListener(listener);
		rBracketNum.addListener(listener);
		tBarSizeDim.addListener(listener);
		tBarSizeNum.addListener(listener);
		style.addListener((observable, oldValue, newValue) -> onChange.run());
	}

	@Override
	public Line getArrowLine() {
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
	public @NotNull ArrowStyle getArrowStyle() {
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
	public @NotNull ArrowableSingleShape getShape() {
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
		}
	}

	@Override
	public void setArrowLength(final double lgth) {
		if(lgth >= 0d) {
			arrowLength.set(lgth);
		}
	}

	@Override
	public void setArrowSizeDim(final double size) {
		if(size > 0d) {
			arrowSizeDim.set(size);
		}
	}

	@Override
	public void setArrowSizeNum(final double size) {
		if(size >= 0d) {
			arrowSizeNum.set(size);
		}
	}


	@Override
	public void setArrowStyle(final @NotNull ArrowStyle arrowStyle) {
		style.set(arrowStyle);
	}

	@Override
	public void setBracketNum(final double brack) {
		if(brack >= 0d) {
			bracketNum.set(brack);
		}
	}

	@Override
	public void setDotSizeDim(final double dot) {
		if(dot > 0d) {
			dotSizeDim.set(dot);
		}
	}

	@Override
	public void setDotSizeNum(final double dot) {
		if(dot >= 0.1) {
			dotSizeNum.set(dot);
		}
	}

	@Override
	public void setRBracketNum(final double brack) {
		if(brack >= 0d) {
			rBracketNum.set(brack);
		}
	}

	@Override
	public void setTBarSizeDim(final double newtbarSizeDim) {
		if(newtbarSizeDim > 0d) {
			tBarSizeDim.set(newtbarSizeDim);
		}
	}

	@Override
	public void setTBarSizeNum(final double newtBarSizeNum) {
		if(newtBarSizeNum >= 0d) {
			tBarSizeNum.set(newtBarSizeNum);
		}
	}

	@Override
	public @NotNull ObjectProperty<ArrowStyle> styleProperty() {
		return style;
	}

	@Override
	public @NotNull DoubleProperty arrowSizeDimProperty() {
		return arrowSizeDim;
	}

	@Override
	public @NotNull DoubleProperty arrowSizeNumProperty() {
		return arrowSizeNum;
	}

	@Override
	public @NotNull DoubleProperty arrowLengthProperty() {
		return arrowLength;
	}

	@Override
	public @NotNull DoubleProperty arrowInsetProperty() {
		return arrowInset;
	}

	@Override
	public @NotNull DoubleProperty dotSizeDimProperty() {
		return dotSizeDim;
	}

	@Override
	public @NotNull DoubleProperty dotSizeNumProperty() {
		return dotSizeNum;
	}

	@Override
	public @NotNull DoubleProperty tBarSizeDimProperty() {
		return tBarSizeDim;
	}

	@Override
	public @NotNull DoubleProperty tBarSizeNumProperty() {
		return tBarSizeNum;
	}

	@Override
	public @NotNull DoubleProperty bracketNumProperty() {
		return bracketNum;
	}

	@Override
	public @NotNull DoubleProperty rBracketNumProperty() {
		return rBracketNum;
	}
}
