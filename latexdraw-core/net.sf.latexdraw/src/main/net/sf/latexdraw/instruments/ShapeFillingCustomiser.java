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
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.FillingStyle;
import net.sf.latexdraw.glib.views.jfx.ui.JFXUtil;

import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument modifies filling properties of shapes or the pencil.<br>
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
 * 11/11/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeFillingCustomiser extends JfxInstrument implements Initializable {// extends ShapePropertyCustomiser {
	/** Sets the colour of the interior of a shape. */
	@FXML protected ColorPicker fillColButton;

	/** Sets the colour of the hatchings. */
	@FXML protected ColorPicker hatchColButton;

	/** Changes the first colour of a gradient. */
	@FXML protected ColorPicker gradStartColButton;

	/** Changes the second colour of a gradient. */
	@FXML protected ColorPicker gradEndColButton;

	/** Changes the style of filling. */
	@FXML protected ComboBox<ImageView> fillStyleCB;

	/** Changes the mid point of the gradient. */
	@FXML protected Spinner<Double> gradMidPtField;

	/** Changes the angle of the gradient. */
	@FXML protected Spinner<Double> gradAngleField;

	/** Changes the separation of the hatchings. */
	@FXML protected Spinner<Double> hatchSepField;

	/** Changes the angle of the hatchings. */
	@FXML protected Spinner<Double> hatchAngleField;

	/** Changes the width of the hatchings. */
	@FXML protected Spinner<Double> hatchWidthField;
	
	@FXML protected AnchorPane fillPane;
	@FXML protected AnchorPane hatchingsPane;
	@FXML protected AnchorPane gradientPane;
	@FXML protected TitledPane mainPane;


	/**
	 * Creates the instrument.
	 */
	public ShapeFillingCustomiser() {
		super();
	}


	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		fillStyleCB.getItems().addAll(
				JFXUtil.INSTANCE.createItem(FillingStyle.NONE, "/res/hatch/hatch.none.png"),
				JFXUtil.INSTANCE.createItem(FillingStyle.PLAIN, "/res/hatch/hatch.solid.png"),
				JFXUtil.INSTANCE.createItem(FillingStyle.CLINES, "/res/hatch/hatch.cross.png"),
				JFXUtil.INSTANCE.createItem(FillingStyle.CLINES_PLAIN, "/res/hatch/hatchf.cross.png"),
				JFXUtil.INSTANCE.createItem(FillingStyle.HLINES, "/res/hatch/hatch.horiz.png"),
				JFXUtil.INSTANCE.createItem(FillingStyle.HLINES_PLAIN, "/res/hatch/hatchf.horiz.png"),
				JFXUtil.INSTANCE.createItem(FillingStyle.VLINES, "/res/hatch/hatch.vert.png"),
				JFXUtil.INSTANCE.createItem(FillingStyle.VLINES_PLAIN, "/res/hatch/hatchf.vert.png"),
				JFXUtil.INSTANCE.createItem(FillingStyle.GRAD, "/res/hatch/gradient.png")
		);
	}


//	@Override
	protected void update(final IGroup shape) {
		if(shape.isInteriorStylable()) {
			final FillingStyle style	= shape.getFillingStyle();
			final boolean isFillable	= style.isFilled();
			final boolean hatchings		= style.isHatchings();
			final boolean gradient		= style.isGradient();

			// Updating the visibility of the widgets.
			gradientPane.setVisible(gradient);
			hatchingsPane.setVisible(hatchings);
			fillPane.setVisible(isFillable);

			fillStyleCB.getSelectionModel().select(JFXUtil.INSTANCE.getItem(fillStyleCB, style).orElseThrow(() -> new IllegalArgumentException()));
			if(isFillable)
				fillColButton.setValue(shape.getFillingCol().toJFX());
			if(hatchings) {
				hatchColButton.setValue(shape.getHatchingsCol().toJFX());
				hatchAngleField.getValueFactory().setValue(Math.toDegrees(shape.getHatchingsAngle()));
				hatchSepField.getValueFactory().setValue(shape.getHatchingsSep());
				hatchWidthField.getValueFactory().setValue(shape.getHatchingsWidth());
			}
			else if(gradient){
				gradStartColButton.setValue(shape.getGradColStart().toJFX());
				gradEndColButton.setValue(shape.getGradColEnd().toJFX());
				gradAngleField.getValueFactory().setValue(Math.toDegrees(shape.getGradAngle()));
				gradMidPtField.getValueFactory().setValue(shape.getGradMidPt());
			}
		}
		else setActivated(false);
	}


