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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import org.jetbrains.annotations.NotNull;

/**
 * Defines some routines that provides information about the operating system currently used.
 * @author Arnaud BLOUIN, Jan-Cornelius MOLNAR
 */
public final class SystemUtils {
	private static SystemUtils INSTANCE = new SystemUtils();

	public static void setSingleton(final @NotNull SystemUtils instance) {
		INSTANCE = instance;
	}

	public static SystemUtils getInstance() {
		return INSTANCE;
	}

	/** The line separator of the current system. */
	public final String EOL = System.getProperty("line.separator");
	/** The file separator of the current system. */
	public final String FILE_SEP = System.getProperty("file.separator");
	/** The name of the cache directory */
	public final String CACHE_DIR = ".cache"; //NON-NLS
	/** The name of the cache directory for shared templates */
	public final String CACHE_SHARED_DIR = ".cacheShared"; //NON-NLS
	/** The name of the templates directory */
	public final String TEMPLATE_DIR = "templates"; //NON-NLS

	private SystemUtils() {
		super();
		checkDirectories();
	}

	public @NotNull String getPathCacheShareDir() {
		return getPathLocalUser() + File.separator + CACHE_SHARED_DIR;
	}

	public @NotNull String getPathCacheDir() {
		return getPathLocalUser() + File.separator + CACHE_DIR;
	}

	public @NotNull String getPathTemplatesDirUser() {
		return getPathLocalUser() + File.separator + TEMPLATE_DIR;
	}

	/**
	 * @return True: the operating system currently used is Windows.
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
	 */
	public boolean is8() {
		return getSystem().orElse(null) == OperatingSystem.EIGHT;
	}

	/**
	 * @return True: the operating system currently used is Vista.
	 */
	public boolean isVista() {
		return getSystem().orElse(null) == OperatingSystem.VISTA;
	}

	/**
	 * @return True: the operating system currently used is XP.
	 */
	public boolean isXP() {
		return getSystem().orElse(null) == OperatingSystem.XP;
	}

	/**
	 * @return True: the operating system currently used is Seven.
	 */
	public boolean isSeven() {
		return getSystem().orElse(null) == OperatingSystem.SEVEN;
	}

	/**
	 * @return True: the operating system currently used is Linux.
	 */
	public boolean isLinux() {
		return getSystem().orElse(null) == OperatingSystem.LINUX;
	}

	/**
	 * @return True: the operating system currently used is Mac OS X.
	 */
	public boolean isMacOSX() {
		return getSystem().orElse(null) == OperatingSystem.MAC_OS_X;
	}

	/**
	 * @return True: the operating system currently used is Mac OS X El Capitan.
	 */
	public boolean isMacOSXElCapitan() {
		return getSystem().orElse(null) == OperatingSystem.MAC_OS_X_CAPITAN;
	}

	/**
	 * @return True: the operating system currently used is Mac OS.
	 */
	public boolean isMac() {
		return isMacOSX() || isMacOSXElCapitan();
	}

	/**
	 * @return The control modifier used by the currently used operating system.
	 */
	public @NotNull KeyCode getControlKey() {
		if(isMac()) {
			return KeyCode.META;
		}
		return KeyCode.CONTROL;
	}

