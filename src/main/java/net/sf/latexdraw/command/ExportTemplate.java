/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command;

import io.github.interacto.jfx.command.IOCommand;
import io.github.interacto.jfx.ui.JfxUI;
import java.io.File;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * This command exports a set of shapes as a template.
 * @author Arnaud Blouin
 */
public class ExportTemplate extends IOCommand<Label> {
	private final @NotNull SVGDocumentGenerator svgGen;
	private final @NotNull Pane templatesPane;
	private final @NotNull ResourceBundle lang;

	public ExportTemplate(final @NotNull Pane templatesPane, final @NotNull SVGDocumentGenerator svgGen, final @NotNull ResourceBundle lang,
						final @NotNull JfxUI ui, final @NotNull ProgressBar progressBar, final @NotNull Label statusWidget) {
		super(null, svgGen, progressBar, statusWidget, ui);
		this.templatesPane = templatesPane;
		this.svgGen = svgGen;
		this.lang = lang;
	}

	@Override
	protected void doCmdBody() {
		final TextInputDialog dialog = new TextInputDialog("templateFileName"); //NON-NLS
		dialog.setHeaderText(lang.getString("DrawContainer.nameTemplate"));

		dialog.showAndWait().ifPresent(name -> {
			final String path = SystemUtils.getInstance().getPathTemplatesDirUser() + File.separator + name + ".svg"; //NON-NLS

			if(Paths.get(path).toFile().exists()) {
				final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(lang.getString("DrawContainer.overwriteTemplate"));
				alert.setTitle(lang.getString("LaTeXDrawFrame.42"));

				if(alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
					svgGen.saveTemplate(path, progressBar, statusWidget, templatesPane);
					ok = true;
				}
			}else {
				svgGen.saveTemplate(path, progressBar, statusWidget, templatesPane);
				ok = true;
			}
		});
	}
}
