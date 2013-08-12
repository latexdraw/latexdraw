package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;

import net.sf.latexdraw.badaboom.BadaboomCollector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * This class must be used to create SVG elements.<br>
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
 * 09/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
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
		if(src==null || parent==null)
			return null;

		try {
			String name = src.getNodeName();

			if(name==null)
				return null;

			String pref = src.getNodeName().contains(":") ? src.getNodeName().substring(0, src.getNodeName().indexOf(':')) : null;//$NON-NLS-1$
			NamedNodeMap nnm = src.getAttributes();
			String ns;

			if(nnm==null)
				ns = parent.lookupNamespaceURI(pref);
			else {
				Node xmlnsNode = nnm.getNamedItem(pref==null ? "xmlns" : "xmlns:"+pref);//$NON-NLS-1$//$NON-NLS-2$

				if(xmlnsNode==null)
					ns = parent.lookupNamespaceURI(pref);
				else
					ns = xmlnsNode.getNodeValue();
			}

			if(ns==null || name.endsWith("#text") || name.endsWith("#comment"))//$NON-NLS-1$//$NON-NLS-2$
				return null;

			if(SVGDocument.SVG_NAMESPACE.equals(ns)) {
				if(name.endsWith(SVGElements.SVG_SVG))
					return new SVGSVGElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_G))
					return new SVGGElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_RECT))
					return new SVGRectElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_ELLIPSE))
					return new SVGEllipseElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_CIRCLE))
					return new SVGCircleElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_POLY_LINE))
					return new SVGPolyLineElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_LINE))
					return new SVGLineElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_POLYGON))
					return new SVGPolygonElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_METADATA))
					return new SVGMetadataElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_DEFS))
					return new SVGDefsElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_PATTERN))
					return new SVGPatternElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_LINEAR_GRADIENT))
					return new SVGLinearGradientElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_STOP))
					return new SVGStopElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_PATH))
					return new SVGPathElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_MARKER))
					return new SVGMarkerElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_TEXT))
					return new SVGTextElement(src, parent);
				else if(name.endsWith(SVGElements.SVG_IMAGE))
					return new SVGImageElement(src, parent);
			}
			else
				return new OtherNSElement(src, parent);
		}
		catch(MalformedSVGDocument | ParseException ex) { BadaboomCollector.INSTANCE.add(ex); return null; }

		return null;
	}
}
