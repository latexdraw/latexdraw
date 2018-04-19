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

import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * Defines methods for classical PSTricks views.
 * @author Arnaud Blouin
 */
abstract class PSTClassicalView<S extends IShape> extends PSTShapeView<S> {
	/**
	 * Creates and initialises an abstract PSTricks view for classical model.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTClassicalView(final S model) {
		super(model);
	}


	/**
	 * @param ppc The number of pixels per centimetre.
	 * @return The properties PSTricks code of the model.
	 * @since 3.0
	 */
	protected StringBuilder getPropertiesCode(final float ppc) {
		final StringBuilder params = new StringBuilder();

		params.append(getLineCode(ppc));
		addCode(params, getShadowCode(ppc));
		addCode(params, getFillingCode(ppc));
		addCode(params, getBorderPositionCode());
		addCode(params, getDoubleBorderCode(ppc));
		addCode(params, getShowPointsCode());
		addCode(params, getArrowsParametersCode());

		return params;
	}


	private void addCode(final StringBuilder mainCodeBuilder, final StringBuilder codeToAdd) {
		if(codeToAdd!=null) {
			if(mainCodeBuilder.length()>0) {
				mainCodeBuilder.append(", "); //NON-NLS
			}
			mainCodeBuilder.append(codeToAdd);
		}
	}
}
