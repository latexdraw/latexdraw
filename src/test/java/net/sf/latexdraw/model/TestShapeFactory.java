package net.sf.latexdraw.model;

import java.util.Collections;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.CircleArc;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Rhombus;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.Triangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestShapeFactory implements HelperTest {
	@Test
	void testNewShape() {
		assertTrue(ShapeFactory.INST.newShape(Rectangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Polyline.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(BezierCurve.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Dot.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Freehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Grid.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Group.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Picture.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Axes.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Polygon.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Rhombus.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Triangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Freehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Ellipse.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Circle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(CircleArc.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Group.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Text.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Square.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Rectangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Polyline.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(BezierCurve.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Dot.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Freehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Grid.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Group.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Picture.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Axes.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Polygon.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Rhombus.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Triangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Freehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Ellipse.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Circle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(CircleArc.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Group.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Text.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(Square.class).isPresent());
	}

	@Test
	void testCreateBezierCurveFromSameNbPoints() {
		BezierCurve bc = ShapeFactory.INST.createBezierCurve(Collections.singletonList(ShapeFactory.INST.createPoint()));
		bc = ShapeFactory.INST.createBezierCurveFrom(bc, ShapeFactory.INST.createPoint(1d, 2d));
		assertEquals(bc.getNbPoints(), bc.getFirstCtrlPts().size());
	}

	@Test
	void testCreateBezierCurveFromSameNewPoint() {
		BezierCurve bc = ShapeFactory.INST.createBezierCurve(Collections.singletonList(ShapeFactory.INST.createPoint()));
		bc = ShapeFactory.INST.createBezierCurveFrom(bc, ShapeFactory.INST.createPoint(1d, 2d));
		assertEqualsDouble(1d, bc.getPtAt(1).getX());
		assertEqualsDouble(2d, bc.getPtAt(1).getY());
	}

	@Test
	void testCreateBezierCurveFromSameNewCtrlPoint() {
		BezierCurve bc = ShapeFactory.INST.createBezierCurve(Collections.singletonList(ShapeFactory.INST.createPoint()));
		bc = ShapeFactory.INST.createBezierCurveFrom(bc, ShapeFactory.INST.createPoint(1d, 2d));
		assertEqualsDouble(1d, bc.getFirstCtrlPtAt(1).getX());
		assertEqualsDouble(2d + BezierCurve.DEFAULT_POSITION_CTRL, bc.getFirstCtrlPtAt(1).getY());
	}

	@Test
	void testBadEquationPlot() {
		assertThrows(IllegalArgumentException.class, () -> ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 3, "y", false));
	}

	@Test
	void testBadMinMaxPlot() {
		assertThrows(IllegalArgumentException.class, () -> ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 3, 1, "x", false));
	}

	@Test
	void testBadMinPlot() {
		assertThrows(IllegalArgumentException.class, () -> ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), Double.NaN, 1, "x", false));
	}

	@Test
	void testBadMaxPlot() {
		assertThrows(IllegalArgumentException.class, () -> ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, Double.NaN, "x", false));
	}
}
