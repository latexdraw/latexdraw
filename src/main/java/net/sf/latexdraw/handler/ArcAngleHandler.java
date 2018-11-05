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
package net.sf.latexdraw.handler;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Arc;
import net.sf.latexdraw.model.api.shape.Point;

/**
 * A handler that changes the start/end angle of an arc.
 * @author Arnaud BLOUIN
 */
public class ArcAngleHandler extends Rectangle implements Handler {
	/** Defines whether the handled angle is the starting or the ending angle. */
	private final boolean start;

	/**
	 * Creates and initialises an arc angle handler.
	 * @param isStart Defines whether the handled angle is the starting or the ending angle.
	 */
	public ArcAngleHandler(final boolean isStart) {
		super();
		start = isStart;
		setWidth(DEFAULT_SIZE);
		setHeight(DEFAULT_SIZE);
		setStroke(null);
		setFill(DEFAULT_COLOR);
	}

	public void setCurrentArc(final Arc arc) {
		translateXProperty().unbind();
		translateYProperty().unbind();

		translateXProperty().bind(Bindings.createDoubleBinding(() -> getPosition(arc).getX() - DEFAULT_SIZE / 2d,
			arc.angleStartProperty(), arc.angleEndProperty(), arc.getPtAt(0).xProperty(), arc.getPtAt(2).xProperty(), arc.rotationAngleProperty()));
		translateYProperty().bind(Bindings.createDoubleBinding(() -> getPosition(arc).getY() - DEFAULT_SIZE / 2d,
			arc.angleStartProperty(), arc.angleEndProperty(), arc.getPtAt(0).yProperty(), arc.getPtAt(2).yProperty(), arc.rotationAngleProperty()));
	}

	private Point getPosition(final Arc arc) {
		return start ? arc.getStartPoint().rotatePoint(arc.getGravityCentre(), arc.getRotationAngle()) :
			arc.getEndPoint().rotatePoint(arc.getGravityCentre(), arc.getRotationAngle());
	}
}
