package net.sf.latexdraw.models;

import java.util.Collections;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestShapeFactory implements HelperTest {
	@Test
	public void testNewShape() {
		assertTrue(ShapeFactory.INST.newShape(IRectangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPolyline.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IBezierCurve.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IDot.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IFreehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGrid.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGroup.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPicture.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IAxes.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPolygon.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IRhombus.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ITriangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IFreehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IEllipse.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ICircle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ICircleArc.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGroup.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IText.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ISquare.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IRectangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPolyline.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IBezierCurve.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IDot.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IFreehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGrid.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGroup.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPicture.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IAxes.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IPolygon.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IRhombus.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ITriangle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IFreehand.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IEllipse.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ICircle.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ICircleArc.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IGroup.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(IText.class).isPresent());
		assertTrue(ShapeFactory.INST.newShape(ISquare.class).isPresent());
	}

	@Test
	public void testCreateBezierCurveFromSameNbPoints() {
		IBezierCurve bc = ShapeFactory.INST.createBezierCurve(Collections.singletonList(ShapeFactory.INST.createPoint()));
		bc = ShapeFactory.INST.createBezierCurveFrom(bc, ShapeFactory.INST.createPoint(1d, 2d));
		assertEquals(bc.getNbPoints(), bc.getFirstCtrlPts().size());
	}

	@Test
	public void testCreateBezierCurveFromSameNewPoint() {
		IBezierCurve bc = ShapeFactory.INST.createBezierCurve(Collections.singletonList(ShapeFactory.INST.createPoint()));
		bc = ShapeFactory.INST.createBezierCurveFrom(bc, ShapeFactory.INST.createPoint(1d, 2d));
		assertEqualsDouble(1d, bc.getPtAt(1).getX());
		assertEqualsDouble(2d, bc.getPtAt(1).getY());
	}

	@Test
	public void testCreateBezierCurveFromSameNewCtrlPoint() {
		IBezierCurve bc = ShapeFactory.INST.createBezierCurve(Collections.singletonList(ShapeFactory.INST.createPoint()));
		bc = ShapeFactory.INST.createBezierCurveFrom(bc, ShapeFactory.INST.createPoint(1d, 2d));
		assertEqualsDouble(1d, bc.getFirstCtrlPtAt(1).getX());
		assertEqualsDouble(2d + IBezierCurve.DEFAULT_POSITION_CTRL, bc.getFirstCtrlPtAt(1).getY());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPointsPolygonCrash() {
		ShapeFactory.INST.createPolygon(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPointsPolylineCrash() {
		ShapeFactory.INST.createPolyline(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPointsBCCrash() {
		ShapeFactory.INST.createBezierCurve(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBadEquationPlot() {
		ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 3, "y", false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBadMinMaxPlot() {
		ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 3, 1, "x", false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBadMinPlot() {
		ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), Double.NaN, 1, "x", false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBadMaxPlot() {
		ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, Double.NaN, "x", false);
	}
}
