package net.sf.latexdraw.instruments;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.net.URISyntaxException;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.ui.dialog.AboutDialogueBox;
import net.sf.latexdraw.util.LResources;

import org.malai.instrument.WidgetInstrument;
import org.malai.instrument.library.MenuItem2OpenWebPageLink;
import org.malai.instrument.library.MenuItem2ShowComponentLink;
import org.malai.ui.UIComposer;
import org.malai.widget.MMenuItem;

/**
 * This instrument manages help features.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
public class Helper extends WidgetInstrument {
	/** This menu item shows the "About latexdraw" panel. */
	protected MMenuItem aboutItem;

	/** This menu opens the web page used to report bugs. */
	protected MMenuItem reportBugItem;

	/** This menu opens the web page used to donate to the latexdraw project. */
	protected MMenuItem donateItem;

	/** This menu opens the latexdraw forum. */
	protected MMenuItem forumItem;

	/** The dialogue box that gives information on latexdraw. */
	protected AboutDialogueBox aboutFrame;


	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @since 3.0
	 */
	public Helper(final UIComposer<?> composer) {
		super(composer);
		initialiseWidgets();
	}


	@Override
	protected void initialiseWidgets() {
		aboutFrame= null;
		aboutItem = new MMenuItem(LResources.LABEL_ABOUT, KeyEvent.VK_A);
		aboutItem.setIcon(LResources.ABOUT_ICON);

		donateItem = new MMenuItem("Donate", KeyEvent.VK_D);
		donateItem.setIcon(LResources.ABOUT_ICON);

		reportBugItem = new MMenuItem("Report bugs", KeyEvent.VK_B);
		reportBugItem.setIcon(LResources.ERR_ICON);

		forumItem = new MMenuItem("Go to forums", KeyEvent.VK_F);
		forumItem.setIcon(LResources.ABOUT_ICON);

	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new MenuItem2AboutFrame(this, aboutFrame, aboutItem));
			addLink(new MenuItem2OpenWebPageLink(this, reportBugItem, new URI("http://sourceforge.net/tracker/?group_id=156523")));
			addLink(new MenuItem2OpenWebPageLink(this, forumItem, new URI("http://sourceforge.net/projects/latexdraw/forums")));
			addLink(new MenuItem2OpenWebPageLink(this, donateItem, new URI("http://sourceforge.net/project/project_donations.php?group_id=156523")));
		}catch(InstantiationException | IllegalAccessException | URISyntaxException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The menu item that shows the "About latexdraw" panel.
	 * @since 3.0
	 */
	public MMenuItem getAboutItem() {
		return aboutItem;
	}


	/**
	 * @return The menu that opens the web page used to report bugs.
	 * @since 3.0
	 */
	public MMenuItem getReportBugItem() {
		return reportBugItem;
	}


	/**
	 * @return The menu that opens the web page used to donate to the latexdraw project.
	 * @since 3.0
	 */
	public MMenuItem getDonateItem() {
		return donateItem;
	}


	/**
	 * @return the menu that opens the latexdraw forum.
	 * @since 3.0
	 */
	public MMenuItem getForumItem() {
		return forumItem;
	}


	/**
	 * @return The created latexdraw dialogue box.
	 * @since 3.0
	 */
	protected AboutDialogueBox initialiseAboutFrame() {
		if(aboutFrame==null)
			aboutFrame = new AboutDialogueBox();
		return aboutFrame;
	}
}


/**
 * The link between a menu item and the action that shows the latexdraw dialogue box.
 */
class MenuItem2AboutFrame extends MenuItem2ShowComponentLink<Helper> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @param component The component to show/hide.
	 * @param menuItem The menu item used to show/hide to component.
	 * @since 3.0
	 */
	protected MenuItem2AboutFrame(final Helper ins, final Component component, final MMenuItem menuItem) throws InstantiationException, IllegalAccessException {
		super(ins, component, menuItem);
	}


	@Override
	public void initAction() {
		if(component==null)
			component = instrument.initialiseAboutFrame();
		super.initAction();
	}
}
