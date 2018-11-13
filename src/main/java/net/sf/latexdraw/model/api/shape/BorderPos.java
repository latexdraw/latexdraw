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
package net.sf.latexdraw.model.api.shape;

import java.util.Arrays;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;

/**
 * The different positions of the border.
 * @author Arnaud Blouin
 */
public enum BorderPos {
	INTO {
		@Override
		public @NotNull String getLatexToken() {
			return PSTricksConstants.BORDERS_INSIDE;
		}
	}, MID {
		@Override
		public @NotNull String getLatexToken() {
			return PSTricksConstants.BORDERS_MIDDLE;
		}
	}, OUT {
		@Override
		public @NotNull String getLatexToken() {
			return PSTricksConstants.BORDERS_OUTSIDE;
		}
	};

	/**
	 * @param style The style to get.
	 * @return The style which name is the given name style.
	 */
	public static @NotNull BorderPos getStyle(final String style) {
		return Arrays.stream(values()).filter(it -> it.toString().equals(style) || it.getLatexToken().equals(style)).findFirst().orElse(INTO);
	}

	/**
	 * @return The latex token corresponding to the BorderPos.
	 */
	public abstract @NotNull String getLatexToken();
}
