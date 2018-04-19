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
package net.sf.latexdraw.models.interfaces.shape;

import java.util.Arrays;

/**
 * The position of the text (bottom-right, top-left, etc.).
 * @author Arnaud Blouin
 */
public enum TextPosition {
	BOT_LEFT {
		@Override
		public String getLatexToken() {
			return "bl"; //NON-NLS
		}
	}, BOT {
		@Override
		public String getLatexToken() {
			return "b"; //NON-NLS
		}
	}, BOT_RIGHT {
		@Override
		public String getLatexToken() {
			return "br"; //NON-NLS
		}
	}, TOP_LEFT {
		@Override
		public String getLatexToken() {
			return "tl"; //NON-NLS
		}
	}, TOP {
		@Override
		public String getLatexToken() {
			return "t"; //NON-NLS
		}
	}, TOP_RIGHT {
		@Override
		public String getLatexToken() {
			return "tr"; //NON-NLS
		}
	}, BASE {
		@Override
		public String getLatexToken() {
			return "B"; //NON-NLS
		}
	}, BASE_LEFT {
		@Override
		public String getLatexToken() {
			return "Bl"; //NON-NLS
		}
	}, BASE_RIGHT {
		@Override
		public String getLatexToken() {
			return "Br"; //NON-NLS
		}
	}, LEFT {
		@Override
		public String getLatexToken() {
			return "l"; //NON-NLS
		}
	}, RIGHT {
		@Override
		public String getLatexToken() {
			return "r"; //NON-NLS
		}
	}, CENTER {
		@Override
		public String getLatexToken() {
			return ""; //NON-NLS
		}
	};

	/**
	 * @param latexToken The latex token to test.
	 * @return The TextPosition enumeration item corresponding to the given latex token.
	 * @since 3.0
	 */
	public static TextPosition getTextPosition(final String latexToken) {
		return Arrays.stream(values()).filter(it -> it.getLatexToken().equals(latexToken)).findFirst().orElse(BOT_LEFT);
	}

	/**
	 * @return The latex token corresponding to the text position.
	 * @since 3.0
	 */
	public abstract String getLatexToken();
}
