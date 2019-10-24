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
package net.sf.latexdraw.model;

import net.sf.latexdraw.model.api.shape.Factory;
import net.sf.latexdraw.model.impl.ShapeFactoryImpl;
import org.jetbrains.annotations.NotNull;

/**
 * The current shape factory (a singleton) of the abstract factory pattern.
 * @author Arnaud Blouin
 */
public final class ShapeFactory extends ShapeFactoryImpl {
	public static final @NotNull Factory INST = new ShapeFactory();

	private ShapeFactory() {
		super();
	}
}
