/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.LangTool;
import org.malai.javafx.binding.MenuItem2OpenWebPage;
import org.malai.javafx.binding.MenuItem2ShowLazyStage;
import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument manages help features.
 * @author Arnaud BLOUIN
 */
public class Helper extends JfxInstrument implements Initializable {
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

	/**
	 * Creates the instrument.
	 * @since 3.0
	 */
	public Helper() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
	}

	@Override
	protected void configureBindings() throws IllegalAccessException, InstantiationException {
		addBinding(new MenuItem2ShowLazyStage(this, aboutItem, this::getAboutFrame, true));
		addBinding(new MenuItem2ShowLazyStage(this, shortcutItem, this::getShortcutsFrame, true));
		try {
			addBinding(new MenuItem2OpenWebPage(this, reportBugItem, new URI("https://github.com/arnobl/latexdraw/wiki/Manual#how-to-report-a-bug"))); //NON-NLS
			addBinding(new MenuItem2OpenWebPage(this, forumItem, new URI("https://sourceforge.net/p/latexdraw/discussion/"))); //NON-NLS
			addBinding(new MenuItem2OpenWebPage(this, donateItem, new URI("http://sourceforge.net/project/project_donations.php?group_id=156523"))); //NON-NLS
			addBinding(new MenuItem2OpenWebPage(this, manuelItem, new URI("https://github.com/arnobl/latexdraw/wiki/Manual"))); //NON-NLS
		}catch(final URISyntaxException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}

	/** @return The created latexdraw dialogue box. */
	protected Stage getAboutFrame() {
		if(aboutFrame == null) {
			try {
				final Parent root = FXMLLoader.load(getClass().getResource("/fxml/About.fxml"), LangTool.INSTANCE.getBundle()); //NON-NLS
				final Scene scene = new Scene(root);
				aboutFrame = new Stage(StageStyle.UTILITY);
				aboutFrame.setTitle(LangTool.INSTANCE.getBundle().getString("Res.1"));
				aboutFrame.setScene(scene);
				aboutFrame.centerOnScreen();
			}catch(final Exception e) {
				BadaboomCollector.INSTANCE.add(e);
			}
		}
		return aboutFrame;
	}

	/** @return The created shortcut dialogue box. */
	protected Stage getShortcutsFrame() {
		if(shortcutFrame == null) {
			try {
				final Parent root = FXMLLoader.load(getClass().getResource("/fxml/Shortcuts.fxml"), LangTool.INSTANCE.getBundle()); //NON-NLS
				final Scene scene = new Scene(root);
				shortcutFrame = new Stage(StageStyle.UTILITY);
				shortcutFrame.setTitle(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.3c"));
				shortcutFrame.setScene(scene);
				shortcutFrame.centerOnScreen();
			}catch(final Exception e) {
				BadaboomCollector.INSTANCE.add(e);
			}
		}
		return shortcutFrame;
	}
}
