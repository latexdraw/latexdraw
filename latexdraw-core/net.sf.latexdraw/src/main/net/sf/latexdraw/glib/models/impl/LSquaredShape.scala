package net.sf.latexdraw.glib.models.impl

import java.awt.geom.Rectangle2D

import net.sf.latexdraw.glib.models.GLibUtilities
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.Position
import net.sf.latexdraw.glib.models.interfaces.shape.ISquaredShape

private[impl] abstract class LSquaredShape(tl:IPoint, width:Double) extends LPositionShape(tl) with ISquaredShape {

	require(GLibUtilities.isValidPoint(tl) && width>0 && GLibUtilities.isValidCoordinate(width))
	points.add(ShapeFactory.createPoint(tl))
	points.add(ShapeFactory.createPoint(tl))
	points.add(ShapeFactory.createPoint(tl))
	setWidth(width)

	override def scale(prevWidth:Double, prevHeight:Double, pos:Position, bound:Rectangle2D) {
		if(bound==null || pos==null) return ;
		scaleSetPointsWithRatio(points, prevWidth, prevHeight, pos, bound)
	}

	override def setWidth(width:Double) {
		if(GLibUtilities.isValidCoordinate(width) && width>0) {
			val pt = points.get(points.size-1)
			val xPos = pt.getX + width
			val yPos = pt.getY - width
			points.get(1).setX(xPos)
			points.get(2).setX(xPos)
			points.get(0).setY(yPos)
			points.get(1).setY(yPos)
		}
	}

	override def getHeight = getWidth

	override def getWidth = points.get(1).getX - points.get(0).getX

	override def isBordersMovable = true

	override def isDbleBorderable = true

	override def isFillable = true

	override def isInteriorStylable = true

	override def isLineStylable = true

	override def isShadowable = true

	override def isThicknessable = true
}