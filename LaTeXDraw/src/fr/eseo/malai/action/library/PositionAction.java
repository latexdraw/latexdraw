package fr.eseo.malai.action.library;

import fr.eseo.malai.action.Action;

/**
 * Defines an abstract action that move an object to the given position.<br>
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
 * 12/20/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public abstract class PositionAction extends Action {
	/** The X-coordinate of the location to zoom. */
	protected double px;

	/** The Y-coordinate of the location to zoom. */
	protected double py;

	/**
	 * Creates the action.
	 */
	public PositionAction() {
		super();

		px = Double.NaN;
		py = Double.NaN;
	}


	@Override
	public boolean canDo() {
		return !Double.isNaN(px) && !Double.isNaN(py);
	}


	/**
	 * @param px The x-coordinate to set.
	 * @since 0.2
	 */
	public void setPx(final double px) {
		this.px = px;
	}


	/**
	 * @param py The y-coordinate to set.
	 * @since 0.2
	 */
	public void setPy(final double py) {
		this.py = py;
	}
}
