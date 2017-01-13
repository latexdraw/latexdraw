package test.models;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import org.junit.Before;
import org.junit.Test;
import test.HelperTest;
import test.models.interfaces.TestIAxes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestLAxes extends TestIAxes<IAxes> implements HelperTest {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		shape2 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
	}

	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IStandardGrid.class));
		assertTrue(shape.isTypeOf(IAxes.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testConstructor3OK() {
		IAxes axes = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint(10, -20));

		assertNotNull(axes.getPtAt(0));
		assertEqualsDouble(10d, axes.getPtAt(0).getX());
		assertEqualsDouble(-20d, axes.getPtAt(0).getY());
	}

	@Test
	public void testConstructor3NotOKNAN0() {
		IAxes axes = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint(Double.NaN, 0));
		assertNotNull(axes.getPtAt(0));
		assertEqualsDouble(0d, axes.getPtAt(0).getX());
		assertEqualsDouble(0d, axes.getPtAt(0).getY());
	}

	@Test
	public void testConstructor3NotOK0NAN() {
		IAxes axes = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint(0, Double.NaN));
		assertNotNull(axes.getPtAt(0));
		assertEqualsDouble(0d, axes.getPtAt(0).getX());
		assertEqualsDouble(0d, axes.getPtAt(0).getY());
	}

	@Test
	public void testConstructor3NotOKINF0() {
		IAxes axes = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 0));
		assertNotNull(axes.getPtAt(0));
		assertEqualsDouble(0d, axes.getPtAt(0).getX());
		assertEqualsDouble(0d, axes.getPtAt(0).getY());
	}
}
