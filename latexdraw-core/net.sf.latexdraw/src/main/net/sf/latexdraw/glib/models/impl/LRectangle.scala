package net.sf.latexdraw.glib.models.impl

import net.sf.latexdraw.glib.models.interfaces.prop.ILineArcProp
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle
import net.sf.latexdraw.glib.models.interfaces.shape.IShape

/**
 * Defines a model of a rectangle.<br>
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
 * 07/05/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
private class LRectangle(tl:IPoint, br:IPoint) extends LRectangularShape(tl, br) with IRectangle with LineArcProp {
	override def copy(sh:IShape) {
		super.copy(sh)
		sh match {
      case prop: ILineArcProp => setLineArc(prop.getLineArc)
      case _ =>
    }
  }
}
