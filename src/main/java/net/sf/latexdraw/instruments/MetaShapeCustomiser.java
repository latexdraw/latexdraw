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
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.Inject;

/**
 * This meta-instrument manages the instruments that customises shape properties.
 * @author Arnaud BLOUIN
 */
public class MetaShapeCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** This instrument customises the line properties of shapes and the pencil. */
	@Inject protected ShapeBorderCustomiser borderCustomiser;

	/** This instrument customises the double line properties of shapes and the pencil. */
	@Inject protected ShapeDoubleBorderCustomiser doubleBorderCustomiser;

	/** This instrument customises the shadow properties of shapes and the pencil. */
	@Inject protected ShapeShadowCustomiser shadowCustomiser;

	/** This instrument customises the filling properties of shapes and the pencil. */
	@Inject protected ShapeFillingCustomiser fillingCustomiser;

	/** This instrument customises the texts. */
	@Inject protected ShapeTextCustomiser shapeTextCustomiser;

	/** This instrument customises the rotation angle. */
	@Inject protected ShapeRotationCustomiser rotationCustomiser;

	/** This instrument customises the arrows. */
	@Inject protected ShapeArrowCustomiser arrowCustomiser;

	/** This instrument customises the dot parameters. */
	@Inject protected ShapeDotCustomiser dotCustomiser;

	/** This instrument customises the arc parameters. */
	@Inject protected ShapeArcCustomiser arcCustomiser;

	/** This instrument customises the dimensions and the position. */
	@Inject protected ShapeCoordDimCustomiser dimPosCustomiser;

	/** This instrument customises grids and axes. */
	@Inject protected ShapeStdGridCustomiser gridCustomiser;

	/** This instrument groups shapes. */
	@Inject protected ShapeGrouper shapeGrouper;

	/** This instrument that customises axes. */
	@Inject protected ShapeAxesCustomiser shapeAxesCustomiser;

	/** This instrument that customises grids. */
	@Inject protected ShapeGridCustomiser shapeGridCustomiser;

	/** This instrument that customises freehands. */
	@Inject protected ShapeFreeHandCustomiser shapeFreeHandCustomiser;

	/** This instrument that transforms shapes. */
	@Inject protected ShapeTransformer shapeTransformer;

	/** This instrument that places shapes. */
	@Inject protected ShapePositioner shapePositioner;

	@Inject protected ShapePlotCustomiser plotCustom;

	/**
	 * Creates the instrument.
	 */
	public MetaShapeCustomiser() {
		super();
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
	protected void update(final IGroup shape) {
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
		drawing.getSelection().getShapes().addListener((ListChangeListener.Change<? extends IShape> change) -> {
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
