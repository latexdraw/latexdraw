package net.sf.latexdraw.actions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.models.interfaces.prop.IArrowable;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;
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
import org.malai.action.Action;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class TestModifyShapeProperty extends TestUndoableAction<ModifyShapeProperty, List<?>> {
	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object> data() {
		return Arrays.stream(ShapeProperties.values()).collect(Collectors.toList());
	}

	// The property to test
	@Parameterized.Parameter
	public ShapeProperties property;
	// The value to set
	private Object value;
	// The group of shapes
	private IGroup group;
	// The function that provides the memento, ie the values before setting the new value
	private Supplier<List<?>> mementoCmd;
	// The function that provides the value to check after the setting of the new value
	private Function<IShape, Object> valueToCheckCmd;
	// The function that returns a boolean to know whether the property is supported by the given shape.
	private Function<IShape, Boolean> supportsPropCmd;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		group = ShapeFactory.INST.createGroup();

		switch(property) {
			case PLOT_STYLE:
				mementoCmd = () -> group.getPlotStyleList();
				valueToCheckCmd = sh -> ((IPlotProp)sh).getPlotStyle();
				supportsPropCmd = sh -> sh.isTypeOf(IPlotProp.class);
				value = PlotStyle.DOTS;
				break;
			case PLOT_POLAR:
				mementoCmd = () -> group.getPlotPolarList();
				valueToCheckCmd = sh -> ((IPlotProp)sh).isPolar();
				supportsPropCmd = sh -> sh.isTypeOf(IPlotProp.class);
				value = true;
				break;
			case PLOT_EQ:
				mementoCmd = () -> group.getPlotEquationList();
				valueToCheckCmd = sh -> ((IPlotProp)sh).getPlotEquation();
				supportsPropCmd = sh -> sh.isTypeOf(IPlotProp.class);
				value = "x 2 mul";
				break;
			case Y_SCALE:
				mementoCmd = () -> group.getYScaleList();
				valueToCheckCmd = sh -> ((IScalable)sh).getYScale();
				supportsPropCmd = sh -> sh.isTypeOf(IScalable.class);
				value = 2d;
				break;
			case X_SCALE:
				mementoCmd = () -> group.getXScaleList();
				valueToCheckCmd = sh -> ((IScalable)sh).getXScale();
				supportsPropCmd = sh -> sh.isTypeOf(IScalable.class);
				value = 2d;
				break;
			case PLOT_MAX_X:
				mementoCmd = () -> group.getPlotMaxXList();
				valueToCheckCmd = sh -> ((IPlotProp)sh).getPlotMaxX();
				supportsPropCmd = sh -> sh.isTypeOf(IPlotProp.class);
				value = 20d;
				break;
			case PLOT_MIN_X:
				mementoCmd = () -> group.getPlotMinXList();
				valueToCheckCmd = sh -> ((IPlotProp)sh).getPlotMinX();
				supportsPropCmd = sh -> sh.isTypeOf(IPlotProp.class);
				value = -10d;
				break;
			case PLOT_NB_PTS:
				mementoCmd = () -> group.getNbPlottedPointsList();
				valueToCheckCmd = sh -> ((IPlotProp)sh).getNbPlottedPoints();
				supportsPropCmd = sh -> sh.isTypeOf(IPlotProp.class);
				value = 123;
				break;
			case SHOW_POINTS:
				mementoCmd = () -> group.getShowPointsList();
				valueToCheckCmd = sh -> sh.isShowPts();
				supportsPropCmd = sh -> sh.isShowPtsable();
				value = true;
				break;
			case AXES_SHOW_ORIGIN:
				mementoCmd = () -> group.getAxesShowOriginList();
				valueToCheckCmd = sh -> ((IAxesProp)sh).isShowOrigin();
				supportsPropCmd = sh -> sh.isTypeOf(IAxesProp.class);
				value = false;
				break;
			case AXES_LABELS_DIST:
				mementoCmd = () -> group.getAxesDistLabelsList();
				valueToCheckCmd = sh -> ((IAxesProp)sh).getDistLabels();
				supportsPropCmd = sh -> sh.isTypeOf(IAxesProp.class);
				value = ShapeFactory.INST.createPoint(1d, 2d);
				break;
			case AXES_LABELS_INCR:
				mementoCmd = () -> group.getAxesIncrementsList();
				valueToCheckCmd = sh -> ((IAxesProp)sh).getIncrement();
				supportsPropCmd = sh -> sh.isTypeOf(IAxesProp.class);
				value = ShapeFactory.INST.createPoint(1d, 2d);
				break;
			case AXES_LABELS_SHOW:
				mementoCmd = () -> group.getAxesLabelsDisplayedList();
				valueToCheckCmd = sh -> ((IAxesProp)sh).getLabelsDisplayed();
				supportsPropCmd = sh -> sh.isTypeOf(IAxesProp.class);
				value = PlottingStyle.X;
				break;
			case AXES_TICKS_SHOW:
				mementoCmd = () -> group.getAxesTicksDisplayedList();
				valueToCheckCmd = sh -> ((IAxesProp)sh).getTicksDisplayed();
				supportsPropCmd = sh -> sh.isTypeOf(IAxesProp.class);
				value = PlottingStyle.Y;
				break;
			case GRID_SUBGRID_WIDTH:
				mementoCmd = () -> group.getSubGridWidthList();
				valueToCheckCmd = sh -> ((IGridProp)sh).getSubGridWidth();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = 21d;
				break;
			case FREEHAND_OPEN:
				mementoCmd = () -> group.getFreeHandOpenList();
				valueToCheckCmd = sh -> ((IFreeHandProp)sh).isOpen();
				supportsPropCmd = sh -> sh.isTypeOf(IFreeHandProp.class);
				value = false;
				break;
			case FREEHAND_INTERVAL:
				mementoCmd = () -> group.getFreeHandIntervalList();
				valueToCheckCmd = sh -> ((IFreeHandProp)sh).getInterval();
				supportsPropCmd = sh -> sh.isTypeOf(IFreeHandProp.class);
				value = 7;
				break;
			case GRID_SUBGRID_DIV:
				mementoCmd = () -> group.getSubGridDivList();
				valueToCheckCmd = sh -> ((IGridProp)sh).getSubGridDiv();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = 5;
				break;
			case GRID_SUBGRID_DOTS:
				mementoCmd = () -> group.getSubGridDotsList();
				valueToCheckCmd = sh -> ((IGridProp)sh).getSubGridDots();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = 3;
				break;
			case GRID_DOTS:
				mementoCmd = () -> group.getGridDotsList();
				valueToCheckCmd = sh -> ((IGridProp)sh).getGridDots();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = 7;
				break;
			case GRID_WIDTH:
				mementoCmd = () -> group.getGridWidthList();
				valueToCheckCmd = sh -> ((IGridProp)sh).getGridWidth();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = 7d;
				break;
			case AXES_TICKS_STYLE:
				mementoCmd = () -> group.getAxesTicksStyleList();
				valueToCheckCmd = sh -> ((IAxesProp)sh).getTicksStyle();
				supportsPropCmd = sh -> sh.isTypeOf(IAxesProp.class);
				value = TicksStyle.FULL;
				break;
			case FREEHAND_STYLE:
				mementoCmd = () -> group.getFreeHandTypeList();
				valueToCheckCmd = sh -> ((IFreeHandProp)sh).getType();
				supportsPropCmd = sh -> sh.isTypeOf(IFreeHandProp.class);
				value = FreeHandStyle.LINES;
				break;
			case AXES_STYLE:
				mementoCmd = () -> group.getAxesStyleList();
				valueToCheckCmd = sh -> ((IAxesProp)sh).getAxesStyle();
				supportsPropCmd = sh -> sh.isTypeOf(IAxesProp.class);
				value = AxesStyle.FRAME;
				break;
			case GRID_LABEL_POSITION_X:
				mementoCmd = () -> group.getGridYLabelWestList();
				valueToCheckCmd = sh -> ((IGridProp)sh).isYLabelWest();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = false;
				break;
			case GRID_LABEL_POSITION_Y:
				mementoCmd = () -> group.getGridXLabelSouthList();
				valueToCheckCmd = sh -> ((IGridProp)sh).isXLabelSouth();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = false;
				break;
			case GRID_SIZE_LABEL:
				mementoCmd = () -> group.getGridLabelSizeList();
				valueToCheckCmd = sh -> ((IStdGridProp)sh).getLabelsSize();
				supportsPropCmd = sh -> sh.isTypeOf(IStdGridProp.class);
				value = 11;
				break;
			case ARROW_T_BAR_SIZE_DIM:
				mementoCmd = () -> group.getTBarSizeDimList();
				valueToCheckCmd = sh -> ((IArrowable)sh).getTBarSizeDim();
				supportsPropCmd = sh -> sh.isTypeOf(IArrowable.class);
				value = 0.55;
				break;
			case ARROW_T_BAR_SIZE_NUM:
				mementoCmd = () -> group.getTBarSizeNumList();
				valueToCheckCmd = sh -> ((IArrowable)sh).getTBarSizeNum();
				supportsPropCmd = sh -> sh.isTypeOf(IArrowable.class);
				value = 0.55;
				break;
			case ARROW_DOT_SIZE_NUM:
				mementoCmd = () -> group.getDotSizeNumList();
				valueToCheckCmd = sh -> ((IArrowable)sh).getDotSizeNum();
				supportsPropCmd = sh -> sh.isTypeOf(IArrowable.class);
				value = 0.55;
				break;
			case ARROW_DOT_SIZE_DIM:
				mementoCmd = () -> group.getDotSizeDimList();
				valueToCheckCmd = sh -> ((IArrowable)sh).getDotSizeDim();
				supportsPropCmd = sh -> sh.isTypeOf(IArrowable.class);
				value = 0.55;
				break;
			case ARROW_BRACKET_NUM:
				mementoCmd = () -> group.getBracketNumList();
				valueToCheckCmd = sh -> ((IArrowable)sh).getBracketNum();
				supportsPropCmd = sh -> sh.isTypeOf(IArrowable.class);
				value = 0.55;
				break;
			case ARROW_R_BRACKET_NUM:
				mementoCmd = () -> group.getRBracketNumList();
				valueToCheckCmd = sh -> ((IArrowable)sh).getRBracketNum();
				supportsPropCmd = sh -> sh.isTypeOf(IArrowable.class);
				value = 0.55;
				break;
			case ARROW_SIZE_NUM:
				mementoCmd = () -> group.getArrowSizeNumList();
				valueToCheckCmd = sh -> ((IArrowable)sh).getArrowSizeNum();
				supportsPropCmd = sh -> sh.isTypeOf(IArrowable.class);
				value = 0.55;
				break;
			case ARROW_SIZE_DIM:
				mementoCmd = () -> group.getArrowSizeDimList();
				valueToCheckCmd = sh -> ((IArrowable)sh).getArrowSizeDim();
				supportsPropCmd = sh -> sh.isTypeOf(IArrowable.class);
				value = 0.55;
				break;
			case ARROW_LENGTH:
				mementoCmd = () -> group.getArrowLengthList();
				valueToCheckCmd = sh -> ((IArrowable)sh).getArrowLength();
				supportsPropCmd = sh -> sh.isTypeOf(IArrowable.class);
				value = 0.55;
				break;
			case ARROW_INSET:
				mementoCmd = () -> group.getArrowInsetList();
				valueToCheckCmd = sh -> ((IArrowable)sh).getArrowInset();
				supportsPropCmd = sh -> sh.isTypeOf(IArrowable.class);
				value = 0.22;
				break;
			case GRID_END:
				mementoCmd = () -> group.getGridEndList();
				valueToCheckCmd = sh -> ((IGridProp)sh).getGridEnd();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = ShapeFactory.INST.createPoint(10d, 20d);
				break;
			case GRID_ORIGIN:
				mementoCmd = () -> group.getGridOriginList();
				valueToCheckCmd = sh -> ShapeFactory.INST.createPoint(((IGridProp)sh).getOriginX(), ((IGridProp)sh).getOriginY());
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = ShapeFactory.INST.createPoint(10d, 20d);
				break;
			case GRID_START:
				mementoCmd = () -> group.getGridStartList();
				valueToCheckCmd = sh -> ((IGridProp)sh).getGridStart();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = ShapeFactory.INST.createPoint(-10d, -20d);
				break;
			case ARC_START_ANGLE:
				mementoCmd = () -> group.getAngleStartList();
				valueToCheckCmd = sh -> ((IArcProp)sh).getAngleStart();
				supportsPropCmd = sh -> sh.isTypeOf(IArcProp.class);
				value = 11d;
				break;
			case ARC_END_ANGLE:
				mementoCmd = () -> group.getAngleEndList();
				valueToCheckCmd = sh -> ((IArcProp)sh).getAngleEnd();
				supportsPropCmd = sh -> sh.isTypeOf(IArcProp.class);
				value = 11d;
				break;
			case ARC_STYLE:
				mementoCmd = () -> group.getArcStyleList();
				valueToCheckCmd = sh -> ((IArcProp)sh).getArcStyle();
				supportsPropCmd = sh -> sh.isTypeOf(IArcProp.class);
				value = ArcStyle.CHORD;
				break;
			case ARROW2_STYLE:
				mementoCmd = () -> group.getArrowStyleList(1);
				valueToCheckCmd = sh -> ((IArrowableShape)sh).getArrowStyle(1);
				supportsPropCmd = sh -> sh.isTypeOf(IArrowableShape.class);
				value = ArrowStyle.CIRCLE_END;
				break;
			case ARROW1_STYLE:
				mementoCmd = () -> group.getArrowStyleList(0);
				valueToCheckCmd = sh -> ((IArrowableShape)sh).getArrowStyle(0);
				supportsPropCmd = sh -> sh.isTypeOf(IArrowableShape.class);
				value = ArrowStyle.CIRCLE_END;
				break;
			case TEXT_POSITION:
				mementoCmd = () -> group.getTextPositionList();
				valueToCheckCmd = sh -> ((ITextProp)sh).getTextPosition();
				supportsPropCmd = sh -> sh.isTypeOf(ITextProp.class);
				value = TextPosition.TOP_RIGHT;
				break;
			case TEXT:
				mementoCmd = () -> group.getTextList();
				valueToCheckCmd = sh -> ((ITextProp)sh).getText();
				supportsPropCmd = sh -> sh.isTypeOf(ITextProp.class);
				value = "foo";
				break;
			case HATCHINGS_ANGLE:
				mementoCmd = () -> {
					group.setFillingStyle(FillingStyle.CLINES);
					return group.getHatchingsAngleList();
				};
				valueToCheckCmd = sh -> sh.getHatchingsAngle();
				supportsPropCmd = sh -> sh.isInteriorStylable();
				value = 11.123;
				break;
			case HATCHINGS_WIDTH:
				mementoCmd = () -> {
					group.setFillingStyle(FillingStyle.CLINES);
					return group.getHatchingsWidthList();
				};
				valueToCheckCmd = sh -> sh.getHatchingsWidth();
				supportsPropCmd = sh -> sh.isInteriorStylable();
				value = 11.123;
				break;
			case HATCHINGS_SEP:
				mementoCmd = () -> {
					group.setFillingStyle(FillingStyle.HLINES);
					return group.getHatchingsSepList();
				};
				valueToCheckCmd = sh -> sh.getHatchingsSep();
				supportsPropCmd = sh -> sh.isInteriorStylable();
				value = 11.123;
				break;
			case GRAD_ANGLE:
				mementoCmd = () -> {
					group.setFillingStyle(FillingStyle.GRAD);
					return group.getGradAngleList();
				};
				valueToCheckCmd = sh -> sh.getGradAngle();
				supportsPropCmd = sh -> sh.isInteriorStylable();
				value = 11.123;
				break;
			case GRAD_MID_POINT:
				mementoCmd = () -> {
					group.setFillingStyle(FillingStyle.GRAD);
					return group.getGradMidPtList();
				};
				valueToCheckCmd = sh -> sh.getGradMidPt();
				supportsPropCmd = sh -> sh.isInteriorStylable();
				value = 0.35;
				break;
			case ROUND_CORNER_VALUE:
				mementoCmd = () -> group.getLineArcList();
				valueToCheckCmd = sh -> ((ILineArcProp)sh).getLineArc();
				supportsPropCmd = sh -> sh.isTypeOf(ILineArcProp.class);
				value = 0.13;
				break;
			case GRID_SUBGRID_COLOUR:
				mementoCmd = () -> group.getSubGridColourList();
				valueToCheckCmd = sh -> ((IGridProp)sh).getSubGridColour();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = DviPsColors.BITTERSWEET;
				break;
			case GRID_LABELS_COLOUR:
				mementoCmd = () -> group.getGridLabelsColourList();
				valueToCheckCmd = sh -> ((IGridProp)sh).getGridLabelsColour();
				supportsPropCmd = sh -> sh.isTypeOf(IGridProp.class);
				value = DviPsColors.BITTERSWEET;
				break;
			case COLOUR_FILLING:
				mementoCmd = () -> {
					group.setFillingStyle(FillingStyle.PLAIN);
					return group.getFillingColList();
				};
				valueToCheckCmd = sh -> sh.getFillingCol();
				supportsPropCmd = sh -> sh.isInteriorStylable();
				value = DviPsColors.GRAY;
				break;
			case COLOUR_LINE:
				mementoCmd = () -> group.getLineColourList();
				valueToCheckCmd = sh -> sh.getLineColour();
				supportsPropCmd = sh -> true;
				value = DviPsColors.GRAY;
				break;
			case COLOUR_HATCHINGS:
				mementoCmd = () -> {
					group.setFillingStyle(FillingStyle.CLINES);
					return group.getHatchingsColList();
				};
				valueToCheckCmd = sh -> sh.getHatchingsCol();
				supportsPropCmd = sh -> sh.isInteriorStylable();
				value = DviPsColors.GRAY;
				break;
			case DBLE_BORDERS:
				mementoCmd = () -> group.hasDbleBordList();
				valueToCheckCmd = sh -> sh.hasDbleBord();
				supportsPropCmd = sh -> sh.isDbleBorderable();
				value = true;
				break;
			case DBLE_BORDERS_SIZE:
				mementoCmd = () -> {
					group.setHasDbleBord(true);
					return group.getDbleBordSepList();
				};
				valueToCheckCmd = sh -> sh.getDbleBordSep();
				supportsPropCmd = sh -> sh.isDbleBorderable();
				value = 98.2;
				break;
			case COLOUR_DBLE_BORD:
				mementoCmd = () -> {
					group.setHasDbleBord(true);
					return group.getDbleBordColList();
				};
				valueToCheckCmd = sh -> sh.getDbleBordCol();
				supportsPropCmd = sh -> sh.isDbleBorderable();
				value = DviPsColors.JUNGLEGREEN;
				break;
			case SHADOW:
				mementoCmd = () -> group.hasShadowList();
				valueToCheckCmd = sh -> sh.hasShadow();
				supportsPropCmd = sh -> sh.isShadowable();
				value = true;
				break;
			case SHADOW_SIZE:
				mementoCmd = () -> {
					group.setHasShadow(true);
					return group.getShadowSizeList();
				};
				valueToCheckCmd = sh -> sh.getShadowSize();
				supportsPropCmd = sh -> sh.isShadowable();
				value = 123d;
				break;
			case SHADOW_ANGLE:
				mementoCmd = () -> {
					group.setHasShadow(true);
					return group.getShadowAngleList();
				};
				valueToCheckCmd = sh -> sh.getShadowAngle();
				supportsPropCmd = sh -> sh.isShadowable();
				value = 122d;
				break;
			case SHADOW_COLOUR:
				mementoCmd = () -> {
					group.setHasShadow(true);
					return group.getShadowColList();
				};
				valueToCheckCmd = sh -> sh.getShadowCol();
				supportsPropCmd = sh -> sh.isShadowable();
				value = DviPsColors.JUNGLEGREEN;
				break;
			case COLOUR_GRADIENT_START:
				mementoCmd = () -> {
					group.setFillingStyle(FillingStyle.GRAD);
					return group.getGradColStartList();
				};
				valueToCheckCmd = sh -> sh.getGradColStart();
				supportsPropCmd = sh -> sh.isInteriorStylable();
				value = DviPsColors.GOLDEN_ROD;
				break;
			case COLOUR_GRADIENT_END:
				mementoCmd = () -> {
					group.setFillingStyle(FillingStyle.GRAD);
					return group.getGradColEndList();
				};
				valueToCheckCmd = sh -> sh.getGradColEnd();
				supportsPropCmd = sh -> sh.isInteriorStylable();
				value = DviPsColors.MULBERRY;
				break;
			case LINE_THICKNESS:
				mementoCmd = () -> group.getThicknessList();
				valueToCheckCmd = sh -> sh.getThickness();
				supportsPropCmd = sh -> sh.isThicknessable();
				value = 11.123;
				break;
			case FILLING_STYLE:
				mementoCmd = () -> group.getFillingStyleList();
				valueToCheckCmd = sh -> sh.getFillingStyle();
				supportsPropCmd = sh -> sh.isInteriorStylable();
				value = FillingStyle.GRAD;
				break;
			case BORDER_POS:
				mementoCmd = () -> group.getBordersPositionList();
				supportsPropCmd = sh -> sh.isBordersMovable();
				valueToCheckCmd = sh -> sh.getBordersPosition();
				value = BorderPos.OUT;
				break;
			case LINE_STYLE:
				mementoCmd = () -> group.getLineStyleList();
				supportsPropCmd = sh -> sh.isLineStylable();
				valueToCheckCmd = sh -> sh.getLineStyle();
				value = LineStyle.DOTTED;
				break;
			case DOT_FILLING_COL:
				mementoCmd = () -> {
					group.setDotStyle(DotStyle.DIAMOND);
					return group.getDotFillingColList();
				};
				valueToCheckCmd = sh -> ((IDotProp)sh).getDotFillingCol();
				supportsPropCmd = sh -> sh.isTypeOf(IDotProp.class);
				value = DviPsColors.BITTERSWEET;
				break;
			case DOT_STYLE:
				mementoCmd = () -> group.getDotStyleList();
				valueToCheckCmd = sh -> ((IDotProp)sh).getDotStyle();
				supportsPropCmd = sh -> sh.isTypeOf(IDotProp.class);
				value = DotStyle.DIAMOND;
				break;
			case DOT_SIZE:
				mementoCmd = () -> group.getDotSizeList();
				valueToCheckCmd = sh -> ((IDotProp)sh).getDiametre();
				supportsPropCmd = sh -> sh.isTypeOf(IDotProp.class);
				value = 11.123;
				break;
		}
	}

	@Override
	protected void checkUndo() {
		int i = 0;
		for(final Object mem : memento) {
			if(mem instanceof Double) {
				final double value = (Double) mem;
				if(Double.isNaN(value)) {
					assertFalse(supportsPropCmd.apply(group.getShapeAt(i)));
				}else {
					assertThat((Double) valueToCheckCmd.apply(group.getShapeAt(i)), closeTo(value, 0.0001));
				}
			}else {
				if(mem == null) {
					assertFalse(String.format("Shape %s supports %s but has null value", group.getShapeAt(i), property),
						supportsPropCmd.apply(group.getShapeAt(i)));
				}else {
					if(supportsPropCmd.apply(group.getShapeAt(i))) {
						assertEquals(String.format("Incorrect value for shape %s", group.getShapeAt(i)), mem, valueToCheckCmd.apply(group.getShapeAt(i)));
					}
				}
			}
			i++;
		}
	}

	@Override
	protected ModifyShapeProperty createAction() {
		return new ModifyShapeProperty();
	}


	private void configureShapes() {
		final IGrid grid = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		final IAxes axes = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		final IDot dot = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		final ICircle circle = ShapeFactory.INST.createCircle();
		final IEllipse ell = ShapeFactory.INST.createEllipse();
		final IText txt = ShapeFactory.INST.createText();
		final IBezierCurve bc = ShapeFactory.INST.createBezierCurve();
		final IPolyline pl = ShapeFactory.INST.createPolyline();
		final IPolygon pg = ShapeFactory.INST.createPolygon();
		final ITriangle tr = ShapeFactory.INST.createTriangle();
		final IRhombus rh = ShapeFactory.INST.createRhombus();
		final IRectangle r1 = ShapeFactory.INST.createRectangle();
		final IPlot plot = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 0d, 1d, "x", false);
		final ICircleArc carc = ShapeFactory.INST.createCircleArc();
		final ISquare sq = ShapeFactory.INST.createSquare();
		final IFreehand fh = ShapeFactory.INST.createFreeHand();
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
	protected void configCorrectAction() {
		configureShapes();
		memento = mementoCmd.get();
		action.setGroup(group);
		action.setProperty(property);
		action.setValue(value);
	}

	@Override
	protected void checkDo() {
		assertThat(valueToCheckCmd.apply(group), equalTo(value));
	}

	@Override
	public void testIsRegisterable() {
		assertThat(action.getRegistrationPolicy(), is(Action.RegistrationPolicy.LIMITED));
	}
}
