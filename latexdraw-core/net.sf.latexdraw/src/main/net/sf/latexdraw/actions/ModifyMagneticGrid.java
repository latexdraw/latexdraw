package net.sf.latexdraw.actions;

import net.sf.latexdraw.glib.ui.LMagneticGrid;
import net.sf.latexdraw.glib.ui.LMagneticGrid.GridStyle;

import org.malai.action.library.ModifyValue;
import org.malai.undo.Undoable;

/**
 * This action modifies a property of the magnetic grid.<br>
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
 * 11/14/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ModifyMagneticGrid extends ModifyValue implements Undoable {
	/**
	 * The different properties of the magnetic grid that can be modified.
	 */
	public static enum GridProperties {
		/** Modification of the style of the grid. */
		STYLE {
			@Override
			public boolean isValidValue(final Object object) {
				return object instanceof GridStyle;
			}
		},
		/** Defines if the grid must be magnetic. */
		MAGNETIC {
			@Override
			public boolean isValidValue(final Object object) {
				return object instanceof Boolean;
			}
		},
		/** Modification of the spacing between the lines of the grid. */
		GRID_SPACING {
			@Override
			public boolean isValidValue(final Object object) {
				return object instanceof Integer;
			}
		};


		/**
		 * @param object The value to test.
		 * @return True: if the type of the given value matches the type of the property.
		 * @since 3.0
		 */
		public abstract boolean isValidValue(final Object object);
	}


	/** The magnetic grid to modify. */
	protected LMagneticGrid grid;

	/** The property to modify. */
	protected GridProperties property;

	/** A back-up of the former value of the modified property. */
	protected Object oldValue;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public ModifyMagneticGrid() {
		super();
	}


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
		return "Modification of the magnetic grid";
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
	 * @param grid The grid to modify.
	 * @since 3.0
	 */
	public void setGrid(final LMagneticGrid grid) {
		this.grid = grid;
	}


	/**
	 * @param property The property to modify.
	 * @since 3.0
	 */
	public void setProperty(final GridProperties property) {
		this.property = property;
	}
}
