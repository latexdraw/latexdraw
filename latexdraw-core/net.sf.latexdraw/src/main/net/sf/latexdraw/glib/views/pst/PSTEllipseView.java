package net.sf.latexdraw.glib.views.pst;

import org.eclipse.jdt.annotation.NonNull;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LEllipse model.<br>
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
class PSTEllipseView extends PSTClassicalView<IEllipse> {
	/**
	 * Creates and initialises a LEllipse PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTEllipseView(@NonNull final IEllipse model) {
		super(model);

		update();
	}


	@Override
	public void updateCache(final IPoint position, final float ppc) {
		if(!GLibUtilities.isValidPoint(position) || ppc<1)
			return ;

		emptyCache();

		final StringBuilder rotation = getRotationHeaderCode(ppc, position);
		final double x	 			 = LNumber.getCutNumber(shape.getX()+shape.getWidth()/2.0 - position.getX());
		final double y	 			 = LNumber.getCutNumber(position.getY()+shape.getHeight()/2.0 - shape.getY());

		if(rotation!=null)
			cache.append(rotation);

		cache.append("\\psellipse[");			//$NON-NLS-1$
		cache.append(getPropertiesCode(ppc));
		cache.append(']').append('(');
		cache.append((float)LNumber.getCutNumber(x/ppc)).append(',');
		cache.append((float)LNumber.getCutNumber(y/ppc)).append(')').append('(');
		cache.append((float)LNumber.getCutNumber(shape.getWidth()/2.0/ppc)).append(',');
		cache.append((float)LNumber.getCutNumber(shape.getHeight()/2.0/ppc)).append(')');

		if(rotation!=null)
			cache.append('}');
	}
}
