package net.sf.latexdraw.instruments;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import fr.eseo.malai.interaction.Eventable;

/**
 * This meta-instrument manages the instruments that customises shape properties.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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



	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public MetaShapeCustomiser(final Hand hand, final Pencil pencil) {
		super(hand, pencil);

		borderCustomiser 		= new ShapeBorderCustomiser(hand, pencil);
		doubleBorderCustomiser	= new ShapeDoubleBorderCustomiser(hand, pencil);
		shadowCustomiser		= new ShapeShadowCustomiser(hand, pencil);
		fillingCustomiser		= new ShapeFillingCustomiser(hand, pencil);
		textCustomiser			= new TextCustomiser(hand, pencil);
		rotationCustomiser		= new ShapeRotationCustomiser(hand, pencil);
		arrowCustomiser			= new ShapeArrowCustomiser(hand, pencil);
		dotCustomiser			= new ShapeDotCustomiser(hand, pencil);
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
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);

		borderCustomiser.setActivated(activated);
		doubleBorderCustomiser.setActivated(activated);
		shadowCustomiser.setActivated(activated);
		fillingCustomiser.setActivated(activated);
		textCustomiser.setActivated(activated);
		rotationCustomiser.setActivated(activated);
		arrowCustomiser.setActivated(activated);
		dotCustomiser.setActivated(activated);
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
	}


	@Override
	public void update() {
		super.update();

		borderCustomiser.update();
		doubleBorderCustomiser.update();
		shadowCustomiser.update();
		fillingCustomiser.update();
		textCustomiser.update();
		rotationCustomiser.update();
		arrowCustomiser.update();
		dotCustomiser.update();
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


	@Override
	protected void initialiseLinks() {
		// This instrument does not have any link.
	}


	@Override
	protected void initWidgets() {
		// This instrument does not have any widget.
	}
}
