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

import java.io.File;
import javax.swing.JFileChooser;
import net.sf.latexdraw.instruments.PreferencesSetter;
import org.malai.action.ActionImpl;

/**
 * This action saves the given drawing into an SVG document.
 */
public class SaveDrawing extends ActionImpl { //  Save<LFrame, JLabel>
	/** The file chooser that will be used to select the location to save. */
	protected JFileChooser fileChooser;

	/** True: A dialog bow will be always shown to ask the location to save. */
	protected boolean saveAs;

	/** True: the app will be closed after the drawing saved. */
	protected boolean saveOnClose;

	/** The instrument that manages the preferences. */
	protected PreferencesSetter prefSetter;

	File currentFolder;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public SaveDrawing() {
		super();
		saveAs 		= false;
		saveOnClose = false;
	}


	@Override
	public boolean canDo() {
//		return ui!=null && openSaveManager!=null && fileChooser!=null;
		return false;
	}


	@Override
	protected void doActionBody() {
//		if(saveOnClose)
//			if(ui.isModified()) {
//				saveAs = true;
//				switch(showAskModificationsDialog(ui)) {
//					case JOptionPane.NO_OPTION: // exit
//						quit();
//						break;
//					case JOptionPane.YES_OPTION: // save + exit
//						final File f = showDialog(fileChooser, saveAs, ui, file, currentFolder);
//						if(f!=null) {
//							file = f;
//							super.doActionBody();
//							quit();
//						}
//						break;
//					case JOptionPane.CANCEL_OPTION:
//						ok = false;
//						break;
//					default:
//						break;
//				}
//			}
//			else quit();
//		else {
//			if(file==null)
//				file = showDialog(fileChooser, saveAs, ui, null, currentFolder);
//			if(file==null)
//				ok = false;
//			else
//				super.doActionBody();
//		}
		done();
	}


//	private void quit() {
//		// Saving the preferences.
//		if(prefSetter!=null)
//			prefSetter.writeXMLPreferences();
//		System.exit(1);
//	}


	@Override
	public void flush() {
		super.flush();
		prefSetter	= null;
		fileChooser = null;
	}


//	/**
//	 * @return -1: cancel, 0: yes, 1: no
//	 * @since 3.0
//	 */
//	protected static int showAskModificationsDialog(final SwingUI ui) {
//		return JOptionPane.showConfirmDialog(ui, LangTool.INSTANCE.getBundle().getString("Actions.2"), //$NON-NLS-1$
//				LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.188"), JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$
//	}


//	/**
//	 * Show the export dialog to select a path.
//	 * @since 3.0
//	 */
//	protected static File showDialog(final JFileChooser fileChooser, final boolean saveAs, final SwingUI ui, final File file,
// 									final File currentFolder) {
//		File f;
//
//		if(saveAs || file==null && ui.isModified()) {
//			fileChooser.setCurrentDirectory(currentFolder);
//			f = fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : null;
//		}
//		else
//			f = file;
//
//		if(f==null)
//			return null;
//
//		if(!f.getPath().toLowerCase().endsWith(SVGFilter.SVG_EXTENSION))
//			f = new File(f.getPath() + SVGFilter.SVG_EXTENSION);
//
//		if(f.exists()) {
//			final int replace = JOptionPane.showConfirmDialog(null, LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.173"), //$NON-NLS-1$
//														LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.188"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$
//
//			if(replace == JOptionPane.NO_OPTION || replace == JOptionPane.CLOSED_OPTION)
//				return null;
//		}
//
//		return f;
//	}


	/**
	 * @param setter The instrument that manages the preferences.
	 * @since 3.0
	 */
	public void setPrefSetter(final PreferencesSetter setter) {
		prefSetter = setter;
	}


	/**
	 * @param chooser The file chooser that will be used to select the location to save.
	 * @since 3.0
	 */
	public void setFileChooser(final JFileChooser chooser) {
		fileChooser = chooser;
	}

	/**
	 * @param pref True: A dialog bow will be always shown to ask the location to save.
	 * @since 3.0
	 */
	public void setSaveAs(final boolean pref) {
		saveAs = pref;
	}

	/**
	 * @param saveOn True: the app will be closed after the drawing saved.
	 * @since 3.0
	 */
	public void setSaveOnClose(final boolean saveOn) {
		saveOnClose = saveOn;
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
