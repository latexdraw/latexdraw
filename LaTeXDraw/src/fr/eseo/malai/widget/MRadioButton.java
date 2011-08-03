package fr.eseo.malai.widget;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRadioButton;

import fr.eseo.malai.picking.Pickable;
import fr.eseo.malai.picking.Picker;

/**
 * This widgets is based on a JRadioButton. It allows to be used in the Malai framework for picking.<br>
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
 * 01/18/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MRadioButton extends JRadioButton implements Pickable {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link MRadioButton#MRadioButton()}
	 * @since 0.2
	 */
	public MRadioButton() {
		super();
	}

	/**
	 * {@link MRadioButton#MRadioButton(Icon)}
	 * @since 0.2
	 */
	public MRadioButton(final Icon icon) {
		super(icon);
	}

	/**
	 * {@link MRadioButton#MRadioButton(Action)}
	 * @since 0.2
	 */
	public MRadioButton(final Action a) {
		super(a);
	}

	/**
	 * {@link MRadioButton#MRadioButton(String)}
	 * @since 0.2
	 */
	public MRadioButton(final String text) {
		super(text);
	}

	/**
	 * {@link MRadioButton#MRadioButton(Icon,boolean)}
	 * @since 0.2
	 */
	public MRadioButton(final Icon icon, final boolean selected) {
		super(icon, selected);
	}

	/**
	 * {@link MRadioButton#MRadioButton(String,boolean)}
	 * @since 0.2
	 */
	public MRadioButton(final String text, final boolean selected) {
		super(text, selected);
	}

	/**
	 * {@link MRadioButton#MRadioButton(String,Icon)}
	 * @since 0.2
	 */
	public MRadioButton(final String text, final Icon icon) {
		super(text, icon);
	}

	/**
	 * {@link MRadioButton#MRadioButton(String,Icon,boolean)}
	 * @since 0.2
	 */
	public MRadioButton(final String text, final Icon icon, final boolean selected) {
		super(text, icon, selected);
	}


	@Override
	public Picker getPicker() {
		return WidgetUtilities.INSTANCE.getPicker(this);
	}


	@Override
	public boolean contains(final double x, final double y) {
		return WidgetUtilities.INSTANCE.contains(this, x, y);
	}
}
