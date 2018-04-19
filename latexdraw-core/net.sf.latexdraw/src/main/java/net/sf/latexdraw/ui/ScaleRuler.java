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
package net.sf.latexdraw.ui;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.beans.NamedArg;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.jfx.Canvas;

/**
 * An abstract scale ruler.
 * @author Arnaud BLOUIN
 */
public class ScaleRuler extends Pane {
	/** The current unit of the rulers. */
	protected static final ObjectProperty<Unit> UNIT = new SimpleObjectProperty<>(Unit.CM);

	/**
	 * @return the current unit used by the rulers.
	 */
	public static Unit getUnit() {
		return UNIT.getValue();
	}

	/**
	 * @param unit The unit that the rulers must use. Must not be null.
	 */
	public static void setUnit(final Unit unit) {
		if(unit != null) {
			UNIT.setValue(unit);
		}
	}


	/** The canvas that the ruler manages. */
	private Canvas canvas;
	private final Group group;
	private final boolean vertical;

	/**
	 * Creates the ruler.
	 */
	public ScaleRuler(@NamedArg("vertical") final boolean verticalRuler) {
		super();
		vertical = verticalRuler;
		group = new Group();
		getChildren().add(group);
		setFocusTraversable(false);
	}

	public void setCanvas(final Canvas canvas) {
		this.canvas = canvas;
	}

	public Group getGroup() {
		return group;
	}

	public void update(final double width, final double height) {
		if(canvas == null) {
			return;
		}

		group.getChildren().clear();
			updateVertical(height);
		if(vertical) {

		}else {
			updateHorizontal(width);
		}
	}

	private double getMainStep() {
		double step = 1d;
		final double zoomedPPC = canvas.getPPCDrawing() * canvas.getZoom();
		while(zoomedPPC * step < 80d) {
			step *= 2d;
		}
		return step;
	}

	private double getSubStep() {
		double step = getMainStep() / 10d;
		final double zoomedPPC = canvas.getPPCDrawing() * canvas.getZoom();
		while(zoomedPPC * step < 5d) {
			step *= 2d;
		}
		return step;
	}

	private void updateVertical(final double height) {
		final double zoomedPPC = canvas.getPPCDrawing() * canvas.getZoom();
		final double step = getMainStep() * zoomedPPC;
		final double substep = getSubStep() * zoomedPPC;
		final int incr = (int) (step / substep);

		group.getChildren().addAll(IntStream.range(0, (int) (height / step) + 1).parallel().
			mapToObj(i -> new Line(0d, i * step, 10d, i * step)).collect(Collectors.toList()));

		group.getChildren().addAll(IntStream.range(-1, (int) (height / step) + 1).parallel().
			mapToObj(j -> IntStream.range(1, incr).parallel().
				mapToObj(i -> new Line(0d, j * step + i * substep, 5d, j * step + i * substep))).
			flatMap(s -> s).collect(Collectors.toList()));
	}

	private void updateHorizontal(final double width) {
		final double zoomedPPC = canvas.getPPCDrawing() * canvas.getZoom();
		final double step = getMainStep() * zoomedPPC;
		final double substep = getSubStep() * zoomedPPC;
		final int incr = (int) (step / substep);

		group.getChildren().addAll(IntStream.range(0, (int) (width / step) + 1).parallel().
			mapToObj(i -> new Line(i * step, 0d, i * step, 10d)).collect(Collectors.toList()));

		group.getChildren().addAll(IntStream.range(-1, (int) (width / step) + 1).parallel().
			mapToObj(j -> IntStream.range(1, incr).parallel().
			mapToObj(i -> new Line(j * step + i * substep, 0d, j * step + i * substep, 5d))).
			flatMap(s -> s).collect(Collectors.toList()));
	}
}
