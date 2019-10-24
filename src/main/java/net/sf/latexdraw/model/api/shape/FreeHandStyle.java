/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.api.shape;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

/**
 * The different types of freehand.
 * @author Arnaud Blouin
 */
public enum FreeHandStyle {
	CURVES, LINES;

	/**
	 * @param type The type to check.
	 * @return The corresponding type. Returns CURVES by default.
	 */
	public static @NotNull FreeHandStyle getType(final String type) {
		return Arrays.stream(values()).filter(style -> style.toString().equals(type)).findFirst().orElse(CURVES);
	}
}
