package net.sf.latexdraw

import java.awt.event.{InputEvent, KeyEvent}
import java.io.File
import javax.swing.text.DefaultEditorKit
import javax.swing.{InputMap, KeyStroke}
import javax.swing.UIManager

import net.sf.latexdraw.actions.LoadDrawing
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.generators.svg.SVGDocumentGenerator
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK
import net.sf.latexdraw.ui.{LFrame, SplashScreen}
import net.sf.latexdraw.util.LCommandLine.CmdLineState
import net.sf.latexdraw.util._
import org.malai.action.ActionsRegistry
import org.malai.mapping.MappingRegistry
import org.malai.undo.UndoCollector

/**
 * The main class of the project.<br>
 *<br>
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
 *<br>
 * 2012-04-19<br>
  *
  * @author Arnaud BLOUIN
 * @version 3.0
 */
object LaTeXDraw {
  {
	  if(LSystem.INSTANCE.isMac) {
		  val im = UIManager.get("TextField.focusInputMap").asInstanceOf[InputMap]
		  im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.META_DOWN_MASK), DefaultEditorKit.copyAction)
		  im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.META_DOWN_MASK), DefaultEditorKit.pasteAction)
		  im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.META_DOWN_MASK), DefaultEditorKit.cutAction)
	  }

    val node = Preference.readXMLPreferencesFromFile(new File(LPath.PATH_PREFERENCES_XML_FILE)).get(LNamespace.XML_OPENGL)

    if(node==null || java.lang.Boolean.parseBoolean(node.getTextContent)) {
      System.setProperty("sun.java2d.opengl", "true")
    }
    else {
      System.setProperty("sun.java2d.opengl", "false")
    }
  }

	// Setting the size of the the saved actions.
	UndoCollector.INSTANCE.setSizeMax(30)
	ActionsRegistry.INSTANCE.setSizeMax(30)

	// Creating the required directories.
	LPath.INSTANCE.checkDirectories

	// Settings the theme.
	Theme.setTheme

	// Settings the factories.
	View2DTK.setFactory(new LViewsFactory())


	/**
	 * The main function.
    *
    * @param args The parameters given during the call of the program.
	 */
	def main(args: Array[String]) {
    	val cmdLine = new LCommandLine()
    	cmdLine.parse(args) match {
    		case CmdLineState.APPLICATION => launchLatexdraw(cmdLine)
			case CmdLineState.STOP =>
    		case CmdLineState.APPLICATION_FILENAME => launchLatexdraw(cmdLine)
    	}
    }



    private def launchLatexdraw(cmdLine : LCommandLine) {
    	// Creation of the splash screen.
		val splashScreen = new SplashScreen(Theme.lookAndFeel)
		splashScreen.setVisible(true)
		// Creation of the main frame.
    	val frame = new LFrame(splashScreen.getProgressBar)
    	// Composing the user interface.
    	frame.getComposer.compose(splashScreen.getProgressBar)
		frame.getPrefSetters.readXMLPreferences

    	// Removing the splash screen.
    	splashScreen.setVisible(false)
    	// Showing the user interface.
    	MappingRegistry.REGISTRY.initMappings
    	frame.setVisible(true)
    	frame.setModified(false)
    	frame.getCanvas.requestFocusInWindow
    	frame.getCanvas.centreViewport
    	// Flushes the resources.
    	splashScreen.flush
    	Thread.setDefaultUncaughtExceptionHandler(BadaboomCollector.INSTANCE)

    	if(cmdLine.getFilename!=null) {
	    	val action = new LoadDrawing()
        val file = new File(cmdLine.getFilename)
	    	action.setFile(file)
        action.setCurrentFolder(file.getParentFile)
	    	action.setUi(frame)
	    	action.setOpenSaveManager(SVGDocumentGenerator.INSTANCE)
	    	action.setFileChooser(frame.getFileLoader.getDialog(false))
	    	action.doIt
        frame.getFileLoader.onActionExecuted(action)
        frame.getFileLoader.onActionDone(action)
	    	action.flush
    	}

//		frame.getCanvas.getDrawing.addShape(ShapeFactory.createPlot(true, ShapeFactory.createPoint(100, 500), 0, 90, "x sin dup mul"))
//		frame.getCanvas.getDrawing.addShape(ShapeFactory.createPlot(true, ShapeFactory.createPoint(100, 500), 10, 90, "x sin x 2 div 2 exp cos mul"))

      Preference.flushPreferencesCache

    	// Checking a new version if required.
    	if(VersionChecker.WITH_UPDATE && frame.getPrefSetters.isVersionCheckEnable)
    		new VersionChecker(frame.getComposer).run
    }
}