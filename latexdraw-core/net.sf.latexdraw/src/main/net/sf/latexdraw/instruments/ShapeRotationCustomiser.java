package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

/**
 * This instrument modifies the rotation angle of selected shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 12/31/2010<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeRotationCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** The rotation button to perform 90 degree rotation. */
	@FXML protected Button rotate90Button;

	/** The rotation button to perform 180 degree rotation. */
	@FXML protected Button rotate180Button;

	/** The rotation button to perform 270 degree rotation. */
	@FXML protected Button rotate270Button;

	/** The field that modifies the rotation angle. */
	@FXML protected Spinner<Double> rotationField;

	@FXML protected AnchorPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeRotationCustomiser() {
		super();
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isEmpty())
			setActivated(false);
		else {
			rotationField.getValueFactory().setValue(Math.toDegrees(shape.getRotationAngle()));
			setActivated(true);
		}
	}

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new ButtonPress2RotateShape(this));
		// addInteractor(new Spinner2RotateShape(this));
	}
}

// /**
// * This link maps a spinner to an action that rotates the selected shapes.
// */
// class Spinner2RotateShape extends InteractorImpl<RotateShapes,
// SpinnerModified, ShapeRotationCustomiser> {
// /**
// * Creates the link.
// * @param ins The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// Spinner2RotateShape(final ShapeRotationCustomiser ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, RotateShapes.class, SpinnerModified.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// return instrument.getRotationField()==interaction.getSpinner();
// }
//
// @Override
// public void updateAction() {
// final Option<IShape> obj = action.shape();
// if(obj.isDefined())
// action.setRotationAngle(Math.toRadians(Double.valueOf(interaction.getSpinner().getValue().toString()))-obj.get().getRotationAngle());
// }
//
// @Override
// public void initAction() {
// final IShape sh =
// instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false);
// action.setShape(sh);
// action.setGravityCentre(sh.getGravityCentre());
// }
// }
//
//
//
// /**
// * This link maps a button to an action that rotates the selected shapes.
// */
// class ButtonPress2RotateShape extends InteractorImpl<RotateShapes,
// ButtonPressed, ShapeRotationCustomiser> {
// /**
// * Creates the link.
// * @param ins The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// ButtonPress2RotateShape(final ShapeRotationCustomiser ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, RotateShapes.class, ButtonPressed.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// final AbstractButton button = interaction.getButton();
// return button==instrument.rotate90Button ||
// button==instrument.rotate180Button || button==instrument.rotate270Button;
// }
//
// @Override
// public void initAction() {
// final double angle;
// final AbstractButton button = interaction.getButton();
//
// if(button==instrument.rotate90Button)
// angle = Math.PI/2.;
// else if(button==instrument.rotate180Button)
// angle = Math.PI;
// else
// angle = -Math.PI/2.;
//
// action.setGravityCentre(instrument.pencil.canvas().getDrawing().getSelection().getGravityCentre());
// action.setRotationAngle(angle);
// action.setShape(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
// }
// }
