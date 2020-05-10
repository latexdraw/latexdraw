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
package net.sf.latexdraw.instrument;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.util.VersionChecker;
import org.jetbrains.annotations.NotNull;

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
	private final @NotNull ResourceBundle lang;

	@Inject
	public AboutController(final ResourceBundle lang) {
		super();
		this.lang = Objects.requireNonNull(lang);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		aboutText.setText(lang.getString("version") + ' ' +  //NON-NLS
			VersionChecker.VERSION + VersionChecker.VERSION_STABILITY + SystemUtils.getInstance().eol +  //NON-NLS
			LaTeXDraw.LABEL_APP + lang.getString("licenceDistrib") + SystemUtils.getInstance().eol + //NON-NLS
			"Copyright(c) 2005-2020 - Arnaud BLOUIN" + SystemUtils.getInstance().eol + //NON-NLS
			"https://latexdraw.sourceforge.io"); //NON-NLS
		noteText.setText(SystemUtils.getInstance().readTextFile("/res/release_note.txt")); //NON-NLS
		contribText.setText(SystemUtils.getInstance().readTextFile("/res/contributors.txt")); //NON-NLS
		licenseText.setText(SystemUtils.getInstance().readTextFile("/res/license.txt")); //NON-NLS

		final StringBuilder builder = new StringBuilder();
		builder.append("LaTeX version:") //NON-NLS
			.append(SystemUtils.getInstance().getLaTeXVersion())
			.append(SystemUtils.getInstance().eol)
			.append("DviPS version:") //NON-NLS
			.append(SystemUtils.getInstance().getDVIPSVersion())
			.append(SystemUtils.getInstance().eol)
			.append("PS2PDF version:") //NON-NLS
			.append(SystemUtils.getInstance().eol)
			.append(SystemUtils.getInstance().getPS2PDFVersion())
			.append(SystemUtils.getInstance().eol)
			.append("GhostScript version:") //NON-NLS
			.append(SystemUtils.getInstance().eol)
			.append(SystemUtils.getInstance().getGSVersion())
			.append(SystemUtils.getInstance().eol)
			.append("pdftoppm version:") //NON-NLS
			.append(SystemUtils.getInstance().eol)
			.append(SystemUtils.getInstance().getPDFtoPPMVersion())
			.append(SystemUtils.getInstance().eol)
			.append("PS2EPSI version:") //NON-NLS
			.append(SystemUtils.getInstance().eol)
			.append(SystemUtils.getInstance().getPS2EPSVersion())
			.append(SystemUtils.getInstance().eol)
			.append("PATH:") //NON-NLS
			.append(SystemUtils.getInstance().eol)
			.append(System.getenv("PATH")) //NON-NLS
			.append(SystemUtils.getInstance().eol)
			.append(SystemUtils.getInstance().eol)
			.append("Java properties:") //NON-NLS
			.append(SystemUtils.getInstance().eol);

		System.getProperties()
			.entrySet()
			.stream()
			// ignoring user information
			.filter(entry -> !entry.getKey().toString().contains("user")) //NON-NLS
			.forEach(entry -> builder.append(entry.getKey()).append(':').append(' ').append(entry.getValue()).append(SystemUtils.getInstance().eol));
		sysText.setText(builder.toString());
	}
}
