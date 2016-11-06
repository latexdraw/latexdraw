/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2017 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.GLibUtilities;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IText;
import org.malai.action.library.ActivateInstrument;

/**
 * This action activates and moves the text setter to a given position.
 */
public class InitTextSetter extends ActivateInstrument {
	/** The text setter to move. */
	protected TextSetter setter;

	/** The text to set to the setter. */
	protected String text;

	/** The position that takes account of the zoom. */
	protected IPoint position;

	/** The text (shape) to modify throw the setter. Can be null. */
	protected IText textShape;

	protected IPlot plotShape;


	@Override
	public void flush() {
		super.flush();
		plotShape = null;
		text = null;
		textShape = null;
		setter = null;
		position = null;
	}

	/**
	 * Sets the text shape to modify.
	 * @param val The text (shape) to modify throw the setter. Can be null.
	 * @since 3.0
	 */
	public void setTextShape(final IText val) {
		textShape = val;
	}

	/**
	 * Sets the text to display into the text setter.
	 * @param val The text to set.
	 * @since 3.0
	 */
	public void setText(final String val) {
		text = val;
	}

	/**
	 * Sets the plot to display into the text setter.
	 * @param shape The plot to set.
	 * @since 3.1
	 */
	public void setPlotShape(final IPlot shape) {
		plotShape = shape;
	}

	/**
	 * @param textSetter the textSetter to set.
	 * @since 3.0
	 */
	public void setTextSetter(final TextSetter textSetter) {
		setter = textSetter;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	/**
	 * @param pt The position that takes account of the zoom.
	 * @since 3.0
	 */
	public void setPosition(final IPoint pt) {
		position = pt;
	}


	@Override
	public boolean canDo() {
		return super.canDo() && GLibUtilities.isValidPoint(position) &&
			setter != null && (text != null || textShape != null || plotShape != null);
	}


	@Override
	protected void doActionBody() {
		super.doActionBody();
		setter.setPosition(position);
		setter.getTextField().setText(text);
		setter.setText(textShape);
		setter.setPlot(plotShape);
	}
}
