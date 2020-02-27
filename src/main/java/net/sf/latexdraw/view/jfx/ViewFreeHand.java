/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import java.util.List;
import javafx.beans.value.ChangeListener;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Point;

/**
 * The JFX view of a freehand model.
 * @author Arnaud Blouin
 */
public class ViewFreeHand extends ViewPathShape<Freehand> {
	private final ChangeListener<Object> update = (observable, oldValue, newValue) -> setPath();


	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewFreeHand(final Freehand sh, final PathElementProducer pathProducer) {
		super(sh, pathProducer);

		// To update on translation. To improve.
		if(!model.getPoints().isEmpty()) {
			model.getPoints().get(model.getPoints().size() - 1).xProperty().addListener(update);
			model.getPoints().get(model.getPoints().size() - 1).yProperty().addListener(update);
		}

		model.intervalProperty().addListener(update);
		model.typeProperty().addListener(update);
		model.openedProperty().addListener(update);

		setPath();
	}


	private final void setPath() {
		border.getElements().clear();
		shadow.getElements().clear();

		if(model.getNbPoints() > 1) {
			switch(model.getType()) {
				case CURVES:
					setPathCurves();
					break;
				case LINES:
					setPathLines();
					break;
			}

			if(!model.isOpened()) {
				border.getElements().add(pathProducer.createClosePath());
			}

			shadow.getElements().addAll(border.getElements());
		}
	}


	/**
	 * Fills the path of curves.
	 */
	private void setPathCurves() {
		final int interval = model.getInterval();
		final List<Point> pts = model.getPoints();
		final int size = pts.size();
		double prevx = pts.get(size - 1).getX();
		double prevy = pts.get(size - 1).getY();
		double curx = pts.get(0).getX();
		double cury = pts.get(0).getY();
		double midx = (curx + prevx) / 2d;
		double midy = (cury + prevy) / 2d;
		int i;
		double x1;
		double x2;
		double y1;
		double y2;

		border.getElements().add(pathProducer.createMoveTo(curx, cury));

		// Starting the drawing of the shape with a line.
		if(size > interval) {
			prevx = curx;
			prevy = cury;
			curx = pts.get(interval).getX();
			cury = pts.get(interval).getY();
			midx = (curx + prevx) / 2d;
			midy = (cury + prevy) / 2d;

			border.getElements().add(pathProducer.createLineTo(midx, midy));
		}

		// Adding curves
		for(i = interval * 2; i < size; i += interval) {
			x1 = (midx + curx) / 2d;
			y1 = (midy + cury) / 2d;
			prevx = curx;
			prevy = cury;
			curx = pts.get(i).getX();
			cury = pts.get(i).getY();
			midx = (curx + prevx) / 2d;
			midy = (cury + prevy) / 2d;
			x2 = (prevx + midx) / 2d;
			y2 = (prevy + midy) / 2d;

			border.getElements().add(pathProducer.createCubicCurveTo(x1, y1, x2, y2, midx, midy));
		}

		// If it remains not used points.
		if(i - interval + 1 < size) {
			x1 = (midx + curx) / 2d;
			y1 = (midy + cury) / 2d;
			prevx = curx;
			prevy = cury;
			curx = pts.get(size - 1).getX();
			cury = pts.get(size - 1).getY();
			midx = (curx + prevx) / 2d;
			midy = (cury + prevy) / 2d;
			x2 = (prevx + midx) / 2d;
			y2 = (prevy + midy) / 2d;

			border.getElements().add(pathProducer.createCubicCurveTo(x1, y1, x2, y2, pts.get(size - 1).getX(), pts.get(size - 1).getY()));
		}
	}


	/**
	 * Fills the path of lines.
	 */
	private void setPathLines() {
		final int interval = model.getInterval();
		final List<Point> pts = model.getPoints();
		final int size = pts.size();
		Point pt = pts.get(0);
		int i;

		border.getElements().add(pathProducer.createMoveTo(pt.getX(), pt.getY()));

		for(i = interval; i < size; i += interval) {
			pt = pts.get(i);
			border.getElements().add(pathProducer.createLineTo(pt.getX(), pt.getY()));
		}

		if(i - interval < size) {
			border.getElements().add(pathProducer.createLineTo(pts.get(size - 1).getX(), pts.get(size - 1).getY()));
		}
	}

	@Override
	public void flush() {
		if(!border.getElements().isEmpty()) {
			model.getPoints().get(model.getPoints().size() - 1).xProperty().removeListener(update);
			model.getPoints().get(model.getPoints().size() - 1).yProperty().removeListener(update);
		}

		model.intervalProperty().removeListener(update);
		model.typeProperty().removeListener(update);
		model.openedProperty().removeListener(update);

		super.flush();
	}
}
