package net.sf.latexdraw.glib.views.pst;

import org.eclipse.jdt.annotation.NonNull;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.shape.IArc;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LNumber;
import net.sf.latexdraw.util.LResources;

/**
 * Defines a PSTricks view of the LArc model.<br>
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
class PSTArcView extends PSTClassicalView<IArc> {
	/**
	 * Creates and initialises a LArc PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTArcView(@NonNull final IArc model) {
		super(model);
		update();
	}


	@Override
	public void updateCache(final IPoint origin, final float ppc) {
		if(!GLibUtilities.isValidPoint(origin) || ppc<1)
			return ;

		emptyCache();

		final double radiusX 		= shape.getWidth()/2.0;
		final double radiusY 		= shape.getHeight()/2.0;
		final double x 			= shape.getX()+radiusX - origin.getX();
		final double y 			= origin.getY() - shape.getY()+radiusY;
		double startAngle 	= shape.getAngleStart();
		double endAngle	  	= shape.getAngleEnd();
		final double yunit 		= radiusY/radiusX;
		final StringBuilder start = new StringBuilder();
		final StringBuilder end 	= new StringBuilder();
		final StringBuilder params = getPropertiesCode(ppc);
		final StringBuilder rotation = getRotationHeaderCode(ppc, origin);
		final StringBuilder arrowsStyle = getArrowsStyleCode();

		if(startAngle>endAngle) {
			final double tmp 	= startAngle;
			startAngle 	= endAngle;
			endAngle 	= tmp;
		}

		if(rotation!=null)
			end.append('}');

		if(!LNumber.equalsDouble(yunit, 1.)) {
			start.append("\\psscalebox{1 ").append((float)LNumber.getCutNumber(yunit)).append('}').append('{'); //$NON-NLS-1$
			end.append('}');
		}

		if(rotation!=null)
			start.append(rotation);

		switch(shape.getArcStyle()) {
			case ARC:
				start.append("\\psarc"); //$NON-NLS-1$
				break;
			case CHORD:
				final IPoint startPt= shape.getStartPoint();
				final IPoint endPt 	= shape.getEndPoint();

				start.append("\\psarc"); //$NON-NLS-1$
				end.append(LResources.EOL).append("\\psline[").append(params).append(']').append('('); //$NON-NLS-1$
				end.append((float)LNumber.getCutNumber(startPt.getX()/ppc)).append(',');
				end.append((float)LNumber.getCutNumber(startPt.getY()/ppc)).append(')').append('(');
				end.append((float)LNumber.getCutNumber(endPt.getX()/ppc)).append(',');
				end.append((float)LNumber.getCutNumber(endPt.getY()/ppc)).append(')');
				break;
			case WEDGE:
				start.append("\\pswedge"); //$NON-NLS-1$
				break;
		}

		cache.append(start);
		cache.append('[').append(params).append(']');
		if(arrowsStyle!=null)
			cache.append(arrowsStyle);
		cache.append('(');
		cache.append((float)LNumber.getCutNumber(x/ppc)).append(',');
		cache.append((float)LNumber.getCutNumber(y/ppc)).append(')').append('{');
		cache.append((float)LNumber.getCutNumber(radiusX/ppc)).append('}').append('{');
		cache.append((float)LNumber.getCutNumber(Math.toDegrees(startAngle))).append('}').append('{');
		cache.append((float)LNumber.getCutNumber(Math.toDegrees(endAngle))).append('}');
		cache.append(end);
	}
}
