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
package net.sf.latexdraw.ui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * An Y-scale ruler.
 * @author Arnaud BLOUIN
 */
public class YScaleRuler extends ScaleRuler {

	/**
	 * Defines a y-scale ruler.
	 * @throws IllegalArgumentException if the given canvas is null.
	 * @since 3.0
	 */
	public YScaleRuler() {
		super();
		setPreferredSize(new Dimension(SIZE, 500));
	}

	@Override
	protected void drawLine(final Graphics2D g2, final double positionA, final double positionB1, final double positionB2) {
		g2.draw(new Line2D.Double(positionB1, positionA, positionB2, positionA));
	}


	@Override
	protected double getLength() {
		return getSize().getHeight()+getClippingGap();
	}

	@Override
	protected double getStart() {
		return 0.;
	}

	@Override
	protected void adaptGraphicsToViewpoint(final Graphics2D g) {
		g.translate(0, (int)-getClippingGap());
	}

	@Override
	protected double getClippingGap() {
		return 0.0;// canvas.getVisibleBound().getMinY();
	}
}
