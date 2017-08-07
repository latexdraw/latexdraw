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
package net.sf.latexdraw.view.jfx;

import net.sf.latexdraw.models.interfaces.shape.IPolyline;

/**
 * @author Arnaud Blouin
 */
public class ViewPolyline extends ViewPolyPoint<IPolyline> {
	protected final ViewArrowableTrait viewArrows = new ViewArrowableTrait(this);

//	private final ChangeListener<Number> updateArrow = (observable, oldValue, newValue) -> update();

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPolyline(final IPolyline sh) {
		super(sh);
		getChildren().add(viewArrows);

//		final int nbPts = model.getNbPoints();
//
//		model.getPtAt(0).xProperty().addListener(updateArrow);
//		model.getPtAt(0).yProperty().addListener(updateArrow);
//		if(nbPts > 2) {
//			model.getPtAt(1).xProperty().addListener(updateArrow);
//			model.getPtAt(1).yProperty().addListener(updateArrow);
//		}
//		if(nbPts > 3) {
//			model.getPtAt(nbPts - 2).xProperty().addListener(updateArrow);
//			model.getPtAt(nbPts - 2).yProperty().addListener(updateArrow);
//		}
//		model.getPtAt(-1).xProperty().addListener(updateArrow);
//		model.getPtAt(-1).yProperty().addListener(updateArrow);
//		model.thicknessProperty().addListener(updateArrow);
		viewArrows.updateArr();
	}


//	private void update() {
//		viewArrows.update(true);
//
//		if(viewArrows.arrows.stream().anyMatch(ar -> ar.arrow.hasStyle())) {
//			clipPath(border);
//		}else {
//			border.setClip(null);
//		}
//	}


//	@Override
//	public void flush() {
//		final int nbPts = model.getNbPoints();
//		model.getPtAt(0).xProperty().removeListener(updateArrow);
//		model.getPtAt(0).yProperty().removeListener(updateArrow);
//		model.getPtAt(-1).xProperty().removeListener(updateArrow);
//		model.getPtAt(-1).yProperty().removeListener(updateArrow);
//		if(nbPts > 2) {
//			model.getPtAt(1).xProperty().removeListener(updateArrow);
//			model.getPtAt(1).yProperty().removeListener(updateArrow);
//		}
//		if(nbPts > 3) {
//			model.getPtAt(nbPts - 2).xProperty().removeListener(updateArrow);
//			model.getPtAt(nbPts - 2).yProperty().removeListener(updateArrow);
//		}
//		super.flush();
//	}
}
