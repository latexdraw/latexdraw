package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp;
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.views.jfx.ui.JFXWidgetCreator;

/**
 * This instrument modifies dot parameters.<br>
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
 * 08/10/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeDotCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** Allows to define the size of a dot. */
	@FXML protected Spinner<Double> dotSizeField;

	/** Allows the selection of a dot shape. */
	@FXML protected ComboBox<ImageView> dotCB;

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
		dotCB.getItems().addAll(
			createItem(DotStyle.DOT, "/res/dotStyles/dot.none.png"),
			createItem(DotStyle.ASTERISK, "/res/dotStyles/dot.asterisk.png"),
			createItem(DotStyle.BAR, "/res/dotStyles/dot.bar.png"),
			createItem(DotStyle.DIAMOND, "/res/dotStyles/dot.diamond.png"),
			createItem(DotStyle.FDIAMOND, "/res/dotStyles/dot.diamondF.png"),
			createItem(DotStyle.O, "/res/dotStyles/dot.o.png"),
			createItem(DotStyle.OPLUS, "/res/dotStyles/dot.oplus.png"),
			createItem(DotStyle.OTIMES, "/res/dotStyles/dot.ocross.png"),
			createItem(DotStyle.PLUS, "/res/dotStyles/dot.plus.png"),
			createItem(DotStyle.X, "/res/dotStyles/dot.cross.png"),
			createItem(DotStyle.TRIANGLE, "/res/dotStyles/dot.triangle.png"),
			createItem(DotStyle.FTRIANGLE, "/res/dotStyles/dot.triangleF.png"),
			createItem(DotStyle.PENTAGON, "/res/dotStyles/dot.pentagon.png"),
			createItem(DotStyle.FPENTAGON, "/res/dotStyles/dot.pentagonF.png"),
			createItem(DotStyle.SQUARE, "/res/dotStyles/dot.square.png"),
			createItem(DotStyle.FSQUARE, "/res/dotStyles/dot.squareF.png")
		);
	}

	
	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IDotProp.class)) {
			dotSizeField.getValueFactory().setValue(shape.getDiametre());
			dotCB.getSelectionModel().select(getItem(dotCB, shape.getDotStyle()).orElseThrow(() -> new IllegalArgumentException()));
			fillingB.setDisable(shape.isFillable());

			if(shape.isFillable())
				fillingB.setValue(shape.getDotFillingCol().toJFX());
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
//			addInteractor(new Spinner2PencilDotSize(this));
//			addInteractor(new Spinner2SelectionDotSize(this));
//			addInteractor(new List2PencilDotStyle(this));
//			addInteractor(new List2SelectionDotStyle(this));
//			addInteractor(new FillingButton2SelectionFilling(this));
//			addInteractor(new FillingButton2PencilFilling(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}
}


//	private static class FillingButton2PencilFilling extends ColourButtonForCustomiser<ModifyPencilParameter, ShapeDotCustomiser> {
//		protected FillingButton2PencilFilling(final ShapeDotCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setPencil(instrument.pencil);
//			action.setProperty(ShapeProperties.DOT_FILLING_COL);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return interaction.getButton()==instrument.fillingB && instrument.pencil.isActivated();
//		}
//	}
//
//
//	private static class FillingButton2SelectionFilling extends ColourButtonForCustomiser<ModifyShapeProperty, ShapeDotCustomiser> {
//		protected FillingButton2SelectionFilling(final ShapeDotCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//			action.setProperty(ShapeProperties.DOT_FILLING_COL);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return interaction.getButton()==instrument.fillingB && instrument.hand.isActivated();
//		}
//	}


//		private static class List2PencilDotStyle extends ListForCustomiser<ModifyPencilParameter, ShapeDotCustomiser> {
//		protected List2PencilDotStyle(final ShapeDotCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			action.setPencil(instrument.pencil);
//			action.setProperty(ShapeProperties.DOT_STYLE);
//			action.setValue(DotStyle.getStyle(getLabelText()));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			final ItemSelectable is = interaction.getList();
//			return is==instrument.dotCB && instrument.pencil.isActivated();
//		}
//	}
//
//
//	private static class List2SelectionDotStyle extends ListForCustomiser<ModifyShapeProperty, ShapeDotCustomiser> {
//		protected List2SelectionDotStyle(final ShapeDotCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//			action.setProperty(ShapeProperties.DOT_STYLE);
//			action.setValue(DotStyle.getStyle(getLabelText()));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			final ItemSelectable is	= interaction.getList();
//			return is==instrument.dotCB && instrument.hand.isActivated();
//		}
//	}
//
//
//	private static class Spinner2SelectionDotSize extends SpinnerForCustomiser<ModifyShapeProperty, ShapeDotCustomiser> {
//		protected Spinner2SelectionDotSize(final ShapeDotCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			action.setProperty(ShapeProperties.DOT_SIZE);
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return interaction.getSpinner()==instrument.dotSizeField && instrument.hand.isActivated();
//		}
//	}
//
//
//	private static class Spinner2PencilDotSize extends SpinnerForCustomiser<ModifyPencilParameter, ShapeDotCustomiser> {
//		protected Spinner2PencilDotSize(final ShapeDotCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			action.setProperty(ShapeProperties.DOT_SIZE);
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return interaction.getSpinner()==instrument.dotSizeField && instrument.pencil.isActivated();
//		}
//	}
//}
