package net.sf.latexdraw.glib.models.impl

import java.util.ArrayList
import java.util.List
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow
import net.sf.latexdraw.glib.models.interfaces.shape.ArrowStyle
import net.sf.latexdraw.glib.models.interfaces.shape.IArrowableShape
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import java.util.stream.Collectors

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
private[impl] trait LGroupArrowable extends IGroup {
	/** May return the first grid of the group. */
	private def firstIArrowable = arrowShapes.stream.filter{_.isTypeOf(classOf[IArrowableShape])}.findFirst

	private def arrowShapes = getShapes.stream.filter{_.isInstanceOf[IArrowableShape]}.map[IArrowableShape]{_.asInstanceOf[IArrowableShape]}.collect(Collectors.toList())


	override def getArrowIndex(arrow:IArrow) = if(firstIArrowable.isPresent) firstIArrowable.get.getArrowIndex(arrow) else -1

	override def getNbArrows = if(firstIArrowable.isPresent) firstIArrowable.get.getNbArrows else 0

	override def setTBarSizeDimList(values:List[java.lang.Double]) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setTBarSizeDim(values.get(j))
          case _ =>
        }
	}

	override def getTBarSizeDimList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		getShapes.forEach{
				case arr:IArrowableShape => list.add(arr.getTBarSizeDim)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def setTBarSizeNumList(values:java.util.List[java.lang.Double]) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setTBarSizeNum(values.get(j))
          case _ =>
        }
	}

	override def getTBarSizeNumList: java.util.List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		getShapes.forEach{
				case arr:IArrowableShape => list.add(arr.getTBarSizeNum)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def setDotSizeNumList(values:java.util.List[java.lang.Double]) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setDotSizeNum(values.get(j))
          case _ =>
        }
	}

	override def getDotSizeNumList: java.util.List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		getShapes.forEach{
				case arr:IArrowableShape => list.add(arr.getDotSizeNum)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def setDotSizeDimList(values:java.util.List[java.lang.Double]) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setDotSizeDim(values.get(j))
          case _ =>
        }
	}

	override def getDotSizeDimList: java.util.List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		getShapes.forEach{
				case arr:IArrowableShape => list.add(arr.getDotSizeDim)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def setBracketNumList(values:java.util.List[java.lang.Double]) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setBracketNum(values.get(j))
          case _ =>
        }
	}

	override def getBracketNumList: java.util.List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		getShapes.forEach{
				case arr:IArrowableShape => list.add(arr.getBracketNum)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def setRBracketNumList(values:java.util.List[java.lang.Double]) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setRBracketNum(values.get(j))
          case _ =>
        }
	}

	override def getRBracketNumList: java.util.List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		getShapes.forEach{
				case arr:IArrowableShape => list.add(arr.getRBracketNum)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def setArrowSizeNumList(values:java.util.List[java.lang.Double]) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setArrowSizeNum(values.get(j))
          case _ =>
        }
	}

	override def getArrowSizeNumList: java.util.List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		getShapes.forEach{
				case arr:IArrowableShape => list.add(arr.getArrowSizeNum)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def setArrowSizeDimList(values:java.util.List[java.lang.Double]) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setArrowSizeDim(values.get(j))
          case _ =>
        }
	}

	override def getArrowSizeDimList: java.util.List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		getShapes.forEach{
				case arr:IArrowableShape => list.add(arr.getArrowSizeDim)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def setArrowLengthList(values:java.util.List[java.lang.Double]) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setArrowLength(values.get(j))
          case _ =>
        }
	}

	override def getArrowLengthList: java.util.List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		getShapes.forEach{
				case arr:IArrowableShape => list.add(arr.getArrowLength)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def setArrowInsetList(values:java.util.List[java.lang.Double]) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setArrowInset(values.get(j))
          case _ =>
        }
	}

	override def getArrowInsetList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		getShapes.forEach{
				case arr:IArrowableShape => list.add(arr.getArrowInset)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def getArrowStyleList(i : Int) : java.util.List[ArrowStyle] =
		getShapes.stream.map[ArrowStyle]{case arr:IArrowableShape => arr.getArrowStyle(i); case _ => ArrowStyle.NONE}.collect(Collectors.toList())

	override def setArrowStyleList(values : java.util.List[ArrowStyle], i : Int) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setArrowStyle(values.get(j), i)
          case _ =>
        }
	}

	override def setArrowStyle(style : ArrowStyle, position : Int) {
		arrowShapes.forEach{_.setArrowStyle(style, position)}
	}

	override def getArrowStyle(position : Int) = if(firstIArrowable.isPresent) firstIArrowable.get.getArrowStyle(position) else ArrowStyle.NONE

	override def getArrowAt(position : Int) = if(firstIArrowable.isPresent) firstIArrowable.get.getArrowAt(position) else null

	override def getArrowLine(arrow : IArrow) = if(firstIArrowable.isPresent) firstIArrowable.get.getArrowLine(arrow) else null

	override def setDotSizeDim(dotSizeDim : Double) {
		arrowShapes.forEach{_.setDotSizeDim(dotSizeDim)}
	}

	override def setDotSizeNum(dotSizeNum : Double) {
		arrowShapes.forEach{_.setDotSizeNum(dotSizeNum)}
	}

	override def setTBarSizeNum(tbarSizeNum : Double) {
		arrowShapes.forEach{_.setTBarSizeNum(tbarSizeNum)}
	}

	override def setTBarSizeDim(tbarSizeDim : Double) {
		arrowShapes.forEach{_.setTBarSizeDim(tbarSizeDim)}
	}

	override def getTBarSizeDim = if(firstIArrowable.isPresent) firstIArrowable.get.getTBarSizeDim else Double.NaN

	override def getTBarSizeNum = if(firstIArrowable.isPresent) firstIArrowable.get.getTBarSizeNum else Double.NaN

	override def setRBracketNum(rBracketNum : Double) {
		arrowShapes.forEach{_.setRBracketNum(rBracketNum)}
	}

	override def setBracketNum(bracketNum : Double) {
		arrowShapes.forEach{_.setBracketNum(bracketNum)}
	}

	override def setArrowLength(lgth : Double) {
		arrowShapes.forEach{_.setArrowLength(lgth)}
	}

	override def setArrowSizeDim(arrowSizeDim : Double) {
		arrowShapes.forEach{_.setArrowSizeDim(arrowSizeDim)}
	}

	override def setArrowSizeNum(arrowSizeNum : Double) {
		arrowShapes.forEach{_.setArrowSizeNum(arrowSizeNum)}
	}

	override def setArrowInset(inset : Double) {
		arrowShapes.forEach{_.setArrowInset(inset)}
	}

	override def getDotSizeDim = if(firstIArrowable.isPresent) firstIArrowable.get.getDotSizeDim else Double.NaN

	override def getDotSizeNum = if(firstIArrowable.isPresent) firstIArrowable.get.getDotSizeNum else Double.NaN

	override def getBracketNum  = if(firstIArrowable.isPresent) firstIArrowable.get.getBracketNum else Double.NaN

	override def getArrowSizeNum = if(firstIArrowable.isPresent) firstIArrowable.get.getArrowSizeNum else Double.NaN

	override def getArrowSizeDim = if(firstIArrowable.isPresent) firstIArrowable.get.getArrowSizeDim else Double.NaN

	override def getArrowInset = if(firstIArrowable.isPresent) firstIArrowable.get.getArrowInset else Double.NaN

	override def getArrowLength = if(firstIArrowable.isPresent) firstIArrowable.get.getArrowLength else Double.NaN

	override def getRBracketNum = if(firstIArrowable.isPresent) firstIArrowable.get.getRBracketNum else Double.NaN
}
