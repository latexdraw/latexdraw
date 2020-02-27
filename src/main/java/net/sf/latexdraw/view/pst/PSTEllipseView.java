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

import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a PSTricks view of the LEllipse model.
 * @author Arnaud Blouin
 */
public class PSTEllipseView extends PSTClassicalView<Ellipse> {
	/**
	 * Creates and initialises a LEllipse PSTricks view.
	 * @param model The model to view.
	 */
	protected PSTEllipseView(final @NotNull Ellipse model) {
		super(model);
	}


	@Override
	public @NotNull String getCode(final @NotNull Point position, final float ppc) {
		final StringBuilder rotation = getRotationHeaderCode(ppc, position);
		final float x = MathUtils.INST.getCutNumberFloat(shape.getX() + shape.getWidth() / 2.0 - position.getX());
		final float y = MathUtils.INST.getCutNumberFloat(position.getY() + shape.getHeight() / 2.0 - shape.getY());
		final StringBuilder code = new StringBuilder();

		if(rotation != null) {
			code.append(rotation);
		}

		code.append("\\psellipse[");            //NON-NLS
		code.append(getPropertiesCode(ppc));
		code.append(']').append('(');
		code.append(MathUtils.INST.getCutNumber(x / ppc)).append(',');
		code.append(MathUtils.INST.getCutNumber(y / ppc)).append(')').append('(');
		code.append(MathUtils.INST.getCutNumberFloat(shape.getWidth() / 2.0 / ppc)).append(',');
		code.append(MathUtils.INST.getCutNumberFloat(shape.getHeight() / 2.0 / ppc)).append(')');

		if(rotation != null) {
			code.append('}');
		}

		return code.toString();
	}
}
