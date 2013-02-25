package net.sf.latexdraw.generators.svg;

import java.awt.geom.Point2D;
import java.util.List;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewDot;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;
import net.sf.latexdraw.parsers.svg.CSSColors;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.parsers.Graphics2D2SVG;
import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;
import net.sf.latexdraw.util.LNamespace;

import org.malai.mapping.MappingRegistry;

/**
 * Defines a SVG generator for a dot.<br>
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
class LDotSVGGenerator extends LShapeSVGGenerator<IDot> {
	/**
	 * Creates a generator of SVG dot.
	 * @param dot The dot used for the generation.
	 * @throws IllegalArgumentException If dot is null.
	 */
	protected LDotSVGGenerator(final IDot dot) {
		super(dot);
	}


	/**
	 * Creates a dot from a G SVG element.
	 * @param elt The G SVG element used for the creation of a dot.
	 * @throws IllegalArgumentException If the given element is null.
	 */
	protected LDotSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}


	/**
	 * Creates a dot from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LDotSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), true));

		if(elt==null)
			throw new IllegalArgumentException();

		String v = elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_SIZE);
		SVGElement main = getLaTeXDrawElement(elt, null);

		try { shape.setDotStyle(DotStyle.getStyle(elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_DOT_SHAPE))); }
		catch(IllegalArgumentException e) { BadaboomCollector.INSTANCE.add(e); }

		if(v!=null)
			try { shape.setRadius(Double.parseDouble(v)); }
			catch(NumberFormatException e) { BadaboomCollector.INSTANCE.add(e); }

		v = elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_POSITION);

		List<Point2D> pos = SVGPointsParser.getPoints(v);

		if(pos!=null && !pos.isEmpty())
			shape.setPosition(pos.get(0).getX(), pos.get(0).getY());

		setSVGLatexdrawParameters(elt);
		setSVGParameters(main);
		setNumber(elt);

		if(withTransformation)
			applyTransformations(elt);

		if(!shape.isFillable() && shape.isFilled())
			shape.setLineColour(CSSColors.INSTANCE.getRGBColour(main.getFill()));
	}


	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			return null;

		final Graphics2D2SVG graphics = new Graphics2D2SVG(doc);
        final SVGElement root;
        final boolean viewCreated;
        // Instead of creating a view, its is gathered from the Java view of the application.
		IViewShape view = MappingRegistry.REGISTRY.getTargetFromSource(shape, IViewDot.class);

		if(view==null) {
			view =  View2DTK.getFactory().createView(shape);
			viewCreated = true;
		}
		else viewCreated = false;

        view.paint(graphics);
        root = graphics.getElement();

        root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_DOT);
        root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_SIZE, String.valueOf(shape.getRadius()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_DOT_SHAPE, shape.getDotStyle().getPSTToken());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_POSITION, shape.getPosition().getX() + " " + shape.getPosition().getY()); //$NON-NLS-1$

		graphics.dispose();

		if(viewCreated)
			view.flush();

		return root;
	}
}
