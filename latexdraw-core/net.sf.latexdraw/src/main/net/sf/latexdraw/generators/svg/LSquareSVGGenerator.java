package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ISquare;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for a square.<br>
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
 * 11/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class LSquareSVGGenerator extends LRectangleSVGGenerator {
	/**
	 * Creates an SVG generator for squares.
	 * @param square The source square to convert in SVG.
	 * @since 3.0
	 */
	protected LSquareSVGGenerator(final ISquare square) {
		super(square);
	}


	/**
	 * Creates an SVG generator for squares.
	 * @param elt The SVG element to convert in latexdraw.
	 * @since 3.0
	 */
	protected LSquareSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}



	/**
	 * Creates a square from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LSquareSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createSquare(false));
		initRectangle(elt, withTransformation);
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			return null;

		SVGElement root = super.toSVG(doc);
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_SQUARE);

		return root;
	}
}
