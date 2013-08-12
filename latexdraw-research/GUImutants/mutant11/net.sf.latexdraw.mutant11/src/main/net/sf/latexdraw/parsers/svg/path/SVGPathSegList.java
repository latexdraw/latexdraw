package net.sf.latexdraw.parsers.svg.path;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import net.sf.latexdraw.parsers.svg.path.SVGPathSeg.PathSeg;

/**
 * Defines a list of SVGPath segments.<br>
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
 * 10/20/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGPathSegList extends ArrayList<SVGPathSeg> implements SVGPathHandler {
	private static final long	serialVersionUID	= 1L;


	/**
	 * The constructor by default.
	 */
	public SVGPathSegList() {
		super();
	}


	@Override
	public String toString() {
		final StringBuilder path = new StringBuilder();

		for(SVGPathSeg seg : this)
			if(seg!=null) {
				path.append(seg.toString());
				path.append(' ');
			}

		return path.toString();
	}



	@Override
	public void onPathSeg(final SVGPathSeg pathSeg) {
		if(pathSeg!=null)
			add(pathSeg);
	}


	/**
	 * Returns the current point <b>with absolute values</b> at the end of the SVGPathSeg at the position <code>i</code>.
	 * @param pos The position of the SVGPathSeg to compute the current point.
	 * @return The current point <b>with absolute values</b>.
	 */
	@SuppressWarnings("incomplete-switch")
	public Point2D getCurrentPoint(final int pos) {
		if(pos<0 || pos>=size())
			return null;

		Point2D.Double pt = new Point2D.Double(), pt2;
		SVGPathSeg p = get(pos);

		switch(p.getType()) {
			case LINETO_ABS:
				pt.setLocation(((SVGPathSegLineto)p).getX(), ((SVGPathSegLineto)p).getY());
				break;

			case MOVETO_ABS:
				pt.setLocation(((SVGPathSegMoveto)p).getX(), ((SVGPathSegMoveto)p).getY());
				break;

			case CLOSEPATH:
				pt.setLocation(getInitialPoint(pos-1));
				break;

			case ARC_ABS:
				pt.setLocation(((SVGPathSegArc)p).getX(), ((SVGPathSegArc)p).getY());
				break;

			case CURVETO_CUBIC_ABS:
				pt.setLocation(((SVGPathSegCurvetoCubic)p).getX(), ((SVGPathSegCurvetoCubic)p).getY());
				break;

			case CURVETO_CUBIC_SMOOTH_ABS:
				pt.setLocation(((SVGPathSegCurvetoCubicSmooth)p).getX(), ((SVGPathSegCurvetoCubicSmooth)p).getY());
				break;

			case CURVETO_QUADRATIC_ABS:
				pt.setLocation(((SVGPathSegCurvetoQuadratic)p).getX(), ((SVGPathSegCurvetoQuadratic)p).getY());
				break;

			case CURVETO_QUADRATIC_SMOOTH_ABS:
				pt.setLocation(((SVGPathSegCurvetoQuadraticSmooth)p).getX(), ((SVGPathSegCurvetoQuadraticSmooth)p).getY());
				break;

			case LINETO_HORIZONTAL_ABS:
				pt2 = (Point2D.Double)getCurrentPoint(pos-1);

				if(pt2==null)
					pt=null;
				else
					pt.setLocation(((SVGPathSegLinetoHorizontal)p).getX(), pt2.getY());

				break;

			case LINETO_VERTICAL_ABS:
				pt2 = (Point2D.Double)getCurrentPoint(pos-1);

				if(pt2==null)
					pt=null;
				else
					pt.setLocation(pt2.getX(), ((SVGPathSegLinetoVertical)p).getY());

				break;
		}

		return pt;
	}



	/**
	 * Returns the initial point of the path containing the element at the position i.
	 * @param pos The position the begin the research.
	 * @return The initial point of the path.
	 * @since 0.1
	 */
	public Point2D getInitialPoint(final int pos) {
		if(pos<0 || pos>=size())
			return null;

		SVGPathSegMoveto m=null;
		int i = pos;

		while(m==null && i>0)
			if(get(i).getType()==PathSeg.MOVETO_ABS)
				m = (SVGPathSegMoveto)get(i);

		if(m==null)
			return null;

		return new Point2D.Double(m.getX(), m.getY());
	}
}
