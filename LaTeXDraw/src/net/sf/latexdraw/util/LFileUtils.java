package net.sf.latexdraw.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Defines some workarounds to deal with the problem of the renameto function.
 * The renameto function cannot rename a file from one filesystem to one other.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
	 * Closes the given closeable object.
	 * @param closeable The object to close.
	 * @return True: the object has been successfully closed.
	 * @since 3.0
	 */
	public boolean closeStream(final Closeable closeable) {
		boolean ok;

		if(closeable==null)
			ok = false;
		else
	    	try {
	    		closeable.close();
	    		ok = true;
	    	}catch(final IOException e) { ok = false; }

    	return ok;
	}



	/**
	 * Creates a temporary directory that will be used to contains temporary latex files.
	 * The created folder will have restricted access: only the user can access the folder.
	 * @return The created folder or null.
	 */
	public File createTempDir() {
		final String pathTmp  = System.getProperty("java.io.tmpdir");	//$NON-NLS-1$
		final String sep		= System.getProperty("file.separator");	//$NON-NLS-1$
		final String path		= pathTmp + (pathTmp.endsWith(sep) ? "" : sep) + "latexdrawTmp" + System.currentTimeMillis(); //$NON-NLS-1$ //$NON-NLS-2$
		final File tmpDir		= new File(path);
		final boolean ok		= tmpDir.mkdir();

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
		final FileInputStream in;
    	final FileOutputStream out;
    	final BufferedInputStream inBuffer;
    	final BufferedOutputStream outBuffer;
    	int theByte = 0;

		try {
	    	in 	= new FileInputStream(fromFile);
		}catch(final FileNotFoundException ex) { return false; }
		try {
	    	out = new FileOutputStream(toFile);
		}catch(final FileNotFoundException ex) {
			closeStream(in);
			return false;
		}
    	inBuffer = new BufferedInputStream(in);
    	outBuffer= new BufferedOutputStream(out);

    	try {
	    	while ((theByte = inBuffer.read()) > -1)
	    		outBuffer.write(theByte);
    	}catch(IOException ex) { ok = false; }

    	ok = closeStream(outBuffer) && ok;
    	ok = closeStream(inBuffer) && ok;
    	ok = closeStream(out) && ok;
    	ok = closeStream(in) && ok;

    	// cleanup if files are not the same length
    	if(fromFile.length() != toFile.length()) {
    		toFile.delete();
    		ok = false;
    	}

    	return ok;
	}

	/**
	* Moves a file.
	* The renameTo method does not allow action across NFS mounted filesystems
	* this method is the workaround
	* @param fromFile The existing File
	* @param toFile The new File
	* @return  <code>true</code> if and only if the renaming succeeded;
	*          <code>false</code> otherwise
	*/
	public boolean move(final File fromFile, final File toFile) {
		if(fromFile==null || toFile==null)
			return false;

	    if(fromFile.renameTo(toFile))
	      return true;

	    // delete if copy was successful, otherwise move will fail
	    if(copy(fromFile, toFile))
	      return fromFile.delete();

	    return false;
	}
}
