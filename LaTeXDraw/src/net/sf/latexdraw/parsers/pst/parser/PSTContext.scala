package net.sf.latexdraw.parsers.pst.parser

import java.awt.geom.Point2D
import java.awt.Color

import net.sf.latexdraw.glib.models.interfaces.IArrow
import net.sf.latexdraw.glib.models.interfaces.IAxes
import net.sf.latexdraw.glib.models.interfaces.IDot
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.views.pst.PSTricksConstants

/**
 * The companion the PSTContext used to encapsulate attributes shared by all the instances.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 2012-04-24<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
object PSTContext {
	private var isCentered = false

	private var pictureSWPt : Point2D = new Point2D.Double()

	private var pictureNEPt : Point2D = new Point2D.Double()

	private var tokenPosition = ""
}


/**
 * A PST context contains the value of the PST parameters used during the parsing
 * and the creation of PST objects.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 2012-04-24<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class PSTContext(var axesStyle : IAxes.AxesStyle, var arrowStyle : Tuple2[IArrow.ArrowStyle, IArrow.ArrowStyle], var arrowSizeD : Double,
		var arrowSizeN : Double, var arrowLgth : Double, var arrowInset : Double, var arrowTBarSD : Double, var arrowTBarSN : Double,
		var arrowBrLgth : Double, var arrowrBrLgth : Double, var arrowDotSize : Tuple2[Double,Double], var arrowScale : Tuple2[Double,Double],
		var arcSep : Double, var arcSepA : Double, var arcSepB : Double, var boxSep : Boolean,
		var borderColor : Color, var borderPos : IShape.BorderPos, var border : Double, var curvature : Tuple3[Double, Double, Double],
		var dxIncrement : Double, var dyIncrement : Double, var dxLabelDist : Double, var dyLabelDist : Double,
		var dotStyle : IDot.DotStyle, var dotScale : Tuple2[Double,Double], var dotAngle : Double, var dotStep : Double,
		var dashBlack : Double, var dashWhite : Double, var dbleLine : Boolean, var dbleSep : Double, var dbleColor : Color,
		var degrees : Double, var frameSep : Double, var frameArc : Double, var fillStyle : IShape.FillingStyle, var fillColor : Color,
		var gridWidth : Double, var gridLabel : Double, var gridDots : Double, var gradAngle : Double, var gridColor : Color,
		var gradMidPoint : Double, var gradBegin : Color, var gradEnd : Color, var gradLines : Int, var gangle : Double,
		var hatchWidth : Double, var hatchSep : Double, var hatchCol : Color, var hatchAngle : Double, var isCornerRel : Boolean, var isShadow : Boolean,
		var lineWidth : Double, var lineColor : Color, var labelsGridCol : Color, var labels : IAxes.PlottingStyle, var lineArc : Double,
		var lineStyle : IShape.LineStyle, var ox : Double, var oy : Double, var onRadians : Boolean, var origin : Point2D, var specialCoor : Boolean,
		var showPoints : Boolean, var showOrigin : Boolean, var subGridWidth : Double, var swapAxes : Boolean, var shadowCol : Color,
		var subGridCol : Color, var shadowAngle : Double, var shadowSize : Double, var subGridDots : Double, var subGridDiv : Double,
		var ticks : IAxes.PlottingStyle, var ticksStyle : IAxes.TicksStyle, var ticksSize : Double, var unit : Double, var xUnit : Double,
		var yUnit : Double, var textColor : Color, var shadow : Boolean, var gridlabelcolor : Color) {

	def this() {
		this(PSTricksConstants.DEFAULT_AXES_STYLE, Tuple2(IArrow.ArrowStyle.NONE, IArrow.ArrowStyle.NONE), PSTricksConstants.DEFAULT_ARROW_SIZE_DIM,
			PSTricksConstants.DEFAULT_ARROW_SIZE_NUM, PSTricksConstants.DEFAULT_ARROW_LENGTH, PSTricksConstants.DEFAULT_ARROW_INSET,
			PSTricksConstants.DEFAULT_ARROW_TBARSIZE_DIM, PSTricksConstants.DEFAULT_ARROW_TBARSIZE_NUM, PSTricksConstants.DEFAULT_ARROW_BRACKET_LGTH,
			PSTricksConstants.DEFAULT_ARROW_RBRACKET_LGTH, Tuple2(PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM, PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM),
			Tuple2(PSTricksConstants.DEFAULT_ARROW_SCALE1, PSTricksConstants.DEFAULT_ARROW_SCALE2), PSTricksConstants.DEFAULT_ARC_SEP,
			PSTricksConstants.DEFAULT_ARC_SEP_A, PSTricksConstants.DEFAULT_ARC_SEP_B, PSTricksConstants.DEFAULT_BOX_SEP,
			PSTricksConstants.DEFAULT_BORDER_COLOR, PSTricksConstants.DEFAULT_BORDERS_POS, PSTricksConstants.DEFAULT_BORDER,
			Tuple3(PSTricksConstants.DEFAULT_CURVATURE_NUM1, PSTricksConstants.DEFAULT_CRUVATURE_NUM2, PSTricksConstants.DEFAULT_CRUVATURE_NUM3),
			PSTricksConstants.DEFAULT_DX, PSTricksConstants.DEFAULT_DY, PSTricksConstants.DEFAULT_DIST_X_LABEL, PSTricksConstants.DEFAULT_DIST_Y_LABEL,
			PSTricksConstants.DEFAULT_DOT_STYLE, Tuple2(PSTricksConstants.DEFAULT_DOT_SCALE1, PSTricksConstants.DEFAULT_DOT_SCALE2),
			PSTricksConstants.DEFAULT_DOT_ANGLE, PSTricksConstants.DEFAULT_DOT_STEP, PSTricksConstants.DEFAULT_DASH_BLACK,
			PSTricksConstants.DEFAULT_DASH_WHITE, PSTricksConstants.DEFAULT_DOUBLE_LINE, PSTricksConstants.DEFAULT_DOUBLE_SEP,
			PSTricksConstants.DEFAULT_DOUBLE_COLOR, PSTricksConstants.DEFAULT_DEGREES, PSTricksConstants.DEFAULT_FRAME_SEP, PSTricksConstants.DEFAULT_FRAME_ARC,
			PSTricksConstants.DEFAULT_FILL_STYLE, PSTricksConstants.DEFAULT_FILL_COLOR, PSTricksConstants.DEFAULT_GRID_WIDTH,
			PSTricksConstants.DEFAULT_GRID_LABEL/PSTricksConstants.CM_VAL_PT, PSTricksConstants.DEFAULT_GRIDDOTS, PSTricksConstants.DEFAULT_GRADIENT_ANGLE,
			PSTricksConstants.DEFAULT_GRIDCOLOR, PSTricksConstants.DEFAULT_GRADIENT_MID_POINT, PSTricksConstants.DEFAULT_GRADIENT_START_COLOR,
			PSTricksConstants.DEFAULT_GRADIENT_END_COLOR, PSTricksConstants.DEFAULT_GRADIENT_LINES, PSTricksConstants.DEFAULT_GANGLE,
			PSTricksConstants.DEFAULT_HATCH_WIDTH, PSTricksConstants.DEFAULT_HATCH_SEP, PSTricksConstants.DEFAULT_HATCHING_COLOR,
			PSTricksConstants.DEFAULT_HATCH_ANGLE, PSTricksConstants.DEFAULT_CORNER_SIZE_RELATIVE, PSTricksConstants.DEFAULT_SHADOW,
			PSTricksConstants.DEFAULT_LINE_WIDTH, PSTricksConstants.DEFAULT_LINE_COLOR, PSTricksConstants.DEFAULT_LABELGRIDCOLOR,
			PSTricksConstants.DEFAULT_LABELS_DISPLAYED, PSTricksConstants.DEFAULT_LINE_ARC, PSTricksConstants.DEFAULT_LINE_STYLE,
			PSTricksConstants.DEFAULT_OX, PSTricksConstants.DEFAULT_OY, PSTricksConstants.DEFAULT_ON_RADIANS,
			new Point2D.Double(PSTricksConstants.DEFAULT_ORIGIN.getX, PSTricksConstants.DEFAULT_ORIGIN.getY), PSTricksConstants.DEFAULT_SPECIAL_COOR,
			PSTricksConstants.DEFAULT_SHOW_POINTS, PSTricksConstants.DEFAULT_SHOW_ORIGIN, PSTricksConstants.DEFAULT_SUB_GRID_WIDTH,
			PSTricksConstants.DEFAULT_SWAP_AXES, PSTricksConstants.DEFAULT_SHADOW_COLOR, PSTricksConstants.DEFAULT_SUB_GRID_COLOR,
			PSTricksConstants.DEFAULT_SHADOW_ANGLE, PSTricksConstants.DEFAULT_SHADOW_SIZE, PSTricksConstants.DEFAULT_SUBGRIDDOTS,
			PSTricksConstants.DEFAULT_SUBGRIDDIV, PSTricksConstants.DEFAULT_TICKS_DISPLAYED, PSTricksConstants.DEFAULT_TICKS_STYLE,
			PSTricksConstants.DEFAULT_TICKS_SIZE, PSTricksConstants.DEFAULT_UNIT, PSTricksConstants.DEFAULT_UNIT, PSTricksConstants.DEFAULT_UNIT, Color.BLACK,
			PSTricksConstants.DEFAULT_SHADOW, PSTricksConstants.DEFAULT_LABELGRIDCOLOR)
	}
//	var textItalic
//	var textBold
//	var textEnc
//	var textShape
//	var textSeries
//	var textFamily
//	var textSize	= PSTricksConstants.COMMAND_TEXT_NORMAL
//	var textParsed

	/**
	 * Creates the PST context by copying the given one.
	 */
	def this(model : PSTContext) {
		this(model.axesStyle, Tuple2(model.arrowStyle._1, model.arrowStyle._2), model.arrowSizeD, model.arrowSizeN, model.arrowLgth, model.arrowInset,
			model.arrowTBarSD, model.arrowTBarSN, model.arrowBrLgth, model.arrowrBrLgth, Tuple2(model.arrowDotSize._1, model.arrowDotSize._2),
			Tuple2(model.arrowScale._1, model.arrowScale._2), model.arcSep, model.arcSepA, model.arcSepB, model.boxSep, model.borderColor, model.borderPos,
			model.border, Tuple3(model.curvature._1, model.curvature._2, model.curvature._3), model.dxIncrement, model.dyIncrement, model.dxLabelDist,
			model.dyLabelDist, model.dotStyle,  Tuple2(model.dotScale._1,  model.dotScale._2), model.dotAngle, model.dotStep, model.dashBlack, model.dashWhite,
			model.dbleLine, model.dbleSep, model.dbleColor, model.degrees,
			  model.frameSep, model.frameArc, model.fillStyle, model.fillColor, model.gridWidth, model.gridLabel, model.gridDots, model.gradAngle,
			  model.gridColor, model.gradMidPoint, model.gradBegin, model.gradEnd, model.gradLines, model.gangle, model.hatchWidth, model.hatchSep,
			  model.hatchCol, model.hatchAngle, model.isCornerRel, model.isShadow, model.lineWidth, model.lineColor, model.labelsGridCol,
			  model.labels, model.lineArc, model.lineStyle, model.ox, model.oy, model.onRadians, new Point2D.Double(model.origin.getX, model.origin.getY),
			  model.specialCoor, model.showPoints, model.showOrigin, model.subGridWidth, model.swapAxes, model.shadowCol, model.subGridCol,
			  model.shadowAngle, model.shadowSize, model.subGridDots, model.subGridDiv, model.ticks, model.ticksStyle, model.ticksSize, model.unit,
			  model.xUnit, model.yUnit, model.textColor, model.shadow, model.gridlabelcolor)
	}


	/**
	 * Returns the value corresponding to the given parameter.
	 */
	def getParam(name : String) : Any = {
		if(name!=null)
			name match {
				case "boxsep" => boxSep
				case "showpoints" => showPoints
				case "swapaxes" => swapAxes
				case "doubleline" => dbleLine
				case "shadow" => shadow
				case "showorigin" => showOrigin
				case "linecolor" => lineColor
				case "fillcolor" => fillColor
				case "gridColor" => gridColor
				case "gridlabelcolor" => gridlabelcolor
				case "subgridcolor" => subGridCol
				case "bordercolor" => borderColor
				case "doublecolor" => dbleColor
				case "shadowcolor" => shadowCol
				case "hatchcolor" => hatchCol
				case "gradend" => gradEnd
				case "gradbegin" => gradBegin
				case "fillstyle" => fillStyle
				case "linestyle" => lineStyle
				case "dimen" => borderPos
				case "linewidth" => lineWidth
				case "shadowsize" => shadowSize
				case "doublesep" => dbleSep
				case "hatchsep" => hatchSep
				case "hatchwidth" => hatchWidth
				case "shadowangle" => shadowAngle
				case "gradangle" => gradAngle
				case "gradmidpoint" => gradMidPoint
				case "hatchangle" => hatchAngle
				case "gradlines" => gradLines
				case "framearc" => frameArc
				case "linearc" => lineArc
				case "cornersize" => isCornerRel
				case "arrows" => arrowStyle
				case "arcsep" => arcSep
				case "arcsepA" => arcSepA
				case "arcsepB" => arcSepB
				case "curvature" => curvature
				case "gangle" => gangle
				case "dotstyle" => dotStyle
				case "dotsize" => arrowDotSize
				case "dotscale" => dotScale
				case "dotangle" => dotAngle
				case _ => PSTParser.errorLogs += "Parameter unknown: " + name
			}
	}


	/**
	 * Sets the value of the given parameter.
	 */
	def setParam(name : String, value : Any) {
		if(name!=null)
			name match {
				case "boxsep" if(value.isInstanceOf[Boolean]) => boxSep = value.asInstanceOf[Boolean]
				case "showpoints" if(value.isInstanceOf[Boolean]) => showPoints = value.asInstanceOf[Boolean]
				case "swapaxes" if(value.isInstanceOf[Boolean]) => swapAxes = value.asInstanceOf[Boolean]
				case "doubleline" if(value.isInstanceOf[Boolean]) => dbleLine = value.asInstanceOf[Boolean]
				case "shadow" if(value.isInstanceOf[Boolean]) => shadow = value.asInstanceOf[Boolean]
				case "showorigin" if(value.isInstanceOf[Boolean]) => showOrigin = value.asInstanceOf[Boolean]
				case "linecolor" if(value.isInstanceOf[Color]) => lineColor = value.asInstanceOf[Color]
				case "fillcolor" if(value.isInstanceOf[Color]) => fillColor = value.asInstanceOf[Color]
				case "gridColor" if(value.isInstanceOf[Color]) => gridColor = value.asInstanceOf[Color]
				case "gridlabelcolor" if(value.isInstanceOf[Color]) => gridlabelcolor = value.asInstanceOf[Color]
				case "subgridcolor" if(value.isInstanceOf[Color]) => subGridCol = value.asInstanceOf[Color]
				case "bordercolor" if(value.isInstanceOf[Color]) => borderColor = value.asInstanceOf[Color]
				case "doublecolor" if(value.isInstanceOf[Color]) => dbleColor = value.asInstanceOf[Color]
				case "shadowcolor" if(value.isInstanceOf[Color]) => shadowCol = value.asInstanceOf[Color]
				case "hatchcolor" if(value.isInstanceOf[Color]) => hatchCol = value.asInstanceOf[Color]
				case "gradend" if(value.isInstanceOf[Color]) => gradEnd = value.asInstanceOf[Color]
				case "gradbegin" if(value.isInstanceOf[Color]) => gradBegin = value.asInstanceOf[Color]
				case "fillstyle" if(value.isInstanceOf[IShape.FillingStyle]) => fillStyle = value.asInstanceOf[IShape.FillingStyle]
				case "linestyle" if(value.isInstanceOf[IShape.LineStyle]) => lineStyle = value.asInstanceOf[IShape.LineStyle]
				case "dimen" if(value.isInstanceOf[IShape.BorderPos]) => borderPos = value.asInstanceOf[IShape.BorderPos]
				case "linewidth" if(value.isInstanceOf[Double]) => lineWidth = value.asInstanceOf[Double]
				case "shadowsize" if(value.isInstanceOf[Double]) => shadowSize = value.asInstanceOf[Double]
				case "doublesep" if(value.isInstanceOf[Double]) => dbleSep = value.asInstanceOf[Double]
				case "hatchsep" if(value.isInstanceOf[Double]) => hatchSep = value.asInstanceOf[Double]
				case "hatchwidth" if(value.isInstanceOf[Double]) => hatchWidth = value.asInstanceOf[Double]
				case "shadowangle" if(value.isInstanceOf[Double]) => shadowAngle = value.asInstanceOf[Double]
				case "gradangle" if(value.isInstanceOf[Double]) => gradAngle = value.asInstanceOf[Double]
				case "gradmidpoint" if(value.isInstanceOf[Double]) => gradMidPoint = value.asInstanceOf[Double]
				case "hatchangle" if(value.isInstanceOf[Double]) => hatchAngle = value.asInstanceOf[Double]
				case "gradlines" if(value.isInstanceOf[Int]) => gradLines = value.asInstanceOf[Int]
				case "framearc" if(value.isInstanceOf[Double]) => frameArc = value.asInstanceOf[Double]
				case "linearc" if(value.isInstanceOf[Double]) => lineArc = value.asInstanceOf[Double]
				case "arcsep" if(value.isInstanceOf[Double]) => arcSep = value.asInstanceOf[Double]
				case "arcsepA" if(value.isInstanceOf[Double]) => arcSepA = value.asInstanceOf[Double]
				case "arcsepB" if(value.isInstanceOf[Double]) => arcSepB = value.asInstanceOf[Double]
				case "gangle" if(value.isInstanceOf[Double]) => gangle = value.asInstanceOf[Double]
				case "dotangle" if(value.isInstanceOf[Double]) => dotAngle = value.asInstanceOf[Double]
				case "dotstyle" if(value.isInstanceOf[IDot.DotStyle]) => dotStyle = value.asInstanceOf[IDot.DotStyle]
				case "cornersize" if(value.isInstanceOf[Boolean]) => isCornerRel = value.asInstanceOf[Boolean]
				case "arrows" if(value.isInstanceOf[Tuple2[_, _]]) => arrowStyle = value.asInstanceOf[Tuple2[IArrow.ArrowStyle, IArrow.ArrowStyle]]
				case "dotscale" if(value.isInstanceOf[Tuple2[_, _]]) => dotScale = value.asInstanceOf[Tuple2[Double, Double]]
				case "dotsize" if(value.isInstanceOf[Tuple2[_, _]]) => arrowDotSize = value.asInstanceOf[Tuple2[Double, Double]]
				case "curvature" if(value.isInstanceOf[Tuple3[_, _, _]]) => curvature = value.asInstanceOf[Tuple3[Double, Double, Double]]
				case _ => PSTParser.errorLogs += "Parameter unknown: " + name + " " + value
			}
	}
}
