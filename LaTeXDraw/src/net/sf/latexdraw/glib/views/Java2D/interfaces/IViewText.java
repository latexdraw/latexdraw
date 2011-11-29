package net.sf.latexdraw.glib.views.Java2D.interfaces;

import java.awt.Image;

/**
 * This interface defines a view of a text.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 11/18/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public interface IViewText extends IViewShape {
	/**
	 * Updates the image.
	 * @since 3.0
	 */
	void updateImage();

	/**
	 * @return the image.
	 * @since 3.0
	 */
	Image getImage();

	/**
	 * @return The precise latex error messages that the latex compilation produced.
	 * @since 3.0
	 */
	String getLatexErrorMessageFromLog();

	/**
	 * @return The LaTeX document that will be compiled in order to get the picture of the text.
	 * @since 3.0
	 */
	String getLaTeXDocument();
}
