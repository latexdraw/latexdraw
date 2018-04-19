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
 * An immutable tuple.
 * @param <A> The type of the first value.
 * @param <B> The type of the second value.
 * @author Arnaud Blouin
 */
public class Tuple<A, B> {
	public final A a;
	public final B b;

	public Tuple(final A a, final B b) {
		super();
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return "Tuple{" + "a=" + a + ", b=" + b + '}'; //NON-NLS
	}
}
