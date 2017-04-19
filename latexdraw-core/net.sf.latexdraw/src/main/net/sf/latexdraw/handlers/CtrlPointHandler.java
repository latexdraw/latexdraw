/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.handlers;

import java.awt.geom.Ellipse2D;
import net.sf.latexdraw.models.interfaces.shape.IControlPointShape;

/**
 * A handler that moves a control point (for Bézier curves).
 * @author Arnaud BLOUIN
 */
public class CtrlPointHandler extends Handler<Ellipse2D, IControlPointShape> {
	/** The index of the point in its shape. */
	protected int indexPt;


	/**
	 * Creates the handler.
	 * @param index The index of the point in its shape.
	 */
	public CtrlPointHandler(final int index) {
		super();
		shape = new Ellipse2D.Double();
		indexPt = index;
		updateShape();
	}


	@Override
	protected void updateShape() {
		shape.setFrame(point.getX()-size/2., point.getY()-size/2., size, size);
	}


	/**
	 * @return The index of the point in its shape.
	 * @since 3.0
	 */
	public int getIndexPt() {
		return indexPt;
	}
}
