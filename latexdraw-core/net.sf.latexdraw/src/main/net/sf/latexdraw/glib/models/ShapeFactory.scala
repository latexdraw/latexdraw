package net.sf.latexdraw.glib.models

import java.awt.Point
import java.awt.geom.Point2D

import scala.language.implicitConversions

import net.sf.latexdraw.glib.models.impl.LShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint

/**
 * This class contains the factory that must be used to create shape instances.<br>
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
 * 01/04/2011<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
object ShapeFactory extends LShapeFactory {
	/** Converts a Point to an IPoint. */
	implicit def Point2IPoint(pt:Point) = ShapeFactory.createPoint(pt.getX, pt.getY)

	/** Converts a Point2D to an IPoint. */
	implicit def Point2D2IPoint(pt:Point2D) = ShapeFactory.createPoint(pt.getX, pt.getY)

	/** Converts an IPoint to a Point2D. */
	implicit def IPoint2Point2D(pt:IPoint) = new Point2D.Double(pt.getX, pt.getY)
}
