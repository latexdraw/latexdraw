package net.sf.latexdraw.ui;

import java.awt.Dimension;

import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.swing.widget.MButton;

/**
 * This class defines a button which allows the user to close a window<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 05/21/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class CloseButton extends MButton {
	private static final long serialVersionUID = 1L;

	/** The width of the button */
	public static final int ICON_WIDTH = 14;

	/** The height of the button */
	public static final int ICON_HEIGHT = 14;

	/**
	 * The constructor by default.
	 */
	public CloseButton() {
		super();

		final Dimension dim = new Dimension(ICON_WIDTH, ICON_HEIGHT);
		setSize(ICON_WIDTH, ICON_HEIGHT);
		setPreferredSize(dim);
		setMinimumSize(dim);
		setMaximumSize(dim);
		setIcon(LResources.BUTTON_CLOSE_OUT_ICON);
		setRolloverIcon(LResources.BUTTON_CLOSE_IN_ICON);
		setToolTipText(LangTool.INSTANCE.getStringOthers("CloseButton.closePanel")); //$NON-NLS-1$
	}
}
