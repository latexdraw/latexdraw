package net.sf.latexdraw.glib.models.impl

import java.awt.geom.Rectangle2D

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.GLibUtilities
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.Position
import net.sf.latexdraw.glib.models.interfaces.shape.ISquaredShape
import net.sf.latexdraw.util.LNumber

private[impl] abstract class LSquaredShape(tl:IPoint, width:Double, uniqueID:Boolean) extends LPositionShape(uniqueID, tl) with ISquaredShape {

	require(GLibUtilities.isValidPoint(tl) && width>0)
	points.add(ShapeFactory.createPoint)
	points.add(ShapeFactory.createPoint)
	points.add(ShapeFactory.createPoint)
	setPosition(tl)
	setWidth(width)

	override def scale(sx:Double, sy:Double, pos:Position, bound:Rectangle2D) {
		var position = pos
		var scale = sx
		pos match {
			case Position.EAST => position = Position.SE; scale = sx
			case Position.WEST => position = Position.SW; scale = sx
			case Position.NORTH => position = Position.NW; scale = sy
			case Position.SOUTH => position = Position.SW; scale = sy
			case _ =>
		}
		scaleSetPoints(points, scale, position, bound)
	}

	protected def scaleSetPoints(pts:java.util.List[IPoint], scale:Double, pos:Position, bound:Rectangle2D) {
		val sx = scale/bound.getWidth
		val refX = if(pos.isWest) bound.getX else bound.getMaxX
		val refY = if(pos.isNorth) bound.getY else bound.getMaxY

		pts.foreach{pt =>
			if(!LNumber.equalsDouble(pt.getX, refX) || !LNumber.equalsDouble(pt.getY, refY)) {
				pt.setX(refX+(pt.getX-refX)*sx)
				pt.setY(refY+(pt.getY-refY)*sx)
			}
		}
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

	override def getHeight() = getWidth

	override def getWidth() = points.get(1).getX - points.get(0).getX

	override def isBordersMovable() = true

	override def isDbleBorderable() = true

	override def isFillable() = true

	override def isInteriorStylable() = true

	override def isLineStylable() = true

	override def isShadowable() = true

	override def isThicknessable() = true
}