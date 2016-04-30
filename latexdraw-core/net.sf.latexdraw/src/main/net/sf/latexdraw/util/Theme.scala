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
 * 2012-04-18<br>
  *
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
    *
    * @since 3.0
	 */
	def setTheme() = {
		try{
			_lookAndFeel = readTheme
			System.setProperty("apple.laf.useScreenMenuBar", "true") //$NON-NLS-1$ //$NON-NLS-2$
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", LResources.LABEL_APP)//$NON-NLS-1$
     		UIManager.setLookAndFeel(_lookAndFeel)
		}
		catch { case ex: Throwable => BadaboomCollector.INSTANCE.add(ex) }
	}


	/**
	 * @return The look and feel identifier of the current platform.
	 * @since 3.0
	 */
	def platformLnF : String = {
		if(LSystem.INSTANCE.isLinux)
			return "com.sun.java.swing.plaf.gtk.GTKLookAndFeel" //$NON-NLS-1$
		if(LSystem.INSTANCE.isMacOSX)
		    return "com.apple.laf.AquaLookAndFeel"//$NON-NLS-1$
	    if(LSystem.INSTANCE.isWindows)
	    	return "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"//$NON-NLS-1$
		return UIManager.getCrossPlatformLookAndFeelClassName
	}



	/**
	 * Allows to get the theme of the program.
    *
    * @since 3.0
	 * @return The class of the theme.
	 */
	def readTheme() : String = {
    val node = Preference.readXMLPreferencesFromFile(new File(LPath.PATH_PREFERENCES_XML_FILE)).get(LNamespace.XML_LAF)

    if(node != null) {
      UIManager.getInstalledLookAndFeels.find { lnfInfo => lnfInfo.getName.equals(node.getTextContent) } match {
        case Some(lnfInfo) => return lnfInfo.getClassName
        case _ =>
      }
    }

    return platformLnF
	}
}
