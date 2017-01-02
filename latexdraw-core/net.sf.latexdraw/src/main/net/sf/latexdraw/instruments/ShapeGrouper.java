package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.models.interfaces.shape.IGroup;

/**
 * This instrument groups and separates shapes.<br>
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
 * 02/13/2012<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeGrouper extends ShapePropertyCustomiser implements Initializable {
	/** The widget to group shapes. */
	@FXML protected Button groupB;

	/** The widget to separate shapes. */
	@FXML protected Button sepB;

	@FXML protected AnchorPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeGrouper() {
		super();
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isEmpty())
			setActivated(false);
		else {
			setActivated(true);
			// groupB.setDisable(shape.isEmpty());
			// sepB.setDisable(shape.size()==1 && shape.getShapeAt(0) instanceof
			// IGroup);
		}
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new Button2GroupShapes(this));
		// addInteractor(new Button2SeparateShapes(this));
	}

	// /** This link maps a button to an action that separates the selected
	// group. */
	// private static class Button2SeparateShapes extends
	// InteractorImpl<SeparateShapes, ButtonPressed, ShapeGrouper> {
	// protected Button2SeparateShapes(final ShapeGrouper ins) throws
	// InstantiationException, IllegalAccessException {
	// super(ins, false, SeparateShapes.class, ButtonPressed.class);
	// }
	//
	// @Override
	// public void initAction() {
	// final SelectShapes selection =
	// ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
	// final List<IShape> shapes = selection.shapes();
	//
	// if(shapes.size()==1 && shapes.get(0) instanceof IGroup)
	// action.setShape(shapes.get(0));
	//
	// action.setDrawing(instrument.pencil.canvas().getDrawing());
	// }
	//
	// @Override
	// public boolean isConditionRespected() {
	// return interaction.getButton()==instrument.getSepB();
	// }
	// }
	//
	//
	// /** This link maps a button to an action that groups the selected shapes.
	// */
	// private static class Button2GroupShapes extends
	// InteractorImpl<JoinShapes, ButtonPressed, ShapeGrouper> {
	// protected Button2GroupShapes(final ShapeGrouper ins) throws
	// InstantiationException, IllegalAccessException {
	// super(ins, false, JoinShapes.class, ButtonPressed.class);
	// }
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
	// action.setDrawing(instrument.pencil.canvas().getDrawing());
	// }
	//
	// @Override
	// public boolean isConditionRespected() {
	// return interaction.getButton()==instrument.getGroupB();
	// }
	// }
}
