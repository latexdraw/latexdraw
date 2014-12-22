package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.glib.models.interfaces.prop.IArrowable;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.views.jfx.ui.JFXWidgetCreator;

/**
 * This instrument customises the arrows of shapes or of the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 08/05/2011<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeArrowCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** Allows to change the style of the left-end of the shape. */
	@FXML protected ComboBox<ImageView> arrowLeftCB;

	/** Allows to change the style of the right-end of the shape. */
	@FXML protected ComboBox<ImageView> arrowRightCB;

	/** The field to set the dot size num parameter of arrows. */
	@FXML protected Spinner<Double> dotSizeNum;

	/** The field to set the dot size dim parameter of arrows. */
	@FXML protected Spinner<Double> dotSizeDim;

	/** The field to set the bracket num parameter of arrows. */
	@FXML protected Spinner<Double> bracketNum;

	/** The field to set the rounded bracket num parameter of arrows. */
	@FXML protected Spinner<Double> rbracketNum;

	/** The field to set the t bar size num parameter of arrows. */
	@FXML protected Spinner<Double> tbarsizeNum;

	/** The field to set the t bar size dim parameter of arrows. */
	@FXML protected Spinner<Double> tbarsizeDim;

	/** The field to set the arrows size dim parameter of arrows. */
	@FXML protected Spinner<Double> arrowSizeDim;

	/** The field to set the arrow size num parameter of arrows. */
	@FXML protected Spinner<Double> arrowSizeNum;

	/** The field to set the arrow length parameter of arrows. */
	@FXML protected Spinner<Double> arrowLength;

	/** The field to set the arrow inset parameter of arrows. */
	@FXML protected Spinner<Double> arrowInset;

	@FXML protected AnchorPane dotPane;
	@FXML protected AnchorPane arrowPane;
	@FXML protected AnchorPane barPane;
	@FXML protected AnchorPane bracketPane;
	@FXML protected AnchorPane rbracketPane;
	@FXML protected TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeArrowCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		arrowLeftCB.getItems().addAll(createItem(ArrowStyle.NONE, "/res/arrowStyles/line.none.left.png"), createItem(ArrowStyle.BAR_END, "/res/arrowStyles/line.barEnd.left.png"),
				createItem(ArrowStyle.BAR_IN, "/res/arrowStyles/line.barIn.left.png"), createItem(ArrowStyle.CIRCLE_END, "/res/arrowStyles/line.circle.end.left.png"),
				createItem(ArrowStyle.CIRCLE_IN, "/res/arrowStyles/line.circle.in.left.png"), createItem(ArrowStyle.DISK_END, "/res/arrowStyles/line.disk.end.left.png"),
				createItem(ArrowStyle.DISK_IN, "/res/arrowStyles/line.disk.in.left.png"), createItem(ArrowStyle.LEFT_ARROW, "/res/arrowStyles/line.arrow.left.png"),
				createItem(ArrowStyle.RIGHT_ARROW, "/res/arrowStyles/line.rarrow.left.png"), createItem(ArrowStyle.LEFT_ROUND_BRACKET, "/res/arrowStyles/line.arc.left.png"),
				createItem(ArrowStyle.RIGHT_ROUND_BRACKET, "/res/arrowStyles/line.arc.r.left.png"), createItem(ArrowStyle.LEFT_SQUARE_BRACKET, "/res/arrowStyles/line.bracket.left.png"),
				createItem(ArrowStyle.RIGHT_SQUARE_BRACKET, "/res/arrowStyles/line.bracket.r.left.png"), createItem(ArrowStyle.LEFT_DBLE_ARROW, "/res/arrowStyles/line.dbleArrow.left.png"),
				createItem(ArrowStyle.RIGHT_DBLE_ARROW, "/res/arrowStyles/line.rdbleArrow.left.png"), createItem(ArrowStyle.ROUND_IN, "/res/arrowStyles/line.roundIn.left.png"));

		arrowRightCB.getItems().addAll(createItem(ArrowStyle.NONE, "/res/arrowStyles/line.none.right.png"), createItem(ArrowStyle.BAR_END, "/res/arrowStyles/line.barEnd.right.png"),
				createItem(ArrowStyle.BAR_IN, "/res/arrowStyles/line.barIn.right.png"), createItem(ArrowStyle.CIRCLE_END, "/res/arrowStyles/line.circle.end.right.png"),
				createItem(ArrowStyle.CIRCLE_IN, "/res/arrowStyles/line.circle.in.right.png"), createItem(ArrowStyle.DISK_END, "/res/arrowStyles/line.disk.end.right.png"),
				createItem(ArrowStyle.DISK_IN, "/res/arrowStyles/line.disk.in.right.png"), createItem(ArrowStyle.RIGHT_ARROW, "/res/arrowStyles/line.arrow.right.png"),
				createItem(ArrowStyle.LEFT_ARROW, "/res/arrowStyles/line.rarrow.right.png"), createItem(ArrowStyle.RIGHT_ROUND_BRACKET, "/res/arrowStyles/line.arc.right.png"),
				createItem(ArrowStyle.LEFT_ROUND_BRACKET, "/res/arrowStyles/line.arc.r.right.png"), createItem(ArrowStyle.RIGHT_SQUARE_BRACKET, "/res/arrowStyles/line.bracket.right.png"),
				createItem(ArrowStyle.LEFT_SQUARE_BRACKET, "/res/arrowStyles/line.bracket.r.right.png"), createItem(ArrowStyle.RIGHT_DBLE_ARROW, "/res/arrowStyles/line.dbleArrow.right.png"),
				createItem(ArrowStyle.LEFT_DBLE_ARROW, "/res/arrowStyles/line.rdbleArrow.right.png"), createItem(ArrowStyle.ROUND_IN, "/res/arrowStyles/line.roundIn.right.png"));
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void initialiseInteractors() {
		// try{
		// addInteractor(new List2PencilArrowStyle(this));
		// addInteractor(new List2ShapeArrowStyle(this));
		// addInteractor(new Spinner2SelectionArrowParam(this));
		// addInteractor(new Spinner2PencilArrowParam(this));
		// }catch(InstantiationException | IllegalAccessException e){
		// BadaboomCollector.INSTANCE.add(e);
		// }
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IArrowable.class)) {
			setActivated(true);
			final IArrow arr1 = shape.getArrowAt(0);
			final IArrow arr2 = shape.getArrowAt(-1);
			final ArrowStyle arrStyle1 = arr1.getArrowStyle();
			final ArrowStyle arrStyle2 = arr2.getArrowStyle();

			arrowLeftCB.getSelectionModel().select(getItem(arrowLeftCB, arrStyle1).orElseThrow(() -> new IllegalArgumentException()));
			arrowRightCB.getSelectionModel().select(getItem(arrowRightCB, arrStyle2).orElseThrow(() -> new IllegalArgumentException()));

			final boolean isArrow = arrStyle1.isArrow()||arrStyle2.isArrow();
			final boolean isDot = arrStyle1.isCircleDisk()||arrStyle2.isCircleDisk();
			final boolean isBar = arrStyle1.isBar()||arrStyle2.isBar();
			final boolean isSBracket = arrStyle1.isSquareBracket()||arrStyle2.isSquareBracket();
			final boolean isRBracket = arrStyle1.isRoundBracket()||arrStyle2.isRoundBracket();

			// Updating the visibility of the widgets.
			arrowPane.setVisible(isArrow);
			dotPane.setVisible(isDot);
			barPane.setVisible(isBar||isSBracket||isRBracket);
			bracketPane.setVisible(isSBracket);
			rbracketPane.setVisible(isRBracket);

			// Updating the value of the widgets.
			if(isArrow) {
				arrowInset.getValueFactory().setValue(arr1.getArrowInset());
				arrowLength.getValueFactory().setValue(arr1.getArrowLength());
				arrowSizeDim.getValueFactory().setValue(arr1.getArrowSizeDim());
				arrowSizeNum.getValueFactory().setValue(arr1.getArrowSizeNum());
			}

			if(isDot) {
				dotSizeNum.getValueFactory().setValue(arr1.getDotSizeNum());
				dotSizeDim.getValueFactory().setValue(arr1.getDotSizeDim());
			}

			if(isBar||isSBracket||isRBracket) {
				tbarsizeDim.getValueFactory().setValue(arr1.getTBarSizeDim());
				tbarsizeNum.getValueFactory().setValue(arr1.getTBarSizeNum());
			}

			if(isSBracket)
				bracketNum.getValueFactory().setValue(arr1.getBracketNum());

			if(isRBracket)
				rbracketNum.getValueFactory().setValue(arr1.getRBracketNum());
		}else
			setActivated(false);
	}
}

