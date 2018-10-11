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
 * The different styles of dot.
 * @author Arnaud Blouin
 */
public enum DotStyle {
	ASTERISK {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.ASTERISK_STYLE;
		}
	}, BAR {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.BAR_STYLE;
		}
	}, OPLUS {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.OPLUS_STYLE;
		}
	}, OTIMES {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.OTIMES_STYLE;
		}
	}, PLUS {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.PLUS_STYLE;
		}
	}, DOT {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.DOT_STYLE;
		}
	}, X {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.X_STYLE;
		}
	}, FDIAMOND {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.FDIAMOND_STYLE;
		}
	}, FTRIANGLE {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.FTRIANGLE_STYLE;
		}
	}, FPENTAGON {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.FPENTAGON_STYLE;
		}
	}, DIAMOND {
		@Override
		public boolean isFillable() {
			return true;
		}

		@Override
		public String getPSTToken() {
			return PSTricksConstants.DIAMOND_STYLE;
		}
	}, O {
		@Override
		public boolean isFillable() {
			return true;
		}

		@Override
		public String getPSTToken() {
			return PSTricksConstants.O_STYLE;
		}
	}, TRIANGLE {
		@Override
		public boolean isFillable() {
			return true;
		}

		@Override
		public String getPSTToken() {
			return PSTricksConstants.TRIANGLE_STYLE;
		}
	}, PENTAGON {
		@Override
		public boolean isFillable() {
			return true;
		}

		@Override
		public String getPSTToken() {
			return PSTricksConstants.PENTAGON_STYLE;
		}
	}, SQUARE {
		@Override
		public boolean isFillable() {
			return true;
		}

		@Override
		public String getPSTToken() {
			return PSTricksConstants.SQUARE_STYLE;
		}
	}, FSQUARE {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.FSQUARE_STYLE;
		}
	};

	/**
	 * @param styleName The style to get (in PST or the name of the style, e.g. FSQUARE.toString())
	 * @return The style which name is the given name style or DOT.
	 * @since 3.0
	 */
	public static DotStyle getStyle(final String styleName) {
		return Arrays.stream(values()).filter(style -> style.name().equals(styleName) || style.getPSTToken().equals(styleName)).findFirst().orElse(DOT);
	}

	/**
	 * Allows to know if the dot shape can be filled.
	 * @return True if the dot can be filled.
	 */
	public boolean isFillable() {
		return false;
	}

	/**
	 * @return The PSTricks token corresponding to the dot style.
	 * @since 3.0
	 */
	public abstract String getPSTToken();
}
