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

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import static net.sf.latexdraw.view.pst.PSTricksConstants.DEFAULT_ORIGIN;

/**
 * A PST context contains the value of the PST parameters used during the parsing and the creation of PST objects.
 * @author Arnaud BLOUIN
 */
public class PSTContext {
	static double PPC = 1d;

	String axesStyle = PSTricksConstants.TOKEN_AXES_STYLE_AXES;
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
	String dimen = PSTricksConstants.BORDERS_OUTSIDE;
	double dxIncrement = PSTricksConstants.DEFAULT_DX;
	double dyIncrement = PSTricksConstants.DEFAULT_DY;
	double dxLabelDist = PSTricksConstants.DEFAULT_DIST_X_LABEL;
	double dyLabelDist = PSTricksConstants.DEFAULT_DIST_Y_LABEL;
	String dotStyle = PSTricksConstants.DOT_STYLE;
	Tuple<Double, Double> dotScale = new Tuple<>(PSTricksConstants.DEFAULT_DOT_SCALE1, PSTricksConstants.DEFAULT_DOT_SCALE2);
	double dotAngle = PSTricksConstants.DEFAULT_DOT_ANGLE;
	boolean dbleLine = PSTricksConstants.DEFAULT_DOUBLE_LINE;
	double dbleSep = PSTricksConstants.DEFAULT_DOUBLE_SEP;
	Color dbleColor = PSTricksConstants.DEFAULT_DOUBLE_COLOR;
	double frameArc = PSTricksConstants.DEFAULT_FRAME_ARC;
	String fillingStyle = PSTricksConstants.TOKEN_FILL_NONE;
	Color fillColor = PSTricksConstants.DEFAULT_FILL_COLOR;
	double gridWidth = PSTricksConstants.DEFAULT_GRID_WIDTH;
	double gridLabel = PSTricksConstants.DEFAULT_GRID_LABEL / PSTricksConstants.CM_VAL_PT;
	double gridDots = PSTricksConstants.DEFAULT_GRIDDOTS;
	double gradAngle = PSTricksConstants.DEFAULT_GRADIENT_ANGLE;
	Color gridColor = PSTricksConstants.DEFAULT_GRIDCOLOR;
	double gradMidPoint = PSTricksConstants.DEFAULT_GRADIENT_MID_POINT;
	Color gradBegin = PSTricksConstants.DEFAULT_GRADIENT_START_COLOR;
	Color gradEnd = PSTricksConstants.DEFAULT_GRADIENT_END_COLOR;
	double gangle = PSTricksConstants.DEFAULT_GANGLE;
	double hatchWidth = PSTricksConstants.DEFAULT_HATCH_WIDTH;
	double hatchSep = PSTricksConstants.DEFAULT_HATCH_SEP;
	Color hatchCol = PSTricksConstants.DEFAULT_HATCHING_COLOR;
	double hatchAngle = PSTricksConstants.DEFAULT_HATCH_ANGLE;
	boolean isShadow = PSTricksConstants.DEFAULT_SHADOW;
	double lineWidth = PSTricksConstants.DEFAULT_LINE_WIDTH;
	Color lineColor = PSTricksConstants.DEFAULT_LINE_COLOR;
	String labels = PSTricksConstants.TOKEN_LABELS_DISPLAYED_ALL;
	double lineArc = PSTricksConstants.DEFAULT_LINE_ARC;
	String lineStyle = PSTricksConstants.LINE_SOLID_STYLE;
	double ox = PSTricksConstants.DEFAULT_OX;
	double oy = PSTricksConstants.DEFAULT_OY;
	Tuple<Double, String> originX = new Tuple<>(DEFAULT_ORIGIN.getX(), PSTricksConstants.TOKEN_CM);
	Tuple<Double, String> originY = new Tuple<>(DEFAULT_ORIGIN.getY(), PSTricksConstants.TOKEN_CM);
	boolean showPoints = PSTricksConstants.DEFAULT_SHOW_POINTS;
	boolean showOrigin = PSTricksConstants.DEFAULT_SHOW_ORIGIN;
	double subGridWidth = PSTricksConstants.DEFAULT_SUB_GRID_WIDTH;
	Color shadowCol = PSTricksConstants.DEFAULT_SHADOW_COLOR;
	Color subGridCol = PSTricksConstants.DEFAULT_SUB_GRID_COLOR;
	double shadowAngle = PSTricksConstants.DEFAULT_SHADOW_ANGLE;
	double shadowSize = PSTricksConstants.DEFAULT_SHADOW_SIZE;
	double subGridDots = PSTricksConstants.DEFAULT_SUBGRIDDOTS;
	double subGridDiv = PSTricksConstants.DEFAULT_SUBGRIDDIV;
	String ticks = PSTricksConstants.TOKEN_LABELS_DISPLAYED_ALL;
	String ticksStyle = PSTricksConstants.TOKEN_TICKS_STYLE_FULL;
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
	String textPosition = "";
	double rputAngle = 0d;
	double opacity = 1d;
	double strokeopacity = 1d;
	boolean polarPlot = false;

