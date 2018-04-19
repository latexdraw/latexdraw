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
package net.sf.latexdraw.util;

import java.io.File;
import net.sf.latexdraw.LaTeXDraw;

/**
 * A command line parser for latexdraw.
 * @author Arnaud BLOUIN
 */
public class LCommandLine {
	/** The option for show the help. */
	public static final String OPTION_HELP = "-h"; //NON-NLS

	/**
	 * deduces the filename that will be open, from the set of parameters.
	 * @param args The parameters given to latexdraw.
	 * @return The filename that will be open.
	 * @since 2.0.3
	 */
	public static String getFileName(final String[] args) {
		if(args == null || args.length == 0) {
			return null;
		}

		StringBuilder name = new StringBuilder(args[0]);

		if(args.length != 1) {
			File f = new File(args[0]);
			boolean ok = f.exists();
			int i = 1;

			while(!ok && i < args.length) {
				name.append(' ').append(args[i]);
				i++;
				f = new File(name.toString());
				ok = f.exists();
			}

			if(!ok) {
				name = new StringBuilder(args[0]);
			}
		}

		return name.toString();
	}
	/** The file name given in argument. */
	protected String filename;


	/**
	 * Parses the given arguments.
	 * @param args The arguments to parse.
	 * @return The result of the parsing.
	 * @since 2.0.3
	 */
	public CmdLineState parse(final String[] args) {
		if(args == null || args.length == 0) {
			return CmdLineState.APPLICATION;
		}

		if(args[0].equals(OPTION_HELP)) {
			displayHelp();
			return CmdLineState.STOP;
		}

		filename = getFileName(args);
		return filename == null ? CmdLineState.APPLICATION : CmdLineState.APPLICATION_FILENAME;
	}

	/**
	 * Displays the different options of the command line of latexdraw.
	 * @since 2.0.3
	 */
	public void displayHelp() {
		System.out.println(LaTeXDraw.LABEL_APP + ' ' + VersionChecker.VERSION + VersionChecker.VERSION_STABILITY);
		System.out.println("Options:"); //NON-NLS
		System.out.println("\t" + OPTION_HELP + "\t\t\t\tDisplay the different options"); //NON-NLS
		System.out.println("\t<filename>\t\t\tOpens the given file <filename> into latexdraw."); //NON-NLS
		System.out.println("If no argument is given, latexdraw will started with a new drawing."); //NON-NLS
	}

	/**
	 * @return The filename.
	 * @since 2.0.3
	 */
	public String getFilename() {
		return filename;
	}


	/** The different states of the command line. */
	public enum CmdLineState {
		/** If the command line states that latexdraw must start without a file to open. */
		APPLICATION, /** If the command line states that latexdraw must start with a file to open. */
		APPLICATION_FILENAME, /** If the command line states that latexdraw must stop. */
		STOP
	}
}
