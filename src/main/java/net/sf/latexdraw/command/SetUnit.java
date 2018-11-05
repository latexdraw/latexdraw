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

import java.util.ResourceBundle;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.util.Unit;
import org.malai.command.CommandImpl;
import org.malai.undo.Undoable;

/**
 * This command changes the unit system used in the user interface.
 * @author Arnaud Blouin
 */
public class SetUnit extends CommandImpl implements Undoable {
	/** The new unit to set. */
	private final Unit unit;
	/** The former unit. */
	private Unit oldUnit;

	public SetUnit(final Unit unit) {
		super();
		this.unit = unit;
	}

	@Override
	protected void doCmdBody() {
		oldUnit = ScaleRuler.getUnit();
		redo();
	}

	@Override
	public boolean canDo() {
		return unit != null;
	}

	@Override
	public void undo() {
		ScaleRuler.setUnit(oldUnit);
	}

	@Override
	public void redo() {
		ScaleRuler.setUnit(unit);
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("Actions.3");
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}
}
