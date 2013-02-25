package net.sf.latexdraw.mapping;

import net.sf.latexdraw.ui.ScaleRuler;

import org.malai.mapping.IUnary;
import org.malai.mapping.Object2ObjectMapping;

/**
 * Creates a mapping between the zoom value and a scale ruler.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 11/12/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class Zoom2ScaleRuler extends Object2ObjectMapping<IUnary<Double>, ScaleRuler> {
	/**
	 * Creates the mapping.
	 * @param source The zoom value.
	 * @param target The scale ruler to update.
	 * @throws IllegalArgumentException If one of the given arguments is null or if they are the same object.
	 * @since 3.0
	 */
	public Zoom2ScaleRuler(final IUnary<Double> source, final ScaleRuler target) {
		super(source, target);
	}

	@Override
	public void onObjectReplaced(final IUnary<?> object, final Object replacedObject) {
		onObjectModified(object);
	}

	@Override
	public void onObjectModified(final Object object) {
		if(targetObject.isVisible())
			targetObject.repaint();
	}
}
