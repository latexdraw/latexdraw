package net.sf.latexdraw.glib.models.interfaces;

/**
 * Defines an interface that classes defining an ellipse should implement.<br>
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
 * 07/02/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IEllipse extends IRectangularShape {
	/**
	 * @return The half of the biggest axe.
	 * @since 3.0
	 */
	double getA();


	/**
	 * @return The half of the smallest axe.
	 * @since 3.0
	 */
	double getB();


	/**
	 * @return The x radius of the ellipse.
	 * @since 3.0
	 */
	double getRx();

	/**
	 * @return The y radius of the ellipse.
	 * @since 3.0
	 */
	double getRy();

	/**
	 * Sets the x radius of the ellipse.
	 * @param rx The new x radius.
	 * @since 3.0
	 */
	void setRx(final double rx);

	/**
	 * Sets the y radius of the ellipse.
	 * @param ry The new y radius.
	 * @since 3.0
	 */
	void setRy(final double ry);


	/**
	 * Translates the shape to its new centre.
	 * @param centre The new centre.
	 * @since 3.0
	 */
	void setCentre(final IPoint centre);


	/**
	 * Computes the intersection points between the ellipse and the given line.
	 * @param line The line to test.
	 * @return 1 or 2 points or null.
	 * @since 3.0
	 */
	IPoint[] getIntersection(final ILine line);
}
