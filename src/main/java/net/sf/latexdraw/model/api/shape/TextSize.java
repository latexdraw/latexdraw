/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.api.shape;

import org.jetbrains.annotations.NotNull;

/**
 * This enumeration defines the concept of text size for latex texts.
 * @author Arnaud Blouin
 */
public enum TextSize {
	/** \tiny size */
	TINY {
		@Override
		public @NotNull String getLatexToken() {
			return "tiny"; //NON-NLS
		}
	},
	/** \scriptsize size */
	SCRIPT {
		@Override
		public @NotNull String getLatexToken() {
			return "scriptsize"; //NON-NLS
		}
	},
	/** \footnotesize size */
	FOOTNOTE {
		@Override
		public @NotNull String getLatexToken() {
			return "footnotesize"; //NON-NLS
		}
	},
	/** \small size */
	SMALL {
		@Override
		public @NotNull String getLatexToken() {
			return "small"; //NON-NLS
		}
	},
	/** \small size */
	NORMAL {
		@Override
		public @NotNull String getLatexToken() {
			return "normalsize"; //NON-NLS
		}
	},
	/** \large size */
	LARGE1 {
		@Override
		public @NotNull String getLatexToken() {
			return "large"; //NON-NLS
		}
	},
	/** \Large size */
	LARGE2 {
		@Override
		public @NotNull String getLatexToken() {
			return "Large"; //NON-NLS
		}
	},
	/** \LARGE size */
	LARGE3 {
		@Override
		public @NotNull String getLatexToken() {
			return "LARGE"; //NON-NLS
		}
	},
	/** \huge size */
	HUGE1 {
		@Override
		public @NotNull String getLatexToken() {
			return "huge"; //NON-NLS
		}
	},
	/** \Huge size */
	HUGE2 {
		@Override
		public @NotNull String getLatexToken() {
			return "Huge"; //NON-NLS
		}
	};

	/**
	 * @param size The text size value to analyse.
	 * @return The corresponding text size item or null.
	 */
	public static TextSize getTextSizeFromSize(final int size) {
		switch(size) {
			case 11:
				return TINY;
			case 16:
				return FOOTNOTE;
			case 35:
				return HUGE1;
			case 44:
				return HUGE2;
			case 22:
				return LARGE1;
			case 24:
				return LARGE2;
			case 30:
				return LARGE3;
			case 18:
				return NORMAL;
			case 14:
				return SCRIPT;
			case 17:
				return SMALL;
			default:
				return null;
		}
	}

	/**
	 * @return The latex token corresponding to the text size.
	 */
	public abstract @NotNull String getLatexToken();
}
