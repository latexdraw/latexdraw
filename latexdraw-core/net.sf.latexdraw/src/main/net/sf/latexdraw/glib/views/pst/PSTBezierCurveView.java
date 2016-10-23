/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2015 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LNumber;
import org.eclipse.jdt.annotation.NonNull;

import java.util.List;

/**
 * Defines a PSTricks view of the LBezierCurve model.<
 */
class PSTBezierCurveView extends PSTClassicalView<IBezierCurve> {
	/**
	 * Creates and initialises a LBezierCurve PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTBezierCurveView(final @NonNull IBezierCurve model) {
		super(model);
	}


	@Override
	public String getCode(final IPoint origin, final float ppc) {
		if(!GLibUtilities.isValidPoint(origin) || ppc < 1) return "";

		int i;
		final int size = shape.getNbPoints();
		IPoint pt;
		IPoint ctrlPt1;
		IPoint ctrlPt2;
		final StringBuilder arrowsStyle = getArrowsStyleCode();
		final StringBuilder params = getPropertiesCode(ppc);
		final StringBuilder coord = new StringBuilder();
		final List<IPoint> pts = shape.getPoints();
		final List<IPoint> fCtrlPts = shape.getFirstCtrlPts();
		final List<IPoint> sCtrlPts = shape.getSecondCtrlPts();
		final double originx = origin.getX();
		final double originy = origin.getY();
		final StringBuilder cache = new StringBuilder();

		if(size < 2) return "";

		coord.append('(').append(LNumber.getCutNumberFloat((pts.get(0).getX() - originx) / ppc));
		coord.append(',').append(LNumber.getCutNumberFloat((originy - pts.get(0).getY()) / ppc));
		coord.append(')').append('(').append(LNumber.getCutNumberFloat((fCtrlPts.get(0).getX() - originx) / ppc));
		coord.append(',').append(LNumber.getCutNumberFloat((originy - fCtrlPts.get(0).getY()) / ppc));
		coord.append(')').append('(').append(LNumber.getCutNumberFloat((fCtrlPts.get(1).getX() - originx) / ppc));
		coord.append(',').append(LNumber.getCutNumberFloat((originy - fCtrlPts.get(1).getY()) / ppc));
		coord.append(')').append('(').append(LNumber.getCutNumberFloat((pts.get(1).getX() - originx) / ppc));
		coord.append(',').append(LNumber.getCutNumber((originy - pts.get(1).getY()) / ppc));
		coord.append(')');

		for(i = 2; i < size; i++) {
			ctrlPt1 = fCtrlPts.get(i);
			ctrlPt2 = sCtrlPts.get(i - 1);

			coord.append('(').append(LNumber.getCutNumberFloat((ctrlPt2.getX() - originx) / ppc));
			coord.append(',').append(LNumber.getCutNumberFloat((originy - ctrlPt2.getY()) / ppc));
			coord.append(')').append('(').append(LNumber.getCutNumberFloat((ctrlPt1.getX() - originx) / ppc));
			coord.append(',').append(LNumber.getCutNumberFloat((originy - ctrlPt1.getY()) / ppc));
			coord.append(')').append('(');

			pt = pts.get(i);
			coord.append(LNumber.getCutNumberFloat((pt.getX() - originx) / ppc)).append(',');
			coord.append(LNumber.getCutNumberFloat((originy - pt.getY()) / ppc)).append(')');
		}

		if(shape.isClosed()) {
			ctrlPt1 = sCtrlPts.get(0);
			ctrlPt2 = sCtrlPts.get(sCtrlPts.size() - 1);

			coord.append('(').append(LNumber.getCutNumberFloat((ctrlPt2.getX() - originx) / ppc));
			coord.append(',').append(LNumber.getCutNumberFloat((originy - ctrlPt2.getY()) / ppc));
			coord.append(')').append('(').append(LNumber.getCutNumberFloat((ctrlPt1.getX() - originx) / ppc));
			coord.append(',').append(LNumber.getCutNumberFloat((originy - ctrlPt1.getY()) / ppc));
			coord.append(')').append('(');

			pt = pts.get(0);
			coord.append(LNumber.getCutNumberFloat((pt.getX() - originx) / ppc)).append(',');
			coord.append(LNumber.getCutNumberFloat((originy - pt.getY()) / ppc)).append(')');
		}

		cache.append("\\psbezier["); //$NON-NLS-1$
		cache.append(params);
		cache.append(']');
		if(arrowsStyle != null) cache.append(arrowsStyle);
		cache.append(coord);

		return cache.toString();
	}
}
