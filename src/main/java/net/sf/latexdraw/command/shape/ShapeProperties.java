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
package net.sf.latexdraw.command.shape;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.sf.latexdraw.model.api.property.ArcProp;
import net.sf.latexdraw.model.api.property.AxesProp;
import net.sf.latexdraw.model.api.property.ClosableProp;
import net.sf.latexdraw.model.api.property.DotProp;
import net.sf.latexdraw.model.api.property.FreeHandProp;
import net.sf.latexdraw.model.api.property.GridProp;
import net.sf.latexdraw.model.api.property.LineArcProp;
import net.sf.latexdraw.model.api.property.PlotProp;
import net.sf.latexdraw.model.api.property.Scalable;
import net.sf.latexdraw.model.api.property.IStdGridProp;
import net.sf.latexdraw.model.api.property.TextProp;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.Color;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.model.api.shape.FillingStyle;
import net.sf.latexdraw.model.api.shape.FreeHandStyle;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.LineStyle;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TextPosition;
import net.sf.latexdraw.model.api.shape.TicksStyle;
import org.jetbrains.annotations.NotNull;

/**
 * Defines shape properties.
 * @author Arnaud Blouin
 */
public final class ShapeProperties<T> {
	/** Plot style. **/
	public static final ShapeProperties<PlotStyle> PLOT_STYLE = new ShapeProperties<>(
		(v, g) -> g.setPlotStyle(v), (v, g) -> g.setPlotStyleList(v), g -> g.getPlotStyleList(), g -> g.isTypeOf(PlotProp.class), "plot.s.parameters");

	/** Polar or cartesian coordinates. **/
	public static final ShapeProperties<Boolean> PLOT_POLAR = new ShapeProperties<>(
		(v, g) -> g.setPolar(v), (v, g) -> g.setPlotPolarList(v), g -> g.getPlotPolarList(), g -> g.isTypeOf(PlotProp.class), "plot.s.parameters");

	/** The equation of plots. **/
	public static final ShapeProperties<String> PLOT_EQ = new ShapeProperties<>(
		(v, g) -> g.setPlotEquation(v), (v, g) -> g.setPlotEquationList(v), g -> g.getPlotEquationList(), g -> g.isTypeOf(PlotProp.class), "plot.s.parameters");

	/** Y-scale. **/
	public static final ShapeProperties<Double> Y_SCALE = new ShapeProperties<>(
		(v, g) -> g.setYScale(v), (v, g) -> g.setYScaleList(v), g -> g.getYScaleList(), g -> g.isTypeOf(Scalable.class), "shape.scale");

	/** X-scale. **/
	public static final ShapeProperties<Double> X_SCALE = new ShapeProperties<>(
		(v, g) -> g.setXScale(v), (v, g) -> g.setXScaleList(v), g -> g.getXScaleList(), g -> g.isTypeOf(Scalable.class), "shape.scale");

	/** The max-x of a plot. **/
	public static final ShapeProperties<Double> PLOT_MAX_X = new ShapeProperties<>(
		(v, g) -> g.setPlotMaxX(v), (v, g) -> g.setPlotMaxXList(v), g -> g.getPlotMaxXList(), g -> g.isTypeOf(PlotProp.class), "plot.s.parameters");

	/** The min-x of a plot. **/
	public static final ShapeProperties<Double> PLOT_MIN_X = new ShapeProperties<>(
		(v, g) -> g.setPlotMinX(v), (v, g) -> g.setPlotMinXList(v), g -> g.getPlotMinXList(), g -> g.isTypeOf(PlotProp.class), "plot.s.parameters");

	/** The number of plotted points. **/
	public static final ShapeProperties<Integer> PLOT_NB_PTS = new ShapeProperties<>(
		(v, g) -> g.setNbPlottedPoints(v), (v, g) -> g.setNbPlottedPointsList(v), g -> g.getNbPlottedPointsList(), g -> g.isTypeOf(PlotProp.class), "plot.s.parameters");

	/** Show/Hide the origin of the axes. */
	public static final ShapeProperties<Boolean> SHOW_POINTS = new ShapeProperties<>(
		(v, g) -> g.setShowPts(v), (v, g) -> g.setShowPointsList(v), g -> g.getShowPointsList(), g -> g.isShowPtsable(), "Actions.12"); //NON-NLS

