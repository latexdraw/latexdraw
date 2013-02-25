package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.IArrow
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.ILine

/**
 * This trait encapsulates the code of the group related to the support of arrowable shapes.<br>
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
protected trait LGroupArrowable extends IGroup {
	/** May return the first grid of the group. */
	private def firstIArrowable = getShapes.find{shape => shape.isArrowable }

	override def setArrowStyle(style : IArrow.ArrowStyle, position : Int) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setArrowStyle(style, position)
		}
	}

	override def getArrowStyle(position : Int) : IArrow.ArrowStyle = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowStyle(position)
			case _ => null
		}
	}


	override def isArrowable() = getShapes.exists{shape => shape.isArrowable }


	override def getArrowAt(position : Int) : IArrow = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowAt(position)
			case _ => null
		}
	}


	override def getArrows() : java.util.List[IArrow] = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrows
			case _ => null
		}
	}


	override def getArrowLine(arrow : IArrow) : ILine = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowLine(arrow)
			case _ => null
		}
	}


	override def setDotSizeDim(dotSizeDim : Double) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setDotSizeDim(dotSizeDim)
		}
	}

	override def setDotSizeNum(dotSizeNum : Double) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setDotSizeNum(dotSizeNum)
		}
	}

	override def setTBarSizeNum(tbarSizeNum : Double) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setTBarSizeNum(tbarSizeNum)
		}
	}

	override def setTBarSizeDim(tbarSizeDim : Double) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setTBarSizeDim(tbarSizeDim)
		}
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

	override def setRBracketNum(rBracketNum : Double) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setRBracketNum(rBracketNum)
		}
	}

	override def setBracketNum(bracketNum : Double) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setBracketNum(bracketNum)
		}
	}

	override def setArrowLength(lgth : Double) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setArrowLength(lgth)
		}
	}

	override def setArrowSizeDim(arrowSizeDim : Double) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setArrowSizeDim(arrowSizeDim)
		}
	}

	override def setArrowSizeNum(arrowSizeNum : Double) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setArrowSizeNum(arrowSizeNum)
		}
	}

	override def setArrowInset(inset : Double) = {
		getShapes.foreach{shape =>
			if(shape.isArrowable)
				shape.setArrowInset(inset)
		}
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
