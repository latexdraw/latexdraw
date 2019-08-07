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

import io.github.interacto.command.Command;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.transform.Translate;
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
public class ShapeCoordDimCustomiser extends ShapePropertyCustomiser implements Initializable {
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
	public void onCmdExecuted(final Command cmd) {
		if(cmd instanceof Translate) {
			update();
		}
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void configureBindings() {
		spinnerBinder(i -> new TranslateShapes(drawing, drawing.getSelection().duplicateDeep(false))).on(tlxS).
			then((i, c) -> c.setT((Double) i.getWidget().getValue() - c.getShape().getTopLeftPoint().getX(), 0d)).
			exec().bind();

		spinnerBinder(i -> new TranslateShapes(drawing, drawing.getSelection().duplicateDeep(false))).on(tlyS).
			then((i, c) -> c.setT(0d, (Double) i.getWidget().getValue() - c.getShape().getTopLeftPoint().getY())).
			exec().bind();
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}
}
