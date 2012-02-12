package org.malai.action.library;

import java.io.File;

import org.malai.action.Action;
import org.malai.ui.ISOpenSaver;
import org.malai.ui.UI;
import org.malai.widget.MProgressBar;

/**
 * This abstract action defines an model for loading and saving actions.
 * <br>
 * This file is part of Malai<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 *  Malai is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 * <br>
 *  Malai is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * @author Arnaud Blouin
 * @since 0.2
 */
public abstract class IOAction extends Action {
	/** The current file loaded or saved. */
	protected File file;

	/** The user interface that contains abstract presentations and instruments. */
	protected UI ui;

	/** Define if the drawing has been successfully loaded or saved. */
	protected boolean ok;

	/** The object that saves and loads files for the IS. */
	protected ISOpenSaver openSaveManager;

	/** The progress bar used to show the progress of the saving. */
	protected MProgressBar progressBar;

	/** The widget that displays the status of the I/O operation. */
	protected Object statusWidget;


	/**
	 * Creates a save action.
	 * @since 0.2
	 */
	public IOAction() {
		super();
		ok = false;
	}


	@Override
	public void flush() {
		super.flush();
		file			= null;
		ui				= null;
		openSaveManager	= null;
		progressBar		= null;
		statusWidget	= null;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	public boolean canDo() {
		return file!=null && ui!=null && openSaveManager!=null;
	}


	@Override
	public boolean hadEffect() {
		return super.hadEffect() && ok;
	}


	/**
	 * @param file the file to set.
	 * @since 0.2
	 */
	public void setFile(final File file) {
		this.file = file;
	}


	/**
	 * @param progressBar The progress bar used to show the progress of the saving.
	 * @since 3.0
	 */
	public void setProgressBar(final MProgressBar progressBar) {
		this.progressBar = progressBar;
	}


	/**
	 * @param ui the ui to set.
	 * @since 0.2
	 */
	public void setUi(final UI ui) {
		this.ui = ui;
	}


	/**
	 * @param openSaveManager the openSaveManager to set.
	 * @since 0.2
	 */
	public void setOpenSaveManager(final ISOpenSaver openSaveManager) {
		this.openSaveManager = openSaveManager;
	}


	/**
	 * @param statusWidget the widget that will be used to display I/O messages.
	 * @since 0.2
	 */
	public void setStatusWidget(final Object statusWidget) {
		this.statusWidget = statusWidget;
	}
}
