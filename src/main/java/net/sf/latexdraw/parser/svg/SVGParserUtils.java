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
package net.sf.latexdraw.parser.svg;

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.parser.svg.path.SVGPathSeg;
import net.sf.latexdraw.parser.svg.path.SVGPathSegArc;
import net.sf.latexdraw.parser.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parser.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parser.svg.path.SVGPathSegCurvetoCubicSmooth;
import net.sf.latexdraw.parser.svg.path.SVGPathSegCurvetoQuadratic;
import net.sf.latexdraw.parser.svg.path.SVGPathSegCurvetoQuadraticSmooth;
import net.sf.latexdraw.parser.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parser.svg.path.SVGPathSegLinetoHorizontal;
import net.sf.latexdraw.parser.svg.path.SVGPathSegLinetoVertical;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.Tuple;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.jetbrains.annotations.NotNull;

public final class SVGParserUtils {
	public static final @NotNull SVGParserUtils INSTANCE = new SVGParserUtils();

	private SVGParserUtils() {
		super();
	}

	/**
	 * Reads a URI reference string from an url(#ref) attribute - such as url(#id123) - returns the extracted reference (id123).
	 * @return The parsed reference or an empty string if the format of the code is not valid (not "url(#ref)")
	 */
	public String parseURIRerefence(final @NotNull String code) {
		final Matcher matcher = Pattern.compile("[\\s]*url\\([\\s]*#(.+?)\\)").matcher(code);

		if(!matcher.matches()) {
			return "";
		}

		return matcher.group(1);
	}

	/**
	 * Parses an SVG length.
	 * @return An SVGLength. The length is always converted in PX.
	 */
	public Optional<SVGLength> parseLength(final String code) {
		final Matcher matcher = Pattern.compile("[\\s]*(" + MathUtils.INST.doubleRegex + ")[\\s]*(pt|cm|in|px|pc|mm|em|ex|%)?[\\s]*").matcher(code);

		if(!matcher.matches()) {
			return Optional.empty();
		}

		final double value = Double.parseDouble(matcher.group(1));
		final SVGLength.LengthType length = matcher.group(2) == null ? SVGLength.LengthType.UNKNOWN : SVGLength.LengthType.valueOf(matcher.group(2).toUpperCase());

		return Optional.of(new SVGLength(toUserUnit(value, length), SVGLength.LengthType.PX, matcher.group(1)));
	}