	/** Text chunks parsed in the current context. */
	List<String> textParsed = new ArrayList<>();

	public PSTContext() {
		super();
	}

	public PSTContext(final PSTContext ctx, final boolean shareTexts) {
		this();
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
//		boxSep = ctx.boxSep;
//		borderColor = ctx.borderColor;
		dimen = ctx.dimen;
//		curvature = ctx.curvature;
		dxIncrement = ctx.dxIncrement;
		dyIncrement = ctx.dyIncrement;
		dxLabelDist = ctx.dxLabelDist;
		dyLabelDist = ctx.dyLabelDist;
		dotStyle = ctx.dotStyle;
		dotScale = ctx.dotScale;
		dotAngle = ctx.dotAngle;
//		dotStep = ctx.dotStep;
		dbleLine = ctx.dbleLine;
		dbleSep = ctx.dbleSep;
		dbleColor = ctx.dbleColor;
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
		gangle = ctx.gangle;
		hatchWidth = ctx.hatchWidth;
		hatchSep = ctx.hatchSep;
		hatchCol = ctx.hatchCol;
		hatchAngle = ctx.hatchAngle;
		isShadow = ctx.isShadow;
		lineWidth = ctx.lineWidth;
		lineColor = ctx.lineColor;
		labels = ctx.labels;
		lineArc = ctx.lineArc;
		lineStyle = ctx.lineStyle;
		ox = ctx.ox;
		oy = ctx.oy;
		originX = ctx.originX;
		originY = ctx.originY;
		showPoints = ctx.showPoints;
		showOrigin = ctx.showOrigin;
		subGridWidth = ctx.subGridWidth;
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
		textPosition = ctx.textPosition;
		rputAngle = ctx.rputAngle;
		opacity = ctx.opacity;
		strokeopacity = ctx.strokeopacity;
		polarPlot = ctx.polarPlot;

		if(shareTexts) {
			textParsed = ctx.textParsed;
		}
	}


	Point2D originToPoint() {
		return new Point2D(doubleUnitToUnit(originX.a, originX.b), doubleUnitToUnit(originY.a, originY.b));
	}

	void setPspicturePoints(final net.sf.latexdraw.parsers.pst.PSTParser.CoordContext coord1,
							final net.sf.latexdraw.parsers.pst.PSTParser.CoordContext coord2) {
		final Point2D p1 = coordToRawPoint(coord1);
		final Point2D p2 = coordToRawPoint(coord2);

		if(coord1 == null) {
			if(coord2 == null) {
				pictureSWPt = new Point2D(0d, 0d);
				pictureNEPt = new Point2D(10d, 10d);
			}
		}else {
			if(coord2 == null) {
				pictureSWPt = new Point2D(0d, 0d);
				pictureNEPt = new Point2D(p1.getX(), p1.getY());
			}else {
				pictureSWPt = new Point2D(p1.getX(), p1.getY());
				pictureNEPt = new Point2D(p2.getX(), p2.getY());
			}
		}
	}

