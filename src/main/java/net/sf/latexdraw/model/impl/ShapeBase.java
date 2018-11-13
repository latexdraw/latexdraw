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
package net.sf.latexdraw.model.impl;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.Color;
import net.sf.latexdraw.model.api.shape.FillingStyle;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.SingleShape;
import net.sf.latexdraw.model.api.shape.LineStyle;
import net.sf.latexdraw.model.api.shape.Position;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The base shape model.
 * @author Arnaud Blouin
 */
abstract class ShapeBase implements SingleShape {
	/** The thickness of the lines of the shape in pixels. */
	protected final @NotNull DoubleProperty thickness;

	/** The colour of the lines. */
	protected final @NotNull ObjectProperty<Color> lineColour;

	/** The style of the lines. */
	protected final @NotNull ObjectProperty<LineStyle> lineStyle;

	/** The style of the interior of the shape. */
	protected final @NotNull ObjectProperty<FillingStyle> fillingStyle;

	/** The white dash separator for dashed lines in pixel. */
	protected final @NotNull DoubleProperty dashSepWhite;

	/** The black dash separator for dashed lines in pixel. */
	protected final @NotNull DoubleProperty dashSepBlack;

	/** The dot separator for dotted lines. */
	protected final @NotNull DoubleProperty dotSep;

	/** The colour of the interior of the shape. */
	protected final @NotNull ObjectProperty<Color> fillingCol;

	/** The start colour of the gradient. */
	protected final @NotNull ObjectProperty<Color> gradColStart;

	/** The end colour of the gradient. */
	protected final @NotNull ObjectProperty<Color> gradColEnd;

	/** The angle of the gradient in radian. */
	protected final @NotNull DoubleProperty gradAngle;

	/** The middle point of the gradient. */
	protected final @NotNull DoubleProperty gradMidPt;

	/** The separation size between hatchings in pixel. */
	protected final @NotNull DoubleProperty hatchingsSep;

	/** The colour of the hatchings. */
	protected final @NotNull ObjectProperty<Color> hatchingsCol;

	/** The angle of the hatchings in radian. */
	protected final @NotNull DoubleProperty hatchingsAngle;

	/** The thickness of the hatchings in pixel. */
	protected final @NotNull DoubleProperty hatchingsWidth;

	/** The rotation angle of the shape. */
	protected final @NotNull DoubleProperty rotationAngle;

	/** Defines if the points of the shape must be considered. */
	protected final @NotNull BooleanProperty showPts;

	/** Defines if the shape has double borders. */
	protected final @NotNull BooleanProperty hasDbleBord;

	/** The colour of the double borders. */
	protected final @NotNull ObjectProperty<Color> dbleBordCol;

	/** The separation size of the double borders in pixel. */
	protected final @NotNull DoubleProperty dbleBordSep;

	/** Defines if the shape has a shadow. */
	protected final @NotNull BooleanProperty hasShadow;

	/** The colour of the shadow. */
	protected final @NotNull ObjectProperty<Color> shadowCol;

	/** The angle of the shadow in radian. */
	protected final @NotNull DoubleProperty shadowAngle;

	/** The size of the shadow in pixel. */
	protected final @NotNull DoubleProperty shadowSize;

	/** The position of the border of the shape. */
	protected final @NotNull ObjectProperty<BorderPos> bordersPosition;

	/** The points of the shape. */
	protected final @NotNull List<Point> points;

	/** Defined if the shape has been modified. */
	protected boolean modified;

