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
package net.sf.latexdraw.command;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import net.sf.latexdraw.util.SystemUtils;
import org.jetbrains.annotations.NotNull;
import org.malai.command.CommandImpl;

/**
 * This command checks whether ImageMagick (its 'convert' binary) is installed.
 * @author Arnaud Blouin
 */
public class CheckConvertExists extends CommandImpl {
	private final @NotNull Label statusLabel;
	private final @NotNull Hyperlink link;

	public CheckConvertExists(final @NotNull Label statusLabel, final @NotNull Hyperlink link) {
		super();
		this.statusLabel = statusLabel;
		this.link = link;
	}

	@Override
	protected void doCmdBody() {
		if(!SystemUtils.getInstance().execute(new String[] {"convert", "-version"}, null).a) { //NON-NLS
			statusLabel.setText("ImageMagick is not installed but is required for converting images. See: ");
			link.setVisible(true);
			link.setText("https://github.com/arnobl/latexdraw/wiki/Manual#inserting--converting-pictures"); //NON-NLS
		}
	}

	@Override
	public @NotNull RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.NONE;
	}
}
