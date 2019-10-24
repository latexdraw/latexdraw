/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command;

import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.command.shape.ShapePropertyCmd;
import net.sf.latexdraw.service.EditingService;
import org.jetbrains.annotations.NotNull;

/**
 * This command modifies a parameter of the editing parameters.
 * @author Arnaud Blouin
 */
public class ModifyEditingParameter<T> extends ShapePropertyCmd<T> {
	private final @NotNull EditingService editing;

	public ModifyEditingParameter(final @NotNull ShapeProperties<T> property, final @NotNull EditingService editing, final T value) {
		super(property, value);
		this.editing = editing;
	}

	@Override
	protected boolean isPropertySupported() {
		return property != ShapeProperties.TEXT && super.isPropertySupported();
	}

	@Override
	protected void doCmdBody() {
		// Modification of the editing.
		applyValue(value);
	}

	@Override
	protected void applyValue(final T obj) {
		property.setPropertyValue(editing.getGroupParams(), value);
	}
}
