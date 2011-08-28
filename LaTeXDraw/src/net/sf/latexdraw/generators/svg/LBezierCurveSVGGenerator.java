package net.sf.latexdraw.generators.svg;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShapeFactory;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for a Bézier curve.<br>
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
public class LBezierCurveSVGGenerator extends LShapeSVGGenerator<IBezierCurve> {
	/**
	 * Creates a generator of SVG bezier curve.
	 * @param bc The bezier curve used for the generation.
	 * @throws IllegalArgumentException If bc is null.
	 * @since 2.0
	 */
	public LBezierCurveSVGGenerator(final IBezierCurve bc){
		super(bc);
	}


	/**
	 * Creates a rectangle from a G SVG element.
	 * @param elt The G SVG element used for the creation of a rectangle.
	 * @throws IllegalArgumentException If the given element is null.
	 * @since 2.0
	 */
	public LBezierCurveSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}


	/**
	 * Creates a Bézier curve from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	public LBezierCurveSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(DrawingTK.getFactory().createBezierCurve(false));

		SVGElement elt2 = getLaTeXDrawElement(elt, null);

		if(elt==null || !(elt2 instanceof SVGPathElement))
			throw new IllegalArgumentException();

		SVGPathElement main = (SVGPathElement)elt2;
//		IArrow arrowHead1 	= shape.getArrowAt(0);
//		IArrow arrowHead2 	= shape.getArrowAt(1);

		setPath(main.getSegList());
		setNumber(elt);
		setSVGParameters(main);
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

//		IPoint pt1 = DrawingTK.getFactory().createPoint(bc.getPtAt(0));
//		IPoint pt2 = DrawingTK.getFactory().createPoint(bc.getPtAt(-1));

//		arrowHead1.setPosition(pt1);
//		arrowHead1.setLine(new LLine(pt1, bc.getFirstCtrlPtAt(0)));
//		arrowHead1.setShape(bc);
//		arrowHead2.setPosition(pt2);
//		arrowHead2.setLine(new LLine(pt2, bc.getFirstCtrlPtAt(-1)));
//		arrowHead2.setShape(bc);

//		setSVGArrow(arrowHead1, main.getAttribute(main.getUsablePrefix()+SVGAttributes.SVG_MARKER_START), main);//TODO
//		setSVGArrow(arrowHead2, main.getAttribute(main.getUsablePrefix()+SVGAttributes.SVG_MARKER_END), main);
//		homogeniseArrows(arrowHead1, arrowHead2);

		shape.setShowPts(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHOW_PTS)!=null);
		shape.update();

		if(withTransformation)
			applyTransformations(elt);
	}



	/**
	 * Sets the shape path according to the given SVG path segments.
	 * @param list The SVG path segments list
	 * @since 2.0.0
	 */
	public void setPath(final SVGPathSegList list) {
		if(list==null || list.size()<2 || !(list.get(0) instanceof SVGPathSegMoveto))
			throw new IllegalArgumentException();

		SVGPathSegMoveto m 	= (SVGPathSegMoveto)list.get(0);
		SVGPathSegCurvetoCubic c;
		int i=1, size = list.size();

		IShapeFactory factory = DrawingTK.getFactory();
		shape.addPoint(factory.createPoint(m.getX(), m.getY()));

		if(i>0 && list.get(1) instanceof SVGPathSegCurvetoCubic) {// We set the control point of the first point.
			c = (SVGPathSegCurvetoCubic)list.get(1);
			shape.getFirstCtrlPtAt(-1).setPoint(c.getX1(), c.getY1());
		}

		while(i<size && list.get(i) instanceof SVGPathSegCurvetoCubic) {
			c = (SVGPathSegCurvetoCubic)list.get(i);
			shape.addPoint(factory.createPoint(c.getX(), c.getY()));
			shape.getFirstCtrlPtAt(-1).setPoint(c.getX2(), c.getY2());
			i++;
		}

		shape.removePoint(0); // We remove the two first points created during the first initialisation.
		shape.removePoint(0);

		if(shape.getPtAt(-1).equals(shape.getPtAt(0), 0.00001)) {// We set the shape as closed
			shape.removePoint(-1);
			shape.setIsClosed(true);
		}
		else
			if(i<size && list.get(i) instanceof SVGPathSegClosePath)// There is something else at the end of the path.
				shape.setIsClosed(true);
			else
				shape.setIsClosed(false);

		shape.updateSecondControlPoints();
		shape.update();
	}




	/**
	 * @return The SVG segment path list of the current Bézier curve.
	 * @since 2.0.0
	 */
	protected SVGPathSegList getPathSegList() {
		if(shape.getNbPoints()<2)
			return null;

		int size 			= shape.getNbPoints(), i;
		SVGPathSegList path = new SVGPathSegList();

		path.add(new SVGPathSegMoveto(shape.getPtAt(0).getX(), shape.getPtAt(0).getY(), false));
		path.add(new SVGPathSegCurvetoCubic(shape.getPtAt(1).getX(), shape.getPtAt(1).getY(), shape.getFirstCtrlPtAt(0).getX(),
				shape.getFirstCtrlPtAt(0).getY(), shape.getFirstCtrlPtAt(1).getX(), shape.getFirstCtrlPtAt(1).getY(), false));

		for(i=2; i<size; i++)
			path.add(new SVGPathSegCurvetoCubic(shape.getPtAt(i).getX(), shape.getPtAt(i).getY(),
												shape.getSecondCtrlPtAt(i-1).getX(), shape.getSecondCtrlPtAt(i-1).getY(),
												shape.getFirstCtrlPtAt(i).getX(), shape.getFirstCtrlPtAt(i).getY(), false));

		if(shape.isClosed()) {
			if(shape.isClosed()) {
				IPoint ctrl1b = shape.getFirstCtrlPtAt(0).centralSymmetry(shape.getPtAt(0));
				IPoint ctrl2b = shape.getFirstCtrlPtAt(-1).centralSymmetry(shape.getPtAt(-1));

				path.add(new SVGPathSegCurvetoCubic(shape.getPtAt(0).getX(), shape.getPtAt(0).getY(), ctrl2b.getX(), ctrl2b.getY(), ctrl1b.getX(), ctrl1b.getY(), false));
			}

			path.add(new SVGPathSegClosePath());
		}

		return path;
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null || doc.getFirstChild().getDefs()==null)
			return null;

