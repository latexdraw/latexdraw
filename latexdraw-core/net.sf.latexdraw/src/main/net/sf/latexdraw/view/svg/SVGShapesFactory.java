/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;

/**
 * Creates SVG elements based on latexdraw.
 * @author Arnaud BLOUIN
 */
public final class SVGShapesFactory {
	/** The singleton. */
	public static final SVGShapesFactory INSTANCE = new SVGShapesFactory();

	/**
	 * Creates the factory.
	 */
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
		if(shape instanceof IGroup) return new LGroupSVGGenerator((IGroup)shape).toSVG(doc);
		if(shape instanceof IPlot) return new LPlotSVGGenerator((IPlot)shape).toSVG(doc);
		if(shape instanceof ISquare) return new LSquareSVGGenerator((ISquare)shape).toSVG(doc);
		if(shape instanceof IRectangle) return new LRectangleSVGGenerator((IRectangle)shape).toSVG(doc);
		if(shape instanceof IText) return new LTextSVGGenerator((IText)shape).toSVG(doc);
		if(shape instanceof ICircleArc) return new LCircleArcSVGGenerator((ICircleArc)shape).toSVG(doc);
		if(shape instanceof ICircle) return new LCircleSVGGenerator((ICircle)shape).toSVG(doc);
		if(shape instanceof IEllipse) return new LEllipseSVGGenerator((IEllipse)shape).toSVG(doc);
		if(shape instanceof ITriangle) return new LTriangleSVGGenerator((ITriangle)shape).toSVG(doc);
		if(shape instanceof IRhombus) return new LRhombusSVGGenerator((IRhombus)shape).toSVG(doc);
		if(shape instanceof IPolyline) return new LPolylinesSVGGenerator((IPolyline)shape).toSVG(doc);
		if(shape instanceof IPolygon) return new LPolygonSVGGenerator((IPolygon)shape).toSVG(doc);
		if(shape instanceof IBezierCurve) return new LBezierCurveSVGGenerator((IBezierCurve)shape).toSVG(doc);
		if(shape instanceof IAxes) return new LAxeSVGGenerator((IAxes)shape).toSVG(doc);
		if(shape instanceof IGrid) return new LGridSVGGenerator((IGrid)shape).toSVG(doc);
		if(shape instanceof IDot) return new LDotSVGGenerator((IDot)shape).toSVG(doc);
		if(shape instanceof IPicture) return new LPictureSVGGenerator((IPicture)shape).toSVG(doc);
		if(shape instanceof IFreehand) return new LFreeHandSVGGenerator((IFreehand)shape).toSVG(doc);
		return null;
	}
}
