package net.sf.latexdraw.glib.models.interfaces.prop;

import java.util.Arrays;

/**
 * Defines properties of freehand shapes.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 */
public interface IFreeHandProp {
	/** The different types of freehand. */
	enum FreeHandType {
		CURVES, LINES;

		/**
		 * @param type The type to check.
		 * @return The corresponding type. Returns CURVES by default.
		 * @since 3.0
		 */
		public static FreeHandType getType(final String type) {
			return Arrays.asList(values()).stream().filter(style -> style.toString().equals(type)).findFirst().orElse(CURVES);
		}
	}

	/**
	 * @return the type.
	 * @since 3.0
	 */
	FreeHandType getType();

	/**
	 * @param type the type to set.
	 * @since 3.0
	 */
	void setType(final FreeHandType type);

	/**
	 * @return the open.
	 * @since 3.0
	 */
	boolean isOpen();

	/**
	 * @param open the open to set.
	 * @since 3.0
	 */
	void setOpen(final boolean open);

	/**
	 * @return the interval.
	 * @since 3.0
	 */
	int getInterval();

	/**
	 * @param interval the interval to set.
	 * @since 3.0
	 */
	void setInterval(final int interval);
}
