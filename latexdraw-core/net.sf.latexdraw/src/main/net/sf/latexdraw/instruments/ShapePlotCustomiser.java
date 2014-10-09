package net.sf.latexdraw.instruments;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import org.malai.swing.ui.SwingUIComposer;
import org.malai.swing.widget.MSpinner;

/**
 * This instrument modifies plot parameters.<br>
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
 * 2014-10-05<br>
 * @author Arnaud BLOUIN
 * @since 3.1
 */
public class ShapePlotCustomiser extends ShapePropertyCustomiser {
	MSpinner nbPtsSpinner;
	MSpinner minXSpinner;
	MSpinner maxXSpinner;

	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null or if the drawing cannot be accessed from the hand.
	 */
	public ShapePlotCustomiser(final SwingUIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IPlotProp.class)) {
			nbPtsSpinner.setValueSafely(shape.getNbPlottedPoints());
			minXSpinner.setValueSafely(shape.getPlotMinX());
			maxXSpinner.setValueSafely(shape.getPlotMaxX());
			setActivated(true);
		}
		else setActivated(false);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(nbPtsSpinner, visible);
		composer.setWidgetVisible(minXSpinner, visible);
		composer.setWidgetVisible(maxXSpinner, visible);
	}

	@Override
	protected void initialiseWidgets() {
		nbPtsSpinner = new MSpinner(new MSpinner.MSpinnerNumberModel(50, 1, 10000, 10), new JLabel("Plotted points:"));
		nbPtsSpinner.setEditor(new JSpinner.NumberEditor(nbPtsSpinner, "0"));//$NON-NLS-1$
		minXSpinner = new MSpinner(new MSpinner.MSpinnerNumberModel(0.0, -100000.0, 100000.0, 1.0), new JLabel("X-min:"));
		minXSpinner.setEditor(new JSpinner.NumberEditor(minXSpinner, "0.0"));//$NON-NLS-1$
		maxXSpinner = new MSpinner(new MSpinner.MSpinnerNumberModel(10.0, -100000.0, 100000.0, 1.0), new JLabel("X-max:"));
		maxXSpinner.setEditor(new JSpinner.NumberEditor(maxXSpinner, "0.0"));//$NON-NLS-1$
	}

	@Override
	protected void initialiseInteractors() {
		try{
			addInteractor(new Spinner2PencilPlot(this));
			addInteractor(new Spinner2SelectionPlot(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	/** @return The widget that permits to change the number of plotted points.	 */
	public MSpinner getNbPtsSpinner() { return nbPtsSpinner; }

	/** @return The widget that permits to change the X-min of the plot. */
	public MSpinner getMinXSpinner() { return minXSpinner; }

	/** @return The widget that permits to change the X-max of the plot. */
	public MSpinner getMaxXSpinner() { return maxXSpinner; }


	private abstract static class SpinnerForPlotCust<A extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapePlotCustomiser> {
		protected SpinnerForPlotCust(final ShapePlotCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(instrument, clazzAction);
		}

		@Override
		public void initAction() {
			final Object spinner = interaction.getSpinner();
			if(spinner==instrument.nbPtsSpinner)
				action.setProperty(ShapeProperties.PLOT_NB_PTS);
			else if(spinner==instrument.minXSpinner)
				action.setProperty(ShapeProperties.PLOT_MIN_X);
			else if(spinner==instrument.maxXSpinner)
				action.setProperty(ShapeProperties.PLOT_MAX_X);
		}

		@Override
		public boolean isConditionRespected() {
			final Object spinner = interaction.getSpinner();
			return spinner==instrument.nbPtsSpinner || spinner==instrument.maxXSpinner || spinner==instrument.minXSpinner;
		}

		@Override
		public void updateAction() {
			if(interaction.getSpinner()==instrument.nbPtsSpinner)
				action.setValue(Integer.valueOf(interaction.getSpinner().getValue().toString()));
			else
				super.updateAction();
		}
	}


	private static class Spinner2PencilPlot extends SpinnerForPlotCust<ModifyPencilParameter> {
		protected Spinner2PencilPlot(final ShapePlotCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyPencilParameter.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setPencil(instrument.pencil);
		}

		@Override public boolean isConditionRespected() { return instrument.pencil.isActivated() && super.isConditionRespected();}
	}


	private static class Spinner2SelectionPlot extends SpinnerForPlotCust<ModifyShapeProperty> {
		protected Spinner2SelectionPlot(final ShapePlotCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
		}

		@Override public boolean isConditionRespected() { return instrument.hand.isActivated() && super.isConditionRespected(); }
	}
}
