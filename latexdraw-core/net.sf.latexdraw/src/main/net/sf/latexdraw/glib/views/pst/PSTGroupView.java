package net.sf.latexdraw.glib.views.pst;

import java.util.List;
import java.util.stream.Collectors;
import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;

/**
 * Defines a PSTricks view of the LDrawing model.<br>
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
		if(!GLibUtilities.isValidPoint(origin) || ppc<1)
			return ;

		emptyCache();

		List<PSTShapeView<?>> pstViews = shape.getShapes().stream().map(sh -> {
			PSTShapeView<?> pstView = PSTViewsFactory.INSTANCE.createView(sh);
			pstView.updateCache(origin, ppc);
			return pstView;
		}).collect(Collectors.toList());

		cache.append(pstViews.stream().map(view -> view.getCache()).collect(Collectors.joining("\n")));
		coloursName = pstViews.stream().map(view -> view.coloursName).filter(col -> col!=null).flatMap(s -> s.stream()).collect(Collectors.toSet());
	}

	@Override
	protected void emptyCache() {
		super.emptyCache();
		coloursName = null;
	}
}
