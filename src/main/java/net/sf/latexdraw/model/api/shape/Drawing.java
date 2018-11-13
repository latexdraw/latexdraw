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
package net.sf.latexdraw.model.api.shape;

import java.util.List;
import javafx.beans.property.StringProperty;
import net.sf.latexdraw.model.api.property.SetShapesProp;
import org.jetbrains.annotations.NotNull;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;

/**
 * The API for drawings that contains a set of shapes and a set of selected shapes.
 * @author Arnaud BLOUIN
 */
public interface Drawing extends SetShapesProp, Modifiable, Reinitialisable {
	/**
	 * @return The group that contains the selected shape. Cannot be null.
	 */
	@NotNull Group getSelection();

	/**
	 * Selects the given shapes and unselect the already selected shapes.
	 * @param shapes The shapes to select. Cannot be null.
	 */
	void setSelection(final @NotNull List<Shape> shapes);

	void setTitle(final @NotNull String title);

	@NotNull StringProperty titleProperty();
}