	/** Show/Hide the origin of the axes. */
	public static final ShapeProperties<Boolean> AXES_SHOW_ORIGIN = new ShapeProperties<>(
		(v, g) -> g.setShowOrigin(v), (v, g) -> g.setAxesShowOriginList(v), g -> g.getAxesShowOriginList(), g -> g.isTypeOf(AxesProp.class), "Actions.13"); //NON-NLS

	/** The increment of the axes' labels. */
	public static final ShapeProperties<Point> AXES_LABELS_DIST = new ShapeProperties<>(
		(v, g) -> g.setDistLabels(v), (v, g) -> g.setAxesDistLabelsList(v), g -> g.getAxesDistLabelsList(), g -> g.isTypeOf(AxesProp.class), "Actions.13"); //NON-NLS

	/** The increment of the axes' labels. */
	public static final ShapeProperties<Point> AXES_LABELS_INCR = new ShapeProperties<>(
		(v, g) -> g.setIncrement(v), (v, g) -> g.setAxesIncrementsList(v), g -> g.getAxesIncrementsList(), g -> g.isTypeOf(AxesProp.class), "Actions.13"); //NON-NLS

	/** How the labels of axes are displayed. */
	public static final ShapeProperties<PlottingStyle> AXES_LABELS_SHOW = new ShapeProperties<>(
		(v, g) -> g.setLabelsDisplayed(v), (v, g) -> g.setAxesLabelsDisplayedList(v), g -> g.getAxesLabelsDisplayedList(), g -> g.isTypeOf(AxesProp.class), "Actions.13"); //NON-NLS

	/** How the ticks of axes are displayed. */
	public static final ShapeProperties<PlottingStyle> AXES_TICKS_SHOW = new ShapeProperties<>(
		(v, g) -> g.setTicksDisplayed(v), (v, g) -> g.setAxesTicksDisplayedList(v), g -> g.getAxesTicksDisplayedList(), g -> g.isTypeOf(AxesProp.class), "Actions.13"); //NON-NLS

	/** The width of the sub-grids. */
	public static final ShapeProperties<Double> GRID_SUBGRID_WIDTH = new ShapeProperties<>(
		(v, g) -> g.setSubGridWidth(v), (v, g) -> g.setSubGridWidthList(v), g -> g.getSubGridWidthList(), g -> g.isTypeOf(GridProp.class), "Actions.14"); //NON-NLS

	/** Defines whether bezier curves and co are closed. */
	public static final ShapeProperties<Boolean> CLOSABLE_CLOSE = new ShapeProperties<>(
		(v, g) -> g.setOpened(v), (v, g) -> g.setOpenList(v), g -> g.getOpenList(), g -> g.isTypeOf(ClosableProp.class), "shape.closed.opened"); //NON-NLS

	/** The interval between the points of free hand shapes. */
	public static final ShapeProperties<Integer> FREEHAND_INTERVAL = new ShapeProperties<>(
		(v, g) -> g.setInterval(v), (v, g) -> g.setFreeHandIntervalList(v), g -> g.getFreeHandIntervalList(), g -> g.isTypeOf(FreeHandProp.class), "Actions.15"); //NON-NLS

	/** The division the sub-lines of grids. */
	public static final ShapeProperties<Integer> GRID_SUBGRID_DIV = new ShapeProperties<>(
		(v, g) -> g.setSubGridDiv(v), (v, g) -> g.setSubGridDivList(v), g -> g.getSubGridDivList(), g -> g.isTypeOf(GridProp.class), "Actions.14"); //NON-NLS

	/** The number of dots composing the sub-lines of grids. */
	public static final ShapeProperties<Integer> GRID_SUBGRID_DOTS = new ShapeProperties<>(
		(v, g) -> g.setSubGridDots(v), (v, g) -> g.setSubGridDotsList(v), g -> g.getSubGridDotsList(), g -> g.isTypeOf(GridProp.class), "Actions.14"); //NON-NLS

	/** The number of dots composing the main lines of grids. */
	public static final ShapeProperties<Integer> GRID_DOTS = new ShapeProperties<>(
		(v, g) -> g.setGridDots(v), (v, g) -> g.setGridDotsList(v), g -> g.getGridDotsList(), g -> g.isTypeOf(GridProp.class), "Actions.14"); //NON-NLS

