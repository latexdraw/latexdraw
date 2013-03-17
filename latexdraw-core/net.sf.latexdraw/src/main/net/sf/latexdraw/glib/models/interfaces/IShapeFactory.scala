package net.sf.latexdraw.glib.models.interfaces

import java.awt.Point
import java.awt.geom.Point2D

/**
 * Defines an interface to implement an abstract factory.<br>
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
 * @version 3.0
 * @since 3.0
 */
trait IShapeFactory {
	/**
	 * @param shapeClass The class of the shape to instantiated.
	 * @return A new instance of the class given as argument or null.
	 * @since 3.0
	 */
	def newShape[T <: IShape](shapeClass : java.lang.Class[T]) : Option[T]

	/**
	 * @return The created drawing.
	 * @since 3.0
	 */
	def createDrawing() : IDrawing

	/**
	 * Creates an arrow from an other arrow.
	 * @param arrow The arrow to copy.
	 * @param owner The shape that contains the arrow.
	 * @return The created arrow.
	 * @throws IllegalArgumentException If the given arrow is null.
	 */
	def createArrow(arrow : IArrow, owner : IShape) : IArrow

	/**
	 * Creates an arrow.
	 * @param owner The shape that contains the arrow.
	 * @return The created arrow.
	 */
	def createArrow(owner : IShape) : IArrow

	/**
	 * Creates axes with default values.
	 * @param pt The bottom left position of the axes.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @return The created axes.
	 */
	def createAxes(isUniqueID : Boolean, pt : IPoint) : IAxes

	/**
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @param pt The centre of the dot.
	 * @return The created dot.
	 */
	def createDot(pt : IPoint, isUniqueID : Boolean) : IDot

	/**
	 * Creates a model with no point.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @return The created bezier curve.
	 */
	def createBezierCurve(isUniqueID : Boolean) : IBezierCurve

	/**
	 * Creates a bezier curve with two points.
	 * @param point The first point of the curve.
	 * @param point2 The second point of the curve.
	 * @param uniqueID uniqueID True: the model will have a unique ID.
	 * @return The created bezier curve.
	 */
	def createBezierCurve(point : IPoint, point2 : IPoint, uniqueID : Boolean) : IBezierCurve

	/**
	 * Creates an ellipse.
	 * @param tl The top-left point of the ellipse.
	 * @param br The bottom-right point of the ellipse.
	 * @param isUniqueID True: the ellipse will have a unique ID.
	 * @throws IllegalArgumentException If a or b is not valid.
	 * @return The created ellipse.
	 */
	def createEllipse(tl : IPoint, br : IPoint, isUniqueID : Boolean) : IEllipse

	/**
	 * @param isUniqueID True: the ellipse will have a unique ID.
	 * @return The created ellipse.
	 */
	def createEllipse(isUniqueID : Boolean) : IEllipse

	/**
	 * Creates a triangle.
	 * @param pos The north-west point of the triangle.
	 * @param width The width of the triangle.
	 * @param height The height of the triangle.
	 * @param uniqueID True: the triangle will have a unique ID.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 * @return The created triangle.
	 */
	def createTriangle(pos : IPoint, width : Double, height : Double, uniqueID : Boolean) : ITriangle

	/**
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @return The created triangle.
	 */
	def createTriangle(isUniqueID : Boolean) : ITriangle

	/**
	 * Creates a rhombus.
	 * @param centre The centre of the rhombus.
	 * @param width The width of the rhombus.
	 * @param height The height of the rhombus.
	 * @param uniqueID True: the rhombus will have a unique ID.
	 * @throws IllegalArgumentException If the width, the height or the centre is not valid.
	 * @return The created rhombus.
	 */
	def createRhombus(centre : IPoint, width : Double, height : Double, uniqueID : Boolean) : IRhombus

	/**
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @return The created rhombus.
	 */
	def createRhombus(isUniqueID : Boolean) : IRhombus

