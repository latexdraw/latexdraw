package net.sf.latexdraw;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;
import net.sf.latexdraw.util.LPath;

import org.malai.action.ActionsRegistry;
import org.malai.undo.UndoCollector;

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
 * 2014-10-14<br>
 * @author Arnaud BLOUIN
 * @version 4.0
 */
public class LaTeXDraw extends Application {
	// Setting the size of the the saved actions.
	static {
		UndoCollector.INSTANCE.setSizeMax(30);
		ActionsRegistry.INSTANCE.setSizeMax(30);
	
		// Creating the required directories.
		LPath.INSTANCE.checkDirectories();
	
		// Settings the factories.
		View2DTK.setFactory(new LViewsFactory());
	}
	
	
	public static void main(String[] args) {
//		val cmdLine = new LCommandLine()
//    	cmdLine.parse(args) match {
//    		case CmdLineState.APPLICATION => launchLatexdraw(cmdLine)
//			case CmdLineState.STOP =>
//    		case CmdLineState.APPLICATION_FILENAME => launchLatexdraw(cmdLine)
//    	}
		launch(args);
	}
	
	
	@Override
    public void start(final Stage stage) throws IOException {
		final Parent root = FXMLLoader.load(getClass().getResource("ui/UI.fxml"));
        final Scene scene = new Scene(root);
        stage.setTitle("LaTeXDraw");
        stage.setScene(scene);
        stage.show();
        
//    	// Creation of the splash screen.
//		val splashScreen = new SplashScreen(Theme.lookAndFeel)
//		splashScreen.setVisible(true)
//		// Creation of the main frame.
//    	val frame = new LFrame(splashScreen.getProgressBar)
//    	// Composing the user interface.
//    	frame.getComposer.compose(splashScreen.getProgressBar)
//		frame.getPrefSetters.readXMLPreferences
//
//    	// Removing the splash screen.
//    	splashScreen.setVisible(false)
//    	// Showing the user interface.
//    	MappingRegistry.REGISTRY.initMappings
//    	frame.setVisible(true)
//    	frame.setModified(false)
//    	frame.getCanvas.requestFocusInWindow
//    	frame.getCanvas.centreViewport
//    	// Flushes the resources.
//    	splashScreen.flush
//    	Thread.setDefaultUncaughtExceptionHandler(BadaboomCollector.INSTANCE)
//
//    	if(cmdLine.getFilename!=null) {
//	    	val action = new LoadDrawing()
//	    	action.setFile(new File(cmdLine.getFilename))
//	    	action.setUi(frame)
//	    	action.setOpenSaveManager(SVGDocumentGenerator.INSTANCE)
//	    	action.setFileChooser(frame.getFileLoader.getDialog(false))
//	    	action.doIt
//	    	action.flush
//    	}
//
//    	// Checking a new version if required.
//    	if(VersionChecker.WITH_UPDATE && frame.getPrefSetters.isVersionCheckEnable)
//    		new VersionChecker(frame.getComposer).run
	}
}
