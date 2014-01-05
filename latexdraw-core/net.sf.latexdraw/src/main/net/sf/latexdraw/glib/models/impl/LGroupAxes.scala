package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp

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
protected trait LGroupAxes extends IGroup {
	/** May return the first axes shape of the group. */
	private def firstIAxes = axesShapes.find{_.isTypeOf(classOf[IAxesProp])}

	private def axesShapes = getShapes.flatMap{case x:IAxesProp => x::Nil; case _ => Nil}

	override def getIncrementX() : Double = {
		firstIAxes match {
			case Some(axe) => axe.getIncrementX
			case _ => Double.NaN
		}
	}


	override def getIncrementY() : Double = {
		firstIAxes match {
			case Some(axe) => axe.getIncrementY
			case _ => Double.NaN
		}
	}


	override def setIncrementX(increment : Double) {
		axesShapes.foreach{_.setIncrementX(increment)}
	}


	override def setIncrementY(increment : Double) {
		axesShapes.foreach{_.setIncrementY(increment)}
	}


	override def getDistLabelsX() : Double = {
		firstIAxes match {
			case Some(axe) => axe.getDistLabelsX
			case _ => Double.NaN
		}
	}


	override def getDistLabelsY() : Double = {
		firstIAxes match {
			case Some(axe) => axe.getDistLabelsY
			case _ => Double.NaN
		}
	}


	override def setDistLabelsX(distLabelsX : Double) {
		axesShapes.foreach{_.setDistLabelsX(distLabelsX)}
	}


	override def setDistLabelsY(distLabelsY : Double) {
		axesShapes.foreach{_.setDistLabelsY(distLabelsY)}
	}


	override def getLabelsDisplayed() : IAxesProp.PlottingStyle = {
		firstIAxes match {
			case Some(axe) => axe.getLabelsDisplayed
			case _ => null
		}
	}


	override def setLabelsDisplayed(labelsDisplayed : IAxesProp.PlottingStyle) {
		axesShapes.foreach{_.setLabelsDisplayed(labelsDisplayed)}
	}


	override def isShowOrigin() : Boolean = {
		firstIAxes match {
			case Some(axe) => axe.isShowOrigin
			case _ => false
		}
	}


	override def setShowOrigin(showOrigin : Boolean) {
		axesShapes.foreach{_.setShowOrigin(showOrigin)}
	}


	override def getTicksDisplayed() : IAxesProp.PlottingStyle = {
		firstIAxes match {
			case Some(axe) => axe.getTicksDisplayed
			case _ => null
		}
	}


	override def setTicksDisplayed(ticksDisplayed : IAxesProp.PlottingStyle) {
		axesShapes.foreach{_.setTicksDisplayed(ticksDisplayed)}
	}


	override def getTicksStyle() : IAxesProp.TicksStyle = {
		firstIAxes match {
			case Some(axe) => axe.getTicksStyle
			case _ => null
		}
	}


	override def setTicksStyle(ticksStyle : IAxesProp.TicksStyle) {
		axesShapes.foreach{_.setTicksStyle(ticksStyle)}
	}


	override def getTicksSize() : Double = {
		firstIAxes match {
			case Some(axe) => axe.getTicksSize
			case _ => Double.NaN
		}
	}


	override def setTicksSize(ticksSize : Double) {
		axesShapes.foreach{_.setTicksSize(ticksSize)}
	}


	override def getAxesStyle() : IAxesProp.AxesStyle = {
		firstIAxes match {
			case Some(axe) => axe.getAxesStyle
			case _ => null
		}
	}


	override def setAxesStyle(axesStyle : IAxesProp.AxesStyle) {
		axesShapes.foreach{_.setAxesStyle(axesStyle)}
	}


	override def getIncrement() : IPoint = {
		firstIAxes match {
			case Some(axe) => axe.getIncrement
			case _ => null
		}
	}


	override def setIncrement(increment : IPoint) {
		axesShapes.foreach{_.setIncrement(increment)}
	}


	override def getDistLabels() : IPoint = {
		firstIAxes match {
			case Some(axe) => axe.getDistLabels
			case _ => null
		}
	}


	override def setDistLabels(distLabels : IPoint) {
		axesShapes.foreach{_.setDistLabels(distLabels)}
	}
}
