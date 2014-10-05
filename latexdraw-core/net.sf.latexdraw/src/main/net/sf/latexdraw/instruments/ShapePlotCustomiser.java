package net.sf.latexdraw.instruments;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import org.malai.swing.ui.SwingUIComposer;
import org.malai.swing.widget.MSpinner;

/**
 * This instrument modifies plot parameters.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-10-05<br>
 * @author Arnaud BLOUIN
 * @since 3.1
 */
public class ShapePlotCustomiser extends ShapePropertyCustomiser {
	MSpinner nbPtsSpinner;

	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null or if the drawing cannot be accessed from the hand.
	 */
	public ShapePlotCustomiser(final SwingUIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IPlotProp.class)) {
//			nbPtsSpinner.setValueSafely(shape.getn
			setActivated(true);
		}
		else setActivated(false);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(nbPtsSpinner, visible);
	}

	@Override
	protected void initialiseWidgets() {
		nbPtsSpinner = new MSpinner(new MSpinner.MSpinnerNumberModel(50., 1., 10000., 10.), new JLabel("Plotted points:"));
		nbPtsSpinner.setEditor(new JSpinner.NumberEditor(nbPtsSpinner, "0.0"));//$NON-NLS-1$
	}

	@Override
	protected void initialiseInteractors() {
		// TODO Auto-generated method stub
	}

	/** @return The widget that permits to change the number of plotted points.	 */
	public MSpinner getnbPtsSpinner() { return nbPtsSpinner; }
}
