package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
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
public class LArcSVGGenerator extends LEllipseSVGGenerator<IArc>
{
	public LArcSVGGenerator(final IArc shape)	{
		super(shape);
	}



	public LArcSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}



	/**
	 * Creates an arc from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @param withTransformation If true, the SVG transformations will be applied.
	 * @since 2.0.0
	 */
	public LArcSVGGenerator(final SVGGElement elt, final boolean withTransformation)
	{
		this((IArc)null);

//		IArc a 			= (IArc)getShape();
//		SVGElement elt2 = getLaTeXDrawElement(elt, null);
//		IArrow ah1 		= a.getArrowAt(0);
//		IArrow ah2 		= a.getArrowAt(1);
//
//		if(elt==null || elt2==null || !(elt2 instanceof SVGPathElement))
//			throw new IllegalArgumentException();
//
//		SVGPathElement main = (SVGPathElement)elt2;
//		SVGPathSegList l = main.getSegList();
//		double sx, sy;
//		IRectangle borders = a.getBorders();
//
//		if(l.size()<2 && !(l.get(0) instanceof SVGPathSegMoveto) && !(l.get(1) instanceof SVGPathSegArc))
//			throw new IllegalArgumentException();
//
//		sx = ((SVGPathSegMoveto)l.get(0)).getX();
//		sy = ((SVGPathSegMoveto)l.get(0)).getY();
//		Arc2D arc = ((SVGPathSegArc)l.get(1)).getArc2D(sx, sy);
//
//		a.setAngleStart(Math.toRadians(arc.getAngleStart())%(Math.PI*2));
//		a.setAngleEnd(Math.toRadians(arc.getAngleExtent()+arc.getAngleStart())%(Math.PI*2));
//		borders.getPoint(0).setLocation(arc.getMinX(), arc.getMinY());
//		borders.getPoint(1).setLocation(arc.getMaxX(), arc.getMinY());
//		borders.getPoint(2).setLocation(arc.getMinX(), arc.getMaxY());
//		borders.getPoint(3).setLocation(arc.getMaxX(), arc.getMaxY());
//
//		if(l.size()>2)
//			if(l.get(2) instanceof SVGPathSegClosePath)
//				a.setType(Arc2D.CHORD);
//			else
//				if(l.size()==4 && l.get(2) instanceof SVGPathSegLineto && l.get(3) instanceof SVGPathSegClosePath)
//					a.setType(Arc2D.PIE);
//
//		a.setShowPts(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHOW_PTS)!=null);
//		a.update();
//		a.updateGravityCentre();
//		setNumber(elt);
//		setSVGParameters(elt);
//		setSVGParameters(main);
//		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
//		a.updateCenterStartEndDelimitors();
//		setSVGArrow(ah1, main.getAttribute(main.getUsablePrefix()+SVGAttributes.SVG_MARKER_START), main);
//		setSVGArrow(ah2, main.getAttribute(main.getUsablePrefix()+SVGAttributes.SVG_MARKER_END), main);
//		homogeniseArrows(ah1, ah2);

		if(withTransformation)
			applyTransformations(elt);
	}


	@Override
	public SVGElement toSVG(final SVGDocument doc)
	{
		if(doc==null || doc.getFirstChild().getDefs()==null)
			return null;

//		IArc arc 			 = (IArc)shape;
//		SVGDefsElement defs  = doc.getFirstChild().getDefs();
//		double rotationAngle = shape.getRotationAngle();
//		IArrow arrowHead1 	 = arc.getArrowAt(0);
//		IArrow arrowHead2 	 = arc.getArrowAt(1);
//		double startAngle    =  arc.getAngleStart();
//		double endAngle      =  arc.getAngleEnd();
//		int type 			 =  arc.getType();
//		IPoint gravityCenter = shape.getGravityCentre();
        SVGElement root = new SVGGElement(doc);
//        SVGElement elt;
//        int number 		= shape.getId();
//        root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_ARC);
//        root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
//        IPoint start = arc.getStartPoint(), end = arc.getEndPoint();
//        double radius = arc.getRadius();
//        boolean largeArcFlag;
//        SVGElement arrow1 = null, arrow2 = null, arrow1Shad = null, arrow2Shad = null;
//        String arrow1Name = "arrow1-" + number; //$NON-NLS-1$
// 	   	String arrow2Name = "arrow2-" + number;//$NON-NLS-1$
// 	   	String arrow1ShadName = "arrow1Shad-" + number;//$NON-NLS-1$
// 	   	String arrow2ShadName = "arrow2Shad-" + number;//$NON-NLS-1$
//
// 	   	startAngle%=2.*Math.PI;
// 	   	endAngle%=2.*Math.PI;
//
//        if(!arrowHead1.isWithoutStyle()) {
// 			arrow1 		= new LArrowHeadSVGGenerator(arrowHead1).toSVG(doc, false);
// 			arrow1Shad 	= new LArrowHeadSVGGenerator(arrowHead1).toSVG(doc, true);
//
// 			arrow1.setAttribute(SVGAttributes.SVG_ID, arrow1Name);
// 			defs.appendChild(arrow1);
//
// 			if(shape.hasShadow()) {
// 				arrow1Shad.setAttribute(SVGAttributes.SVG_ID, arrow1ShadName);
// 				defs.appendChild(arrow1Shad);
// 			}
//        }
//
//        if(!arrowHead2.isWithoutStyle()) {
//     	   arrow2 		= new LArrowHeadSVGGenerator(arrowHead2).toSVG(doc, false);
//     	   arrow2Shad 	= new LArrowHeadSVGGenerator(arrowHead2).toSVG(doc, true);
//
//     	   arrow2.setAttribute(SVGAttributes.SVG_ID, arrow2Name);
//     	   defs.appendChild(arrow2);
//
// 			if(shape.hasShadow()) {
// 	    	   arrow2Shad.setAttribute(SVGAttributes.SVG_ID, arrow2ShadName);
// 	    	   defs.appendChild(arrow2Shad);
// 			}
//        }
//
//        largeArcFlag = startAngle>endAngle ? 2.*Math.PI-startAngle+endAngle>=Math.PI : endAngle-startAngle>Math.PI;
//
//        SVGPathSegList path = new SVGPathSegList();
//
//        path.add(new SVGPathSegMoveto(start.getX(), start.getY(), false));
//        path.add(new SVGPathSegArc(end.getX(), end.getY(), radius, radius, 0, largeArcFlag, false, false));
//
//        if(type==Arc2D.CHORD)
//        	path.add(new SVGPathSegClosePath());
//        else
//        	if(type==Arc2D.PIE) {
//        		path.add(new SVGPathSegLineto(gravityCenter.getX(), gravityCenter.getY(), false));
//        		path.add(new SVGPathSegClosePath());
//        	}
//
//        if(shape.hasShadow())  {
//     	   SVGElement shad = new SVGPathElement(doc);
//
//     	   shad.setAttribute(SVGAttributes.SVG_D, path.toString());
//     	   setSVGShadowAttributes(shad, true);
//     	   root.appendChild(shad);
//
//           if(arrow1Shad!=null)
//        	   shad.setAttribute(SVGAttributes.SVG_MARKER_START, SVG_URL_TOKEN_BEGIN + arrow1ShadName + ')');
//
//           if(arrow2Shad!=null)
//        	   shad.setAttribute(SVGAttributes.SVG_MARKER_END, SVG_URL_TOKEN_BEGIN + arrow2ShadName + ')');
//        }
//
//        if(shape.hasShadow() && !shape.getLineStyle().equals(PSTricksConstants.LINE_NONE_STYLE))
//        {// The background of the borders must be filled is there is a shadow.
//            elt = new SVGPathElement(doc);
//            elt.setAttribute(SVGAttributes.SVG_D, path.toString());
//            setSVGBorderBackground(elt, root);
//        }
//
//        elt = new SVGPathElement(doc);
//        elt.setAttribute(SVGAttributes.SVG_D, path.toString());
//        root.appendChild(elt);
//
//        setSVGRotationAttribute(root);
//        setSVGAttributes(doc, elt, true);
//        elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE +':'+ LNamespace.XML_ROTATION, String.valueOf(rotationAngle));
//
//        if(arrow1!=null)
//     	   elt.setAttribute(SVGAttributes.SVG_MARKER_START, SVG_URL_TOKEN_BEGIN + arrow1Name + ')');
//
//        if(arrow2!=null)
//     	   elt.setAttribute(SVGAttributes.SVG_MARKER_END, SVG_URL_TOKEN_BEGIN + arrow2Name + ')');
//
//        if(arc.isShowPts())
//        	root.appendChild(getShowPointsElement(doc));

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
//		double thickness	= arc.getThickness()/2.;

		showPts.setAttribute(new StringBuffer(LNamespace.LATEXDRAW_NAMESPACE).append(':').append(
				LNamespace.XML_TYPE).toString(), LNamespace.XML_TYPE_SHOW_PTS);

//		showPts.appendChild(getShowPointsLine(doc, thickness, arc.getLineColour(), arc.getGravityCentre(),
//				arc.getNonRotatedStartPoint(), arc.getDashSepBlack(), arc.getDashSepWhite(), false, 1));
//		showPts.appendChild(getShowPointsLine(doc, thickness, arc.getLineColour(), arc.getGravityCentre(),
//				arc.getNonRotatedEndPoint(), arc.getDashSepBlack(), arc.getDashSepWhite(), false, 1));

		return showPts;
	}
}