//	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}


	@Override
	protected void initialiseInteractors() {
//		try{
//			addInteractor(new List2PencilFilling(this));
//			addInteractor(new List2SelectionFilling(this));
//			addInteractor(new ColourButton2PencilFilling(this));
//			addInteractor(new ColourButton2SelectionFilling(this));
//			addInteractor(new Spinner2PencilFilling(this));
//			addInteractor(new Spinner2SelectionFilling(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}
}


/**
// * This link maps a list to a ModifyPencil action.
// */
//class List2PencilFilling extends ListForCustomiser<ModifyPencilParameter, ShapeFillingCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param instrument The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	protected List2PencilFilling(final ShapeFillingCustomiser instrument) throws InstantiationException, IllegalAccessException {
//		super(instrument, ModifyPencilParameter.class);
//	}
//
//	@Override
//	public void initAction() {
//		action.setPencil(instrument.pencil);
//		action.setProperty(ShapeProperties.FILLING_STYLE);
//		action.setValue(FillingStyle.getStyle(getLabelText()));
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final ItemSelectable is = interaction.getList();
//
//		return is==instrument.fillStyleCB && instrument.pencil.isActivated();
//	}
//}
//
//
///**
// * This link maps a list to a ModifyShape action.
// */
//class List2SelectionFilling extends ListForCustomiser<ModifyShapeProperty, ShapeFillingCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param instrument The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	protected List2SelectionFilling(final ShapeFillingCustomiser instrument) throws InstantiationException, IllegalAccessException {
//		super(instrument, ModifyShapeProperty.class);
//	}
//
//	@Override
//	public void initAction() {
//		action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		action.setProperty(ShapeProperties.FILLING_STYLE);
//		action.setValue(FillingStyle.getStyle(getLabelText()));
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final ItemSelectable is	= interaction.getList();
//
//		return is==instrument.fillStyleCB && instrument.hand.isActivated();
//	}
//}
//
//
///**
// * This link maps a colour button to the pencil.
// */
//class ColourButton2PencilFilling extends ColourButtonForCustomiser<ModifyPencilParameter, ShapeFillingCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param instrument The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	protected ColourButton2PencilFilling(final ShapeFillingCustomiser instrument) throws InstantiationException, IllegalAccessException {
//		super(instrument, ModifyPencilParameter.class);
//	}
//
//	@Override
//	public void initAction() {
//		super.initAction();
//
//		final AbstractButton but = interaction.getButton();
//		action.setPencil(instrument.pencil);
//
//		if(but==instrument.fillColButton)
//			action.setProperty(ShapeProperties.COLOUR_FILLING);
//		else if(but==instrument.gradEndColButton)
//			action.setProperty(ShapeProperties.COLOUR_GRADIENT_END);
//		else if(but==instrument.gradStartColButton)
//			action.setProperty(ShapeProperties.COLOUR_GRADIENT_START);
//		else if(but==instrument.hatchColButton)
//			action.setProperty(ShapeProperties.COLOUR_HATCHINGS);
//		else action = null;
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final AbstractButton but = interaction.getButton();
//		return (but==instrument.fillColButton || but==instrument.gradEndColButton || but==instrument.gradStartColButton ||
//				but==instrument.hatchColButton) && instrument.pencil.isActivated();
//	}
//}
//
//
///**
// * This link maps a colour button to the pencil.
// */
//class ColourButton2SelectionFilling extends ColourButtonForCustomiser<ModifyShapeProperty, ShapeFillingCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param instrument The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	protected ColourButton2SelectionFilling(final ShapeFillingCustomiser instrument) throws InstantiationException, IllegalAccessException {
//		super(instrument, ModifyShapeProperty.class);
//	}
//
//	@Override
//	public void initAction() {
//		super.initAction();
//
//		final AbstractButton but = interaction.getButton();
//		action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//
//		if(but==instrument.fillColButton)
//			action.setProperty(ShapeProperties.COLOUR_FILLING);
//		else if(but==instrument.gradEndColButton)
//			action.setProperty(ShapeProperties.COLOUR_GRADIENT_END);
//		else if(but==instrument.gradStartColButton)
//			action.setProperty(ShapeProperties.COLOUR_GRADIENT_START);
//		else if(but==instrument.hatchColButton)
//			action.setProperty(ShapeProperties.COLOUR_HATCHINGS);
//		else action = null;
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final AbstractButton but = interaction.getButton();
//		return (but==instrument.fillColButton || but==instrument.gradEndColButton || but==instrument.gradStartColButton ||
//				but==instrument.hatchColButton) && instrument.hand.isActivated();
//	}
//}
//
//
///**
// * This link maps a spinner to a ModifyPencil action.
// */
//class Spinner2SelectionFilling extends SpinnerForCustomiser<ModifyShapeProperty, ShapeFillingCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param ins The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	protected Spinner2SelectionFilling(final ShapeFillingCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, ModifyShapeProperty.class);
//	}
//
//	@Override
//	public void initAction() {
//		action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		setProperty(interaction.getSpinner(), instrument, action);
//	}
//
//
//	@Override
//	public void updateAction() {
//		setValue(interaction.getSpinner(), instrument, action);
//	}
//
//
//	@Override
//	public boolean isConditionRespected() {
//		final JSpinner spinner = getInteraction().getSpinner();
//		return instrument.hand.isActivated() && (spinner==instrument.gradAngleField || spinner==instrument.gradMidPtField ||
//				spinner==instrument.hatchAngleField || spinner==instrument.hatchSepField || spinner==instrument.hatchWidthField);
//	}
//
//	protected static void setProperty(final JSpinner spinner, final ShapeFillingCustomiser sfc, final ShapePropertyAction act) {
//		if(spinner==sfc.gradAngleField)
//			act.setProperty(ShapeProperties.GRAD_ANGLE);
//		else if(spinner==sfc.gradMidPtField)
//			act.setProperty(ShapeProperties.GRAD_MID_POINT);
//		else if(spinner==sfc.hatchAngleField)
//			act.setProperty(ShapeProperties.HATCHINGS_ANGLE);
//		else if(spinner==sfc.hatchSepField)
//			act.setProperty(ShapeProperties.HATCHINGS_SEP);
//		else if(spinner==sfc.hatchWidthField)
//			act.setProperty(ShapeProperties.HATCHINGS_WIDTH);
//		else
//			act.setProperty(null);
//	}
//
//
//	protected static void setValue(final JSpinner spinner, final ShapeFillingCustomiser sfc, final ShapePropertyAction act) {
//		if(spinner==sfc.hatchAngleField || spinner==sfc.gradAngleField)
//			act.setValue(Math.toRadians(Double.valueOf(spinner.getValue().toString())));
//		else
//			act.setValue(Double.valueOf(spinner.getValue().toString()));
//	}
//}
//
//
///**
// * This link maps a spinner to a ModifyPencil action.
// */
//class Spinner2PencilFilling extends SpinnerForCustomiser<ModifyPencilParameter, ShapeFillingCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param ins The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	protected Spinner2PencilFilling(final ShapeFillingCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, ModifyPencilParameter.class);
//	}
//
//	@Override
//	public void initAction() {
//		action.setPencil(instrument.pencil);
//		Spinner2SelectionFilling.setProperty(interaction.getSpinner(), instrument, action);
//	}
//
//	@Override
//	public void updateAction() {
//		Spinner2SelectionFilling.setValue(interaction.getSpinner(), instrument, action);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final JSpinner spinner = interaction.getSpinner();
//		return instrument.pencil.isActivated() && (spinner==instrument.gradAngleField || spinner==instrument.gradMidPtField || spinner==instrument.hatchAngleField ||
//				spinner==instrument.hatchSepField || spinner==instrument.hatchWidthField);
//	}
//}
