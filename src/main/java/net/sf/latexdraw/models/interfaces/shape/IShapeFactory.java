/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.interfaces.shape;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Point3D;

/**
 * The API for shape factories.
 * @author Arnaud BLOUIN
 */
public interface IShapeFactory {
	/**
	 * @param shapeClass The class of the shape to instantiated.
	 * @return A new instance of the class given as argument or null.
	 * @since 3.0
	 */
	<T extends IShape> Optional<T> newShape(java.lang.Class<T> shapeClass);

	/**
	 * Creates a color from an JavaFX color.
	 * @param col The colour to convert.
	 * @return The converted colour. Cannot be null.
	 */
	Color createColorFX(final javafx.scene.paint.Color col);

	/**
	 * Creates a color from an AWT color.
	 * @param col The colour to convert.
	 * @return The converted colour. Cannot be null.
	 */
	Color createColorAWT(final java.awt.Color col);

	/**
	 * Creates a color following the HSB format.
	 * @param h The H
	 * @param s The S
	 * @param b The B
	 * @return The converted colour. Cannot be null.
	 */
	Color createColorHSB(double h, double s, double b);

	/**
	 * Creates a colour following the RGBA format.
	 * @param r The R.
	 * @param g The G.
	 * @param b The B.
	 * @param a The A.
	 * @return The converted colour. Cannot be null.
	 */
	Color createColorInt(int r, int g, int b, int a);

	/**
	 * Creates a colour following the RGB format.
	 * @param r The R.
	 * @param g The G.
	 * @param b The B.
	 * @return The converted colour. Cannot be null.
	 */
	Color createColorInt(int r, int g, int b);

	/**
	 * Creates a colour.
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 * @param o Opacity
	 * @return The converted colour. Cannot be null.
	 */
	Color createColor(double r, double g, double b, double o);

	/**
	 * Creates a colour.
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 * @return The converted colour. Cannot be null.
	 */
	Color createColor(double r, double g, double b);

	/**
	 * Creates a colour (1,1,1,1)
	 * @return The converted colour. Cannot be null.
	 */
	Color createColor();

	/**
	 * @return The created drawing.
	 * @since 3.0
	 */
	IDrawing createDrawing();

	/**
	 * Creates a group that will contains initially the given sh.
	 * @param sh The shape to add to the group to create.
	 * @return Created groupe. Cannot be null.
	 * @since 3.3
	 */
	IGroup createGroup(IShape sh);

	/**
	 * Creates an arrow from an other arrow.
	 * @param arrow The arrow to copy.
	 * @param owner The shape that contains the arrow.
	 * @return The created arrow.
	 * @throws IllegalArgumentException If the given arrow is null.
	 */
	IArrow createArrow(IArrow arrow, IArrowableSingleShape owner);

	/**
	 * Creates an arrow.
	 * @param owner The shape that contains the arrow.
	 * @return The created arrow.
	 */
	IArrow createArrow(IArrowableSingleShape owner);

	/**
	 * Creates axes with default values.
	 * @param pt The bottom left position of the axes.
	 * @return The created axes.
	 */
	IAxes createAxes(IPoint pt);

	/**
	 * @param pt The centre of the dot.
	 * @return The created dot.
	 */
	IDot createDot(IPoint pt);

	/**
	 * Creates a bezier curve with a set of points.
	 * @param pts The points of the curve.
	 * @return The created bezier curve.
	 */
	IBezierCurve createBezierCurve(final List<IPoint> pts);

	IBezierCurve createBezierCurve(final List<IPoint> pts, final List<IPoint> ctrlpts);

	IBezierCurve createBezierCurveFrom(final IBezierCurve bc, final IPoint pointToAdd);

	/**
	 * Creates an ellipse.
	 * @param tl The top-left point of the ellipse.
	 * @param br The bottom-right point of the ellipse.
	 * @return The created ellipse.
	 * @throws IllegalArgumentException If a or b is not valid.
	 */
	IEllipse createEllipse(IPoint tl, IPoint br);

	/**
	 * @return The created ellipse.
	 */
	IEllipse createEllipse();