	void setRputAngle(final Token star, final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext valDim, final net.sf.latexdraw.parsers.pst.PSTParser.PutContext put) {
		if(put != null) {
			switch(put.getText()) {
				case "L":
					rputAngle += -Math.PI / 2d;
					break;
				case "D":
					rputAngle += -Math.PI;
					break;
				case "R":
					rputAngle += -3d * Math.PI / 2d;
					break;
				case "N":
					rputAngle = 0d;
					break;
				case "W":
					rputAngle = -Math.PI / 2d;
					break;
				case "S":
					rputAngle = -Math.PI;
					break;
				case "E":
					rputAngle = -3d * Math.PI / 2d;
					break;
			}
		}else {
			final double angle = -Math.toRadians(valDimtoDouble(valDim));
			if(star != null) {
				rputAngle = angle;
			}else {
				rputAngle += angle;
			}
		}
	}

	/**
	 * Converts the given parsed coordinate into a valid Java value.
	 */
	double valToDouble(final String val) {
		return Double.valueOf(val.replace("+", "").replace("--", ""));
	}

	double numberToDouble(final Token node) {
		return valToDouble(node.getText());
	}

	double valDimtoDouble(final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext valdim) {
		if(valdim == null) return PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE;
		return PSTContext.doubleUnitToUnit(valToDouble(valdim.NUMBER().getText()), unitOrEmpty(valdim.unit()));
	}

	Tuple<Double, Double> valNumNumberToDoubles(final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext valdim, final TerminalNode number) {
		return new Tuple<>(valDimtoDouble(valdim), numberOrZero(number));
	}

	String unitOrEmpty(final net.sf.latexdraw.parsers.pst.PSTParser.UnitContext unit) {
		return unit == null ? "" : unit.getText();
	}

	double numberOrZero(final TerminalNode node) {
		return node==null ? 0d : valToDouble(node.getText());
	}

	boolean starredCmd(final Token cmd) {
		return cmd.getText().endsWith("*");
	}

	double fromXvalDimToCoord(final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext valDim) {
		if(valDim == null) return PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE * PPC;
		final double xunit = valDim.unit() == null ? xUnit * unit : 1d;
		return PSTContext.doubleUnitToUnit(valToDouble(valDim.NUMBER().getText()) * PPC * xunit, unitOrEmpty(valDim.unit()));
	}

	double fromYvalDimToCoord(final net.sf.latexdraw.parsers.pst.PSTParser.ValueDimContext valDim) {
		if(valDim == null) return -PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE * PPC;
		final double yunit = valDim.unit() == null ? yUnit * unit : 1d;
		return -PSTContext.doubleUnitToUnit(valToDouble(valDim.NUMBER().getText()) * PPC * yunit, unitOrEmpty(valDim.unit()));
	}

	/**
	 * Converts a coord token into a raw point (no adaptation to be a coordinate done).
	 * @param coord The coord to convert.
	 * @return The transformed point. Cannot be null.
	 */
	Point2D coordToRawPoint(final net.sf.latexdraw.parsers.pst.PSTParser.CoordContext coord) {
		if(coord == null) return new Point2D(PSTContext.doubleUnitToUnit(originX.a, originX.b), PSTContext.doubleUnitToUnit(originY.a, originY.b));
		return new Point2D(valDimtoDouble(coord.x), valDimtoDouble(coord.y));
	}

	/**
	 * Converts a coord token into a point. The point is adapted to be a coordinated: negative Y, PPC used.
	 * @param coord The coord to convert.
	 * @return The transformed point. Cannot be null.
	 */
	Point2D coordToAdjustedPoint(final net.sf.latexdraw.parsers.pst.PSTParser.CoordContext coord) {
		if(coord == null) return new Point2D(PSTContext.doubleUnitToUnit(originX.a, originX.b) * PPC, PSTContext.doubleUnitToUnit(originY.a, originY.b) * PPC);
		return new Point2D(fromXvalDimToCoord(coord.x), fromYvalDimToCoord(coord.y));
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
}

