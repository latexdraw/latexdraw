package net.sf.latexdraw.parsers.ps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Defines a postscript function parser.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
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
 * 03/11/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class PSFunctionParser {
	/** The postscript function. */
	protected String function;

	protected List<PSArithemticCommand> commands;

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


	/**
	 * Creates and parser from postscript functions.
	 * @param fct The function to parse.
	 * @throws InvalidFormatPSFunctionException If the function format is not valid.
	 * @since 3.0
	 */
	public PSFunctionParser(final String fct) throws InvalidFormatPSFunctionException {
		if(fct==null || fct.length()==0)
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
		Deque<String> stack = new ArrayDeque<>();

		for(PSArithemticCommand cmd : commands)
			cmd.execute(stack, x);

		if(stack.isEmpty())
			throw new InvalidFormatPSFunctionException();

		return Double.parseDouble(stack.pop());
	}



	/**
	 * Parses the function.
	 * @throws InvalidFormatPSFunctionException If the function is not correct.
	 */
	public void parseFunction() throws InvalidFormatPSFunctionException {
		if(function==null)
			return ;

		int i = 0, lgth = function.length();
		StringBuilder cmd = new StringBuilder();

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
	 * @since 3.0
	 */
	public PSArithemticCommand identifyCommand(final String cmd) throws InvalidFormatPSFunctionException {
		if(cmd==null || cmd.length()==0)
			throw new InvalidFormatPSFunctionException();

		if(cmd.equals(CMD_ADD))
			return new PSAddCommand();
		if(cmd.equals(CMD_SUB))
			return new PSSubCommand();
		if(cmd.equals(CMD_MUL))
			return new PSMulCommand();
		if(cmd.equals(CMD_DIV))
			return new PSDivCommand();
		if(cmd.equals(CMD_IDIV))
			return new PSIDivCommand();
		if(cmd.equals(CMD_MOD))
			return new PSModCommand();
		if(cmd.equals(CMD_NEG))
			return new PSNegCommand();
		if(cmd.equals(CMD_ABS))
			return new PSAbsCommand();
		if(cmd.equals(CMD_CEILING))
			return new PSCeilingCommand();
		if(cmd.equals(CMD_FLOOR))
			return new PSFloorCommand();
		if(cmd.equals(CMD_X))
			return new PSPlotXVariable();
		if(cmd.equals(CMD_EXCH))
			return new PSExchCommand();
		if(cmd.equals(CMD_CLEAR))
			return new PSClearCommand();
		if(cmd.equals(CMD_DUP))
			return new PSDupCommand();
		if(cmd.equals(CMD_POP))
			return new PSPopCommand();
//		if(cmd.equals(CMD_ROLL))
//			return new PSPopCommand();
//		if(cmd.equals(CMD_SQRT))
//			return new PSPopCommand();
		if(cmd.equals(CMD_EXP))
			return new PSExpCommand();
		if(cmd.equals(CMD_COUNT))
			return new PSCountCommand();
		if(cmd.equals(CMD_SIN))
			return new PSSinCommand();
		if(cmd.equals(CMD_COS))
			return new PSCosCommand();

		return new PSValue(Double.parseDouble(cmd));
	}
}
