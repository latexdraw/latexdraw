package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines methods for classical PSTricks views.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 04/15/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
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
			if(mainCodeBuilder.length()>0)
				mainCodeBuilder.append(", ");
			mainCodeBuilder.append(codeToAdd);
		}
	}
}
