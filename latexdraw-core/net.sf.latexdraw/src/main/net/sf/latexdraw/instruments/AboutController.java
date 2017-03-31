/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import net.sf.latexdraw.util.LFileUtils;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.util.VersionChecker;

/**
 * The controller of the "About Latexdraw" dialogue box.
 * @author Arnaud BLOUIN
 */
public class AboutController implements Initializable {
	@FXML private WebView aboutText;
	@FXML private TextArea noteText;
	@FXML private TextArea contribText;
	@FXML private TextArea sysText;
	@FXML private TextArea licenseText;

	/**
	 * Creates the controller.
	 */
	AboutController() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		aboutText.getEngine().loadContent("<html><body><div style=\"text-align: center; \"><font size=\"-1\"><br>" + //$NON-NLS-1$
			LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.219") + " " + //$NON-NLS-1$ //$NON-NLS-2$
			VersionChecker.VERSION + VersionChecker.VERSION_STABILITY + //$NON-NLS-1$ //$NON-NLS-2$
			LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.220") + " " + VersionChecker.ID_BUILD + "<br><br>" + //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			LResources.LABEL_APP + LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.221") + "<br>" + //$NON-NLS-1$//$NON-NLS-2$
			"Copyright(c) 2005-2017 - Arnaud BLOUIN - arno.b.dev@gmail.com<br><br>" + //$NON-NLS-1$
			"http://latexdraw.sourceforge.net/<br></div></body></html>");//$NON-NLS-1$
		noteText.setText(LFileUtils.INSTANCE.readTextFile("/res/release_note.txt"));//$NON-NLS-1$
		contribText.setText(LFileUtils.INSTANCE.readTextFile("/res/contributors.txt"));//$NON-NLS-1$
		licenseText.setText(LFileUtils.INSTANCE.readTextFile("/res/license.txt"));//$NON-NLS-1$

		final StringBuilder builder = new StringBuilder();
		builder.append("LaTeX version:").append(LSystem.INSTANCE.getLaTeXVersion()).append(LResources.EOL); //$NON-NLS-1$
		builder.append("DviPS version:").append(LSystem.INSTANCE.getDVIPSVersion()).append(LResources.EOL); //$NON-NLS-1$
		builder.append("PS2PDF version:").append(LResources.EOL).append(LSystem.INSTANCE.getPS2PDFVersion()).append(LResources.EOL); //$NON-NLS-1$
		builder.append("PS2EPSI version:").append(LSystem.INSTANCE.getPS2EPSVersion()).append(LResources.EOL); //$NON-NLS-1$
		builder.append("PDFcrop version:").append(LSystem.INSTANCE.getPDFCROPVersion()).append(LResources.EOL); //$NON-NLS-1$
		builder.append("Java properties:").append(LResources.EOL); //$NON-NLS-1$
		System.getProperties().entrySet().forEach(entry -> builder.append(entry.getKey()).append(':').append(' ').append(entry.getValue()).append(LResources.EOL));
		sysText.setText(builder.toString());
	}
}
