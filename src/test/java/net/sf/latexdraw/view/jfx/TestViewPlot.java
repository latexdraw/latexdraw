package net.sf.latexdraw.view.jfx;

import java.util.List;
import javafx.application.Platform;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestViewPlot extends TestViewShape<ViewPlot, IPlot> {
	@BeforeAll
	public static void beforeClass() {
		try {
			Platform.startup(() -> {});
		}catch(final IllegalStateException ex) {
			// Ok
		}
	}

	private List<PathElement> getCurvePath() {
		return ((ViewBezierCurve) view.getChildren().get(0)).getBorder().getElements();
	}

	@Override
	protected IPlot createModel() {
		return ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(101, 67), 1d, 10d, "x", false);
	}


	@Test
	void testOnChangePlotXMin() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setPlotMinX(model.getPlotMinX() - 2d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(before, getCurvePath());
	}

	@Test
	void testOnChangePlotXMax() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setPlotMaxX(model.getPlotMaxX() + 3d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(before, getCurvePath());
	}

	@Test
	void testOnChangePlotXScale() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setXScale(model.getXScale() * 1.33);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(before, getCurvePath());
	}

	@Test
	void testOnChangePlotYScale() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setYScale(model.getYScale() * 0.87);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(before, getCurvePath());
	}

	@Test
	void testOnChangeNbPlotPoints() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setNbPlottedPoints(model.getNbPlottedPoints() + 41);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(before, getCurvePath());
	}

	@Test
	void testOnChangePolar() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setPolar(!model.isPolar());
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(before, getCurvePath());
	}

	@Test
	void testOnChangeEquation() {
		final List<PathElement> before = duplicatePath(getCurvePath());
		model.setPlotEquation("x 2 mul");
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(before, getCurvePath());
	}

	@Test
	void testOnChangeStyleLINE() {
		model.setPlotStyle(PlotStyle.LINE);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getChildren().get(0) instanceof ViewPolyline);
	}

	@Test
	void testOnChangeStyleCCURVE() {
		model.setPlotStyle(PlotStyle.LINE);
		model.setPlotStyle(PlotStyle.CCURVE);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getChildren().get(0) instanceof ViewBezierCurve);
	}

	@Test
	void testOnChangeStyleDOTS() {
		model.setPlotStyle(PlotStyle.DOTS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getChildren().get(0) instanceof ViewDot);
	}

	@Test
	void testOnChangeStyleECURVE() {
		model.setPlotStyle(PlotStyle.LINE);
		model.setPlotStyle(PlotStyle.ECURVE);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getChildren().get(0) instanceof ViewBezierCurve);
	}

	@Test
	void testOnChangeStylePOLYGON() {
		model.setPlotStyle(PlotStyle.POLYGON);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getChildren().get(0) instanceof ViewPolygon);
	}

	@Test
	void testOnChangeStyleCURVE() {
		model.setPlotStyle(PlotStyle.LINE);
		model.setPlotStyle(PlotStyle.CURVE);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getChildren().get(0) instanceof ViewBezierCurve);
	}

	@Test
	void testOnChangeDotDiametre() {
		model.setPlotStyle(PlotStyle.DOTS);
		WaitForAsyncUtils.waitForFxEvents();
		final double before = ((Ellipse) ((ViewDot) view.getChildren().get(0)).getChildren().get(0)).getRadiusX();
		model.setDiametre(model.getDiametre() * 1.577);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(before, ((Ellipse) ((ViewDot) view.getChildren().get(0)).getChildren().get(0)).getRadiusX());
	}

	@Test
	void testOnChangeDotStyle() {
		model.setPlotStyle(PlotStyle.DOTS);
		WaitForAsyncUtils.waitForFxEvents();
		final List<PathElement> before = ((Path) ((ViewDot) view.getChildren().get(0)).getChildren().get(1)).getElements();
		model.setDotStyle(DotStyle.FDIAMOND);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(before, ((Path) ((ViewDot) view.getChildren().get(0)).getChildren().get(1)).getElements());
	}


	@Test
	void testOnDotNotSamePoints() {
		model.setPlotStyle(PlotStyle.DOTS);
		WaitForAsyncUtils.waitForFxEvents();
		// Computing the number of different x
		final int nbXDiff = (int) view.getChildren().stream().map(node -> ((ViewDot)node).dot.centerXProperty().get()).distinct().count();
		// Computing the number of different y
		final int nbYDiff = (int) view.getChildren().stream().map(node -> ((ViewDot)node).dot.centerYProperty().get()).distinct().count();
		// The points of the plot must all differ
		WaitForAsyncUtils.waitForFxEvents();
		assertThat(view.getChildren().size(), anyOf(equalTo(nbXDiff), equalTo(nbYDiff)));
	}

	@Test
	void testOnDotNbPoints() {
		model.setPlotStyle(PlotStyle.DOTS);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getNbPlottedPoints(), view.getChildren().size());
	}


	@Override
	@Test
	public void testOnTranslateX() {
		final double x = view.getTranslateX();
		model.translate(11d, 0d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(x + 11d, view.getTranslateX(), 0.0000001);
	}

	@Override
	@Test
	public void testOnTranslateY() {
		final double y = view.getTranslateY();
		model.translate(0d, 13d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(y + 13d, view.getTranslateY(), 0.0000001);
	}
}
