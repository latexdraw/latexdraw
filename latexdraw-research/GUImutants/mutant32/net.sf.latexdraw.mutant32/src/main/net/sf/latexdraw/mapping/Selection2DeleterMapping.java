package net.sf.latexdraw.mapping;

import java.util.List;
import java.util.Objects;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.instruments.ShapeDeleter;

/**
 * This mapping maps the selected shapes of the drawing to the instrument that can delete them.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 01/07/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Selection2DeleterMapping extends SelectionMapping {
	/** The instrument that removes shapes. */
	protected ShapeDeleter deleter;


	/**
	 * Creates the mapping.
	 * @param selection The list of selected shapes.
	 * @param deleter The instrument that removes shapes.
	 * @throws IllegalArgumentException If one of the given parameter is null.
	 * @since 3.0
	 */
	public Selection2DeleterMapping(final List<IShape> selection, final ShapeDeleter deleter) {
		super(selection);
		this.deleter = Objects.requireNonNull(deleter);
	}

	@Override
	public ShapeDeleter getTarget() {
		return deleter;
	}


	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		deleter.setActivated(true);
	}

	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		if(list instanceof List<?>)
			deleter.setActivated(!((List<?>)list).isEmpty());
	}


	@Override
	public void onListCleaned(final Object list) {
		if(deleter.isActivated())
			deleter.setActivated(false);
	}


	@Override
	public void clear() {
		super.clear();
		deleter = null;
	}
}
