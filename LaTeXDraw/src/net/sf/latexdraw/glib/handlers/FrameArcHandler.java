package net.sf.latexdraw.glib.handlers;

import java.awt.geom.Ellipse2D;

import net.sf.latexdraw.glib.models.interfaces.IRectangle;

/**
 * Defines a handler to change the frame arc attribute.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 08/28/11<br>
 * @author Arnaud BLOUIN<br>
 * @version 3.0<br>
 */
public class FrameArcHandler extends Handler<Ellipse2D> {
	/**
	 * Creates the handler.
	 */
	public FrameArcHandler() {
		super();
		shape = new Ellipse2D.Double();
		size  = size/4.*3.;
	}


	@Override
	protected void updateShape() {
		shape.setFrame(point.getX(), point.getY()-size, size, size);
	}


	/**
	 * Updates the handler using the given rectangle.
	 * @param frame The rectangle to use to update the handler.
	 * @since 3.0
	 */
	public void updateUsingFrame(final IRectangle frame, final double x, final double y) {
		if(frame==null) return ;

		if(frame.getHeight()>frame.getWidth())
			setPoint(x-size, y+size);
		else
			setPoint(x, y);
	}
}
