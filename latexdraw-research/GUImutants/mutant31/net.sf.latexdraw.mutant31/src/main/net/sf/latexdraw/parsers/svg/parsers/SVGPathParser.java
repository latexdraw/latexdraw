package net.sf.latexdraw.parsers.svg.parsers;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.path.*;

/**
 * Defines an SVGPath parser.<br>
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
 * @since 0.1
 */
public class SVGPathParser extends SVGNumberParser {
	/** The handler of the SVGPath. @since 0.1 */
	protected SVGPathHandler handler;


	/**
	 * The main constructor.
	 * @param code The path to parse.
	 * @param handler The handler of the path.
	 */
	public SVGPathParser(final String code, final SVGPathHandler handler) {
		super(code);

		this.handler = handler;
	}



	@Override
	public void parse() throws ParseException {
		setPosition(0);
		skipWSP();

		if(getChar()!='m' && getChar()!='M')// The first command must be a moveto command.
			throw new ParseException("moveto command (m|M) expected.", getPosition());//$NON-NLS-1$

		parseMoveto(false);// If a relative moveto (m) appears as the first element of the path, then it is treated as a pair of absolute coordinates.

		while(!isEOC()) {
			skipWSP();

			switch(getChar()) {
				case EOC:
					break;

				case 'Z':
				case 'z':
					parseClosepath();
					break;

				case 'm':
					parseMoveto(true);
					break;

				case 'M':
					parseMoveto(false);
					break;

				case 'l':
					parseLineto(true);
					break;

				case 'L':
					parseLineto(false);
					break;

				case 'h':
					parseHorizontalLineto(true);
					break;

				case 'H':
					parseHorizontalLineto(false);
					break;

				case 'v':
					parseVerticalLineto(true);
					break;

				case 'V':
					parseVerticalLineto(false);
					break;

				case 'a':
					parseEllipticalArcto(true);
					break;

				case 'A':
					parseEllipticalArcto(false);
					break;

				case 'c':
					parseCurveto(true);
					break;

				case 'C':
					parseCurveto(false);
					break;

				case 'q':
					parseQuadraticBezierCurveto(true);
					break;

				case 'Q':
					parseQuadraticBezierCurveto(false);
					break;

				case 't':
					parseShorthandQuadraticBezierCurveto(true);
					break;

				case 'T':
					parseShorthandQuadraticBezierCurveto(false);
					break;

				case 's':
					parseShorthandCurveto(true);
					break;

				case 'S':
					parseShorthandCurveto(false);
					break;

				default:
					throw new ParseException("Invalid token:" + getChar(), getPosition());//$NON-NLS-1$
			}
		}
	}


	/**
	 * Parses an SVGPath smooth curveto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 * @since 0.1
	 */
	protected void parseShorthandCurveto(final boolean isRelative) throws ParseException {
		double x, y, x2, y2;

		nextChar();
		skipWSP();
		x2 = parseNumber(false);
		skipWSPComma();
		y2 = parseNumber(false);
		skipWSPComma();
		x = parseNumber(false);
		skipWSPComma();
		y = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegCurvetoCubicSmooth(x, y, x2, y2, isRelative));

