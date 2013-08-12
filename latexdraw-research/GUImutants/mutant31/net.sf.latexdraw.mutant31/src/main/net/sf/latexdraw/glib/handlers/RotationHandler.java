package net.sf.latexdraw.glib.handlers;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;

/**
 * Defines a handler that rotates a shape.<br>
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
public class RotationHandler extends Handler<Path2D> {
	public static final BasicStroke STROKE = new BasicStroke(2.5f);

	private Arc2D arc;

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
		g.setColor(colour);
		g.setStroke(STROKE);
		g.draw(shape);
	}


	@Override
	public boolean contains(final double x, final double y) {
		return super.contains(x, y) || STROKE.createStrokedShape(shape).contains(x, y);
	}
}
