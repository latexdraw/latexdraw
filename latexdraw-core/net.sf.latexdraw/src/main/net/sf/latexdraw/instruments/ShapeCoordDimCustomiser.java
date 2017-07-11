/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.actions.shape.TranslateShapes;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.malai.javafx.binding.SpinnerBinding;

/**
 * This instrument modifies arc dimensions and coordinates of shapes or pencil parameters.
 * @author Arnaud BLOUIN
 */
public class ShapeCoordDimCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** Sets the X-coordinate of the top-left position. */
	@FXML private Spinner<Double> tlxS;
	/** Sets the Y-coordinate of the top-left position. */
	@FXML private Spinner<Double> tlyS;
	@FXML private TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	ShapeCoordDimCustomiser() {
		super();
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isEmpty() || !hand.isActivated()) {
			setActivated(false);
		}
		else {
			setActivated(true);
			final IPoint tl = shape.getTopLeftPoint();
			tlxS.getValueFactory().setValue(tl.getX());
			tlyS.getValueFactory().setValue(tl.getY());
		}
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		addBinding(new Spinner2Translate(this));
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}


	private static class Spinner2Translate extends SpinnerBinding<TranslateShapes, ShapeCoordDimCustomiser> {
		private IPoint tl;

		Spinner2Translate(final ShapeCoordDimCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, true, TranslateShapes.class, ins.tlxS, ins.tlyS);
		}

		@Override
		public void initAction() {
			tl = instrument.drawing.getSelection().getTopLeftPoint();
			action.setDrawing(instrument.drawing);
			action.setShape(instrument.drawing.getSelection().duplicateDeep(false));
			updateAction();
		}

		@Override
		public void updateAction() {
			if(interaction.getWidget() == instrument.tlxS) {
				action.setTx(instrument.tlxS.getValue() - tl.getX());
			}else {
				action.setTy(instrument.tlyS.getValue() - tl.getY());
			}
		}
	}
}
