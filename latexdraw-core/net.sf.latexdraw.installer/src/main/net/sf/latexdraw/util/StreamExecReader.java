package net.sf.latexdraw.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Defines a thread for managing command execution. While the process is running,
 * the log is gathered from it.
 *
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
	private StringBuilder log;


	/**
	 * Default constructor.
	 * @param is The stream to listen.
	 */
	public StreamExecReader(final InputStream is) {
		super();

		if(is==null)
			throw new IllegalArgumentException();

		stream = is;
	}


	@Override
	public void run() {
		final InputStreamReader isr = new InputStreamReader(stream);
        final BufferedReader br = new BufferedReader(isr);
        final String eol = System.getProperty("line.separator");
        log = new StringBuilder();

        try {
	        String line = br.readLine();

	        while(line != null) {
	            log.append(line).append(eol);
	            line = br.readLine();
	        }
        }catch(IOException ex) { ex.printStackTrace(); }

        try { br.close(); } catch(IOException ex) { ex.printStackTrace(); }
        try { isr.close(); } catch(IOException ex) { ex.printStackTrace(); }
	}


	/**
	 * @return The read log.
	 */
	public String getLog() {
		return log==null ? "" : log.toString();
	}
}
