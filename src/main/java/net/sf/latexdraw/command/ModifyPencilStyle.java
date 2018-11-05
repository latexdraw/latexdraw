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
package net.sf.latexdraw.command;

import net.sf.latexdraw.instrument.EditionChoice;
import net.sf.latexdraw.instrument.Pencil;
import org.malai.command.CommandImpl;

/**
 * This command allows to set the kind of shape that the pencil must draw.
 * @author Arnaud Blouin
 */
public class ModifyPencilStyle extends CommandImpl {
	/** The pencil to set. */
	private final Pencil pencil;

	/** The new editing choice to set. */
	private EditionChoice editingChoice;

	public ModifyPencilStyle(final Pencil pencil) {
		super();
		this.pencil = pencil;
	}

	@Override
	protected void doCmdBody() {
		pencil.setCurrentChoice(editingChoice);
	}

	@Override
	public boolean canDo() {
		return pencil != null && editingChoice != null && pencil.getCurrentChoice() != editingChoice;
	}

	/**
	 * Sets the new editing choice of the pencil.
	 * @param choice The new editing choice (can be null).
	 */
	public void setEditingChoice(final EditionChoice choice) {
		editingChoice = choice;
	}
}
