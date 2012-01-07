package net.sf.latexdraw.util;

import java.io.File;

/**
 * Defines a command line parser for latexdraw.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 11/28/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LCommandLine {
	/** The different states of the command line. */
	public static enum CmdLineState {
		/** If the command line states a conversion. */
		CONVERTION,
		/** If the command line states that latexdraw must start without a file to open. */
		APPLICATION,
		/** If the command line states that latexdraw must start with a file to open. */
		APPLICATION_FILENAME,
		/** If the command line states that latexdraw must stop. */
		STOP
	}

	/** The option for show the help. */
	public static final String OPTION_HELP 		 = "-h"; //$NON-NLS-1$

	/** The option to show the conversion option. */
	public static final String OPTION_CONVERTION = "-c"; //$NON-NLS-1$


	/** The file name given in argument. */
	protected String filename;

	/** The source file to convert. */
	protected String fileConvertionSrc;

	/** The target file to create. */
	protected String fileConvertionTarget;


	/**
	 * Creates and initialises a command line parser.
	 */
	public LCommandLine() {
		super();
		filename 			 = null;
		fileConvertionSrc    = null;
		fileConvertionTarget = null;
	}



	/**
	 * Parses the given arguments.
	 * @param args The arguments to parse.
	 * @return The result of the parsing.
	 * @since 2.0.3
	 */
	public CmdLineState parse(final String[] args) {
		if(args==null || args.length==0)
			return CmdLineState.APPLICATION;

		if(args[0].equals(OPTION_HELP)) {
			displayHelp();
			return CmdLineState.STOP;
		}

		if(args[0].equals(OPTION_CONVERTION)) {
//			if(args.length<3) {
//				System.out.println("No enough parameters. ");
//				displayHelp();
//				return CmdLineState.STOP;
//			}
//
//			if(BatchConvertFrame.checkExtensionConvertion(args[1], true) &&
//				BatchConvertFrame.checkExtensionConvertion(args[2], false))
//			{
//				String ext1 = args[1].substring(args[1].lastIndexOf('.'));
//				String ext2 = args[2].substring(args[2].lastIndexOf('.'));
//				boolean isVectorial1 = ext1.equals(SVGFilter.SVG_EXTENSION) || ext1.equals(TeXFilter.TEX_EXTENSION);
//				boolean isVectorial2 = ext2.equals(SVGFilter.SVG_EXTENSION) || ext2.equals(TeXFilter.TEX_EXTENSION);
//
//				if(isVectorial2 && !isVectorial1) {
//					System.out.println("The svg and tex output formats can be used only if the input file is a tex or an svg document.");
//					return CmdLineState.STOP;
//				}
//
//
//				fileConvertionSrc = args[1];
//				fileConvertionTarget = args[2];
//				return CmdLineState.CONVERTION;
//			}
//
//			System.out.println("One of the given file formats is not managed or cannot be recognised.");
//			displayHelp();

			return CmdLineState.STOP;
		}

		filename = getFileName(args);

		return filename==null ? CmdLineState.APPLICATION : CmdLineState.APPLICATION_FILENAME;
	}



	/**
	 * deduces the filename that will be open, from the set of parameters.
	 * @param args The parameters given to latexdraw.
	 * @return The filename that will be open.
	 * @since 2.0.3
	 */
	public static String getFileName(final String[] args) {
		if(args==null || args.length==0)
			return null;

		StringBuilder name = new StringBuilder(args[0]);

		if(args.length!=1) {
			File f = new File(args[0]);
			boolean ok = f.exists();
			int i = 1;

			while(!ok && i<args.length) {
				name.append(' ').append(args[i]);
				i++;
				f = new File(name.toString());
				ok = f.exists();
			}

			if(!ok)
				name = new StringBuilder(args[0]);
		}

		return name.toString();
	}



	/**
	 * Displays the different options of the command line of latexdraw.
	 * @since 2.0.3
	 */
	public void displayHelp() {
		System.out.println(LResources.LABEL_APP + ' ' + VersionChecker.VERSION + VersionChecker.VERSION_STABILITY);
		System.out.println("Options:");
		System.out.println("\t"+OPTION_HELP+"\t\t\t\tDisplay the different options"); //$NON-NLS-1$
		System.out.println("\t"+OPTION_CONVERTION+" <filename1> <filename2>\tConvert filename1 in filename2. "+ //$NON-NLS-1$
							"The extensions are used in order to define the source and target formats.");
		System.out.println("\t\t\t\t\tSource formats are: svg, tex, png, bmp, jpg, ppm, gif");
		System.out.println("\t\t\t\t\tTarget formats are: svg, tex, png, bmp, jpg, ppm, eps");
		System.out.println("\t\t\t\t\tNote that svg and tex output formats cannot be used if the source format");
		System.out.println("\t\t\t\t\tis different than tex or svg");
		System.out.println("\t<filename>\t\t\tOpens the given file <filename> into latexdraw.");
		System.out.println("If no argument is given, latexdraw will started with a new drawing.");
	}



	/**
	 * @return The filename.
	 * @since 2.0.3
	 */
	public String getFilename() {
		return filename;
	}



	/**
	 * @return The fileConvertionSrc.
	 * @since 2.0.3
	 */
	public String getFileConvertionSrc() {
		return fileConvertionSrc;
	}



	/**
	 * @return The fileConvertionTarget.
	 * @since 2.0.3
	 */
	public String getFileConvertionTarget() {
		return fileConvertionTarget;
	}
}
