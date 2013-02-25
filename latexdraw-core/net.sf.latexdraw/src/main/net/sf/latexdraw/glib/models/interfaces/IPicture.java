package net.sf.latexdraw.glib.models.interfaces;

import java.awt.Image;
import java.io.IOException;

/**
 * Defines an interface that classes defining a picture should implement.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/03/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IPicture extends IPositionShape {
	@Override
	IPicture duplicate();

	/**
	 * Sets the new picture.
	 * @param pathSource the pathSource to set.
	 * @throws IOException If a problem while reading/writing pictures occurs.
	 */
	void setPathSource(String pathSource) throws IOException;

	/**
	 * @return the pathSource.
	 */
	String getPathSource();

	/**
	 * @return the pathTarget.
	 */
	String getPathTarget();

	/**
	 * @return the image.
	 */
	Image getImage();

	/**
	 * @return The width of the picture.
	 * @since 3.0
	 */
	int getWidth();

	/**
	 * @return The height of the picture.
	 * @since 3.0
	 */
	int getHeight();
}
