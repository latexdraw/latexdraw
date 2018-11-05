package net.sf.latexdraw.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import net.sf.latexdraw.command.shape.ModifyShapeProperty;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.ArcProp;
import net.sf.latexdraw.model.api.property.Arrowable;
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
import net.sf.latexdraw.model.api.shape.ArrowableShape;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.CircleArc;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Rhombus;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.Triangle;
import net.sf.latexdraw.model.api.shape.LineStyle;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TextPosition;
import net.sf.latexdraw.model.api.shape.TicksStyle;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class TestModifyShapeProperty extends TestUndoableCommand<ModifyShapeProperty<Object>, List<Optional<Object>>> {

	@Parameterized.Parameters(name = "{0} with {1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{ShapeProperties.PLOT_STYLE, PlotStyle.DOTS, (Function<Group, List<Optional<PlotStyle>>>) g -> g.getPlotStyleList(), (Function<Shape, PlotStyle>) s -> ((PlotProp) s).getPlotStyle()},
			{ShapeProperties.PLOT_POLAR, true, (Function<Group, List<Optional<Boolean>>>) g -> g.getPlotPolarList(), (Function<Shape, Boolean>) s -> ((PlotProp) s).isPolar()},
			{ShapeProperties.PLOT_EQ, "x 2 mul", (Function<Group, List<Optional<String>>>) g -> g.getPlotEquationList(), (Function<Shape, String>) s -> ((PlotProp) s).getPlotEquation()},
			{ShapeProperties.Y_SCALE, 2d, (Function<Group, List<Optional<Double>>>) g -> g.getYScaleList(), (Function<Shape, Double>) s -> ((Scalable) s).getYScale()},
			{ShapeProperties.X_SCALE, 2d, (Function<Group, List<Optional<Double>>>) g -> g.getXScaleList(), (Function<Shape, Double>) s -> ((Scalable) s).getXScale()},
			{ShapeProperties.PLOT_MAX_X, 20d, (Function<Group, List<Optional<Double>>>) g -> g.getPlotMaxXList(), (Function<Shape, Double>) s -> ((PlotProp) s).getPlotMaxX()},
			{ShapeProperties.PLOT_MIN_X, -10d, (Function<Group, List<Optional<Double>>>) g -> g.getPlotMinXList(), (Function<Shape, Double>) s -> ((PlotProp) s).getPlotMinX()},
			{ShapeProperties.PLOT_NB_PTS, 123, (Function<Group, List<Optional<Integer>>>) g -> g.getNbPlottedPointsList(), (Function<Shape, Integer>) s -> ((PlotProp) s).getNbPlottedPoints()},
			{ShapeProperties.SHOW_POINTS, true, (Function<Group, List<Optional<Boolean>>>) g -> g.getShowPointsList(), (Function<Shape, Boolean>) s -> s.isShowPts()},
			{ShapeProperties.AXES_SHOW_ORIGIN, false, (Function<Group, List<Optional<Boolean>>>) g -> g.getAxesShowOriginList(), (Function<Shape, Boolean>) s -> ((AxesProp) s).isShowOrigin()},
			{ShapeProperties.AXES_LABELS_DIST, ShapeFactory.INST.createPoint(1d, 2d), (Function<Group, List<Optional<Point>>>) g -> g.getAxesDistLabelsList(), (Function<Shape, Point>) s -> ((AxesProp) s).getDistLabels()},
			{ShapeProperties.AXES_LABELS_INCR, ShapeFactory.INST.createPoint(1d, 2d), (Function<Group, List<Optional<Point>>>) g -> g.getAxesIncrementsList(), (Function<Shape, Point>) s -> ((AxesProp) s).getIncrement()},
			{ShapeProperties.AXES_LABELS_SHOW, PlottingStyle.X, (Function<Group, List<Optional<PlottingStyle>>>) g -> g.getAxesLabelsDisplayedList(), (Function<Shape, PlottingStyle>) s -> ((AxesProp) s).getLabelsDisplayed()},
			{ShapeProperties.AXES_TICKS_SHOW, PlottingStyle.Y, (Function<Group, List<Optional<PlottingStyle>>>) g -> g.getAxesTicksDisplayedList(), (Function<Shape, PlottingStyle>) s -> ((AxesProp) s).getTicksDisplayed()},
			{ShapeProperties.GRID_SUBGRID_WIDTH, 21d, (Function<Group, List<Optional<Double>>>) g -> g.getSubGridWidthList(), (Function<Shape, Double>) s -> ((GridProp) s).getSubGridWidth()},
			{ShapeProperties.CLOSABLE_CLOSE, false, (Function<Group, List<Optional<Boolean>>>) g -> g.getOpenList(), (Function<Shape, Boolean>) s -> ((ClosableProp) s).isOpened()},
			{ShapeProperties.FREEHAND_INTERVAL, 7, (Function<Group, List<Optional<Integer>>>) g -> g.getFreeHandIntervalList(), (Function<Shape, Integer>) s -> ((FreeHandProp) s).getInterval()},
			{ShapeProperties.GRID_SUBGRID_DIV, 5, (Function<Group, List<Optional<Integer>>>) g -> g.getSubGridDivList(), (Function<Shape, Integer>) s -> ((GridProp) s).getSubGridDiv()},
			{ShapeProperties.GRID_SUBGRID_DOTS, 3, (Function<Group, List<Optional<Integer>>>) g -> g.getSubGridDotsList(), (Function<Shape, Integer>) s -> ((GridProp) s).getSubGridDots()},
			{ShapeProperties.GRID_DOTS, 7, (Function<Group, List<Optional<Integer>>>) g -> g.getGridDotsList(), (Function<Shape, Integer>) s -> ((GridProp) s).getGridDots()},
			{ShapeProperties.GRID_WIDTH, 7d, (Function<Group, List<Optional<Double>>>) g -> g.getGridWidthList(), (Function<Shape, Double>) s -> ((GridProp) s).getGridWidth()},
			{ShapeProperties.AXES_TICKS_STYLE, TicksStyle.FULL, (Function<Group, List<Optional<TicksStyle>>>) g -> g.getAxesTicksStyleList(), (Function<Shape, TicksStyle>) s -> ((AxesProp) s).getTicksStyle()},
			{ShapeProperties.FREEHAND_STYLE, FreeHandStyle.LINES, (Function<Group, List<Optional<FreeHandStyle>>>) g -> g.getFreeHandTypeList(), (Function<Shape, FreeHandStyle>) s -> ((FreeHandProp) s).getType()},
			{ShapeProperties.AXES_STYLE, AxesStyle.FRAME, (Function<Group, List<Optional<AxesStyle>>>) g -> g.getAxesStyleList(), (Function<Shape, AxesStyle>) s -> ((AxesProp) s).getAxesStyle()},
			{ShapeProperties.GRID_LABEL_POSITION_X, false, (Function<Group, List<Optional<Boolean>>>) g -> g.getGridYLabelWestList(), (Function<Shape, Boolean>) s -> ((GridProp) s).isYLabelWest()},
			{ShapeProperties.GRID_LABEL_POSITION_Y, false, (Function<Group, List<Optional<Boolean>>>) g -> g.getGridXLabelSouthList(), (Function<Shape, Boolean>) s -> ((GridProp) s).isXLabelSouth()},
			{ShapeProperties.GRID_SIZE_LABEL, 11, (Function<Group, List<Optional<Integer>>>) g -> g.getGridLabelSizeList(), (Function<Shape, Integer>) s -> ((IStdGridProp) s).getLabelsSize()},
			{ShapeProperties.ARROW_T_BAR_SIZE_DIM, 0.55, (Function<Group, List<Optional<Double>>>) g -> g.getTBarSizeDimList(), (Function<Shape, Double>) s -> ((Arrowable) s).getTBarSizeDim()},
			{ShapeProperties.ARROW_T_BAR_SIZE_NUM, 0.55, (Function<Group, List<Optional<Double>>>) g -> g.getTBarSizeNumList(), (Function<Shape, Double>) s -> ((Arrowable) s).getTBarSizeNum()},
			{ShapeProperties.ARROW_DOT_SIZE_NUM, 0.55, (Function<Group, List<Optional<Double>>>) g -> g.getDotSizeNumList(), (Function<Shape, Double>) s -> ((Arrowable) s).getDotSizeNum()},
			{ShapeProperties.ARROW_DOT_SIZE_DIM, 0.55, (Function<Group, List<Optional<Double>>>) g -> g.getDotSizeDimList(), (Function<Shape, Double>) s -> ((Arrowable) s).getDotSizeDim()},
			{ShapeProperties.ARROW_BRACKET_NUM, 0.55, (Function<Group, List<Optional<Double>>>) g -> g.getBracketNumList(), (Function<Shape, Double>) s -> ((Arrowable) s).getBracketNum()},
			{ShapeProperties.ARROW_R_BRACKET_NUM, 0.55, (Function<Group, List<Optional<Double>>>) g -> g.getRBracketNumList(), (Function<Shape, Double>) s -> ((Arrowable) s).getRBracketNum()},
			{ShapeProperties.ARROW_SIZE_NUM, 0.55, (Function<Group, List<Optional<Double>>>) g -> g.getArrowSizeNumList(), (Function<Shape, Double>) s -> ((Arrowable) s).getArrowSizeNum()},
			{ShapeProperties.ARROW_SIZE_DIM, 0.55, (Function<Group, List<Optional<Double>>>) g -> g.getArrowSizeDimList(), (Function<Shape, Double>) s -> ((Arrowable) s).getArrowSizeDim()},
			{ShapeProperties.ARROW_LENGTH, 0.55, (Function<Group, List<Optional<Double>>>) g -> g.getArrowLengthList(), (Function<Shape, Double>) s -> ((Arrowable) s).getArrowLength()},
			{ShapeProperties.ARROW_INSET, 0.22, (Function<Group, List<Optional<Double>>>) g -> g.getArrowInsetList(), (Function<Shape, Double>) s -> ((Arrowable) s).getArrowInset()},
			{ShapeProperties.GRID_END, ShapeFactory.INST.createPoint(10d, 20d), (Function<Group, List<Optional<Point>>>) g -> g.getGridEndList(), (Function<Shape, Point>) s -> ((IStdGridProp) s).getGridEnd()},
			{ShapeProperties.GRID_ORIGIN, ShapeFactory.INST.createPoint(10d, 20d), (Function<Group, List<Optional<Point>>>) g -> g.getGridOriginList(), (Function<Shape, Point>) s -> ShapeFactory.INST.createPoint(((IStdGridProp) s).getOriginX(), ((IStdGridProp) s).getOriginY())},
			{ShapeProperties.GRID_START, ShapeFactory.INST.createPoint(-10d, -20d), (Function<Group, List<Optional<Point>>>) g -> g.getGridStartList(), (Function<Shape, Point>) s -> ((IStdGridProp) s).getGridStart()},
			{ShapeProperties.ARC_START_ANGLE, 11d, (Function<Group, List<Optional<Double>>>) g -> g.getAngleStartList(), (Function<Shape, Double>) s -> ((ArcProp) s).getAngleStart()},
			{ShapeProperties.ARC_END_ANGLE, 11d, (Function<Group, List<Optional<Double>>>) g -> g.getAngleEndList(), (Function<Shape, Double>) s -> ((ArcProp) s).getAngleEnd()},
			{ShapeProperties.ARC_STYLE, ArcStyle.WEDGE, (Function<Group, List<Optional<ArcStyle>>>) g -> g.getArcStyleList(), (Function<Shape, ArcStyle>) s -> ((ArcProp) s).getArcStyle()},
			{ShapeProperties.ARROW2_STYLE, ArrowStyle.CIRCLE_END, (Function<Group, List<Optional<ArrowStyle>>>) g -> g.getArrowStyleList(1), (Function<Shape, ArrowStyle>) s -> ((ArrowableShape) s).getArrowStyle(1)},
			{ShapeProperties.ARROW1_STYLE, ArrowStyle.CIRCLE_END, (Function<Group, List<Optional<ArrowStyle>>>) g -> g.getArrowStyleList(0), (Function<Shape, ArrowStyle>) s -> ((ArrowableShape) s).getArrowStyle(0)},
			{ShapeProperties.TEXT_POSITION, TextPosition.TOP_RIGHT, (Function<Group, List<Optional<TextPosition>>>) g -> g.getTextPositionList(), (Function<Shape, TextPosition>) s -> ((TextProp) s).getTextPosition()},
			{ShapeProperties.TEXT, "foo", (Function<Group, List<Optional<String>>>) g -> g.getTextList(), (Function<Shape, String>) s -> ((TextProp) s).getText()},
			{ShapeProperties.HATCHINGS_ANGLE, 11.223, (Function<Group, List<Optional<Double>>>) g -> {
				g.setFillingStyle(FillingStyle.CLINES);
				return g.getHatchingsAngleList();
			}, (Function<Shape, Double>) s -> s.getHatchingsAngle()},
			{ShapeProperties.HATCHINGS_WIDTH, 11.323, (Function<Group, List<Optional<Double>>>) g -> {
				g.setFillingStyle(FillingStyle.HLINES);
				return g.getHatchingsWidthList();
			}, (Function<Shape, Double>) s -> s.getHatchingsWidth()},
			{ShapeProperties.HATCHINGS_SEP, 11.423, (Function<Group, List<Optional<Double>>>) g -> {
				g.setFillingStyle(FillingStyle.HLINES);
				return g.getHatchingsSepList();
			}, (Function<Shape, Double>) s -> s.getHatchingsSep()},
			{ShapeProperties.GRAD_ANGLE, 11.523, (Function<Group, List<Optional<Double>>>) g -> {
				g.setFillingStyle(FillingStyle.GRAD);
				return g.getGradAngleList();
			}, (Function<Shape, Double>) s -> s.getGradAngle()},
			{ShapeProperties.GRAD_MID_POINT, 0.35, (Function<Group, List<Optional<Double>>>) g -> {
				g.setFillingStyle(FillingStyle.GRAD);
				return g.getGradMidPtList();
			}, (Function<Shape, Double>) s -> s.getGradMidPt()},
			{ShapeProperties.ROUND_CORNER_VALUE, 0.13, (Function<Group, List<Optional<Double>>>) g -> g.getLineArcList(), (Function<Shape, Double>) s -> ((LineArcProp) s).getLineArc()},
			{ShapeProperties.GRID_SUBGRID_COLOUR, DviPsColors.BITTERSWEET, (Function<Group, List<Optional<Color>>>) g -> g.getSubGridColourList(), (Function<Shape, Color>) s -> ((GridProp) s).getSubGridColour()},
			{ShapeProperties.GRID_LABELS_COLOUR, DviPsColors.BITTERSWEET, (Function<Group, List<Optional<Color>>>) g -> g.getGridLabelsColourList(), (Function<Shape, Color>) s -> ((GridProp) s).getGridLabelsColour()},
			{ShapeProperties.COLOUR_FILLING, DviPsColors.GRAY, (Function<Group, List<Optional<Color>>>) g -> {
				g.setFillingStyle(FillingStyle.PLAIN);
				return g.getFillingColList();
			}, (Function<Shape, Color>) s -> s.getFillingCol()},
			{ShapeProperties.COLOUR_LINE, DviPsColors.GRAY, (Function<Group, List<Optional<Color>>>) g -> g.getLineColourList(), (Function<Shape, Color>) s -> s.getLineColour()},
			{ShapeProperties.COLOUR_HATCHINGS, DviPsColors.GRAY, (Function<Group, List<Optional<Color>>>) g -> {
				g.setFillingStyle(FillingStyle.CLINES);
				return g.getHatchingsColList();
			}, (Function<Shape, Color>) s -> s.getHatchingsCol()},
			{ShapeProperties.DBLE_BORDERS, true, (Function<Group, List<Optional<Boolean>>>) g -> g.hasDbleBordList(), (Function<Shape, Boolean>) s -> s.hasDbleBord()},
			{ShapeProperties.DBLE_BORDERS_SIZE, 98.2, (Function<Group, List<Optional<Double>>>) g -> {
				g.setHasDbleBord(true);
				return g.getDbleBordSepList();
			}, (Function<Shape, Double>) s -> s.getDbleBordSep()},
			{ShapeProperties.COLOUR_DBLE_BORD, DviPsColors.JUNGLEGREEN, (Function<Group, List<Optional<Color>>>) g -> {
				g.setHasDbleBord(true);
				return g.getDbleBordColList();
			}, (Function<Shape, Color>) s -> s.getDbleBordCol()},
			{ShapeProperties.SHADOW, true, (Function<Group, List<Optional<Boolean>>>) g -> g.hasShadowList(), (Function<Shape, Boolean>) s -> s.hasShadow()},
			{ShapeProperties.SHADOW_SIZE, 123d, (Function<Group, List<Optional<Double>>>) g -> {
				g.setHasShadow(true);
				return g.getShadowSizeList();
			}, (Function<Shape, Double>) s -> s.getShadowSize()},
			{ShapeProperties.SHADOW_ANGLE, 122d, (Function<Group, List<Optional<Double>>>) g -> {
				g.setHasShadow(true);
				return g.getShadowAngleList();
			}, (Function<Shape, Double>) s -> s.getShadowAngle()},
			{ShapeProperties.SHADOW_COLOUR, DviPsColors.JUNGLEGREEN, (Function<Group, List<Optional<Color>>>) g -> {
				g.setHasShadow(true);
				return g.getShadowColList();
			}, (Function<Shape, Color>) s -> s.getShadowCol()},
			{ShapeProperties.COLOUR_GRADIENT_START, DviPsColors.GOLDEN_ROD, (Function<Group, List<Optional<Color>>>) g -> {
				g.setFillingStyle(FillingStyle.GRAD);
				return g.getGradColStartList();
			}, (Function<Shape, Color>) s -> s.getGradColStart()},
			{ShapeProperties.COLOUR_GRADIENT_END, DviPsColors.MULBERRY, (Function<Group, List<Optional<Color>>>) g -> {
				g.setFillingStyle(FillingStyle.GRAD);
				return g.getGradColEndList();
			}, (Function<Shape, Color>) s -> s.getGradColEnd()},
			{ShapeProperties.LINE_THICKNESS, 11.123, (Function<Group, List<Optional<Double>>>) g -> g.getThicknessList(), (Function<Shape, Double>) s -> s.getThickness()},
			{ShapeProperties.FILLING_STYLE, FillingStyle.GRAD, (Function<Group, List<Optional<FillingStyle>>>) g -> g.getFillingStyleList(), (Function<Shape, FillingStyle>) s -> s.getFillingStyle()},
			{ShapeProperties.BORDER_POS, BorderPos.OUT, (Function<Group, List<Optional<BorderPos>>>) g -> g.getBordersPositionList(), (Function<Shape, BorderPos>) s -> s.getBordersPosition()},
			{ShapeProperties.LINE_STYLE, LineStyle.DOTTED, (Function<Group, List<Optional<LineStyle>>>) g -> g.getLineStyleList(), (Function<Shape, LineStyle>) s -> s.getLineStyle()},
			{ShapeProperties.DOT_FILLING_COL, DviPsColors.BITTERSWEET, (Function<Group, List<Optional<Color>>>) g -> {
				g.setDotStyle(DotStyle.DIAMOND);
				return g.getDotFillingColList();
			}, (Function<Shape, Color>) s -> ((DotProp) s).getDotFillingCol()},
			{ShapeProperties.DOT_STYLE, DotStyle.DIAMOND, (Function<Group, List<Optional<DotStyle>>>) g -> g.getDotStyleList(), (Function<Shape, DotStyle>) s -> ((DotProp) s).getDotStyle()},
			{ShapeProperties.DOT_SIZE, 11.123, (Function<Group, List<Optional<Double>>>) g -> g.getDotSizeList(), (Function<Shape, Double>) s -> ((DotProp) s).getDiametre()}
		});
	}

	// The property to test
	@Parameterized.Parameter
	public ShapeProperties<Object> property;
	// The value to set
	@Parameterized.Parameter(1)
	public Object value;
	// The function that provides the memento, ie the values before setting the new value
	@Parameterized.Parameter(2)
	public Function<Group, List<Optional<Object>>> mementoCmd;
	@Parameterized.Parameter(3)
	// The function that provides the value to check after the setting of the new value
	public Function<Shape, Object> valueToCheckCmd;
	// The group of shapes
	private Group group;


	@Override
	@Before
	public void setUp() {
		group = ShapeFactory.INST.createGroup();
		super.setUp();
	}

	@Override
	protected void checkUndo() {
		final AtomicInteger i = new AtomicInteger(0);
		for(final Optional<Object> mem : memento) {
			mem.ifPresent(obj -> {
				if(obj instanceof Double) {
					final double value = (Double) obj;
					if(Double.isNaN(value)) {
						fail();
					}else {
						assertThat((Double) valueToCheckCmd.apply(group.getShapeAt(i.get())), closeTo(value, 0.0001));
					}
				}else {
					assertEquals(String.format("Incorrect value for shape %s", group.getShapeAt(i.get())), obj,
						valueToCheckCmd.apply(group.getShapeAt(i.get())));
				}
			});
			i.incrementAndGet();
		}
	}


	private void configureShapes() {
		final Grid grid = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		final Axes axes = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		final Dot dot = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		final Circle circle = ShapeFactory.INST.createCircle();
		final Ellipse ell = ShapeFactory.INST.createEllipse();
		final Text txt = ShapeFactory.INST.createText();
		final BezierCurve bc = ShapeFactory.INST.createBezierCurve(Collections.emptyList());
		final Polyline pl = ShapeFactory.INST.createPolyline(Collections.emptyList());
		final Polygon pg = ShapeFactory.INST.createPolygon(Collections.emptyList());
		final Triangle tr = ShapeFactory.INST.createTriangle();
		final Rhombus rh = ShapeFactory.INST.createRhombus();
		final Rectangle r1 = ShapeFactory.INST.createRectangle();
		final Plot plot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 0d, 1d, "x", false);
		final CircleArc carc = ShapeFactory.INST.createCircleArc();
		final Square sq = ShapeFactory.INST.createSquare();
		final Freehand fh = ShapeFactory.INST.createFreeHand(Collections.emptyList());
		r1.setLineStyle(LineStyle.DASHED);
		r1.setBordersPosition(BorderPos.INTO);
		r1.setFillingStyle(FillingStyle.PLAIN);
		r1.setThickness(2.3);
		r1.setHasDbleBord(true);
		r1.setLineArc(0.2);
		r1.setDbleBordSep(1.3);
		r1.setHatchingsAngle(0.33);
		r1.setHatchingsSep(9.1);
		r1.setHatchingsWidth(12.11);
		r1.setShadowAngle(0.1);
		r1.setGradMidPt(0.66);
		r1.setHatchingsCol(DviPsColors.CYAN);
		r1.setLineColour(DviPsColors.NAVYBLUE);
		r1.setShadowSize(87.2);
		r1.setFillingCol(DviPsColors.CARNATIONPINK);
		r1.setShadowCol(DviPsColors.CORNFLOWERBLUE);
		r1.setDashSepBlack(1.2);
		r1.setDashSepWhite(2.1);
		r1.setDotSep(23.1);
		r1.setGradAngle(1.3);
		r1.setDbleBordCol(DviPsColors.RED);
		r1.setGradColEnd(DviPsColors.BITTERSWEET);
		r1.setGradColStart(DviPsColors.FORESTGREEN);
		r1.setShowPts(true);
		final Rectangle r2 = ShapeFactory.INST.createRectangle();
		r2.setLineStyle(LineStyle.SOLID);
		r2.setBordersPosition(BorderPos.MID);
		r2.setFillingStyle(FillingStyle.HLINES);
		r2.setThickness(6.3);
		r2.setHasShadow(true);
		r2.setLineArc(0.33);
		r2.setDbleBordSep(2.3);
		r2.setHatchingsAngle(-0.53);
		r2.setHatchingsSep(1.1);
		r2.setHatchingsWidth(2.11);
		r2.setShadowAngle(-0.1);
		r2.setGradMidPt(0.31);
		r2.setHatchingsCol(DviPsColors.APRICOT);
		r2.setLineColour(DviPsColors.YELLOW);
		r2.setShadowSize(8.1);
		r2.setFillingCol(DviPsColors.CERULEAN);
		r2.setShadowCol(DviPsColors.DARKORCHID);
		r1.setDashSepBlack(11.2);
		r1.setDashSepWhite(21.1);
		r1.setDotSep(231.1);
		r1.setGradAngle(11.3);
		r1.setDbleBordCol(DviPsColors.ROYALBLUE);
		r1.setGradColEnd(DviPsColors.CADETBLUE);
		r1.setGradColStart(DviPsColors.OLIVE);
		txt.copy(r1);
		ell.copy(r1);
		circle.copy(r2);
		dot.copy(r2);
		bc.copy(r2);
		pl.copy(r1);
		pg.copy(r2);
		tr.copy(r1);
		rh.copy(r2);
		plot.copy(r1);
		carc.copy(r2);
		sq.copy(r1);
		fh.copy(r2);
		group.addShape(r1);
		group.addShape(txt);
		group.addShape(grid);
		group.addShape(pl);
		group.addShape(pg);
		group.addShape(r2);
		group.addShape(axes);
		group.addShape(ell);
		group.addShape(circle);
		group.addShape(tr);
		group.addShape(rh);
		group.addShape(dot);
		group.addShape(bc);
		group.addShape(plot);
		group.addShape(carc);
		group.addShape(sq);
		group.addShape(fh);
	}

	@Override
	protected void configCorrectCmd() {
		cmd = new ModifyShapeProperty<>(property, group, value);
		configureShapes();
		memento = mementoCmd.apply(group);
	}

	@Override
	protected void checkDo() {
		assertThat(valueToCheckCmd.apply(group), equalTo(value));
	}
}
