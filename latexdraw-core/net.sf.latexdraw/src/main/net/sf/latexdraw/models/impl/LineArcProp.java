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

import net.sf.latexdraw.models.GLibUtilities;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;

/**
 * Line arc properties.
 * To turn as a trait as soon as Java will support private attributes in interfaces.
 */
class LineArcProp implements ILineArcProp {
	/** The radius of arcs drawn at the corners of lines. */
	private double frameArc;

	LineArcProp() {
		frameArc = 0.0;
	}

	@Override
	public double getLineArc() {
		return frameArc;
	}

	@Override
	public boolean isRoundCorner() {
		return frameArc > 0.0;
	}

	@Override
	public void setLineArc(final double arc) {
		if(GLibUtilities.isValidCoordinate(arc) && arc >= 0.0 && arc <= 1.0) {
			frameArc = arc;
		}
	}
}