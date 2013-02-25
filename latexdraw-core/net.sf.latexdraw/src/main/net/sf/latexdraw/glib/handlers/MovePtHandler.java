package net.sf.latexdraw.glib.handlers;

import java.awt.geom.Rectangle2D;

/**
 * Defines a handler that moves a point of a shape.<br>
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
 * 08/28/11<br>
 * @author Arnaud BLOUIN<br>
 * @version 3.0<br>
 */
public class MovePtHandler extends Handler<Rectangle2D> {
	/** The index of the point in its shape. */
	protected int indexPt;


	/**
	 * The constructor by default.
	 * @param indexPt The index of the point in its shape.
	 */
	public MovePtHandler(final int indexPt) {
		super();
		shape  = new Rectangle2D.Double();
		this.indexPt = indexPt;
		updateShape();
	}


	@Override
	protected void updateShape() {
		shape.setFrame(point.getX()-size/2., point.getY()-size/2., size, size);
	}


	/**
	 * @return The index of the point in its shape.
	 * @since 3.0
	 */
	public int getIndexPt() {
		return indexPt;
	}
}
