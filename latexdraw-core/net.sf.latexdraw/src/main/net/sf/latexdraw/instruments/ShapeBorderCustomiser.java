package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.LineStyle;
import net.sf.latexdraw.glib.views.jfx.ui.JFXWidgetCreator;

import org.malai.javafx.instrument.library.CheckboxInteractor;
import org.malai.javafx.instrument.library.ColorPickerInteractor;

/**
 * This instrument modifies border properties of shapes or the pencil.<br>
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
 * 10/31/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeBorderCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** The field which allows to change shapes thickness. */
	@FXML protected Spinner<Double> thicknessField;

	/** Allows to set the colour of the borders of shapes. */
	@FXML protected ColorPicker lineColButton;

	/** Allows to change the style of the borders */
	@FXML protected ComboBox<ImageView> lineCB;

	/** Allows to select the position of the borders of the shape. */
	@FXML protected ComboBox<ImageView> bordersPosCB;

	/** Allows to change the angle of the round corner. */
	@FXML protected Spinner<Double> frameArcField;

	/** Defines if the points of the shape must be painted. */
	@FXML protected CheckBox showPoints;
	
	@FXML protected ImageView thicknessPic;
	@FXML protected ImageView frameArcPic;
	@FXML protected TitledPane linePane;
	

	/**
	 * Creates the instrument.
	 */
	public ShapeBorderCustomiser() {
		super();
	}

	
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		linePane.managedProperty().bind(linePane.visibleProperty());
		
		thicknessPic.visibleProperty().bind(thicknessField.visibleProperty());
		frameArcPic.visibleProperty().bind(frameArcField.visibleProperty());
		
		bordersPosCB.getItems().addAll(
			createItem(BorderPos.INTO, "/res/doubleBoundary/double.boundary.into.png"),
			createItem(BorderPos.OUT, "/res/doubleBoundary/double.boundary.out.png"),
			createItem(BorderPos.MID, "/res/doubleBoundary/double.boundary.middle.png")
		);
		
		lineCB.getItems().addAll(
			createItem(LineStyle.SOLID, "/res/lineStyles/lineStyle.none.png"),
			createItem(LineStyle.DASHED, "/res/lineStyles/lineStyle.dashed.png"),
			createItem(LineStyle.DOTTED, "/res/lineStyles/lineStyle.dotted.png")
		);
	}


	@Override
	protected void update(final IGroup shape) {
		if(shape.isEmpty())
			setActivated(false);
		else {
			setActivated(true);
			final boolean isTh	 	 = shape.isThicknessable();
			final boolean isStylable = shape.isLineStylable();
			final boolean isMvble	 = shape.isBordersMovable();
			final boolean isColor	 = shape.isColourable();
			final boolean supportRound = shape.isTypeOf(ILineArcProp.class);
			final boolean showPts	 = shape.isShowPtsable();

			thicknessField.setVisible(isTh);
			lineCB.setVisible(isStylable);
			bordersPosCB.setVisible(isMvble);
			frameArcField.setVisible(supportRound);
			lineColButton.setVisible(isColor);
			showPoints.setVisible(showPts);

			if(isColor)
				lineColButton.setValue(shape.getLineColour().toJFX());
			if(isTh)
				thicknessField.getValueFactory().setValue(shape.getThickness());
			if(isStylable)
				lineCB.getSelectionModel().select(getItem(lineCB, shape.getLineStyle()).orElseThrow(() -> new IllegalArgumentException()));
			if(isMvble)
				bordersPosCB.getSelectionModel().select(getItem(bordersPosCB, shape.getBordersPosition()).orElseThrow(() -> new IllegalArgumentException()));
			if(supportRound)
				frameArcField.getValueFactory().setValue(shape.getLineArc());
			if(showPts)
				showPoints.setSelected(shape.isShowPts());
		}
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		linePane.setVisible(visible);
	}


	@Override
	protected void initialiseInteractors() {
		try{
//			addInteractor(new Spinner2PencilBorder(this));
//			addInteractor(new List2PencilBorder(this));
//			addInteractor(new List2SelectionBorder(this));
//			addInteractor(new Spinner2SelectionBorder(this));
			addInteractor(new ColourButton2PencilBorder(this));
			addInteractor(new ColourButton2SelectionBorder(this));
			addInteractor(new Checkbox2ShowPointsSelection(this));
			addInteractor(new Checkbox2ShowPointsPencil(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}
	
	
	private static class ColourButton2PencilBorder extends ColorPickerInteractor<ModifyPencilParameter, ShapeBorderCustomiser> {
		ColourButton2PencilBorder(final ShapeBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, Arrays.asList(ins.lineColButton));
		}

		@Override
		public void initAction() {
			action.setValue(ShapeFactory.createColorFX(interaction.getWidget().getValue()));
			action.setProperty(ShapeProperties.COLOUR_LINE);
			action.setPencil(instrument.pencil);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}
	
	
	private static class ColourButton2SelectionBorder extends ColorPickerInteractor<ModifyShapeProperty, ShapeBorderCustomiser> {
		ColourButton2SelectionBorder(final ShapeBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, Arrays.asList(ins.lineColButton));
		}
	
		@Override
		public void initAction() {
			action.setValue(ShapeFactory.createColorFX(interaction.getWidget().getValue()));
			action.setProperty(ShapeProperties.COLOUR_LINE);
			action.setGroup(instrument.pencil.getCanvas().getDrawing().getSelection().duplicateDeep(false));
		}
	
		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}
	
	private class Checkbox2ShowPointsPencil extends CheckboxInteractor<ModifyPencilParameter, ShapeBorderCustomiser> {
		Checkbox2ShowPointsPencil(final ShapeBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, Arrays.asList(ins.showPoints));
		}
	
		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.SHOW_POINTS);
			action.setPencil(instrument.pencil);
			action.setValue(interaction.getWidget().isSelected());
		}
	
		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}
	
	
	private class Checkbox2ShowPointsSelection extends CheckboxInteractor<ModifyShapeProperty, ShapeBorderCustomiser> {
		Checkbox2ShowPointsSelection(final ShapeBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, Arrays.asList(ins.showPoints));
		}
	
		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	
		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.SHOW_POINTS);
			action.setGroup(instrument.pencil.getCanvas().getDrawing().getSelection().duplicateDeep(false));
			action.setValue(interaction.getWidget().isSelected());
		}
	}
}


