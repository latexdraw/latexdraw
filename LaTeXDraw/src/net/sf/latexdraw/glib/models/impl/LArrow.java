package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a model of an arrow.<br>
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
 * 02/14/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LArrow implements IArrow {
	/**
	 * Creates an arrow from an other arrow.
	 * @param arrow The arrow to copy.
	 * @throws IllegalArgumentException If the given arrow is null.
	 */
	protected LArrow(final IArrow arrow) {
		super();

		if(arrow==null)
			throw new IllegalArgumentException();
	}


	/**
	 * Creates an arrow.
	 * @param owner The shape that contains the arrow.
	 */
	protected LArrow(final IShape owner) {
		super();

		// TODO Auto-generated constructor stub
	}

	@Override
	public double getArrowInset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getArrowLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getArrowSizeDim() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getArrowSizeNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrowStyle getArrowStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getBracketNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDotSizeDim() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDotSizeNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IPoint getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getRBracketNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IShape getShape() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTBarSizeDim() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTBarSizeNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInverted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLeftArrow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWithoutStyle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setArrowInset(final double inset) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArrowLength(final double lgth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArrowSizeDim(final double arrowSizeDim) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArrowSizeNum(final double arrowSizeNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArrowStyle(final String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArrowStyle(final ArrowStyle arrowStyle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBracketNum(final double bracketNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDotSizeDim(final double dotSizeDim) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDotSizeNum(final double dotSizeNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRBracketNum(final double rBracketNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTBarSizeDim(final double tbarSizeDim) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTBarSizeNum(final double tbarSizeNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShape(final IShape shape) {
		// TODO Auto-generated method stub

	}
}
