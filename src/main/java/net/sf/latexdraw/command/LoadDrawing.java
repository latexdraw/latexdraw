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
import java.util.concurrent.ExecutionException;
import javafx.concurrent.Task;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.latexdraw.util.LangService;
import org.malai.javafx.command.Load;
import org.malai.javafx.ui.JfxUI;
import org.malai.javafx.ui.OpenSaver;

/**
 * This command loads an SVG document into the app.
 * @author Arnaud Blouin
 */
public class LoadDrawing extends Load<Label> implements Modifying {
	/** The file chooser that will be used to select the location to save. */
	private FileChooser fileChooser;
	private final File currentFolder;
	private final LangService lang;
	private final Stage mainstage;

	public LoadDrawing(final File file, final OpenSaver<Label> openSaveManager, final ProgressBar progressBar, final Label statusWidget, final JfxUI ui,
				final FileChooser fileChooser, final File currentFolder, final LangService lang, final Stage mainstage) {
		super(file, openSaveManager, progressBar, statusWidget, ui);
		this.fileChooser = fileChooser;
		this.currentFolder = currentFolder;
		this.lang = lang;
		this.mainstage = mainstage;
	}

	@Override
	protected void doCmdBody() {
		if(ui.isModified()) {
			final ButtonType type = SaveDrawing.showAskModificationsDialog(lang);

			if(type == ButtonType.NO) {
				load();
				done();
				return;
			}
			if(type == ButtonType.YES && saveAndLoad()) {
				done();
			}
		}else {
			load();
			done();
		}
	}

	private boolean saveAndLoad() {
		return SaveDrawing.showDialog(fileChooser, true, file, currentFolder, ui, mainstage).map(f -> {
			final Task<Boolean> saveTask = openSaveManager.save(f.getPath(), progressBar, statusWidget);
			try {
				if(saveTask.get()) {
					ui.setModified(false);
					load();
					return Boolean.TRUE;
				}
			}catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}catch(ExecutionException ex) {
				// ignored.
			}
			return Boolean.FALSE;
		}).orElse(Boolean.FALSE);
	}

	@Override
	public void flush() {
		fileChooser = null;
		super.flush();
	}

	@Override
	public boolean canDo() {
		return ui != null && openSaveManager != null && fileChooser != null;
	}


	private void load() {
		if(file == null) {
			fileChooser.setInitialDirectory(currentFolder);
			file = fileChooser.showOpenDialog(mainstage);
		}else {
			fileChooser.setInitialDirectory(file.getParentFile());
			fileChooser.setInitialFileName(file.getName());
		}

		if(file != null && file.canRead()) {
			super.doCmdBody();
		}else {
			ok = false;
		}
	}
}
