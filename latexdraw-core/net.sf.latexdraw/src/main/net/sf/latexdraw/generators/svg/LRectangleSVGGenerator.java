package net.sf.latexdraw.generators.svg;

import static java.lang.Math.min;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGRectElement;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for a rectangle.<br>
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
class LRectangleSVGGenerator extends LShapeSVGGenerator<IRectangle> {
	/**
	 * Creates a generator of SVG rectangle.
	 * @param rect The rectangle shape used for the generation.
	 * @throws IllegalArgumentException If rect is null.
	 * @since 2.0
	 */
	protected LRectangleSVGGenerator(final IRectangle rect) {
		super(rect);
	}



	/**
	 * Creates a rectangle from an SVG rect element.
	 * @param elt The source element.
	 * @throws IllegalArgumentException If the given element is null.
	 * @since 2.0.0
	 */
	protected LRectangleSVGGenerator(final SVGRectElement elt) {
		this(DrawingTK.getFactory().createRectangle(true));

		setSVGRectParameters(elt);
		applyTransformations(elt);
	}



	/**
	 * Creates a rectangle from a G SVG element.
	 * @param elt The G SVG element used for the creation of a rectangle.
	 * @throws IllegalArgumentException If the given element is null.
	 * @since 2.0
	 */
	protected LRectangleSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}



	/**
	 * Creates a rectangle from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @throws IllegalArgumentException If the given element is null or not valid.
	 * @since 2.0.0
	 */
	protected LRectangleSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createRectangle(false));
		initRectangle(elt, withTransformation);
	}



	/**
	 * Initialises the rectangle using an SVGGElement provided by a latexdraw SVG document.
	 * @param elt The source element.
	 * @throws IllegalArgumentException If the given element is null or not valid.
	 * @since 3.0
	 */
	protected void initRectangle(final SVGGElement elt, final boolean withTransformation) {
		setNumber(elt);
		SVGElement elt2 = getLaTeXDrawElement(elt, null);

		if(elt==null || !(elt2 instanceof SVGRectElement))
			throw new IllegalArgumentException();

		setSVGLatexdrawParameters(elt);
		setSVGRectParameters((SVGRectElement)elt2);
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

		if(withTransformation)
			applyTransformations(elt);
	}



	/**
	 * Sets the parameters of the latexdraw rectangle using the given SVG rectangle.
	 * @param elt The SVG rectangle used to set the latexdraw rectangle.
	 * @since 2.0
	 */
	protected void setSVGRectParameters(final SVGRectElement elt) {
		if(elt==null)
			return ;

		setSVGParameters(elt);

		final double rx	= elt.getRx();
		final double gap= getPositionGap();

		shape.setPosition(elt.getX()+gap/2., elt.getY()+elt.getHeight()-gap/2.);
		shape.setWidth(elt.getWidth()-gap);
		shape.setHeight(elt.getHeight()-gap);
		shape.setLineArc((2.*rx)/(min(shape.getHeight(), shape.getWidth())- (shape.hasDbleBord() ? shape.getDbleBordSep()+shape.getThickness() : 0.)));
	}



	@Override
	public SVGElement toSVG(final SVGDocument document) {
		if(document==null || document.getFirstChild().getDefs()==null)
			throw new IllegalArgumentException();

		final double gap = getPositionGap();
		final IPoint tl  = shape.getTopLeftPoint();
		final IPoint br  = shape.getBottomRightPoint();
		SVGElement elt;
        final SVGElement root = new SVGGElement(document);
        final double width  = Math.max(1, (br.getX()-tl.getX())+gap);
        final double height = Math.max(1., (br.getY()-tl.getY())+gap);
        final double x		= tl.getX()-gap/2.;
        final double y		= tl.getY()-gap/2.;

        root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_RECT);
        root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

        if(shape.hasShadow()) {
        	elt = new SVGRectElement(x, y, width, height, document);
        	setSVGShadowAttributes(elt, true);
        	root.appendChild(elt);
        	setSVGRoundCorner(elt);
        }

        if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE)) {
        	// The background of the borders must be filled is there is a shadow.
	        elt = new SVGRectElement(x, y, width, height, document);
	        setSVGBorderBackground(elt, root);
	        setSVGRoundCorner(elt);
        }

        elt = new SVGRectElement(x, y, width, height, document);
        root.appendChild(elt);
        setSVGAttributes(document, elt, true);
        setSVGRoundCorner(elt);

        if(shape.hasDbleBord()) {
        	elt = new SVGRectElement(x, y, width, height, document);
        	setSVGDoubleBordersAttributes(elt);
        	setSVGRoundCorner(elt);
        	root.appendChild(elt);
        }

        setSVGRotationAttribute(root);

		return root;
	}



	/**
	 * Sets the roundness of the SVG shape.
	 * @param elt The SVG element into which the roundness must be set.
	 * @since 2.0.0
	 */
	protected void setSVGRoundCorner(final SVGElement elt) {
		if(elt==null)
			return ;

		if(shape.isRoundCorner()) {
			final double add = shape.isDbleBorderable() ? shape.getDbleBordSep() + shape.getThickness() : 0.;
			final double value = 0.5*(min(shape.getWidth(), shape.getHeight())-add)*shape.getLineArc();

			elt.setAttribute(SVGAttributes.SVG_RX, String.valueOf(value));
		}
	}
}
