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
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.view.MagneticGrid;
import org.malai.action.library.ModifyValue;
import org.malai.undo.Undoable;

/**
 * This action modifies a property of the magnetic grid.
 */
public class ModifyMagneticGrid extends ModifyValue implements Undoable {
	/** The magnetic grid to modify. */
	protected MagneticGrid grid;

	/** The property to modify. */
	protected GridProperties property;

	/** A back-up of the former value of the modified property. */
	protected Object oldValue;


    @Override
	public void flush() {
		super.flush();
		grid 		= null;
		property 	= null;
		oldValue 	= null;
	}


	@Override
	public void undo() {
		applyValue(oldValue);
	}


	@Override
	public void redo() {
		applyValue(value);
	}


	@Override
	protected void applyValue(final Object object) {
		switch(property) {
			case GRID_SPACING:
				grid.setGridSpacing((Integer)object);
				break;
			case MAGNETIC:
				grid.setMagnetic((Boolean)object);
				break;
			case STYLE:
				grid.setStyle((GridStyle)object);
				break;
		}
	}


	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("Actions.1"); //$NON-NLS-1$
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	protected void doActionBody() {
		// Backing-up the value of the property that will be modified.
		switch(property) {
			case GRID_SPACING:
				oldValue = grid.getGridSpacing();
				break;
			case MAGNETIC:
				oldValue = grid.isMagnetic();
				break;
			case STYLE:
				oldValue = grid.getStyle();
				break;
		}
		// Modifying the property.
		redo();
	}


	@Override
	public boolean canDo() {
		return super.canDo() && grid!=null;
	}



	@Override
	protected boolean isValueMatchesProperty() {
		return property!=null && property.isValidValue(value);
	}


	/**
	 * @param gr The group to modify.
	 * @since 3.0
	 */
	public void setGrid(final MagneticGrid gr) {
		grid = gr;
	}


	/**
	 * @param prop The property to modify.
	 * @since 3.0
	 */
	public void setProperty(final GridProperties prop) {
		property = prop;
	}
}
