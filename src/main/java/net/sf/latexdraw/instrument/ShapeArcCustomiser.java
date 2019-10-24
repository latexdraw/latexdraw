/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.model.api.property.ArcProp;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;

/**
 * This instrument modifies arc parameters.
 * @author Arnaud BLOUIN
 */
public class ShapeArcCustomiser extends ShapePropertyCustomiser {
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

	@Inject
	public ShapeArcCustomiser(final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing, final EditingService editing) {
		super(hand, pencil, canvas, drawing, editing);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		super.initialize(location, resources);
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void update(final Group shape) {
		if(shape.isTypeOf(ArcProp.class)) {
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
		addSpinnerAnglePropBinding(startAngleS, ShapeProperties.ARC_START_ANGLE);
		addSpinnerAnglePropBinding(endAngleS, ShapeProperties.ARC_END_ANGLE);

		addTogglePropBinding(arcB, ShapeProperties.ARC_STYLE, ArcStyle.ARC);
		addTogglePropBinding(chordB, ShapeProperties.ARC_STYLE, ArcStyle.CHORD);
		addTogglePropBinding(wedgeB, ShapeProperties.ARC_STYLE, ArcStyle.WEDGE);
	}
}
