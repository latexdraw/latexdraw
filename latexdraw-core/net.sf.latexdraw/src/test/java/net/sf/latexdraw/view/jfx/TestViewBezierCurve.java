package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import net.sf.latexdraw.CollectionMatcher;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestViewBezierCurve extends TestViewBorderedShape<ViewBezierCurve, IBezierCurve, Path> implements CollectionMatcher {
	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
	}

	@Override
	protected IBezierCurve createModel() {
		return ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
			ShapeFactory.INST.createPoint(251d, 33d)));
	}

	@Test
	public void testClosingCurveNotNull() {
		assertNotNull(view.closingCurve);
	}

	@Test
	public void testOpen() {
		model.setOpened(false);
		model.setOpened(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(view.border.getElements().contains(view.closingCurve));
	}

	@Test
	public void testNotOpen() {
		model.setOpened(true);
		model.setOpened(false);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.border.getElements().contains(view.closingCurve));
	}

	@Test
	public void showPointsContained() {
		assertTrue(view.getChildren().contains(view.showPoint));
	}

	@Test
	public void testShowPointVisible() {
		model.setShowPts(false);
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.showPoint.isVisible());
	}

	@Test
	public void testShowPointNotVisible() {
		model.setShowPts(true);
		model.setShowPts(false);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(view.showPoint.isVisible());
	}

	@Test
	public void testShowPtsGoodNbOfDots() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getNbPoints() * 3L, view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse).count());
	}

	@Test
	public void testShowPtsGoodNbOfLines() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getNbPoints() * 2L, view.showPoint.getChildren().stream().filter(n -> n instanceof Line).count());
	}

	@Test
	public void testShowPtsDotsOkColor() {
		model.setShowPts(true);
		model.setLineColour(ShapeFactory.INST.createColorFX(Color.RED));
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse), elt -> ((Shape) elt).getFill(), Color.RED);
	}

	@Test
	public void testShowPtsLinesOkColor() {
		model.setShowPts(true);
		model.setLineColour(ShapeFactory.INST.createColorFX(Color.RED));
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Line), elt -> ((Shape) elt).getStroke(), Color.RED);
	}

	@Test
	public void testShowPtsMainDotsOkPosition() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		final List<IPoint> centers = view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse).
			map(ell -> ShapeFactory.INST.createPoint(((Ellipse) ell).getCenterX(), ((Ellipse) ell).getCenterY())).collect(Collectors.toList());
		model.getPoints().forEach(pt -> assertThat(centers, CoreMatchers.hasItem(pt)));
	}

	@Test
	public void testShowPtsCtrl1DotsOkPosition() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		final List<IPoint> centers = view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse).
			map(ell -> ShapeFactory.INST.createPoint(((Ellipse) ell).getCenterX(), ((Ellipse) ell).getCenterY())).collect(Collectors.toList());
		model.getFirstCtrlPts().forEach(pt -> assertThat(centers, CoreMatchers.hasItem(pt)));
	}

	@Test
	public void testShowPtsCtrl2DotsOkPosition() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		final List<IPoint> centers = view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse).
			map(ell -> ShapeFactory.INST.createPoint(((Ellipse) ell).getCenterX(), ((Ellipse) ell).getCenterY())).collect(Collectors.toList());
		model.getSecondCtrlPts().forEach(pt -> assertThat(centers, CoreMatchers.hasItem(pt)));
	}

	@Test
	public void testShowPtsCtrlEllSize() {
		model.setShowPts(true);
		model.setThickness(10d);
		model.setHasDbleBord(true);
		model.setDbleBordSep(12d);
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse), elt -> ((Ellipse) elt).getRadiusX(),
			(model.getArrowAt(0).getDotSizeDim() + model.getArrowAt(0).getDotSizeNum() * model.getFullThickness()) / 2d);
	}

	@Test
	public void testShowPtsCtrlEllSizeDotParams() {
		model.setShowPts(true);
		model.getArrowAt(0).setDotSizeDim(1.1);
		model.getArrowAt(0).setDotSizeNum(3.3);
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse), elt -> ((Ellipse) elt).getRadiusX(),
			(model.getArrowAt(0).getDotSizeDim() + model.getArrowAt(0).getDotSizeNum() * model.getThickness()) / 2d);
	}

	@Test
	public void testShowPtsCtrlLineSize() {
		model.setShowPts(true);
		model.setThickness(10d);
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Line), elt -> ((Shape) elt).getStrokeWidth(), 5d);
	}

	@Test
	public void testShowPtsCtrlLineSizeDbleLine() {
		model.setShowPts(true);
		model.setHasDbleBord(true);
		model.setDbleBordSep(12d);
		model.setThickness(5d);
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Line), elt -> ((Shape) elt).getStrokeWidth(), 11d);
	}

	@Test
	public void testShowPtsCtrlEllNoStroke() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertAllNull(view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse), elt -> ((Shape) elt).getStroke());
	}

	@Test
	@Override
	public void testShadowPositionSameThanBorder() {
		assertEquals(view.border.getElements(), view.shadow.getElements());
	}

	@Test
	public void testDbleBorderElements() {
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(view.border.getElements(), view.dblBorder.getElements());
	}

	@Test
	@Override
	public void testOnTranslateX() {
		final double x = ((MoveTo) getBorder().getElements().get(0)).getX();
		model.translate(11d, 0d);
		model.setHasDbleBord(true);
		assertEquals(x + 11d, ((MoveTo) getBorder().getElements().get(0)).getX(), 0.0000001);
	}

	@Test
	@Override
	public void testOnTranslateY() {
		final double y = ((MoveTo) getBorder().getElements().get(0)).getY();
		model.translate(0, 12d);
		model.setHasDbleBord(true);
		assertEquals(y + 12d, ((MoveTo) getBorder().getElements().get(0)).getY(), 0.0000001);
	}

	private Optional<Node> getShowPtDotAtCoord(final IPoint pt) {
		return view.showPoint.getChildren().stream().filter(node -> node instanceof Ellipse &&
			Double.compare(((Ellipse) node).getCenterX(), pt.getX()) == 0 && Double.compare(((Ellipse) node).getCenterY(), pt.getY()) == 0).findAny();
	}

	@Test
	public void testNoShowPointDotOnArrow1() {
		model.setShowPts(true);
		model.getArrowAt(0).setArrowStyle(ArrowStyle.LEFT_ARROW);
		model.setHasDbleBord(true);
		final Optional<Node> dot = getShowPtDotAtCoord(model.getPtAt(0));
		assertTrue(dot.isPresent());
		assertFalse(dot.get().isVisible());
	}

	@Test
	public void testShowPointDotOnNoArrow1() {
		model.setShowPts(true);
		model.getArrowAt(0).setArrowStyle(ArrowStyle.NONE);
		model.setHasDbleBord(true);
		final Optional<Node> dot = getShowPtDotAtCoord(model.getPtAt(0));
		assertTrue(dot.isPresent());
		assertTrue(dot.get().isVisible());
	}

	@Test
	public void testNoShowPointDotOnArrow2() {
		model.setShowPts(true);
		model.getArrowAt(-1).setArrowStyle(ArrowStyle.LEFT_ARROW);
		model.setHasDbleBord(true);
		final Optional<Node> dot = getShowPtDotAtCoord(model.getPtAt(-1));
		assertTrue(dot.isPresent());
		assertFalse(dot.get().isVisible());
	}

	@Test
	public void testShowPointDotOnNoArrow2() {
		model.setShowPts(true);
		model.getArrowAt(-1).setArrowStyle(ArrowStyle.NONE);
		model.setHasDbleBord(true);
		final Optional<Node> dot = getShowPtDotAtCoord(model.getPtAt(-1));
		assertTrue(dot.isPresent());
		assertTrue(dot.get().isVisible());
	}
}
