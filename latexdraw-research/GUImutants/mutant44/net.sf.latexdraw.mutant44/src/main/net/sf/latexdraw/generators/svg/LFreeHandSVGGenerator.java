package net.sf.latexdraw.generators.svg;

import java.awt.geom.Point2D;
import java.util.List;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IFreehand.FreeHandType;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.models.interfaces.IShapeFactory;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for a free hand drawing.<br>
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
class LFreeHandSVGGenerator extends LShapeSVGGenerator<IFreehand> {

	protected LFreeHandSVGGenerator(final IFreehand fh) {
		super(fh);
	}

	protected LFreeHandSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}


	protected LFreeHandSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createFreeHand(DrawingTK.getFactory().createPoint(), false));

		final IShapeFactory factory = DrawingTK.getFactory();
		SVGElement elt2 = getLaTeXDrawElement(elt, null);

		setNumber(elt);

		if(elt==null || elt2==null || !(elt2 instanceof SVGPathElement))
			throw new IllegalArgumentException();

		SVGPathElement main = (SVGPathElement)elt2;
		String v = elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_INTERVAL);

		if(v!=null)
			try{ shape.setInterval(Double.valueOf(v).intValue()); }
		catch(NumberFormatException ex) { BadaboomCollector.INSTANCE.add(ex); }

		List<Point2D> pts = SVGPointsParser.getPoints(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_POINTS));

		if(pts==null)
			throw new IllegalArgumentException();

		for(Point2D pt : pts)
			shape.addPoint(factory.createPoint(pt.getX(), pt.getY()));

		shape.removePoint(0);

		setSVGLatexdrawParameters(elt);
		setSVGParameters(main);
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));

		v = elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ROTATION);

		if(v!=null)
			try{ shape.setRotationAngle(Double.valueOf(v)); }
			catch(NumberFormatException ex) { BadaboomCollector.INSTANCE.add(ex); }

		v = elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_PATH_TYPE);

		try{
			int value = Double.valueOf(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_PATH_TYPE)).intValue();
			shape.setType(value==0 ? FreeHandType.LINES : FreeHandType.CURVES); }
		catch(NumberFormatException ex) { BadaboomCollector.INSTANCE.add(ex); }

		if(withTransformation)
			applyTransformations(elt);
	}


	/**
	 * Fills the given SVG path with elements corresponding to the Freehand curved path.
	 * @since 3.0
	 */
	protected void getPathCurves(final SVGPathSegList path) {
		double prevx = shape.getPtAt(-1).getX();
		double prevy = shape.getPtAt(-1).getY();
		double curx = shape.getPtAt(0).getX();
		double cury = shape.getPtAt(0).getY();
		double midx = (curx + prevx) / 2.;
		double midy = (cury + prevy) / 2.;
		int i;
		final int size = shape.getNbPoints();
		final int interval = shape.getInterval();

    	path.add(new SVGPathSegMoveto(curx, cury, false));

        if(size>interval) {
           prevx = curx;
           prevy = cury;
           curx = shape.getPtAt(interval).getX();
           cury = shape.getPtAt(interval).getY();
           midx = (curx + prevx) / 2.;
           midy = (cury + prevy) / 2.;
           path.add(new SVGPathSegLineto(midx, midy, false));
        }

        for(i=interval*2; i<size; i+=interval)  {
        	double x1 = (midx + curx) / 2.;
        	double y1 = (midy + cury) / 2.;
        	prevx = curx;
        	prevy = cury;
            curx = shape.getPtAt(i).getX();
            cury = shape.getPtAt(i).getY();
            midx = (curx + prevx) / 2.;
            midy = (cury + prevy) / 2.;
            double x2 = (prevx + midx) / 2.;
            double y2 = (prevy + midy) / 2.;

            path.add(new SVGPathSegCurvetoCubic(midx, midy, x1, y1, x2, y2, false));
        }

        if((i-interval+1)<size) {
        	double x1 = (midx + curx) / 2.;
        	double y1 = (midy + cury) / 2.;
            prevx = curx;
            prevy = cury;
            curx = shape.getPtAt(-1).getX();
            cury = shape.getPtAt(-1).getY();
            midx = (curx + prevx) / 2.;
            midy = (cury + prevy) / 2.;
            double x2 = (prevx + midx) / 2.;
            double y2 = (prevy + midy) / 2.;

            path.add(new SVGPathSegCurvetoCubic(shape.getPtAt(-1).getX(), shape.getPtAt(-1).getY(), x1, y1, x2, y2, false));
        }
	}


	/**
	 * Fills the given SVG path with elements corresponding to the Freehand lined path.
	 * @since 3.0
	 */
	protected void getPathLines(final SVGPathSegList path) {
		IPoint p = shape.getPtAt(0);
		int i;
		final int size = shape.getNbPoints();
		final int interval = shape.getInterval();

		path.add(new SVGPathSegMoveto(p.getX(), p.getY(), false));

		for(i=interval; i<size; i+=interval)
			path.add(new SVGPathSegLineto(shape.getPtAt(i).getX(), shape.getPtAt(i).getY(), false));

		if((i-interval)<size)
			path.add(new SVGPathSegLineto(shape.getPtAt(-1).getX(), shape.getPtAt(-1).getY(), false));
	}



	/**
	 * @return The path of the shape.
	 * @since 2.0.0
	 */
	public SVGPathSegList getPath() {
		final SVGPathSegList path = new SVGPathSegList();

		switch(shape.getType()) {
			case CURVES:
				getPathCurves(path);
				break;
			case LINES:
				getPathLines(path);
				break;
		}

		if(!shape.isOpen())
			path.add(new SVGPathSegClosePath());

		return path;
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			return null;

		SVGElement root = new SVGGElement(doc);
		SVGElement elt;

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_FREEHAND);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_PATH_TYPE, String.valueOf(shape.getType()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_INTERVAL, String.valueOf(shape.getInterval()));
		String path = getPath().toString();
		StringBuilder pts = new StringBuilder();

		for(int i = 0, size = shape.getNbPoints(); i < size; i++)
				pts.append(shape.getPtAt(i).getX()).append(' ').append(shape.getPtAt(i).getY()).append(' ');

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POINTS, pts.toString());

		if(shape.hasShadow()) {
			SVGElement shad = new SVGPathElement(doc);
			shad.setAttribute(SVGAttributes.SVG_D, path);
			setSVGShadowAttributes(shad, false);
			root.appendChild(shad);
		}

        if(shape.hasShadow() && shape.getLineStyle()!=LineStyle.NONE && shape.isFilled())
        {// The background of the borders must be filled is there is a shadow.
    		elt = new SVGPathElement(doc);
    		elt.setAttribute(SVGAttributes.SVG_D, path);
    		setSVGBorderBackground(elt, root);
        }

		elt = new SVGPathElement(doc);
		elt.setAttribute(SVGAttributes.SVG_D, path);
		root.appendChild(elt);

		setSVGAttributes(doc, elt, false);
		elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ROTATION, String.valueOf(shape.getRotationAngle()));

		return root;
	}
}
