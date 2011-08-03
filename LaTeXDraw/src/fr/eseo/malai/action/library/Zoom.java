package fr.eseo.malai.action.library;

import fr.eseo.malai.properties.Zoomable;

/**
 * Defines an action that zoom int/out a panel.<br>
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
 * 05/11/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class Zoom extends PositionAction {
	/** The object to zoom. */
	protected Zoomable zoomable;

	/** The zooming level. */
	protected double zoomLevel;


	/**
	 * Initialises a Zoom action.
	 * @since 0.2
	 */
	public Zoom() {
		super();

		zoomLevel 	= Double.NaN;
		zoomable 	= null;
	}


	@Override
	public void flush() {
		super.flush();
		zoomable = null;
	}


	@Override
	public boolean canDo() {
		return zoomable!=null && zoomLevel>=Zoomable.MIN_ZOOM && zoomLevel<=Zoomable.MAX_ZOOM;
	}


	@Override
	protected void doActionBody() {
		zoomable.setZoom(px, py, zoomLevel);
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	/**
	 * @param zoomable the zoomable to set.
	 * @since 0.2
	 */
	public void setZoomable(final Zoomable zoomable) {
		this.zoomable = zoomable;
	}


	/**
	 * @param zoomLevel the zoomLevel to set.
	 * @since 0.2
	 */
	public void setZoomLevel(final double zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
}
