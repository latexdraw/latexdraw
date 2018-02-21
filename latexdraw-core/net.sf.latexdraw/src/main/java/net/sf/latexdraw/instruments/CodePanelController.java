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
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.latex.LaTeXGenerator;

/**
 * Manages the code panel.
 * @author Arnaud Blouin
 */
public class CodePanelController extends CanvasInstrument implements Initializable {
	@FXML private TextArea codeArea;
	@FXML private Tab tabPST;
	/** The PSTricks generator. */
	@Inject private LaTeXGenerator pstGenerator;

	public CodePanelController() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		tabPST.selectedProperty().addListener(evt -> codeArea.setText(pstGenerator.getDrawingCode()));
	}

	@Override
	protected void configureBindings() {
		// Nothing to do.
	}
}
