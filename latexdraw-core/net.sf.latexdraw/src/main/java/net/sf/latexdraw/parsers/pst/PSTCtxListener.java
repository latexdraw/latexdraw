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

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

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
	public void exitParamRbracketlength(final net.sf.latexdraw.parsers.pst.PSTParser.ParamRbracketlengthContext ctx) {
		ctx.pstctx.arrowrBrLgth = numberToDouble(ctx.NUMBER());
	}

	@Override
	public void exitParamBracketlength(final net.sf.latexdraw.parsers.pst.PSTParser.ParamBracketlengthContext ctx) {
		ctx.pstctx.arrowBrLgth = numberToDouble(ctx.NUMBER());
	}

	@Override
	public void exitParamArrowinset(final net.sf.latexdraw.parsers.pst.PSTParser.ParamArrowinsetContext ctx) {
		ctx.pstctx.arrowInset = numberToDouble(ctx.NUMBER());
	}

	@Override
	public void exitParamArrowlength(final net.sf.latexdraw.parsers.pst.PSTParser.ParamArrowlengthContext ctx) {
		ctx.pstctx.arrowLgth = numberToDouble(ctx.NUMBER());
	}

	@Override
	public void exitParamtbarsize(final net.sf.latexdraw.parsers.pst.PSTParser.ParamtbarsizeContext ctx) {
		ctx.pstctx.arrowTBar = valNumNumberToDoubles(ctx.valueDim(), ctx.NUMBER());
	}

	@Override
	public void exitParamarrowsize(final net.sf.latexdraw.parsers.pst.PSTParser.ParamarrowsizeContext ctx) {
		ctx.pstctx.arrowSize = valNumNumberToDoubles(ctx.valueDim(), ctx.NUMBER());
	}

	@Override
	public void exitParamunit(final net.sf.latexdraw.parsers.pst.PSTParser.ParamunitContext ctx) {
		ctx.pstctx.unit = valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamxunit(final net.sf.latexdraw.parsers.pst.PSTParser.ParamxunitContext ctx) {
		ctx.pstctx.xUnit = valDimtoDouble(ctx.valueDim());
	}

	@Override
	public void exitParamyunit(final net.sf.latexdraw.parsers.pst.PSTParser.ParamyunitContext ctx) {
		ctx.pstctx.yUnit = valDimtoDouble(ctx.valueDim());
	}

//	@Override
//	public void exitParamborder(final PSTParser.ParamborderContext ctx) {
//	}

	@Override
	public void exitParamdotsep(final net.sf.latexdraw.parsers.pst.PSTParser.ParamdotsepContext ctx) {
		ctx.pstctx.dotStep = valDimtoDouble(ctx.valueDim());
	}

//	@Override
//	public void exitParamdash(final PSTParser.ParamdashContext ctx) {
//		ctx.pstctx.dash = new Tuple<>(valDimtoDouble(ctx.valueDim()), valDimtoDouble(ctx.valueDim()));
//	}

	@Override
	public void exitParamframearc(final net.sf.latexdraw.parsers.pst.PSTParser.ParamframearcContext ctx) {
		ctx.pstctx.frameArc = numberToDouble(ctx.NUMBER());
	}

	@Override
	public void exitUnkownParamSetting(final net.sf.latexdraw.parsers.pst.PSTParser.UnkownParamSettingContext ctx) {
		LOG.severe("Unkown parameter: " + ctx.name.getText());
	}

	double numberToDouble(final TerminalNode node) {
		return valToDouble(node.getText());
	}

	double valDimtoDouble(final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext valdim) {
		return PSTContext.doubleUnitToUnit(valToDouble(valdim.NUMBER().getText()), unitOrEmpty(valdim.unit()));
	}

	Tuple<Double, Double> valNumNumberToDoubles(final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext valdim, final TerminalNode number) {
		return new Tuple<>(valDimtoDouble(valdim), numberOrZero(number));
	}

	double fromXvalDimToCoord(final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext valDim, final PSTContext ctx) {
		if(valDim == null) return PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE * getPPC();
		return PSTContext.doubleUnitToUnit(valToDouble(valDim.NUMBER().getText()) * getPPC() * ctx.xUnit * ctx.unit, unitOrEmpty(valDim.unit()));
	}

	double fromYvalDimToCoord(final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext valDim, final PSTContext ctx) {
		if(valDim == null) return -PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE * getPPC();
		return -PSTContext.doubleUnitToUnit(valToDouble(valDim.NUMBER().getText()) * getPPC() * ctx.yUnit * ctx.unit, unitOrEmpty(valDim.unit()));
	}

	String unitOrEmpty(final net.sf.latexdraw.parsers.pst.PSTParser.UnitContext unit) {
		return unit == null ? "" : unit.getText();
	}

	double numberOrZero(final TerminalNode node) {
		return node==null ? 0d : valToDouble(node.getText());
	}

	/**
	 * Converts the given parsed coordinate into a valid Java value.
	 */
	double valToDouble(final String val) {
		return Double.valueOf(val.replace("+", "").replace("--", ""));
	}

	boolean starredCmd(final Token cmd) {
		return cmd.getText().endsWith("*");
	}

	abstract double getPPC();
}
