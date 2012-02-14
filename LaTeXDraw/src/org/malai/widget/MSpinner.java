package org.malai.widget;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import org.malai.interaction.SwingEventManager;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;

/**
 * This widgets is based on a JSpinner. It allows to be used in the Malai framework for picking.<br>
 * An improvement of MSpinner against JSpinner is that MSpinner can have a label (JLabel).<br>
 * Spinner models provided by Malai ({@link MSpinner.MSpinnerNumberModel} should be used to 
 * avoid the unexpected creation of actions.
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
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
	 * @param model The data model of the widget.
	 */
	public MSpinner(final SpinnerModel model, final JLabel label) {
		super(model);

		this.label = label;

		if(label!=null)
			label.setLabelFor(this);
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



	/**
	 * The number model to use with a MSpinner. Compared to SpinnerNumberModel this model provides
	 * 'safely' operations to change minimum and maximum values without firing any event (that may
	 * produce actions).
	 * @since 0.2
	 */
	public static class MSpinnerNumberModel extends SpinnerNumberModel {
		private static final long serialVersionUID = 1L;

		/**
		 * {@link SpinnerNumberModel#SpinnerNumberModel(Number, Comparable, Comparable, Number)}
	     * @param value the current (non <code>null</code>) value of the model
	     * @param minimum the first number in the sequence or <code>null</code>
	     * @param maximum the last number in the sequence or <code>null</code>
	     * @param stepSize the difference between elements of the sequence
		 */
		public MSpinnerNumberModel(final Number value, final Comparable<?> minimum, final Comparable<?> maximum, final Number stepSize) {
			super(value, minimum, maximum, stepSize);
		}


		/**
		 * Changes the lower bound for numbers in this sequence without firing any event (that may
		 * produce actions).
		 * {@link SpinnerNumberModel#setMinimum(Comparable)}
		 * @param min The new minimum value. Can be null.
		 * @since 0.2
		 */
		public void setMinumunSafely(final Comparable<?> min) {
			EventListenerList ell = listenerList;
			listenerList = new EventListenerList();
			setMinimum(min);
			listenerList = ell;
		}


		/**
		 * Changes the upper bound for numbers in this sequence without firing any event (that may produce actions).
		 * {@link SpinnerNumberModel#setMaximum(Comparable)}
		 * @param max The new maximum value. Can be null.
		 * @since 0.2
		 */
		public void setMaximumSafely(final Comparable<?> max) {
			EventListenerList ell = listenerList;
			listenerList = new EventListenerList();
			setMaximum(max);
			listenerList = ell;
		}
	}
}
