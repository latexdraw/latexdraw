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
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 * @author Arnaud Blouin
 */
public class ShapeTransformationController implements Initializable {
	@FXML TitledPane pane;
	@FXML VBox layout;

	ShapeTransformationController() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		pane.managedProperty().bind(pane.visibleProperty());

		pane.visibleProperty().bind(Bindings.createBooleanBinding(() -> layout.getChildren().stream().anyMatch(c -> c.isVisible()),
			layout.getChildren().stream().map(c -> c.visibleProperty()).toArray(size -> new BooleanProperty[size])));
	}
}
