package net.sf.latexdraw.models.impl

import java.util.stream.Collectors

import net.sf.latexdraw.models.interfaces.prop.IStdGridProp
import net.sf.latexdraw.models.interfaces.shape.IGroup
import net.sf.latexdraw.models.interfaces.shape.IShape

/**
 * This trait encapsulates the code of the group related to the support of standard grids.<br>
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
 * 2012-04-17<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
private[impl] trait LGroupStdGrid extends IGroup {
	/** May return the first stdGrid of the group. */
	private def firstIStdGrid = gridShapes.stream.filter{_.isTypeOf(classOf[IStdGridProp])}.findFirst

	private def gridShapes =
	  getShapes.stream.filter{_.isInstanceOf[IStdGridProp]}.map[IShape with IStdGridProp]{_.asInstanceOf[IShape with IStdGridProp]}.collect(Collectors.toList())

	override def getGridMinX = if(firstIStdGrid.isPresent) firstIStdGrid.get.getGridMinX else Double.NaN

	override def getGridMaxX = if(firstIStdGrid.isPresent) firstIStdGrid.get.getGridMaxX else Double.NaN

	override def getGridMinY = if(firstIStdGrid.isPresent) firstIStdGrid.get.getGridMinY else Double.NaN

	override def getGridMaxY = if(firstIStdGrid.isPresent) firstIStdGrid.get.getGridMaxY else Double.NaN

	override def getLabelsSize = if(firstIStdGrid.isPresent) firstIStdGrid.get.getLabelsSize else -1

	override def setLabelsSize(labelsSize : Int) {
		gridShapes.forEach{_.setLabelsSize(labelsSize)}
	}

	override def setGridEndX(x : Double) {
		gridShapes.forEach{_.setGridEndX(x)}
	}

	override def setGridEndY(y : Double) {
		gridShapes.forEach{_.setGridEndY(y)}
	}

	override def getGridStartX = if(firstIStdGrid.isPresent) firstIStdGrid.get.getGridStartX else Double.NaN

	override def getGridStartY = if(firstIStdGrid.isPresent) firstIStdGrid.get.getGridStartY else Double.NaN

	override def setGridStart(x : Double, y : Double) {
		gridShapes.forEach{_.setGridStart(x, y)}
	}

	override def getGridEndX = if(firstIStdGrid.isPresent) firstIStdGrid.get.getGridEndX else Double.NaN

	override def getGridEndY = if(firstIStdGrid.isPresent) firstIStdGrid.get.getGridEndY else Double.NaN

	override def setGridEnd(x : Double, y : Double) {
		gridShapes.forEach{_.setGridEnd(x, y)}
	}

	override def getOriginX = if(firstIStdGrid.isPresent) firstIStdGrid.get.getOriginX else Double.NaN

	override def getOriginY = if(firstIStdGrid.isPresent) firstIStdGrid.get.getOriginY else Double.NaN

	override def setOrigin(x : Double, y : Double) {
		gridShapes.forEach{_.setOrigin(x, y)}
	}

	override def setGridStartY(y : Double) {
		gridShapes.forEach{_.setGridStartY(y)}
	}

	override def setGridStartX(x : Double) {
		gridShapes.forEach{_.setGridStartX(x)}
	}

	override def setOriginX(x : Double) {
		gridShapes.forEach{_.setOriginX(x)}
	}

	override def setOriginY(y : Double) {
		gridShapes.forEach{_.setOriginY(y)}
	}

	override def getStep = if(firstIStdGrid.isPresent) firstIStdGrid.get.getStep else Double.NaN

	override def getGridStart = if(firstIStdGrid.isPresent) firstIStdGrid.get.getGridStart else null

	override def getGridEnd = if(firstIStdGrid.isPresent) firstIStdGrid.get.getGridEnd else null
}
