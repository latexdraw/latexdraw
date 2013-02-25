package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPicture;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LNumber;
import net.sf.latexdraw.util.LResources;

/**
 * Defines a PSTricks view of the LPicture model.<br>
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
class PSTPictureView extends PSTShapeView<IPicture> {
	/**
	 * Creates and initialises a LPicture PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTPictureView(final IPicture model) {
		super(model);
		update();
	}



	@Override
	public void updateCache(final IPoint origin, final float ppc) {
		if(!GLibUtilities.INSTANCE.isValidPoint(origin) || ppc<1)
			return ;

		emptyCache();

		String path 		= shape.getPathTarget();
		StringBuilder start = new StringBuilder();
		StringBuilder rot 	= getRotationHeaderCode(ppc, origin);

		path = path.replaceAll("\\\\", "/");//$NON-NLS-1$ //$NON-NLS-2$

		if(path.contains(" "))//$NON-NLS-1$
			start.append(LangTool.INSTANCE.getString16("Picture.0")).append(LResources.EOL);

		if(rot!=null)
			cache.append(rot);

		cache.append(start);
		cache.append("\\rput(");//$NON-NLS-1$
		cache.append((float)LNumber.INSTANCE.getCutNumber((shape.getX()+shape.getWidth()/2.-origin.getX())/ppc)).append(',');
		cache.append((float)LNumber.INSTANCE.getCutNumber((origin.getY()-shape.getY()-shape.getHeight()/2.)/ppc)).append(')').append('{');
		cache.append("\\includegraphics{"); //$NON-NLS-1$
		cache.append(path);
		cache.append('}').append('}');

		if(rot!=null)
			cache.append(rot);
	}
}
