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

import javafx.scene.shape.Arc;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;

/**
 * An implementation of ViewArrowable for arc-based views.
 */
class ViewArrowableTraitArc extends ViewArrowableTrait<Arc, ICircleArc> {
	ViewArrowableTraitArc(final ViewSingleShape<ICircleArc, Arc> view) {
		super(view);
	}

	@Override
	protected void clipPath(final Arc arc) {
		final double width = Math.max(arc.getRadiusX() * 2d, 1d);
		double sAngle = model.getAngleStart();
		double eAngle = model.getAngleEnd();

		if(model.getArcStyle().supportArrow()) {
			IArrow arr = model.getArrowAt(-1);
			if(arr.getArrowStyle().isReducingShape()) {
				eAngle -= Math.atan(arr.getArrowShapeLength() / width);
			}
			arr = model.getArrowAt(0);
			if(arr.getArrowStyle().isReducingShape()) {
				sAngle += Math.atan(arr.getArrowShapeLength() / width);
			}
		}
		sAngle = Math.toDegrees(sAngle % (2d * Math.PI));
		eAngle = Math.toDegrees(eAngle % (2d * Math.PI));

		if(MathUtils.INST.equalsDouble(sAngle, eAngle)) {
			eAngle += 0.1;
		}

		final Arc clip = new Arc(arc.getCenterX(), arc.getCenterY(), arc.getRadiusX(), arc.getRadiusY(), sAngle, eAngle - sAngle);
		clip.setType(arc.getType());
		clip.setStrokeWidth(arc.getStrokeWidth());
		arc.setClip(clip);
	}
}
