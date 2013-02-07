package net.sf.latexdraw.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

import javax.swing.JLabel;

import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * This class allows to check if a new version of LaTeXDraw is out. This class is a child of Thread
 * to avoid a freeze when the application starts.<br>
 * <br>
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
 * 05/20/2010<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 1.8
 */
public class VersionChecker extends Thread {
	/** The version of the application */
	public final static String VERSION   = "3.0.0";//$NON-NLS-1$

	public final static String VERSION_STABILITY = "alpha 5"; //$NON-NLS-1$

	/** The identifier of the build */
	public static final String ID_BUILD = "20120505";//$NON-NLS-1$

	/** To change if update is needed or not. */
	public static final boolean WITH_UPDATE = true;

    /** The path of the file containing the news */
    public static final String PATH_MSG = "http://latexdraw.sourceforge.net/news.txt"; //$NON-NLS-1$

    /** The field where messages will be displayed. */
    protected JLabel notificationTextField;


	/**
	 * Creates the version checker.
	 * @param notificationTextField The field where messages will be displayed.
	 * @throws IllegalArgumentException If notificationTextField is null.
	 */
	public VersionChecker(final JLabel notificationTextField) {
		super();
		this.notificationTextField = Objects.requireNonNull(notificationTextField);
	}


	@Override
	public void run() {
        checkNewVersion();
	}


 	/**
  	 * Checks if a new version of latexdraw is out.
  	 */
	protected void checkNewVersion() {
		InputStream is=null;
		DataInputStream dis=null;
		InputStreamReader isr=null;
		BufferedReader br=null;

		try {
			is  = new URL(PATH_MSG).openStream();
			dis = new DataInputStream(is);
  			isr = new InputStreamReader(dis);
  			br 	= new BufferedReader(isr);

  			final String line = br.readLine();
			final String[] div = line==null ? null : line.split("_"); //$NON-NLS-1$

			if(div!=null && div.length>3 && div[3].compareTo(VERSION)>0)
				notificationTextField.setText("<html><span style=\"color: rgb(204, 0, 0); font-weight: bold;\">" +
											"Version" + ' ' + div[3]+ ' ' + "available!" + "</html>");
		}catch(final IOException e) { /* Nothing to do. */ }
		finally {
			if(is!=null) try{ is.close(); } catch(final IOException ex) { BadaboomCollector.INSTANCE.add(ex); }
			if(dis!=null) try{ dis.close(); } catch(final IOException ex) { BadaboomCollector.INSTANCE.add(ex); }
			if(isr!=null) try{ isr.close(); } catch(final IOException ex) { BadaboomCollector.INSTANCE.add(ex); }
			if(br!=null) try{ br.close(); } catch(final IOException ex) { BadaboomCollector.INSTANCE.add(ex); }
		}
  	}
}
