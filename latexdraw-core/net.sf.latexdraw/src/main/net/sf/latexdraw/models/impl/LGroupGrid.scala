/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models.impl

import java.util.stream.Collectors
import javafx.beans.property.{BooleanProperty, DoubleProperty, IntegerProperty, ObjectProperty}

import net.sf.latexdraw.models.interfaces.prop.IGridProp
import net.sf.latexdraw.models.interfaces.shape.Color
import net.sf.latexdraw.models.interfaces.shape.IGroup
import net.sf.latexdraw.models.interfaces.shape.IShape
import net.sf.latexdraw.view.latex.DviPsColors

/**
 * This trait encapsulates the code of the group related to the support of grids.
 * @author Arnaud BLOUIN
 */
private[impl] trait LGroupGrid extends IGroup {
	/** May return the first grid of the group. */
	private def firstIGrid = gridShapes.stream.filter{_.isTypeOf(classOf[IGridProp])}.findFirst

	private def gridShapes =
      getShapes.stream.filter{_.isInstanceOf[IGridProp]}.map[IShape with IGridProp]{_.asInstanceOf[IShape with IGridProp]}.collect(Collectors.toList())

	override def isXLabelSouth = if(firstIGrid.isPresent) firstIGrid.get.isXLabelSouth else false

	override def setXLabelSouth(isXLabelSouth : Boolean) {
		gridShapes.forEach{_.setXLabelSouth(isXLabelSouth)}
	}

	override def isYLabelWest = if(firstIGrid.isPresent) firstIGrid.get.isYLabelWest else false

	override def setYLabelWest(isYLabelWest : Boolean) {
		gridShapes.forEach{_.setYLabelWest(isYLabelWest)}
	}

	override def getGridDots = if(firstIGrid.isPresent) firstIGrid.get.getGridDots else 0

	override def setGridDots(gridDots : Int) {
		gridShapes.forEach{_.setGridDots(gridDots)}
	}

	override def getGridLabelsColour = if(firstIGrid.isPresent) firstIGrid.get.getGridLabelsColour else DviPsColors.BLACK

	override def setGridLabelsColour(gridLabelsColour : Color) {
		gridShapes.forEach{_.setGridLabelsColour(gridLabelsColour)}
	}

	override def getGridWidth = if(firstIGrid.isPresent) firstIGrid.get.getGridWidth else Double.NaN

	override def setGridWidth(gridWidth : Double) {
		gridShapes.forEach{_.setGridWidth(gridWidth)}
	}

	override def getSubGridColour = if(firstIGrid.isPresent) firstIGrid.get.getSubGridColour else DviPsColors.BLACK

	override def setSubGridColour(subGridColour : Color) {
		gridShapes.forEach{_.setSubGridColour(subGridColour)}
	}

	override def getSubGridDiv = if(firstIGrid.isPresent) firstIGrid.get.getSubGridDiv else 0

	override def setSubGridDiv(subGridDiv : Int) {
		gridShapes.forEach{_.setSubGridDiv(subGridDiv)}
	}

	override def getSubGridDots = if(firstIGrid.isPresent) firstIGrid.get.getSubGridDots else 0

	override def setSubGridDots(subGridDots : Int) {
		gridShapes.forEach{_.setSubGridDots(subGridDots)}
	}

	override def getSubGridWidth = if(firstIGrid.isPresent) firstIGrid.get.getSubGridWidth else Double.NaN

	override def setSubGridWidth(subGridWidth : Double) {
		gridShapes.forEach{_.setSubGridWidth(subGridWidth)}
	}

	override def setUnit(unit : Double) {
		gridShapes.forEach{_.setUnit(unit)}
	}

	override def getUnit = if(firstIGrid.isPresent) firstIGrid.get.getUnit else Double.NaN

	override def gridLabelsColourProperty(): ObjectProperty[Color] = null

	override def gridDotsProperty(): IntegerProperty = null

	override def unitProperty(): DoubleProperty = null

	override def subGridWidthProperty(): DoubleProperty = null

	override def subGridDotsProperty(): IntegerProperty = null

	override def subGridDivProperty(): IntegerProperty = null

	override def subGridColourProperty(): ObjectProperty[Color] = null

	override def gridWidthProperty(): DoubleProperty = null

	override def yLabelWestProperty(): BooleanProperty = null

	override def xLabelSouthProperty(): BooleanProperty = null
}
