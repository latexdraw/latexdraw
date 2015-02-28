package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp;
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.views.jfx.ui.JFXWidgetCreator;

import org.malai.javafx.instrument.library.ColorPickerInteractor;
import org.malai.javafx.instrument.library.ComboBoxInteractor;
import org.malai.javafx.instrument.library.SpinnerInteractor;

/**
 * This instrument modifies dot parameters.<br>
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
 * 08/10/2011<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeDotCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** Allows to define the size of a dot. */
	@FXML protected Spinner<Double> dotSizeField;

	/** Allows the selection of a dot shape. */
	@FXML protected ComboBox<DotStyle> dotCB;

	/** Changes the colour of the filling of the dot. */
	@FXML protected ColorPicker fillingB;

	@FXML protected TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeDotCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		Map<DotStyle, Image> cache = new HashMap<>();
		cache.put(DotStyle.DOT, new Image("/res/dotStyles/dot.none.png"));
		cache.put(DotStyle.ASTERISK, new Image("/res/dotStyles/dot.asterisk.png"));
		cache.put(DotStyle.BAR, new Image("/res/dotStyles/dot.bar.png"));
		cache.put(DotStyle.DIAMOND, new Image("/res/dotStyles/dot.diamond.png"));
		cache.put(DotStyle.FDIAMOND, new Image("/res/dotStyles/dot.diamondF.png"));
		cache.put(DotStyle.O, new Image("/res/dotStyles/dot.o.png"));
		cache.put(DotStyle.OPLUS, new Image("/res/dotStyles/dot.oplus.png"));
		cache.put(DotStyle.OTIMES, new Image("/res/dotStyles/dot.ocross.png"));
		cache.put(DotStyle.PLUS, new Image("/res/dotStyles/dot.plus.png"));
		cache.put(DotStyle.X, new Image("/res/dotStyles/dot.cross.png"));
		cache.put(DotStyle.TRIANGLE, new Image("/res/dotStyles/dot.triangle.png"));
		cache.put(DotStyle.FTRIANGLE, new Image("/res/dotStyles/dot.triangleF.png"));
		cache.put(DotStyle.PENTAGON, new Image("/res/dotStyles/dot.pentagon.png"));
		cache.put(DotStyle.FPENTAGON, new Image("/res/dotStyles/dot.pentagonF.png"));
		cache.put(DotStyle.SQUARE, new Image("/res/dotStyles/dot.square.png"));
		cache.put(DotStyle.FSQUARE, new Image("/res/dotStyles/dot.squareF.png"));
		initComboBox(dotCB, cache, DotStyle.values());
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IDotProp.class)) {
			setActivated(true);
			dotSizeField.getValueFactory().setValue(shape.getDiametre());
			dotCB.getSelectionModel().select(shape.getDotStyle());
			fillingB.setDisable(!shape.isFillable());

			if(shape.isFillable())
				fillingB.setValue(shape.getDotFillingCol().toJFX());
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
			addInteractor(new Spinner2PencilDotSize(this));
			addInteractor(new Spinner2SelectionDotSize(this));
			addInteractor(new List2PencilDotStyle(this));
			addInteractor(new List2SelectionDotStyle(this));
			addInteractor(new FillingButton2SelectionFilling(this));
			addInteractor(new FillingButton2PencilFilling(this));
		}catch(InstantiationException | IllegalAccessException e) {
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	private static class FillingButton2PencilFilling extends ColorPickerInteractor<ModifyPencilParameter, ShapeDotCustomiser> {
		FillingButton2PencilFilling(final ShapeDotCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, ins.fillingB);
		}

		@Override
		public void initAction() {
			action.setPencil(instrument.pencil);
			action.setProperty(ShapeProperties.DOT_FILLING_COL);
			action.setValue(ShapeFactory.createColorFX(interaction.getWidget().getValue()));
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	private static class FillingButton2SelectionFilling extends ColorPickerInteractor<ModifyShapeProperty, ShapeDotCustomiser> {
		FillingButton2SelectionFilling(final ShapeDotCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, ins.fillingB);
		}

		@Override
		public void initAction() {
			action.setGroup(instrument.pencil.getCanvas().getDrawing().getSelection().duplicateDeep(false));
			action.setProperty(ShapeProperties.DOT_FILLING_COL);
			action.setValue(ShapeFactory.createColorFX(interaction.getWidget().getValue()));
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

	private static class List2PencilDotStyle extends ComboBoxInteractor<ModifyPencilParameter, ShapeDotCustomiser> {
		List2PencilDotStyle(final ShapeDotCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, Arrays.asList(ins.dotCB));
		}

		@Override
		public void initAction() {
			action.setPencil(instrument.pencil);
			action.setProperty(ShapeProperties.DOT_STYLE);
			action.setValue(interaction.getWidget().getSelectionModel().getSelectedItem());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	private static class List2SelectionDotStyle extends ComboBoxInteractor<ModifyShapeProperty, ShapeDotCustomiser> {
		List2SelectionDotStyle(final ShapeDotCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, Arrays.asList(ins.dotCB));
		}

		@Override
		public void initAction() {
			action.setGroup(instrument.pencil.getCanvas().getDrawing().getSelection().duplicateDeep(false));
			action.setProperty(ShapeProperties.DOT_STYLE);
			action.setValue(interaction.getWidget().getSelectionModel().getSelectedItem());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

	private static class Spinner2SelectionDotSize extends SpinnerInteractor<ModifyShapeProperty, ShapeDotCustomiser> {
		Spinner2SelectionDotSize(final ShapeDotCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, Arrays.asList(ins.dotSizeField));
		}

		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.DOT_SIZE);
			action.setGroup(instrument.pencil.getCanvas().getDrawing().getSelection().duplicateDeep(false));
			action.setValue(getInteraction().getWidget().getValue());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

	private static class Spinner2PencilDotSize extends SpinnerInteractor<ModifyPencilParameter, ShapeDotCustomiser> {
		Spinner2PencilDotSize(final ShapeDotCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, Arrays.asList(ins.dotSizeField));
		}

		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.DOT_SIZE);
			action.setPencil(instrument.pencil);
			action.setValue(getInteraction().getWidget().getValue());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}
}