	/**
	 * Creates a triangle.
	 * @param pos The north-west point of the triangle.
	 * @param width The width of the triangle.
	 * @param height The height of the triangle.
	 * @return The created triangle.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 */
	ITriangle createTriangle(IPoint pos, double width, double height);

	/**
	 * @return The created triangle.
	 */
	ITriangle createTriangle();

	/**
	 * Creates a rhombus.
	 * @param centre The centre of the rhombus.
	 * @param width The width of the rhombus.
	 * @param height The height of the rhombus.
	 * @return The created rhombus.
	 * @throws IllegalArgumentException If the width, the height or the centre is not valid.
	 */
	IRhombus createRhombus(IPoint centre, double width, double height);

	/**
	 * Creates a rhombus at the position (0,0) with width=height=1.
	 * @return The created rhombus.
	 */
	IRhombus createRhombus();

	/**
	 * Creates a picture and the corresponding EPS picture.
	 * @param pt The position of the top-left point of the picture.
	 * @return The created picture.
	 * @throws IllegalArgumentException If the given picture path is not valid.
	 * @throws NullPointerException If the given point is null.
	 */
	IPicture createPicture(IPoint pt);

	/**
	 * Creates a grid with a predefined point.
	 * @param pt The position.
	 * @return The created grid.
	 */
	IGrid createGrid(IPoint pt);

	/**
	 * Creates and initialises a freehand model.
	 * @return The created freehand shape.
	 * @throws IllegalArgumentException If the given point is not valid.
	 * @since 3.0
	 */
	IFreehand createFreeHand(final List<IPoint> pts);

	IFreehand createFreeHandFrom(IFreehand sh, IPoint pointToAdd);

	/**
	 * Creates a circle.
	 * @param pt The position of the top-left point of the picture.
	 * @param radius The radius.
	 * @return The created circle.
	 * @throws IllegalArgumentException If the radius is not valid.
	 * @throws NullPointerException If the given point pt is null.
	 */
	ICircle createCircle(IPoint pt, double radius);

	/**
	 * @return The created circle.
	 */
	ICircle createCircle();

	/**
	 * @return The created group of shapes.
	 * @since 3.0
	 */
	IGroup createGroup();

	/**
	 * Constructs a line from the specified coordinates.
	 * @param x1 the X coordinate of the start point.
	 * @param y1 the Y coordinate of the start point.
	 * @param x2 the X coordinate of the end point.
	 * @param y2 the Y coordinate of the end point.
	 * @return The created line.
	 * @throws IllegalArgumentException If one of the given coordinate is not valid.
	 */
	ILine createLine(double x1, double y1, double x2, double y2);

	/**
	 * Creates a line by creating a second point with
	 * @param b y = ax+ b
	 * @param p1 The first point.
	 * @return The created line.
	 * @throws IllegalArgumentException If one of the given parameter is not valid.
	 */
	ILine createLine(double b, IPoint p1);

	/**
	 * Constructs a line from the specified <code>Point2D</code> objects.
	 * @param p1 the start <code>Point2D</code> of this line segment.
	 * @param p2 the end <code>Point2D</code> of this line segment.
	 * @return The created line.
	 * @throws IllegalArgumentException If one of the given points is not valid.
	 */
	ILine createLine(IPoint p1, IPoint p2);

	/**
	 * @return The created point with coordinates (0, 0).
	 * @since 3.0
	 */
	IPoint createPoint();

	/**
	 * Duplicates a java 2D point into a IPoint. If the given point pt is null, a point (0,0) is
	 * created.
	 * @param pt The point to convert.
	 * @return The created point. Cannot be null.
	 */
	IPoint createPoint(Point2D pt);

	/**
	 * Duplicates a JFX point into a IPoint. If the given point pt is null, a point (0,0) is
	 * created.
	 * @param pt The point to convert.
	 * @return The created point. Cannot be null.
	 */
	IPoint createPoint(javafx.geometry.Point2D pt);

