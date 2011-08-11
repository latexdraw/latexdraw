package org.malai.widget;

import javax.swing.JTextArea;
import javax.swing.text.Document;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * This widgets is based on a JTextArea. It allows to be used in the Malai framework for picking.<br>
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
 * 12/12/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MTextArea extends JTextArea implements Pickable {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link JTextArea}
	 */
	public MTextArea() {
		super();
	}

	/**
	 * {@link JTextArea}
	 */
	public MTextArea(final String text) {
		super(text);
	}

	/**
	 * {@link JTextArea}
	 */
	public MTextArea(final Document doc) {
		super(doc);
	}

	/**
	 * {@link JTextArea}
	 */
	public MTextArea(final int rows, final int columns) {
		super(rows, columns);
	}

	/**
	 * {@link JTextArea}
	 */
	public MTextArea(final String text, final int rows, final int columns) {
		super(text, rows, columns);
	}

	/**
	 * {@link JTextArea}
	 */
	public MTextArea(final Document doc, final String text, final int rows, final int columns) {
		super(doc, text, rows, columns);
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