	/**
	 * @return The name of the operating system currently used.
	 */
	public @NotNull Optional<OperatingSystem> getSystem() {
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
			return getMacSystem();
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

	private Optional<OperatingSystem> getMacSystem() {
		final String[] v = System.getProperty("os.version").split("\\."); //NON-NLS
		final double[] d = new double[v.length];

		for(int i = 0; i < v.length; i++) {
			d[i] = Double.parseDouble(v[i]);
		}

		// A change since El Capitan
		if((d.length >= 1 && d[0] > 10) || (d.length >= 2 && d[0] == 10 && d[1] >= 11)) {
			return Optional.of(OperatingSystem.MAC_OS_X_CAPITAN);
		}
		return Optional.of(OperatingSystem.MAC_OS_X);
	}

	/**
	 * @return The version of the current LaTeX.
	 */
	public @NotNull String getLaTeXVersion() {
		return execute(new String[] { getSystem().orElse(OperatingSystem.LINUX).getLatexBinPath(), "--version" }, null).b; //NON-NLS
	}

	/**
	 * @return The version of the current dvips.
	 */
	public @NotNull String getDVIPSVersion() {
		return execute(new String[] { getSystem().orElse(OperatingSystem.LINUX).getDvipsBinPath(), "--version" }, null).b; //NON-NLS
	}

	/**
	 * @return The version of the current ps2pdf.
	 */
	public @NotNull String getPS2PDFVersion() {
		return execute(new String[] { getSystem().orElse(OperatingSystem.LINUX).getPs2pdfBinPath() }, null).b;
	}

	/**
	 * @return The version of the current ps2eps.
	 */
	public @NotNull String getPS2EPSVersion() {
		return execute(new String[] { getSystem().orElse(OperatingSystem.LINUX).getPS2EPSBinPath(), "--version" }, null).b; //NON-NLS
	}

	/**
	 * @return The version of the current pdfcrop.
	 */
	public @NotNull String getPDFCROPVersion() {
		return execute(new String[] { getSystem().orElse(OperatingSystem.LINUX).getPdfcropBinPath(), "--version" }, null).b; //NON-NLS
	}

	/**
	 * Executes a command.
	 * @param cmd The execution command
	 * @param tmpdir The working dir
	 * @return The log.
	 */
	public @NotNull Tuple<Boolean, String> execute(final @NotNull String[] cmd, final File tmpdir) {
		if(cmd.length == 0) {
			return new Tuple<>(Boolean.FALSE, "");
		}

		final StringBuilder log = new StringBuilder();

		try {
			final ProcessBuilder builder = new ProcessBuilder(cmd).
				redirectErrorStream(true).
				directory(tmpdir);
			final Process process = builder.start();

			try(final InputStream is = process.getInputStream();
				final InputStreamReader isr = new InputStreamReader(is);
				final BufferedReader br = new BufferedReader(isr)) {

				String line = br.readLine();

				while(line != null) {
					log.append(line).append(EOL);
					line = br.readLine();
				}
			}

			if(process.waitFor() == 0) {
				return new Tuple<>(Boolean.TRUE, log.toString());
			}

		}catch(final IOException | SecurityException ex) {
			return new Tuple<>(Boolean.FALSE, "ERR while execute the command : " + Arrays.toString(cmd) + ": " + ex.getMessage()); //NON-NLS
		}catch(final InterruptedException ex) {
			Thread.currentThread().interrupt();
			return new Tuple<>(Boolean.FALSE, "ERR while execute the command : " + Arrays.toString(cmd) + ": " + ex.getMessage()); //NON-NLS
		}

		return new Tuple<>(Boolean.FALSE, log.toString());
	}


	/**
	 * @return The precise latex error messages that the latex compilation produced.
	 */
	public @NotNull String getLatexErrorMessageFromLog(final String log) {
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

	public @NotNull String getFileWithoutExtension(final String file) {
		return file == null ? "" : file.substring(0, file.lastIndexOf('.'));
	}

	/**
	 * Replaces ~ characters by \string~.
	 * @param str The string to process.
	 * @return The normalised string. Can be null.
	 */
	public String normalizeForLaTeX(final String str) {
		if(str == null) {
			return null;
		}
		if(isWindows()) {
			return str.replaceAll("\\\\", "/").replaceAll("~", "\\\\string~"); //NON-NLS
		}
		return str.replaceAll("~", "\\\\string~"); //NON-NLS
	}


	/**
	 * Removes the given dir with its content.
	 * @param dir The directory to remove.
	 */
	public void removeDirWithContent(final String dir) {
		if(dir == null) {
			return;
		}

		final Path path = Paths.get(dir);
		if(!path.toFile().isDirectory()) {
			return;
		}

		try(final Stream<Path> paths = Files.walk(path)) {
			paths.sorted(Comparator.reverseOrder()).forEach(file -> removeFilePath(file));
		}catch(final IOException | SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}

	/**
	 * Removes the file corresponding to the given path.
	 * @param path The path of the file to remove. Nothing is done if null.
	 */
	public void removeFilePath(final Path path) {
		if(path == null) {
			return;
		}

		try {
			Files.delete(path);
		}catch(final NoSuchFileException fnEx) {
			// Ignoring the exception.
		}catch(final IOException | SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}

	/**
	 * Writes the given text into a file at the given path.
	 * @param path The location where the file must be created.
	 * @return The created file or nothing.
	 */
	public @NotNull Optional<File> saveFile(final String path, final String text) {
		boolean ok = true;
		try(final OutputStreamWriter osw = new OutputStreamWriter(Files.newOutputStream(Path.of(path)))) {
			osw.append(text);
		}catch(final IOException | SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			ok = false;
		}
		return ok ? Optional.of(new File(path)) : Optional.empty();
	}


	/**
	 * Reads the given file and returns its text.
	 * @param path The path of the file to read.
	 * @return The content of the text file to read. Cannot be null.
	 */
	public @NotNull String readTextFile(final String path) {
		final StringBuilder txt = new StringBuilder();

		try(final InputStream is = getClass().getResourceAsStream(path);
			final Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
			final BufferedReader br = new BufferedReader(reader)) {
			String line = br.readLine();

			while(line != null) {
				txt.append(line).append(EOL);
				line = br.readLine();
			}
		}catch(final IOException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
		return txt.toString();
	}


	/**
	 * Creates a temporary directory that will be used to contains temporary files.
	 * The created folder will have restricted access: only the user can access the folder.
	 * @return The created folder or null (if the folder cannot be created or the rights cannot be restricted to the current user).
	 */
	public @NotNull Optional<File> createTempDir() {
		final String pathTmp = System.getProperty("java.io.tmpdir"); //NON-NLS
		final String path = pathTmp + (pathTmp.endsWith(FILE_SEP) ? "" : FILE_SEP) + "latexdraw" + FILE_SEP + "latexdrawTmp" + //NON-NLS
			System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(100000);
		final File tmpDir = new File(path);

		try {
			boolean ok = tmpDir.mkdirs();

			if(ok) {
				// Rights are removed for everybody.
				ok = tmpDir.setReadable(false, false);
				// They are added to the owner only.
				ok = ok && tmpDir.setReadable(true, true);
				// same thing here.
				ok = ok && tmpDir.setWritable(false, false);
				ok = ok && tmpDir.setWritable(true, true);
				tmpDir.deleteOnExit();
			}

			return ok ? Optional.of(tmpDir) : Optional.empty();
		}catch(final SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			return Optional.empty();
		}
	}

	/**
	 * Normalises the given namespace URI: if the given namespace is null or empty, an empty
	 * string is returned. Otherwise, the namespace followed by character ':' is returned.
	 * @param nsURI The namespace to normalise.
	 * @return The normalised namespace.
	 */
	public @NotNull String getNormaliseNamespaceURI(final String nsURI) {
		return nsURI == null || nsURI.isEmpty() ? "" : nsURI + ':'; //NON-NLS
	}


	/**
	 * @return The home directory of the user depending of his operating system.
	 */
	public @NotNull String getPathLocalUser() {
		final String home = System.getProperty("user.home"); //NON-NLS

		if(isVista() || isSeven() || is8() || is10()) {
			return home + "\\AppData\\Local\\latexdraw"; //NON-NLS
		}
		if(isXP()) {
			return home + "\\Application Data\\latexdraw"; //NON-NLS
		}
		if(isMacOSX()) {
			return home + "/Library/Preferences/latexdraw"; //NON-NLS
		}
		return home + "/.latexdraw"; //NON-NLS
	}


	/**
	 * @return The path of the directory where the templates shared by the different users are located.
	 */
	public @NotNull String getPathTemplatesShared() {
		return getPathShared() + File.separator + TEMPLATE_DIR;
	}


	/**
	 * @return The path where files are shared by users.
	 */
	private @NotNull String getPathShared() {
		final String home = System.getProperty("user.home"); //NON-NLS

		if(isMacOSX()) {
			return "/Users/Shared/latexdraw"; //NON-NLS
		}

		if(isVista()) {
			File dir = new File("C:\\ProgramData"); //NON-NLS
			int cpt = 0;
			final int max = 10;

			while(cpt < max && !dir.exists()) {
				dir = new File((char) ('C' + cpt++) + ":\\ProgramData"); //NON-NLS
			}

			if(dir.exists()) {
				return dir.getPath() + "\\latexdraw"; //NON-NLS
			}

			return home.substring(0, 1 + home.lastIndexOf('\\')) + "All Users\\Application Data\\latexdraw"; //NON-NLS
		}

		if(isSeven() || is8() || is10()) {
			return home.substring(0, 1 + home.lastIndexOf('\\')) + "Default\\AppData\\Local\\latexdraw"; //NON-NLS
		}

		if(isXP()) {
			return home.substring(0, 1 + home.lastIndexOf('\\')) + "All Users\\Application Data\\latexdraw"; //NON-NLS
		}

		return "/usr/share/latexdraw"; //NON-NLS
	}


	/**
	 * Creates the necessary directories for the execution of LaTeXDraw.
	 */
	public void checkDirectories() {
		try {
			new File(getPathLocalUser()).mkdirs();
			new File(getPathTemplatesDirUser()).mkdirs();
			new File(getPathCacheDir()).mkdirs();
			new File(getPathCacheShareDir()).mkdirs();
		}catch(final SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}
}
