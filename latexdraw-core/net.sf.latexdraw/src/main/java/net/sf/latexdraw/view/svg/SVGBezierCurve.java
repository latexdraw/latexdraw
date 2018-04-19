/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.svg;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.path.CtrlPointsSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * An SVG generator for a Bézier curve.
 * @author Arnaud BLOUIN
 */
class SVGBezierCurve extends SVGModifiablePointsShape<IBezierCurve> {
	/**
	 * Creates a bezier curve and initialises its path from an SVG element.
	 */
	private static IBezierCurve pathToBezierCurve(final SVGElement elt) {
		if(!(elt instanceof SVGPathElement)) {
			return null;
		}

		final SVGPathSegList list = ((SVGPathElement) elt).getSegList();

		if(list == null || list.size() < 2 || !(list.get(0) instanceof SVGPathSegMoveto)) {
			return null;
		}

		final SVGPathSegMoveto m = (SVGPathSegMoveto) list.get(0);
		CtrlPointsSeg c;
		int i = 1;
		final int size = list.size();
		Point2D pt = new Point2D.Double(); // Creating a point to support when the first path element is relative.
		final List<IPoint> pts = new ArrayList<>();
		final List<IPoint> ctrlpts = new ArrayList<>();
		final boolean closed;

		pt = m.getPoint(pt);
		pts.add(ShapeFactory.INST.createPoint(pt));

		if(list.get(1) instanceof CtrlPointsSeg) {// We set the control point of the first point.
			c = (CtrlPointsSeg) list.get(1);
			ctrlpts.add(ShapeFactory.INST.createPoint(c.getCtrl1(pt)));
		}

		while(i < size && list.get(i) instanceof CtrlPointsSeg) {
			c = (CtrlPointsSeg) list.get(i);
			final Point2D currPt = c.getPoint(pt);
			pts.add(ShapeFactory.INST.createPoint(currPt));
			ctrlpts.add(ShapeFactory.INST.createPoint(c.getCtrl2(pt)));
			pt = currPt;
			i++;
		}

		if(pts.size() > 2 && pts.get(pts.size() - 1).equals(pts.get(0), 0.00001)) { // We set the shape as closed
			pts.remove(pts.size() - 1);
			ctrlpts.remove(ctrlpts.size() - 1);
			closed = true;
		}else {
			closed = i < size && list.get(i) instanceof SVGPathSegClosePath; // There is something else at the end of the path.
		}

		final IBezierCurve bc = ShapeFactory.INST.createBezierCurve(pts, ctrlpts);
		bc.setOpened(!closed);
		return bc;
	}


	/**
	 * Creates a generator of SVG bezier curve.
	 * @param bc The bezier curve used for the generation.
	 * @throws IllegalArgumentException If bc is null.
	 */
	protected SVGBezierCurve(final IBezierCurve bc) {
		super(bc);
	}


