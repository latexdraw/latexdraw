package net.sf.latexdraw.parsers.pst.parser

import java.awt.geom.Point2D
import java.awt.Color
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IArrow
import net.sf.latexdraw.glib.models.interfaces.IAxes
import net.sf.latexdraw.glib.models.interfaces.IDot
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.views.pst.PSTricksConstants
import scala.collection.mutable.MutableList


/**
 * The different kinds of font shapes.
 */
object fontShape extends Enumeration {
	case class FontShapeVal(pstToken:String, equivCmd:String) extends Val
	val italic    = FontShapeVal("it", "\\it")
	val slanted   = FontShapeVal("sl", "\\sl")
	val smallCaps = FontShapeVal("sc", "\\sc")
	val normal    = FontShapeVal("n", "\\upshape")

	/**
	 * returns the font shape value corresponding to the given latex token (or None).
	 */
	def toFontShape(token:String) : Option[FontShapeVal] = {
		token match {
			case italic.pstToken => Some(italic)
			case slanted.pstToken => Some(slanted)
			case smallCaps.pstToken => Some(smallCaps)
			case normal.pstToken => Some(normal)
			case _ => None
		}
	}
}

/**
 * The different kinds of font families.
 */
object fontFamily extends Enumeration {
	case class FontFamilyVal(pstToken:String, equivCmd:String) extends Val
	val rm 	 = FontFamilyVal("cmr", "\\rmfamily")
	val sf 	 = FontFamilyVal("cmss", "\\sffamily")
	val tt 	 = FontFamilyVal("cmtt", "\\ttfamily")

	/**
	 * returns the font family value corresponding to the given latex token (or None).
	 */
	def toFontFamily(token:String) : Option[FontFamilyVal] = {
		token match {
			case rm.pstToken => Some(rm)
			case sf.pstToken => Some(sf)
			case tt.pstToken => Some(tt)
			case _ => None
		}
	}
}

/**
 * The different kinds of font series.
 */
object fontSerie extends Enumeration {
	case class FontSerieVal(pstToken:String, equivCmd:String) extends Val
	val normal 	= FontSerieVal("m", "\\mdseries")
	val bf 		= FontSerieVal("b", "\\bf")

	/**
	 * returns the font serie value corresponding to the given latex token (or None).
	 */
	def toFontSerie(token:String) : Option[FontSerieVal] = {
		token match {
			case normal.pstToken => Some(normal)
			case bf.pstToken => Some(bf)
			case _ => None
		}
	}
}

import fontShape._
import fontFamily._
import fontSerie._



