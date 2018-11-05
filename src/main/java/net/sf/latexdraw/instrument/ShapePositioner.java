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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import net.sf.latexdraw.command.shape.MoveBackForegroundShapes;
import net.sf.latexdraw.model.api.shape.Group;

/**
 * Puts shapes in background / foreground.
 * @author Arnaud BLOUIN
 */
public class ShapePositioner extends ShapePropertyCustomiser implements Initializable {
	/** The foreground button. */
	@FXML private Button foregroundB;
	/** The background button. */
	@FXML private Button backgroundB;
	@FXML private Pane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapePositioner() {
		super();
	}

	@Override
	protected void configureBindings() {
		buttonBinder(() -> new MoveBackForegroundShapes(canvas.getDrawing().getSelection().duplicateDeep(false), true, canvas.getDrawing())).
			on(foregroundB).bind();
		buttonBinder(() -> new MoveBackForegroundShapes(canvas.getDrawing().getSelection().duplicateDeep(false), false, canvas.getDrawing())).
			on(backgroundB).bind();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void update(final Group shape) {
		setActivated(hand.isActivated() && !shape.isEmpty());
	}
}
