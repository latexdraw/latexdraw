package net.sf.latexdraw.mapping;

import java.util.List;

import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.ToolTipable;

import org.malai.mapping.IUnary;
import org.malai.mapping.List2ListMapping;

/**
 * Defines a mapping that link the views of a canvas to a set of tooltipable views.
 * This last list is used to avoid a full exploration of the set of views (that can be huge).
 * So a synchronisation between these two lists must be performed.
 * <br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 19/01/12<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 * @version 3.0
 */
public class ViewList2TooltipableList extends List2ListMapping<IViewShape, ToolTipable> {
	/**
	 * Creates the mapping.
	 * @param source The list of views.
	 * @param target The subset of tooltipable views.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 3.0
	 */
	public ViewList2TooltipableList(final List<IViewShape> source, final List<ToolTipable> target) {
		super(source, target);
	}

	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		// TODO Auto-generated method stub
		if(object instanceof ToolTipable)
			synchronized(target){ target.add((ToolTipable)object); }
	}

	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		if(object instanceof ToolTipable)
			synchronized(target){ target.remove(object); }
	}

	@Override
	public void onListCleaned(final Object list) {
		synchronized(target){ target.clear(); }
	}


	@Override
	public void onObjectReplaced(final IUnary<?> object, final Object replacedObject) {
		// Nothing to do.
	}

	@Override
	public void onObjectMoved(final Object list, final Object object, final int srcIndex, final int targetIndex) {
		// Nothing to do.
	}

	@Override
	public void onObjectModified(final Object object) {
		// Nothing to do.
	}

	@Override
	public void init() {
		// Nothing to do.
	}
}
