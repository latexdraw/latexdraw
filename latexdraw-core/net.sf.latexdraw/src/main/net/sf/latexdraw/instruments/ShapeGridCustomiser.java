package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.glib.models.interfaces.prop.IGridProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

/**
 * This instrument modifies grids properties of shapes or the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-15<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeGridCustomiser extends ShapePropertyCustomiser {
	/** Changes the colour of the labels. */
	@FXML protected ColorPicker colourLabels;

	/** Changes the colour of the sub-grid. */
	@FXML protected ColorPicker colourSubGrid;

	/** Changes the width of the main grid. */
	@FXML protected Spinner<Double> gridWidth;

	/** Changes the width of the sub-grid. */
	@FXML protected Spinner<Double> subGridWidth;

	/** Changes the number of dots composing the main grid. */
	@FXML protected Spinner<Integer> gridDots;

	/** Changes the number of dots composing the sub-grid. */
	@FXML protected Spinner<Integer> subGridDots;

	/** Changes the division of the sub-grid. */
	@FXML protected Spinner<Integer> subGridDiv;

	/** The field that defines the Y-coordinates of the labels. */
	@FXML protected ToggleButton labelsYInvertedCB;

	/** The field that defines the X-coordinates of the labels. */
	@FXML protected ToggleButton labelsXInvertedCB;

	@FXML protected AnchorPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeGridCustomiser() {
		super();
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
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}


	@Override
	protected void initialiseInteractors() {
//		try{
//			addInteractor(new ColourButton2PencilGrid(this));
//			addInteractor(new ColourButton2SelectionGrid(this));
//			addInteractor(new Spinner2PencilGrid(this));
//			addInteractor(new Spinner2SelectionGrid(this));
//			addInteractor(new CheckBox2ModifySelectionGrid(this));
//			addInteractor(new CheckBox2ModifyPencilGrid(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}


//	private abstract static class CheckBox4ShapeGridCust<A extends ShapePropertyAction> extends ButtonPressedForCustomiser<A, ShapeGridCustomiser> {
//		protected CheckBox4ShapeGridCust(final ShapeGridCustomiser ins, final Class<A> actClazz) throws InstantiationException, IllegalAccessException {
//			super(ins, actClazz);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return interaction.getButton()==instrument.labelsYInvertedCB || interaction.getButton()==instrument.labelsXInvertedCB;
//		}
//
//		@Override
//		public void initAction() {
//			if(interaction.getButton()==instrument.labelsYInvertedCB)
//				action.setProperty(ShapeProperties.GRID_LABEL_POSITION_Y);
//			else
//				action.setProperty(ShapeProperties.GRID_LABEL_POSITION_X);
//		}
//
//		@Override
//		public void updateAction() {
//			action.setValue(!interaction.getButton().isSelected());
//		}
//	}
//
//
//	/** The link that maps a checkbox to an action that modifies the selection. */
//	private static class CheckBox2ModifySelectionGrid extends CheckBox4ShapeGridCust<ModifyShapeProperty> {
//		protected CheckBox2ModifySelectionGrid(final ShapeGridCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return super.isConditionRespected() && instrument.hand.isActivated();
//		}
//	}
//
//
//	/** The link that maps a checkbox to an action that modifies the pencil. */
//	private static class CheckBox2ModifyPencilGrid extends CheckBox4ShapeGridCust<ModifyPencilParameter> {
//		protected CheckBox2ModifyPencilGrid(final ShapeGridCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return super.isConditionRespected() && instrument.pencil.isActivated();
//		}
//	}
//
//
//	private abstract static class SpinnerForShapeGridCust<A extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapeGridCustomiser> {
//		protected SpinnerForShapeGridCust(final ShapeGridCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
//			super(instrument, clazzAction);
//		}
//
//		@Override
//		public void initAction() {
//			final Object spinner = interaction.getSpinner();
//
//			if(spinner==instrument.gridWidth)
//				action.setProperty(ShapeProperties.GRID_WIDTH);
//			else if(spinner==instrument.subGridWidth)
//				action.setProperty(ShapeProperties.GRID_SUBGRID_WIDTH);
//			else if(spinner==instrument.gridDots)
//				action.setProperty(ShapeProperties.GRID_DOTS);
//			else if(spinner==instrument.subGridDots)
//				action.setProperty(ShapeProperties.GRID_SUBGRID_DOTS);
//			else
//				action.setProperty(ShapeProperties.GRID_SUBGRID_DIV);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			final Object spinner = interaction.getSpinner();
//			return spinner==instrument.gridWidth || spinner==instrument.subGridWidth || spinner==instrument.gridDots ||
//					spinner==instrument.subGridDots || spinner==instrument.subGridDiv;
//		}
//
//		@Override
//		public void updateAction() {
//			if(interaction.getSpinner()==instrument.gridDots || interaction.getSpinner()==instrument.subGridDots ||
//				interaction.getSpinner()==instrument.subGridDiv)
//				action.setValue(Integer.valueOf(interaction.getSpinner().getValue().toString()));
//			else
//				super.updateAction();
//		}
//	}
//
//
//	private static class Spinner2PencilGrid extends SpinnerForShapeGridCust<ModifyPencilParameter> {
//		/**
//		 * Creates the link.
//		 * @param instrument The instrument that contains the link.
//		 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//		 * @throws IllegalAccessException If no free-parameter constructor are provided.
//		 */
//		protected Spinner2PencilGrid(final ShapeGridCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.pencil.isActivated() && super.isConditionRespected();
//		}
//	}
//
//
//
//	/**
//	 * This link maps a colour button to the selected shapes.
//	 */
//	private static class Spinner2SelectionGrid extends SpinnerForShapeGridCust<ModifyShapeProperty> {
//		/**
//		 * Creates the link.
//		 * @param instrument The instrument that contains the link.
//		 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//		 * @throws IllegalAccessException If no free-parameter constructor are provided.
//		 */
//		protected Spinner2SelectionGrid(final ShapeGridCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.hand.isActivated() && super.isConditionRespected();
//		}
//	}
//
//
//
//	private abstract static class ColourButtonForShapeGridCust<A extends ShapePropertyAction> extends ColourButtonForCustomiser<A, ShapeGridCustomiser> {
//		protected ColourButtonForShapeGridCust(final ShapeGridCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
//			super(instrument, clazzAction);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			if(interaction.getButton()==instrument.colourLabels)
//				action.setProperty(ShapeProperties.GRID_LABELS_COLOUR);
//			else
//				action.setProperty(ShapeProperties.GRID_SUBGRID_COLOUR);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return interaction.getButton()==instrument.colourLabels || interaction.getButton()==instrument.colourSubGrid;
//		}
//	}
//
//
//	/**
//	 * This link maps a colour button to the pencil.
//	 */
//	private static class ColourButton2PencilGrid extends ColourButtonForShapeGridCust<ModifyPencilParameter> {
//		/**
//		 * Creates the link.
//		 * @param instrument The instrument that contains the link.
//		 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//		 * @throws IllegalAccessException If no free-parameter constructor are provided.
//		 */
//		protected ColourButton2PencilGrid(final ShapeGridCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.pencil.isActivated() && super.isConditionRespected();
//		}
//	}
//
//
//
//	/**
//	 * This link maps a colour button to the selected shapes.
//	 */
//	private static class ColourButton2SelectionGrid extends ColourButtonForShapeGridCust<ModifyShapeProperty> {
//		/**
//		 * Creates the link.
//		 * @param instrument The instrument that contains the link.
//		 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//		 * @throws IllegalAccessException If no free-parameter constructor are provided.
//		 */
//		protected ColourButton2SelectionGrid(final ShapeGridCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.hand.isActivated() && super.isConditionRespected();
//		}
//	}
}
