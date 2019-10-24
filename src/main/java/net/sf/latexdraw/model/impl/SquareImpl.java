/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.impl;

import javafx.beans.property.DoubleProperty;
import net.sf.latexdraw.model.api.property.LineArcProp;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Square;
import org.jetbrains.annotations.NotNull;

/**
 * A model of a square.
 * @author Arnaud Blouin
 */
class SquareImpl extends SquaredShapeBase implements Square {
	private final @NotNull LineArcPropImpl lineArcProp;

	SquareImpl(final Point tl, final double width) {
		super(tl, width);
		lineArcProp = new LineArcPropImpl();
	}

	@Override
	public void copy(final Shape sh) {
		super.copy(sh);
		if(sh instanceof LineArcProp) {
			setLineArc(((LineArcProp) sh).getLineArc());
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
	public @NotNull DoubleProperty frameArcProperty() {
		return lineArcProp.frameArc;
	}
}
