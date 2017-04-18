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
package net.sf.latexdraw.actions;

import java.io.File;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import net.sf.latexdraw.LaTeXDraw;
import org.malai.javafx.action.library.Load;

/**
 * This action loads an SVG document into the app.
 */
public class LoadDrawing extends Load<Label> implements Modifying {
	/** The file chooser that will be used to select the location to save. */
	private FileChooser fileChooser;
	private File currentFolder;


	@Override
	protected void doActionBody() {
		if(ui.isModified()) {
			final ButtonType type = SaveDrawing.showAskModificationsDialog();

			if(type == ButtonType.NO) {
				load();
			}else {
				if(type == ButtonType.YES) {
					SaveDrawing.showDialog(fileChooser, true, file, currentFolder, ui).ifPresent(f -> {
						openSaveManager.save(f.getPath(), progressBar, statusWidget);
						ui.setModified(false);
						load();
					});
				}
			}
		}else {
			load();
		}
		done();
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


	protected void load() {
		if(file == null) {
			fileChooser.setInitialDirectory(currentFolder);
			file = fileChooser.showOpenDialog(LaTeXDraw.getINSTANCE().getMainStage());
		}else {
			fileChooser.setInitialDirectory(file.getParentFile());
			fileChooser.setInitialFileName(file.getName());
		}

		if(file != null && file.canRead()) {
			super.doActionBody();
		}else {
			ok = false;
		}
	}

	/**
	 * @param chooser The file chooser that will be used to select the location to save.
	 */
	public void setFileChooser(final FileChooser chooser) {
		this.fileChooser = chooser;
	}

	public void setCurrentFolder(final File currFolder) {
		currentFolder = currFolder;
	}
}
