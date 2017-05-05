/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.view.jfx.JFXWidgetCreator;

/**
 * This instrument modifies border properties of shapes or the pencil.
 * @author Arnaud BLOUIN
 */
public class ShapeBorderCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** For changing shapes thickness. */
	@FXML private Spinner<Double> thicknessField;
	@FXML private ImageView thicknessPic;
	/** For changing the style of the borders */
	@FXML private ComboBox<LineStyle> lineCB;
	/** For setting the colour of the borders of shapes. */
	@FXML private ColorPicker lineColButton;
	/** For changing the position of the borders of the shape. */
	@FXML private ComboBox<BorderPos> bordersPosCB;
	/** For changing the angle of the round corner. */
	@FXML private Spinner<Double> frameArcField;
	@FXML private ImageView frameArcPic;
	/** For setting whether the points of the shape must be painted. */
	@FXML private CheckBox showPoints;
	@FXML private TitledPane linePane;

	/**
	 * Creates the instrument.
	 */
	ShapeBorderCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		thicknessPic.managedProperty().bind(thicknessPic.visibleProperty());
		thicknessField.managedProperty().bind(thicknessField.visibleProperty());
		lineCB.managedProperty().bind(lineCB.visibleProperty());
		lineColButton.managedProperty().bind(lineColButton.visibleProperty());
		bordersPosCB.managedProperty().bind(bordersPosCB.visibleProperty());
		frameArcField.managedProperty().bind(frameArcField.visibleProperty());
		frameArcPic.managedProperty().bind(frameArcPic.visibleProperty());
		showPoints.managedProperty().bind(showPoints.visibleProperty());

		linePane.managedProperty().bind(linePane.visibleProperty());

		Map<BorderPos, Image> cachePos = new HashMap<>();
		cachePos.put(BorderPos.INTO, new Image("/res/doubleBoundary/double.boundary.into.png"));
		cachePos.put(BorderPos.MID, new Image("/res/doubleBoundary/double.boundary.middle.png"));
		cachePos.put(BorderPos.OUT, new Image("/res/doubleBoundary/double.boundary.out.png"));
		initComboBox(bordersPosCB, cachePos, BorderPos.values());

		Map<LineStyle, Image> cacheStyle = new HashMap<>();
		cacheStyle.put(LineStyle.SOLID, new Image("/res/lineStyles/lineStyle.none.png"));
		cacheStyle.put(LineStyle.DASHED, new Image("/res/lineStyles/lineStyle.dashed.png"));
		cacheStyle.put(LineStyle.DOTTED, new Image("/res/lineStyles/lineStyle.dotted.png"));
		initComboBox(lineCB, cacheStyle, LineStyle.values());

		scrollOnSpinner(frameArcField);
		scrollOnSpinner(thicknessField);
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isEmpty()) {
			setActivated(false);
		}
		else {
			setActivated(true);
			final boolean isTh = shape.isThicknessable();
			final boolean isStylable = shape.isLineStylable();
			final boolean isMvble = shape.isBordersMovable();
			final boolean isColor = shape.isColourable();
			final boolean supportRound = shape.isTypeOf(ILineArcProp.class);
			final boolean showPts = shape.isShowPtsable();

			thicknessField.setVisible(isTh);
			thicknessPic.setVisible(isTh);
			lineCB.setVisible(isStylable);
			lineColButton.setVisible(isColor);
			bordersPosCB.setVisible(isMvble);
			frameArcField.setVisible(supportRound);
			frameArcPic.setVisible(supportRound);
			showPoints.setVisible(showPts);
			linePane.setVisible(isTh || isStylable || isColor || isMvble || supportRound || showPts);

			if(isColor) lineColButton.setValue(shape.getLineColour().toJFX());
			if(isTh) thicknessField.getValueFactory().setValue(shape.getThickness());
			if(isStylable) lineCB.getSelectionModel().select(shape.getLineStyle());
			if(isMvble) bordersPosCB.getSelectionModel().select(shape.getBordersPosition());
			if(supportRound) frameArcField.getValueFactory().setValue(shape.getLineArc());
			if(showPts) showPoints.setSelected(shape.isShowPts());
		}
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		linePane.setVisible(visible);
	}

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		addBinding(new Spinner4Pencil(this, thicknessField, ShapeProperties.LINE_THICKNESS, false));
		addBinding(new Spinner4Selection(this, thicknessField, ShapeProperties.LINE_THICKNESS, false));
		addBinding(new Spinner4Pencil(this, frameArcField, ShapeProperties.ROUND_CORNER_VALUE, false));
		addBinding(new Spinner4Selection(this, frameArcField, ShapeProperties.ROUND_CORNER_VALUE, false));
		addBinding(new List4Pencil(this, bordersPosCB, ShapeProperties.BORDER_POS));
		addBinding(new List4Pencil(this, lineCB, ShapeProperties.LINE_STYLE));
		addBinding(new List4Selection(this, bordersPosCB, ShapeProperties.BORDER_POS));
		addBinding(new List4Selection(this, lineCB, ShapeProperties.LINE_STYLE));
		addBinding(new ColourPicker4Selection(this, lineColButton, ShapeProperties.COLOUR_LINE));
		addBinding(new ColourPicker4Pencil(this, lineColButton, ShapeProperties.COLOUR_LINE));
		addBinding(new Checkbox4Pencil(this, showPoints, ShapeProperties.SHOW_POINTS));
		addBinding(new Checkbox4Selection(this, showPoints, ShapeProperties.SHOW_POINTS));
	}
}
