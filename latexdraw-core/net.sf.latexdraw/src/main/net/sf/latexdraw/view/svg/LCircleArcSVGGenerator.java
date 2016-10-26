package net.sf.latexdraw.view.svg;

import java.awt.geom.Arc2D;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
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
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
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
class LCircleArcSVGGenerator extends LShapeSVGGenerator<ICircleArc> {
	/**
	 * Creates a generator of SVG arc.
	 * @param shape The arc shape used for the generation.
	 * @throws IllegalArgumentException If arc is null.
	 * @since 2.0
	 */
	protected LCircleArcSVGGenerator(final ICircleArc shape)	{
		super(shape);
	}


	/**
	 * Creates an arc from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @param withTransformation If true, the SVG transformations will be applied.
	 * @since 2.0.0
	 */
	protected LCircleArcSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.createCircleArc());

		final SVGElement elt2 = getLaTeXDrawElement(elt, null);
		final IArrow arr1	= shape.getArrowAt(0);
		final IArrow arr2	= shape.getArrowAt(-1);

		if(elt==null || !(elt2 instanceof SVGPathElement))
			throw new IllegalArgumentException();

		final SVGPathElement main = (SVGPathElement)elt2;
		final SVGPathSegList l = main.getSegList();
		final double sx;
        final double sy;

        if(l.size()<2 && !(l.get(0) instanceof SVGPathSegMoveto) && !(l.get(1) instanceof SVGPathSegArc))
			throw new IllegalArgumentException();

		sx = ((SVGPathSegMoveto)l.get(0)).getX();
		sy = ((SVGPathSegMoveto)l.get(0)).getY();
		final Arc2D arc = ((SVGPathSegArc)l.get(1)).getArc2D(sx, sy);
		double angle = Math.toRadians(arc.getAngleStart())%(Math.PI*2);
		shape.setAngleStart(angle);
		angle = Math.toRadians(arc.getAngleExtent()+arc.getAngleStart())%(Math.PI*2);
		shape.setAngleEnd(angle);
		shape.setPosition(arc.getMinX(), arc.getMaxY());
		shape.setWidth(arc.getMaxX()-arc.getMinX());

		if(l.size()>2)
			if(l.get(2) instanceof SVGPathSegClosePath)
				shape.setArcStyle(ArcStyle.CHORD);
			else
				if(l.size()==4 && l.get(2) instanceof SVGPathSegLineto && l.get(3) instanceof SVGPathSegClosePath)
					shape.setArcStyle(ArcStyle.WEDGE);

		shape.setShowPts(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHOW_PTS)!=null);

		setSVGParameters(main);
		setSVGArrow(arr1, main.getAttribute(main.getUsablePrefix()+SVGAttributes.SVG_MARKER_START), main, SVGAttributes.SVG_MARKER_START);
		setSVGArrow(arr2, main.getAttribute(main.getUsablePrefix()+SVGAttributes.SVG_MARKER_END), main, SVGAttributes.SVG_MARKER_END);
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

		final SVGDefsElement defs  = doc.getFirstChild().getDefs();
		final double rotationAngle = shape.getRotationAngle();
		final double startAngle    = shape.getAngleStart()%(2.*Math.PI);
		final double endAngle      = shape.getAngleEnd()%(2.*Math.PI);
		final ArcStyle type 		 = shape.getArcStyle();
        final SVGElement root 	 = new SVGGElement(doc);
        final IPoint start 		 = shape.getStartPoint();
        final IPoint end 			 = shape.getEndPoint();
        final double radius 		 = shape.getWidth()/2.0;
        final boolean largeArcFlag = Math.abs(startAngle-endAngle)>=Math.PI || startAngle>endAngle;
        final SVGPathSegList path  = new SVGPathSegList();
        SVGElement elt;

        root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_ARC);
        root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

        path.add(new SVGPathSegMoveto(start.getX(), start.getY(), false));
        path.add(new SVGPathSegArc(end.getX(), end.getY(), radius, radius, 0, largeArcFlag, false, false));

        if(type==ArcStyle.CHORD)
        	path.add(new SVGPathSegClosePath());
        else
        	if(type==ArcStyle.WEDGE) {
        		final IPoint gravityCenter = shape.getGravityCentre();
        		path.add(new SVGPathSegLineto(gravityCenter.getX(), gravityCenter.getY(), false));
        		path.add(new SVGPathSegClosePath());
        	}

        if(shape.hasShadow())  {
        	final SVGElement shad = new SVGPathElement(doc);

        	shad.setAttribute(SVGAttributes.SVG_D, path.toString());
        	setSVGShadowAttributes(shad, true);
        	root.appendChild(shad);
        	setSVGArrow(shape, shad, 0, true, doc, defs);
			setSVGArrow(shape, shad, 1, true, doc, defs);
        }

        // The background of the borders must be filled is there is a shadow.
        if(shape.hasShadow()){// && shape.getLineStyle()!=LineStyle.NONE) {
            elt = new SVGPathElement(doc);
            elt.setAttribute(SVGAttributes.SVG_D, path.toString());
            setSVGBorderBackground(elt, root);
        }

        elt = new SVGPathElement(doc);
        elt.setAttribute(SVGAttributes.SVG_D, path.toString());
        root.appendChild(elt);

        if(shape.hasDbleBord()) {
            final SVGElement dble = new SVGPathElement(doc);
            dble.setAttribute(SVGAttributes.SVG_D, path.toString());
        	setSVGDoubleBordersAttributes(dble);
        	root.appendChild(dble);
        }

        setSVGRotationAttribute(root);
        setSVGAttributes(doc, elt, true);
        elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE +':'+ LNamespace.XML_ROTATION, String.valueOf(rotationAngle));

		setSVGArrow(shape, elt, 0, false, doc, defs);
		setSVGArrow(shape, elt, 1, false, doc, defs);

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

		final SVGGElement showPts = new SVGGElement(doc);
		final double thickness	= shape.getThickness()/2.;

		showPts.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_SHOW_PTS);

		showPts.appendChild(getShowPointsLine(doc, thickness, shape.getLineColour(), shape.getGravityCentre(),
				shape.getStartPoint(), shape.getDashSepBlack(), shape.getDashSepWhite(), false, 1, 0));
		showPts.appendChild(getShowPointsLine(doc, thickness, shape.getLineColour(), shape.getGravityCentre(),
				shape.getEndPoint(), shape.getDashSepBlack(), shape.getDashSepWhite(), false, 1, 0));

		return showPts;
	}
}
