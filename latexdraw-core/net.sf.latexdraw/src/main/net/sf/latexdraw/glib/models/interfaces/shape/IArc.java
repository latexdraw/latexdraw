package net.sf.latexdraw.glib.models.interfaces.shape;

import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp;

/**
 * Defines an interface that classes defining a elliptic arc should implement.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/03/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IArc extends IPositionShape, IArcProp {
	/**
	 * @return The coordinate of the start point of the arc.
	 * @since 1.9
	 */
	IPoint getStartPoint();

	/**
	 * @return The coordinate of the end point of the arc.
	 * @since 1.9
	 */
	IPoint getEndPoint();
}
