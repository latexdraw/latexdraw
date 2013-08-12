package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGCircleElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for a circle.<br>
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
class LCircleSVGGenerator extends LEllipseSVGGenerator<ICircle> {
	/**
	 * Creates a generator of SVG circle.
	 * @param circle The circle shape used for the generation.
	 * @throws IllegalArgumentException If circle is null.
	 * @since 2.0
	 */
	protected LCircleSVGGenerator(final ICircle circle) {
		super(circle);
	}


	/**
	 * Creates a circle from an SVG circle element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LCircleSVGGenerator(final SVGCircleElement elt) {
		this(DrawingTK.getFactory().createCircle(true));

		setSVGParameters(elt);
		setCircleParameters(elt, 0.);
		applyTransformations(elt);
	}


	/**
	 * Creates a circle from a G SVG element.
	 * @param elt The G SVG element used for the creation of a circle.
	 * @throws IllegalArgumentException If the given element is null.
	 * @since 2.0
	 */
	protected LCircleSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}


	/**
	 * Creates a circle from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LCircleSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createCircle(false));

		setNumber(elt);
		SVGElement elt2 = getLaTeXDrawElement(elt, null);

		if(elt==null || !(elt2 instanceof SVGCircleElement))
			throw new IllegalArgumentException();

		setSVGLatexdrawParameters(elt);
		setSVGParameters(elt2);
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));
		setCircleParameters((SVGCircleElement)elt2, getPositionGap());

		if(withTransformation)
			applyTransformations(elt);
	}



	/**
	 * Sets the circle parameters.
	 * @param circleElt The source SVG circle element.
	 * @param gap The gap used to define the latexdraw circle.
	 * @since 3.0
	 */
	protected void setCircleParameters(final SVGCircleElement circleElt, final double gap) {
		final double radius  = circleElt.getR()-gap/2.;

		shape.setWidth(radius*2.);
		shape.setPosition(circleElt.getCx()-radius, circleElt.getCy()+radius);
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null || doc.getFirstChild().getDefs()==null)
			return null;

		final IPoint tl = shape.getTopLeftPoint();
		final IPoint br = shape.getBottomRightPoint();
		final double tlx = tl.getX();
		final double tly = tl.getY();
		final double brx = br.getX();
		final double bry = br.getY();
		SVGElement elt, shad = null, dblBord = null;
		SVGElement root = new SVGGElement(doc);
        root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_CIRCLE);
        root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
        double gap = getPositionGap();

        if(shape.hasShadow()) {
        	shad = new SVGCircleElement((brx+tlx)/2., (bry+tly)/2., (brx-tlx+gap)/2., doc);
        	setSVGShadowAttributes(shad, true);
        	root.appendChild(shad);
        }

        if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE)){
        	// The background of the borders must be filled is there is a shadow.
	        elt = new SVGCircleElement((brx+tlx)/2., (bry+tly)/2., (brx-tlx+gap)/2., doc);
	        setSVGBorderBackground(elt, root);
        }

        elt = new SVGCircleElement((brx+tlx)/2., (bry+tly)/2., (brx-tlx+gap)/2., doc);// FIXME Should use prototype design pattern to reduce re-creation.
        root.appendChild(elt);

        if(shape.hasDbleBord()) {
        	dblBord = new SVGCircleElement((brx+tlx)/2., (bry+tly)/2., (brx-tlx+gap)/2., doc);
        	setSVGDoubleBordersAttributes(dblBord);
        	root.appendChild(dblBord);
        }

        setSVGAttributes(doc, elt, true);
        setSVGRotationAttribute(root);

		return root;
	}
}
