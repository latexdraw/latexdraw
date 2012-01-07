package org.malai.widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;

import javax.swing.Icon;

/**
 * A button icon can be used in a Button (JButton, MButton) to display a colour in a button.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 06/05/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MButtonIcon implements Icon, Serializable {
	private static final long serialVersionUID = 1L;

	/** The colour of the icon */
	private Color color;

	/** The colour of the borders of the icon */
	public final static Color EDGE = Color.BLACK;

	/** The width of the icon */
	public final static int WIDTH = 11;

	/** he height of the icon */
	public final static int HEIGHT = 11;


	/**
	 * The constructor using a colour.
	 * @param color The colour of the icon.
	 * @throws IllegalArgumentException if the given colour is null.
	 */
	public MButtonIcon(final Color color) {
		if(color==null)
			throw new IllegalArgumentException();

		this.color = color;
	}




	@Override
	public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
		if(g instanceof Graphics2D) {
			final Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		}

		final int width  = getIconWidth();
		final int height = getIconHeight();

		g.setColor(EDGE);
		g.fillRect(x, y, width, height);

		g.setColor(color);
		g.fillRect(x+2, y+2, width-4, height-4);
	}



	/**
	 * @return The colour of the interior of the icon
	 */
	public Color getColor() {
		return color;
	}



	/**
	 * Sets the colour of the interior of the icon.
	 * @param c The new Colour of the interior of the icon. If null, nothing is done.
	 */
	public void setColor(final Color c) {
		if(c!=null)
			color = c;
	}



	@Override
	public int getIconWidth() {
		return WIDTH;
	}



	@Override
	public int getIconHeight() {
		return HEIGHT;
	}
}
