package net.sf.latexdraw.mapping;

import java.util.List;
import java.util.Objects;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;

/**
 * This mapping maps the selected shapes of the drawing to the instrument that
 * manages the instruments customising the shapes and the pencil.<br>
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
public class Selection2MetaCustumiserMapping extends SelectionMapping {
	/** The instrument that manages the instruments customising the shapes and the pencil. */
	protected MetaShapeCustomiser shapeCustomiser;


	/**
	 * Creates the mapping.
	 * @param selection The list of selected shapes.
	 * @param shapeCustomiser The instrument that manages the instruments customising the shapes and the pencil.
	 * @throws NullPointerException If on of the given parameter is null.
	 * @since 3.0
	 */
	public Selection2MetaCustumiserMapping(final List<IShape> selection, final MetaShapeCustomiser shapeCustomiser) {
		super(selection);
		this.shapeCustomiser = Objects.requireNonNull(shapeCustomiser);
	}


	@Override
	public MetaShapeCustomiser getTarget() {
		return shapeCustomiser;
	}


	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		shapeCustomiser.setActivated(true);
	}


	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		if(shapeCustomiser.getHand().isActivated())
			shapeCustomiser.setActivated(!selection.isEmpty());
	}


	@Override
	public void onListCleaned(final Object list) {
		if(shapeCustomiser.getHand().isActivated())
			shapeCustomiser.setActivated(false);
	}


	@Override
	public void clear() {
		super.clear();
		shapeCustomiser = null;
	}
}
