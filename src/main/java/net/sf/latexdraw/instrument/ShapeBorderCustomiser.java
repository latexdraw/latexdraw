/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.model.api.property.ClosableProp;
import net.sf.latexdraw.model.api.property.LineArcProp;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.LineStyle;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.JFXWidgetCreator;

/**
 * This instrument modifies border properties of shapes or the pencil.
 * @author Arnaud BLOUIN
 */
public class ShapeBorderCustomiser extends ShapePropertyCustomiser implements JFXWidgetCreator {
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
	@FXML private CheckBox opened;

	@Inject
	public ShapeBorderCustomiser(final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing, final EditingService editing) {
		super(hand, pencil, canvas, drawing, editing);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		super.initialize(location, resources);
		thicknessPic.managedProperty().bind(thicknessPic.visibleProperty());
		thicknessField.managedProperty().bind(thicknessField.visibleProperty());
		lineCB.managedProperty().bind(lineCB.visibleProperty());
		lineColButton.managedProperty().bind(lineColButton.visibleProperty());
		bordersPosCB.managedProperty().bind(bordersPosCB.visibleProperty());
		frameArcField.managedProperty().bind(frameArcField.visibleProperty());
		frameArcPic.managedProperty().bind(frameArcPic.visibleProperty());
		showPoints.managedProperty().bind(showPoints.visibleProperty());
		opened.managedProperty().bind(opened.visibleProperty());
		linePane.managedProperty().bind(linePane.visibleProperty());

		linePane.visibleProperty().bind(thicknessField.visibleProperty().or(lineCB.visibleProperty()).or(lineColButton.visibleProperty()).
				or(bordersPosCB.visibleProperty()).or(frameArcField.visibleProperty()).or(showPoints.visibleProperty()));

		final Map<BorderPos, Image> cachePos = new EnumMap<>(BorderPos.class);
		cachePos.put(BorderPos.INTO, new Image("/res/doubleBoundary/double.boundary.into.png")); //NON-NLS
		cachePos.put(BorderPos.MID, new Image("/res/doubleBoundary/double.boundary.middle.png")); //NON-NLS
		cachePos.put(BorderPos.OUT, new Image("/res/doubleBoundary/double.boundary.out.png")); //NON-NLS
		initComboBox(bordersPosCB, cachePos, BorderPos.values());

		final Map<LineStyle, Image> cacheStyle = new EnumMap<>(LineStyle.class);
		cacheStyle.put(LineStyle.SOLID, new Image("/res/lineStyles/lineStyle.none.png")); //NON-NLS
		cacheStyle.put(LineStyle.DASHED, new Image("/res/lineStyles/lineStyle.dashed.png")); //NON-NLS
		cacheStyle.put(LineStyle.DOTTED, new Image("/res/lineStyles/lineStyle.dotted.png")); //NON-NLS
		initComboBox(lineCB, cacheStyle, LineStyle.values());
	}

	@Override
	protected void update(final Group shape) {
		if(shape.isEmpty()) {
			setActivated(false);
		}else {
			setActivated(true);
			updateOnActivation(shape);
		}
	}

	private void updateOnActivation(final Group shape) {
		final boolean isTh = shape.isThicknessable();
		final boolean isStylable = shape.isLineStylable();
		final boolean isMvble = shape.isBordersMovable();
		final boolean isColor = shape.isColourable();
		final boolean supportRound = shape.isTypeOf(LineArcProp.class);
		final boolean showPts = shape.isShowPtsable();
		final boolean curve = shape.isTypeOf(ClosableProp.class);

		thicknessField.setVisible(isTh);
		thicknessPic.setVisible(isTh);
		lineCB.setVisible(isStylable);
		lineColButton.setVisible(isColor);
		bordersPosCB.setVisible(isMvble);
		frameArcField.setVisible(supportRound);
		frameArcPic.setVisible(supportRound);
		showPoints.setVisible(showPts);
		opened.setVisible(curve);

		if(isColor) {
			lineColButton.setValue(shape.getLineColour().toJFX());
		}
		if(isTh) {
			thicknessField.getValueFactory().setValue(shape.getThickness());
		}
		if(isStylable) {
			lineCB.getSelectionModel().select(shape.getLineStyle());
		}
		if(isMvble) {
			bordersPosCB.getSelectionModel().select(shape.getBordersPosition());
		}
		if(supportRound) {
			frameArcField.getValueFactory().setValue(shape.getLineArc());
		}
		if(showPts) {
			showPoints.setSelected(shape.isShowPts());
		}
		if(curve) {
			opened.setSelected(shape.isOpened());
		}
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		// Nothing to do.
	}

	@Override
	protected void configureBindings() {
		addSpinnerPropBinding(thicknessField, ShapeProperties.LINE_THICKNESS);
		addSpinnerPropBinding(frameArcField, ShapeProperties.ROUND_CORNER_VALUE);

		addComboPropBinding(bordersPosCB, ShapeProperties.BORDER_POS);
		addComboPropBinding(lineCB, ShapeProperties.LINE_STYLE);

		addColorPropBinding(lineColButton, ShapeProperties.COLOUR_LINE);

		addCheckboxPropBinding(showPoints, ShapeProperties.SHOW_POINTS);

		addCheckboxPropBinding(opened, ShapeProperties.CLOSABLE_CLOSE);
	}
}
