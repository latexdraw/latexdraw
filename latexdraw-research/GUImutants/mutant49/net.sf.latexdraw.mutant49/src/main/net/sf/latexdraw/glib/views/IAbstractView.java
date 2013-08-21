package net.sf.latexdraw.glib.views;

import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines an interface for abstract views.<br>
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
 * 03/01/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public interface IAbstractView {
	/**
	 * @return the model of the view.
	 * @since 3.0
	 */
	IShape getShape();


	/**
	 * Updates the view.
	 * @since 3.0
	 */
	void update();
}
