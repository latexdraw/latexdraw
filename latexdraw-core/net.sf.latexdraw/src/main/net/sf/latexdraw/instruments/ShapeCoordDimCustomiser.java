package net.sf.latexdraw.instruments;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import net.sf.latexdraw.actions.shape.TranslateShapes;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.malai.instrument.Link;
import org.malai.swing.interaction.library.SpinnerModified;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MSpinner;

/**
 * This instrument modifies arc dimensions and coordinates of shapes or pencil parameters.<br>
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
 * 12/17/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeCoordDimCustomiser extends ShapePropertyCustomiser {
	/** Sets the X-coordinate of the top-left position. */
	protected MSpinner tlxS;

	/** Sets the Y-coordinate of the top-left position. */
	protected MSpinner tlyS;


	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null or if the drawing cannot
	 * be accessed from the hand.
	 * @since 3.0
	 */
	public ShapeCoordDimCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}



	@Override
	protected void update(final IShape shape) {
		if(shape!=null) {
			final IPoint tl = shape.getTopLeftPoint();
			tlxS.setValueSafely(tl.getX());
			tlyS.setValueSafely(tl.getY());
		}
	}



	@Override
	protected void initialiseWidgets() {
		tlxS = new MSpinner(new MSpinner.MSpinnerNumberModel(0., (double)Integer.MIN_VALUE, (double)Integer.MAX_VALUE, 1.), new JLabel("x"));
		tlxS.setEditor(new JSpinner.NumberEditor(tlxS, "0.00"));//$NON-NLS-1$
		tlxS.setToolTipText("Sets the X-coordinate of the top-left position");
		tlyS = new MSpinner(new MSpinner.MSpinnerNumberModel(0., (double)Integer.MIN_VALUE, (double)Integer.MAX_VALUE, 1.), new JLabel("y"));
		tlyS.setEditor(new JSpinner.NumberEditor(tlyS, "0.00"));//$NON-NLS-1$
		tlyS.setToolTipText("Sets the Y-coordinate of the top-left position");
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(tlxS, activated);
		composer.setWidgetVisible(tlyS, activated);
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new Spinner2TranslateShape(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The spinner that sets the X-coordinate of the top-left position.
	 * @since 3.0
	 */
	public MSpinner getTlxS() {
		return tlxS;
	}

	/**
	 * @return The spinner that sets the Y-coordinate of the top-left position.
	 * @since 3.0
	 */
	public MSpinner getTlyS() {
		return tlyS;
	}


	/**
	 * Maps spinners to translation of shapes. The X and Y spinners are used to change the position of the top-left point of the
	 * selected shapes, i.e. to translate it.
	 */
	private static class Spinner2TranslateShape extends Link<TranslateShapes, SpinnerModified, ShapeCoordDimCustomiser> {
		protected Spinner2TranslateShape(final ShapeCoordDimCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, TranslateShapes.class, SpinnerModified.class);
		}

		@Override
		public void initAction() {
			action.setDrawing(instrument.pencil.canvas.getDrawing());
		}


		@Override
		public void updateAction() {
			super.updateAction();

			final IPoint tl = action.drawing().get().getSelection().getTopLeftPoint();
			final double value = Double.parseDouble(interaction.getSpinner().getValue().toString());

			if(interaction.getSpinner()==instrument.tlxS)
				action.setTx(value-tl.getX());
			else
				action.setTy(value-tl.getY());
		}

		@Override
		public boolean isConditionRespected() {
			final JSpinner obj = interaction.getSpinner();
			return obj==instrument.tlxS || obj==instrument.tlyS;
		}
	}
}
