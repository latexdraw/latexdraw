/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.util;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Several cursors used in LaTeXDraw.
 * @author Arnaud BLOUIN
 * @since 1.9
 */
public final class LCursor {
	/** The singleton. */
	public static final LCursor INSTANCE = new LCursor();

	/** The cursor that should be used during rotation. */
	// FIXME: use the cursor on rotation.
	public static final Cursor CURSOR_ROTATE = INSTANCE.getCursor("/main/res/cursors/RotCWDown.gif", "rotate");//$NON-NLS-1$//$NON-NLS-2$


	private LCursor() {
		super();
	}


	/**
	 * Create a cursor define by the picture corresponding to the path.
	 * @param path The path of the picture of the cursor.
	 * @param name The name which will be given to the cursor.
	 * @return The created cursor.
	 * @since 1.9
	 */
	public Cursor getCursor(final String path, final String name) {
        final BufferedImage bi = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphic = (Graphics2D)bi.getGraphics();
        final URL url = LCursor.class.getClass().getResource(path);
        final ImageIcon img = new ImageIcon(url);

        graphic.drawImage(img.getImage(), null, null);

		return Toolkit.getDefaultToolkit().createCustomCursor(bi,new Point(0,0), name);
	}

}
