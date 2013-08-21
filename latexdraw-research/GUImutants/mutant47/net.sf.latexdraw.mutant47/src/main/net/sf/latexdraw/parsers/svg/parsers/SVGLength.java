package net.sf.latexdraw.parsers.svg.parsers;

/**
 * Defines an SVG length implementation.<br>
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
public class SVGLength {
	public static enum LengthType { UNKNOWN, NUMBER, PERCENTAGE, EM,
									EX, PX, CM, MM, IN, PT, PC }

    /** The value of the length in the user space. @since 0.1 */
    protected double value;

    /** The type of the length. @since 0.1 */
    protected LengthType lengthType;

    /** The original parsed length value (without the length token). @since 0.1 */
    protected String valueAsString;



    /**
     * Builds an SVGLength.
     * @param value The value of the length in the user space.
     * @param lengthType The type of the length.
     * @param valueAsString The original parsed length value (without the length token).
     */
    public SVGLength(final double value, final LengthType lengthType, final String valueAsString) {
    	if(lengthType==null || valueAsString==null)
    		throw new IllegalArgumentException();

    	try{ Double.valueOf(valueAsString); }
    	catch(NumberFormatException e){ throw new IllegalArgumentException(); }

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
}
