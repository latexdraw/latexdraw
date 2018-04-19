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

import java.awt.geom.Rectangle2D;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import org.malai.properties.Modifiable;

/**
 * The API for abstract shapes.
 * @author Arnaud BLOUIN
 */
public interface IShape extends Modifiable {
	/** The number of pixels per centimetre by default. */
	int PPC = 50;

	/** Corresponds to the golden angle (Useful for golden diamond). */
	double GOLDEN_ANGLE = 0.553574;

	/**
	 * @return The points of the shape.
	 * @since 3.0
	 */
	List<IPoint> getPoints();

	/**
	 * @return The number of points of the shape.
	 */
	int getNbPoints();

	/**
	 * @param position The position of the wanted points (-1 for the last point).
	 * @return The point at the given position or null if the position is not valid.
	 * @since 3.0
	 */
	IPoint getPtAt(final int position);

	/**
	 * @return The full thickness of the shape. It means that the potential double boundary can be considered.
	 * @since 3.0
	 */
	double getFullThickness();

	/**
	 * Copies a shape using another.
	 * @param s The shape to copy.
	 */
	void copy(final IShape s);

	/**
	 * @return True if the thickness of the shape can be changed.
	 * @since 3.0
	 */
	boolean isThicknessable();

	/**
	 * @return True if the shape can have a double border.
	 * @since 3.0
	 */
	boolean isDbleBorderable();

	/**
	 * @return True if the shape can have colours.
	 * @since 3.0
	 */
	boolean isColourable();

	/**
	 * @return True if the shape can have an interior colour.
	 * @since 3.0
	 */
	boolean isFillable();

	/**
	 * @return True if the borders of the shape can be moved.
	 * @since 3.0
	 */
	boolean isBordersMovable();

	/**
	 * @return True if the interior of the shape can have a style (hatchings, gradient).
	 * @since 3.0
	 */
	boolean isInteriorStylable();

	/**
	 * @return True if the points of the shape can be displayed.
	 * @since 3.0
	 */
	boolean isShowPtsable();

	/**
	 * @return True if the line style of the shape can be changed.
	 * @since 3.0
	 */
	boolean isLineStylable();

	/**
	 * @return True if the shape can have a shadow.
	 * @since 3.0
	 */
	boolean isShadowable();

	/**
	 * @return The top left point of the shape. It does not take account
	 * of the thickness, the rotation angle, the double border, nor any
	 * parameters; only the points of the shape are used to compute the returned point.
	 * @since 3.0
	 */
	IPoint getTopLeftPoint();

	/**
	 * @return The top right point of the shape. It does not take account
	 * of the thickness, the rotation angle, the double border, nor any
	 * parameters; only the points of the shape are used to compute the returned point.
	 * @since 3.0
	 */
	IPoint getTopRightPoint();

	/**
	 * @return The bottom right point of the shape. It does not take account
	 * of the thickness, the rotation angle, the double border, nor any
	 * parameters; only the points of the shape are used to compute the returned point.
	 * @since 3.0
	 */
	IPoint getBottomRightPoint();

	/**
	 * @return The bottom left point of the shape. It does not take account
	 * of the thickness, the rotation angle, the double border, nor any
	 * parameters; only the points of the shape are used to compute the returned point.
	 * @since 3.0
	 */
	IPoint getBottomLeftPoint();

	/**
	 * @return The top left point of the shape. It takes account
	 * of the thickness, the rotation angle, the double border, or any
	 * parameters but not the rotation angle; in contrary to getTopLeftPoint().
	 * @since 3.0
	 */
	IPoint getFullTopLeftPoint();

	/**
	 * @return The bottom right point of the shape. It takes account
	 * of the thickness, the rotation angle, the double border, or any
	 * parameters but not the rotation angle; in contrary to getBottomRightPoint().
	 * @since 3.0
	 */
	IPoint getFullBottomRightPoint();


	/**
	 * Scales the shape where the move reference point is the
	 * bottom right point, and the fixation point the top left point.
	 * @param prevWidth The previous width of the scaled shape.
	 * @param prevHeight The previous height of the scaled shape.
	 * @param pos The position of the reference point: if the reference point is top-left point,
	 * then the scale will extend or reduce the shape at the bottom-right point. If the reference
	 * position is NORTH or SOUTH the sx parameter will not be used. If it is EAST or WEST the sy
	 * parameter will not be used.
	 * @param bound The bound (e.g. the border of the selected shapes) used to compute the scaling.
	 * @throws IllegalArgumentException If one of the parameter is not valid.
	 * @since 3.0
	 */
	void scale(final double prevWidth, final double prevHeight, final Position pos, final Rectangle2D bound);

