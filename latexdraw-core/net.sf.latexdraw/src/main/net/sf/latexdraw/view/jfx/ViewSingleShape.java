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

import javafx.beans.binding.Bindings;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import net.sf.latexdraw.glib.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.glib.models.interfaces.shape.LineStyle;

/**
 * The base class of a JFX single shape view.<br>
 * 2015-11-29<br>
 * @author Arnaud BLOUIN
 * @since 4.0
 * @param <S> The type of the model.
 * @param <T> The type of the JFX shape used to draw the view.
 */
public abstract class ViewSingleShape<S extends ISingleShape, T extends Shape> extends ViewShape<S, T> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	public ViewSingleShape(final @NonNull S sh) {
		super(sh);

		border.setStrokeLineJoin(StrokeLineJoin.MITER);

		if(model.isThicknessable()) {
			border.strokeWidthProperty().bind(model.thicknessProperty());
		}

		if(model.isLineStylable()) {
			model.linestyleProperty().addListener((obj, oldVal, newVal) -> updateLineStyle(newVal));
			updateLineStyle(model.getLineStyle());
		}

		border.strokeProperty().bind(Bindings.createObjectBinding(() -> model.getLineColour().toJFX(), model.lineColourProperty()));

		bindBorderMovable();
		border.setFill(null);
	}

	private void bindBorderMovable() {
		if(model.isBordersMovable()) {
			border.strokeTypeProperty().bind(Bindings.createObjectBinding(() -> {
				switch(model.getBordersPosition()) {
					case INTO:
						return StrokeType.INSIDE;
					case MID:
						return StrokeType.CENTERED;
					case OUT:
						return StrokeType.OUTSIDE;
					default:
						return StrokeType.INSIDE;
				}
			}, model.borderPosProperty()));
		}
	}

	private void updateLineStyle(final LineStyle newVal) {
		switch(newVal) {
			case DASHED:
				border.setStrokeLineCap(StrokeLineCap.BUTT);
				border.getStrokeDashArray().addAll(model.getDashSepBlack(), model.getDashSepWhite());
				break;
			case DOTTED:// FIXME problem when dotted line + INTO/OUT border position.
				final double dotSep = model.getDotSep() + (model.hasDbleBord()?model.getThickness() * 2.0 + model.getDbleBordSep():model.getThickness());
				border.setStrokeLineCap(StrokeLineCap.ROUND);
				border.getStrokeDashArray().addAll(0.0, dotSep);
				break;
			case SOLID:
				border.setStrokeLineCap(StrokeLineCap.SQUARE);
				border.getStrokeDashArray().clear();
				break;
		}
	}
}
