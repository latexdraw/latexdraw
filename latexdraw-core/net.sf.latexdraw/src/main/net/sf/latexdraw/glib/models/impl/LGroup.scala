package net.sf.latexdraw.glib.models.impl

import java.util.ArrayList
import java.util.List
import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp
import net.sf.latexdraw.glib.models.interfaces.prop.IFreeHandProp
import net.sf.latexdraw.glib.models.interfaces.prop.IGridProp
import net.sf.latexdraw.glib.models.interfaces.prop.ILineArcProp
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp
import net.sf.latexdraw.glib.models.interfaces.prop.IScalable
import net.sf.latexdraw.glib.models.interfaces.prop.IStdGridProp
import net.sf.latexdraw.glib.models.interfaces.prop.ITextProp
import net.sf.latexdraw.glib.models.interfaces.shape.ArcStyle
import net.sf.latexdraw.glib.models.interfaces.shape.AxesStyle
import net.sf.latexdraw.glib.models.interfaces.shape.BorderPos
import net.sf.latexdraw.glib.models.interfaces.shape.Color
import net.sf.latexdraw.glib.models.interfaces.shape.DotStyle
import net.sf.latexdraw.glib.models.interfaces.shape.FillingStyle
import net.sf.latexdraw.glib.models.interfaces.shape.FreeHandStyle
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.interfaces.shape.LineStyle
import net.sf.latexdraw.glib.models.interfaces.shape.PlotStyle
import net.sf.latexdraw.glib.models.interfaces.shape.PlottingStyle
import net.sf.latexdraw.glib.models.interfaces.shape.TextPosition
import net.sf.latexdraw.glib.models.interfaces.shape.TicksStyle
import net.sf.latexdraw.glib.views.latex.DviPsColors

