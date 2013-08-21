package net.sf.latexdraw.glib.models.interfaces;

/**
 * Defines an interface that classes defining a shape that
 * can have round corners should implement.<br>
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
 * 07/02/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface ILineArcShape {
	/**
	 * @return the lineArc.
	 */
	double getLineArc();

	/**
	 * @param lineArc the lineArc to set. Must be in [0,1]
	 */
	void setLineArc(final double lineArc);

	/**
	 * @return the isCornerRound.
	 */
	boolean isRoundCorner();
}
