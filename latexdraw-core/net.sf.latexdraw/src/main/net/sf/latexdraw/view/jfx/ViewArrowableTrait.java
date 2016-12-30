/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.view.jfx;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import net.sf.latexdraw.models.interfaces.shape.IArrowableShape;
import org.eclipse.jdt.annotation.NonNull;

/**
 * A (sort of) trait for grouping all the JFX code related to painting arrows of arrowable shape.
 */
class ViewArrowableTrait {
	private final IArrowableShape model;
	private final Group arrowsView;
	private final List<ViewArrow> arrows;

	ViewArrowableTrait(final @NonNull IArrowableShape sh) {
		super();
		model = sh;
		arrowsView = new Group();
		arrows = new ArrayList<>();

		for(int i=0, size=model.getNbArrows(); i<size; i++) {
			final ViewArrow viewArrow = new ViewArrow(model.getArrowAt(i));
			arrows.add(viewArrow);
			arrowsView.getChildren().addAll(viewArrow);
			model.getArrowAt(i).setOnArrowChanged(() -> viewArrow.updatePath());
		}
	}

	Group getArrowsView() {
		return arrowsView;
	}

	void update(final boolean showArrows) {
		arrowsView.setVisible(showArrows);

		if(showArrows) {
			arrowsView.setDisable(false);
			arrows.forEach(v -> v.updatePath());
		}else {
			arrowsView.setDisable(true);
		}
	}
}
