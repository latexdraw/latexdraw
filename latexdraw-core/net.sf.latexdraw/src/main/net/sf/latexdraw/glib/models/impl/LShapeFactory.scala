package net.sf.latexdraw.glib.models.impl

import java.awt.geom.Point2D
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow
import net.sf.latexdraw.glib.models.interfaces.shape.IArrowableShape
import net.sf.latexdraw.glib.models.interfaces.shape.IAxes
import net.sf.latexdraw.glib.models.interfaces.shape.IBezierCurve
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle
import net.sf.latexdraw.glib.models.interfaces.shape.ICircleArc
import net.sf.latexdraw.glib.models.interfaces.shape.IDot
import net.sf.latexdraw.glib.models.interfaces.shape.IDrawing
import net.sf.latexdraw.glib.models.interfaces.shape.IEllipse
import net.sf.latexdraw.glib.models.interfaces.shape.IFreehand
import net.sf.latexdraw.glib.models.interfaces.shape.IGrid
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.glib.models.interfaces.shape.ILine
import net.sf.latexdraw.glib.models.interfaces.shape.IPicture
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IPolygon
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle
import net.sf.latexdraw.glib.models.interfaces.shape.IRhombus
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.interfaces.shape.IShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.ISquare
import net.sf.latexdraw.glib.models.interfaces.shape.IText
import net.sf.latexdraw.glib.models.interfaces.shape.ITriangle
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot

/**
 * This factory creates shapes.<br>
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
 * 2012-04-19<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LShapeFactory extends IShapeFactory {
	/** The map that maps types to creation operations. */
	val factoryMap: Map[Class[_], () => IShape] = Map(
			  (classOf[IPlot], () => createPlot(true, createPoint, 1.0, 10.0, "x")),
			  (classOf[ICircleArc], () => createCircleArc(true)),
			  (classOf[LCircleArc], () => createCircleArc(true)),
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
			  (classOf[IFreehand], () => createFreeHand(true)),
			  (classOf[LFreehand], () => createFreeHand(true)),
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
				catch { case ex: Throwable => BadaboomCollector.INSTANCE.add(ex); None }
		}

	override def createPlot(isUniqueID : Boolean, pos : IPoint, minX:Double, maxX:Double, eq:String) : IPlot = new LPlot(isUniqueID, pos, minX, maxX, eq)

	override def createPoint(pt:Point2D):IPoint = if(pt==null) createPoint else createPoint(pt.getX, pt.getY)

	override def createDrawing() : IDrawing = new LDrawing()

	override def createArrow(arrow : IArrow, owner : IArrowableShape) : IArrow = new LArrow(arrow, owner)

	override def createArrow(owner : IArrowableShape) : IArrow = new LArrow(owner)

	override def createAxes(isUniqueID : Boolean, pt : IPoint) :IAxes = new LAxes(isUniqueID, pt)

	override def createDot(pt : IPoint, isUniqueID : Boolean) : IDot = new LDot(pt, isUniqueID)

	override def createBezierCurve(isUniqueID : Boolean) : IBezierCurve = new LBezierCurve(true, isUniqueID)

	override def createBezierCurve(point : IPoint, point2 : IPoint, uniqueID : Boolean) : IBezierCurve = new LBezierCurve(point, point2, true, uniqueID)

	override def createEllipse(tl : IPoint, br : IPoint, isUniqueID : Boolean) : IEllipse = new LEllipse(tl, br, isUniqueID)

	override def createEllipse(isUniqueID : Boolean) : IEllipse = new LEllipse(isUniqueID)

	override def createTriangle(pos : IPoint, width : Double, height : Double, uniqueID : Boolean) : ITriangle = new LTriangle(pos, width, height, uniqueID)

	override def createTriangle(isUniqueID : Boolean) : ITriangle = new LTriangle(isUniqueID)

	override def createRhombus(centre : IPoint, width : Double, height : Double, uniqueID : Boolean) : IRhombus = new LRhombus(centre, width, height, uniqueID)

	override def createRhombus(isUniqueID : Boolean) : IRhombus = new LRhombus(isUniqueID)

	override def createPicture(isUniqueID : Boolean, pt : IPoint) : IPicture = new LPicture(isUniqueID, pt)

	override def createGrid(isUniqueID : Boolean, pt : IPoint) : IGrid = new LGrid(isUniqueID, pt)

	override def createFreeHand(uniqueID : Boolean) : IFreehand = new LFreehand(uniqueID)

	override def createCircle(pt : IPoint, radius : Double, isUniqueID : Boolean) : ICircle = new LCircle(pt, radius, isUniqueID)

	override def createCircle(isUniqueID : Boolean) : ICircle = createCircle(ShapeFactory.createPoint, 10, isUniqueID)

	override def createGroup(uniqueID : Boolean) : IGroup = new LGroup(uniqueID)

	override def createLine(x1 : Double, y1 : Double, x2 : Double, y2 : Double) : ILine = new LLine(x1, y1, x2, y2)

	override def createLine(b : Double, p1 : IPoint) : ILine = new LLine(b, p1)

	override def createLine(p1 : IPoint, p2 : IPoint) : ILine = new LLine(p1, p2)

	override def createPoint() : IPoint = new LPoint()

	override def createPoint(x : Double, y : Double) : IPoint = new LPoint(x, y)

	override def createPoint(pt : IPoint) : IPoint = new LPoint(pt)

	override def createPolyline(uniqueID : Boolean) : IPolyline = new LPolyline(uniqueID)

	override def createPolyline(point : IPoint, point2 : IPoint, uniqueID : Boolean) : IPolyline = new LPolyline(point, point2, uniqueID)

	override def createPolygon(uniqueID : Boolean) : IPolygon = new LPolygon(uniqueID)

	override def createPolygon(point : IPoint, point2 : IPoint, uniqueID : Boolean) : IPolygon = new LPolygon(point, point2, uniqueID)

	override def createRectangle(uniqueID : Boolean) : IRectangle = createRectangle(ShapeFactory.createPoint, ShapeFactory.createPoint(1, 1), uniqueID)

	override def createRectangle(pos : IPoint, width : Double, height : Double, uniqueID : Boolean) : IRectangle = createRectangle(pos, ShapeFactory.createPoint(pos.getX+width, pos.getY+height), uniqueID)

	override def createRectangle(tl : IPoint, br : IPoint, uniqueID : Boolean) : IRectangle = new LRectangle(tl, br, uniqueID)

	override def createText(isUniqueID : Boolean) : IText = new LText(isUniqueID)

	override def createText(isUniqueID : Boolean, pt : IPoint, text : String) : IText = new LText(isUniqueID, pt, text)

	override def createSquare(isUniqueID : Boolean) : ISquare = createSquare(ShapeFactory.createPoint, 1, isUniqueID)

	override def createSquare(pos : IPoint, width : Double, isUniqueID : Boolean) : ISquare = new LSquare(pos, width, isUniqueID)

	override def createCircleArc(pos : IPoint, width : Double, isUniqueID : Boolean) : ICircleArc = new LCircleArc(pos, width, isUniqueID)

	override def createCircleArc(isUniqueID : Boolean) : ICircleArc = createCircleArc(ShapeFactory.createPoint, 1, isUniqueID)

	override def duplicate(shape : IShape) = if(shape==null) null else shape.duplicate
}
