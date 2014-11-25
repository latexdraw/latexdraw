package net.sf.latexdraw.parsers.ps;

import java.util.Deque;

/**
 * Defines the log command.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
 * 2014-10-09<br>
 * @author Arnaud BLOUIN
 */
public class PSLogCommand implements PSArithemticCommand {
	@Override
	public void execute(final Deque<Double> stack, final double x) throws InvalidFormatPSFunctionException {
		if(stack.isEmpty())
			throw new InvalidFormatPSFunctionException();

		stack.push(Math.log10(stack.pop()));
	}
}
