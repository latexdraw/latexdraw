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
 * The different possible plotting styles.
 * @author Arnaud Blouin
 */
public enum PlotStyle {
	CURVE {
		@Override
		public String getPSTToken() {
			return "curve"; //NON-NLS
		}
	}, LINE {
		@Override
		public String getPSTToken() {
			return "line"; //NON-NLS
		}
	}, DOTS {
		@Override
		public String getPSTToken() {
			return "dots"; //NON-NLS
		}
	}, POLYGON {
		@Override
		public String getPSTToken() {
			return "polygon"; //NON-NLS
		}
	}, ECURVE {
		@Override
		public String getPSTToken() {
			return "ecurve"; //NON-NLS
		}
	}, CCURVE {
		@Override
		public String getPSTToken() {
			return "ccurve"; //NON-NLS
		}
	};

	/**
	 * @param latexToken The latex token to check.
	 * @return The style corresponding to the PSTricks token given as parameter, or CURVE otherwise.
	 * @since 3.2
	 */
	public static PlotStyle getPlotStyle(final String latexToken) {
		return Arrays.stream(values()).filter(it -> it.getPSTToken().equals(latexToken)).findFirst().orElse(CURVE);
	}

	/** @return The PST token corresponding to the plot style. */
	public abstract String getPSTToken();
}
