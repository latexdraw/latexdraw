package net.sf.latexdraw.actions;

import java.io.IOException;

import javax.swing.JFileChooser;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IPicture;

/**
 * This action asks the user to select a picture and, if valid, adds it to a drawing.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 20/01/2012<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class InsertPicture extends AddShape {
	/** The file chooser used to select the picture to add. */
	protected JFileChooser fileChooser;

	/** Defines if the picture has been successfully loaded. */
	protected boolean loaded;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public InsertPicture() {
		super();
		loaded = false;
	}


	@Override
	protected void doActionBody() {
		final int exitVal = fileChooser.showOpenDialog(null);

		// Asks the user for the picture to load.
		if(exitVal==JFileChooser.APPROVE_OPTION) {
			try{
				((IPicture)shape).setPathSource(fileChooser.getSelectedFile().getAbsolutePath());
				loaded = true;
			}catch(IOException exception){
				BadaboomCollector.INSTANCE.add(exception);
			}
		}

		if(loaded)
			redo();
	}


	@Override
	public void redo() {
		drawing.addShape(shape);
		drawing.setModified(true);
	}


	@Override
	public boolean hadEffect() {
		return super.hadEffect() && loaded;
	}


	@Override
	public boolean canDo() {
		return super.canDo() && fileChooser!=null && shape instanceof IPicture;
	}


	/**
	 * @param fileChooser The file chooser used to select the picture to load.
	 * @since 3.0
	 */
	public void setFileChooser(final JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}
}
