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
package net.sf.latexdraw.view.jfx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.scene.shape.Shape;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import net.sf.latexdraw.model.api.shape.ControlPointShape;
import net.sf.latexdraw.model.api.shape.Line;
import net.sf.latexdraw.model.api.shape.Point;

/**
 * A (sort of) trait for grouping all the JFX code related to painting arrows of arrowable shape.
 * @param <S> The type of the shape to view.
 * @param <T> The type of the JFX view.
 * @author Arnaud Blouin
 */
abstract class ViewArrowableTrait<T extends Shape, S extends ArrowableSingleShape> extends ViewShape<S> {
	protected final List<ViewArrow> arrows;
	protected final ViewSingleShape<? extends ArrowableSingleShape, T> mainView;
	protected final ChangeListener<Object> updateArrow = (observable, oldValue, newValue) -> updateAllArrows();
	protected final ChangeListener<Object> updateClip = (observable, oldValue, newValue) -> updateClip();

	ViewArrowableTrait(final ViewSingleShape<S, T> view) {
		super(view.model);
		arrows = new ArrayList<>();
		mainView = view;

		for(int i = 0, size = model.getNbArrows(); i < size; i++) {
			final ViewArrow viewArrow = new ViewArrow(model.getArrowAt(i));
			arrows.add(viewArrow);
			getChildren().addAll(viewArrow);
			model.getArrowAt(i).onChanges(() -> updateArrows(arrows.indexOf(viewArrow)));
		}

		final int nbPts = model.getNbPoints();

		if(nbPts > 0) {
			model.getPtAt(0).xProperty().addListener(updateArrow);
			model.getPtAt(0).yProperty().addListener(updateArrow);
			if(nbPts > 2) {
				model.getPtAt(1).xProperty().addListener(updateArrow);
				model.getPtAt(1).yProperty().addListener(updateArrow);
			}
			if(nbPts > 3) {
				model.getPtAt(nbPts - 2).xProperty().addListener(updateArrow);
				model.getPtAt(nbPts - 2).yProperty().addListener(updateArrow);
			}
			model.getPtAt(-1).xProperty().addListener(updateArrow);
			model.getPtAt(-1).yProperty().addListener(updateArrow);

			if(model instanceof ControlPointShape) {
				final ControlPointShape ctrl = (ControlPointShape) model;
				ctrl.getFirstCtrlPtAt(0).xProperty().addListener(updateArrow);
				ctrl.getFirstCtrlPtAt(0).yProperty().addListener(updateArrow);
				ctrl.getFirstCtrlPtAt(-1).xProperty().addListener(updateArrow);
				ctrl.getFirstCtrlPtAt(-1).yProperty().addListener(updateArrow);
			}
		}

		model.thicknessProperty().addListener(updateArrow);
		model.fillingProperty().addListener(updateClip);
		model.dbleBordProperty().addListener(updateArrow);
		model.dbleBordSepProperty().addListener(updateArrow);

		// The arrow trait is a component of the view
		setUserData(view);
	}

	void updateAllArrows() {
		updateArrows(-1);
	}

	void updateArrows(final int index) {
		update(index);
		updateClip();
	}

	void updateClip() {
		if(arrows.stream().anyMatch(ar -> ar.arrow.hasStyle())) {
			clipPath(mainView.border);
		}else {
			mainView.border.setClip(null);
		}
	}

	private void update(final int index) {
		setVisible(true);
		setDisable(false);

		if(index < 0 || index >= arrows.size()) {
			arrows.forEach(v -> v.updatePath(false));
		}else {
			arrows.get(index).updatePath(false);
		}
	}

	/**
	 * If the shape has an arrow, the corresponding point must be move (in fact clipped) to the beginning of the arrow to avoid the
	 * line to be visible behind the arrow. To do so, a new point (for each arrow) is computed and the view is clipped.
	 * @param path The path to clip. Cannot be null.
	 * @throws NullPointerException if the given path is null.
	 */
	protected abstract void clipPath(final T path);

	@Override
	public void flush() {
		final int nbPts = model.getNbPoints();
		model.getPtAt(0).xProperty().removeListener(updateArrow);
		model.getPtAt(0).yProperty().removeListener(updateArrow);
		model.getPtAt(-1).xProperty().removeListener(updateArrow);
		model.getPtAt(-1).yProperty().removeListener(updateArrow);
		if(nbPts > 2) {
			model.getPtAt(1).xProperty().removeListener(updateArrow);
			model.getPtAt(1).yProperty().removeListener(updateArrow);
		}
		if(nbPts > 3) {
			model.getPtAt(nbPts - 2).xProperty().removeListener(updateArrow);
			model.getPtAt(nbPts - 2).yProperty().removeListener(updateArrow);
		}

		model.thicknessProperty().removeListener(updateArrow);
		model.fillingProperty().removeListener(updateClip);
		model.dbleBordProperty().removeListener(updateArrow);
		model.dbleBordSepProperty().removeListener(updateArrow);
		model.getArrows().forEach(arr -> arr.onChanges(null));
		model.getPoints().forEach(pt -> {
			pt.xProperty().removeListener(updateArrow);
			pt.yProperty().removeListener(updateArrow);
		});
		if(model instanceof ControlPointShape) {
			((ControlPointShape) model).getFirstCtrlPts().forEach(pt -> {
				pt.xProperty().removeListener(updateArrow);
				pt.yProperty().removeListener(updateArrow);
			});
		}

		super.flush();
	}

	protected static Optional<Point> getArrowReducedPoint(final Arrow arrow) {
		final Line l = arrow.getArrowLine();
		if(l == null) {
			return Optional.empty();
		}
		final Point[] points = l.findPoints(l.getX1(), l.getY1(), arrow.getArrowShapeLength());
		if(points.length == 0) {
			return Optional.empty();
		}
		return Arrays.stream(points).reduce((p1, p2) -> p1.distance(l.getPoint2()) < p2.distance(l.getPoint2()) ? p1 : p2);
	}
}
