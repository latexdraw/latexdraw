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
package net.sf.latexdraw.parsers.pst;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.latex.DviPsColors;

public abstract class PSTCtxListener extends net.sf.latexdraw.parsers.pst.PSTBaseListener {
	public final Logger log = Logger.getAnonymousLogger();

	public PSTCtxListener() {
		super();
		log.setLevel(Level.SEVERE);
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
	public void exitParampolarplot(final net.sf.latexdraw.parsers.pst.PSTParser.ParampolarplotContext ctx) {
		ctx.pstctx.polarPlot = Boolean.valueOf(ctx.booleanvalue().getText());
	}

	@Override
	public void exitParamframearc(final net.sf.latexdraw.parsers.pst.PSTParser.ParamframearcContext ctx) {
		ctx.pstctx.frameArc = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamdotstyle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdotstyleContext ctx) {
		ctx.pstctx.dotStyle = ctx.style.getText();
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
	public void exitParamplotstyle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamplotstyleContext ctx) {
		ctx.pstctx.plotStyle = ctx.style.getText();
	}

	@Override
	public void exitParamplotpoints(final net.sf.latexdraw.parsers.pst.PSTParser.ParamplotpointsContext ctx) {
		ctx.pstctx.plotPoints = (int) ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamshadowangle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamshadowangleContext ctx) {
		ctx.pstctx.shadowAngle = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamgradangle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamgradangleContext ctx) {
		ctx.pstctx.gradAngle = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamgradmidpoint(final net.sf.latexdraw.parsers.pst.PSTParser.ParamgradmidpointContext ctx) {
		ctx.pstctx.gradMidPoint = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamhatchangle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamhatchangleContext ctx) {
		ctx.pstctx.hatchAngle = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamhatchsep(final net.sf.latexdraw.parsers.pst.PSTParser.ParamhatchsepContext ctx) {
		ctx.pstctx.hatchSep = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamhatchwidth(final net.sf.latexdraw.parsers.pst.PSTParser.ParamhatchwidthContext ctx) {
		ctx.pstctx.hatchWidth = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamshadowsize(final net.sf.latexdraw.parsers.pst.PSTParser.ParamshadowsizeContext ctx) {
		ctx.pstctx.shadowSize = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamdoublesep(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdoublesepContext ctx) {
		ctx.pstctx.dbleSep = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamdimen(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdimenContext ctx) {
		ctx.pstctx.dimen = ctx.type.getText();
	}

	@Override
	public void exitParamlinestyle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamlinestyleContext ctx) {
		ctx.pstctx.lineStyle = ctx.style.getText();
	}

	@Override
	public void exitParamfillstyle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamfillstyleContext ctx) {
		ctx.pstctx.fillingStyle = ctx.fillstyle().getText();
	}

	@Override
	public void exitParamfillcolor(final net.sf.latexdraw.parsers.pst.PSTParser.ParamfillcolorContext ctx) {
		getColor(ctx.WORD().getText()).ifPresent(col -> ctx.pstctx.fillColor = col);
	}

	@Override
	public void exitParamshadow(final net.sf.latexdraw.parsers.pst.PSTParser.ParamshadowContext ctx) {
		ctx.pstctx.shadow = Boolean.valueOf(ctx.booleanvalue().getText());
	}

	@Override
	public void exitParamshadowcolor(final net.sf.latexdraw.parsers.pst.PSTParser.ParamshadowcolorContext ctx) {
		getColor(ctx.WORD().getText()).ifPresent(col -> ctx.pstctx.shadowCol = col);
	}

	@Override
	public void exitParamdoublecolor(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdoublecolorContext ctx) {
		getColor(ctx.WORD().getText()).ifPresent(col -> ctx.pstctx.dbleColor = col);
	}

	@Override
	public void exitParamgradbegin(final net.sf.latexdraw.parsers.pst.PSTParser.ParamgradbeginContext ctx) {
		getColor(ctx.WORD().getText()).ifPresent(col -> ctx.pstctx.gradBegin = col);
	}

	@Override
	public void exitParamgradend(final net.sf.latexdraw.parsers.pst.PSTParser.ParamgradendContext ctx) {
		getColor(ctx.WORD().getText()).ifPresent(col -> ctx.pstctx.gradEnd = col);
	}

	@Override
	public void exitParamhatchcolor(final net.sf.latexdraw.parsers.pst.PSTParser.ParamhatchcolorContext ctx) {
		getColor(ctx.WORD().getText()).ifPresent(col -> ctx.pstctx.hatchCol = col);
	}

	@Override
	public void exitParamdoubleline(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdoublelineContext ctx) {
		ctx.pstctx.dbleLine = Boolean.valueOf(ctx.booleanvalue().getText());
	}

	@Override
	public void exitParamticks(final net.sf.latexdraw.parsers.pst.PSTParser.ParamticksContext ctx) {
		ctx.pstctx.ticks = ctx.show().getText();
	}

	@Override
	public void exitParamlabels(final net.sf.latexdraw.parsers.pst.PSTParser.ParamlabelsContext ctx) {
		ctx.pstctx.labels = ctx.show().getText();
	}

	@Override
	public void exitParamdx(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdxContext ctx) {
		ctx.pstctx.dxLabelDist = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamdy(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdyContext ctx) {
		ctx.pstctx.dyLabelDist = ctx.pstctx.valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamDx(final net.sf.latexdraw.parsers.pst.PSTParser.ParamDxContext ctx) {
		ctx.pstctx.dxIncrement = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamOx(final net.sf.latexdraw.parsers.pst.PSTParser.ParamOxContext ctx) {
		ctx.pstctx.ox = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamOy(final net.sf.latexdraw.parsers.pst.PSTParser.ParamOyContext ctx) {
		ctx.pstctx.oy = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamDy(final net.sf.latexdraw.parsers.pst.PSTParser.ParamDyContext ctx) {
		ctx.pstctx.dyIncrement = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
	}

	@Override
	public void exitParamtickstyle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamtickstyleContext ctx) {
		ctx.pstctx.ticksStyle = ctx.style.getText();
	}

	@Override
	public void exitParamshoworigin(final net.sf.latexdraw.parsers.pst.PSTParser.ParamshoworiginContext ctx) {
		ctx.pstctx.showOrigin = Boolean.valueOf(ctx.booleanvalue().getText());
	}

	@Override
	public void exitParamaxesstyle(final net.sf.latexdraw.parsers.pst.PSTParser.ParamaxesstyleContext ctx) {
		ctx.pstctx.axesStyle = ctx.style.getText();
	}

	@Override
	public void exitParamshowpoints(final net.sf.latexdraw.parsers.pst.PSTParser.ParamshowpointsContext ctx) {
		ctx.pstctx.showPoints = Boolean.valueOf(ctx.booleanvalue().getText());
	}

	@Override
	public void exitParamlinearc(final net.sf.latexdraw.parsers.pst.PSTParser.ParamlinearcContext ctx) {
		super.exitParamlinearc(ctx);
	}

	@Override
	public void exitParamcornersize(final net.sf.latexdraw.parsers.pst.PSTParser.ParamcornersizeContext ctx) {
		super.exitParamcornersize(ctx);
	}

	@Override
	public void exitParamarcsepA(final net.sf.latexdraw.parsers.pst.PSTParser.ParamarcsepAContext ctx) {
		super.exitParamarcsepA(ctx);
	}

	@Override
	public void exitParamarcsepB(final net.sf.latexdraw.parsers.pst.PSTParser.ParamarcsepBContext ctx) {
		super.exitParamarcsepB(ctx);
	}

	@Override
	public void exitParamarcsep(final net.sf.latexdraw.parsers.pst.PSTParser.ParamarcsepContext ctx) {
		super.exitParamarcsep(ctx);
	}

	@Override
	public void exitParamstrokeopacity(final net.sf.latexdraw.parsers.pst.PSTParser.ParamstrokeopacityContext ctx) {
		final double opacity = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
		if(opacity >= 0d && opacity <= 1d) {
			ctx.pstctx.strokeopacity = opacity;
		}
	}

	@Override
	public void exitParamopacity(final net.sf.latexdraw.parsers.pst.PSTParser.ParamopacityContext ctx) {
		final double opacity = ctx.pstctx.numberToDouble(ctx.NUMBER().getSymbol());
		if(opacity >= 0d && opacity <= 1d) {
			ctx.pstctx.opacity = opacity;
		}
	}

	@Override
	public void exitUnknownParamSetting(final net.sf.latexdraw.parsers.pst.PSTParser.UnknownParamSettingContext ctx) {
		log.severe("Unkown parameter");
	}

	/**
	 * Parses the given text to produce a colour.
	 * @param txtColor The text to parse.
	 * @return The possible created colour. The Optional cannot be null.
	 */
	Optional<Color> getColor(final String txtColor) {
		final Optional<Color> colour = DviPsColors.INSTANCE.getColour(txtColor);

		if(!colour.isPresent()) {
			log.severe("The following colour is unknown: " + txtColor);
		}

		return colour;
	}
}
