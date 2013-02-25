package net.sf.latexdraw.ui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Objects;

/**
 * Allows to display a given image in a panel(canvas).<br>
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
 * 01/20/06<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class DisplayCanvas extends Canvas {
	private static final long serialVersionUID = 1L;

	/** The image to display */
	protected Image image;


	/**
	 * The constructor by default
	 * @param img The image to display
	 * @throws IllegalArgumentException If the given image is null.
	 */
	public DisplayCanvas(final Image img) {
		super();
		image = Objects.requireNonNull(img);
		prepareImage(image, this);
		setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
	}


	@Override
	public void paint(final Graphics g) {
		g.drawImage(image, (int)Math.max(0,(getWidth()-image.getWidth(null))/2.), 0, this);
	}


	/**
	 * Flushes the image.
	 * @since 3.0
	 */
	public void flush() {
		image.flush();
	}
}
