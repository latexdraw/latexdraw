package net.sf.latexdraw.generators.svg;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.List;

import net.sf.latexdraw.bordel.BordelCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.views.Java2D.LDotView;
import net.sf.latexdraw.parsers.svg.CSSColors;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGCircleElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGLineElement;
import net.sf.latexdraw.parsers.svg.SVGRectElement;
import net.sf.latexdraw.parsers.svg.parsers.Path2D2SVGPath;
import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for a dot.<br>
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
public class LDotSVGGenerator extends LShapeSVGGenerator<IDot> {
	/**
	 * Creates a generator of SVG dot.
	 * @param dot The dot used for the generation.
	 * @throws IllegalArgumentException If dot is null.
	 */
	public LDotSVGGenerator(final IDot dot) {
		super(dot);
	}


	/**
	 * Creates a dot from a G SVG element.
	 * @param elt The G SVG element used for the creation of a dot.
	 * @throws IllegalArgumentException If the given element is null.
	 */
	public LDotSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}


	/**
	 * Creates a dot from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	public LDotSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), true));

		if(elt==null)
			throw new IllegalArgumentException();

		String v = elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_SIZE);
		SVGElement main = getLaTeXDrawElement(elt, null);

		try { shape.setDotStyle(DotStyle.getStyle(elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_DOT_SHAPE))); }
		catch(IllegalArgumentException e) { BordelCollector.INSTANCE.add(e); }

		if(v!=null)
			try { shape.setRadius(Double.valueOf(v).doubleValue()); }
			catch(NumberFormatException e) { BordelCollector.INSTANCE.add(e); }

		v = elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI)+LNamespace.XML_POSITION);

		List<Point2D> pos = SVGPointsParser.getPoints(v);

		if(pos!=null && !pos.isEmpty())
			shape.setPosition(pos.get(0).getX(), pos.get(0).getY());

		setSVGLatexdrawParameters(elt);
		setSVGParameters(main);
		setNumber(elt);

		if(withTransformation)
			applyTransformations(elt);

		if(!shape.isFillable())
			shape.setLineColour(CSSColors.INSTANCE.getRGBColour(main.getFill()));
	}


	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			return null;

        final SVGElement root = new SVGGElement(doc);
        final SVGElement dot;

        switch(shape.getDotStyle()) {
        	case DOT	: dot = toSVGDot(doc, shape.getRadius()/2., shape.getLineColour()); break;
        	case O		: dot = toSVGOLike(doc, shape.isFilled()? shape.getFillingCol() : Color.WHITE); break;
        	case OTIMES	: dot = toSVGOTime(doc); break;
        	case OPLUS	: dot = toSVGOPlus(doc); break;
        	case BAR	: dot = toSVGBar(doc); break;
        	case PLUS	: dot = toSVGOPlus(doc); break;
        	case SQUARE	: dot = toSVGSquare(doc, shape.isFilled() ? shape.getFillingCol() : Color.WHITE); break;
        	case FSQUARE: dot = toSVGSquare(doc, shape.getLineColour()); break;
        	case DIAMOND  :
        	case PENTAGON :
        	case TRIANGLE : dot = toSVGPolygon(doc, shape.isFilled() ? shape.getFillingCol() : Color.WHITE); break;
        	case FDIAMOND :
        	case FPENTAGON:
        	case FTRIANGLE: dot = toSVGPolygon(doc, shape.getLineColour()); break;
        	case X		  : dot = toSVGX(doc); break;
        	case ASTERISK : dot = toSVGAsterisk(doc); break;
        	default		  : throw new IllegalArgumentException("Bad dot style.");
        }

        root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_DOT);
        root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_SIZE, String.valueOf(shape.getRadius()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_DOT_SHAPE, shape.getDotStyle().getPSTToken());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_POSITION, shape.getPosition().getX() + " " + shape.getPosition().getY()); //$NON-NLS-1$
		root.appendChild(dot);

		setSVGRotationAttribute(root);

		return root;
	}


	protected SVGElement toSVGAsterisk(final SVGDocument doc) {
		final SVGElement dot	= new SVGGElement(doc);
		final SVGElement l1 	= new SVGLineElement(doc);
		final SVGElement l2 	= new SVGLineElement(doc);
		final SVGElement l3 	= new SVGLineElement(doc);
		final IPoint tl 		= shape.getLazyTopLeftPoint();
		final IPoint br			= shape.getLazyBottomRightPoint();
		final double width 		= shape.getRadius();
		final double gap 		= shape.getGeneralGap();
		final double xCenter 	= (tl.getX()+br.getX())/2., yCenter = (tl.getY()+br.getY())/2.;
		final double radius 	= Math.abs((tl.getY()+width/10.)-(br.getY()-width/10.))/2.+gap;

		dot.setAttribute(SVGAttributes.SVG_STROKE_LINECAP, SVGAttributes.SVG_LINECAP_VALUE_SQUARE);
		dot.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getLineColour(), true));
		dot.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(gap));

		l1.setAttribute(SVGAttributes.SVG_X1, String.valueOf((tl.getX()+br.getX())/2.));
		l1.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(tl.getY()+width/10.-gap));
		l1.setAttribute(SVGAttributes.SVG_X2, String.valueOf((tl.getX()+br.getX())/2.));
		l1.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(br.getY()-width/10.+gap));
		l2.setAttribute(SVGAttributes.SVG_X1, String.valueOf(Math.cos(Math.PI/6.)*radius+xCenter));
		l2.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(radius/2. + yCenter));
		l2.setAttribute(SVGAttributes.SVG_X2, String.valueOf(Math.cos(7*Math.PI/6.)*radius+xCenter));
		l2.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(Math.sin(7*Math.PI/6.)*radius+yCenter));
		l3.setAttribute(SVGAttributes.SVG_X1, String.valueOf(Math.cos(5*Math.PI/6.)*radius+xCenter));
		l3.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(Math.sin(5*Math.PI/6.)*radius+yCenter));
		l3.setAttribute(SVGAttributes.SVG_X2, String.valueOf(Math.cos(11*Math.PI/6.)*radius+xCenter));
		l3.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(Math.sin(11*Math.PI/6.)*radius+yCenter));
		dot.appendChild(l1);
		dot.appendChild(l2);
		dot.appendChild(l3);

		return dot;
	}


	protected SVGElement toSVGX(final SVGDocument doc) {
		final double crossGap	= shape.getCrossGap();
		final SVGElement dot	= new SVGGElement(doc);
		final SVGElement p1 	= new SVGLineElement(doc);
		final SVGElement p2 	= new SVGLineElement(doc);
		final IPoint tl 		= shape.getLazyTopLeftPoint();
		final IPoint br			= shape.getLazyBottomRightPoint();

		dot.setAttribute(SVGAttributes.SVG_STROKE_LINECAP, SVGAttributes.SVG_LINECAP_VALUE_SQUARE);
		dot.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getLineColour(), true));
		dot.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(crossGap));

		p1.setAttribute(SVGAttributes.SVG_X1, String.valueOf(tl.getX()+crossGap));
		p1.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(tl.getY()+crossGap));
		p1.setAttribute(SVGAttributes.SVG_X2, String.valueOf(br.getX()-crossGap));
		p1.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(br.getY()-crossGap));
		p2.setAttribute(SVGAttributes.SVG_X1, String.valueOf(br.getX()-crossGap));
		p2.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(tl.getY()+crossGap));
		p2.setAttribute(SVGAttributes.SVG_X2, String.valueOf(tl.getX()+crossGap));
		p2.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(br.getY()-crossGap));
		dot.appendChild(p1);
		dot.appendChild(p2);

		return dot;
	}



	protected SVGElement toSVGPolygon(final SVGDocument doc, final Color fillCol) {
		final Path2D2SVGPath parser = new Path2D2SVGPath(new LDotView(shape).getPath(), doc);
		final SVGElement dot;

		try{ parser.parse(); }
		catch(final ParseException exception){
			BordelCollector.INSTANCE.add(exception);
			return null;
		}

		dot = parser.getPathSVG();
		dot.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getLineColour(), true));
		dot.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(fillCol, true));
		dot.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(shape.getGeneralGap()));

		return dot;
	}


	protected SVGElement toSVGOLike(final SVGDocument doc, final Color fillColour) {
		final SVGElement dot = new SVGGElement(doc);
		SVGElement o = toSVGDot(doc, shape.getRadius()/2.-shape.getOGap()/2., fillColour);

		dot.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getLineColour(), true));
		dot.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(shape.getOGap()));
		dot.appendChild(o);

		return dot;
	}


	protected SVGElement toSVGDot(final SVGDocument doc, final double radius, final Color fillColour) {
		IPoint center 		= shape.getPosition();
		final SVGElement dot = new SVGCircleElement(doc);

		if(fillColour==null)
			dot.setAttribute(SVGAttributes.SVG_FILL, SVGAttributes.SVG_VALUE_NONE);
		else
			dot.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(fillColour, true));

		dot.setAttribute(SVGAttributes.SVG_CX, String.valueOf(center.getX()));
		dot.setAttribute(SVGAttributes.SVG_CY, String.valueOf(center.getY()));
		dot.setAttribute(SVGAttributes.SVG_R, String.valueOf(radius));
		return dot;
	}


	protected SVGElement toSVGOPlus(final SVGDocument doc) {
		final SVGElement dot= toSVGOLike(doc, null);
		final SVGElement p1 = new SVGLineElement(doc);
		final SVGElement p2 = new SVGLineElement(doc);
		final double gap 	= shape.getOGap()*2;
		final IPoint tl 	= shape.getLazyTopLeftPoint();
		final IPoint br		= shape.getLazyBottomRightPoint();

		p1.setAttribute(SVGAttributes.SVG_X1, String.valueOf((tl.getX()+br.getX())/2.));
		p1.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(tl.getY()+gap));
		p1.setAttribute(SVGAttributes.SVG_X2, String.valueOf((tl.getX()+br.getX())/2.));
		p1.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(br.getY()-gap));
		p2.setAttribute(SVGAttributes.SVG_X1, String.valueOf(tl.getX()+gap));
		p2.setAttribute(SVGAttributes.SVG_Y1, String.valueOf((tl.getY()+br.getY())/2.));
		p2.setAttribute(SVGAttributes.SVG_X2, String.valueOf(br.getX()-gap));
		p2.setAttribute(SVGAttributes.SVG_Y2, String.valueOf((tl.getY()+br.getY())/2.));
		dot.appendChild(p1);
		dot.appendChild(p2);

		return dot;
	}


	protected SVGElement toSVGOTime(final SVGDocument doc) {
		final IPoint tl 	= shape.getLazyTopLeftPoint();
		final IPoint br		= shape.getLazyBottomRightPoint();
		final SVGElement dot= toSVGOLike(doc, shape.isFilled()? shape.getFillingCol() : Color.WHITE);
		final SVGElement p1 = new SVGLineElement(doc);
		final SVGElement p2 = new SVGLineElement(doc);
		final double gap 	= shape.getOGap()*2.+2.*2.; // where 2 is the default thickness

		p1.setAttribute(SVGAttributes.SVG_X1, String.valueOf(tl.getX()+gap));
		p1.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(tl.getY()+gap));
		p1.setAttribute(SVGAttributes.SVG_X2, String.valueOf(br.getX()-gap));
		p1.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(br.getY()-gap));
		p2.setAttribute(SVGAttributes.SVG_X1, String.valueOf(br.getX()-gap));
		p2.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(tl.getY()+gap));
		p2.setAttribute(SVGAttributes.SVG_X2, String.valueOf(tl.getX()+gap));
		p2.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(br.getY()-gap));
		dot.appendChild(p1);
		dot.appendChild(p2);

		return dot;
	}


	protected SVGElement toSVGBar(final SVGDocument doc) {
		final SVGElement dot = new SVGLineElement(doc);
		final IPoint tl 	= shape.getLazyTopLeftPoint();
		final IPoint br		= shape.getLazyBottomRightPoint();

		dot.setAttribute(SVGAttributes.SVG_STROKE_LINECAP, SVGAttributes.SVG_LINECAP_VALUE_SQUARE);
		dot.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getLineColour(), true));
		dot.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(shape.getBarThickness()));
		dot.setAttribute(SVGAttributes.SVG_X1, String.valueOf((tl.getX()+br.getX())/2.));
		dot.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(tl.getY()+shape.getBarThickness()/2.));
		dot.setAttribute(SVGAttributes.SVG_X2, String.valueOf((tl.getX()+br.getX())/2.));
		dot.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(br.getY()+shape.getBarGap()));

		return dot;
	}



	protected SVGElement toSVGPlus(final SVGDocument doc) {
		final SVGElement dot	= new SVGGElement(doc);
		final SVGElement p1 	= new SVGLineElement(doc);
		final SVGElement p2 	= new SVGLineElement(doc);
		final double plusGap 	= shape.getPlusGap();
		final IPoint tl 		= shape.getLazyTopLeftPoint();
		final IPoint br			= shape.getLazyBottomRightPoint();

		dot.setAttribute(SVGAttributes.SVG_STROKE_LINECAP, SVGAttributes.SVG_LINECAP_VALUE_SQUARE);
		dot.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getLineColour(), true));
		dot.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(shape.getRadius()/IDot.PLUS_COEFF_WIDTH));

		p1.setAttribute(SVGAttributes.SVG_X1, String.valueOf((tl.getX()+br.getX())/2.));
		p1.setAttribute(SVGAttributes.SVG_Y1, String.valueOf(tl.getY()-plusGap));
		p1.setAttribute(SVGAttributes.SVG_X2, String.valueOf((tl.getX()+br.getX())/2.));
		p1.setAttribute(SVGAttributes.SVG_Y2, String.valueOf(br.getY()+plusGap));
		p2.setAttribute(SVGAttributes.SVG_X1, String.valueOf(tl.getX()-plusGap));
		p2.setAttribute(SVGAttributes.SVG_Y1, String.valueOf((tl.getY()+br.getY())/2.));
		p2.setAttribute(SVGAttributes.SVG_X2, String.valueOf(br.getX()+plusGap));
		p2.setAttribute(SVGAttributes.SVG_Y2, String.valueOf((tl.getY()+br.getY())/2.));
		dot.appendChild(p1);
		dot.appendChild(p2);

		return dot;
	}



	protected SVGElement toSVGSquare(final SVGDocument doc, final Color fillColour) {
		final SVGElement dot	= new SVGRectElement(doc);
		final double gap 		= shape.getRadius()*0.25*2.;
		final IPoint tl 		= shape.getLazyTopLeftPoint();
		final IPoint br 		= shape.getLazyBottomRightPoint();

		dot.setAttribute(SVGAttributes.SVG_X, String.valueOf(tl.getX()+gap*1.5));
		dot.setAttribute(SVGAttributes.SVG_Y, String.valueOf(tl.getY()+gap*1.5));
		dot.setAttribute(SVGAttributes.SVG_WIDTH, String.valueOf(Math.abs(tl.getX()-br.getX())-3*gap));
		dot.setAttribute(SVGAttributes.SVG_HEIGHT, String.valueOf(Math.abs(tl.getY() - br.getY())-3*gap));
		dot.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.INSTANCE.getColorName(shape.getLineColour(), true));
		dot.setAttribute(SVGAttributes.SVG_FILL, CSSColors.INSTANCE.getColorName(fillColour, true));
		dot.setAttribute(SVGAttributes.SVG_STROKE_WIDTH, String.valueOf(gap));

		return dot;
	}
}
