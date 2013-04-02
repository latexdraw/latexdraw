package net.sf.latexdraw.glib.models.impl

import java.awt.Color
import java.util.ArrayList
import java.util.List

import scala.collection.JavaConversions._

import org.malai.mapping.ActiveArrayList

import net.sf.latexdraw.glib.models.interfaces.IArc.ArcStyle
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle
import net.sf.latexdraw.glib.models.interfaces.IAxes.AxesStyle
import net.sf.latexdraw.glib.models.interfaces.IAxes.PlottingStyle
import net.sf.latexdraw.glib.models.interfaces.IAxes.TicksStyle
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle
import net.sf.latexdraw.glib.models.interfaces.IFreehand.FreeHandType
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition
import net.sf.latexdraw.glib.models.interfaces._

/**
 * A Group is a group of IShape instances.<br>
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
protected class LGroup(uniqueID : java.lang.Boolean) extends LShape(uniqueID)
			with LGroupArc with LGroupArrowable with LGroupAxes
			with LGroupDottable with LGroupFreeHand with LGroupLineArc
			with LGroupGrid with LGroupShape with LGroupStdGrid
			with LGroupText with LSetShapes {

	override def duplicate() = duplicateDeep(false)


	override def setModified(modified : Boolean) = {
		getShapes.foreach{shape => shape.setModified(modified)}
		super.setModified(modified)
	}


	override def duplicateDeep(duplicateShapes : Boolean) : IGroup = {
		val dup = new LGroup(true)

		if(duplicateShapes)
			shapes.foreach{sh => dup.addShape(sh.duplicate)}
		else
			shapes.foreach{sh => dup.addShape(sh)}

		return dup
	}


	override def isTypeOf(clazz : java.lang.Class[_]) : Boolean = {
		if(clazz==null)
			return false

		if(clazz.equals(getClass) || clazz.equals(classOf[IShape]) ||
			clazz.equals(classOf[LShape]) || clazz.equals(classOf[IGroup]))
			return true

		return shapes.exists{sh => sh.isTypeOf(clazz)}
	}


	override def setFreeHandIntervalList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IFreehand])
					shapes.get(i).asInstanceOf[IFreehand].setInterval(values.get(i))
	}


	override def getFreeHandIntervalList() : List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.foreach{_ match {
				case fh : IFreehand => list.add(fh.getInterval)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setFreeHandOpenList(values : List[java.lang.Boolean]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IFreehand])
					shapes.get(i).asInstanceOf[IFreehand].setOpen(values.get(i))
	}


	override def getFreeHandOpenList() : List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.foreach{_ match {
				case fh : IFreehand => list.add(fh.isOpen)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setGridLabelsColourList(values : List[Color]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IGrid])
					shapes.get(i).asInstanceOf[IGrid].setGridLabelsColour(values.get(i))
	}


	override def getGridLabelsColourList() : List[Color] = {
		val list = new ArrayList[Color]()
		shapes.foreach{_ match {
				case grid : IGrid => list.add(grid.getGridLabelsColour)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setSubGridColourList(values : List[Color]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IGrid])
					shapes.get(i).asInstanceOf[IGrid].setSubGridColour(values.get(i))
	}


	override def getSubGridColourList() : List[Color] = {
		val list = new ArrayList[Color]()
		shapes.foreach{_ match {
				case grid : IGrid => list.add(grid.getSubGridColour)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setGridWidthList(values : List[java.lang.Double]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IGrid])
					shapes.get(i).asInstanceOf[IGrid].setGridWidth(values.get(i))
	}


	override def getGridWidthList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{_ match {
				case grid : IGrid => list.add(grid.getGridWidth)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setSubGridWidthList(values : List[java.lang.Double]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IGrid])
					shapes.get(i).asInstanceOf[IGrid].setSubGridWidth(values.get(i))
	}


	override def getSubGridWidthList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{_ match {
				case grid : IGrid => list.add(grid.getSubGridWidth)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setGridDotsList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IGrid])
					shapes.get(i).asInstanceOf[IGrid].setGridDots(values.get(i))
	}


	override def getGridDotsList() : List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.foreach{_ match {
				case grid : IGrid => list.add(grid.getGridDots)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setSubGridDotsList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IGrid])
					shapes.get(i).asInstanceOf[IGrid].setSubGridDots(values.get(i))
	}


	override def getSubGridDotsList() : List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.foreach{_ match {
				case grid : IGrid => list.add(grid.getSubGridDots)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setSubGridDivList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IGrid])
					shapes.get(i).asInstanceOf[IGrid].setSubGridDiv(values.get(i))
	}


	override def getSubGridDivList() : List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.foreach{_ match {
				case grid : IGrid => list.add(grid.getSubGridDiv)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setFreeHandTypeList(values : List[FreeHandType]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IFreehand])
					shapes.get(i).asInstanceOf[IFreehand].setType(values.get(i))
	}


	override def getFreeHandTypeList() : List[FreeHandType] = {
		val list = new ArrayList[FreeHandType]()
		shapes.foreach{_ match {
				case fh : IFreehand => list.add(fh.getType)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setAxesDistLabelsList(values : List[IPoint]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setDistLabels(values.get(i))
	}


	override def getAxesDistLabelsList() : List[IPoint] = {
		val list = new ArrayList[IPoint]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.getDistLabels)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setAxesLabelsDisplayedList(values : List[PlottingStyle]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setLabelsDisplayed(values.get(i))
	}


	override def getAxesLabelsDisplayedList() : List[PlottingStyle] = {
		val list = new ArrayList[PlottingStyle]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.getLabelsDisplayed)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setAxesShowOriginList(values : List[java.lang.Boolean]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setShowOrigin(values.get(i))
	}


	override def getAxesShowOriginList() : List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.isShowOrigin)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setAxesTicksStyleList(values : List[TicksStyle]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setTicksStyle(values.get(i))
	}


	override def getAxesTicksStyleList() : List[TicksStyle] = {
		val list = new ArrayList[TicksStyle]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.getTicksStyle)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setAxesTicksSizeList(values : List[java.lang.Double]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setTicksSize(values.get(i))
	}


	override def getAxesTicksSizeList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.getTicksSize)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setAxesTicksDisplayedList(values : List[PlottingStyle]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setTicksDisplayed(values.get(i))
	}


	override def getAxesTicksDisplayedList() : List[PlottingStyle] = {
		val list = new ArrayList[PlottingStyle]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.getTicksDisplayed)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setAxesIncrementsList(values : List[IPoint]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setIncrement(values.get(i))
	}


	override def getAxesIncrementsList() : List[IPoint] = {
		val list = new ArrayList[IPoint]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.getIncrement)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setGridLabelSizeList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setLabelsSize(values.get(i))
	}


	override def getGridLabelSizeList() : List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.getLabelsSize)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setGridXLabelSouthList(values : List[java.lang.Boolean]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setXLabelSouth(values.get(i))
	}


	override def getGridXLabelSouthList() : List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.isXLabelSouth)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setGridYLabelWestList(values : List[java.lang.Boolean]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setYLabelWest(values.get(i))
	}


	override def getGridYLabelWestList() : List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.isYLabelWest)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setAxesStyleList(values : List[AxesStyle]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IAxes])
					shapes.get(i).asInstanceOf[IAxes].setAxesStyle(values.get(i))
	}


	override def getAxesStyleList() : List[AxesStyle] = {
		val list = new ArrayList[AxesStyle]()
		shapes.foreach{_ match {
				case axes : IAxes => list.add(axes.getAxesStyle)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setGridOriginList(values : List[IPoint]) = {
		var pt : IPoint = null
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size) {
				pt = values.get(i)
				if(pt!=null && shapes.get(i).isInstanceOf[IStandardGrid])
					shapes.get(i).asInstanceOf[IStandardGrid].setOrigin(pt.getX, pt.getY)
			}
	}


	override def getGridOriginList() : List[IPoint] = {
		val list = new ArrayList[IPoint]()
		shapes.foreach{_ match {
				case grid : IStandardGrid => list.add(new LPoint(grid.getOriginX, grid.getOriginY))
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setGridEndList(values : List[IPoint]) = {
		var pt : IPoint = null
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size) {
				pt = values.get(i)
				if(pt!=null && shapes.get(i).isInstanceOf[IStandardGrid])
					shapes.get(i).asInstanceOf[IStandardGrid].setGridEnd(pt.getX, pt.getY)
			}
	}


	override def setGridStartList(values : List[IPoint]) = {
		var pt : IPoint = null
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size) {
				pt = values.get(i)
				if(pt!=null && shapes.get(i).isInstanceOf[IStandardGrid])
					shapes.get(i).asInstanceOf[IStandardGrid].setGridStart(pt.getX, pt.getY)
			}
	}


	override def getGridStartList() : List[IPoint] = {
		val list = new ArrayList[IPoint]()
		shapes.foreach{_ match {
				case grid : IStandardGrid => list.add(grid.getGridStart)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def getGridEndList() : List[IPoint] = {
		val list = new ArrayList[IPoint]()
		shapes.foreach{_ match {
				case grid : IStandardGrid => list.add(grid.getGridEnd)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def getBordersPositionList() : List[BorderPos] = {
		val list = new ArrayList[BorderPos]()
		shapes.foreach{sh => sh.isBordersMovable match {
				case true => list.add(sh.getBordersPosition)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getLineColourList() : List[Color] = {
		val list = new ArrayList[Color]()
		shapes.foreach{sh => list.add(sh.getLineColour)}
		return list
	}


	override def setBordersPositionList(list : List[BorderPos]) = {
		if(list!=null && list.size==shapes.size)
			for(i <- 0 until list.size)
				if(shapes.get(i).isBordersMovable)
					shapes.get(i).setBordersPosition(list.get(i))
	}


	override def setLineColourList(list : List[Color]) = {
		if(list!=null && list.size==shapes.size)
			for(i <- 0 until list.size)
				shapes.get(i).setLineColour(list.get(i))
	}


	override def getAngleStartList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{_ match {
				case arc : IArc => list.add(arc.getAngleStart)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def getAngleEndList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{_ match {
				case arc : IArc => list.add(arc.getAngleEnd)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def getArcStyleList() : List[ArcStyle] = {
		val list = new ArrayList[ArcStyle]()
		shapes.foreach{_ match {
				case arc : IArc => list.add(arc.getArcStyle)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def getArrowStyleList(i : Int) : List[ArrowStyle] = {
		val list = new ArrayList[ArrowStyle]()
		shapes.foreach{sh => sh.isArrowable match {
				case true => list.add(sh.getArrowStyle(i))
				case false => list.add(null)
			}
		}
		return list
	}


	override def getRotationAngleList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{sh => list.add(sh.getRotationAngle)}
		return list
	}


	override def getTextPositionList() : List[TextPosition] = {
		val list = new ArrayList[TextPosition]()
		shapes.foreach{_ match {
				case txt : IText => list.add(txt.getTextPosition)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def getTextList() : List[String] = {
		val list = new ArrayList[String]()
		shapes.foreach{_ match {
				case txt : IText => list.add(txt.getText)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def getHatchingsAngleList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getHatchingsAngle)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getHatchingsWidthList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getHatchingsWidth)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getHatchingsSepList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getHatchingsSep)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getGradAngleList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getGradAngle)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getGradMidPtList(): List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getGradMidPt)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getLineArcList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{_ match {
				case lineArc : ILineArcShape => list.add(lineArc.getLineArc)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def getFillingColList() : List[Color] = {
		val list = new ArrayList[Color]()
		shapes.foreach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getFillingCol)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getHatchingsColList() : List[Color] = {
		val list = new ArrayList[Color]()
		shapes.foreach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getHatchingsCol)
				case false => list.add(null)
			}
		}
		return list
	}


	override def hasDbleBordList() : List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.foreach{sh => sh.isDbleBorderable match {
				case true => list.add(sh.hasDbleBord)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getDbleBordSepList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{sh => sh.isDbleBorderable match {
				case true => list.add(sh.getDbleBordSep)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getDbleBordColList() : List[Color] = {
		val list = new ArrayList[Color]()
		shapes.foreach{sh => sh.isDbleBorderable match {
				case true => list.add(sh.getDbleBordCol)
				case false => list.add(null)
			}
		}
		return list
	}


	override def hasShadowList() : List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.foreach{sh => sh.isShadowable match {
				case true => list.add(sh.hasShadow)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getShadowSizeList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{sh => sh.isShadowable match {
				case true => list.add(sh.getShadowSize)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getShadowAngleList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{sh => sh.isShadowable match {
				case true => list.add(sh.getShadowAngle)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getShadowColList() : List[Color] = {
		val list = new ArrayList[Color]()
		shapes.foreach{sh => sh.isShadowable match {
				case true => list.add(sh.getShadowCol)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getGradColStartList() : List[Color] = {
		val list = new ArrayList[Color]()
		shapes.foreach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getGradColStart)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getGradColEndList() : List[Color] = {
		val list = new ArrayList[Color]()
		shapes.foreach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getGradColEnd)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getThicknessList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{sh => sh.isThicknessable match {
				case true => list.add(sh.getThickness)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getFillingStyleList() : List[FillingStyle] = {
		val list = new ArrayList[FillingStyle]()
		shapes.foreach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getFillingStyle)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getLineStyleList() : List[LineStyle] = {
		val list = new ArrayList[LineStyle]()
		shapes.foreach{sh => sh.isLineStylable match {
				case true => list.add(sh.getLineStyle)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getDotFillingColList() : List[Color] = {
		val list = new ArrayList[Color]()
		shapes.foreach{_ match {
				case dot : Dottable => list.add(dot.getDotFillingCol)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def getDotStyleList() : List[DotStyle] = {
		val list = new ArrayList[DotStyle]()
		shapes.foreach{_ match {
				case dot : Dottable => list.add(dot.getDotStyle)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def getDotSizeList() : List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.foreach{_ match {
				case dot : Dottable => list.add(dot.getRadius)
				case _ => list.add(null)
			}
		}
		return list
	}


	override def setAngleStartList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IArc])
					shapes.get(i).asInstanceOf[IArc].setAngleStart(values.get(i))
	}


	override def setDotStyleList(values : List[DotStyle]) {
		println(values.toString + " " + shapes)
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[Dottable])
					shapes.get(i).asInstanceOf[Dottable].setDotStyle(values.get(i))
	}


	override def setAngleEndList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IArc])
					shapes.get(i).asInstanceOf[IArc].setAngleEnd(values.get(i))
	}


	override def setArcStyleList(values : List[ArcStyle]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IArc])
					shapes.get(i).asInstanceOf[IArc].setArcStyle(values.get(i))
	}


	override def setArrowStyleList(values : List[ArrowStyle], i : Int) {
		if(values!=null && values.size==shapes.size)
			for(j <- 0 until values.size)
				if(shapes.get(j).isArrowable)
					shapes.get(j).setArrowStyle(values.get(j), i)
	}


	override def setRotationAngleList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i).setRotationAngle(values.get(i))
	}


	override def setTextPositionList(values : List[TextPosition]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IText])
					shapes.get(i).asInstanceOf[IText].setTextPosition(values.get(i))
	}


	override def setTextList(values : List[String]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[IText])
					shapes.get(i).asInstanceOf[IText].setText(values.get(i))
	}


	override def setHatchingsAngleList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInteriorStylable)
					shapes.get(i).setHatchingsAngle(values.get(i))
	}


	override def setHatchingsWidthList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInteriorStylable)
					shapes.get(i).setHatchingsWidth(values.get(i))
	}


	override def setHatchingsSepList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInteriorStylable)
					shapes.get(i).setHatchingsSep(values.get(i))
	}


	override def setGradAngleList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInteriorStylable)
					shapes.get(i).setGradAngle(values.get(i))
	}


	override def setGradMidPtList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInteriorStylable)
					shapes.get(i).setGradMidPt(values.get(i))
	}


	override def setLineArcList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[ILineArcShape])
					shapes.get(i).asInstanceOf[ILineArcShape].setLineArc(values.get(i))
	}


	override def setFillingColList(values : List[Color]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInteriorStylable)
					shapes.get(i).setFillingCol(values.get(i))
	}


	override def setHatchingsColList(values : List[Color]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInteriorStylable)
					shapes.get(i).setHatchingsCol(values.get(i))
	}


	override def setHasDbleBordList(values : List[java.lang.Boolean]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isDbleBorderable)
					shapes.get(i).setHasDbleBord(values.get(i))
	}


	override def setDbleBordSepList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isDbleBorderable)
					shapes.get(i).setDbleBordSep(values.get(i))
	}


	override def setDbleBordColList(values : List[Color]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isDbleBorderable)
					shapes.get(i).setDbleBordCol(values.get(i))
	}


	override def setHasShadowList(values : List[java.lang.Boolean]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isShadowable)
					shapes.get(i).setHasShadow(values.get(i))
	}


	override def setShadowSizeList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isShadowable)
					shapes.get(i).setShadowSize(values.get(i))
	}


	override def setShadowAngleList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isShadowable)
					shapes.get(i).setShadowAngle(values.get(i))
	}


	override def setShadowColList(values : List[Color]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isShadowable)
					shapes.get(i).setShadowCol(values.get(i))
	}


	override def setGradColStartList(values : List[Color]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInteriorStylable)
					shapes.get(i).setGradColStart(values.get(i))
	}


	override def setGradColEndList(values : List[Color]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInteriorStylable)
					shapes.get(i).setGradColEnd(values.get(i))
	}


	override def setThicknessList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isThicknessable)
					shapes.get(i).setThickness(values.get(i))
	}


	override def setFillingStyleList(values : List[FillingStyle]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInteriorStylable)
					shapes.get(i).setFillingStyle(values.get(i))
	}


	override def setLineStyleList(values : List[LineStyle]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isLineStylable)
					shapes.get(i).setLineStyle(values.get(i))
	}


	override def setDotFillingColList(values : List[Color]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[Dottable])
					shapes.get(i).asInstanceOf[Dottable].setDotFillingCol(values.get(i))
	}


	override def setDotSizeList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isInstanceOf[Dottable])
					shapes.get(i).asInstanceOf[Dottable].setRadius(values.get(i))
	}


	override def setShowPointsList(values : List[java.lang.Boolean]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isShowPtsable)
					shapes.get(i).setShowPts(values.get(i))
	}


	override def getShowPointsList() : List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.foreach{sh => sh.isShowPtsable match {
				case true => list.add(sh.isShowPts)
				case false => list.add(null)
			}
		}
		return list
	}
}
