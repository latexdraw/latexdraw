/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import javafx.beans.NamedArg;
import javafx.event.ActionEvent;
import javafx.scene.control.Spinner;

/**
 * A spinner that works...
 * @author Arnaud BLOUIN
 */
public class LSpinner<T> extends Spinner<T> {
	public LSpinner() {
		super();
		configureSpinner();
	}

	public LSpinner(@NamedArg("min") final int min, @NamedArg("max") final int max, @NamedArg("initialValue") final int initialValue) {
		super(min, max, initialValue);
		configureSpinner();
	}

	public LSpinner(@NamedArg("min") final int min, @NamedArg("max") final int max, @NamedArg("initialValue") final int initialValue, @NamedArg
		("amountToStepBy") final int amountToStepBy) {
		super(min, max, initialValue, amountToStepBy);
		configureSpinner();
	}

	public LSpinner(@NamedArg("min") final double min, @NamedArg("max") final double max, @NamedArg("initialValue") final double initialValue) {
		super(min, max, initialValue);
		configureSpinner();
	}

	public LSpinner(@NamedArg("min") final double min, @NamedArg("max") final double max, @NamedArg("initialValue") final double initialValue,
					@NamedArg("amountToStepBy") final double amountToStepBy) {
		super(min, max, initialValue, amountToStepBy);
		configureSpinner();
	}

	private final void configureSpinner() {
		setOnScroll(event -> {
			if(event.getDeltaY() < 0d) {
				decrement();
			}else {
				if(event.getDeltaY() > 0d) {
					increment();
				}
			}
		});
	}

	@Override
	public void increment(final int steps) {
		final T value = getValue();
		super.increment(steps);
		if(value != null && !value.equals(getValue())) {
			sendAction();
		}
	}

	@Override
	public void decrement(final int steps) {
		final T value = getValue();
		super.decrement(steps);
		if(value != null && !value.equals(getValue())) {
			sendAction();
		}
	}

	private void sendAction() {
		fireEvent(new ActionEvent(this, null));
	}
}