		while(!isEOC() && isNumber(false)) {
			x2 = parseNumber(false);
			skipWSPComma();
			y2 = parseNumber(false);
			skipWSPComma();
			x = parseNumber(false);
			skipWSPComma();
			y = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegCurvetoCubicSmooth(x, y, x2, y2, isRelative));
		}

		nextChar();
	}



	/**
	 * Parses an SVGPath closepath.
	 */
	protected void parseClosepath() {
		handler.onPathSeg(new SVGPathSegClosePath());
		nextChar();
	}


	/**
	 * Parses an SVGPath horizontal lineto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseHorizontalLineto(final boolean isRelative) throws ParseException {
		double h;

		nextChar();
		skipWSP();
		h = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegLinetoHorizontal(h, isRelative));

		while(!isEOC() && isNumber(false)) {
			h = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegLinetoHorizontal(h, isRelative));
		}
	}


	/**
	 * Parses an SVGPath vertical lineto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseVerticalLineto(final boolean isRelative) throws ParseException {
		double v;

		nextChar();
		skipWSP();
		v = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegLinetoVertical(v, isRelative));

		while(!isEOC() && isNumber(false)) {
			v = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegLinetoVertical(v, isRelative));
		}
	}


	/**
	 * Parses an SVGPath arc.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseEllipticalArcto(final boolean isRelative) throws ParseException {
		double x, y, rx, ry, angle;
		boolean laf, sf;

		nextChar();
		skipWSP();
		rx = parseNumber(true);
		skipWSPComma();
		ry = parseNumber(true);
		skipWSPComma();
		angle = parseNumber(false);
		skipWSPComma();
		laf = parseFlag();
		skipWSPComma();
		sf = parseFlag();
		skipWSPComma();
		x = parseNumber(false);
		skipWSPComma();
		y = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegArc(x, y, rx, ry, angle, laf, sf, isRelative));

		while(!isEOC() && isNumber(true)) {
			rx = parseNumber(true);
			skipWSPComma();
			ry = parseNumber(true);
			skipWSPComma();
			angle = parseNumber(false);
			skipWSPComma();
			laf = parseFlag();
			skipWSPComma();
			sf = parseFlag();
			skipWSPComma();
			x = parseNumber(false);
			skipWSPComma();
			y = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegArc(x, y, rx, ry, angle, laf, sf, isRelative));
		}
	}


	/**
	 * Parses an SVGPath curveto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseCurveto(final boolean isRelative) throws ParseException {
		double x, y, x1, x2, y1, y2;

		nextChar();
		skipWSP();
		x1 = parseNumber(false);
		skipWSPComma();
		y1 = parseNumber(false);
		skipWSPComma();
		x2 = parseNumber(false);
		skipWSPComma();
		y2 = parseNumber(false);
		skipWSPComma();
		x = parseNumber(false);
		skipWSPComma();
		y = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegCurvetoCubic(x, y, x1, y1, x2, y2, isRelative));

		while(!isEOC() && isNumber(false)) {
			x1 = parseNumber(false);
			skipWSPComma();
			y1 = parseNumber(false);
			skipWSPComma();
			x2 = parseNumber(false);
			skipWSPComma();
			y2 = parseNumber(false);
			skipWSPComma();
			x = parseNumber(false);
			skipWSPComma();
			y = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegCurvetoCubic(x, y, x1, y1, x2, y2, isRelative));
		}
	}


	/**
	 * Parses an SVGPath quadratic curveto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseQuadraticBezierCurveto(final boolean isRelative) throws ParseException {
		double x, y, x1, y1;

		nextChar();
		skipWSP();
		x1 = parseNumber(false);
		skipWSPComma();
		y1 = parseNumber(false);
		skipWSPComma();
		x = parseNumber(false);
		skipWSPComma();
		y = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegCurvetoQuadratic(x, y, x1, y1, isRelative));

		while(!isEOC() && isNumber(false)) {
			x1 = parseNumber(false);
			skipWSPComma();
			y1 = parseNumber(false);
			skipWSPComma();
			x = parseNumber(false);
			skipWSPComma();
			y = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegCurvetoQuadratic(x, y, x1, y1, isRelative));
		}
	}


	/**
	 * Parses an SVGPath quadratic smooth curveto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseShorthandQuadraticBezierCurveto(final boolean isRelative) throws ParseException {
		double x, y;

		nextChar();
		skipWSP();
		x = parseNumber(false);
		skipWSPComma();
		y = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegCurvetoQuadraticSmooth(x, y, isRelative));

		while(!isEOC() && isNumber(false)) {
			x = parseNumber(false);
			skipWSPComma();
			y = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegLineto(x, y, isRelative));
		}
	}


	/**
	 * Parses an SVGPath lineto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseLineto(final boolean isRelative) throws ParseException {
		double x, y;

		nextChar();
		skipWSP();
		x = parseNumber(false);
		skipWSPComma();
		y = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegLineto(x, y, isRelative));

		while(!isEOC() && isNumber(false)) {
			x = parseNumber(false);
			skipWSPComma();
			y = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegLineto(x, y, isRelative));
		}
	}



	/**
	 * Parses an SVGPath moveto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseMoveto(final boolean isRelative) throws ParseException {
		double x, y;

		nextChar();
		skipWSP();
		x = parseNumber(false);
		skipWSPComma();
		y = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegMoveto(x, y, isRelative));

		while(!isEOC() && isNumber(false)) {
			x = parseNumber(false);
			skipWSPComma();
			y = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegLineto(x, y, isRelative));
		}
	}
}
