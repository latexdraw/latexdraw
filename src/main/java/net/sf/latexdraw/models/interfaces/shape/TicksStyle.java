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
 * Defines the different kinds of ticks.
 * @author Arnaud Blouin
 */
public enum TicksStyle {
	FULL {
		@Override
		public boolean isTop() {
			return true;
		}

		@Override
		public boolean isBottom() {
			return true;
		}

		@Override
		public String getPSTToken() {
			return PSTricksConstants.TOKEN_TICKS_STYLE_FULL;
		}

		@Override
		public String toString() {
			return LangTool.INSTANCE.getBundle().getString("Axe.3"); //NON-NLS
		}
	}, TOP {
		@Override
		public boolean isTop() {
			return true;
		}

		@Override
		public boolean isBottom() {
			return false;
		}

		@Override
		public String getPSTToken() {
			return PSTricksConstants.TOKEN_TICKS_STYLE_TOP;
		}

		@Override
		public String toString() {
			return LangTool.INSTANCE.getBundle().getString("Axe.4"); //NON-NLS
		}
	}, BOTTOM {
		@Override
		public boolean isTop() {
			return false;
		}

		@Override
		public boolean isBottom() {
			return true;
		}

		@Override
		public String getPSTToken() {
			return PSTricksConstants.TOKEN_TICKS_STYLE_BOTTOM;
		}

		@Override
		public String toString() {
			return LangTool.INSTANCE.getBundle().getString("Axe.5"); //NON-NLS
		}
	};

	/**
	 * @param style The style to check. Can be the PST token or the name of the style (e.g.
	 * FULL.toString()).
	 * @return The corresponding style or FULL.
	 * @since 3.0
	 */
	public static TicksStyle getStyle(final String style) {
		return Arrays.stream(values()).filter(it -> it.toString().equals(style) || it.getPSTToken().equals(style)).findFirst().orElse(FULL);
	}

	/**
	 * @return The PST token corresponding to the tick style.
	 * @since 3.0
	 */
	public abstract String getPSTToken();

	/**
	 * @return True if the current tick style considers the top ticks.
	 * @since 3.0
	 */
	public abstract boolean isTop();

	/**
	 * @return True if the current tick style considers the bottom ticks.
	 * @since 3.0
	 */
	public abstract boolean isBottom();
}
