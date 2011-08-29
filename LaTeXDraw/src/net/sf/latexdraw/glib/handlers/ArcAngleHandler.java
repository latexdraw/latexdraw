package net.sf.latexdraw.glib.handlers;

import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.IArc;

/**
 * Defines a handler that changes the start/end angle of an arc.<br>
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
public class ArcAngleHandler extends Handler<Rectangle2D> {
	/** Defines if the handled angle is the starting or the ending angle. */
	protected boolean start;

	/**
	 * Creates and initialises an arc angle handler.
	 * @param start Defines if the handled angle is the starting or the ending angle.
	 * @since 3.0
	 */
	public ArcAngleHandler(final boolean start) {
		super();
		this.start  = start;
		shape 		= new Rectangle2D.Double();
		updateShape();
	}


	@Override
	protected void updateShape() {
		shape.setFrame(point.getX()-size/2., point.getY()-size/2., size, size);
	}


	/**
	 * Updates the handler using the given arc as reference.
	 * @param arc The arc that will be used as reference.
	 * @since 3.0
	 */
	public void updateFromArc(final IArc arc) {
		if(arc==null) return;

		if(start)
			point.setPoint(arc.getStartPoint().getMiddlePoint(arc.getGravityCentre()));
		else
			point.setPoint(arc.getEndPoint().getMiddlePoint(arc.getGravityCentre()));

		updateShape();
	}
}
