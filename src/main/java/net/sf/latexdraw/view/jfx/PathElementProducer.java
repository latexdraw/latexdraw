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
package net.sf.latexdraw.view.jfx;

import java.util.Optional;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;

public interface PathElementProducer {
	void flushPathElement(final PathElement elt);

	Optional<PathElement> createPathElement(final PathElement elt);

	Path clonePath(final Path path);

	LineTo createLineTo(final double x, final double y);

	MoveTo createMoveTo(final double x, final double y);

	ClosePath createClosePath();

	CubicCurveTo createCubicCurveTo(final double controlX1, final double controlY1, final double controlX2, final double controlY2, final double x, final double y);
}
