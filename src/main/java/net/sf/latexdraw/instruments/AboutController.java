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
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.util.VersionChecker;

/**
 * The controller of the "About Latexdraw" dialogue box.
 * @author Arnaud BLOUIN
 */
public class AboutController implements Initializable {
	@FXML private Label aboutText;
	@FXML private TextArea noteText;
	@FXML private TextArea contribText;
	@FXML private TextArea sysText;
	@FXML private TextArea licenseText;

	@Inject private SystemService system;
	@Inject private LangService lang;

	/**
	 * Creates the controller.
	 */
	public AboutController() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		aboutText.setText(lang.getBundle().getString("LaTeXDrawFrame.219") + ' ' +  //NON-NLS
			VersionChecker.VERSION + VersionChecker.VERSION_STABILITY + ", build " + VersionChecker.ID_BUILD + SystemService.EOL +  //NON-NLS
			LaTeXDraw.LABEL_APP + lang.getBundle().getString("LaTeXDrawFrame.221") + SystemService.EOL + //NON-NLS
			"Copyright(c) 2005-2018 - Arnaud BLOUIN" + SystemService.EOL + //NON-NLS
			"http://latexdraw.sourceforge.net/"); //NON-NLS
		noteText.setText(system.readTextFile("/res/release_note.txt")); //NON-NLS
		contribText.setText(system.readTextFile("/res/contributors.txt")); //NON-NLS
		licenseText.setText(system.readTextFile("/res/license.txt")); //NON-NLS

		final StringBuilder builder = new StringBuilder();
		builder.append("LaTeX version:").append(system.getLaTeXVersion()).append(SystemService.EOL); //NON-NLS
		builder.append("DviPS version:").append(system.getDVIPSVersion()).append(SystemService.EOL); //NON-NLS
		builder.append("PS2PDF version:").append(SystemService.EOL).append(system.getPS2PDFVersion()).append(SystemService.EOL); //NON-NLS
		builder.append("PS2EPSI version:").append(system.getPS2EPSVersion()).append(SystemService.EOL); //NON-NLS
		builder.append("PDFcrop version:").append(system.getPDFCROPVersion()).append(SystemService.EOL); //NON-NLS
		builder.append("Java properties:").append(SystemService.EOL); //NON-NLS
		System.getProperties().forEach((key, value) -> builder.append(key).append(':').append(' ').append(value).append(SystemService.EOL));
		sysText.setText(builder.toString());
	}
}
