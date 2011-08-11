package org.malai.widget;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * This widgets is based on a JCheckBox. It allows to be used in the Malai framework for picking.<br>
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
public class MCheckBox extends JCheckBox implements Pickable {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link JCheckBox#JCheckBox()}
	 */
	public MCheckBox() {
		super();
	}


	/**
	 * {@link JCheckBox#JCheckBox(Icon)}
	 */
	public MCheckBox(final Icon icon) {
		super(icon);
	}


	/**
	 * {@link JCheckBox#JCheckBox(String)}
	 */
	public MCheckBox(final String text) {
		super(text);
	}


	/**
	 * {@link JCheckBox#JCheckBox(Action)}
	 */
	public MCheckBox(final Action a) {
		super(a);
	}


	/**
	 * {@link JCheckBox#JCheckBox(Icon, boolean)}
	 */
	public MCheckBox(final Icon icon, final boolean selected) {
		super(icon, selected);
	}


	/**
	 * {@link JCheckBox#JCheckBox(String, boolean)}
	 */
	public MCheckBox(final String text, final boolean selected) {
		super(text, selected);
	}


	/**
	 * {@link JCheckBox#JCheckBox(String, Icon)}
	 */
	public MCheckBox(final String text, final Icon icon) {
		super(text, icon);
	}


	/**
	 * {@link JCheckBox#JCheckBox(String, Icon, boolean)}
	 */
	public MCheckBox(final String text, final Icon icon, final boolean selected) {
		super(text, icon, selected);
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
