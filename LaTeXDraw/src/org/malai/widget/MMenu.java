package org.malai.widget;

import java.awt.geom.Point2D;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.malai.interaction.Eventable;
import org.malai.interaction.SwingEventManager;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * This widgets is based on a JMenu. It allows to be used in the Malai framework for picking.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 06/05/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MMenu extends JMenu implements Pickable, Picker, Eventable {
	private static final long	serialVersionUID	= 1L;

	/** The event manager that listens events produced by the panel. May be null. */
	protected SwingEventManager eventManager;


	/**
	 * Creates a menu.
	 * @param withEvtManager True: the panel will have an event manager that gathers.
	 * @since 0.2
	 */
	public MMenu(final boolean withEvtManager) {
		this("Menu", withEvtManager); //$NON-NLS-1$
	}


	/**
	 * Creates a menu.
	 * @param withEvtManager True: the panel will have an event manager that gathers.
	 * @param name The name of the menu.
	 * @since 0.2
	 */
	public MMenu(final String name, final boolean withEvtManager) {
		super(name, true);

		if(withEvtManager) {
			eventManager = new SwingEventManager();
			eventManager.attachTo(this);
		}
	}


	@Override
	public boolean contains(final double x, final double y) {
		return WidgetUtilities.INSTANCE.contains(this, x, y);
	}


	@Override
	public boolean contains(final Object obj) {
		return WidgetUtilities.INSTANCE.contains(getMenuComponents(), obj);
	}



	@Override
	public Pickable getPickableAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickableAt(this, getMenuComponents(), x, y);
	}


	@Override
	public Picker getPickerAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickerAt(this, getMenuComponents(), x, y);
	}


	@Override
	public Point2D getRelativePoint(final double x, final double y, final Object o){
		return WidgetUtilities.INSTANCE.getRelativePoint(getComponents(), x, y, o);
	}


	@Override
	public Picker getPicker() {
		return WidgetUtilities.INSTANCE.getPicker(this);
	}


	@Override
	public SwingEventManager getEventManager() {
		return eventManager;
	}


	@Override
	public boolean hasEventManager() {
		return eventManager!=null;
	}


	@Override
	public JMenuItem add(final JMenuItem menuItem) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, menuItem);
		return super.add(menuItem);
	}
}
