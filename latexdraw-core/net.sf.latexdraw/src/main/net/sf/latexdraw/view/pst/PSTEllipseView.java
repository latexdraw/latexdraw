/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2015 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.pst;

import net.sf.latexdraw.models.GLibUtilities;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LNumber;
import org.eclipse.jdt.annotation.NonNull;

/**
 * Defines a PSTricks view of the LEllipse model.
 */
class PSTEllipseView extends PSTClassicalView<IEllipse> {
	/**
	 * Creates and initialises a LEllipse PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTEllipseView(final @NonNull IEllipse model) {
		super(model);
	}


	@Override
	public String getCode(final IPoint position, final float ppc) {
		if(!GLibUtilities.isValidPoint(position) || ppc < 1) return "";

		final StringBuilder rotation = getRotationHeaderCode(ppc, position);
		final float x = LNumber.getCutNumberFloat(shape.getX() + shape.getWidth() / 2.0 - position.getX());
		final float y = LNumber.getCutNumberFloat(position.getY() + shape.getHeight() / 2.0 - shape.getY());
		final StringBuilder code = new StringBuilder();

		if(rotation != null) code.append(rotation);

		code.append("\\psellipse[");            //$NON-NLS-1$
		code.append(getPropertiesCode(ppc));
		code.append(']').append('(');
		code.append(LNumber.getCutNumber(x / ppc)).append(',');
		code.append(LNumber.getCutNumber(y / ppc)).append(')').append('(');
		code.append(LNumber.getCutNumberFloat(shape.getWidth() / 2.0 / ppc)).append(',');
		code.append(LNumber.getCutNumberFloat(shape.getHeight() / 2.0 / ppc)).append(')');

		if(rotation != null) code.append('}');

		return code.toString();
	}
}
