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
package net.sf.latexdraw.util;

import java.util.Arrays;
import java.util.Optional;

/**
 * The different page formats.
 */
public enum Page {
	/** The US letter format. */
	USLETTER(21.6, 27.9),
	/** horizontal format */
	HORIZONTAL(16, 12);

	final double width;
	final double height;

	Page(final double w, final double h) {
		width = w;
		height = h;
	}

	/**
	 * @return The width of the page in CM.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return The height of the page in CM.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param name The name of the page to search for.
	 * @return The found page or nothing.
	 */
	public static Optional<Page> getPage(final String name) {
		return Arrays.stream(values()).filter(it -> it.name().equals(name)).findFirst();
	}
}
