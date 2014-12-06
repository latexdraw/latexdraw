package net.sf.latexdraw.instruments;

import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import com.google.inject.Inject;

/**
 * This meta-instrument manages the instruments that customises shape properties.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 10/31/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class MetaShapeCustomiser extends ShapePropertyCustomiser {
	/** This instrument customises the line properties of shapes and the pencil. */
	protected @Inject ShapeBorderCustomiser borderCustomiser;

	/** This instrument customises the double line properties of shapes and the pencil. */
	protected @Inject ShapeDoubleBorderCustomiser doubleBorderCustomiser;

	/** This instrument customises the shadow properties of shapes and the pencil. */
	protected @Inject ShapeShadowCustomiser shadowCustomiser;

	/** This instrument customises the filling properties of shapes and the pencil. */
	protected @Inject ShapeFillingCustomiser fillingCustomiser;

	/** This instrument customises the texts. */
	protected @Inject ShapeTextCustomiser shapeTextCustomiser;

	/** This instrument customises the rotation angle. */
	protected @Inject ShapeRotationCustomiser rotationCustomiser;

	/** This instrument customises the arrows. */
	protected @Inject ShapeArrowCustomiser arrowCustomiser;

	/** This instrument customises the dot parameters. */
	protected @Inject ShapeDotCustomiser dotCustomiser;

	/** This instrument customises the arc parameters. */
	protected @Inject ShapeArcCustomiser arcCustomiser;

	/** This instrument customises the dimensions and the position. */
	protected @Inject ShapeCoordDimCustomiser dimPosCustomiser;

	/** This instrument customises grids and axes. */
	protected @Inject ShapeStdGridCustomiser gridCustomiser;

	/** This instrument groups shapes. */
	protected @Inject ShapeGrouper shapeGrouper;

	/** This instrument that customises axes. */
	protected @Inject ShapeAxesCustomiser shapeAxesCustomiser;

	/** This instrument that customises grids. */
	protected @Inject ShapeGridCustomiser shapeGridCustomiser;

	/** This instrument that customises freehands. */
	protected @Inject ShapeFreeHandCustomiser shapeFreeHandCustomiser;

	/** This instrument that transforms shapes. */
	protected @Inject ShapeTransformer shapeTransformer;

	/** This instrument that places shapes. */
	protected @Inject ShapePositioner shapePositioner;

	protected @Inject ShapePlotCustomiser plotCustom;


	/**
	 * Creates the instrument.
	 */
	public MetaShapeCustomiser() {
		super();
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);

		final IGroup selection = pencil.getCanvas().getDrawing().getSelection();

		borderCustomiser.setActivated(activated);
		doubleBorderCustomiser.setActivated(activated);
		shadowCustomiser.setActivated(activated);
		fillingCustomiser.setActivated(activated);
		shapeTextCustomiser.setActivated(activated);
		rotationCustomiser.setActivated(activated && hand.isActivated());
		arrowCustomiser.setActivated(activated);
		dotCustomiser.setActivated(activated);
		arcCustomiser.setActivated(activated);
		gridCustomiser.setActivated(activated);
		shapeAxesCustomiser.setActivated(activated);
		shapeGridCustomiser.setActivated(activated);
		plotCustom.setActivated(activated);
		shapeFreeHandCustomiser.setActivated(activated);
		dimPosCustomiser.setActivated(activated && hand.isActivated() && !selection.isEmpty());
		shapeGrouper.setActivated(activated && hand.isActivated() && (selection.size()>1 || selection.getShapeAt(0) instanceof IGroup));
		shapeTransformer.setActivated(activated && hand.isActivated() && !selection.isEmpty());
		shapePositioner.setActivated(activated && hand.isActivated() && !selection.isEmpty());

		if(activated)
			update();
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
//		shapeTransformer.update(shape);
//		shapePositioner.update(shape);
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
	protected void initialiseInteractors() {
		// This instrument does not have any link.
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		// This instrument does not have any widget.
	}
}
