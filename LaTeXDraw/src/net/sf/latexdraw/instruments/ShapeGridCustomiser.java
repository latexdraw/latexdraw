package net.sf.latexdraw.instruments;

import java.awt.Color;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.ModifyShapeProperty;
import net.sf.latexdraw.actions.ShapeProperties;
import net.sf.latexdraw.actions.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.malai.ui.UIComposer;
import org.malai.widget.MButtonIcon;
import org.malai.widget.MColorButton;

/**
 * This instrument modifies grids properties of shapes or the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-15<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeGridCustomiser extends ShapePropertyCustomiser {
	/** Changes the colour of the labels. */
	protected MColorButton colourLabels;


	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null or if the drawing cannot
	 * be accessed from the hand.
	 * @since 3.0
	 */
	public ShapeGridCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	@Override
	protected void update(final IShape shape) {
		if(shape.isTypeOf(IGrid.class)) {
			final IGrid grid = (IGrid)shape;
			colourLabels.setColor(grid.getGridLabelsColour());
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(colourLabels, activated);
	}


	@Override
	protected void initialiseWidgets() {
		colourLabels = new MColorButton("Colour", new MButtonIcon(Color.BLACK));
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new ColourButton2PencilGrid(this));
			addLink(new ColourButton2SelectionGrid(this));
		}catch(final InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(final IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The button that permits to change the colour of the labels.
	 * @since 3.0
	 */
	public final MColorButton getColourLabels() {
		return colourLabels;
	}


	private static abstract class ColourButtonForShapeGridCust<A extends ShapePropertyAction> extends ColourButtonForCustomiser<A, ShapeGridCustomiser> {
		protected ColourButtonForShapeGridCust(final ShapeGridCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(instrument, clazzAction);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setProperty(ShapeProperties.GRID_LABELS_COLOUR);
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getButton()==instrument.colourLabels;
		}
	}


	/**
	 * This link maps a colour button to the pencil.
	 */
	private static class ColourButton2PencilGrid extends ColourButtonForShapeGridCust<ModifyPencilParameter> {
		/**
		 * Creates the link.
		 * @param instrument The instrument that contains the link.
		 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
		 * @throws IllegalAccessException If no free-parameter constructor are provided.
		 */
		protected ColourButton2PencilGrid(final ShapeGridCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyPencilParameter.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setPencil(instrument.pencil);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated() && super.isConditionRespected();
		}
	}



	/**
	 * This link maps a colour button to the selected shapes.
	 */
	private static class ColourButton2SelectionGrid extends ColourButtonForShapeGridCust<ModifyShapeProperty> {
		/**
		 * Creates the link.
		 * @param instrument The instrument that contains the link.
		 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
		 * @throws IllegalAccessException If no free-parameter constructor are provided.
		 */
		protected ColourButton2SelectionGrid(final ShapeGridCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup((IGroup)instrument.pencil.drawing.getSelection().duplicate());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated() && super.isConditionRespected();
		}
	}
}
