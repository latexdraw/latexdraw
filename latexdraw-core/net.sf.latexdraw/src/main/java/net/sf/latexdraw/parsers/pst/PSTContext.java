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

import javafx.geometry.Point2D;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.view.pst.PSTricksConstants;

import static net.sf.latexdraw.view.pst.PSTricksConstants.DEFAULT_ORIGIN;

/**
 * A PST context contains the value of the PST parameters used during the parsing and the creation of PST objects.
 * @author Arnaud BLOUIN
 */
public class PSTContext {
	AxesStyle axesStyle = PSTricksConstants.DEFAULT_AXES_STYLE;
	String arrowLeft = "";
	String arrowRight = "";
	Tuple<Double, Double> arrowSize = new Tuple<>(PSTricksConstants.DEFAULT_ARROW_SIZE_DIM, PSTricksConstants.DEFAULT_ARROW_SIZE_NUM);
	double arrowLgth = PSTricksConstants.DEFAULT_ARROW_LENGTH;
	double arrowInset = PSTricksConstants.DEFAULT_ARROW_INSET;
	Tuple<Double, Double> arrowTBar = new Tuple<>(PSTricksConstants.DEFAULT_ARROW_TBARSIZE_DIM, PSTricksConstants.DEFAULT_ARROW_TBARSIZE_NUM);
	double arrowBrLgth = PSTricksConstants.DEFAULT_ARROW_BRACKET_LGTH;
	double arrowrBrLgth = PSTricksConstants.DEFAULT_ARROW_RBRACKET_LGTH;
	Tuple<Double, Double> arrowDotSize = new Tuple<>(PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM, PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM);
	Tuple<Double, Double> arrowScale = new Tuple<>(PSTricksConstants.DEFAULT_ARROW_SCALE1, PSTricksConstants.DEFAULT_ARROW_SCALE2);
	double arcSep = PSTricksConstants.DEFAULT_ARC_SEP;
	double arcSepA = PSTricksConstants.DEFAULT_ARC_SEP_A;
	double arcSepB = PSTricksConstants.DEFAULT_ARC_SEP_B;
	boolean boxSep = PSTricksConstants.DEFAULT_BOX_SEP;
	Color borderColor = PSTricksConstants.DEFAULT_BORDER_COLOR;
	BorderPos borderPos = PSTricksConstants.DEFAULT_BORDERS_POS;
//	double border = PSTricksConstants.DEFAULT_BORDER;
	double[] curvature = new double[]{PSTricksConstants.DEFAULT_CURVATURE_NUM1, PSTricksConstants.DEFAULT_CRUVATURE_NUM2, PSTricksConstants.DEFAULT_CRUVATURE_NUM3};
	double dxIncrement = PSTricksConstants.DEFAULT_DX;
	double dyIncrement = PSTricksConstants.DEFAULT_DY;
	double dxLabelDist = PSTricksConstants.DEFAULT_DIST_X_LABEL;
	double dyLabelDist = PSTricksConstants.DEFAULT_DIST_Y_LABEL;
	DotStyle dotStyle = PSTricksConstants.DEFAULT_DOT_STYLE;
	Tuple<Double, Double> dotScale = new Tuple<>(PSTricksConstants.DEFAULT_DOT_SCALE1, PSTricksConstants.DEFAULT_DOT_SCALE2);
	double dotAngle = PSTricksConstants.DEFAULT_DOT_ANGLE;
	double dotStep = PSTricksConstants.DEFAULT_DOT_STEP;
//	Tuple<Double, Double> dash = new Tuple<>(PSTricksConstants.DEFAULT_DASH_BLACK, PSTricksConstants.DEFAULT_DASH_WHITE);
	boolean dbleLine = PSTricksConstants.DEFAULT_DOUBLE_LINE;
	double dbleSep = PSTricksConstants.DEFAULT_DOUBLE_SEP;
	Color dbleColor = PSTricksConstants.DEFAULT_DOUBLE_COLOR;
	double degrees = PSTricksConstants.DEFAULT_DEGREES;
	double frameSep = PSTricksConstants.DEFAULT_FRAME_SEP;
	double frameArc = PSTricksConstants.DEFAULT_FRAME_ARC;
	FillingStyle fillingStyle = PSTricksConstants.DEFAULT_FILL_STYLE;
	Color fillColor = PSTricksConstants.DEFAULT_FILL_COLOR;
	double gridWidth = PSTricksConstants.DEFAULT_GRID_WIDTH;
	double gridLabel = PSTricksConstants.DEFAULT_GRID_LABEL / PSTricksConstants.CM_VAL_PT;
	double gridDots = PSTricksConstants.DEFAULT_GRIDDOTS;
	double gradAngle = PSTricksConstants.DEFAULT_GRADIENT_ANGLE;
	Color gridColor = PSTricksConstants.DEFAULT_GRIDCOLOR;
	double gradMidPoint = PSTricksConstants.DEFAULT_GRADIENT_MID_POINT;
	Color gradBegin = PSTricksConstants.DEFAULT_GRADIENT_START_COLOR;
	Color gradEnd = PSTricksConstants.DEFAULT_GRADIENT_END_COLOR;
	int gradLines = PSTricksConstants.DEFAULT_GRADIENT_LINES;
	double gangle = PSTricksConstants.DEFAULT_GANGLE;
	double hatchWidth = PSTricksConstants.DEFAULT_HATCH_WIDTH;
	double hatchSep = PSTricksConstants.DEFAULT_HATCH_SEP;
	Color hatchCol = PSTricksConstants.DEFAULT_HATCHING_COLOR;
	double hatchAngle = PSTricksConstants.DEFAULT_HATCH_ANGLE;
	boolean isCornerRel = PSTricksConstants.DEFAULT_CORNER_SIZE_RELATIVE;
	boolean isShadow = PSTricksConstants.DEFAULT_SHADOW;
	double lineWidth = PSTricksConstants.DEFAULT_LINE_WIDTH;
	Color lineColor = PSTricksConstants.DEFAULT_LINE_COLOR;
	PlottingStyle labels = PSTricksConstants.DEFAULT_LABELS_DISPLAYED;
	double lineArc = PSTricksConstants.DEFAULT_LINE_ARC;
	LineStyle lineStyle = PSTricksConstants.DEFAULT_LINE_STYLE;
	double ox = PSTricksConstants.DEFAULT_OX;
	double oy = PSTricksConstants.DEFAULT_OY;
	boolean onRadians = PSTricksConstants.DEFAULT_ON_RADIANS;
	Tuple<Double, String> originX = new Tuple<>(DEFAULT_ORIGIN.getX(), PSTricksConstants.TOKEN_CM);
	Tuple<Double, String> originY = new Tuple<>(DEFAULT_ORIGIN.getY(), PSTricksConstants.TOKEN_CM);
//	boolean specialCoor = PSTricksConstants.DEFAULT_SPECIAL_COOR;
	boolean showPoints = PSTricksConstants.DEFAULT_SHOW_POINTS;
	boolean showOrigin = PSTricksConstants.DEFAULT_SHOW_ORIGIN;
	double subGridWidth = PSTricksConstants.DEFAULT_SUB_GRID_WIDTH;
	boolean swapAxes = PSTricksConstants.DEFAULT_SWAP_AXES;
	Color shadowCol = PSTricksConstants.DEFAULT_SHADOW_COLOR;
	Color subGridCol = PSTricksConstants.DEFAULT_SUB_GRID_COLOR;
	double shadowAngle = PSTricksConstants.DEFAULT_SHADOW_ANGLE;
	double shadowSize = PSTricksConstants.DEFAULT_SHADOW_SIZE;
	double subGridDots = PSTricksConstants.DEFAULT_SUBGRIDDOTS;
	double subGridDiv = PSTricksConstants.DEFAULT_SUBGRIDDIV;
	PlottingStyle ticks = PSTricksConstants.DEFAULT_TICKS_DISPLAYED;
	TicksStyle ticksStyle = PSTricksConstants.DEFAULT_TICKS_STYLE;
	double ticksSize = PSTricksConstants.DEFAULT_TICKS_SIZE;
	double unit = PSTricksConstants.DEFAULT_UNIT;
	double xUnit = PSTricksConstants.DEFAULT_UNIT;
	double yUnit = PSTricksConstants.DEFAULT_UNIT;
	Color textColor = DviPsColors.BLACK;
	boolean shadow = PSTricksConstants.DEFAULT_SHADOW;
	Color gridlabelcolor = PSTricksConstants.DEFAULT_LABELGRIDCOLOR;
	boolean isCentered = false;
	Point2D pictureSWPt = new Point2D(0d, 0d);
	Point2D pictureNEPt = new Point2D(0d, 0d);
	String tokenPosition = "";
	String plotStyle = "line";
	int plotPoints = 50;
	FillingStyle addfillstyle = PSTricksConstants.DEFAULT_FILL_STYLE;
//	int liftpen = 0;
	boolean isPsCustom;
	String textPosition = "";
	double rputAngle = 0d;
	boolean parsedTxtNoTxt = true;
	FontShape currFontShape = FontShape.NORMAL;
	FontSerie currFontSerie = FontSerie.NORMAL;
	FontFamily currFontFamily = FontFamily.RM;
	Point2D psCustomLatestPt = new Point2D(0d, 0d);
	double opacity = 1d;
	double strokeopacity = 1d;
	boolean polarPlot = false;

