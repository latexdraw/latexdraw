package net.sf.latexdraw.glib.ui;

/**
 * Defines the modes of edition of the canvas.<br>
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
 * 07/22/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public enum EditionMode
{
	/** The normal mode. */
	NORMAL,
	/** The rotation mode. */
	ROTATION;


	/**
	 * @param mode The mode to test.
	 * @return True if the current mode is the rotation mode.
	 * @since 3.0
	 */
	public static boolean isRotationMode(final EditionMode mode)
	{
		return mode!=null && mode.equals(ROTATION);
	}
}
