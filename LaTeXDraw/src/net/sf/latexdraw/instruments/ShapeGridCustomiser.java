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
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IStandardGrid;
import net.sf.latexdraw.lang.LangTool;

import org.malai.ui.UIComposer;
import org.malai.widget.MSpinner;

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
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(xStartS, visible);
		composer.setWidgetVisible(yStartS, visible);
		composer.setWidgetVisible(xEndS, visible);
		composer.setWidgetVisible(yEndS, visible);
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
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new Spinner2ModifySelectionGridCoords(this));
			addLink(new Spinner2ModifyPencilGridCoords(this));
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
			if(isStartGridSpinner())
				action.setProperty(ShapeProperties.GRID_START);
			else
				action.setProperty(ShapeProperties.GRID_END);
		}

		@Override
		public void updateAction() {
			IPoint pt;

			if(isStartGridSpinner())
				pt = DrawingTK.getFactory().createPoint(
						Double.parseDouble(instrument.xStartS.getValue().toString()), Double.parseDouble(instrument.yStartS.getValue().toString()));
			else
				pt = DrawingTK.getFactory().createPoint(
						Double.parseDouble(instrument.xEndS.getValue().toString()), Double.parseDouble(instrument.yEndS.getValue().toString()));

			action.setValue(pt);
		}


		private boolean isStartGridSpinner() {
			final Object spinner = interaction.getSpinner();
			return spinner==instrument.xStartS || spinner==instrument.yStartS;
		}

		private boolean isEndGridSpinner() {
			final Object spinner = interaction.getSpinner();
			return spinner==instrument.xEndS || spinner==instrument.yEndS;
		}


		@Override
		public boolean isConditionRespected() {
			return isStartGridSpinner() || isEndGridSpinner();
		}
	}
}
