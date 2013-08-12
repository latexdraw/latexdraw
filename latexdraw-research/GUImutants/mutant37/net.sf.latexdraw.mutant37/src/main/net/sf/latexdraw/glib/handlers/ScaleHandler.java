package net.sf.latexdraw.glib.handlers;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import net.sf.latexdraw.glib.models.interfaces.IShape.Position;

/**
 * Defines a handler that scales a shape.<br>
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
public class ScaleHandler extends Handler<Path2D> {
	/** The position of the possible scalings. */
	protected Position position;

	/**
	 * The constructor by default.
	 * @param position The position of the handler.
	 * @see Position
	 */
	public ScaleHandler(final Position position) {
		super();
		this.position 	= Objects.requireNonNull(position);
		shape			= new Path2D.Double();
		updateShape();
	}


	@Override
	protected void updateShape() {
		final double x 		= point.getX();
		final double y 		= point.getY();
		final double mid 	= size/2.;
		final double quat 	= size/4.;
		final double oct 	= size/8.;

		shape.reset();

		switch(position) {
			case EAST:
				shape.moveTo(x, y-size/4.);
				shape.lineTo(x, y+size/4.);
				shape.lineTo(x+size/2., y+size/4.);
				shape.lineTo(x+size/2., y+size/2.);
				shape.lineTo(x+size, y);
				shape.lineTo(x+size/2., y-size/2.);
				shape.lineTo(x+size/2., y-size/4.);
				shape.closePath();
				break;
			case NORTH:
				shape.moveTo(x-size/4., y);
				shape.lineTo(x+size/4., y);
				shape.lineTo(x+size/4., y-size/2.);
				shape.lineTo(x+size/2., y-size/2.);
				shape.lineTo(x, y-size);
				shape.lineTo(x-size/2., y-size/2.);
				shape.lineTo(x-size/4., y-size/2.);
				shape.closePath();
				break;
			case SOUTH:
				shape.moveTo(x-size/4., y);
				shape.lineTo(x+size/4., y);
				shape.lineTo(x+size/4., y+size/2.);
				shape.lineTo(x+size/2., y+size/2.);
				shape.lineTo(x, y+size);
				shape.lineTo(x-size/2., y+size/2.);
				shape.lineTo(x-size/4., y+size/2.);
				shape.closePath();
				break;
			case WEST:
				shape.moveTo(x, y-size/4.);
				shape.lineTo(x, y+size/4.);
				shape.lineTo(x-size/2., y+size/4.);
				shape.lineTo(x-size/2., y+size/2.);
				shape.lineTo(x-size, y);
				shape.lineTo(x-size/2., y-size/2.);
				shape.lineTo(x-size/2., y-size/4.);
				shape.closePath();
				break;
			case NE:
				shape.moveTo(x+size-oct, y-size+oct);
				shape.lineTo(x+size-oct, y-quat+oct);
				shape.lineTo(x+mid+quat-oct, y-mid+oct);
				shape.lineTo(x+quat-oct, y+oct);
				shape.lineTo(x-oct, y-quat+oct);
				shape.lineTo(x+mid-oct, y-mid-quat+oct);
				shape.lineTo(x+quat-oct, y-size+oct);
				shape.closePath();
				break;
			case NW:
				shape.moveTo(x-size+oct, y-size+oct);
				shape.lineTo(x-size+oct, y-quat+oct);
				shape.lineTo(x-mid-quat+oct, y-mid+oct);
				shape.lineTo(x-quat+oct, y+oct);
				shape.lineTo(x+oct, y-quat+oct);
				shape.lineTo(x-mid+oct, y-mid-quat+oct);
				shape.lineTo(x-quat+oct, y-size+oct);
				shape.closePath();
				break;
			case SW:
				shape.moveTo(x-size+oct, y+size-oct);
				shape.lineTo(x-size+oct, y+quat-oct);
				shape.lineTo(x-mid-quat+oct, y+mid-oct);
				shape.lineTo(x-quat+oct, y-oct);
				shape.lineTo(x+oct, y+quat-oct);
				shape.lineTo(x-mid+oct, y+mid+quat-oct);
				shape.lineTo(x-quat+oct, y+size-oct);
				shape.closePath();
				break;
			case SE:
				shape.moveTo(x+size-oct, y+size-oct);
				shape.lineTo(x+size-oct, y+quat-oct);
				shape.lineTo(x+mid+quat-oct, y+mid-oct);
				shape.lineTo(x+quat-oct, y-oct);
				shape.lineTo(x-oct, y+quat-oct);
				shape.lineTo(x+mid-oct, y+mid+quat-oct);
				shape.lineTo(x+quat-oct, y+size-oct);
				shape.closePath();
				break;
		}
	}


	/**
	 * @return The position of the handler.
	 * @since 3.0
	 */
	public Position getPosition() {
		return position;
	}


	@Override
	public void updateFromShape(final Shape sh) {
		if(sh instanceof Rectangle2D) {
			final Rectangle2D frame = (Rectangle2D)sh;

			switch(position) {
				case EAST:	setPoint(frame.getMaxX(), frame.getCenterY()); break;
				case NE:	setPoint(frame.getMaxX(), frame.getMinY()); 	break;
				case NORTH:	setPoint(frame.getCenterX(), frame.getMinY()); break;
				case NW:	setPoint(frame.getMinX(), frame.getMinY()); break;
				case SE:	setPoint(frame.getMaxX(), frame.getMaxY()); break;
				case SOUTH:	setPoint(frame.getCenterX(), frame.getMaxY()); break;
				case SW:	setPoint(frame.getMinX(), frame.getMaxY()); break;
				case WEST:	setPoint(frame.getMinX(), frame.getCenterY()); break;
			}
		}
	}
}
