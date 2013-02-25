package net.sf.latexdraw.mapping;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.instruments.Border;

import org.malai.mapping.Object2ObjectMapping;

/**
 * This mapping is used to link a selected shape to the border instrument.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * @author Arnaud Blouin
 * @since 3.0
 */
public class Shape2BorderMapping extends Object2ObjectMapping<IShape, Border> {
	/**
	 * {@link Object2ObjectMapping#Object2ObjectMapping(Object, Object)}
	 */
	public Shape2BorderMapping(final IShape source, final Border target) {
		super(source, target);
	}


	@Override
	public void onObjectModified(final Object object) {
		targetObject.update();
	}
}
