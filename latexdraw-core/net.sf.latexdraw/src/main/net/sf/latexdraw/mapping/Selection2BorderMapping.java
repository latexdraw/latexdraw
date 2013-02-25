package net.sf.latexdraw.mapping;

import java.util.List;
import java.util.Objects;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.instruments.Border;

import org.malai.mapping.MappingRegistry;

/**
 * This mapping maps the selected shapes of the drawing to the Border instrument that manages
 * the corresponding selected views.<br>
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
 * 10/27/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Selection2BorderMapping extends SelectionMapping {
	/** The border to update in function of the selection. */
	protected Border border;


	/**
	 * Creates the mapping.
	 * @param selection The list of selected shapes.
	 * @param border The Border instrument.
	 * @throws IllegalArgumentException If one of the given parameter is null.
	 * @since 3.0
	 */
	public Selection2BorderMapping(final List<IShape> selection, final Border border) {
		super(selection);
		this.border = Objects.requireNonNull(border);
	}


	@Override
	public Border getTarget() {
		return border;
	}


	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		final IViewShape view = MappingRegistry.REGISTRY.getTargetFromSource(object, IViewShape.class);

		if(view!=null) {
			updateActivation(list);
			border.add(view);
		}
	}

	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		final IViewShape view = MappingRegistry.REGISTRY.getTargetFromSource(object, IViewShape.class);

		if(view!=null) {
			border.remove(view);
			updateActivation(list);
		}
	}


	@Override
	public void onListCleaned(final Object list) {
		border.clear();
	}


	@Override
	public void clear() {
		super.clear();
		border = null;
	}


	/**
	 * Updates the activation of the border.
	 * @since 3.0
	 */
	protected void updateActivation(final Object list) {
		if(list instanceof List)
			border.setActivated(!((List<?>)list).isEmpty());
	}
}
