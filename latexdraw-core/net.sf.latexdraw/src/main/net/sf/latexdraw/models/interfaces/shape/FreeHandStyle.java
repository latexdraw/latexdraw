/*
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2017 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 */
package net.sf.latexdraw.models.interfaces.shape;

import java.util.Arrays;

/** The different types of freehand. */
public enum FreeHandStyle {
	CURVES, LINES;

	/**
	 * @param type The type to check.
	 * @return The corresponding type. Returns CURVES by default.
	 * @since 3.0
	 */
	public static FreeHandStyle getType(final String type) {
		return Arrays.stream(values()).filter(style -> style.toString().equals(type)).findFirst().orElse(CURVES);
	}
}
