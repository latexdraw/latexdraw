/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.util;

/**
 * The different operating systems managed.
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
	MAC_OS_X_CAPITAN {
		@Override
		public String getPS2EPSBinPath() {
			return "/usr/local/bin/ps2epsi"; //$NON-NLS-1$
		}

		@Override
		public String getLatexBinPath() {
			return "/Library/TeX/texbin/latex"; //$NON-NLS-1$
		}

		@Override
		public String getDvipsBinPath() {
			return "/Library/TeX/texbin/dvips"; //$NON-NLS-1$
		}

		@Override
		public String getPs2pdfBinPath() {
			return "/usr/local/bin/ps2pdf"; //$NON-NLS-1$
		}

		@Override
		public String getPdfcropBinPath() {
			return "/Library/TeX/texbin/pdfcrop"; //$NON-NLS-1$
		}
	},
	MAC_OS_X {
		@Override
		public String getPS2EPSBinPath() {
			return "/usr/local/bin/ps2epsi"; //$NON-NLS-1$
		}

		@Override
		public String getLatexBinPath() {
			return "/usr/texbin/latex"; //$NON-NLS-1$
		}

		@Override
		public String getDvipsBinPath() {
			return "/usr/texbin/dvips"; //$NON-NLS-1$
		}

		@Override
		public String getPs2pdfBinPath() {
			return "/usr/local/bin/ps2pdf"; //$NON-NLS-1$
		}

		@Override
		public String getPdfcropBinPath() {
			return "pdfcrop"; //$NON-NLS-1$
		}
	},
	/** Linux */
	LINUX;

	/**
	 * @return The path where is the ps2eps binary.
	 * @since 3.1
	 */
	public String getPS2EPSBinPath() {
		return "ps2epsi"; //$NON-NLS-1$
	}

	/**
	 * @return The path where is the latex binary.
	 * @since 3.0
	 */
	public String getLatexBinPath() {
		return "latex"; //$NON-NLS-1$
	}

	/**
	 * @return The path where is the dvips binary.
	 * @since 3.0
	 */
	public String getDvipsBinPath() {
		return "dvips"; //$NON-NLS-1$
	}

	/**
	 * @return The path where is the ps2pdf binary.
	 * @since 3.0
	 */
	public String getPs2pdfBinPath() {
		return "ps2pdf"; //$NON-NLS-1$
	}

	/**
	 * @return The path where is the pdfcrop binary.
	 * @since 3.0
	 */
	public String getPdfcropBinPath() {
		return "pdfcrop"; //$NON-NLS-1$
	}
}
