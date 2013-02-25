package net.sf.latexdraw.glib.models.impl

import java.awt.Color

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.IGrid
import net.sf.latexdraw.glib.models.interfaces.IGroup

/**
 * This trait encapsulates the code of the group related to the support of grids.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-16<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
protected trait LGroupGrid extends IGroup {
	/** May return the first grid of the group. */
	private def firstIGrid = getShapes.find{shape => shape.isInstanceOf[IGrid] }

	override def getGridDots() : Int = {
		firstIGrid match {
			case Some(grid) => grid.asInstanceOf[IGrid].getGridDots
			case _ => 0
		}
	}


	override def setGridDots(gridDots : Int) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IGrid])
				shape.asInstanceOf[IGrid].setGridDots(gridDots)
		}
	}


	override def getGridLabelsColour() : Color = {
		firstIGrid match {
			case Some(grid) => grid.asInstanceOf[IGrid].getGridLabelsColour
			case _ => null
		}
	}


	override def setGridLabelsColour(gridLabelsColour : Color) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IGrid])
				shape.asInstanceOf[IGrid].setGridLabelsColour(gridLabelsColour)
		}
	}


	override def getGridWidth() : Double = {
		firstIGrid match {
			case Some(grid) => grid.asInstanceOf[IGrid].getGridWidth
			case _ => Double.NaN
		}
	}


	override def setGridWidth(gridWidth : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IGrid])
				shape.asInstanceOf[IGrid].setGridWidth(gridWidth)
		}
	}


	override def getSubGridColour() : Color = {
		firstIGrid match {
			case Some(grid) => grid.asInstanceOf[IGrid].getSubGridColour
			case _ => null
		}
	}


	override def setSubGridColour(subGridColour : Color) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IGrid])
				shape.asInstanceOf[IGrid].setSubGridColour(subGridColour)
		}
	}


	override def getSubGridDiv() : Int = {
		firstIGrid match {
			case Some(grid) => grid.asInstanceOf[IGrid].getSubGridDiv
			case _ => 0
		}
	}


	override def setSubGridDiv(subGridDiv : Int) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IGrid])
				shape.asInstanceOf[IGrid].setSubGridDiv(subGridDiv)
		}
	}


	override def getSubGridDots() : Int = {
		firstIGrid match {
			case Some(grid) => grid.asInstanceOf[IGrid].getSubGridDots
			case _ => 0
		}
	}


	override def setSubGridDots(subGridDots : Int) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IGrid])
				shape.asInstanceOf[IGrid].setSubGridDots(subGridDots)
		}
	}


	override def getSubGridWidth() : Double = {
		firstIGrid match {
			case Some(grid) => grid.asInstanceOf[IGrid].getSubGridWidth
			case _ => Double.NaN
		}
	}


	override def setSubGridWidth(subGridWidth : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IGrid])
				shape.asInstanceOf[IGrid].setSubGridWidth(subGridWidth)
		}
	}


	override def setUnit(unit : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IGrid])
				shape.asInstanceOf[IGrid].setUnit(unit)
		}
	}


	override def getUnit() : Double = {
		firstIGrid match {
			case Some(grid) => grid.asInstanceOf[IGrid].getUnit
			case _ => Double.NaN
		}
	}
}
