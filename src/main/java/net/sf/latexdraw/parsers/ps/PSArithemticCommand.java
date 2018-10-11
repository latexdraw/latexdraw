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
package net.sf.latexdraw.parsers.ps;

import java.util.Deque;

/**
 * An abstract arithmetic command.
 * @author Arnaud Blouin
 */
@FunctionalInterface
public interface PSArithemticCommand {
	/**
	 * Creates an abstract postscript arithmetical command.
	 * @param stack The current stack of values.
	 * @param x The x variable of the command.
	 * @throws InvalidFormatPSFunctionException If the command format is not valid.
	 * @throws ArithmeticException When a division by 0 occurs for instance.
	 * @since 3.0
	 */
	void execute(final Deque<Double> stack, final double x);
}
