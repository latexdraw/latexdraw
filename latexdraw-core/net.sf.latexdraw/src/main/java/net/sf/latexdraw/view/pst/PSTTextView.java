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
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IText;

/**
 * Defines a PSTricks view of the LText model.
 * @author Arnaud Blouin
 */
public class PSTTextView extends PSTShapeView<IText> {
	/**
	 * Creates and initialises a LText PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTTextView(final IText model) {
		super(model);
	}


	@Override
	public String getCode(final IPoint origin, final float ppc) {
		if(!MathUtils.INST.isValidPt(origin) || ppc < 1) {
			return "";
		}

		final StringBuilder rot = getRotationHeaderCode(ppc, origin);
		final StringBuilder code = new StringBuilder();

		if(rot != null) {
			code.append(rot);
		}

		final String colorName;
		final Color lineCol = shape.getLineColour();

		if(lineCol.equals(PSTricksConstants.DEFAULT_LINE_COLOR)) {
			colorName = null;
		}else {
			colorName = getColourName(shape.getLineColour());
			addColour(colorName);
		}

		final String tokenPosition = shape.getTextPosition().getLatexToken();

		if(tokenPosition == null || tokenPosition.isEmpty()) {
			code.append("\\rput("); //NON-NLS
		}else {
			code.append("\\rput[").append(shape.getTextPosition().getLatexToken()).append(']').append('('); //NON-NLS
		}

		code.append(MathUtils.INST.getCutNumberFloat((shape.getX() - origin.getX()) / ppc)).append(',');
		code.append(MathUtils.INST.getCutNumberFloat((origin.getY() - shape.getY()) / ppc)).append(')').append('{');

		if(colorName != null) {
			code.append("\\textcolor{").append(colorName).append('}').append('{'); //NON-NLS
		}

		code.append(shape.getText()).append('}');

		if(colorName != null) {
			code.append('}');
		}

		if(rot != null) {
			code.append('}');
		}

		return code.toString();
	}
}
