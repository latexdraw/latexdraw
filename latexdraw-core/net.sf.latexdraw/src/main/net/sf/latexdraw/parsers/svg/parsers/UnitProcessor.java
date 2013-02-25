package net.sf.latexdraw.parsers.svg.parsers;

import net.sf.latexdraw.parsers.svg.parsers.SVGLength.LengthType;

/**
 * This processor manages and transforms value in different units.<br>
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
public final class UnitProcessor {
	/** The singleton. */
	public static final UnitProcessor INSTANCE = new UnitProcessor();
	

	private UnitProcessor() {
		super();
	}
	
	
	/**
	 * Transforms a value in point according to the given unit (%, em and ex are not managed).
	 * @param value The value to transform.
	 * @param lgthType The type of the value.
	 * @return The value in point.
	 * @throws IllegalArgumentException If the given length type is not valid or not managed.
	 */
	public double toPoint(final double value, final LengthType lgthType) {
		if(lgthType==null)
			throw new IllegalArgumentException();

		if(lgthType==LengthType.PT)
			return value;

		return toUserUnit(value, lgthType)/1.25;
	}


	/**
	 * Transforms a value in the user unit (in pixels) according to the given unit (%, em and ex are not managed).
	 * @param value The value to transform.
	 * @param lgthType The type of the value.
	 * @return The value in the user unit (in pixels).
	 * @throws IllegalArgumentException If the given length type is not valid or not managed.
	 */
	public double toUserUnit(final double value, final LengthType lgthType) {
		if(lgthType==null)
			throw new IllegalArgumentException();

		double userValue;

		switch(lgthType) {
			case CM:
				userValue = value * 35.43307;
				break;

			case IN:
				userValue = value * 90.;
				break;

			case MM:
				userValue = value * 3.543307;
				break;

			case PC:
				userValue = value * 15.;
				break;

			case PT:
				userValue = value * 1.25;
				break;

			case EM:
			case EX:
			case PERCENTAGE:
				throw new IllegalArgumentException("Not yet managed.");//$NON-NLS-1$

			case NUMBER:
			case PX:
			case UNKNOWN:
				userValue = value ;
				break;

			default:
				throw new IllegalArgumentException("Invalid length type.");//$NON-NLS-1$
		}

		return userValue;
	}
}
