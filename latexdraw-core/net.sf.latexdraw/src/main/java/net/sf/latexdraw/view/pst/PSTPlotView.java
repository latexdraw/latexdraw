/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.pst;

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;

/**
 * @author Arnaud Blouin
 */
public class PSTPlotView extends PSTClassicalView<IPlot> {
	protected PSTPlotView(final IPlot model) {
		super(model);
	}

	@Override
	public String getCode(final IPoint position, final float ppc) {
		if(!MathUtils.INST.isValidPt(position) || ppc < 1) {
			return "";
		}

		final StringBuilder params = getPropertiesCode(ppc);
		final StringBuilder rotation = getRotationHeaderCode(ppc, position);
		final StringBuilder code = new StringBuilder();

		if(rotation != null) {
			code.append(rotation);
		}

		code.append("\\rput("); //NON-NLS
		code.append(MathUtils.INST.getCutNumberFloat((shape.getX() - position.getX()) / ppc)).append(',');
		code.append(MathUtils.INST.getCutNumberFloat((position.getY() - shape.getY()) / ppc)).append(')').append('{');
		code.append("\\psplot[");    //NON-NLS
		code.append(params).append(", plotstyle=").append(shape.getPlotStyle().getPSTToken()).append(", plotpoints=").
			append(shape.getNbPlottedPoints()).append(", xunit=").append(shape.getXScale()).append(", yunit=").
			append(shape.getYScale()).append(", polarplot=").append(shape.isPolar());
		if(shape.getPlotStyle() == PlotStyle.DOTS) {
			code.append(", dotstyle=").append(shape.getDotStyle().getPSTToken()).
				append(", dotsize=").append(MathUtils.INST.getCutNumberFloat(shape.getDiametre() / ppc));
			if(shape.getDotStyle().isFillable()) {
				code.append(", fillcolor=").append(getColourName(shape.getFillingCol()));
			}
		}
		code.append("]{").append(shape.getPlotMinX()).append("}{").append(shape.getPlotMaxX()).append("}{").
			append(shape.getPlotEquation()).append('}');

		if(rotation != null) {
			code.append('}');
		}
		code.append('}');

		return code.toString();
	}
}
