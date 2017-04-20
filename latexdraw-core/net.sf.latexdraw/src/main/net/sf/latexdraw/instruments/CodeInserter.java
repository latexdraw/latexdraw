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

import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.actions.InsertPSTCode;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.util.LangTool;
import org.malai.action.library.InactivateInstrument;
import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument converts PST code into graphical shapes.
 * @author Arnaud BLOUIN
 */
public class CodeInserter extends JfxInstrument implements Initializable {
	@FXML private WebView label;
	@FXML private Button ok;
	@FXML private Button cancel;
	@FXML private TextArea text;
	private Stage codeInserterDialogue;
	@Inject private IDrawing drawing;
	@Inject private StatusBarController statusBar;

	/**
	 * Creates the instrument.
	 */
	CodeInserter() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		label.getEngine().loadContent(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.16"));
	}


	/** @return The created latexdraw dialogue box. */
	private Stage getInsertCodeDialogue() {
		if(codeInserterDialogue == null) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/fxml/InsertCode.fxml"), LangTool.INSTANCE.getBundle(),
					new JavaFXBuilderFactory(), LaTeXDraw.getINSTANCE().getInstanceCallBack());
				final Scene scene = new Scene(root);
				codeInserterDialogue = new Stage(StageStyle.UTILITY);
				codeInserterDialogue.setTitle(LangTool.INSTANCE.getBundle().getString("InsertPSTricksCodeFrame.0"));
				codeInserterDialogue.setScene(scene);
				codeInserterDialogue.setOnHiding(evt -> setActivated(false));
			}catch(final Exception ex) {
				ex.printStackTrace();
				BadaboomCollector.INSTANCE.add(ex);
			}
		}
		return codeInserterDialogue;
	}

	@Override
	public void initialiseInteractors() throws IllegalAccessException, InstantiationException {
		addButtonInteractor(InsertPSTCode.class, action -> {
			 action.setDrawing(drawing);
			 action.setCode(text.getText());
			 action.setStatusBar(statusBar.getLabel());
		}, ok);
		addButtonInteractor(InactivateInstrument.class, action -> action.setInstrument(this), cancel, ok);
	}

	@Override
	public void setActivated(final boolean activated) {
		if(isActivated() != activated) {
			final Stage dialogue = getInsertCodeDialogue();
			super.setActivated(activated);
			if(activated) {
				dialogue.show();
				dialogue.centerOnScreen();
			}else {
				dialogue.hide();
			}
		}
	}
}