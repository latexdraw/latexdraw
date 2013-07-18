package net.sf.latexdraw.glib.views.Java2D.interfaces;

import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * This interface defines a factory of views.<br>
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
public interface IViewsFactory {
	/**
	 * Creates a view from a shape.
	 * @param shape The shape used to create the view.
	 * @return The created view or null.
	 * @since 3.0
	 */
	IViewShape createView(final IShape shape);
}
