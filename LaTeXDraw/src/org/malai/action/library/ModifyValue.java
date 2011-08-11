package org.malai.action.library;

import org.malai.action.Action;

/**
 * The goal of this abstract action is to modify an object. This object can be for instance
 * a property of an object.<br>
 * The use of this action can be made when an object has a lot of properties which modification
 * follow the same process. Thus, a same action can be used to modify all the properties. To do
 * so, a enumeration of the properties can be defined and used into the action to specify which
 * property will be modified by the current action instance.
 * <br>
 * This file is part of Malai<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 *  Malai is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 * <br>
 *  Malai is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * @author Arnaud Blouin
 * @since 0.2
 */
public abstract class ModifyValue extends Action {
	/** The new value of the property. */
	protected Object value;

	/**
	 * Initialises the action.
	 * @since 0.2
	 */
	public ModifyValue() {
		super();
	}


	@Override
	public void flush() {
		super.flush();
		value = null;
	}


	@Override
	public boolean canDo() {
		return value!=null && isValueMatchesProperty();
	}


	/**
	 * Sets the new value of the parameter to change.
	 * @param value The new value.
	 * @since 0.2
	 */
	public void setValue(final Object value) {
		this.value = value;
	}


	/**
	 * This method executes the job of methods undo, redo, and do
	 * @param obj The value to set. Must not be null.
	 * @throws NullPointerException If the given value is null.
	 * @since 0.2
	 */
	protected abstract void applyValue(final Object obj);


	/**
	 * @return True: the object to modified supports the selected property.
	 * @since 0.2
	 */
	protected abstract boolean isValueMatchesProperty();
}
