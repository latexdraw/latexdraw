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
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.models.interfaces.shape.IGroup;

/**
 * Puts shapes in background / foreground.
 * @author Arnaud BLOUIN
 */
public class ShapePositioner extends ShapePropertyCustomiser implements Initializable {
	/** The foreground button. */
	@FXML private Button foregroundB;
	/** The background button. */
	@FXML private Button backgroundB;
	@FXML private AnchorPane mainPane;

	/**
	 * Creates the instrument.
	 */
	ShapePositioner() {
		super();
	}

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new Button2MoveBackForeground(this));
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
	protected void update(final IGroup shape) {
		setActivated(hand.isActivated() && !shape.isEmpty());
	}
}

// /** This link maps a button interaction to an action that puts shapes in
// foreground / background. */
// class Button2MoveBackForeground extends
// InteractorImpl<MoveBackForegroundShapes, ButtonPressed, ShapePositioner> {
// protected Button2MoveBackForeground(final ShapePositioner ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, MoveBackForegroundShapes.class, ButtonPressed.class);
// }
//
// @Override
// public void initAction() {
// action.setIsForeground(interaction.getButton()==instrument.foregroundB);
// action.setDrawing(instrument.pencil.canvas().getDrawing());
// action.setShape(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getButton()==instrument.backgroundB ||
// interaction.getButton()==instrument.foregroundB;
// }
// }
