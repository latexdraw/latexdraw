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
package net.sf.latexdraw.commands.shape;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.stage.FileChooser;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * This command asks the user to select a picture and, if valid, adds it to a drawing.
 * @author Arnaud Blouin
 */
public class InsertPicture extends AddShape {
	/** The file chooser used to select the picture to add. */
	private final Optional<FileChooser> fileChooser;

	/** Defines if the picture has been successfully loaded. */
	private boolean loaded;

	/**
	 * Creates the command.
	 * @param sh The picture to add.
	 * @param dr The drawing that will be handled by the command.
	 * @param chooser The file chooser used to select the picture to load.
	 */
	public InsertPicture(final IShape sh, final IDrawing dr, final FileChooser chooser) {
		super(sh, dr);
		fileChooser = Optional.ofNullable(chooser);
	}

	@Override
	protected void doCmdBody() {
		// Asks the user for the picture to load.
		fileChooser.ifPresent(ch -> shape.ifPresent(sh -> {
			final File file = ch.showOpenDialog(null);

			if(file != null && file.canRead()) {
				try {
					((IPicture) sh).setPathSource(file.getAbsolutePath());
					loaded = true;
				}catch(final IOException ex) {
					BadaboomCollector.INSTANCE.add(ex);
				}
			}
		}));

		if(loaded) {
			redo();
		}
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
		return loaded && super.hadEffect();
	}

	@Override
	public boolean canDo() {
		return super.canDo() && fileChooser.isPresent() && shape.orElse(null) instanceof IPicture;
	}
}
