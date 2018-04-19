/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.pst;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * A PSTricks view of the LDrawing model.
 * @author Arnaud BLOUIN
 */
public class PSTGroupView extends PSTShapeView<IGroup> {
	/**
	 * Creates and initialises a LDrawing PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTGroupView(final IGroup model) {
		super(model);
	}


	@Override
	public String getCode(final IPoint origin, final float ppc) {
		if(!MathUtils.INST.isValidPt(origin) || ppc<1) {
			return "";
		}

		final List<PSTShapeView<?>> pstViews = shape.getShapes().stream().map(PSTViewsFactory.INSTANCE::createView).
			filter(Optional::isPresent).map(opt -> opt.get()).collect(Collectors.toList());

		coloursName = pstViews.stream().map(view -> view.coloursName).filter(col -> col!=null).flatMap(s -> s.stream()).collect(Collectors.toSet());

		return pstViews.stream().map(v -> v.getCode(origin, ppc)).collect(Collectors.joining("\n"));
	}
}
