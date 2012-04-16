package net.sf.latexdraw.instruments;

import net.sf.latexdraw.actions.UpdateTemplates;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.instrument.Link;
import org.malai.instrument.WidgetInstrument;
import org.malai.interaction.library.MenuItemPressed;
import org.malai.ui.UIComposer;
import org.malai.widget.MMenu;
import org.malai.widget.MMenuItem;

/**
 * This instrument manages the templates.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-16<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class TemplateManager extends WidgetInstrument {
	/** The main menu that contains the template menu items. */
	protected MMenu templateMenu;

	/** The menu item that permits to update the templates. */
	protected MMenuItem updateTemplatesMenu;


	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @since 3.0
	 */
	public TemplateManager(final UIComposer<?> composer) {
		super(composer);
		initialiseWidgets();
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		templateMenu.setEnabled(isActivated());
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new MenuItem2UpdateTemplates(this));
		}catch(final InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(final IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	@Override
	protected void initialiseWidgets() {
		templateMenu = new MMenu(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.103"), true);
		updateTemplatesMenu = new MMenuItem(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.41"), LResources.RELOAD_ICON);

		templateMenu.addSeparator();
		templateMenu.add(updateTemplatesMenu);

		final UpdateTemplates action = new UpdateTemplates();
		action.setTemplatesMenu(templateMenu);
		action.setUpdateThumbnails(false);
		action.doIt();
		action.flush();
	}


	/**
	 * @return The main menu that contains the template menu items.
	 * @since 3.0
	 */
	public final MMenu getTemplateMenu() {
		return templateMenu;
	}


	/** Maps a menu item interaction to an action that updates the templates. */
	private static class MenuItem2UpdateTemplates extends Link<UpdateTemplates, MenuItemPressed, TemplateManager> {
		public MenuItem2UpdateTemplates(final TemplateManager ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, UpdateTemplates.class, MenuItemPressed.class);
		}

		@Override
		public void initAction() {
			action.setUpdateThumbnails(true);
			action.setTemplatesMenu(instrument.templateMenu);
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getMenuItem()==instrument.updateTemplatesMenu;
		}
	}
}
