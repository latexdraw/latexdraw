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

import java.util.List;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IArrowableShape;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * Implementation of IArrowableShape.
 * @author Arnaud Blouin
 */
interface LArrowableShape extends IArrowableSingleShape {
	@Override
	default void copy(final IShape sh) {
		if(sh instanceof IArrowableShape) {
			final IArrowableShape arr = (IArrowableShape) sh;
			int i = 0;
			final int nbArrowsArr = arr.getNbArrows();
			final int nbArrows = getNbArrows();

			while(i < nbArrows && i < nbArrowsArr) {
				setArrowStyle(arr.getArrowStyle(i), i);
				i++;
			}
			setArrowInset(arr.getArrowInset());
			setArrowLength(arr.getArrowLength());
			setArrowSizeDim(arr.getArrowSizeDim());
			setArrowSizeNum(arr.getArrowSizeNum());
			setDotSizeDim(arr.getDotSizeDim());
			setDotSizeNum(arr.getDotSizeNum());
			setBracketNum(arr.getBracketNum());
			setRBracketNum(arr.getRBracketNum());
			setTBarSizeDim(arr.getTBarSizeDim());
			setTBarSizeNum(arr.getTBarSizeNum());
		}
	}

	@Override
	default IArrow getArrowAt(final int position) {
		final List<IArrow> arrows = getArrows();
		if(arrows.isEmpty() || position >= arrows.size() || position < -1) {
			return null;
		}
		if(position == -1) {
			return arrows.get(arrows.size() - 1);
		}
		return arrows.get(position);
	}

	@Override
	default int getArrowIndex(final IArrow arrow) {
		return getArrows().indexOf(arrow);
	}

	@Override
	default int getNbArrows() {
		return getArrows().size();
	}

	@Override
	default void setDotSizeDim(final double dotSizeDim) {
		getArrows().forEach(arr -> arr.setDotSizeDim(dotSizeDim));
	}

	@Override
	default void setDotSizeNum(final double dotSizeNum) {
		getArrows().forEach(arr -> arr.setDotSizeNum(dotSizeNum));
	}

	@Override
	default void setTBarSizeNum(final double tbarSizeNum) {
		getArrows().forEach(arr -> arr.setTBarSizeNum(tbarSizeNum));
	}

	@Override
	default void setTBarSizeDim(final double tbarSizeDim) {
		getArrows().forEach(arr -> arr.setTBarSizeDim(tbarSizeDim));
	}

	@Override
	default double getTBarSizeDim() {
		return getArrows().isEmpty() ? Double.NaN : getArrows().get(0).getTBarSizeDim();
	}

	@Override
	default double getTBarSizeNum() {
		return getArrows().isEmpty() ? Double.NaN : getArrows().get(0).getTBarSizeNum();
	}

	@Override
	default void setRBracketNum(final double rBracketNum) {
		getArrows().forEach(arr -> arr.setRBracketNum(rBracketNum));
	}

	@Override
	default void setBracketNum(final double bracketNum) {
		getArrows().forEach(arr -> arr.setBracketNum(bracketNum));
	}

	@Override
	default void setArrowLength(final double lgth) {
		getArrows().forEach(arr -> arr.setArrowLength(lgth));
	}

	@Override
	default void setArrowSizeDim(final double arrowSizeDim) {
		getArrows().forEach(arr -> arr.setArrowSizeDim(arrowSizeDim));
	}

	@Override
	default void setArrowSizeNum(final double arrowSizeNum) {
		getArrows().forEach(arr -> arr.setArrowSizeNum(arrowSizeNum));
	}

	@Override
	default void setArrowInset(final double inset) {
		getArrows().forEach(arr -> arr.setArrowInset(inset));
	}

	@Override
	default double getDotSizeDim() {
		return getArrows().isEmpty() ? Double.NaN : getArrows().get(0).getDotSizeDim();
	}

	@Override
	default double getDotSizeNum() {
		return getArrows().isEmpty() ? Double.NaN : getArrows().get(0).getDotSizeNum();
	}

	@Override
	default double getBracketNum() {
		return getArrows().isEmpty() ? Double.NaN : getArrows().get(0).getBracketNum();
	}

	@Override
	default double getArrowSizeNum() {
		return getArrows().isEmpty() ? Double.NaN : getArrows().get(0).getArrowSizeNum();
	}

	@Override
	default double getArrowSizeDim() {
		return getArrows().isEmpty() ? Double.NaN : getArrows().get(0).getArrowSizeDim();
	}

	@Override
	default double getArrowInset() {
		return getArrows().isEmpty() ? Double.NaN : getArrows().get(0).getArrowInset();
	}

	@Override
	default double getArrowLength() {
		return getArrows().isEmpty() ? Double.NaN : getArrows().get(0).getArrowLength();
	}

	@Override
	default double getRBracketNum() {
		return getArrows().isEmpty() ? Double.NaN : getArrows().get(0).getRBracketNum();
	}

	@Override
	default void setArrowStyle(final ArrowStyle style, final int position) {
		if(style != null) {
			final IArrow arr = getArrowAt(position);
			if(arr != null) {
				arr.setArrowStyle(style);
			}
		}
	}

	@Override
	default ArrowStyle getArrowStyle(final int position) {
		final IArrow arr = getArrowAt(position);
		return arr == null ? null : arr.getArrowStyle();
	}

	@Override
	default void setOnArrowChanged(final Runnable run) {
		//TODO
	}
}
