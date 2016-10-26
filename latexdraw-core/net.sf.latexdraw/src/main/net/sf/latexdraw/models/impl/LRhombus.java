package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;

/**
 * Defines a model of a rhombus.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/05/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LRhombus extends LRectangularShape implements IRhombus {
	/**
	 * Creates a rhombus at the bottom-left position (0,0) with width=height=1.
	 */
	protected LRhombus() {
		this(ShapeFactory.createPoint(0.5, -0.5), 1, 1);
	}


	/**
	 * Creates a rhombus.
	 * @param centre The centre of the rhombus.
	 * @param width The width of the rhombus.
	 * @param height The height of the rhombus.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 * @throws NullPointerException If the given point is null.
	 */
	protected LRhombus(final IPoint centre, final double width, final double height) {
		super(ShapeFactory.createPoint(centre.getX()-width/2., centre.getY()-height/2.),
				ShapeFactory.createPoint(centre.getX()+width/2., centre.getY()+height/2.));
	}
}
