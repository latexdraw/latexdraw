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
package net.sf.latexdraw.commands.shape;

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.malai.command.CommandImpl;

/**
 * This abstract command moves any kind of points.
 * @author Arnaud Blouin
 */
public abstract class MovePoint extends CommandImpl {
	/** The point to move. */
	protected IPoint point;

	/** The new coordinates of the point to move. */
	protected IPoint newCoord;

	/** The X-translation vector performed by the command. For undo/redo. */
	protected double tx;

	/** The Y-translation vector performed by the command. For undo/redo. */
	protected double ty;


	/**
	 * Creates the command.
	 * @since 3.0
	 */
	protected MovePoint() {
		super();
		tx = 0d;
		ty = 0d;
	}

	@Override
	public boolean canDo() {
		return MathUtils.INST.isValidPt(point) && MathUtils.INST.isValidPt(newCoord);
	}

	@Override
	public boolean hadEffect() {
		return super.hadEffect() && (!MathUtils.INST.equalsDouble(tx, 0d) || !MathUtils.INST.equalsDouble(ty, 0d));
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.LIMITED;
	}

	/**
	 * Sets the point to move.
	 * @param pt The point to move.
	 */
	public void setPoint(final IPoint pt) {
		point = pt;
	}

	/**
	 * Sets the new coordinates of the point to move.
	 * @param coord The new coordinates of the point to move.
	 * @since 3.0
	 */
	public void setNewCoord(final IPoint coord) {
		newCoord = coord;
	}
}
