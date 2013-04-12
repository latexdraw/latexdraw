package net.sf.latexdraw.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.swing.JButton;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.ui.UIBuilder;

import org.malai.action.library.OpenWebPage;

/**
 * This class allows to check if a new version of LaTeXDraw is out. This class is a child of Thread
 * to avoid a freeze when the application starts.<br>
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
 *<br>
 * 05/20/2010<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 1.8
 */
public class VersionChecker extends Thread {
	/** The version of the application */
	public final static String VERSION   = "3.0.0";//$NON-NLS-1$

	public final static String VERSION_STABILITY = ""; //$NON-NLS-1$

	/** The identifier of the build */
	public static final String ID_BUILD = "20130505";//$NON-NLS-1$

	/** To change if update is needed or not. */
	public static final boolean WITH_UPDATE = true;

    /** The path of the file containing the news */
    public static final String PATH_MSG = "http://latexdraw.sourceforge.net/news.txt"; //$NON-NLS-1$

    /** The field where messages will be displayed. */
    protected JButton buttonUpdate;

    /** The composer of the application. */
    protected UIBuilder builder;


	/**
	 * Creates the version checker.
	 * @param builder The composer of the application.
	 */
	public VersionChecker(final UIBuilder builder) {
		super();
		this.builder = builder;
	}


	@Override
	public void run() {
        checkNewVersion();
	}


 	/**
  	 * Checks if a new version of latexdraw is out.
  	 */
	protected void checkNewVersion() {
		try {
			try(InputStream is  = new URL(PATH_MSG).openStream();
				DataInputStream dis = new DataInputStream(is);
				InputStreamReader isr = new InputStreamReader(dis);
				BufferedReader br 	= new BufferedReader(isr)){
	  			final String line = br.readLine();
				final String[] div = line==null ? null : line.split("_"); //$NON-NLS-1$

				if(div!=null && div.length>3 && div[3].compareTo(VERSION)>0) {
					buttonUpdate = new JButton(LResources.UPDATE_ICON);
					buttonUpdate.setToolTipText("<html><span style=\"color: rgb(204, 0, 0); font-weight: bold;\">" +
												"Version" + ' ' + div[3]+ ' ' + "available!" + "</html>");
					buttonUpdate.setVisible(true);
					buttonUpdate.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent evt) {
							try {
								OpenWebPage action = new OpenWebPage();
								action.setUri(new URI("http://latexdraw.sourceforge.net/"));
								if(action.canDo())
									action.doIt();
								action.flush();
								buttonUpdate.setVisible(false);
							}catch(Exception ex) { BadaboomCollector.INSTANCE.add(ex); }
						}
					});
					builder.getToolbar().add(buttonUpdate);
				}
			}
		}catch(final IOException e) { /* Nothing to do. */ }
  	}
}