/**
 * A PST context contains the value of the PST parameters used during the parsing
 * and the creation of PST objects.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
class PSTContext(var axesStyle : IAxes.AxesStyle, var arrowStyle : Tuple2[IArrow.ArrowStyle, IArrow.ArrowStyle], var arrowSize : Tuple2[Double,Double],
		var arrowLgth : Double, var arrowInset : Double, var arrowTBar : Tuple2[Double,Double],
		var arrowBrLgth : Double, var arrowrBrLgth : Double, var arrowDotSize : Tuple2[Double,Double], var arrowScale : Tuple2[Double,Double],
		var arcSep : Double, var arcSepA : Double, var arcSepB : Double, var boxSep : Boolean,
		var borderColor : Color, var borderPos : IShape.BorderPos, var border : Double, var curvature : Tuple3[Double, Double, Double],
		var dxIncrement : Double, var dyIncrement : Double, var dxLabelDist : Double, var dyLabelDist : Double,
		var dotStyle : IDot.DotStyle, var dotScale : Tuple2[Double,Double], var dotAngle : Double, var dotSep : Double,
		var dash : Tuple2[Double,Double], var dbleLine : Boolean, var dbleSep : Double, var dbleColor : Color,
		var degrees : Double, var frameSep : Double, var frameArc : Double, var fillStyle : IShape.FillingStyle, var fillColor : Color,
		var gridWidth : Double, var gridLabel : Double, var gridDots : Double, var gradAngle : Double, var gridColor : Color,
		var gradMidPoint : Double, var gradBegin : Color, var gradEnd : Color, var gradLines : Int, var gangle : Double,
		var hatchWidth : Double, var hatchSep : Double, var hatchCol : Color, var hatchAngle : Double, var isCornerRel : Boolean, var isShadow : Boolean,
		var lineWidth : Double, var lineColor : Color, var labels : IAxes.PlottingStyle, var lineArc : Double,
		var lineStyle : IShape.LineStyle, var ox : Double, var oy : Double, var onRadians : Boolean, var origin : PointUnit, var specialCoor : Boolean,
		var showPoints : Boolean, var showOrigin : Boolean, var subGridWidth : Double, var swapAxes : Boolean, var shadowCol : Color,
		var subGridCol : Color, var shadowAngle : Double, var shadowSize : Double, var subGridDots : Double, var subGridDiv : Double,
		var ticks : IAxes.PlottingStyle, var ticksStyle : IAxes.TicksStyle, var ticksSize : Double, var unit : Double, var xUnit : Double,
		var yUnit : Double, var textColor : Color, var shadow : Boolean, var gridlabelcolor : Color, var isCentered : Boolean,
		var pictureSWPt : IPoint, var pictureNEPt : IPoint, var tokenPosition : String, var plotStyle : String, var plotPoints : Int,
		var addfillstyle : IShape.FillingStyle, var liftpen : Int, var isPsCustom : Boolean, var textPosition : String,
		var rputAngle : Double, var parsedTxtNoTxt:Boolean, var currFontShape:FontShapeVal, var currFontSerie:FontSerieVal,
		var currFontFamily:FontFamilyVal, val psCustomLatestPt : IPoint) {

	/** Text text parsed in the current context. */
	var textParsed : String = ""

	def this(psCustom : Boolean) {
		this(PSTricksConstants.DEFAULT_AXES_STYLE, Tuple2(IArrow.ArrowStyle.NONE, IArrow.ArrowStyle.NONE), Tuple2(PSTricksConstants.DEFAULT_ARROW_SIZE_DIM,
			PSTricksConstants.DEFAULT_ARROW_SIZE_NUM), PSTricksConstants.DEFAULT_ARROW_LENGTH, PSTricksConstants.DEFAULT_ARROW_INSET,
			Tuple2(PSTricksConstants.DEFAULT_ARROW_TBARSIZE_DIM, PSTricksConstants.DEFAULT_ARROW_TBARSIZE_NUM), PSTricksConstants.DEFAULT_ARROW_BRACKET_LGTH,
			PSTricksConstants.DEFAULT_ARROW_RBRACKET_LGTH, Tuple2(PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM, PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM),
			Tuple2(PSTricksConstants.DEFAULT_ARROW_SCALE1, PSTricksConstants.DEFAULT_ARROW_SCALE2), PSTricksConstants.DEFAULT_ARC_SEP,
			PSTricksConstants.DEFAULT_ARC_SEP_A, PSTricksConstants.DEFAULT_ARC_SEP_B, PSTricksConstants.DEFAULT_BOX_SEP,
			PSTricksConstants.DEFAULT_BORDER_COLOR, PSTricksConstants.DEFAULT_BORDERS_POS, PSTricksConstants.DEFAULT_BORDER,
			Tuple3(PSTricksConstants.DEFAULT_CURVATURE_NUM1, PSTricksConstants.DEFAULT_CRUVATURE_NUM2, PSTricksConstants.DEFAULT_CRUVATURE_NUM3),
			PSTricksConstants.DEFAULT_DX, PSTricksConstants.DEFAULT_DY, PSTricksConstants.DEFAULT_DIST_X_LABEL, PSTricksConstants.DEFAULT_DIST_Y_LABEL,
			PSTricksConstants.DEFAULT_DOT_STYLE, Tuple2(PSTricksConstants.DEFAULT_DOT_SCALE1, PSTricksConstants.DEFAULT_DOT_SCALE2),
			PSTricksConstants.DEFAULT_DOT_ANGLE, PSTricksConstants.DEFAULT_DOT_STEP, Tuple2(PSTricksConstants.DEFAULT_DASH_BLACK,
			PSTricksConstants.DEFAULT_DASH_WHITE), PSTricksConstants.DEFAULT_DOUBLE_LINE, PSTricksConstants.DEFAULT_DOUBLE_SEP,
			PSTricksConstants.DEFAULT_DOUBLE_COLOR, PSTricksConstants.DEFAULT_DEGREES, PSTricksConstants.DEFAULT_FRAME_SEP, PSTricksConstants.DEFAULT_FRAME_ARC,
			PSTricksConstants.DEFAULT_FILL_STYLE, PSTricksConstants.DEFAULT_FILL_COLOR, PSTricksConstants.DEFAULT_GRID_WIDTH,
			PSTricksConstants.DEFAULT_GRID_LABEL/PSTricksConstants.CM_VAL_PT, PSTricksConstants.DEFAULT_GRIDDOTS, PSTricksConstants.DEFAULT_GRADIENT_ANGLE,
			PSTricksConstants.DEFAULT_GRIDCOLOR, PSTricksConstants.DEFAULT_GRADIENT_MID_POINT, PSTricksConstants.DEFAULT_GRADIENT_START_COLOR,
			PSTricksConstants.DEFAULT_GRADIENT_END_COLOR, PSTricksConstants.DEFAULT_GRADIENT_LINES, PSTricksConstants.DEFAULT_GANGLE,
			PSTricksConstants.DEFAULT_HATCH_WIDTH, PSTricksConstants.DEFAULT_HATCH_SEP, PSTricksConstants.DEFAULT_HATCHING_COLOR,
			PSTricksConstants.DEFAULT_HATCH_ANGLE, PSTricksConstants.DEFAULT_CORNER_SIZE_RELATIVE, PSTricksConstants.DEFAULT_SHADOW,
			PSTricksConstants.DEFAULT_LINE_WIDTH, PSTricksConstants.DEFAULT_LINE_COLOR,
			PSTricksConstants.DEFAULT_LABELS_DISPLAYED, PSTricksConstants.DEFAULT_LINE_ARC, PSTricksConstants.DEFAULT_LINE_STYLE,
			PSTricksConstants.DEFAULT_OX, PSTricksConstants.DEFAULT_OY, PSTricksConstants.DEFAULT_ON_RADIANS,
			new PointUnit(PSTricksConstants.DEFAULT_ORIGIN.getX, PSTricksConstants.DEFAULT_ORIGIN.getY, "", ""), PSTricksConstants.DEFAULT_SPECIAL_COOR,
			PSTricksConstants.DEFAULT_SHOW_POINTS, PSTricksConstants.DEFAULT_SHOW_ORIGIN, PSTricksConstants.DEFAULT_SUB_GRID_WIDTH,
			PSTricksConstants.DEFAULT_SWAP_AXES, PSTricksConstants.DEFAULT_SHADOW_COLOR, PSTricksConstants.DEFAULT_SUB_GRID_COLOR,
			PSTricksConstants.DEFAULT_SHADOW_ANGLE, PSTricksConstants.DEFAULT_SHADOW_SIZE, PSTricksConstants.DEFAULT_SUBGRIDDOTS,
			PSTricksConstants.DEFAULT_SUBGRIDDIV, PSTricksConstants.DEFAULT_TICKS_DISPLAYED, PSTricksConstants.DEFAULT_TICKS_STYLE,
			PSTricksConstants.DEFAULT_TICKS_SIZE, PSTricksConstants.DEFAULT_UNIT, PSTricksConstants.DEFAULT_UNIT, PSTricksConstants.DEFAULT_UNIT, Color.BLACK,
			PSTricksConstants.DEFAULT_SHADOW, PSTricksConstants.DEFAULT_LABELGRIDCOLOR, false, DrawingTK.getFactory.createPoint,
			DrawingTK.getFactory.createPoint, "", "line", 50, PSTricksConstants.DEFAULT_FILL_STYLE, 0, psCustom, "", 0, true,
			fontShape.normal, fontSerie.normal, fontFamily.rm, DrawingTK.getFactory.createPoint)
	}


	/**
	 * Creates the PST context by copying the given one.
	 */
	def this(model : PSTContext, psCustom : Boolean) {
		this(model.axesStyle, Tuple2(model.arrowStyle._1, model.arrowStyle._2), Tuple2(model.arrowSize._1, model.arrowSize._2), model.arrowLgth, model.arrowInset,
			Tuple2(model.arrowTBar._1, model.arrowTBar._2), model.arrowBrLgth, model.arrowrBrLgth, Tuple2(model.arrowDotSize._1, model.arrowDotSize._2),
			Tuple2(model.arrowScale._1, model.arrowScale._2), model.arcSep, model.arcSepA, model.arcSepB, model.boxSep, model.borderColor, model.borderPos,
			model.border, Tuple3(model.curvature._1, model.curvature._2, model.curvature._3), model.dxIncrement, model.dyIncrement, model.dxLabelDist,
			model.dyLabelDist, model.dotStyle,  Tuple2(model.dotScale._1,  model.dotScale._2), model.dotAngle, model.dotSep, Tuple2(model.dash._1, model.dash._2),
			model.dbleLine, model.dbleSep, model.dbleColor, model.degrees,
			  model.frameSep, model.frameArc, model.fillStyle, model.fillColor, model.gridWidth, model.gridLabel, model.gridDots, model.gradAngle,
			  model.gridColor, model.gradMidPoint, model.gradBegin, model.gradEnd, model.gradLines, model.gangle, model.hatchWidth, model.hatchSep,
			  model.hatchCol, model.hatchAngle, model.isCornerRel, model.isShadow, model.lineWidth, model.lineColor,
			  model.labels, model.lineArc, model.lineStyle, model.ox, model.oy, model.onRadians, model.origin.dup,
			  model.specialCoor, model.showPoints, model.showOrigin, model.subGridWidth, model.swapAxes, model.shadowCol, model.subGridCol,
			  model.shadowAngle, model.shadowSize, model.subGridDots, model.subGridDiv, model.ticks, model.ticksStyle, model.ticksSize, model.unit,
			  model.xUnit, model.yUnit, model.textColor, model.shadow, model.gridlabelcolor, model.isCentered, DrawingTK.getFactory.createPoint(model.pictureSWPt),
			  DrawingTK.getFactory.createPoint(model.pictureNEPt), model.tokenPosition, model.plotStyle, model.plotPoints, model.fillStyle, model.liftpen, psCustom,
			  model.textPosition, model.rputAngle, model.parsedTxtNoTxt, model.currFontShape, model.currFontSerie, model.currFontFamily,
			  DrawingTK.getFactory.createPoint(model.psCustomLatestPt))

			  if(model.currFontShape!=fontShape.normal) textParsed += model.currFontShape.equivCmd
			  if(model.currFontSerie!=fontSerie.normal) textParsed += model.currFontSerie.equivCmd
			  if(model.currFontFamily!=fontFamily.rm) textParsed += model.currFontFamily.equivCmd
	}

	def this(model:PSTContext) {
		this(model, model.isPsCustom)
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
				case "gridcolor" => gridColor
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
				case "gridwidth" => gridWidth
				case "griddots" => gridDots
				case "gridlabels" => gridLabel
				case "subgriddiv" => subGridDiv
				case "subgridwidth" => subGridWidth
				case "subgriddots" => subGridDots
				case "unit" => unit
				case "xunit" => xUnit
				case "yunit" => yUnit
				case "plotstyle" => plotStyle
				case "plotpoints" => plotPoints
				case "origin" => origin
				case "dash" => dash
				case "dotsep" => dotSep
				case "border" => border
				case "addfillstyle" => addfillstyle
				case "arrowsize" => arrowSize
				case "arrowlength" => arrowLgth
				case "tbarsize" => arrowTBar
				case "bracketlength" => arrowBrLgth
				case "rbracketlength" => arrowrBrLgth
				case "arrowscale" => arrowScale
				case "liftpen" => liftpen
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
				case "gridcolor" if(value.isInstanceOf[Color]) => gridColor = value.asInstanceOf[Color]
				case "gridlabelcolor" if(value.isInstanceOf[Color]) => gridlabelcolor = value.asInstanceOf[Color]
				case "subgridcolor" if(value.isInstanceOf[Color]) => subGridCol = value.asInstanceOf[Color]
				case "bordercolor" if(value.isInstanceOf[Color]) => borderColor = value.asInstanceOf[Color]
				case "doublecolor" if(value.isInstanceOf[Color]) => dbleColor = value.asInstanceOf[Color]
				case "shadowcolor" if(value.isInstanceOf[Color]) => shadowCol = value.asInstanceOf[Color]
				case "hatchcolor" if(value.isInstanceOf[Color]) => hatchCol = value.asInstanceOf[Color]
				case "gradend" if(value.isInstanceOf[Color]) => gradEnd = value.asInstanceOf[Color]
				case "gradbegin" if(value.isInstanceOf[Color]) => gradBegin = value.asInstanceOf[Color]
				case "fillstyle" if(value.isInstanceOf[IShape.FillingStyle]) => fillStyle = value.asInstanceOf[IShape.FillingStyle]
				case "addfillstyle" if(value.isInstanceOf[IShape.FillingStyle]) => addfillstyle = value.asInstanceOf[IShape.FillingStyle]
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
				case "gridwidth" if(value.isInstanceOf[Double]) => gridWidth = value.asInstanceOf[Double]
				case "dotsep" if(value.isInstanceOf[Double]) => dotSep = value.asInstanceOf[Double]
				case "border" if(value.isInstanceOf[Double]) => border = value.asInstanceOf[Double]
				case "dotangle" if(value.isInstanceOf[Double]) => dotAngle = value.asInstanceOf[Double]
				case "griddots" if(value.isInstanceOf[Double]) => gridDots = value.asInstanceOf[Double]
				case "gridlabels" if(value.isInstanceOf[Double]) => gridLabel = value.asInstanceOf[Double]
				case "subgriddiv" if(value.isInstanceOf[Double]) => subGridDiv = value.asInstanceOf[Double]
				case "subgridwidth" if(value.isInstanceOf[Double]) => subGridWidth = value.asInstanceOf[Double]
				case "subgriddots" if(value.isInstanceOf[Double]) => subGridDots = value.asInstanceOf[Double]
				case "bracketlength" if(value.isInstanceOf[Double]) => arrowBrLgth = value.asInstanceOf[Double]
				case "rbracketlength" if(value.isInstanceOf[Double]) => arrowrBrLgth = value.asInstanceOf[Double]
				case "unit" if(value.isInstanceOf[Double]) => unit = value.asInstanceOf[Double]
				case "xunit" if(value.isInstanceOf[Double]) => xUnit = value.asInstanceOf[Double]
				case "yunit" if(value.isInstanceOf[Double]) => yUnit = value.asInstanceOf[Double]
				case "arrowlength" if(value.isInstanceOf[Double]) => arrowLgth = value.asInstanceOf[Double]
				case "arrowinset" if(value.isInstanceOf[Double]) => arrowInset = value.asInstanceOf[Double]
				case "plotpoints" if(value.isInstanceOf[Int]) => plotPoints = value.asInstanceOf[Int]
				case "plotstyle" if(value.isInstanceOf[String]) => plotStyle = value.asInstanceOf[String]
				case "dotstyle" if(value.isInstanceOf[IDot.DotStyle]) => dotStyle = value.asInstanceOf[IDot.DotStyle]
				case "cornersize" if(value.isInstanceOf[Boolean]) => isCornerRel = value.asInstanceOf[Boolean]
				case "origin" if(value.isInstanceOf[PointUnit]) => origin = value.asInstanceOf[PointUnit]
				case "liftpen" if(value.isInstanceOf[Int]) => liftpen = value.asInstanceOf[Int]
				case "arrows" if(value.isInstanceOf[Tuple2[_, _]]) => arrowStyle = value.asInstanceOf[Tuple2[IArrow.ArrowStyle, IArrow.ArrowStyle]]
				case "dotscale" if(value.isInstanceOf[Tuple2[_, _]]) => dotScale = value.asInstanceOf[Tuple2[Double, Double]]
				case "arrowscale" if(value.isInstanceOf[Tuple2[_, _]]) => arrowScale = value.asInstanceOf[Tuple2[Double, Double]]
				case "dotsize" if(value.isInstanceOf[Tuple2[_, _]]) => arrowDotSize = value.asInstanceOf[Tuple2[Double, Double]]
				case "arrowsize" if(value.isInstanceOf[Tuple2[_, _]]) => arrowSize = value.asInstanceOf[Tuple2[Double, Double]]
				case "tbarsize" if(value.isInstanceOf[Tuple2[_, _]]) => arrowTBar = value.asInstanceOf[Tuple2[Double, Double]]
				case "dash" if(value.isInstanceOf[Tuple2[_, _]]) => dash = value.asInstanceOf[Tuple2[Double, Double]]
				case "curvature" if(value.isInstanceOf[Tuple3[_, _, _]]) => curvature = value.asInstanceOf[Tuple3[Double, Double, Double]]
				case _ => PSTParser.errorLogs += "Parameter unknown: " + name + " " + value
			}
	}
}
