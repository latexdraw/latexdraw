package net.sf.latexdraw.glib.views.pst;

import org.eclipse.jdt.annotation.NonNull;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LRhombus model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
class PSTRhombusView extends PSTClassicalView<IRhombus> {
	/**
	 * Creates and initialises a LRhombus PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTRhombusView(@NonNull final IRhombus model) {
		super(model);
		update();
	}


	@Override
	public void updateCache(final IPoint origin, final float ppc) {
		if(!GLibUtilities.isValidPoint(origin) || ppc<1)
			return ;

		emptyCache();

		final IPoint tl 			= shape.getTopLeftPoint();
		final IPoint br 			= shape.getBottomRightPoint();
		final double tlx 			= tl.getX();
		final double tly 			= tl.getY();
		final double brx 			= br.getX();
		final double bry 			= br.getY();
		final double xCenter 	  	= (tlx+brx)/2. - origin.getX();
		final double yCenter 		= origin.getY() - (tly+bry)/2.;
		final StringBuilder params 	= getPropertiesCode(ppc);
		final double rotationAngle  = Math.toDegrees(shape.getRotationAngle())%360;

		if(!LNumber.equalsDouble(rotationAngle, 0.))
			params.append(", gangle=").append((float)LNumber.getCutNumber(-rotationAngle));//$NON-NLS-1$

		cache.append("\\psdiamond[");//$NON-NLS-1$
		cache.append(params);
		cache.append(']').append('(');
		cache.append((float)LNumber.getCutNumber(xCenter/ppc)).append(',');
		cache.append((float)LNumber.getCutNumber(yCenter/ppc)).append(')').append('(');
		cache.append((float)LNumber.getCutNumber((brx-tlx)/2f)/ppc).append(',');
		cache.append((float)LNumber.getCutNumber((bry-tly)/2f)/ppc).append(')');
	}
}