//
// abstract class Spinner2ArrowParam<A extends ShapePropertyAction> extends
// SpinnerForCustomiser<A, ShapeArrowCustomiser> {
// protected Spinner2ArrowParam(final ShapeArrowCustomiser ins, final Class<A>
// clazzAction) throws InstantiationException, IllegalAccessException {
// super(ins, clazzAction);
// }
//
// @Override
// public boolean isConditionRespected() {
// final Object obj = interaction.getSpinner();
// return obj==instrument.arrowInset || obj==instrument.arrowLength ||
// obj==instrument.arrowSizeDim || obj==instrument.arrowSizeNum ||
// obj==instrument.bracketNum || obj==instrument.dotSizeDim ||
// obj==instrument.dotSizeNum || obj==instrument.rbracketNum ||
// obj==instrument.tbarsizeDim || obj==instrument.tbarsizeNum;
// }
//
// @Override
// public void initAction() {
// final Object obj = interaction.getSpinner();
// final ShapeProperties prop;
//
// if(obj==instrument.arrowInset) prop = ShapeProperties.ARROW_INSET;
// else if(obj==instrument.arrowLength) prop = ShapeProperties.ARROW_LENGTH;
// else if(obj==instrument.arrowSizeDim) prop = ShapeProperties.ARROW_SIZE_DIM;
// else if(obj==instrument.arrowSizeNum) prop = ShapeProperties.ARROW_SIZE_NUM;
// else if(obj==instrument.bracketNum) prop = ShapeProperties.ARROW_BRACKET_NUM;
// else if(obj==instrument.dotSizeDim) prop =
// ShapeProperties.ARROW_DOT_SIZE_DIM;
// else if(obj==instrument.dotSizeNum) prop =
// ShapeProperties.ARROW_DOT_SIZE_NUM;
// else if(obj==instrument.rbracketNum) prop =
// ShapeProperties.ARROW_R_BRACKET_NUM;
// else if(obj==instrument.tbarsizeDim) prop =
// ShapeProperties.ARROW_T_BAR_SIZE_DIM;
// else prop = ShapeProperties.ARROW_T_BAR_SIZE_NUM;
//
// action.setProperty(prop);
// }
// }
//
//
// /** This link maps spinners to a ModifyShapeProperty action. */
// class Spinner2PencilArrowParam extends
// Spinner2ArrowParam<ModifyPencilParameter> {
// protected Spinner2PencilArrowParam(final ShapeArrowCustomiser ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, ModifyPencilParameter.class);
// }
//
//
// @Override
// public void initAction() {
// super.initAction();
// action.setPencil(instrument.pencil);
// }
// }
//
//
// /** This link maps spinners to a ModifyShapeProperty action. */
// class Spinner2SelectionArrowParam extends
// Spinner2ArrowParam<ModifyShapeProperty> {
// protected Spinner2SelectionArrowParam(final ShapeArrowCustomiser ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, ModifyShapeProperty.class);
// }
//
//
// @Override
// public void initAction() {
// super.initAction();
// action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
// }
// }
//
//
//
// /**
// * This link maps a list to a ModifyPencil action.
// */
// class List2PencilArrowStyle extends ListForCustomiser<ModifyPencilParameter,
// ShapeArrowCustomiser> {
// /**
// * Creates the link.
// * @param instrument The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected List2PencilArrowStyle(final ShapeArrowCustomiser instrument) throws
// InstantiationException, IllegalAccessException {
// super(instrument, ModifyPencilParameter.class);
// }
//
// @Override
// public void initAction() {
// if(getInteraction().getList()==instrument.arrowLeftCB)
// action.setProperty(ShapeProperties.ARROW1_STYLE);
// else
// action.setProperty(ShapeProperties.ARROW2_STYLE);
//
// action.setPencil(instrument.pencil);
// action.setValue(ArrowStyle.getArrowStyle(getLabelText()));
// }
//
// @Override
// public boolean isConditionRespected() {
// final ItemSelectable is = interaction.getList();
// return (is==instrument.arrowLeftCB || is==instrument.arrowRightCB) &&
// instrument.pencil.isActivated();
// }
// }
//
//
// /**
// * This link maps a list to a ModifyShape action.
// */
// class List2ShapeArrowStyle extends ListForCustomiser<ModifyShapeProperty,
// ShapeArrowCustomiser> {
// /**
// * Creates the link.
// * @param instrument The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected List2ShapeArrowStyle(final ShapeArrowCustomiser instrument) throws
// InstantiationException, IllegalAccessException {
// super(instrument, ModifyShapeProperty.class);
// }
//
//
// @Override
// public void initAction() {
// if(getInteraction().getList()==instrument.arrowLeftCB)
// action.setProperty(ShapeProperties.ARROW1_STYLE);
// else
// action.setProperty(ShapeProperties.ARROW2_STYLE);
//
// action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
// action.setValue(ArrowStyle.getArrowStyle(getLabelText()));
// }
//
// @Override
// public boolean isConditionRespected() {
// final ItemSelectable is = interaction.getList();
// return (is==instrument.arrowLeftCB || is==instrument.arrowRightCB) &&
// instrument.hand.isActivated();
// }
// }
