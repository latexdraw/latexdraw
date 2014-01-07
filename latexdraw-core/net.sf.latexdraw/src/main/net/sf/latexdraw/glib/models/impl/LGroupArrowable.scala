package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer
import net.sf.latexdraw.glib.models.interfaces.IArrow
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.ILine
import java.util.Collections

/**
 * This trait encapsulates the code of the group related to the support of arrowable shapes.<br>
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
protected trait LGroupArrowable extends IGroup {
	/** May return the first grid of the group. */
	private def firstIArrowable = arrowShapes.headOption

	private def arrowShapes = getShapes.filter(_.isArrowable)

	override def setArrowStyle(style : IArrow.ArrowStyle, position : Int) {
		arrowShapes.foreach{_.setArrowStyle(style, position)}
	}

	override def getArrowStyle(position : Int) : IArrow.ArrowStyle = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowStyle(position)
			case _ => IArrow.ArrowStyle.NONE
		}
	}

	override def isArrowable() = firstIArrowable.isDefined

	override def getArrowAt(position : Int) : IArrow = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowAt(position)
			case _ => null
		}
	}


	override def getArrows() : java.util.List[IArrow] = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrows
			case _ => Collections.emptyList()
		}
	}


	override def getArrowLine(arrow : IArrow) : ILine = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowLine(arrow)
			case _ => null
		}
	}


	override def setDotSizeDim(dotSizeDim : Double) {
		arrowShapes.foreach{_.setDotSizeDim(dotSizeDim)}
	}

	override def setDotSizeNum(dotSizeNum : Double) {
		arrowShapes.foreach{_.setDotSizeNum(dotSizeNum)}
	}

	override def setTBarSizeNum(tbarSizeNum : Double) {
		arrowShapes.foreach{_.setTBarSizeNum(tbarSizeNum)}
	}

	override def setTBarSizeDim(tbarSizeDim : Double) {
		arrowShapes.foreach{_.setTBarSizeDim(tbarSizeDim)}
	}

	override def getTBarSizeDim() : Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getTBarSizeDim
			case _ => Double.NaN
		}
	}

	override def getTBarSizeNum() : Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getTBarSizeNum
			case _ => Double.NaN
		}
	}

	override def setRBracketNum(rBracketNum : Double) {
		arrowShapes.foreach{_.setRBracketNum(rBracketNum)}
	}

	override def setBracketNum(bracketNum : Double) {
		arrowShapes.foreach{_.setBracketNum(bracketNum)}
	}

	override def setArrowLength(lgth : Double) {
		arrowShapes.foreach{_.setArrowLength(lgth)}
	}

	override def setArrowSizeDim(arrowSizeDim : Double) {
		arrowShapes.foreach{_.setArrowSizeDim(arrowSizeDim)}
	}

	override def setArrowSizeNum(arrowSizeNum : Double) {
		arrowShapes.foreach{_.setArrowSizeNum(arrowSizeNum)}
	}

	override def setArrowInset(inset : Double) {
		arrowShapes.foreach{_.setArrowInset(inset)}
	}

	override def getDotSizeDim() : Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getDotSizeDim
			case _ => Double.NaN
		}
	}

	override def getDotSizeNum() : Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getDotSizeNum
			case _ => Double.NaN
		}
	}

	override def getBracketNum() : Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getBracketNum
			case _ => Double.NaN
		}
	}

	override def getArrowSizeNum() : Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowSizeNum
			case _ => Double.NaN
		}
	}

	override def getArrowSizeDim() : Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowSizeDim
			case _ => Double.NaN
		}
	}

	override def getArrowInset() : Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowInset
			case _ => Double.NaN
		}
	}

	override def getArrowLength() : Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowLength
			case _ => Double.NaN
		}
	}

	override def getRBracketNum() : Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getRBracketNum
			case _ => Double.NaN
		}
	}
}
