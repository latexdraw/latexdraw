package net.sf.latexdraw.glib.views.pst;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks view of the LGrid model.<br>
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
class PSTGridView extends PSTShapeView<IGrid> {
	/**
	 * Creates and initialises a LGrid PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTGridView(final IGrid model) {
		super(model);

		update();
	}


	/**
	 * Returns the PST code of the parameters of the grid.
	 */
	private StringBuilder getParamsCode(final float ppc, final double unit) {
		Color gridLabelsColor = shape.getGridLabelsColour();
		Color subGridColor	  = shape.getSubGridColour();
		Color linesColor	  = shape.getLineColour();
		StringBuilder params  = new StringBuilder();

		params.append("gridwidth=").append((float)LNumber.INSTANCE.getCutNumber(shape.getGridWidth()/ppc)); //$NON-NLS-1$
		params.append(", subgridwidth=").append((float)LNumber.INSTANCE.getCutNumber(shape.getSubGridWidth()/ppc)); //$NON-NLS-1$
		params.append(", gridlabels=").append(LNumber.INSTANCE.getCutNumber(shape.getLabelsSize()*0.6f)).append("pt"); //$NON-NLS-1$ //$NON-NLS-2$

		if(shape.getSubGridDiv()!=PSTricksConstants.DEFAULT_SUBGRIDDIV)
			params.append(", subgriddiv=").append(shape.getSubGridDiv()); //$NON-NLS-1$

		if(shape.getGridDots()!=PSTricksConstants.DEFAULT_GRIDDOTS)
			params.append(", griddots=").append(shape.getGridDots()); //$NON-NLS-1$

		if(shape.getSubGridDots()!=PSTricksConstants.DEFAULT_SUBGRIDDOTS)
			params.append(", subgriddots=").append(shape.getSubGridDots()); //$NON-NLS-1$

		if(!gridLabelsColor.equals(PSTricksConstants.DEFAULT_LABELGRIDCOLOR))
			params.append(", gridlabelcolor=").append(getColourName(gridLabelsColor)); //$NON-NLS-1$

		if(!LNumber.INSTANCE.equals(unit, PSTricksConstants.DEFAULT_UNIT))
			params.append(", unit=").append(LNumber.INSTANCE.getCutNumber((float)unit)).append(PSTricksConstants.TOKEN_CM); //$NON-NLS-1$

		if(!linesColor.equals(PSTricksConstants.DEFAULT_GRIDCOLOR))
			params.append(", gridcolor=").append(getColourName(linesColor)); //$NON-NLS-1$

		params.append(", subgridcolor=").append(getColourName(subGridColor)); //$NON-NLS-1$

		return params;
	}


	@Override
	public void updateCache(final IPoint d, final float ppc) {
		if(!GLibUtilities.INSTANCE.isValidPoint(d) || ppc<1)
			return ;

		emptyCache();

		int startX, startY, endX, endY;
		boolean isXLabelSouth = shape.isXLabelSouth();
		boolean isYLabelWest  = shape.isYLabelWest();
		IPoint position		  = shape.getPosition();
		StringBuilder start	  = new StringBuilder();
		StringBuilder end	  = new StringBuilder();
		StringBuilder rot	  = getRotationHeaderCode(ppc, d);
		StringBuilder coord	  = new StringBuilder();
		double unit			  = shape.getUnit();
		double gridStartx 	  = shape.getGridStartX();
		double gridStarty 	  = shape.getGridStartY();
		double gridEndx 	  = shape.getGridEndX();
		double gridEndy 	  = shape.getGridEndY();

		if(isXLabelSouth) {
			startY = (int)gridStarty;
			endY   = (int)gridEndy;
		}
		else {
			startY = (int)gridEndy;
			endY   = (int)gridStarty;

		}

		if(isYLabelWest) {
			startX = (int)gridStartx;
			endX   = (int)gridEndx;
		}
		else {
			startX = (int)gridEndx;
			endX   = (int)gridStartx;
		}

		coord.append('(').append((int)shape.getOriginX()).append(',').append((int)shape.getOriginY()).append(')');
		coord.append('(').append(startX).append(',').append(startY).append(')');
		coord.append('(').append(endX).append(',').append(endY).append(')');

		if(!LNumber.INSTANCE.equals(unit, PSTricksConstants.DEFAULT_UNIT))
			end.append("\n\\psset{unit=").append(PSTricksConstants.DEFAULT_UNIT).append(PSTricksConstants.TOKEN_CM).append('}');//$NON-NLS-1$

		if(!LNumber.INSTANCE.equals(position.getX(), 0.) || !LNumber.INSTANCE.equals(position.getY(), 0.)) {
			float posX = (float)LNumber.INSTANCE.getCutNumber((position.getX()-d.getX())/ppc);
			float posY = (float)LNumber.INSTANCE.getCutNumber((d.getY()-position.getY())/ppc);

			end.append('}');
			start.append("\\rput(").append(posX).append(',').append(posY).append(')').append('{');//$NON-NLS-1$
		}

		if(rot!=null) {
			start.append(rot);
			end.insert(0, '}');
		}

		cache.append(start);
		cache.append("\\psgrid[");//$NON-NLS-1$
		cache.append(getParamsCode(ppc, unit));
		cache.append(']');
		cache.append(coord);
		cache.append(end);
	}
}
