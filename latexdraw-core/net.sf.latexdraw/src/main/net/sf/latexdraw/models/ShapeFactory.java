/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models;

import net.sf.latexdraw.models.impl.LShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IShapeFactory;

/**
 * The current shape factory (a singleton) of the abstract factory pattern.
 */
public final class ShapeFactory extends LShapeFactory {
	public static final IShapeFactory INST = new LShapeFactory();

	private ShapeFactory() {
		super();
	}
}
