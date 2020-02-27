/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.api.shape;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Point3D;
import org.jetbrains.annotations.NotNull;

/**
 * The API for shape factories.
 * @author Arnaud BLOUIN
 */
public interface Factory {
	/**
	 * @param shapeClass The class of the shape to instantiated.
	 * @return A new instance of the class given as argument or null.
	 */
	@NotNull <T extends Shape> Optional<T> newShape(java.lang.Class<T> shapeClass);

	/**
	 * Creates a color from an JavaFX color.
	 * @param col The colour to convert.
	 * @return The converted colour. Cannot be null.
	 */
	@NotNull Color createColorFX(final javafx.scene.paint.Color col);

	/**
	 * Creates a color following the HSB format.
	 * @param h The H
	 * @param s The S
	 * @param b The B
	 * @return The converted colour. Cannot be null.
	 */
	@NotNull Color createColorHSB(double h, double s, double b);

	/**
	 * Creates a colour following the RGBA format.
	 * @param r The R.
	 * @param g The G.
	 * @param b The B.
	 * @param a The A.
	 * @return The converted colour. Cannot be null.
	 */
	@NotNull Color createColorInt(int r, int g, int b, int a);

	/**
	 * Creates a colour following the RGB format.
	 * @param r The R.
	 * @param g The G.
	 * @param b The B.
	 * @return The converted colour. Cannot be null.
	 */
	@NotNull Color createColorInt(int r, int g, int b);

	/**
	 * Creates a colour.
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 * @param o Opacity
	 * @return The converted colour. Cannot be null.
	 */
	@NotNull Color createColor(double r, double g, double b, double o);

	/**
	 * Creates a colour.
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 * @return The converted colour. Cannot be null.
	 */
	@NotNull Color createColor(double r, double g, double b);

	/**
	 * Creates a colour (1,1,1,1)
	 * @return The converted colour. Cannot be null.
	 */
	@NotNull Color createColor();

	/**
	 * @return The created drawing.
	 */
	@NotNull Drawing createDrawing();

	/**
	 * Creates a group that will contains initially the given sh.
	 * @param sh The shape to add to the group to create.
	 * @return Created groupe. Cannot be null.
	 */
	@NotNull Group createGroup(Shape sh);

	/**
	 * Creates an arrow from an other arrow.
	 * @param arrow The arrow to copy.
	 * @param owner The shape that contains the arrow.
	 * @return The created arrow.
	 * @throws IllegalArgumentException If the given arrow is null.
	 */
	@NotNull Arrow createArrow(Arrow arrow, ArrowableSingleShape owner);

	/**
	 * Creates an arrow.
	 * @param owner The shape that contains the arrow.
	 * @return The created arrow.
	 */
	@NotNull Arrow createArrow(ArrowableSingleShape owner);

	/**
	 * Creates axes with default values.
	 * @param pt The bottom left position of the axes.
	 * @return The created axes.
	 */
	@NotNull Axes createAxes(Point pt);

	/**
	 * @param pt The centre of the dot.
	 * @return The created dot.
	 */
	@NotNull Dot createDot(Point pt);

	/**
	 * Creates a bezier curve with a set of points.
	 * @param pts The points of the curve.
	 * @return The created bezier curve.
	 */
	@NotNull BezierCurve createBezierCurve(final @NotNull List<Point> pts);

	@NotNull BezierCurve createBezierCurve(final @NotNull List<Point> pts, final @NotNull List<Point> ctrlpts);

	@NotNull BezierCurve createBezierCurveFrom(final BezierCurve bc, final Point pointToAdd);

	/**
	 * Creates an ellipse.
	 * @param tl The top-left point of the ellipse.
	 * @param br The bottom-right point of the ellipse.
	 * @return The created ellipse.
	 * @throws IllegalArgumentException If a or b is not valid.
	 */
	@NotNull Ellipse createEllipse(Point tl, Point br);

	/**
	 * @return The created ellipse.
	 */
	@NotNull Ellipse createEllipse();

	/**
	 * Creates a triangle.
	 * @param pos The north-west point of the triangle.
	 * @param width The width of the triangle.
	 * @param height The height of the triangle.
	 * @return The created triangle.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 */
	@NotNull Triangle createTriangle(Point pos, double width, double height);

	/**
	 * @return The created triangle.
	 */
	@NotNull Triangle createTriangle();

	/**
	 * Creates a rhombus.
	 * @param centre The centre of the rhombus.
	 * @param width The width of the rhombus.
	 * @param height The height of the rhombus.
	 * @return The created rhombus.
	 * @throws IllegalArgumentException If the width, the height or the centre is not valid.
	 */
	@NotNull Rhombus createRhombus(Point centre, double width, double height);

	/**
	 * Creates a rhombus at the position (0,0) with width=height=1.
	 * @return The created rhombus.
	 */
	@NotNull Rhombus createRhombus();

	/**
	 * Creates a picture and the corresponding EPS picture.
	 * @param pt The position of the top-left point of the picture.
	 * @return The created picture.
	 * @throws IllegalArgumentException If the given picture path is not valid.
	 * @throws NullPointerException If the given point is null.
	 */
	@NotNull Picture createPicture(Point pt);

	/**
	 * Creates a grid with a predefined point.
	 * @param pt The position.
	 * @return The created grid.
	 */
	@NotNull Grid createGrid(Point pt);

	/**
	 * Creates and initialises a freehand model.
	 * @return The created freehand shape.
	 * @throws IllegalArgumentException If the given point is not valid.
	 */
	@NotNull Freehand createFreeHand(final @NotNull List<Point> pts);

	@NotNull Freehand createFreeHandFrom(Freehand sh, Point pointToAdd);

