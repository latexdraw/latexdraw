package org.malai.widget;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;

/**
 * This widgets is based on a JProgressBar.<br>
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
 * 02/11/2012<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MProgressBar extends JProgressBar {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link JProgressBar#JProgressBar()}
	 */
	public MProgressBar() {
		super();
	}


	/**
	 * {@link JProgressBar#JProgressBar(int)}
	 */
	public MProgressBar(final int orient) {
		super(orient);
	}


	/**
	 * {@link JProgressBar#JProgressBar(BoundedRangeModel)}
	 */
	public MProgressBar(final BoundedRangeModel newModel) {
		super(newModel);
	}


	/**
	 * {@link JProgressBar#JProgressBar(int,int)}
	 */
	public MProgressBar(final int min, final int max) {
		super(min, max);
	}


	/**
	 * {@link JProgressBar#JProgressBar(int,int,int)}
	 */
	public MProgressBar(final int orient, final int min, final int max) {
		super(orient, min, max);
	}


	/**
	 * Adds the increment to the progress bar (if the new value is contained in the min-max bounds of the progress bar).
	 * @param increment The increment to add.
	 * @since 0.2
	 */
	public void addToProgressBar(final int increment) {
		final int cpt = getValue()+increment;

		if(cpt>=getMinimum() && cpt<=getMaximum())
			setValue(cpt);
	}
}
