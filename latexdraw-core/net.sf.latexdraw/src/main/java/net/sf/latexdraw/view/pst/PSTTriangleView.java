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
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;

/**
 * Defines a PSTricks view of the LTriangle model.
 * @author Arnaud Blouin
 */
public class PSTTriangleView extends PSTClassicalView<ITriangle> {
	/**
	 * Creates and initialises a LTriangle PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTTriangleView(final ITriangle model) {
		super(model);
	}


	@Override
	public String getCode(final IPoint origin, final float ppc) {
		if(!MathUtils.INST.isValidPt(origin) || ppc < 1) {
			return "";
		}

		final IPoint tl = shape.getTopLeftPoint();
		final IPoint br = shape.getBottomRightPoint();
		final double tlx = tl.getX();
		final double brx = br.getX();
		final double bry = br.getY();
		final StringBuilder rot = getRotationHeaderCode(ppc, origin);
		final StringBuilder code = new StringBuilder();

		if(rot != null) {
			code.append(rot);
		}

		code.append("\\pstriangle["); //NON-NLS
		code.append(getPropertiesCode(ppc)).append(']').append('(');
		code.append(MathUtils.INST.getCutNumberFloat(((tlx + brx) / 2. - origin.getX()) / ppc)).append(',');
		code.append(MathUtils.INST.getCutNumberFloat((origin.getY() - bry) / ppc)).append(')').append('(');
		code.append(MathUtils.INST.getCutNumberFloat((brx - tlx) / ppc)).append(',');
		code.append(MathUtils.INST.getCutNumberFloat((bry - tl.getY()) / ppc)).append(')');

		if(rot != null) {
			code.append('}');
		}

		return code.toString();
	}
}

