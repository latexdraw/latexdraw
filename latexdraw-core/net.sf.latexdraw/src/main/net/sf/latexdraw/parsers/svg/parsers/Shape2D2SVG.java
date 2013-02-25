package net.sf.latexdraw.parsers.svg.parsers;

import java.awt.Shape;
import java.util.Objects;

import net.sf.latexdraw.parsers.Parser;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;

/**
 * This abstract converter converts a Java Shape into an SVG element.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 08/03/11<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @param <S> The type of the shape to convert.
 * @param <T> The type of the SVG element to create.
 */
public abstract class Shape2D2SVG<S extends Shape, T extends SVGElement> implements Parser {
	/** The Java shape to convert. */
	protected S shape;

	/** The generated SVG element. */
	protected T element;

	/** The SVG document that will be used to create SVG elements. */
	protected SVGDocument document;

	/**
	 * Creates the converter.
	 * @param shape The shape to convert.
	 * @param document The document used to create SVG elements.
	 * @since 3.0
	 */
	public Shape2D2SVG(final S shape, final SVGDocument document) {
		super();
		this.document 	= Objects.requireNonNull(document);
		this.shape 		= Objects.requireNonNull(shape);
	}


	/**
	 * @return The generated SVG element.
	 * @since 3.0
	 */
	public T getSVGElement() {
		return element;
	}


	/**
	 * Sets the SVG document that will be used to create SVG elements.
	 * @param document The new SVG document.
	 * @since 3.0
	 */
	public void setDocument(final SVGDocument document) {
		if(document!=null)
			this.document = document;
	}


	/**
	 * @param shape The shape to convert. Must not be null.
	 * @since 3.0
	 */
	public void setShape(final S shape) {
		if(shape!=null)
			this.shape = shape;
	}
}
