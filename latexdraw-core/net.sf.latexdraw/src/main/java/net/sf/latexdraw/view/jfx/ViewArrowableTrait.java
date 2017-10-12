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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IControlPointShape;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * A (sort of) trait for grouping all the JFX code related to painting arrows of arrowable shape.
 * @author Arnaud Blouin
 */
class ViewArrowableTrait extends ViewShape<IArrowableSingleShape> {
	protected final List<ViewArrow> arrows;
	private final ChangeListener<Object> updateArrow = (observable, oldValue, newValue) -> updateAllArrows();
	private final ChangeListener<Object> updateClip = (observable, oldValue, newValue) -> updateClip();
	private final ViewSingleShape<? extends IArrowableSingleShape, Path> mainView;

	ViewArrowableTrait(final ViewSingleShape<? extends IArrowableSingleShape, Path> view) {
		super(view.model);
		arrows = new ArrayList<>();
		mainView = view;

		for(int i = 0, size = model.getNbArrows(); i < size; i++) {
			final ViewArrow viewArrow = new ViewArrow(model.getArrowAt(i));
			arrows.add(viewArrow);
			getChildren().addAll(viewArrow);
			model.getArrowAt(i).setOnArrowChanged(() -> updateArrows(arrows.indexOf(viewArrow)));
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

			if(model instanceof IControlPointShape) {
				final IControlPointShape ctrl = (IControlPointShape) model;
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
			arrows.forEach(v -> v.updatePath());
		} else {
			arrows.get(index).updatePath();
		}
	}

	/**
	 * If the shape has an arrow, the corresponding point must be move (in fact clipped) to the beginning of the arrow to avoid the
	 * line to be visible behind the arrow. To do so, a new point (for each arrow) is computed and the view is clipped.
	 * @param path The path to clip. Cannot be null.
	 * @throws NullPointerException if the given path is null.
	 */
	private void clipPath(final Path path) {
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

			if(pt2.isPresent()) {
				if(clip.getElements().get(clip.getElements().size() - 1) instanceof LineTo) {
					final LineTo lineTo = (LineTo) clip.getElements().get(clip.getElements().size() - 1);
					lineTo.setX(pt2.get().getX());
					lineTo.setY(pt2.get().getY());
				}else if(clip.getElements().get(clip.getElements().size() - 1) instanceof CubicCurveTo) {
					final CubicCurveTo ccTo = (CubicCurveTo) clip.getElements().get(clip.getElements().size() - 1);
					ccTo.setX(pt2.get().getX());
					ccTo.setY(pt2.get().getY());
				}
			}
		}

		clip.setStrokeWidth(path.getStrokeWidth());
		clip.setStrokeLineCap(path.getStrokeLineCap());
		path.setClip(clip);
	}

	private static Optional<IPoint> getArrowReducedPoint(final IArrow arrow) {
		final ILine l = arrow.getArrowLine();
		if(l == null) return Optional.empty();
		final IPoint[] points = l.findPoints(l.getX1(), l.getY1(), arrow.getArrowShapeLength());
		if(points == null) return Optional.empty();
		return Arrays.stream(points).reduce((p1, p2) -> p1.distance(l.getPoint2()) < p2.distance(l.getPoint2()) ? p1 : p2);
	}

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
		model.getArrows().forEach(arr -> arr.setOnArrowChanged(null));
		model.getPoints().forEach(pt -> {
			pt.xProperty().removeListener(updateArrow);
			pt.yProperty().removeListener(updateArrow);
		});
		if(model instanceof IControlPointShape) {
			((IControlPointShape) model).getFirstCtrlPts().forEach(pt -> {
				pt.xProperty().removeListener(updateArrow);
				pt.yProperty().removeListener(updateArrow);
			});
		}

		super.flush();
	}
}
