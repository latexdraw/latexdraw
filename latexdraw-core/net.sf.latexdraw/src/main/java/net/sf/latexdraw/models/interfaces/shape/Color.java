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
package net.sf.latexdraw.models.interfaces.shape;


/**
 * The API for colours.
 * @author Arnaud BLOUIN
 */
public interface Color {
	/** @return The AWT color. */
	java.awt.Color toAWT();

	/** @return The JavaFX color. */
	javafx.scene.paint.Color toJFX();

	/**
	 * @return The red channel.
	 * @throws IllegalArgumentException If the value in not in [0,1].
	 */
	double getR();

	/**
	 * Sets the red channel.
	 * @param red the red to set
	 */
	void setR(final double red);

	/**
	 * @return The green channel.
	 * @throws IllegalArgumentException If the value in not in [0,1].
	 */
	double getG();

	/**
	 * Sets the green channel.
	 * @param green the green to set
	 */
	void setG(final double green);

	/**
	 * @return The blue channel.
	 * @throws IllegalArgumentException If the value in not in [0,1].
	 */
	double getB();

	/**
	 * Sets the blue channel.
	 * @param blue the blue to set
	 */
	void setB(final double blue);

	/**
	 * @return The opacity channel.
	 * @throws IllegalArgumentException If the value in not in [0,1].
	 */
	double getO();

	/**
	 * Sets the opacity channel.
	 * @param opacity the opacity to set
	 */
	void setO(final double opacity);
}
