package net.sf.latexdraw.parsers.ps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Defines a postscript function parser.<br>
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
 * 03/11/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class PSFunctionParser {
	/**
	 * Checks whether the given equation is a valid post-fixed PS equation.
	 * @param eq The equation to check.
	 * @return True if the given equation is a valid post-fixed PS equation.
	 * @since 3.3
	 */
	@SuppressWarnings("unused")
	public static boolean isValidPostFixEquation(final String eq) {
		try {
			new PSFunctionParser(eq);
			return true;
		}
		catch(final NumberFormatException ex){return false;}
	}

	/** The postscript function. */
	protected final String function;

	protected final List<PSArithemticCommand> commands;

	public static final String CMD_MUL 	= "mul"; //$NON-NLS-1$
	public static final String CMD_ADD 	= "add";//$NON-NLS-1$
	public static final String CMD_SUB 	= "sub";//$NON-NLS-1$
	public static final String CMD_SIN 	= "sin";//$NON-NLS-1$
	public static final String CMD_COS 	= "cos";//$NON-NLS-1$
	public static final String CMD_DIV 	= "div";//$NON-NLS-1$
	public static final String CMD_IDIV = "idiv";//$NON-NLS-1$
	public static final String CMD_MOD 	= "mod";//$NON-NLS-1$
	public static final String CMD_NEG 	= "neg";//$NON-NLS-1$
	public static final String CMD_EXCH = "exch";//$NON-NLS-1$
	public static final String CMD_CLEAR= "clear";//$NON-NLS-1$
	public static final String CMD_DUP 	= "dup";//$NON-NLS-1$
	public static final String CMD_POP	= "pop";//$NON-NLS-1$
	public static final String CMD_ROLL	= "roll";//$NON-NLS-1$
	public static final String CMD_SQRT	= "sqrt";//$NON-NLS-1$
	public static final String CMD_EXP	= "exp";//$NON-NLS-1$
	public static final String CMD_ABS	= "abs";//$NON-NLS-1$
	public static final String CMD_FLOOR= "floor";//$NON-NLS-1$
	public static final String CMD_CEILING = "ceiling";//$NON-NLS-1$
	public static final String CMD_COUNT= "count";//$NON-NLS-1$
	public static final String CMD_X 	= "x";//$NON-NLS-1$
	public static final String CMD_LOG 	= "log";//$NON-NLS-1$


	/**
	 * Creates and parser from postscript functions.
	 * @param fct The function to parse.
	 * @throws InvalidFormatPSFunctionException If the function format is not valid.
	 * @since 3.0
	 */
	public PSFunctionParser(final String fct) throws InvalidFormatPSFunctionException {
        super();
        if (fct == null || fct.isEmpty())
            throw new IllegalArgumentException();

        commands = new ArrayList<>();
        function = fct;

        parseFunction();
    }



	/**
	 * @param x The X-coordinate used to computre the Y using the function.
	 * @return The y value corresponding to the given X value.
	 * @throws InvalidFormatPSFunctionException If the function is not correct.
	 */
	public double getY(final double x) throws InvalidFormatPSFunctionException {
		final Deque<Double> stack = new ArrayDeque<>();

		for(final PSArithemticCommand cmd : commands)
			cmd.execute(stack, x);

		if(stack.isEmpty())
			throw new InvalidFormatPSFunctionException();

		return stack.pop();
	}



	/**
	 * Parses the function.
	 * @throws InvalidFormatPSFunctionException If the function is not correct.
	 * @throws NumberFormatException If the function is not correct.
	 */
	protected void parseFunction() throws InvalidFormatPSFunctionException, NumberFormatException {
		int i = 0;
        final int lgth = function.length();
        final StringBuilder cmd = new StringBuilder();

		while(i<lgth) {
			cmd.delete(0, cmd.length());

			while(i<lgth && function.charAt(i)==' ')
				i++;

			while(i<lgth && function.charAt(i)!=' ')
				cmd.append(function.charAt(i++));

			if(cmd.length()>0)
				commands.add(identifyCommand(cmd.toString()));
		}
	}



	/**
	 * @param cmd The arithmetic command to analyse.
	 * @return The arithmetic instance corresponding to the given command.
	 * @throws InvalidFormatPSFunctionException If the function is not correct.
	 * @throws NumberFormatException If the function is not correct.
	 * @since 3.0
	 */
	protected PSArithemticCommand identifyCommand(final String cmd) throws InvalidFormatPSFunctionException, NumberFormatException {
		if(cmd==null || cmd.isEmpty())
			throw new InvalidFormatPSFunctionException();

		switch(cmd) {
			case CMD_ADD: return new PSAddCommand();
			case CMD_ABS: return new PSAbsCommand();
			case CMD_CEILING: return new PSCeilingCommand();
			case CMD_CLEAR: return new PSClearCommand();
			case CMD_COS: return new PSCosCommand();
			case CMD_COUNT: return new PSCountCommand();
			case CMD_DIV: return new PSDivCommand();
			case CMD_DUP: return new PSDupCommand();
			case CMD_EXCH: return new PSExchCommand();
			case CMD_EXP: return new PSExpCommand();
			case CMD_FLOOR: return new PSFloorCommand();
			case CMD_IDIV: return new PSIDivCommand();
			case CMD_LOG: return new PSLogCommand();
			case CMD_MOD: return new PSModCommand();
			case CMD_MUL: return new PSMulCommand();
			case CMD_NEG: return new PSNegCommand();
			case CMD_POP: return new PSPopCommand();
			case CMD_ROLL: return null;
			case CMD_SIN: return new PSSinCommand();
			case CMD_SQRT: return null;
			case CMD_SUB: return new PSSubCommand();
			case CMD_X: return new PSPlotXVariable();
		}

		try {return new PSValue(Double.parseDouble(cmd));}
		catch(final NumberFormatException ex) {throw new InvalidFormatPSFunctionException("Cannot parse: " + cmd);} //$NON-NLS-1$
	}
}
