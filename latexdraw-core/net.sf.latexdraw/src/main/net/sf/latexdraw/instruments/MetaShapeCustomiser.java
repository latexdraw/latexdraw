package net.sf.latexdraw.instruments;

import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.malai.interaction.Eventable;
import org.malai.swing.ui.UIComposer;

/**
 * This meta-instrument manages the instruments that customises shape properties.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
	protected ShapeBorderCustomiser borderCustomiser;

	/** This instrument customises the double line properties of shapes and the pencil. */
	protected ShapeDoubleBorderCustomiser doubleBorderCustomiser;

	/** This instrument customises the shadow properties of shapes and the pencil. */
	protected ShapeShadowCustomiser shadowCustomiser;

	/** This instrument customises the filling properties of shapes and the pencil. */
	protected ShapeFillingCustomiser fillingCustomiser;

	/** This instrument customises the texts. */
	protected TextCustomiser textCustomiser;

	/** This instrument customises the rotation angle. */
	protected ShapeRotationCustomiser rotationCustomiser;

	/** This instrument customises the arrows. */
	protected ShapeArrowCustomiser arrowCustomiser;

	/** This instrument customises the dot parameters. */
	protected ShapeDotCustomiser dotCustomiser;

	/** This instrument customises the arc parameters. */
	protected ShapeArcCustomiser arcCustomiser;

	/** This instrument customises the dimensions and the position. */
	protected ShapeCoordDimCustomiser dimPosCustomiser;

	/** This instrument customises grids and axes. */
	protected ShapeStandardGridCustomiser gridCustomiser;

	/** This instrument groups shapes. */
	protected ShapeGrouper shapeGrouper;

	/** This instrument that customises axes. */
	protected ShapeAxesCustomiser shapeAxesCustomiser;

	/** This instrument that customises grids. */
	protected ShapeGridCustomiser shapeGridCustomiser;

	/** This instrument that customises freehands. */
	protected ShapeFreeHandCustomiser shapeFreeHandCustomiser;

	/** This instrument that transforms shapes. */
	protected ShapeTransformer shapeTransformer;

	/** This instrument that places shapes. */
	protected ShapePositioner shapePositioner;



	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param border The instrument Border
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public MetaShapeCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil, final Border border) {
		super(composer, hand, pencil);

		borderCustomiser 		= new ShapeBorderCustomiser(composer, hand, pencil);
		doubleBorderCustomiser	= new ShapeDoubleBorderCustomiser(composer, hand, pencil);
		shadowCustomiser		= new ShapeShadowCustomiser(composer, hand, pencil);
		fillingCustomiser		= new ShapeFillingCustomiser(composer, hand, pencil);
		textCustomiser			= new TextCustomiser(composer, hand, pencil);
		rotationCustomiser		= new ShapeRotationCustomiser(composer, hand, pencil);
		arrowCustomiser			= new ShapeArrowCustomiser(composer, hand, pencil);
		dotCustomiser			= new ShapeDotCustomiser(composer, hand, pencil);
		arcCustomiser			= new ShapeArcCustomiser(composer, hand, pencil);
		dimPosCustomiser		= new ShapeCoordDimCustomiser(composer, hand, pencil);
		gridCustomiser			= new ShapeStandardGridCustomiser(composer, hand, pencil);
		shapeGrouper			= new ShapeGrouper(composer, hand, pencil);
		shapeAxesCustomiser		= new ShapeAxesCustomiser(composer, hand, pencil);
		shapeGridCustomiser		= new ShapeGridCustomiser(composer, hand, pencil);
		shapeFreeHandCustomiser	= new ShapeFreeHandCustomiser(composer, hand, pencil);
		shapeTransformer		= new ShapeTransformer(composer, hand, pencil, border);
		shapePositioner			= new ShapePositioner(composer, hand, pencil);
	}


	@Override
	public void addEventable(final Eventable eventable) {
		borderCustomiser.addEventable(eventable);
		doubleBorderCustomiser.addEventable(eventable);
		shadowCustomiser.addEventable(eventable);
		fillingCustomiser.addEventable(eventable);
		textCustomiser.addEventable(eventable);
		rotationCustomiser.addEventable(eventable);
		arrowCustomiser.addEventable(eventable);
		dotCustomiser.addEventable(eventable);
		arcCustomiser.addEventable(eventable);
		dimPosCustomiser.addEventable(eventable);
		gridCustomiser.addEventable(eventable);
		shapeGrouper.addEventable(eventable);
		shapeAxesCustomiser.addEventable(eventable);
		shapeGridCustomiser.addEventable(eventable);
		shapeFreeHandCustomiser.addEventable(eventable);
		shapeTransformer.addEventable(eventable);
		shapePositioner.addEventable(eventable);
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);

		final IGroup selection = pencil.canvas.getDrawing().getSelection();

		borderCustomiser.setActivated(activated);
		doubleBorderCustomiser.setActivated(activated);
		shadowCustomiser.setActivated(activated);
		fillingCustomiser.setActivated(activated);
		textCustomiser.setActivated(activated);
		rotationCustomiser.setActivated(activated && hand.isActivated());
		arrowCustomiser.setActivated(activated);
		dotCustomiser.setActivated(activated);
		arcCustomiser.setActivated(activated);
		gridCustomiser.setActivated(activated);
		shapeAxesCustomiser.setActivated(activated);
		shapeGridCustomiser.setActivated(activated);
		shapeFreeHandCustomiser.setActivated(activated);
		dimPosCustomiser.setActivated(activated && hand.isActivated() && !selection.isEmpty());
		shapeGrouper.setActivated(activated && hand.isActivated() && (selection.size()>1 || selection.getShapeAt(0) instanceof IGroup));
		shapeTransformer.setActivated(activated && hand.isActivated() && !selection.isEmpty());
		shapePositioner.setActivated(activated && hand.isActivated() && !selection.isEmpty());

		if(activated)
			update();
	}


	@Override
	protected void update(final IShape shape) {
		borderCustomiser.update(shape);
		doubleBorderCustomiser.update(shape);
		shadowCustomiser.update(shape);
		fillingCustomiser.update(shape);
		textCustomiser.update(shape);
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
	}


	@Override
	public void clearEvents() {
		borderCustomiser.clearEvents();
		doubleBorderCustomiser.clearEvents();
		shadowCustomiser.clearEvents();
		fillingCustomiser.clearEvents();
		textCustomiser.clearEvents();
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
	}


	/** @return The instrument that places shapes. */
	public ShapePositioner getShapePositioner() { return shapePositioner; }

	/** @return The instrument that transforms shapes. */
	public ShapeTransformer getShapeTransformer() { return shapeTransformer; }

	/**
	 * @return The instrument that customises freehands.
	 * @since 3.0
	 */
	public ShapeFreeHandCustomiser getFreeHandCustomiser() {
		return shapeFreeHandCustomiser;
	}

	/**
	 * @return The instrument that customises axes.
	 * @since 3.0
	 */
	public ShapeAxesCustomiser getAxesCustomiser() {
		return shapeAxesCustomiser;
	}

	/**
	 * @return The instrument that customises grids.
	 * @since 3.0
	 */
	public ShapeGridCustomiser getGridCustomiser() {
		return shapeGridCustomiser;
	}


	/**
	 * @return The instrument that customises grids and axes.
	 * @since 3.0
	 */
	public ShapeStandardGridCustomiser getStandardGridCustomiser() {
		return gridCustomiser;
	}


	/**
	 * @return The instrument that customises the line properties of shapes.
	 * @since 3.0
	 */
	public ShapeBorderCustomiser getBorderCustomiser() {
		return borderCustomiser;
	}


	/**
	 * @return The instrument that customises the double line properties of shapes.
	 * @since 3.0
	 */
	public ShapeDoubleBorderCustomiser getDoubleBorderCustomiser() {
		return doubleBorderCustomiser;
	}


	/**
	 * @return The instrument that customises the shadow properties of shapes.
	 * @since 3.0
	 */
	public ShapeShadowCustomiser getShadowCustomiser() {
		return shadowCustomiser;
	}


	/**
	 * @return The instrument that customises the filling properties of shapes.
	 * @since 3.0
	 */
	public ShapeFillingCustomiser getFillingCustomiser() {
		return fillingCustomiser;
	}


	/**
	 * @return The instrument that customises the texts.
	 * @since 3.0
	 */
	public TextCustomiser getTextCustomiser() {
		return textCustomiser;
	}


	/**
	 * @return The instrument that customises the rotation angle.
	 * @since 3.0
	 */
	public ShapeRotationCustomiser getRotationCustomiser() {
		return rotationCustomiser;
	}


	/**
	 * @return The instrument that customises the arrows.
	 * @since 3.0
	 */
	public ShapeArrowCustomiser getArrowCustomiser() {
		return arrowCustomiser;
	}


	/**
	 * @return The instrument that customises the dots.
	 * @since 3.0
	 */
	public ShapeDotCustomiser getDotCustomiser() {
		return dotCustomiser;
	}


	/**
	 * @return This instrument customises the arc parameters.
	 * @since 3.0
	 */
	public ShapeArcCustomiser getArcCustomiser() {
		return arcCustomiser;
	}

	/**
	 * @return This instrument customises the dimensions and the position.
	 * @since 3.0
	 */
	public ShapeCoordDimCustomiser getDimPosCustomiser() {
		return dimPosCustomiser;
	}

	/**
	 * @return the shapeGrouper.
	 * @since 3.0
	 */
	public ShapeGrouper getShapeGrouper() {
		return shapeGrouper;
	}


	@Override
	protected void initialiseLinks() {
		// This instrument does not have any link.
	}


	@Override
	protected void initialiseWidgets() {
		// This instrument does not have any widget.
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		// This instrument does not have any widget.
	}
}
