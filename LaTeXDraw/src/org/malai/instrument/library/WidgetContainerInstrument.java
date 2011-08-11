package org.malai.instrument.library;

import javax.swing.JComponent;

import org.malai.instrument.Instrument;


/**
 * An instrument that defines a widgets that contains the widgets of the instruments.<br>
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
 * 11/16/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public abstract class WidgetContainerInstrument extends Instrument {
	/** The component contains the widgets of the instrument. Used to
	 *  show/hide widgets during activation. */
	protected JComponent widgetContainer;


	/**
	 * Creates the instrument.
	 * @since 3.0
	 */
	public WidgetContainerInstrument() {
		super();

		widgetContainer = null;
	}


	/**
	 * @param mainWidget The component contains the widgets of the instrument. Used to
	 *  show/hide widgets during activation. If null, nothing is done.
	 * @since 3.0
	 */
	public void setWidgetContainer(final JComponent mainWidget) {
		if(mainWidget!=null)
			this.widgetContainer = mainWidget;
	}
}
