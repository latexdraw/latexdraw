package net.sf.latexdraw.models.impl

import java.awt.geom.Point2D
import java.util.Optional

import org.eclipse.jdt.annotation.NonNullByDefault

import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.models.ShapeFactory
import net.sf.latexdraw.models.interfaces.shape.Color
import net.sf.latexdraw.models.interfaces.shape.IArrow
import net.sf.latexdraw.models.interfaces.shape.IArrowableShape
import net.sf.latexdraw.models.interfaces.shape.IAxes
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve
import net.sf.latexdraw.models.interfaces.shape.ICircle
import net.sf.latexdraw.models.interfaces.shape.ICircleArc
import net.sf.latexdraw.models.interfaces.shape.IDot
import net.sf.latexdraw.models.interfaces.shape.IDrawing
import net.sf.latexdraw.models.interfaces.shape.IEllipse
import net.sf.latexdraw.models.interfaces.shape.IFreehand
import net.sf.latexdraw.models.interfaces.shape.IGrid
import net.sf.latexdraw.models.interfaces.shape.IGroup
import net.sf.latexdraw.models.interfaces.shape.ILine
import net.sf.latexdraw.models.interfaces.shape.IPicture
import net.sf.latexdraw.models.interfaces.shape.IPlot
import net.sf.latexdraw.models.interfaces.shape.IPoint
import net.sf.latexdraw.models.interfaces.shape.IPolygon
import net.sf.latexdraw.models.interfaces.shape.IPolyline
import net.sf.latexdraw.models.interfaces.shape.IRectangle
import net.sf.latexdraw.models.interfaces.shape.IRhombus
import net.sf.latexdraw.models.interfaces.shape.IShape
import net.sf.latexdraw.models.interfaces.shape.IShapeFactory
import net.sf.latexdraw.models.interfaces.shape.ISquare
import net.sf.latexdraw.models.interfaces.shape.IText
import net.sf.latexdraw.models.interfaces.shape.ITriangle

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
@NonNullByDefault
class LShapeFactory extends IShapeFactory {
	/** The map that maps types to creation operations. */
	val factoryMap: Map[Class[_], () => IShape] = Map(
			  (classOf[IPlot], () => createPlot(createPoint, 1.0, 10.0, "x", false)),
			  (classOf[ICircleArc], () => createCircleArc()),
			  (classOf[LCircleArc], () => createCircleArc()),
			  (classOf[ICircle], () => createCircle()),
			  (classOf[LCircle], () => createCircle()),
			  (classOf[IEllipse], () => createEllipse()),
			  (classOf[LEllipse], () => createEllipse()),
			  (classOf[ISquare], () => createSquare()),
			  (classOf[LSquare], () => createSquare()),
			  (classOf[IRectangle], () => createRectangle()),
			  (classOf[LRectangle], () => createRectangle()),
			  (classOf[ITriangle], () => createTriangle()),
			  (classOf[LTriangle], () => createTriangle()),
			  (classOf[IRhombus], () => createRhombus()),
			  (classOf[LRhombus], () => createRhombus()),
			  (classOf[IPolyline], () => createPolyline()),
			  (classOf[LPolyline], () => createPolyline()),
			  (classOf[IPolygon], () => createPolygon()),
			  (classOf[LPolygon], () => createPolygon()),
			  (classOf[IAxes], () => createAxes(createPoint)),
			  (classOf[LAxes], () => createAxes(createPoint)),
			  (classOf[IGrid], () => createGrid(createPoint)),
			  (classOf[LGrid], () => createGrid(createPoint)),
			  (classOf[IBezierCurve], () => createBezierCurve()),
			  (classOf[LBezierCurve], () => createBezierCurve()),
			  (classOf[IDot], () => createDot(createPoint)),
			  (classOf[LDot], () => createDot(createPoint)),
			  (classOf[IFreehand], () => createFreeHand()),
			  (classOf[LFreehand], () => createFreeHand()),
			  (classOf[IGroup], () => createGroup()),
			  (classOf[LGroup], () => createGroup()),
			  (classOf[IPicture], () => createPicture(createPoint)),
			  (classOf[LPicture], () => createPicture(createPoint)),
			  (classOf[IText], () => createText()),
			  (classOf[LText], () => createText()),
			  (classOf[IPlot], () => createPlot(createPoint, 1, 10, "x", false)),
			  (classOf[LPlot], () => createPlot(createPoint, 1, 10, "x", false)))


	override def newShape[T <: IShape](shapeClass : java.lang.Class[T]) : Optional[T] =
		shapeClass match {
			case null => Optional.empty()
			case _ =>
				try { Optional.of(shapeClass.cast(factoryMap(shapeClass)())) }
				catch { case ex: Throwable => BadaboomCollector.INSTANCE.add(ex); Optional.empty() }
		}

	override def createGroup(sh:IShape):IGroup = {
		val gp = createGroup()
		if(sh!=null) gp.addShape(sh)
		return gp
	}
	
