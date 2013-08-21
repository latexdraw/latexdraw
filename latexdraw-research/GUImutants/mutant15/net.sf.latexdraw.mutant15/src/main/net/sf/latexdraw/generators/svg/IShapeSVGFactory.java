package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.parsers.svg.*;
import net.sf.latexdraw.util.LNamespace;

/**
 * Creates IShape instances according to the given SVG element.<br>
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
 * 09/21/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public final class IShapeSVGFactory {
	/** The singleton. */
	public static final IShapeSVGFactory INSTANCE = new IShapeSVGFactory();


	private IShapeSVGFactory() {
		super();
	}


	/**
	 * Creates a IShape instance using the given SVGElement.
	 * @param elt The SVGElement to parse.
	 * @return The created IShape instance or null.
	 * @since 3.0
	 */
	public IShape createShape(final SVGElement elt) {
		return createShape(elt, true);
	}


	/**
	 * Creates a IShape instance using the given SVGElement.
	 * @param elt The SVGElement to parse.
	 * @param withTransformations True: the set of transformations that concerned the given SVG element will be applied to the shape.
	 * @return The created IShape instance or null.
	 * @since 3.0
	 */
	public IShape createShape(final SVGElement elt, final boolean withTransformations) {
		if(elt==null || !elt.enableRendering())
			return null;

		try {
			if(elt instanceof SVGRectElement)
				return new LRectangleSVGGenerator((SVGRectElement)elt).getShape();
			else if(elt instanceof SVGEllipseElement)
				return new LEllipseSVGGenerator<>((SVGEllipseElement)elt).getShape();
			else if(elt instanceof SVGCircleElement)
				return new LCircleSVGGenerator((SVGCircleElement)elt).getShape();
			else if(elt instanceof SVGPolygonElement)
				return new LPolygonSVGGenerator((SVGPolygonElement)elt).getShape();
			else if(elt instanceof SVGPolyLineElement)
				return new LPolylinesSVGGenerator((SVGPolyLineElement)elt).getShape();
			else if(elt instanceof SVGImageElement)
				return new LPictureSVGGenerator((SVGImageElement)elt).getShape();
			else if(elt instanceof SVGLineElement)
				return new LPolylinesSVGGenerator((SVGLineElement)elt).getShape();
			else if(elt instanceof SVGTextElement)
				return new LTextSVGGenerator((SVGTextElement)elt).getShape();
			else if(elt instanceof SVGPathElement) {
				SVGPathElement p = (SVGPathElement)elt;

				if(p.isPolygon())
					return new LPolygonSVGGenerator((SVGPathElement)elt).getShape();

				if(p.isLines() || p.isLine())
					return new LPolylinesSVGGenerator((SVGPathElement)elt).getShape();
			}
			else
				if(elt instanceof SVGGElement) {// If we have a group of shapes or a latexdraw shape.
					String ltdPref 	= elt.lookupPrefixUsable(LNamespace.LATEXDRAW_NAMESPACE_URI);
					String type 	= elt.getAttribute(ltdPref+LNamespace.XML_TYPE);

					// If we have a group of shapes.
					if(type==null || type.length()==0 || LNamespace.XML_TYPE_GROUP.equals(type)) {
						switch(elt.getChildNodes().getLength()) {
							case 0:
								return null;
							case 1:
								return createShape((SVGElement)elt.getChildNodes().item(0));
							default:
								return new LGroupSVGGenerator((SVGGElement)elt, withTransformations).getShape();
						}
					}

					// Otherwise, it should be a latexdraw shape saved in an SVG document.
					if(type.equals(LNamespace.XML_TYPE_RECT))
						return new LRectangleSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_ELLIPSE))
						return new LEllipseSVGGenerator<>((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_CIRCLE))
						return new LCircleSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_POLYGON))
						return new LPolygonSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_SQUARE))
						return new LSquareSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_TRIANGLE))
						return new LTriangleSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_RHOMBUS))
						return new LRhombusSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_JOINED_LINES))
						return new LPolylinesSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_FREEHAND))
						return new LFreeHandSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_LINE))
						return new LPolylinesSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_BEZIER_CURVE))
						return new LBezierCurveSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_GRID))
						return new LGridSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_AXE))
						return new LAxeSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_TEXT))
						return new LTextSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_GROUP))
						return new LGroupSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_DOT))
						return new LDotSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_ARC))
						return new LCircleArcSVGGenerator((SVGGElement)elt, withTransformations).getShape();
					else if(type.equals(LNamespace.XML_TYPE_PICTURE))
						return new LPictureSVGGenerator((SVGGElement)elt, withTransformations).getShape();
				}
		}
		catch(final Exception ex) { BadaboomCollector.INSTANCE.add(ex); }

		return null;
	}
}
