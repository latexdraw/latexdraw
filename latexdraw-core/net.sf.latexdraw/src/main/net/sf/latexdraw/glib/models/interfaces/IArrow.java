package net.sf.latexdraw.glib.models.interfaces;

import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

/**
 * Defines an interface that classes defining an arrow should implement.<br>
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
public interface IArrow extends IArrowable {
	public static enum ArrowStyle {
		NONE {
			@Override
			public String getPSTToken() { return ""; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return NONE; }
		}, LEFT_ARROW {
			@Override
			public String getPSTToken() { return PSTricksConstants.LARROW_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return RIGHT_ARROW; }

			@Override
			public boolean needsLineReduction() { return true; }
		}, RIGHT_ARROW {
			@Override
			public String getPSTToken() { return PSTricksConstants.RARROW_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return LEFT_ARROW; }

			@Override
			public boolean needsLineReduction() { return true; }
		}, RIGHT_DBLE_ARROW {
			@Override
			public String getPSTToken() { return PSTricksConstants.DRARROW_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return LEFT_DBLE_ARROW; }

			@Override
			public boolean needsLineReduction() { return true; }
		}, LEFT_DBLE_ARROW {
			@Override
			public String getPSTToken() { return PSTricksConstants.DLARROW_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return RIGHT_DBLE_ARROW; }

			@Override
			public boolean needsLineReduction() { return true; }
		}, BAR_END {
			@Override
			public String getPSTToken() { return PSTricksConstants.BAREND_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return BAR_END; }
		}, BAR_IN {
			@Override
			public String getPSTToken() { return PSTricksConstants.BARIN_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return BAR_IN; }
		}, LEFT_SQUARE_BRACKET {
			@Override
			public String getPSTToken() { return PSTricksConstants.LSBRACKET_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return RIGHT_SQUARE_BRACKET; }

			@Override
			public boolean needsLineReduction() { return true; }
		}, RIGHT_SQUARE_BRACKET {
			@Override
			public String getPSTToken() { return PSTricksConstants.RSBRACKET_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return LEFT_SQUARE_BRACKET; }

			@Override
			public boolean needsLineReduction() { return true; }
		}, LEFT_ROUND_BRACKET {
			@Override
			public String getPSTToken() { return PSTricksConstants.LRBRACKET_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return RIGHT_ROUND_BRACKET; }

			@Override
			public boolean needsLineReduction() { return true; }
		}, RIGHT_ROUND_BRACKET {
			@Override
			public String getPSTToken() { return PSTricksConstants.RRBRACKET_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return LEFT_ROUND_BRACKET; }

			@Override
			public boolean needsLineReduction() { return true; }
		}, CIRCLE_END {
			@Override
			public String getPSTToken() { return PSTricksConstants.CIRCLEEND_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return CIRCLE_END; }
		}, CIRCLE_IN {
			@Override
			public String getPSTToken() { return PSTricksConstants.CIRCLEIN_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return CIRCLE_IN; }
		}, DISK_END {
			@Override
			public String getPSTToken() { return PSTricksConstants.DISKEND_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return DISK_END; }
		}, DISK_IN {
			@Override
			public String getPSTToken() { return PSTricksConstants.DISKIN_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return DISK_IN; }
		}, ROUND_END {
			@Override
			public String getPSTToken() { return PSTricksConstants.ROUNDEND_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return ROUND_END; }
		}, ROUND_IN {
			@Override
			public String getPSTToken() { return PSTricksConstants.ROUNDIN_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return ROUND_IN; }

			@Override
			public boolean needsLineReduction() { return true; }
		}, SQUARE_END {
			@Override
			public String getPSTToken() { return PSTricksConstants.SQUAREEND_STYLE; }

			@Override
			public ArrowStyle getOppositeArrowStyle() { return SQUARE_END; }
		};

		/**
		 * @return The opposite arrow of the current one.
		 * @since 3.0
		 */
		public abstract ArrowStyle getOppositeArrowStyle();

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

		public boolean isReducingShape() {
			return this==LEFT_ARROW || this==RIGHT_ARROW || this==LEFT_DBLE_ARROW || this==RIGHT_DBLE_ARROW || this==ROUND_IN ||
					this==CIRCLE_IN || this==DISK_IN;
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
		 * @return True if the style is a style for right arrows.
		 * @since 3.0
		 */
		public boolean isRightStyle() {
			return this==RIGHT_ARROW || this==RIGHT_DBLE_ARROW || this==RIGHT_ROUND_BRACKET || this==RIGHT_SQUARE_BRACKET;
		}


		/**
		 * @param style The style to test.
		 * @return True if the given style and the calling style are of the same kind (e.g. both are circles or disks).
		 * @since 3.0
		 */
		public boolean isSameKind(final ArrowStyle style) {
			return style!=null && (isArrow() && style.isArrow() || isBar() && style.isBar() || isCircleDisk() && style.isCircleDisk() ||
								   isRoundBracket() && style.isRoundBracket() || isSquareBracket() && style.isSquareBracket());
		}

		/**
		 * @return True if the current arrow style need its line to be reduced.
		 * For instance the arrow style requires its line to be smaller.
		 * The width of the arrow can be used in complement to reduce the line.
		 * @since 3.0
		 */
		public boolean needsLineReduction() {
			return false;
		}

		/**
		 * @return The arrow style corresponding to the given PST token or the style name (or null).
		 * @param token The PST token or the name of the style to get (e.g. NONE.toString()).
		 * @since 3.0
		 */
		public static ArrowStyle getArrowStyle(final String token) {
			if(token==null) return null;
			if(token.length()==0 || NONE.toString().equals(token)) return NONE;
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
	 * Copies the parameters of the given arrow to the current arrow.
	 * The owner of the arrow to copy is not copied to the current arrow.
	 * @param model The arrow to copy. Cannot be null.
	 * @since 3.0
	 */
	void copy(final IArrow model);


	/**
	 * @return True if the current arrow has a style.
	 * @since 3.0
	 */
	boolean hasStyle();

	/**
	 * Defines the style of the arrow.
	 * @param arrowStyle The new style of the arrow.
	 * @since 3.0
	 */
	void setArrowStyle(final ArrowStyle arrowStyle);

	/**
	 * @return The length of the arrow computed from the provided formula:
	 * length = arrowlength x (num x linewidth + dim)
	 * @since 3.0
	 */
	double getArrowShapeLength();

	/**
	 * @return The style of the arrow.
	 * @since 3.0
	 */
	ArrowStyle getArrowStyle();

	/**
	 * @return True if the arrow is the left arrow of its shape.
	 * @since 3.0
	 */
	boolean isLeftArrow();

	/**
	 * @return True if the arrow if inverted in its shape.
	 * @since 3.0
	 */
	boolean isInverted();

	/**
	 * @return The shape that contains the arrow.
	 * @since 3.0
	 */
	IShape getShape();

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
