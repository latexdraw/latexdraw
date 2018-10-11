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
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * Defines some routines that provides information about the operating system currently used.
 * @author Arnaud BLOUIN, Jan-Cornelius MOLNAR
 */
public final class LSystem {
	/** The singleton. */
	public static final LSystem INSTANCE = new LSystem();
	/** The line separator of the current system. */
	public static final String EOL = System.getProperty("line.separator");
	/** The file separator of the current system. */
	public static final String FILE_SEP = System.getProperty("file.separator");

	/**
	 * Creates the singleton.
	 */
	private LSystem() {
		super();
	}

	/**
	 * @return True: the operating system currently used is Windows.
	 * @since 3.0
	 */
	public boolean isWindows() {
		return isSeven() || isVista() || isXP() || is8() || is10();
	}

	/**
	 * @return True: the operating system currently used is Windows 10.
	 */
	public boolean is10() {
		return getSystem().orElse(null) == OperatingSystem.TEN;
	}

	/**
	 * @return True: the operating system currently used is Windows 8.
	 * @since 3.0
	 */
	public boolean is8() {
		return getSystem().orElse(null) == OperatingSystem.EIGHT;
	}

	/**
	 * @return True: the operating system currently used is Vista.
	 * @since 3.0
	 */
	public boolean isVista() {
		return getSystem().orElse(null) == OperatingSystem.VISTA;
	}

	/**
	 * @return True: the operating system currently used is XP.
	 * @since 3.0
	 */
	public boolean isXP() {
		return getSystem().orElse(null) == OperatingSystem.XP;
	}

	/**
	 * @return True: the operating system currently used is Seven.
	 * @since 3.0
	 */
	public boolean isSeven() {
		return getSystem().orElse(null) == OperatingSystem.SEVEN;
	}

	/**
	 * @return True: the operating system currently used is Linux.
	 * @since 3.0
	 */
	public boolean isLinux() {
		return getSystem().orElse(null) == OperatingSystem.LINUX;
	}

	/**
	 * @return True: the operating system currently used is Mac OS X.
	 * @since 3.0
	 */
	public boolean isMacOSX() {
		return getSystem().orElse(null) == OperatingSystem.MAC_OS_X;
	}

	/**
	 * @return True: the operating system currently used is Mac OS X El Capitan.
	 * @since 3.3
	 */
	public boolean isMacOSXElCapitan() {
		return getSystem().orElse(null) == OperatingSystem.MAC_OS_X_CAPITAN;
	}

	/**
	 * @return True: the operating system currently used is Mac OS.
	 * @since 3.3
	 */
	public boolean IsMac() {
		return isMacOSX() || isMacOSXElCapitan();
	}

	/**
	 * @return The control modifier used by the currently used operating system.
	 * @since 3.0
	 */
	public KeyCode getControlKey() {
		if(LSystem.INSTANCE.IsMac()) {
			return KeyCode.META;
		}
		return KeyCode.CONTROL;
	}

	public void sleep(final long sleep) {
		try {
			Thread.sleep(sleep);
		}catch(final InterruptedException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * @return The name of the operating system currently used.
	 * @since 3.0
	 */
	public Optional<OperatingSystem> getSystem() {
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
			final String[] v = System.getProperty("os.version").split("\\."); //NON-NLS
			final double[] d = new double[v.length];

			for(int i = 0; i < v.length; i++) {
				d[i] = Double.valueOf(v[i]);
			}

			// A change since El Capitan
			if((d.length >= 1 && d[0] > 10) || (d.length >= 2 && d[0] == 10 && d[1] >= 11)) {
				return Optional.of(OperatingSystem.MAC_OS_X_CAPITAN);
			}
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
	 * @return The version of the current LaTeX.
	 * @since 3.1
	 */
	public String getLaTeXVersion() {
		return execute(new String[] { getSystem().orElse(OperatingSystem.LINUX).getLatexBinPath(), "--version" }, null); //NON-NLS
	}

	/**
	 * @return The version of the current dvips.
	 * @since 3.1
	 */
	public String getDVIPSVersion() {
		return execute(new String[] { getSystem().orElse(OperatingSystem.LINUX).getDvipsBinPath(), "--version" }, null); //NON-NLS
	}

	/**
	 * @return The version of the current ps2pdf.
	 * @since 3.1
	 */
	public String getPS2PDFVersion() {
		return execute(new String[] { getSystem().orElse(OperatingSystem.LINUX).getPs2pdfBinPath() }, null);
	}

	/**
	 * @return The version of the current ps2eps.
	 * @since 3.1
	 */
	public String getPS2EPSVersion() {
		return execute(new String[] { getSystem().orElse(OperatingSystem.LINUX).getPS2EPSBinPath(), "--version" }, null); //NON-NLS
	}

	/**
	 * @return The version of the current pdfcrop.
	 * @since 3.1
	 */
	public String getPDFCROPVersion() {
		return execute(new String[] { getSystem().orElse(OperatingSystem.LINUX).getPdfcropBinPath(), "--version" }, null); //NON-NLS
	}

	/**
	 * Executes a command.
	 * @param cmd The execution command
	 * @param tmpdir The working dir
	 * @return The log.
	 * @since 3.1
	 */
	public String execute(final String[] cmd, final File tmpdir) {
		if(cmd == null || cmd.length == 0) {
			return null;
		}

		try {
			// Command launched
			final Process process = Runtime.getRuntime().exec(cmd, null, tmpdir);
			// Catch the error log
			final StreamExecReader err = new StreamExecReader(process.getErrorStream());
			// Catch the log
			final StreamExecReader inp = new StreamExecReader(process.getInputStream());

			err.start();
			inp.start();

			// Waiting for the end of the process.
			process.waitFor();

			return err.getLog() + EOL + inp.getLog();
		}catch(final IOException | SecurityException | InterruptedException ex) {
			return "ERR while execute the command : " + Arrays.toString(cmd) + ": " + ex.getMessage(); //NON-NLS
		}
	}


	/**
	 * @return The precise latex error messages that the latex compilation produced.
	 * @since 3.0
	 */
	public String getLatexErrorMessageFromLog(final String log) {
		if(log == null) {
			return "";
		}
		final Matcher matcher = Pattern.compile(".*\r?\n").matcher(log); //NON-NLS
		final StringBuilder errors = new StringBuilder();

		while(matcher.find()) {
			final String line = matcher.group();

			if(line.startsWith("!") && !"! Emergency stop.\n".equals(line)) { //NON-NLS
				errors.append(line, 2, line.length());
			}
		}
		return errors.toString();
	}
}
