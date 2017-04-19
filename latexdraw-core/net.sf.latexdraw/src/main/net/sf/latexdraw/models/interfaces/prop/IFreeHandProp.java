/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.interfaces.prop;

import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;

/**
 * Properties of freehand shapes.
 * @author Arnaud Blouin
 */
public interface IFreeHandProp {
	/**
	 * @return the type.
	 * @since 3.0
	 */
	FreeHandStyle getType();

	/**
	 * @param type the type to set.
	 * @since 3.0
	 */
	void setType(final FreeHandStyle type);

	/**
	 * @return the open.
	 * @since 3.0
	 */
	boolean isOpen();

	/**
	 * @param open the open to set.
	 * @since 3.0
	 */
	void setOpen(final boolean open);

	/**
	 * @return the interval.
	 * @since 3.0
	 */
	int getInterval();

	/**
	 * @param interval the interval to set.
	 * @since 3.0
	 */
	void setInterval(final int interval);
}
