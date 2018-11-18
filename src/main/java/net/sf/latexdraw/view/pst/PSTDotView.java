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

import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a PSTricks view of the LDot model.
 * @author Arnaud Blouin
 */
public class PSTDotView extends PSTClassicalView<Dot> {
	/**
	 * Creates and initialises a LDot PSTricks view.
	 * @param model The model to view.
	 */
	protected PSTDotView(final @NotNull Dot model) {
		super(model);
	}


	@Override
	public @NotNull String getCode(final @NotNull Point origin, final float ppc) {
		final double x = shape.getX() - origin.getX();
		final double y = origin.getY() - shape.getY();
		final DotStyle style = shape.getDotStyle();
		final StringBuilder params = getPropertiesCode(ppc);
		final StringBuilder rotation = getRotationHeaderCode(ppc, origin);
		final StringBuilder code = new StringBuilder();

		if(style != DotStyle.DOT) {
			params.append(", dotstyle=").append(style.getPSTToken()); //NON-NLS
		}

		params.append(", dotsize=").append((float) MathUtils.INST.getCutNumber(shape.getDiametre() / ppc)); //NON-NLS

		if(rotation != null) {
			code.append(rotation);
		}

		code.append("\\psdots["); //NON-NLS
		code.append(params);
		if(shape.isFillable()) {
			code.append(", fillcolor=").append(getColourName(shape.getFillingCol())); //NON-NLS
		}
		code.append(']').append('(');
		code.append(MathUtils.INST.getCutNumberFloat(x / ppc)).append(',');
		code.append(MathUtils.INST.getCutNumberFloat(y / ppc)).append(')');

		if(rotation != null) {
			code.append('}');
		}

		return code.toString();
	}
}
