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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.sf.latexdraw.util.Tuple;
import org.jetbrains.annotations.NotNull;

/**
 * A postscript function parser.
 * @author Arnaud Blouin
 */
public class PSFunctionParser {
	private static final Map<String, Supplier<PSArithemticCommand>> factoryMap;

	static {
		factoryMap = new HashMap<>();
		factoryMap.put("add", () -> new PSAddCommand()); //NON-NLS
		factoryMap.put("mul", () -> new PSMulCommand()); //NON-NLS
		factoryMap.put("sub", () -> new PSSubCommand()); //NON-NLS
		factoryMap.put("sin", () -> new PSSinCommand()); //NON-NLS
		factoryMap.put("cos", () -> new PSCosCommand()); //NON-NLS
		factoryMap.put("div", () -> new PSDivCommand()); //NON-NLS
		factoryMap.put("idiv", () -> new PSIDivCommand()); //NON-NLS
		factoryMap.put("mod", () -> new PSModCommand()); //NON-NLS
		factoryMap.put("neg", () -> new PSNegCommand()); //NON-NLS
		factoryMap.put("exch", () -> new PSExchCommand()); //NON-NLS
		factoryMap.put("clear", () -> new PSClearCommand()); //NON-NLS
		factoryMap.put("dup", () -> new PSDupCommand()); //NON-NLS
		factoryMap.put("pop", () -> new PSPopCommand()); //NON-NLS
		factoryMap.put("roll", () -> null); //NON-NLS
		factoryMap.put("sqrt", () -> null); //NON-NLS
		factoryMap.put("exp", () -> new PSExpCommand()); //NON-NLS
		factoryMap.put("abs", () -> new PSAbsCommand()); //NON-NLS
		factoryMap.put("floor", () -> new PSFloorCommand()); //NON-NLS
		factoryMap.put("ceiling", () -> new PSCeilingCommand()); //NON-NLS
		factoryMap.put("count", () -> new PSCountCommand()); //NON-NLS
		factoryMap.put("x", () -> new PSPlotXVariable()); //NON-NLS
		factoryMap.put("log", () -> new PSLogCommand()); //NON-NLS
	}

	/**
	 * Checks whether the given equation is a valid post-fixed PS equation.
	 * @param eq The equation to check.
	 * @param min The X-min of the plotting.
	 * @param max The X-max of the plotting.
	 * @param nbPts The number of points to plot.
	 * @return True if the given equation is a valid post-fixed PS equation. The second parameter is
	 * the possible error message (never null but can be empty).
	 */
	public static Tuple<Boolean, String> isValidPostFixEquation(final String eq, final double min, final double max, final double nbPts) {
		try {
			final PSFunctionParser fct = new PSFunctionParser(eq);
			final double gap = (max - min) / (nbPts - 1);

			for(double x = min; x < max; x += gap) {
				final double y = fct.getY(x);
				if(Double.isNaN(y) || Double.isInfinite(y)) {
					return new Tuple<>(Boolean.FALSE, "f(x)=" + eq + " produces an invalid value with x=" + x);
				}
			}

			final double y = fct.getY(max);
			if(Double.isNaN(y) || Double.isInfinite(y)) {
				return new Tuple<>(Boolean.FALSE, "f(x)=" + eq + " produces an invalid value with x=" + max);
			}

			return new Tuple<>(Boolean.TRUE, "");
		}catch(final NumberFormatException ex) {
			return new Tuple<>(Boolean.FALSE, "Error while computing the curve's points: " + ex.getMessage());
		}catch(final ArithmeticException ex) {
			return new Tuple<>(Boolean.FALSE, "Error while computing the curve's points: arithmetical error.");
		}
	}
	/** The postscript function. */
	private final String function;
	private final List<PSArithemticCommand> commands;

	/**
	 * Creates and parser from postscript functions.
	 * @param fct The function to parse.
	 * @throws InvalidFormatPSFunctionException If the function format is not valid.
	 */
	public PSFunctionParser(final String fct) {
		super();
		if(fct == null || fct.isEmpty()) {
			throw new IllegalArgumentException();
		}

		commands = new ArrayList<>();
		function = fct;

		parseFunction();
	}

	/**
	 * @param x The X-coordinate used to compute the Y using the function.
	 * @return The y value corresponding to the given X value. Or Double.NaN is an arithmetic error occurs.
	 * @throws InvalidFormatPSFunctionException If the function is not correct.
	 * @throws ArithmeticException If an error occurs during the computation of the points (e.g. division by 0).
	 */
	public double getY(final double x) {
		final Deque<Double> stack = new ArrayDeque<>();

		commands.forEach(cmd -> cmd.execute(stack, x));

		if(stack.isEmpty()) {
			throw new InvalidFormatPSFunctionException();
		}

		return stack.pop();
	}

	/**
	 * Parses the function.
	 * @throws InvalidFormatPSFunctionException If the function is not correct.
	 * @throws NumberFormatException If the function is not correct.
	 */
	protected void parseFunction() {
		int i = 0;
		final int lgth = function.length();
		final StringBuilder cmd = new StringBuilder();

		while(i < lgth) {
			cmd.delete(0, cmd.length());

			while(i < lgth && function.charAt(i) == ' ') {
				i++;
			}

			while(i < lgth && function.charAt(i) != ' ') {
				cmd.append(function.charAt(i++));
			}

			if(cmd.length() > 0) {
				commands.add(identifyCommand(cmd.toString()));
			}
		}
	}

	/**
	 * @param cmd The arithmetic command to analyse.
	 * @return The arithmetic instance corresponding to the given command.
	 * @throws InvalidFormatPSFunctionException If the function is not correct.
	 * @throws NumberFormatException If the function is not correct.
	 */
	protected PSArithemticCommand identifyCommand(final @NotNull String cmd) {
		try {
			return factoryMap.getOrDefault(cmd, () -> new PSValue(Double.parseDouble(cmd))).get();
		}catch(final NumberFormatException ex) {
			throw new InvalidFormatPSFunctionException("Cannot parse: " + cmd); //NON-NLS
		}
	}
}
