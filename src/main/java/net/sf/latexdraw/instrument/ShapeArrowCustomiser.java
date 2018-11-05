/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.model.api.property.Arrowable;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.view.jfx.JFXWidgetCreator;

/**
 * This instrument customises the arrows of shapes or of the pencil.
 * @author Arnaud BLOUIN
 */
public class ShapeArrowCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** Allows to change the style of the left-end of the shape. */
	@FXML private ComboBox<ArrowStyle> arrowLeftCB;
	/** Allows to change the style of the right-end of the shape. */
	@FXML private ComboBox<ArrowStyle> arrowRightCB;
	/** The field to set the dot size num parameter of arrows. */
	@FXML private Spinner<Double> dotSizeNum;
	/** The field to set the dot size dim parameter of arrows. */
	@FXML private Spinner<Double> dotSizeDim;
	/** The field to set the bracket num parameter of arrows. */
	@FXML private Spinner<Double> bracketNum;
	/** The field to set the rounded bracket num parameter of arrows. */
	@FXML private Spinner<Double> rbracketNum;
	/** The field to set the t bar size num parameter of arrows. */
	@FXML private Spinner<Double> tbarsizeNum;
	/** The field to set the t bar size dim parameter of arrows. */
	@FXML private Spinner<Double> tbarsizeDim;
	/** The field to set the arrows size dim parameter of arrows. */
	@FXML private Spinner<Double> arrowSizeDim;
	/** The field to set the arrow size num parameter of arrows. */
	@FXML private Spinner<Double> arrowSizeNum;
	/** The field to set the arrow length parameter of arrows. */
	@FXML private Spinner<Double> arrowLength;
	/** The field to set the arrow inset parameter of arrows. */
	@FXML private Spinner<Double> arrowInset;
	@FXML private AnchorPane dotPane;
	@FXML private AnchorPane arrowPane;
	@FXML private AnchorPane barPane;
	@FXML private AnchorPane bracketPane;
	@FXML private AnchorPane rbracketPane;
	@FXML private TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeArrowCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
		dotPane.managedProperty().bind(dotPane.visibleProperty());
		arrowPane.managedProperty().bind(arrowPane.visibleProperty());
		barPane.managedProperty().bind(barPane.visibleProperty());
		bracketPane.managedProperty().bind(bracketPane.visibleProperty());
		rbracketPane.managedProperty().bind(rbracketPane.visibleProperty());

		final Map<ArrowStyle, Image> cacheLeft = new EnumMap<>(ArrowStyle.class);
		cacheLeft.put(ArrowStyle.NONE, new Image("/res/arrowStyles/line.none.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.BAR_END, new Image("/res/arrowStyles/line.barEnd.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.BAR_IN, new Image("/res/arrowStyles/line.barIn.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.CIRCLE_END, new Image("/res/arrowStyles/line.circle.end.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.CIRCLE_IN, new Image("/res/arrowStyles/line.circle.in.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.DISK_END, new Image("/res/arrowStyles/line.disk.end.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.DISK_IN, new Image("/res/arrowStyles/line.disk.in.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.LEFT_ARROW, new Image("/res/arrowStyles/line.arrow.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.RIGHT_ARROW, new Image("/res/arrowStyles/line.rarrow.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.LEFT_ROUND_BRACKET, new Image("/res/arrowStyles/line.arc.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.RIGHT_ROUND_BRACKET, new Image("/res/arrowStyles/line.arc.r.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.LEFT_SQUARE_BRACKET, new Image("/res/arrowStyles/line.bracket.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.RIGHT_SQUARE_BRACKET, new Image("/res/arrowStyles/line.bracket.r.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.LEFT_DBLE_ARROW, new Image("/res/arrowStyles/line.dbleArrow.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.RIGHT_DBLE_ARROW, new Image("/res/arrowStyles/line.rdbleArrow.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.ROUND_IN, new Image("/res/arrowStyles/line.roundIn.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.ROUND_END, new Image("/res/arrowStyles/line.roundEnd.left.png")); //NON-NLS
		cacheLeft.put(ArrowStyle.SQUARE_END, new Image("/res/arrowStyles/lineEnd.left.png")); //NON-NLS
		initComboBox(arrowLeftCB, cacheLeft, ArrowStyle.values());

		final Map<ArrowStyle, Image> cacheRight = new EnumMap<>(ArrowStyle.class);
		cacheRight.put(ArrowStyle.NONE, new Image("/res/arrowStyles/line.none.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.BAR_END, new Image("/res/arrowStyles/line.barEnd.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.BAR_IN, new Image("/res/arrowStyles/line.barIn.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.CIRCLE_END, new Image("/res/arrowStyles/line.circle.end.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.CIRCLE_IN, new Image("/res/arrowStyles/line.circle.in.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.DISK_END, new Image("/res/arrowStyles/line.disk.end.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.DISK_IN, new Image("/res/arrowStyles/line.disk.in.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.RIGHT_ARROW, new Image("/res/arrowStyles/line.arrow.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.LEFT_ARROW, new Image("/res/arrowStyles/line.rarrow.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.RIGHT_ROUND_BRACKET, new Image("/res/arrowStyles/line.arc.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.LEFT_ROUND_BRACKET, new Image("/res/arrowStyles/line.arc.r.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.RIGHT_SQUARE_BRACKET, new Image("/res/arrowStyles/line.bracket.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.LEFT_SQUARE_BRACKET, new Image("/res/arrowStyles/line.bracket.r.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.RIGHT_DBLE_ARROW, new Image("/res/arrowStyles/line.dbleArrow.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.LEFT_DBLE_ARROW, new Image("/res/arrowStyles/line.rdbleArrow.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.ROUND_IN, new Image("/res/arrowStyles/line.roundIn.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.ROUND_END, new Image("/res/arrowStyles/line.roundEnd.right.png")); //NON-NLS
		cacheRight.put(ArrowStyle.SQUARE_END, new Image("/res/arrowStyles/lineEnd.right.png")); //NON-NLS
		initComboBox(arrowRightCB, cacheRight, ArrowStyle.values());
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void configureBindings() {
		addComboPropBinding(arrowLeftCB, ShapeProperties.ARROW1_STYLE);
		addComboPropBinding(arrowRightCB, ShapeProperties.ARROW2_STYLE);

		addSpinnerPropBinding(tbarsizeDim, ShapeProperties.ARROW_T_BAR_SIZE_DIM);
		addSpinnerPropBinding(rbracketNum, ShapeProperties.ARROW_R_BRACKET_NUM);
		addSpinnerPropBinding(dotSizeNum, ShapeProperties.ARROW_DOT_SIZE_NUM);
		addSpinnerPropBinding(dotSizeDim, ShapeProperties.ARROW_DOT_SIZE_DIM);
		addSpinnerPropBinding(bracketNum, ShapeProperties.ARROW_BRACKET_NUM);
		addSpinnerPropBinding(arrowSizeNum, ShapeProperties.ARROW_SIZE_NUM);
		addSpinnerPropBinding(arrowSizeDim, ShapeProperties.ARROW_SIZE_DIM);
		addSpinnerPropBinding(arrowInset, ShapeProperties.ARROW_INSET);
		addSpinnerPropBinding(arrowLength, ShapeProperties.ARROW_LENGTH);
		addSpinnerPropBinding(tbarsizeNum, ShapeProperties.ARROW_T_BAR_SIZE_NUM);
	}

	@Override
	protected void update(final Group shape) {
		if(shape.isTypeOf(Arrowable.class)) {
			setActivated(true);
			final Arrow arr1 = shape.getArrowAt(0);
			final Arrow arr2 = shape.getArrowAt(-1);
			final ArrowStyle arrStyle1 = arr1.getArrowStyle();
			final ArrowStyle arrStyle2 = arr2.getArrowStyle();

			arrowLeftCB.setValue(arrStyle1);
			arrowRightCB.setValue(arrStyle2);
			final boolean isArrow = arrStyle1.isArrow() || arrStyle2.isArrow();
			final boolean isDot = arrStyle1.isCircleDisk() || arrStyle2.isCircleDisk();
			final boolean isBar = arrStyle1.isBar() || arrStyle2.isBar();
			final boolean isSBracket = arrStyle1.isSquareBracket() || arrStyle2.isSquareBracket();
			final boolean isRBracket = arrStyle1.isRoundBracket() || arrStyle2.isRoundBracket();

			// Updating the visibility of the widgets.
			arrowPane.setVisible(isArrow);
			dotPane.setVisible(isDot);
			barPane.setVisible(isBar || isSBracket || isRBracket);
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

			if(isBar || isSBracket || isRBracket) {
				tbarsizeDim.getValueFactory().setValue(arr1.getTBarSizeDim());
				tbarsizeNum.getValueFactory().setValue(arr1.getTBarSizeNum());
			}

			if(isSBracket) {
				bracketNum.getValueFactory().setValue(arr1.getBracketNum());
			}

			if(isRBracket) {
				rbracketNum.getValueFactory().setValue(arr1.getRBracketNum());
			}
		}else {
			setActivated(false);
		}
	}
}
