/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.impl;

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
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IDotProp;
import net.sf.latexdraw.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.models.interfaces.shape.Position;
import net.sf.latexdraw.parsers.ps.PSFunctionParser;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * Implementation of the plotted function.
 * @author Arnaud Blouin
 */
class LPlot extends LPositionShape implements IPlot {
	private final IntegerProperty nbPoints;
	private final ObjectProperty<PlotStyle> style;
	private final ObjectProperty<DotStyle> dotStyle;
	private final DoubleProperty dotDiametre;
	private final BooleanProperty polar;
	private final DoubleProperty minX;
	private final DoubleProperty maxX;
	private final StringProperty equation;
	private final DoubleProperty xscale;
	private final DoubleProperty yscale;
	private PSFunctionParser parser;

	LPlot(final IPoint pt, final double xMin, final double xMax, final String equationPlot, final boolean polarCoord) {
		super(pt);

		if(!(MathUtils.INST.isValidPt(pt) && xMin < xMax && MathUtils.INST.isValidCoord(xMin) && MathUtils.INST.isValidCoord(xMax)))
			throw new IllegalArgumentException("Parameter not valid: " + xMin + " " + xMax + " " + MathUtils.INST.isValidPt(pt));

		nbPoints = new SimpleIntegerProperty(50);
		style = new SimpleObjectProperty<>(PlotStyle.CURVE);
		equation = new SimpleStringProperty(equationPlot);
		parser = new PSFunctionParser(equationPlot);
		dotStyle = new SimpleObjectProperty<>(DotStyle.DOT);
		dotDiametre = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM * IShape.PPC + PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM);
		polar = new SimpleBooleanProperty(polarCoord);
		minX = new SimpleDoubleProperty(xMin);
		maxX = new SimpleDoubleProperty(xMax);
		xscale = new SimpleDoubleProperty(1d);
		yscale = new SimpleDoubleProperty(1d);
	}


	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IPlotProp) {
			final IPlotProp plot = (IPlotProp) sh;
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
		}else if(sh instanceof IDotProp) {
			final IDotProp dot = (IDotProp) sh;
			dotStyle.set(dot.getDotStyle());
			dotDiametre.set(dot.getDiametre());
		}

		parser = new PSFunctionParser(equation.get());
	}


	@Override
	public void mirrorVertical(final IPoint origin) {
		final IPoint gc = getGravityCentre();
		if(MathUtils.INST.isValidPt(origin) && !origin.equals(gc, 0.0001)) {
			translate(0d, gc.verticalSymmetry(origin).getY() - gc.getY());
		}
	}

	@Override
	public void mirrorHorizontal(final IPoint origin) {
		final IPoint gc = getGravityCentre();
		if(MathUtils.INST.isValidPt(origin) && !origin.equals(gc, 0.0001)) {
			translate(gc.horizontalSymmetry(origin).getX() - gc.getX(), 0d);
		}
	}

	@Override
	public PlotStyle getPlotStyle() {
		return style.get();
	}

	@Override
	public void setPlotStyle(final PlotStyle plotStyle) {
		if(plotStyle != null) {
			style.setValue(plotStyle);
		}
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
	public IPoint getTopLeftPoint() {
		final double step = getPlottingStep();
		final IPoint pos = getPosition();
		final double plotMinX = getPlotMinX();
		final double yMax = IntStream.range(0, getNbPlottedPoints()).mapToDouble(x -> getY(plotMinX + x * step)).max().orElse(0.0);
		return ShapeFactory.INST.createPoint(pos.getX() + plotMinX * IShape.PPC * getXScale(), pos.getY() - yMax * IShape.PPC * getYScale());
	}

	@Override
	public IPoint getBottomRightPoint() {
		final double step = getPlottingStep();
		final IPoint pos = getPosition();
		final double plotMinX = getPlotMinX();
		final double yMin = IntStream.range(0, getNbPlottedPoints()).mapToDouble(x -> getY(plotMinX + x * step)).min().orElse(0.0);
		return ShapeFactory.INST.createPoint(pos.getX() + getPlotMaxX() * IShape.PPC * getXScale(), pos.getY() - yMin * IShape.PPC * getYScale());
	}

	@Override
	public IPoint getTopRightPoint() {
		final double step = getPlottingStep();
		final IPoint pos = getPosition();
		final double plotMinX = getPlotMinX();
		final double maxY = IntStream.range(0, getNbPlottedPoints()).mapToDouble(x -> getY(plotMinX + x * step)).max().orElse(0.0);
		return ShapeFactory.INST.createPoint(pos.getX() + getPlotMaxX() * IShape.PPC * getXScale(), pos.getY() - maxY * IShape.PPC * getYScale());
	}

	@Override
	public IPoint getBottomLeftPoint() {
		final double step = getPlottingStep();
		final IPoint pos = getPosition();
		final double plotMinX = getPlotMinX();
		final double yMin = IntStream.range(0, getNbPlottedPoints()).mapToDouble(x -> getY(plotMinX + x * step)).min().orElse(0.0);
		return ShapeFactory.INST.createPoint(pos.getX() + plotMinX * IShape.PPC * getXScale(), pos.getY() - yMin * IShape.PPC * getYScale());
	}

	@Override
	protected void scaleSetPointsWithRatio(final List<IPoint> pts, final double prevWidth, final double prevHeight, final Position pos, final Rectangle2D bound) {
		scaleSetPoints(pts, prevWidth, prevHeight, pos, bound);
	}

	@Override
	protected void scaleSetPoints(final List<IPoint> pts, final double prevWidth, final double prevHeight, final Position pos, final Rectangle2D bound) {
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
	public IPoint getPosition() {
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
	public String getPlotEquation() {
		return equation.get();
	}

	@Override
	public void setPlotEquation(final String eq) {
		if(eq != null && !eq.isEmpty()) {
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
	public Color getDotFillingCol() {
		return super.getFillingCol();
	}

	@Override
	public void setDotFillingCol(final Color col) {
		setFillingCol(col);
	}

	@Override
	public DotStyle getDotStyle() {
		return dotStyle.get();
	}

	@Override
	public void setDotStyle(final DotStyle dotst) {
		if(dotst != null) {
			dotStyle.setValue(dotst);
		}
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
	public BooleanProperty polarProperty() {
		return polar;
	}

	@Override
	public StringProperty plotEquationProperty() {
		return equation;
	}

	@Override
	public DoubleProperty plotMinXProperty() {
		return minX;
	}

	@Override
	public DoubleProperty plotMaxXProperty() {
		return maxX;
	}

	@Override
	public IntegerProperty nbPlottedPointsProperty() {
		return nbPoints;
	}

	@Override
	public ObjectProperty<PlotStyle> plotStyleProperty() {
		return style;
	}

	@Override
	public ObjectProperty<DotStyle> dotStyleProperty() {
		return dotStyle;
	}

	@Override
	public DoubleProperty dotDiametreProperty() {
		return dotDiametre;
	}

	@Override
	public DoubleProperty xScaleProperty() {
		return xscale;
	}

	@Override
	public DoubleProperty yScaleProperty() {
		return yscale;
	}
}
