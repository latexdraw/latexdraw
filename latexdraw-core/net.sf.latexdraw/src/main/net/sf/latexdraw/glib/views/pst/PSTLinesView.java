package net.sf.latexdraw.glib.views.pst;

import org.eclipse.jdt.annotation.NonNull;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;

/**
 * Defines a PSTricks view of the LLines model.<br>
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
class PSTLinesView extends PSTPolygonView {
	/**
	 * Creates and initialises a LLines PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTLinesView(@NonNull final IPolyline model) {
		super(model);

		update();
	}



	@Override
	public void updateCache(final IPoint position, final float ppc) {
		if(!GLibUtilities.isValidPoint(position) || ppc<1)
			return ;

		emptyCache();

		final StringBuilder points   		= getPointsCode(position, ppc);
		final StringBuilder arrowsStyle 	= getArrowsStyleCode();

		cache.append("\\psline["); //$NON-NLS-1$
		cache.append(getPropertiesCode(ppc));
		cache.append(']');
		if(arrowsStyle!=null)
			cache.append(arrowsStyle);
		cache.append(points);
	}
}