	/**
	 * Creates a Bézier curve from a latexdraw-SVG element.
	 * @param elt The source element.
	 */
	protected SVGBezierCurve(final SVGGElement elt, final boolean withTransformation) {
		this(pathToBezierCurve(getLaTeXDrawElement(elt, null)));

		final SVGElement main = getLaTeXDrawElement(elt, null);
		setSVGParameters(main);
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));
		final IArrow arrow1 = shape.getArrowAt(0);
		final IArrow arrow2 = shape.getArrowAt(-1);
		setSVGArrow(arrow1, main.getAttribute(main.getUsablePrefix() + SVGAttributes.SVG_MARKER_START), main, SVGAttributes.SVG_MARKER_START);
		setSVGArrow(arrow2, main.getAttribute(main.getUsablePrefix() + SVGAttributes.SVG_MARKER_END), main, SVGAttributes.SVG_MARKER_END);
		homogeniseArrows(arrow1, arrow2);

		shape.setShowPts(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHOW_PTS) != null);

		setRotationAngle(main);

		if(withTransformation) {
			applyTransformations(elt);
		}
	}

	protected SVGBezierCurve(final SVGPathElement path) {
		this(pathToBezierCurve(path));
		setSVGParameters(path);
		applyTransformations(path);
	}

	/**
	 * @return The SVG segment path list of the current Bézier curve.
	 * @since 2.0.0
	 */
	protected SVGPathSegList getPathSegList() {
		if(shape.getNbPoints() < 2) {
			return null;
		}

		final int size = shape.getNbPoints();
		int i;
		final SVGPathSegList path = new SVGPathSegList();

		path.add(new SVGPathSegMoveto(shape.getPtAt(0).getX(), shape.getPtAt(0).getY(), false));
		path.add(new SVGPathSegCurvetoCubic(shape.getPtAt(1).getX(), shape.getPtAt(1).getY(),
			shape.getFirstCtrlPtAt(0).getX(), shape.getFirstCtrlPtAt(0).getY(),
			shape.getFirstCtrlPtAt(1).getX(), shape.getFirstCtrlPtAt(1).getY(), false));

		for(i = 2; i < size; i++) {
			path.add(new SVGPathSegCurvetoCubic(shape.getPtAt(i).getX(), shape.getPtAt(i).getY(), shape.getSecondCtrlPtAt(i - 1).getX(),
				shape.getSecondCtrlPtAt(i - 1).getY(), shape.getFirstCtrlPtAt(i).getX(), shape.getFirstCtrlPtAt(i).getY(), false));
		}

		if(!shape.isOpened()) {
			final IPoint ctrl1b = shape.getFirstCtrlPtAt(0).centralSymmetry(shape.getPtAt(0));
			final IPoint ctrl2b = shape.getFirstCtrlPtAt(-1).centralSymmetry(shape.getPtAt(-1));

			path.add(new SVGPathSegCurvetoCubic(shape.getPtAt(0).getX(), shape.getPtAt(0).getY(), ctrl2b.getX(), ctrl2b.getY(),
				ctrl1b.getX(), ctrl1b.getY(), false));

			path.add(new SVGPathSegClosePath());
		}

		return path;
	}


	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc == null || doc.getFirstChild().getDefs() == null) {
			return null;
		}

		final SVGDefsElement defs = doc.getFirstChild().getDefs();
		final SVGElement root = new SVGGElement(doc);
		SVGElement elt;
		final String path = getPathSegList().toString();

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_BEZIER_CURVE);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

		if(shape.hasShadow()) {
			final SVGElement shad = new SVGPathElement(doc);

			shad.setAttribute(SVGAttributes.SVG_D, path);
			setSVGShadowAttributes(shad, false);
			root.appendChild(shad);

			if(shape.isOpened()) {
				setSVGArrow(shape, shad, 0, true, doc, defs);
				setSVGArrow(shape, shad, 1, true, doc, defs);
			}
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
			final SVGElement dblBord = new SVGPathElement(doc);
			dblBord.setAttribute(SVGAttributes.SVG_D, path);
			setSVGDoubleBordersAttributes(dblBord);
			root.appendChild(dblBord);
		}

		setSVGAttributes(doc, elt, false);
		elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ROTATION, String.valueOf(shape.getRotationAngle()));

		if(shape.isOpened()) {
			setSVGArrow(shape, elt, 0, false, doc, defs);
			setSVGArrow(shape, elt, 1, false, doc, defs);
		}

		if(shape.isShowPts()) {
			root.appendChild(getShowPointsElement(doc));
		}

		return root;
	}


	/**
	 * Creates an SVG g element that contains the 'show points' plotting.
	 * @param doc The owner document.
	 * @return The created g element or null if the shape has not the 'show points' option activated.
	 * @since 2.0.0
	 */
	protected SVGGElement getShowPointsElement(final SVGDocument doc) {
		if(!shape.isShowPts() || doc == null) {
			return null;
		}

		final double blackDash = shape.getDashSepBlack();
		final double whiteDash = shape.getDashSepWhite();
		final boolean hasDble = shape.hasDbleBord();
		final Color col = shape.getLineColour();
		final boolean isClosed = !shape.isOpened();
		final SVGGElement showPts = new SVGGElement(doc);
		final IArrow arrow1 = shape.getArrowAt(0);
		final IArrow arrow2 = shape.getArrowAt(-1);
		final double doubleSep = shape.getDbleBordSep();
		final double thick = (hasDble ? shape.getDbleBordSep() + shape.getThickness() * 2. : shape.getThickness()) / 2.;
		final double rad = (PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM * IShape.PPC + PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM * thick * 2.) / 2.;
		int i;
		final int size = shape.getNbPoints();

		showPts.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_SHOW_PTS);

		/* Plotting the lines. */
		for(i = 3; i < size; i += 2) {
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getPtAt(i - 1), shape.getSecondCtrlPtAt(i - 1), blackDash, whiteDash, hasDble, 1.,
				doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getSecondCtrlPtAt(i - 1), shape.getFirstCtrlPtAt(i), blackDash, whiteDash, hasDble,
				1., doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getFirstCtrlPtAt(i), shape.getPtAt(i), blackDash, whiteDash, hasDble, 1., doubleSep));
		}

		for(i = 2; i < size; i += 2) {
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getPtAt(i - 1), shape.getSecondCtrlPtAt(i - 1), blackDash, whiteDash, hasDble, 1.,
				doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getSecondCtrlPtAt(i - 1), shape.getFirstCtrlPtAt(i), blackDash, whiteDash, hasDble,
				1., doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getFirstCtrlPtAt(i), shape.getPtAt(i), blackDash, whiteDash, hasDble, 1., doubleSep));
		}

		if(isClosed) {
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getPtAt(-1), shape.getSecondCtrlPtAt(-1), blackDash, whiteDash, hasDble, 1.,
				doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getSecondCtrlPtAt(-1), shape.getSecondCtrlPtAt(0), blackDash, whiteDash, hasDble, 1.,
				doubleSep));
			showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getSecondCtrlPtAt(0), shape.getPtAt(0), blackDash, whiteDash, hasDble, 1.,
				doubleSep));
		}

		showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getPtAt(0), shape.getFirstCtrlPtAt(0), blackDash, whiteDash, hasDble, 1., doubleSep));
		showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getFirstCtrlPtAt(0), shape.getFirstCtrlPtAt(1), blackDash, whiteDash, hasDble, 1.,
			doubleSep));
		showPts.appendChild(getShowPointsLine(doc, thick, col, shape.getFirstCtrlPtAt(1), shape.getPtAt(1), blackDash, whiteDash, hasDble, 1., doubleSep));

		// Plotting the dots.
		if(!arrow1.hasStyle() || isClosed) {
			showPts.appendChild(SVGShape.getShowPointsDot(doc, rad, shape.getPtAt(0), col));
		}

		if(!arrow2.hasStyle() || isClosed) {
			showPts.appendChild(SVGShape.getShowPointsDot(doc, rad, shape.getPtAt(-1), col));
		}

		for(i = 1; i < size - 1; i++) {
			showPts.appendChild(SVGShape.getShowPointsDot(doc, rad, shape.getPtAt(i), col));
			showPts.appendChild(SVGShape.getShowPointsDot(doc, rad, shape.getSecondCtrlPtAt(i), col));
		}

		for(i = 0; i < size; i++) {
			showPts.appendChild(SVGShape.getShowPointsDot(doc, rad, shape.getFirstCtrlPtAt(i), col));
		}

		if(isClosed) {
			showPts.appendChild(SVGShape.getShowPointsDot(doc, rad, shape.getSecondCtrlPtAt(-1), col));
			showPts.appendChild(SVGShape.getShowPointsDot(doc, rad, shape.getSecondCtrlPtAt(0), col));
		}

		return showPts;
	}
}
