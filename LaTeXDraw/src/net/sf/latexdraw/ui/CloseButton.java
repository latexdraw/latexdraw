package net.sf.latexdraw.ui;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import org.malai.widget.MButton;

import net.sf.latexdraw.lang.LangTool;

/**
 * This class defines a button which allows the user to close a window<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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

	/** The image of the button. */
	protected final static ImageIcon CLOSE_OUT = new ImageIcon(Class.class.getClass().getResource("/res/closeOut.png"));//$NON-NLS-1$

	/** The image of the button when the mouse is on. */
	protected final static ImageIcon CLOSE_IN = new ImageIcon(Class.class.getClass().getResource("/res/closeIn.png"));//$NON-NLS-1$



	/**
	 * The constructor by default.
	 */
	public CloseButton() {
		super();

		setSize(ICON_WIDTH, ICON_HEIGHT);
		setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
		setMinimumSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
		setMaximumSize(new Dimension(ICON_WIDTH, ICON_HEIGHT));
		setIcon(CLOSE_OUT);
		setRolloverIcon(CLOSE_IN);
		setToolTipText(LangTool.INSTANCE.getStringOthers("CloseButton.closePanel")); //$NON-NLS-1$
	}
}
