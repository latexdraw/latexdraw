/*
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2017 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 */
package net.sf.latexdraw.util;

/**
 * The different page formats.
 */
public enum Page {
	/** The US letter format. */
	USLETTER {
		@Override
		public double getWidth() {
			return 21.6;
		}

		@Override
		public double getHeight() {
			return 27.9;
		}
	};

	/**
	 * @return The width of the page in CM.
	 * @since 3.1
	 */
	public abstract double getWidth();

	/**
	 * @return The height of the page in CM.
	 * @since 3.1
	 */
	public abstract double getHeight();
}
