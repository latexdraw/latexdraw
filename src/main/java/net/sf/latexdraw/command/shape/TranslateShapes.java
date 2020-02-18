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
package net.sf.latexdraw.command.shape;

import io.github.interacto.undo.Undoable;
import java.util.ResourceBundle;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import org.jetbrains.annotations.NotNull;

/**
 * This command translates shapes.
 * @author Arnaud Blouin
 */
public class TranslateShapes extends ShapeCmdImpl<Group> implements Undoable, Modifying {
	/** The x vector translation. */
	private double tx;
	/** The y vector translation. */
	private double ty;
	/**
	 * The x vector translation that has been already performed. This attribute is needed since
	 * this command can be executed several times.
	 */
	private double performedTx;
	/**
	 * The y vector translation that has been already performed. This attribute is needed since
	 * this command can be executed several times.
	 */
	private double performedTy;
	/** The drawing that will be handled by the command. */
	private final @NotNull Drawing drawing;
	private boolean mementoModified;


	public TranslateShapes(final @NotNull Drawing dr, final @NotNull Group sh) {
		super(sh);
		drawing = dr;
		performedTx = 0d;
		performedTy = 0d;
	}

	@Override
	protected void createMemento() {
		mementoModified = drawing.isModified();
	}

	@Override
	public boolean hadEffect() {
		return !MathUtils.INST.equalsDouble(performedTx, 0d) || !MathUtils.INST.equalsDouble(performedTy, 0d);
	}

	@Override
	protected void doCmdBody() {
		shape.translate(tx, ty);
		drawing.setModified(true);
		performedTx += tx;
		performedTy += ty;
	}

	@Override
	public boolean canDo() {
		return !shape.isEmpty()
			&& MathUtils.INST.isValidPt(tx, ty)
			&& (!MathUtils.INST.equalsDouble(tx, 0d)
			|| !MathUtils.INST.equalsDouble(ty, 0d));
	}

	@Override
	public void undo() {
		shape.translate(-performedTx, -performedTy);
		drawing.setModified(mementoModified);
	}

	@Override
	public void redo() {
		shape.translate(performedTx, performedTy);
		drawing.setModified(true);
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("Actions.32");
	}

	/**
	 * @param theTx The x vector translation.
	 * @param theTy The y vector translation.
	 */
	public void setT(final double theTx, final double theTy) {
		tx = theTx;
		ty = theTy;
	}
}