	/**
	 * Creates a circle.
	 * @param pt The position of the top-left point of the picture.
	 * @param radius The radius.
	 * @return The created circle.
	 * @throws IllegalArgumentException If the radius is not valid.
	 * @throws NullPointerException If the given point pt is null.
	 */
	@NotNull Circle createCircle(Point pt, double radius);

	/**
	 * @return The created circle.
	 */
	@NotNull Circle createCircle();

	/**
	 * @return The created group of shapes.
	 */
	@NotNull Group createGroup();

	/**
	 * Constructs a line from the specified coordinates.
	 * @param x1 the X coordinate of the start point.
	 * @param y1 the Y coordinate of the start point.
	 * @param x2 the X coordinate of the end point.
	 * @param y2 the Y coordinate of the end point.
	 * @return The created line.
	 * @throws IllegalArgumentException If one of the given coordinate is not valid.
	 */
	@NotNull Line createLine(double x1, double y1, double x2, double y2);

	/**
	 * Creates a line by creating a second point with
	 * @param b y = ax+ b
	 * @param p1 The first point.
	 * @return The created line.
	 * @throws IllegalArgumentException If one of the given parameter is not valid.
	 */
	@NotNull Line createLine(double b, Point p1);

	/**
	 * Constructs a line from the specified <code>Point2D</code> objects.
	 * @param p1 the start <code>Point2D</code> of this line segment.
	 * @param p2 the end <code>Point2D</code> of this line segment.
	 * @return The created line.
	 * @throws IllegalArgumentException If one of the given points is not valid.
	 */
	@NotNull Line createLine(Point p1, Point p2);

	/**
	 * @return The created point with coordinates (0, 0).
	 */
	@NotNull Point createPoint();

	/**
	 * Duplicates a java 2D point into a IPoint. If the given point pt is null, a point (0,0) is
	 * created.
	 * @param pt The point to convert.
	 * @return The created point. Cannot be null.
	 */
	@NotNull Point createPoint(Point2D pt);

	/**
	 * Duplicates a JFX point into a IPoint. If the given point pt is null, a point (0,0) is
	 * created.
	 * @param pt The point to convert.
	 * @return The created point. Cannot be null.
	 */
	@NotNull Point createPoint(javafx.geometry.Point2D pt);

	/**
	 * Duplicates a java 3D point into a IPoint. If the given point pt is null, a point (0,0) is
	 * created.
	 * @param pt The point to convert.
	 * @return The created point. Cannot be null.
	 */
	@NotNull Point createPoint(Point3D pt);

	/**
	 * Creates a Point2D with the specified coordinates.
	 * @param x The X-coordinate to set.
	 * @param y The Y-coordinate to set.
	 * @return The created point.
	 */
	@NotNull Point createPoint(double x, double y);

	/**
	 * Creates a Point2D with the specified coordinates.
	 * @param pt The IPoint, if null the default value (0,0) will be used.
	 * @return The created point.
	 */
	@NotNull Point createPoint(Point pt);

	/**
	 * Creates a model with a set of points.
	 * @param pts The points of the shape.
	 * @return The created polyline.
	 */
	@NotNull Polyline createPolyline(final @NotNull List<Point> pts);

	@NotNull Polyline createPolylineFrom(Polyline sh, Point pointToAdd);

	/**
	 * Creates a polygon with a set of points.
	 * @param pts The points of the shape.
	 * @return The created polygon.
	 */
	@NotNull Polygon createPolygon(final @NotNull List<Point> pts);

	@NotNull Polygon createPolygonFrom(Polygon sh, Point pointToAdd);

	/**
	 * @return The created rectangle with position (0,0) and width=10 and height=10.
	 */
	@NotNull Rectangle createRectangle();

	/**
	 * Creates a rectangle.
	 * @param pos The north-west point of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @return The created rectangle.
	 * @throws IllegalArgumentException If the width, the height or the point is not valid.
	 * @throws NullPointerException if the given point is null.
	 */
	@NotNull Rectangle createRectangle(Point pos, double width, double height);

	/**
	 * Creates a rectangle.
	 * @param tl The top left point of the rectangle.
	 * @param br The bottom right point of the rectangle.
	 * @return The created rectangle.
	 * @throws IllegalArgumentException if one of the given points is not valid.
	 */
	@NotNull Rectangle createRectangle(Point tl, Point br);

	/**
	 * Create a text at position (0,0) which text is "text".
	 * @return The created text.
	 */
	@NotNull Text createText();

	/**
	 * Creates a text.
	 * @param pt The position of the text.
	 * @param text The text.
	 * @return The created text.
	 * @throws IllegalArgumentException If pt is not valid.
	 */
	@NotNull Text createText(Point pt, String text);

	/**
	 * Creates a square at position (0,0) which width equals 10.
	 * @return The created square.
	 */
	@NotNull Square createSquare();

	/**
	 * Creates a square.
	 * @param pos The north-west point of the square.
	 * @param width The width of the square.
	 * @return The created square.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 */
	@NotNull Square createSquare(Point pos, double width);

	/**
	 * Creates a circled arc.
	 * @param pos The north-west point of the square.
	 * @param width The width of the square.
	 * @return The created circled arc.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 */
	@NotNull CircleArc createCircleArc(Point pos, double width);

	/**
	 * Creates a circled arc with a 1 radius.
	 * @return The created circled arc.
	 */
	@NotNull CircleArc createCircleArc();

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
	 */
	@NotNull Plot createPlot(Point pos, double minX, double maxX, String eq, boolean polar);

	/**
	 * Duplicates the given shape.
	 * @param shape The shape to duplicate
	 * @return The duplicated shape or nothing.
	 */
	@NotNull <T extends Shape> Optional<T> duplicate(final T shape);
}
