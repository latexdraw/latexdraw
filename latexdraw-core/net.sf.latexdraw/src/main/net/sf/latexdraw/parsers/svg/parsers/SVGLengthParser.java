package net.sf.latexdraw.parsers.svg.parsers;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.SVGLength.LengthType;

/**
 * Defines a parser that parses SVG length code.<br>
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
 * 10/24/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGLengthParser extends SVGNumberParser {
	/**
	 * Describes textual value for the font-size attribute.
	 */
	public static enum FontSize {
		XXSMALL {
			@Override
			public float getPointValue() {
				return XSMALL.getPointValue()/SCALING_FACTOR;
			}

			@Override
			public String getToken() {
				return "xx-small"; //$NON-NLS-1$
			}
		}, XSMALL {
			@Override
			public float getPointValue() {
				return SMALL.getPointValue()/SCALING_FACTOR;
			}

			@Override
			public String getToken() {
				return "x-small"; //$NON-NLS-1$
			}
		}, SMALL {
			@Override
			public float getPointValue() {
				return MEDIUM.getPointValue()/SCALING_FACTOR;
			}

			@Override
			public String getToken() {
				return "small"; //$NON-NLS-1$
			}
		}, MEDIUM {
			@Override
			public float getPointValue() {
				return 12f;
			}

			@Override
			public String getToken() {
				return "medium"; //$NON-NLS-1$
			}
		}, LARGE {
			@Override
			public float getPointValue() {
				return MEDIUM.getPointValue()*SCALING_FACTOR;
			}

			@Override
			public String getToken() {
				return "large"; //$NON-NLS-1$
			}
		}, XLARGE {
			@Override
			public float getPointValue() {
				return LARGE.getPointValue()*SCALING_FACTOR;
			}

			@Override
			public String getToken() {
				return "x-large"; //$NON-NLS-1$
			}
		}, XXLARGE {
			@Override
			public float getPointValue() {
				return XLARGE.getPointValue()*SCALING_FACTOR;
			}

			@Override
			public String getToken() {
				return "xx-large"; //$NON-NLS-1$
			}
		};

		/**
		 * @return The value in point of the token.
		 */
		public abstract float getPointValue();

		/**
		 * @return The token of the current font-size used in SVG documents.
		 */
		public abstract String getToken();

		/**
		 * @param token The token to test.
		 * @return The value in point of the font-size given as argument, or -1
		 * if 'token' is not valid.
		 */
		public static float getFontSize(final String token) {
			if(token==null)
				return -1f;
			if(token.equals(XXSMALL.getToken()))
				return XXSMALL.getPointValue();
			if(token.equals(XSMALL.getToken()))
				return XSMALL.getPointValue();
			if(token.equals(SMALL.getToken()))
				return SMALL.getPointValue();
			if(token.equals(MEDIUM.getToken()))
				return MEDIUM.getPointValue();
			if(token.equals(LARGE.getToken()))
				return LARGE.getPointValue();
			if(token.equals(XLARGE.getToken()))
				return XLARGE.getPointValue();
			if(token.equals(XXLARGE.getToken()))
				return XXLARGE.getPointValue();
			return -1f;
		}

		public static final float SCALING_FACTOR=1.2f;
	}


	/**
	 * Converts the font-size string value in point value.
	 * @param fontSize The font-size value to convert.
	 * @return the font-size value in point, or -1 if a
	 * problem occurs.
	 */
	public static float fontSizetoPoint(final String fontSize) {
		if(fontSize==null)
			return -1f;

		float value;

		try {
			value = Double.valueOf(fontSize).floatValue();
		}catch(NumberFormatException e) {
			try {
				SVGLength lgth  = new SVGLengthParser(fontSize).parseLength();
				value 			= (float)UnitProcessor.INSTANCE.toPoint(lgth.getValue(), lgth.getLengthType());
			}
			catch(ParseException ex) {
				value = FontSize.getFontSize(fontSize);
			}
		}

		return value;
	}



	/**
	 * The constructor.
	 * @param code The code to parse.
	 */
	public SVGLengthParser(final String code) {
		super(code);
	}



	/**
	 * Parses an SVG length.
	 * @return An SVGLength. The length is always converted in PX.
	 * @throws ParseException If a problem occurs or if not managed unit are parsed (%, em and ex).
	 */
	public SVGLength parseLength() throws ParseException {
		double value = parseNumber(false);
		LengthType lgthType;
		String errorUnit = "Invalid unit", valueAsStr;//$NON-NLS-1$

		setPosition(0);
		valueAsStr = parseNumberAsString(false);
		skipWSP();

		switch(getChar()) {
			case EOC:
				lgthType = LengthType.NUMBER;
				break;

			case 'p':
				switch(nextChar()) {
					case 't':
						lgthType = LengthType.PT;
						break;

					case 'x':
						lgthType = LengthType.PX;
						break;

					case 'c':
						lgthType = LengthType.PC;
						break;

					case EOC:
					default:
						throw new ParseException(errorUnit, getPosition());
				}

				break;

			case '%':
				throw new ParseException("Not yet managed: %", getPosition());//$NON-NLS-1$

			case 'c':
				if(nextChar()=='m')
					lgthType = LengthType.CM;
				else
					throw new ParseException(errorUnit, getPosition());

				break;

			case 'm':
				if(nextChar()=='m')
					lgthType = LengthType.MM;
				else
					throw new ParseException(errorUnit, getPosition());
				break;

			case 'i':
				if(nextChar()=='n')
					lgthType = LengthType.IN;
				else
					throw new ParseException(errorUnit, getPosition());
				break;

			case 'e':
				switch(nextChar()) {
					case EOC:
					case 'm':
						throw new ParseException("Not yet managed: em", getPosition());//$NON-NLS-1$

					case 'x':
						throw new ParseException("Not yet managed: ex", getPosition());//$NON-NLS-1$

					default:
						throw new ParseException(errorUnit, getPosition());
				}

			default:
				throw new ParseException(errorUnit, getPosition());
		}

		return new SVGLength(UnitProcessor.INSTANCE.toUserUnit(value, lgthType), LengthType.PX, valueAsStr);
	}



	/**
	 * Parses an SVG coordinate.
	 * @return An SVGLength.
	 * @throws ParseException If a problem occurs or if not managed unit are parsed (%, em and ex).
	 */
	public SVGLength parseCoordinate() throws ParseException {
		return parseLength();
	}



	/**
	 * Parses a number or a percentage (not yet managed).
	 * @return An SVGLength.
	 * @throws ParseException If a problem occurs or if a percentage is parsed.
	 */
	public SVGLength parseNumberOrPercent() throws ParseException {
		double value = parseNumber(false);
		LengthType type;
		String valueAsStr;

		setPosition(0);
		valueAsStr = parseNumberAsString(false);
		skipWSP();

		switch(getChar()) {
			case '%':
				throw new ParseException("Not yet managed: %", getPosition());//$NON-NLS-1$

			case EOC:
			default:
				type = LengthType.NUMBER;
				break;
		}

		return new SVGLength(UnitProcessor.INSTANCE.toUserUnit(value, type), type, valueAsStr);
	}
}
