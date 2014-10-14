package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.glib.models.interfaces.prop.IGridProp
import net.sf.latexdraw.glib.models.interfaces.shape.Color
import net.sf.latexdraw.glib.views.latex.DviPsColors

/**
 * This trait encapsulates the code of the group related to the support of grids.<br>
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
 * 2012-04-16<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
private[impl] trait LGroupGrid extends IGroup {
	/** May return the first grid of the group. */
	private def firstIGrid = gridShapes.find{_.isTypeOf(classOf[IGridProp])}

	private def gridShapes = getShapes.flatMap{case x:IGridProp => x::Nil; case _ => Nil}


	override def isXLabelSouth: Boolean =
		firstIGrid match {
			case Some(stdGrid) => stdGrid.isXLabelSouth
			case _ => false
		}


	override def setXLabelSouth(isXLabelSouth : Boolean) {
		gridShapes.foreach{_.setXLabelSouth(isXLabelSouth)}
	}


	override def isYLabelWest: Boolean =
		firstIGrid match {
			case Some(stdGrid) => stdGrid.isYLabelWest
			case _ => false
		}


	override def setYLabelWest(isYLabelWest : Boolean) {
		gridShapes.foreach{_.setYLabelWest(isYLabelWest)}
	}

	override def getGridDots: Int = {
		firstIGrid match {
			case Some(grid) => grid.getGridDots
			case _ => 0
		}
	}


	override def setGridDots(gridDots : Int) {
		gridShapes.foreach{_.setGridDots(gridDots)}
	}


	override def getGridLabelsColour: Color = {
		firstIGrid match {
			case Some(grid) => grid.getGridLabelsColour
			case _ => DviPsColors.BLACK
		}
	}


	override def setGridLabelsColour(gridLabelsColour : Color) {
		gridShapes.foreach{_.setGridLabelsColour(gridLabelsColour)}
	}


	override def getGridWidth: Double = {
		firstIGrid match {
			case Some(grid) => grid.getGridWidth
			case _ => Double.NaN
		}
	}


	override def setGridWidth(gridWidth : Double) {
		gridShapes.foreach{_.setGridWidth(gridWidth)}
	}


	override def getSubGridColour: Color = {
		firstIGrid match {
			case Some(grid) => grid.getSubGridColour
			case _ => DviPsColors.BLACK
		}
	}


	override def setSubGridColour(subGridColour : Color) {
		gridShapes.foreach{_.setSubGridColour(subGridColour)}
	}


	override def getSubGridDiv: Int = {
		firstIGrid match {
			case Some(grid) => grid.getSubGridDiv
			case _ => 0
		}
	}


	override def setSubGridDiv(subGridDiv : Int) {
		gridShapes.foreach{_.setSubGridDiv(subGridDiv)}
	}


	override def getSubGridDots: Int = {
		firstIGrid match {
			case Some(grid) => grid.getSubGridDots
			case _ => 0
		}
	}


	override def setSubGridDots(subGridDots : Int) {
		gridShapes.foreach{_.setSubGridDots(subGridDots)}
	}


	override def getSubGridWidth: Double = {
		firstIGrid match {
			case Some(grid) => grid.getSubGridWidth
			case _ => Double.NaN
		}
	}


	override def setSubGridWidth(subGridWidth : Double) {
		gridShapes.foreach{_.setSubGridWidth(subGridWidth)}
	}


	override def setUnit(unit : Double) {
		gridShapes.foreach{_.setUnit(unit)}
	}


	override def getUnit: Double = {
		firstIGrid match {
			case Some(grid) => grid.getUnit
			case _ => Double.NaN
		}
	}
}
