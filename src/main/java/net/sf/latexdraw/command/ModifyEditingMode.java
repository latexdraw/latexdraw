/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command;

import io.github.interacto.command.CommandImpl;
import net.sf.latexdraw.instrument.EditionChoice;
import net.sf.latexdraw.service.EditingService;
import org.jetbrains.annotations.NotNull;

/**
 * This command allows to set the kind of shape that the pencil must draw.
 * @author Arnaud Blouin
 */
public class ModifyEditingMode extends CommandImpl {
	/** The pencil to set. */
	private final @NotNull EditingService editing;

	/** The new editing choice to set. */
	private final @NotNull EditionChoice editingChoice;

	public ModifyEditingMode(final @NotNull EditingService editing, final @NotNull EditionChoice choice) {
		super();
		this.editing = editing;
		editingChoice = choice;
	}

	@Override
	protected void doCmdBody() {
		editing.setCurrentChoice(editingChoice);
	}
}
