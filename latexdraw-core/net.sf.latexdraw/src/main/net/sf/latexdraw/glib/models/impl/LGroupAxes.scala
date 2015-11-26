package net.sf.latexdraw.glib.models.impl

import java.util.stream.Collectors

import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp
import net.sf.latexdraw.glib.models.interfaces.shape.AxesStyle
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.interfaces.shape.PlottingStyle
import net.sf.latexdraw.glib.models.interfaces.shape.TicksStyle

/**
 * This trait encapsulates the code of the group related to the support of axes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-16<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
private[impl] trait LGroupAxes extends IGroup {
	/** May return the first axes shape of the group. */
	private def firstIAxes = axesShapes.stream.filter{_.isTypeOf(classOf[IAxesProp])}.findFirst

	private def axesShapes = 
	  getShapes.stream.filter{_.isInstanceOf[IAxesProp]}.map[IShape with IAxesProp]{_.asInstanceOf[IShape with IAxesProp]}.collect(Collectors.toList())

	override def getIncrementX = if(firstIAxes.isPresent) firstIAxes.get.getIncrementX else Double.NaN

	override def getIncrementY = if(firstIAxes.isPresent) firstIAxes.get.getIncrementY else Double.NaN

	override def setIncrementX(increment : Double) {
		axesShapes.forEach{_.setIncrementX(increment)}
	}

	override def setIncrementY(increment : Double) {
		axesShapes.forEach{_.setIncrementY(increment)}
	}

	override def getDistLabelsX = if(firstIAxes.isPresent) firstIAxes.get.getDistLabelsX else Double.NaN

	override def getDistLabelsY = if(firstIAxes.isPresent) firstIAxes.get.getDistLabelsY else Double.NaN

	override def setDistLabelsX(distLabelsX : Double) {
		axesShapes.forEach{_.setDistLabelsX(distLabelsX)}
	}

	override def setDistLabelsY(distLabelsY : Double) {
		axesShapes.forEach{_.setDistLabelsY(distLabelsY)}
	}

	override def getLabelsDisplayed = if(firstIAxes.isPresent) firstIAxes.get.getLabelsDisplayed else PlottingStyle.ALL

	override def setLabelsDisplayed(labelsDisplayed : PlottingStyle) {
		axesShapes.forEach{_.setLabelsDisplayed(labelsDisplayed)}
	}

	override def isShowOrigin = if(firstIAxes.isPresent) firstIAxes.get.isShowOrigin else false

	override def setShowOrigin(showOrigin : Boolean) {
		axesShapes.forEach{_.setShowOrigin(showOrigin)}
	}

	override def getTicksDisplayed = if(firstIAxes.isPresent) firstIAxes.get.getTicksDisplayed else PlottingStyle.ALL

	override def setTicksDisplayed(ticksDisplayed : PlottingStyle) {
		axesShapes.forEach{_.setTicksDisplayed(ticksDisplayed)}
	}

	override def getTicksStyle = if(firstIAxes.isPresent) firstIAxes.get.getTicksStyle else TicksStyle.FULL

	override def setTicksStyle(ticksStyle : TicksStyle) {
		axesShapes.forEach{_.setTicksStyle(ticksStyle)}
	}

	override def getTicksSize = if(firstIAxes.isPresent) firstIAxes.get.getTicksSize else Double.NaN

	override def setTicksSize(ticksSize : Double) {
		axesShapes.forEach{_.setTicksSize(ticksSize)}
	}

	override def getAxesStyle = if(firstIAxes.isPresent) firstIAxes.get.getAxesStyle else AxesStyle.AXES

	override def setAxesStyle(axesStyle : AxesStyle) {
		axesShapes.forEach{_.setAxesStyle(axesStyle)}
	}

	override def getIncrement = if(firstIAxes.isPresent) firstIAxes.get.getIncrement else null

	override def setIncrement(increment : IPoint) {
		axesShapes.forEach{_.setIncrement(increment)}
	}

	override def getDistLabels = if(firstIAxes.isPresent) firstIAxes.get.getDistLabels else null

	override def setDistLabels(distLabels : IPoint) {
		axesShapes.forEach{_.setDistLabels(distLabels)}
	}
}
