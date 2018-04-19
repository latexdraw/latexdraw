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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * Routines related to files manipulation.
 * @author Arnaud BLOUIN
 */
public final class LFileUtils {
	/** The singleton. */
	public static final LFileUtils INSTANCE = new LFileUtils();


	private LFileUtils() {
		super();
	}

	public String getFileWithoutExtension(final String file) {
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
		if(LSystem.INSTANCE.isWindows()) {
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
	public Optional<File> saveFile(final String path, final String text) {
		boolean ok = true;
		try(final OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path))) {
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
	public String readTextFile(final String path) {
		final StringBuilder txt = new StringBuilder();

		try(final InputStream is = getClass().getResourceAsStream(path); final Reader reader = new InputStreamReader(is, "UTF-8"); //NON-NLS
			final BufferedReader br = new BufferedReader(reader)) {
			String line = br.readLine();

			while(line != null) {
				txt.append(line).append(LSystem.EOL);
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
	public Optional<File> createTempDir() {
		final String pathTmp = System.getProperty("java.io.tmpdir");
		final String path = pathTmp + (pathTmp.endsWith(LSystem.FILE_SEP) ? "" : LSystem.FILE_SEP) + "latexdraw" + LSystem.FILE_SEP + "latexdrawTmp" +
			//NON-NLS
			System.currentTimeMillis() + new Random().nextInt(100000);
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
}
