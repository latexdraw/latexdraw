package net.sf.latexdraw.ui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import net.sf.latexdraw.glib.ui.ICanvas;

/**
 * This class defines an Y-scale ruler.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 11/12/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class YScaleRuler extends ScaleRuler {
	private static final long serialVersionUID = 1L;

	/**
	 * Defines a y-scale ruler.
	 * @param canvas The canvas to supervises.
	 * @throws IllegalArgumentException if the given canvas is null.
	 * @since 3.0
	 */
	public YScaleRuler(final ICanvas canvas) {
		super(canvas);

		setPreferredSize(new Dimension(SIZE, 500));
	}

	@Override
	protected void drawLine(final Graphics2D g2, final double positionA, final double positionB1, final double positionB2) {
		g2.draw(new Line2D.Double(positionB1, positionA, positionB2, positionA));
	}


	@Override
	protected double getLength() {
		return getSize().getHeight();
	}

	@Override
	protected double getStart() {
		return 0.;
	}
}
