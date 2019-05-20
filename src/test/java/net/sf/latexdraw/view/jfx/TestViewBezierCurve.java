package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import net.sf.latexdraw.CollectionMatcher;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Point;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestViewBezierCurve extends TestViewBorderedShape<ViewBezierCurve, BezierCurve, Path> implements CollectionMatcher {
	@Override
	protected BezierCurve createModel() {
		return ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
			ShapeFactory.INST.createPoint(251d, 33d)));
	}

	@Test
	void testClosingCurveNotNull() {
		assertNotNull(view.closingCurve);
	}

	@Test
	void testOpen() {
		model.setOpened(false);
		model.setOpened(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(view.border.getElements().contains(view.closingCurve));
	}

	@Test
	void testNotOpen() {
		model.setOpened(true);
		model.setOpened(false);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.border.getElements().contains(view.closingCurve));
	}

	@Test
	void testShowPointsContained() {
		assertTrue(view.getChildren().contains(view.showPoint));
	}

	@Test
	void testShowPointVisible() {
		model.setShowPts(false);
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.showPoint.isVisible());
	}

	@Test
	void testShowPointNotVisible() {
		model.setShowPts(true);
		model.setShowPts(false);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(view.showPoint.isVisible());
	}

	@Test
	void testShowPtsGoodNbOfDots() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getNbPoints() * 3L, view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse).count());
	}

	@Test
	void testShowPtsGoodNbOfLines() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getNbPoints() * 2L, view.showPoint.getChildren().stream().filter(n -> n instanceof Line).count());
	}

	@Test
	void testShowPtsDotsOkColor() {
		model.setShowPts(true);
		model.setLineColour(ShapeFactory.INST.createColorFX(Color.RED));
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse), elt -> ((Shape) elt).getFill(), Color.RED);
	}

	@Test
	void testShowPtsLinesOkColor() {
		model.setShowPts(true);
		model.setLineColour(ShapeFactory.INST.createColorFX(Color.RED));
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Line), elt -> ((Shape) elt).getStroke(), Color.RED);
	}

	@Test
	void testShowPtsMainDotsOkPosition() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		final List<Point> centers = view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse).
			map(ell -> ShapeFactory.INST.createPoint(((Ellipse) ell).getCenterX(), ((Ellipse) ell).getCenterY())).collect(Collectors.toList());
		model.getPoints().forEach(pt -> assertThat(centers).contains(pt));
	}

	@Test
	void testShowPtsCtrl1DotsOkPosition() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		final List<Point> centers = view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse).
			map(ell -> ShapeFactory.INST.createPoint(((Ellipse) ell).getCenterX(), ((Ellipse) ell).getCenterY())).collect(Collectors.toList());
		model.getFirstCtrlPts().forEach(pt -> assertThat(centers).contains(pt));
	}

	@Test
	void testShowPtsCtrl2DotsOkPosition() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		final List<Point> centers = view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse).
			map(ell -> ShapeFactory.INST.createPoint(((Ellipse) ell).getCenterX(), ((Ellipse) ell).getCenterY())).collect(Collectors.toList());
		model.getSecondCtrlPts().forEach(pt -> assertThat(centers).contains(pt));
	}

	@Test
	void testShowPtsCtrlEllSize() {
		model.setShowPts(true);
		model.setThickness(10d);
		model.setHasDbleBord(true);
		model.setDbleBordSep(12d);
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse), elt -> ((Ellipse) elt).getRadiusX(),
			(model.getArrowAt(0).getDotSizeDim() + model.getArrowAt(0).getDotSizeNum() * model.getFullThickness()) / 2d);
	}

	@Test
	void testShowPtsCtrlEllSizeDotParams() {
		model.setShowPts(true);
		model.getArrowAt(0).setDotSizeDim(1.1);
		model.getArrowAt(0).setDotSizeNum(3.3);
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse), elt -> ((Ellipse) elt).getRadiusX(),
			(model.getArrowAt(0).getDotSizeDim() + model.getArrowAt(0).getDotSizeNum() * model.getThickness()) / 2d);
	}

	@Test
	void testShowPtsCtrlLineSize() {
		model.setShowPts(true);
		model.setThickness(10d);
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Line), elt -> ((Shape) elt).getStrokeWidth(), 5d);
	}

	@Test
	void testShowPtsCtrlLineSizeDbleLine() {
		model.setShowPts(true);
		model.setHasDbleBord(true);
		model.setDbleBordSep(12d);
		model.setThickness(5d);
		WaitForAsyncUtils.waitForFxEvents();
		assertAllEquals(view.showPoint.getChildren().stream().filter(n -> n instanceof Line), elt -> ((Shape) elt).getStrokeWidth(), 11d);
	}

	@Test
	void testShowPtsCtrlEllNoStroke() {
		model.setShowPts(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertAllNull(view.showPoint.getChildren().stream().filter(n -> n instanceof Ellipse), elt -> ((Shape) elt).getStroke());
	}

	@Test
	@Override
	void testShadowPositionSameThanBorder() {
		assertEquals(view.border.getElements(), view.shadow.getElements());
	}

	@Test
	void testDbleBorderElements() {
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

	private Optional<Node> getShowPtDotAtCoord(final Point pt) {
		return view.showPoint.getChildren().stream().filter(node -> node instanceof Ellipse &&
			Double.compare(((Ellipse) node).getCenterX(), pt.getX()) == 0 && Double.compare(((Ellipse) node).getCenterY(), pt.getY()) == 0).findAny();
	}

	@Test
	void testNoShowPointDotOnArrow1() {
		model.setShowPts(true);
		model.getArrowAt(0).setArrowStyle(ArrowStyle.LEFT_ARROW);
		model.setHasDbleBord(true);
		final Optional<Node> dot = getShowPtDotAtCoord(model.getPtAt(0));
		assertTrue(dot.isPresent());
		assertFalse(dot.get().isVisible());
	}

	@Test
	void testShowPointDotOnNoArrow1() {
		model.setShowPts(true);
		model.getArrowAt(0).setArrowStyle(ArrowStyle.NONE);
		model.setHasDbleBord(true);
		final Optional<Node> dot = getShowPtDotAtCoord(model.getPtAt(0));
		assertTrue(dot.isPresent());
		assertTrue(dot.get().isVisible());
	}

	@Test
	void testNoShowPointDotOnArrow2() {
		model.setShowPts(true);
		model.getArrowAt(-1).setArrowStyle(ArrowStyle.LEFT_ARROW);
		model.setHasDbleBord(true);
		final Optional<Node> dot = getShowPtDotAtCoord(model.getPtAt(-1));
		assertTrue(dot.isPresent());
		assertFalse(dot.get().isVisible());
	}

	@Test
	void testShowPointDotOnNoArrow2() {
		model.setShowPts(true);
		model.getArrowAt(-1).setArrowStyle(ArrowStyle.NONE);
		model.setHasDbleBord(true);
		final Optional<Node> dot = getShowPtDotAtCoord(model.getPtAt(-1));
		assertTrue(dot.isPresent());
		assertTrue(dot.get().isVisible());
	}
}
