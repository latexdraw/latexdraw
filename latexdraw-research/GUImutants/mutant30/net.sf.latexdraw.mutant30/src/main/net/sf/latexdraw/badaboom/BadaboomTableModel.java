package net.sf.latexdraw.badaboom;

import java.util.Objects;

import javax.swing.table.AbstractTableModel;

/**
 * Defines a table model for the 'Bordel !' manager.<br>
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
 * 02/18/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public class BadaboomTableModel extends AbstractTableModel {
	private static final long serialVersionUID 	= 1L;

	protected static final String NAME_COL1		= "Name";

	protected static final String NAME_COL2		= "Message";

	protected static final String NAME_COL3		= "Location";

	/** The table data. */
	protected BadaboomCollector collector;



	/**
	 * Creates and initialises a table model for the badaboom manager.
	 * @param collector The exceptions collector.
	 * @throws NullPointerException If the given collector is null.
	 */
	public BadaboomTableModel(final BadaboomCollector collector) {
		super();
		this.collector = Objects.requireNonNull(collector);
	}


	@Override
	public String getColumnName(final int column) {
		switch(column) {
			case 0:  return NAME_COL1;
			case 1:  return NAME_COL2;
			case 2:  return NAME_COL3;
			default: return "";//$NON-NLS-1$
		}
	}


	@Override
	public int getColumnCount() {
		return 3;
	}


	@Override
	public int getRowCount() {
		return collector.size();
	}


	@Override
	public String getValueAt(final int rowIndex, final int columnIndex) {
		if(rowIndex<0 || rowIndex>=collector.size())
			return null;

		switch(columnIndex) {
			case 0: return collector.get(rowIndex).toString();
			case 1: return collector.get(rowIndex).getMessage();
			case 2:
				final StackTraceElement[] stack = collector.get(rowIndex).getStackTrace();
				return stack!=null && stack.length>0 ? stack[0].toString() : null;

			default: return null;
		}
	}
}
