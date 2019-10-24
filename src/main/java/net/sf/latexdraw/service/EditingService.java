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
package net.sf.latexdraw.service;

import java.util.Collections;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.instrument.EditionChoice;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class EditingService {
	/** The current editing choice (rectangle, ellipse, etc.) of the instrument. */
	private final @NotNull ObjectProperty<EditionChoice> currentChoice;

	/** This shape gathers all the current shape parameters. Used as a model when creating shapes. Use its getter instead as it is lazy instantiated. */
	private Group groupParams;

	public EditingService() {
		super();
		currentChoice = new SimpleObjectProperty<>(EditionChoice.RECT);
	}

	/**
	 * @return the shape that gathers all the current shape parameters. Use as a model to copy while creating a new shape. Cannot be null.
	 */
	public @NotNull Group getGroupParams() {
		if(groupParams == null) {
			groupParams = ShapeFactory.INST.createGroup();
			groupParams.addShape(ShapeFactory.INST.createRectangle());
			groupParams.addShape(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
			groupParams.addShape(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
			groupParams.addShape(ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()));
			groupParams.addShape(ShapeFactory.INST.createText());
			groupParams.addShape(ShapeFactory.INST.createCircleArc());
			groupParams.addShape(ShapeFactory.INST.createPolyline(Collections.emptyList()));
			groupParams.addShape(ShapeFactory.INST.createBezierCurve(Collections.emptyList()));
			groupParams.addShape(ShapeFactory.INST.createFreeHand(Collections.emptyList()));
			groupParams.addShape(ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 10, "x", false)); //NON-NLS
		}
		return groupParams;
	}


	/**
	 * @return An instance of a shape configured (thickness, colours, etc.) with the parameters of the current editing mode.
	 * @throws java.util.NoSuchElementException If the editing mode is hand.
	 */
	public @NotNull Shape createShapeInstance() {
		return setShapeParameters(currentChoice.get().createShapeInstance().orElseThrow());
	}

	/**
	 * Configures the given shape with the parameters (e.g. thickness, colours, etc.) of the current editing mode.
	 * @param shape The shape to configure.
	 * @return The modified shape given as argument.
	 */
	public @NotNull <T extends Shape> T setShapeParameters(final @NotNull T shape) {
		shape.copy(getGroupParams());
		shape.setModified(true);
		return shape;
	}


	/**
	 * @return The current editing choice.
	 */
	public @NotNull EditionChoice getCurrentChoice() {
		return currentChoice.get();
	}

	/**
	 * Sets the current editing choice.
	 * @param choice The new editing choice to set.
	 */
	public void setCurrentChoice(final @NotNull EditionChoice choice) {
		currentChoice.set(choice);
	}

	public @NotNull ObjectProperty<EditionChoice> currentChoiceProperty() {
		return currentChoice;
	}
}
