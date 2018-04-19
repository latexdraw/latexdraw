/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
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
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.instruments.PreferencesSetter;
import net.sf.latexdraw.instruments.StatusBarController;
import net.sf.latexdraw.instruments.TabSelector;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.util.Preference;
import net.sf.latexdraw.util.VersionChecker;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.command.CommandsRegistry;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.ui.JfxUI;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;
import org.malai.undo.UndoCollector;
import org.w3c.dom.Node;

/**
 * The main class of the project.
 * @author Arnaud Blouin
 */
public class LaTeXDraw extends JfxUI {
	public static final String LABEL_APP = "LaTeXDraw"; //NON-NLS

	private static LaTeXDraw instance;

	static {
		final Node node = Preference.INSTANCE.readXMLPreferencesFromFile(new File(LPath.PATH_PREFERENCES_XML_FILE)).get(LNamespace.XML_OPENGL);

		if(node == null || java.lang.Boolean.parseBoolean(node.getTextContent())) {
			System.setProperty("sun.java2d.opengl", "true");
		}else {
			System.setProperty("sun.java2d.opengl", "false");
		}

		Thread.setDefaultUncaughtExceptionHandler(BadaboomCollector.INSTANCE);
		UndoCollector.INSTANCE.setSizeMax(30);
		CommandsRegistry.INSTANCE.setSizeMax(30);
		// Creating the required directories.
		LPath.INSTANCE.checkDirectories();
	}

	/**
	 * The entry point of the program.
	 * @param args The parameters.
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	public static LaTeXDraw getInstance() {
		return instance;
	}

	private Stage mainStage;
	private final Callback<Class<?>, Object> instanceCallBack;
	private final Injector injector;

	public LaTeXDraw() {
		super();
		instance = this;
		injector = new LatexdrawInjector();
		// This callback gathers all the JFX instruments.
		instanceCallBack = cl -> injector.getInstance(cl);
	}

	private void showSplash(final Stage initStage, final Task<Void> task) {
		final ProgressBar loadProgress = new ProgressBar();
		loadProgress.progressProperty().bind(task.progressProperty());

		final Pane splashLayout = new VBox();
		final Image img = new Image("res/LaTeXDrawSmall.png"); //NON-NLS
		final ImageView splash = new ImageView(img);
		splashLayout.getChildren().addAll(splash, loadProgress);
		splashLayout.setEffect(new DropShadow());
		loadProgress.setPrefWidth(img.getWidth());

		task.stateProperty().addListener((observableValue, oldState, newState) -> {
			if(newState == Worker.State.SUCCEEDED) {
				loadProgress.progressProperty().unbind();
				loadProgress.setProgress(1d);
				final FadeTransition fadeSplash = new FadeTransition(Duration.seconds(0.8), splashLayout);
				fadeSplash.setFromValue(1d);
				fadeSplash.setToValue(0d);
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
		initStage.getIcons().add(new Image("/res/LaTeXDrawIcon.png")); //NON-NLS
		initStage.centerOnScreen();
		initStage.toFront();
		initStage.show();
	}

	@Override
	public void start(final Stage stage) {
		final Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws IOException {
				updateProgress(0.1, 1d);
				Platform.runLater(() -> {
					mainStage = new Stage(StageStyle.DECORATED);
					mainStage.setIconified(true);
					mainStage.setTitle(LABEL_APP);
				});

				final Parent root = FXMLLoader.load(getClass().getResource("/fxml/UI.fxml"), LangTool.INSTANCE.getBundle(), //NON-NLS
					new LatexdrawBuilderFactory(injector), instanceCallBack);
				updateProgress(0.6, 1d);
				final Scene scene = new Scene(root);
				updateProgress(0.7, 1d);
				scene.getStylesheets().add("css/style.css"); //NON-NLS
				updateProgress(0.8, 1d);

				Platform.runLater(() -> {
					mainStage.setScene(scene);
					updateProgress(0.9, 1d);
					mainStage.show();
					registerScene(scene);
					final PreferencesSetter prefSetter = injector.getInstance(PreferencesSetter.class);
					prefSetter.readXMLPreferences();
					// Preventing the stage to close automatically.
					mainStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, WindowEvent::consume);
					mainStage.getIcons().add(new Image("/res/LaTeXDrawIcon.png")); //NON-NLS
					mainStage.centerOnScreen();
					injector.getInstance(MagneticGrid.class).update();
					injector.getInstance(TabSelector.class).centreViewport();
					injector.getInstance(Canvas.class).requestFocus();
					// Checking a new version if required.
					if(VersionChecker.WITH_UPDATE && injector.getInstance(PreferencesSetter.class).isVersionCheckEnable()) {
						new Thread(new VersionChecker(injector.getInstance(StatusBarController.class))).start();
					}
					setModified(false);
				});
				return null;
			}
		};

		task.setOnFailed(BadaboomCollector.INSTANCE);
		showSplash(stage, task);
		new Thread(task).start();
	}

	/**
	 * @return The callback that creates controller instances.
	 */
	public Callback<Class<?>, Object> getInstanceCallBack() {
		return instanceCallBack;
	}

	@Override
	public Set<JfxInstrument> getInstruments() {
		return injector.getInstances().stream().filter(ins -> ins instanceof JfxInstrument).map(obj -> (JfxInstrument) obj).collect(Collectors.toSet());
	}

	@Override
	protected <T extends Modifiable & Reinitialisable> Set<T> getAdditionalComponents() {
		final Set<T> set = new HashSet<>();
		set.add((T) injector.getInstance(Canvas.class));
		set.add((T) injector.getInstance(IDrawing.class));
		return set;
	}

	@Override
	public void reinit() {
		super.reinit();
		mainStage.setTitle(LABEL_APP);
	}

	/**
	 * @return The instance injector of the app.
	 */
	public Injector getInjector() {
		return injector;
	}

	/**
	 * @return The main stage of the app.
	 */
	public Stage getMainStage() {
		return mainStage;
	}
}
