package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IPolygon;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LPolygon model.<br>
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
 * 05/23/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class PSTPolygonView extends PSTClassicalView<IPolygon> {
	/**
	 * Creates and initialises a LRect PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTPolygonView(final IPolygon model) {
		super(model);
		update();
	}


	/**
	 * @param position The reference point of the PSTricks drawing.
	 * @param ppc The number of pixels per centimetre.
	 * @return The PSTricks code of the polygon coordinates.
	 * @since 3.0
	 */
	protected StringBuilder getPointsCode(final IPoint position, final float ppc) {
		if(!GLibUtilities.INSTANCE.isValidPoint(position) || ppc<1)
			return null;

		IPoint p;
		int i;
		final int size  	 		= shape.getNbPoints();
		final StringBuilder points 	= new StringBuilder();

		for(i=0; i<size; i++) {
			p = shape.getPtAt(i);
			points.append('(').append((float)LNumber.INSTANCE.getCutNumber((p.getX()-position.getX())/ppc));
			points.append(',').append((float)LNumber.INSTANCE.getCutNumber((position.getY()-p.getY())/ppc)).append(')');
		}

		return points;
	}



	@Override
	public void updateCache(final IPoint position, final float ppc) {
		if(!GLibUtilities.INSTANCE.isValidPoint(position) || ppc<1)
			return ;

		emptyCache();

		final StringBuilder points   = getPointsCode(position, ppc);
		final StringBuilder rotation = getRotationHeaderCode(ppc, position);

		if(rotation!=null)
			cache.append(rotation);

		cache.append("\\pspolygon["); //$NON-NLS-1$
		cache.append(getPropertiesCode(ppc));
		cache.append(']');
		cache.append(points);
	}
}
