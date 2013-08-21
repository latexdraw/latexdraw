package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the ICircle model.<br>
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
 * 07/25/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class PSTCircleView extends PSTEllipseView {
	/**
	 * Creates an SVG generator for circles.
	 * @param circle The circle used for the conversion in SVG.
	 * @since 3.0
	 */
	protected PSTCircleView(final ICircle circle) {
		super(circle);

		update();
	}



	@Override
	public void updateCache(final IPoint position, final float ppc) {
		if(!GLibUtilities.INSTANCE.isValidPoint(position) || ppc<1)
			return ;

		emptyCache();

		final ICircle circle 		 = (ICircle)shape;
		final double radius			 = circle.getWidth()/2.;
		final StringBuilder rotation = getRotationHeaderCode(ppc, position);
		final double x	 			 = circle.getX()+radius - position.getX();
		final double y	 			 = position.getY()+radius - circle.getY();

		if(rotation!=null)
			cache.append(rotation);

		cache.append("\\pscircle["); //$NON-NLS-1$
		cache.append(getPropertiesCode(ppc));
		cache.append(']').append('(');
		cache.append((float)LNumber.INSTANCE.getCutNumber(x/ppc)).append(',');
		cache.append((float)LNumber.INSTANCE.getCutNumber(y/ppc)).append(')').append('{');
		cache.append((float)LNumber.INSTANCE.getCutNumber(radius/ppc)).append('}');

		if(rotation!=null)
			cache.append('}');
	}
}
