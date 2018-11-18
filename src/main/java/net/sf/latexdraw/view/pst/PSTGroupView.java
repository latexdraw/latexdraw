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
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * A PSTricks view of the LDrawing model.
 * @author Arnaud BLOUIN
 */
public class PSTGroupView extends PSTShapeView<Group> {
	private final @NotNull PSTViewProducer producer;

	/**
	 * Creates and initialises a LDrawing PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 */
	protected PSTGroupView(final Group model, final @NotNull PSTViewProducer producer) {
		super(model);
		this.producer = producer;
	}


	@Override
	public @NotNull String getCode(final @NotNull Point origin, final float ppc) {
		final List<PSTShapeView<?>> pstViews = shape.getShapes().stream().map(sh -> producer.createView(sh)).
			filter(Optional::isPresent).map(opt -> opt.get()).collect(Collectors.toList());

		coloursName = pstViews.stream().map(view -> view.coloursName).filter(col -> col != null).flatMap(s -> s.stream()).collect(Collectors.toSet());

		return pstViews.stream().map(v -> v.getCode(origin, ppc)).collect(Collectors.joining("\n"));
	}
}
