package net.sf.latexdraw.glib.handlers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import net.sf.latexdraw.glib.models.interfaces.IPoint;

import org.malai.picking.Pickable;


public interface IHandler extends Pickable {
	/**
	 * @return The opacity of the handler.
	 */
	int getOpacity();


	/**
	 * @param opacity the opacity to set.
	 */
	void setOpacity(final int opacity);

	/**
	 * Updates the handler using the given shape.
	 * @param shape The shape used to updated the handler.
	 * @since 3.0
	 */
	void updateFromShape(final Shape shape);

	/**
	 * Changes the centre of the handler and updates the shape.
	 * @param x The new X coordinate.
	 * @param y The new Y coordinate.
	 */
	void setPoint(final double x, final double y);


	/**
	 * Sets the width of the handler.
	 * @param size Its new width. Must be greater than 0.
	 */
	void setDim(final double size);


	/**
	 * @return The X-coordinate of the handler.
	 */
	double getX();


	/**
	 * @return The centre of the handler.
	 */
	IPoint getCentre();


	/**
	 * @return The Y-coordinate of the handler.
	 */
	double getY();


	/**
	 * paint the handler.
	 */
	void paint(final Graphics2D g);


	/**
	 * Updates the handler.
	 */
	void update();


	/**
	 * @return the size of the handler.
	 */
	double getSize();


	/**
	 * @return The colour of the handler.
	 * @since 3.0
	 */
	Color getColour();
}