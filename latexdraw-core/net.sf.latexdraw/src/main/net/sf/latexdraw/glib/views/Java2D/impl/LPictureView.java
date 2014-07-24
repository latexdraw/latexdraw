package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.shape.IPicture;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines an abstract view of the IPicture model.<br>
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
 * 03/18/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LPictureView extends LShapeView<IPicture> {
	/**
	 * Initialises a view of a picture.
	 * @param model The model to view.
	 * @since 3.0
	 */
	protected LPictureView(final IPicture model) {
		super(model);
		update();
	}


	@Override
	public boolean intersects(final Rectangle2D r) {
		return border.intersects(r);
	}


	@Override
	public boolean contains(final double x, final double y) {
		return border.contains(x, y);
	}


	@Override
	public void paint(final Graphics2D g, final Rectangle clip) {
		if(clip!=null && !clip.contains(border) && !clip.intersects(border)) return;

		final IPoint vectorTrans = beginRotation(g);
		g.drawImage(shape.getImage(), (int)shape.getX(), (int)shape.getY(), null);
		if(vectorTrans!=null)
			endRotation(g, vectorTrans);
	}


	@Override
	public void updateBorder() {
		border.setFrame(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());

		if(!LNumber.equalsDouble(shape.getRotationAngle(), 0.))
			border.setFrame(getRotatedShape2D(shape.getRotationAngle(), border, shape.getTopLeftPoint(), shape.getBottomRightPoint()).getBounds2D());
	}


	@Override
	protected void updateDblePathInside() {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathMiddle() {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathOutside() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathInside() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathMiddle() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathOutside() {
		// Nothing to do.
	}
}