	/**
	 * Duplicates a java 3D point into a IPoint. If the given point pt is null, a point (0,0) is
	 * created.
	 * @param pt The point to convert.
	 * @return The created point. Cannot be null.
	 */
	IPoint createPoint(Point3D pt);

	/**
	 * Creates a Point2D with the specified coordinates.
	 * @param x The X-coordinate to set.
	 * @param y The Y-coordinate to set.
	 * @return The created point.
	 * @since 3.0
	 */
	IPoint createPoint(double x, double y);

	/**
	 * Creates a Point2D with the specified coordinates.
	 * @param pt The IPoint, if null the default value (0,0) will be used.
	 * @return The created point.
	 * @since 3.0
	 */
	IPoint createPoint(IPoint pt);

	/**
	 * Creates a model with a set of points.
	 * @param pts The points of the shape.
	 * @return The created polyline.
	 * @since 3.0
	 */
	IPolyline createPolyline(final List<IPoint> pts);

	IPolyline createPolylineFrom(IPolyline sh, IPoint pointToAdd);

	/**
	 * Creates a polygon with a set of points.
	 * @param pts The points of the shape.
	 * @return The created polygon.
	 * @since 3.0
	 */
	IPolygon createPolygon(final List<IPoint> pts);

	IPolygon createPolygonFrom(IPolygon sh, IPoint pointToAdd);

	/**
	 * @return The created rectangle with position (0,0) and width=10 and height=10.
	 * @since 3.0
	 */
	IRectangle createRectangle();

	/**
	 * Creates a rectangle.
	 * @param pos The north-west point of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @return The created rectangle.
	 * @throws IllegalArgumentException If the width, the height or the point is not valid.
	 * @throws NullPointerException if the given point is null.
	 * @since 3.0
	 */
	IRectangle createRectangle(IPoint pos, double width, double height);

	/**
	 * Creates a rectangle.
	 * @param tl The top left point of the rectangle.
	 * @param br The bottom right point of the rectangle.
	 * @return The created rectangle.
	 * @throws IllegalArgumentException if one of the given points is not valid.
	 * @since 3.0
	 */
	IRectangle createRectangle(IPoint tl, IPoint br);

	/**
	 * Create a text at position (0,0) which text is "text".
	 * @return The created text.
	 * @since 3.0
	 */
	IText createText();

	/**
	 * Creates a text.
	 * @param pt The position of the text.
	 * @param text The text.
	 * @return The created text.
	 * @throws IllegalArgumentException If pt is not valid.
	 * @since 3.0
	 */
	IText createText(IPoint pt, String text);

	/**
	 * Creates a square at position (0,0) which width equals 10.
	 * @return The created square.
	 * @since 3.0
	 */
	ISquare createSquare();

	/**
	 * Creates a square.
	 * @param pos The north-west point of the square.
	 * @param width The width of the square.
	 * @return The created square.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 * @since 3.0
	 */
	ISquare createSquare(IPoint pos, double width);

	/**
	 * Creates a circled arc.
	 * @param pos The north-west point of the square.
	 * @param width The width of the square.
	 * @return The created circled arc.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 * @since 3.0
	 */
	ICircleArc createCircleArc(IPoint pos, double width);

	/**
	 * Creates a circled arc with a 1 radius.
	 * @return The created circled arc.
	 * @since 3.0
	 */
	ICircleArc createCircleArc();

	/**
	 * Creates a plotted function.
	 * @param pos The north-west point of the rectangle.
	 * @param minX The min position of the function.
	 * @param maxX The max position of the function.
	 * @param eq The equation of the function.
	 * @param polar Defines the coordinates to use (polar or cartesian).
	 * @return The created function.
	 * @throws IllegalArgumentException If the given point is not valid or minX is greater than
	 * maxX.
	 * @since 3.2
	 */
	IPlot createPlot(IPoint pos, double minX, double maxX, String eq, boolean polar);

	/**
	 * Duplicates the given shape.
	 * @param shape The shape to duplicate
	 * @return The duplicated shape or null.
	 * @since 3.0
	 */
	<T extends IShape> T duplicate(final T shape);
}
