package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LEllipse model.<br>
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
class PSTEllipseView extends PSTClassicalView<IEllipse> {
	/**
	 * Creates and initialises a LEllipse PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTEllipseView(final IEllipse model) {
		super(model);

		update();
	}


	@Override
	public void updateCache(final IPoint position, final float ppc) {
		if(!GLibUtilities.INSTANCE.isValidPoint(position) || ppc<1)
			return ;

		emptyCache();

		final StringBuilder rotation = getRotationHeaderCode(ppc, position);
		final double x	 			 = LNumber.INSTANCE.getCutNumber(shape.getX()+shape.getRx() - position.getX());
		final double y	 			 = LNumber.INSTANCE.getCutNumber(position.getY()+shape.getRy() - shape.getY());

		if(rotation!=null)
			cache.append(rotation);

		cache.append("\\psellipse[");			//$NON-NLS-1$
		cache.append(getPropertiesCode(ppc));
		cache.append(']').append('(');
		cache.append((float)LNumber.INSTANCE.getCutNumber(x/ppc)).append(',');
		cache.append((float)LNumber.INSTANCE.getCutNumber(y/ppc)).append(')').append('(');
		cache.append((float)LNumber.INSTANCE.getCutNumber(shape.getRx()/ppc)).append(',');
		cache.append((float)LNumber.INSTANCE.getCutNumber(shape.getRy()/ppc)).append(')');

		if(rotation!=null)
			cache.append('}');
	}
}
