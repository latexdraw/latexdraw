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
package net.sf.latexdraw.view.svg;

import java.util.List;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.util.LNamespace;
import org.w3c.dom.NodeList;

/**
 * An SVG generator for a group of shapes.
 * @author Arnaud BLOUIN
 */
class SVGGroup extends SVGShape<IGroup> {
	private final SVGShapeProducer shapeProducer;

	/**
	 * Creates an SVG generator from IGroup instance.
	 * @param group The group of shapes that will be converted.
	 */
	SVGGroup(final IGroup group, final SVGShapeProducer shapeProducer) {
		super(group);
		this.shapeProducer = shapeProducer;
	}


	/**
	 * Creates an SVG generator from an SVGGElement that should corresponds to a
	 * group of latexdraw shapes.
	 * @param elt The group of latexdraw shapes in the SVG format supplemented with latexdraw
	 * information.
	 * @param withTransformation True: the set of transformations that concerned the given SVG element will be applied to the shape.
	 * @throws IllegalArgumentException If the given SVGGElement is null or not valid.
	 */
	SVGGroup(final SVGGElement elt, final boolean withTransformation, final SVGShapeProducer shapeProducer) {
		this(ShapeFactory.INST.createGroup(), shapeProducer);

		if(elt == null) {
			throw new IllegalArgumentException();
		}

		final NodeList nodeList = elt.getChildNodes();
		IShape sh;

		if(nodeList.getLength() < 2) {
			throw new IllegalArgumentException();
		}

		for(int i = 0, size = nodeList.getLength(); i < size; i++) {
			sh = shapeProducer.createShape((SVGElement) nodeList.item(i), withTransformation);

			if(sh != null) {
				shape.addShape(sh);
			}
		}
	}


	@Override
	SVGElement toSVG(final SVGDocument doc) {
		if(doc == null) {
			return null;
		}

		if(!shape.isEmpty()) {
			final SVGElement root = new SVGGElement(doc);
			final List<IShape> shapes = shape.getShapes();

			root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_GROUP);
			root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

			for(final IShape f : shapes) {
				root.appendChild(shapeProducer.createSVGElement(f, doc));
			}

			return root;
		}

		return null;
	}
}
