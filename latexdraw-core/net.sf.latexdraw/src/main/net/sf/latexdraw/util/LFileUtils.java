package net.sf.latexdraw.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;

import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * Defines some workarounds to deal with the problem of the renameto function.
 * The renameto function cannot rename a file from one filesystem to one other.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
 * 09/22/09<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public final class LFileUtils {
	/** The singleton. */
	public static final LFileUtils INSTANCE = new LFileUtils();


	private LFileUtils() {
		super();
	}

	
	/**
	 * Returns the file name without its potential extension.
	 * @param fileNameExt The file name.
	 * @return The file name without its potential extension. An empty string is the parameter is null.
	 */
	public String getFileNameNoExtension(final String fileNameExt) {
		if(fileNameExt==null) return "";
		final int pos = fileNameExt.lastIndexOf(".");
        if (pos == -1) return fileNameExt;
        return fileNameExt.substring(0, pos);
	}
	

	/**
	 * Reads the given file and returns its text.
	 * @param path The path of the file to read.
	 * @return The content of the text file to read. Cannot be null.
	 */
	public String readTextFile(final String path) {
		final StringBuilder txt = new StringBuilder();
		
		try(final InputStream is = getClass().getResourceAsStream(path);
		final Reader reader = new InputStreamReader(is, "UTF-8");//$NON-NLS-1$
		final BufferedReader br = new BufferedReader(reader)){
	        String line = br.readLine();

	        while(line != null) {
	        	txt.append(line).append(LResources.EOL);
	            line = br.readLine();
	        }
		}catch(final IOException ex) { BadaboomCollector.INSTANCE.add(ex); }
		return txt.toString();
	}
	
	
	/**
	 * Creates a temporary directory that will be used to contains temporary latex files.
	 * The created folder will have restricted access: only the user can access the folder.
	 * @return The created folder or null.
	 */
	public File createTempDir() {
		final String pathTmp  = System.getProperty("java.io.tmpdir");	//$NON-NLS-1$
		final String path		= pathTmp + (pathTmp.endsWith(LResources.FILE_SEP) ? "" : LResources.FILE_SEP) + "latexdraw" + LResources.FILE_SEP + //$NON-NLS-1$ //$NON-NLS-2$
									"latexdrawTmp" + System.currentTimeMillis() + new Random().nextInt(100000); //$NON-NLS-1$
		final File tmpDir		= new File(path);
		final boolean ok		= tmpDir.mkdirs();

		if(ok) {
			tmpDir.setReadable(false, false);	// Rights are removed for everybody.
			tmpDir.setReadable(true, true); 	// They are added to the owner only.
			tmpDir.setWritable(false, false);	// same thing here.
			tmpDir.setWritable(true, true);
			tmpDir.deleteOnExit();
		}

		return ok ? tmpDir : null;
	}


	/**
	 * Copies a file.
	 * The renameTo method does not allow action across NFS mounted filesystems
	 * this method is the workaround
	 * @param fromFile The existing File
	 * @param toFile The new File
	 * @return  <code>true</code> if and only if the renaming succeeded;
	 *          <code>false</code> otherwise
	 */
	public boolean copy(final File fromFile, final File toFile) {
		if(fromFile==null || toFile==null)
			return false;

		boolean ok = true;

		try {
	    	try(FileInputStream in 	= new FileInputStream(fromFile);
    			FileOutputStream out = new FileOutputStream(toFile);
    			BufferedInputStream inBuffer = new BufferedInputStream(in);
    			BufferedOutputStream outBuffer= new BufferedOutputStream(out)){

		    	int theByte = inBuffer.read();

		    	while(theByte > -1){
		    		outBuffer.write(theByte);
		    		theByte = inBuffer.read();
		    	}
	    	}
		}catch(final Exception ex) { ok = false; }

    	// cleanup if files are not the same length
    	if(fromFile.length() != toFile.length()) {
    		toFile.delete();
    		ok = false;
    	}

    	return ok;
	}
}
