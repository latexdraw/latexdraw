/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import net.sf.latexdraw.view.latex.LaTeXGenerator;

/**
 * Manages the code panel.
 */
public class CodePanelController extends CanvasInstrument implements Initializable {
	@FXML TextArea codeArea;
	@FXML Tab tab;

	/** The PSTricks generator. */
	@Inject LaTeXGenerator pstGenerator;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		tab.selectedProperty().addListener(evt -> codeArea.setText(pstGenerator.getDrawingCode()));
	}


	@Override
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
		// Nothing to do.
	}
}
