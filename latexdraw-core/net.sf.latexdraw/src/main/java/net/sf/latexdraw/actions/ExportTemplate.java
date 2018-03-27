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
package net.sf.latexdraw.actions;

import java.io.File;
import java.nio.file.Paths;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.malai.javafx.action.IOAction;

/**
 * This action exports a set of shapes as a template.
 * @author Arnaud Blouin
 */
public class ExportTemplate extends IOAction<Label> implements TemplateAction {
	private Pane templatesPane;

	public ExportTemplate() {
		super();
	}

	@Override
	protected void doActionBody() {
		final TextInputDialog dialog = new TextInputDialog("templateFileName"); //NON-NLS
		dialog.setHeaderText(LangTool.INSTANCE.getBundle().getString("DrawContainer.nameTemplate"));

		dialog.showAndWait().ifPresent(name -> {
			final String path = LPath.PATH_TEMPLATES_DIR_USER + File.separator + name + ".svg"; //NON-NLS

			if(Paths.get(path).toFile().exists()) {
				final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(LangTool.INSTANCE.getBundle().getString("DrawContainer.overwriteTemplate"));
				alert.setTitle(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.42"));

				if(alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
					SVGDocumentGenerator.INSTANCE.saveTemplate(path, progressBar, statusWidget, templatesPane);
					ok = true;
				}
			}else {
				SVGDocumentGenerator.INSTANCE.saveTemplate(path, progressBar, statusWidget, templatesPane);
				ok = true;
			}
		});
	}

	@Override
	public boolean canDo() {
		return ui != null && templatesPane != null;
	}

	@Override
	public void setTemplatesPane(final Pane pane) {
		templatesPane = pane;
	}

	@Override
	public Pane getTemplatesPane() {
		return templatesPane;
	}
}