	/** The width of the grids. */
	public static final ShapeProperties<Double> GRID_WIDTH = new ShapeProperties<>(
		(v, g) -> g.setGridWidth(v), (v, g) -> g.setGridWidthList(v), g -> g.getGridWidthList(), g -> g.isTypeOf(GridProp.class), "Actions.14"); //NON-NLS

	/** The style of the ticks of axes. */
	public static final ShapeProperties<TicksStyle> AXES_TICKS_STYLE = new ShapeProperties<>(
		(v, g) -> g.setTicksStyle(v), (v, g) -> g.setAxesTicksStyleList(v), g -> g.getAxesTicksStyleList(), g -> g.isTypeOf(AxesProp.class), "Actions.13"); //NON-NLS

	/** The style of axes. */
	public static final ShapeProperties<FreeHandStyle> FREEHAND_STYLE = new ShapeProperties<>(
		(v, g) -> g.setType(v), (v, g) -> g.setFreeHandTypeList(v), g -> g.getFreeHandTypeList(), g -> g.isTypeOf(FreeHandProp.class), "Actions.15"); //NON-NLS

	/** The style of axes. */
	public static final ShapeProperties<AxesStyle> AXES_STYLE = new ShapeProperties<>((v, g) -> g.setAxesStyle(v), (v, g) -> g.setAxesStyleList(v),
		g -> g.getAxesStyleList(), g -> g.isTypeOf(AxesProp.class), "Actions.13"); //NON-NLS
	/** The X-coordinate of the grid's labels. */
	public static final ShapeProperties<Boolean> GRID_LABEL_POSITION_X = new ShapeProperties<>(
		(v, g) -> g.setYLabelWest(v), (v, g) -> g.setGridYLabelWestList(v), g -> g.getGridYLabelWestList(), g -> g.isTypeOf(IStdGridProp.class), "Actions.14"); //NON-NLS

	/** The Y-coordinate of the grid's labels. */
	public static final ShapeProperties<Boolean> GRID_LABEL_POSITION_Y = new ShapeProperties<>(
		(v, g) -> g.setXLabelSouth(v), (v, g) -> g.setGridXLabelSouthList(v), g -> g.getGridXLabelSouthList(), g -> g.isTypeOf(IStdGridProp.class), "Actions.14"); //NON-NLS

	/** The size of the labels of grids. */
	public static final ShapeProperties<Integer> GRID_SIZE_LABEL = new ShapeProperties<>(
		(v, g) -> g.setLabelsSize(v), (v, g) -> g.setGridLabelSizeList(v), g -> g.getGridLabelSizeList(), g -> g.isTypeOf(IStdGridProp.class), "Actions.14"); //NON-NLS

