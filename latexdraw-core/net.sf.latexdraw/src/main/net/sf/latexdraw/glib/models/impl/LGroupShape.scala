package net.sf.latexdraw.glib.models.impl

import java.awt.geom.Rectangle2D
import java.awt.Color
import scala.collection.JavaConversions.asScalaBuffer
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.ShapeFactory

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
	override def mirrorHorizontal(origin:IPoint) {
		getShapes.foreach{_.mirrorHorizontal(origin)}
	}

	override def mirrorVertical(origin:IPoint) {
		getShapes.foreach{_.mirrorVertical(origin)}
	}

	override def setThickness(thickness : Double) = getShapes.foreach{_.setThickness(thickness)}


	override def scale(sx : Double, sy : Double, pos : IShape.Position, bound : Rectangle2D) {
		getShapes.foreach{_.scale(sx, sy, pos, bound)}
	}


	override def getThickness: Double = {
		getShapes.find{_.isThicknessable} match {
			case Some(sh) => sh.getThickness
			case _ => Double.NaN
		}
	}


	override def isThicknessable = getShapes.exists{_.isThicknessable}


	override def isShowPtsable = getShapes.exists{_.isShowPtsable}


	override def isShowPts = getShapes.exists{shape => shape.isShowPtsable && shape.isShowPts}


	override def setShowPts(show : Boolean) {
		getShapes.filter{_.isShowPtsable}.foreach{_.setShowPts(show)}
	}


	override def getLineColour: Color = {
		getShapes.headOption match {
			case Some(shape) => shape.getLineColour
			case _ => Color.BLACK
		}
	}


	override def isLineStylable = getShapes.exists{_.isLineStylable}


	override def getLineStyle: IShape.LineStyle = {
		getShapes.find{_.isLineStylable} match {
			case Some(sh) => sh.getLineStyle
			case _ => IShape.LineStyle.SOLID
		}
	}


	override def setLineStyle(style : IShape.LineStyle) {
		getShapes.filter{_.isLineStylable}.foreach{_.setLineStyle(style)}
	}


	override def isBordersMovable = getShapes.exists{_.isBordersMovable}


	override def getBordersPosition: IShape.BorderPos = {
		getShapes.find{_.isBordersMovable} match {
			case Some(sh) => sh.getBordersPosition
			case _ => IShape.BorderPos.INTO
		}
	}


	override def setBordersPosition(position : IShape.BorderPos) {
		getShapes.filter{_.isBordersMovable}.foreach{_.setBordersPosition(position)}
	}


	override def setLineColour(lineColour : Color) {
		getShapes.foreach{_.setLineColour(lineColour)}
	}


	override def setDbleBordCol(colour : Color) {
		getShapes.filter{_.isDbleBorderable}.foreach{_.setDbleBordCol(colour)}
	}


	override def getDbleBordCol: Color = {
		getShapes.find{_.hasDbleBord} match {
			case Some(sh) => sh.getDbleBordCol
			case _ => Color.BLACK
		}
	}


	override def hasDbleBord = getShapes.exists{shape => shape.hasDbleBord && shape.isDbleBorderable }


	override def setHasDbleBord(dbleBorders : Boolean) {
		getShapes.filter{_.isDbleBorderable}.foreach{_.setHasDbleBord(dbleBorders)}
	}


	override def isDbleBorderable = getShapes.exists{_.isDbleBorderable}


	override def setDbleBordSep(dbleBorderSep : Double) {
		getShapes.filter{_.isDbleBorderable}.foreach{_.setDbleBordSep(dbleBorderSep)}
	}


	override def getDbleBordSep: Double = {
		getShapes.find{shape => shape.isDbleBorderable && shape.hasDbleBord} match {
			case Some(sh) => sh.getDbleBordSep
			case _ => Double.NaN
		}
	}


	override def isShadowable = getShapes.exists{_.isShadowable }


	override def hasShadow = getShapes.exists{shape => shape.isShadowable && shape.hasShadow }


	override def setHasShadow(shadow : Boolean) {
		getShapes.filter{_.isShadowable}.foreach{_.setHasShadow(shadow)}
	}


	override def setShadowSize(shadSize : Double) {
		getShapes.filter{_.isShadowable}.foreach{_.setShadowSize(shadSize)}
	}


	override def getShadowSize: Double = {
		getShapes.find{shape => shape.isShadowable && shape.hasShadow} match {
			case Some(sh) => sh.getShadowSize
			case _ => Double.NaN
		}
	}


	override def setShadowAngle(shadAngle : Double) {
		getShapes.filter{_.isShadowable}.foreach{_.setShadowAngle(shadAngle)}
	}


	override def getShadowAngle: Double = {
		getShapes.find{shape => shape.isShadowable && shape.hasShadow} match {
			case Some(sh) => sh.getShadowAngle
			case _ => Double.NaN
		}
	}


	override def setShadowCol(colour : Color) {
		getShapes.filter{_.isShadowable}.foreach{_.setShadowCol(colour)}
	}


	override def getShadowCol: Color = {
		getShapes.find{shape => shape.isShadowable && shape.hasShadow} match {
			case Some(sh) => sh.getShadowCol
			case _ => Color.BLACK
		}
	}


	override def getFillingStyle: IShape.FillingStyle = {
		getShapes.find{_.isInteriorStylable} match {
			case Some(sh) => sh.getFillingStyle
			case _ => IShape.FillingStyle.NONE
		}
	}


	override def setFillingStyle(style : IShape.FillingStyle) {
		getShapes.filter{_.isInteriorStylable}.foreach{_.setFillingStyle(style)}
	}


	override def isFillable = getShapes.exists{_.isFillable }


	override def isInteriorStylable = getShapes.exists{_.isInteriorStylable }


	override def setFillingCol(colour : Color) = {
		getShapes.filter{_.isFillable}.foreach{_.setFillingCol(colour)}
	}


	override def getFillingCol: Color = {
		getShapes.find{shape => shape.isFillable && shape.isFilled} match {
			case Some(sh) => sh.getFillingCol
			case _ => Color.BLACK
		}
	}


	override def setHatchingsCol(colour : Color) {
		getShapes.filter{_.isInteriorStylable}.foreach{_.setHatchingsCol(colour)}
	}


	override def getHatchingsCol: Color = {
		getShapes.find{shape => shape.isInteriorStylable && shape.getFillingStyle.isHatchings} match {
			case Some(sh) => sh.getHatchingsCol
			case _ => Color.BLACK
		}
	}


	override def setGradColStart(colour : Color) {
		getShapes.filter{_.isInteriorStylable}.foreach{_.setGradColStart(colour)}
	}


	override def getGradColStart: Color = {
		getShapes.find{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient} match {
			case Some(sh) => sh.getGradColStart
			case _ => Color.BLACK
		}
	}


	override def setGradColEnd(colour : Color) {
		getShapes.filter{_.isInteriorStylable}.foreach{_.setGradColEnd(colour)}
	}


	override def getGradColEnd: Color = {
		getShapes.find{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient} match {
			case Some(sh) => sh.getGradColEnd
			case _ => Color.BLACK
		}
	}


	override def setGradAngle(gradAngle : Double) {
		getShapes.filter{_.isInteriorStylable}.foreach{_.setGradAngle(gradAngle)}
	}


	override def getGradAngle: Double = {
		getShapes.find{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient} match {
			case Some(sh) => sh.getGradAngle
			case _ => Double.NaN
		}
	}


	override def setGradMidPt(gradMidPoint : Double) = {
		getShapes.filter{_.isInteriorStylable}.foreach{_.setGradMidPt(gradMidPoint)}
	}


	override def getGradMidPt: Double = {
		getShapes.find{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient} match {
			case Some(sh) => sh.getGradMidPt
			case _ => Double.NaN
		}
	}


	override def getHatchingsAngle: Double = {
		getShapes.find{shape => shape.isInteriorStylable} match {
			case Some(sh) => sh.getHatchingsAngle
			case _ => Double.NaN
		}
	}


	override def getHatchingsSep: Double = {
		getShapes.find{shape => shape.isInteriorStylable} match {
			case Some(sh) => sh.getHatchingsSep
			case _ => Double.NaN
		}
	}


	override def getHatchingsWidth: Double = {
		getShapes.find{shape => shape.isInteriorStylable} match {
			case Some(sh) => sh.getHatchingsWidth
			case _ => Double.NaN
		}
	}


	override def setHatchingsAngle(hatchingsAngle : Double) {
		getShapes.filter{_.isInteriorStylable}.foreach{_.setHatchingsAngle(hatchingsAngle)}
	}


	override def setHatchingsSep(hatchingsSep : Double) = {
		getShapes.filter{_.isInteriorStylable}.foreach{_.setHatchingsSep(hatchingsSep)}
	}


	override def setHatchingsWidth(hatchingsWidth : Double) = {
		getShapes.filter{_.isInteriorStylable}.foreach{_.setHatchingsWidth(hatchingsWidth)}
	}


	override def translate(tx : Double, ty : Double) = getShapes.foreach{_.translate(tx, ty)}


	override def addToRotationAngle(gravCentre : IPoint, angle : Double) = {
		val gc : IPoint = if(gravCentre==null) getGravityCentre else gravCentre
		getShapes.foreach{_.addToRotationAngle(gc, angle)}
	}


	override def setRotationAngle(rotationAngle : Double) = getShapes.foreach{_.setRotationAngle(rotationAngle)}


	override def rotate(point : IPoint, angle : Double) = getShapes.foreach{_.rotate(point, angle)}


	override def getRotationAngle: Double = {
		getShapes.headOption match {
			case Some(sh) => sh.getRotationAngle
			case _ => 0.0
		}
	}


	override def getGravityCentre: IPoint = {
		size match {
			case 0 => ShapeFactory.createPoint
			case _ => getTopLeftPoint.getMiddlePoint(getBottomRightPoint)
		}
	}


	override def getBottomRightPoint: IPoint = {
		var x : Double = Double.NaN
		var y : Double = Double.NaN

		if(size>0) {
			var br : IPoint = null
			x = Double.MinValue
			y = Double.MinValue

			getShapes.foreach{shape =>
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

			getShapes.foreach{shape =>
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

			getShapes.foreach{shape =>
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

			getShapes.foreach{shape =>
				tr = shape.getTopRightPoint
				if(tr.getX>x)
					x = tr.getX
				if(tr.getY<y)
					y = tr.getY
			}
		}

		return ShapeFactory.createPoint(x, y)
	}


	override def hasHatchings = getShapes.exists{_.hasHatchings}


	override def hasGradient = getShapes.exists{_.hasGradient}
}
