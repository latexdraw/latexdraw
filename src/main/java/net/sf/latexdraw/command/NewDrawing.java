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

import io.github.interacto.command.CommandsRegistry;
import io.github.interacto.jfx.command.IOCommand;
import io.github.interacto.jfx.ui.JfxUI;
import io.github.interacto.jfx.ui.OpenSaver;
import io.github.interacto.undo.UndoCollector;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.latexdraw.util.BadaboomCollector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This command permits to create a new drawing and initialises the application as required.
 * @author Arnaud Blouin
 */
public class NewDrawing extends IOCommand<Label> implements Modifying {
	/** The file chooser that will be used to select the location to save. */
	private final @NotNull FileChooser fileChooser;
	/** The instrument used that manage the preferences. */
	private final @NotNull Optional<File> currentFolder;
	private final @NotNull Stage mainstage;
	private final @NotNull Alert modifiedAlert;

	public NewDrawing(final @Nullable File file, final @NotNull OpenSaver<Label> openSaveManager, final @NotNull ProgressBar progressBar,
		final @NotNull Label statusWidget, final @NotNull JfxUI ui, final @NotNull FileChooser fileChooser, final @NotNull Optional<File> currentFolder,
		final @NotNull Stage mainstage, final @NotNull Alert modifiedAlert) {
		super(file, openSaveManager, progressBar, statusWidget, ui);
		this.fileChooser = fileChooser;
		this.currentFolder = currentFolder;
		this.mainstage = mainstage;
		this.modifiedAlert = modifiedAlert;
	}

	@Override
	protected void doCmdBody() {
		if(ui.isModified()) {
			doModified();
		}else {
			newDrawing();
		}
	}

	private void doModified() {
		final ButtonType type = modifiedAlert.showAndWait().orElse(ButtonType.CANCEL);

		if(type == ButtonType.NO) {
			newDrawing();
			return;
		}
		if(type == ButtonType.YES) {
			SaveDrawing.showDialog(fileChooser, true, file, currentFolder, ui, mainstage).ifPresent(f -> {
				try {
					openSaveManager.save(f.getPath(), progressBar, statusWidget).get();
					ui.setModified(false);
					newDrawing();
				}catch(final InterruptedException ex) {
					Thread.currentThread().interrupt();
					BadaboomCollector.INSTANCE.add(ex);
				}catch(final ExecutionException ex) {
					BadaboomCollector.INSTANCE.add(ex);
				}
			});
		}
	}


	protected void newDrawing() {
		ok = true;
		ui.reinit();
		UndoCollector.getInstance().clear();
		CommandsRegistry.getInstance().clear();
	}
}
