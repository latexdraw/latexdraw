package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.IAxes
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IPoint

/**
 * This trait encapsulates the code of the group related to the support of axes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
	private def firstIAxes = getShapes.find{shape => shape.isInstanceOf[IAxes] }

	override def getIncrementX() : Double = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getIncrementX
			case _ => Double.NaN
		}
	}


	override def getIncrementY() : Double = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getIncrementY
			case _ => Double.NaN
		}
	}


	override def setIncrementX(increment : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setIncrementX(increment)
		}
	}


	override def setIncrementY(increment : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setIncrementY(increment)
		}
	}


	override def getDistLabelsX() : Double = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getDistLabelsX
			case _ => Double.NaN
		}
	}


	override def getDistLabelsY() : Double = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getDistLabelsY
			case _ => Double.NaN
		}
	}


	override def setDistLabelsX(distLabelsX : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setDistLabelsX(distLabelsX)
		}
	}


	override def setDistLabelsY(distLabelsY : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setDistLabelsY(distLabelsY)
		}
	}


	override def getLabelsDisplayed() : IAxes.PlottingStyle = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getLabelsDisplayed
			case _ => null
		}
	}


	override def setLabelsDisplayed(labelsDisplayed : IAxes.PlottingStyle) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setLabelsDisplayed(labelsDisplayed)
		}
	}


	override def isShowOrigin() : Boolean = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].isShowOrigin
			case _ => false
		}
	}


	override def setShowOrigin(showOrigin : Boolean) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setShowOrigin(showOrigin)
		}
	}


	override def getTicksDisplayed() : IAxes.PlottingStyle = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getTicksDisplayed
			case _ => null
		}
	}


	override def setTicksDisplayed(ticksDisplayed : IAxes.PlottingStyle) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setTicksDisplayed(ticksDisplayed)
		}
	}


	override def getTicksStyle() : IAxes.TicksStyle = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getTicksStyle
			case _ => null
		}
	}


	override def setTicksStyle(ticksStyle : IAxes.TicksStyle) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setTicksStyle(ticksStyle)
		}
	}


	override def getTicksSize() : Double = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getTicksSize
			case _ => Double.NaN
		}
	}


	override def setTicksSize(ticksSize : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setTicksSize(ticksSize)
		}
	}


	override def getAxesStyle() : IAxes.AxesStyle = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getAxesStyle
			case _ => null
		}
	}


	override def setAxesStyle(axesStyle : IAxes.AxesStyle) = {
		if(axesStyle!=null)
			getShapes.foreach{shape =>
				if(shape.isInstanceOf[IAxes])
					shape.asInstanceOf[IAxes].setAxesStyle(axesStyle)
		}
	}


	override def getIncrement() : IPoint = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getIncrement
			case _ => null
		}
	}


	override def setIncrement(increment : IPoint) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setIncrement(increment)
		}
	}


	override def getDistLabels() : IPoint = {
		firstIAxes match {
			case Some(axe) => axe.asInstanceOf[IAxes].getDistLabels
			case _ => null
		}
	}


	override def setDistLabels(distLabels : IPoint) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IAxes])
				shape.asInstanceOf[IAxes].setDistLabels(distLabels)
		}
	}
}