	/**
	 * Scales the shape by using the same ratio between X and Y.
	 * @param prevWidth The previous width of the scaled shape.
	 * @param prevHeight The previous height of the scaled shape.
	 * @param pos The position of the reference point: if the reference point is top-left point,
	 * then the scale will extend or reduce the shape at the bottom-right point. If the reference
	 * position is NORTH or SOUTH the sx parameter will not be used. If it is EAST or WEST the sy
	 * parameter will not be used.
	 * @param bound The bound (e.g. the border of the selected shapes) used to compute the scaling.
	 * @throws IllegalArgumentException If one of the parameter is not valid.
	 * @since 3.0
	 */
	void scaleWithRatio(final double prevWidth, final double prevHeight, final Position pos, final Rectangle2D bound);

	/**
	 * Returns horizontally the shape.
	 * @param x The location of the horizontal axe.
	 */
	void mirrorHorizontal(final double x);

	/**
	 * Returns vertically the shape.
	 * @param y The location of the vertical axe.
	 */
	void mirrorVertical(final double y);

	/**
	 * Translates the shape.
	 * @param tx The X translation.
	 * @param ty The Y translation.
	 */
	void translate(final double tx, final double ty);

	/**
	 * @return True if the shape has hatchings.
	 * @since 3.0
	 */
	boolean hasHatchings();

	/**
	 * @return True if the shape has a gradient.
	 * @since 3.0
	 */
	boolean hasGradient();

	/**
	 * @return the thickness.
	 */
	double getThickness();

	/**
	 * @param thickness the thickness to set.
	 */
	void setThickness(final double thickness);

	/**
	 * @return the lineColour.
	 */
	Color getLineColour();

	/**
	 * @param lineColour the lineColour to set.
	 */
	void setLineColour(final Color lineColour);

	/**
	 * @return the lineStyle.
	 */
	LineStyle getLineStyle();

	/**
	 * @param lineStyle the lineStyle to set.
	 */
	void setLineStyle(final LineStyle lineStyle);

	/**
	 * @return the dashSepWhite.
	 */
	double getDashSepWhite();

	/**
	 * @param dashSepWhite the dashSepWhite to set.
	 */
	void setDashSepWhite(final double dashSepWhite);

	/**
	 * @return the dashSepBlack.
	 */
	double getDashSepBlack();

	/**
	 * @param dashSepBlack the dashSepBlack to set.
	 */
	void setDashSepBlack(final double dashSepBlack);

	/**
	 * @return the dotSep.
	 */
	double getDotSep();

	/**
	 * @param dotSep the dotSep to set.
	 */
	void setDotSep(final double dotSep);

	/**
	 * @return the fillingCol.
	 */
	Color getFillingCol();

	/**
	 * @param fillingCol the fillingCol to set.
	 */
	void setFillingCol(final Color fillingCol);

	/**
	 * @return the fillingStyle.
	 */
	FillingStyle getFillingStyle();

	/**
	 * @param fillingStyle the fillingStyle to set.
	 */
	void setFillingStyle(final FillingStyle fillingStyle);

	/**
	 * @return the gradColStart.
	 */
	Color getGradColStart();

	/**
	 * @param gradColStart the gradColStart to set.
	 */
	void setGradColStart(final Color gradColStart);

	/**
	 * @return the gradColEnd.
	 */
	Color getGradColEnd();

	/**
	 * @param gradColEnd the gradColEnd to set.
	 */
	void setGradColEnd(final Color gradColEnd);

	/**
	 * @return the gradAngle.
	 */
	double getGradAngle();

	/**
	 * @param gradAngle the gradAngle to set. In radian.
	 */
	void setGradAngle(final double gradAngle);

	/**
	 * @return the gradMidPt.
	 */
	double getGradMidPt();

	/**
	 * @param gradMidPt the gradMidPt to set. Must be in [0,1].
	 */
	void setGradMidPt(final double gradMidPt);

	/**
	 * @return the hatchingsSep.
	 */
	double getHatchingsSep();

	/**
	 * @param hatchingsSep the hatchingsSep to set. Must be greater or equal than 0.
	 */
	void setHatchingsSep(final double hatchingsSep);

	/**
	 * @return the hatchingsCol.
	 */
	Color getHatchingsCol();

	/**
	 * @param hatchingsCol the hatchingsCol to set.
	 */
	void setHatchingsCol(final Color hatchingsCol);

	/**
	 * @return the hatchingsAngle.
	 */
	double getHatchingsAngle();

