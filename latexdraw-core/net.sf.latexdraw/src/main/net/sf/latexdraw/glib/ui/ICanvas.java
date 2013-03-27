package net.sf.latexdraw.glib.ui;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.synchroniser.ViewsSynchroniserHandler;
import net.sf.latexdraw.instruments.Border;

import org.malai.action.ActionHandler;
import org.malai.interaction.Eventable;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;
import org.malai.presentation.ConcretePresentation;
import org.malai.properties.Zoomable;
import org.malai.swing.widget.ScrollableWidget;

/**
 * Defines an interface for a canvas that draw the drawing and manages the selected shapes.<br>
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
 * 05/15/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public interface ICanvas extends Zoomable, ConcretePresentation, ScrollableWidget, Eventable, ViewsSynchroniserHandler, ActionHandler, Picker, Pickable {
	/**
	 * Changes the cursor of the canvas.
	 * @param cursor The new canvas. Cannot be null.
	 * @since 3.0
	 */
	void setCursor(final Cursor cursor);

	/**
	 * @return The model of the canvas.
	 * @since 3.0
	 */
	IDrawing getDrawing();

	/**
	 * Sets the anti aliasing value.
	 * @param antiAliasingValue The new anti-aliasing value.
	 * @since 3.0
	 */
	void setAntiAliasing(final Object antiAliasingValue);

	/**
	 * Sets the rendering value.
	 * @param renderingValue The new rendering value.
	 * @since 3.0
	 */
	void setRendering(final Object renderingValue);

	/**
	 * Sets the colour rendering value.
	 * @param colorRenderingValue The new colour rendering value.
	 * @since 3.0
	 */
	void setColorRendering(final Object colorRenderingValue);

	/**
	 * Sets the alpha interpolation value.
	 * @param alphaInterpolValue The new alpha interpolation value.
	 * @since 3.0
	 */
	void setAlphaInterpolation(final Object alphaInterpolValue);


	/**
	 * Paints the shapes.
	 * @param g The graphics used to paint the shapes.
	 * @since 3.0
	 */
	void paint(final Graphics g);


	/**
	 * Paints the shapes.
	 * @param g The graphics.
	 * @param withZoom True: the zoom will be considered.
	 * @param withGrid True: the grid will be considered.
	 * @throws NullPointerException If g is null.
	 */
	void paintViews(final Graphics2D g, final boolean withZoom, final boolean withGrid);



	/**
	 * Updates the canvas.
	 */
	@Override
	void update();


	/**
	 * Updates the border of the views.
	 * @since 3.0
	 */
	void updateBorder();


	/**
	 * Defines the dimensions of the canvas (needed for the scrollers).
	 */
	void updatePreferredSize();


	/**
	 * @param x The x-coordinate of the point to test.
	 * @param y The y-coordinate of the point to test.
	 * @return The view at the given point or null.
	 * @since 3.0
	 */
	IViewShape getViewAt(final double x, final double y);


	/**
	 * @return The list of views.
	 * @since 3.0
	 */
	List<IViewShape> getViews();



	/**
	 * Sets the temporary view.
	 * @param view The new temporary view.
	 * @since 3.0
	 */
	void setTempView(final IViewShape view);


	/**
	 * @return The temporary view contained by the canvas.
	 * @since 3.0
	 */
	IViewShape getTempView();


	/**
	 * Sets the rectangle corresponding to the rectangle that performs users to select shapes. Can be null.
	 * This rectangle, if not null, is then displayed to provide users with feedback on the selection he is performing.
	 * @param rect The rectangle to display.
	 * @since 3.0
	 */
	void setTempUserSelectionBorder(final Rectangle2D rect);


	/**
	 * Repaints the canvas.
	 * @since 3.0
	 */
	void refresh();


	/**
	 * @return The instrument that managing selected views.
	 * @since 3.0
	 */
	Border getBorderInstrument();

	/**
	 * @return The magnetic grid of the canvas.
	 * @since 3.0
	 */
	LMagneticGrid getMagneticGrid();

	/**
	 * Requests the focus to the canvas.
	 * @since 3.0
	 */
	void requestFocus();
}