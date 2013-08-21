package net.sf.latexdraw.glib.models.interfaces;

/**
 * This class contains the factory that must be used to create shape instances.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 01/04/2011<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public final class DrawingTK {
	private DrawingTK() {
		super();
	}
	
	/** The factory. */
	private static IShapeFactory factory = null;

	/**
	 * Sets the new factory.
	 * @param factory The new factory.
	 * @since 3.0
	 */
	public static void setFactory(final IShapeFactory factory) {
		DrawingTK.factory = factory;
	}

	/**
	 * @return The current factory. Can be null.
	 * @since 3.0
	 */
	public static IShapeFactory getFactory() {
		return factory;
	}
}
