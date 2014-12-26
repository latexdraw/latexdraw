package net.sf.latexdraw.instruments;

import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.actions.shape.TranslateShapes;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;

import org.malai.javafx.instrument.library.SpinnerInteractor;

/**
 * This instrument modifies arc dimensions and coordinates of shapes or pencil parameters.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 * <br>
 * 12/17/2011<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeCoordDimCustomiser extends ShapePropertyCustomiser {
	/** Sets the X-coordinate of the top-left position. */
	@FXML Spinner<Double> tlxS;

	/** Sets the Y-coordinate of the top-left position. */
	@FXML Spinner<Double> tlyS;

	@FXML TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeCoordDimCustomiser() {
		super();
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isEmpty() || !hand.isActivated())
			setActivated(false);
		else {
			setActivated(true);
			final IPoint tl = shape.getTopLeftPoint();
			tlxS.getValueFactory().setValue(tl.getX());
			tlyS.getValueFactory().setValue(tl.getY());
		}
	}

	@Override
	protected void initialiseInteractors() {
		try {
			addInteractor(new Spinner2TranslateShape(this));
		}catch(InstantiationException | IllegalAccessException e) {
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	private static class Spinner2TranslateShape extends SpinnerInteractor<TranslateShapes, ShapeCoordDimCustomiser> {
		Spinner2TranslateShape(final ShapeCoordDimCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, TranslateShapes.class, Arrays.asList(ins.tlxS, ins.tlyS));
		}

		@Override
		public void initAction() {
			final IDrawing drawing = instrument.pencil.getCanvas().getDrawing();
			final IPoint tl = drawing.getSelection().getTopLeftPoint();
			final double value = (double)interaction.getWidget().getValue();

			action.setDrawing(drawing);
			action.setShape(drawing.getSelection().duplicateDeep(false));

			if(interaction.getWidget() == instrument.tlxS)
				action.setTx(value - tl.getX());
			else
				action.setTy(value - tl.getY());
		}
	}
}
