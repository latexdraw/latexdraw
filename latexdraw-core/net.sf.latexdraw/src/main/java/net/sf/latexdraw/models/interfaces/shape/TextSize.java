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

/**
 * This enumeration defines the concept of text size for latex texts.
 * @author Arnaud Blouin
 */
public enum TextSize {
	/** \tiny size */
	TINY {
		@Override
		public String getLatexToken() {
			return "tiny"; //$NON-NLS-1$
		}
	},
	/** \scriptsize size */
	SCRIPT {
		@Override
		public String getLatexToken() {
			return "scriptsize"; //$NON-NLS-1$
		}
	},
	/** \footnotesize size */
	FOOTNOTE {
		@Override
		public String getLatexToken() {
			return "footnotesize"; //$NON-NLS-1$
		}
	},
	/** \small size */
	SMALL {
		@Override
		public String getLatexToken() {
			return "small"; //$NON-NLS-1$
		}
	},
	/** \small size */
	NORMAL {
		@Override
		public String getLatexToken() {
			return "normalsize"; //$NON-NLS-1$
		}
	},
	/** \large size */
	LARGE1 {
		@Override
		public String getLatexToken() {
			return "large"; //$NON-NLS-1$
		}
	},
	/** \Large size */
	LARGE2 {
		@Override
		public String getLatexToken() {
			return "Large"; //$NON-NLS-1$
		}
	},
	/** \LARGE size */
	LARGE3 {
		@Override
		public String getLatexToken() {
			return "LARGE"; //$NON-NLS-1$
		}
	},
	/** \huge size */
	HUGE1 {
		@Override
		public String getLatexToken() {
			return "huge"; //$NON-NLS-1$
		}
	},
	/** \Huge size */
	HUGE2 {
		@Override
		public String getLatexToken() {
			return "Huge"; //$NON-NLS-1$
		}
	};

	/**
	 * @return The latex token corresponding to the text size.
	 * @since 3.0
	 */
	public abstract String getLatexToken();

	/**
	 * @param size The text size value to analyse.
	 * @return The corresponding text size item or null.
	 * @since 3.0
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
}
