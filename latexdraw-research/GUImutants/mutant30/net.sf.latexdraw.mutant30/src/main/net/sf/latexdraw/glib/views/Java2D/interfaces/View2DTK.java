package net.sf.latexdraw.glib.views.Java2D.interfaces;

/**
 * This class permits the access to the current view factory.<br>
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
 * 11/18/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public final class View2DTK {
	private View2DTK() {
		super();
	}
	
	/** The current view factory. */
	private static IViewsFactory factory = null;
	
	/**
	 * Sets the view factory.
	 * @param factory The new factory. Can be null.
	 * @since 3.0
	 */
	public static void setFactory(final IViewsFactory factory) {
		View2DTK.factory = factory;
	}
	
	/**
	 * @return The current factory. Can be null.
	 * @since 3.0
	 */
	public static IViewsFactory getFactory() {
		return factory;
	}
}
