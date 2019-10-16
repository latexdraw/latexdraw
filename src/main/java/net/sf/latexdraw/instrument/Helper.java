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

import io.github.interacto.jfx.binding.Bindings;
import io.github.interacto.jfx.instrument.JfxInstrument;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.Injector;
import org.jetbrains.annotations.NotNull;

/**
 * This instrument manages help features.
 * @author Arnaud BLOUIN
 */
public final class Helper extends JfxInstrument implements Initializable {
	/** This menu item shows the shortcut panel. */
	@FXML private MenuItem shortcutItem;
	/** This menu item shows the "About latexdraw" panel. */
	@FXML private MenuItem aboutItem;
	/** This menu opens the web page used to report bugs. */
	@FXML private MenuItem reportBugItem;
	/** This menu opens the web page used to donate to the latexdraw project. */
	@FXML private MenuItem donateItem;
	/** This menu opens the latexdraw forum. */
	@FXML private MenuItem forumItem;
	/** The dialogue box that gives information on latexdraw. */
	private Stage aboutFrame;
	/** The shortcut dialogue box. */
	private Stage shortcutFrame;
	@FXML private MenuItem manuelItem;
	private final @NotNull HostServices services;
	private final @NotNull ResourceBundle lang;
	private final @NotNull Injector injector;

	@Inject
	public Helper(final Injector injector, final ResourceBundle lang, final HostServices services) {
		super();
		this.injector = Objects.requireNonNull(injector);
		this.lang = Objects.requireNonNull(lang);
		this.services = Objects.requireNonNull(services);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
	}

	@Override
	protected void configureBindings() {
		Bindings.menuItem2OpenStage(this::getAboutFrame, true, this)
			.on(aboutItem)
			.bind();

		Bindings.menuItem2OpenStage(this::getShortcutsFrame, true, this)
			.on(shortcutItem)
			.bind();

		Bindings.menuItem2OpenWebPage("https://github.com/arnobl/latexdraw/wiki/Manual#how-to-report-a-bug", services, this) //NON-NLS
			.on(reportBugItem)
			.bind();

		Bindings.menuItem2OpenWebPage("https://sourceforge.net/p/latexdraw/discussion/", services, this) //NON-NLS
			.on(forumItem)
			.bind();

		Bindings.menuItem2OpenWebPage("http://sourceforge.net/project/project_donations.php?group_id=156523", services, this) //NON-NLS
			.on(donateItem)
			.bind();

		Bindings.menuItem2OpenWebPage("https://github.com/arnobl/latexdraw/wiki/Manual", services, this) //NON-NLS
			.on(manuelItem)
			.bind();
	}

	/** @return The created latexdraw dialogue box. */
	Stage getAboutFrame() {
		if(aboutFrame == null) {
			try {
				final Parent root = FXMLLoader.load(getClass().getResource("/fxml/About.fxml"), lang, //NON-NLS
					injector.getInstance(BuilderFactory.class), cl -> injector.getInstance(cl));
				final Scene scene = new Scene(root);
				aboutFrame = new Stage(StageStyle.UTILITY);
				aboutFrame.setTitle(lang.getString("Res.1"));
				aboutFrame.setScene(scene);
				aboutFrame.centerOnScreen();
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
		}
		return aboutFrame;
	}

	/** @return The created shortcut dialogue box. */
	Stage getShortcutsFrame() {
		if(shortcutFrame == null) {
			try {
				final Parent root = FXMLLoader.load(getClass().getResource("/fxml/Shortcuts.fxml"), lang, //NON-NLS
					injector.getInstance(BuilderFactory.class), cl -> injector.getInstance(cl));
				final Scene scene = new Scene(root);
				shortcutFrame = new Stage(StageStyle.UTILITY);
				shortcutFrame.setTitle(lang.getString("LaTeXDrawFrame.3c"));
				shortcutFrame.setScene(scene);
				shortcutFrame.centerOnScreen();
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
		}
		return shortcutFrame;
	}
}
