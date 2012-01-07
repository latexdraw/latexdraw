package org.malai.widget;

import java.awt.Component;
import java.awt.geom.Point2D;

import javax.swing.JToolBar;

import org.malai.interaction.Eventable;
import org.malai.interaction.SwingEventManager;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * This widgets is based on a JToolBar. It allows to be used in the Malai framework for picking.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
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
public class MToolBar extends JToolBar implements Picker, Eventable {
	private static final long serialVersionUID = 1L;

	/** The event manager that listens events produced by the toolbar. May be null. */
	protected SwingEventManager eventManager;


	/**
	 * Creates a toolbar.
	 * @param withEvtManager True: the toolbar will have an event manager.
	 * @since 0.1
	 */
	public MToolBar(final boolean withEvtManager) {
		super();

		if(withEvtManager) {
			eventManager = new SwingEventManager();
			eventManager.attachTo(this);
		}
	}


	@Override
	public Point2D getRelativePoint(final double x, final double y, final Object o){
		return WidgetUtilities.INSTANCE.getRelativePoint(getComponents(), x, y, o);
	}


	@Override
	public Pickable getPickableAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickableAt(this, getComponents(), x, y);
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
	public SwingEventManager getEventManager() {
		return eventManager;
	}


	@Override
	public Component add(final Component comp, final int index) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, comp);
		return super.add(comp, index);
	}


	@Override
	public void add(final Component comp, final Object constraints, final int index) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, comp);
		super.add(comp, constraints, index);
	}


	@Override
	public void add(final Component comp, final Object constraints) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, comp);
		super.add(comp, constraints);
	}



	@Override
	public Component add(final Component comp) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, comp);
		return super.add(comp);
	}



	@Override
	public Component add(final String name, final Component comp) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, comp);
		return super.add(name, comp);
	}



	@Override
	public boolean hasEventManager() {
		return eventManager!=null;
	}
}
