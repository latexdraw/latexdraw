package net.sf.latexdraw.glib.models.interfaces;

/**
 * Defines an interface to share operations on arrow's properties.<br>
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
 * 08/07/2011<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IArrowable {
	/**
	 * Sets the latex parameter dotsize dim.
	 * @param dotSizeDim The new dotsize dim.
	 * @since 3.0
	 */
	void setDotSizeDim(final double dotSizeDim);

	/**
	 * Sets the latex parameter dotsize num.
	 * @param dotSizeNum The new dotsize num.
	 * @since 3.0
	 */
	void setDotSizeNum(final double dotSizeNum);

	/**
	 * Sets the latex parameter tbarsize num.
	 * @param tbarSizeNum The new tbarsize num.
	 * @since 3.0
	 */
	void setTBarSizeNum(final double tbarSizeNum);

	/**
	 * Sets the latex parameter tbarsize num.
	 * @param tbarSizeDim The new tbarsize num.
	 * @since 3.0
	 */
	void setTBarSizeDim(final double tbarSizeDim);

	/**
	 * @return The tbarsize dim parameter.
	 * @since 3.0
	 */
	double getTBarSizeDim();

	/**
	 * @return The tbarsize num parameter.
	 * @since 3.0
	 */
	double getTBarSizeNum();

	/**
	 * Sets the latex parameter rbracket num.
	 * @param rBracketNum The new rbracket num.
	 * @since 3.0
	 */
	void setRBracketNum(final double rBracketNum);

	/**
	 * Sets the latex parameter bracket num.
	 * @param bracketNum The new bracket num.
	 * @since 3.0
	 */
	void setBracketNum(final double bracketNum);

	/**
	 * Defines the length of the arrow.
	 * @param lgth The new length of the arrow.
	 * @since 3.0
	 */
	void setArrowLength(final double lgth);

	/**
	 * Sets the latex parameter arrowSize num.
	 * @param arrowSizeDim The new arrowSize num.
	 * @since 3.0
	 */
	void setArrowSizeDim(final double arrowSizeDim);

	/**
	 * Sets the latex parameter arrowSize num.
	 * @param arrowSizeNum The new arrowSize num.
	 * @since 3.0
	 */
	void setArrowSizeNum(final double arrowSizeNum);

	/**
	 * Defines the inset of the arrow.
	 * @param inset The new inset.
	 * @since 3.0
	 */
	void setArrowInset(final double inset);

	/**
	 * @return The dotsize dim parameter.
	 * @since 3.0
	 */
	double getDotSizeDim();

	/**
	 * @return The dotsize num parameter.
	 * @since 3.0
	 */
	double getDotSizeNum();

	/**
	 * @return The bracket num parameter.
	 * @since 3.0
	 */
	double getBracketNum();

	/**
	 * @return The arrowsize num parameter.
	 * @since 3.0
	 */
	double getArrowSizeNum();

	/**
	 * @return The arrowsize dim parameter.
	 * @since 3.0
	 */
	double getArrowSizeDim();

	/**
	 * @return The inset of the arrow.
	 * @since 3.0
	 */
	double getArrowInset();

	/**
	 * @return The length of the arrow.
	 * @since 3.0
	 */
	double getArrowLength();

	/**
	 * @return The rbracket parameter.
	 * @since 3.0
	 */
	double getRBracketNum();
}
