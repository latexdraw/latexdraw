package net.sf.latexdraw.instruments;

import java.awt.ItemSelectable;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JLabel;

import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.malai.action.Action;
import org.malai.instrument.Link;
import org.malai.swing.instrument.WidgetInstrument;
import org.malai.swing.interaction.library.ButtonPressed;
import org.malai.swing.interaction.library.CheckBoxModified;
import org.malai.swing.interaction.library.ListSelectionModified;
import org.malai.swing.interaction.library.SpinnerModified;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MColorButton;
import org.malai.undo.Undoable;

/**
 * This abstract instrument defines the base definition of instruments that customise
 * shape properties.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 10/31/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public abstract class ShapePropertyCustomiser extends WidgetInstrument {
	/** The Hand instrument. */
	protected Hand hand;

	/** The Pencil instrument. */
	protected Pencil pencil;


	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public ShapePropertyCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer);
		this.hand   = Objects.requireNonNull(hand);
		this.pencil = Objects.requireNonNull(pencil);
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
	 * @since 3.0
	 */
	public void update() {
		if(pencil.isActivated())
			update(pencil.createShapeInstance());
		else
			update(hand.canvas().getDrawing().getSelection());
	}


	/**
	 * Updates the widgets using the given shape.
	 * @param shape The shape used to update the widgets. If null, nothing is performed.
	 * @since 3.0
	 */
	protected abstract void update(final IShape shape);


	/**
	 * Sets the widgets of the instrument visible or not.
	 * @param visible True: they are visible.
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



/**
 * This link maps a list widget to an object.
 */
abstract class ListForCustomiser<A extends ShapePropertyAction, N extends ShapePropertyCustomiser> extends Link<A, ListSelectionModified, N> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @param clazzAction The class of the action to create.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected ListForCustomiser(final N instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
		super(instrument, false, clazzAction, ListSelectionModified.class);
	}

	/**
	 * @return The selected text. Can be null.
	 * @since 3.0
	 */
	protected String getLabelText() {
		final ItemSelectable is	 = interaction.getList();
		final Object[] selection = is.getSelectedObjects();

		return selection.length!=1 && ! (selection[0] instanceof JLabel) ? null : ((JLabel)selection[0]).getText();
	}
}



/**
 * This link maps a colour button to an object.
 */
abstract class ColourButtonForCustomiser<A extends ShapePropertyAction, N extends ShapePropertyCustomiser> extends Link<A, ButtonPressed, N> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @param clazzAction The class of the action to create.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected ColourButtonForCustomiser(final N instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
		super(instrument, false, clazzAction, ButtonPressed.class);
	}

	@Override
	public void initAction() {
		final MColorButton button = (MColorButton)interaction.getButton();
		action.setValue(JColorChooser.showDialog(button, "", button.getColor())); //$NON-NLS-1$
	}
}




/**
 * This link maps a checkbox to an object.
 */
abstract class CheckBoxForCustomiser<A extends ShapePropertyAction, N extends ShapePropertyCustomiser> extends Link<A, CheckBoxModified, N> {
	/**
	 * Creates the link.
	 * @param instrument The instrument that contains the link.
	 * @param clazzAction The class of the action to create.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected CheckBoxForCustomiser(final N instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
		super(instrument, false, clazzAction, CheckBoxModified.class);
	}

	@Override
	public void initAction() {
		action.setValue(interaction.getCheckBox().isSelected());
	}
}


/**
 * This link maps a button to an object.
 */
abstract class ButtonPressedForCustomiser<A extends ShapePropertyAction, N extends ShapePropertyCustomiser> extends Link<A, ButtonPressed, N> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @param clazzAction The class of the action to create.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected ButtonPressedForCustomiser(final N ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
		super(ins, false, clazzAction, ButtonPressed.class);
	}
}



/**
 * This link maps a spinner to an object.
 */
abstract class SpinnerForCustomiser<A extends ShapePropertyAction, N extends ShapePropertyCustomiser> extends Link<A, SpinnerModified, N> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @param clazzAction The class of the action to create.
	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
	 * @throws IllegalAccessException If no free-parameter constructor are provided.
	 */
	protected SpinnerForCustomiser(final N ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
		super(ins, false, clazzAction, SpinnerModified.class);
	}

	@Override
	public void updateAction() {
		action.setValue(Double.valueOf(interaction.getSpinner().getValue().toString()));
	}
}
