package org.malai.widget;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeListener;

import org.malai.interaction.SwingEventManager;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;


/**
 * This widgets is based on a JSpinner. It allows to be used in the Malai framework for picking.<br>
 * An improvement of MSpinner against JSpinner is that MSpinner can have a label (JLabel).
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
public class MSpinner extends JSpinner implements Pickable {
	private static final long serialVersionUID = 1L;

	/** The label used to describe the use of the spinner. */
	protected JLabel label;
	
	
	/**
	 * {@link JSpinner#JSpinner()}
	 */
	public MSpinner() {
		super();
	}


	/**
	 * {@link JSpinner#JSpinner(SpinnerModel)}
	 * @param label The label used to describe the use of the spinner. Can be null. When
	 * the spinner is added to a container, the label is not automatically added; it must be manually
	 * added to the container.
	 */
	public MSpinner(final SpinnerModel model, final JLabel label) {
		super(model);
		
		this.label = label;
	}

	
	
	/**
	 * Idem than method setValue(value) but here the SwingEventManager listener is removed
	 * from the spinner before the setting to avoid any unexpected behaviour.
	 * The listener is then re-added.<br>
	 * The widget must be linked to a single SwingEventManager listener at max.
	 * @param value The value to set.
	 * @since 3.0
	 */
	public void setValueSafely(final Object value) {
		final ChangeListener[] list = getChangeListeners();
		ChangeListener cl = null;
		
		for(int i=0; i<list.length && cl==null; i++)
			// Removing the listener. 
			if(list[i] instanceof SwingEventManager) {
				removeChangeListener(list[i]);
				cl = list[i];
			}
		
		// changing the value.
		super.setValue(value);
		
		// Re-adding the listener if needed.
		if(cl!=null)
			addChangeListener(cl);
	}
	


	@Override
	public boolean contains(final double x, final double y) {
		return WidgetUtilities.INSTANCE.contains(this, x, y);
	}


	@Override
	public Picker getPicker() {
		return WidgetUtilities.INSTANCE.getPicker(this);
	}
	
	
	/**
	 * @return The label used to describe the use of the spinner. Can be null.
	 * @since 0.2
	 */
	public JLabel getLabel() {
		return label;
	}
	
	
	@Override
	public void setVisible(final boolean visible) {
		super.setVisible(visible);
		
		if(label!=null)
			label.setVisible(visible);
	}
}
