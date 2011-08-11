package org.malai.widget;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * This widgets is based on a JButton. It allows to be used in the Malai framework for picking.<br>
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
public class MButton extends JButton implements Pickable {
	private static final long	serialVersionUID	= 1L;

	/**
	 * {@link JButton#JButton()}
	 */
	public MButton() {
		super();
	}


	/**
	 * {@link JButton#JButton(Action)}
	 */
	public MButton(final Action a) {
		super(a);
	}


	/**
	 * {@link JButton#JButton(Icon)}
	 */
	public MButton(final Icon i) {
		super(i);
	}


	/**
	 * {@link JButton#JButton(String, Icon)}
	 */
	public MButton(final String str, final Icon i) {
		super(str, i);
	}


	/**
	 * {@link JButton#JButton(String)}
	 */
	public MButton(final String str) {
		super(str);
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
