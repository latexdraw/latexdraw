package net.sf.latexdraw.glib.views.pst;

import org.eclipse.jdt.annotation.NonNull;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LRect model.<br>
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
 * 05/23/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class PSTRectView extends PSTClassicalView<IRectangle> {
	/**
	 * Creates and initialises a LRect PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTRectView(@NonNull final IRectangle model) {
		super(model);

		update();
	}


	@Override
	public void updateCache(final IPoint position, final float ppc) {
		if(!GLibUtilities.isValidPoint(position) || ppc<1)
			return ;

		emptyCache();

		final StringBuilder rotation;
		final StringBuilder params  = getPropertiesCode(ppc);
		final IPoint tl  = shape.getTopLeftPoint();
		final IPoint br  = shape.getBottomRightPoint();
		final double x1 		 = tl.getX() - position.getX();
		final double x2 		 = br.getX() - position.getX();
		final double y1 		 = position.getY() - tl.getY();
		final double y2 		 = position.getY() - br.getY();

		if(shape.isRoundCorner())
			params.append(", framearc=").append(LNumber.getCutNumberFloat(shape.getLineArc())); //$NON-NLS-1$

		rotation = getRotationHeaderCode(ppc, position);

		if(rotation!=null)
			cache.append(rotation);

		cache.append("\\psframe[");	//$NON-NLS-1$
		cache.append(params);
		cache.append(']').append('(');
		cache.append(LNumber.getCutNumberFloat(x2 / ppc)).append(',');
		cache.append(LNumber.getCutNumberFloat(y1 / ppc)).append(')').append('(');
		cache.append(LNumber.getCutNumberFloat(x1 / ppc)).append(',');
		cache.append(LNumber.getCutNumberFloat(y2 / ppc)).append(')');

		if(rotation!=null)
			cache.append('}');
	}
}
