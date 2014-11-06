package net.sf.latexdraw;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LangTool;

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
	
	
	/**
	 * The entry point of the program.
	 * @param args The parameters.
	 */
	public static void main(String[] args) {
//		val cmdLine = new LCommandLine()
//    	cmdLine.parse(args) match {
//    		case CmdLineState.APPLICATION => launchLatexdraw(cmdLine)
//			case CmdLineState.STOP =>
//    		case CmdLineState.APPLICATION_FILENAME => launchLatexdraw(cmdLine)
//    	}
		launch(args);
	}
	
	
	private Stage showSplashScreen() {
		final Pane splash = new VBox();
		final Image img = new Image("res/LaTeXDrawSmall.png");
		final ImageView iv = new ImageView(img);
		final ProgressBar progressBar = new ProgressBar();
//		while(img.getProgress()<1.0){}
		progressBar.setPrefWidth(img.getWidth());
		splash.getChildren().addAll(iv, progressBar);
		final Scene splashScene = new Scene(splash);
		final Stage splashStage = new Stage(StageStyle.UNDECORATED);
		splashStage.setScene(splashScene);
		splashStage.centerOnScreen();
		splashStage.show();
		splashStage.toFront();
		return splashStage;
	}


	@Override
    public void start(final Stage stage) throws IOException {
		final Stage splashStage = showSplashScreen();
		final Parent root = FXMLLoader.load(getClass().getResource("glib/views/jfx/ui/UI.fxml"), LangTool.INSTANCE.getBundle());
        final Scene scene = new Scene(root);
        stage.setTitle("LaTeXDraw");
        stage.setScene(scene);
        splashStage.hide();
        stage.show();
        
//		frame.getPrefSetters.readXMLPreferences
//
//    	// Showing the user interface.
//    	MappingRegistry.REGISTRY.initMappings
//    	frame.setVisible(true)
//    	frame.setModified(false)
//    	frame.getCanvas.requestFocusInWindow
//    	frame.getCanvas.centreViewport
    	Thread.setDefaultUncaughtExceptionHandler(BadaboomCollector.INSTANCE);
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
	//FIXME clean strings(?):  LaTeXDrawFrame.38, LaTeXDrawFrame.39 LaTeXDrawFrame.90 FileLoaderSaver.4 LaTeXDrawFrame.200 LaTeXDrawFrame.188
	// ShapeBord.1, AbstractParametersFrame.3, AbstractParametersFrame.0b, PropBuilder.13, PropBuilder.11 LaTeXDrawFrame.48
}
