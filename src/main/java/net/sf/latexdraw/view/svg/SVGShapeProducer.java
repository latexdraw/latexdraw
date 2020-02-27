/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.parser.svg.SVGDocument;
import net.sf.latexdraw.parser.svg.SVGElement;

public interface SVGShapeProducer {
	/**
	 * Creates a IShape instance using the given SVGElement.
	 * @param elt The SVGElement to parse.
	 * @return The created IShape instance or null.
	 */
	Shape createShape(final SVGElement elt);

	/**
	 * Creates a IShape instance using the given SVGElement.
	 * @param elt The SVGElement to parse.
	 * @param withTransformations True: the set of transformations that concerned the given SVG element will be applied to the shape.
	 * @return The created IShape instance or null.
	 */
	Shape createShape(final SVGElement elt, final boolean withTransformations);

	/**
	 * Creates an SVG Element corresponding to the given shape.
	 * @param shape The shape used to determine which SVG element to create.
	 * @param doc The SVG document used to instantiate to SVG element.
	 * @return The created SVG element.
	 */
	SVGElement createSVGElement(final Shape shape, final SVGDocument doc);
}
