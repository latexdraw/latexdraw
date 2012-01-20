package net.sf.latexdraw.glib.models.interfaces;

/**
 * Defines an interface that classes defining a text should implement.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
			if(BOT.getLatexToken().equals(latexToken))
				return BOT;
			if(BOT_LEFT.getLatexToken().equals(latexToken))
				return BOT_LEFT;
			if(BOT_RIGHT.getLatexToken().equals(latexToken))
				return BOT_RIGHT;
			if(TOP.getLatexToken().equals(latexToken))
				return TOP;
			if(TOP_LEFT.getLatexToken().equals(latexToken))
				return TOP_LEFT;
			if(TOP_RIGHT.getLatexToken().equals(latexToken))
				return TOP_RIGHT;

			return null;
		}
	}


	@Override
	IText duplicate();

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
