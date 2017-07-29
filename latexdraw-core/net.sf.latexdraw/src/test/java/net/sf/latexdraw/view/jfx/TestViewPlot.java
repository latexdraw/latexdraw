package net.sf.latexdraw.view.jfx;

import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TestViewPlot extends TestViewShape<ViewPlot, IPlot> {
	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}


	private List<PathElement> getCurvePath() {
		return ((ViewBezierCurve) view.getChildren().get(0)).getBorder().getElements();
	}

	@Override
	protected IPlot createModel() {
		return ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(101, 67), 1d, 10d, "x", false);
	}


	@Test
	public void testOnChangePlotXMin() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setPlotMinX(model.getPlotMinX() - 2d);
		assertNotEquals(before, getCurvePath());
	}

	@Test
	public void testOnChangePlotXMax() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setPlotMaxX(model.getPlotMaxX() + 3d);
		assertNotEquals(before, getCurvePath());
	}

	@Test
	public void testOnChangePlotXScale() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setXScale(model.getXScale() * 1.33);
		assertNotEquals(before, getCurvePath());
	}

	@Test
	public void testOnChangePlotYScale() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setYScale(model.getYScale() * 0.87);
		assertNotEquals(before, getCurvePath());
	}

	@Test
	public void testOnChangeNbPlotPoints() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setNbPlottedPoints(model.getNbPlottedPoints() + 41);
		assertNotEquals(before, getCurvePath());
	}

	@Test
	public void testOnChangePolar() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setPolar(!model.isPolar());
		assertNotEquals(before, getCurvePath());
	}

	@Test
	public void testOnChangeEquation() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setPlotEquation("x 2 mul");
		assertNotEquals(before, getCurvePath());
	}

	@Test
	public void testOnChangeStyleLINE() {
		model.setPlotStyle(PlotStyle.LINE);
		assertTrue(view.getChildren().get(0) instanceof ViewPolyline);
	}

	@Test
	public void testOnChangeStyleCCURVE() {
		model.setPlotStyle(PlotStyle.LINE);
		model.setPlotStyle(PlotStyle.CCURVE);
		assertTrue(view.getChildren().get(0) instanceof ViewBezierCurve);
	}

	@Test
	public void testOnChangeStyleDOTS() {
		model.setPlotStyle(PlotStyle.DOTS);
		assertTrue(view.getChildren().get(0) instanceof ViewDot);
	}

	@Test
	public void testOnChangeStyleECURVE() {
		model.setPlotStyle(PlotStyle.LINE);
		model.setPlotStyle(PlotStyle.ECURVE);
		assertTrue(view.getChildren().get(0) instanceof ViewBezierCurve);
	}

	@Test
	public void testOnChangeStylePOLYGON() {
		model.setPlotStyle(PlotStyle.POLYGON);
		assertTrue(view.getChildren().get(0) instanceof ViewPolygon);
	}

	@Test
	public void testOnChangeStyleCURVE() {
		model.setPlotStyle(PlotStyle.LINE);
		model.setPlotStyle(PlotStyle.CURVE);
		assertTrue(view.getChildren().get(0) instanceof ViewBezierCurve);
	}

	@Test
	public void testOnChangeDotDiametre() {
		model.setPlotStyle(PlotStyle.DOTS);
		final double before = ((Ellipse) ((ViewDot) view.getChildren().get(0)).getChildren().get(0)).getRadiusX();
		model.setDiametre(model.getDiametre() * 1.577);
		assertNotEquals(before, ((Ellipse) ((ViewDot) view.getChildren().get(0)).getChildren().get(0)).getRadiusX());
	}

	@Test
	public void testOnChangeDotStyle() {
		model.setPlotStyle(PlotStyle.DOTS);
		final List<PathElement> before = ((Path) ((ViewDot) view.getChildren().get(0)).getChildren().get(1)).getElements();
		model.setDotStyle(DotStyle.FDIAMOND);
		assertNotEquals(before, ((Path) ((ViewDot) view.getChildren().get(0)).getChildren().get(1)).getElements());
	}


	@Override
	@Test
	public void testOnTranslateX() {
		final double x = view.getTranslateX();
		model.translate(11d, 0d);
		assertEquals(x + 11d, view.getTranslateX(), 0.0000001);
	}

	@Override
	@Test
	public void testOnTranslateY() {
		final double y = view.getTranslateY();
		model.translate(0d, 13d);
		assertEquals(y + 13d, view.getTranslateY(), 0.0000001);
	}
}
