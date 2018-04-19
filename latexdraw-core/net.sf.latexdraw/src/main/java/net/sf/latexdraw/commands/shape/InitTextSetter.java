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
package net.sf.latexdraw.commands.shape;

import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IText;
import org.malai.command.library.ActivateInstrument;
import org.malai.instrument.Instrument;

/**
 * This command activates and moves the text setter to a given position.
 * @author Arnaud Blouin
 */
public class InitTextSetter extends ActivateInstrument {
	/** The text setter to move. */
	private TextSetter setter;

	/** The text to set to the setter. */
	private String text;

	/** The position that takes account of the zoom. */
	private IPoint position;

	/** The text (shape) to modify throw the setter. Can be null. */
	private IText textShape;

	private IPlot plotShape;

	public InitTextSetter(final Instrument<?> instrument, final TextSetter setter, final String text, final IPoint position, final IText textShape,
					final IPlot plotShape) {
		super(instrument);
		this.setter = setter;
		this.text = text;
		this.position = position;
		this.textShape = textShape;
		this.plotShape = plotShape;
	}

	@Override
	public boolean canDo() {
		return super.canDo() && MathUtils.INST.isValidPt(position) && setter != null && (text != null || textShape != null || plotShape != null);
	}

	@Override
	protected void doCmdBody() {
		super.doCmdBody();
		setter.setPosition(position);
		setter.getTextField().setText(text);
		setter.setText(textShape);
		setter.setPlot(plotShape);
	}

	@Override
	public void flush() {
		plotShape = null;
		text = null;
		textShape = null;
		setter = null;
		position = null;
		super.flush();
	}
}
