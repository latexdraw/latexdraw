package net.sf.latexdraw.util

import javax.swing.UIManager
import net.sf.latexdraw.badaboom.BadaboomCollector
import org.w3c.dom.NodeList
import java.io.File
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory
import scala.collection.JavaConversions._
import javax.swing.UIManager.LookAndFeelInfo

/**
 * This singleton contains methods to handle look-and feel objects.<br>
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
 * 2012-04-18<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
object Theme {
	/** The look and feel of the program. */
	private var _lookAndFeel : String = ""


	/**
	 * @return The current look and feel.
	 * @since 3.0
	 */
	def lookAndFeel = _lookAndFeel


	/**
	 * Sets the current look and feel.
	 * @since 3.0
	 */
	def setTheme() = {
		try{
			_lookAndFeel = readTheme
			System.setProperty("apple.laf.useScreenMenuBar", "true") //$NON-NLS-1$ //$NON-NLS-2$
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", LResources.LABEL_APP)//$NON-NLS-1$
     		UIManager.setLookAndFeel(_lookAndFeel)
		}
		catch { case ex => BadaboomCollector.INSTANCE.add(ex) }
	}


	/**
	 * @return The look and feel identifier of the current platform.
	 * @since 3.0
	 */
	def platformLnF : String = {
		LSystem.INSTANCE.isLinux match {
			case true => "com.sun.java.swing.plaf.gtk.GTKLookAndFeel" //$NON-NLS-1$
			case false => 
			   LSystem.INSTANCE.isMacOSX match {
			     case true => "com.apple.laf.AquaLookAndFeel"
			     case false => UIManager.getCrossPlatformLookAndFeelClassName
			   } 
		}
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
	def readTheme() : String = {
		var laf : String = null
		val xml = new File(LPath.PATH_PREFERENCES_XML_FILE)

		// Reading the LnF from the preferences.
		if(xml.exists) {
            val node = DocumentBuilderFactory.newInstance.newDocumentBuilder.parse(xml).getFirstChild

            if(node==null || !node.getNodeName.equals(LNamespace.XML_ROOT_PREFERENCES))
            	throw new IllegalArgumentException()

            val nl = node.getChildNodes
            var i = 0
            val size =  nl.getLength

            while(i<size && laf==null) {
            	if(nl.item(i).getNodeName.equals(LNamespace.XML_LAF))
            		laf = nl.item(i).getTextContent
            	i+=1
            }

            // Searching if the read LnF is supported by the current platform.
    		UIManager.getInstalledLookAndFeels.find{lnfInfo => lnfInfo.getName.equals(laf)} match {
    			case Some(lnfInfo) => laf = lnfInfo.getClassName
    			case _ => laf = platformLnF
    		}
		}
		else // Deducing the LnF from the system properties.
			laf = platformLnF

		return laf
	}
}
