package net.sf.latexdraw.glib.models.impl

import java.awt.geom.Rectangle2D
import java.awt.Color
import scala.collection.JavaConversions.asScalaBuffer
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * This trait encapsulates the code of the group related to the support of the general shape's properties.<br>
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
protected trait LGroupShape extends IGroup {
	override def mirrorHorizontal(origin:IPoint) {
		getShapes.foreach{sh => sh.mirrorHorizontal(origin)}
	}

	override def mirrorVertical(origin:IPoint) {
		getShapes.foreach{sh => sh.mirrorVertical(origin)}
	}

	override def setThickness(thickness : Double) = getShapes.foreach{shape => shape.setThickness(thickness) }


	override def scale(sx : Double, sy : Double, pos : IShape.Position, bound : Rectangle2D) {
		getShapes.foreach{sh => sh.scale(sx, sy, pos, bound)}
	}


	override def getThickness() : Double = {
		getShapes.find{shape => shape.isThicknessable} match {
			case Some(sh) => sh.getThickness
			case _ => Double.NaN
		}
	}


	override def isThicknessable() = getShapes.exists{shape => shape.isThicknessable}


	override def isShowPtsable() = getShapes.exists{shape => shape.isShowPtsable}


	override def isShowPts() = getShapes.exists{shape => shape.isShowPtsable && shape.isShowPts}


	override def setShowPts(show : Boolean) {
		getShapes.foreach{shape =>
			if(shape.isShowPtsable)
				shape.setShowPts(show)
		}
	}


	override def getLineColour() : Color = {
		getShapes.headOption match {
			case Some(shape) => shape.getLineColour
			case _ => null
		}
	}


	override def isLineStylable() = getShapes.exists{shape => shape.isLineStylable}


	override def getLineStyle() : IShape.LineStyle = {
		getShapes.find{shape => shape.isLineStylable} match {
			case Some(sh) => sh.getLineStyle
			case _ => null
		}
	}


	override def setLineStyle(style : IShape.LineStyle) = {
		getShapes.foreach{shape =>
			if(shape.isLineStylable)
				shape.setLineStyle(style)
		}
	}



	override def isBordersMovable() = getShapes.exists{shape => shape.isBordersMovable}


	override def getBordersPosition() : IShape.BorderPos = {
		getShapes.find{shape => shape.isBordersMovable} match {
			case Some(sh) => sh.getBordersPosition
			case _ => null
		}
	}


	override def setBordersPosition(position : IShape.BorderPos) = {
		getShapes.foreach{shape =>
			if(shape.isBordersMovable)
				shape.setBordersPosition(position)
		}
	}


	override def setLineColour(lineColour : Color) = getShapes.foreach{shape => shape.setLineColour(lineColour)}


	override def setDbleBordCol(colour : Color) = {
		getShapes.foreach{shape =>
			if(shape.isDbleBorderable)
				shape.setDbleBordCol(colour)
		}
	}


	override def getDbleBordCol() : Color = {
		getShapes.find{shape => shape.hasDbleBord} match {
			case Some(sh) => sh.getDbleBordCol
			case _ => null
		}
	}


	override def hasDbleBord() = getShapes.exists{shape => shape.hasDbleBord && shape.isDbleBorderable }


	override def setHasDbleBord(dbleBorders : Boolean) = {
		getShapes.foreach{shape =>
			if(shape.isDbleBorderable)
				shape.setHasDbleBord(dbleBorders)
		}
	}


	override def isDbleBorderable() = getShapes.exists{shape => shape.isDbleBorderable }


	override def setDbleBordSep(dbleBorderSep : Double) = {
		getShapes.foreach{shape =>
			if(shape.isDbleBorderable)
				shape.setDbleBordSep(dbleBorderSep)
		}
	}


	override def getDbleBordSep() : Double = {
		getShapes.find{shape => shape.isDbleBorderable && shape.hasDbleBord} match {
			case Some(sh) => sh.getDbleBordSep
			case _ => Double.NaN
		}
	}


	override def isShadowable() = getShapes.exists{shape => shape.isShadowable }


	override def hasShadow() = getShapes.exists{shape => shape.isShadowable && shape.hasShadow }


	override def setHasShadow(shadow : Boolean) = {
		getShapes.foreach{shape =>
			if(shape.isShadowable)
				shape.setHasShadow(shadow)
		}
	}


	override def setShadowSize(shadSize : Double) = {
		getShapes.foreach{shape =>
			if(shape.isShadowable)
				shape.setShadowSize(shadSize)
		}
	}


	override def getShadowSize() : Double = {
		getShapes.find{shape => shape.isShadowable && shape.hasShadow} match {
			case Some(sh) => sh.getShadowSize
			case _ => Double.NaN
		}
	}


	override def setShadowAngle(shadAngle : Double) = {
		getShapes.foreach{shape =>
			if(shape.isShadowable)
				shape.setShadowAngle(shadAngle)
		}
	}


	override def getShadowAngle() : Double = {
		getShapes.find{shape => shape.isShadowable && shape.hasShadow} match {
			case Some(sh) => sh.getShadowAngle
			case _ => Double.NaN
		}
	}


	override def setShadowCol(colour : Color) = {
		getShapes.foreach{shape =>
			if(shape.isShadowable)
				shape.setShadowCol(colour)
		}
	}


	override def getShadowCol() : Color = {
		getShapes.find{shape => shape.isShadowable && shape.hasShadow} match {
			case Some(sh) => sh.getShadowCol
			case _ => null
		}
	}


	override def getFillingStyle() : IShape.FillingStyle = {
		getShapes.find{shape => shape.isInteriorStylable} match {
			case Some(sh) => sh.getFillingStyle
			case _ => null
		}
	}


