package net.sf.latexdraw.glib.models.interfaces.shape;


/**
 * Defines the concept of colour.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-10-13<br>
 * @author Arnaud BLOUIN
 * @version 4.0
 * @since 4.0
 */
public interface Color {
	/**
	 * @return The AWT color.
	 */
	java.awt.Color toAWT();
	
	/**
	 * @return The red.
	 */
	double getR();
	
	/**
	 * @return The green.
	 */
	double getG();
	
	/**
	 * @return The blue.
	 */
	double getB();
	
	/**
	 * @return The opacity.
	 */
	double getO();
	
	/**
	 * Sets the red.
	 * @param r the red to set
	 */
	void setR(final double red);
	
	/**
	 * Sets the green.
	 * @param g the green to set
	 */
	void setG(final double green);
	
	/**
	 * Sets the blue.
	 * @param b the blue to set
	 */
	void setB(final double blue);
	
	/**
	 * Sets the opacity.
	 * @param o the opacity to set
	 */
	void setO(final double opacity);
}
