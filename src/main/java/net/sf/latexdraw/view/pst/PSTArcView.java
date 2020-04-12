/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.pst;

import java.util.List;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Arc;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polyline;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a PSTricks view of the LArc model.
 * @author Arnaud Blouin
 */
public class PSTArcView extends PSTClassicalView<Arc> {
	/**
	 * Creates and initialises a LArc PSTricks view.
	 * @param model The model to view.
	 */
	protected PSTArcView(final @NotNull Arc model) {
		super(model);
	}


	@Override
	public @NotNull String getCode(final @NotNull Point origin, final float ppc) {
		final double radiusX = shape.getWidth() / 2d;
		final double radiusY = shape.getHeight() / 2d;
		final double x = shape.getX() + radiusX - origin.getX();
		final double y = origin.getY() - shape.getY() + radiusY;
		final double yunit = radiusY / radiusX;
		final StringBuilder start = new StringBuilder();
		final StringBuilder end = new StringBuilder();
		final StringBuilder params = getPropertiesCode(ppc);
		final StringBuilder rotation = getRotationHeaderCode(ppc, origin);
		final StringBuilder arrowsStyle = getArrowsStyleCode();
		final StringBuilder cache = new StringBuilder();

		if(rotation != null) {
			end.append('}');
		}

		if(!MathUtils.INST.equalsDouble(yunit, 1d)) {
			start.append("\\psscalebox{1 ").append(MathUtils.INST.getCutNumberFloat(yunit)).append('}').append('{'); //NON-NLS
			end.append('}');
		}

		if(rotation != null) {
			start.append(rotation);
		}

		// Creating the closing line
		switch(shape.getArcStyle()) {
			case ARC -> start.append("\\psarc"); //NON-NLS
			case CHORD -> {
				final Point startPt = shape.getStartPoint();
				final Point endPt = shape.getEndPoint();
				start.append("\\psarc"); //NON-NLS
				final Point gcArc = shape.getGravityCentre();
				final Polyline closingLine = ShapeFactory.INST.createPolyline(List.of(startPt.rotatePoint(gcArc, shape.getRotationAngle()),
					endPt.rotatePoint(gcArc, shape.getRotationAngle())));
				closingLine.copy(shape);
				closingLine.setRotationAngle(0d);
				end.append(System.getProperty("line.separator")).append(new PSTLinesView(closingLine).getCode(origin, ppc));
			}
			case WEDGE -> start.append("\\pswedge"); //NON-NLS
		}

		cache.append(start);
		cache.append('[').append(params).append(']');
		if(arrowsStyle != null) {
			cache.append(arrowsStyle);
		}
		cache.append('(');
		cache.append(MathUtils.INST.getCutNumberFloat(x / ppc)).append(',');
		cache.append(MathUtils.INST.getCutNumberFloat(y / ppc)).append(')').append('{');
		cache.append(MathUtils.INST.getCutNumberFloat(radiusX / ppc)).append('}').append('{');
		cache.append(MathUtils.INST.getCutNumberFloat(Math.toDegrees(shape.getAngleStart()))).append('}').append('{');
		cache.append(MathUtils.INST.getCutNumberFloat(Math.toDegrees(shape.getAngleEnd()))).append('}');
		cache.append(end);

		return cache.toString();
	}
}
