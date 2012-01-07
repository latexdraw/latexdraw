package org.malai.widget;

import java.awt.Color;

import javax.swing.Icon;

/**
 * This widgets is based on a MButton. A colour button displays a colour into its icon.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 06/05/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MColorButton extends MButton {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link MButton#MButton()}
	 */
	public MColorButton() {
		super();
	}


	/**
	 * {@link MButton#MButton(javax.swing.Action)}
	 */
	public MColorButton(final javax.swing.Action action) {
		super(action);
	}


	/**
	 * {@link MButton#MButton(Icon)}
	 */
	public MColorButton(final Icon arg0) {
		super(arg0);
	}

	/**
	 * {@link MButton#MButton(String, Icon)}
	 */
	public MColorButton(final String arg0, final Icon arg1) {
		super(arg0, arg1);
	}

	/**
	 * {@link MButton#MButton(String)}
	 */
	public MColorButton(final String arg0) {
		super(arg0);
	}


	/**
	 * Set a new colour to the button.
	 * @param c The new colour.
	 */
	public void setColor(final Color c) {
		final Icon icon = getIcon();

		if(c!=null && icon instanceof MButtonIcon) {
			((MButtonIcon)icon).setColor(c);
			repaint();
		}
	}



	/**
	 * @return The colour of the icon of the button.
	 */
	public Color getColor() {
		final Icon icon = getIcon();

		return icon!=null && icon instanceof MButtonIcon ? ((MButtonIcon)icon).getColor() : null;
	}
}
