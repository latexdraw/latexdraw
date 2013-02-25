package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.geom.Path2D;

import net.sf.latexdraw.glib.models.interfaces.IRhombus;

/**
 * Defines a view of the IRhombus model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 03/19/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LRhombusView extends LRectangularView<IRhombus> {
	/**
	 * Creates an initialises the Java view of a LRhombus.
	 * @param model The model to view.
	 * @since 3.0
	 */
	protected LRhombusView(final IRhombus model) {
		super(model);
		update();
	}


	@Override
	protected void setRectangularShape(final Path2D path, final double tlx, final double tly, final double width, final double height) {
		path.moveTo(tlx+width/2., tly);
		path.lineTo(tlx+width, tly+height/2.);
		path.lineTo(tlx+width/2., tly+height);
		path.lineTo(tlx		 , tly+height/2.);
		path.closePath();
	}
}
