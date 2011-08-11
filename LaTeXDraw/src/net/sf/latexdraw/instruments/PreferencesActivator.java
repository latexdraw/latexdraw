package net.sf.latexdraw.instruments;

import java.awt.Component;
import java.awt.event.KeyEvent;

import org.malai.action.library.ActivateInstrument;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.instrument.library.MenuItem2ShowComponentLink;
import org.malai.interaction.library.MenuItemPressed;
import org.malai.widget.MMenuItem;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.dialog.PreferencesFrame;
import net.sf.latexdraw.util.LResources;

/**
 * This instrument activates the preferences setter and shows the preferences frame.<br>
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
 * 01/18/11<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class PreferencesActivator extends Instrument {
	/** The frame containing the widgets of the preferences setter. */
	protected PreferencesFrame preferenceFrame;

	/** The menu item used to activate the setter. */
	protected MMenuItem showPreferencesMenu;

	/** The instrument that modifies the preferences. */
	protected PreferencesSetter prefSetter;


	/**
	 * Creates the instrument.
	 * @param prefSetter The instrument that modifies the preferences.
	 * @throws IllegalArgumentException If the given instrument is null.
	 * @since 3.0
	 */
	public PreferencesActivator(final PreferencesSetter prefSetter) {
		super();

		if(prefSetter==null)
			throw new IllegalArgumentException();

		showPreferencesMenu = new MMenuItem(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.56"), KeyEvent.VK_P); //$NON-NLS-1$
		showPreferencesMenu.setIcon(LResources.PREFERENCES_ICON);
		this.prefSetter = prefSetter;

		initialiseLinks();
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new MenuItem2ActivateSetterLink(this));
			links.add(new MenuItem2ShowPreferencesLink(this, null, showPreferencesMenu));
		}catch(InstantiationException ex){
			BadaboomCollector.INSTANCE.add(ex);
		}catch(IllegalAccessException ex){
			BadaboomCollector.INSTANCE.add(ex);
		}
	}


	/**
	 * Initialises and returns the preferences frame.
	 * @return The preferences frame.
	 * @since 3.0
	 */
	public PreferencesFrame initialisePreferencesFrame() {
		if(preferenceFrame==null) {
			preferenceFrame = new PreferencesFrame(prefSetter);
			prefSetter.addEventable(preferenceFrame);
		}
		return preferenceFrame;
	}


	/**
	 * @return the menu item used to activates the setter.
	 * @since 3.0
	 */
	public MMenuItem getShowPreferencesMenu() {
		return showPreferencesMenu;
	}
}

/**
 * This links maps a menu item to an action that activates the preferences setter.
 */
class MenuItem2ActivateSetterLink extends Link<ActivateInstrument, MenuItemPressed, PreferencesActivator> {
	/**
	 * Creates the link.
	 */
	protected MenuItem2ActivateSetterLink(final PreferencesActivator ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ActivateInstrument.class, MenuItemPressed.class);
	}

	@Override
	public void initAction() {
		action.setInstrument(instrument.prefSetter);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getMenuItem()==instrument.showPreferencesMenu;
	}
}


/**
 * This link maps a menu item to an action that shows the preferences frame.
 */
class MenuItem2ShowPreferencesLink extends MenuItem2ShowComponentLink<PreferencesActivator> {
	/**
	 * Creates the link.
	 */
	protected MenuItem2ShowPreferencesLink(final PreferencesActivator ins, final Component component, final MMenuItem menuItem)
											throws InstantiationException, IllegalAccessException {
		super(ins, component, menuItem);
	}


	@Override
	public void initAction() {
		if(component==null)
			component = instrument.initialisePreferencesFrame();
		super.initAction();
	}
}
