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

/**
 * Defines an SVG length implementation.
 * @author Arnaud BLOUIN
 */
public class SVGLength {
	/** The value of the length in the user space. */
	protected double value;
	/** The type of the length. */
	protected LengthType lengthType;
	/** The original parsed length value (without the length token). */
	protected String valueAsString;

	/**
	 * Builds an SVGLength.
	 * @param value The value of the length in the user space.
	 * @param lengthType The type of the length.
	 * @param valueAsString The original parsed length value (without the length token).
	 */
	public SVGLength(final double value, final LengthType lengthType, final String valueAsString) {
		super();
		if(lengthType == null || valueAsString == null) {
			throw new IllegalArgumentException();
		}

		try {
			Double.valueOf(valueAsString);
		}catch(final NumberFormatException e) {
			throw new IllegalArgumentException();
		}

		this.value = value;
		this.lengthType = lengthType;
		this.valueAsString = valueAsString;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @return the lengthType
	 */
	public LengthType getLengthType() {
		return lengthType;
	}

	/**
	 * @return the valueAsString
	 */
	public String getValueAsString() {
		return valueAsString;
	}


	public enum LengthType {UNKNOWN, NUMBER, PERCENTAGE, EM, EX, PX, CM, MM, IN, PT, PC}
}
