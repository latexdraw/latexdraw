package net.sf.latexdraw.instruments;

import java.awt.Color;

import javax.swing.JLabel;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;

import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MButtonIcon;
import org.malai.swing.widget.MColorButton;
import org.malai.swing.widget.MSpinner;
import org.malai.swing.widget.MSpinner.MSpinnerNumberModel;

/**
 * This instrument modifies grids properties of shapes or the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
	protected MColorButton colourLabels;

	/** Changes the colour of the sub-grid. */
	protected MColorButton colourSubGrid;

	/** Changes the width of the main grid. */
	protected MSpinner gridWidth;

	/** Changes the width of the sub-grid. */
	protected MSpinner subGridWidth;

	/** Changes the number of dots composing the main grid. */
	protected MSpinner gridDots;

	/** Changes the number of dots composing the sub-grid. */
	protected MSpinner subGridDots;

	/** Changes the division of the sub-grid. */
	protected MSpinner subGridDiv;


	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null or if the drawing cannot
	 * be accessed from the hand.
	 * @since 3.0
	 */
	public ShapeGridCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	@Override
	protected void update(final IShape shape) {
		if(shape.isTypeOf(IGrid.class)) {
			final IGrid grid = (IGrid)shape;
			colourLabels.setColor(grid.getGridLabelsColour());
			colourSubGrid.setColor(grid.getSubGridColour());
			gridWidth.setValueSafely(grid.getGridWidth());
			subGridWidth.setValueSafely(grid.getSubGridWidth());
			gridDots.setValueSafely(grid.getGridDots());
			subGridDots.setValueSafely(grid.getSubGridDots());
			subGridDiv.setValueSafely(grid.getSubGridDiv());
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(colourLabels, activated);
		composer.setWidgetVisible(colourSubGrid, activated);
		composer.setWidgetVisible(gridWidth, activated);
		composer.setWidgetVisible(subGridWidth, activated);
		composer.setWidgetVisible(gridDots, activated);
		composer.setWidgetVisible(subGridDots, activated);
		composer.setWidgetVisible(subGridDiv, activated);
	}


	@Override
	protected void initialiseWidgets() {
		colourLabels  = new MColorButton("Labels", new MButtonIcon(Color.BLACK));
		colourSubGrid = new MColorButton("Sub-grid", new MButtonIcon(Color.BLACK));
		gridWidth	  = new MSpinner(new MSpinnerNumberModel(1., 0.1, 1000., 0.5), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersGridFrame.6")));
		subGridWidth  = new MSpinner(new MSpinnerNumberModel(1., 0.1, 1000., 0.5), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersGridFrame.7")));
		gridDots 	  = new MSpinner(new MSpinnerNumberModel(0, 0, 10000, 1), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersGridFrame.5")));
		subGridDots	  = new MSpinner(new MSpinnerNumberModel(0, 0, 10000, 1), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersGridFrame.4")));
		subGridDiv	  = new MSpinner(new MSpinnerNumberModel(1, 1, 100, 1), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersGridFrame.8")));
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new ColourButton2PencilGrid(this));
			addLink(new ColourButton2SelectionGrid(this));
			addLink(new Spinner2PencilGrid(this));
			addLink(new Spinner2SelectionGrid(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The button that permits to change the colour of the labels.
	 * @since 3.0
	 */
	public final MColorButton getColourLabels() {
		return colourLabels;
	}

	/**
	 * @return The button that permits to change the colour of the sub-grid.
	 * @since 3.0
	 */
	public final MColorButton getColourSubGrid() {
		return colourSubGrid;
	}

	/**
	 * @return The spinner that permits to change the width of the main grid.
	 * @since 3.0
	 */
	public final MSpinner getGridWidth() {
		return gridWidth;
	}

	/**
	 * @return The spinner that permits to change the width of the sub-grid.
	 * @since 3.0
	 */
	public final MSpinner getSubGridWidth() {
		return subGridWidth;
	}

	/**
	 * @return The spinner that permits to change the number of dots composing the main grid.
	 * @since 3.0
	 */
	public final MSpinner getGridDots() {
		return gridDots;
	}

	/**
	 * @return The spinner that permits to change the number of dots composing the sub-grid.
	 * @since 3.0
	 */
	public final MSpinner getSubGridDots() {
		return subGridDots;
	}

	/**
	 * @return The spinner that permits to change the division of the sub-grid.
	 * @since 3.0
	 */
	public final MSpinner getSubGridDiv() {
		return subGridDiv;
	}


	private static abstract class SpinnerForShapeGridCust<A extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapeGridCustomiser> {
		protected SpinnerForShapeGridCust(final ShapeGridCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(instrument, clazzAction);
		}

		@Override
		public void initAction() {
			final Object spinner = interaction.getSpinner();

			if(spinner==instrument.gridWidth)
				action.setProperty(ShapeProperties.GRID_WIDTH);
			else if(spinner==instrument.subGridWidth)
				action.setProperty(ShapeProperties.GRID_SUBGRID_WIDTH);
			else if(spinner==instrument.gridDots)
				action.setProperty(ShapeProperties.GRID_DOTS);
			else if(spinner==instrument.subGridDots)
				action.setProperty(ShapeProperties.GRID_SUBGRID_DOTS);
			else
				action.setProperty(ShapeProperties.GRID_SUBGRID_DIV);
		}

		@Override
		public boolean isConditionRespected() {
			final Object spinner = interaction.getSpinner();
			return spinner==instrument.gridWidth || spinner==instrument.subGridWidth || spinner==instrument.gridDots ||
					spinner==instrument.subGridDots || spinner==instrument.subGridDiv;
		}

		@Override
		public void updateAction() {
			if(interaction.getSpinner()==instrument.gridDots || interaction.getSpinner()==instrument.subGridDots ||
				interaction.getSpinner()==instrument.subGridDiv)
				action.setValue(Integer.valueOf(interaction.getSpinner().getValue().toString()));
			else
				super.updateAction();
		}
	}


	private static class Spinner2PencilGrid extends SpinnerForShapeGridCust<ModifyPencilParameter> {
		/**
		 * Creates the link.
		 * @param instrument The instrument that contains the link.
		 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
		 * @throws IllegalAccessException If no free-parameter constructor are provided.
		 */
		protected Spinner2PencilGrid(final ShapeGridCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyPencilParameter.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setPencil(instrument.pencil);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated() && super.isConditionRespected();
		}
	}



	/**
	 * This link maps a colour button to the selected shapes.
	 */
	private static class Spinner2SelectionGrid extends SpinnerForShapeGridCust<ModifyShapeProperty> {
		/**
		 * Creates the link.
		 * @param instrument The instrument that contains the link.
		 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
		 * @throws IllegalAccessException If no free-parameter constructor are provided.
		 */
		protected Spinner2SelectionGrid(final ShapeGridCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated() && super.isConditionRespected();
		}
	}



	private static abstract class ColourButtonForShapeGridCust<A extends ShapePropertyAction> extends ColourButtonForCustomiser<A, ShapeGridCustomiser> {
		protected ColourButtonForShapeGridCust(final ShapeGridCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(instrument, clazzAction);
		}

		@Override
		public void initAction() {
			super.initAction();
			if(interaction.getButton()==instrument.colourLabels)
				action.setProperty(ShapeProperties.GRID_LABELS_COLOUR);
			else
				action.setProperty(ShapeProperties.GRID_SUBGRID_COLOUR);
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getButton()==instrument.colourLabels || interaction.getButton()==instrument.colourSubGrid;
		}
	}


	/**
	 * This link maps a colour button to the pencil.
	 */
	private static class ColourButton2PencilGrid extends ColourButtonForShapeGridCust<ModifyPencilParameter> {
		/**
		 * Creates the link.
		 * @param instrument The instrument that contains the link.
		 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
		 * @throws IllegalAccessException If no free-parameter constructor are provided.
		 */
		protected ColourButton2PencilGrid(final ShapeGridCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyPencilParameter.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setPencil(instrument.pencil);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated() && super.isConditionRespected();
		}
	}



	/**
	 * This link maps a colour button to the selected shapes.
	 */
	private static class ColourButton2SelectionGrid extends ColourButtonForShapeGridCust<ModifyShapeProperty> {
		/**
		 * Creates the link.
		 * @param instrument The instrument that contains the link.
		 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
		 * @throws IllegalAccessException If no free-parameter constructor are provided.
		 */
		protected ColourButton2SelectionGrid(final ShapeGridCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated() && super.isConditionRespected();
		}
	}
}
