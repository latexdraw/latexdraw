/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models.impl;

import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.stream.IntStream;
import net.sf.latexdraw.models.GLibUtilities;
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
 */
class LPlot extends LPositionShape implements IPlot {
	private int nbPoints;
	private PlotStyle style;
	private PSFunctionParser parser;
	private DotStyle dotStyle;
	private double dotDiametre;
	private boolean polar;
	private double minX;
	private double maxX;
	private String equation;
	private double xscale;
	private double yscale;

	LPlot(final IPoint pt, final double xMin, final double xMax, final String equationPlot, final boolean polarCoord) {
		super(pt);

		if(!(GLibUtilities.isValidPoint(pt) && xMin < xMax && GLibUtilities.isValidCoordinate(xMin) && GLibUtilities.isValidCoordinate(xMax)))
			throw new IllegalArgumentException("Parameter not valid: " + xMin + " " + xMax + " " + GLibUtilities.isValidPoint(pt));

		nbPoints = 50;
		style = PlotStyle.CURVE;
		equation = equationPlot;
		parser = new PSFunctionParser(equationPlot);
		dotStyle = DotStyle.DOT;
		dotDiametre = PSTricksConstants.DEFAULT_ARROW_DOTSIZE_DIM * IShape.PPC + PSTricksConstants.DEFAULT_ARROW_DOTSIZE_NUM;
		polar = polarCoord;
		minX = xMin;
		maxX = xMax;
		xscale = 1.0;
		yscale = 1.0;
	}


	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IPlotProp) {
			IPlotProp plot = (IPlotProp) sh;
			style = plot.getPlotStyle();
			nbPoints = plot.getNbPlottedPoints();
			polar = plot.isPolar();
			dotStyle = plot.getDotStyle();
			dotDiametre = plot.getDiametre();
			minX = plot.getPlotMinX();
			maxX = plot.getPlotMaxX();
			xscale = plot.getXScale();
			yscale = plot.getYScale();
			setPlotEquation(plot.getPlotEquation());
		}else if(sh instanceof IDotProp) {
			IDotProp dot = (IDotProp) sh;
			dotStyle = dot.getDotStyle();
			dotDiametre = dot.getDiametre();
		}

		parser = new PSFunctionParser(equation);
	}


	@Override
	public void mirrorVertical(final IPoint origin) {
		final IPoint gc = getGravityCentre();
		if(GLibUtilities.isValidPoint(origin) && !origin.equals(gc, 0.0001)) {
			translate(0, gc.verticalSymmetry(origin).getY() - gc.getY());
		}
	}

	@Override
	public void mirrorHorizontal(final IPoint origin) {
		final IPoint gc = getGravityCentre();
		if(GLibUtilities.isValidPoint(origin) && !origin.equals(gc, 0.0001)) {
			translate(gc.horizontalSymmetry(origin).getX() - gc.getX(), 0);
		}
	}


	@Override
	public void setPlotStyle(final PlotStyle plotStyle) {
		if(plotStyle != null) {
			style = plotStyle;
		}
	}

	@Override
	public PlotStyle getPlotStyle() {
		return style;
	}

	@Override
	public void setNbPlottedPoints(final int nbPts) {
		if(nbPts > 1) {
			nbPoints = nbPts;
		}
	}

	@Override
	public boolean isShowPtsable() {
		return false;
	}

	@Override
	public boolean isThicknessable() {
		return style != PlotStyle.DOTS;
	}

	@Override
	public boolean isShadowable() {
		return style != PlotStyle.DOTS;
	}

	@Override
	public boolean isLineStylable() {
		return style != PlotStyle.DOTS;
	}

	@Override
	public boolean isInteriorStylable() {
		return style != PlotStyle.DOTS;
	}

	@Override
	public boolean isFillable() {
		return style != PlotStyle.DOTS || dotStyle.isFillable();
	}

	@Override
	public boolean isDbleBorderable() {
		return style != PlotStyle.DOTS;
	}

	@Override
	public double getPlottingStep() {
		return (maxX - minX) / (nbPoints - 1);
	}


	@Override
	public IPoint getTopLeftPoint() {
		final double step = getPlottingStep();
		final IPoint pos = getPosition();
		final double yMax = IntStream.range(0, nbPoints).mapToDouble(x -> getY(minX + x * step)).max().orElse(0.0);
		return ShapeFactory.createPoint(pos.getX() + minX * IShape.PPC * xscale, pos.getY() - yMax * IShape.PPC * yscale);
	}


	@Override
	public IPoint getBottomRightPoint() {
		final double step = getPlottingStep();
		final IPoint pos = getPosition();
		final double yMin = IntStream.range(0, nbPoints).mapToDouble(x -> getY(minX + x * step)).min().orElse(0.0);
		return ShapeFactory.createPoint(pos.getX() + maxX * IShape.PPC * xscale, pos.getY() - yMin * IShape.PPC * yscale);
	}


	@Override
	public IPoint getTopRightPoint() {
		final double step = getPlottingStep();
		final IPoint pos = getPosition();
		final double maxY = IntStream.range(0, nbPoints).mapToDouble(x -> getY(minX + x * step)).max().orElse(0.0);
		return ShapeFactory.createPoint(pos.getX() + maxX * IShape.PPC * xscale, pos.getY() - maxY * IShape.PPC * yscale);
	}


	@Override
	public IPoint getBottomLeftPoint() {
		final double step = getPlottingStep();
		final IPoint pos = getPosition();
		final double yMin = IntStream.range(0, nbPoints).mapToDouble(x -> getY(minX + x * step)).min().orElse(0.0);
		return ShapeFactory.createPoint(pos.getX() + minX * IShape.PPC * xscale, pos.getY() - yMin * IShape.PPC * yscale);
	}


	@Override
	protected void scaleSetPointsWithRatio(final List<IPoint> pts, final double prevWidth, final double prevHeight, final Position pos, final Rectangle2D bound) {
		scaleSetPoints(pts, prevWidth, prevHeight, pos, bound);
	}


	@Override
	protected void scaleSetPoints(final List<IPoint> pts, final double prevWidth, final double prevHeight, final Position pos, final Rectangle2D bound) {
		switch(pos) {
			case EAST:
				getPtAt(0).translate(bound.getWidth() - prevWidth, 0.0);
				break;
			case WEST:
				getPtAt(0).translate(prevWidth - bound.getWidth(), 0.0);
				break;
			case SOUTH:
				getPtAt(0).translate(0.0, bound.getHeight() - prevHeight);
				break;
			case NORTH:
				getPtAt(0).translate(0.0, prevHeight - bound.getHeight());
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
		return nbPoints;
	}

	@Override
	public double getY(final double x) {
		return parser.getY(x);
	}

	@Override
	public String getPlotEquation() {
		return equation;
	}

	@Override
	public void setPlotEquation(final String eq) {
		if(eq != null && !eq.isEmpty()) {
			equation = eq;
			parser = new PSFunctionParser(equation);
		}
	}

	@Override
	public double getPlotMinX() {
		return minX;
	}

	@Override
	public double getPlotMaxX() {
		return maxX;
	}

	@Override
	public void setPolar(final boolean pol) {
		polar = pol;
	}

	@Override
	public boolean isPolar() {
		return polar;
	}

	@Override
	public void setPlotMaxX(final double x) {
		if(GLibUtilities.isValidCoordinate(x) && x > minX) {
			maxX = x;
		}
	}

	@Override
	public void setPlotMinX(final double x) {
		if(GLibUtilities.isValidCoordinate(x) && x < maxX) {
			minX = x;
		}
	}

	@Override
	public double getDiametre() {
		return dotDiametre;
	}

	@Override
	public Color getDotFillingCol() {
		return super.getFillingCol();
	}

	@Override
	public DotStyle getDotStyle() {
		return dotStyle;
	}

	@Override
	public void setDiametre(final double diam) {
		if(diam > 0.0 && GLibUtilities.isValidCoordinate(diam)) {
			dotDiametre = diam;
		}
	}

	@Override
	public void setDotFillingCol(final Color col) {
		setFillingCol(col);
	}

	@Override
	public void setDotStyle(final DotStyle dotst) {
		if(dotst != null) {
			dotStyle = dotst;
		}
	}

	@Override
	public void setXScale(final double xscalePlot) {
		if(xscalePlot > 0 && GLibUtilities.isValidCoordinate(xscalePlot)) {
			xscale = xscalePlot;
		}
	}

	@Override
	public void setYScale(final double xScalePlot) {
		if(xScalePlot > 0 && GLibUtilities.isValidCoordinate(xScalePlot)) {
			yscale = xScalePlot;
		}
	}

	@Override
	public void setScale(final double scale) {
		setXScale(scale);
		setYScale(scale);
	}

	@Override
	public double getXScale() {
		return xscale;
	}

	@Override
	public double getYScale() {
		return yscale;
	}
}
