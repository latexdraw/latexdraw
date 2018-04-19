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
package net.sf.latexdraw.parsers.svg.parsers;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegArc;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubicSmooth;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoQuadratic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoQuadraticSmooth;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLinetoHorizontal;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLinetoVertical;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.Tuple;

/**
 * Defines an SVGPath parser.
 * @author Arnaud BLOUIN
 */
public class SVGPathParser extends SVGNumberParser {
	/** The handler of the SVGPath. */
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

		// The first command must be a moveto command.
		if(getChar() != 'm' && getChar() != 'M') {
			throw new ParseException("moveto command (m|M) expected.", getPosition()); //NON-NLS
		}

		parseMoveto(getChar() == 'm');

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
					throw new ParseException("Invalid token:" + getChar(), getPosition()); //NON-NLS
			}
		}
	}

	/**
	 * Parses two coordinates separated by a comma.
	 * @return The parsed values.
	 * @throws ParseException On parsing errors.
	 */
	private Tuple<Double, Double> parsePoint() throws ParseException {
		skipWSP();
		final double v1 = parseNumber(false);
		skipWSPComma();
		final double v2 = parseNumber(false);
		return new Tuple<>(v1, v2);
	}

	/**
	 * Parses an SVGPath smooth curveto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseShorthandCurveto(final boolean isRelative) throws ParseException {
		nextChar();

		do {
			final Tuple<Double, Double> p2 = parsePoint();
			skipWSPComma();
			final Tuple<Double, Double> p1 = parsePoint();
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegCurvetoCubicSmooth(p1.a, p1.b, p2.a, p2.b, isRelative));
		}while(!isEOC() && isNumber(false));
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
		nextChar();
		skipWSP();

		do {
			final double h = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegLinetoHorizontal(h, isRelative));
		}while(!isEOC() && isNumber(false));
	}


	/**
	 * Parses an SVGPath vertical lineto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseVerticalLineto(final boolean isRelative) throws ParseException {
		nextChar();
		skipWSP();

		do {
			final double v = parseNumber(false);
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegLinetoVertical(v, isRelative));
		}while(!isEOC() && isNumber(false));
	}


	/**
	 * Parses an SVGPath arc.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseEllipticalArcto(final boolean isRelative) throws ParseException {
		nextChar();
		skipWSP();

		do {
			final Tuple<Double, Double> pr = parsePoint();
			skipWSPComma();
			final double angle = parseNumber(false);
			skipWSPComma();
			final boolean laf = parseFlag();
			skipWSPComma();
			final boolean sf = parseFlag();
			skipWSPComma();
			final Tuple<Double, Double> pt = parsePoint();
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegArc(pt.a, pt.b, pr.a, pr.b, angle, laf, sf, isRelative));
		}while(!isEOC() && isNumber(true));
	}


	/**
	 * Parses an SVGPath curveto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseCurveto(final boolean isRelative) throws ParseException {
		nextChar();
		skipWSP();

		do {
			final Tuple<Double, Double> p1 = parsePoint();
			skipWSPComma();
			final Tuple<Double, Double> p2 = parsePoint();
			skipWSPComma();
			final Tuple<Double, Double> p0 = parsePoint();
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegCurvetoCubic(p0.a, p0.b, p1.a, p1.b, p2.a, p2.b, isRelative));
		}while(!isEOC() && isNumber(false));
	}


	/**
	 * Parses an SVGPath quadratic curveto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseQuadraticBezierCurveto(final boolean isRelative) throws ParseException {
		nextChar();
		skipWSP();

		do {
			final Tuple<Double, Double> p1 = parsePoint();
			skipWSPComma();
			final Tuple<Double, Double> p0 = parsePoint();
			skipWSPComma();
			handler.onPathSeg(new SVGPathSegCurvetoQuadratic(p0.a, p0.b, p1.a, p1.b, isRelative));
		}while(!isEOC() && isNumber(false));
	}


	/**
	 * Parses an SVGPath quadratic smooth curveto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseShorthandQuadraticBezierCurveto(final boolean isRelative) throws ParseException {
		nextChar();
		skipWSP();
		final double x = parseNumber(false);
		skipWSPComma();
		final double y = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegCurvetoQuadraticSmooth(x, y, isRelative));
		parserPathSeg(isRelative);
	}


	private void parserPathSeg(final boolean isRelative) throws ParseException {
		while(!isEOC() && isNumber(false)) {
			final double x = parseNumber(false);
			skipWSPComma();
			final double y = parseNumber(false);
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
		nextChar();
		skipWSP();
		final double x = parseNumber(false);
		skipWSPComma();
		final double y = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegLineto(x, y, isRelative));
		parserPathSeg(isRelative);
	}


	/**
	 * Parses an SVGPath moveto.
	 * @param isRelative True if segment is relative.
	 * @throws ParseException If a problem occurs.
	 */
	protected void parseMoveto(final boolean isRelative) throws ParseException {
		nextChar();
		skipWSP();
		final double x = parseNumber(false);
		skipWSPComma();
		final double y = parseNumber(false);
		skipWSPComma();
		handler.onPathSeg(new SVGPathSegMoveto(x, y, isRelative));
		parserPathSeg(isRelative);
	}
}
