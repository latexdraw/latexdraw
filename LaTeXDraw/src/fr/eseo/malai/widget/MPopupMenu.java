package fr.eseo.malai.widget;

import java.awt.geom.Point2D;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import fr.eseo.malai.interaction.Eventable;
import fr.eseo.malai.interaction.SwingEventManager;
import fr.eseo.malai.picking.Pickable;
import fr.eseo.malai.picking.Picker;

/**
 * This widgets is based on a JPopupMenu. It allows to be used in the Malai framework for picking.<br>
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
 * 11/13/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MPopupMenu extends JPopupMenu implements Picker, Eventable {
	private static final long serialVersionUID = 1L;

	/** The event manager that listens events produced by the panel. May be null. */
	protected SwingEventManager eventManager;


	/**
	 * Creates the popup menu.
	 * @param withEvtManager True: the panel will have an event manager that gathers.
	 * @since 0.2
	 */
	public MPopupMenu(final boolean withEvtManager) {
		super();

		if(withEvtManager) {
			eventManager = new SwingEventManager();
			eventManager.attachTo(this);
		}
	}


	@Override
	public Pickable getPickableAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickableAt(this, getComponents(), x, y);
	}


	@Override
	public Point2D getRelativePoint(final double x, final double y, final Object obj){
		return WidgetUtilities.INSTANCE.getRelativePoint(getComponents(), x, y, obj);
	}


	@Override
	public Picker getPickerAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickerAt(this, getComponents(), x, y);
	}


	@Override
	public boolean contains(final Object obj) {
		return WidgetUtilities.INSTANCE.contains(getComponents(), obj);
	}


	@Override
	public boolean hasEventManager() {
		return eventManager!=null;
	}


	@Override
	public SwingEventManager getEventManager() {
		return eventManager;
	}


	@Override
	public JMenuItem add(final JMenuItem menuItem) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, menuItem);
		return super.add(menuItem);
	}
}
