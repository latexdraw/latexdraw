/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2014 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.actions;

import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.util.Unit;
import org.malai.action.ActionImpl;
import org.malai.undo.Undoable;

/**
 * This action allows to modify the unit.
 */
public class SetUnit extends ActionImpl implements Undoable {
	/** The new unit to set. */
	protected Unit unit;

	/** The former unit. */
	protected Unit oldUnit;


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
		return LangTool.INSTANCE.getBundle().getString("Actions.3"); //$NON-NLS-1$
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
	 * @param val The new unit to set.
	 * @since 3.0
	 */
	public void setUnit(final Unit val) {
		unit = val;
	}
}
