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
package net.sf.latexdraw.models.interfaces.shape;

import javafx.scene.image.Image;

import java.io.IOException;

/**
 * An interface that classes defining a picture should implement.
 */
public interface IPicture extends IPositionShape {
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
}
