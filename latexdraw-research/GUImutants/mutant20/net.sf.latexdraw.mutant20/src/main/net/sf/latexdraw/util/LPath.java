package net.sf.latexdraw.util;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * This class defines paths used in LaTeXDraw.<br>
 * <br>
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
 * <br>
 * 05/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public final class LPath {
	/** The singleton. */
	public static final LPath INSTANCE = new LPath();

	/** The name of the cache directory */
	public static final String CACHE_DIR 		= ".cache";//$NON-NLS-1$

	/** The name of the cache directory for shared templates */
	public static final String CACHE_SHARED_DIR = ".cacheShared";//$NON-NLS-1$

	/** The name of the templates directory */
	public static final String TEMPLATE_DIR 	= "templates";//$NON-NLS-1$

	public static final String PATH_LOCAL_USER 				= INSTANCE.getPathLocalUser();
	public static final String PATH_TEMPLATES_DIR_USER 		= PATH_LOCAL_USER + File.separator + TEMPLATE_DIR;
	public static final String PATH_PREFERENCES_XML_FILE   	= PATH_LOCAL_USER + File.separator + ".preferences.xml";//$NON-NLS-1$
	public static final String PATH_CACHE_DIR      	   		= PATH_LOCAL_USER + File.separator + CACHE_DIR;
	public static final String PATH_CACHE_SHARE_DIR	   		= PATH_LOCAL_USER + File.separator + CACHE_SHARED_DIR;
	public static final String PATH_TEMPLATES_SHARED   		= INSTANCE.getPathTemplatesShared();
	public static final String PATH_SHARED 			  		= INSTANCE.getPathShared();


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
		return nsURI==null || nsURI.length()==0 ? "" : nsURI + ':'; //$NON-NLS-1$
	}


	/**
	 * @return The home directory of the user depending of his operating system.
	 * @since 3.0
	 */
	private String getPathLocalUser() {
		final String home = System.getProperty("user.home");//$NON-NLS-1$
		final String path;

		if(LSystem.INSTANCE.isVista() || LSystem.INSTANCE.isSeven())
			path = home + "\\AppData\\Local\\latexdraw";//$NON-NLS-1$
		else if(LSystem.INSTANCE.isXP())
				path = home + "\\Application Data\\latexdraw";//$NON-NLS-1$
		else if(LSystem.INSTANCE.isMacOSX())
				path = home + "/Library/Preferences/latexdraw";//$NON-NLS-1$
		else path = home + "/.latexdraw";//$NON-NLS-1$

		return path;
	}


	/**
	 * @return The path of the directory where the templates shared by the different users are located.
	 * @since 3.0
	 */
	private String getPathTemplatesShared() {
		return getPathShared()+File.separator+TEMPLATE_DIR;
	}


	/**
	 * @return The path where files are shared by users.
	 * @since 3.0
	 */
	private String getPathShared() {
		final String home = System.getProperty("user.home");//$NON-NLS-1$

		if(LSystem.INSTANCE.isMacOSX())
			return "/Users/Shared/latexdraw";//$NON-NLS-1$

		if(LSystem.INSTANCE.isVista()) {
			File dir = new File("C:\\ProgramData");//$NON-NLS-1$
			int cpt = 0;
			final int max = 10;

			while(!dir.exists() && cpt<max)
				dir = new File((char)('C'+cpt++)+":\\ProgramData");//$NON-NLS-1$

			if(dir.exists())
				return dir.getPath()+"\\latexdraw";//$NON-NLS-1$

			return home.substring(0, 1+home.lastIndexOf('\\'))+"All Users\\Application Data\\latexdraw";//$NON-NLS-1$
		}

		if(LSystem.INSTANCE.isSeven())
			return home.substring(0, 1+home.lastIndexOf('\\'))+"Default\\AppData\\Local\\latexdraw";//$NON-NLS-1$

		if(LSystem.INSTANCE.isXP())
			return home.substring(0, 1+home.lastIndexOf('\\'))+"All Users\\Application Data\\latexdraw";//$NON-NLS-1$

		return "/usr/share/latexdraw";//$NON-NLS-1$
	}



	/**
	 * Creates the necessary directories for the installation of LaTeXDraw.
	 * @since 1.9.2
	 */
	public void checkInstallDirectories() {
		try {
			(new File(PATH_SHARED)).mkdirs();
			(new File(PATH_TEMPLATES_SHARED)).mkdirs();
		}
		catch(SecurityException e) { BadaboomCollector.INSTANCE.add(e); }
	}


	/**
	 * Creates the necessary directories for the execution of LaTeXDraw.
	 * @since 1.9.2
	 */
	public void checkDirectories() {
		try {
			(new File(PATH_LOCAL_USER)).mkdirs();
			(new File(PATH_TEMPLATES_DIR_USER)).mkdirs();
			(new File(PATH_CACHE_DIR)).mkdirs();
			(new File(PATH_CACHE_SHARE_DIR)).mkdirs();
		}
		catch(SecurityException e) { BadaboomCollector.INSTANCE.add(e); }
	}



	/**
	 * Allows to get the path of the project where the class LaTeXDrawPath is located.
	 * @return The path or null.
	 * @since 1.9.2
	 */
	public String getPathJar() {
		try {
			String path = LPath.class.getSimpleName() + ".class";//$NON-NLS-1$
		    URL url = LPath.class.getResource(path);
		    path = URLDecoder.decode(url.toString(), "UTF-8");//$NON-NLS-1$
		    int index = path.lastIndexOf('/');
		    path = path.substring(0, index);
		    String jar = "jar:file:", file = "file:";//$NON-NLS-1$//$NON-NLS-2$

		    if(path.startsWith(jar))  {
		    	index = path.lastIndexOf('!');
		    	path = path.substring(jar.length(), path.substring(0, index).lastIndexOf('/'));
		    }
		    else {
		    	path = path.substring(file.length(), path.length());
		    	Package pack = LPath.class.getPackage();

		    	if(null != pack) {
		    		String packPath = pack.getName().replace('.', '/');

		    		if(path.endsWith(packPath))
		    			path = path.substring(0, path.length() - packPath.length());
		      }
		    }

		    return path;

		}catch(Exception e) {
			BadaboomCollector.INSTANCE.add(e);
			return null;
		}
	}
}
