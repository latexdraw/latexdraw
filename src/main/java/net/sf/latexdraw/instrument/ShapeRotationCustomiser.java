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
package net.sf.latexdraw.instrument;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import net.sf.latexdraw.command.shape.RotateShapes;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;

/**
 * This instrument modifies the rotation angle of selected shapes.
 * @author Arnaud BLOUIN
 */
public class ShapeRotationCustomiser extends ShapePropertyCustomiser {
	/** The rotation button to perform 90 degree rotation. */
	@FXML private Button rotate90Button;
	/** The rotation button to perform 180 degree rotation. */
	@FXML private Button rotate180Button;
	/** The rotation button to perform 270 degree rotation. */
	@FXML private Button rotate270Button;
	/** The field that modifies the rotation angle. */
	@FXML private Spinner<Double> rotationField;
	@FXML private Pane mainPane;

	@Inject
	public ShapeRotationCustomiser(final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing, final EditingService editing) {
		super(hand, pencil, canvas, drawing, editing);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		super.initialize(location, resources);
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void update(final Group shape) {
		if(!hand.isActivated() || shape.isEmpty()) {
			setActivated(false);
		}else {
			rotationField.getValueFactory().setValue(Math.toDegrees(shape.getRotationAngle()));
			setActivated(true);
		}
	}

	@Override
	protected void configureBindings() {
		spinnerBinder()
			.toProduce(() -> new RotateShapes(canvas.getDrawing().getSelection().getGravityCentre(),
				canvas.getDrawing().getSelection().duplicateDeep(false),
				Math.toRadians(rotationField.getValue()) - canvas.getDrawing().getSelection().getRotationAngle()))
			.on(rotationField)
			.then(c -> c.setRotationAngle(Math.toRadians(rotationField.getValue()) - canvas.getDrawing().getSelection().getRotationAngle()))
			.continuousExecution()
			.bind();

		buttonBinder()
			.toProduce(() -> new RotateShapes(canvas.getDrawing().getSelection().getGravityCentre(), canvas.getDrawing().getSelection().duplicateDeep(false), Math.PI / 2d))
			.on(rotate90Button)
			.bind();

		buttonBinder()
			.toProduce(() -> new RotateShapes(canvas.getDrawing().getSelection().getGravityCentre(), canvas.getDrawing().getSelection().duplicateDeep(false), Math.PI))
			.on(rotate180Button)
			.bind();

		buttonBinder()
			.toProduce(() -> new RotateShapes(canvas.getDrawing().getSelection().getGravityCentre(), canvas.getDrawing().getSelection().duplicateDeep(false), -Math.PI / 2d))
			.on(rotate270Button)
			.bind();
	}
}
