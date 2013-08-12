package net.sf.latexdraw.glib.models.interfaces;

import java.util.List;

/**
 * Defines an interface that classes defining a shape, containg control points, should implement.<br>
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
 * 07/03/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IControlPointShape extends IModifiablePointsShape {
	/** The default position gap of control points against their reference point. */
	int DEFAULT_POSITION_CTRL = 40;

	/** The default balance gap used to balance all the points of the bézier curve. */
	int DEFAULT_BALANCE_GAP = 50;


	/**
	 * Balances all the control points in order the create a rounded shape.
	 */
	void balance();

	/**
	 * @return the balanceGap.
	 */
	int getBalanceGap();

	/**
	 * @param balanceGap the balanceGap to set.
	 */
	void setBalanceGap(final int balanceGap);

	/**
	 * @param position The position of the wanted points (-1 for the last point).
	 * @return The first control point at the given position or null if the position is not valid.
	 * @since 3.0
	 */
	IPoint getFirstCtrlPtAt(final int position);

	/**
	 * @param position The position of the wanted points (-1 for the last point).
	 * @return The second control point at the given position or null if the position is not valid.
	 * @since 3.0
	 */
	IPoint getSecondCtrlPtAt(final int position);

	/**
	 * Updates the second control points by using the first control points.
	 * @since 1.9
	 */
	void updateSecondControlPoints();

	/**
	 * Sets the X-coordinate of one of the first control point.
	 * @param x The new X-coordinate.
	 * @param id The position of the point to set.
	 * @since 1.9
	 */
	void setXFirstCtrlPt(final double x, final int id);

	/**
	 * Sets the Y-coordinate of one of the first control point.
	 * @param y The new Y-coordinate.
	 * @param id The position of the point to set.
	 * @since 1.9
	 */
	void setYFirstCtrlPt(final double y, final int id);

	/**
	 * Sets the X-coordinate of one of the second control point.
	 * @param x The new X-coordinate.
	 * @param id The position of the point to set.
	 * @since 1.9
	 */
	void setXSecondCtrlPt(final double x, final int id);

	/**
	 * Sets the Y-coordinate of one of the second control point.
	 * @param y The new Y-coordinate.
	 * @param id The position of the point to set.
	 * @since 1.9
	 */
	void setYSecondCtrlPt(final double y, final int id);

	/**
	 * @return the firstCtrlPts.
	 */
	List<IPoint> getFirstCtrlPts();

	/**
	 * @return the secondCtrlPts.
	 */
	List<IPoint> getSecondCtrlPts();
}
