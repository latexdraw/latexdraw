package net.sf.latexdraw;

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
import javafx.util.Callback;
import javafx.util.Duration;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LangTool;

import org.malai.action.ActionsRegistry;
import org.malai.undo.UndoCollector;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The main class of the project.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-10-14<br>
 * 
 * @author Arnaud BLOUIN
 * @version 4.0
 */
public class LaTeXDraw extends Application {
	// Setting the size of the the saved actions.
	static {
		Thread.setDefaultUncaughtExceptionHandler(BadaboomCollector.INSTANCE);
		UndoCollector.INSTANCE.setSizeMax(30);
		ActionsRegistry.INSTANCE.setSizeMax(30);
		// Creating the required directories.
		LPath.INSTANCE.checkDirectories();
		// Settings the factories.
		View2DTK.setFactory(new LViewsFactory());
	}

	/**
	 * The entry point of the program.
	 * 
	 * @param args
	 *            The parameters.
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

	Pane		splashLayout;
	Stage		mainStage;
	ProgressBar	loadProgress;

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
					final Injector injector = Guice.createInjector(new LatexdrawModule());
					final Callback<Class<?>, Object> guiceFactory = clazz -> injector.getInstance(clazz);
					final Parent root = FXMLLoader.load(getClass().getResource("glib/views/jfx/ui/UI.fxml"), 
										LangTool.INSTANCE.getBundle(), new LatexdrawBuilderFactory(injector), guiceFactory);
					updateProgress(0.6, 1.0);
					final Scene scene = new Scene(root);
					updateProgress(0.7, 1.0);
					scene.getStylesheets().add("net/sf/latexdraw/glib/views/jfx/ui/style.css");
					updateProgress(0.8, 1.0);
					Platform.runLater(() -> {
						mainStage = new Stage(StageStyle.DECORATED);
						mainStage.setIconified(true);
						mainStage.setTitle("LaTeXDraw");
						mainStage.setScene(scene);
						updateProgress(0.9, 1.0);
						mainStage.show();
						mainStage.centerOnScreen();
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
		// action.setFile(new File(cmdLine.getFilename))
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
