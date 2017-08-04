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

import java.util.Arrays;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;

/**
 * @author Arnaud Blouin
 */
public class ViewPolyline extends ViewPolyPoint<IPolyline> {
	protected final ViewArrowableTrait viewArrows = new ViewArrowableTrait(model);

	private final ChangeListener<Number> updateArrow = (observable, oldValue, newValue) -> update();

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPolyline(final IPolyline sh) {
		super(sh);

		getChildren().add(viewArrows);

		// TODO: to optimise: the arrows may be re-computed four times instead of a single one (on a shape move for example).
		model.getPtAt(0).xProperty().addListener(updateArrow);
		model.getPtAt(0).yProperty().addListener(updateArrow);
		model.getPtAt(-1).xProperty().addListener(updateArrow);
		model.getPtAt(-1).yProperty().addListener(updateArrow);
		model.thicknessProperty().addListener(updateArrow);
		update();
	}


	private void update() {
		viewArrows.update(true);

		if(viewArrows.arrows.stream().anyMatch(ar -> ar.arrow.hasStyle())) {
			clipPath(border);
		}else {
			border.setClip(null);
		}
	}


	private Optional<IPoint> getArrowReducedPoint(final IArrow arrow) {
		final ILine l = arrow.getArrowLine();
		if(l == null) return Optional.empty();
		final IPoint[] points = l.findPoints(l.getX1(), l.getY1(), arrow.getArrowShapeLength());
		if(points == null) return Optional.empty();
		return Arrays.stream(points).reduce((p1, p2) -> p1.distance(l.getPoint2()) < p2.distance(l.getPoint2()) ? p1 : p2);
	}


	/**
	 * If the shape has an arrow, the corresponding point must be move (in fact clipped) to the beginning of the arrow to avoid the
	 * line to be visible behind the arrow. To do so, a new point (for each arrow) is computed and the view is clipped.
	 * @param path The path to clip. Cannot be null.
	 * @throws NullPointerException if the given path is null.
	 */
	private void clipPath(final Path path) {
		final Path clip = ViewFactory.INSTANCE.clonePath(path);

		if(!clip.getElements().isEmpty()) { // Defensive programming
			final Optional<IPoint> pt1 = getArrowReducedPoint(viewArrows.arrows.get(0).arrow);
			final Optional<IPoint> pt2 = getArrowReducedPoint(viewArrows.arrows.get(viewArrows.arrows.size() - 1).arrow);

			if(pt1.isPresent() && clip.getElements().get(0) instanceof MoveTo) { // Defensive programming
				// Changing the first point to the one at the beginning of the arrow.
				final MoveTo moveTo = (MoveTo) clip.getElements().get(0);
				moveTo.setX(pt1.get().getX());
				moveTo.setY(pt1.get().getY());
			}

			if(pt2.isPresent() && clip.getElements().get(clip.getElements().size()-1) instanceof LineTo) {
				final LineTo lineTo = (LineTo) clip.getElements().get(clip.getElements().size()-1);
				lineTo.setX(pt2.get().getX());
				lineTo.setY(pt2.get().getY());
			}
		}

		clip.setStrokeWidth(path.getStrokeWidth());
		clip.setStrokeLineCap(path.getStrokeLineCap());
		path.setClip(clip);
	}


	@Override
	public void flush() {
		model.getPtAt(0).xProperty().removeListener(updateArrow);
		model.getPtAt(0).yProperty().removeListener(updateArrow);
		model.getPtAt(-1).xProperty().removeListener(updateArrow);
		model.getPtAt(-1).yProperty().removeListener(updateArrow);
		super.flush();
	}
}
