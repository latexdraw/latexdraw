package net.sf.latexdraw.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.lang.LangTool;

/**
 * This class allows to check if a new version of LaTeXDraw is out. This class is a child of Thread
 * to avoid a freeze when the application starts.<br>
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
 *<br>
 * 05/20/2010<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 1.8<br>
 */
public class VersionChecker extends Thread {
	/** The version of the application */
	public final static String VERSION   = "3.0.0";//$NON-NLS-1$

	public final static String VERSION_STABILITY = "pre alpha 1"; //$NON-NLS-1$

	/** The identifier of the build */
	public static final String ID_BUILD = "20110110";//$NON-NLS-1$

	/** To change if update is needed or not. */
	public static final boolean WITH_UPDATE = true;
	
    /** The path of the file containing the news */
    public static final String PATH_MSG = "http://latexdraw.sourceforge.net/news.txt"; //$NON-NLS-1$

    /** The field where messages will be displayed. */
    protected JTextField notificationTextField;


	/**
	 * Creates the version checker.
	 * @param notificationTextField The field where messages will be displayed.
	 * @throws IllegalArgumentException If notificationTextField is null.
	 */
	public VersionChecker(final JTextField notificationTextField) {
		super();

		if(notificationTextField==null)
			throw new IllegalArgumentException();

		this.notificationTextField = notificationTextField;
	}


	@Override
	public void run() {
        checkNewVersion();
	}


 	/**
  	 * Checks if a new version of latexdraw is out.
  	 */
	protected void checkNewVersion() {
		boolean ok = true;
		final URL url;
		final InputStream is;
		final DataInputStream dis;
		final InputStreamReader isr;
		final BufferedReader br;
		final String line;

		try {
			url = new URL(PATH_MSG);
			try {
				is  = url.openStream();
				dis = new DataInputStream(is);
	  			isr = new InputStreamReader(dis);
	  			br 	= new BufferedReader(isr);

	  			try{
	  				String[] div = null;
	  				line = br.readLine();

		  			if(line!=null)
		  				div = line.split("_");//$NON-NLS-1$

					if(div!=null && div.length>3)
						if(div[3].compareTo(VERSION)>0) {
							JOptionPane.showMessageDialog(null,
					 		    "<html><span style=\"color: rgb(204, 0, 0); font-weight: bold;\">"//$NON-NLS-1$
					 		    +div[1]+ '/'+div[2]+'/'+div[0]+LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.208") //$NON-NLS-1$
					 		    +div[3]+LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.209"),  //$NON-NLS-1$
						 		   LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.210"),  //$NON-NLS-1$
						 		    JOptionPane.WARNING_MESSAGE);
							notificationTextField.setText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.211")); //$NON-NLS-1$
						}
						else notificationTextField.setText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.212")); //$NON-NLS-1$
	  			}catch(final IOException e) { ok = false; }

	  			LFileUtils.INSTANCE.closeStream(br);
	  			LFileUtils.INSTANCE.closeStream(isr);
	  			LFileUtils.INSTANCE.closeStream(dis);
	  			LFileUtils.INSTANCE.closeStream(is);
			}catch(final IOException e) { ok = false; }
		}
		catch(final MalformedURLException ex) {
			// That not normal so we launch the crash reporter.
			BadaboomCollector.INSTANCE.add(ex);
			ok = false;
		}

		if(!ok)
  			notificationTextField.setText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.213")); //$NON-NLS-1$
  	}
}
