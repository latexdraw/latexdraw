package org.malai.widget;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JToggleButton;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * This widgets is based on a JToggleButton. It allows to be used in the Malai framework for picking.<br>
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
public class MToggleButton extends JToggleButton implements Pickable {

	private static final long serialVersionUID = 1L;

	/**
	 * {@link JToggleButton#JToggleButton()}
	 */
	public MToggleButton() {
		super();
	}


	/**
	 * {@link JToggleButton#JToggleButton(Icon)}
	 */
	public MToggleButton(final Icon arg0) {
		super(arg0);
	}

	/**
	 * {@link JToggleButton#JToggleButton(String)}
	 */
	public MToggleButton(final String arg0) {
		super(arg0);
	}

	/**
	 * {@link JToggleButton#JToggleButton(Action)}
	 */
	public MToggleButton(final Action arg0) {
		super(arg0);
	}

	/**
	 * {@link JToggleButton#JToggleButton(Icon, boolean)}
	 */
	public MToggleButton(final Icon arg0, final boolean arg1) {
		super(arg0, arg1);
	}

	/**
	 * {@link JToggleButton#JToggleButton(String, boolean)}
	 */
	public MToggleButton(final String arg0, final boolean arg1) {
		super(arg0, arg1);
	}

	/**
	 * {@link JToggleButton#JToggleButton(String, Icon)}
	 */
	public MToggleButton(final String arg0, final Icon arg1) {
		super(arg0, arg1);
	}

	/**
	 * {@link JToggleButton#JToggleButton(String, Icon, boolean)}
	 */
	public MToggleButton(final String arg0, final Icon arg1, final boolean arg2) {
		super(arg0, arg1, arg2);
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
