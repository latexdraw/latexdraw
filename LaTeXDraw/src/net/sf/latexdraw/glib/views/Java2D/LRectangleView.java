package net.sf.latexdraw.glib.views.Java2D;

import java.awt.geom.Path2D;

import net.sf.latexdraw.glib.models.interfaces.ILineArcShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;

/**
 * Defines a view of the IRectangle model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 02/16/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class LRectangleView extends LRectangularView<IRectangle> {
	/**
	 * Creates an initialises the Java view of a LRect.
	 * @param model The model to view.
	 * @since 3.0
	 */
	public LRectangleView(final IRectangle model) {
		super(model);

		update();
	}



	@Override
	protected void setRectangularShape(final Path2D path, final double tlx, final double tly, final double width, final double height) {
		if(((ILineArcShape)shape).isRoundCorner()) {
			final double diameter = ((ILineArcShape)shape).getLineArc() * Math.min(width, height);
			final double radius   = diameter/2.;

			path.moveTo(tlx + radius, tly);
			path.lineTo(tlx + width - radius, tly);
			LEllipseView.curveQuarter(tlx+width-diameter, tly, diameter, diameter, path, LEllipseView.POINTS[3]);
			path.lineTo(tlx + width, tly + height - radius);
			LEllipseView.curveQuarter(tlx+width-diameter, tly+height-diameter, diameter, diameter, path, LEllipseView.POINTS[0]);
			path.lineTo(tlx + radius, tly + height);
			LEllipseView.curveQuarter(tlx, tly+height-diameter, diameter, diameter, path, LEllipseView.POINTS[1]);
			path.lineTo(tlx, tly + height - radius);
			path.lineTo(tlx, tly + radius);
			LEllipseView.curveQuarter(tlx, tly, diameter, diameter, path, LEllipseView.POINTS[2]);
			path.closePath();
		} else {
			path.moveTo(tlx		 , tly);
			path.lineTo(tlx+width, tly);
			path.lineTo(tlx+width, tly+height);
			path.lineTo(tlx		 , tly+height);
			path.closePath();
		}
	}
}