//class List2SelectionBorder extends ListForCustomiser<ModifyShapeProperty, ShapeBorderCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param instrument The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	protected List2SelectionBorder(final ShapeBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
//		super(instrument, ModifyShapeProperty.class);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final ItemSelectable is = interaction.getList();
//		return (is==instrument.bordersPosCB || is==instrument.lineCB) && instrument.hand.isActivated();
//	}
//
//	@Override
//	public void initAction() {
//		final ItemSelectable is	= interaction.getList();
//		action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//
//		if(is==instrument.bordersPosCB) {
//			action.setProperty(ShapeProperties.BORDER_POS);
//			action.setValue(BorderPos.getStyle(getLabelText()));
//		}else {
//			action.setProperty(ShapeProperties.LINE_STYLE);
//			action.setValue(LineStyle.getStyle(getLabelText()));
//		}
//	}
//}
//
//
//
///**
// * This link maps a list to a ModifyPencil action.
// */
//class List2PencilBorder extends ListForCustomiser<ModifyPencilParameter, ShapeBorderCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param instrument The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	protected List2PencilBorder(final ShapeBorderCustomiser instrument) throws InstantiationException, IllegalAccessException {
//		super(instrument, ModifyPencilParameter.class);
//	}
//
//	@Override
//	public void initAction() {
//		final ItemSelectable is	= interaction.getList();
//		action.setPencil(instrument.pencil);
//
//		if(is==instrument.bordersPosCB) {
//			action.setProperty(ShapeProperties.BORDER_POS);
//			action.setValue(BorderPos.getStyle(getLabelText()));
//		} else {
//			action.setProperty(ShapeProperties.LINE_STYLE);
//			action.setValue(LineStyle.getStyle(getLabelText()));
//		}
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final ItemSelectable is	= interaction.getList();
//		return (is==instrument.bordersPosCB || is==instrument.lineCB) && instrument.pencil.isActivated();
//	}
//}
//
//
///**
// * This link maps a spinner to a ModifyPencil action.
// */
//class Spinner2SelectionBorder extends SpinnerForCustomiser<ModifyShapeProperty, ShapeBorderCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param borderCustom The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	protected Spinner2SelectionBorder(final ShapeBorderCustomiser borderCustom) throws InstantiationException, IllegalAccessException {
//		super(borderCustom, ModifyShapeProperty.class);
//	}
//
//
//	@Override
//	public void initAction() {
//		if(interaction.getSpinner()==instrument.thicknessField)
//			action.setProperty(ShapeProperties.LINE_THICKNESS);
//		else
//			action.setProperty(ShapeProperties.ROUND_CORNER_VALUE);
//
//		action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final JSpinner spinner = interaction.getSpinner();
//		return (spinner==instrument.thicknessField || spinner==instrument.frameArcField) && instrument.hand.isActivated();
//	}
//}
//
//
///**
// * This link maps a spinner to a ModifyPencil action.
// */
//class Spinner2PencilBorder extends SpinnerForCustomiser<ModifyPencilParameter, ShapeBorderCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param borderCustom The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	protected Spinner2PencilBorder(final ShapeBorderCustomiser borderCustom) throws InstantiationException, IllegalAccessException {
//		super(borderCustom, ModifyPencilParameter.class);
//	}
//
//
//	@Override
//	public void initAction() {
//		if(interaction.getSpinner()==instrument.thicknessField)
//			action.setProperty(ShapeProperties.LINE_THICKNESS);
//		else
//			action.setProperty(ShapeProperties.ROUND_CORNER_VALUE);
//
//		action.setPencil(instrument.pencil);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final JSpinner spinner = interaction.getSpinner();
//		return (spinner==instrument.thicknessField || spinner==instrument.frameArcField) && instrument.pencil.isActivated();
//	}
//}
