package net.sf.latexdraw.glib.views.pst;

import org.eclipse.jdt.annotation.NonNull;

import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LDot model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
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
class PSTDotView extends PSTClassicalView<IDot> {
	/**
	 * Creates and initialises a LDot PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTDotView(@NonNull final IDot model) {
		super(model);

		update();
	}


	@Override
	public void updateCache(final IPoint origin, final float ppc) {
		final double x 				= shape.getX() - origin.getX();
		final double y 				= origin.getY() - shape.getY();
		final DotStyle style 		= shape.getDotStyle();
		final StringBuilder params  = getPropertiesCode(ppc);
		final StringBuilder rotation= getRotationHeaderCode(ppc, origin);

		emptyCache();

		if(style!=DotStyle.DOT)
			params.append(", dotstyle=").append(style.getPSTToken()); //$NON-NLS-1$

		params.append(", dotsize=").append((float)LNumber.getCutNumber(shape.getDiametre()/ppc)); //$NON-NLS-1$

		if(rotation!=null)
			cache.append(rotation);

		cache.append("\\psdots["); //$NON-NLS-1$
		cache.append(params);
		if(shape.isFillable())
			cache.append(", fillcolor=").append(getColourName(shape.getFillingCol()));
		cache.append(']').append('(');
		cache.append((float)LNumber.getCutNumber(x/ppc)).append(',');
		cache.append((float)LNumber.getCutNumber(y/ppc)).append(')');

		if(rotation!=null)
			cache.append('}');
	}
}
