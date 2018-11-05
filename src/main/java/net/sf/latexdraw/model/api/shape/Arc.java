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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import net.sf.latexdraw.model.api.property.ArcProp;

/**
 * The API for an elliptic arc.
 * @author Arnaud BLOUIN
 */
public interface Arc extends PositionShape, ArcProp, ArrowableSingleShape {
	/**
	 * @return The coordinate of the start point of the arc.
	 */
	Point getStartPoint();

	/**
	 * @return The coordinate of the end point of the arc.
	 */
	Point getEndPoint();

	ObjectProperty<ArcStyle> arcStyleProperty();

	DoubleProperty angleStartProperty();

	DoubleProperty angleEndProperty();
}
