package org.malai.widget;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * This widgets is based on a JCheckBoxMenuItem. It allows to be used in the Malai framework for picking.<br>
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
public class MCheckBoxMenuItem extends JCheckBoxMenuItem implements Pickable {
	private static final long serialVersionUID = 1L;


	/**
	 * {@link JCheckBoxMenuItem#JCheckBoxMenuItem()}
	 */
	public MCheckBoxMenuItem() {
		super();
	}


	/**
	 * {@link JCheckBoxMenuItem#JCheckBoxMenuItem(Icon)}
	 */
	public MCheckBoxMenuItem(final Icon icon) {
		super(icon);
	}


	/**
	 * {@link JCheckBoxMenuItem#JCheckBoxMenuItem(String)}
	 */
	public MCheckBoxMenuItem(final String text) {
		super(text);
	}


	/**
	 * {@link JCheckBoxMenuItem#JCheckBoxMenuItem(Action)}
	 */
	public MCheckBoxMenuItem(final Action a) {
		super(a);
	}


	/**
	 * {@link JCheckBoxMenuItem#JCheckBoxMenuItem(String, Icon)}
	 */
	public MCheckBoxMenuItem(final String text, final Icon icon) {
		super(text, icon);
	}


	/**
	 * {@link JCheckBoxMenuItem#JCheckBoxMenuItem(String, boolean)}
	 */
	public MCheckBoxMenuItem(final String text, final boolean b) {
		super(text, b);
	}


	/**
	 * {@link JCheckBoxMenuItem#JCheckBoxMenuItem(String, Icon, boolean)}
	 */
	public MCheckBoxMenuItem(final String text, final Icon icon, final boolean b) {
		super(text, icon, b);
	}


	@Override
	public boolean contains(final double x, final double y) {
		return WidgetUtilities.INSTANCE.contains(this, x, y);
	}


	@Override
	public Picker getPicker() {
		return WidgetUtilities.INSTANCE.getPicker(this);
	}
}
