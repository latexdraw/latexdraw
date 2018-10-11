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
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * Defines a PSTricks view of the ICircle model.
 * @author Arnaud Blouin
 */
public class PSTCircleView extends PSTClassicalView<ICircle> {
	/**
	 * Creates an SVG generator for circles.
	 * @param circle The circle used for the conversion in SVG.
	 * @since 3.0
	 */
	protected PSTCircleView(final ICircle circle) {
		super(circle);
	}


	@Override
	public String getCode(final IPoint position, final float ppc) {
		if(!MathUtils.INST.isValidPt(position) || ppc < 1) {
			return "";
		}

		final double radius = shape.getWidth() / 2.0;
		final StringBuilder rotation = getRotationHeaderCode(ppc, position);
		final double x = shape.getX() + radius - position.getX();
		final double y = position.getY() + radius - shape.getY();
		final StringBuilder cache = new StringBuilder();

		if(rotation != null) {
			cache.append(rotation);
		}

		cache.append("\\pscircle["); //NON-NLS
		cache.append(getPropertiesCode(ppc));
		cache.append(']').append('(');
		cache.append(MathUtils.INST.getCutNumberFloat(x / ppc)).append(',');
		cache.append(MathUtils.INST.getCutNumberFloat(y / ppc)).append(')').append('{');
		cache.append(MathUtils.INST.getCutNumberFloat(radius / ppc)).append('}');

		if(rotation != null) {
			cache.append('}');
		}

		return cache.toString();
	}
}
