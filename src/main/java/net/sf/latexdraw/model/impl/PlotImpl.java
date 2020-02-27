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
package net.sf.latexdraw.model.impl;

import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.stream.IntStream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.DotProp;
import net.sf.latexdraw.model.api.property.PlotProp;
import net.sf.latexdraw.model.api.shape.Color;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Position;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.parser.ps.PSFunctionParser;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of the plotted function.
 * @author Arnaud Blouin
 */
class PlotImpl extends PositionShapeBase implements Plot {
	private final @NotNull IntegerProperty nbPoints;
	private final @NotNull ObjectProperty<PlotStyle> style;
	private final @NotNull ObjectProperty<DotStyle> dotStyle;
	private final @NotNull DoubleProperty dotDiametre;
	private final @NotNull BooleanProperty polar;
	private final @NotNull DoubleProperty minX;
	private final @NotNull DoubleProperty maxX;
	private final @NotNull StringProperty equation;
	private final @NotNull DoubleProperty xscale;
	private final @NotNull DoubleProperty yscale;
	private @NotNull PSFunctionParser parser;

	PlotImpl(final Point pt, final double xMin, final double xMax, final String equationPlot, final boolean polarCoord) {
		super(pt);

		if(xMin >= xMax || !MathUtils.INST.isValidPt(pt) || !MathUtils.INST.isValidCoord(xMin) || !MathUtils.INST.isValidCoord(xMax)) {
			throw new IllegalArgumentException("Parameter not valid: " + xMin + " " + xMax + " " + MathUtils.INST.isValidPt(pt));
		}

		nbPoints = new SimpleIntegerProperty(50);
		style = new SimpleObjectProperty<>(PlotStyle.CURVE);
		equation = new SimpleStringProperty(equationPlot);
		parser = new PSFunctionParser(equationPlot);
		dotStyle = new SimpleObjectProperty<>(DotStyle.DOT);
		dotDiametre = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM * Shape.PPC + PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM);
		polar = new SimpleBooleanProperty(polarCoord);
		minX = new SimpleDoubleProperty(xMin);
		maxX = new SimpleDoubleProperty(xMax);
		xscale = new SimpleDoubleProperty(1d);
		yscale = new SimpleDoubleProperty(1d);
	}


	@Override
	public void copy(final Shape sh) {
		super.copy(sh);

		if(sh instanceof PlotProp) {
			final PlotProp plot = (PlotProp) sh;
			style.set(plot.getPlotStyle());
			nbPoints.set(plot.getNbPlottedPoints());
			polar.set(plot.isPolar());
			dotStyle.set(plot.getDotStyle());
			dotDiametre.set(plot.getDiametre());
			minX.set(plot.getPlotMinX());
			maxX.set(plot.getPlotMaxX());
			xscale.set(plot.getXScale());
			yscale.set(plot.getYScale());
			setPlotEquation(plot.getPlotEquation());
		}else {
			if(sh instanceof DotProp) {
				final DotProp dot = (DotProp) sh;
				dotStyle.set(dot.getDotStyle());
				dotDiametre.set(dot.getDiametre());
			}
		}

		parser = new PSFunctionParser(equation.get());
	}

	@Override
	public @NotNull Plot duplicate() {
		final Plot plot = ShapeFactory.INST.createPlot(getPosition(), getPlotMinX(), getPlotMaxX(), getPlotEquation(), isPolar());
		plot.copy(this);
		return plot;
	}

	@Override
	public void mirrorVertical(final double y) {
		final Point gc = getGravityCentre();
		if(MathUtils.INST.isValidCoord(y) && !MathUtils.INST.equalsDouble(y, gc.getY())) {
			translate(0d, gc.verticalSymmetry(y).getY() - gc.getY());
		}
	}

	@Override
	public void mirrorHorizontal(final double x) {
		final Point gc = getGravityCentre();
		if(MathUtils.INST.isValidCoord(x) && !MathUtils.INST.equalsDouble(x, gc.getX())) {
			translate(gc.horizontalSymmetry(x).getX() - gc.getX(), 0d);
		}
	}

	@Override
	public @NotNull PlotStyle getPlotStyle() {
		return style.get();
	}

	@Override
	public void setPlotStyle(final @NotNull PlotStyle plotStyle) {
		style.setValue(plotStyle);
	}

	@Override
	public boolean isShowPtsable() {
		return false;
	}

	@Override
	public boolean isThicknessable() {
		return getPlotStyle() != PlotStyle.DOTS;
	}

	@Override
	public boolean isShadowable() {
		return getPlotStyle() != PlotStyle.DOTS;
	}

	@Override
	public boolean isLineStylable() {
		return getPlotStyle() != PlotStyle.DOTS;
	}

	@Override
	public boolean isInteriorStylable() {
		return getPlotStyle() != PlotStyle.DOTS;
	}

	@Override
	public boolean isFillable() {
		return getPlotStyle() != PlotStyle.DOTS || dotStyle.get().isFillable();
	}

	@Override
	public boolean isDbleBorderable() {
		return getPlotStyle() != PlotStyle.DOTS;
	}

	@Override
	public double getPlottingStep() {
		return (getPlotMaxX() - getPlotMinX()) / (getNbPlottedPoints() - 1);
	}

	@Override
	public @NotNull Point getTopLeftPoint() {
		final double step = getPlottingStep();
		final Point pos = getPosition();
		final double plotMinX = getPlotMinX();
		final double yMax = IntStream.range(0, getNbPlottedPoints()).mapToDouble(x -> getY(plotMinX + x * step)).max().orElse(0.0);
		return ShapeFactory.INST.createPoint(pos.getX() + plotMinX * Shape.PPC * getXScale(), pos.getY() - yMax * Shape.PPC * getYScale());
	}

	@Override
	public @NotNull Point getBottomRightPoint() {
		final double step = getPlottingStep();
		final Point pos = getPosition();
		final double plotMinX = getPlotMinX();
		final double yMin = IntStream.range(0, getNbPlottedPoints()).mapToDouble(x -> getY(plotMinX + x * step)).min().orElse(0.0);
		return ShapeFactory.INST.createPoint(pos.getX() + getPlotMaxX() * Shape.PPC * getXScale(), pos.getY() - yMin * Shape.PPC * getYScale());
	}

	@Override
	public @NotNull Point getTopRightPoint() {
		final double step = getPlottingStep();
		final Point pos = getPosition();
		final double plotMinX = getPlotMinX();
		final double maxY = IntStream.range(0, getNbPlottedPoints()).mapToDouble(x -> getY(plotMinX + x * step)).max().orElse(0.0);
		return ShapeFactory.INST.createPoint(pos.getX() + getPlotMaxX() * Shape.PPC * getXScale(), pos.getY() - maxY * Shape.PPC * getYScale());
	}

	@Override
	public @NotNull Point getBottomLeftPoint() {
		final double step = getPlottingStep();
		final Point pos = getPosition();
		final double plotMinX = getPlotMinX();
		final double yMin = IntStream.range(0, getNbPlottedPoints()).mapToDouble(x -> getY(plotMinX + x * step)).min().orElse(0.0);
		return ShapeFactory.INST.createPoint(pos.getX() + plotMinX * Shape.PPC * getXScale(), pos.getY() - yMin * Shape.PPC * getYScale());
	}

	@Override
	protected void scaleSetPointsWithRatio(final @NotNull List<Point> pts, final double prevWidth, final double prevHeight, final @NotNull Position pos,
		final @NotNull Rectangle2D bound) {
		scaleSetPoints(pts, prevWidth, prevHeight, pos, bound);
	}

	@Override
	protected void scaleSetPoints(final @NotNull List<Point> pts, final double prevWidth, final double prevHeight, final @NotNull Position pos,
		final @NotNull Rectangle2D bound) {
		switch(pos) {
			case EAST:
				getPtAt(0).translate(bound.getWidth() - prevWidth, 0d);
				break;
			case WEST:
				getPtAt(0).translate(prevWidth - bound.getWidth(), 0d);
				break;
			case SOUTH:
				getPtAt(0).translate(0d, bound.getHeight() - prevHeight);
				break;
			case NORTH:
				getPtAt(0).translate(0d, prevHeight - bound.getHeight());
				break;
			case NE:
				getPtAt(0).translate(bound.getWidth() - prevWidth, prevHeight - bound.getHeight());
				break;
			case NW:
				getPtAt(0).translate(prevWidth - bound.getWidth(), prevHeight - bound.getHeight());
				break;
			case SE:
				getPtAt(0).translate(bound.getWidth() - prevWidth, bound.getHeight() - prevHeight);
				break;
			case SW:
				getPtAt(0).translate(prevWidth - bound.getWidth(), bound.getHeight() - prevHeight);
				break;
		}
	}

	@Override
	public @NotNull Point getPosition() {
		return getPtAt(0);
	}

	@Override
	public int getNbPlottedPoints() {
		return nbPoints.get();
	}

	@Override
	public void setNbPlottedPoints(final int nbPts) {
		if(nbPts > 1) {
			nbPoints.setValue(nbPts);
		}
	}

	@Override
	public double getY(final double x) {
		return parser.getY(x);
	}

	@Override
	public @NotNull String getPlotEquation() {
		return equation.get();
	}

	@Override
	public void setPlotEquation(final @NotNull String eq) {
		if(!eq.isEmpty()) {
			parser = new PSFunctionParser(eq);
			equation.setValue(eq);
		}
	}

	@Override
	public double getPlotMinX() {
		return minX.get();
	}

	@Override
	public void setPlotMinX(final double x) {
		if(MathUtils.INST.isValidCoord(x) && x < getPlotMaxX()) {
			minX.setValue(x);
		}
	}

	@Override
	public double getPlotMaxX() {
		return maxX.get();
	}

	@Override
	public void setPlotMaxX(final double x) {
		if(MathUtils.INST.isValidCoord(x) && x > getPlotMinX()) {
			maxX.setValue(x);
		}
	}

	@Override
	public boolean isPolar() {
		return polar.get();
	}

	@Override
	public void setPolar(final boolean pol) {
		polar.setValue(pol);
	}

	@Override
	public double getDiametre() {
		return dotDiametre.get();
	}

	@Override
	public void setDiametre(final double diam) {
		if(diam > 0d && MathUtils.INST.isValidCoord(diam)) {
			dotDiametre.setValue(diam);
		}
	}

	@Override
	public @NotNull Color getDotFillingCol() {
		return super.getFillingCol();
	}

	@Override
	public void setDotFillingCol(final @NotNull Color col) {
		setFillingCol(col);
	}

	@Override
	public @NotNull DotStyle getDotStyle() {
		return dotStyle.get();
	}

	@Override
	public void setDotStyle(final @NotNull DotStyle dotst) {
		dotStyle.setValue(dotst);
	}

	@Override
	public void setScale(final double scale) {
		setXScale(scale);
		setYScale(scale);
	}

	@Override
	public double getXScale() {
		return xscale.get();
	}

	@Override
	public void setXScale(final double xscalePlot) {
		if(xscalePlot > 0d && MathUtils.INST.isValidCoord(xscalePlot)) {
			xscale.setValue(xscalePlot);
		}
	}

	@Override
	public double getYScale() {
		return yscale.get();
	}

	@Override
	public void setYScale(final double yScalePlot) {
		if(yScalePlot > 0d && MathUtils.INST.isValidCoord(yScalePlot)) {
			yscale.setValue(yScalePlot);
		}
	}

	@Override
	public @NotNull BooleanProperty polarProperty() {
		return polar;
	}

	@Override
	public @NotNull StringProperty plotEquationProperty() {
		return equation;
	}

	@Override
	public @NotNull DoubleProperty plotMinXProperty() {
		return minX;
	}

	@Override
	public @NotNull DoubleProperty plotMaxXProperty() {
		return maxX;
	}

	@Override
	public @NotNull IntegerProperty nbPlottedPointsProperty() {
		return nbPoints;
	}

	@Override
	public @NotNull ObjectProperty<PlotStyle> plotStyleProperty() {
		return style;
	}

	@Override
	public @NotNull ObjectProperty<DotStyle> dotStyleProperty() {
		return dotStyle;
	}

	@Override
	public @NotNull DoubleProperty dotDiametreProperty() {
		return dotDiametre;
	}

	@Override
	public @NotNull DoubleProperty xScaleProperty() {
		return xscale;
	}

	@Override
	public @NotNull DoubleProperty yScaleProperty() {
		return yscale;
	}
}