//		SVGDefsElement defs 	= doc.getFirstChild().getDefs();
		SVGElement root 		= new SVGGElement(doc), elt;
//		IArrow arrowHead1 		= shape.getArrowAt(0);//TODO
//		IArrow arrowHead2 		= shape.getArrowAt(1);
		String path 			= getPathSegList().toString();
//		SVGElement arrow1 		= null, arrow2 = null, arrow1Shad = null, arrow2Shad = null;
//		String arrow1Name 		= "arrow1-" + shape.getId();//$NON-NLS-1$
//		String arrow2Name 		= "arrow2-" + shape.getId();//$NON-NLS-1$
//		String arrow1ShadName 	= "arrow1Shad-" + shape.getId();//$NON-NLS-1$
//		String arrow2ShadName 	= "arrow2Shad-" + shape.getId();//$NON-NLS-1$

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_BEZIER_CURVE);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

//		if(!arrowHead1.isWithoutStyle()) {
//			arrow1     = new LArrowHeadSVGGenerator(arrowHead1).toSVG(doc, false);
//			arrow1Shad = new LArrowHeadSVGGenerator(arrowHead1).toSVG(doc, true);
//
//			arrow1.setAttribute(SVGAttributes.SVG_ID, arrow1Name);
//			defs.appendChild(arrow1);
//
//			if(shape.hasShadow()) {
//				arrow1Shad.setAttribute(SVGAttributes.SVG_ID, arrow1ShadName);
//				defs.appendChild(arrow1Shad);
//			}
//		}
//
//		if(!arrowHead2.isWithoutStyle() && !shape.isClosed()) {
//			arrow2     = new LArrowHeadSVGGenerator(arrowHead2).toSVG(doc, false);
//			arrow2Shad = new LArrowHeadSVGGenerator(arrowHead2).toSVG(doc, true);
//
//			arrow2.setAttribute(SVGAttributes.SVG_ID, arrow2Name);
//			defs.appendChild(arrow2);
//
//			if(shape.hasShadow()) {
//				arrow2Shad.setAttribute(SVGAttributes.SVG_ID, arrow2ShadName);
//				defs.appendChild(arrow2Shad);
//			}
//		}

       	if(shape.hasShadow()) {
       		SVGElement shad = new SVGPathElement(doc);

			shad.setAttribute(SVGAttributes.SVG_D, path);
			setSVGShadowAttributes(shad, false);
			root.appendChild(shad);

//			if(arrow1Shad != null)
//				shad.setAttribute(SVGAttributes.SVG_MARKER_START, SVG_URL_TOKEN_BEGIN + arrow1ShadName + ')');
//
//			if(arrow2Shad != null)
//				shad.setAttribute(SVGAttributes.SVG_MARKER_END, SVG_URL_TOKEN_BEGIN + arrow2ShadName + ')');
		}

        if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE) && shape.isFilled()) {
        	// The background of the borders must be filled is there is a shadow.
    		elt = new SVGPathElement(doc);
    		elt.setAttribute(SVGAttributes.SVG_D, path);
    		setSVGBorderBackground(elt, root);
        }

		elt = new SVGPathElement(doc);
		elt.setAttribute(SVGAttributes.SVG_D, path);
		root.appendChild(elt);

		if(shape.hasDbleBord()) {
			SVGElement dblBord = new SVGPathElement(doc);
			dblBord.setAttribute(SVGAttributes.SVG_D, path);
			setSVGDoubleBordersAttributes(dblBord);
			root.appendChild(dblBord);
		}

		setSVGAttributes(doc, elt, false);
		elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE +':'+ LNamespace.XML_ROTATION, String.valueOf(shape.getRotationAngle()));

