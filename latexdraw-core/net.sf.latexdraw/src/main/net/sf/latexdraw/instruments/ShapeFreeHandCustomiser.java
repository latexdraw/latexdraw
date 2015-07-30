package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.glib.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.views.jfx.ui.JFXWidgetCreator;

/**
 * This instrument modifies free hand properties of shapes or the pencil.<br>
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
 * 2012-04-15<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeFreeHandCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** The type of the freehand. */
	@FXML protected ComboBox<IFreeHandProp.FreeHandType> freeHandType;

	/** The gap to consider between the points. */
	@FXML protected Spinner<Integer> gapPoints;

	/** Defines if the shape is open. */
	@FXML protected CheckBox open;

	@FXML protected TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeFreeHandCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		final Map<IFreeHandProp.FreeHandType, Image> cache = new HashMap<>();
		cache.put(IFreeHandProp.FreeHandType.LINES, new Image("/res/freehand/line.png"));
		cache.put(IFreeHandProp.FreeHandType.CURVES, new Image("/res/freehand/curve.png"));
		initComboBox(freeHandType, cache, IFreeHandProp.FreeHandType.values());
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IFreeHandProp.class)) {
			setActivated(true);
			freeHandType.getSelectionModel().select(shape.getType());
			gapPoints.getValueFactory().setValue(shape.getInterval());
			open.setSelected(shape.isOpen());
		}else
			setActivated(false);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
		addInteractor(new List4Pencil(this, freeHandType, ShapeProperties.FREEHAND_STYLE));
		addInteractor(new List4Selection(this, freeHandType, ShapeProperties.FREEHAND_STYLE));
		addInteractor(new Spinner4Pencil(this, gapPoints, ShapeProperties.FREEHAND_INTERVAL, false));
		addInteractor(new Spinner4Selection(this, gapPoints, ShapeProperties.FREEHAND_INTERVAL, false));
		addInteractor(new Checkbox4Pencil(this, open, ShapeProperties.FREEHAND_OPEN));
		addInteractor(new Checkbox4Selection(this, open, ShapeProperties.FREEHAND_OPEN));
	}
}
