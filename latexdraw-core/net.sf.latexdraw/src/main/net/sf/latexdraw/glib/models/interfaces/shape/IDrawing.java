package net.sf.latexdraw.glib.models.interfaces.shape;

import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.prop.ISetShapesProp;

import org.malai.presentation.AbstractPresentation;

/**
 * Defines an interface of a drawing that contains a set of shapes
 * and a set of selected shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 02/22/2010<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IDrawing extends ISetShapesProp, AbstractPresentation {
	/**
	 * @return The group that contains the selected shape. Cannot be null.
	 * @since 3.0
	 */
	IGroup getSelection();

	/**
	 * Selects the given shapes and unselect the already selected shapes.
	 * @param shapes The shapes to select. Cannot be null.
	 * @throws NullPointerException when shapes is null.
	 * @since 3.0
	 */
	void setSelection(final List<IShape> shapes);
}
