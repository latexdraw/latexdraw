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

	/** This value defines the threshold under which sub-lines of the rule will be not drawn. */
	protected static final double MIN_PCC_SUBLINES = 20d;

	/** The size of the lines in axes */
	public static final int SIZE = 10;

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
			ScaleRuler.UNIT.setValue(unit);
		}
	}

	/**
	 * @return The singleton that contains the unit value.
	 */
	public static ObjectProperty<Unit> getUnitSingleton() {
		return ScaleRuler.UNIT;
	}

	/** The canvas that the ruler manages. */
	private Canvas canvas;

	private final Group group;
	private final boolean vertical;

	/**
	 * Creates the ruler.
	 */
	public ScaleRuler(@NamedArg("vertical") boolean verticalRuler) {
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
		if(canvas == null) return;

		group.getChildren().clear();

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

		return step * zoomedPPC;
	}

	private void updateHorizontal(final double width) {
		int min = 0;
		double zoomedPPC = canvas.getPPCDrawing() * canvas.getZoom();
		final double step = getMainStep() * zoomedPPC;

		group.getChildren().addAll(IntStream.range(min, (int) (width / step) + 1).parallel().
				mapToObj(i -> new Line(i * step, 0d, i * step, 10d)).collect(Collectors.toList()));
	}

	//	public void paintComponent(final Graphics g) {
//		final double zoom = canvas.getZoom();
//		final double lgth = getLength() / zoom;
//		final double start = getStart() / zoom;
//		double ppc = canvas.getPPCDrawing();
//		final double sizeZoomed = SIZE / zoom;
//		double i;
//		double j;
//		double cpt;
//
//		// adjusting the ppc value according to the current unit.
//		if(getUnit() == Unit.INCH) ppc *= PSTricksConstants.INCH_VAL_CM;
//
//		// Optimisation for limitating the painting to the visible part only.
//		final double clipStart = (int) (getClippingGap() / zoom / ppc) * ppc;
//
//		// Settings the parameters of the graphics.
//		adaptGraphicsToViewpoint(g2);
//		g2.scale(zoom, zoom);
//
//		// If the ppc is not to small sub-lines are drawn.
//		if(ppc > MIN_PCC_SUBLINES / zoom) {
//			final double ppc10 = ppc / 10.;
//			final double halfSizeZoomed = sizeZoomed / 2.;
//
//			for(i = start + ppc10 + clipStart; i < lgth; i += ppc)
//				for(j = i, cpt = 1; cpt < 10; j += ppc10, cpt++)
//					drawLine(g2, j, halfSizeZoomed, sizeZoomed);
//		}
//
//		// Major lines of the ruler are drawn.
//		for(i = start + clipStart; i < lgth; i += ppc)
//			drawLine(g2, i, 0., sizeZoomed);
//	}
}
