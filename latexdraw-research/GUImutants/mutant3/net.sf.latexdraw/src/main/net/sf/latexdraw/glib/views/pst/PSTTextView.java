package net.sf.latexdraw.glib.views.pst;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LText model.<br>
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
class PSTTextView extends PSTShapeView<IText> {
	/**
	 * Creates and initialises a LText PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTTextView(final IText model) {
		super(model);

		update();
	}



	@Override
	public void updateCache(final IPoint origin, final float ppc) {
		if(!GLibUtilities.INSTANCE.isValidPoint(origin) || ppc<1)
			return ;

		final StringBuilder rot = getRotationHeaderCode(ppc, origin);

		emptyCache();

		if(rot!=null)
			cache.append(rot);

		String colorName;
		Color lineCol = shape.getLineColour();

		if(lineCol.equals(PSTricksConstants.DEFAULT_LINE_COLOR))
			colorName = null;
		else {
			colorName = getColourName(shape.getLineColour());
			addColour(colorName);
		}

		String tokenPosition = shape.getTextPosition().getLatexToken();

		if(tokenPosition==null || tokenPosition.length()==0)
			cache.append("\\rput("); //$NON-NLS-1$
		else
			cache.append("\\rput[").append(shape.getTextPosition().getLatexToken()).append(']').append('('); //$NON-NLS-1$

		cache.append((float)LNumber.INSTANCE.getCutNumber((shape.getX()-origin.getX())/ppc)).append(',');
		cache.append((float)LNumber.INSTANCE.getCutNumber((origin.getY()-shape.getY())/ppc)).append(')').append('{');

		if(colorName!=null)
			cache.append("\\textcolor{").append(colorName).append('}').append('{'); //$NON-NLS-1$

		cache.append(shape.getText()).append('}');

		if(colorName!=null)
			cache.append('}');

		if(rot!=null)
			cache.append('}');
	}
}
