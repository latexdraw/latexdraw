package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LAxes model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
		if(!GLibUtilities.INSTANCE.isValidPoint(origDrawing) || ppc<1)
			return ;

		emptyCache();

		double startX, startY, endX, endY;
		StringBuilder start	  		= new StringBuilder();
		StringBuilder end	  		= new StringBuilder();
		StringBuilder rot	  		= getRotationHeaderCode(ppc, origDrawing);
		StringBuilder coord	  		= new StringBuilder();
		StringBuilder arrowsStyle 	= getArrowsStyleCode();
		final double gridEndx 		= shape.getGridEndX();
		final double gridEndy 		= shape.getGridEndY();
		final double positionx  	= shape.getPosition().getX();
		final double positiony  	= shape.getPosition().getY();
		final double gridStartx 	= shape.getGridStartX();
		final double gridStarty 	= shape.getGridStartY();

		if(shape.isXLabelSouth()) {
			startY = gridStarty;
			endY   = gridEndy;
		}
		else {
			startY = gridEndy;
			endY   = gridStarty;
		}

		if(shape.isYLabelWest()) {
			startX = gridStartx;
			endX   = gridEndx;
		}
		else {
			startX = gridEndx;
			endX   = gridStartx;
		}

		if(!LNumber.INSTANCE.equals(positionx, 0.) || !LNumber.INSTANCE.equals(positiony, 0.)) {
			end.append('}');
			start.append("\\rput(").append((float)LNumber.INSTANCE.getCutNumber((positionx-origDrawing.getX())/ppc)).append(','); //$NON-NLS-1$
			start.append((float)LNumber.INSTANCE.getCutNumber((origDrawing.getY()-positiony)/ppc)).append(')').append('{');
		}

		if(rot!=null) {
			start.append(rot);
			end.insert(0, '}');
		}

		coord.append('(').append(0).append(',').append(0).append(')');
		coord.append('(').append((int)startX).append(',');
		coord.append((int)startY).append(')').append('(');
		coord.append((int)endX).append(',').append((int)endY).append(')');

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
		StringBuilder params 		= getLineCode(ppc);
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
//		params.append(", ticksize=").append((float)LNumber.INSTANCE.getCutNumber(shape.getTicksSize()/ppc)).append(PSTricksConstants.TOKEN_CM);//$NON-NLS-1$

		if(!LNumber.INSTANCE.equals(distLabelsX, 0.))
			params.append(", dx=").append((float)LNumber.INSTANCE.getCutNumber(distLabelsX)).append(PSTricksConstants.TOKEN_CM);//$NON-NLS-1$

		if(!LNumber.INSTANCE.equals(distLabelsY, 0.))
			params.append(", dy=").append((float)LNumber.INSTANCE.getCutNumber(distLabelsY)).append(PSTricksConstants.TOKEN_CM);//$NON-NLS-1$

		if(!LNumber.INSTANCE.equals(incrementx, PSTricksConstants.DEFAULT_DX))
			params.append(", Dx=").append(LNumber.INSTANCE.equals(incrementx, incrementx) ? String.valueOf((int)incrementx): //$NON-NLS-1$
											String.valueOf((float)LNumber.INSTANCE.getCutNumber(incrementx)));

		if(!LNumber.INSTANCE.equals(incrementy, PSTricksConstants.DEFAULT_DY))
			params.append(", Dy=").append(LNumber.INSTANCE.equals(incrementy, incrementy) ? String.valueOf((int)incrementy): //$NON-NLS-1$
										String.valueOf((float)LNumber.INSTANCE.getCutNumber(incrementy)));

		if(!LNumber.INSTANCE.equals(originx, PSTricksConstants.DEFAULT_OX))
			params.append(", Ox=").append((int)originx);//$NON-NLS-1$

		if(!LNumber.INSTANCE.equals(originy, PSTricksConstants.DEFAULT_OY))
			params.append(", Oy=").append((int)originy);//$NON-NLS-1$

		if(showOrigin!=PSTricksConstants.DEFAULT_SHOW_ORIGIN)
			params.append(", showorigin=").append(showOrigin);//$NON-NLS-1$

		return params;
	}
}
