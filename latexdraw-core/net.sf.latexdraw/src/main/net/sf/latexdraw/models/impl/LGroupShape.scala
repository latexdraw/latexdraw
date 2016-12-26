package net.sf.latexdraw.models.impl

import java.awt.geom.Rectangle2D
import java.util.Optional
import javafx.collections.FXCollections

import net.sf.latexdraw.models.ShapeFactory
import net.sf.latexdraw.models.interfaces.shape._
import net.sf.latexdraw.view.pst.PSTricksConstants

/**
 * This trait encapsulates the code of the group related to the support of the general shape's properties.<br>
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
private[impl] trait LGroupShape extends IGroup {
  override def copy(sh: IShape) {
    //TODO
  }

  override def getBorderGap() = if(getShapes.isEmpty) 0.0 else getShapes.get(0).getBorderGap

  override def getDashSepBlack() =
		getShapes.stream.filter{_.isLineStylable}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getDashSepBlack
			case _ => Double.NaN
		}

  override def getDashSepWhite() =
		getShapes.stream.filter{_.isLineStylable}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getDashSepWhite
			case _ => Double.NaN
		}

  override def getDotSep() =
		getShapes.stream.filter{_.isLineStylable}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getDotSep
			case _ => Double.NaN
		}

	override def getFullBottomRightPoint() = {
		val gap = getBorderGap
		val br = getBottomRightPoint
		br.translate(gap, gap)
		br
	}

	override def getFullTopLeftPoint() = {
		val gap = getBorderGap
		val tl = getTopLeftPoint
		tl.translate(-gap, -gap)
		tl
	}

  override def getHeight() = if(getShapes.isEmpty) 0.0 else getShapes.get(0).getHeight

  override def getNbPoints() = 0

  override def getPoints() = FXCollections.emptyObservableList()

  override def getPtAt(x$1: Int) = null

  override def getWidth() = if(getShapes.isEmpty) 0.0 else getShapes.get(0).getWidth

  override def scaleWithRatio(x: Double, x$2: Double, x$3: net.sf.latexdraw.models.interfaces.shape.Position, x$4: java.awt.geom.Rectangle2D): Unit = ???

  override def setDashSepBlack(dash: Double) {
    getShapes.stream.filter{_.isLineStylable}.forEach{_.setDashSepBlack(dash)}
  }

  override def setDashSepWhite(dash: Double) {
    getShapes.stream.filter{_.isLineStylable}.forEach{_.setDashSepWhite(dash)}
  }

  override def setDotSep(dot: Double) {
    getShapes.stream.filter{_.isLineStylable}.forEach{_.setDotSep(dot)}
  }

  override def shadowFillsShape() = getShapes.stream.filter{_.shadowFillsShape}.findAny.isPresent

	override def mirrorHorizontal(origin:IPoint) {
		getShapes.forEach{_.mirrorHorizontal(origin)}
	}

	override def mirrorVertical(origin:IPoint) {
		getShapes.forEach{_.mirrorVertical(origin)}
	}

	override def setThickness(thickness : Double) {
	  getShapes.forEach{_.setThickness(thickness)}
	}

	override def scale(prevWidth : Double, prevHeight : Double, pos : Position, bound : Rectangle2D) {
	  if(getShapes.stream.filter{sh => sh.isInstanceOf[ISquaredShape] || sh.isInstanceOf[IStandardGrid] || sh.isInstanceOf[IDot]}.findAny().isPresent())
  		getShapes.forEach{_.scaleWithRatio(prevWidth, prevHeight, pos, bound)}
	  else
  		getShapes.forEach{_.scale(prevWidth, prevHeight, pos, bound)}
	}

	override def getThickness: Double = {
		getShapes.stream.filter{_.isThicknessable}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getThickness
			case _ => Double.NaN
		}
	}

	override def getFullThickness: Double = {
		getShapes.stream.filter{_.isThicknessable}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getThickness
			case _ => Double.NaN
		}
	}

  	override def isColourable = getShapes.stream.filter{_.isColourable}.findAny.isPresent

	override def isThicknessable = getShapes.stream.filter{_.isThicknessable}.findAny.isPresent

	override def isShowPtsable = getShapes.stream.filter{_.isShowPtsable}.findAny.isPresent

	override def isShowPts = getShapes.stream.filter{shape => shape.isShowPtsable && shape.isShowPts}.findAny.isPresent

	override def setShowPts(show : Boolean) {
		getShapes.stream.filter{_.isShowPtsable}.forEach{_.setShowPts(show)}
	}

	override def getLineColour = if(size>0) getShapes.get(0).getLineColour else PSTricksConstants.DEFAULT_LINE_COLOR

	override def isLineStylable = getShapes.stream.filter{_.isLineStylable}.findAny.isPresent

	override def getLineStyle =
		getShapes.stream.filter{_.isLineStylable}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getLineStyle
			case _ => LineStyle.SOLID
		}

	override def setLineStyle(style : LineStyle) {
		getShapes.stream.filter{_.isLineStylable}.forEach{_.setLineStyle(style)}
	}

	override def isBordersMovable = getShapes.stream.filter{_.isBordersMovable}.findAny.isPresent

	override def getBordersPosition =
		getShapes.stream.filter{_.isBordersMovable}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getBordersPosition
			case _ => BorderPos.INTO
		}

	override def setBordersPosition(position : BorderPos) {
		getShapes.stream.filter{_.isBordersMovable}.forEach{_.setBordersPosition(position)}
	}

	override def setLineColour(lineColour : Color) {
		getShapes.forEach{_.setLineColour(lineColour)}
	}

	override def setDbleBordCol(colour : Color) {
		getShapes.stream.filter{_.isDbleBorderable}.forEach{_.setDbleBordCol(colour)}
	}

	override def getDbleBordCol =
		getShapes.stream.filter{_.hasDbleBord}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getDbleBordCol
			case _ => PSTricksConstants.DEFAULT_DOUBLE_COLOR
		}

	override def hasDbleBord = getShapes.stream.filter{shape => shape.hasDbleBord && shape.isDbleBorderable }.findAny.isPresent

	override def setHasDbleBord(dbleBorders : Boolean) {
		getShapes.stream.filter{_.isDbleBorderable}.forEach{_.setHasDbleBord(dbleBorders)}
	}

	override def isDbleBorderable = getShapes.stream.filter{_.isDbleBorderable}.findAny.isPresent

	override def setDbleBordSep(dbleBorderSep : Double) {
		getShapes.stream.filter{_.isDbleBorderable}.forEach{_.setDbleBordSep(dbleBorderSep)}
	}

	override def getDbleBordSep =
		getShapes.stream.filter{shape => shape.isDbleBorderable && shape.hasDbleBord}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getDbleBordSep
			case _ => Double.NaN
		}

	override def isShadowable = getShapes.stream.filter{_.isShadowable }.findAny.isPresent

	override def hasShadow = getShapes.stream.filter{shape => shape.isShadowable && shape.hasShadow }.findAny.isPresent

	override def setHasShadow(shadow : Boolean) {
		getShapes.stream.filter{_.isShadowable}.forEach{_.setHasShadow(shadow)}
	}

	override def setShadowSize(shadSize : Double) {
		getShapes.stream.filter{_.isShadowable}.forEach{_.setShadowSize(shadSize)}
	}

	override def getShadowSize =
		getShapes.stream.filter{shape => shape.isShadowable && shape.hasShadow}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getShadowSize
			case _ => Double.NaN
		}

	override def setShadowAngle(shadAngle : Double) {
		getShapes.stream.filter{_.isShadowable}.forEach{_.setShadowAngle(shadAngle)}
	}

	override def getShadowAngle =
		getShapes.stream.filter{shape => shape.isShadowable && shape.hasShadow}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getShadowAngle
			case _ => Double.NaN
		}

	override def setShadowCol(colour : Color) {
		getShapes.stream.filter{_.isShadowable}.forEach{_.setShadowCol(colour)}
	}

	override def getShadowCol =
		getShapes.stream.filter{shape => shape.isShadowable && shape.hasShadow}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getShadowCol
			case _ => PSTricksConstants.DEFAULT_SHADOW_COLOR
		}

	override def getFillingStyle =
		getShapes.stream.filter{_.isInteriorStylable}.findFirst match {
			case opt:Optional[IShape] if opt.isPresent => opt.get.getFillingStyle
			case _ => FillingStyle.NONE
		}

	override def setFillingStyle(style : FillingStyle) {
		getShapes.stream.filter{_.isInteriorStylable}.forEach{_.setFillingStyle(style)}
	}

	override def setFilled(filled:Boolean) {
	  getShapes.stream.filter{_.isFillable}.forEach{_.setFilled(filled)}
	}

	override def isFilled = getShapes.stream.filter{sh => sh.isFillable && sh.isFilled}.findAny.isPresent

	override def isFillable = getShapes.stream.filter{_.isFillable }.findAny.isPresent

	override def isInteriorStylable = getShapes.stream.filter{_.isInteriorStylable }.findAny.isPresent

	override def setFillingCol(colour : Color) {
		getShapes.stream.filter{_.isFillable}.forEach{_.setFillingCol(colour)}
	}

	override def getFillingCol =
		getShapes.stream.filter{shape => shape.isFillable && shape.isFilled}.findFirst match {
			case opt:Optional[IShape] if(opt.isPresent) => opt.get.getFillingCol
			case _ => PSTricksConstants.DEFAULT_FILL_COLOR
		}

	override def setHatchingsCol(colour : Color) {
		getShapes.stream.filter{_.isInteriorStylable}.forEach{_.setHatchingsCol(colour)}
	}

	override def getHatchingsCol =
		getShapes.stream.filter{shape => shape.isInteriorStylable && shape.getFillingStyle.isHatchings}.findFirst match {
			case opt:Optional[IShape] if(opt.isPresent) => opt.get.getHatchingsCol
			case _ => PSTricksConstants.DEFAULT_HATCHING_COLOR
		}

	override def setGradColStart(colour : Color) {
		getShapes.stream.filter{_.isInteriorStylable}.forEach{_.setGradColStart(colour)}
	}

	override def getGradColStart =
		getShapes.stream.filter{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient}.findFirst match {
			case opt:Optional[IShape] if(opt.isPresent) => opt.get.getGradColStart
			case _ => PSTricksConstants.DEFAULT_GRADIENT_START_COLOR
		}

	override def setGradColEnd(colour : Color) {
		getShapes.stream.filter{_.isInteriorStylable}.forEach{_.setGradColEnd(colour)}
	}

	override def getGradColEnd =
		getShapes.stream.filter{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient}.findFirst match {
			case opt:Optional[IShape] if(opt.isPresent) => opt.get.getGradColEnd
			case _ => PSTricksConstants.DEFAULT_GRADIENT_END_COLOR
		}

	override def setGradAngle(gradAngle : Double) {
		getShapes.stream.filter{_.isInteriorStylable}.forEach{_.setGradAngle(gradAngle)}
	}

	override def getGradAngle =
		getShapes.stream.filter{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient}.findFirst match {
			case opt:Optional[IShape] if(opt.isPresent) => opt.get.getGradAngle
			case _ => Double.NaN
		}

	override def setGradMidPt(gradMidPoint : Double) {
		getShapes.stream.filter{_.isInteriorStylable}.forEach{_.setGradMidPt(gradMidPoint)}
	}

	override def getGradMidPt: Double = {
		getShapes.stream.filter{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient}.findFirst match {
			case opt:Optional[IShape] if(opt.isPresent) => opt.get.getGradMidPt
			case _ => Double.NaN
		}
	}

	override def getHatchingsAngle =
		getShapes.stream.filter{shape => shape.isInteriorStylable}.findFirst match {
			case opt:Optional[IShape] if(opt.isPresent) => opt.get.getHatchingsAngle
			case _ => Double.NaN
		}

	override def getHatchingsSep =
		getShapes.stream.filter{shape => shape.isInteriorStylable}.findFirst match {
			case opt:Optional[IShape] if(opt.isPresent) => opt.get.getHatchingsSep
			case _ => Double.NaN
		}

	override def getHatchingsWidth =
		getShapes.stream.filter{shape => shape.isInteriorStylable}.findFirst match {
			case opt:Optional[IShape] if(opt.isPresent) => opt.get.getHatchingsWidth
			case _ => Double.NaN
		}

	override def setHatchingsAngle(hatchingsAngle : Double) {
		getShapes.stream.filter{_.isInteriorStylable}.forEach{_.setHatchingsAngle(hatchingsAngle)}
	}

	override def setHatchingsSep(hatchingsSep : Double) {
		getShapes.stream.filter{_.isInteriorStylable}.forEach{_.setHatchingsSep(hatchingsSep)}
	}

	override def setHatchingsWidth(hatchingsWidth : Double) {
		getShapes.stream.filter{_.isInteriorStylable}.forEach{_.setHatchingsWidth(hatchingsWidth)}
	}

	override def translate(tx : Double, ty : Double) {
	  getShapes.forEach{_.translate(tx, ty)}
	}

	override def addToRotationAngle(gravCentre : IPoint, angle : Double) {
		val gc : IPoint = if(gravCentre==null) getGravityCentre else gravCentre
		getShapes.forEach{_.addToRotationAngle(gc, angle)}
	}

	override def setRotationAngle(rotationAngle : Double) {
	  getShapes.forEach{_.setRotationAngle(rotationAngle)}
	}

	override def rotate(point : IPoint, angle : Double) {
	  getShapes.forEach{_.rotate(point, angle)}
	}

	override def getRotationAngle = if(size>0) getShapes.get(0).getRotationAngle else 0.0

	override def getGravityCentre = if(size==0) ShapeFactory.createPoint else getTopLeftPoint.getMiddlePoint(getBottomRightPoint)

	override def getBottomRightPoint: IPoint = {
		var x : Double = Double.NaN
		var y : Double = Double.NaN

		if(size>0) {
			var br : IPoint = null
			x = Double.MinValue
			y = Double.MinValue

			getShapes.forEach{shape =>
				br = shape.getBottomRightPoint
				if(br.getX>x)
					x = br.getX
				if(br.getY>y)
					y = br.getY
			}
		}
		return ShapeFactory.createPoint(x, y)
	}

	override def getBottomLeftPoint: IPoint = {
		var x : Double = Double.NaN
		var y : Double = Double.NaN

		if(size>0) {
			var bl : IPoint = null
			x = Double.MaxValue
			y = Double.MinValue

			getShapes.forEach{shape =>
				bl = shape.getBottomLeftPoint
				if(bl.getX<x)
					x = bl.getX
				if(bl.getY>y)
					y = bl.getY
			}
		}
		return ShapeFactory.createPoint(x, y)
	}

	override def getTopLeftPoint: IPoint = {
		var x : Double = Double.NaN
		var y : Double = Double.NaN

		if(size>0) {
			var tl : IPoint = null
			x = Double.MaxValue
			y = Double.MaxValue

			getShapes.forEach{shape =>
				tl = shape.getTopLeftPoint
				if(tl.getX<x)
					x = tl.getX
				if(tl.getY<y)
					y = tl.getY
			}
		}
		return ShapeFactory.createPoint(x, y)
	}

	override def getTopRightPoint: IPoint = {
		var x : Double = Double.NaN
		var y : Double = Double.NaN

		if(size>0) {
			var tr : IPoint = null
			x = Double.MinValue
			y = Double.MaxValue

			getShapes.forEach{shape =>
				tr = shape.getTopRightPoint
				if(tr.getX>x)
					x = tr.getX
				if(tr.getY<y)
					y = tr.getY
			}
		}

		return ShapeFactory.createPoint(x, y)
	}

	override def hasHatchings = getShapes.stream.filter{_.hasHatchings}.findAny.isPresent

	override def hasGradient = getShapes.stream.filter{_.hasGradient}.findAny.isPresent
}
