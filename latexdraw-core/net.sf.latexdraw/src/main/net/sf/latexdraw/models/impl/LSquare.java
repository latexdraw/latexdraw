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

import javafx.beans.property.DoubleProperty;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import org.eclipse.jdt.annotation.NonNull;

/**
 * A model of a square.
 */
class LSquare extends LSquaredShape implements ISquare {
	private final LineArcProp lineArcProp;

	LSquare(final IPoint tl, final double width) {
		super(tl, width);
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

	@Override
	public @NonNull DoubleProperty frameArcProperty() {
		return lineArcProp.frameArc;
	}
}
