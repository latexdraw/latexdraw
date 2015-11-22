/*
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 */
package net.sf.latexdraw.view.jfx;

import org.eclipse.jdt.annotation.NonNull;

import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.LineStyle;

/**
 * The base class of a JFX shape view.<br>
 * 2015-11-13<br>
 * @author Arnaud BLOUIN
 * @since 4.0
 * @param <S> The type of the model.
 * @param <T> The type of the JFX shape used to draw the view.
 */
public abstract class ViewShape<S extends IShape, T extends Shape> extends Group {
	/** The model of the view. */
	protected final @NonNull S model;
	protected final @NonNull T border;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	public ViewShape(final @NonNull S sh) {
		super();
		model = sh;
		border = createJFXShape();

		border.setStrokeLineJoin(StrokeLineJoin.MITER);

		if(model.isThicknessable()) {
			border.strokeWidthProperty().bind(model.thicknessProperty());
		}

		if(model.isLineStylable()) {
			model.linestyleProperty().addListener((obj, oldVal, newVal) -> updateLineStyle(newVal));
			updateLineStyle(model.getLineStyle());
		}

		getChildren().add(border);
	}
	
	protected void updateLineStyle(final LineStyle newVal) {
		switch(newVal) {
			case DASHED:
				border.setStrokeLineCap(StrokeLineCap.BUTT);
				border.setStrokeDashOffset(0.0);
				border.getStrokeDashArray().addAll(model.getDashSepBlack(), model.getDashSepWhite());
				break;
			case DOTTED:
				final double dotSep = model.getDotSep() + (model.hasDbleBord()?model.getThickness() * 2.0 + model.getDbleBordSep():model.getThickness());
				border.setStrokeLineCap(StrokeLineCap.ROUND);
				border.setStrokeDashOffset(0.0);
				border.getStrokeDashArray().addAll(0.0, dotSep);
				break;
			case SOLID:
				border.setStrokeLineCap(StrokeLineCap.SQUARE);
				border.getStrokeDashArray().clear();
				break;
		}
	}

	protected abstract @NonNull T createJFXShape();

	/**
	 * Flushes the view.
	 */
	public void flush() {
		// Should be overridden to flush the bindings.
	}
}