	/**
	 * Creates a picture and the corresponding EPS picture.
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @param pt The position of the top-left point of the picture.
	 * @return The created picture.
	 * @throws IllegalArgumentException If the given picture path is not valid.
	 */
	def createPicture(isUniqueID : Boolean, pt : IPoint) : IPicture

	/**
	 * Creates a grid with a predefined point.
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @param pt The position.
	 * @return The created grid.
	 */
	def createGrid(isUniqueID : Boolean, pt : IPoint) : IGrid

	/**
	 * Creates and initialises a freehand model.
	 * @param pt The first point.
	 * @param uniqueID True: the model will have a unique ID.
	 * @throws IllegalArgumentException If the given point is not valid.
	 * @return The created freehand shape.
	 * @since 3.0
	 */
	def createFreeHand(pt : IPoint, uniqueID : Boolean) : IFreehand

	/**
	 * Creates a circle.
	 * @param pt The centre of the circle.
	 * @param radius The radius.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @throws IllegalArgumentException If the radius is not valid.
	 * @throw NullPointerException If the given point pt is null.
	 * @return The created circle.
	 */
	def createCircle(pt : IPoint, radius : Double, isUniqueID : Boolean) : ICircle

	/**
	 * @param isUniqueID True: the circle will have a unique ID.
	 * @return The created circle.
	 */
	def createCircle(isUniqueID : Boolean) : ICircle

	/**
	 * @param uniqueID True: the model will have a unique ID.
	 * @return The created group of shapes.
	 * @since 3.0
	 */
	def createGroup(uniqueID : Boolean) : IGroup

	/**
	 * Constructs a line from the specified coordinates.
	 * @param x1 the X coordinate of the start point.
	 * @param y1 the Y coordinate of the start point.
	 * @param x2 the X coordinate of the end point.
	 * @param y2 the Y coordinate of the end point.
	 * @throws IllegalArgumentException If one of the given coordinate is not valid.
	 * @return The created line.
	 */
	def createLine(x1 : Double, y1 : Double, x2 : Double, y2 : Double) : ILine

	/**
	 * Creates a line by creating a second point with:
	 * @param b y = ax+ b
	 * @param p1 The first point.
	 * @throws IllegalArgumentException If one of the given parameter is not valid.
	 * @return The created line.
	 */
	def createLine(b : Double, p1 : IPoint) : ILine

	/**
	 * Constructs a line from the specified <code>Point2D</code> objects.
	 * @param p1 the start <code>Point2D</code> of this line segment.
	 * @param p2 the end <code>Point2D</code> of this line segment.
	 * @throws IllegalArgumentException If one of the given points is not valid.
	 * @return The created line.
	 */
	def createLine(p1 : IPoint, p2 : IPoint) : ILine


	/**
	 * @return The created point with coordinates (0, 0).
	 * @since 3.0
	 */
	def createPoint() : IPoint


	/**
	 * Duplicates a java 2D point into a IPoint.
	 * If the given point pt is null, a point (0,0) is created.
	 */
	def createPoint(pt:Point2D):IPoint


	/**
	 * Creates a Point2D with the specified coordinates.
	 * @param x The X-coordinate to set.
	 * @param y The Y-coordinate to set.
	 * @return The created point.
	 * @since 3.0
	 */
	def createPoint(x : Double, y : Double) : IPoint

	/**
	 * Creates a Point2D with the specified coordinates.
	 * @param pt The IPoint, if null the default value (0,0) will be used.
	 * @return The created point.
	 * @since 3.0
	 */
	def createPoint(pt : IPoint) : IPoint

	/**
	 * @return The created polyline
	 * @param uniqueID True: the shape will have a unique ID.
	 * @since 3.0
	 */
	def createPolyline(uniqueID : Boolean) : IPolyline

	/**
	 * Creates a model with two points.
	 * @param uniqueID True: the shape will have a unique ID.
	 * @param point The first point of the shape.
	 * @param point2 The second point of the shape.
	 * @return The created polyline.
	 * @since 3.0
	 */
	def createPolyline(point : IPoint, point2 : IPoint, uniqueID : Boolean) : IPolyline

