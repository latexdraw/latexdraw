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
package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * This class must be used to create SVG elements.
 * @author Arnaud BLOUIN
 */
public final class SVGElementsFactory {
	/** The singleton. */
	public static final SVGElementsFactory INSTANCE = new SVGElementsFactory();


	private SVGElementsFactory() {
		super();
	}


	/**
	 * This factory can be used in order to create an SVG element according to the given SVG node (tag).
	 * @param src The node that will be used to create the SVG element.
	 * @param parent The parent of the element to create.
	 * @return The created SVG element.
	 * @since 0.1
	 */
	public SVGElement createSVGElement(final Node src, final SVGElement parent) {
		if(src == null || parent == null) {
			return null;
		}

		try {
			final String name = src.getNodeName();

			if(name == null) {
				return null;
			}

			final String pref = src.getNodeName().contains(":") ? src.getNodeName().substring(0, src.getNodeName().indexOf(':')) : null;
			final NamedNodeMap nnm = src.getAttributes();
			final String ns;

			if(nnm == null) {
				ns = parent.lookupNamespaceURI(pref);
			}else {
				final Node xmlnsNode = nnm.getNamedItem(pref == null ? "xmlns" : "xmlns:" + pref); //NON-NLS

				if(xmlnsNode == null) {
					ns = parent.lookupNamespaceURI(pref);
				}else {
					ns = xmlnsNode.getNodeValue();
				}
			}

			if(ns == null || name.endsWith("#text") || name.endsWith("#comment")) { //NON-NLS
				return null;
			}

			if(SVGDocument.SVG_NAMESPACE.equals(ns)) {
				return createElement(name, src, parent);
			}
			return new OtherNSElement(src, parent);
		}catch(final MalformedSVGDocument | ParseException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			return null;
		}
	}

	private SVGElement createElement(final String name, final Node src, final SVGElement parent) throws MalformedSVGDocument, ParseException {
		if(name.endsWith(SVGElements.SVG_SVG)) {
			return new SVGSVGElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_G)) {
			return new SVGGElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_RECT)) {
			return new SVGRectElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_ELLIPSE)) {
			return new SVGEllipseElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_CIRCLE)) {
			return new SVGCircleElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_POLY_LINE)) {
			return new SVGPolyLineElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_LINE)) {
			return new SVGLineElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_POLYGON)) {
			return new SVGPolygonElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_METADATA)) {
			return new SVGMetadataElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_DEFS)) {
			return new SVGDefsElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_PATTERN)) {
			return new SVGPatternElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_LINEAR_GRADIENT)) {
			return new SVGLinearGradientElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_STOP)) {
			return new SVGStopElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_PATH)) {
			return new SVGPathElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_MARKER)) {
			return new SVGMarkerElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_TEXT)) {
			return new SVGTextElement(src, parent);
		}
		if(name.endsWith(SVGElements.SVG_IMAGE)) {
			return new SVGImageElement(src, parent);
		}

		return null;
	}
}
