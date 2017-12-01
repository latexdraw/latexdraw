/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import java.util.Optional;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * An implementation of ViewArrowable for path-based views.
 */
class ViewArrowableTraitPath<T extends IArrowableSingleShape> extends ViewArrowableTrait<Path, T> {
	ViewArrowableTraitPath(final ViewSingleShape<T, Path> view) {
		super(view);
	}

	@Override
	protected void clipPath(final Path path) {
		final Path clip = ViewFactory.INSTANCE.clonePath(path);
		clip.setFill(path.getFill());
		clip.setStrokeWidth(path.getStrokeWidth());

		if(!clip.getElements().isEmpty()) { // Defensive programming
			final Optional<IPoint> pt1 = getArrowReducedPoint(arrows.get(0).arrow);
			final Optional<IPoint> pt2 = getArrowReducedPoint(arrows.get(arrows.size() - 1).arrow);

			if(pt1.isPresent() && clip.getElements().get(0) instanceof MoveTo) { // Defensive programming
				// Changing the first point to the one at the beginning of the arrow.
				final MoveTo moveTo = (MoveTo) clip.getElements().get(0);
				moveTo.setX(pt1.get().getX());
				moveTo.setY(pt1.get().getY());
			}

			pt2.ifPresent(pt -> {
				if(clip.getElements().get(clip.getElements().size() - 1) instanceof LineTo) {
					final LineTo lineTo = (LineTo) clip.getElements().get(clip.getElements().size() - 1);
					lineTo.setX(pt.getX());
					lineTo.setY(pt.getY());
				}else if(clip.getElements().get(clip.getElements().size() - 1) instanceof CubicCurveTo) {
					final CubicCurveTo ccTo = (CubicCurveTo) clip.getElements().get(clip.getElements().size() - 1);
					ccTo.setX(pt.getX());
					ccTo.setY(pt.getY());
				}
			});
		}

		clip.setStrokeWidth(path.getStrokeWidth());
		clip.setStrokeLineCap(path.getStrokeLineCap());
		path.setClip(clip);
	}
}
