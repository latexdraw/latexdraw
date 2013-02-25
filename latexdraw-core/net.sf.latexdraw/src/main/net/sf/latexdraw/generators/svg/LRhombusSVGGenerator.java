package net.sf.latexdraw.generators.svg;

import java.awt.geom.Point2D;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IRhombus;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGPolygonElement;
import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines an SVG generator for a polygon.<br>
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
class LRhombusSVGGenerator extends LShapeSVGGenerator<IRhombus> {
	/**
	 * Creates a generator of SVG rhombus.
	 * @param rhombus The rhombus used for the generation.
	 * @throws IllegalArgumentException If the given rhombus is null.
	 * @since 2.0
	 */
	protected LRhombusSVGGenerator(final IRhombus rhombus) {
		super(rhombus);
	}


	/**
	 * Creates a rhombus from a G SVG element.
	 * @param elt The G SVG element used for the creation of a rhombus.
	 * @throws IllegalArgumentException If the given element is null.
	 * @since 2.0
	 */
	protected LRhombusSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}


	/**
	 * Creates a rhombus from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LRhombusSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createRhombus(true));

		setNumber(elt);
		SVGElement elt2 = getLaTeXDrawElement(elt, null);

		if(elt==null || !(elt2 instanceof SVGPolygonElement))
			throw new IllegalArgumentException();

		SVGPolygonElement main = (SVGPolygonElement)elt2;
		setSVGLatexdrawParameters(elt);
		setSVGParameters(main);

		List<Point2D> ptsPol = SVGPointsParser.getPoints(elt.getAttribute(
								 elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_POINTS));

		if(ptsPol==null || ptsPol.size()!=4)
			throw new IllegalArgumentException();

		shape.getPtAt(0).setPoint2D(ptsPol.get(0));
		shape.getPtAt(1).setPoint2D(ptsPol.get(1));
		shape.getPtAt(2).setPoint2D(ptsPol.get(2));
		shape.getPtAt(3).setPoint2D(ptsPol.get(3));

		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

		if(withTransformation)
			applyTransformations(elt);
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			return null;

		final IPoint tl  = shape.getTopLeftPoint();
		final IPoint br  = shape.getBottomRightPoint();
		IPoint gc = shape.getGravityCentre();
		IPoint p1 = DrawingTK.getFactory().createPoint((tl.getX()+br.getX())/2., tl.getY());
		IPoint p2 = DrawingTK.getFactory().createPoint(br.getX(), (tl.getY()+br.getY())/2.);
		IPoint p3 = DrawingTK.getFactory().createPoint((tl.getX()+br.getX())/2., br.getY());
		SVGElement root = new SVGGElement(doc);
		SVGElement elt;
	    double gap = getPositionGap()/2.;
	    double cornerGap1 = GLibUtilities.INSTANCE.getCornerGap(gc, p1, p2, gap);
	    double cornerGap2 = GLibUtilities.INSTANCE.getCornerGap(gc, p2, p3, gap);

	    if(p2.getX()<p3.getX())
	    	cornerGap2*=-1;

		StringBuilder pointsBuilder = new StringBuilder().append(p1.getX()).append(',').append(p1.getY() - cornerGap1).append(' ')
							.append(p2.getX() + cornerGap2).append(',').append(p2.getY()).append(' ')
							.append(p3.getX()).append(',').append(p3.getY() + cornerGap1).append(' ')
							.append(tl.getX() - cornerGap2).append(',').append(p2.getY());
		String points = pointsBuilder.toString();
		StringBuilder ltdPoints = new StringBuilder().append(tl.getX()).append(' ').append(tl.getY())
								.append(' ').append(br.getX()).append(' ').append(tl.getY())
								.append(' ').append(tl.getX()).append(' ').append(br.getY())
								.append(' ').append(br.getX()).append(' ').append(br.getY());

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_RHOMBUS);
        root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

        if(shape.hasShadow()) {
        	elt = new SVGPolygonElement(doc);
        	elt.setAttribute(SVGAttributes.SVG_POINTS, points);
        	setSVGShadowAttributes(elt, true);
        	root.appendChild(elt);
        }

        if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE)) {
        	// The background of the borders must be filled is there is a shadow.
    		elt = new SVGPolygonElement(doc);
    		elt.setAttribute(SVGAttributes.SVG_POINTS, points);
    		setSVGBorderBackground(elt, root);
        }

		elt = new SVGPolygonElement(doc);
		elt.setAttribute(SVGAttributes.SVG_POINTS, points);
		root.appendChild(elt);
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_POINTS, ltdPoints.toString());

		if(shape.hasDbleBord()) {
			SVGElement dblBord = new SVGPolygonElement(doc);
			dblBord.setAttribute(SVGAttributes.SVG_POINTS, points);
			setSVGDoubleBordersAttributes(dblBord);
			root.appendChild(dblBord);
		}

		setSVGAttributes(doc, elt, true);
		setSVGRotationAttribute(root);

		return root;
	}
}

