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
 * The position of the text (bottom-right, top-left, etc.).
 * @author Arnaud Blouin
 */
public enum TextPosition {
	BOT_LEFT {
		@Override
		public @NotNull String getLatexToken() {
			return "bl"; //NON-NLS
		}
	}, BOT {
		@Override
		public @NotNull String getLatexToken() {
			return "b"; //NON-NLS
		}
	}, BOT_RIGHT {
		@Override
		public @NotNull String getLatexToken() {
			return "br"; //NON-NLS
		}
	}, TOP_LEFT {
		@Override
		public @NotNull String getLatexToken() {
			return "tl"; //NON-NLS
		}
	}, TOP {
		@Override
		public @NotNull String getLatexToken() {
			return "t"; //NON-NLS
		}
	}, TOP_RIGHT {
		@Override
		public @NotNull String getLatexToken() {
			return "tr"; //NON-NLS
		}
	}, LEFT {
		@Override
		public @NotNull String getLatexToken() {
			return "l"; //NON-NLS
		}
	}, RIGHT {
		@Override
		public @NotNull String getLatexToken() {
			return "r"; //NON-NLS
		}
	}, CENTER {
		@Override
		public @NotNull String getLatexToken() {
			return ""; //NON-NLS
		}
	};

	/**
	 * @param latexToken The latex token to test.
	 * @return The TextPosition enumeration item corresponding to the given latex token.
	 */
	public static @NotNull TextPosition getTextPosition(final String latexToken) {
		return Arrays.stream(values()).filter(it -> it.getLatexToken().equals(latexToken)).findFirst().orElse(BOT_LEFT);
	}

	/**
	 * @return The latex token corresponding to the text position.
	 */
	public abstract @NotNull String getLatexToken();
}
