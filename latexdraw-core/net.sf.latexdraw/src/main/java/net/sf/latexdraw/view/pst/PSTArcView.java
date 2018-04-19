/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.pst;

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LSystem;

/**
 * Defines a PSTricks view of the LArc model.
 * @author Arnaud Blouin
 */
public class PSTArcView extends PSTClassicalView<IArc> {
	/**
	 * Creates and initialises a LArc PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTArcView(final IArc model) {
		super(model);
	}


	@Override
	public String getCode(final IPoint origin, final float ppc) {
		if(!MathUtils.INST.isValidPt(origin) || ppc < 1) {
			return "";
		}

		final double radiusX = shape.getWidth() / 2.0;
		final double radiusY = shape.getHeight() / 2.0;
		final double x = shape.getX() + radiusX - origin.getX();
		final double y = origin.getY() - shape.getY() + radiusY;
		double startAngle = shape.getAngleStart();
		double endAngle = shape.getAngleEnd();
		final double yunit = radiusY / radiusX;
		final StringBuilder start = new StringBuilder();
		final StringBuilder end = new StringBuilder();
		final StringBuilder params = getPropertiesCode(ppc);
		final StringBuilder rotation = getRotationHeaderCode(ppc, origin);
		final StringBuilder arrowsStyle = getArrowsStyleCode();
		final StringBuilder cache = new StringBuilder();

		if(startAngle > endAngle) {
			final double tmp = startAngle;
			startAngle = endAngle;
			endAngle = tmp;
		}

		if(rotation != null) {
			end.append('}');
		}

		if(!MathUtils.INST.equalsDouble(yunit, 1.0)) {
			start.append("\\psscalebox{1 ").append(MathUtils.INST.getCutNumberFloat(yunit)).append('}').append('{'); //NON-NLS
			end.append('}');
		}

		if(rotation != null) {
			start.append(rotation);
		}

		switch(shape.getArcStyle()) {
			case ARC:
				start.append("\\psarc"); //NON-NLS
				break;
			case CHORD:
				final IPoint startPt = shape.getStartPoint();
				final IPoint endPt = shape.getEndPoint();

				start.append("\\psarc"); //NON-NLS
				end.append(LSystem.EOL).append("\\psline[").append(params).append(']').append('('); //NON-NLS
				end.append(MathUtils.INST.getCutNumberFloat(startPt.getX() / ppc)).append(',');
				end.append(MathUtils.INST.getCutNumberFloat(startPt.getY() / ppc)).append(')').append('(');
				end.append(MathUtils.INST.getCutNumberFloat(endPt.getX() / ppc)).append(',');
				end.append(MathUtils.INST.getCutNumberFloat(endPt.getY() / ppc)).append(')');
				break;
			case WEDGE:
				start.append("\\pswedge"); //NON-NLS
				break;
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
		cache.append(MathUtils.INST.getCutNumberFloat(Math.toDegrees(startAngle))).append('}').append('{');
		cache.append(MathUtils.INST.getCutNumberFloat(Math.toDegrees(endAngle))).append('}');
		cache.append(end);

		return cache.toString();
	}
}
