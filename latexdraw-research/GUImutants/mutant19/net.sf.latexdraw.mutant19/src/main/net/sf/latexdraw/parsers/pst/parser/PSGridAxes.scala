package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IShape
import scala.collection.mutable.ListBuffer
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IGrid
import net.sf.latexdraw.glib.views.pst.PSTricksConstants
import net.sf.latexdraw.glib.models.interfaces.IStandardGrid
import net.sf.latexdraw.util.LNumber

/**
 * A parser grouping parsers parsing grids and axes.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 2012-05-06<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSGridAxes extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser {
	/**
	 * Parses psgrid commands.
	 */
	def parsePsgrid(ctx : PSTContext) : Parser[List[IShape]] =
		"\\psgrid" ~ opt(parseParam(ctx)) ~ opt(parseCoord(ctx)) ~ opt(parseCoord(ctx)) ~ opt(parseCoord(ctx)) ^^ {
		case cmdName ~ _ ~ p1 ~ p2 ~ p3 =>

		(p1, p2, p3) match {
			case (Some(pt1), Some(pt2), Some(pt3)) => checkTextParsed(ctx) ::: List(createGrid(pt1, pt2, pt3, ctx))
			case (Some(pt1), Some(pt2), None) => checkTextParsed(ctx) ::: List(createGrid(pt1.dup, pt1, pt2, ctx))
			case (Some(pt1), None, None) =>
				checkTextParsed(ctx) ::: List(createGrid(new PointUnit(0,0, "", ""), new PointUnit(0,0, "", ""), pt1, ctx))
			case _ =>
				val gridEnd = new PointUnit(getApproxCoord(ctx.pictureNEPt.getX), getApproxCoord(ctx.pictureNEPt.getY), "", "")
				val gridStart = new PointUnit(getApproxCoord(ctx.pictureSWPt.getX), getApproxCoord(ctx.pictureSWPt.getY), "", "")
				checkTextParsed(ctx) ::: List(createGrid(gridStart.dup, gridStart, gridEnd, ctx))
		}
	}



	private def createGrid(origin : PointUnit, min : PointUnit, max : PointUnit, ctx : PSTContext) : IGrid = {
		val grid = DrawingTK.getFactory.createGrid(true, DrawingTK.getFactory.createPoint)
		val position = DrawingTK.getFactory.createPoint(ctx.pictureSWPt.getX*IShape.PPC, ctx.pictureSWPt.getY*IShape.PPC*(-1.0))

		setStdGridParams(origin, min, max, grid, ctx)

		grid.setPosition(position)
		grid.setUnit(ctx.unit)
		grid.setGridDots(ctx.gridDots.toInt)
		grid.setGridLabelsColour(ctx.gridlabelcolor)
		grid.setLabelsSize((ctx.gridLabel*IShape.PPC).toInt)
		grid.setGridWidth(scala.math.abs(ctx.gridWidth*IShape.PPC))
		grid.setSubGridColour(ctx.subGridCol)
		grid.setSubGridDiv(ctx.subGridDiv.toInt)
		grid.setSubGridDots(ctx.subGridDots.toInt)
		grid.setSubGridWidth(scala.math.abs(ctx.subGridWidth*IShape.PPC))
		grid
	}


	/** Sets the parameters of std grids (axes and grids). */
	private def setStdGridParams(origin : PointUnit, min : PointUnit, max : PointUnit, grid : IStandardGrid, ctx : PSTContext) {
		var gridEndX = max.x
		var gridEndY = max.y
		var gridStartX = min.x
		var gridStartY = min.y
		var isGridXLabelInverted = false
		var isGridYLabelInverted = false

		if(gridStartX>=gridEndX) {
			val tmp = gridEndX
			gridEndX = gridStartX
			gridStartX = tmp
			isGridXLabelInverted = true
		}

		if(gridStartY>=gridEndY) {
			val tmp = gridEndY
			gridEndY = gridStartY
			gridStartY = tmp
			isGridYLabelInverted = true
		}

		grid.setLineColour(ctx.gridColor)
		grid.setOriginX(origin.x)
		grid.setOriginY(origin.y)
		grid.setGridEndX(gridEndX)
		grid.setGridEndY(gridEndY)
		grid.setGridStartX(gridStartX)
		grid.setGridStartY(gridStartY)
		grid.setXLabelSouth(!isGridYLabelInverted)
		grid.setYLabelWest(!isGridXLabelInverted)
	}



	private def getApproxCoord(value : Double) : Double = {
		if(value>=0)
			if(value-value.toInt>=0.5)
				value.toInt+1
			else
				value.toInt
		else
			if(value.toInt-value>=0.5)
				value.toInt-1
			else
				value.toInt+1
	}
}
