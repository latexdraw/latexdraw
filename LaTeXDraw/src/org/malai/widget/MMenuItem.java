package org.malai.widget;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * Defines a menu item (based on the JMenuItem) that can be used within Malai.<br>
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
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class MMenuItem extends JMenuItem implements Pickable {
	private static final long	serialVersionUID	= 1L;

	/**
	 * {@link JMenuItem#JMenuItem(Action)}
	 */
	public MMenuItem(final Action a) {
		super(a);
	}

	/**
	 * {@link JMenuItem#JMenuItem(String, int)}
	 */
	public MMenuItem(final String text, final int mnemonic) {
		super(text, mnemonic);
	}

	/**
	 * {@link JMenuItem#JMenuItem()}
	 */
	public MMenuItem() {
		super();
	}


	/**
	 * {@link JMenuItem#JMenuItem(Icon)}
	 */
	public MMenuItem(final Icon icon) {
		super(icon);
	}


	/**
	 * {@link JMenuItem#JMenuItem(String)}
	 */
	public MMenuItem(final String text) {
		super(text);
	}


	/**
	 * {@link JMenuItem#JMenuItem(String, Icon)}
	 */
	public MMenuItem(final String text, final Icon icon) {
		super(text, icon);
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
