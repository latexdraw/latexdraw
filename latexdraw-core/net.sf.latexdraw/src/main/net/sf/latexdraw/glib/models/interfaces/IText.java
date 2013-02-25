package net.sf.latexdraw.glib.models.interfaces;

/**
 * Defines an interface that classes defining a text should implement.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/03/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IText extends IPositionShape {
	/**
	 * This enumeration defines the concept of text size for latex texts.
	 */
	public static enum TextSize {
		/** \tiny size */
		TINY {
			@Override
			public String getLatexToken() {
				return "tiny";
			}
		},/** \scriptsize size */
		SCRIPT {
			@Override
			public String getLatexToken() {
				return "scriptsize";
			}
		},/** \footnotesize size */
		FOOTNOTE {
			@Override
			public String getLatexToken() {
				return "footnotesize";
			}
		},/** \small size */
		SMALL {
			@Override
			public String getLatexToken() {
				return "small";
			}
		},/** \small size */
		NORMAL {
			@Override
			public String getLatexToken() {
				return "normalsize";
			}
		},/** \large size */
		LARGE1 {
			@Override
			public String getLatexToken() {
				return "large";
			}
		},/** \Large size */
		LARGE2 {
			@Override
			public String getLatexToken() {
				return "Large";
			}
		},/** \LARGE size */
		LARGE3 {
			@Override
			public String getLatexToken() {
				return "LARGE";
			}
		},/** \huge size */
		HUGE1 {
			@Override
			public String getLatexToken() {
				return "huge";
			}
		},/** \Huge size */
		HUGE2 {
			@Override
			public String getLatexToken() {
				return "Huge";
			}
		};

		/**
		 * @return The latex token corresponding to the text size.
		 * @since 3.0
		 */
		public abstract String getLatexToken();


		/**
		 * @param token The token to analyse.
		 * @return The text size corresponding to the given latex token or null.
		 * @since 3.0
		 */
		public static TextSize getTextSizeFromToken(final String token) {
			TextSize textSize;

			if(TINY.equals(token)) textSize = TINY;
			else if(FOOTNOTE.equals(token)) textSize = FOOTNOTE;
			else if(HUGE1.equals(token)) textSize = HUGE1;
			else if(HUGE2.equals(token)) textSize = HUGE2;
			else if(LARGE1.equals(token)) textSize = LARGE1;
			else if(LARGE2.equals(token)) textSize = LARGE2;
			else if(LARGE3.equals(token)) textSize = LARGE3;
			else if(NORMAL.equals(token)) textSize = NORMAL;
			else if(SCRIPT.equals(token)) textSize = SCRIPT;
			else if(SMALL.equals(token)) textSize = SMALL;
			else textSize = null;

			return textSize;
		}


		/**
		 * @param size The text size value to analyse.
		 * @return The corresponding text size item or null.
		 * @since 3.0
		 */
		public static TextSize getTextSizeFromSize(final int size) {
			TextSize textSize;

			switch(size){
				case 11: textSize = TINY; break;
				case 16: textSize = FOOTNOTE; break;
				case 35: textSize = HUGE1; break;
				case 44: textSize = HUGE2; break;
				case 22: textSize = LARGE1; break;
				case 24: textSize = LARGE2; break;
				case 30: textSize = LARGE3; break;
				case 18: textSize = NORMAL; break;
				case 14: textSize = SCRIPT; break;
				case 17: textSize = SMALL; break;
				default: textSize = null;
			}

			return textSize;
		}
	}

	/**
	 * The position of the text (bottom-right, top-left, etc.).
	 */
	public static enum TextPosition {
		BOT_LEFT {
			@Override
			public String getLatexToken() {
				return "bl"; //$NON-NLS-1$
			}
		}, BOT {
			@Override
			public String getLatexToken() {
				return "b"; //$NON-NLS-1$
			}
		}, BOT_RIGHT {
			@Override
			public String getLatexToken() {
				return "br"; //$NON-NLS-1$
			}
		}, TOP_LEFT {
			@Override
			public String getLatexToken() {
				return "tl"; //$NON-NLS-1$
			}
		}, TOP {
			@Override
			public String getLatexToken() {
				return "t"; //$NON-NLS-1$
			}
		}, TOP_RIGHT {
			@Override
			public String getLatexToken() {
				return "tr"; //$NON-NLS-1$
			}
		}, BASE {
			@Override
			public String getLatexToken() {
				return "B"; //$NON-NLS-1$
			}
		}, BASE_LEFT {
			@Override
			public String getLatexToken() {
				return "Bl"; //$NON-NLS-1$
			}
		}, BASE_RIGHT {
			@Override
			public String getLatexToken() {
				return "Br"; //$NON-NLS-1$
			}
		}, LEFT {
			@Override
			public String getLatexToken() {
				return "l"; //$NON-NLS-1$
			}
		}, RIGHT {
			@Override
			public String getLatexToken() {
				return "r"; //$NON-NLS-1$
			}
		}, CENTER {
			@Override
			public String getLatexToken() {
				return ""; //$NON-NLS-1$
			}
		};

		/**
		 * @return The latex token corresponding to the text position.
		 * @since 3.0
		 */
		public abstract String getLatexToken();


		/**
		 * @param latexToken The latex token to test.
		 * @return The TextPosition enumeration item corresponding to the given latex token.
		 * @since 3.0
		 */
		public static TextPosition getTextPosition(final String latexToken) {
			TextPosition textPos = null;
			final TextPosition[] textPosList = values();

			for(int i=0; i<textPosList.length && textPos==null; i++)
				if(textPosList[i].getLatexToken().equals(latexToken))
					textPos = textPosList[i];

			return textPos;
		}
	}


	/**
	 * @return The text position of the text (bottom-right, top-left, etc.).
	 * @since 3.0
	 */
	TextPosition getTextPosition();


	/**
	 * Sets the text position of the text.
	 * @param textPosition The new text position of the text.
	 * @since 3.0
	 */
	void setTextPosition(final TextPosition textPosition);


	/**
	 * @return the text.
	 * @since 3.0
	 */
	String getText();

	/**
	 * @param text the text to set.
	 * @since 3.0
	 */
	void setText(final String text);
}
