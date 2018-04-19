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
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.commands.shape.TranslateShapes;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * This instrument modifies arc dimensions and coordinates of shapes or pencil parameters.
 * @author Arnaud BLOUIN
 */
public class ShapeCoordDimCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** Sets the X-coordinate of the top-left position. */
	@FXML private Spinner<Double> tlxS;
	/** Sets the Y-coordinate of the top-left position. */
	@FXML private Spinner<Double> tlyS;
	@FXML private TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeCoordDimCustomiser() {
		super();
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isEmpty() || !hand.isActivated()) {
			setActivated(false);
		}else {
			setActivated(true);
			final IPoint tl = shape.getTopLeftPoint();
			tlxS.getValueFactory().setValue(tl.getX());
			tlyS.getValueFactory().setValue(tl.getY());
		}
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void configureBindings() {
		spinnerBinder(TranslateShapes.class).on(tlxS).
			map(i -> new TranslateShapes(drawing, drawing.getSelection().duplicateDeep(false))).
			then((i, c) -> c.setT((Double) i.getWidget().getValue() - c.getShape().get().getTopLeftPoint().getX(), 0d)).
			exec().bind();

		spinnerBinder(TranslateShapes.class).on(tlyS).
			map(i -> new TranslateShapes(drawing, drawing.getSelection().duplicateDeep(false))).
			then((i, c) -> c.setT(0d, (Double) i.getWidget().getValue() - c.getShape().get().getTopLeftPoint().getY())).
			exec().bind();
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}
}
