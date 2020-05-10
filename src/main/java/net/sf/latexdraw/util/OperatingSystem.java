/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.util;

/**
 * The different operating systems managed.
 * @author Arnaud Blouin
 */
public enum OperatingSystem {
	/** Vista */
	VISTA,
	/** XP */
	XP,
	/** Windows 7 */
	SEVEN,
	/** Windows 8 */
	EIGHT,
	/** Windows 10 */
	TEN,
	/** Mac OS X */
	MAC_OS_X,
	/** Linux */
	LINUX;

	/**
	 * @return The path where is the ps2eps binary.
	 */
	public String getPS2EPSBinPath() {
		return "ps2epsi"; //NON-NLS
	}

	/**
	 * @return The path where is the latex binary.
	 */
	public String getLatexBinPath() {
		return "latex"; //NON-NLS
	}

	/**
	 * @return The path where is the dvips binary.
	 */
	public String getDvipsBinPath() {
		return "dvips"; //NON-NLS
	}

	/**
	 * @return The path where is the ps2pdf binary.
	 */
	public String getPs2pdfBinPath() {
		return "ps2pdf"; //NON-NLS
	}

	/**
	 * @return The path where the GhostScript binary is located.
	 */
	public String getGSbinPath() {
		return "gs"; //NON-NLS
	}

	/**
	 * @return The path where the pdftoppm binary is located.
	 */
	public String getPDFtoPPMbinPath() {
		return "pdftoppm"; //NON-NLS
	}
}
