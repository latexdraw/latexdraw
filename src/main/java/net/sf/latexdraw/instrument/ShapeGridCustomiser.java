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
package net.sf.latexdraw.instrument;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.model.api.property.GridProp;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;

/**
 * This instrument modifies grids properties of shapes or the pencil.
 * @author Arnaud BLOUIN
 */
public class ShapeGridCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** Changes the colour of the labels. */
	@FXML private ColorPicker colourLabels;
	/** Changes the colour of the sub-grid. */
	@FXML private ColorPicker colourSubGrid;
	/** Changes the width of the main grid. */
	@FXML private Spinner<Double> gridWidth;
	/** Changes the width of the sub-grid. */
	@FXML private Spinner<Double> subGridWidth;
	/** Changes the number of dots composing the main grid. */
	@FXML private Spinner<Integer> gridDots;
	/** Changes the number of dots composing the sub-grid. */
	@FXML private Spinner<Integer> subGridDots;
	/** Changes the division of the sub-grid. */
	@FXML private Spinner<Integer> subGridDiv;
	/** The field that defines the Y-coordinates of the labels. */
	@FXML private ToggleButton labelsYInvertedCB;
	/** The field that defines the X-coordinates of the labels. */
	@FXML private ToggleButton labelsXInvertedCB;
	@FXML private Pane mainPane;

	@Inject
	public ShapeGridCustomiser(final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing, final EditingService editing) {
		super(hand, pencil, canvas, drawing, editing);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void update(final Group gp) {
		if(gp.isTypeOf(GridProp.class)) {
			setActivated(true);
			colourLabels.setValue(gp.getGridLabelsColour().toJFX());
			colourSubGrid.setValue(gp.getSubGridColour().toJFX());
			gridWidth.getValueFactory().setValue(gp.getGridWidth());
			subGridWidth.getValueFactory().setValue(gp.getSubGridWidth());
			gridDots.getValueFactory().setValue(gp.getGridDots());
			subGridDots.getValueFactory().setValue(gp.getSubGridDots());
			subGridDiv.getValueFactory().setValue(gp.getSubGridDiv());
			labelsYInvertedCB.setSelected(!gp.isXLabelSouth());
			labelsXInvertedCB.setSelected(!gp.isYLabelWest());
		}else {
			setActivated(false);
		}
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void configureBindings() {
		addColorPropBinding(colourLabels, ShapeProperties.GRID_LABELS_COLOUR);
		addColorPropBinding(colourSubGrid, ShapeProperties.GRID_SUBGRID_COLOUR);

		addSpinnerPropBinding(gridWidth, ShapeProperties.GRID_WIDTH);
		addSpinnerPropBinding(subGridWidth, ShapeProperties.GRID_SUBGRID_WIDTH);
		addSpinnerPropBinding(subGridDots, ShapeProperties.GRID_SUBGRID_DOTS);
		addSpinnerPropBinding(gridDots, ShapeProperties.GRID_DOTS);
		addSpinnerPropBinding(subGridDiv, ShapeProperties.GRID_SUBGRID_DIV);

		addTogglePropBinding(labelsYInvertedCB, ShapeProperties.GRID_LABEL_POSITION_Y, true);
		addTogglePropBinding(labelsXInvertedCB, ShapeProperties.GRID_LABEL_POSITION_X, true);
	}
}
