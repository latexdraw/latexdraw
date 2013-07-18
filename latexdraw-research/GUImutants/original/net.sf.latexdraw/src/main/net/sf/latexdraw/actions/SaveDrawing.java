package net.sf.latexdraw.actions;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.sf.latexdraw.filters.SVGFilter;
import net.sf.latexdraw.instruments.PreferencesSetter;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.LFrame;

import org.malai.swing.action.library.Save;
import org.malai.swing.ui.UI;

/**
 * This action saves the given drawing into an SVG document.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * @author Arnaud Blouin
 * @date 06/09/2011
 * @since 3.0
 */
public class SaveDrawing extends Save<LFrame, JLabel> {
	/** The file chooser that will be used to select the location to save. */
	protected JFileChooser fileChooser;

	/** True: A dialog bow will be always shown to ask the location to save. */
	protected boolean saveAs;

	/** True: the app will be closed after the drawing saved. */
	protected boolean saveOnClose;

	/** The instrument that manages the preferences. */
	protected PreferencesSetter prefSetter;


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
		return ui!=null && openSaveManager!=null && fileChooser!=null;
	}


	@Override
	protected void doActionBody() {
		if(saveOnClose)
			if(ui.isModified()) {
				saveAs = true;
				switch(showAskModificationsDialog(ui)) {
					case JOptionPane.NO_OPTION: // exit
						quit();
						break;
					case JOptionPane.YES_OPTION: // save + exit
						final File f = showDialog(fileChooser, saveAs, ui, file);
						if(f!=null) {
							file = f;
							super.doActionBody();
							quit();
						}
						break;
					case JOptionPane.CANCEL_OPTION:
						ok = false;
						break;
					default:
						break;
				}
			}
			else quit();
		else {
			if(file==null)
				file = showDialog(fileChooser, saveAs, ui, file);
			if(file==null)
				ok = false;
			else
				super.doActionBody();
		}
		done();
	}


	private void quit() {
		// Saving the preferences.
		if(prefSetter!=null)
			prefSetter.writeXMLPreferences();
		System.exit(1);
	}


	@Override
	public void flush() {
		super.flush();
		prefSetter	= null;
		fileChooser = null;
	}


	/**
	 * @return -1: cancel, 0: yes, 1: no
	 * @since 3.0
	 */
	protected static int showAskModificationsDialog(final UI ui) {
		return JOptionPane.showConfirmDialog(ui, LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.156"), //$NON-NLS-1$
					LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.110"), JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$
	}


	/**
	 * Show the export dialog to select a path.
	 * @since 3.0
	 */
	protected static File showDialog(final JFileChooser fileChooser, final boolean saveAs, final UI ui, final File file) {
		File f;

		if(saveAs || file==null && ui.isModified())
			f = fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : null;
		else
			f = file;

		if(f==null)
			return null;

		if(f.getPath().toLowerCase().indexOf(SVGFilter.SVG_EXTENSION.toLowerCase()) ==-1)
			f = new File(f.getPath() + SVGFilter.SVG_EXTENSION);

		if(f.exists()) {
			final int replace = JOptionPane.showConfirmDialog(null, LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.173"), //$NON-NLS-1$
														LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.188"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$

			if(replace == JOptionPane.NO_OPTION || replace == JOptionPane.CLOSED_OPTION)
				return null;
		}

		return f;
	}


	/**
	 * @param prefSetter The instrument that manages the preferences.
	 * @since 3.0
	 */
	public void setPrefSetter(final PreferencesSetter prefSetter) {
		this.prefSetter = prefSetter;
	}


	/**
	 * @param fileChooser The file chooser that will be used to select the location to save.
	 * @since 3.0
	 */
	public void setFileChooser(final JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}

	/**
	 * @param saveAs True: A dialog bow will be always shown to ask the location to save.
	 * @since 3.0
	 */
	public void setSaveAs(final boolean saveAs) {
		this.saveAs = saveAs;
	}

	/**
	 * @param saveOnClose True: the app will be closed after the drawing saved.
	 * @since 3.0
	 */
	public void setSaveOnClose(final boolean saveOnClose) {
		this.saveOnClose = saveOnClose;
	}
}
