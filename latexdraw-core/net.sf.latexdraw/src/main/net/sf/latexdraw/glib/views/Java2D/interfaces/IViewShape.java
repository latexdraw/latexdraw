package net.sf.latexdraw.glib.views.Java2D.interfaces;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.views.IAbstractView;

import org.malai.picking.Pickable;

/**
 * This interface defines a view of a shape.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 11/14/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public interface IViewShape extends IAbstractView, Pickable {
	/**
	 * Draws the shape within a Java2D graphics.
	 * @param g The graphics where the shape must be drawn.
	 * @throw NullPointerException If the given Graphics2D is null.
	 * @since 3.0
	 */
	void paint(final Graphics2D g);


	/**
	 * Draws the borders of the shape within a Java2D graphics.
	 * @param g The graphics where the shape must be drawn.
	 * @throw NullPointerException If the given Graphics2D is null.
	 * @since 3.0
	 */
	void paintBorders(final Graphics2D g);


	/**
	 * Draws the shadow of the shape within a Java2D graphics.
	 * @param g The graphics where the shape must be drawn.
	 * @throw NullPointerException If the given Graphics2D is null.
	 * @since 3.0
	 */
	void paintShadow(final Graphics2D g);


	/**
	 * Paints the lines of the option 'show points'.
	 * @param g The graphics.
	 * @throw NullPointerException If the given Graphics2D is null.
	 * @since 3.0
	 */
	void paintShowPointsLines(final Graphics2D g);


	/**
	 * Paints the dots of the option 'show points'.
	 * @param g The graphics.
	 * @throw NullPointerException If the given Graphics2D is null.
	 * @since 3.0
	 */
	void paintShowPointsDots(final Graphics2D g);


	/**
	 * Fills the shape within a Java2D graphics.
	 * @param g The graphics where the shape must be drawn.
	 * @throw NullPointerException If the given Graphics2D is null.
	 * @since 3.0
	 */
	void paintFilling(final Graphics2D g);


	/**
	 * Allows to know if the point pt is in (or not) the shape.
	 * @param pt The point.
	 * @return True if the point is in the shape.
	 */
	boolean contains(final IPoint pt);


	@Override
	boolean contains(final double x, final double y);


	/**
	 * @param rec The rectangle used to check the intersection.
	 * @return True if the given rectangle intersects the view.
	 * @since 3.0
	 */
	boolean intersects(final Rectangle2D rec);


	/**
	 * This function creates the corresponding rotated Java shape using the shape model.
	 * @return The rotated shape.
	 * @since 3.0
	 */
	Shape getRotatedShape2D();


	/**
	 * @return The border of the view.
	 * @since 3.0
	 */
	Rectangle2D getBorder();


	/**
	 * @return The Java2D path of the view.
	 * @since 3.0
	 */
	Path2D getPath();


	/**
	 * Updates the path of the shape.
	 * @since 3.0
	 */
	void updatePath();


	/**
	 * Update the border of the shape view.
	 * @since 3.0
	 */
	void updateBorder();


	/**
	 * Creates and returns a stroke corresponding to the current parameters of the shape model.
	 * @return The stroke based on the thickness, the double borders and so on, of the shape. Returns null
	 * if the line style is NONE.
	 * @since 3.0
	 */
	BasicStroke getStroke();


	/**
	 * Removes and flushes the used resources and temporary documents.
	 * @since 3.0
	 */
	void flush();
}
