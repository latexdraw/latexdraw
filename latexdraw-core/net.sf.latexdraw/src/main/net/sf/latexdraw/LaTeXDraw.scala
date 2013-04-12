package net.sf.latexdraw

import net.sf.latexdraw.util.LCommandLine
import net.sf.latexdraw.util.LCommandLine.CmdLineState
import net.sf.latexdraw.actions.LoadDrawing
import net.sf.latexdraw.util.VersionChecker
import net.sf.latexdraw.badaboom.BadaboomCollector
import org.malai.mapping.MappingRegistry
import net.sf.latexdraw.ui.LFrame
import net.sf.latexdraw.ui.SplashScreen
import net.sf.latexdraw.util.Theme
import net.sf.latexdraw.generators.svg.SVGDocumentGenerator
import java.io.File
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK
import org.malai.undo.UndoCollector
import net.sf.latexdraw.util.LPath
import org.malai.action.ActionsRegistry
import net.sf.latexdraw.glib.models.impl.LShapeFactory
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory
import net.sf.latexdraw.ui.UIBuilder

/**
 * The main class of the project.<br>
 *<br>
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
 * 2012-04-19<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
object LaTeXDraw {
	// Setting the size of the the saved actions.
	UndoCollector.INSTANCE.setSizeMax(30)
	ActionsRegistry.INSTANCE.setSizeMax(30)

	// Creating the required directories.
	LPath.INSTANCE.checkDirectories

	// Settings the theme.
	Theme.setTheme

	// Settings the factories.
	DrawingTK.setFactory(new LShapeFactory())
	View2DTK.setFactory(new LViewsFactory())


	/**
	 * The main function.
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
    	// Flushes the resources.
    	splashScreen.flush
    	Thread.setDefaultUncaughtExceptionHandler(BadaboomCollector.INSTANCE)

    	if(cmdLine.getFilename!=null) {
	    	val action = new LoadDrawing()
	    	action.setFile(new File(cmdLine.getFilename))
	    	action.setUi(frame)
	    	action.setOpenSaveManager(SVGDocumentGenerator.INSTANCE)
	    	action.setFileChooser(frame.getFileLoader.getDialog(false))
	    	action.doIt
	    	action.flush
    	}

    	// Checking a new version if required.
    	if(VersionChecker.WITH_UPDATE && frame.getPrefSetters.isVersionCheckEnable)
    		new VersionChecker(frame.getComposer).run
    }
}