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
package net.sf.latexdraw.view;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.TicksStyle;
import org.jetbrains.annotations.NotNull;

public interface GenericAxes<T> {
	/** The interval between the labels and the axes. */
	double GAP_LABEL = 5d;

	Axes getModel();

	default void updatePathTicksX(final double gapx, final TicksStyle ticksStyle, final double tickLgth) {
		final Axes model = getModel();
		final boolean noArrowLeftX = isNoArrowLeftX();
		final boolean noArrowRightX = isNoArrowRightX();
		final double distX = model.getDistLabelsX();
		final double y = switch(ticksStyle) {
			case FULL -> tickLgth / 2d;
			case TOP -> 0d;
			default -> tickLgth;
		};

		for(double incrx = model.getIncrementX(), maxx = model.getGridMaxX() / distX, minx = model.getGridMinX() / distX, i = minx * incrx; i <= maxx * incrx;
			i += incrx * distX) {
			final int inti = (int) i;
			if(isElementPaintable(noArrowLeftX, noArrowRightX, minx, maxx, inti)) {
				final double x = inti * gapx;
				createPathTicksMoveTo(x, y);
				createPathTicksLineTo(x, y - tickLgth);
			}
		}
	}

	default void updatePathTicksY(final double gapy, final TicksStyle ticksStyle, final double tickLgth) {
		final Axes model = getModel();
		final boolean noArrowTopY = model.getArrowStyle(2) == ArrowStyle.NONE || model.getGridMaxY() == model.getOriginY();
		final boolean noArrowBotY = model.getArrowStyle(0) == ArrowStyle.NONE || model.getGridMinY() == model.getOriginY();
		final double distY = model.getDistLabelsY();
		final double x = switch(ticksStyle) {
			case FULL -> -tickLgth / 2d;
			case TOP -> 0d;
			default -> -tickLgth;
		};

		for(double incry = model.getIncrementY(), maxy = model.getGridMaxY() / distY, miny = model.getGridMinY() / distY, i = miny * incry; i <= maxy * incry;
			i += incry * distY) {
			final int inti = (int) i;
			if(isElementPaintable(noArrowBotY, noArrowTopY, miny, maxy, inti)) {
				final double y = -inti * gapy;
				createPathTicksMoveTo(x, y);
				createPathTicksLineTo(x + tickLgth, y);
			}
		}
	}

	default void updatePathLabelsY(final PlottingStyle ticksDisplay, final TicksStyle ticksStyle, final Text fontText) {
		final Axes model = getModel();
		final int origy = (int) model.getOriginY();
		final double gap;
		final double height = fontText.getBaselineOffset();
		final boolean noArrowTopY = model.getArrowStyle(2) == ArrowStyle.NONE || model.getGridMaxY() == model.getOriginY();
		final boolean noArrowBotY = model.getArrowStyle(0) == ArrowStyle.NONE || model.getGridMinY() == model.getOriginY();
		final boolean showOrig = model.isShowOrigin();
		final double distY = model.getDistLabelsY();
		final boolean xGE0 = model.getGridMinX() >= 0;
		final double gapy = getGapY();

		if(ticksStyle.isBottom() && ticksDisplay.isY()) {
			gap = -(model.getTicksSize() + model.getThickness() / 2d + GAP_LABEL);
		}else {
			gap = -(model.getThickness() / 2d + GAP_LABEL);
		}

		for(double incry = model.getIncrementY(), maxy = model.getGridMaxY() / distY, miny = model.getGridMinY() / distY, i = miny * incry; i <= maxy * incry;
			i += incry * distY) {
			final int inti = (int) i;
			if((inti != 0 || (showOrig && xGE0)) && isElementPaintable(noArrowBotY, noArrowTopY, miny, maxy, inti)) {
				final String str = String.valueOf(inti + origy);
				fontText.setText(str);
				createTextLabel(str, gap - fontText.getBoundsInLocal().getWidth(), height / 2d - inti * gapy, fontText.getFont());
			}
		}
	}


	default boolean isNoArrowLeftX() {
		final Axes model = getModel();
		return model.getArrowStyle(1) == ArrowStyle.NONE || MathUtils.INST.equalsDouble(model.getGridMinX(), model.getOriginX());
	}

	default boolean isNoArrowRightX() {
		final Axes model = getModel();
		return model.getArrowStyle(3) == ArrowStyle.NONE || MathUtils.INST.equalsDouble(model.getGridMaxX(), model.getOriginX());
	}


