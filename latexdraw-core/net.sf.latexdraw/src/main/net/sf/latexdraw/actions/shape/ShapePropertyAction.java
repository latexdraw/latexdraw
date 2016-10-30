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
package net.sf.latexdraw.actions.shape;

import org.malai.action.library.ModifyValue;

/**
 * This action modifies a shape property of an object.
 */
public abstract class ShapePropertyAction extends ModifyValue {
	/** The property to set. */
	protected ShapeProperties property;


	/**
	 * Creates and initialises the action.
	 * @since 3.0
	 */
	protected ShapePropertyAction() {
		super();
	}


	@Override
	public void flush() {
		super.flush();
		property = null;
	}


	@Override
	protected boolean isValueMatchesProperty() {
		return isPropertySupported() && property.isValueValid(value);
	}


	/**
	 * @return True if the property to modify is supported.
	 * @since 3.0
	 */
	protected boolean isPropertySupported() {
		return property != null;
	}


	/**
	 * Defines the property to modify
	 * @param prop The property to modify.
	 * @since 3.0
	 */
	public void setProperty(final ShapeProperties prop) {
		property = prop;
	}
}
