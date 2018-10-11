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
import net.sf.latexdraw.models.interfaces.shape.IPolyline;

/**
 * Defines a PSTricks view of the LLines model.
 * @author Arnaud Blouin
 */
public class PSTLinesView extends PSTPolygonView {
	/**
	 * Creates and initialises a LLines PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTLinesView(final IPolyline model) {
		super(model);
	}


	@Override
	public String getCode(final IPoint position, final float ppc) {
		if(!MathUtils.INST.isValidPt(position) || ppc < 1) {
			return "";
		}

		final StringBuilder points = getPointsCode(position, ppc);
		final StringBuilder arrowsStyle = getArrowsStyleCode();
		final StringBuilder code = new StringBuilder();

		code.append("\\psline["); //NON-NLS
		code.append(getPropertiesCode(ppc));
		code.append(']');
		if(arrowsStyle != null) {
			code.append(arrowsStyle);
		}
		code.append(points);

		return code.toString();
	}
}
