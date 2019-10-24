/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.api.property;

/**
 * Properties of arrows.
 * @author Arnaud BLOUIN
 */
public interface Arrowable {
	/**
	 * @return The tbarsize dim parameter.
	 */
	double getTBarSizeDim();

	/**
	 * Sets the latex parameter tbarsize num.
	 * @param tbarSizeDim The new tbarsize num.
	 */
	void setTBarSizeDim(final double tbarSizeDim);

	/**
	 * @return The tbarsize num parameter.
	 */
	double getTBarSizeNum();

	/**
	 * Sets the latex parameter tbarsize num.
	 * @param tbarSizeNum The new tbarsize num.
	 */
	void setTBarSizeNum(final double tbarSizeNum);

	/**
	 * @return The dotsize dim parameter.
	 */
	double getDotSizeDim();

	/**
	 * Sets the latex parameter dotsize dim.
	 * @param dotSizeDim The new dotsize dim.
	 */
	void setDotSizeDim(final double dotSizeDim);

	/**
	 * @return The dotsize num parameter.
	 */
	double getDotSizeNum();

	/**
	 * Sets the latex parameter dotsize num.
	 * @param dotSizeNum The new dotsize num.
	 */
	void setDotSizeNum(final double dotSizeNum);

	/**
	 * @return The bracket num parameter.
	 */
	double getBracketNum();

	/**
	 * Sets the latex parameter bracket num.
	 * @param bracketNum The new bracket num.
	 */
	void setBracketNum(final double bracketNum);

	/**
	 * @return The arrowsize num parameter.
	 */
	double getArrowSizeNum();

	/**
	 * Sets the latex parameter arrowSize num.
	 * @param arrowSizeNum The new arrowSize num.
	 */
	void setArrowSizeNum(final double arrowSizeNum);

	/**
	 * @return The arrowsize dim parameter.
	 */
	double getArrowSizeDim();

	/**
	 * Sets the latex parameter arrowSize num.
	 * @param arrowSizeDim The new arrowSize num.
	 */
	void setArrowSizeDim(final double arrowSizeDim);

	/**
	 * @return The inset of the arrow.
	 */
	double getArrowInset();

	/**
	 * Defines the inset of the arrow.
	 * @param inset The new inset.
	 */
	void setArrowInset(final double inset);

	/**
	 * @return The length of the arrow.
	 */
	double getArrowLength();

	/**
	 * Defines the length of the arrow.
	 * @param lgth The new length of the arrow.
	 */
	void setArrowLength(final double lgth);

	/**
	 * @return The rbracket parameter.
	 */
	double getRBracketNum();

	/**
	 * Sets the latex parameter rbracket num.
	 * @param rBracketNum The new rbracket num.
	 */
	void setRBracketNum(final double rBracketNum);

	/**
	 * Sets the command to execute when one of the properties of the arrow changes.
	 * @param run The command to execute.
	 */
	void onChanges(final Runnable run);
}
