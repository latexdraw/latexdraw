package net.sf.latexdraw.instruments;

import java.awt.Component;
import java.awt.event.KeyEvent;

import net.sf.latexdraw.bordel.BordelCollector;
import net.sf.latexdraw.ui.dialog.AboutDialogueBox;
import net.sf.latexdraw.util.LResources;
import fr.eseo.malai.instrument.Instrument;
import fr.eseo.malai.instrument.library.MenuItem2ShowComponentLink;
import fr.eseo.malai.widget.MMenuItem;

/**
 * This instrument manages help features.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public class Helper extends Instrument {
	/** This menu item shows the "About latexdraw" panel. */
	protected MMenuItem aboutItem;

	/** The dialogue box that gives information on latexdraw. */
	protected AboutDialogueBox aboutFrame;


	/**
	 * Creates the instrument.
	 * @since 3.0
	 */
	public Helper() {
		super();

		aboutFrame= null;
		aboutItem = new MMenuItem(LResources.LABEL_ABOUT, KeyEvent.VK_A);
		aboutItem.setIcon(LResources.ABOUT_ICON);

		initialiseLinks();
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new MenuItem2AboutFrame(this, aboutFrame, aboutItem));
		}catch(InstantiationException e){
			BordelCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BordelCollector.INSTANCE.add(e);
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
	public MenuItem2AboutFrame(final Helper ins, final Component component, final MMenuItem menuItem) throws InstantiationException, IllegalAccessException {
		super(ins, component, menuItem);
	}


	@Override
	public void initAction() {
		if(component==null)
			component = instrument.initialiseAboutFrame();
		super.initAction();
	}
}
