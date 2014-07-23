package net.sf.latexdraw.glib.models.impl

import java.util.ArrayList
import java.util.List

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList

import net.sf.latexdraw.glib.models.interfaces.shape.IArrow
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow.ArrowStyle
import net.sf.latexdraw.glib.models.interfaces.shape.IArrowableShape
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.glib.models.interfaces.shape.ILine

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
	private def firstIArrowable = arrowShapes.find{_.isTypeOf(classOf[IArrowableShape])}

	private def arrowShapes = getShapes.flatMap{case x:IArrowableShape => x::Nil; case _ => Nil}


	override def getArrowIndex(arrow:IArrow) =
		firstIArrowable match {
			case Some(arr) => arr.getArrowIndex(arrow)
			case _ => -1
		}

	override def getNbArrows =
		firstIArrowable match {
			case Some(arr) => arr.getNbArrows
			case _ => 0
		}

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
		getShapes.foreach{
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
		getShapes.foreach{
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
		getShapes.foreach{
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
		getShapes.foreach{
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
		getShapes.foreach{
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
		getShapes.foreach{
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
		getShapes.foreach{
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
		getShapes.foreach{
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
		getShapes.foreach{
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
		getShapes.foreach{
				case arr:IArrowableShape => list.add(arr.getArrowInset)
				case _ => list.add(java.lang.Double.NaN)
		}
		return list
	}

	override def getArrowStyleList(i : Int) : java.util.List[ArrowStyle] =
		getShapes.map{case arr:IArrowableShape => arr.getArrowStyle(i); case _ => ArrowStyle.NONE}

	override def setArrowStyleList(values : java.util.List[ArrowStyle], i : Int) {
		val shapes = getShapes
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				shapes.get(j) match {
          case shape: IArrowableShape => shape.setArrowStyle(values.get(j), i)
          case _ =>
        }
	}

	override def setArrowStyle(style : IArrow.ArrowStyle, position : Int) {
		arrowShapes.foreach{_.setArrowStyle(style, position)}
	}

	override def getArrowStyle(position : Int) : IArrow.ArrowStyle = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowStyle(position)
			case _ => IArrow.ArrowStyle.NONE
		}
	}

	override def getArrowAt(position : Int) : IArrow = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowAt(position)
			case _ => null
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

	override def getTBarSizeDim: Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getTBarSizeDim
			case _ => Double.NaN
		}
	}

	override def getTBarSizeNum: Double = {
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

	override def getDotSizeDim: Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getDotSizeDim
			case _ => Double.NaN
		}
	}

	override def getDotSizeNum: Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getDotSizeNum
			case _ => Double.NaN
		}
	}

	override def getBracketNum: Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getBracketNum
			case _ => Double.NaN
		}
	}

	override def getArrowSizeNum: Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowSizeNum
			case _ => Double.NaN
		}
	}

	override def getArrowSizeDim: Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowSizeDim
			case _ => Double.NaN
		}
	}

	override def getArrowInset: Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowInset
			case _ => Double.NaN
		}
	}

	override def getArrowLength: Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getArrowLength
			case _ => Double.NaN
		}
	}

	override def getRBracketNum: Double = {
		firstIArrowable match {
			case Some(arrowable) => arrowable.getRBracketNum
			case _ => Double.NaN
		}
	}
}
