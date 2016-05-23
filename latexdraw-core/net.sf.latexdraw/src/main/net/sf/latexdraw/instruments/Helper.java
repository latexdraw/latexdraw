package net.sf.latexdraw.instruments;

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
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.library.MenuItem2OpenWebPageInteractor;
import org.malai.javafx.instrument.library.MenuItem2ShowLazyStage;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This instrument manages help features.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
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
 * 11/20/10<br>
 * 
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Helper extends JfxInstrument implements Initializable {
	/** This menu item shows the shortcut panel. */
	@FXML
	protected MenuItem shortcutItem;

	/** This menu item shows the "About latexdraw" panel. */
	@FXML
	protected MenuItem aboutItem;

	/** This menu opens the web page used to report bugs. */
	@FXML
	protected MenuItem reportBugItem;

	/** This menu opens the web page used to donate to the latexdraw project. */
	@FXML
	protected MenuItem donateItem;

	/** This menu opens the latexdraw forum. */
	@FXML
	protected MenuItem forumItem;

	/** The dialogue box that gives information on latexdraw. */
	private Stage aboutFrame;

	/** The shortcut dialogue box. */
	private Stage shortcutFrame;

	@FXML
	protected MenuItem manuelItem;

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
	protected void initialiseInteractors() throws IllegalAccessException, InstantiationException {
		 addInteractor(new MenuItem2ShowLazyStage(this, aboutItem, this::getAboutFrame, true));
		 addInteractor(new MenuItem2ShowLazyStage(this, shortcutItem, this::getShortcutsFrame, true));
		try {
			addInteractor(new MenuItem2OpenWebPageInteractor(this, reportBugItem, new URI("https://sourceforge.net/p/latexdraw/bugs/?source=navbar")));
			addInteractor(new MenuItem2OpenWebPageInteractor(this, forumItem, new URI("https://sourceforge.net/p/latexdraw/discussion/")));
			addInteractor(new MenuItem2OpenWebPageInteractor(this, donateItem, new URI("http://sourceforge.net/project/project_donations.php?group_id=156523")));
			addInteractor(new MenuItem2OpenWebPageInteractor(this, manuelItem, new URI("https://github.com/arnobl/latexdraw/wiki/Manual")));
		}catch(URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/** @return The created latexdraw dialogue box. */
	protected Stage getAboutFrame() {
		if(aboutFrame == null) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/jfx/ui/About.fxml"), LangTool.INSTANCE.getBundle());
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
				Parent root = FXMLLoader.load(getClass().getResource("../view/jfx/ui/Shortcuts.fxml"), LangTool.INSTANCE.getBundle());
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

// class MenuItem2AboutFrame extends
// JfxInteractor<ShowStage,ButtonPressed,Helper> {
// protected MenuItem2AboutFrame(final Helper ins, final MenuButton menuItem)
// throws InstantiationException, IllegalAccessException {
// super(ins, false, ShowStage.class, ButtonPressed.class,
// Arrays.asList(ins.aboutItem));
// }
//
// @Override
// public void initAction() {
// if(component==null)
// component = instrument.initialiseAboutFrame();
// super.initAction();
// }
// }
//
//
// /** The link between a menu item and the action that shows the shortcut box.
// */
// class MenuItem2ShortcutFrame extends MenuItem2ShowComponentInteractor<Helper>
// {
// /**
// * Creates the link.
// * @param ins The instrument that contains the link.
// * @param component The component to show/hide.
// * @param menuItem The menu item used to show/hide to component.
// * @since 3.0
// */
// protected MenuItem2ShortcutFrame(final Helper ins, final Component component,
// final MMenuItem menuItem) throws InstantiationException,
// IllegalAccessException {
// super(ins, component, menuItem);
// }
//
// @Override
// public void initAction() {
// if(component==null)
// component = instrument.initialiseShortcutsFrame();
// super.initAction();
// }
// }
