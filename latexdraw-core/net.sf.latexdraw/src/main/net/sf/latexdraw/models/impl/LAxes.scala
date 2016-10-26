package net.sf.latexdraw.models.impl

import net.sf.latexdraw.models.GLibUtilities
import net.sf.latexdraw.models.ShapeFactory
import net.sf.latexdraw.models.interfaces.prop.IAxesProp
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle
import net.sf.latexdraw.models.interfaces.shape.AxesStyle
import net.sf.latexdraw.models.interfaces.shape.IArrow
import net.sf.latexdraw.models.interfaces.shape.IAxes
import net.sf.latexdraw.models.interfaces.shape.ILine
import net.sf.latexdraw.models.interfaces.shape.IPoint
import net.sf.latexdraw.models.interfaces.shape.IShape
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle
import net.sf.latexdraw.models.interfaces.shape.TicksStyle
import net.sf.latexdraw.view.pst.PSTricksConstants

/**
 * Defines a model of axes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 02/13/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
private[impl] class LAxes(pt:IPoint) extends LAbstractGrid(pt) with IAxes with LArrowableShape {
	/** The increment of X axe (Dx in PST). */
	var incrementX : Double = PSTricksConstants.DEFAULT_DX

	/** The increment of Y axe (Dy in PST). */
	var incrementY : Double = PSTricksConstants.DEFAULT_DY

	/** The distance between each label of the X axe; if 0, the default value will be used (in cm). */
	var distLabelsX = 1.0

	/** The distance between each label of the Y axe; if 0, the default value will be used (in cm). */
	var distLabelsY = 1.0

	/** Define which labels must be displayed. */
	var labelsDisplayed = PlottingStyle.ALL

	/** Define the origin must be shown. */
	var showOrigin = true

	/** Define how the ticks must be shown. */
	var ticksDisplayed = PlottingStyle.ALL

	/** Define the style of the ticks. */
	var ticksStyle = TicksStyle.FULL

	/** The size of the ticks. */
	var ticksSize : Double = PSTricksConstants.DEFAULT_TICKS_SIZE*IShape.PPC

	/** The style of the axes. */
	var axesStyle = AxesStyle.AXES

	// The first arrow is for the bottom of the Y-axis.
	arrows += ShapeFactory.createArrow(this)
	// The second arrow is for the left of the X-axis.
	arrows += ShapeFactory.createArrow(this)
	// The third arrow is for the top of the Y-axis.
	arrows += ShapeFactory.createArrow(this)
	// The fourth arrow is for the right of the X-axis.
	arrows += ShapeFactory.createArrow(this)


	override def copy(s:IShape) {
		super[LAbstractGrid].copy(s)
		super[LArrowableShape].copy(s)
		s match {
			case axes:IAxesProp =>
				setTicksDisplayed(axes.getTicksDisplayed)
				setTicksSize(axes.getTicksSize)
				setTicksStyle(axes.getTicksStyle)
				setAxesStyle(axes.getAxesStyle)
				setShowOrigin(axes.isShowOrigin)
				setDistLabelsX(axes.getDistLabelsX)
				setDistLabelsY(axes.getDistLabelsY)
				setIncrementX(axes.getIncrementX)
				setIncrementY(axes.getIncrementY)
				setLabelsDisplayed(axes.getLabelsDisplayed)
			case _ =>
		}
	}

	override def setArrowStyle(style:ArrowStyle, position:Int) {
		val arr1 = getArrowAt(position)

		if(style!=null && arr1!=null) {
			super.setArrowStyle(style, position)
			val pos = (if(position == -1) arrows.size-1 else position) % 4
			pos match {
				case 0 => arrows(1).setArrowStyle(style)
				case 1 => arrows(0).setArrowStyle(style)
				case 2 => arrows(3).setArrowStyle(style)
				case 3 => arrows(2).setArrowStyle(style)
			}
		}
	}

	override def getArrowLine(arrow:IArrow) : ILine = {
		// For the X-axis
		if(arrow==arrows(1) || arrow==arrows(3))
			return getArrowLineX(arrow==arrows(1))
		// For the Y-axis.
		if(arrow==arrows(0) || arrow==arrows(2))
			return getArrowLineY(arrow==arrows(2))
		return null
	}


	/**
	 * @return The line of the Y-axis.
	 */
	private def getArrowLineY(topY:Boolean) : ILine = {
		val pos = getPosition
		val p2 = ShapeFactory.createPoint(pos.getX, pos.getY-gridEndy*IShape.PPC)
		val p1 = ShapeFactory.createPoint(pos.getX, pos.getY-gridStarty*IShape.PPC)

		if(topY)
			return ShapeFactory.createLine(p2, p1)
		return ShapeFactory.createLine(p1, p2)
	}


	/**
	 * @return The line of the X-axis.
	 */
	private def getArrowLineX(leftX:Boolean) : ILine = {
		val pos = getPosition
		val p2 = ShapeFactory.createPoint(pos.getX+gridEndx*IShape.PPC, pos.getY)
		val p1 = ShapeFactory.createPoint(pos.getX+gridStartx*IShape.PPC, pos.getY)

		if(leftX)
			return ShapeFactory.createLine(p1, p2)
		return ShapeFactory.createLine(p2, p1)
	}

	override def getAxesStyle = axesStyle

	override def getDistLabelsX = distLabelsX

	override def getDistLabelsY = distLabelsY

	override def getLabelsDisplayed = labelsDisplayed

	override def getTicksDisplayed = ticksDisplayed

	override def getTicksSize = ticksSize

	override def getTicksStyle = ticksStyle

	override def isShowOrigin = showOrigin

	override def setAxesStyle(axesStyle:AxesStyle) {
		if(axesStyle!=null)
			this.axesStyle = axesStyle
	}

	override def setDistLabelsX(distLabelsX:Double) {
		if(distLabelsX>0 && GLibUtilities.isValidCoordinate(distLabelsX))
				this.distLabelsX = distLabelsX
	}

	override def setDistLabelsY(distLabelsY:Double) {
		if(distLabelsY>0 && GLibUtilities.isValidCoordinate(distLabelsY))
			this.distLabelsY = distLabelsY
	}

	override def setIncrementX(increment:Double) {
		if(increment>0 && GLibUtilities.isValidCoordinate(increment))
			this.incrementX = increment
	}


	override def setIncrementY(increment:Double) {
		if(increment>0 && GLibUtilities.isValidCoordinate(increment))
			this.incrementY = increment
	}

	override def setLabelsDisplayed(labelsDisplayed:PlottingStyle) {
		if(labelsDisplayed!=null)
			this.labelsDisplayed = labelsDisplayed
	}

	override def setShowOrigin(showOrigin:Boolean) {
		this.showOrigin = showOrigin
	}

	override def setTicksDisplayed(ticksDisplayed:PlottingStyle) {
		if(ticksDisplayed!=null)
			this.ticksDisplayed = ticksDisplayed
	}

	override def setTicksSize(ticksSize:Double) {
		if(ticksSize>0 && GLibUtilities.isValidCoordinate(ticksSize))
			this.ticksSize = ticksSize
	}

	override def setTicksStyle(ticksStyle:TicksStyle) {
		if(ticksStyle!=null)
			this.ticksStyle = ticksStyle
	}

	override def getStep = IShape.PPC

	override def isLineStylable = true

	override def isThicknessable = true

	override def getIncrementX = incrementX

	override def getIncrementY = incrementY

	override def getIncrement = ShapeFactory.createPoint(incrementX, incrementY)

	override def setIncrement(increment:IPoint) {
		if(increment!=null) {
			setIncrementX(increment.getX)
			setIncrementY(increment.getY)
		}
	}

	override def getDistLabels = ShapeFactory.createPoint(distLabelsX, distLabelsY)

	override def setDistLabels(distLabels:IPoint) {
		if(distLabels!=null) {
			setDistLabelsX(distLabels.getX)
			setDistLabelsY(distLabels.getY)
		}
	}
}
