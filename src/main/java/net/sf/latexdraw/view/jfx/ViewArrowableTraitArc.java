/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import javafx.scene.shape.Arc;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.CircleArc;

/**
 * An implementation of ViewArrowable for arc-based views.
 */
class ViewArrowableTraitArc extends ViewArrowableTrait<Arc, CircleArc> {
	ViewArrowableTraitArc(final ViewSingleShape<CircleArc, Arc> view) {
		super(view);
	}

	@Override
	protected void clipPath(final Arc arc) {
		if(!model.getArcStyle().supportArrow()) {
			arc.setClip(null);
			return;
		}

		final double width = Math.max(arc.getRadiusX() * 2d, 1d);
		double sAngle = model.getAngleStart();
		double eAngle = model.getAngleEnd();
		Arrow arr = model.getArrowAt(1);
		final double gap = Math.atan(arr.getArrowShapeLength() / width);

		if(arr.getArrowStyle().isReducingShape()) {
			if(eAngle > sAngle) {
				eAngle -= gap;
			}else {
				eAngle += gap;
			}
		}

		arr = model.getArrowAt(0);
		if(arr.getArrowStyle().isReducingShape()) {
			if(eAngle > sAngle) {
				sAngle += gap;
			}else {
				sAngle -= gap;
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
