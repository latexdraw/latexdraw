package net.sf.latexdraw.glib.models.interfaces;

import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

/**
 * Defines an interface that classes defining an arrow should implement.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public interface IArrow {
	public static enum ArrowStyle {
		NONE {
			@Override
			public String getPSTToken() { return PSTricksConstants.NONEARROW_STYLE; }
		}, LEFT_ARROW {
			@Override
			public String getPSTToken() { return PSTricksConstants.LARROW_STYLE; }
		}, RIGHT_ARROW {
			@Override
			public String getPSTToken() { return PSTricksConstants.RARROW_STYLE; }
		}, RIGHT_DBLE_ARROW {
			@Override
			public String getPSTToken() { return PSTricksConstants.DRARROW_STYLE; }
		}, LEFT_DBLE_ARROW {
			@Override
			public String getPSTToken() { return PSTricksConstants.DLARROW_STYLE; }
		}, BAR_END {
			@Override
			public String getPSTToken() { return PSTricksConstants.BAREND_STYLE; }
		}, BAR_IN {
			@Override
			public String getPSTToken() { return PSTricksConstants.BARIN_STYLE; }
		}, LEFT_SQUARE_BRACKET {
			@Override
			public String getPSTToken() { return PSTricksConstants.LSBRACKET_STYLE; }
		}, RIGHT_SQUARE_BRACKET {
			@Override
			public String getPSTToken() { return PSTricksConstants.RSBRACKET_STYLE; }
		}, LEFT_ROUND_BRACKET {
			@Override
			public String getPSTToken() { return PSTricksConstants.LRBRACKET_STYLE; }
		}, RIGHT_ROUND_BRACKET {
			@Override
			public String getPSTToken() { return PSTricksConstants.RRBRACKET_STYLE; }
		}, CIRCLE_END {
			@Override
			public String getPSTToken() { return PSTricksConstants.CIRCLEEND_STYLE; }
		}, CIRCLE_IN {
			@Override
			public String getPSTToken() { return PSTricksConstants.CIRCLEIN_STYLE; }
		}, DISK_END {
			@Override
			public String getPSTToken() { return PSTricksConstants.DISKEND_STYLE; }
		}, DISK_IN {
			@Override
			public String getPSTToken() { return PSTricksConstants.DISKIN_STYLE; }
		}, ROUND_END {
			@Override
			public String getPSTToken() { return PSTricksConstants.ROUNDEND_STYLE; }
		}, ROUND_IN {
			@Override
			public String getPSTToken() { return PSTricksConstants.ROUNDIN_STYLE; }
		}, SQUARE_END {
			@Override
			public String getPSTToken() { return PSTricksConstants.SQUAREEND_STYLE; }
		};

		/**
		 * @return The PSTricks token of the arrow style.
		 * @since 3.0
		 */
		public abstract String getPSTToken();

		/**
		 * @return True if the style is a bar.
		 * @since 3.0
		 */
		public boolean isBar() {
			return this==BAR_END || this==BAR_IN;
		}
		
		/**
		 * @return True if the style is an arrow.
		 * @since 3.0
		 */
		public boolean isArrow() {
			return this==LEFT_ARROW || this==RIGHT_ARROW || this==RIGHT_DBLE_ARROW || this== LEFT_DBLE_ARROW;
		}
		
		/**
		 * @return True if the style is a round bracket.
		 * @since 3.0
		 */
		public boolean isRoundBracket() {
			return this==LEFT_ROUND_BRACKET || this==RIGHT_ROUND_BRACKET;
		}
		
		/**
		 * @return True if the style is a square bracket.
		 * @since 3.0
		 */
		public boolean isSquareBracket() {
			return this==LEFT_SQUARE_BRACKET || this==RIGHT_SQUARE_BRACKET;
		}
		
		/**
		 * @return True if the style is a circle or a disk.
		 * @since 3.0
		 */
		public boolean isCircleDisk() {
			return this==CIRCLE_END || this==CIRCLE_IN || this==DISK_END || this==DISK_IN;
		}
		

		/**
		 * @return The arrow style corresponding to the given PST token or the style name (or null).
		 * @param token The PST token or the name of the style to get (e.g. NONE.toString()).
		 * @since 3.0
		 */
		public static ArrowStyle getArrowStyle(final String token) {
			if(token==null) return null;
			if(PSTricksConstants.NONEARROW_STYLE.equals(token) || NONE.toString().equals(token)) return NONE;
			if(PSTricksConstants.LARROW_STYLE.equals(token) || LEFT_ARROW.toString().equals(token)) return LEFT_ARROW;
			if(PSTricksConstants.RARROW_STYLE.equals(token) || RIGHT_ARROW.toString().equals(token)) return RIGHT_ARROW;
			if(PSTricksConstants.DRARROW_STYLE.equals(token) || RIGHT_DBLE_ARROW.toString().equals(token)) return RIGHT_DBLE_ARROW;
			if(PSTricksConstants.DLARROW_STYLE.equals(token) || LEFT_DBLE_ARROW.toString().equals(token)) return LEFT_DBLE_ARROW;
			if(PSTricksConstants.BARIN_STYLE.equals(token) || BAR_IN.toString().equals(token)) return BAR_IN;
			if(PSTricksConstants.BAREND_STYLE.equals(token) || BAR_END.toString().equals(token)) return BAR_END;
			if(PSTricksConstants.RSBRACKET_STYLE.equals(token) || RIGHT_SQUARE_BRACKET.toString().equals(token)) return RIGHT_SQUARE_BRACKET;
			if(PSTricksConstants.LSBRACKET_STYLE.equals(token) || LEFT_SQUARE_BRACKET.toString().equals(token)) return LEFT_SQUARE_BRACKET;
			if(PSTricksConstants.LRBRACKET_STYLE.equals(token) || LEFT_ROUND_BRACKET.toString().equals(token)) return LEFT_ROUND_BRACKET;
			if(PSTricksConstants.RRBRACKET_STYLE.equals(token) || RIGHT_ROUND_BRACKET.toString().equals(token)) return RIGHT_ROUND_BRACKET;
			if(PSTricksConstants.CIRCLEIN_STYLE.equals(token) || CIRCLE_IN.toString().equals(token)) return CIRCLE_IN;
			if(PSTricksConstants.CIRCLEEND_STYLE.equals(token) || CIRCLE_END.toString().equals(token)) return CIRCLE_END;
			if(PSTricksConstants.DISKIN_STYLE.equals(token) || DISK_IN.toString().equals(token)) return DISK_IN;
			if(PSTricksConstants.DISKEND_STYLE.equals(token) || DISK_END.toString().equals(token)) return DISK_END;
			if(PSTricksConstants.SQUAREEND_STYLE.equals(token) || SQUARE_END.toString().equals(token)) return SQUARE_END;
			if(PSTricksConstants.ROUNDEND_STYLE.equals(token) || ROUND_END.toString().equals(token)) return ROUND_END;
			if(PSTricksConstants.ROUNDIN_STYLE.equals(token) || ROUND_IN.toString().equals(token)) return ROUND_IN;
			return null;
		}
	}


	/**
	 * @return True if the current arrow has no style.
	 * @since 3.0
	 */
	boolean isWithoutStyle();

	/**
	 * Defines the style of the arrow.
	 * @param arrowStyle The new style of the arrow.
	 * @since 3.0
	 */
	void setArrowStyle(final ArrowStyle arrowStyle);

	/**
	 * Sets the latex parameter dotsize dim.
	 * @param dotSizeDim The new dotsize dim.
	 * @since 3.0
	 */
	void setDotSizeDim(final double dotSizeDim);

	/**
	 * Sets the latex parameter dotsize num.
	 * @param dotSizeNum The new dotsize num.
	 * @since 3.0
	 */
	void setDotSizeNum(final double dotSizeNum);

	/**
	 * @return The position of the arrow head.
	 * @since 3.0
	 */
	IPoint getPosition();

	/**
	 * Sets the latex parameter tbarsize num.
	 * @param tbarSizeNum The new tbarsize num.
	 * @since 3.0
	 */
	void setTBarSizeNum(final double tbarSizeNum);

	/**
	 * Sets the latex parameter tbarsize num.
	 * @param tbarSizeDim The new tbarsize num.
	 * @since 3.0
	 */
	void setTBarSizeDim(final double tbarSizeDim);

	/**
	 * @return The tbarsize dim parameter.
	 * @since 3.0
	 */
	double getTBarSizeDim();

	/**
	 * @return The tbarsize num parameter.
	 * @since 3.0
	 */
	double getTBarSizeNum();

	/**
	 * Sets the latex parameter rbracket num.
	 * @param rBracketNum The new rbracket num.
	 * @since 3.0
	 */
	void setRBracketNum(final double rBracketNum);

	/**
	 * Sets the latex parameter bracket num.
	 * @param bracketNum The new bracket num.
	 * @since 3.0
	 */
	void setBracketNum(final double bracketNum);

	/**
	 * Defines the length of the arrow.
	 * @param lgth The new length of the arrow.
	 * @since 3.0
	 */
	void setArrowLength(final double lgth);

	/**
	 * Sets the latex parameter arrowSize num.
	 * @param arrowSizeDim The new arrowSize num.
	 * @since 3.0
	 */
	void setArrowSizeDim(final double arrowSizeDim);

	/**
	 * Sets the latex parameter arrowSize num.
	 * @param arrowSizeNum The new arrowSize num.
	 * @since 3.0
	 */
	void setArrowSizeNum(final double arrowSizeNum);

	/**
	 * Defines the inset of the arrow.
	 * @param inset The new inset.
	 * @since 3.0
	 */
	void setArrowInset(final double inset);

	/**
	 * @return The style of the arrow.
	 * @since 3.0
	 */
	ArrowStyle getArrowStyle();

	/**
	 * @return The dotsize dim parameter.
	 * @since 3.0
	 */
	double getDotSizeDim();

	/**
	 * @return The dotsize num parameter.
	 * @since 3.0
	 */
	double getDotSizeNum();

	/**
	 * @return True if the arrow is the left arrow of its shape.
	 * @since 3.0
	 */
	boolean isLeftArrow();

	/**
	 * @return The bracket num parameter.
	 * @since 3.0
	 */
	double getBracketNum();

	/**
	 * @return True if the arrow if inverted in its shape.
	 * @since 3.0
	 */
	boolean isInverted();

	/**
	 * @return The arrowsize num parameter.
	 * @since 3.0
	 */
	double getArrowSizeNum();

	/**
	 * @return The arrowsize dim parameter.
	 * @since 3.0
	 */
	double getArrowSizeDim();

	/**
	 * @return The inset of the arrow.
	 * @since 3.0
	 */
	double getArrowInset();

	/**
	 * @return The length of the arrow.
	 * @since 3.0
	 */
	double getArrowLength();

	/**
	 * @return The rbracket parameter.
	 * @since 3.0
	 */
	double getRBracketNum();

	/**
	 * @return The shape that contains the arrow.
	 * @since 3.0
	 */
	IShape getShape();

	/**
	 * Defines the shape that contains the arrow.
	 * @param shape The new shape. Cannot be null.
	 * @since 3.0
	 */
	void setShape(final IShape shape);
	
	/**
	 * @return The line that can be used to locate the arrow.
	 * @since 3.0
	 */
	ILine getArrowLine();
	
	/**
	 * @return The radius of the rounded arrow styles.
	 * @since 3.0
	 */
	double getRoundShapedArrowRadius();
	
	/**
	 * @return The width of the bar arrow styles.
	 * @since 3.0
	 */
	double getBarShapedArrowWidth();
	
	/**
	 * @return The length of the brackets of bracket arrow styles.
	 * @since 3.0
	 */
	double getBracketShapedArrowLength();
	
	/**
	 * @return The width of arrow styles.
	 * @since 3.0
	 */
	double getArrowShapedWidth();
}
