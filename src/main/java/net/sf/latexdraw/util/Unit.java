/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The different possible units used by the rulers.
 * @author Arnaud Blouin
 */
public enum Unit {
	/** Centimetre */
	CM {
		@Override
		public String getLabel(final ResourceBundle bundle) {
			return bundle.getString("XScale.cm"); //NON-NLS
		}
	},
	/** Inch */
	INCH {
		@Override
		public String getLabel(final ResourceBundle bundle) {
			return bundle.getString("XScale.inch"); //NON-NLS
		}
	};

	/**
	 * @param name The name to test.
	 * @return The unit corresponding to the given label, or nothing.
	 */
	public static Optional<Unit> getUnit(final String name) {
		return Arrays.stream(values()).filter(it -> it.name().equals(name)).findFirst();
	}

	/**
	 * @return The label of the unit.
	 */
	public abstract String getLabel(final ResourceBundle bundle);
}
