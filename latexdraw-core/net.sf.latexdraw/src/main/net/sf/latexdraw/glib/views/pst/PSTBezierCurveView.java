package net.sf.latexdraw.glib.views.pst;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LBezierCurve model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 04/16/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class PSTBezierCurveView extends PSTClassicalView<IBezierCurve> {
	/**
	 * Creates and initialises a LBezierCurve PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTBezierCurveView(@NonNull final IBezierCurve model) {
		super(model);

		update();
	}



	@Override
	public void updateCache(final IPoint origin, final float ppc) {
		if(!GLibUtilities.isValidPoint(origin) || ppc<1)
			return ;

		emptyCache();

		int i;
        final int size 			= shape.getNbPoints();
        IPoint pt;
        IPoint ctrlPt1;
        IPoint ctrlPt2;
        final StringBuilder arrowsStyle= getArrowsStyleCode();
		final StringBuilder params 	= getPropertiesCode(ppc);
		final StringBuilder coord 	= new StringBuilder();
		final List<IPoint> pts 		= shape.getPoints();
		final List<IPoint> fCtrlPts 	= shape.getFirstCtrlPts();
		final List<IPoint> sCtrlPts 	= shape.getSecondCtrlPts();
		final double originx 			= origin.getX();
		final double originy 			= origin.getY();

		if(size<2)
			return ;

		coord.append('(').append((float)LNumber.getCutNumber((pts.get(0).getX()-originx)/ppc));
		coord.append(',').append((float)LNumber.getCutNumber((originy-pts.get(0).getY())/ppc));
		coord.append(')').append('(').append((float)LNumber.getCutNumber((fCtrlPts.get(0).getX()-originx)/ppc));
		coord.append(',').append((float)LNumber.getCutNumber((originy-fCtrlPts.get(0).getY())/ppc));
		coord.append(')').append('(').append((float)LNumber.getCutNumber((fCtrlPts.get(1).getX()-originx)/ppc));
		coord.append(',').append((float)LNumber.getCutNumber((originy-fCtrlPts.get(1).getY())/ppc));
		coord.append(')').append('(').append((float)LNumber.getCutNumber((pts.get(1).getX()-originx)/ppc));
		coord.append(',').append((float)LNumber.getCutNumber((originy-pts.get(1).getY())/ppc));
		coord.append(')');

		for(i=2; i<size; i++) {
			ctrlPt1 = fCtrlPts.get(i);
			ctrlPt2 = sCtrlPts.get(i-1);

			coord.append('(').append(LNumber.getCutNumber((float)((ctrlPt2.getX()-originx)/ppc)));
			coord.append(',').append(LNumber.getCutNumber((float)((originy-ctrlPt2.getY())/ppc)));
			coord.append(')').append('(').append(LNumber.getCutNumber((float)((ctrlPt1.getX()-originx)/ppc)));
			coord.append(',').append(LNumber.getCutNumber((float)((originy-ctrlPt1.getY())/ppc)));
			coord.append(')').append('(');

			pt = pts.get(i);
			coord.append(LNumber.getCutNumber((float)((pt.getX()-originx)/ppc))).append(',');
			coord.append(LNumber.getCutNumber((float)((originy-pt.getY())/ppc))).append(')');
		}

		if(shape.isClosed()) {
			ctrlPt1 = sCtrlPts.get(0);
			ctrlPt2 = sCtrlPts.get(sCtrlPts.size()-1);

			coord.append('(').append(LNumber.getCutNumber((float)((ctrlPt2.getX()-originx)/ppc)));
			coord.append(',').append(LNumber.getCutNumber((float)((originy-ctrlPt2.getY())/ppc)));
			coord.append(')').append('(').append(LNumber.getCutNumber((float)((ctrlPt1.getX()-originx)/ppc)));
			coord.append(',').append(LNumber.getCutNumber((float)((originy-ctrlPt1.getY())/ppc)));
			coord.append(')').append('(');

			pt = pts.get(0);
			coord.append(LNumber.getCutNumber((float)((pt.getX()-originx)/ppc))).append(',');
			coord.append(LNumber.getCutNumber((float)((originy-pt.getY())/ppc))).append(')');
		}

		cache.append("\\psbezier["); //$NON-NLS-1$
		cache.append(params);
		cache.append(']');
		if(arrowsStyle!=null)
			cache.append(arrowsStyle);
		cache.append(coord);
	}
}
