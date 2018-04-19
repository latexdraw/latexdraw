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
package net.sf.latexdraw.view;

import java.util.Arrays;
import java.util.Optional;
import net.sf.latexdraw.util.LangTool;

/**
 * This enumeration contains the different style of a magnetic grid.
 * @author Arnaud Blouin
 */
public enum GridStyle {
	/** A grid customised by users. */
	CUSTOMISED {
		@Override
		public String getLabel() {
			return LangTool.INSTANCE.getBundle().getString("Res.3");
		}
	}, /** The standard grid. */
	STANDARD {
			@Override
			public String getLabel() {
				return LangTool.INSTANCE.getBundle().getString("PreferencesFrame.4");
			}
		}, /** No grid. */
	NONE {
			@Override
			public String getLabel() {
				return "None";
			}
		}; //NON-NLS

	/**
	 * Searches the style which label matches the given name.
	 * @param name The name of the style to find.
	 * @return The found style or empty.
	 * @since 4.0
	 */
	public static Optional<GridStyle> getStylefromName(final String name) {
		return Arrays.stream(values()).filter(v -> v.name().equals(name)).findFirst();
	}

	/**
	 * Searches the style which label matches the given label. Warning,
	 * labels change depending on the language.
	 * @param label The label of the style to find.
	 * @return The found style or empty.
	 * @since 4.0
	 */
	public static Optional<GridStyle> getStyleFromLabel(final String label) {
		return Arrays.stream(values()).filter(v -> v.getLabel().equals(label)).findFirst();
	}

	/**
	 * @return The label of the style.
	 * @since 3.0
	 */
	public abstract String getLabel();

	@Override
	public String toString() {
		return getLabel();
	}
}
