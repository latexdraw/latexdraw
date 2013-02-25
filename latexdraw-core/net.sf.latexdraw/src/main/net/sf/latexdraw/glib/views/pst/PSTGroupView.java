package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

/**
 * Defines a PSTricks view of the LDrawing model.<br>
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
 * 04/17/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class PSTGroupView extends PSTShapeView<IGroup> {
	/**
	 * Creates and initialises a LDrawing PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTGroupView(final IGroup model) {
		super(model);

		update();
	}




	@Override
	public void updateCache(final IPoint origin, final float ppc) {
		if(!GLibUtilities.INSTANCE.isValidPoint(origin) || ppc<1)
			return ;

		emptyCache();

		int i, size 	= shape.size()-1;
		PSTShapeView<?> pstView;

		if(size>0) {
			for(i=0; i<size; i++) {
				pstView = PSTViewsFactory.INSTANCE.createView(shape.getShapeAt(i));
				pstView.updateCache(origin, ppc);
				cache.append(pstView.getCache()).append('\n');
			}

			pstView = PSTViewsFactory.INSTANCE.createView(shape.getShapeAt(i));
			pstView.updateCache(origin, ppc);
			cache.append(pstView.getCache());
		}
	}
}