	/** Text text parsed in the current context. */
	String textParsed = "";

	public PSTContext(final boolean isCustom) {
		super();
		isPsCustom = isCustom;
	}

	public PSTContext(final PSTContext ctx) {
		this(ctx.isPsCustom);
	}

	public PSTContext(final PSTContext ctx, final boolean isCustom) {
		this(isCustom);
		axesStyle = ctx.axesStyle;
		arrowLeft = ctx.arrowLeft;
		arrowRight = ctx.arrowRight;
		arrowSize = ctx.arrowSize;
		arrowLgth = ctx.arrowLgth;
		arrowInset = ctx.arrowInset;
		arrowTBar = ctx.arrowTBar;
		arrowBrLgth = ctx.arrowBrLgth;
		arrowrBrLgth = ctx.arrowrBrLgth;
		arrowDotSize = ctx.arrowDotSize;
		arrowScale = ctx.arrowScale;
		arcSep = ctx.arcSep;
		arcSepA = ctx.arcSepA;
		arcSepB = ctx.arcSepB;
		boxSep = ctx.boxSep;
		borderColor = ctx.borderColor;
		borderPos = ctx.borderPos;
//		border = ctx.border;
		curvature = ctx.curvature;
		dxIncrement = ctx.dxIncrement;
		dyIncrement = ctx.dyIncrement;
		dxLabelDist = ctx.dxLabelDist;
		dyLabelDist = ctx.dyLabelDist;
		dotStyle = ctx.dotStyle;
		dotScale = ctx.dotScale;
		dotAngle = ctx.dotAngle;
		dotStep = ctx.dotStep;
//		dash = ctx.dash;
		dbleLine = ctx.dbleLine;
		dbleSep = ctx.dbleSep;
		dbleColor = ctx.dbleColor;
		degrees = ctx.degrees;
		frameSep = ctx.frameSep;
		frameArc = ctx.frameArc;
		fillingStyle = ctx.fillingStyle;
		fillColor = ctx.fillColor;
		gridWidth = ctx.gridWidth;
		gridLabel = ctx.gridLabel;
		gridDots = ctx.gridDots;
		gradAngle = ctx.gradAngle;
		gridColor = ctx.gridColor;
		gradMidPoint = ctx.gradMidPoint;
		gradBegin = ctx.gradBegin;
		gradEnd = ctx.gradEnd;
		gradLines = ctx.gradLines;
		gangle = ctx.gangle;
		hatchWidth = ctx.hatchWidth;
		hatchSep = ctx.hatchSep;
		hatchCol = ctx.hatchCol;
		hatchAngle = ctx.hatchAngle;
		isCornerRel = ctx.isCornerRel;
		isShadow = ctx.isShadow;
		lineWidth = ctx.lineWidth;
		lineColor = ctx.lineColor;
		labels = ctx.labels;
		lineArc = ctx.lineArc;
		lineStyle = ctx.lineStyle;
		ox = ctx.ox;
		oy = ctx.oy;
		onRadians = ctx.onRadians;
		originX = ctx.originX;
		originY = ctx.originY;
//		specialCoor = ctx.specialCoor;
		showPoints = ctx.showPoints;
		showOrigin = ctx.showOrigin;
		subGridWidth = ctx.subGridWidth;
		swapAxes = ctx.swapAxes;
		shadowCol = ctx.shadowCol;
		subGridCol = ctx.subGridCol;
		shadowAngle = ctx.shadowAngle;
		shadowSize = ctx.shadowSize;
		subGridDots = ctx.subGridDots;
		subGridDiv = ctx.subGridDiv;
		ticks = ctx.ticks;
		ticksStyle = ctx.ticksStyle;
		ticksSize = ctx.ticksSize;
		unit = ctx.unit;
		xUnit = ctx.xUnit;
		yUnit = ctx.yUnit;
		textColor = ctx.textColor;
		shadow = ctx.shadow;
		gridlabelcolor = ctx.gridlabelcolor;
		isCentered = ctx.isCentered;
		pictureSWPt = ctx.pictureSWPt;
		pictureNEPt = ctx.pictureNEPt;
		tokenPosition = ctx.tokenPosition;
		plotStyle = ctx.plotStyle;
		plotPoints = ctx.plotPoints;
		addfillstyle = ctx.addfillstyle;
//		liftpen = ctx.liftpen;
		textPosition = ctx.textPosition;
		rputAngle = ctx.rputAngle;
		parsedTxtNoTxt = ctx.parsedTxtNoTxt;
		currFontShape = ctx.currFontShape;
		currFontSerie = ctx.currFontSerie;
		currFontFamily = ctx.currFontFamily;
		psCustomLatestPt = ctx.psCustomLatestPt;
		opacity = ctx.opacity;
		strokeopacity = ctx.strokeopacity;
		polarPlot = ctx.polarPlot;
	}