	/**
	 * @param hatchingsAngle the hatchingsAngle to set. In radian.
	 */
	void setHatchingsAngle(final double hatchingsAngle);

	/**
	 * @return the hatchingsWidth.
	 */
	double getHatchingsWidth();

	/**
	 * @param hatchingsWidth the hatchingsWidth to set. Must be greater than 0.
	 */
	void setHatchingsWidth(final double hatchingsWidth);

	/**
	 * @return the rotationAngle.
	 */
	double getRotationAngle();

	/**
	 * @param rotationAngle the rotationAngle to set. In radian.
	 */
	void setRotationAngle(final double rotationAngle);

	/**
	 * @return the showPts.
	 */
	boolean isShowPts();

	/**
	 * @param showPts the showPts to set.
	 */
	void setShowPts(final boolean showPts);

	/**
	 * @return the hasDbleBord.
	 */
	boolean hasDbleBord();

	/**
	 * @param hasDbleBord the hasDbleBord to set.
	 */
	void setHasDbleBord(final boolean hasDbleBord);

	/**
	 * @return the dbleBordCol.
	 */
	Color getDbleBordCol();

	/**
	 * @param dbleBordCol the dbleBordCol to set.
	 */
	void setDbleBordCol(final Color dbleBordCol);

	/**
	 * @return the dbleBordSep.
	 */
	double getDbleBordSep();

	/**
	 * @param dbleBordSep the dbleBordSep to set. Must be greater or equal to 0.
	 */
	void setDbleBordSep(final double dbleBordSep);

	/**
	 * @return the hasShadow.
	 */
	boolean hasShadow();

	/**
	 * @param hasShadow the hasShadow to set.
	 */
	void setHasShadow(final boolean hasShadow);

	/**
	 * @return the shadowCol.
	 */
	Color getShadowCol();

	/**
	 * @param shadowCol the shadowCol to set.
	 */
	void setShadowCol(final Color shadowCol);

	/**
	 * @return the shadowAngle in radian.
	 */
	double getShadowAngle();

	/**
	 * @param shadowAngle the shadowAngle to set. In radian.
	 */
	void setShadowAngle(final double shadowAngle);

	/**
	 * @return the gravityCentre.
	 */
	IPoint getGravityCentre();

	/**
	 * @return the isFilled.
	 */
	boolean isFilled();

	/**
	 * @param isFilled the isFilled to set.
	 */
	void setFilled(final boolean isFilled);

	/**
	 * Adds the given angle to the current rotation angle.
	 * @param gravCentre The gravity centre of the rotation. If null, the gravity centre of the shape will be used.
	 * @param angle The angle to add. In radian.
	 */
	void addToRotationAngle(final IPoint gravCentre, final double angle);

	/**
	 * @return the shadowSize in pixel.
	 */
	double getShadowSize();

	/**
	 * @param shadowSize the shadowSize to set. Must be greater than 0.
	 */
	void setShadowSize(final double shadowSize);

	/**
	 * @return the bordersPosition.
	 */
	BorderPos getBordersPosition();

	/**
	 * Sets the position of the borders.
	 * @param position The new position.
	 * @since 3.0
	 */
	void setBordersPosition(final BorderPos position);

	/**
	 * @return True if when the shape has a shadow, it must be filled.
	 * @since 2.0
	 */
	boolean shadowFillsShape();

	/**
	 * Computes the gap created by the thickness, the double borders and the position
	 * of the border. For example, it helps to compute the top left point that considers
	 * the attributes of the shape (thickness, double borders,...).
	 * @return The computed gap.
	 * @since 3.0
	 */
	double getBorderGap();

	/**
	 * Creates a duplicate of the shape (however id are not the same).
	 * @return The duplicata.
	 * @since 3.0
	 */
	IShape duplicate();

	/**
	 * Rotates the shape.
	 * @param point The rotation centre.
	 * @param angle The angle of rotation in radians.
	 * @since 3.0
	 */
	void rotate(final IPoint point, final double angle);

	/**
	 * This operation is a workaround to support the dynamic typing
	 * of groups: the type of a group depends on its content.
	 * This operation tests if the type of the calling shape conforms
	 * to the given type.
	 * @param clazz The type to check.
	 * @return True if the type of the calling object conforms to the given type.
	 * @since 3.0
	 */
	boolean isTypeOf(final Class<?> clazz);

	/**
	 * @return The width of the rectangle.
	 * @since 3.0
	 */
	double getWidth();

	/**
	 * @return The height of the rectangle.
	 * @since 3.0
	 */
	double getHeight();

	DoubleProperty rotationAngleProperty();
}
