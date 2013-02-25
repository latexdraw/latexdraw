package net.sf.latexdraw.parsers.svg.parsers;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoQuadratic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;

/**
 * Defines a parser parsing Java path2D to generate an SVG path element.<br>
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
 * 08/02/11<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Path2D2SVGPath extends Shape2D2SVG<Path2D, SVGPathElement> {
	/**
	 * Creates the parser.
	 * @param path The Path2D to parse.
	 * @param document The SVG document that will be used to create SVG elements.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public Path2D2SVGPath(final Path2D path, final SVGDocument document) {
		super(path, document);
	}


	@Override
	public void parse() throws ParseException {
		final PathIterator pi 		= shape.getPathIterator(null);
		double[] coords 			= new double[6];
		final SVGPathSegList list 	= new SVGPathSegList();
		element 					= new SVGPathElement(document);

		while(!pi.isDone()) {
			switch(pi.currentSegment(coords)) {
				case PathIterator.SEG_CLOSE	: list.add(new SVGPathSegClosePath()); break;
				case PathIterator.SEG_LINETO: list.add(new SVGPathSegLineto(coords[0], coords[1], false)); break;
				case PathIterator.SEG_MOVETO: list.add(new SVGPathSegMoveto(coords[0], coords[1], false)); break;
				case PathIterator.SEG_CUBICTO: list.add(new SVGPathSegCurvetoCubic(coords[4], coords[5], coords[0], coords[1], coords[2], coords[3], false)); break;
				case PathIterator.SEG_QUADTO: list.add(new SVGPathSegCurvetoQuadratic(coords[2], coords[3], coords[0], coords[1], false)); break;
				default: throw new ParseException("Invalid Path2D element:" + pi.currentSegment(coords), -1);
			}
			pi.next();
		}

		element.setAttribute(SVGAttributes.SVG_D, list.toString());
		list.clear(); // Freeing the memory.
	}
}
