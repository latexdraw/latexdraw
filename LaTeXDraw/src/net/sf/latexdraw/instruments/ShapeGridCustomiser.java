package net.sf.latexdraw.instruments;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.ModifyShapeProperty;
import net.sf.latexdraw.actions.ShapeProperties;
import net.sf.latexdraw.actions.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IStandardGrid;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.ui.UIComposer;
import org.malai.widget.MSpinner;
import org.malai.widget.MToggleButton;

/**
 * This instrument modifies the parameters of grids and axes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 12/23/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeGridCustomiser extends ShapePropertyCustomiser {
	/** The field that sets the X-coordinate of the starting point of the grid. */
	protected MSpinner xStartS;

	/** The field that sets the Y-coordinate of the starting point of the grid. */
	protected MSpinner yStartS;

	/** The field that sets the X-coordinate of the ending point of the grid. */
	protected MSpinner xEndS;

	/** The field that sets the Y-coordinate of the ending point of the grid. */
	protected MSpinner yEndS;

	/** The field that sets the size of the labels of the grid. */
	protected MSpinner labelsSizeS;

	/** The field that defines the Y-coordinates of the labels. */
	protected MToggleButton labelsYInvertedCB;

	/** The field that defines the X-coordinates of the labels. */
	protected MToggleButton labelsXInvertedCB;


	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public ShapeGridCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	@Override
	protected void update(final IShape shape) {
		if(shape instanceof IStandardGrid && (!(shape instanceof IGroup) || ((IGroup)shape).containsGrids())) {
			final IStandardGrid grid = (IStandardGrid)shape;

			((MSpinner.MSpinnerNumberModel)xStartS.getModel()).setMaximumSafely(grid.getGridEndX());
			((MSpinner.MSpinnerNumberModel)yStartS.getModel()).setMaximumSafely(grid.getGridEndY());
			xStartS.setValueSafely(grid.getGridStartX());
			yStartS.setValueSafely(grid.getGridStartY());
			((MSpinner.MSpinnerNumberModel)xEndS.getModel()).setMinumunSafely(grid.getGridStartX());
			((MSpinner.MSpinnerNumberModel)yEndS.getModel()).setMinumunSafely(grid.getGridStartY());
			xEndS.setValueSafely(grid.getGridEndX());
			yEndS.setValueSafely(grid.getGridEndY());
			labelsSizeS.setValueSafely(grid.getLabelsSize());
			labelsYInvertedCB.setSelected(!grid.isXLabelSouth());
			labelsXInvertedCB.setSelected(!grid.isYLabelWest());
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(xStartS, visible);
		composer.setWidgetVisible(yStartS, visible);
		composer.setWidgetVisible(xEndS, visible);
		composer.setWidgetVisible(yEndS, visible);
		composer.setWidgetVisible(labelsSizeS, visible);
		composer.setWidgetVisible(labelsYInvertedCB, visible);
		composer.setWidgetVisible(labelsXInvertedCB, visible);
	}


	@Override
	protected void initialiseWidgets() {
		xStartS = new MSpinner(new MSpinner.MSpinnerNumberModel(0., (double)Integer.MIN_VALUE, (double)Integer.MAX_VALUE, 1.), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersGridFrame.12")));//$NON-NLS-1$
		xStartS.setEditor(new JSpinner.NumberEditor(xStartS, "0"));//$NON-NLS-1$
		xStartS.setToolTipText("Sets the minimal X-coordinate of the grid.");

		yStartS = new MSpinner(new MSpinner.MSpinnerNumberModel(0., (double)Integer.MIN_VALUE, (double)Integer.MAX_VALUE, 1.), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersGridFrame.14")));//$NON-NLS-1$
		yStartS.setEditor(new JSpinner.NumberEditor(yStartS, "0"));//$NON-NLS-1$
		yStartS.setToolTipText("Sets the minimal Y-coordinate of the grid.");

		xEndS = new MSpinner(new MSpinner.MSpinnerNumberModel(0., (double)Integer.MIN_VALUE, (double)Integer.MAX_VALUE, 1.), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersGridFrame.11")));//$NON-NLS-1$
		xEndS.setEditor(new JSpinner.NumberEditor(xEndS, "0"));//$NON-NLS-1$
		xEndS.setToolTipText("Sets the maximal X-coordinate of the grid.");

		yEndS = new MSpinner(new MSpinner.MSpinnerNumberModel(0., (double)Integer.MIN_VALUE, (double)Integer.MAX_VALUE, 1.), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersGridFrame.13")));//$NON-NLS-1$
		yEndS.setEditor(new JSpinner.NumberEditor(yEndS, "0"));//$NON-NLS-1$
		yEndS.setToolTipText("Sets the maximal Y-coordinate of the grid.");

		labelsSizeS = new MSpinner(new MSpinner.MSpinnerNumberModel(10, 1, 100, 1), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersGridFrame.9")));//$NON-NLS-1$
		labelsSizeS.setEditor(new JSpinner.NumberEditor(labelsSizeS, "0"));//$NON-NLS-1$
		labelsSizeS.setToolTipText("Sets the size of the labels of the grid.");

     	labelsYInvertedCB = new MToggleButton(LResources.GRID_Y_LABEL);
     	labelsYInvertedCB.setToolTipText("Changes the Y-coordinates of the labels.");

     	labelsXInvertedCB = new MToggleButton(LResources.GRID_X_LABEL);
     	labelsXInvertedCB.setToolTipText("Changes the X-coordinates of the labels.");
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new Spinner2ModifySelectionGridCoords(this));
			addLink(new Spinner2ModifyPencilGridCoords(this));
			addLink(new CheckBox2ModifySelectionGrid(this));
			addLink(new CheckBox2ModifyPencilGrid(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The field that sets the X-coordinate of the starting point of the grid.
	 * @since 3.0
	 */
	public MSpinner getxStartS() {
		return xStartS;
	}

	/**
	 * @return The field that sets the Y-coordinate of the starting point of the grid.
	 * @since 3.0
	 */
	public MSpinner getyStartS() {
		return yStartS;
	}

	/**
	 * @return The field that sets the X-coordinate of the ending point of the grid.
	 * @since 3.0
	 */
	public MSpinner getxEndS() {
		return xEndS;
	}

	/**
	 * @return The field that sets the Y-coordinate of the ending point of the grid.
	 * @since 3.0
	 */
	public MSpinner getyEndS() {
		return yEndS;
	}

	/**
	 * @return The field that sets the size of the labels of the grid.
	 * @since 3.0
	 */
	public MSpinner getLabelsSizeS() {
		return labelsSizeS;
	}

	/**
	 * @return The field that defines the Y-coordinates of the labels.
	 * @since 3.0
	 */
	public MToggleButton getLabelsYInvertedCB() {
		return labelsYInvertedCB;
	}

	/**
	 * @return The field that defines the X-coordinates of the labels.
	 * @since 3.0
	 */
	public MToggleButton getLabelsXInvertedCB() {
		return labelsXInvertedCB;
	}


	private static abstract class CheckBox4ShapeGridCust<A extends ShapePropertyAction> extends ButtonPressedForCustomiser<A, ShapeGridCustomiser> {
		protected CheckBox4ShapeGridCust(final ShapeGridCustomiser ins, Class<A> actClazz) throws InstantiationException, IllegalAccessException {
			super(ins, actClazz);
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getButton()==instrument.labelsYInvertedCB || interaction.getButton()==instrument.labelsXInvertedCB;
		}

		@Override
		public void initAction() {
			if(interaction.getButton()==instrument.labelsYInvertedCB)
				action.setProperty(ShapeProperties.GRID_LABEL_POSITION_Y);
			else
				action.setProperty(ShapeProperties.GRID_LABEL_POSITION_X);
		}

		@Override
		public void updateAction() {
			action.setValue(!interaction.getButton().isSelected());
		}
	}


	/** The link that maps a checkbox to an action that modifies the selection. */
	private static class CheckBox2ModifySelectionGrid extends CheckBox4ShapeGridCust<ModifyShapeProperty> {
		protected CheckBox2ModifySelectionGrid(final ShapeGridCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup(instrument.pencil.drawing.getSelection().duplicate());
		}

		@Override
		public boolean isConditionRespected() {
			return super.isConditionRespected() && instrument.hand.isActivated();
		}
	}


	/** The link that maps a checkbox to an action that modifies the pencil. */
	private static class CheckBox2ModifyPencilGrid extends CheckBox4ShapeGridCust<ModifyPencilParameter> {
		protected CheckBox2ModifyPencilGrid(final ShapeGridCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setPencil(instrument.pencil);
		}

		@Override
		public boolean isConditionRespected() {
			return super.isConditionRespected() && instrument.pencil.isActivated();
		}
	}



	private static class Spinner2ModifySelectionGridCoords extends Spinner2ModifyGridCoords<ModifyShapeProperty> {
		protected Spinner2ModifySelectionGridCoords(final ShapeGridCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup(instrument.pencil.drawing.getSelection().duplicate());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated() && super.isConditionRespected();
		}
	}



	private static class Spinner2ModifyPencilGridCoords extends Spinner2ModifyGridCoords<ModifyPencilParameter> {
		protected Spinner2ModifyPencilGridCoords(final ShapeGridCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class);
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


	private static abstract class Spinner2ModifyGridCoords<A extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapeGridCustomiser> {
		protected Spinner2ModifyGridCoords(final ShapeGridCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(ins, clazzAction);
		}

		@Override
		public void initAction() {
			if(isLabelSizeSpinner())
				action.setProperty(ShapeProperties.GRID_SIZE_LABEL);
			else if(isStartGridSpinner())
				action.setProperty(ShapeProperties.GRID_START);
			else
				action.setProperty(ShapeProperties.GRID_END);
		}

		@Override
		public void updateAction() {
			if(isLabelSizeSpinner())
				action.setValue(Integer.parseInt(instrument.labelsSizeS.getValue().toString()));
			else if(isStartGridSpinner())
				action.setValue(DrawingTK.getFactory().createPoint(
						Double.parseDouble(instrument.xStartS.getValue().toString()), Double.parseDouble(instrument.yStartS.getValue().toString())));
			else
				action.setValue(DrawingTK.getFactory().createPoint(
						Double.parseDouble(instrument.xEndS.getValue().toString()), Double.parseDouble(instrument.yEndS.getValue().toString())));
		}


		private boolean isStartGridSpinner() {
			final Object spinner = interaction.getSpinner();
			return spinner==instrument.xStartS || spinner==instrument.yStartS;
		}

		private boolean isEndGridSpinner() {
			final Object spinner = interaction.getSpinner();
			return spinner==instrument.xEndS || spinner==instrument.yEndS;
		}

		private boolean isLabelSizeSpinner() {
			return interaction.getSpinner()==instrument.labelsSizeS;
		}


		@Override
		public boolean isConditionRespected() {
			return isStartGridSpinner() || isEndGridSpinner() || isLabelSizeSpinner();
		}
	}
}
