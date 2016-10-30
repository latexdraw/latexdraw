/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2014 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.actions;

import org.malai.action.ActionImpl;

import javax.swing.JFileChooser;
import java.io.File;

/**
 * This action loads an SVG document into the app.
 */
public class LoadDrawing extends ActionImpl implements Modifying { //  extends Load<LFrame, JLabel>
	/** The file chooser that will be used to select the location to save. */
	protected JFileChooser fileChooser;
	File currentFolder;

	@Override
	protected void doActionBody() {
//		if(ui.isModified())
//			switch(SaveDrawing.showAskModificationsDialog(ui)) {
//				case JOptionPane.NO_OPTION: //load
//					load();
//					break;
//				case JOptionPane.YES_OPTION: // save + load
//					final File f = SaveDrawing.showDialog(fileChooser, true, ui, file, currentFolder);
//					if(f!=null) {
//						openSaveManager.save(f.getPath(), ui, progressBar, statusWidget);
//						ui.setModified(false);
//						load();
//					}
//					break;
//				case JOptionPane.CANCEL_OPTION: // nothing
//					break;
//				default:
//					break;
//			}
//		else load();
//		done();
	}


	@Override
	public void flush() {
		super.flush();
		fileChooser = null;
	}

	@Override
	public boolean canDo() {
//		return ui!=null && openSaveManager!=null && fileChooser!=null;
		return false;
	}


	protected void load() {
//		if(file==null) {
//			fileChooser.setCurrentDirectory(currentFolder);
//			file = fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : null;
//		}
//		else
//			fileChooser.setSelectedFile(file);
//
//		if(file!=null && file.canRead())
//			super.doActionBody();
//		else
//			ok = false;
	}


	/**
	 * @param chooser The file chooser that will be used to select the location to save.
	 * @since 3.0
	 */
	public void setFileChooser(final JFileChooser chooser) {
		this.fileChooser = chooser;
	}

	public void setCurrentFolder(final File currFolder) {
		currentFolder = currFolder;
	}

	//FIXME to remove
	@Override
	public boolean isRegisterable() {
		return false;
	}
}

