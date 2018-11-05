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
package net.sf.latexdraw.model.api.shape;

import java.util.Arrays;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * The different styles of filling.
 * @author Arnaud Blouin
 */
public enum FillingStyle {
	NONE {
		@Override
		public boolean isFilled() {
			return false;
		}

		@Override
		public String getLatexToken() {
			return PSTricksConstants.TOKEN_FILL_NONE;
		}

		@Override
		public FillingStyle getFilledStyle() {
			return PLAIN;
		}
	}, GRAD {
		@Override
		public boolean isFilled() {
			return false;
		}

		@Override
		public String getLatexToken() {
			return PSTricksConstants.TOKEN_FILL_GRADIENT;
		}
	}, HLINES {
		@Override
		public boolean isFilled() {
			return false;
		}

		@Override
		public String getLatexToken() {
			return PSTricksConstants.TOKEN_FILL_HLINES;
		}

		@Override
		public FillingStyle getFilledStyle() {
			return HLINES_PLAIN;
		}
	}, VLINES {
		@Override
		public boolean isFilled() {
			return false;
		}

		@Override
		public String getLatexToken() {
			return PSTricksConstants.TOKEN_FILL_VLINES;
		}

		@Override
		public FillingStyle getFilledStyle() {
			return VLINES_PLAIN;
		}
	}, CLINES {
		@Override
		public boolean isFilled() {
			return false;
		}

		@Override
		public String getLatexToken() {
			return PSTricksConstants.TOKEN_FILL_CROSSHATCH;
		}

		@Override
		public FillingStyle getFilledStyle() {
			return CLINES_PLAIN;
		}
	}, PLAIN {
		@Override
		public boolean isFilled() {
			return true;
		}

		@Override
		public String getLatexToken() {
			return PSTricksConstants.TOKEN_FILL_SOLID;
		}

		@Override
		public FillingStyle getUnfilledStyle() {
			return NONE;
		}
	}, HLINES_PLAIN {
		@Override
		public boolean isFilled() {
			return true;
		}

		@Override
		public String getLatexToken() {
			return PSTricksConstants.TOKEN_FILL_HLINES_F;
		}

		@Override
		public FillingStyle getUnfilledStyle() {
			return HLINES;
		}
	}, VLINES_PLAIN {
		@Override
		public boolean isFilled() {
			return true;
		}

		@Override
		public String getLatexToken() {
			return PSTricksConstants.TOKEN_FILL_VLINES_F;
		}

		@Override
		public FillingStyle getUnfilledStyle() {
			return VLINES;
		}
	}, CLINES_PLAIN {
		@Override
		public boolean isFilled() {
			return true;
		}

		@Override
		public String getLatexToken() {
			return PSTricksConstants.TOKEN_FILL_CROSSHATCH_F;
		}

		@Override
		public FillingStyle getUnfilledStyle() {
			return CLINES;
		}
	};

	/**
	 * @param token The style to get.
	 * @return The style which name is the given name style (or null).
	 */
	public static FillingStyle getStyleFromLatex(final String token) {
		return Arrays.stream(values()).filter(style -> style.getLatexToken().equals(token)).findFirst().orElse(NONE);
	}

	/**
	 * @param style The text version of the filling style.
	 * @return The filling style that corresponds to the given text (or null).
	 */
	public static FillingStyle getStyle(final String style) {
		return Arrays.stream(values()).filter(item -> item.toString().equals(style)).findFirst().orElse(NONE);
	}

	/**
	 * Allows to know if the style is filled.
	 * @return True if the shape is filled.
	 */
	public abstract boolean isFilled();

	/**
	 * @return The latex token corresponding to the filling style.
	 */
	public abstract String getLatexToken();

	/**
	 * @return The filling style that corresponds to the given style but filled. Cannot be null.
	 */
	public FillingStyle getFilledStyle() {
		return this;
	}

	/**
	 * @return The filling style that corresponds to the given style but not filled. Cannot be null.
	 */
	public FillingStyle getUnfilledStyle() {
		return this;
	}

	/**
	 * @return True if the style is a kind of hatchings.
	 */
	public boolean isHatchings() {
		return this == HLINES || this == HLINES_PLAIN || this == VLINES || this == VLINES_PLAIN || this == CLINES || this == CLINES_PLAIN;
	}

	/**
	 * @return True if the style is a gradient.
	 */
	public boolean isGradient() {
		return this == GRAD;
	}
}
