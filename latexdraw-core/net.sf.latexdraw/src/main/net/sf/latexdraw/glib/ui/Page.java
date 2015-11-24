package net.sf.latexdraw.glib.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

/**
 * The different page sizes that can be used.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *  @since 3.1
 *<br>
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

	/** The stroke used for painting the page. */
	private static final BasicStroke STROKE_PAGE = new BasicStroke(1f);

	/** The gap between the page and its shadow. */
	private static final int GAP_SHADOW = 3;

	/** The size of the shadow of the page. */
	private static final int SIZE_SHADOW = 4;

	private final Rectangle2D pageRec = new Rectangle2D.Double(0.0, 0.0, getWidth()*IShape.PPC, getHeight()*IShape.PPC);
	private final Rectangle2D shadowRec1 = new Rectangle2D.Double(pageRec.getMaxX(), pageRec.getMinY()+GAP_SHADOW, SIZE_SHADOW, pageRec.getHeight()); 
	private final Rectangle2D shadowRec2 = new Rectangle2D.Double(pageRec.getMinX()+GAP_SHADOW, pageRec.getMaxY(), pageRec.getWidth(), SIZE_SHADOW); 
	
	/**
	 * Paints the page into the given graphics.
	 * @param g The graphics to use.
	 * @param clip The rectangle that requires to be painted.
	 * @throws NullPointerException If the given graphics or point is null.
	 * @since 3.1
	 */
	public void paint(final Graphics2D g, final Rectangle clip) {
		if(clip!=null && !clip.contains(pageRec) && !clip.intersects(pageRec)) return;

		g.setStroke(STROKE_PAGE);
		g.setColor(Color.GRAY);
		g.fill(shadowRec1);
		g.fill(shadowRec2);
		g.setColor(Color.BLACK);
		g.draw(pageRec);
	}
}