	/**
	 * Parses the given code and return the parsed points or null.
	 * @param code The code to parse.
	 * @return The parsed points or null.
	 */
	public List<Point2D> parsePoints(final String code) {
		final List<Point2D> points = new ArrayList<>();
		final AtomicBoolean error = new AtomicBoolean(false);
		final net.sf.latexdraw.parser.svg.SVGPathLexer lexer = new net.sf.latexdraw.parser.svg.SVGPathLexer(CharStreams.fromString(code));
		final net.sf.latexdraw.parser.svg.SVGPathParser parser = new net.sf.latexdraw.parser.svg.SVGPathParser(new CommonTokenStream(lexer));
		parser.addParseListener(new net.sf.latexdraw.parser.svg.SVGPathBaseListener() {
			@Override
			public void exitCoordPair(final net.sf.latexdraw.parser.svg.SVGPathParser.CoordPairContext ctx) {
				if(ctx.x != null && ctx.y != null) {
					points.add(new Point2D.Double(Double.parseDouble(ctx.x.getText()), Double.parseDouble(ctx.y.getText())));
				}
			}
		});
		parser.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int charPositionInLine,
				final String msg, final RecognitionException e) {
				error.set(true);
				BadaboomCollector.INSTANCE.add(new ParseException(msg, line));
			}
		});
		parser.coordPairSeq();
		parser.getInterpreter().clearDFA();
		lexer.getInterpreter().clearDFA();
		new ATNDeserializer().deserialize(net.sf.latexdraw.parser.pst.PSTLexer._serializedATN.toCharArray());
		return error.get() ? new ArrayList<>() : points;
	}


	public void parseSVGPath(final @NotNull String code, final @NotNull Consumer<SVGPathSeg> handler) {
		if(code.isEmpty()) {
			return;
		}

		final net.sf.latexdraw.parser.svg.SVGPathLexer lexer = new net.sf.latexdraw.parser.svg.SVGPathLexer(CharStreams.fromString(code));
		final net.sf.latexdraw.parser.svg.SVGPathParser parser = new net.sf.latexdraw.parser.svg.SVGPathParser(new CommonTokenStream(lexer));
		parser.addParseListener(new SVGPathListener(handler));
		parser.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int charPositionInLine,
				final String msg, final RecognitionException e) {
				BadaboomCollector.INSTANCE.add(new ParseException(msg, line));
			}
		});
		parser.svgpath();
		parser.getInterpreter().clearDFA();
		lexer.getInterpreter().clearDFA();
		new ATNDeserializer().deserialize(net.sf.latexdraw.parser.pst.PSTLexer._serializedATN.toCharArray());
	}

	private static class SVGPathListener extends net.sf.latexdraw.parser.svg.SVGPathBaseListener {
		private final @NotNull Consumer<SVGPathSeg> handler;

		SVGPathListener(final @NotNull Consumer<SVGPathSeg> handler) {
			super();
			this.handler = handler;
		}

		@Override
		public void exitClosePath(final net.sf.latexdraw.parser.svg.SVGPathParser.ClosePathContext ctx) {
			handler.accept(new SVGPathSegClosePath());
		}

		@Override
		public void exitEllipticalArcRel(final net.sf.latexdraw.parser.svg.SVGPathParser.EllipticalArcRelContext ctx) {
			processEllArc(ctx.ellipticalArcSeq().ellipticalArcArgument(0), true);
		}

		private void processEllArc(final net.sf.latexdraw.parser.svg.SVGPathParser.EllipticalArcArgumentContext ctx, final boolean rel) {
			handler.accept(new SVGPathSegArc(Double.parseDouble(ctx.p.x.getText()), Double.parseDouble(ctx.p.y.getText()),
				Double.parseDouble(ctx.rx.getText()), Double.parseDouble(ctx.ry.getText()), Double.parseDouble(ctx.xAxisRot.getText()),
				"1".equals(ctx.largeArcFlag.getText()), "1".equals(ctx.sweepFlag.getText()), rel));
		}

		@Override
		public void exitEllipticalArcAbs(final net.sf.latexdraw.parser.svg.SVGPathParser.EllipticalArcAbsContext ctx) {
			processEllArc(ctx.ellipticalArcSeq().ellipticalArcArgument(0), false);
		}

		@Override
		public void exitSmoothQuadraBezierCurveToRel(final net.sf.latexdraw.parser.svg.SVGPathParser.SmoothQuadraBezierCurveToRelContext ctx) {
			handler.accept(new SVGPathSegCurvetoQuadraticSmooth(
				Double.parseDouble(ctx.coordPairSeq().coordPair(0).x.getText()), Double.parseDouble(ctx.coordPairSeq().coordPair(0).y.getText()), true));
		}

		@Override
		public void exitSmoothQuadraBezierCurveToAbs(final net.sf.latexdraw.parser.svg.SVGPathParser.SmoothQuadraBezierCurveToAbsContext ctx) {
			handler.accept(new SVGPathSegCurvetoQuadraticSmooth(
				Double.parseDouble(ctx.coordPairSeq().coordPair(0).x.getText()), Double.parseDouble(ctx.coordPairSeq().coordPair(0).y.getText()), false));
		}

		@Override
		public void exitQuadraBezierCurveToRel(final net.sf.latexdraw.parser.svg.SVGPathParser.QuadraBezierCurveToRelContext ctx) {
			processQuadraBezierCurveTo(ctx.quadraCoordSeq().coordPairDouble(), true);
		}

		private void processQuadraBezierCurveTo(final net.sf.latexdraw.parser.svg.SVGPathParser.CoordPairDoubleContext ctx, final boolean rel) {
			handler.accept(new SVGPathSegCurvetoQuadratic(Double.parseDouble(ctx.p.x.getText()), Double.parseDouble(ctx.p.y.getText()),
				Double.parseDouble(ctx.p2.x.getText()), Double.parseDouble(ctx.p2.y.getText()), rel));
		}

		@Override
		public void exitQuadraBezierCurveToAbs(final net.sf.latexdraw.parser.svg.SVGPathParser.QuadraBezierCurveToAbsContext ctx) {
			processQuadraBezierCurveTo(ctx.quadraCoordSeq().coordPairDouble(), false);
		}

		@Override
		public void exitSmoothCurveToRel(final net.sf.latexdraw.parser.svg.SVGPathParser.SmoothCurveToRelContext ctx) {
			processSmoothCurveTo(ctx.smoothCoordSeq().coordPairDouble(0), true);
		}

		private void processSmoothCurveTo(final net.sf.latexdraw.parser.svg.SVGPathParser.CoordPairDoubleContext ctx, final boolean rel) {
			handler.accept(new SVGPathSegCurvetoCubicSmooth(Double.parseDouble(ctx.p.x.getText()),
				Double.parseDouble(ctx.p.y.getText()), Double.parseDouble(ctx.p2.x.getText()), Double.parseDouble(ctx.p2.y.getText()), rel));
		}

		@Override
		public void exitSmoothCurveToAbs(final net.sf.latexdraw.parser.svg.SVGPathParser.SmoothCurveToAbsContext ctx) {
			processSmoothCurveTo(ctx.smoothCoordSeq().coordPairDouble(0), false);
		}

		@Override
		public void exitCurveToRel(final net.sf.latexdraw.parser.svg.SVGPathParser.CurveToRelContext ctx) {
			processCurveTo(ctx.curveCoordSeq().coordTriple().get(0), true);
		}

		private void processCurveTo(final net.sf.latexdraw.parser.svg.SVGPathParser.CoordTripleContext ctx, final boolean rel) {
			handler.accept(new SVGPathSegCurvetoCubic(
				Double.parseDouble(ctx.p.x.getText()), Double.parseDouble(ctx.p.y.getText()),
				Double.parseDouble(ctx.p1.x.getText()), Double.parseDouble(ctx.p1.y.getText()),
				Double.parseDouble(ctx.p2.x.getText()), Double.parseDouble(ctx.p2.y.getText()), rel));
		}

		@Override
		public void exitCurveToAbs(final net.sf.latexdraw.parser.svg.SVGPathParser.CurveToAbsContext ctx) {
			processCurveTo(ctx.curveCoordSeq().coordTriple().get(0), false);
		}

		@Override
		public void exitHLineToRel(final net.sf.latexdraw.parser.svg.SVGPathParser.HLineToRelContext ctx) {
			handler.accept(new SVGPathSegLinetoHorizontal(Double.parseDouble(ctx.x.coord(0).value.getText()), true));
		}

		@Override
		public void exitHLineToAbs(final net.sf.latexdraw.parser.svg.SVGPathParser.HLineToAbsContext ctx) {
			handler.accept(new SVGPathSegLinetoHorizontal(Double.parseDouble(ctx.x.coord(0).value.getText()), false));
		}

		@Override
		public void exitVLineToRel(final net.sf.latexdraw.parser.svg.SVGPathParser.VLineToRelContext ctx) {
			handler.accept(new SVGPathSegLinetoVertical(Double.parseDouble(ctx.y.coord(0).value.getText()), true));
		}

		@Override
		public void exitVLineToAbs(final net.sf.latexdraw.parser.svg.SVGPathParser.VLineToAbsContext ctx) {
			handler.accept(new SVGPathSegLinetoVertical(Double.parseDouble(ctx.y.coord(0).value.getText()), false));
		}

		@Override
		public void exitLineToRel(final net.sf.latexdraw.parser.svg.SVGPathParser.LineToRelContext ctx) {
			handler.accept(new SVGPathSegLineto(Double.parseDouble(ctx.p.coordPair(0).x.getText()), Double.parseDouble(ctx.p.coordPair(0).y.getText()), true));
		}

		@Override
		public void exitLineToAbs(final net.sf.latexdraw.parser.svg.SVGPathParser.LineToAbsContext ctx) {
			handler.accept(new SVGPathSegLineto(Double.parseDouble(ctx.p.coordPair(0).x.getText()), Double.parseDouble(ctx.p.coordPair(0).y.getText()), false));
		}

		@Override
		public void exitMoveToRel(final net.sf.latexdraw.parser.svg.SVGPathParser.MoveToRelContext ctx) {
			handler.accept(new SVGPathSegMoveto(Double.parseDouble(ctx.p.coordPair(0).x.getText()), Double.parseDouble(ctx.p.coordPair(0).y.getText()), true));
		}

		@Override
		public void exitMoveToAbs(final net.sf.latexdraw.parser.svg.SVGPathParser.MoveToAbsContext ctx) {
			handler.accept(new SVGPathSegMoveto(Double.parseDouble(ctx.p.coordPair(0).x.getText()), Double.parseDouble(ctx.p.coordPair(0).y.getText()), false));
		}
	}


	/**
	 * Transforms a value in the user unit (in pixels) according to the given unit (%, EM and EX are not managed).
	 * @param value The value to transform.
	 * @param lgthType The type of the value.
	 * @return The value in the user unit (in pixels).
	 */
	public double toUserUnit(final double value, final @NotNull SVGLength.LengthType lgthType) {
		switch(lgthType) {
			case CM:
				return value * 35.43307;
			case IN:
				return value * 90d;
			case MM:
				return value * 3.543307;
			case PC:
				return value * 15d;
			case PT:
				return value * 1.25;
			case EM:
			case EX:
			case PERCENTAGE:
				throw new IllegalArgumentException("Not yet managed."); //NON-NLS
			case NUMBER:
			case PX:
			case UNKNOWN:
				return value;
			default:
				throw new IllegalArgumentException("Invalid length type."); //NON-NLS
		}
	}

	public Map<String, String> parseCSS(final @NotNull String code) {
		return Arrays.stream(code.split(";"))
			.map(item -> item.split(":"))
			.filter(item -> item.length == 2)
			.map(item -> new Tuple<>(item[0].trim(), item[1].trim()))
			.collect(Collectors.toMap(t -> t.a, t -> t.b));
	}
}
