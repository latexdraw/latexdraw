package net.sf.latexdraw.glib.models.interfaces;

/**
 * Defines an interface that classes defining a Bezier curve should implement.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public interface IBezierCurve extends IControlPointShape {
	/** The type of close that a Bézier curve can have. */
	static enum CloseType {
		CURVE, LINE, NONE;
	}


	/**
	 * @return the isClosed.
	 */
	boolean isClosed();


	/**
	 * @return The close type of the curve.
	 * @since 3.0
	 */
	CloseType getCloseType();


	/**
	 * @param isClosed the isClosed to set.
	 */
	void setClosed(final CloseType closeType);
}
