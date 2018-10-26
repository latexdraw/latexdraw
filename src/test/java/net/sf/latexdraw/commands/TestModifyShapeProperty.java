package net.sf.latexdraw.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import net.sf.latexdraw.commands.shape.ModifyShapeProperty;
import net.sf.latexdraw.commands.shape.ShapeProperties;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.models.interfaces.prop.IArrowable;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.models.interfaces.prop.IClosableProp;
import net.sf.latexdraw.models.interfaces.prop.IDotProp;
import net.sf.latexdraw.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.models.interfaces.prop.IGridProp;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.models.interfaces.prop.IScalable;
import net.sf.latexdraw.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.models.interfaces.prop.ITextProp;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableShape;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
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
			{ShapeProperties.PLOT_STYLE, PlotStyle.DOTS, (Function<IGroup, List<Optional<PlotStyle>>>) g -> g.getPlotStyleList(), (Function<IShape, PlotStyle>) s -> ((IPlotProp) s).getPlotStyle()},
			{ShapeProperties.PLOT_POLAR, true, (Function<IGroup, List<Optional<Boolean>>>) g -> g.getPlotPolarList(), (Function<IShape, Boolean>) s -> ((IPlotProp) s).isPolar()},
			{ShapeProperties.PLOT_EQ, "x 2 mul", (Function<IGroup, List<Optional<String>>>) g -> g.getPlotEquationList(), (Function<IShape, String>) s -> ((IPlotProp) s).getPlotEquation()},
			{ShapeProperties.Y_SCALE, 2d, (Function<IGroup, List<Optional<Double>>>) g -> g.getYScaleList(), (Function<IShape, Double>) s -> ((IScalable) s).getYScale()},
			{ShapeProperties.X_SCALE, 2d, (Function<IGroup, List<Optional<Double>>>) g -> g.getXScaleList(), (Function<IShape, Double>) s -> ((IScalable) s).getXScale()},
			{ShapeProperties.PLOT_MAX_X, 20d, (Function<IGroup, List<Optional<Double>>>) g -> g.getPlotMaxXList(), (Function<IShape, Double>) s -> ((IPlotProp) s).getPlotMaxX()},
			{ShapeProperties.PLOT_MIN_X, -10d, (Function<IGroup, List<Optional<Double>>>) g -> g.getPlotMinXList(), (Function<IShape, Double>) s -> ((IPlotProp) s).getPlotMinX()},
			{ShapeProperties.PLOT_NB_PTS, 123, (Function<IGroup, List<Optional<Integer>>>) g -> g.getNbPlottedPointsList(), (Function<IShape, Integer>) s -> ((IPlotProp) s).getNbPlottedPoints()},
			{ShapeProperties.SHOW_POINTS, true, (Function<IGroup, List<Optional<Boolean>>>) g -> g.getShowPointsList(), (Function<IShape, Boolean>) s -> s.isShowPts()},
			{ShapeProperties.AXES_SHOW_ORIGIN, false, (Function<IGroup, List<Optional<Boolean>>>) g -> g.getAxesShowOriginList(), (Function<IShape, Boolean>) s -> ((IAxesProp) s).isShowOrigin()},
			{ShapeProperties.AXES_LABELS_DIST, ShapeFactory.INST.createPoint(1d, 2d), (Function<IGroup, List<Optional<IPoint>>>) g -> g.getAxesDistLabelsList(), (Function<IShape, IPoint>) s -> ((IAxesProp) s).getDistLabels()},
			{ShapeProperties.AXES_LABELS_INCR, ShapeFactory.INST.createPoint(1d, 2d), (Function<IGroup, List<Optional<IPoint>>>) g -> g.getAxesIncrementsList(), (Function<IShape, IPoint>) s -> ((IAxesProp) s).getIncrement()},
			{ShapeProperties.AXES_LABELS_SHOW, PlottingStyle.X, (Function<IGroup, List<Optional<PlottingStyle>>>) g -> g.getAxesLabelsDisplayedList(), (Function<IShape, PlottingStyle>) s -> ((IAxesProp) s).getLabelsDisplayed()},
			{ShapeProperties.AXES_TICKS_SHOW, PlottingStyle.Y, (Function<IGroup, List<Optional<PlottingStyle>>>) g -> g.getAxesTicksDisplayedList(), (Function<IShape, PlottingStyle>) s -> ((IAxesProp) s).getTicksDisplayed()},
			{ShapeProperties.GRID_SUBGRID_WIDTH, 21d, (Function<IGroup, List<Optional<Double>>>) g -> g.getSubGridWidthList(), (Function<IShape, Double>) s -> ((IGridProp) s).getSubGridWidth()},
			{ShapeProperties.CLOSABLE_CLOSE, false, (Function<IGroup, List<Optional<Boolean>>>) g -> g.getOpenList(), (Function<IShape, Boolean>) s -> ((IClosableProp) s).isOpened()},
			{ShapeProperties.FREEHAND_INTERVAL, 7, (Function<IGroup, List<Optional<Integer>>>) g -> g.getFreeHandIntervalList(), (Function<IShape, Integer>) s -> ((IFreeHandProp) s).getInterval()},
			{ShapeProperties.GRID_SUBGRID_DIV, 5, (Function<IGroup, List<Optional<Integer>>>) g -> g.getSubGridDivList(), (Function<IShape, Integer>) s -> ((IGridProp) s).getSubGridDiv()},
			{ShapeProperties.GRID_SUBGRID_DOTS, 3, (Function<IGroup, List<Optional<Integer>>>) g -> g.getSubGridDotsList(), (Function<IShape, Integer>) s -> ((IGridProp) s).getSubGridDots()},
			{ShapeProperties.GRID_DOTS, 7, (Function<IGroup, List<Optional<Integer>>>) g -> g.getGridDotsList(), (Function<IShape, Integer>) s -> ((IGridProp) s).getGridDots()},
			{ShapeProperties.GRID_WIDTH, 7d, (Function<IGroup, List<Optional<Double>>>) g -> g.getGridWidthList(), (Function<IShape, Double>) s -> ((IGridProp) s).getGridWidth()},
			{ShapeProperties.AXES_TICKS_STYLE, TicksStyle.FULL, (Function<IGroup, List<Optional<TicksStyle>>>) g -> g.getAxesTicksStyleList(), (Function<IShape, TicksStyle>) s -> ((IAxesProp) s).getTicksStyle()},
			{ShapeProperties.FREEHAND_STYLE, FreeHandStyle.LINES, (Function<IGroup, List<Optional<FreeHandStyle>>>) g -> g.getFreeHandTypeList(), (Function<IShape, FreeHandStyle>) s -> ((IFreeHandProp) s).getType()},
			{ShapeProperties.AXES_STYLE, AxesStyle.FRAME, (Function<IGroup, List<Optional<AxesStyle>>>) g -> g.getAxesStyleList(), (Function<IShape, AxesStyle>) s -> ((IAxesProp) s).getAxesStyle()},
			{ShapeProperties.GRID_LABEL_POSITION_X, false, (Function<IGroup, List<Optional<Boolean>>>) g -> g.getGridYLabelWestList(), (Function<IShape, Boolean>) s -> ((IGridProp) s).isYLabelWest()},
			{ShapeProperties.GRID_LABEL_POSITION_Y, false, (Function<IGroup, List<Optional<Boolean>>>) g -> g.getGridXLabelSouthList(), (Function<IShape, Boolean>) s -> ((IGridProp) s).isXLabelSouth()},
			{ShapeProperties.GRID_SIZE_LABEL, 11, (Function<IGroup, List<Optional<Integer>>>) g -> g.getGridLabelSizeList(), (Function<IShape, Integer>) s -> ((IStdGridProp) s).getLabelsSize()},
			{ShapeProperties.ARROW_T_BAR_SIZE_DIM, 0.55, (Function<IGroup, List<Optional<Double>>>) g -> g.getTBarSizeDimList(), (Function<IShape, Double>) s -> ((IArrowable) s).getTBarSizeDim()},
			{ShapeProperties.ARROW_T_BAR_SIZE_NUM, 0.55, (Function<IGroup, List<Optional<Double>>>) g -> g.getTBarSizeNumList(), (Function<IShape, Double>) s -> ((IArrowable) s).getTBarSizeNum()},
			{ShapeProperties.ARROW_DOT_SIZE_NUM, 0.55, (Function<IGroup, List<Optional<Double>>>) g -> g.getDotSizeNumList(), (Function<IShape, Double>) s -> ((IArrowable) s).getDotSizeNum()},
			{ShapeProperties.ARROW_DOT_SIZE_DIM, 0.55, (Function<IGroup, List<Optional<Double>>>) g -> g.getDotSizeDimList(), (Function<IShape, Double>) s -> ((IArrowable) s).getDotSizeDim()},
			{ShapeProperties.ARROW_BRACKET_NUM, 0.55, (Function<IGroup, List<Optional<Double>>>) g -> g.getBracketNumList(), (Function<IShape, Double>) s -> ((IArrowable) s).getBracketNum()},
			{ShapeProperties.ARROW_R_BRACKET_NUM, 0.55, (Function<IGroup, List<Optional<Double>>>) g -> g.getRBracketNumList(), (Function<IShape, Double>) s -> ((IArrowable) s).getRBracketNum()},
			{ShapeProperties.ARROW_SIZE_NUM, 0.55, (Function<IGroup, List<Optional<Double>>>) g -> g.getArrowSizeNumList(), (Function<IShape, Double>) s -> ((IArrowable) s).getArrowSizeNum()},
			{ShapeProperties.ARROW_SIZE_DIM, 0.55, (Function<IGroup, List<Optional<Double>>>) g -> g.getArrowSizeDimList(), (Function<IShape, Double>) s -> ((IArrowable) s).getArrowSizeDim()},
			{ShapeProperties.ARROW_LENGTH, 0.55, (Function<IGroup, List<Optional<Double>>>) g -> g.getArrowLengthList(), (Function<IShape, Double>) s -> ((IArrowable) s).getArrowLength()},
			{ShapeProperties.ARROW_INSET, 0.22, (Function<IGroup, List<Optional<Double>>>) g -> g.getArrowInsetList(), (Function<IShape, Double>) s -> ((IArrowable) s).getArrowInset()},
			{ShapeProperties.GRID_END, ShapeFactory.INST.createPoint(10d, 20d), (Function<IGroup, List<Optional<IPoint>>>) g -> g.getGridEndList(), (Function<IShape, IPoint>) s -> ((IStdGridProp) s).getGridEnd()},
			{ShapeProperties.GRID_ORIGIN, ShapeFactory.INST.createPoint(10d, 20d), (Function<IGroup, List<Optional<IPoint>>>) g -> g.getGridOriginList(), (Function<IShape, IPoint>) s ->ShapeFactory.INST.createPoint(((IStdGridProp) s).getOriginX(), ((IStdGridProp) s).getOriginY())},
			{ShapeProperties.GRID_START, ShapeFactory.INST.createPoint(-10d, -20d), (Function<IGroup, List<Optional<IPoint>>>) g -> g.getGridStartList(), (Function<IShape, IPoint>) s -> ((IStdGridProp) s).getGridStart()},
			{ShapeProperties.ARC_START_ANGLE, 11d, (Function<IGroup, List<Optional<Double>>>) g -> g.getAngleStartList(), (Function<IShape, Double>) s -> ((IArcProp) s).getAngleStart()},
			{ShapeProperties.ARC_END_ANGLE, 11d, (Function<IGroup, List<Optional<Double>>>) g -> g.getAngleEndList(), (Function<IShape, Double>) s -> ((IArcProp) s).getAngleEnd()},
			{ShapeProperties.ARC_STYLE, ArcStyle.WEDGE, (Function<IGroup, List<Optional<ArcStyle>>>) g -> g.getArcStyleList(), (Function<IShape, ArcStyle>) s -> ((IArcProp) s).getArcStyle()},
			{ShapeProperties.ARROW2_STYLE, ArrowStyle.CIRCLE_END, (Function<IGroup, List<Optional<ArrowStyle>>>) g -> g.getArrowStyleList(1), (Function<IShape, ArrowStyle>) s -> ((IArrowableShape) s).getArrowStyle(1)},
			{ShapeProperties.ARROW1_STYLE, ArrowStyle.CIRCLE_END, (Function<IGroup, List<Optional<ArrowStyle>>>) g -> g.getArrowStyleList(0), (Function<IShape, ArrowStyle>) s -> ((IArrowableShape) s).getArrowStyle(0)},
			{ShapeProperties.TEXT_POSITION, TextPosition.TOP_RIGHT, (Function<IGroup, List<Optional<TextPosition>>>) g -> g.getTextPositionList(), (Function<IShape, TextPosition>) s -> ((ITextProp) s).getTextPosition()},
			{ShapeProperties.TEXT, "foo", (Function<IGroup, List<Optional<String>>>) g -> g.getTextList(), (Function<IShape, String>) s -> ((ITextProp) s).getText()},
			{ShapeProperties.HATCHINGS_ANGLE, 11.223, (Function<IGroup, List<Optional<Double>>>) g -> {
				g.setFillingStyle(FillingStyle.CLINES);
				return g.getHatchingsAngleList();
			}, (Function<IShape, Double>) s -> s.getHatchingsAngle()},
			{ShapeProperties.HATCHINGS_WIDTH, 11.323, (Function<IGroup, List<Optional<Double>>>) g -> {
				g.setFillingStyle(FillingStyle.HLINES);
				return g.getHatchingsWidthList();
			}, (Function<IShape, Double>) s -> s.getHatchingsWidth()},
			{ShapeProperties.HATCHINGS_SEP, 11.423, (Function<IGroup, List<Optional<Double>>>) g -> {
				g.setFillingStyle(FillingStyle.HLINES);
				return g.getHatchingsSepList();
			}, (Function<IShape, Double>) s -> s.getHatchingsSep()},
			{ShapeProperties.GRAD_ANGLE, 11.523, (Function<IGroup, List<Optional<Double>>>) g -> {
				g.setFillingStyle(FillingStyle.GRAD);
				return g.getGradAngleList();
			}, (Function<IShape, Double>) s -> s.getGradAngle()},
			{ShapeProperties.GRAD_MID_POINT, 0.35, (Function<IGroup, List<Optional<Double>>>) g -> {
				g.setFillingStyle(FillingStyle.GRAD);
				return g.getGradMidPtList();
			}, (Function<IShape, Double>) s -> s.getGradMidPt()},
			{ShapeProperties.ROUND_CORNER_VALUE, 0.13, (Function<IGroup, List<Optional<Double>>>) g -> g.getLineArcList(), (Function<IShape, Double>) s -> ((ILineArcProp) s).getLineArc()},
			{ShapeProperties.GRID_SUBGRID_COLOUR, DviPsColors.BITTERSWEET, (Function<IGroup, List<Optional<Color>>>) g -> g.getSubGridColourList(), (Function<IShape, Color>) s -> ((IGridProp) s).getSubGridColour()},
			{ShapeProperties.GRID_LABELS_COLOUR, DviPsColors.BITTERSWEET, (Function<IGroup, List<Optional<Color>>>) g -> g.getGridLabelsColourList(), (Function<IShape, Color>) s -> ((IGridProp) s).getGridLabelsColour()},
			{ShapeProperties.COLOUR_FILLING, DviPsColors.GRAY, (Function<IGroup, List<Optional<Color>>>) g -> {
				g.setFillingStyle(FillingStyle.PLAIN);
				return g.getFillingColList();
			}, (Function<IShape, Color>) s -> s.getFillingCol()},
			{ShapeProperties.COLOUR_LINE, DviPsColors.GRAY, (Function<IGroup, List<Optional<Color>>>) g -> g.getLineColourList(), (Function<IShape, Color>) s -> s.getLineColour()},
			{ShapeProperties.COLOUR_HATCHINGS, DviPsColors.GRAY, (Function<IGroup, List<Optional<Color>>>) g -> {
				g.setFillingStyle(FillingStyle.CLINES);
				return g.getHatchingsColList();
			}, (Function<IShape, Color>) s -> s.getHatchingsCol()},
			{ShapeProperties.DBLE_BORDERS, true, (Function<IGroup, List<Optional<Boolean>>>) g -> g.hasDbleBordList(), (Function<IShape, Boolean>) s -> s.hasDbleBord()},
			{ShapeProperties.DBLE_BORDERS_SIZE, 98.2, (Function<IGroup, List<Optional<Double>>>) g -> {
				g.setHasDbleBord(true);
				return g.getDbleBordSepList();
			}, (Function<IShape, Double>) s -> s.getDbleBordSep()},
			{ShapeProperties.COLOUR_DBLE_BORD, DviPsColors.JUNGLEGREEN, (Function<IGroup, List<Optional<Color>>>) g -> {
				g.setHasDbleBord(true);
				return g.getDbleBordColList();
			}, (Function<IShape, Color>) s -> s.getDbleBordCol()},
			{ShapeProperties.SHADOW, true, (Function<IGroup, List<Optional<Boolean>>>) g -> g.hasShadowList(), (Function<IShape, Boolean>) s -> s.hasShadow()},
			{ShapeProperties.SHADOW_SIZE, 123d, (Function<IGroup, List<Optional<Double>>>) g -> {
				g.setHasShadow(true);
				return g.getShadowSizeList();
			}, (Function<IShape, Double>) s -> s.getShadowSize()},
			{ShapeProperties.SHADOW_ANGLE, 122d, (Function<IGroup, List<Optional<Double>>>) g -> {
				g.setHasShadow(true);
				return g.getShadowAngleList();
			}, (Function<IShape, Double>) s -> s.getShadowAngle()},
			{ShapeProperties.SHADOW_COLOUR, DviPsColors.JUNGLEGREEN, (Function<IGroup, List<Optional<Color>>>) g -> {
				g.setHasShadow(true);
				return g.getShadowColList();
			}, (Function<IShape, Color>) s -> s.getShadowCol()},
			{ShapeProperties.COLOUR_GRADIENT_START, DviPsColors.GOLDEN_ROD, (Function<IGroup, List<Optional<Color>>>) g -> {
				g.setFillingStyle(FillingStyle.GRAD);
				return g.getGradColStartList();
			}, (Function<IShape, Color>) s -> s.getGradColStart()},
			{ShapeProperties.COLOUR_GRADIENT_END, DviPsColors.MULBERRY, (Function<IGroup, List<Optional<Color>>>) g -> {
				g.setFillingStyle(FillingStyle.GRAD);
				return g.getGradColEndList();
			}, (Function<IShape, Color>) s -> s.getGradColEnd()},
			{ShapeProperties.LINE_THICKNESS, 11.123, (Function<IGroup, List<Optional<Double>>>) g -> g.getThicknessList(), (Function<IShape, Double>) s -> s.getThickness()},
			{ShapeProperties.FILLING_STYLE, FillingStyle.GRAD, (Function<IGroup, List<Optional<FillingStyle>>>) g -> g.getFillingStyleList(), (Function<IShape, FillingStyle>) s -> s.getFillingStyle()},
			{ShapeProperties.BORDER_POS, BorderPos.OUT, (Function<IGroup, List<Optional<BorderPos>>>) g -> g.getBordersPositionList(), (Function<IShape, BorderPos>) s -> s.getBordersPosition()},
			{ShapeProperties.LINE_STYLE, LineStyle.DOTTED, (Function<IGroup, List<Optional<LineStyle>>>) g -> g.getLineStyleList(), (Function<IShape, LineStyle>) s -> s.getLineStyle()},
			{ShapeProperties.DOT_FILLING_COL, DviPsColors.BITTERSWEET, (Function<IGroup, List<Optional<Color>>>) g -> {
				g.setDotStyle(DotStyle.DIAMOND);
				return g.getDotFillingColList();
			}, (Function<IShape, Color>) s -> ((IDotProp) s).getDotFillingCol()},
			{ShapeProperties.DOT_STYLE, DotStyle.DIAMOND, (Function<IGroup, List<Optional<DotStyle>>>) g -> g.getDotStyleList(), (Function<IShape, DotStyle>) s -> ((IDotProp) s).getDotStyle()},
			{ShapeProperties.DOT_SIZE, 11.123, (Function<IGroup, List<Optional<Double>>>) g -> g.getDotSizeList(), (Function<IShape, Double>) s -> ((IDotProp) s).getDiametre()}
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
	public Function<IGroup, List<Optional<Object>>> mementoCmd;
	@Parameterized.Parameter(3)
	// The function that provides the value to check after the setting of the new value
	public Function<IShape, Object> valueToCheckCmd;
	// The group of shapes
	private IGroup group;


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
		final IGrid grid = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		final IAxes axes = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		final IDot dot = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		final ICircle circle = ShapeFactory.INST.createCircle();
		final IEllipse ell = ShapeFactory.INST.createEllipse();
		final IText txt = ShapeFactory.INST.createText();
		final IBezierCurve bc = ShapeFactory.INST.createBezierCurve(Collections.emptyList());
		final IPolyline pl = ShapeFactory.INST.createPolyline(Collections.emptyList());
		final IPolygon pg = ShapeFactory.INST.createPolygon(Collections.emptyList());
		final ITriangle tr = ShapeFactory.INST.createTriangle();
		final IRhombus rh = ShapeFactory.INST.createRhombus();
		final IRectangle r1 = ShapeFactory.INST.createRectangle();
		final IPlot plot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 0d, 1d, "x", false);
		final ICircleArc carc = ShapeFactory.INST.createCircleArc();
		final ISquare sq = ShapeFactory.INST.createSquare();
		final IFreehand fh = ShapeFactory.INST.createFreeHand(Collections.emptyList());
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
		final IRectangle r2 = ShapeFactory.INST.createRectangle();
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
