package net.sf.latexdraw.glib.handlers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

import org.malai.picking.Picker;

/**
 * A handler helps to manipulate and to delimit a shape view.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 08/28/11<br>
 * @author Arnaud BLOUIN<br>
 * @version 3.0<br>
 */
abstract class Handler<T extends Shape> implements IHandler {
	/** The coordinates of the centre of the delimiter. */
	protected IPoint point;

	/** The size of the handler. */
	protected double size;

	/** The opacity of the delimiters. Can be changed. */
	protected int opacity;

	/** The colour of the handler. */
	protected Color colour;

	/** The shape of the handler. */
	protected T shape;


	/**
	 * Creates the handler.
	 */
	public Handler() {
		super();
		opacity	= 100;
		size   	= DEFAULT_SIZE;
		//Mutant6
		//colour 	= new Color(0, 0, 0, opacity);
		colour 	= new Color(255, 255, 255, opacity);
		point  	= DrawingTK.getFactory().createPoint();
	}


	/**
	 * Changes the centre of the handler and updates the shape.
	 * @param x The new X coordinate.
	 * @param y The new Y coordinate.
	 */
	@Override
	public void setPoint(final double x, final double y) {
		if(GLibUtilities.INSTANCE.isValidPoint(x, y)) {
			point.setPoint(x, y);
			updateShape();
		}
	}


	/**
	 * Sets the width of the handler and updates the shape.
	 * @param size Its new width. Must be greater than 0.
	 */
	@Override
	public void setSize(final double size) {
		if(size>0) {
			this.size = size;
			updateShape();
		}
	}


	/**
	 * @return The X-coordinate of the handler.
	 */
	@Override
	public double getX() {
		return point.getX();
	}


	/**
	 * @return The centre of the handler.
	 */
	@Override
	public IPoint getCentre() {
		return point;
	}


	/**
	 * @return The Y-coordinate of the handler.
	 */
	@Override
	public double getY() {
		return point.getY();
	}


	/**
	 * paint the handler.
	 */
	@Override
	public void paint(final Graphics2D g) {
		if(g==null) return ;

		g.setColor(colour);
		g.fill(shape);
	}


	/**
	 * Updates the handler.
	 */
	@Override
	public void update() {
		if(opacity!=colour.getTransparency())
			colour = new Color(colour.getRed(), colour.getGreen(),colour.getBlue(), opacity);

		updateShape();
	}


	/**
	 * Updates the Java2D shape of the handler.
	 * @since 3.0
	 */
	protected abstract void updateShape();


	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append("[centre=").append(point).append(", size=").append(size).append(", colour=").append(colour).append(']');
		return builder.toString();
	}


	/**
	 * @return the size of the handler.
	 */
	@Override
	public double getSize() {
		return size;
	}


	@Override
	public int getOpacity() {
		return opacity;
	}


	@Override
	public void setOpacity(final int opacity) {
		if(opacity>=0 && opacity<=255)
			this.opacity = opacity;
	}


	@Override
	public Color getColour() {
		return colour;
	}


	@Override
	public void updateFromShape(final Shape sh) {
		updateShape();
	}


	@Override
	public boolean contains(final double x, final double y) {
		return shape.contains(x, y);
	}


	@Override
	public Picker getPicker() {
		// TODO Auto-generated method stub
		return null;
	}
}
