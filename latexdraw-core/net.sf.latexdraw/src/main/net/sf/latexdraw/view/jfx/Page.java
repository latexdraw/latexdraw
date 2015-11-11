package net.sf.latexdraw.view.jfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

/**
 * The different page sizes that can be used.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *  @since 4.0
 *<br>
 *@author Arnaud Blouin
 *@date 2014-10-15
 */
public enum Page {
	USLETTER {
		@Override public double getWidth() { return 21.6; }
		@Override public double getHeight() { return 27.9; }
	};

	/**
	 * @return The width of the page in CM.
	 * @since 3.1
	 */
	public abstract double getWidth();

	/**
	 * @return The height of the page in CM.
	 * @since 3.1
	 */
	public abstract double getHeight();

	/** The gap between the page and its shadow. */
	private static final int GAP_SHADOW = 3;

	/** The size of the shadow of the page. */
	private static final int SIZE_SHADOW = 4;

	/**
	 * Paints the page into the given graphics.
	 * @param gc The graphics to use.
	 * @param origin The position in the graphics corresponding to the origin.
	 * @throws NullPointerException If the given graphics or point is null.
	 * @since 4.0
	 */
	public void paint(final GraphicsContext gc, final IPoint origin) {
		final Rectangle page = new Rectangle(origin.getX(), origin.getY(),getWidth()*IShape.PPC, getHeight()*IShape.PPC);
		gc.setLineWidth(1.0);
		gc.setStroke(Color.GRAY);
		gc.fillRect(page.getX()+page.getWidth(), page.getY()+GAP_SHADOW, SIZE_SHADOW, page.getHeight());
		gc.fillRect(page.getX()+GAP_SHADOW, page.getY()+page.getHeight(), page.getWidth(), SIZE_SHADOW);
		gc.setStroke(Color.BLACK);
		gc.strokeRect(page.getX(), page.getY(), page.getWidth(), page.getHeight());
	}
}
