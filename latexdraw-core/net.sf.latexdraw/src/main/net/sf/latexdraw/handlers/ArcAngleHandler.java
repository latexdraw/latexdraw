package net.sf.latexdraw.handlers;

import java.awt.geom.Rectangle2D;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * Defines a handler that changes the start/end angle of an arc.
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 * 08/28/11
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class ArcAngleHandler extends Handler<Rectangle2D, IArc> {
	/** Defines if the handled angle is the starting or the ending angle. */
	protected boolean start;

	/**
	 * Creates and initialises an arc angle handler.
	 * @param isStart Defines if the handled angle is the starting or the ending angle.
	 * @since 3.0
	 */
	public ArcAngleHandler(final boolean isStart) {
		super();
		this.start  = isStart;
		shape 		= new Rectangle2D.Double();
		updateShape();
	}


	@Override
	protected void updateShape() {
		shape.setFrame(point.getX()-size/2., point.getY()-size/2., size, size);
	}


	@Override
	public void update(final IArc arc, final double zoom) {
		if(arc==null) return;

		final IPoint zoomedGC = arc.getGravityCentre().zoom(zoom);
		final double rotAngle = arc.getRotationAngle();
		IPoint pt;

		if(start)
			pt = arc.getStartPoint();
		else
			pt = arc.getEndPoint();

		// If the shape is rotated, the handler's position must fit the rotation angle.
		if(!MathUtils.INST.equalsDouble(rotAngle, 0.))
			pt = pt.rotatePoint(zoomedGC, rotAngle);

		point.setPoint(pt.zoom(zoom).getMiddlePoint(zoomedGC));
		super.update(arc, zoom);
	}
}