	override def createColorFX(col:javafx.scene.paint.Color):Color = {
	  require(col!=null)
	  return createColor(col.getRed(), col.getGreen(), col.getBlue(), col.getOpacity()) 
	}
	
	override def createColorAWT(col:java.awt.Color):Color = {
	  require(col!=null)
	  return createColorInt(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha()) 
	}
	
	override def createColorInt(r:Int, g:Int, b:Int, a:Int) : Color = createColor(r/255.0, g/255.0, b/255.0, a/255.0)
	
	override def createColorInt(r:Int, g:Int, b:Int) : Color = createColorInt(r, g, b, 255)
	
	override def createColorHSB(h:Double, s:Double, b:Double):Color = {
	  val col = javafx.scene.paint.Color.hsb(h, s, b)
	  return createColor(col.getRed, col.getGreen, col.getBlue, col.getOpacity)
	}
	
	override def createColor(r:Double, g:Double, b:Double, o:Double) : Color = new ColorImpl(r, g, b, o)
	
	override def createColor(r:Double, g:Double, b:Double) : Color = createColor(r,g,b,1.0)
	
	override def createColor(): Color = createColor(1.0,1.0,1.0,1.0)

	override def createPlot(pos:IPoint, minX:Double, maxX:Double, eq:String, polar:Boolean) : IPlot = new LPlot(pos, minX, maxX, eq, polar)

	override def createPoint(pt:Point2D):IPoint = if(pt==null) createPoint else createPoint(pt.getX, pt.getY)

	override def createDrawing() : IDrawing = new LDrawing()

	override def createArrow(arrow : IArrow, owner : IArrowableShape) : IArrow = new LArrow(arrow, owner)

	override def createArrow(owner : IArrowableShape) : IArrow = new LArrow(owner)

	override def createAxes(pt : IPoint) :IAxes = new LAxes(pt)

	override def createDot(pt : IPoint) : IDot = new LDot(pt)

	override def createBezierCurve() : IBezierCurve = new LBezierCurve(true)

	override def createBezierCurve(point : IPoint, point2 : IPoint) : IBezierCurve = new LBezierCurve(point, point2, true)

	override def createEllipse(tl : IPoint, br : IPoint) : IEllipse = new LEllipse(tl, br)

	override def createEllipse() : IEllipse = new LEllipse()

	override def createTriangle(pos : IPoint, width : Double, height : Double) : ITriangle = new LTriangle(pos, width, height)

	override def createTriangle() : ITriangle = new LTriangle()

	override def createRhombus(centre : IPoint, width : Double, height : Double) : IRhombus = new LRhombus(centre, width, height)

	override def createRhombus() : IRhombus = new LRhombus()

	override def createPicture(pt : IPoint) : IPicture = new LPicture(pt)

	override def createGrid(pt : IPoint) : IGrid = new LGrid(pt)

	override def createFreeHand() : IFreehand = new LFreehand()

	override def createCircle(pt : IPoint, radius : Double) : ICircle = new LCircle(pt, radius)

	override def createCircle() : ICircle = createCircle(ShapeFactory.createPoint, 10)

	override def createGroup() : IGroup = new LGroup()

	override def createLine(x1 : Double, y1 : Double, x2 : Double, y2 : Double) : ILine = new LLine(x1, y1, x2, y2)

	override def createLine(b : Double, p1 : IPoint) : ILine = new LLine(b, p1)

	override def createLine(p1 : IPoint, p2 : IPoint) : ILine = new LLine(p1, p2)

	override def createPoint() : IPoint = new LPoint()

	override def createPoint(x : Double, y : Double) : IPoint = new LPoint(x, y)

	override def createPoint(pt : IPoint) : IPoint = new LPoint(pt)

	override def createPolyline() : IPolyline = new LPolyline()

	override def createPolyline(point : IPoint, point2 : IPoint) : IPolyline = new LPolyline(point, point2)

	override def createPolygon() : IPolygon = new LPolygon()

	override def createPolygon(point : IPoint, point2 : IPoint) : IPolygon = new LPolygon(point, point2)

	override def createRectangle() : IRectangle = createRectangle(ShapeFactory.createPoint, ShapeFactory.createPoint(1, 1))

	override def createRectangle(pos : IPoint, width : Double, height : Double) : IRectangle = createRectangle(pos, ShapeFactory.createPoint(pos.getX+width, pos.getY+height))

	override def createRectangle(tl : IPoint, br : IPoint) : IRectangle = new LRectangle(tl, br)

	override def createText() : IText = new LText()

	override def createText(pt : IPoint, text : String) : IText = new LText(pt, text)

	override def createSquare() : ISquare = createSquare(ShapeFactory.createPoint, 1)

	override def createSquare(pos : IPoint, width : Double) : ISquare = new LSquare(pos, width)

	override def createCircleArc(pos : IPoint, width : Double) : ICircleArc = new LCircleArc(pos, width)

	override def createCircleArc() : ICircleArc = createCircleArc(ShapeFactory.createPoint, 1)

	override def duplicate(shape : IShape) = if(shape==null) null else shape.duplicate
}
