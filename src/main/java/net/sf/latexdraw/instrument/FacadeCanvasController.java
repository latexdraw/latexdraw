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
import javafx.fxml.Initializable;
import net.sf.latexdraw.util.Inject;

/**
 * A facade controller for the canvas as a FXML document can be controlled by a single controller.
 * @author Arnaud Blouin
 */
public class FacadeCanvasController implements Initializable {
	@Inject Border border;
	@Inject CanvasController canvasController;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		border.initialize(location, resources);
		canvasController.initialize(location, resources);
	}
}
