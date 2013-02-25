package net.sf.latexdraw.instruments;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Objects;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.dialog.PreferencesFrame;
import net.sf.latexdraw.util.LResources;

import org.malai.action.library.ActivateInstrument;
import org.malai.instrument.Link;
import org.malai.swing.instrument.WidgetInstrument;
import org.malai.swing.instrument.library.MenuItem2ShowComponentLink;
import org.malai.swing.interaction.library.MenuItemPressed;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MMenuItem;

/**
 * This instrument activates the preferences setter and shows the preferences frame.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
public class PreferencesActivator extends WidgetInstrument {
	/** The frame containing the widgets of the preferences setter. */
	protected PreferencesFrame preferenceFrame;

	/** The menu item used to activate the setter. */
	protected MMenuItem showPreferencesMenu;

	/** The instrument that modifies the preferences. */
	protected PreferencesSetter prefSetter;


	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param prefSetter The instrument that modifies the preferences.
	 * @throws IllegalArgumentException If the given instrument is null.
	 * @since 3.0
	 */
	public PreferencesActivator(final UIComposer<?> composer, final PreferencesSetter prefSetter) {
		super(composer);

		this.prefSetter = Objects.requireNonNull(prefSetter);
		initialiseWidgets();
	}


	@Override
	protected void initialiseWidgets() {
		showPreferencesMenu = new MMenuItem(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.56"), KeyEvent.VK_P); //$NON-NLS-1$
		showPreferencesMenu.setIcon(LResources.PREFERENCES_ICON);
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new MenuItem2ActivateSetterLink(this));
			addLink(new MenuItem2ShowPreferencesLink(this, null, showPreferencesMenu));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * Initialises and returns the preferences frame.
	 * @return The preferences frame.
	 * @since 3.0
	 */
	protected PreferencesFrame initialisePreferencesFrame() {
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
