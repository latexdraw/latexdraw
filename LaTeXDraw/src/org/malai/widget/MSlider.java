package org.malai.widget;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * This widgets is based on a JSlider. It allows to be used in the Malai framework for picking.<br>
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
public class MSlider extends JSlider implements Pickable {
	private static final long	serialVersionUID	= 1L;

	/**
	 * {@link JSlider#JSlider()}
	 */
	public MSlider() {
		super();
	}

	/**
	 * {@link JSlider#JSlider(int)}
	 */
	public MSlider(final int orientation) {
		super(orientation);
	}

	/**
	 * {@link JSlider#JSlider(BoundedRangeModel)}
	 */
	public MSlider(final BoundedRangeModel brm) {
		super(brm);
	}

	/**
	 * {@link JSlider#JSlider(int, int)}
	 */
	public MSlider(final int min, final int max) {
		super(min, max);
	}

	/**
	 * {@link JSlider#JSlider(int, int, int)}
	 */
	public MSlider(final int min, final int max, final int value) {
		super(min, max, value);
	}

	/**
	 * {@link JSlider#JSlider(int, int, int, int)}
	 */
	public MSlider(final int orientation, final int min, final int max, final int value) {
		super(orientation, min, max, value);
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
