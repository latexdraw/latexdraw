package net.sf.latexdraw.handlers;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * Defines a handler that rotates a shape.
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
public class RotationHandler extends Handler<Path2D, IShape> {
	public static final BasicStroke STROKE = new BasicStroke(2.5f);

	private final Arc2D arc;

	/**
	 * The constructor by default.
	 */
	public RotationHandler() {
		super();
		shape	= new Path2D.Double();
		arc		= new Arc2D.Double();
		updateShape();
	}


	@Override
	protected void updateShape() {
		arc.setArc(point.getX()+STROKE.getLineWidth()/2., point.getY()+STROKE.getLineWidth()/2., size, size, 0, 270, Arc2D.OPEN);
		shape.reset();
		shape.append(arc, false);
		shape.moveTo(point.getX()+STROKE.getLineWidth()/2.+size+2., point.getY()+STROKE.getLineWidth()/2.+size/2.);
		shape.lineTo(point.getX()+STROKE.getLineWidth()/2.+size-2., point.getY()+STROKE.getLineWidth()/2.+size/2.);
		shape.lineTo(point.getX()+STROKE.getLineWidth()/2.+size, point.getY()+STROKE.getLineWidth()/2.+size/2.+4.);
		shape.closePath();
	}


	@Override
	public void paint(final Graphics2D g) {
		g.setColor(colour.toAWT());
		g.setStroke(STROKE);
		g.draw(shape);
	}


	@Override
	public boolean contains(final double x, final double y) {
		return super.contains(x, y) || STROKE.createStrokedShape(shape).contains(x, y);
	}
}
