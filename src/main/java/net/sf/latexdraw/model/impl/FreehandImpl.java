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
package net.sf.latexdraw.model.impl;

import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.FreeHandProp;
import net.sf.latexdraw.model.api.shape.FreeHandStyle;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of a free hand shape.
 * @author Arnaud Blouin
 */
class FreehandImpl extends ShapeBase implements Freehand {
	/** The type of the curves of the shape. */
	private final @NotNull ObjectProperty<FreeHandStyle> type;
	/** The interval to consider while painting the shape. */
	private final @NotNull IntegerProperty interval;
	/** Defines if the drawing is opened of closed. */
	private final @NotNull BooleanProperty open;


	/**
	 * Creates and initialises a freehand model.
	 * @throws IllegalArgumentException If the given point is not valid.
	 */
	FreehandImpl(final @NotNull List<Point> pts) {
		super();
		type = new SimpleObjectProperty<>(FreeHandStyle.CURVES);
		interval = new SimpleIntegerProperty(2);
		open = new SimpleBooleanProperty(true);
		points.addAll(pts);
	}

	@Override
	public @NotNull Freehand duplicate() {
		final Freehand dup = ShapeFactory.INST.createFreeHand(points.stream().
			map(pt -> ShapeFactory.INST.createPoint(pt.getX(), pt.getY())).collect(Collectors.toList()));
		dup.copy(this);
		return dup;
	}

	@Override
	public void copy(final Shape sh) {
		super.copy(sh);

		if(sh instanceof FreeHandProp) {
			final FreeHandProp fh = (FreeHandProp) sh;
			open.set(fh.isOpened());
			interval.set(fh.getInterval());
			type.set(fh.getType());
		}
	}

	@Override
	public int getInterval() {
		return interval.get();
	}

	@Override
	public void setInterval(final int newInterval) {
		if(newInterval > 0) {
			interval.set(newInterval);
		}
	}

	@Override
	public @NotNull FreeHandStyle getType() {
		return type.get();
	}

	@Override
	public void setType(final @NotNull FreeHandStyle freeHandStyle) {
		type.set(freeHandStyle);
	}

	@Override
	public boolean isOpened() {
		return open.get();
	}

	@Override
	public void setOpened(final boolean isOpen) {
		open.set(isOpen);
	}

	@Override
	public @NotNull ObjectProperty<FreeHandStyle> typeProperty() {
		return type;
	}

	@Override
	public @NotNull IntegerProperty intervalProperty() {
		return interval;
	}

	@Override
	public boolean isLineStylable() {
		return true;
	}

	@Override
	public @NotNull BooleanProperty openedProperty() {
		return open;
	}
}
