package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.prop.IStdGridProp

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
	private def firstIStdGrid = gridShapes.find{_.isTypeOf(classOf[IStdGridProp])}

	private def gridShapes = getShapes.flatMap{case x:IStdGridProp => x::Nil; case _ => Nil}

	override def getGridMinX: Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getGridMinX
			case _ => Double.NaN
		}

	override def getGridMaxX: Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getGridMaxX
			case _ => Double.NaN
		}

	override def getGridMinY: Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getGridMinY
			case _ => Double.NaN
		}

	override def getGridMaxY =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getGridMaxY
			case _ => Double.NaN
		}

	override def getLabelsSize: Int =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getLabelsSize
			case _ => -1
		}

	override def setLabelsSize(labelsSize : Int) {
		gridShapes.foreach{_.setLabelsSize(labelsSize)}
	}

	override def setGridEndX(x : Double) {
		gridShapes.foreach{_.setGridEndX(x)}
	}

	override def setGridEndY(y : Double) {
		gridShapes.foreach{_.setGridEndY(y)}
	}

	override def getGridStartX: Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getGridStartX
			case _ => Double.NaN
		}

	override def getGridStartY: Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getGridStartY
			case _ => Double.NaN
		}

	override def setGridStart(x : Double, y : Double) {
		gridShapes.foreach{_.setGridStart(x, y)}
	}

	override def getGridEndX: Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getGridEndX
			case _ => Double.NaN
		}

	override def getGridEndY: Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getGridEndY
			case _ => Double.NaN
		}

	override def setGridEnd(x : Double, y : Double) {
		gridShapes.foreach{_.setGridEnd(x, y)}
	}

	override def getOriginX: Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getOriginX
			case _ => Double.NaN
		}

	override def getOriginY: Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getOriginY
			case _ => Double.NaN
		}

	override def setOrigin(x : Double, y : Double) {
		gridShapes.foreach{_.setOrigin(x, y)}
	}

	override def setGridStartY(y : Double) {
		gridShapes.foreach{_.setGridStartY(y)}
	}

	override def setGridStartX(x : Double) {
		gridShapes.foreach{_.setGridStartX(x)}
	}

	override def setOriginX(x : Double) {
		gridShapes.foreach{_.setOriginX(x)}
	}

	override def setOriginY(y : Double) {
		gridShapes.foreach{_.setOriginY(y)}
	}

	override def getStep: Double =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getStep
			case _ => Double.NaN
		}

	override def getGridStart: IPoint =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getGridStart
			case _ => null
		}

	override def getGridEnd: IPoint =
		firstIStdGrid match {
			case Some(stdGrid) => stdGrid.getGridEnd
			case _ => null
		}
}
