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
package net.sf.latexdraw.command;

import io.github.interacto.jfx.command.IOCommand;
import io.github.interacto.jfx.ui.JfxUI;
import java.nio.file.Path;
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
	private final @NotNull TextInputDialog nameInput;
	private final @NotNull Alert alertName;

	public ExportTemplate(final @NotNull Pane templatesPane, final @NotNull SVGDocumentGenerator svgGen, final @NotNull JfxUI ui,
			final @NotNull ProgressBar progressBar, final @NotNull Label statusWidget, final @NotNull Alert alertName,
			final @NotNull TextInputDialog nameInput) {
		super(null, svgGen, progressBar, statusWidget, ui);
		this.templatesPane = templatesPane;
		this.svgGen = svgGen;
		this.nameInput = nameInput;
		this.alertName = alertName;
	}

	@Override
	protected void doCmdBody() {
		nameInput.showAndWait().ifPresent(name -> {
			final Path path = Path.of(SystemUtils.getInstance().getPathTemplatesDirUser(), name + ".svg");

			if(!path.toFile().exists() || alertName.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
				svgGen.saveTemplate(path.toString(), progressBar, statusWidget, templatesPane);
				ok = true;
			}
		});
	}
}
