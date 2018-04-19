/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.interfaces.prop;

/**
 * Properties of arrows.
 * @author Arnaud BLOUIN
 */
public interface IArrowable {
	/**
	 * @return The tbarsize dim parameter.
	 * @since 3.0
	 */
	double getTBarSizeDim();

	/**
	 * Sets the latex parameter tbarsize num.
	 * @param tbarSizeDim The new tbarsize num.
	 * @since 3.0
	 */
	void setTBarSizeDim(final double tbarSizeDim);

	/**
	 * @return The tbarsize num parameter.
	 * @since 3.0
	 */
	double getTBarSizeNum();

	/**
	 * Sets the latex parameter tbarsize num.
	 * @param tbarSizeNum The new tbarsize num.
	 * @since 3.0
	 */
	void setTBarSizeNum(final double tbarSizeNum);

	/**
	 * @return The dotsize dim parameter.
	 * @since 3.0
	 */
	double getDotSizeDim();

	/**
	 * Sets the latex parameter dotsize dim.
	 * @param dotSizeDim The new dotsize dim.
	 * @since 3.0
	 */
	void setDotSizeDim(final double dotSizeDim);

	/**
	 * @return The dotsize num parameter.
	 * @since 3.0
	 */
	double getDotSizeNum();

	/**
	 * Sets the latex parameter dotsize num.
	 * @param dotSizeNum The new dotsize num.
	 * @since 3.0
	 */
	void setDotSizeNum(final double dotSizeNum);

	/**
	 * @return The bracket num parameter.
	 * @since 3.0
	 */
	double getBracketNum();

	/**
	 * Sets the latex parameter bracket num.
	 * @param bracketNum The new bracket num.
	 * @since 3.0
	 */
	void setBracketNum(final double bracketNum);

	/**
	 * @return The arrowsize num parameter.
	 * @since 3.0
	 */
	double getArrowSizeNum();

	/**
	 * Sets the latex parameter arrowSize num.
	 * @param arrowSizeNum The new arrowSize num.
	 * @since 3.0
	 */
	void setArrowSizeNum(final double arrowSizeNum);

	/**
	 * @return The arrowsize dim parameter.
	 * @since 3.0
	 */
	double getArrowSizeDim();

	/**
	 * Sets the latex parameter arrowSize num.
	 * @param arrowSizeDim The new arrowSize num.
	 * @since 3.0
	 */
	void setArrowSizeDim(final double arrowSizeDim);

	/**
	 * @return The inset of the arrow.
	 * @since 3.0
	 */
	double getArrowInset();

	/**
	 * Defines the inset of the arrow.
	 * @param inset The new inset.
	 * @since 3.0
	 */
	void setArrowInset(final double inset);

	/**
	 * @return The length of the arrow.
	 * @since 3.0
	 */
	double getArrowLength();

	/**
	 * Defines the length of the arrow.
	 * @param lgth The new length of the arrow.
	 * @since 3.0
	 */
	void setArrowLength(final double lgth);

	/**
	 * @return The rbracket parameter.
	 * @since 3.0
	 */
	double getRBracketNum();

	/**
	 * Sets the latex parameter rbracket num.
	 * @param rBracketNum The new rbracket num.
	 * @since 3.0
	 */
	void setRBracketNum(final double rBracketNum);

	/**
	 * Sets the command to execute when one of the properties of the arrow changes.
	 * @param run The command to execute.
	 */
	void setOnArrowChanged(final Runnable run);
}
