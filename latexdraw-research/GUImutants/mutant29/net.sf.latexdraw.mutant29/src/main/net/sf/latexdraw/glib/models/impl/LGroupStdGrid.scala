package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IStandardGrid

/**
 * This trait encapsulates the code of the group related to the support of standard grids.<br>
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
 * 2012-04-17<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
protected trait LGroupStdGrid extends IGroup {
	/** May return the first stdGrid of the group. */
	private def firstIStdGrid = getShapes.find{shape => shape.isInstanceOf[IStandardGrid] }

	override def getGridMinX() : Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getGridMinX
			case _ => Double.NaN
		}


	override def getGridMaxX() : Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getGridMaxX
			case _ => Double.NaN
		}


	override def getGridMinY() : Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getGridMinY
			case _ => Double.NaN
		}


	override def getGridMaxY() =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getGridMaxY
			case _ => Double.NaN
		}


	override def getLabelsSize() : Int =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getLabelsSize
			case _ => -1
		}


	override def setLabelsSize(labelsSize : Int) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setLabelsSize(labelsSize)
		}
	}


	override def setGridEndX(x : Double) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setGridEndX(x)
		}
	}


	override def setGridEndY(y : Double) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setGridEndY(y)
		}
	}


	override def isXLabelSouth() : Boolean =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].isXLabelSouth
			case _ => false
		}


	override def setXLabelSouth(isXLabelSouth : Boolean) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setXLabelSouth(isXLabelSouth)
		}
	}


	override def isYLabelWest() : Boolean =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].isYLabelWest
			case _ => false
		}


	override def setYLabelWest(isYLabelWest : Boolean) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setYLabelWest(isYLabelWest)
		}
	}


	override def getGridStartX() : Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getGridStartX
			case _ => Double.NaN
		}


	override def getGridStartY() : Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getGridStartY
			case _ => Double.NaN
		}


	override def setGridStart(x : Double, y : Double) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setGridStart(x, y)
		}
	}


	override def getGridEndX() : Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getGridEndX
			case _ => Double.NaN
		}


	override def getGridEndY() : Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getGridEndY
			case _ => Double.NaN
		}


	override def setGridEnd(x : Double, y : Double) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setGridEnd(x, y)
		}
	}


	override def getOriginX() : Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getOriginX
			case _ => Double.NaN
		}


	override def getOriginY() : Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getOriginY
			case _ => Double.NaN
		}


	override def setOrigin(x : Double, y : Double) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setOrigin(x, y)
		}
	}


	override def setGridStartY(y : Double) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setGridStartY(y)
		}
	}


	override def setGridStartX(x : Double) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setGridStartX(x)
		}
	}


	override def setOriginX(x : Double) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setOriginX(x)
		}
	}


	override def setOriginY(y : Double) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IStandardGrid])
				shape.asInstanceOf[IStandardGrid].setOriginY(y)
		}
	}


	override def getStep() : Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getStep
			case _ => Double.NaN
		}


	override def getGridStart() : IPoint =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getGridStart
			case _ => null
		}


	override def getGridEnd() : IPoint =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.asInstanceOf[IStandardGrid].getGridEnd
			case _ => null
		}
}
