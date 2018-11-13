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
package net.sf.latexdraw.instrument;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.badaboom.BadaboomHandler;
import net.sf.latexdraw.util.Injector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.malai.javafx.command.ShowStage;
import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument allows to see exceptions launched during the execution of the program.
 * @author Arnaud Blouin
 */
public final class ExceptionsManager extends JfxInstrument implements BadaboomHandler, Initializable {
	private final @NotNull ResourceBundle lang;
	private final @NotNull Injector injector;
	/** The button used to shows the panel of exceptions. */
	@FXML private Button exceptionB;
	/** The frame to show when exceptions occur. */
	private @Nullable Stage stageEx;

	/**
	 * Creates the instrument.
	 */
	public ExceptionsManager(final ResourceBundle lang, final Injector injector) {
		super();
		this.lang = Objects.requireNonNull(lang);
		this.injector = Objects.requireNonNull(injector);
		BadaboomCollector.INSTANCE.addHandler(this);
	}

	/**
	 * @return The frame showing the exceptions. Cannot be null.
	 */
	public Stage getStageEx() {
		if(stageEx == null) {
			try {
				final Parent root = FXMLLoader.load(getClass().getResource("/fxml/Badaboom.fxml"), lang, //NON-NLS
					injector.getInstance(BuilderFactory.class), cl -> injector.getInstance(cl));
				final Scene scene = new Scene(root);
				stageEx = new Stage(StageStyle.UTILITY);
				stageEx.setScene(scene);
				stageEx.centerOnScreen();
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
		}
		return stageEx;
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		exceptionB.managedProperty().bind(exceptionB.visibleProperty());
		exceptionB.visibleProperty().bind(activatedProp);
		setActivated(false);
	}

	@Override
	protected void configureBindings() {
		buttonBinder(ShowStage::new).on(exceptionB).first(c -> {
			c.setWidget(getStageEx());
			c.setVisible(true);
		}).bind();
	}

	@Override
	public void notifyEvent(final Throwable ex) {
		setActivated(true);
	}

	@Override
	public void notifyEvents() {
		setActivated(true);
	}
}
