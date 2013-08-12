package net.sf.latexdraw.actions;

import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.ui.ScaleRuler.Unit;

import org.malai.action.Action;
import org.malai.undo.Undoable;

/**
 * This action allows to modify the unit.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 11/13/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class SetUnit extends Action implements Undoable {
	/** The new unit to set. */
	protected Unit unit;

	/** The former unit. */
	protected Unit oldUnit;


	/**
	 * Initialises the action.
	 * @since 3.0
	 */
	public SetUnit() {
		super();
	}


	@Override
	public void flush() {
		super.flush();
		unit 	= null;
		oldUnit = null;
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
	public String getUndoName() {
		return "Change unit";
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	protected void doActionBody() {
		oldUnit = ScaleRuler.getUnit();
		redo();
	}


	@Override
	public boolean canDo() {
		return unit!=null;
	}


	/**
	 * @param unit The new unit to set.
	 * @since 3.0
	 */
	public void setUnit(final Unit unit) {
		this.unit = unit;
	}
}
