package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;
import java.util.List;

import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;
import net.sf.latexdraw.util.LNumber;

import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>SVG</code>.<br>
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
 * @since 0.1
 */
public class SVGSVGElement extends SVGElement {
	/**
	 * See {@link SVGElement#SVGElement()}
	 * @param n The node.
	 * @param owner The owner document.
	 * @throws MalformedSVGDocument If the tag is not valid.
	 */
	public SVGSVGElement(final SVGDocument owner, final Node n) throws MalformedSVGDocument {
		super(n);

		if(n==null || !n.getNodeName().endsWith(SVGElements.SVG_SVG) || owner==null)
			throw new IllegalArgumentException();

		if(!checkAttributes())
			throw new MalformedSVGDocument();

		ownerDocument = owner;
	}



	/**
	 *
	 * @param n The source node.
	 * @param e Will not be used.
	 * @throws MalformedSVGDocument If the document is not valid.
	 */
	public SVGSVGElement(final Node n, final SVGElement e) throws MalformedSVGDocument {
		super(n, e);

		if(n==null || !n.getNodeName().endsWith(SVGElements.SVG_SVG))
			throw new IllegalArgumentException();

		if(!checkAttributes())
			throw new MalformedSVGDocument();
	}



	/**
	 * Creates an empty SVG element.
	 * @param owner The owner document.
	 * @throws IllegalArgumentException If owner is null.
	 */
	public SVGSVGElement(final SVGDocument owner) {
		super();

		if(owner==null)
			throw new IllegalArgumentException();

		setAttribute("xmlns", SVGDocument.SVG_NAMESPACE);//$NON-NLS-1$
		ownerDocument = owner;

		setNodeName(SVGElements.SVG_SVG);
	}



	/**
	 * @return the meta element or null.
	 * @since 0.1
	 */
	public SVGMetadataElement getMeta() {
		SVGMetadataElement meta = null;

		for(int i=0, size=children.getLength(); i<size && meta==null; i++)
			if(children.item(i) instanceof SVGMetadataElement)
				meta = (SVGMetadataElement)children.item(i);

		return meta;
	}



	/**
	 * @return the defs element or null.
	 * @since 0.1
	 */
	public SVGDefsElement getDefs() {
		SVGDefsElement defs = null;

		for(int i=0, size=children.getLength(); i<size && defs==null; i++)
			if(children.item(i) instanceof SVGDefsElement)
				defs = (SVGDefsElement)children.item(i);

		return defs;
	}



	@Override
	public String toString() {
		SVGMetadataElement meta	= getMeta();
		SVGDefsElement defs		= getDefs();
		StringBuilder str = new StringBuilder().append('[').append("attributes=");//$NON-NLS-1$

		if(attributes!=null)
			str.append(attributes.toString()).append('\n');

		if(meta!=null)
			str.append(", meta=").append(meta.toString()).append('\n');//$NON-NLS-1$

		if(defs!=null)
			str.append(", defs=").append(defs.toString()).append('\n');//$NON-NLS-1$

		str.append(", children={");//$NON-NLS-1$

		if(children!=null) {
			final List<SVGElement> chiNodes = children.getNodes();
			final int size = chiNodes.size();

			for(int i=0; i<size-1; i++)
				str.append(chiNodes.get(i).toString()).append(',');

			if(size>0)
				str.append(chiNodes.get(chiNodes.size()-1).toString());
		}

		return str.append('}').append(']').toString();
	}



	@Override
	public boolean checkAttributes() {
		return true;
	}



	@Override
	public boolean enableRendering() {
		return !LNumber.INSTANCE.equals(getWidth(), 0.) && !LNumber.INSTANCE.equals(getHeight(), 0.);
	}



	/**
	 * @return The value of the X attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getX() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_X);
		double x;

		try { x = v==null ? 0. : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { x = 0.; }

		return x;
	}


	/**
	 * @return The value of the X attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getY() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_Y);
		double y;

		try { y = v==null ? 0. : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { y = 0.; }

		return y;
	}



	/**
	 * @return The value of the <code>width</code> attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getWidth() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_WIDTH);
		double width;

		try { width = v==null ? 1. : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { width = 1.; }

		return width;//FIXME: doit retourner 100%
	}


	/**
	 * @return The value of the <code>height</code> attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getHeight() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_HEIGHT);
		double height;

		try { height = v==null ? 1. : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { height = 1.; }

		return height; //FIXME: doit retourner 100%
	}


	/**
	 * @return The version of the SVG document or an empty string if it is not specified.
	 * @since 0.1
	 */
	public String getVersion() {
		return getAttribute(getUsablePrefix()+SVGAttributes.SVG_VERSION);
	}
}
