package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.bordel.BordelCollector;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRhombus;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.ISquare;
import net.sf.latexdraw.glib.models.interfaces.ITriangle;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;

/**
 * Creates SVG elements based on latexdraw.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public final class SVGShapesFactory {
	/** The singleton. */
	public static final SVGShapesFactory INSTANCE = new SVGShapesFactory();


	private SVGShapesFactory() {
		super();
	}


	/**
	 * Creates an SVG Element corresponding to the given shape.
	 * @param shape The shape used to determine which SVG element to create.
	 * @param doc The SVG document used to instantiate to SVG element.
	 * @return The created SVG element.
	 */
	public SVGElement createSVGElement(final IShape shape, final SVGDocument doc) {
		SVGElement elt = null;

		if(shape!=null && doc!=null)
			try {
				if(shape instanceof ISquare)
					elt = new LSquareSVGGenerator((ISquare)shape).toSVG(doc);
				else
					if(shape instanceof IRectangle)
					elt = new LRectangleSVGGenerator((IRectangle)shape).toSVG(doc);
				else if(shape instanceof IPolyline)
					elt = new LPolylinesSVGGenerator((IPolyline)shape).toSVG(doc);
				else if(shape instanceof IBezierCurve)
					elt = new LBezierCurveSVGGenerator((IBezierCurve)shape).toSVG(doc);
				else if(shape instanceof IGrid)
					elt = new LGridSVGGenerator((IGrid)shape).toSVG(doc);
				else if(shape instanceof IAxes)
					elt = new LAxeSVGGenerator((IAxes)shape).toSVG(doc);
				else if(shape instanceof IRhombus)
					elt = new LRhombusSVGGenerator((IRhombus)shape).toSVG(doc);
				else if(shape instanceof ITriangle)
					elt = new LTriangleSVGGenerator((ITriangle)shape).toSVG(doc);
				else if(shape instanceof IDot)
					elt = new LDotSVGGenerator((IDot)shape).toSVG(doc);
//				else if(shape instanceof IFreehand)
//					elt = new LFreeHandSVGGenerator((IFreehand)shape).toSVG(doc);
//				else if(shape instanceof IArc)
//					elt = new LArcSVGGenerator((IArc)shape).toSVG(doc);
				else if(shape instanceof ICircle)
					elt = new LCircleSVGGenerator((ICircle)shape).toSVG(doc);
				else if(shape instanceof IEllipse)
					elt = new LEllipseSVGGenerator<IEllipse>((IEllipse)shape).toSVG(doc);
				else if(shape instanceof IGroup)
					elt = new LGroupSVGGenerator((IGroup)shape).toSVG(doc);
//				else if(shape instanceof IPicture)
//					elt = new LPictureSVGGenerator((IPicture)shape).toSVG(doc);
//				else if(shape instanceof IText)
//					elt = new LTextSVGGenerator((IText)shape).toSVG(doc);
				else if(shape instanceof IPolygon)
					elt = new LPolygonSVGGenerator((IPolygon)shape).toSVG(doc);
			}
			catch(final Exception e) { BordelCollector.INSTANCE.add(e); }

		return elt;
	}
}
