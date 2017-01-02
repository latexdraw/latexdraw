/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2015 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.instruments.FrameController;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.action.ActionsRegistry;
import org.malai.undo.UndoCollector;

/**
 * The main class of the project.
 */
public class LaTeXDraw extends Application {
	private static Injector injector;

	// Setting the size of the the saved actions.
	static {
		System.setProperty("sun.java2d.opengl", "true");

		Thread.setDefaultUncaughtExceptionHandler(BadaboomCollector.INSTANCE);
		UndoCollector.INSTANCE.setSizeMax(30);
		ActionsRegistry.INSTANCE.setSizeMax(30);
		// Creating the required directories.
		LPath.INSTANCE.checkDirectories();
	}

	/**
	 * The entry point of the program.
	 * @param args The parameters.
	 */
	public static void main(String[] args) {
		// val cmdLine = new LCommandLine()
		// cmdLine.parse(args) match {
		// case CmdLineState.APPLICATION => launchLatexdraw(cmdLine)
		// case CmdLineState.STOP =>
		// case CmdLineState.APPLICATION_FILENAME => launchLatexdraw(cmdLine)
		// }
		launch(args);
	}

	/**
	 * @return The dependencies injector of the application.
	 */
	public static Injector getInjector() {
		return injector;
	}


	private Pane splashLayout;
	private Stage mainStage;
	private ProgressBar loadProgress;

	@Override
	public void init() {
		Image img = new Image("res/LaTeXDrawSmall.png");
		ImageView splash = new ImageView(img);
		loadProgress = new ProgressBar();
		loadProgress.setPrefWidth(img.getWidth());
		splashLayout = new VBox();
		splashLayout.getChildren().addAll(splash, loadProgress);
		splashLayout.setEffect(new DropShadow());
	}

	private void showSplash(final Stage initStage, Task<Void> task) {
		loadProgress.progressProperty().bind(task.progressProperty());

		task.stateProperty().addListener((observableValue, oldState, newState) -> {
			if(newState == Worker.State.SUCCEEDED) {
				loadProgress.progressProperty().unbind();
				loadProgress.setProgress(1);
				FadeTransition fadeSplash = new FadeTransition(Duration.seconds(0.8), splashLayout);
				fadeSplash.setFromValue(1.0);
				fadeSplash.setToValue(0.0);
				fadeSplash.setOnFinished(evt -> {
					initStage.hide();
					mainStage.setIconified(false);
					mainStage.toFront();
				});
				fadeSplash.play();
			}
		});

		final Scene splashScene = new Scene(splashLayout);
		initStage.initStyle(StageStyle.UNDECORATED);
		initStage.setScene(splashScene);
		initStage.centerOnScreen();
		initStage.toFront();
		initStage.show();
	}

	@Override
	public void start(final Stage stage) throws IOException {
		final Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws InterruptedException {
				updateProgress(0.1, 1.0);
				try {
					injector = Guice.createInjector(new LatexdrawModule());
					final Parent root = FXMLLoader.load(getClass().getResource("/fxml/UI.fxml"), LangTool.INSTANCE.getBundle(),
											new LatexdrawBuilderFactory(injector), injector::getInstance);
					updateProgress(0.6, 1.0);
					final Scene scene = new Scene(root);
					updateProgress(0.7, 1.0);
					scene.getStylesheets().add("css/style.css");
					updateProgress(0.8, 1.0);
					Platform.runLater(() -> {
						mainStage = new Stage(StageStyle.DECORATED);
						mainStage.setIconified(true);
						mainStage.setTitle("LaTeXDraw");
						mainStage.setScene(scene);
						updateProgress(0.9, 1.0);
						mainStage.show();
						mainStage.centerOnScreen();
						injector.getInstance(FrameController.class).centreViewport();
						injector.getInstance(Canvas.class).requestFocus();
					});
				}catch(final IOException ex) {
					ex.printStackTrace();
				}
				return null;
			}
		};

		task.setOnFailed(BadaboomCollector.INSTANCE);
		showSplash(stage, task);
		new Thread(task).start();

		// frame.getPrefSetters.readXMLPreferences
		//
		// // Showing the user interface.
		// MappingRegistry.REGISTRY.initMappings
		// frame.setModified(false)
		// frame.getCanvas.requestFocusInWindow
		// frame.getCanvas.centreViewport
		//
		// if(cmdLine.getFilename!=null) {
		// val action = new LoadDrawing()
		//		val file = new File(cmdLine.getFilename)
		//		action.setFile(file)
		//		action.setCurrentFolder(file.getParentFile)
		// action.setUi(frame)
		// action.setOpenSaveManager(SVGDocumentGenerator.INSTANCE)
		// action.setFileChooser(frame.getFileLoader.getDialog(false))
		// action.doIt
		// frame.getFileLoader.onActionExecuted(action)
		// frame.getFileLoader.onActionDone(action)
		// action.flush
		// }
		//
		// // Checking a new version if required.
		// if(VersionChecker.WITH_UPDATE &&
		// frame.getPrefSetters.isVersionCheckEnable)
		// new VersionChecker(frame.getComposer).run
	}

	// FIXME clean strings(?): LaTeXDrawFrame.38, LaTeXDrawFrame.39
	// LaTeXDrawFrame.90 FileLoaderSaver.4 LaTeXDrawFrame.200 LaTeXDrawFrame.188
	// ShapeBord.1, AbstractParametersFrame.3, AbstractParametersFrame.0b,
	// PropBuilder.13, PropBuilder.11 LaTeXDrawFrame.48
	// ParametersAkinPointsFrame.2 PreferencesFrame.quality Pref.1
	// PreferencesFrame.antiAl PreferencesFrame.rendQ PreferencesFrame.colRendQ
	// PreferencesFrame.AlphaQ
	// PreferencesFrame.1 LaTeXDrawFrame.137 LaTeXDrawFrame.138
}
