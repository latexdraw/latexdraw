package net.sf.latexdraw.generators.svg;

import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IPolyline;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGLineElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.SVGPolyLineElement;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for some joined lines.<br>
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
public class LPolylinesSVGGenerator extends LModifiablePointsGenerator<IPolyline> {
	/**
	 * Creates a generator for IPolyline.
	 * @param polyline The source polyline used to generate the SVG element.
	 */
	public LPolylinesSVGGenerator(final IPolyline polyline) {
		super(polyline);
	}


	/**
	 * Creates some lines using a SVG path.
	 * @param elt The SVG path.
	 */
	public LPolylinesSVGGenerator(final SVGPathElement elt) {
		super(DrawingTK.getFactory().createPolyline(true));

		if(elt==null || !elt.isLines())
			throw new IllegalArgumentException();

		initModifiablePointsShape(elt);
	}



	/**
	 * Creates some joined-lines from an SVG polyline element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	public LPolylinesSVGGenerator(final SVGPolyLineElement elt) {
		this(DrawingTK.getFactory().createPolyline(true));

		setSVGModifiablePointsParameters(elt);
		applyTransformations(elt);
		shape.update();
	}


	/**
	 * Creates a line from an SVG line element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	public LPolylinesSVGGenerator(final SVGLineElement elt) {
		this(DrawingTK.getFactory().createPolyline(true));

		//TODO
		applyTransformations(elt);
		shape.update();
	}



	/**
	 * Creates a latexdraw polyline from an SVG element provided by a latexdraw-SVG document.
	 * @param elt The latexdraw-SVG element to convert as a latexdraw shape.
	 * @since 3.0
	 */
	public LPolylinesSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}



	/**
	 * Creates some joined-lines from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	public LPolylinesSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createPolyline(true));

		setNumber(elt);
		SVGElement elt2 = getLaTeXDrawElement(elt, null);

		if(elt==null || !(elt2 instanceof SVGPolyLineElement))//TODO must manage SVGLineElement
			throw new IllegalArgumentException();

		IArrow arrowHead1 	= shape.getArrowAt(0);
		IArrow arrowHead2 	= shape.getArrowAt(1);
		SVGPolyLineElement main = (SVGPolyLineElement)elt2;
		setSVGLatexdrawParameters(elt);
		setSVGModifiablePointsParameters(main);
		shape.update();

		setSVGArrow(arrowHead1, main.getAttribute(main.getUsablePrefix()+SVGAttributes.SVG_MARKER_START), main);
		setSVGArrow(arrowHead2, main.getAttribute(main.getUsablePrefix()+SVGAttributes.SVG_MARKER_END), main);
		homogeniseArrows(arrowHead1, arrowHead2);
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

		if(withTransformation)
			applyTransformations(elt);
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			return null;

        final SVGElement root 		= new SVGGElement(doc);
		final SVGDefsElement defs 	= doc.getFirstChild().getDefs();
		final StringBuilder points 	= new StringBuilder();
		final List<IPoint> pts		= shape.getPoints();
		SVGElement elt;

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_JOINED_LINES);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

		for(IPoint pt : pts)
			points.append(pt.getX()).append(',').append(pt.getY()).append(' ');

		final String pointsStr = points.toString();

		if(shape.hasShadow()) {
			SVGElement shad = new SVGPolyLineElement(doc);

			shad.setAttribute(SVGAttributes.SVG_POINTS, pointsStr);
			setSVGShadowAttributes(shad, false);
			root.appendChild(shad);
			setSVGArrow(shad, 0, true, doc, defs);
			setSVGArrow(shad, 1, true, doc, defs);
		}

        if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE) && shape.isFilled()) {
        // The background of the borders must be filled is there is a shadow.
    		elt = new SVGPolyLineElement(doc);
    		elt.setAttribute(SVGAttributes.SVG_POINTS, pointsStr);
    		setSVGBorderBackground(elt, root);
        }

		elt = new SVGPolyLineElement(doc);
		elt.setAttribute(SVGAttributes.SVG_POINTS, pointsStr);
		root.appendChild(elt);

		if(shape.hasDbleBord()) {
			SVGElement dblBord = new SVGPolyLineElement(doc);

			dblBord.setAttribute(SVGAttributes.SVG_POINTS, pointsStr);
			setSVGDoubleBordersAttributes(dblBord);
			root.appendChild(dblBord);
		}

		setSVGAttributes(doc, elt, false);
		elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ROTATION, String.valueOf(shape.getRotationAngle()));

		setSVGArrow(elt, 0, false, doc, defs);
		setSVGArrow(elt, 1, false, doc, defs);

		return root;
	}
}
