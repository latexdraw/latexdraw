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
import javafx.scene.layout.Pane;
import net.sf.latexdraw.command.shape.UpdateToGrid;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.view.jfx.Canvas;

public class ShapeGridTransformer extends ShapePropertyCustomiser {
	/** The button to update the selected shapes to the magnetic grid. */
	@FXML private Button updateToGrid;
	@FXML private Pane mainPane;
	private final PreferencesService prefs;

	@Inject
	public ShapeGridTransformer(final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing,
		final EditingService editing, final PreferencesService prefs) {
		super(hand, pencil, canvas, drawing, editing);
		this.prefs = prefs;
	}

	@Override
	protected void update(final Group shape) {
		setActivated(hand.isActivated() && !shape.isEmpty() && prefs.getGridStyle() != GridStyle.NONE);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void configureBindings() {
		buttonBinder()
			.toProduce(() -> new UpdateToGrid(canvas.getMagneticGrid(), canvas.getDrawing().getSelection().duplicateDeep(false)))
			.on(updateToGrid)
			.bind();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		super.initialize(location, resources);
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}
}
