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
package net.sf.latexdraw.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.application.Platform;
import net.sf.latexdraw.instruments.StatusBarController;

/**
 * To check whether a new version of LaTeXDraw is out.
 * @author Arnaud BLOUIN
 * @since 1.8
 */
public final class VersionChecker implements Runnable {
	/** The version of the application */
	public static final String VERSION = "4.0.0"; //$NON-NLS-1$

	/** The stability of the build. */
	public static final String VERSION_STABILITY = "-snapshot"; //$NON-NLS-1$

	/** The identifier of the build */
	public static final String ID_BUILD = "20170419"; //$NON-NLS-1$

	/** To change if update is needed or not. */
	public static final boolean WITH_UPDATE = true;

	private final StatusBarController statusBar;


	/**
	 * Creates the version checker.
	 */
	public VersionChecker(final StatusBarController statusBarCtrl) {
		super();
		statusBar = statusBarCtrl;
	}


	@Override
	public void run() {
		try(final BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(
				new URL("http://latexdraw.sourceforge.net/news.txt").openStream())))) {
			final String line = br.readLine();
			final String[] div = line == null ? null : line.split("_"); //$NON-NLS-1$

			if(div != null && div.length > 3 && div[3].compareTo(VERSION) > 0) {
				Platform.runLater(() -> {
					statusBar.getLabel().setVisible(true);
					statusBar.getLabel().setText(LangTool.INSTANCE.getBundle().getString("Version.1") + ' ' + div[3] + ". See the release note:");
					statusBar.getLink().setVisible(true);
					statusBar.getLink().setText("http://latexdraw.sourceforge.net/");
				});
			}
		}catch(final IOException ex) {
			/* Nothing to do. */
		}
	}
}
