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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.model.api.property.DotProp;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.view.jfx.JFXWidgetCreator;

/**
 * This instrument modifies dot parameters.
 * @author Arnaud BLOUIN
 */
public class ShapeDotCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** Allows to define the size of a dot. */
	@FXML private Spinner<Double> dotSizeField;
	/** Allows the selection of a dot shape. */
	@FXML private ComboBox<DotStyle> dotCB;
	/** Changes the colour of the filling of the dot. */
	@FXML private ColorPicker fillingB;
	@FXML private TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeDotCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());

		final Map<DotStyle, Image> cache = new EnumMap<>(DotStyle.class);
		cache.put(DotStyle.DOT, new Image("/res/dotStyles/dot.none.png")); //NON-NLS
		cache.put(DotStyle.ASTERISK, new Image("/res/dotStyles/dot.asterisk.png")); //NON-NLS
		cache.put(DotStyle.BAR, new Image("/res/dotStyles/dot.bar.png")); //NON-NLS
		cache.put(DotStyle.DIAMOND, new Image("/res/dotStyles/dot.diamond.png")); //NON-NLS
		cache.put(DotStyle.FDIAMOND, new Image("/res/dotStyles/dot.diamondF.png")); //NON-NLS
		cache.put(DotStyle.O, new Image("/res/dotStyles/dot.o.png")); //NON-NLS
		cache.put(DotStyle.OPLUS, new Image("/res/dotStyles/dot.oplus.png")); //NON-NLS
		cache.put(DotStyle.OTIMES, new Image("/res/dotStyles/dot.ocross.png")); //NON-NLS
		cache.put(DotStyle.PLUS, new Image("/res/dotStyles/dot.plus.png")); //NON-NLS
		cache.put(DotStyle.X, new Image("/res/dotStyles/dot.cross.png")); //NON-NLS
		cache.put(DotStyle.TRIANGLE, new Image("/res/dotStyles/dot.triangle.png")); //NON-NLS
		cache.put(DotStyle.FTRIANGLE, new Image("/res/dotStyles/dot.triangleF.png")); //NON-NLS
		cache.put(DotStyle.PENTAGON, new Image("/res/dotStyles/dot.pentagon.png")); //NON-NLS
		cache.put(DotStyle.FPENTAGON, new Image("/res/dotStyles/dot.pentagonF.png")); //NON-NLS
		cache.put(DotStyle.SQUARE, new Image("/res/dotStyles/dot.square.png")); //NON-NLS
		cache.put(DotStyle.FSQUARE, new Image("/res/dotStyles/dot.squareF.png")); //NON-NLS
		initComboBox(dotCB, cache, DotStyle.values());
	}

	@Override
	protected void update(final Group shape) {
		if(shape.isTypeOf(DotProp.class)) {
			setActivated(true);
			dotSizeField.getValueFactory().setValue(shape.getDiametre());
			dotCB.getSelectionModel().select(shape.getDotStyle());
			fillingB.setDisable(!shape.isFillable());

			if(shape.isFillable()) {
				fillingB.setValue(shape.getDotFillingCol().toJFX());
			}
		}else {
			setActivated(false);
		}
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void configureBindings() {
		addSpinnerPropBinding(dotSizeField, ShapeProperties.DOT_SIZE);
		addComboPropBinding(dotCB, ShapeProperties.DOT_STYLE);
		addColorPropBinding(fillingB, ShapeProperties.DOT_FILLING_COL);
	}
}
