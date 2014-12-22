package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument deletes the selected shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 01/05/2010<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeDeleter extends JfxInstrument {
	/** The button used to remove the selected shapes. */
	@FXML protected Button deleteB;

	/**
	 * Creates the instrument.
	 */
	public ShapeDeleter() {
		super();
	}

	// @Override
	// public void setActivated(final boolean activated, final boolean
	// hideWidgets) {
	// super.setActivated(activated, hideWidgets);
	// updateWidgets(hideWidgets);
	// }

	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		updateWidgets(false);
	}

	/**
	 * Updates the widgets of this instrument.
	 * 
	 * @param hideWidgets
	 *            True: the widgets are hidden on deactivation.
	 * @since 3.0
	 */
	protected void updateWidgets(final boolean hideWidgets) {
		deleteB.setVisible(activated||!hideWidgets);
		deleteB.setDisable(!activated);
	}

	@Override
	protected void initialiseInteractors() {
		// try{
		// addInteractor(new ButtonPressed2DeleteShapes(this));
		// addInteractor(new KeyPressed2DeleteShapes(this));
		// }catch(InstantiationException | IllegalAccessException e){
		// BadaboomCollector.INSTANCE.add(e);
		// }
	}
}

/**
 * // * This abstract link maps an interaction to an action that delete shapes.
 * //
 */
// abstract class DeleteShapesInteractor<I extends Interaction> extends
// InteractorImpl<DeleteShapes, I, ShapeDeleter> {
// /**
// * Creates the link.
// * @param ins The instrument that contains the link.
// * @param clazzInteraction The class of the interaction to create.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected DeleteShapesInteractor(final ShapeDeleter ins, final Class<I>
// clazzInteraction) throws InstantiationException, IllegalAccessException {
// super(ins, false, DeleteShapes.class, clazzInteraction);
// }
//
//
// @Override
// public void initAction() {
// final SelectShapes selection =
// ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
// final List<IShape> shapes = selection.shapes();
//
// for(final IShape sh : shapes)
// action.addShape(sh);
//
// action.setDrawing(selection.drawing().get());
// }
//
//
// @Override
// public boolean isConditionRespected() {
// final SelectShapes selection =
// ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
// return selection!=null && !selection.shapes().isEmpty();
// }
// }
//
//
// /**
// * This link maps an key pressure interaction to an action that delete shapes.
// */
// class KeyPressed2DeleteShapes extends DeleteShapesInteractor<KeyPressure> {
// /**
// * Creates the link.
// * @param ins The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected KeyPressed2DeleteShapes(final ShapeDeleter ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, KeyPressure.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getKey()==KeyEvent.VK_DELETE &&
// super.isConditionRespected();
// }
// }
//
//
// /**
// * This link maps an button pressure interaction to an action that delete
// shapes.
// */
// class ButtonPressed2DeleteShapes extends
// DeleteShapesInteractor<ButtonPressed> {
// /**
// * Creates the link.
// * @param ins The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected ButtonPressed2DeleteShapes(final ShapeDeleter ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, ButtonPressed.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getButton()==instrument.deleteB &&
// super.isConditionRespected();
// }
// }
