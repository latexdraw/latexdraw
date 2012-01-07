package org.malai.widget;

import java.awt.Component;
import java.awt.Container;
import java.awt.geom.Point2D;

import org.malai.interaction.SwingEventManager;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;

/**
 * This singleton provides common methods for widgets.<br>
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
 * 11/02/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class WidgetUtilities {
	/** The singleton of the class. */
	public static final WidgetUtilities INSTANCE = new WidgetUtilities();

	/**
	 * Initialise the singleton.
	 * @since 0.2
	 */
	protected WidgetUtilities() {
		super();
	}


	/**
	 * Tests if the given object is contained into the given components.
	 * @param subComponents The components to test.
	 * @param obj The object to test.
	 * @return True: the given is contained into the given components. Otherwise, false.
	 * @since 0.2
	 */
	public boolean contains(final Component[] subComponents, final Object obj) {
		if(subComponents==null || obj==null)
			return false;

		int i			 = 0;
		boolean contains = false;

		while(i<subComponents.length && !contains) {
			if(subComponents[i]==obj)
				contains = true;
			else if(subComponents[i] instanceof Picker)
				contains = ((Picker)subComponents[i]).contains(obj);
			i++;
		}

		return contains;
	}


	/**
	 * Tests if the given point is into the pickable object.
	 * @param component The concerned widget.
	 * @param x The x-coordinate of the point to test.
	 * @param y The y-coordinate of the point to test.
	 * @return True if the given point is into the pickable object.
	 * @since 0.1
	 */
	public boolean contains(final Component component, final double x, final double y) {
		return component!=null && component.isEnabled() && component.contains((int)x, (int)y);
	}


	/**
	 * @param component The concerned widget.
	 * @return The picker object that contains the pickable object.
	 * @since 0.2
	 */
	public Picker getPicker(final Component component) {
		Container container = component.getParent();
		Picker picker 		= null;

		while(picker==null && container!=null)
			if(container instanceof Picker)
				picker = (Picker) container;
			else
				container = container.getParent();

		return picker;
	}


	/**
	 * @param component The component to test.
	 * @param subComponents The sub-components of the given component.
	 * @param x The x-coordinate to test.
	 * @param y The Y-coordinate to test.
	 * @return True if the given component and one of its sub-components contains the given point.
	 * @since 0.2
	 */
	public Picker getPickerAt(final Component component, final Component[] subComponents, final double x, final double y) {
		if(subComponents==null || component==null || !component.contains((int)x, (int)y))
			return null;

		Picker o = null;
		int i	 = 0;

		while(i<subComponents.length && o==null)
			if(subComponents[i] instanceof Picker && subComponents[i].getBounds().contains(x, y))
				o = (Picker)subComponents[i];
			else
				i++;

		return o;
	}



	/**
	 * @param component The concerned widget.
	 * @param subComponents All the components that contains the given component.
	 * @param x The x-coordinate of the position used to get the pickable object.
	 * @param y The y-coordinate of the position used to get the pickable object.
	 * @return The pickable object at the given position.
	 * @since 0.2
	 */
	public Pickable getPickableAt(final Component component, final Component[] subComponents, final double x, final double y) {
		if(component==null || !component.contains((int)x, (int)y))
			return null;

		Pickable o 	= null;
		int i		= 0;

		while(i<subComponents.length && o==null) {
			if(subComponents[i] instanceof Pickable && subComponents[i].getBounds().contains(x, y))
				o = (Pickable)subComponents[i];
			else if(subComponents[i] instanceof Picker)
				o = ((Picker)subComponents[i]).getPickableAt(x-subComponents[i].getX(), y-subComponents[i].getY());
			i++;
		}

		return o;
	}



	/**
	 * @param comps The components contained by a widget.
	 * @param x The x-coordinate of the position to convert.
	 * @param y The y-coordinate of the position to convert.
	 * @param o An object contained by the calling picker. This function will computed the real position of the given
	 * point in <code>o</code>.
	 * @return Converts the given point in to fit the coordinates of the given object contained by the picker.
	 * For instance, given an object <code>o1</code> that contains an other object <code>o2</code> at position <code>(10, 10)</code>. <code>o1.getRelativePoint(30, 30, o2)</code>
	 * will return <code>(20, 20)</code>.
	 * @since 0.2
	 */
	public Point2D getRelativePoint(final Component[] comps, final double x, final double y, final Object o) {
		if(o==null || comps==null)
			return null;

		Point2D ptRel 			= new Point2D.Double(Double.NaN, Double.NaN);
		int i					= 0;
		boolean ok 				= false;

		while(i<comps.length && !ok)
			if(comps[i]==o)
				ok = true;
			else
				i++;

		if(ok)
			ptRel.setLocation(x, y);
		else {
			i = 0;
			while(Double.isNaN(ptRel.getX()) && i<comps.length) {
				if(comps[i] instanceof Picker)
					ptRel = ((Picker)comps[i]).getRelativePoint(x-comps[i].getX(), y-comps[i].getY(), o);

				i++;
			}
		}

		return ptRel;
	}


	/**
	 * Attaches the given component to the event manager of the panel if it exists.
	 * @param comp The component to attach.
	 * @param eventManager The event manager where the given component will be attached.
	 * @since 0.2
	 */
	public void attachAddedComponent(final SwingEventManager eventManager, final Component comp) {
		if(comp!=null && eventManager!=null)
			eventManager.attachTo(comp);
	}
}
