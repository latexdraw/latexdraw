package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.interfaces.prop.IDotProp;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.view.jfx.JFXWidgetCreator;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This instrument modifies dot parameters.<br>
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
		mainPane.managedProperty().bind(mainPane.visibleProperty());

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
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
		addInteractor(new Spinner4Pencil(this, dotSizeField, ShapeProperties.DOT_SIZE, false));
		addInteractor(new Spinner4Selection(this, dotSizeField, ShapeProperties.DOT_SIZE, false));
		addInteractor(new List4Pencil(this, dotCB, ShapeProperties.DOT_STYLE));
		addInteractor(new List4Selection(this, dotCB, ShapeProperties.DOT_STYLE));
		addInteractor(new ColourPicker4Selection(this, fillingB, ShapeProperties.DOT_FILLING_COL));
		addInteractor(new ColourPicker4Pencil(this, fillingB, ShapeProperties.DOT_FILLING_COL));
	}
}
