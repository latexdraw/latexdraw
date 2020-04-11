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

import io.github.interacto.jfx.command.Save;
import io.github.interacto.jfx.ui.JfxUI;
import io.github.interacto.jfx.ui.OpenSaver;
import java.io.File;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Injector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A command for saving a drawing as an SVG document.
 * @author Arnaud Blouin
 */
public class SaveDrawing extends Save<Label> {
	/**
	 * Show the export dialog to select a path.
	 */
	protected static Optional<File> showDialog(final @NotNull FileChooser fc, final boolean saveAs, final @Nullable File file,
		final @NotNull Optional<File> currFolder, final @NotNull JfxUI ui, final Stage stage) {
		File f;

		if(saveAs || (file == null && ui.isModified())) {
			currFolder.ifPresent(dir -> fc.setInitialDirectory(dir));
			f = fc.showSaveDialog(stage);
		}else {
			f = file;
		}

		if(f != null && !f.getPath().toLowerCase().endsWith(".svg")) { //NON-NLS
			f = new File(f.getPath() + ".svg"); //NON-NLS
		}

		return Optional.ofNullable(f);
	}


	/** True: A dialog bow will be always shown to ask the location to save. */
	private boolean saveAs;
	/** True: the app will be closed after the drawing saved. */
	private final boolean saveOnClose;
	private final @NotNull Optional<File> currentFolder;
	/** The file chooser that will be used to select the location to save. */
	private final @NotNull FileChooser fileChooser;
	private final @NotNull Injector injector;
	private final @NotNull Stage mainstage;
	private final @NotNull Alert modifiedAlert;

	public SaveDrawing(final boolean saveAs, final boolean saveOnClose, final @NotNull Optional<File> currentFolder, final @NotNull FileChooser fileChooser,
				final @NotNull Injector injector, final File file, final @NotNull OpenSaver<Label> openSaveManager, final @NotNull ProgressBar bar,
		final @NotNull JfxUI ui, final @NotNull Label statusWidget, final @NotNull Stage mainstage, final @NotNull Alert modifiedAlert) {
		super(file, openSaveManager, bar, statusWidget, ui);
		this.saveAs = saveAs;
		this.saveOnClose = saveOnClose;
		this.currentFolder = currentFolder;
		this.fileChooser = fileChooser;
		this.injector = injector;
		this.mainstage = mainstage;
		this.modifiedAlert = modifiedAlert;
	}

	@Override
	protected void doCmdBody() {
		if(saveOnClose) {
			saveOnClose();
		}else {
			if(file == null) {
				file = showDialog(fileChooser, saveAs, null, currentFolder, ui, mainstage).orElse(null);
			}
			if(file == null) {
				ok = false;
			}else {
				super.doCmdBody();
			}
		}
	}

	/**
	 * Does save on close.
	 */
	private void saveOnClose() {
		if(ui.isModified()) {
			saveAs = true;
			final ButtonType type = modifiedAlert.showAndWait().orElse(ButtonType.CANCEL);
			if(type == ButtonType.NO) {
				quit();
			}else {
				if(type == ButtonType.YES) {
					showDialog(fileChooser, saveAs, file, currentFolder, ui, mainstage).ifPresent(f -> {
						file = f;
						super.doCmdBody();
						quit();
					});
				}else {
					ok = false;
				}
			}
		}else {
			quit();
		}
	}

	private void quit() {
		injector.getInstance(PreferencesService.class).writePreferences();
		mainstage.close();
		injector.clear();
	}
}
