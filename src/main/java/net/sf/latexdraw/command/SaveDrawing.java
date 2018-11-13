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
package net.sf.latexdraw.command;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.latexdraw.service.PreferencesService;
import org.malai.javafx.command.Save;
import org.malai.javafx.ui.JfxUI;
import org.malai.javafx.ui.OpenSaver;

/**
 * A command for saving a drawing as an SVG document.
 * @author Arnaud Blouin
 */
public class SaveDrawing extends Save<Label> {
	/**
	 * Show the export dialog to select a path.
	 */
	protected static Optional<File> showDialog(final FileChooser fc, final boolean saveAs, final File file, final Optional<File> currFolder,
												final JfxUI ui, final Stage stage) {
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

	protected static ButtonType showAskModificationsDialog(final ResourceBundle lang) {
		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(lang.getString("Actions.2"));
		alert.setHeaderText(lang.getString("LaTeXDrawFrame.188"));
		alert.getButtonTypes().setAll(ButtonType.NO, ButtonType.YES, ButtonType.CANCEL);
		return alert.showAndWait().orElse(ButtonType.CANCEL);
	}

	/** True: A dialog bow will be always shown to ask the location to save. */
	private boolean saveAs;
	/** True: the app will be closed after the drawing saved. */
	private final boolean saveOnClose;
	private final Optional<File> currentFolder;
	/** The file chooser that will be used to select the location to save. */
	private FileChooser fileChooser;
	private final PreferencesService prefService;
	private final Stage mainstage;

	public SaveDrawing(final boolean saveAs, final boolean saveOnClose, final Optional<File> currentFolder, final FileChooser fileChooser,
				final PreferencesService prefService, final File file, final OpenSaver<Label> openSaveManager, final ProgressBar bar, final JfxUI ui,
				final Label statusWidget, final Stage mainstage) {
		super(file, openSaveManager, bar, statusWidget, ui);
		this.saveAs = saveAs;
		this.saveOnClose = saveOnClose;
		this.currentFolder = currentFolder;
		this.fileChooser = fileChooser;
		this.prefService = prefService;
		this.mainstage = mainstage;
	}

	@Override
	public boolean canDo() {
		return openSaveManager != null && fileChooser != null && ui != null && prefService != null;
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
			final ButtonType type = showAskModificationsDialog(prefService.getBundle());
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
		prefService.writePreferences();
		mainstage.close();
	}

	@Override
	public void flush() {
		super.flush();
		fileChooser = null;
	}
}