//		if(arrow1 != null)
//			elt.setAttribute(SVGAttributes.SVG_MARKER_START, SVG_URL_TOKEN_BEGIN + arrow1Name + ')');
//
//		if(arrow2 != null)
//			elt.setAttribute(SVGAttributes.SVG_MARKER_END, SVG_URL_TOKEN_BEGIN + arrow2Name + ')');

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

		double blackDash  = shape.getDashSepBlack();
		double whiteDash  = shape.getDashSepWhite();
		boolean hasDble   = shape.hasDbleBord();
		Color col         = shape.getLineColour();
		boolean isClosed  = shape.isClosed();
		SVGGElement showPts  = new SVGGElement(doc);
//		IArrow arrowHead1 = shape.getArrowAt(0);
//		IArrow arrowHead2 = shape.getArrowAt(1);
		final double doubleSep = shape.getDbleBordSep();
		double thick = (hasDble ? shape.getDbleBordSep()+shape.getThickness()*2. : shape.getThickness())/2.;
		double rad   = (PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM*IShape.PPC + PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM*thick*2.)/2.;
		int i = 0, size = shape.getNbPoints();
//
		showPts.setAttribute(new StringBuffer(LNamespace.LATEXDRAW_NAMESPACE).append(':').append(
											LNamespace.XML_TYPE).toString(), LNamespace.XML_TYPE_SHOW_PTS);

		/* Plotting the lines. */
		for(i=3; i<size; i+=2) {
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getPtAt(i-1), shape.getSecondCtrlPtAt(i-1),
								blackDash, whiteDash, hasDble, 1., doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getSecondCtrlPtAt(i-1), shape.getFirstCtrlPtAt(i),
					  			blackDash, whiteDash, hasDble, 1., doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getFirstCtrlPtAt(i), shape.getPtAt(i),
					  			blackDash, whiteDash, hasDble, 1., doubleSep));
		}

		for(i=2; i<size; i+=2) {
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getPtAt(i-1), shape.getSecondCtrlPtAt(i-1),
								blackDash, whiteDash, hasDble, 1., doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getSecondCtrlPtAt(i-1), shape.getFirstCtrlPtAt(i),
					  			blackDash, whiteDash, hasDble, 1., doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getFirstCtrlPtAt(i), shape.getPtAt(i),
					  			blackDash, whiteDash, hasDble, 1., doubleSep));
		}

		if(isClosed) {
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getPtAt(-1), shape.getSecondCtrlPtAt(-1),
								blackDash, whiteDash, hasDble, 1., doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getSecondCtrlPtAt(-1), shape.getSecondCtrlPtAt(0),
					  			blackDash, whiteDash, hasDble, 1., doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getSecondCtrlPtAt(0), shape.getPtAt(0),
					  			blackDash, whiteDash, hasDble, 1., doubleSep));
		}

		showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getPtAt(0), shape.getFirstCtrlPtAt(0),
							blackDash, whiteDash, hasDble, 1., doubleSep));
		showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getFirstCtrlPtAt(0), shape.getFirstCtrlPtAt(1),
							blackDash, whiteDash, hasDble, 1., doubleSep));
		showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getFirstCtrlPtAt(1), shape.getPtAt(1),
							blackDash, whiteDash, hasDble, 1., doubleSep));

		// Plotting the dots.
//		if(arrowHead1.isWithoutStyle() || (!isOpen && closeType==BezierCurve.CLOSE_TYPE_CURVE))
			showPts.appendChild(LShapeSVGGenerator.getShowPointsDot(doc, rad, shape.getPtAt(0), col));

//		if(arrowHead2.isWithoutStyle() || (!isOpen && closeType==BezierCurve.CLOSE_TYPE_CURVE))
			showPts.appendChild(LShapeSVGGenerator.getShowPointsDot(doc, rad, shape.getPtAt(-1), col));

		for(i=1; i<size-1; i++) {
			showPts.appendChild(LShapeSVGGenerator.getShowPointsDot(doc, rad, shape.getPtAt(i), col));
			showPts.appendChild(LShapeSVGGenerator.getShowPointsDot(doc, rad, shape.getSecondCtrlPtAt(i), col));
		}

		for(i=0; i<size; i++)
			showPts.appendChild(LShapeSVGGenerator.getShowPointsDot(doc, rad, shape.getFirstCtrlPtAt(i), col));

		if(isClosed) {
			showPts.appendChild(LShapeSVGGenerator.getShowPointsDot(doc, rad, shape.getSecondCtrlPtAt(-1), col));
			showPts.appendChild(LShapeSVGGenerator.getShowPointsDot(doc, rad, shape.getSecondCtrlPtAt(0), col));
		}

		return showPts;
	}
}
