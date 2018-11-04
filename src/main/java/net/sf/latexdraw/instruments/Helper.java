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
package net.sf.latexdraw.instruments;

import java.io.IOException;
import java.net.URL;
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
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangService;
import org.malai.javafx.binding.MenuItem2OpenWebPage;
import org.malai.javafx.binding.MenuItem2ShowLazyStage;
import org.malai.javafx.instrument.JfxInstrument;

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
	@Inject private HostServices services;
	@Inject private LangService lang;
	@Inject private Injector injector;

	/**
	 * Creates the instrument.
	 */
	public Helper() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
	}

	@Override
	protected void configureBindings() {
		addBinding(new MenuItem2ShowLazyStage(this, aboutItem, this::getAboutFrame, true));
		addBinding(new MenuItem2ShowLazyStage(this, shortcutItem, this::getShortcutsFrame, true));
		addBinding(new MenuItem2OpenWebPage(this, reportBugItem, "https://github.com/arnobl/latexdraw/wiki/Manual#how-to-report-a-bug", services)); //NON-NLS
		addBinding(new MenuItem2OpenWebPage(this, forumItem, "https://sourceforge.net/p/latexdraw/discussion/", services)); //NON-NLS
		addBinding(new MenuItem2OpenWebPage(this, donateItem, "http://sourceforge.net/project/project_donations.php?group_id=156523", services)); //NON-NLS
		addBinding(new MenuItem2OpenWebPage(this, manuelItem, "https://github.com/arnobl/latexdraw/wiki/Manual", services)); //NON-NLS
	}

	/** @return The created latexdraw dialogue box. */
	Stage getAboutFrame() {
		if(aboutFrame == null) {
			try {
				final Parent root = FXMLLoader.load(getClass().getResource("/fxml/About.fxml"), lang.getBundle(), //NON-NLS
					injector.getInstance(BuilderFactory.class), cl -> injector.getInstance(cl));
				final Scene scene = new Scene(root);
				aboutFrame = new Stage(StageStyle.UTILITY);
				aboutFrame.setTitle(lang.getBundle().getString("Res.1"));
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
				final Parent root = FXMLLoader.load(getClass().getResource("/fxml/Shortcuts.fxml"), lang.getBundle(), //NON-NLS
					injector.getInstance(BuilderFactory.class), cl -> injector.getInstance(cl));
				final Scene scene = new Scene(root);
				shortcutFrame = new Stage(StageStyle.UTILITY);
				shortcutFrame.setTitle(lang.getBundle().getString("LaTeXDrawFrame.3c"));
				shortcutFrame.setScene(scene);
				shortcutFrame.centerOnScreen();
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
		}
		return shortcutFrame;
	}
}