	Point2D originToPoint() {
		return new Point2D(doubleUnitToUnit(originX.a, originX.b), doubleUnitToUnit(originY.a, originY.b));
	}

	static double doubleUnitToUnit(final double value, final String unit) {
		switch(unit) {
			case PSTricksConstants.TOKEN_CM: return value;
			case PSTricksConstants.TOKEN_MM: return value / 10d;
			case PSTricksConstants.TOKEN_PS_PT: return value / PSTricksConstants.CM_VAL_PT;
			case PSTricksConstants.TOKEN_INCH: return value / PSTricksConstants.INCH_VAL_CM;
			default : return value;
		}
	}

	public enum FontShape {
		ITALIC("it", "\\it"), SLANTED("sl", "\\sl"), SMALCAPS("sc", "\\sc"), NORMAL("n", "\\upshape");

		public final String pstToken;
		public final String equivCmd;

		FontShape(final String pst, final String cmd) {
			pstToken = pst;
			equivCmd = cmd;
		}
	}

	public enum FontFamily {
		RM("cmr", "\\rmfamily"), SF("cmss", "\\sffamily"), TT("cmtt", "\\ttfamily");

		public final String pstToken;
		public final String equivCmd;

		FontFamily(final String pst, final String cmd) {
			pstToken = pst;
			equivCmd = cmd;
		}
	}

	public enum FontSerie {
		NORMAL("m", "\\mdseries"), BF("b", "\\bf");

		public final String pstToken;
		public final String equivCmd;

		FontSerie(final String pst, final String cmd) {
			pstToken = pst;
			equivCmd = cmd;
		}
	}
}

