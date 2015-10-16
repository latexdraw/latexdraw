package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.shape.IAxes;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LAxes model.<br>
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
 * 04/17/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class PSTAxesView extends PSTShapeView<IAxes> {
	/**
	 * Creates and initialises a LAxes PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTAxesView(final IAxes model) {
		super(model);
		update();
	}


	@Override
	public void updateCache(final IPoint origDrawing, final float ppc) {
		if(!GLibUtilities.isValidPoint(origDrawing) || ppc<1)
			return ;

		emptyCache();

		final StringBuilder start	  		= new StringBuilder();
		final StringBuilder end	  		= new StringBuilder();
		final StringBuilder rot	  		= getRotationHeaderCode(ppc, shape.getPosition());
		final StringBuilder coord	  		= new StringBuilder();
		final StringBuilder arrowsStyle 	= getArrowsStyleCode();
		final double gridEndx 		= shape.getGridEndX();
		final double gridEndy 		= shape.getGridEndY();
		final double positionx  	= shape.getPosition().getX();
		final double positiony  	= shape.getPosition().getY();
		final double gridStartx 	= shape.getGridStartX();
		final double gridStarty 	= shape.getGridStartY();

		if(!LNumber.equalsDouble(positionx, 0.) || !LNumber.equalsDouble(positiony, 0.)) {
			end.append('}');
			start.append("\\rput(").append(LNumber.getCutNumberFloat((positionx-origDrawing.getX())/ppc)).append(','); //$NON-NLS-1$
			start.append(LNumber.getCutNumberFloat((origDrawing.getY()-positiony)/ppc)).append(')').append('{');
		}

		if(rot!=null) {
			start.append(rot);
			end.insert(0, '}');
		}

		coord.append('(').append(0).append(',').append(0).append(')');
		coord.append('(').append((int)gridStartx).append(',');
		coord.append((int)gridStarty).append(')').append('(');
		coord.append((int)gridEndx).append(',').append((int)gridEndy).append(')');

		cache.append(start);
		cache.append("\\psaxes[");//$NON-NLS-1$
		cache.append(updateParams(ppc));
		cache.append(']');
		if(arrowsStyle!=null)
			cache.append(arrowsStyle);
		cache.append(coord);
		cache.append(end);
	}



	protected StringBuilder updateParams(final float ppc) {
		final StringBuilder params 		= getLineCode(ppc);
		final double incrementx 	= shape.getIncrementX();
		final double incrementy 	= shape.getIncrementY();
		final double originx 		= shape.getOriginX();
		final double originy 		= shape.getOriginY();
		final double distLabelsX	= shape.getDistLabelsX();
		final double distLabelsY	= shape.getDistLabelsY();
		final boolean showOrigin	= shape.isShowOrigin();

		params.append(", tickstyle=").append(shape.getTicksStyle().getPSTToken());//$NON-NLS-1$
		params.append(", axesstyle=").append(shape.getAxesStyle().getPSTToken());//$NON-NLS-1$
		params.append(", labels=").append(shape.getLabelsDisplayed().getPSTToken());//$NON-NLS-1$
		params.append(", ticks=").append(shape.getTicksDisplayed().getPSTToken());//$NON-NLS-1$
//		params.append(", ticksize=").append((float)LNumber.getCutNumber(shape.getTicksSize()/ppc)).append(PSTricksConstants.TOKEN_CM);//$NON-NLS-1$

		if(!LNumber.equalsDouble(distLabelsX, 0.))
			params.append(", dx=").append(LNumber.getCutNumberFloat(distLabelsX)).append(PSTricksConstants.TOKEN_CM);//$NON-NLS-1$

		if(!LNumber.equalsDouble(distLabelsY, 0.))
			params.append(", dy=").append(LNumber.getCutNumberFloat(distLabelsY)).append(PSTricksConstants.TOKEN_CM);//$NON-NLS-1$

		if(!LNumber.equalsDouble(incrementx, PSTricksConstants.DEFAULT_DX))
			params.append(", Dx=").append(LNumber.equalsDouble(incrementx, incrementx) ? String.valueOf((int)incrementx): //$NON-NLS-1$
											String.valueOf(LNumber.getCutNumberFloat(incrementx)));

		if(!LNumber.equalsDouble(incrementy, PSTricksConstants.DEFAULT_DY))
			params.append(", Dy=").append(LNumber.equalsDouble(incrementy, incrementy) ? String.valueOf((int)incrementy): //$NON-NLS-1$
										String.valueOf(LNumber.getCutNumberFloat(incrementy)));

		if(!LNumber.equalsDouble(originx, PSTricksConstants.DEFAULT_OX))
			params.append(", Ox=").append((int)originx);//$NON-NLS-1$

		if(!LNumber.equalsDouble(originy, PSTricksConstants.DEFAULT_OY))
			params.append(", Oy=").append((int)originy);//$NON-NLS-1$

		if(showOrigin!=PSTricksConstants.DEFAULT_SHOW_ORIGIN)
			params.append(", showorigin=").append(showOrigin);//$NON-NLS-1$

		return params;
	}
}
