package net.sf.latexdraw.instruments;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import org.malai.action.Action;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.undo.Undoable;

import com.google.inject.Inject;

/**
 * This abstract instrument defines the base definition of instruments that
 * customise shape properties.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 10/31/10<br>
 * 
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public abstract class ShapePropertyCustomiser extends JfxInstrument {
	/** The Hand instrument. */
	protected @Inject Hand hand;

	/** The Pencil instrument. */
	protected @Inject Pencil pencil;

	/**
	 * Creates the instrument.
	 */
	protected ShapePropertyCustomiser() {
		super();
	}

	@Override
	public void onActionDone(final Action action) {
		update();
	}

	@Override
	public void onUndoableUndo(final Undoable undoable) {
		update();
	}

	@Override
	public void onUndoableRedo(final Undoable undoable) {
		update();
	}

	/**
	 * Updates the instrument and its widgets
	 * 
	 * @since 3.0
	 */
	public void update() {
		if(pencil.isActivated())
			update(ShapeFactory.createGroup(pencil.createShapeInstance()));
		else
			update(hand.getCanvas().getDrawing().getSelection());
	}

	/**
	 * Updates the widgets using the given shape.
	 * 
	 * @param shape
	 *            The shape used to update the widgets. If null, nothing is
	 *            performed.
	 * @since 3.0
	 */
	protected abstract void update(final IGroup shape);

	/**
	 * Sets the widgets of the instrument visible or not.
	 * 
	 * @param visible
	 *            True: they are visible.
	 * @since 3.0
	 */
	protected abstract void setWidgetsVisible(final boolean visible);

	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		setWidgetsVisible(activated);
	}

	/**
	 * @return The Hand instrument.
	 * @since 3.0
	 */
	public Hand getHand() {
		return hand;
	}

	/**
	 * @return The Pencil instrument.
	 * @since 3.0
	 */
	public Pencil getPencil() {
		return pencil;
	}
}
