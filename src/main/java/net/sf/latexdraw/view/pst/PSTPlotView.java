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

import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * @author Arnaud Blouin
 */
public class PSTPlotView extends PSTClassicalView<Plot> {
	protected PSTPlotView(final @NotNull Plot model) {
		super(model);
	}

	@Override
	public @NotNull String getCode(final @NotNull Point position, final float ppc) {
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
		code.append(params).append(", plotstyle=").append(shape.getPlotStyle().getPSTToken()).append(", plotpoints="). //NON-NLS
			append(shape.getNbPlottedPoints()).append(", xunit=").append(shape.getXScale()).append(", yunit="). //NON-NLS
			append(shape.getYScale()).append(", polarplot=").append(shape.isPolar()); //NON-NLS
		if(shape.getPlotStyle() == PlotStyle.DOTS) { //NON-NLS
			code.append(", dotstyle=").append(shape.getDotStyle().getPSTToken()). //NON-NLS
				append(", dotsize=").append(MathUtils.INST.getCutNumberFloat(shape.getDiametre() / ppc)); //NON-NLS
			if(shape.getDotStyle().isFillable()) {
				code.append(", fillcolor=").append(getColourName(shape.getFillingCol())); //NON-NLS
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