	/**
	 * @return The created polygon
	 * @param uniqueID True: the shape will have a unique ID.
	 * @since 3.0
	 */
	def createPolygon(uniqueID : Boolean) : IPolygon

	/**
	 * Creates a polygon with two points.
	 * @param uniqueID True: the shape will have a unique ID.
	 * @param point The first point of the shape.
	 * @param point2 The second point of the shape.
	 * @return The created polygon.
	 * @since 3.0
	 */
	def createPolygon(point : IPoint, point2 : IPoint, uniqueID : Boolean) : IPolygon

	/**
	 * @return The created rectangle with position (0,0) and width=10 and height=10.
	 * @param uniqueID True: the shape will have a unique ID.
	 * @since 3.0
	 */
	def createRectangle(uniqueID : Boolean) : IRectangle

	/**
	 * Creates a rectangle.
	 * @param pos The north-west point of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @param uniqueID True: the rectangle will have a unique ID.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 * @return The created rectangle.
	 * @since 3.0
	 */
	def createRectangle(pos : IPoint, width : Double, height : Double, uniqueID : Boolean) : IRectangle

	/**
	 * Creates a rectangle.
	 * @param tl The top left point of the rectangle.
	 * @param br The bottom right point of the rectangle.
	 * @param uniqueID True: the rectangle will have a unique ID.
	 * @return The created rectangle.
	 * @since 3.0
	 */
	def createRectangle(tl : IPoint, br : IPoint, uniqueID : Boolean) : IRectangle

	/**
	 * Create a text at position (0,0) which text is "text".
	 * @param uniqueID True: the shape will have a unique ID.
	 * @return The created text.
	 * @since 3.0
	 */
	def createText(isUniqueID : Boolean) : IText

	/**
	 * Creates a text.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @param pt The position of the text.
	 * @param text The text.
	 * @throws IllegalArgumentException If pt is not valid.
	 * @return The created text.
	 * @since 3.0
	 */
	def createText(isUniqueID : Boolean, pt : IPoint, text : String) : IText

	/**
	 * Creates a square at position (0,0) which width equals 10.
	 * @return The created square.
	 * @param uniqueID True: the shape will have a unique ID.
	 * @since 3.0
	 */
	def createSquare(isUniqueID : Boolean) : ISquare

	/**
	 * Creates a square.
	 * @param pos The north-west point of the square.
	 * @param width The width of the square.
	 * @param uniqueID True: the shape will have a unique ID.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 * @return The created square.
	 * @since 3.0
	 */
	def createSquare(pos : IPoint, width : Double, isUniqueID : Boolean) : ISquare


	/**
	 * Creates a circled arc.
	 * @param tl The top left point of the circled arc.
	 * @param br The bottom right point of the circled arc.
	 * @param uniqueID True: the circled arc will have a unique ID.
	 * @return The created circled arc.
	 * @since 3.0
	 */
	def createCircleArc(tl : IPoint, br : IPoint, uniqueID : Boolean) : ICircleArc


	/**
	 * Creates a circled arc with a 1 radius.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @return The created circled arc.
	 * @since 3.0
	 */
	def createCircleArc(isUniqueID : Boolean) : ICircleArc


	/**
	 * Creates an arc.
	 * @param tl The top left point of the arc.
	 * @param br The bottom right point of the arc.
	 * @param uniqueID True: the arc will have a unique ID.
	 * @return The created arc.
	 * @since 3.0
	 */
	def createArc(tl : IPoint, br : IPoint, uniqueID : Boolean) : IArc


	/**
	 * Creates a circled arc with a 1 radius.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @return The created arc.
	 * @since 3.0
	 */
	def createArc(isUniqueID : Boolean) : IArc


	/**
	 * Duplicates the given shape.
	 * @param shape The shape to duplicate
	 * @return The duplicated shape or null.
	 * @since 3.0
	 */
	def duplicate(shape : IShape) : IShape


	/**
	 * Converts a Java point into a IPoint.
	 * @since 3.0
	 */
	implicit def Point2IPoint(pt : Point) = if(pt==null) null else createPoint(pt.getX, pt.getY)
}