	/** The t bar num of arrows. */
	public static final ShapeProperties<Double> ARROW_T_BAR_SIZE_DIM = new ShapeProperties<>(
		(v, g) -> g.setTBarSizeDim(v), (v, g) -> g.setTBarSizeDimList(v), g -> g.getTBarSizeDimList(), g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	/** The t bar num of arrows. */
	public static final ShapeProperties<Double> ARROW_T_BAR_SIZE_NUM = new ShapeProperties<>(
		(v, g) -> g.setTBarSizeNum(v), (v, g) -> g.setTBarSizeNumList(v), g -> g.getTBarSizeNumList(), g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	/** The dot size dim of arrows. */
	public static final ShapeProperties<Double> ARROW_DOT_SIZE_NUM = new ShapeProperties<>(
		(v, g) -> g.setDotSizeNum(v), (v, g) -> g.setDotSizeNumList(v), g -> g.getDotSizeNumList(), g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	/** The dot size dim of arrows. */
	public static final ShapeProperties<Double> ARROW_DOT_SIZE_DIM = new ShapeProperties<>(
		(v, g) -> g.setDotSizeDim(v), (v, g) -> g.setDotSizeDimList(v), g -> g.getDotSizeDimList(), g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	/** The bracket num of arrows. */
	public static final ShapeProperties<Double> ARROW_BRACKET_NUM = new ShapeProperties<>(
		(v, g) -> g.setBracketNum(v), (v, g) -> g.setBracketNumList(v), g -> g.getBracketNumList(), g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	/** The round bracket num of arrows. */
	public static final ShapeProperties<Double> ARROW_R_BRACKET_NUM = new ShapeProperties<>(
		(v, g) -> g.setRBracketNum(v), (v, g) -> g.setRBracketNumList(v), g -> g.getRBracketNumList(), g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS
	/** The size num of arrows. */
	public static final ShapeProperties<Double> ARROW_SIZE_NUM = new ShapeProperties<>(
		(v, g) -> g.setArrowSizeNum(v), (v, g) -> g.setArrowSizeNumList(v), g -> g.getArrowSizeNumList(), g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	/** The size dim of arrows. */
	public static final ShapeProperties<Double> ARROW_SIZE_DIM = new ShapeProperties<>(
		(v, g) -> g.setArrowSizeDim(v), (v, g) -> g.setArrowSizeDimList(v), g -> g.getArrowSizeDimList(), g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	public static final ShapeProperties<Double> ARROW_LENGTH = new ShapeProperties<>(
		(v, g) -> g.setArrowLength(v), (v, g) -> g.setArrowLengthList(v), g -> g.getArrowLengthList(), g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	/** The inset of arrows. */
	public static final ShapeProperties<Double> ARROW_INSET = new ShapeProperties<>(
		(v, g) -> g.setArrowInset(v), (v, g) -> g.setArrowInsetList(v), g -> g.getArrowInsetList(), g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	/** Modification of the starting position of grids. */
	public static final ShapeProperties<Point> GRID_END = new ShapeProperties<>(
		(v, g) -> g.setGridEnd(v.getX(), v.getY()), (v, g) -> g.setGridEndList(v), g -> g.getGridEndList(), g -> g.isTypeOf(IStdGridProp.class), "Actions.14"); //NON-NLS

	/** Modification of the starting position of grids. */
	public static final ShapeProperties<Point> GRID_ORIGIN = new ShapeProperties<>(
		(v, g) -> g.setOrigin(v.getX(), v.getY()), (v, g) -> g.setGridOriginList(v), g -> g.getGridOriginList(), g -> g.isTypeOf(IStdGridProp.class), "Actions.14"); //NON-NLS

	/** Modification of the starting position of grids. */
	public static final ShapeProperties<Point> GRID_START = new ShapeProperties<>(
		(v, g) -> g.setGridStart(v.getX(), v.getY()), (v, g) -> g.setGridStartList(v), g -> g.getGridStartList(), g -> g.isTypeOf(IStdGridProp.class), "Actions.14"); //NON-NLS

	/** Modification of the start angle of arcs. */
	public static final ShapeProperties<Double> ARC_START_ANGLE = new ShapeProperties<>(
		(v, g) -> g.setAngleStart(v), (v, g) -> g.setAngleStartList(v), g -> g.getAngleStartList(), g -> g.isTypeOf(ArcProp.class), "Actions.17"); //NON-NLS
	/** Modification of the end angle of arcs. */

	public static final ShapeProperties<Double> ARC_END_ANGLE = new ShapeProperties<>(
		(v, g) -> g.setAngleEnd(v), (v, g) -> g.setAngleEndList(v), g -> g.getAngleEndList(), g -> g.isTypeOf(ArcProp.class), "Actions.17"); //NON-NLS

	/** Modification of the style of arcs. */
	public static final ShapeProperties<ArcStyle> ARC_STYLE = new ShapeProperties<>(
		(v, g) -> g.setArcStyle(v), (v, g) -> g.setArcStyleList(v), g -> g.getArcStyleList(), g -> g.isTypeOf(ArcProp.class), "Actions.17"); //NON-NLS

	/** Defines if the shape has a second arrow. */
	public static final ShapeProperties<ArrowStyle> ARROW2_STYLE = new ShapeProperties<>(
		(v, g) -> g.setArrowStyle(v, -1), (v, g) -> g.setArrowStyleList(v, -1), g -> g.getArrowStyleList(-1),
		g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	/** Defines if the shape has a first arrow. */
	public static final ShapeProperties<ArrowStyle> ARROW1_STYLE = new ShapeProperties<>(
		(v, g) -> g.setArrowStyle(v, 0), (v, g) -> g.setArrowStyleList(v, 0), g -> g.getArrowStyleList(0),
		g -> g.isTypeOf(ArrowableSingleShape.class), "Actions.16"); //NON-NLS

	/** Modification of the position of texts. */
	public static final ShapeProperties<TextPosition> TEXT_POSITION = new ShapeProperties<>(
		(v, g) -> g.setTextPosition(v), (v, g) -> g.setTextPositionList(v), g -> g.getTextPositionList(), g -> g.isTypeOf(TextProp.class), "Actions.18"); //NON-NLS

	/** Modification of the text. */
	public static final ShapeProperties<String> TEXT = new ShapeProperties<>(
		(v, g) -> g.setText(v), (v, g) -> g.setTextList(v), g -> g.getTextList(), g -> g.isTypeOf(TextProp.class), "Actions.18"); //NON-NLS

	/** Modification of the hatchings angle of shapes. */
	public static final ShapeProperties<Double> HATCHINGS_ANGLE = new ShapeProperties<>(
		(v, g) -> g.setHatchingsAngle(v), (v, g) -> g.setHatchingsAngleList(v), g -> g.getHatchingsAngleList(), g -> g.isInteriorStylable(), "Actions.19"); //NON-NLS

	/** Modification of the hatchings width a shape. */
	public static final ShapeProperties<Double> HATCHINGS_WIDTH = new ShapeProperties<>(
		(v, g) -> g.setHatchingsWidth(v), (v, g) -> g.setHatchingsWidthList(v), g -> g.getHatchingsWidthList(), g -> g.isInteriorStylable(), "Actions.19"); //NON-NLS

	/** Modification of the hatching spacing a shape. */
	public static final ShapeProperties<Double> HATCHINGS_SEP = new ShapeProperties<>(
		(v, g) -> g.setHatchingsSep(v), (v, g) -> g.setHatchingsSepList(v), g -> g.getHatchingsSepList(), g -> g.isInteriorStylable(), "Actions.19"); //NON-NLS

	/** Modification of the gradient angle a shape. */
	public static final ShapeProperties<Double> GRAD_ANGLE = new ShapeProperties<>(
		(v, g) -> g.setGradAngle(v), (v, g) -> g.setGradAngleList(v), g -> g.getGradAngleList(), g -> g.isInteriorStylable(), "Actions.20"); //NON-NLS

	/** Modification of the middle point of the gradient a shape. */
	public static final ShapeProperties<Double> GRAD_MID_POINT = new ShapeProperties<>(
		(v, g) -> g.setGradMidPt(v), (v, g) -> g.setGradMidPtList(v), g -> g.getGradMidPtList(), g -> g.isInteriorStylable(), "Actions.20"); //NON-NLS

	/** Modification of the round corner value of a shape. */
	public static final ShapeProperties<Double> ROUND_CORNER_VALUE = new ShapeProperties<>(
		(v, g) -> g.setLineArc(v), (v, g) -> g.setLineArcList(v), g -> g.getLineArcList(), g -> g.isTypeOf(LineArcProp.class), "Actions.21"); //NON-NLS

	/** Modification of the colour of the labels of a grid. */
	public static final ShapeProperties<Color> GRID_SUBGRID_COLOUR = new ShapeProperties<>(
		(v, g) -> g.setSubGridColour(v), (v, g) -> g.setSubGridColourList(v), g -> g.getSubGridColourList(), g -> g.isTypeOf(GridProp.class), "Actions.14"); //NON-NLS

	/** Modification of the colour of the labels of a grid. */
	public static final ShapeProperties<Color> GRID_LABELS_COLOUR = new ShapeProperties<>(
		(v, g) -> g.setGridLabelsColour(v), (v, g) -> g.setGridLabelsColourList(v), g -> g.getGridLabelsColourList(), g -> g.isTypeOf(GridProp.class), "Actions.14"); //NON-NLS

	/** Modification of the colour of the filling of a shape. */
	public static final ShapeProperties<Color> COLOUR_FILLING = new ShapeProperties<>(
		(v, g) -> g.setFillingCol(v), (v, g) -> g.setFillingColList(v), g -> g.getFillingColList(), g -> g.isFillable(), "Actions.21"); //NON-NLS

	/** Modification of the colour of the borders of a shape. */
	public static final ShapeProperties<Color> COLOUR_LINE = new ShapeProperties<>(
		(v, g) -> g.setLineColour(v), (v, g) -> g.setLineColourList(v), g -> g.getLineColourList(), g -> true, "Actions.23"); //NON-NLS

	/** Modification of the colour of the hatchings of a shape. */
	public static final ShapeProperties<Color> COLOUR_HATCHINGS = new ShapeProperties<>(
		(v, g) -> g.setHatchingsCol(v), (v, g) -> g.setHatchingsColList(v), g -> g.getHatchingsColList(), g -> g.isInteriorStylable(), "Actions.19"); //NON-NLS

	/** Defines if a shape must have double borders. */
	public static final ShapeProperties<Boolean> DBLE_BORDERS = new ShapeProperties<>(
		(v, g) -> g.setHasDbleBord(v), (v, g) -> g.setHasDbleBordList(v), g -> g.hasDbleBordList(), g -> g.isDbleBorderable(), "Actions.24"); //NON-NLS

	/** Modification of the size of the double borders of a shape. */
	public static final ShapeProperties<Double> DBLE_BORDERS_SIZE = new ShapeProperties<>(
		(v, g) -> g.setDbleBordSep(v), (v, g) -> g.setDbleBordSepList(v), g -> g.getDbleBordSepList(), g -> g.isDbleBorderable(), "Actions.24"); //NON-NLS

	/** Modification of the colour of the double borders of a shape. */
	public static final ShapeProperties<Color> COLOUR_DBLE_BORD = new ShapeProperties<>(
		(v, g) -> g.setDbleBordCol(v), (v, g) -> g.setDbleBordColList(v), g -> g.getDbleBordColList(), g -> g.isDbleBorderable(), "Actions.24"); //NON-NLS

	/** Defines if a shape must have a shadow. */
	public static final ShapeProperties<Boolean> SHADOW = new ShapeProperties<>(
		(v, g) -> g.setHasShadow(v), (v, g) -> g.setHasShadowList(v), g -> g.hasShadowList(), g -> g.isShadowable(), "Actions.25"); //NON-NLS

	/** Modification of the size of the shadow of a shape. */
	public static final ShapeProperties<Double> SHADOW_SIZE = new ShapeProperties<>(
		(v, g) -> g.setShadowSize(v), (v, g) -> g.setShadowSizeList(v), g -> g.getShadowSizeList(), g -> g.isShadowable(), "Actions.25"); //NON-NLS

	/** Modification of the angle of the shadow of a shape. */
	public static final ShapeProperties<Double> SHADOW_ANGLE = new ShapeProperties<>(
		(v, g) -> g.setShadowAngle(v), (v, g) -> g.setShadowAngleList(v), g -> g.getShadowAngleList(), g -> g.isShadowable(), "Actions.25"); //NON-NLS

	/** Modification of colour of the shadow of a shape. */
	public static final ShapeProperties<Color> SHADOW_COLOUR = new ShapeProperties<>(
		(v, g) -> g.setShadowCol(v), (v, g) -> g.setShadowColList(v), g -> g.getShadowColList(), g -> g.isShadowable(), "Actions.25"); //NON-NLS

	/** Modification of the colour of the start gradient of a shape. */
	public static final ShapeProperties<Color> COLOUR_GRADIENT_START = new ShapeProperties<>(
		(v, g) -> g.setGradColStart(v), (v, g) -> g.setGradColStartList(v), g -> g.getGradColStartList(), g -> g.isLineStylable(), "Actions.20"); //NON-NLS

	/** Modification of the colour of the end gradient of a shape. */
	public static final ShapeProperties<Color> COLOUR_GRADIENT_END = new ShapeProperties<>(
		(v, g) -> g.setGradColEnd(v), (v, g) -> g.setGradColEndList(v), g -> g.getGradColEndList(), g -> g.isLineStylable(), "Actions.20"); //NON-NLS

	/** Modification of the thickness of the borders of a shape. */
	public static final ShapeProperties<Double> LINE_THICKNESS = new ShapeProperties<>(
		(v, g) -> g.setThickness(v), (v, g) -> g.setThicknessList(v), g -> g.getThicknessList(), g -> g.isThicknessable(), "Actions.26"); //NON-NLS

	/** Modification of the filling style of a shape. */
	public static final ShapeProperties<FillingStyle> FILLING_STYLE = new ShapeProperties<>(
		(v, g) -> g.setFillingStyle(v), (v, g) -> g.setFillingStyleList(v), g -> g.getFillingStyleList(), g -> g.isInteriorStylable(), "Actions.22"); //NON-NLS

	/** Modification of the border position of a shape. */
	public static final ShapeProperties<BorderPos> BORDER_POS = new ShapeProperties<>(
		(v, g) -> g.setBordersPosition(v), (v, g) -> g.setBordersPositionList(v), g -> g.getBordersPositionList(), g -> g.isBordersMovable(), "Actions.27"); //NON-NLS

	/** Modification of the line style of a shape. */
	public static final ShapeProperties<LineStyle> LINE_STYLE = new ShapeProperties<>(
		(v, g) -> g.setLineStyle(v), (v, g) -> g.setLineStyleList(v), g -> g.getLineStyleList(), g -> g.isLineStylable(), "Actions.28"); //NON-NLS

	/** Modification of the filling colour of a dot. */
	public static final ShapeProperties<Color> DOT_FILLING_COL = new ShapeProperties<>(
		(v, g) -> g.setDotFillingCol(v), (v, g) -> g.setDotFillingColList(v), g -> g.getDotFillingColList(), g -> g.isTypeOf(DotProp.class), "Actions.29"); //NON-NLS

	/** Modification of the style of a dot. */
	public static final ShapeProperties<DotStyle> DOT_STYLE = new ShapeProperties<>(
		(v, g) -> g.setDotStyle(v), (v, g) -> g.setDotStyleList(v), g -> g.getDotStyleList(), g -> g.isTypeOf(DotProp.class), "Actions.29"); //NON-NLS

	/** Modification of the size of dots. */
	public static final ShapeProperties<Double> DOT_SIZE = new ShapeProperties<>(
		(v, g) -> g.setDiametre(v), (v, g) -> g.setDotSizeList(v), g -> g.getDotSizeList(), g -> g.isTypeOf(DotProp.class), "Actions.29"); //NON-NLS;

	private final @NotNull BiConsumer<T, Group> setValue;
	private final @NotNull BiConsumer<List<Optional<T>>, Group> setListValue;
	private final @NotNull Function<Group, List<Optional<T>>> getListValue;
	private final @NotNull Predicate<Group> acceptPred;
	private final @NotNull String labelName;


	private ShapeProperties(final @NotNull BiConsumer<T, Group> setValue, final @NotNull BiConsumer<List<Optional<T>>, Group> setListValue,
							final @NotNull Function<Group, @NotNull List<Optional<T>>> getListValue, final @NotNull Predicate<Group> accept,
							final @NotNull String labelName) {
		super();
		this.setListValue = setListValue;
		this.setValue = setValue;
		this.labelName = labelName;
		this.getListValue = getListValue;
		acceptPred = accept;
	}

	/**
	 * Sets the given value of the property to the given group.
	 * @param group The group to modify.
	 * @param value The new value of the property to set.
	 */
	public void setPropertyValue(final @NotNull Group group, final @NotNull T value) {
		if(group != null) {
			setValue.accept(value, group);
		}
	}

	/**
	 * Sets the given values of the property to the given group. The size of the list
	 * must equals the number of shapes of the group. If a shape of the group must not be set,
	 * its corresponding value in the list must be null.
	 * @param group The group to modify.
	 * @param values The set of new values of the property to set.
	 */
	public void setPropertyValueList(final @NotNull Group group, final @NotNull List<Optional<T>> values) {
		if(group != null) {
			setListValue.accept(values, group);
		}
	}

	/**
	 * @param group The group to explore.
	 * @return The list of property values of the shapes of the given group.
	 */
	public @NotNull List<Optional<T>> getPropertyValues(final @NotNull Group group) {
		return group == null ? Collections.emptyList() : getListValue.apply(group);
	}

	/**
	 * @return The title of the properties.
	 */
	public @NotNull String getMessage(final @NotNull ResourceBundle bundle) {
		return bundle.getString(labelName);
	}

	public boolean accept(final Group g) {
		return g != null && acceptPred.test(g);
	}
}
