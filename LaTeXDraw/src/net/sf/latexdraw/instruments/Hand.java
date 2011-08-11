package net.sf.latexdraw.instruments;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.DnD;
import org.malai.mapping.MappingRegistry;

import net.sf.latexdraw.actions.SelectShapes;
import net.sf.latexdraw.actions.TranslateShape;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.ui.ICanvas;
import net.sf.latexdraw.glib.ui.LMagneticGrid;
import net.sf.latexdraw.glib.views.Java2D.IShapeView;

/**
 * This instrument allows to manipulate (e.g. move or select) shapes.<br>
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
 * 05/13/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Hand extends Instrument {
	/** The canvas that contains the shapes to handle. */
	protected ICanvas canvas;

	/** The magnetic grid used to create shapes. */
	protected LMagneticGrid grid;

	/** The zoomer that is used to give the zoom level to compute coordinates of the created shapes. */
	protected Zoomer zoomer;


	/**
	 * Creates the Hand instrument.
	 * @param canvas The canvas that contains the shapes to handle.
	 * @param grid The magnetic grid used to compute points.
	 * @param zoomer The zommer used to computed points.
	 * @throws IllegalArgumentException If on of the given argument is null.
	 * @since 3.0
	 */
	public Hand(final ICanvas canvas, final LMagneticGrid grid, final Zoomer zoomer) {
		super();

		if(canvas==null || grid==null || zoomer==null)
			throw new IllegalArgumentException();

		this.zoomer	= zoomer;
		this.grid	= grid;
		this.canvas = canvas;
		initialiseLinks();
	}



	@Override
	protected void initialiseLinks() {
		try{
			links.add(new DnD2Select(this, true));
			links.add(new DnD2Translate(this, true));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	@Override
	public void interimFeedback() {
		// The rectangle used for the interim feedback of the selection is removed.
		canvas.setTempUserSelectionBorder(null);
	}
}


/**
 * This link allows to translate the selected shapes.
 */
class DnD2Translate extends Link<TranslateShape, DnD, Hand> {
	/**
	 * Initialises the link.
	 * @param hand The hand.
	 * @param exec True: the action is executed at each update of the interaction.
	 */
	public DnD2Translate(final Hand hand, final boolean exec) throws InstantiationException, IllegalAccessException {
		super(hand, exec, TranslateShape.class, DnD.class);
	}

	@Override
	public void initAction() {
		action.setDrawing(MappingRegistry.REGISTRY.getSourceFromTarget(instrument.canvas, IDrawing.class));
	}

	@Override
	public void updateAction() {
		final IPoint startPt	= instrument.grid.getTransformedPointToGrid(instrument.zoomer.getZoomedPoint(interaction.getStartPt()));
		final IPoint endPt		= instrument.grid.getTransformedPointToGrid(instrument.zoomer.getZoomedPoint(interaction.getEndPt()));

		action.setTx(endPt.getX() - startPt.getX());
		action.setTy(endPt.getY() - startPt.getY());
	}

	@Override
	public boolean isConditionRespected() {
		final Object startObject = interaction.getStartObject();
		return (startObject==instrument.canvas || startObject instanceof IShapeView) && interaction.getButton()==MouseEvent.BUTTON3;
	}
}



/**
 * This link allows to select several shapes.
 */
class DnD2Select extends Link<SelectShapes, DnD, Hand> {
	/** The is rectangle is used as interim feedback to show the rectangle made by the user to select some shapes. */
	protected Rectangle2D selectionBorder;

	/**
	 * Initialises the link.
	 * @param hand The hand.
	 * @param exec True: the action is executed at each update of the interaction.
	 */
	public DnD2Select(final Hand hand, final boolean exec) throws InstantiationException, IllegalAccessException {
		super(hand, exec, SelectShapes.class, DnD.class);
		selectionBorder = new Rectangle2D.Double();
	}


	@Override
	public void initAction() {
		action.setDrawing(MappingRegistry.REGISTRY.getSourceFromTarget(instrument.canvas, IDrawing.class));
	}


	@Override
	public void updateAction() {
		Point start			= interaction.getStartPt();
		Point end			= interaction.getEndPt();
		double minX			= Math.min(start.x, end.x);
		double maxX			= Math.max(start.x, end.x);
		double minY			= Math.min(start.y, end.y);
		double maxY			= Math.max(start.y, end.y);
		final double zoom	= instrument.canvas.getZoom();

		// Updating the rectangle used for the interim feedback and for the selection of shapes.
		selectionBorder.setFrame(minX/zoom, minY/zoom, (maxX-minX)/zoom, (maxY-minY)/zoom);
		// Cleaning the selected shapes in the action.
		action.setShape(null);

		for(IShapeView<?> view : instrument.canvas.getViews())
			if(view.intersects(selectionBorder))
				// Taking the shape in function of the view.
				action.addShape(MappingRegistry.REGISTRY.getSourceFromTarget(view, IShape.class));
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getStartObject()==instrument.canvas && interaction.getButton()==MouseEvent.BUTTON1;
	}

	@Override
	public void interimFeedback() {
		instrument.canvas.setTempUserSelectionBorder(selectionBorder);
		instrument.canvas.refresh();
	}
}
