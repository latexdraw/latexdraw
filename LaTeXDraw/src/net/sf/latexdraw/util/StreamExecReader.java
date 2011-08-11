package net.sf.latexdraw.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * Defines a thread for managing command execution. While the process is running,
 * the log is gathered from it.
 *
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
 *<br>
 * 09/14/09<br>
 * @author Arnaud BLOUIN
 * @version 2.0.4<br>
 */
public class StreamExecReader extends Thread {
	/** The stream to listen. */
	private InputStream stream;

	/** The read log. */
	private String log;


	/**
	 * Default constructor.
	 * @param is The stream to listen.
	 */
	public StreamExecReader(final InputStream is) {
		super();

		stream = is;
		log	   = ""; //$NON-NLS-1$
	}


	@Override
	public void run() {
		InputStreamReader isr = null;
        BufferedReader br     = null;

		try {
			if(stream!=null) {
	            isr 		= new InputStreamReader(stream);
	            br 	  		= new BufferedReader(isr);
	            String line = null;
	            log			= ""; //$NON-NLS-1$

	            while((line = br.readLine()) != null)
	                log += line;
			}
        }catch(IOException ioe) { BadaboomCollector.INSTANCE.add(ioe); }
        try {
        	if(br!=null)
        		br.close();
        }catch(IOException ioe) { BadaboomCollector.INSTANCE.add(ioe); }
        try {
        	if(isr!=null)
        		isr.close();
        }catch(IOException ioe) { BadaboomCollector.INSTANCE.add(ioe); }
	}


	/**
	 * @return The read log.
	 */
	public String getLog() {
		synchronized(log){
			return log;
		}
	}
}
