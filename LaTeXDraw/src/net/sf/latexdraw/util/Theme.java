package net.sf.latexdraw.util;

import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.latexdraw.badaboom.BadaboomCollector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This singleton contains methods to handle look-and feel objects.<br>
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
 * <br>
 * 01/18/11<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public final class Theme {
	/** The singleton. */
	public static final Theme INSTANCE = new Theme();

	/** The look and feel of the program. */
	private String lookAndFeel;


	/**
	 * Creates the singleton.
	 * @since 3.0
	 */
	private Theme() {
		super();
	}


	/**
	 * @return The current look and feel.
	 * @since 3.0
	 */
	public String getLookAndFeel() {
		return lookAndFeel;
	}


	/**
	 * Sets the current look and feel.
	 * @since 3.0
	 */
	public void setTheme() {
		try{
			lookAndFeel = Theme.INSTANCE.readTheme();
			System.setProperty("apple.laf.useScreenMenuBar", "true"); //$NON-NLS-1$ //$NON-NLS-2$
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", LResources.LABEL_APP);//$NON-NLS-1$
     		UIManager.setLookAndFeel(lookAndFeel);
		}
		catch(final Exception ex) { BadaboomCollector.INSTANCE.add(ex); }
	}


	/**
	 * @return The look and feel identifier of the current platform.
	 * @since 3.0
	 */
	public String getPlatformLnF() {
		String laf;

		if(LSystem.INSTANCE.isLinux())
			laf = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"; //$NON-NLS-1$
		else laf = UIManager.getCrossPlatformLookAndFeelClassName();

		return laf;
	}



	/**
	 * Allows to get the theme of the program.
	 * @since 3.0
	 * @throws IllegalArgumentException If a problem occurs.
	 * @return The class of the theme.
	 * @throws IOException If any IO errors occur.
     * @throws SAXException If any parse errors occur.
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested.
	 */
	public String readTheme() throws SAXException, IOException, ParserConfigurationException {
		String laf 	= null;
		File xml 	= new File(LPath.PATH_PREFERENCES_XML_FILE);

		// Reading the LnF from the preferences.
		if(xml.exists()) {
            Node node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml).getFirstChild();

            if(node==null || !node.getNodeName().equals(LNamespace.XML_ROOT_PREFERENCES))
            	throw new IllegalArgumentException();

            NodeList nl	= node.getChildNodes();

            for(int i=0, size=nl.getLength(); i<size && laf==null; i++)
            	if(nl.item(i).getNodeName().equals(LNamespace.XML_LAF))
            		laf = nl.item(i).getTextContent();

            // Searching if the read LnF is supported by the current platform.
    		LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
    		LookAndFeelInfo lnfInfo = null;

    		for(int i=0; laf!=null && i<infos.length && lnfInfo==null; i++)
				if(laf.equals(infos[i].getName()))
					lnfInfo = infos[i];

    		laf = lnfInfo==null ? getPlatformLnF() : lnfInfo.getClassName();
		}
		else // Deducing the LnF from the system properties.
			laf = getPlatformLnF();

		return laf;
	}
}