/**
 * A Group is a group of IShape instances.<br>
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
private[impl] class LGroup() extends LShape()
			with LGroupArc with LGroupArrowable with LGroupAxes
			with LGroupDot with LGroupFreeHand with LGroupLineArc
			with LGroupGrid with LGroupShape with LGroupStdGrid
			with LGroupText with LSetShapes with LPlotGroup {

	override def duplicate() = duplicateDeep(true)


	override def setModified(modified : Boolean) = {
		getShapes.forEach{sh => sh.setModified(modified)}
		super.setModified(modified)
	}


	override def duplicateDeep(duplicateShapes : Boolean) : IGroup = {
		val dup = ShapeFactory.createGroup()

		if(duplicateShapes)
			shapes.forEach{sh => dup.addShape(sh.duplicate)}
		else
			shapes.forEach{sh => dup.addShape(sh)}

		return dup
	}


	override def isTypeOf(clazz : java.lang.Class[_]) : Boolean = {
		if(clazz==null)
			return false

		if(clazz.equals(getClass) || clazz.equals(classOf[IShape]) ||
			clazz.equals(classOf[LShape]) || clazz.equals(classOf[IGroup]))
			return true

		return shapes.stream().filter{_.isTypeOf(clazz)}.findAny.isPresent
	}

	override def setPlotPolarList(values:List[java.lang.Boolean]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IPlotProp => prop.setPolar(values.get(i))
          case _ =>
        }
	}

	override def getPlotPolarList: List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.forEach{
				case sh : IPlotProp => list.add(sh.isPolar)
				case _ => list.add(false)
		}
		return list
	}

	override def setYScaleList(values : List[java.lang.Double]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IScalable => prop.setYScale(values.get(i))
          case _ =>
        }
	}

	override def getYScaleList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case fh : IScalable => list.add(fh.getYScale)
				case _ => list.add(null)
		}
		return list
	}

	override def setXScaleList(values : List[java.lang.Double]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IScalable => prop.setXScale(values.get(i))
          case _ =>
        }
	}

	override def getXScaleList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case fh : IScalable => list.add(fh.getXScale)
				case _ => list.add(null)
		}
		return list
	}

	override def setPlotMinXList(values : List[java.lang.Double]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IPlotProp => prop.setPlotMinX(values.get(i))
          case _ =>
        }
	}

	override def getPlotMinXList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case fh : IPlotProp => list.add(fh.getPlotMinX)
				case _ => list.add(null)
		}
		return list
	}

	override def setPlotMaxXList(values : List[java.lang.Double]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IPlotProp => prop.setPlotMaxX(values.get(i))
          case _ =>
        }
	}

	override def getPlotMaxXList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case fh : IPlotProp => list.add(fh.getPlotMaxX)
				case _ => list.add(null)
		}
		return list
	}

	override def setNbPlottedPointsList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IPlotProp => prop.setNbPlottedPoints(values.get(i))
          case _ =>
        }
	}

	override def getNbPlottedPointsList: List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.forEach{
				case fh : IPlotProp => list.add(fh.getNbPlottedPoints)
				case _ => list.add(null)
		}
		return list
	}

	override def setPlotStyleList(values : List[PlotStyle]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IPlotProp => prop.setPlotStyle(values.get(i))
          case _ =>
        }
	}

	override def getPlotStyleList: List[PlotStyle] = {
		val list = new ArrayList[PlotStyle]()
		shapes.foreach{
				case fh : IPlotProp => list.add(fh.getPlotStyle())
				case _ => list.add(null)
		}
		return list
	}

	override def setPlotEquationList(values : List[String]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IPlotProp => prop.setPlotEquation(values.get(i))
          case _ =>
        }
	}

	override def getPlotEquationList: List[String] = {
		val list = new ArrayList[String]()
		shapes.forEach{
				case fh : IPlotProp => list.add(fh.getPlotEquation)
				case _ => list.add(null)
		}
		return list
	}

	override def setFreeHandIntervalList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IFreeHandProp => prop.setInterval(values.get(i))
          case _ =>
        }
	}


	override def getFreeHandIntervalList: List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.forEach{
				case fh : IFreeHandProp => list.add(fh.getInterval)
				case _ => list.add(null)
		}
		return list
	}


	override def setFreeHandOpenList(values : List[java.lang.Boolean]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IFreeHandProp => prop.setOpen(values.get(i))
          case _ =>
        }
	}


	override def getFreeHandOpenList: List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.forEach{
				case fh : IFreeHandProp => list.add(fh.isOpen)
				case _ => list.add(null)
		}
		return list
	}


	override def setGridLabelsColourList(values : List[Color]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IGridProp => prop.setGridLabelsColour(values.get(i))
          case _ =>
        }
	}


	override def getGridLabelsColourList: List[Color] = getShapes.map{case sh:IGridProp => sh.getGridLabelsColour; case _ => DviPsColors.BLACK}

	override def setSubGridColourList(values : List[Color]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IGridProp => prop.setSubGridColour(values.get(i))
          case _ =>
        }
	}


	override def getSubGridColourList: List[Color] = getShapes.map{case sh:IGridProp => sh.getSubGridColour; case _ => DviPsColors.BLACK}

	override def setGridWidthList(values : List[java.lang.Double]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IGridProp => prop.setGridWidth(values.get(i))
          case _ =>
        }
	}


	override def getGridWidthList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case grid : IGridProp => list.add(grid.getGridWidth)
				case _ => list.add(null)
		}
		return list
	}


	override def setSubGridWidthList(values : List[java.lang.Double]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IGridProp => prop.setSubGridWidth(values.get(i))
          case _ =>
        }
	}


	override def getSubGridWidthList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case grid : IGridProp => list.add(grid.getSubGridWidth)
				case _ => list.add(null)
		}
		return list
	}


	override def setGridDotsList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IGridProp => prop.setGridDots(values.get(i))
          case _ =>
        }
	}


	override def getGridDotsList: List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.forEach{
				case grid : IGridProp => list.add(grid.getGridDots)
				case _ => list.add(null)
		}
		return list
	}


	override def setSubGridDotsList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IGridProp => prop.setSubGridDots(values.get(i))
          case _ =>
        }
	}


	override def getSubGridDotsList: List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.forEach{
				case grid : IGridProp => list.add(grid.getSubGridDots)
				case _ => list.add(null)
		}
		return list
	}


	override def setSubGridDivList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IGridProp => prop.setSubGridDiv(values.get(i))
          case _ =>
        }
	}


	override def getSubGridDivList: List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.forEach{
				case grid : IGridProp => list.add(grid.getSubGridDiv)
				case _ => list.add(null)
		}
		return list
	}


	override def setFreeHandTypeList(values : List[FreeHandStyle]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IFreeHandProp => prop.setType(values.get(i))
          case _ =>
        }
	}

	override def getFreeHandTypeList: List[FreeHandStyle] = getShapes.map{case sh:IFreeHandProp => sh.getType; case _ => FreeHandStyle.CURVES}

	override def setAxesDistLabelsList(values : List[IPoint]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IAxesProp => prop.setDistLabels(values.get(i))
          case _ =>
        }
	}

	override def getAxesDistLabelsList: List[IPoint] = getShapes.map{case sh:IAxesProp => sh.getDistLabels; case _ => null}

	override def setAxesLabelsDisplayedList(values : List[PlottingStyle]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IAxesProp => prop.setLabelsDisplayed(values.get(i))
          case _ =>
        }
	}

	override def getAxesLabelsDisplayedList: List[PlottingStyle] = getShapes.map{case sh:IAxesProp => sh.getLabelsDisplayed; case _ => PlottingStyle.NONE}

	override def setAxesShowOriginList(values : List[java.lang.Boolean]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IAxesProp => prop.setShowOrigin(values.get(i))
          case _ =>
        }
	}


	override def getAxesShowOriginList: List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.forEach{
				case axes : IAxesProp => list.add(axes.isShowOrigin)
				case _ => list.add(null)
		}
		return list
	}


	override def setAxesTicksStyleList(values : List[TicksStyle]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IAxesProp => prop.setTicksStyle(values.get(i))
          case _ =>
        }
	}

	override def getAxesTicksStyleList: List[TicksStyle] = getShapes.map{case sh:IAxesProp => sh.getTicksStyle; case _ => TicksStyle.FULL}

	override def setAxesTicksSizeList(values : List[java.lang.Double]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IAxesProp => prop.setTicksSize(values.get(i))
          case _ =>
        }
	}


	override def getAxesTicksSizeList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case axes : IAxesProp => list.add(axes.getTicksSize)
				case _ => list.add(null)
		}
		return list
	}


	override def setAxesTicksDisplayedList(values : List[PlottingStyle]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IAxesProp => prop.setTicksDisplayed(values.get(i))
          case _ =>
        }
	}

	override def getAxesTicksDisplayedList: List[PlottingStyle] = getShapes.map{case sh:IAxesProp => sh.getTicksDisplayed; case _ => PlottingStyle.NONE}

	override def setAxesIncrementsList(values : List[IPoint]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IAxesProp => prop.setIncrement(values.get(i))
          case _ =>
        }
	}

	override def getAxesIncrementsList: List[IPoint] = getShapes.map{case sh:IAxesProp => sh.getIncrement; case _ => null}

	override def setGridLabelSizeList(values : List[java.lang.Integer]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IStdGridProp => prop.setLabelsSize(values.get(i))
          case _ =>
        }
	}


	override def getGridLabelSizeList: List[java.lang.Integer] = {
		val list = new ArrayList[java.lang.Integer]()
		shapes.forEach{
				case axes : IStdGridProp => list.add(axes.getLabelsSize)
				case _ => list.add(null)
		}
		return list
	}


	override def setGridXLabelSouthList(values : List[java.lang.Boolean]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IGridProp => prop.setXLabelSouth(values.get(i))
          case _ =>
        }
	}


	override def getGridXLabelSouthList: List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.forEach{
				case axes : IGridProp => list.add(axes.isXLabelSouth)
				case _ => list.add(null)
		}
		return list
	}


	override def setGridYLabelWestList(values : List[java.lang.Boolean]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IGridProp => prop.setYLabelWest(values.get(i))
          case _ =>
        }
	}


	override def getGridYLabelWestList: List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.forEach{
				case axes : IGridProp => list.add(axes.isYLabelWest)
				case _ => list.add(null)
		}
		return list
	}


	override def setAxesStyleList(values : List[AxesStyle]) = {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IAxesProp => prop.setAxesStyle(values.get(i))
          case _ =>
        }
	}

	override def getAxesStyleList: List[AxesStyle] = getShapes.map{case sh:IAxesProp => sh.getAxesStyle; case _ => AxesStyle.NONE}

	override def setGridOriginList(values : List[IPoint]) = {
		var pt : IPoint = null
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size) {
				pt = values.get(i)
				if(pt!=null && shapes.get(i).isInstanceOf[IStdGridProp])
					shapes.get(i).asInstanceOf[IStdGridProp].setOrigin(pt.getX, pt.getY)
			}
	}

	override def getGridOriginList: List[IPoint] =
		getShapes.map{case sh:IStdGridProp => ShapeFactory.createPoint(sh.getOriginX, sh.getOriginY); case _ => null}

	override def setGridEndList(values : List[IPoint]) = {
		var pt : IPoint = null
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size) {
				pt = values.get(i)
				if(pt!=null && shapes.get(i).isInstanceOf[IStdGridProp])
					shapes.get(i).asInstanceOf[IStdGridProp].setGridEnd(pt.getX, pt.getY)
			}
	}


	override def setGridStartList(values : List[IPoint]) = {
		var pt : IPoint = null
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size) {
				pt = values.get(i)
				if(pt!=null && shapes.get(i).isInstanceOf[IStdGridProp])
					shapes.get(i).asInstanceOf[IStdGridProp].setGridStart(pt.getX, pt.getY)
			}
	}

	override def getGridStartList: List[IPoint] = getShapes.map{case sh:IStdGridProp => sh.getGridStart; case _ => null}

	override def getGridEndList: List[IPoint] = getShapes.map{case sh:IStdGridProp => sh.getGridEnd; case _ => null}

	override def getBordersPositionList: List[BorderPos] =
		getShapes.map{sh => sh.isBordersMovable match { case true => sh.getBordersPosition; case false => BorderPos.INTO}}

	override def getLineColourList: List[Color] = getShapes.map{_.getLineColour}

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


	override def getAngleStartList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case arc : IArcProp => list.add(arc.getAngleStart)
				case _ => list.add(null)
		}
		return list
	}


	override def getAngleEndList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case arc : IArcProp => list.add(arc.getAngleEnd)
				case _ => list.add(null)
		}
		return list
	}

	override def getArcStyleList: List[ArcStyle] = getShapes.map{case sh:IArcProp => sh.getArcStyle; case _ => ArcStyle.ARC}

	override def getRotationAngleList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{sh => list.add(sh.getRotationAngle)}
		return list
	}

	override def getTextPositionList: List[TextPosition] = getShapes.map{case sh:ITextProp => sh.getTextPosition; case _ => TextPosition.BOT_LEFT}

	override def getTextList: List[String] = getShapes.map{case sh:ITextProp => sh.getText; case _ => ""}

	override def getHatchingsAngleList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getHatchingsAngle)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getHatchingsWidthList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getHatchingsWidth)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getHatchingsSepList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getHatchingsSep)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getGradAngleList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getGradAngle)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getGradMidPtList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{sh => sh.isInteriorStylable match {
				case true => list.add(sh.getGradMidPt)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getLineArcList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case lineArc : ILineArcProp => list.add(lineArc.getLineArc)
				case _ => list.add(null)
		}
		return list
	}

	override def getFillingColList: List[Color] = getShapes.map{sh => sh.isInteriorStylable match { case true => sh.getFillingCol; case false => DviPsColors.BLACK}}

	override def getHatchingsColList: List[Color] = getShapes.map{sh => sh.isInteriorStylable match { case true => sh.getHatchingsCol; case false => DviPsColors.BLACK}}

	override def hasDbleBordList: List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.forEach{sh => sh.isDbleBorderable match {
				case true => list.add(sh.hasDbleBord)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getDbleBordSepList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{sh => sh.isDbleBorderable match {
				case true => list.add(sh.getDbleBordSep)
				case false => list.add(null)
			}
		}
		return list
	}

	override def getDbleBordColList: List[Color] = getShapes.map{sh => sh.isDbleBorderable match { case true => sh.getDbleBordCol; case false => DviPsColors.BLACK}}

	override def hasShadowList: List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.forEach{sh => sh.isShadowable match {
				case true => list.add(sh.hasShadow)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getShadowSizeList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{sh => sh.isShadowable match {
				case true => list.add(sh.getShadowSize)
				case false => list.add(null)
			}
		}
		return list
	}


	override def getShadowAngleList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{sh => sh.isShadowable match {
				case true => list.add(sh.getShadowAngle)
				case false => list.add(null)
			}
		}
		return list
	}

	override def getShadowColList: List[Color] = getShapes.map{sh => sh.isShadowable match { case true => sh.getShadowCol; case false => DviPsColors.BLACK}}

	override def getGradColStartList: List[Color] = getShapes.map{sh => sh.isInteriorStylable match { case true => sh.getGradColStart; case false => DviPsColors.BLACK}}

	override def getGradColEndList: List[Color] = getShapes.map{sh => sh.isInteriorStylable match { case true => sh.getGradColEnd; case false => DviPsColors.BLACK}}

	override def getThicknessList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{sh => sh.isThicknessable match {
				case true => list.add(sh.getThickness)
				case false => list.add(null)
			}
		}
		return list
	}

	override def getFillingStyleList: List[FillingStyle] =
		getShapes.map{sh => sh.isInteriorStylable match { case true => sh.getFillingStyle; case false => FillingStyle.NONE}}

	override def getLineStyleList: List[LineStyle] =
		getShapes.map{sh => sh.isLineStylable match { case true => sh.getLineStyle; case false => LineStyle.SOLID}}

	override def getDotFillingColList: List[Color] = getShapes.map{case sh:IDotProp => sh.getDotFillingCol; case _ => DviPsColors.BLACK}

	override def getDotStyleList: List[DotStyle] = getShapes.map{case sh:IDotProp => sh.getDotStyle; case _ => DotStyle.DOT}

	override def getDotSizeList: List[java.lang.Double] = {
		val list = new ArrayList[java.lang.Double]()
		shapes.forEach{
				case dot : IDotProp => list.add(dot.getDiametre)
				case _ => list.add(null)
		}
		return list
	}


	override def setAngleStartList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IArcProp => prop.setAngleStart(values.get(i))
          case _ =>
        }
	}


	override def setDotStyleList(values : List[DotStyle]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IDotProp => prop.setDotStyle(values.get(i))
          case _ =>
        }
	}


	override def setAngleEndList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IArcProp => prop.setAngleEnd(values.get(i))
          case _ =>
        }
	}


	override def setArcStyleList(values : List[ArcStyle]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IArcProp => prop.setArcStyle(values.get(i))
          case _ =>
        }
	}


	override def setRotationAngleList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i).setRotationAngle(values.get(i))
	}


	override def setTextPositionList(values : List[TextPosition]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: ITextProp => prop.setTextPosition(values.get(i))
          case _ =>
        }
	}


	override def setTextList(values : List[String]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: ITextProp => prop.setText(values.get(i))
          case _ =>
        }
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
				shapes.get(i) match {
          case prop: ILineArcProp => prop.setLineArc(values.get(i))
          case _ =>
        }
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
				shapes.get(i) match {
          case prop: IDotProp => prop.setDotFillingCol(values.get(i))
          case _ =>
        }
	}


	override def setDotSizeList(values : List[java.lang.Double]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				shapes.get(i) match {
          case prop: IDotProp => prop.setDiametre(values.get(i))
          case _ =>
        }
	}


	override def setShowPointsList(values : List[java.lang.Boolean]) {
		if(values!=null && values.size==shapes.size)
			for(i <- 0 until values.size)
				if(shapes.get(i).isShowPtsable)
					shapes.get(i).setShowPts(values.get(i))
	}


	override def getShowPointsList: List[java.lang.Boolean] = {
		val list = new ArrayList[java.lang.Boolean]()
		shapes.forEach{sh => sh.isShowPtsable match {
				case true => list.add(sh.isShowPts)
				case false => list.add(null)
			}
		}
		return list
	}
}
