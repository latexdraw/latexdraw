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
package net.sf.latexdraw.commands;

import java.io.File;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.instruments.PreferencesSetter;
import net.sf.latexdraw.util.LangTool;
import org.malai.javafx.command.Save;
import org.malai.javafx.ui.JfxUI;
import org.malai.javafx.ui.OpenSaver;

/**
 * A command for saveing a drawing as an SVG document.
 * @author Arnaud Blouin
 */
public class SaveDrawing extends Save<Label> {
	/**
	 * Show the export dialog to select a path.
	 * @since 3.0
	 */
	protected static Optional<File> showDialog(final FileChooser fc, final boolean saveAs, final File file, final File currFolder, final JfxUI ui) {
		File f;

		if(saveAs || (file == null && ui.isModified())) {
			fc.setInitialDirectory(currFolder);
			f = fc.showSaveDialog(LaTeXDraw.getInstance().getMainStage());
		}else {
			f = file;
		}

		if(f != null && !f.getPath().toLowerCase().endsWith(".svg")) { //NON-NLS
			f = new File(f.getPath() + ".svg"); //NON-NLS
		}

		return Optional.ofNullable(f);
	}

	protected static ButtonType showAskModificationsDialog() {
		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(LangTool.INSTANCE.getBundle().getString("Actions.2"));
		alert.setHeaderText(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.188"));
		alert.getButtonTypes().setAll(ButtonType.NO, ButtonType.YES, ButtonType.CANCEL);
		return alert.showAndWait().orElse(ButtonType.CANCEL);
	}

	/** True: A dialog bow will be always shown to ask the location to save. */
	private boolean saveAs;
	/** True: the app will be closed after the drawing saved. */
	private final boolean saveOnClose;
	private final File currentFolder;
	/** The file chooser that will be used to select the location to save. */
	private FileChooser fileChooser;
	private final PreferencesSetter prefSetter;

	public SaveDrawing(final boolean saveAs, final boolean saveOnClose, final File currentFolder, final FileChooser fileChooser,
				final PreferencesSetter prefSetter, final File file, final OpenSaver<Label> openSaveManager, final ProgressBar bar, final JfxUI ui,
				final Label statusWidget) {
		super(file, openSaveManager, bar, statusWidget, ui);
		this.saveAs = saveAs;
		this.saveOnClose = saveOnClose;
		this.currentFolder = currentFolder;
		this.fileChooser = fileChooser;
		this.prefSetter = prefSetter;
	}

	@Override
	public boolean canDo() {
		return openSaveManager != null && fileChooser != null && ui != null && prefSetter != null;
	}

	@Override
	protected void doCmdBody() {
		if(saveOnClose) {
			saveOnClose();
		}else {
			if(file == null) {
				file = showDialog(fileChooser, saveAs, null, currentFolder, ui).orElse(null);
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
			final ButtonType type = showAskModificationsDialog();
			if(type == ButtonType.NO) {
				quit();
			}else {
				if(type == ButtonType.YES) {
					showDialog(fileChooser, saveAs, file, currentFolder, ui).ifPresent(f -> {
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
		prefSetter.writeXMLPreferences();
		LaTeXDraw.getInstance().getMainStage().close();
	}

	@Override
	public void flush() {
		super.flush();
		fileChooser = null;
	}
}
