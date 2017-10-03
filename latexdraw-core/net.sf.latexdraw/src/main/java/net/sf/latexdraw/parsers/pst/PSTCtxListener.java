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
package net.sf.latexdraw.parsers.pst;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.latex.DviPsColors;

public abstract class PSTCtxListener extends net.sf.latexdraw.parsers.pst.PSTBaseListener {
	public final Logger LOG = Logger.getAnonymousLogger();

	public PSTCtxListener() {
		super();
		LOG.setLevel(Level.SEVERE);
	}

	@Override
	public void exitArrowvalue(final net.sf.latexdraw.parsers.pst.PSTParser.ArrowvalueContext ctx) {
		ctx.pstctx.arrowLeft = ctx.arrLeft == null ? "" : ctx.arrLeft.getText();
		ctx.pstctx.arrowRight = ctx.arrRight == null ? "" : ctx.arrRight.getText();
	}

	@Override
	public void exitParamgridwidth(final net.sf.latexdraw.parsers.pst.PSTParser.ParamgridwidthContext ctx) {
		ctx.pstctx.gridWidth = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamgridcolor(final net.sf.latexdraw.parsers.pst.PSTParser.ParamgridcolorContext ctx) {
		getColor(ctx.WORD().getText()).ifPresent(col -> ctx.pstctx.gridColor = col);
	}

	@Override
	public void exitParamgriddots(final net.sf.latexdraw.parsers.pst.PSTParser.ParamgriddotsContext ctx) {
		ctx.pstctx.gridDots = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamgridlabels(final net.sf.latexdraw.parsers.pst.PSTParser.ParamgridlabelsContext ctx) {
		ctx.pstctx.gridLabel = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamgridlabelcolor(final net.sf.latexdraw.parsers.pst.PSTParser.ParamgridlabelcolorContext ctx) {
		getColor(ctx.WORD().getText()).ifPresent(col -> ctx.pstctx.gridlabelcolor = col);
	}

	@Override
	public void exitParamsubgriddiv(final net.sf.latexdraw.parsers.pst.PSTParser.ParamsubgriddivContext ctx) {
		ctx.pstctx.subGridDiv = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamsubgridwidth(final net.sf.latexdraw.parsers.pst.PSTParser.ParamsubgridwidthContext ctx) {
		ctx.pstctx.subGridWidth = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamsubgridcolor(final net.sf.latexdraw.parsers.pst.PSTParser.ParamsubgridcolorContext ctx) {
		getColor(ctx.WORD().getText()).ifPresent(col -> ctx.pstctx.subGridCol = col);
	}

	@Override
	public void exitParamsubgriddots(final net.sf.latexdraw.parsers.pst.PSTParser.ParamsubgriddotsContext ctx) {
		ctx.pstctx.subGridDots = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamRbracketlength(final net.sf.latexdraw.parsers.pst.PSTParser.ParamRbracketlengthContext ctx) {
		ctx.pstctx.arrowrBrLgth = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamBracketlength(final net.sf.latexdraw.parsers.pst.PSTParser.ParamBracketlengthContext ctx) {
		ctx.pstctx.arrowBrLgth = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamArrowinset(final net.sf.latexdraw.parsers.pst.PSTParser.ParamArrowinsetContext ctx) {
		ctx.pstctx.arrowInset = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamArrowlength(final net.sf.latexdraw.parsers.pst.PSTParser.ParamArrowlengthContext ctx) {
		ctx.pstctx.arrowLgth = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamtbarsize(final net.sf.latexdraw.parsers.pst.PSTParser.ParamtbarsizeContext ctx) {
		ctx.pstctx.arrowTBar = ctx.pstctx.valNumNumberToDoubles(ctx.valueDim(), ctx.NUMBER());
	}

	@Override
	public void exitParamarrowsize(final net.sf.latexdraw.parsers.pst.PSTParser.ParamarrowsizeContext ctx) {
		ctx.pstctx.arrowSize = ctx.pstctx.valNumNumberToDoubles(ctx.valueDim(), ctx.NUMBER());
	}

	@Override
	public void exitParamunit(final net.sf.latexdraw.parsers.pst.PSTParser.ParamunitContext ctx) {
		ctx.pstctx.unit = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamxunit(final net.sf.latexdraw.parsers.pst.PSTParser.ParamxunitContext ctx) {
		ctx.pstctx.xUnit = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamyunit(final net.sf.latexdraw.parsers.pst.PSTParser.ParamyunitContext ctx) {
		ctx.pstctx.yUnit = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamdotsep(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdotsepContext ctx) {
		ctx.pstctx.dotStep = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamframearc(final net.sf.latexdraw.parsers.pst.PSTParser.ParamframearcContext ctx) {
		ctx.pstctx.frameArc = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamdotstyle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdotstyleContext ctx) {
		ctx.pstctx.dotStyle = DotStyle.getStyle(ctx.style.getText());
	}

	@Override
	public void exitParamdotscale(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdotscaleContext ctx) {
		ctx.pstctx.dotScale = new Tuple<>(ctx.pstctx.numberToDouble(ctx.num1), ctx.num2 == null ? ctx.pstctx.numberToDouble(ctx.num1) : ctx.pstctx.numberToDouble(ctx.num2));
	}

	@Override
	public void exitParamdotdotangle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdotdotangleContext ctx) {
		ctx.pstctx.dotAngle = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamdotsize(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdotsizeContext ctx) {
		ctx.pstctx.arrowDotSize = ctx.pstctx.valNumNumberToDoubles(ctx.valueDim(), ctx.NUMBER());
	}

	@Override
	public void exitParamlinecolor(final net.sf.latexdraw.parsers.pst.PSTParser.ParamlinecolorContext ctx) {
		getColor(ctx.WORD().getText()).ifPresent(col -> ctx.pstctx.lineColor = col);
	}

	@Override
	public void exitParamgangle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamgangleContext ctx) {
		ctx.pstctx.gangle = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamlinewidth(final net.sf.latexdraw.parsers.pst.PSTParser.ParamlinewidthContext ctx) {
		ctx.pstctx.lineWidth = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitUnkownParamSetting(final net.sf.latexdraw.parsers.pst.PSTParser.UnkownParamSettingContext ctx) {
		LOG.severe("Unkown parameter: " + ctx.getText());
	}

	/**
	 * Parses the given text to produce a colour.
	 * @param txtColor The text to parse.
	 * @return The possible created colour. The Optional cannot be null.
	 */
	Optional<Color> getColor(final String txtColor) {
		final Optional<Color> colour = DviPsColors.INSTANCE.getColour(txtColor);

		if(!colour.isPresent()) {
			LOG.severe("The following colour is unknown: " + txtColor);
		}

		return colour;
	}
}
