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
package net.sf.latexdraw.command.shape;

import io.github.interacto.command.library.ActivateInstrument;
import io.github.interacto.instrument.Instrument;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This command activates and moves the text setter to a given position.
 * @author Arnaud Blouin
 */
public class InitTextSetter extends ActivateInstrument {
	/** The text setter to move. */
	private final @NotNull TextSetter setter;
	/** The text to set to the setter. */
	private final @Nullable String text;
	/** The position that takes account of the zoom. */
	private final @NotNull Point position;
	/** The text (shape) to modify throw the setter. Can be null. */
	private final @Nullable Text textShape;
	private final @Nullable Plot plotShape;

	public InitTextSetter(final @NotNull Instrument<?> instrument, final @NotNull TextSetter setter, final @Nullable String text,
						final @NotNull Point position, final @Nullable Text textShape, final @Nullable Plot plotShape) {
		super(instrument);
		this.setter = setter;
		this.text = text;
		this.position = position;
		this.textShape = textShape;
		this.plotShape = plotShape;
	}

	@Override
	public boolean canDo() {
		return (text != null || textShape != null || plotShape != null) && MathUtils.INST.isValidPt(position) && super.canDo();
	}

	@Override
	protected void doCmdBody() {
		super.doCmdBody();
		setter.setPosition(position);
		setter.getTextField().setText(text);
		setter.setText(textShape);
		setter.setPlot(plotShape);
	}
}