	/**
	 * The second default constructor
	 */
	ShapeBase() {
		super();
		modified = false;
		thickness = new SimpleDoubleProperty(2d);
		rotationAngle = new SimpleDoubleProperty(0d);
		shadowAngle = new SimpleDoubleProperty(-Math.PI / 4d);
		gradAngle = new SimpleDoubleProperty(0d);
		hatchingsAngle = new SimpleDoubleProperty(0d);
		hasShadow = new SimpleBooleanProperty(false);
		hasDbleBord = new SimpleBooleanProperty(false);
		lineStyle = new SimpleObjectProperty<>(LineStyle.SOLID);
		lineColour = new SimpleObjectProperty<>(PSTricksConstants.DEFAULT_LINE_COLOR);
		dotSep = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_DOT_STEP * PPC);
		dashSepBlack = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_DASH_BLACK * PPC);
		dashSepWhite = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_DASH_WHITE * PPC);
		hatchingsCol = new SimpleObjectProperty<>(PSTricksConstants.DEFAULT_HATCHING_COLOR);
		hatchingsSep = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_HATCH_SEP * PPC);
		hatchingsWidth = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_HATCH_WIDTH * PPC);
		fillingStyle = new SimpleObjectProperty<>(FillingStyle.NONE);
		fillingCol = new SimpleObjectProperty<>(PSTricksConstants.DEFAULT_FILL_COLOR);
		bordersPosition = new SimpleObjectProperty<>(BorderPos.INTO);
		dbleBordCol = new SimpleObjectProperty<>(PSTricksConstants.DEFAULT_DOUBLE_COLOR);
		dbleBordSep = new SimpleDoubleProperty(6d);
		shadowCol = new SimpleObjectProperty<>(PSTricksConstants.DEFAULT_SHADOW_COLOR);
		shadowSize = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_SHADOW_SIZE * PPC);
		gradColStart = new SimpleObjectProperty<>(PSTricksConstants.DEFAULT_GRADIENT_START_COLOR);
		gradColEnd = new SimpleObjectProperty<>(PSTricksConstants.DEFAULT_GRADIENT_END_COLOR);
		gradMidPt = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_GRADIENT_MID_POINT);
		showPts = new SimpleBooleanProperty(false);
		points = new ArrayList<>();
	}

	@Override
	public double getFullThickness() {
		return isDbleBorderable() && hasDbleBord() ? getThickness() * 2d + getDbleBordSep() : getThickness();
	}

	@Override
	public void copy(final Shape sh) {
		if(sh == null) {
			return;
		}

		setThickness(sh.getThickness());
		setRotationAngle(sh.getRotationAngle());
		setShadowAngle(sh.getShadowAngle());
		setGradAngle(sh.getGradAngle());
		setHatchingsAngle(sh.getHatchingsAngle());
		setHasShadow(sh.hasShadow());
		setHasDbleBord(sh.hasDbleBord());
		setLineStyle(sh.getLineStyle());
		setLineColour(sh.getLineColour());
		setDotSep(sh.getDotSep());
		setDashSepBlack(sh.getDashSepBlack());
		setDashSepWhite(sh.getDashSepWhite());
		setHatchingsCol(sh.getHatchingsCol());
		setHatchingsSep(sh.getHatchingsSep());
		setHatchingsWidth(sh.getHatchingsWidth());
		setFillingStyle(sh.getFillingStyle());
		setFillingCol(sh.getFillingCol());
		setBordersPosition(sh.getBordersPosition());
		setDbleBordCol(sh.getDbleBordCol());
		setDbleBordSep(sh.getDbleBordSep());
		setShadowCol(sh.getShadowCol());
		setShadowSize(sh.getShadowSize());
		setGradColStart(sh.getGradColStart());
		setGradColEnd(sh.getGradColEnd());
		setGradMidPt(sh.getGradMidPt());
		setShowPts(sh.isShowPts());
	}

	@Override
	public void addToRotationAngle(final Point gravCentre, final double angle) {
		if(MathUtils.INST.isValidCoord(angle)) {
			setRotationAngle(getRotationAngle() + angle);

			if(gravCentre != null) {
				final Point gravityCentre = getGravityCentre();
				final Point rotatedGC = gravityCentre.rotatePoint(gravCentre, angle);
				translate(rotatedGC.getX() - gravityCentre.getX(), rotatedGC.getY() - gravityCentre.getY());
			}
		}
	}

	@Override
	public double getBorderGap() {
		switch(bordersPosition.get()) {
			case MID:
				return hasDbleBord() ? thickness.doubleValue() + getDbleBordSep() / 2d : thickness.doubleValue() / 2d;
			case OUT:
				return hasDbleBord() ? thickness.doubleValue() * 2d + getDbleBordSep() : thickness.doubleValue();
			case INTO:
				return 0d;
		}
		return 0d;
	}

	@Override
	public @NotNull BorderPos getBordersPosition() {
		return bordersPosition.get();
	}

	@Override
	public double getDashSepBlack() {
		return dashSepBlack.doubleValue();
	}

	@Override
	public double getDashSepWhite() {
		return dashSepWhite.doubleValue();
	}

	@Override
	public @NotNull Color getDbleBordCol() {
		return dbleBordCol.get();
	}

	@Override
	public double getDbleBordSep() {
		return dbleBordSep.doubleValue();
	}

	@Override
	public double getDotSep() {
		return dotSep.doubleValue();
	}

	@Override
	public @NotNull Color getFillingCol() {
		return fillingCol.get();
	}

	@Override
	public @NotNull FillingStyle getFillingStyle() {
		return fillingStyle.get();
	}

	@Override
	public @NotNull Point getFullBottomRightPoint() {
		final double gap = getBorderGap();
		final Point br = getBottomRightPoint();

		br.translate(gap, gap);

		return br;
	}

	@Override
	public @NotNull Point getFullTopLeftPoint() {
		final double gap = getBorderGap();
		final Point tl = getTopLeftPoint();

		tl.translate(-gap, -gap);

		return tl;
	}

	@Override
	public double getGradAngle() {
		return gradAngle.doubleValue();
	}

	@Override
	public @NotNull Color getGradColEnd() {
		return gradColEnd.get();
	}

	@Override
	public @NotNull Color getGradColStart() {
		return gradColStart.get();
	}

	@Override
	public double getGradMidPt() {
		return gradMidPt.doubleValue();
	}

	@Override
	public @NotNull Point getGravityCentre() {
		return points.isEmpty() ? ShapeFactory.INST.createPoint() : getTopLeftPoint().getMiddlePoint(getBottomRightPoint());
	}

	@Override
	public double getHatchingsAngle() {
		return hatchingsAngle.doubleValue();
	}

	@Override
	public @NotNull Color getHatchingsCol() {
		return hatchingsCol.get();
	}

	@Override
	public double getHatchingsSep() {
		return hatchingsSep.doubleValue();
	}

	@Override
	public double getHatchingsWidth() {
		return hatchingsWidth.doubleValue();
	}

	@Override
	public @NotNull Color getLineColour() {
		return lineColour.get();
	}

	@Override
	public @NotNull LineStyle getLineStyle() {
		return lineStyle.get();
	}

	@Override
	public int getNbPoints() {
		return points.size();
	}

	@Override
	public @NotNull List<Point> getPoints() {
		return Collections.unmodifiableList(points);
	}

	@Override
	public Point getPtAt(final int position) {
		final Point point;

		if(position < -1 || points.isEmpty() || position >= points.size()) {
			point = null;
		}else {
			point = position == -1 ? points.get(points.size() - 1) : points.get(position);
		}

		return point;
	}

	@Override
	public double getRotationAngle() {
		return rotationAngle.get();
	}

	@Override
	public double getShadowAngle() {
		return shadowAngle.get();
	}

	@Override
	public @NotNull Color getShadowCol() {
		return shadowCol.get();
	}

	@Override
	public double getShadowSize() {
		return shadowSize.get();
	}

	@Override
	public double getThickness() {
		return thickness.doubleValue();
	}

	@Override
	public boolean hasDbleBord() {
		return hasDbleBord.get();
	}

	@Override
	public boolean hasGradient() {
		return isInteriorStylable() && fillingStyle.get() == FillingStyle.GRAD;
	}

	@Override
	public boolean hasHatchings() {
		return isInteriorStylable() && fillingStyle.get().isHatchings();
	}

	@Override
	public boolean hasShadow() {
		return hasShadow.get();
	}

	@Override
	public boolean isFilled() {
		return fillingStyle.get().isFilled();
	}

	@Override
	public boolean isShowPts() {
		return showPts.get();
	}

	@Override
	public boolean isColourable() {
		return true;
	}


	@Override
	public void setBordersPosition(final @NotNull BorderPos position) {
		if(isBordersMovable()) {
			bordersPosition.set(position);
		}
	}

	@Override
	public void setDashSepBlack(final double dash) {
		if(dash > 0d && MathUtils.INST.isValidCoord(dash)) {
			dashSepBlack.set(dash);
		}
	}

	@Override
	public void setDashSepWhite(final double dash) {
		if(dash > 0d && MathUtils.INST.isValidCoord(dash)) {
			dashSepWhite.set(dash);
		}
	}

	@Override
	public void setDbleBordCol(final @NotNull Color col) {
		if(isDbleBorderable()) {
			dbleBordCol.set(col);
		}
	}

	@Override
	public void setDbleBordSep(final double sep) {
		if(sep >= 0 && isDbleBorderable() && MathUtils.INST.isValidCoord(sep)) {
			dbleBordSep.set(sep);
		}
	}

	@Override
	public void setDotSep(final double sep) {
		if(sep >= 0 && MathUtils.INST.isValidCoord(sep)) {
			dotSep.set(sep);
		}
	}

	@Override
	public void setFilled(final boolean isFilled) {
		if(!isFillable()) {
			return;
		}

		if(isFilled) {
			fillingStyle.set(fillingStyle.get().getFilledStyle());
		}else {
			fillingStyle.set(fillingStyle.get().getUnfilledStyle());
		}
	}

	@Override
	public void setFillingCol(final @NotNull Color col) {
		if(isFillable()) {
			fillingCol.set(col);
		}
	}

	@Override
	public void setFillingStyle(final @NotNull FillingStyle style) {
		if(isFillable()) {
			fillingStyle.set(style);
		}
	}

	@Override
	public void setGradAngle(final double angle) {
		if(MathUtils.INST.isValidCoord(angle) && isInteriorStylable()) {
			gradAngle.set(angle);
		}
	}

	@Override
	public void setGradColEnd(final @NotNull Color col) {
		if(isInteriorStylable()) {
			gradColEnd.set(col);
		}
	}

	@Override
	public void setGradColStart(final @NotNull Color col) {
		if(isInteriorStylable()) {
			gradColStart.set(col);
		}
	}

	@Override
	public void setGradMidPt(final double pt) {
		if(pt >= 0 && pt <= 1 && isInteriorStylable()) {
			gradMidPt.set(pt);
		}
	}

	@Override
	public void setHasDbleBord(final boolean bord) {
		if(isDbleBorderable()) {
			hasDbleBord.set(bord);
		}
	}

	@Override
	public void setHasShadow(final boolean shad) {
		if(isShadowable()) {
			hasShadow.set(shad);
		}
	}

	@Override
	public void setHatchingsAngle(final double angle) {
		if(MathUtils.INST.isValidCoord(angle) && isInteriorStylable()) {
			hatchingsAngle.set(angle);
		}
	}

	@Override
	public void setHatchingsCol(final @NotNull Color col) {
		if(isInteriorStylable()) {
			hatchingsCol.set(col);
		}
	}

	@Override
	public void setHatchingsSep(final double sep) {
		if(sep >= 0d && MathUtils.INST.isValidCoord(sep) && isInteriorStylable()) {
			hatchingsSep.set(sep);
		}
	}

	@Override
	public void setHatchingsWidth(final double width) {
		if(width > 0d && MathUtils.INST.isValidCoord(width) && isInteriorStylable()) {
			hatchingsWidth.set(width);
		}
	}

	@Override
	public void setLineColour(final @NotNull Color col) {
		lineColour.set(col);
	}

	@Override
	public void setLineStyle(final @NotNull LineStyle style) {
		if(isLineStylable()) {
			lineStyle.setValue(style);
		}
	}

	@Override
	public void scale(final double prevWidth, final double prevHeight, final @NotNull Position pos, final @NotNull Rectangle2D bound) {
		scaleSetPoints(points, prevWidth, prevHeight, pos, bound);
	}

	@Override
	public void scaleWithRatio(final double prevWidth, final double prevHeight, final @NotNull Position pos, final @NotNull Rectangle2D bound) {
		scaleSetPointsWithRatio(points, prevWidth, prevHeight, pos, bound);
	}

	protected void scaleSetPointsWithRatio(final List<Point> pts, final double prevWidth, final double prevHeight, final Position pos, final Rectangle2D bound) {
		final double s = Math.max(prevWidth / bound.getWidth(), prevHeight / bound.getHeight());
		final Point refPt = pos.getReferencePoint(bound);
		final double refX = refPt.getX();
		final double refY = refPt.getY();

		for(final Point pt : pts) {
			if(!MathUtils.INST.equalsDouble(pt.getX(), refX)) {
				pt.setX(refX + (pt.getX() - refX) * s);
			}
			if(!MathUtils.INST.equalsDouble(pt.getY(), refY)) {
				pt.setY(refY + (pt.getY() - refY) * s);
			}
		}
	}

	protected void scaleSetPoints(final List<Point> pts, final double prevWidth, final double prevHeight, final Position pos, final Rectangle2D bound) {
		final double sx = prevWidth / bound.getWidth();
		final double sy = prevHeight / bound.getHeight();
		final boolean xScale = pos.isEast() || pos.isWest();
		final boolean yScale = pos.isNorth() || pos.isSouth();
		final Point refPt = pos.getReferencePoint(bound);
		final double refX = refPt.getX();
		final double refY = refPt.getY();

		pts.forEach(pt -> {
			if(xScale && !MathUtils.INST.equalsDouble(pt.getX(), refX)) {
				pt.setX(refX + (pt.getX() - refX) * sx);
			}
			if(yScale && !MathUtils.INST.equalsDouble(pt.getY(), refY)) {
				pt.setY(refY + (pt.getY() - refY) * sy);
			}
		});
	}

	@Override
	public void setRotationAngle(final double angle) {
		if(MathUtils.INST.isValidCoord(angle)) {
			rotationAngle.set(angle);
		}
	}

	@Override
	public void setShadowAngle(final double angle) {
		if(isShadowable() && MathUtils.INST.isValidCoord(angle)) {
			shadowAngle.set(angle);
		}
	}

	@Override
	public void setShadowCol(final @NotNull Color col) {
		if(isShadowable()) {
			shadowCol.set(col);
		}
	}

	@Override
	public void setShadowSize(final double size) {
		if(size > 0d && isShadowable() && MathUtils.INST.isValidCoord(size)) {
			shadowSize.set(size);
		}
	}

	@Override
	public void setShowPts(final boolean pts) {
		if(isShowPtsable()) {
			showPts.set(pts);
		}
	}

	@Override
	public void setThickness(final double thick) {
		if(thick > 0d && isThicknessable() && MathUtils.INST.isValidCoord(thick)) {
			thickness.setValue(thick);
		}
	}

	@Override
	public boolean shadowFillsShape() {
		return true;
	}

	@Override
	public void translate(final double tx, final double ty) {
		if(MathUtils.INST.isValidPt(tx, ty)) {
			points.forEach(pt -> pt.translate(tx, ty));
		}
	}

	@Override
	public void mirrorHorizontal(final double x) {
		if(MathUtils.INST.isValidCoord(x)) {
			points.forEach(pt -> pt.setPoint(pt.horizontalSymmetry(x)));
		}
	}

	@Override
	public void mirrorVertical(final double y) {
		if(MathUtils.INST.isValidCoord(y)) {
			points.forEach(pt -> pt.setPoint(pt.verticalSymmetry(y)));
		}
	}

	@Override
	public @NotNull Point getBottomRightPoint() {
		return getNbPoints() == 1 ? ShapeFactory.INST.createPoint(getPtAt(0)) :
			points.stream().reduce((p1, p2) -> ShapeFactory.INST.createPoint(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()))).
				orElseGet(() -> ShapeFactory.INST.createPoint());
	}

	@Override
	public @NotNull Point getBottomLeftPoint() {
		return getNbPoints() == 1 ? ShapeFactory.INST.createPoint(getPtAt(0)) :
			points.stream().reduce((p1, p2) -> ShapeFactory.INST.createPoint(Math.min(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()))).
				orElseGet(() -> ShapeFactory.INST.createPoint());
	}

	@Override
	public @NotNull Point getTopLeftPoint() {
		return getNbPoints() == 1 ? ShapeFactory.INST.createPoint(getPtAt(0)) :
			points.stream().reduce((p1, p2) -> ShapeFactory.INST.createPoint(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()))).
				orElseGet(() -> ShapeFactory.INST.createPoint());
	}

	@Override
	public @NotNull Point getTopRightPoint() {
		return getNbPoints() == 1 ? ShapeFactory.INST.createPoint(getPtAt(0)) :
			points.stream().reduce((p1, p2) -> ShapeFactory.INST.createPoint(Math.max(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()))).
				orElseGet(() -> ShapeFactory.INST.createPoint());
	}

	@Override
	public @NotNull Shape duplicate() {
		final Shape shape = ShapeFactory.INST.newShape(getClass()).orElseThrow();
		shape.copy(this);
		return shape;
	}

	@Override
	public void setModified(final boolean changed) {
		modified = changed;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void rotate(final @Nullable Point point, final double angle) {
		final Point gc = getGravityCentre();

		if(point != null && !gc.equals(point)) { // The position of the shape must be rotated.
			final Point rotGC = gc.rotatePoint(point, angle);
			translate(rotGC.getX() - gc.getX(), rotGC.getY() - gc.getY());
		}

		setRotationAngle(getRotationAngle() + angle);
	}

	@Override
	public boolean isBordersMovable() {
		return false;
	}

	@Override
	public boolean isDbleBorderable() {
		return false;
	}

	@Override
	public boolean isFillable() {
		return true;
	}

	@Override
	public boolean isInteriorStylable() {
		return true;
	}

	@Override
	public boolean isLineStylable() {
		return false;
	}

	@Override
	public boolean isShadowable() {
		return true;
	}

	@Override
	public boolean isShowPtsable() {
		return false;
	}

	@Override
	public boolean isThicknessable() {
		return true;
	}

	@Override
	public boolean isTypeOf(final @NotNull Class<?> clazz) {
		return clazz.isInstance(this);
	}

	@Override
	public double getHeight() {
		return Math.abs(getBottomLeftPoint().getY() - getTopLeftPoint().getY());
	}

	@Override
	public double getWidth() {
		return Math.abs(getTopRightPoint().getX() - getTopLeftPoint().getX());
	}

	@Override
	public @NotNull DoubleProperty thicknessProperty() {
		return thickness;
	}

	@Override
	public @NotNull ObjectProperty<LineStyle> linestyleProperty() {
		return lineStyle;
	}

	@Override
	public @NotNull ObjectProperty<BorderPos> borderPosProperty() {
		return bordersPosition;
	}

	@Override
	public @NotNull ObjectProperty<Color> lineColourProperty() {
		return lineColour;
	}

	@Override
	public @NotNull ObjectProperty<FillingStyle> fillingProperty() {
		return fillingStyle;
	}

	@Override
	public @NotNull DoubleProperty dashSepWhiteProperty() {
		return dashSepWhite;
	}

	@Override
	public @NotNull DoubleProperty dashSepBlackProperty() {
		return dashSepBlack;
	}

	@Override
	public @NotNull DoubleProperty dotSepProperty() {
		return dotSep;
	}

	@Override
	public @NotNull BooleanProperty dbleBordProperty() {
		return hasDbleBord;
	}

	@Override
	public @NotNull DoubleProperty dbleBordSepProperty() {
		return dbleBordSep;
	}

	@Override
	public @NotNull ObjectProperty<Color> dbleBordColProperty() {
		return dbleBordCol;
	}

	@Override
	public @NotNull ObjectProperty<Color> gradColStartProperty() {
		return gradColStart;
	}

	@Override
	public @NotNull ObjectProperty<Color> gradColEndProperty() {
		return gradColEnd;
	}

	@Override
	public @NotNull ObjectProperty<Color> fillingColProperty() {
		return fillingCol;
	}

	@Override
	public @NotNull DoubleProperty gradAngleProperty() {
		return gradAngle;
	}

	@Override
	public @NotNull DoubleProperty gradMidPtProperty() {
		return gradMidPt;
	}

	@Override
	public @NotNull BooleanProperty shadowProperty() {
		return hasShadow;
	}

	@Override
	public @NotNull ObjectProperty<Color> shadowColProperty() {
		return shadowCol;
	}

	@Override
	public @NotNull DoubleProperty shadowAngleProperty() {
		return shadowAngle;
	}

	@Override
	public @NotNull DoubleProperty shadowSizeProperty() {
		return shadowSize;
	}

	@Override
	public @NotNull DoubleProperty hatchingsAngleProperty() {
		return hatchingsAngle;
	}

	@Override
	public @NotNull DoubleProperty hatchingsSepProperty() {
		return hatchingsSep;
	}

	@Override
	public @NotNull DoubleProperty hatchingsWidthProperty() {
		return hatchingsWidth;
	}

	@Override
	public @NotNull ObjectProperty<Color> hatchingsColProperty() {
		return hatchingsCol;
	}

	@Override
	public @NotNull DoubleProperty rotationAngleProperty() {
		return rotationAngle;
	}

	@Override
	public @NotNull ReadOnlyBooleanProperty showPointProperty() {
		return showPts;
	}
}
