/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.parser.ps;

/**
 * An exception for the ps parser.
 * @author Arnaud BLOUIN
 */
public class InvalidFormatPSFunctionException extends NumberFormatException {
	/**
	 * Creates the exception.
	 */
	public InvalidFormatPSFunctionException() {
		super();
	}

	/**
	 * Creates the exception.
	 * @param s The detail message.
	 */
	public InvalidFormatPSFunctionException(final String s) {
		super(s);
	}
}
