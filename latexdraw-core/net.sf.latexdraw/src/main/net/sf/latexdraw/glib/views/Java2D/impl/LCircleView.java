package net.sf.latexdraw.glib.views.Java2D.impl;

import net.sf.latexdraw.glib.models.interfaces.ICircle;

/**
 * Defines a view of the ICircle model.<br>
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
 * 03/12/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LCircleView extends LEllipseView<ICircle> {
	/**
	 * Creates an initialises the Java view of a LCircle.
	 * @param model The model to view.
	 * @since 3.0
	 */
	protected LCircleView(final ICircle model) {
		super(model);

		update();
	}


	@Override
	public void updateBorder() {
		final double gap 	= getBorderGap();
		final double width = shape.getWidth();
		final double widthMin 	= Math.max(width+gap*2., shape.getThickness());

		border.setFrame(shape.getX()-gap, shape.getY()-width-gap, widthMin, widthMin);
	}
}