	/**
	 * Updates the labels path by drawing the labels of the X-axis.
	 */
	default void updatePathLabelsX(final @NotNull PlottingStyle ticksDisplay, final @NotNull TicksStyle ticksStyle, final @NotNull Text fontText) {
		final Axes model = getModel();
		// Painting the labels on the X-axis.
		final int origx = (int) model.getOriginX();
		final double gap = (ticksDisplay.isX() && ticksStyle.isBottom() ? model.getTicksSize() : 0d) + model.getThickness() / 2d + GAP_LABEL;
		final double sep = model.getGridMaxY() <= -model.getOriginY() ? -gap - GAP_LABEL : gap + fontText.getBaselineOffset();
		final boolean noArrowLeftX = isNoArrowLeftX();
		final boolean noArrowRightX = isNoArrowRightX();
		final boolean showOrig = model.isShowOrigin();
		final double distX = model.getDistLabelsX();
		final boolean yGE0 = model.getGridMinY() >= 0;
		final double gapx = getGapX();

		for(double incrx = model.getIncrementX(), maxx = model.getGridMaxX() / distX, minx = model.getGridMinX() / distX, i = minx * incrx; i <= maxx * incrx;
			i += incrx * distX) {
			final int inti = (int) i;
			if((inti != 0 || (showOrig && yGE0)) && isElementPaintable(noArrowLeftX, noArrowRightX, minx, maxx, inti)) {
				final String str = String.valueOf(inti + origx);
				fontText.setText(str);
				createTextLabel(str, inti * gapx - fontText.getBoundsInLocal().getWidth() / 2d, sep, fontText.getFont());
			}
		}
	}

	default void updatePathTicks() {
		final Axes model = getModel();
		final PlottingStyle ticksDisplay = model.getTicksDisplayed();
		final TicksStyle ticksStyle = model.getTicksStyle();
		final double tickLgth = ticksStyle == TicksStyle.FULL ? model.getTicksSize() * 2d : model.getTicksSize();

		if(ticksDisplay.isX()) {
			updatePathTicksX(getGapX(), ticksStyle, tickLgth);
		}

		if(ticksDisplay.isY()) {
			updatePathTicksY(getGapY(), ticksStyle, tickLgth);
		}

		disablePathTicks(!ticksDisplay.isX() && !ticksDisplay.isY());
		setPathTicksFill(Color.BLACK);
	}


	default void updatePathLabels() {
		final Axes model = getModel();
		final Font font = new Font("cmr10", model.getLabelsSize()); //NON-NLS
		final PlottingStyle labelsDisplay = model.getLabelsDisplayed();
		final PlottingStyle ticksDisplay = model.getTicksDisplayed();
		final TicksStyle ticksStyle = model.getTicksStyle();
		// This fake text is used to compute widths and heights and other font metrics of a current text.
		final Text fooText = new Text("foo"); //NON-NLS
		fooText.setFont(font);

		if(labelsDisplay.isX()) {
			updatePathLabelsX(ticksDisplay, ticksStyle, fooText);
		}

		if(labelsDisplay.isY()) {
			updatePathLabelsY(ticksDisplay, ticksStyle, fooText);
		}
	}

	T createTextLabel(final String text, final double x, final double y, final Font font);

	void createPathTicksMoveTo(final double x, final double y);

	void createPathTicksLineTo(final double x, final double y);

	void disablePathTicks(final boolean disable);

	void setPathTicksFill(final Color color);

	/**
	 * @return True if a ticks or a label corresponding to the given parameter can be painted.
	 */
	default boolean isElementPaintable(final boolean noArrow1, final boolean noArrow2, final double min, final double max, final double i) {
		return (noArrow2 || !MathUtils.INST.equalsDouble(max, i)) && (noArrow1 || !MathUtils.INST.equalsDouble(min, i));
	}

	default double getGapX() {
		final Axes model = getModel();
		return MathUtils.INST.equalsDouble(model.getDistLabelsX(), 0d) ? Shape.PPC : model.getDistLabelsX() / model.getIncrementX() * Shape.PPC;
	}

	default double getGapY() {
		final Axes model = getModel();
		return MathUtils.INST.equalsDouble(model.getDistLabelsY(), 0d) ? Shape.PPC : model.getDistLabelsY() / model.getIncrementY() * Shape.PPC;
	}
}
