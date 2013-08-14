package net.sf.latexdraw.glib.views.Java2D.impl;

import net.sf.latexdraw.glib.models.interfaces.IPolygon;

/**
 * Defines a view of the IPolygon model.<br>
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
 * 03/18/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LPolygonView<M extends IPolygon> extends LModifiablePointsShapeView<IPolygon> {
	/**
	 * Creates an initialises the Java view of a LPolygon.
	 * @param model The model to view.
	 * @since 3.0
	 */
	protected LPolygonView(final IPolygon model) {
		super(model);
		update();
	}
}
