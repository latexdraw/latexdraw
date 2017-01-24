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
package net.sf.latexdraw.actions.shape;

import java.io.File;
import java.util.Optional;
import javafx.stage.FileChooser;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.IPicture;

/**
 * This action asks the user to select a picture and, if valid, adds it to a drawing.
 */
public class InsertPicture extends AddShape {
	/** The file chooser used to select the picture to add. */
	Optional<FileChooser> fileChooser;

	/** Defines if the picture has been successfully loaded. */
	boolean loaded;

	public InsertPicture() {
		super();
		loaded = false;
	}

	@Override
	protected void doActionBody() {
		// Asks the user for the picture to load.
		fileChooser.ifPresent(ch -> shape.ifPresent(sh -> {
			final File file = ch.showOpenDialog(null);

			if(file!=null) {
				try {
					((IPicture) sh).setPathSource(file.getAbsolutePath());
					loaded = true;
				}catch(final Throwable ex) {
					BadaboomCollector.INSTANCE.add(ex);
				}
			}
		}));

		if(loaded) redo();
	}

	@Override
	public void redo() {
		drawing.ifPresent(dr -> shape.ifPresent(sh -> {
			dr.addShape(sh);
			dr.setModified(true);
		}));
	}

	@Override
	public boolean hadEffect() {
		return super.hadEffect() && loaded;
	}

	@Override
	public boolean canDo() {
		return super.canDo() && fileChooser.isPresent() && shape.get() instanceof IPicture;
	}

	/**
	 * @param chooser The file chooser used to select the picture to load.
	 * @since 3.0
	 */
	public void setFileChooser(final FileChooser chooser) {
		fileChooser = Optional.ofNullable(chooser);
	}
}
