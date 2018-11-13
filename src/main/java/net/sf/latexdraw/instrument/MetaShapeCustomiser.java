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
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import org.jetbrains.annotations.NotNull;

/**
 * This meta-instrument manages the instruments that customises shape properties.
 * Facade pattern for all the instruments that handle shape's properties.
 * @author Arnaud BLOUIN
 */
public class MetaShapeCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** This instrument customises the line properties of shapes and the pencil. */
	protected final @NotNull ShapeBorderCustomiser borderCustomiser;
	/** This instrument customises the double line properties of shapes and the pencil. */
	protected final @NotNull ShapeDoubleBorderCustomiser doubleBorderCustomiser;
	/** This instrument customises the shadow properties of shapes and the pencil. */
	protected final @NotNull ShapeShadowCustomiser shadowCustomiser;
	/** This instrument customises the filling properties of shapes and the pencil. */
	protected final @NotNull ShapeFillingCustomiser fillingCustomiser;
	/** This instrument customises the texts. */
	protected final @NotNull ShapeTextCustomiser shapeTextCustomiser;
	/** This instrument customises the rotation angle. */
	protected final @NotNull ShapeRotationCustomiser rotationCustomiser;
	/** This instrument customises the arrows. */
	protected final @NotNull ShapeArrowCustomiser arrowCustomiser;
	/** This instrument customises the dot parameters. */
	protected final @NotNull ShapeDotCustomiser dotCustomiser;
	/** This instrument customises the arc parameters. */
	protected final @NotNull ShapeArcCustomiser arcCustomiser;
	/** This instrument customises the dimensions and the position. */
	protected final @NotNull ShapeCoordDimCustomiser dimPosCustomiser;
	/** This instrument customises grids and axes. */
	protected final @NotNull ShapeStdGridCustomiser gridCustomiser;
	/** This instrument groups shapes. */
	protected final @NotNull ShapeGrouper shapeGrouper;
	/** This instrument that customises axes. */
	protected final @NotNull ShapeAxesCustomiser shapeAxesCustomiser;
	/** This instrument that customises grids. */
	protected final @NotNull ShapeGridCustomiser shapeGridCustomiser;
	/** This instrument that customises freehands. */
	protected final @NotNull ShapeFreeHandCustomiser shapeFreeHandCustomiser;
	/** This instrument that transforms shapes. */
	protected final @NotNull ShapeTransformer shapeTransformer;
	/** This instrument that places shapes. */
	protected final @NotNull ShapePositioner shapePositioner;
	protected final @NotNull ShapePlotCustomiser plotCustom;

	@Inject
	public MetaShapeCustomiser(final ShapeBorderCustomiser borderCust, final ShapeDoubleBorderCustomiser dbleBordCust, final ShapeShadowCustomiser shadCust,
		final ShapeFillingCustomiser fillCust, final ShapeTextCustomiser txtCust, final ShapeRotationCustomiser rotCust, final ShapeArrowCustomiser arrCust,
		final ShapeDotCustomiser dotCust, final ShapeArcCustomiser arcCust, final ShapeCoordDimCustomiser dimPosCust, final ShapeStdGridCustomiser absGridCust,
		final ShapeGrouper grouper, final ShapeAxesCustomiser axesCust, final ShapeGridCustomiser gridCust, final ShapeFreeHandCustomiser fhCust,
		final ShapeTransformer transformer, final ShapePositioner positioner, final ShapePlotCustomiser plotCust, final Hand hand, final Pencil pencil,
		final Canvas canvas, final Drawing drawing, final EditingService editing) {
		super(hand, pencil, canvas, drawing, editing);
		borderCustomiser = Objects.requireNonNull(borderCust);
		doubleBorderCustomiser = Objects.requireNonNull(dbleBordCust);
		shadowCustomiser = Objects.requireNonNull(shadCust);
		fillingCustomiser = Objects.requireNonNull(fillCust);
		shapeTextCustomiser = Objects.requireNonNull(txtCust);
		rotationCustomiser = Objects.requireNonNull(rotCust);
		arrowCustomiser = Objects.requireNonNull(arrCust);
		dotCustomiser = Objects.requireNonNull(dotCust);
		arcCustomiser = Objects.requireNonNull(arcCust);
		dimPosCustomiser = Objects.requireNonNull(dimPosCust);
		gridCustomiser = Objects.requireNonNull(absGridCust);
		shapeGrouper = Objects.requireNonNull(grouper);
		shapeAxesCustomiser = Objects.requireNonNull(axesCust);
		shapeGridCustomiser = Objects.requireNonNull(gridCust);
		shapeFreeHandCustomiser = Objects.requireNonNull(fhCust);
		shapeTransformer = Objects.requireNonNull(transformer);
		shapePositioner = Objects.requireNonNull(positioner);
		plotCustom = Objects.requireNonNull(plotCust);
	}

	@Override
	public void setActivated(final boolean act) {
		super.setActivated(act);

		if(act) {
			update();
		}else {
			borderCustomiser.setActivated(false);
			doubleBorderCustomiser.setActivated(false);
			shadowCustomiser.setActivated(false);
			fillingCustomiser.setActivated(false);
			shapeTextCustomiser.setActivated(false);
			rotationCustomiser.setActivated(false);
			arrowCustomiser.setActivated(false);
			dotCustomiser.setActivated(false);
			arcCustomiser.setActivated(false);
			gridCustomiser.setActivated(false);
			shapeAxesCustomiser.setActivated(false);
			shapeGridCustomiser.setActivated(false);
			plotCustom.setActivated(false);
			shapeFreeHandCustomiser.setActivated(false);
			dimPosCustomiser.setActivated(false);
			shapeGrouper.setActivated(false);
			shapeTransformer.setActivated(false);
			shapePositioner.setActivated(false);
		}
	}

	@Override
	protected void update(final Group shape) {
		borderCustomiser.update(shape);
		doubleBorderCustomiser.update(shape);
		shadowCustomiser.update(shape);
		fillingCustomiser.update(shape);
		shapeTextCustomiser.update(shape);
		rotationCustomiser.update(shape);
		arrowCustomiser.update(shape);
		dotCustomiser.update(shape);
		arcCustomiser.update(shape);
		dimPosCustomiser.update(shape);
		gridCustomiser.update(shape);
		shapeGrouper.update(shape);
		shapeAxesCustomiser.update(shape);
		shapeGridCustomiser.update(shape);
		shapeFreeHandCustomiser.update(shape);
		shapeTransformer.update(shape);
		shapePositioner.update(shape);
		plotCustom.update(shape);
	}

	@Override
	public void clearEvents() {
		borderCustomiser.clearEvents();
		doubleBorderCustomiser.clearEvents();
		shadowCustomiser.clearEvents();
		fillingCustomiser.clearEvents();
		shapeTextCustomiser.clearEvents();
		rotationCustomiser.clearEvents();
		arrowCustomiser.clearEvents();
		dotCustomiser.clearEvents();
		arcCustomiser.clearEvents();
		dimPosCustomiser.clearEvents();
		gridCustomiser.clearEvents();
		shapeGrouper.clearEvents();
		shapeAxesCustomiser.clearEvents();
		shapeGridCustomiser.clearEvents();
		shapeFreeHandCustomiser.clearEvents();
		shapeTransformer.clearEvents();
		shapePositioner.clearEvents();
		plotCustom.clearEvents();
	}

	@Override
	protected void configureBindings() {
		// This instrument does not have any link.
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		// This instrument does not have any widget.
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		drawing.getSelection().getShapes().addListener((ListChangeListener.Change<? extends Shape> change) -> {
			while(change.next()) {
				if(change.wasRemoved()) {
					if(hand.isActivated()) {
						setActivated(!drawing.getSelection().isEmpty());
					}
				}else {
					setActivated(true);
				}
			}
		});
	}
}