	override def setFillingStyle(style : IShape.FillingStyle) = {
		getShapes.foreach{shape =>
			if(shape.isInteriorStylable)
				shape.setFillingStyle(style)
		}
	}


	override def isFillable() = getShapes.exists{shape => shape.isFillable }


	override def isInteriorStylable() = getShapes.exists{shape => shape.isInteriorStylable }


	override def setFillingCol(colour : Color) = {
		getShapes.foreach{shape =>
			if(shape.isFillable)
				shape.setFillingCol(colour)
		}
	}


	override def getFillingCol() : Color = {
		getShapes.find{shape => shape.isFillable && shape.isFilled} match {
			case Some(sh) => sh.getFillingCol
			case _ => null
		}
	}


	override def setHatchingsCol(colour : Color) = {
		getShapes.foreach{shape =>
			if(shape.isInteriorStylable)
				shape.setHatchingsCol(colour)
		}
	}


	override def getHatchingsCol() : Color = {
		getShapes.find{shape => shape.isInteriorStylable && shape.getFillingStyle.isHatchings} match {
			case Some(sh) => sh.getHatchingsCol
			case _ => null
		}
	}


	override def setGradColStart(colour : Color) = {
		getShapes.foreach{shape =>
			if(shape.isInteriorStylable)
				shape.setGradColStart(colour)
		}
	}


	override def getGradColStart() : Color = {
		getShapes.find{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient} match {
			case Some(sh) => sh.getGradColStart
			case _ => null
		}
	}


	override def setGradColEnd(colour : Color) = {
		getShapes.foreach{shape =>
			if(shape.isInteriorStylable)
				shape.setGradColEnd(colour)
		}
	}


	override def getGradColEnd() : Color = {
		getShapes.find{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient} match {
			case Some(sh) => sh.getGradColEnd
			case _ => null
		}
	}


	override def setGradAngle(gradAngle : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInteriorStylable)
				shape.setGradAngle(gradAngle)
		}
	}


	override def getGradAngle() : Double = {
		getShapes.find{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient} match {
			case Some(sh) => sh.getGradAngle
			case _ => Double.NaN
		}
	}


	override def setGradMidPt(gradMidPoint : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInteriorStylable)
				shape.setGradMidPt(gradMidPoint)
		}
	}


	override def getGradMidPt() : Double = {
		getShapes.find{shape => shape.isInteriorStylable && shape.getFillingStyle.isGradient} match {
			case Some(sh) => sh.getGradMidPt
			case _ => Double.NaN
		}
	}


	override def getHatchingsAngle() : Double = {
		getShapes.find{shape => shape.isInteriorStylable} match {
			case Some(sh) => sh.getHatchingsAngle
			case _ => Double.NaN
		}
	}


	override def getHatchingsSep() : Double = {
		getShapes.find{shape => shape.isInteriorStylable} match {
			case Some(sh) => sh.getHatchingsSep
			case _ => Double.NaN
		}
	}


	override def getHatchingsWidth() : Double = {
		getShapes.find{shape => shape.isInteriorStylable} match {
			case Some(sh) => sh.getHatchingsWidth
			case _ => Double.NaN
		}
	}


	override def setHatchingsAngle(hatchingsAngle : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInteriorStylable)
				shape.setHatchingsAngle(hatchingsAngle)
		}
	}


	override def setHatchingsSep(hatchingsSep : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInteriorStylable)
				shape.setHatchingsSep(hatchingsSep)
		}
	}


	override def setHatchingsWidth(hatchingsWidth : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInteriorStylable)
				shape.setHatchingsWidth(hatchingsWidth)
		}
	}


	override def translate(tx : Double, ty : Double) = getShapes.foreach{shape => shape.translate(tx, ty)}


	override def addToRotationAngle(gravCentre : IPoint, angle : Double) = {
		val gc : IPoint = if(gravCentre==null) getGravityCentre else gravCentre
		getShapes.foreach{shape => shape.addToRotationAngle(gc, angle)}
	}


	override def setRotationAngle(rotationAngle : Double) = getShapes.foreach{shape => shape.setRotationAngle(rotationAngle)}


	override def rotate(point : IPoint, angle : Double) = getShapes.foreach{shape => shape.rotate(point, angle)}


	override def getRotationAngle() : Double = {
		getShapes.headOption match {
			case Some(sh) => sh.getRotationAngle
			case _ => 0.0
		}
	}


	override def getGravityCentre() : IPoint = {
		size match {
			case 0 => new LPoint()
			case _ => getTopLeftPoint.getMiddlePoint(getBottomRightPoint)
		}
	}


	override def getBottomRightPoint() : IPoint = {
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
		return new LPoint(x, y)
	}


	override def getBottomLeftPoint() : IPoint = {
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
		return new LPoint(x, y)
	}


	override def getTopLeftPoint() : IPoint = {
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
		return new LPoint(x, y)
	}


	override def getTopRightPoint() : IPoint = {
		var x : Double = Double.NaN
		var y : Double = Double.NaN

		if(size>0) {
			var tr : IPoint = null
			x = Double.MinValue
			y = Double.MaxValue

			getShapes.foreach{shape =>
				tr = shape.getTopRightPoint()
				if(tr.getX()>x)
					x = tr.getX()
				if(tr.getY()<y)
					y = tr.getY()
			}
		}

		return new LPoint(x, y)
	}


	override def hasHatchings() = getShapes.exists{shape => shape.hasHatchings}


	override def hasGradient() = getShapes.exists{shape => shape.hasGradient}
}
