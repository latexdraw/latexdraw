package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.ui.dialog.ShortcutsFrame;
import net.sf.latexdraw.util.LangTool;

import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument manages help features.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 11/20/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Helper extends JfxInstrument {
	/** This menu item shows the shortcut panel. */
	@FXML protected MenuItem shortcutItem;

	/** This menu item shows the "About latexdraw" panel. */
	@FXML protected MenuItem aboutItem;

	/** This menu opens the web page used to report bugs. */
	@FXML protected MenuItem reportBugItem;

	/** This menu opens the web page used to donate to the latexdraw project. */
	@FXML protected MenuItem donateItem;

	/** This menu opens the latexdraw forum. */
	@FXML protected MenuItem forumItem;

	/** The dialogue box that gives information on latexdraw. */
	protected Stage aboutFrame;

	/** The shortcut dialogue box. */
	protected ShortcutsFrame shortcutFrame;

	@FXML protected MenuItem manuelItem;


	/**
	 * Creates the instrument.
	 * @since 3.0
	 */
	public Helper() {
		super();
	}

	@Override
	protected void initialiseInteractors() {
//		try{
//			addInteractor(new MenuItem2AboutFrame(this, aboutFrame, aboutItem));
//			addInteractor(new MenuItem2ShortcutFrame(this, aboutFrame, shortcutItem));
//			addInteractor(new MenuItem2OpenWebPageInteractor(this, reportBugItem, new URI("http://sourceforge.net/tracker/?group_id=156523"))); //$NON-NLS-1$
//			addInteractor(new MenuItem2OpenWebPageInteractor(this, forumItem, new URI("http://sourceforge.net/projects/latexdraw/forums"))); //$NON-NLS-1$
//			addInteractor(new MenuItem2OpenWebPageInteractor(this, donateItem, new URI("http://sourceforge.net/project/project_donations.php?group_id=156523"))); //$NON-NLS-1$
//			addInteractor(new MenuItem2OpenWebPageInteractor(this, manuelItem, new URI("https://github.com/arnobl/latexdraw/wiki/Manual"))); //$NON-NLS-1$
//		}catch(InstantiationException | IllegalAccessException | URISyntaxException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}

	/** @return The created latexdraw dialogue box. */
	protected Stage getAboutFrame() {
		if(aboutFrame==null){
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../glib/views/jfx/ui/About.fxml"), LangTool.INSTANCE.getBundle());
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
	protected ShortcutsFrame initialiseShortcutsFrame() {
		if(shortcutFrame==null)
			shortcutFrame = new ShortcutsFrame();
		return shortcutFrame;
	}
}


///** The link between a menu item and the action that shows the latexdraw dialogue box. */
//class MenuItem2AboutFrame extends MenuItem2ShowComponentInteractor<Helper> {
//	protected MenuItem2AboutFrame(final Helper ins, final Component component, final MMenuItem menuItem) throws InstantiationException, IllegalAccessException {
//		super(ins, component, menuItem);
//	}
//
//	@Override
//	public void initAction() {
//		if(component==null)
//			component = instrument.initialiseAboutFrame();
//		super.initAction();
//	}
//}
//
//
///** The link between a menu item and the action that shows the shortcut box. */
//class MenuItem2ShortcutFrame extends MenuItem2ShowComponentInteractor<Helper> {
//	/**
//	 * Creates the link.
//	 * @param ins The instrument that contains the link.
//	 * @param component The component to show/hide.
//	 * @param menuItem The menu item used to show/hide to component.
//	 * @since 3.0
//	 */
//	protected MenuItem2ShortcutFrame(final Helper ins, final Component component, final MMenuItem menuItem) throws InstantiationException, IllegalAccessException {
//		super(ins, component, menuItem);
//	}
//
//	@Override
//	public void initAction() {
//		if(component==null)
//			component = instrument.initialiseShortcutsFrame();
//		super.initAction();
//	}
//}
