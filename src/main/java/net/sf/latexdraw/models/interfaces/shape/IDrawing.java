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
package net.sf.latexdraw.models.interfaces.shape;

import java.util.List;
import net.sf.latexdraw.models.interfaces.prop.ISetShapesProp;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;

/**
 * The API for drawings that contains a set of shapes and a set of selected shapes.
 * @author Arnaud BLOUIN
 */
public interface IDrawing extends ISetShapesProp, Modifiable, Reinitialisable {
	/**
	 * @return The group that contains the selected shape. Cannot be null.
	 */
	IGroup getSelection();

	/**
	 * Selects the given shapes and unselect the already selected shapes.
	 * @param shapes The shapes to select. Cannot be null.
	 * @throws NullPointerException when shapes is null.
	 */
	void setSelection(final List<IShape> shapes);
}
