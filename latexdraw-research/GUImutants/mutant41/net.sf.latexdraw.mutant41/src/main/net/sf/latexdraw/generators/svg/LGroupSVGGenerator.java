package net.sf.latexdraw.generators.svg;

import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.util.LNamespace;

import org.w3c.dom.NodeList;

/**
 * Defines a SVG generator for a group of shapes.<br>
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
class LGroupSVGGenerator extends LShapeSVGGenerator<IGroup> {
	/**
	 * Creates an SVG generator from IGroup instance.
	 * @param group The group of shapes that will be converted.
	 * @since 3.0
	 */
	protected LGroupSVGGenerator(final IGroup group) {
		super(group);
	}


	/**
	 * Creates an SVG generator from an SVGGElement that should corresponds to a
	 * group of latexdraw shapes.
	 * @param group The group of latexdraw shapes in the SVG format supplemented with latexdraw
	 * information.
	 * @since 3.0
	 */
	protected LGroupSVGGenerator(final SVGGElement group) {
		this(group, true);
	}



	/**
	 * Creates an SVG generator from an SVGGElement that should corresponds to a
	 * group of latexdraw shapes.
	 * @param elt The group of latexdraw shapes in the SVG format supplemented with latexdraw
	 * information.
	 * @param withTransformation True: the set of transformations that concerned the given SVG element will be applied to the shape.
	 * @throws IllegalArgumentException If the given SVGGElement is null or not valid.
	 * @since 3.0
	 */
	public LGroupSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createGroup(true));

		if(elt==null)
			throw new IllegalArgumentException();

		final NodeList nodeList	= elt.getChildNodes();
		IShape sh;

		if(nodeList.getLength()<2)
			throw new IllegalArgumentException();

		for(int i=0, size=nodeList.getLength(); i<size; i++) {
			sh = IShapeSVGFactory.INSTANCE.createShape((SVGElement)nodeList.item(i), withTransformation);

			if(sh!=null)
				shape.addShape(sh);
		}

		setNumber(elt);
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			return null;

		if(!shape.isEmpty()) {
			 final SVGElement root 		= new SVGGElement(doc);
			 final List<IShape> shapes 	= shape.getShapes();

			 root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_GROUP);
			 root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

			 for(IShape f : shapes)
				 root.appendChild(SVGShapesFactory.INSTANCE.createSVGElement(f, doc));

			 return root;
		}

		return null;
	}
}
