package org.malai.action.library;

import org.malai.action.Action;
import org.malai.instrument.Instrument;

/**
 * This action manipulates an instrument.<br>
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
 * @author Arnaud Blouin
 * @since 0.2
 */
public abstract class InstrumentAction  extends Action {
	/** The manipulated instrument. */
	protected Instrument instrument;


	/**
	 * Creates the action.
	 * @since 0.2
	 */
	public InstrumentAction() {
		super();
	}


	@Override
	public void flush() {
		super.flush();
		instrument = null;
	}


	@Override
	public boolean canDo() {
		return instrument!=null;
	}


	/**
	 * @return The manipulated instrument.
	 * @since 0.2
	 */
	public Instrument getInstrument() {
		return instrument;
	}


	/**
	 * Sets the manipulated instrument.
	 * @param instrument The manipulated instrument.
	 * @since 0.2
	 */
	public void setInstrument(final Instrument instrument) {
		this.instrument = instrument;
	}
}
