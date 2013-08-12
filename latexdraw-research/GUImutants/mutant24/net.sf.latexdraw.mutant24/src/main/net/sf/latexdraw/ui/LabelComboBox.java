package net.sf.latexdraw.ui;

import javax.swing.JLabel;

import org.malai.swing.widget.MComboBox;

/**
 * A combo box that contains JLabel instances as items that can be
 * selected using a string corresponding to the text of the label.<br>
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
 * 2013-04-06<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LabelComboBox extends MComboBox<JLabel> {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates the combo box.
	 * @since 3.0
	 */
	public LabelComboBox() {
		super();
	}

	/**
	 * Converts, or not, the given parameter as its corresponding JLabel if it is a string.
	 * @param obj The object to analyse.
	 * @return The object obj or the JLabel corresponding to obj if it is a String.
	 * @since 3.0
	 */
	protected Object getJLabelFromString(Object obj) {
		Object objToSelect = obj;

		if(obj instanceof String) {
			String txt = (String)obj;
			JLabel lab = null;
			for(int i=0, size=dataModel.getSize(); i<size && lab==null; i++)
				if(txt.equals(dataModel.getElementAt(i).getText()))
					lab = dataModel.getElementAt(i);
			if(lab!=null)
				objToSelect = lab;
		}

		return objToSelect;
	}

	@Override
	public void setSelectedItem(final Object anObject) {
		super.setSelectedItem(getJLabelFromString(anObject));
	}


	@Override
	public void setSelectedItemSafely(final Object anObject) {
		super.setSelectedItemSafely(getJLabelFromString(anObject));
	}
}
