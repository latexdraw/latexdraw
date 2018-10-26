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
package net.sf.latexdraw.commands;

import net.sf.latexdraw.commands.shape.ShapeProperties;
import net.sf.latexdraw.commands.shape.ShapePropertyCmd;
import net.sf.latexdraw.instruments.Pencil;

/**
 * This command modifies a parameter of the pencil and updates its corresponding instrument.
 * @author Arnaud Blouin
 */
public class ModifyPencilParameter<T> extends ShapePropertyCmd<T> {
	/** The pencil to modify. */
	private final Pencil pencil;

	public ModifyPencilParameter(final ShapeProperties<T> property, final Pencil pencil, final T value) {
		super(property, value);
		this.pencil = pencil;
	}

	@Override
	public boolean canDo() {
		return pencil != null && super.canDo();
	}

	@Override
	protected boolean isPropertySupported() {
		return property != ShapeProperties.TEXT && super.isPropertySupported();
	}

	@Override
	protected void doCmdBody() {
		// Modification of the pencil.
		applyValue(value);
	}

	@Override
	protected void applyValue(final T obj) {
		property.setPropertyValue(pencil.getGroupParams(), value);
	}
}
