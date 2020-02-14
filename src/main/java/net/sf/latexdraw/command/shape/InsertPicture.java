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
package net.sf.latexdraw.command.shape;

import java.io.File;
import javafx.stage.FileChooser;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Picture;
import org.jetbrains.annotations.NotNull;

/**
 * This command asks the user to select a picture and, if valid, adds it to a drawing.
 * @author Arnaud Blouin
 */
public class InsertPicture extends AddShape {
	/** The file chooser used to select the picture to add. */
	private final @NotNull FileChooser fileChooser;

	/** Defines if the picture has been successfully loaded. */
	private boolean loaded;

	/**
	 * Creates the command.
	 * @param sh The picture to add.
	 * @param dr The drawing that will be handled by the command.
	 * @param chooser The file chooser used to select the picture to load.
	 */
	public InsertPicture(final @NotNull Picture sh, final @NotNull Drawing dr, final @NotNull FileChooser chooser) {
		super(sh, dr);
		fileChooser = chooser;
	}

	@Override
	protected void doCmdBody() {
		// Asks the user for the picture to load.
		final File file = fileChooser.showOpenDialog(null);

		if(file != null && file.canRead()) {
			((Picture) shape).setPathSource(file.getAbsolutePath());
			loaded = true;
		}

		if(loaded) {
			redo();
		}
	}

	@Override
	public boolean hadEffect() {
		return loaded && super.hadEffect();
	}

	@Override
	public boolean canDo() {
		return shape instanceof Picture;
	}
}
