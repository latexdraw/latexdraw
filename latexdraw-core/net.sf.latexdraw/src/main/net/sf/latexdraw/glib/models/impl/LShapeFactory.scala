package net.sf.latexdraw.glib.models.impl

import java.awt.Point
import net.sf.latexdraw.glib.models.interfaces._
import net.sf.latexdraw.badaboom.BadaboomCollector
import java.awt.geom.Point2D

/**
 * This factory creates shapes.<br>
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
 * 2012-04-19<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LShapeFactory extends IShapeFactory {
	/** The map that maps types to creation operations. */
	private val factoryMap: Map[Class[_], () => IShape] = Map(
			  (classOf[ICircleArc], () => createCircleArc(true)),
			  (classOf[LCircleArc], () => createCircleArc(true)),
			  (classOf[IArc], () => createArc(true)),
			  (classOf[LArc], () => createArc(true)),
			  (classOf[ICircle], () => createCircle(true)),
			  (classOf[LCircle], () => createCircle(true)),
			  (classOf[IEllipse], () => createEllipse(true)),
			  (classOf[LEllipse], () => createEllipse(true)),
			  (classOf[ISquare], () => createSquare(true)),
			  (classOf[LSquare], () => createSquare(true)),
			  (classOf[IRectangle], () => createRectangle(true)),
			  (classOf[LRectangle], () => createRectangle(true)),
			  (classOf[ITriangle], () => createTriangle(true)),
			  (classOf[LTriangle], () => createTriangle(true)),
			  (classOf[IRhombus], () => createRhombus(true)),
			  (classOf[LRhombus], () => createRhombus(true)),
			  (classOf[IPolyline], () => createPolyline(true)),
			  (classOf[LPolyline], () => createPolyline(true)),
			  (classOf[IPolygon], () => createPolygon(true)),
			  (classOf[LPolygon], () => createPolygon(true)),
			  (classOf[IAxes], () => createAxes(true, createPoint)),
			  (classOf[LAxes], () => createAxes(true, createPoint)),
			  (classOf[IGrid], () => createGrid(true, createPoint)),
			  (classOf[LGrid], () => createGrid(true, createPoint)),
			  (classOf[IBezierCurve], () => createBezierCurve(true)),
			  (classOf[LBezierCurve], () => createBezierCurve(true)),
			  (classOf[IDot], () => createDot(createPoint, true)),
			  (classOf[LDot], () => createDot(createPoint, true)),
			  (classOf[IFreehand], () => createFreeHand(createPoint, true)),
			  (classOf[LFreehand], () => createFreeHand(createPoint, true)),
			  (classOf[IGroup], () => createGroup(true)),
			  (classOf[LGroup], () => createGroup(true)),
			  (classOf[IPicture], () => createPicture(true, createPoint)),
			  (classOf[LPicture], () => createPicture(true, createPoint)),
			  (classOf[IText], () => createText(true)),
			  (classOf[LText], () => createText(true)))


	override def newShape[T <: IShape](shapeClass : java.lang.Class[T]) : Option[T] =
		shapeClass match {
			case null => None
			case _ =>
				try { Some(shapeClass.cast(factoryMap(shapeClass)())) }
				catch { case ex => BadaboomCollector.INSTANCE.add(ex); None }
		}

	override def createPoint(pt:Point2D):IPoint = if(pt==null) new LPoint() else new LPoint(pt.getX(), pt.getY())

	override def createDrawing() = new LDrawing()

	override def createArrow(arrow : IArrow, owner : IShape) = new LArrow(arrow, owner)

	override def createArrow(owner : IShape) = new LArrow(owner)

	override def createAxes(isUniqueID : Boolean, pt : IPoint) = new LAxes(isUniqueID, pt)

	override def createDot(pt : IPoint, isUniqueID : Boolean) = new LDot(pt, isUniqueID)

	override def createBezierCurve(isUniqueID : Boolean) = new LBezierCurve(isUniqueID)

	override def createBezierCurve(point : IPoint, point2 : IPoint, uniqueID : Boolean) = new LBezierCurve(point, point2, uniqueID)

	override def createEllipse(tl : IPoint, br : IPoint, isUniqueID : Boolean) = new LEllipse(tl, br, isUniqueID)

	override def createEllipse(isUniqueID : Boolean) = new LEllipse(isUniqueID)

	override def createTriangle(pos : IPoint, width : Double, height : Double, uniqueID : Boolean) = new LTriangle(pos, width, height, uniqueID)

	override def createTriangle(isUniqueID : Boolean) = new LTriangle(isUniqueID)

	override def createRhombus(centre : IPoint, width : Double, height : Double, uniqueID : Boolean) = new LRhombus(centre, width, height, uniqueID)

	override def createRhombus(isUniqueID : Boolean) = new LRhombus(isUniqueID)

	override def createPicture(isUniqueID : Boolean, pt : IPoint) = new LPicture(isUniqueID, pt)

	override def createGrid(isUniqueID : Boolean, pt : IPoint) = new LGrid(isUniqueID, pt)

	override def createFreeHand(pt : IPoint, uniqueID : Boolean) = new LFreehand(pt, uniqueID)

	override def createCircle(pt : IPoint, radius : Double, isUniqueID : Boolean) = new LCircle(pt, radius, isUniqueID)

	override def createCircle(isUniqueID : Boolean) = new LCircle(isUniqueID)

	override def createGroup(uniqueID : Boolean) = new LGroup(uniqueID)

	override def createLine(x1 : Double, y1 : Double, x2 : Double, y2 : Double) = new LLine(x1, y1, x2, y2)

	override def createLine(b : Double, p1 : IPoint) = new LLine(b, p1)

	override def createLine(p1 : IPoint, p2 : IPoint) = new LLine(p1, p2)

	override def createPoint() = new LPoint()

	override def createPoint(x : Double, y : Double) = new LPoint(x, y)

	override def createPoint(pt : IPoint) = new LPoint(pt)

	override def createPolyline(uniqueID : Boolean) = new LPolyline(uniqueID)

	override def createPolyline(point : IPoint, point2 : IPoint, uniqueID : Boolean) = new LPolyline(point, point2, uniqueID)

	override def createPolygon(uniqueID : Boolean) = new LPolygon(uniqueID)

	override def createPolygon(point : IPoint, point2 : IPoint, uniqueID : Boolean) = new LPolygon(point, point2, uniqueID)

	override def createRectangle(uniqueID : Boolean) = new LRectangle(uniqueID)

	override def createRectangle(pos : IPoint, width : Double, height : Double, uniqueID : Boolean) = new LRectangle(pos, width, height, uniqueID)

	override def createRectangle(tl : IPoint, br : IPoint, uniqueID : Boolean) = new LRectangle(tl, br, uniqueID)

	override def createText(isUniqueID : Boolean) = new LText(isUniqueID)

	override def createText(isUniqueID : Boolean, pt : IPoint, text : String) = new LText(isUniqueID, pt, text)

	override def createSquare(isUniqueID : Boolean) = new LSquare(isUniqueID)

	override def createSquare(pos : IPoint, width : Double, isUniqueID : Boolean) = new LSquare(pos, width, isUniqueID)

	override def createCircleArc(tl : IPoint, br : IPoint, uniqueID : Boolean) = new LCircleArc(tl, br, uniqueID)

	override def createCircleArc(isUniqueID : Boolean) = new LCircleArc(isUniqueID)

	override def createArc(tl : IPoint, br : IPoint, uniqueID : Boolean) = new LArc(tl, br, uniqueID)

	override def createArc(isUniqueID : Boolean) = new LArc(isUniqueID)

	override def duplicate(shape : IShape) = if(shape==null) null else shape.duplicate
}
