package net.sf.latexdraw.parsers.pst.parser

import java.awt.geom.Point2D
import net.sf.latexdraw.glib.models.interfaces.IAxes
import scala.collection.mutable.ListBuffer
import net.sf.latexdraw.glib.models.interfaces.IArrow
import net.sf.latexdraw.glib.views.pst.PSTricksConstants
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.IDot
import java.awt.Color

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
class PSTContext {
	var axesStyle 	= PSTricksConstants.DEFAULT_AXES_STYLE
	var arrowStyle 	= new ListBuffer[IArrow.ArrowStyle]
	var arrowSizeD 	= PSTricksConstants.DEFAULT_ARROW_SIZE_DIM
	var arrowSizeN 	= PSTricksConstants.DEFAULT_ARROW_SIZE_NUM
	var arrowLgth 	= PSTricksConstants.DEFAULT_ARROW_LENGTH
	var arrowInset 	= PSTricksConstants.DEFAULT_ARROW_INSET
	var arrowTBarSD	= PSTricksConstants.DEFAULT_ARROW_TBARSIZE_DIM
	var arrowTBarSN = PSTricksConstants.DEFAULT_ARROW_TBARSIZE_NUM
	var arrowBrLgth = PSTricksConstants.DEFAULT_ARROW_BRACKET_LGTH
	var arrowrBrLgth= PSTricksConstants.DEFAULT_ARROW_RBRACKET_LGTH
	var arrowDotSD	= PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM
	var arrowDotSN 	= PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM
	var arrowScale1 = PSTricksConstants.DEFAULT_ARROW_SCALE1
	var arrowScale2 = PSTricksConstants.DEFAULT_ARROW_SCALE2
	var arcSep 		= PSTricksConstants.DEFAULT_ARC_SEP
	var arcSepA 	= PSTricksConstants.DEFAULT_ARC_SEP_A
	var arcSepB 	= PSTricksConstants.DEFAULT_ARC_SEP_B
	var boxSep		= PSTricksConstants.DEFAULT_BOX_SEP
	var borderColor = PSTricksConstants.DEFAULT_BORDER_COLOR
	var borderPos	= PSTricksConstants.DEFAULT_BORDERS_POS
	var border 		= PSTricksConstants.DEFAULT_BORDER
	var curvature1 	= PSTricksConstants.DEFAULT_CURVATURE_NUM1
	var curvature2 	= PSTricksConstants.DEFAULT_CRUVATURE_NUM2
	var curvature3 	= PSTricksConstants.DEFAULT_CRUVATURE_NUM3
	var dxIncrement = PSTricksConstants.DEFAULT_DX
	var dyIncrement = PSTricksConstants.DEFAULT_DY
	var dxLabelDist = PSTricksConstants.DEFAULT_DIST_X_LABEL
	var dyLabelDist = PSTricksConstants.DEFAULT_DIST_Y_LABEL
	var dotStyle	= PSTricksConstants.DEFAULT_DOT_STYLE
	var dotScale1	= PSTricksConstants.DEFAULT_DOT_SCALE1
	var dotScale2	= PSTricksConstants.DEFAULT_DOT_SCALE2
	var dotAngle	= PSTricksConstants.DEFAULT_DOT_ANGLE
	var dotStep		= PSTricksConstants.DEFAULT_DOT_STEP
	var dashBlack	= PSTricksConstants.DEFAULT_DASH_BLACK
	var dashWhite	= PSTricksConstants.DEFAULT_DASH_WHITE
	var dbleLine	= PSTricksConstants.DEFAULT_DOUBLE_LINE
	var dbleSep		= PSTricksConstants.DEFAULT_DOUBLE_SEP
	var dbleColor	= PSTricksConstants.DEFAULT_DOUBLE_COLOR
	var degrees		= PSTricksConstants.DEFAULT_DEGREES
	var frameSep	= PSTricksConstants.DEFAULT_FRAME_SEP
	var frameArc	= PSTricksConstants.DEFAULT_FRAME_ARC
	var fillStyle	= PSTricksConstants.DEFAULT_FILL_STYLE
	var fillColor	= PSTricksConstants.DEFAULT_FILL_COLOR
	var gridWidth	= PSTricksConstants.DEFAULT_GRID_WIDTH
	var gridLabel	= PSTricksConstants.DEFAULT_GRID_LABEL/PSTricksConstants.CM_VAL_PT
	var gridDots	= PSTricksConstants.DEFAULT_GRIDDOTS
	var gradAngle	= PSTricksConstants.DEFAULT_GRADIENT_ANGLE
	var gridColor	= PSTricksConstants.DEFAULT_GRIDCOLOR
	var gradMidPoint= PSTricksConstants.DEFAULT_GRADIENT_MID_POINT
	var gradBegin	= PSTricksConstants.DEFAULT_GRADIENT_START_COLOR
	var gradEnd		= PSTricksConstants.DEFAULT_GRADIENT_END_COLOR
	var gradLines	= PSTricksConstants.DEFAULT_GRADIENT_LINES
	var gangle		= PSTricksConstants.DEFAULT_GANGLE
	var hatchWidth	= PSTricksConstants.DEFAULT_HATCH_WIDTH
	var hatchSep	= PSTricksConstants.DEFAULT_HATCH_SEP
	var hatchCol	= PSTricksConstants.DEFAULT_HATCHING_COLOR
	var hatchAngle	= PSTricksConstants.DEFAULT_HATCH_ANGLE
	var isCornerRel	= PSTricksConstants.DEFAULT_CORNER_SIZE_RELATIVE
	var isShadow	= PSTricksConstants.DEFAULT_SHADOW
	var lineWidth	= PSTricksConstants.DEFAULT_LINE_WIDTH
	var lineColor	= PSTricksConstants.DEFAULT_LINE_COLOR
	var labelsGridCol= PSTricksConstants.DEFAULT_LABELGRIDCOLOR
	var labels		= PSTricksConstants.DEFAULT_LABELS_DISPLAYED
	var lineArc		= PSTricksConstants.DEFAULT_LINE_ARC
	var lineStyle	= PSTricksConstants.DEFAULT_LINE_STYLE
	var ox			= PSTricksConstants.DEFAULT_OX
	var oy			= PSTricksConstants.DEFAULT_OY
	var onRadians	= PSTricksConstants.DEFAULT_ON_RADIANS
	var origin : Point2D = new Point2D.Double(PSTricksConstants.DEFAULT_ORIGIN.getX, PSTricksConstants.DEFAULT_ORIGIN.getY)
	var specialCoor	= PSTricksConstants.DEFAULT_SPECIAL_COOR
	var showPoints	= PSTricksConstants.DEFAULT_SHOW_POINTS
	var showOrigin	= PSTricksConstants.DEFAULT_SHOW_ORIGIN
	var subGridWidth= PSTricksConstants.DEFAULT_SUB_GRID_WIDTH
	var swapAxes	= PSTricksConstants.DEFAULT_SWAP_AXES
	var shadowCol	= PSTricksConstants.DEFAULT_SHADOW_COLOR
	var subGridCol	= PSTricksConstants.DEFAULT_SUB_GRID_COLOR
	var shadowAngle	= PSTricksConstants.DEFAULT_SHADOW_ANGLE
	var shadowSize	= PSTricksConstants.DEFAULT_SHADOW_SIZE
	var subGridDots = PSTricksConstants.DEFAULT_SUBGRIDDOTS
	var subGridDiv	= PSTricksConstants.DEFAULT_SUBGRIDDIV
	var ticks		= PSTricksConstants.DEFAULT_TICKS_DISPLAYED
	var ticksStyle	= PSTricksConstants.DEFAULT_TICKS_STYLE
	var ticksSize	= PSTricksConstants.DEFAULT_TICKS_SIZE
	var unit		= PSTricksConstants.DEFAULT_UNIT
	var xUnit		= PSTricksConstants.DEFAULT_UNIT
	var yUnit		= PSTricksConstants.DEFAULT_UNIT
	var textColor	= Color.BLACK
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
		this()
		copy(model)
	}


	/**
	 * Copies the current PST context using the given one.
	 */
	def copy(model : PSTContext) {
		axesStyle 	= model.axesStyle
		arrowStyle 	= model.arrowStyle.clone
		arrowSizeD 	= model.arrowSizeD
		arrowSizeN 	= model.arrowSizeN
		arrowLgth 	= model.arrowLgth
		arrowInset 	= model.arrowInset
		arrowTBarSD	= model.arrowTBarSD
		arrowTBarSN = model.arrowTBarSN
		arrowBrLgth = model.arrowBrLgth
		arrowrBrLgth= model.arrowrBrLgth
		arrowDotSD	= model.arrowDotSD
		arrowDotSN 	= model.arrowDotSN
		arrowScale1 = model.arrowScale1
		arrowScale2 = model.arrowScale2
		arcSep 		= model.arcSep
		arcSepA 	= model.arcSepA
		arcSepB 	= model.arcSepB
		boxSep		= model.boxSep
		borderColor = model.borderColor
		borderPos	= model.borderPos
		border 		= model.border
		curvature1 	= model.curvature1
		curvature2 	= model.curvature2
		curvature3 	= model.curvature3
		dxIncrement = model.dxIncrement
		dyIncrement = model.dyIncrement
		dxLabelDist = model.dxLabelDist
		dyLabelDist = model.dyLabelDist
		dotStyle	= model.dotStyle
		dotScale1	= model.dotScale1
		dotScale2	= model.dotScale2
		dotAngle	= model.dotAngle
		dotStep		= model.dotStep
		dashBlack	= model.dashBlack
		dashWhite	= model.dashWhite
		dbleLine	= model.dbleLine
		dbleSep		= model.dbleSep
		dbleColor	= model.dbleColor
		degrees		= model.degrees
		frameSep	= model.frameSep
		frameArc	= model.frameArc
		fillStyle	= model.fillStyle
		fillColor	= model.fillColor
		gridWidth	= model.gridWidth
		gridLabel	= model.gridLabel
		gridDots	= model.gridDots
		gradAngle	= model.gradAngle
		gridColor	= model.gridColor
		gradMidPoint= model.gradMidPoint
		gradBegin	= model.gradBegin
		gradEnd		= model.gradEnd
		gradLines	= model.gradLines
		gangle		= model.gangle
		hatchWidth	= model.hatchWidth
		hatchSep	= model.hatchSep
		hatchCol	= model.hatchCol
		hatchAngle	= model.hatchAngle
		isCornerRel	= model.isCornerRel
		isShadow	= model.isShadow
		lineWidth	= model.lineWidth
		lineColor	= model.lineColor
		labelsGridCol= model.labelsGridCol
		labels		= model.labels
		lineArc		= model.lineArc
		lineStyle	= model.lineStyle
		ox			= model.ox
		oy			= model.oy
		onRadians	= model.onRadians
		origin		= new Point2D.Double(model.origin.getX, model.origin.getY)
		specialCoor	= model.specialCoor
		showPoints	= model.showPoints
		showOrigin	= model.showOrigin
		subGridWidth= model.subGridWidth
		swapAxes	= model.swapAxes
		shadowCol	= model.shadowCol
		subGridCol	= model.subGridCol
		shadowAngle	= model.shadowAngle
		shadowSize	= model.shadowAngle
		subGridDots = model.subGridDots
		subGridDiv	= model.subGridDiv
		ticks		= model.ticks
		ticksStyle	= model.ticksStyle
		ticksSize	= model.ticksSize
		unit		= model.unit
		xUnit		= model.xUnit
		yUnit		= model.yUnit
		textColor	= model.textColor
	}
}
