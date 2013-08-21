package net.sf.latexdraw.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * This class allows the change the renderer of JComboBox. it allows to display a icon in the list<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 01/20/06<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LabelListCellRenderer extends JLabel implements ListCellRenderer<JLabel> {
	private static final long serialVersionUID = 1L;

	/** The colour displayed when a item is selected in the list */
	public static final Color SELECT_COLOUR = new Color(120, 160, 195);


	/**
	 * The constructor by default
	 */
	public LabelListCellRenderer() {
		super();
        setOpaque(true);
    }


	@Override
	public Component getListCellRendererComponent(final JList<? extends JLabel> list, final JLabel value, final int index, final boolean isSelected, final boolean cellHasFocus) {
		setBackground(isSelected ? SELECT_COLOUR : Color.WHITE);
		setIcon(value.getIcon());
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		return this;
	}
}
