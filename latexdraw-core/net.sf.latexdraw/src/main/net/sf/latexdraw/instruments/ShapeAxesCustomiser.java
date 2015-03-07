package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.TicksStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import org.malai.javafx.instrument.library.SpinnerInteractor;

/**
 * This instrument modifies axes properties of shapes or the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 * <br>
 * 2012-04-05<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeAxesCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** The widget that permits to select the style of the axes. */
	@FXML protected ComboBox<AxesStyle> shapeAxes;

	/** The widget that permits to select the style of the ticks. */
	@FXML protected ComboBox<TicksStyle> shapeTicks;

	/** The widget that permits to show/hide the ticks of the axes. */
	@FXML protected ComboBox<PlottingStyle> showTicks;

	/** The widget that permits to set the increment of X-labels. */
	@FXML protected Spinner<Double> incrLabelX;

	/** The widget that permits to set the increment of Y-labels. */
	@FXML protected Spinner<Double> incrLabelY;

	/** The widget that permits to set the visibility of the labels. */
	@FXML protected ComboBox<PlottingStyle> showLabels;

	/** The widget that permits to set the visibility of the origin point. */
	@FXML protected CheckBox showOrigin;

	/** The distance between the X-labels. */
	@FXML protected Spinner<Double> distLabelsX;

	/** The distance between the Y-labels. */
	@FXML protected Spinner<Double> distLabelsY;

	@FXML protected AnchorPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeAxesCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		shapeAxes.getItems().addAll(IAxesProp.AxesStyle.values());
		shapeTicks.getItems().addAll(IAxesProp.TicksStyle.values());
		showTicks.getItems().addAll(IAxesProp.PlottingStyle.values());
		showLabels.getItems().addAll(IAxesProp.PlottingStyle.values());
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IAxesProp.class)) {
			shapeAxes.getSelectionModel().select(shape.getAxesStyle());
			shapeTicks.getSelectionModel().select(shape.getTicksStyle());
			showTicks.getSelectionModel().select(shape.getTicksDisplayed());
			incrLabelX.getValueFactory().setValue(shape.getIncrementX());
			incrLabelY.getValueFactory().setValue(shape.getIncrementY());
			showLabels.getSelectionModel().select(shape.getLabelsDisplayed());
			showOrigin.setSelected(shape.isShowOrigin());
			distLabelsX.getValueFactory().setValue(shape.getDistLabelsX());
			distLabelsY.getValueFactory().setValue(shape.getDistLabelsY());
			setActivated(true);
		}else
			setActivated(false);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void initialiseInteractors() {
		try {
			addInteractor(new List4Selection(this, shapeAxes, ShapeProperties.AXES_STYLE));
			addInteractor(new List4Selection(this, showTicks, ShapeProperties.AXES_TICKS_SHOW));
			addInteractor(new List4Selection(this, showLabels, ShapeProperties.AXES_LABELS_SHOW));
			addInteractor(new List4Selection(this, shapeTicks, ShapeProperties.AXES_TICKS_STYLE));
			addInteractor(new List4Pencil(this, shapeAxes, ShapeProperties.AXES_STYLE));
			addInteractor(new List4Pencil(this, showTicks, ShapeProperties.AXES_TICKS_SHOW));
			addInteractor(new List4Pencil(this, showLabels, ShapeProperties.AXES_LABELS_SHOW));
			addInteractor(new List4Pencil(this, shapeTicks, ShapeProperties.AXES_TICKS_STYLE));
			addInteractor(new Spinner2CustomPencilAxes(this));
			addInteractor(new Spinner2CustomSelectedAxes(this));
			addInteractor(new Checkbox4Pencil(this, showOrigin, ShapeProperties.AXES_SHOW_ORIGIN));
			addInteractor(new Checkbox4Selection(this, showOrigin, ShapeProperties.AXES_SHOW_ORIGIN));
		}catch(InstantiationException | IllegalAccessException e) {
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	private abstract static class Spinner2CustomAxes<A extends ShapePropertyAction> extends SpinnerInteractor<A, ShapeAxesCustomiser> {
		Spinner2CustomAxes(final ShapeAxesCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(ins, clazzAction, ins.distLabelsX, ins.distLabelsY, ins.incrLabelX, ins.incrLabelY);
		}

		@Override
		public void initAction() {
			final Spinner<?> spinner = interaction.getWidget();

			if(spinner == instrument.distLabelsX || spinner == instrument.distLabelsY) {
				action.setProperty(ShapeProperties.AXES_LABELS_DIST);
				action.setValue(ShapeFactory.createPoint(instrument.distLabelsX.getValue(), instrument.distLabelsY.getValue()));
			}else {
				action.setProperty(ShapeProperties.AXES_LABELS_INCR);
				action.setValue(ShapeFactory.createPoint(instrument.incrLabelX.getValue(), instrument.incrLabelY.getValue()));
			}
		}
	}

	private static class Spinner2CustomSelectedAxes extends Spinner2CustomAxes<ModifyShapeProperty> {
		Spinner2CustomSelectedAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup(instrument.pencil.getCanvas().getDrawing().getSelection().duplicateDeep(false));
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

	private static class Spinner2CustomPencilAxes extends Spinner2CustomAxes<ModifyPencilParameter> {
		Spinner2CustomPencilAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setPencil(instrument.pencil);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}
}
