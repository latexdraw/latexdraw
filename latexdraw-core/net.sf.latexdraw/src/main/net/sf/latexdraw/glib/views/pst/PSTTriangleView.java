package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.ITriangle;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LTriangle model.<br>
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
 * 04/18/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class PSTTriangleView extends PSTClassicalView<ITriangle> {
	/**
	 * Creates and initialises a LTriangle PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTTriangleView(final ITriangle model) {
		super(model);
		update();
	}


	@Override
	public void updateCache(final IPoint origin, final float ppc) {
		if(!GLibUtilities.INSTANCE.isValidPoint(origin) || ppc<1)
			return ;

		emptyCache();

		final IPoint tl  = shape.getTopLeftPoint();
		final IPoint br  = shape.getBottomRightPoint();
		final double tlx = tl.getX();
		final double brx = br.getX();
		final double bry = br.getY();
		final StringBuilder rot = getRotationHeaderCode(ppc, origin);

		if(rot!=null)
			cache.append(rot);

		cache.append("\\pstriangle[");//$NON-NLS-1$
		cache.append(getPropertiesCode(ppc)).append(']').append('(');
		cache.append((float)LNumber.INSTANCE.getCutNumber(((tlx+brx)/2. - origin.getX())/ppc)).append(',');
		cache.append((float)LNumber.INSTANCE.getCutNumber((origin.getY()-bry)/ppc)).append(')').append('(');
		cache.append((float)LNumber.INSTANCE.getCutNumber((brx-tlx)/ppc)).append(',');
		cache.append((float)LNumber.INSTANCE.getCutNumber((bry-tl.getY())/ppc)).append(')');

		if(rot!=null)
			cache.append('}');
	}
}

