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
package net.sf.latexdraw.actions;

import net.sf.latexdraw.view.GridStyle;

/**
 * The different properties of the magnetic grid that can be modified.
 * @author Arnaud Blouin
 */
public enum GridProperties {
	/** Modification of the style of the grid. */
	STYLE {
		@Override
		public boolean isValidValue(final Object object) {
			return object instanceof GridStyle;
		}
	},
	/** Defines if the grid must be magnetic. */
	MAGNETIC {
		@Override
		public boolean isValidValue(final Object object) {
			return object instanceof Boolean;
		}
	},
	/** Modification of the spacing between the lines of the grid. */
	GRID_SPACING {
		@Override
		public boolean isValidValue(final Object object) {
			return object instanceof Integer;
		}
	};

	/**
	 * @param object The value to test.
	 * @return True: if the type of the given value matches the type of the property.
	 * @since 3.0
	 */
	public abstract boolean isValidValue(final Object object);
}
