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
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * The arrow styles.
 * @author Arnaud Blouin
 */
public enum ArrowStyle {
	NONE {
		@Override
		public String getPSTToken() {
			return "";
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return NONE;
		}
	}, LEFT_ARROW {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.LARROW_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return RIGHT_ARROW;
		}

		@Override
		public boolean needsLineReduction() {
			return true;
		}
	}, RIGHT_ARROW {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.RARROW_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return LEFT_ARROW;
		}

		@Override
		public boolean needsLineReduction() {
			return true;
		}
	}, RIGHT_DBLE_ARROW {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.DRARROW_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return LEFT_DBLE_ARROW;
		}

		@Override
		public boolean needsLineReduction() {
			return true;
		}
	}, LEFT_DBLE_ARROW {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.DLARROW_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return RIGHT_DBLE_ARROW;
		}

		@Override
		public boolean needsLineReduction() {
			return true;
		}
	}, BAR_END {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.BAREND_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return BAR_END;
		}
	}, BAR_IN {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.BARIN_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return BAR_IN;
		}
	}, LEFT_SQUARE_BRACKET {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.LSBRACKET_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return RIGHT_SQUARE_BRACKET;
		}
	}, RIGHT_SQUARE_BRACKET {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.RSBRACKET_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return LEFT_SQUARE_BRACKET;
		}
	}, LEFT_ROUND_BRACKET {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.LRBRACKET_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return RIGHT_ROUND_BRACKET;
		}
	}, RIGHT_ROUND_BRACKET {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.RRBRACKET_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return LEFT_ROUND_BRACKET;
		}
	}, CIRCLE_END {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.CIRCLEEND_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return CIRCLE_END;
		}
	}, CIRCLE_IN {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.CIRCLEIN_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return CIRCLE_IN;
		}
	}, DISK_END {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.DISKEND_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return DISK_END;
		}
	}, DISK_IN {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.DISKIN_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return DISK_IN;
		}
	}, ROUND_END {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.ROUNDEND_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return ROUND_END;
		}
	}, ROUND_IN {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.ROUNDIN_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return ROUND_IN;
		}

		@Override
		public boolean needsLineReduction() {
			return true;
		}
	}, SQUARE_END {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.SQUAREEND_STYLE;
		}

		@Override
		public ArrowStyle getOppositeArrowStyle() {
			return SQUARE_END;
		}
	};

	/**
	 * @param token The PST token or the name of the style to get (e.g. NONE.toString()).
	 * @return The arrow style corresponding to the given PST token or the style name (or null).
	 * @since 3.0
	 */
	public static ArrowStyle getArrowStyle(final String token) {
		return Arrays.stream(values()).filter(it -> it.getPSTToken().equals(token) || it.toString().equals(token)).findAny().orElse(ArrowStyle.NONE);
	}

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
		return this == BAR_END || this == BAR_IN;
	}

	public boolean isReducingShape() {
		return this == LEFT_ARROW || this == RIGHT_ARROW || this == LEFT_DBLE_ARROW || this == RIGHT_DBLE_ARROW || this == ROUND_IN || this == CIRCLE_IN ||
			this == DISK_IN;
	}

	/**
	 * @return True if the style is an arrow.
	 * @since 3.0
	 */
	public boolean isArrow() {
		return this == LEFT_ARROW || this == RIGHT_ARROW || this == RIGHT_DBLE_ARROW || this == LEFT_DBLE_ARROW;
	}

	/**
	 * @return True if the style is a round bracket.
	 * @since 3.0
	 */
	public boolean isRoundBracket() {
		return this == LEFT_ROUND_BRACKET || this == RIGHT_ROUND_BRACKET;
	}

	/**
	 * @return True if the style is a square bracket.
	 * @since 3.0
	 */
	public boolean isSquareBracket() {
		return this == LEFT_SQUARE_BRACKET || this == RIGHT_SQUARE_BRACKET;
	}

	/**
	 * @return True if the style is a circle or a disk.
	 * @since 3.0
	 */
	public boolean isCircleDisk() {
		return this == CIRCLE_END || this == CIRCLE_IN || this == DISK_END || this == DISK_IN;
	}

	/**
	 * @return True if the style is a style for right arrows.
	 * @since 3.0
	 */
	public boolean isRightStyle() {
		return this == RIGHT_ARROW || this == RIGHT_DBLE_ARROW || this == RIGHT_ROUND_BRACKET || this == RIGHT_SQUARE_BRACKET;
	}

	/**
	 * @param style The style to test.
	 * @return True if the given style and the calling style are of the same kind (e.g. both are circles or disks).
	 * @since 3.0
	 */
	public boolean isSameKind(final ArrowStyle style) {
		return style != null && ((isArrow() && style.isArrow()) || (isBar() && style.isBar()) || (isCircleDisk() && style.isCircleDisk()) || (isRoundBracket()
			&& style.isRoundBracket()) || (isSquareBracket() && style.isSquareBracket()));
	}

	/**
	 * @return True if the current arrow style need its line to be reduced. For instance the arrow
	 * style requires its line to be smaller. The width of the arrow can be used in complement to reduce the line.
	 * @since 3.0
	 */
	public boolean needsLineReduction() {
		return false;
	}
}
