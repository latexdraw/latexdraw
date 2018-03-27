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
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IGroup;

/**
 * This instrument modifies arc parameters.
 * @author Arnaud BLOUIN
 */
public class ShapeArcCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** The toggle button that selects the arc style. */
	@FXML private ToggleButton arcB;
	/** The toggle button that selects the wedge style. */
	@FXML private ToggleButton wedgeB;
	/** The toggle button that selects the chord style. */
	@FXML private ToggleButton chordB;
	/** The spinner that sets the start angle. */
	@FXML private Spinner<Double> startAngleS;
	/** The spinner that sets the end angle. */
	@FXML private Spinner<Double> endAngleS;
	@FXML private TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeArcCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IArcProp.class)) {
			final ArcStyle type = shape.getArcStyle();
			arcB.setSelected(type == ArcStyle.ARC);
			wedgeB.setSelected(type == ArcStyle.WEDGE);
			chordB.setSelected(type == ArcStyle.CHORD);
			startAngleS.getValueFactory().setValue(Math.toDegrees(shape.getAngleStart()));
			endAngleS.getValueFactory().setValue(Math.toDegrees(shape.getAngleEnd()));
			setActivated(true);
		}else {
			setActivated(false);
		}
	}

	@Override
	protected void configureBindings() {
		addSpinnerPropBinding(startAngleS, ShapeProperties.ARC_START_ANGLE, true);
		addSpinnerPropBinding(endAngleS, ShapeProperties.ARC_END_ANGLE, true);

		addTogglePropBinding(arcB, ShapeProperties.ARC_STYLE, ArcStyle.ARC);
		addTogglePropBinding(chordB, ShapeProperties.ARC_STYLE, ArcStyle.CHORD);
		addTogglePropBinding(wedgeB, ShapeProperties.ARC_STYLE, ArcStyle.WEDGE);
	}
}
