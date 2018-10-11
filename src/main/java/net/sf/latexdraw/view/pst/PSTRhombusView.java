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
import net.sf.latexdraw.models.interfaces.shape.IRhombus;

/**
 * Defines a PSTricks view of the LRhombus model.
 * @author Arnaud Blouin
 */
public class PSTRhombusView extends PSTClassicalView<IRhombus> {
	/**
	 * Creates and initialises a LRhombus PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTRhombusView(final IRhombus model) {
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
		final double tly = tl.getY();
		final double brx = br.getX();
		final double bry = br.getY();
		final double xCenter = (tlx + brx) / 2f - origin.getX();
		final double yCenter = origin.getY() - (tly + bry) / 2f;
		final StringBuilder params = getPropertiesCode(ppc);
		final double rotationAngle = Math.toDegrees(shape.getRotationAngle()) % 360;
		final StringBuilder code = new StringBuilder();

		if(!MathUtils.INST.equalsDouble(rotationAngle, 0.0)) {
			params.append(", gangle=").append(MathUtils.INST.getCutNumberFloat(-rotationAngle)); //NON-NLS
		}

		code.append("\\psdiamond["); //NON-NLS
		code.append(params);
		code.append(']').append('(');
		code.append(MathUtils.INST.getCutNumberFloat(xCenter / ppc)).append(',');
		code.append(MathUtils.INST.getCutNumberFloat(yCenter / ppc)).append(')').append('(');
		code.append(MathUtils.INST.getCutNumberFloat((brx - tlx) / 2f) / ppc).append(',');
		code.append(MathUtils.INST.getCutNumberFloat((bry - tly) / 2f) / ppc).append(')');

		return code.toString();
	}
}

