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
package net.sf.latexdraw.actions;

import net.sf.latexdraw.instruments.EditionChoice;
import net.sf.latexdraw.instruments.Pencil;
import org.malai.action.ActionImpl;

/**
 * This action allows to set the kind of shape that the pencil must draw.
 * @author Arnaud Blouin
 */
public class ModifyPencilStyle extends ActionImpl {
	/** The pencil to set. */
	protected Pencil pencil;

	/** The new editing choice to set. */
	protected EditionChoice editingChoice;

	public ModifyPencilStyle() {
		super();
	}

	@Override
	protected void doActionBody() {
		pencil.setCurrentChoice(editingChoice);
	}

	@Override
	public boolean canDo() {
		return pencil != null && editingChoice != null && pencil.getCurrentChoice() != editingChoice;
	}


	/**
	 * Sets the pencil to parameterise.
	 * @param pen The pencil.
	 * @since 3.0
	 */
	public void setPencil(final Pencil pen) {
		pencil = pen;
	}

	/**
	 * Sets the new editing choice of the pencil.
	 * @param choice The new editing choice (can be null).
	 * @since 3.0
	 */
	public void setEditingChoice(final EditionChoice choice) {
		editingChoice = choice;
	}
}
