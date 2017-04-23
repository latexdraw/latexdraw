/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.interfaces.prop.IGridProp;
import net.sf.latexdraw.models.interfaces.shape.IGroup;

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

	/**
	 * Creates the instrument.
	 */
	ShapeGridCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());

		scrollOnSpinner(subGridDiv);
		scrollOnSpinner(subGridDots);
		scrollOnSpinner(gridDots);
		scrollOnSpinner(subGridWidth);
		scrollOnSpinner(gridWidth);
	}

	@Override
	protected void update(final IGroup gp) {
		if(gp.isTypeOf(IGridProp.class)) {
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
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
		addInteractor(new ColourPicker4Pencil(this, colourLabels, ShapeProperties.GRID_LABELS_COLOUR));
		addInteractor(new ColourPicker4Selection(this, colourLabels, ShapeProperties.GRID_LABELS_COLOUR));
		addInteractor(new ColourPicker4Pencil(this, colourSubGrid, ShapeProperties.GRID_SUBGRID_COLOUR));
		addInteractor(new ColourPicker4Selection(this, colourSubGrid, ShapeProperties.GRID_SUBGRID_COLOUR));
		addInteractor(new Spinner4Pencil(this, gridWidth, ShapeProperties.GRID_WIDTH, false));
		addInteractor(new Spinner4Selection(this, gridWidth, ShapeProperties.GRID_WIDTH, false));
		addInteractor(new Spinner4Pencil(this, subGridWidth, ShapeProperties.GRID_SUBGRID_WIDTH, false));
		addInteractor(new Spinner4Selection(this, subGridWidth, ShapeProperties.GRID_SUBGRID_WIDTH, false));
		addInteractor(new Spinner4Pencil(this, subGridDots, ShapeProperties.GRID_SUBGRID_DOTS, false));
		addInteractor(new Spinner4Selection(this, subGridDots, ShapeProperties.GRID_SUBGRID_DOTS, false));
		addInteractor(new Spinner4Pencil(this, gridDots, ShapeProperties.GRID_DOTS, false));
		addInteractor(new Spinner4Selection(this, gridDots, ShapeProperties.GRID_DOTS, false));
		addInteractor(new Spinner4Pencil(this, subGridDiv, ShapeProperties.GRID_SUBGRID_DIV, false));
		addInteractor(new Spinner4Selection(this, subGridDiv, ShapeProperties.GRID_SUBGRID_DIV, false));
		addInteractor(new Checkbox4Pencil(this, labelsYInvertedCB, ShapeProperties.GRID_LABEL_POSITION_Y));
		addInteractor(new Checkbox4Selection(this, labelsYInvertedCB, ShapeProperties.GRID_LABEL_POSITION_Y));
		addInteractor(new Checkbox4Pencil(this, labelsXInvertedCB, ShapeProperties.GRID_LABEL_POSITION_X));
		addInteractor(new Checkbox4Selection(this, labelsXInvertedCB, ShapeProperties.GRID_LABEL_POSITION_X));
	}
}
