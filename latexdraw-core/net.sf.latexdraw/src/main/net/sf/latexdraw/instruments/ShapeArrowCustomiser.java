package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.glib.models.interfaces.prop.IArrowable;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.views.jfx.ui.JFXWidgetCreator;

/**
 * This instrument customises the arrows of shapes or of the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 * <br>
 * 08/05/2011<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeArrowCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** Allows to change the style of the left-end of the shape. */
	@FXML protected ComboBox<ArrowStyle> arrowLeftCB;

	/** Allows to change the style of the right-end of the shape. */
	@FXML protected ComboBox<ArrowStyle> arrowRightCB;

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
		Map<ArrowStyle, Image> cacheLeft = new HashMap<>();
		cacheLeft.put(ArrowStyle.NONE, new Image("/res/arrowStyles/line.none.left.png"));
		cacheLeft.put(ArrowStyle.BAR_END, new Image("/res/arrowStyles/line.barEnd.left.png"));
		cacheLeft.put(ArrowStyle.BAR_IN, new Image("/res/arrowStyles/line.barIn.left.png"));
		cacheLeft.put(ArrowStyle.CIRCLE_END, new Image("/res/arrowStyles/line.circle.end.left.png"));
		cacheLeft.put(ArrowStyle.CIRCLE_IN, new Image("/res/arrowStyles/line.circle.in.left.png"));
		cacheLeft.put(ArrowStyle.DISK_END, new Image("/res/arrowStyles/line.disk.end.left.png"));
		cacheLeft.put(ArrowStyle.DISK_IN, new Image("/res/arrowStyles/line.disk.in.left.png"));
		cacheLeft.put(ArrowStyle.LEFT_ARROW, new Image("/res/arrowStyles/line.arrow.left.png"));
		cacheLeft.put(ArrowStyle.RIGHT_ARROW, new Image("/res/arrowStyles/line.rarrow.left.png"));
		cacheLeft.put(ArrowStyle.LEFT_ROUND_BRACKET, new Image("/res/arrowStyles/line.arc.left.png"));
		cacheLeft.put(ArrowStyle.RIGHT_ROUND_BRACKET, new Image("/res/arrowStyles/line.arc.r.left.png"));
		cacheLeft.put(ArrowStyle.LEFT_SQUARE_BRACKET, new Image("/res/arrowStyles/line.bracket.left.png"));
		cacheLeft.put(ArrowStyle.RIGHT_SQUARE_BRACKET, new Image("/res/arrowStyles/line.bracket.r.left.png"));
		cacheLeft.put(ArrowStyle.LEFT_DBLE_ARROW, new Image("/res/arrowStyles/line.dbleArrow.left.png"));
		cacheLeft.put(ArrowStyle.RIGHT_DBLE_ARROW, new Image("/res/arrowStyles/line.rdbleArrow.left.png"));
		cacheLeft.put(ArrowStyle.ROUND_IN, new Image("/res/arrowStyles/line.roundIn.left.png"));
		initComboBox(arrowLeftCB, cacheLeft, ArrowStyle.values());

		Map<ArrowStyle, Image> cacheRight = new HashMap<>();
		cacheRight.put(ArrowStyle.NONE, new Image("/res/arrowStyles/line.none.right.png"));
		cacheRight.put(ArrowStyle.BAR_END, new Image("/res/arrowStyles/line.barEnd.right.png"));
		cacheRight.put(ArrowStyle.BAR_IN, new Image("/res/arrowStyles/line.barIn.right.png"));
		cacheRight.put(ArrowStyle.CIRCLE_END, new Image("/res/arrowStyles/line.circle.end.right.png"));
		cacheRight.put(ArrowStyle.CIRCLE_IN, new Image("/res/arrowStyles/line.circle.in.right.png"));
		cacheRight.put(ArrowStyle.DISK_END, new Image("/res/arrowStyles/line.disk.end.right.png"));
		cacheRight.put(ArrowStyle.DISK_IN, new Image("/res/arrowStyles/line.disk.in.right.png"));
		cacheRight.put(ArrowStyle.LEFT_ARROW, new Image("/res/arrowStyles/line.arrow.right.png"));
		cacheRight.put(ArrowStyle.RIGHT_ARROW, new Image("/res/arrowStyles/line.rarrow.right.png"));
		cacheRight.put(ArrowStyle.LEFT_ROUND_BRACKET, new Image("/res/arrowStyles/line.arc.right.png"));
		cacheRight.put(ArrowStyle.RIGHT_ROUND_BRACKET, new Image("/res/arrowStyles/line.arc.r.right.png"));
		cacheRight.put(ArrowStyle.LEFT_SQUARE_BRACKET, new Image("/res/arrowStyles/line.bracket.right.png"));
		cacheRight.put(ArrowStyle.RIGHT_SQUARE_BRACKET, new Image("/res/arrowStyles/line.bracket.r.right.png"));
		cacheRight.put(ArrowStyle.LEFT_DBLE_ARROW, new Image("/res/arrowStyles/line.dbleArrow.right.png"));
		cacheRight.put(ArrowStyle.RIGHT_DBLE_ARROW, new Image("/res/arrowStyles/line.rdbleArrow.right.png"));
		cacheRight.put(ArrowStyle.ROUND_IN, new Image("/res/arrowStyles/line.roundIn.right.png"));
		initComboBox(arrowRightCB, cacheRight, ArrowStyle.values());
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
		addInteractor(new List4Pencil(this, arrowLeftCB, ShapeProperties.ARROW1_STYLE));
		addInteractor(new List4Pencil(this, arrowRightCB, ShapeProperties.ARROW2_STYLE));
		addInteractor(new List4Selection(this, arrowLeftCB, ShapeProperties.ARROW1_STYLE));
		addInteractor(new List4Selection(this, arrowRightCB, ShapeProperties.ARROW2_STYLE));
		addInteractor(new Spinner4Pencil(this, tbarsizeDim, ShapeProperties.ARROW_T_BAR_SIZE_DIM, false));
		addInteractor(new Spinner4Pencil(this, rbracketNum, ShapeProperties.ARROW_R_BRACKET_NUM, false));
		addInteractor(new Spinner4Pencil(this, dotSizeNum, ShapeProperties.ARROW_DOT_SIZE_NUM, false));
		addInteractor(new Spinner4Pencil(this, dotSizeDim, ShapeProperties.ARROW_DOT_SIZE_DIM, false));
		addInteractor(new Spinner4Pencil(this, bracketNum, ShapeProperties.ARROW_BRACKET_NUM, false));
		addInteractor(new Spinner4Pencil(this, arrowSizeNum, ShapeProperties.ARROW_SIZE_NUM, false));
		addInteractor(new Spinner4Pencil(this, arrowSizeDim, ShapeProperties.ARROW_SIZE_DIM, false));
		addInteractor(new Spinner4Pencil(this, arrowInset, ShapeProperties.ARROW_INSET, false));
		addInteractor(new Spinner4Pencil(this, arrowLength, ShapeProperties.ARROW_LENGTH, false));
		addInteractor(new Spinner4Pencil(this, tbarsizeNum, ShapeProperties.ARROW_T_BAR_SIZE_NUM, false));
		addInteractor(new Spinner4Selection(this, tbarsizeDim, ShapeProperties.ARROW_T_BAR_SIZE_DIM, false));
		addInteractor(new Spinner4Selection(this, rbracketNum, ShapeProperties.ARROW_R_BRACKET_NUM, false));
		addInteractor(new Spinner4Selection(this, dotSizeNum, ShapeProperties.ARROW_DOT_SIZE_NUM, false));
		addInteractor(new Spinner4Selection(this, dotSizeDim, ShapeProperties.ARROW_DOT_SIZE_DIM, false));
		addInteractor(new Spinner4Selection(this, bracketNum, ShapeProperties.ARROW_BRACKET_NUM, false));
		addInteractor(new Spinner4Selection(this, arrowSizeNum, ShapeProperties.ARROW_SIZE_NUM, false));
		addInteractor(new Spinner4Selection(this, arrowSizeDim, ShapeProperties.ARROW_SIZE_DIM, false));
		addInteractor(new Spinner4Selection(this, arrowInset, ShapeProperties.ARROW_INSET, false));
		addInteractor(new Spinner4Selection(this, arrowLength, ShapeProperties.ARROW_LENGTH, false));
		addInteractor(new Spinner4Selection(this, tbarsizeNum, ShapeProperties.ARROW_T_BAR_SIZE_NUM, false));
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IArrowable.class)) {
			setActivated(true);
			final IArrow arr1 = shape.getArrowAt(0);
			final IArrow arr2 = shape.getArrowAt(-1);
			final ArrowStyle arrStyle1 = arr1.getArrowStyle();
			final ArrowStyle arrStyle2 = arr2.getArrowStyle();

			arrowLeftCB.getSelectionModel().select(arrStyle1);
			arrowRightCB.getSelectionModel().select(arrStyle2);

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

			if(isSBracket)
				bracketNum.getValueFactory().setValue(arr1.getBracketNum());

			if(isRBracket)
				rbracketNum.getValueFactory().setValue(arr1.getRBracketNum());
		}else
			setActivated(false);
	}
}
