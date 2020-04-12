/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
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
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import javafx.application.Platform;
import net.sf.latexdraw.instrument.StatusBarController;

/**
 * To check whether a new version of LaTeXDraw is out.
 * @author Arnaud BLOUIN
 */
public final class VersionChecker implements Runnable {
	/** The version of the application */
	public static final String VERSION = "4.0.1"; //NON-NLS

	/** The stability of the build. */
	public static final String VERSION_STABILITY = ""; //NON-NLS

	/** To change if update is needed or not. */
	public static final boolean WITH_UPDATE = true;

	private final StatusBarController statusBar;
	private final ResourceBundle lang;


	/**
	 * Creates the version checker.
	 */
	public VersionChecker(final StatusBarController statusBarCtrl, final ResourceBundle lang) {
		super();
		statusBar = statusBarCtrl;
		this.lang = lang;
	}


	@Override
	public void run() {
		try(final BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(
				new URL("https://latexdraw.sourceforge.io/news.txt").openStream()), StandardCharsets.UTF_8))) { //NON-NLS
			final String line = br.readLine();
			final String[] div = line == null ? null : line.split("_"); //NON-NLS

			if(div != null && div.length > 3 && VERSION.compareTo(div[3]) < 0) {
				Platform.runLater(() -> {
					statusBar.getLabel().setVisible(true);
					statusBar.getLabel().setText(lang.getString("newVersion") + ' ' + div[3] + ". See the release note:"); //NON-NLS
					statusBar.getLink().setVisible(true);
					statusBar.getLink().setText("https://latexdraw.sourceforge.io"); //NON-NLS
				});
			}
		}catch(final IOException ignored) {
			/* Nothing to do. */
		}
	}
}
