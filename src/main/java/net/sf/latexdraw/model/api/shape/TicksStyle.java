/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.api.shape;

import java.util.Arrays;
import java.util.ResourceBundle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;

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
		public @NotNull String getPSTToken() {
			return PSTricksConstants.TOKEN_TICKS_STYLE_FULL;
		}

		@Override
		public @NotNull String getLabel(final @NotNull ResourceBundle bundle) {
			return bundle.getString("Axe.3"); //NON-NLS
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
		public @NotNull String getPSTToken() {
			return PSTricksConstants.TOKEN_TICKS_STYLE_TOP;
		}

		@Override
		public @NotNull String getLabel(final @NotNull ResourceBundle bundle) {
			return bundle.getString("Axe.4"); //NON-NLS
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
		public @NotNull String getPSTToken() {
			return PSTricksConstants.TOKEN_TICKS_STYLE_BOTTOM;
		}

		@Override
		public @NotNull String getLabel(final @NotNull ResourceBundle bundle) {
			return bundle.getString("Axe.5"); //NON-NLS
		}
	};

	/**
	 * @param style The style to check. Can be the PST token or the name of the style (e.g. FULL.toString()).
	 * @return The corresponding style or FULL.
	 */
	public static @NotNull TicksStyle getStyle(final String style) {
		return Arrays.stream(values()).filter(it -> it.toString().equals(style) || it.getPSTToken().equals(style)).findFirst().orElse(FULL);
	}

	/**
	 * @return The PST token corresponding to the tick style.
	 */
	public abstract @NotNull String getPSTToken();

	/**
	 * @return True if the current tick style considers the top ticks.
	 */
	public abstract boolean isTop();

	/**
	 * @return True if the current tick style considers the bottom ticks.
	 */
	public abstract boolean isBottom();

	/**
	 * @return The internationalised label of the ticks style.
	 */
	public abstract @NotNull String getLabel(final @NotNull ResourceBundle bundle);
}
