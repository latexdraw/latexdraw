package org.malai.widget;

import java.awt.Component;
import java.awt.geom.Point2D;

import javax.swing.JScrollPane;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;

/**
 * Extends the Java JScrollPane to conform malai requirements.<br>
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
 * 08/14/2011<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MScrollPane extends JScrollPane implements Picker {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link JScrollPane#JScrollPane()}
	 */
	public MScrollPane() {
		super();
	}

	/**
	 * {@link JScrollPane#JScrollPane(Component, int, int)}
	 */
	public MScrollPane(final Component view, final int vsbPolicy, final int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
	}

	/**
	 * {@link JScrollPane#JScrollPane(Component)}
	 */
	public MScrollPane(final Component view) {
		super(view);
	}

	/**
	 * {@link JScrollPane#JScrollPane(int,int)}
	 */
	public MScrollPane(final int vsbPolicy, final int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
	}

	@Override
	public Pickable getPickableAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickableAt(this, getViewport().getComponents(), x, y);
	}

	@Override
	public Picker getPickerAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickerAt(this, getViewport().getComponents(), x, y);
	}

	@Override
	public Point2D getRelativePoint(final double x, final double y, final Object o) {
		return WidgetUtilities.INSTANCE.getRelativePoint(getViewport().getComponents(), x, y, o);
	}

	@Override
	public boolean contains(final Object obj) {
		return WidgetUtilities.INSTANCE.contains(getViewport().getComponents(), obj);
	}
}
