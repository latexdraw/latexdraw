package net.sf.latexdraw.instruments;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.actions.InitTextSetter;
import net.sf.latexdraw.actions.SelectShapes;
import net.sf.latexdraw.actions.TranslateShapes;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.ui.ICanvas;
import net.sf.latexdraw.glib.ui.LMagneticGrid;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewText;

import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.DnD;
import org.malai.interaction.library.DoubleClick;
import org.malai.interaction.library.Press;
import org.malai.mapping.MappingRegistry;

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

	/** The instrument used to edit texts. */
	protected TextSetter textSetter;
	
	/** The model of the canvas. Computed attribute. */
	protected IDrawing drawing;


	/**
	 * Creates the Hand instrument.
	 * @param canvas The canvas that contains the shapes to handle.
	 * @param grid The magnetic grid used to compute points.
	 * @param zoomer The zommer used to computed points.
	 * @throws IllegalArgumentException If on of the given argument is null.
	 * @since 3.0
	 */
	public Hand(final ICanvas canvas, final LMagneticGrid grid, final Zoomer zoomer, final TextSetter textSetter) {
		super();

		if(canvas==null || grid==null || zoomer==null || textSetter==null)
			throw new IllegalArgumentException();

		this.textSetter = textSetter;
		this.zoomer		= zoomer;
		this.grid		= grid;
		this.canvas 	= canvas;
		drawing			= MappingRegistry.REGISTRY.getSourceFromTarget(this.canvas, IDrawing.class);
		initialiseLinks();
	}



	@Override
	protected void initialiseLinks() {
		try{
			links.add(new Press2Select(this));
			links.add(new DnD2Select(this, true));
			links.add(new DnD2Translate(this, true));
			links.add(new DoubleClick2InitTextSetter(this));
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



class DoubleClick2InitTextSetter extends Link<InitTextSetter, DoubleClick, Hand> {
	protected DoubleClick2InitTextSetter(final Hand ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, InitTextSetter.class, DoubleClick.class);
	}

	@Override
	public void initAction() {
		final Object o = interaction.getTarget();
		
		if(o instanceof IViewText) {
			final IShape sh = ((IViewText)o).getShape();
			
			if(sh instanceof IText) {
				final IText text		= (IText)sh;
				final IPoint position 	= text.getPosition();
				final double zoom 		= instrument.zoomer.zoomable.getZoom();

				action.setTextShape(text);
				action.setInstrument(instrument.textSetter);
				action.setTextSetter(instrument.textSetter);
				action.setAbsolutePoint(DrawingTK.getFactory().createPoint(position.getX()*zoom, position.getY()*zoom));
				action.setRelativePoint(DrawingTK.getFactory().createPoint(position));
			}
		}
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getTarget() instanceof IViewText;
	}
}



/**
 * This link allows to translate the selected shapes.
 */
class DnD2Translate extends Link<TranslateShapes, DnD, Hand> {
	/**
	 * Initialises the link.
	 * @param hand The hand.
	 * @param exec True: the action is executed at each update of the interaction.
	 */
	public DnD2Translate(final Hand hand, final boolean exec) throws InstantiationException, IllegalAccessException {
		super(hand, exec, TranslateShapes.class, DnD.class);
	}

	@Override
	public void initAction() {
		action.setDrawing(instrument.drawing);
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
		final int button 		 = interaction.getButton();
		return  !instrument.drawing.getSelection().isEmpty() && 
				((startObject==instrument.canvas && button==MouseEvent.BUTTON3) || 
				 (startObject instanceof IViewShape && (button==MouseEvent.BUTTON1 || button==MouseEvent.BUTTON3)));
	}
}



class Press2Select extends Link<SelectShapes, Press, Hand> {
	protected Press2Select(final Hand ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, SelectShapes.class, Press.class);
	}

	@Override
	public void initAction() {
		action.setDrawing(instrument.drawing);		
	}
	
	@Override
	public void updateAction() {
		final Object target = interaction.getTarget();
		
		if(target instanceof IViewShape)
			action.setShape(MappingRegistry.REGISTRY.getSourceFromTarget(target, IShape.class));
	}

	@Override
	public boolean isConditionRespected() {
		final Object target = interaction.getTarget();
		return interaction.getTarget() instanceof IViewShape && 
			   !instrument.drawing.getSelection().contains((MappingRegistry.REGISTRY.getSourceFromTarget(target, IShape.class)));
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
		action.setDrawing(instrument.drawing);
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

		if(!selectionBorder.isEmpty())
			for(IViewShape view : instrument.canvas.getViews())
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
