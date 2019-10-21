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
package net.sf.latexdraw.instrument;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.command.shape.TranslateShapes;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;

/**
 * This instrument modifies arc dimensions and coordinates of shapes or pencil parameters.
 * @author Arnaud BLOUIN
 */
public class ShapeCoordDimCustomiser extends ShapePropertyCustomiser {
	/** Sets the X-coordinate of the top-left position. */
	@FXML private Spinner<Double> tlxS;
	/** Sets the Y-coordinate of the top-left position. */
	@FXML private Spinner<Double> tlyS;
	@FXML private TitledPane mainPane;

	@Inject
	public ShapeCoordDimCustomiser(final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing, final EditingService editing) {
		super(hand, pencil, canvas, drawing, editing);
	}

	@Override
	protected void update(final Group shape) {
		if(shape.isEmpty() || !hand.isActivated()) {
			setActivated(false);
		}else {
			setActivated(true);
			final Point tl = shape.getTopLeftPoint();
			tlxS.getValueFactory().setValue(tl.getX());
			tlyS.getValueFactory().setValue(tl.getY());
		}
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		super.initialize(location, resources);
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void configureBindings() {
		final var bindingFragment = spinnerBinder()
			.toProduce(i -> new TranslateShapes(drawing, drawing.getSelection().duplicateDeep(false)))
			.continuousExecution()
			.end(i -> update());

		bindingFragment
			.on(tlxS)
			.then((i, c) -> c.setT((Double) i.getWidget().getValue() - c.getShape().getTopLeftPoint().getX(), 0d))
			.bind();

		bindingFragment
			.on(tlyS)
			.then((i, c) -> c.setT(0d, (Double) i.getWidget().getValue() - c.getShape().getTopLeftPoint().getY()))
			.bind();
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}
}
