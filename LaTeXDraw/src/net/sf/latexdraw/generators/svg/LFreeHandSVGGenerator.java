package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;

/**
 * Defines a SVG generator for a free hand drawing.<br>
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
class LFreeHandSVGGenerator extends LShapeSVGGenerator<IFreehand> {

	protected LFreeHandSVGGenerator(final IFreehand f) {
		super(f);
	}


	protected LFreeHandSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}



	protected LFreeHandSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createFreeHand(DrawingTK.getFactory().createPoint(), false));

//		LFreeHand ak = (LFreeHand)getShape();
//		SVGElement elt2 = getLaTeXDrawElement(elt, null);
//
//		setNumber(elt);
//
//		if(elt==null || elt2==null || !(elt2 instanceof SVGPathElement))
//			throw new IllegalArgumentException();
//
//		try{ ak.setType(Double.valueOf(elt.getAttribute(
//										LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_PATH_TYPE)).intValue()); }
//		catch(NumberFormatException e) { throw new IllegalArgumentException("Invalid path type."); }//$NON-NLS-1$
//
//		SVGPathElement main = (SVGPathElement)elt2;
//		String v = elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_INTERVAL);
//
//		if(v!=null)
//			try{ ak.setInterval(Double.valueOf(v).intValue()); }
//		catch(NumberFormatException e) { /* */ }
//
//		Vector<Point2D> pts = SVGPointsParser.getPoints(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_POINTS));
//
//		if(pts==null)
//			throw new IllegalArgumentException();
//
//		for(Point2D pt : pts)
//			ak.addPoint(new LPoint(pt));
//
//		ak.removePointAt(0);
//
//		setSVGLatexdrawParameters(elt);
//		setSVGParameters(main);
//		ak.update();
//		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
//
//		v = elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_ROTATION);
//
//		if(v!=null)
//			try{ ak.setRotationAngle(Double.valueOf(v)); }
//			catch(NumberFormatException e) { /* */ }
//
//		v = elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_PATH_TYPE);
//
//		if(v!=null)
//			try{ ak.setType(Double.valueOf(v).intValue()); }
//			catch(NumberFormatException e) { /* */ }

		if(withTransformation)
			applyTransformations(elt);
	}



	/**
	 * @return The path of the shape.
	 * @since 2.0.0
	 */
	public SVGPathSegList getPath()
	{
		SVGPathSegList path = new SVGPathSegList();
//		LFreeHand ak = (LFreeHand)shape;
//		int i, size = ak.getNbPoints();
//		int interval = ak.getInterval();
//
//		if(ak.getType().equals(LFreeHand.FreeHandType.CURVES))
//		{
//			float prevx = (float)ak.getPoint(-1).x;
//			float prevy = (float)ak.getPoint(-1).y;
//			float curx = (float)ak.getPoint(0).x;
//			float cury = (float)ak.getPoint(0).y;
//	        float midx = (curx + prevx) / 2.0f;
//	        float midy = (cury + prevy) / 2.0f;
//
//        	path.add(new SVGPathSegMoveto(curx, cury, false));
//
//	        if(size>interval)
//	        {
//	            prevx = curx;
//	            prevy = cury;
//	            curx = (float)ak.getPoint(interval).x;
//	            cury = (float)ak.getPoint(interval).y;
//	            midx = (curx + prevx) / 2.0f;
//	            midy = (cury + prevy) / 2.0f;
//
//	            path.add(new SVGPathSegLineto(midx, midy, false));
//	        }
//
//	        for(i=interval*2; i<size; i+=interval)
//	        {
//	        	 float x1 = (midx + curx) / 2.0f;
//	             float y1 = (midy + cury) / 2.0f;
//	             prevx = curx;
//	             prevy = cury;
//	             curx = (float)ak.getPoint(i).x;
//	             cury = (float)ak.getPoint(i).y;
//	             midx = (curx + prevx) / 2.0f;
//	             midy = (cury + prevy) / 2.0f;
//	             float x2 = (prevx + midx) / 2.0f;
//	             float y2 = (prevy + midy) / 2.0f;
//
//	             path.add(new SVGPathSegCurvetoCubic(midx, midy, x1, y1, x2, y2, false));
//	        }
//
//	        if((i-interval+1)<size)
//	        {
//	        	float x1 = (midx + curx) / 2.0f;
//	        	float y1 = (midy + cury) / 2.0f;
//	            prevx = curx;
//	            prevy = cury;
//	            curx = (float)ak.getPoint(-1).x;
//	            cury = (float)ak.getPoint(-1).y;
//	            midx = (curx + prevx) / 2.0f;
//	            midy = (cury + prevy) / 2.0f;
//	            float x2 = (prevx + midx) / 2.0f;
//	            float y2 = (prevy + midy) / 2.0f;
//
//	            path.add(new SVGPathSegCurvetoCubic(ak.getPoint(-1).x, ak.getPoint(-1).y, x1, y1, x2, y2, false));
//	        }
//		}
//		else
//		{
//			LPoint p = ak.getPoint(0);
//
//			path.add(new SVGPathSegMoveto(p.x, p.y, false));
//
//			for(i=interval; i<size; i+=interval)
//				path.add(new SVGPathSegLineto(ak.getPoint(i).x, ak.getPoint(i).y, false));
//
//			if((i-interval)<size)
//				path.add(new SVGPathSegLineto(ak.getPoint(-1).x, ak.getPoint(-1).y, false));
//		}
//
//		if(!ak.isOpen())
//			path.add(new SVGPathSegClosePath());

		return path;
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc)
	{
		if(doc==null)
			return null;

		SVGElement root = new SVGGElement(doc);
//		SVGElement elt;
//		LFreeHand ak 	= (LFreeHand)shape;
//
//		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_FREEHAND);
//		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
//		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_PATH_TYPE, String.valueOf(ak.getType()));
//		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_INTERVAL, String.valueOf(ak.getInterval()));
//		String path = getPath().toString();
//		String pts = "";//$NON-NLS-1$
//
//		for(int i = 0, size = ak.getNbPoints(); i < size; i++)
//				pts += ak.getPoint(i).x + " " + ak.getPoint(i).y + " ";//$NON-NLS-1$ //$NON-NLS-2$
//
//		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POINTS, pts);
//
//		if(shape.hasShadow())
//		{
//			SVGElement shad = new SVGPathElement(doc);
//
//			shad.setAttribute(SVGAttributes.SVG_D, path);
//			setSVGShadowAttributes(shad, false);
//			root.appendChild(shad);
//		}
//
//        if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE) && shape.isFilled())
//        {// The background of the borders must be filled is there is a shadow.
//    		elt = new SVGPathElement(doc);
//    		elt.setAttribute(SVGAttributes.SVG_D, path);
//    		setSVGBorderBackground(elt, root);
//        }
//
//		elt = new SVGPathElement(doc);
//		elt.setAttribute(SVGAttributes.SVG_D, path);
//		root.appendChild(elt);
//
//		setSVGAttributes(doc, elt, false);
//		elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ROTATION, String.valueOf(shape.getRotationAngle()));

		return root;
	}
}
