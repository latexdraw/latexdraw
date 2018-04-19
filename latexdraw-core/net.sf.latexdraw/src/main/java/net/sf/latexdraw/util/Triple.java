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
package net.sf.latexdraw.util;

/**
 * An immutable triple.
 * @param <A> The type of the first value.
 * @param <B> The type of the second value.
 * @param <C> The type of the third value.
 * @author Arnaud Blouin
 */
public class Triple<A, B, C> extends Tuple<A, B> {
	public final C c;

	public Triple(final A a, final B b, final C c) {
		super(a, b);
		this.c = c;
	}

	@Override
	public String toString() {
		return "Triple{" + "a=" + a + ", b=" + b + ", c=" + c + '}'; //NON-NLS
	}
}
