package net.sf.latexdraw.generators.svg;

import java.awt.geom.Arc2D;

import net.sf.latexdraw.glib.models.interfaces.Arcable.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegArc;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for an arc.<br>
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
 * 11/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class LArcSVGGenerator extends LEllipseSVGGenerator<IArc> {
	/**
	 * Creates a generator of SVG arc.
	 * @param shape The arc shape used for the generation.
	 * @throws IllegalArgumentException If arc is null.
	 * @since 2.0
	 */
	protected LArcSVGGenerator(final IArc shape)	{
		super(shape);
	}


	/**
	 * Creates an arc from a G SVG element.
	 * @param elt The G SVG element used for the creation of an arc.
	 * @throws IllegalArgumentException If the given element is null.
	 * @since 2.0
	 */
	protected LArcSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}



	/**
	 * Creates an arc from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @param withTransformation If true, the SVG transformations will be applied.
	 * @since 2.0.0
	 */
	protected LArcSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createArc(true));

		SVGElement elt2 = getLaTeXDrawElement(elt, null);
		IArrow arr1	= shape.getArrowAt(0);
		IArrow arr2	= shape.getArrowAt(1);

		if(elt==null || elt2==null || !(elt2 instanceof SVGPathElement))
			throw new IllegalArgumentException();

		SVGPathElement main = (SVGPathElement)elt2;
		SVGPathSegList l = main.getSegList();
		double sx, sy;

		if(l.size()<2 && !(l.get(0) instanceof SVGPathSegMoveto) && !(l.get(1) instanceof SVGPathSegArc))
			throw new IllegalArgumentException();

		sx = ((SVGPathSegMoveto)l.get(0)).getX();
		sy = ((SVGPathSegMoveto)l.get(0)).getY();
		Arc2D arc = ((SVGPathSegArc)l.get(1)).getArc2D(sx, sy);

		shape.setAngleStart(Math.toRadians(arc.getAngleStart())%(Math.PI*2));
		shape.setAngleEnd(Math.toRadians(arc.getAngleExtent()+arc.getAngleStart())%(Math.PI*2));
		shape.setPosition(arc.getMinX(), arc.getMaxY());
		shape.setWidth(arc.getMaxX()-arc.getMinX());
		shape.setHeight(arc.getMaxY()-arc.getMinY());

		if(l.size()>2)
			if(l.get(2) instanceof SVGPathSegClosePath)
				shape.setArcStyle(ArcStyle.CHORD);
			else
				if(l.size()==4 && l.get(2) instanceof SVGPathSegLineto && l.get(3) instanceof SVGPathSegClosePath)
					shape.setArcStyle(ArcStyle.WEDGE);

		shape.setShowPts(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHOW_PTS)!=null);
		shape.update();

		setNumber(elt);
		setSVGParameters(main);
		setSVGArrow(arr1, main.getAttribute(main.getUsablePrefix()+SVGAttributes.SVG_MARKER_START), main);
		setSVGArrow(arr2, main.getAttribute(main.getUsablePrefix()+SVGAttributes.SVG_MARKER_END), main);
		homogeniseArrows(arr1, arr2);
		setSVGLatexdrawParameters(elt);
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

		if(withTransformation)
			applyTransformations(elt);
	}


	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null || doc.getFirstChild().getDefs()==null)
			return null;

		SVGDefsElement defs  = doc.getFirstChild().getDefs();
		double rotationAngle = shape.getRotationAngle();
		double startAngle    = shape.getAngleStart()%(2.*Math.PI);
		double endAngle      = shape.getAngleEnd()%(2.*Math.PI);
		ArcStyle type 		 = shape.getArcStyle();
        SVGElement root 	 = new SVGGElement(doc);
        IPoint start 		 = shape.getStartPoint();
        IPoint end 			 = shape.getEndPoint();
        double radius 		 = shape.getRx();
        boolean largeArcFlag = startAngle>endAngle ? startAngle-endAngle>=Math.PI : endAngle-startAngle>Math.PI;
        SVGPathSegList path  = new SVGPathSegList();
        SVGElement elt;

        root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_ARC);
        root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

        path.add(new SVGPathSegMoveto(start.getX(), start.getY(), false));
        path.add(new SVGPathSegArc(end.getX(), end.getY(), radius, radius, 0, largeArcFlag, startAngle>endAngle, false));

        if(type==ArcStyle.CHORD)
        	path.add(new SVGPathSegClosePath());
        else
        	if(type==ArcStyle.WEDGE) {
        		final IPoint gravityCenter = shape.getGravityCentre();
        		path.add(new SVGPathSegLineto(gravityCenter.getX(), gravityCenter.getY(), false));
        		path.add(new SVGPathSegClosePath());
        	}

        if(shape.hasShadow())  {
        	SVGElement shad = new SVGPathElement(doc);

        	shad.setAttribute(SVGAttributes.SVG_D, path.toString());
        	setSVGShadowAttributes(shad, true);
        	root.appendChild(shad);
        	setSVGArrow(shad, 0, true, doc, defs);
			setSVGArrow(shad, 1, true, doc, defs);
        }

        // The background of the borders must be filled is there is a shadow.
        if(shape.hasShadow() && !shape.getLineStyle().equals(LineStyle.NONE)) {
            elt = new SVGPathElement(doc);
            elt.setAttribute(SVGAttributes.SVG_D, path.toString());
            setSVGBorderBackground(elt, root);
        }

        elt = new SVGPathElement(doc);
        elt.setAttribute(SVGAttributes.SVG_D, path.toString());
        root.appendChild(elt);

        setSVGRotationAttribute(root);
        setSVGAttributes(doc, elt, true);
        elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE +':'+ LNamespace.XML_ROTATION, String.valueOf(rotationAngle));

		setSVGArrow(elt, 0, false, doc, defs);
		setSVGArrow(elt, 1, false, doc, defs);

        if(shape.isShowPts())
        	root.appendChild(getShowPointsElement(doc));

        return root;
	}


	/**
	 * Creates an SVG g element that contains the 'show points' plotting.
	 * @param doc The owner document.
	 * @return The created g element or null if the shape has not the 'show points' option activated.
	 * @since 2.0.0
	 */
	protected SVGGElement getShowPointsElement(final SVGDocument doc) {
		if(!shape.isShowPts() || doc==null)
			return null;

		SVGGElement showPts = new SVGGElement(doc);
		double thickness	= shape.getThickness()/2.;

		showPts.setAttribute(new StringBuffer(LNamespace.LATEXDRAW_NAMESPACE).append(':').append(
				LNamespace.XML_TYPE).toString(), LNamespace.XML_TYPE_SHOW_PTS);

		showPts.appendChild(getShowPointsLine(doc, thickness, shape.getLineColour(), shape.getGravityCentre(),
				shape.getStartPoint(), shape.getDashSepBlack(), shape.getDashSepWhite(), false, 1, 0));
		showPts.appendChild(getShowPointsLine(doc, thickness, shape.getLineColour(), shape.getGravityCentre(),
				shape.getEndPoint(), shape.getDashSepBlack(), shape.getDashSepWhite(), false, 1, 0));

		return showPts;
	}
}
