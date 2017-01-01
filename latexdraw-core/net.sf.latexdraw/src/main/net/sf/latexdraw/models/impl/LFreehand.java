/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * An implementation of a free hand shape.
 */
class LFreehand extends LModifiablePointsShape implements IFreehand {
	/** The type of the curves of the shape. */
	private FreeHandStyle type;
	/** The interval to consider while painting the shape. */
	private int interval;
	/** Defines if the drawing is opened of closed. */
	private boolean open;


	/**
	 * Creates and initialises a freehand model.
	 * @throws IllegalArgumentException If the given point is not valid.
	 * @since 3.0
	 */
	LFreehand() {
		super();
		type = FreeHandStyle.CURVES;
		interval = 5;
		open = true;
	}

	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IFreeHandProp) {
			final IFreeHandProp fh = (IFreeHandProp) sh;
			open = fh.isOpen();
			interval = fh.getInterval();
			type = fh.getType();
		}
	}

	@Override
	public int getInterval() {
		return interval;
	}


	@Override
	public FreeHandStyle getType() {
		return type;
	}


	@Override
	public boolean isOpen() {
		return open;
	}


	@Override
	public void setInterval(final int newInterval) {
		if(newInterval > 0) {
			interval = newInterval;
		}
	}


	@Override
	public void setOpen(final boolean isOpen) {
		open = isOpen;
	}


	@Override
	public void setType(final FreeHandStyle freeHandStyle) {
		if(freeHandStyle != null) {
			type = freeHandStyle;
		}
	}

	@Override
	public boolean isBordersMovable() {
		return false;
	}

	@Override
	public boolean isDbleBorderable() {
		return false;
	}

	@Override
	public boolean isFillable() {
		return true;
	}

	@Override
	public boolean isInteriorStylable() {
		return true;
	}

	@Override
	public boolean isLineStylable() {
		return true;
	}

	@Override
	public boolean isShadowable() {
		return true;
	}

	@Override
	public boolean isShowPtsable() {
		return false;
	}

	@Override
	public boolean isThicknessable() {
		return true;
	}
}
