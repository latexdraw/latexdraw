package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;

import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>path</code>.<br>
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
 * 10/06/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 0.1
 */
public class SVGPathElement extends SVGElement {
	/**
	 * See {@link SVGElement#SVGElement(Node, SVGElement)}.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public SVGPathElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}



	/**
	 * Creates an empty SVG path element.
	 * @param owner The owner document.
	 * @since 0.1
	 */
	public SVGPathElement(final SVGDocument owner) {
		super(owner);

		setAttribute(SVGAttributes.SVG_D, "");//$NON-NLS-1$
		setNodeName(SVGElements.SVG_PATH);
	}



	/**
	 * @return True if the path is a unique line.
	 * @since 0.1
	 */
	public boolean isLine() {
		SVGPathSegList segList = getSegList();

		return segList.size()==2 && segList.get(0) instanceof SVGPathSegMoveto &&
				segList.get(1) instanceof SVGPathSegLineto ;
	}




	/**
	 * @return True if the path is composed of lines and has a 'close path' segment at the end.
	 * @since 0.1
	 */
	public boolean isLines() {
		SVGPathSegList segList = getSegList();

		if(segList.size()<3 || !(segList.get(0) instanceof SVGPathSegMoveto))
			return false;

		boolean ok = true;
		int i, size;

		for(i=1, size=segList.size()-1; i<size && ok; i++)
			if(!(segList.get(i) instanceof SVGPathSegLineto))
				ok = false;

		return ok;
	}



	/**
	 * @return True if the path is composed of lines and has a 'close path' segment at the end.
	 * @since 0.1
	 */
	public boolean isPolygon() {
		SVGPathSegList segList = getSegList();

		if(segList.isEmpty() || !(segList.get(0) instanceof SVGPathSegMoveto))
			return false;

		boolean ok = true;
		int i, size;

		for(i=1, size=segList.size()-1; i<size && ok; i++)
			if(!(segList.get(i) instanceof SVGPathSegLineto))
				ok = false;

		if(!(segList.get(segList.size()-1) instanceof SVGPathSegClosePath))
			ok = false;

		return ok;
	}



	/**
	 * The definition of the outline of a shape.
	 * @return The path data (in postscript) from the segList attribute.
	 * @since 0.1
	 */
	public String getPathData() {
		return getAttribute(getUsablePrefix()+SVGAttributes.SVG_D);
	}


	@Override
	public boolean checkAttributes() {
		return getPathData()!=null;
	}



	@Override
	public boolean enableRendering() {
		return getPathData().length()>0;
	}


	/**
	 * @return the segList.
	 * @since 0.1
	 */
	public SVGPathSegList getSegList() {
		final String path			 = getPathData();
		final SVGPathSegList segList = new SVGPathSegList();
		final SVGPathParser pp 		 = new SVGPathParser(path, segList);
		
		try{ pp.parse(); }
		catch(ParseException e) { throw new IllegalArgumentException(e.toString() + " But : \"" + path + "\" found."); } //$NON-NLS-1$ //$NON-NLS-2$
		
		return segList;
	}



	/**
	 * Sets the path data.
	 * @param path The path to set.
	 * @since 2.0.0
	 */
	public void setPathData(final SVGPathSegList path) {
		if(path!=null)
			setAttribute(SVGAttributes.SVG_D, path.toString());
	}
}
