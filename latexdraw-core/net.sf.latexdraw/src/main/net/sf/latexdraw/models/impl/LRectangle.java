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

import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * A model of a rectangle.
 */
class LRectangle extends LRectangularShape implements IRectangle {
	private final LineArcProp lineArcProp;

	protected LRectangle(final IPoint tl, final IPoint br) {
		super(tl, br);
		lineArcProp = new LineArcProp();
	}

	@Override
	public void copy(final IShape sh) {
		super.copy(sh);
		if(sh instanceof ILineArcProp) {
			setLineArc(((ILineArcProp) sh).getLineArc());
		}
	}

	@Override
	public double getLineArc() {
		return lineArcProp.getLineArc();
	}

	@Override
	public void setLineArc(final double lineArc) {
		lineArcProp.setLineArc(lineArc);
	}

	@Override
	public boolean isRoundCorner() {
		return lineArcProp.isRoundCorner();
	}
}
