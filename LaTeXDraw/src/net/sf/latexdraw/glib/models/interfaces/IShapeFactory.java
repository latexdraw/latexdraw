package net.sf.latexdraw.glib.models.interfaces;

import net.sf.latexdraw.glib.models.interfaces.IArc.ArcType;

/**
 * Defines an interface to implement an abstract factory.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 01/04/2011<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IShapeFactory {
	/**
	 * @param shapeClass The class of the shape to instantiated.
	 * @return A new instance of the class given as argument or null.
	 * @since 3.0
	 */
	<T extends IShape> T newShape(final Class<T> shapeClass);

	/**
	 * @return The created drawing.
	 * @since 3.0
	 */
	IDrawing createDrawing();

	/**
	 * Creates an arrow from an other arrow.
	 * @param arrow The arrow to copy.
	 * @param owner The shape that contains the arrow.
	 * @throws IllegalArgumentException If the given arrow is null.
	 */
	IArrow createArrow(final IArrow arrow, final IShape owner);

	/**
	 * Creates an arrow.
	 * @param owner The shape that contains the arrow.
	 */
	IArrow createArrow(final IShape owner);

	/**
	 * Creates axes with default values.
	 * @param pt The bottom left position of the axes.
	 * @param isUniqueID True: the model will have a unique ID.
	 */
	IAxes createAxes(final boolean isUniqueID, final IPoint pt);

	/**
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @param pt The centre of the dot.
	 */
	IDot createDot(final IPoint pt, final boolean isUniqueID);

	/**
	 * Creates a model with no point.
	 * @param uniqueID True: the model will have a unique ID.
	 */
	IBezierCurve createBezierCurve(final boolean isUniqueID);

	/**
	 * Creates a bezier curve with two points.
	 * @param point The first point of the curve.
	 * @param point2 The second point of the curve.
	 * @param uniqueID uniqueID True: the model will have a unique ID.
	 */
	IBezierCurve createBezierCurve(final IPoint point, final IPoint point2, final boolean uniqueID);

	/**
	 * Creates an ellipse.
	 * @param tl The top-left point of the ellipse.
	 * @param br The bottom-right point of the ellipse.
	 * @param isUniqueID True: the ellipse will have a unique ID.
	 * @throws IllegalArgumentException If a or b is not valid.
	 * @return The created ellipse.
	 */
	IEllipse createEllipse(final IPoint tl, final IPoint br, final boolean isUniqueID);

	/**
	 * @param isUniqueID True: the ellipse will have a unique ID.
	 * @return The created ellipse.
	 */
	IEllipse createEllipse(final boolean isUniqueID);

	/**
	 * Creates a triangle.
	 * @param pos The north-west point of the triangle.
	 * @param width The width of the triangle.
	 * @param height The height of the triangle.
	 * @param uniqueID True: the triangle will have a unique ID.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 * @return The created triangle.
	 */
	ITriangle createTriangle(final IPoint pos, final double width, final double height, final boolean uniqueID);

	/**
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @return The created triangle.
	 */
	ITriangle createTriangle(final boolean isUniqueID);

	/**
	 * Creates a rhombus.
	 * @param centre The centre of the rhombus.
	 * @param width The width of the rhombus.
	 * @param height The height of the rhombus.
	 * @param uniqueID True: the rhombus will have a unique ID.
	 * @throws IllegalArgumentException If the width, the height or the centre is not valid.
	 * @return The created rhombus.
	 */
	IRhombus createRhombus(final IPoint centre, final double width, final double height, final boolean uniqueID);

	/**
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @return The created rhombus.
	 */
	IRhombus createRhombus(final boolean isUniqueID);

	/**
	 * Creates a picture and the corresponding EPS picture.
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @param pt The position of the top-left point of the picture.
	 * @param pathSource The path of the image to load.
	 * @throws IllegalArgumentException If the given picture path is not valid.
	 */
	IPicture createPicture(final boolean isUniqueID, final IPoint pt, final String pathSource);

	/**
	 * Creates a grid with a predefined point.
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @param pt The position.
	 */
	IGrid createGrid(final boolean isUniqueID, final IPoint pt);

	/**
	 * Creates and initialises a freehand model.
	 * @param pt The first point.
	 * @param uniqueID True: the model will have a unique ID.
	 * @throws IllegalArgumentException If the given point is not valid.
	 * @since 3.0
	 */
	IFreehand createFreeHand(final IPoint pt, final boolean uniqueID);

	/**
	 * Creates a circle.
	 * @param pt The centre of the circle.
	 * @param radius The radius.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @throws IllegalArgumentException If the radius is not valid.
	 * @throw NullPointerException If the given point pt is null.
	 * @return The created circle.
	 */
	ICircle createCircle(final IPoint pt, final double radius, final boolean isUniqueID);

	/**
	 * @param isUniqueID True: the circle will have a unique ID.
	 * @return The created circle.
	 */
	ICircle createCircle(final boolean isUniqueID);

	/**
	 * @param uniqueID True: the model will have a unique ID.
	 * @return The created group of shapes.
	 * @since 3.0
	 */
	IGroup createGroup(final boolean uniqueID);

	/**
	 * Constructs a line from the specified coordinates.
	 * @param x1 the X coordinate of the start point.
	 * @param y1 the Y coordinate of the start point.
	 * @param x2 the X coordinate of the end point.
	 * @param y2 the Y coordinate of the end point.
	 * @throws IllegalArgumentException If one of the given coordinate is not valid.
	 * @return The created line.
	 */
	ILine createLine(final double x1, final double y1, final double x2, final double y2);

	/**
	 * Creates a line by creating a second point with:
	 * @param b y = ax+ b
	 * @param p1 The first point.
	 * @throws IllegalArgumentException If one of the given parameter is not valid.
	 * @return The created line.
	 */
	ILine createLine(final double b, final IPoint p1);

	/**
	 * Constructs a line from the specified <code>Point2D</code> objects.
	 * @param p1 the start <code>Point2D</code> of this line segment.
	 * @param p2 the end <code>Point2D</code> of this line segment.
	 * @throws IllegalArgumentException If one of the given points is not valid.
	 * @return The created line.
	 */
	ILine createLine(final IPoint p1, final IPoint p2);


	/**
	 * @return The created point with coordinates (0, 0).
	 * @since 3.0
	 */
	IPoint createPoint();

	/**
	 * Creates a Point2D with the specified coordinates.
	 * @param x The X-coordinate to set.
	 * @param y The Y-coordinate to set.
	 * @since 3.0
	 */
	IPoint createPoint(final double x, final double y);

	/**
	 * Creates a Point2D with the specified coordinates.
	 * @param pt The IPoint, if null the default value (0,0) will be used.
	 * @since 3.0
	 */
	IPoint createPoint(final IPoint pt);

	/**
	 * @return The created polyline
	 * @param uniqueID True: the shape will have a unique ID.
	 * @since 3.0
	 */
	IPolyline createPolyline(final boolean uniqueID);

	/**
	 * Creates a model with two points.
	 * @param uniqueID True: the shape will have a unique ID.
	 * @param point The first point of the shape.
	 * @param point2 The second point of the shape.
	 * @return The created polyline.
	 * @since 3.0
	 */
	IPolyline createPolyline(final IPoint point, final IPoint point2, final boolean uniqueID);

	/**
	 * @return The created polygon
	 * @param uniqueID True: the shape will have a unique ID.
	 * @since 3.0
	 */
	IPolygon createPolygon(final boolean uniqueID);

	/**
	 * Creates a polygon with two points.
	 * @param uniqueID True: the shape will have a unique ID.
	 * @param point The first point of the shape.
	 * @param point2 The second point of the shape.
	 * @return The created polygon.
	 * @since 3.0
	 */
	IPolygon createPolygon(final IPoint point, final IPoint point2, final boolean uniqueID);

	/**
	 * @return The created rectangle with position (0,0) and width=10 and height=10.
	 * @param uniqueID True: the shape will have a unique ID.
	 * @since 3.0
	 */
	IRectangle createRectangle(final boolean uniqueID);

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
	IRectangle createRectangle(final IPoint pos, final double width, final double height, final boolean uniqueID);

	/**
	 * Creates a rectangle.
	 * @param tl The top left point of the rectangle.
	 * @param br The bottom right point of the rectangle.
	 * @param uniqueID True: the rectangle will have a unique ID.
	 * @return The created rectangle.
	 * @since 3.0
	 */
	IRectangle createRectangle(final IPoint tl, final IPoint br, final boolean uniqueID);

	/**
	 * Create a text at position (0,0) which text is "text".
	 * @param uniqueID True: the shape will have a unique ID.
	 * @return The created text.
	 * @since 3.0
	 */
	IText createText(final boolean uniqueID);

	/**
	 * Creates a text.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @param pt The position of the text.
	 * @param text The text.
	 * @throws IllegalArgumentException If pt is not valid.
	 * @return The created text.
	 * @since 3.0
	 */
	IText createText(final boolean isUniqueID, final IPoint pt, final String text);

	/**
	 * Creates a square at position (0,0) which width equals 10.
	 * @return The created square.
	 * @param uniqueID True: the shape will have a unique ID.
	 * @since 3.0
	 */
	ISquare createSquare(final boolean uniqueID);

	/**
	 * Creates a square.
	 * @param pos The north-west point of the square.
	 * @param width The width of the square.
	 * @param uniqueID True: the shape will have a unique ID.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 * @return The created square.
	 * @since 3.0
	 */
	ISquare createSquare(final IPoint pos, final double width, final boolean uniqueID);


	/**
	 * Creates a round arc.
	 * @param pt The centre of the arc.
	 * @param radius The radius.
	 * @param type The kind of arc.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @throws IllegalArgumentException If the radius is not valid.
	 */
	ICircleArc createCircleArc(final IPoint pt, final double radius, final ArcType type, final boolean isUniqueID);


	/**
	 * Creates a circle arc with a 1 radius.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @since 3.0
	 */
	ICircleArc createCircleArc(final boolean isUniqueID);


	/**
	 * Duplicates the given shape.
	 * @param shape The shape to duplicate
	 * @return The duplicated shape or null.
	 * @since 3.0
	 */
	IShape duplicate(IShape shape);
}
