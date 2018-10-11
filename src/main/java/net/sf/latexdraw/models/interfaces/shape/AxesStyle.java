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
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * Defines the different kinds of axes.
 * @author Arnaud Blouin
 */
public enum AxesStyle {
	AXES {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.TOKEN_AXES_STYLE_AXES;
		}

		@Override
		public boolean supportsArrows() {
			return true;
		}

		@Override
		public String toString() {
			return LangTool.INSTANCE.getBundle().getString("Axe.1"); //NON-NLS
		}
	}, FRAME {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.TOKEN_AXES_STYLE_FRAME;
		}

		@Override
		public boolean supportsArrows() {
			return false;
		}

		@Override
		public String toString() {
			return LangTool.INSTANCE.getBundle().getString("Axe.2"); //NON-NLS
		}
	}, NONE {
		@Override
		public String getPSTToken() {
			return PSTricksConstants.TOKEN_AXES_STYLE_NONE;
		}

		@Override
		public boolean supportsArrows() {
			return false;
		}

		@Override
		public String toString() {
			return "None"; //NON-NLS
		}
	};

	/**
	 * @param style The PST token or the name of the style (e.g. AXES.toString()) corresponding to
	 * the style to get.
	 * @return The corresponding style or AXES.
	 * @since 3.0
	 */
	public static AxesStyle getStyle(final String style) {
		return Arrays.stream(values()).filter(it -> it.toString().equals(style) || it.getPSTToken().equals(style)).findFirst().orElse(AXES);
	}

	/**
	 * @return True if the axe style supports arrows.
	 * @since 3.0
	 */
	public abstract boolean supportsArrows();

	/**
	 * @return The PST token corresponding to the axe style.
	 * @since 3.0
	 */
	public abstract String getPSTToken();
}
