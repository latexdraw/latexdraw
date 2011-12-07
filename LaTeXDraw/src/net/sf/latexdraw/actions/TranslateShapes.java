package net.sf.latexdraw.actions;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.util.LNumber;

import org.malai.undo.Undoable;

/**
 * This action translates shapes.<br>
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
 * 12/05/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class TranslateShapes extends DrawingSelectionAction implements Undoable, Modifying {
	/** The x vector translation. */
	protected double tx;

	/** The y vector translation. */
	protected double ty;

	/** The x vector translation that has been already performed. This attribute is needed since
	 * this action can be executed several times. */
	private double performedTx;

	/** The y vector translation that has been already performed. This attribute is needed since
	 * this action can be executed several times. */
	private double performedTy;


	/**
	 * Initialises the action.
	 * @since 3.0
	 */
	public TranslateShapes() {
		super();

		tx = 0.;
		ty = 0.;
		performedTx = 0.;
		performedTy = 0.;
	}


	@Override
	public boolean isRegisterable() {
		return hadEffect();
	}


	@Override
	protected void doActionBody() {
		selection.translate(tx-performedTx, ty-performedTy);
		selection.setModified(true);
		drawing.setModified(true);
		performedTx = tx;
		performedTy = ty;
	}


	@Override
	public boolean canDo() {
		final boolean okShape = super.canDo() && !selection.isEmpty() && GLibUtilities.INSTANCE.isValidPoint(tx, ty);
		return okShape && (!LNumber.INSTANCE.equals(tx, 0.) || !LNumber.INSTANCE.equals(ty, 0.));
	}


	@Override
	public void undo() {
		selection.translate(-tx, -ty);
		selection.setModified(true);
		drawing.setModified(true);
	}


	@Override
	public void redo() {
		selection.translate(tx, ty);
		selection.setModified(true);
		drawing.setModified(true);
	}


	@Override
	public String getUndoName() {
		return "Translation";
	}


	/**
	 * @param tx The x vector translation.
	 * @since 3.0
	 */
	public void setTx(final double tx) {
		this.tx = tx;
	}


	/**
	 * @param ty The y vector translation.
	 * @since 3.0
	 */
	public void setTy(final double ty) {
		this.ty = ty;
	}
}
