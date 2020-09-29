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

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

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

	/**
	 * @return The name of the operating system currently used.
	 */
	public static @NotNull Optional<OperatingSystem> getSystem() {
		final String os = System.getProperty("os.name"); //NON-NLS

		if("linux".equalsIgnoreCase(os)) { //NON-NLS
			return Optional.of(OperatingSystem.LINUX);
		}

		if("windows 7".equalsIgnoreCase(os)) { //NON-NLS
			return Optional.of(OperatingSystem.SEVEN);
		}

		if("windows vista".equalsIgnoreCase(os)) { //NON-NLS
			return Optional.of(OperatingSystem.VISTA);
		}

		if("windows xp".equalsIgnoreCase(os)) { //NON-NLS
			return Optional.of(OperatingSystem.XP);
		}

		if("mac os x".equalsIgnoreCase(os)) { //NON-NLS
			return Optional.of(OperatingSystem.MAC_OS_X);
		}

		if(os.toLowerCase().contains("windows 8")) { //NON-NLS
			return Optional.of(OperatingSystem.EIGHT);
		}

		if(os.toLowerCase().contains("windows 10")) { //NON-NLS
			return Optional.of(OperatingSystem.TEN);
		}

		BadaboomCollector.INSTANCE.add(new IllegalArgumentException("This OS is not supported: " + os)); //NON-NLS

		return Optional.empty();
	}

	/**
	 * @return True: the operating system currently used is Windows.
	 */
	public static boolean isWindows() {
		return isSeven() || isVista() || isXP() || is8() || is10();
	}

	/**
	 * @return True: the operating system currently used is Windows 10.
	 */
	public static boolean is10() {
		return getSystem().orElse(null) == OperatingSystem.TEN;
	}

	/**
	 * @return True: the operating system currently used is Windows 8.
	 */
	public static boolean is8() {
		return getSystem().orElse(null) == OperatingSystem.EIGHT;
	}

	/**
	 * @return True: the operating system currently used is Vista.
	 */
	public static boolean isVista() {
		return getSystem().orElse(null) == OperatingSystem.VISTA;
	}

	/**
	 * @return True: the operating system currently used is XP.
	 */
	public static boolean isXP() {
		return getSystem().orElse(null) == OperatingSystem.XP;
	}

	/**
	 * @return True: the operating system currently used is Seven.
	 */
	public static boolean isSeven() {
		return getSystem().orElse(null) == OperatingSystem.SEVEN;
	}

	/**
	 * @return True: the operating system currently used is Linux.
	 */
	public static boolean isLinux() {
		return getSystem().orElse(null) == OperatingSystem.LINUX;
	}

	/**
	 * @return True: the operating system currently used is Mac OS X.
	 */
	public static boolean isMacOSX() {
		return getSystem().orElse(null) == OperatingSystem.MAC_OS_X;
	}
}
