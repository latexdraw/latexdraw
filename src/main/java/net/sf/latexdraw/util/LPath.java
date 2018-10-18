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
import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * Defines paths used in LaTeXDraw.
 * @author Arnaud BLOUIN
 */
public final class LPath {
	/** The singleton. */
	public static final LPath INSTANCE = new LPath();
	/** The name of the cache directory */
	public static final String CACHE_DIR = ".cache"; //NON-NLS
	/** The name of the cache directory for shared templates */
	public static final String CACHE_SHARED_DIR = ".cacheShared"; //NON-NLS
	/** The name of the templates directory */
	public static final String TEMPLATE_DIR = "templates"; //NON-NLS
	public static final String PATH_LOCAL_USER = INSTANCE.getPathLocalUser();
	public static final String PATH_TEMPLATES_DIR_USER = PATH_LOCAL_USER + File.separator + TEMPLATE_DIR;
	public static final String PATH_PREFERENCES_XML_FILE = PATH_LOCAL_USER + File.separator + ".preferences.xml"; //NON-NLS
	public static final String PATH_CACHE_DIR = PATH_LOCAL_USER + File.separator + CACHE_DIR;
	public static final String PATH_CACHE_SHARE_DIR = PATH_LOCAL_USER + File.separator + CACHE_SHARED_DIR;
	public static final String PATH_TEMPLATES_SHARED = INSTANCE.getPathTemplatesShared();
	public static final String PATH_SHARED = INSTANCE.getPathShared();


	private LPath() {
		super();
	}

	/**
	 * Normalises the given namespace URI: if the given namespace is null or empty, an empty
	 * string is returned. Otherwise, the namespace followed by character ':' is returned.
	 * @param nsURI The namespace to normalise.
	 * @return The normalised namespace.
	 * @since 3.0
	 */
	public String getNormaliseNamespaceURI(final String nsURI) {
		return nsURI == null || nsURI.isEmpty() ? "" : nsURI + ':'; //NON-NLS
	}


	/**
	 * @return The home directory of the user depending of his operating system.
	 * @since 3.0
	 */
	private String getPathLocalUser() {
		final String home = System.getProperty("user.home"); //NON-NLS

		if(LSystem.INSTANCE.isVista() || LSystem.INSTANCE.isSeven() || LSystem.INSTANCE.is8() || LSystem.INSTANCE.is10()) {
			return home + "\\AppData\\Local\\latexdraw"; //NON-NLS
		}
		if(LSystem.INSTANCE.isXP()) {
			return home + "\\Application Data\\latexdraw"; //NON-NLS
		}
		if(LSystem.INSTANCE.isMacOSX()) {
			return home + "/Library/Preferences/latexdraw"; //NON-NLS
		}
		return home + "/.latexdraw"; //NON-NLS
	}


	/**
	 * @return The path of the directory where the templates shared by the different users are located.
	 * @since 3.0
	 */
	private String getPathTemplatesShared() {
		return getPathShared() + File.separator + TEMPLATE_DIR;
	}


	/**
	 * @return The path where files are shared by users.
	 * @since 3.0
	 */
	private String getPathShared() {
		final String home = System.getProperty("user.home"); //NON-NLS

		if(LSystem.INSTANCE.isMacOSX()) {
			return "/Users/Shared/latexdraw"; //NON-NLS
		}

		if(LSystem.INSTANCE.isVista()) {
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

		if(LSystem.INSTANCE.isSeven() || LSystem.INSTANCE.is8() || LSystem.INSTANCE.is10()) {
			return home.substring(0, 1 + home.lastIndexOf('\\')) + "Default\\AppData\\Local\\latexdraw"; //NON-NLS
		}

		if(LSystem.INSTANCE.isXP()) {
			return home.substring(0, 1 + home.lastIndexOf('\\')) + "All Users\\Application Data\\latexdraw"; //NON-NLS
		}

		return "/usr/share/latexdraw"; //NON-NLS
	}


	/**
	 * Creates the necessary directories for the installation of LaTeXDraw.
	 * @since 1.9.2
	 */
	public void checkInstallDirectories() {
		try {
			new File(PATH_SHARED).mkdirs();
			new File(PATH_TEMPLATES_SHARED).mkdirs();
		}catch(final SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}


	/**
	 * Creates the necessary directories for the execution of LaTeXDraw.
	 * @since 1.9.2
	 */
	public void checkDirectories() {
		try {
			new File(PATH_LOCAL_USER).mkdirs();
			new File(PATH_TEMPLATES_DIR_USER).mkdirs();
			new File(PATH_CACHE_DIR).mkdirs();
			new File(PATH_CACHE_SHARE_DIR).mkdirs();
		}catch(final SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}
}
