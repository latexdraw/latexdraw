package net.sf.latexdraw.mapping;

import java.util.List;
import java.util.Objects;

import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.malai.mapping.IMapping;
import org.malai.mapping.IUnary;

/**
 * This abstract mapping maps the selected shapes of the drawing to something.<br>
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
 * 10/31/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public abstract class SelectionMapping implements IMapping {
	/** The selected shapes. */
	protected List<IShape> selection;


	/**
	 * Creates the mapping.
	 * @param selection The list of selected shapes.
	 * @throws IllegalArgumentException If the given parameter is null.
	 * @since 3.0
	 */
	public SelectionMapping(final List<IShape> selection) {
		super();
		this.selection = Objects.requireNonNull(selection);
	}


	@Override
	public List<IShape> getSource() {
		return selection;
	}


	@Override
	public void clear() {
		selection = null;
	}


	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		// Nothing to do.
	}

	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		// Nothing to do.
	}

	@Override
	public void onObjectMoved(final Object list, final Object object, final int srcIndex, final int targetIndex) {
		// Nothing to do.
	}

	@Override
	public void onObjectReplaced(final IUnary<?> object, final Object replacedObject) {
		// Nothing to do.
	}

	@Override
	public void onObjectModified(final Object object) {
		// Nothing to do.
	}

	@Override
	public void onListCleaned(final Object list) {
		// Nothing to do.
	}


	@Override
	public void init() {
		onListCleaned(selection);
	}
}
