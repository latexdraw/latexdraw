package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;

/**
 * This instrument modifies arc dimensions and coordinates of shapes or pencil parameters.<br>
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
 * 12/17/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeCoordDimCustomiser extends ShapePropertyCustomiser {
	/** Sets the X-coordinate of the top-left position. */
	@FXML protected Spinner<Double> tlxS;

	/** Sets the Y-coordinate of the top-left position. */
	@FXML protected Spinner<Double> tlyS;


	/**
	 * Creates the instrument.
	 */
	public ShapeCoordDimCustomiser() {
		super();
	}



	@Override
	protected void update(final IGroup shape) {
		if(shape.isEmpty())
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
//		try{
//			addInteractor(new Spinner2TranslateShape(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}



	@Override
	protected void setWidgetsVisible(final boolean visible) {
		//
	}

//
//	/**
//	 * Maps spinners to translation of shapes. The X and Y spinners are used to change the position of the top-left point of the
//	 * selected shapes, i.e. to translate it.
//	 */
//	private static class Spinner2TranslateShape extends InteractorImpl<TranslateShapes, SpinnerModified, ShapeCoordDimCustomiser> {
//		protected Spinner2TranslateShape(final ShapeCoordDimCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, false, TranslateShapes.class, SpinnerModified.class);
//		}
//
//		@Override
//		public void initAction() {
//			action.setDrawing(instrument.pencil.canvas().getDrawing());
//			action.setShape(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//
//		@Override
//		public void updateAction() {
//			super.updateAction();
//
//			final IPoint tl = action.drawing().get().getSelection().getTopLeftPoint();
//			final double value = Double.parseDouble(interaction.getSpinner().getValue().toString());
//
//			if(interaction.getSpinner()==instrument.tlxS)
//				action.setTx(value-tl.getX());
//			else
//				action.setTy(value-tl.getY());
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			final JSpinner obj = interaction.getSpinner();
//			return obj==instrument.tlxS || obj==instrument.tlyS;
//		}
//	}
}
