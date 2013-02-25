package net.sf.latexdraw.ui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import net.sf.latexdraw.glib.ui.ICanvas;

/**
 * This class defines an X-scale ruler.<br>
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
public class XScaleRuler extends ScaleRuler {
	private static final long serialVersionUID = 1L;

	/** The y rule. used to get information about where the x-scale must be displayed. */
	protected YScaleRuler yruler;

	/**
	 * Defines a x-scale ruler.
	 * @param canvas The canvas to supervises.
	 * @param yruler The opposite ruler. Can be null.
	 * @throws IllegalArgumentException if the given canvas is null.
	 * @since 3.0
	 */
	public XScaleRuler(final ICanvas canvas, final YScaleRuler yruler) {
		super(canvas);

		this.yruler = yruler;

		setPreferredSize(new Dimension(500, SIZE));
	}


	@Override
	protected void drawLine(final Graphics2D g2, final double positionA, final double positionB1, final double positionB2) {
		g2.draw(new Line2D.Double(positionA, positionB1, positionA, positionB2));
	}


	@Override
	protected double getLength() {
		return getSize().getWidth();
	}


	@Override
	protected double getStart() {
		return yruler==null || !yruler.isVisible() ? 0. : SIZE;
	}
}
