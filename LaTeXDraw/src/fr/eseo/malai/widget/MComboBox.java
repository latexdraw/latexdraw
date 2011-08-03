package fr.eseo.malai.widget;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import fr.eseo.malai.interaction.SwingEventManager;
import fr.eseo.malai.picking.Pickable;
import fr.eseo.malai.picking.Picker;

/**
 * This widgets is based on a JComboBox. It allows to be used in the Malai framework for picking.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 06/05/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MComboBox extends JComboBox implements Pickable {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link JComboBox#JComboBox()}
	 */
	public MComboBox() {
		super();
	}

	/**
	 * {@link JComboBox#JComboBox(ComboBoxModel)}
	 */
	public MComboBox(final ComboBoxModel aModel) {
		super(aModel);
	}

	/**
	 * {@link JComboBox#JComboBox(Object[])}
	 */
	public MComboBox(final Object[] items) {
		super(items);
	}

	/**
	 * {@link JComboBox#JComboBox(Vector)}
	 */
	public MComboBox(final Vector<?> items) {
		super(items);
	}


	@Override
	public void setSelectedItem(final Object anObject) {
		int i;
		final int size = getItemCount();

		if(anObject==null || size==0)
			return ;
		// Getting the real item corresponding to the given object.
		Object o = null;
		// anObject can be either a JLabel or a String.
		if(anObject instanceof JLabel)
			o = anObject;
		else {
			boolean found = false;

			if(!(anObject instanceof String))
				throw new IllegalArgumentException(anObject.toString());
			// Looking for the item that matches the given string.
			for(i=0; i<size && !found; i++) {
				o = getItemAt(i);

				if(o instanceof JLabel && ((JLabel)o).getText().equals(anObject))
					found=true;
				else
					if(o instanceof String && ((String)o).equals(anObject))
						found=true;
			}
		}

	    dataModel.setSelectedItem(o);
	}



	/**
	 * Idem than method setSelectedItem(value) but here the SwingEventManager listener is removed
	 * from the combobox before the setting to avoid any unexpected behaviour.
	 * The listener is then re-added.<br>
	 * The widget must be linked to a single SwingEventManager listener at max.
	 * @param anObject The item to select.
	 * @since 3.0
	 */
	public void setSelectedItemSafely(final Object anObject) {
		final ItemListener[] list = getItemListeners();
		ItemListener il = null;

		for(int i=0; i<list.length && il==null; i++)
			// Removing the listener.
			if(list[i] instanceof SwingEventManager) {
				removeItemListener(list[i]);
				il = list[i];
			}

		setSelectedItem(anObject);

		// Re-adding the listener if needed.
		if(il!=null)
			addItemListener(il);
	}


    @Override
	protected void selectedItemChanged() {
    	selectedItemReminder = dataModel.getSelectedItem();

    	if(selectedItemReminder != null)
    		fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, selectedItemReminder, ItemEvent.SELECTED));
    }


	@Override
	public boolean contains(final double x, final double y) {
		return WidgetUtilities.INSTANCE.contains(this, x, y);
	}


	@Override
	public Picker getPicker() {
		return WidgetUtilities.INSTANCE.getPicker(this);
	}
}
