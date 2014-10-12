package test.glib.models;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IAxes;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IStandardGrid;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIAxes;

public class TestLAxes extends TestIAxes<IAxes> {
	@Before
	public void setUp() {
		shape  = ShapeFactory.createAxes(ShapeFactory.createPoint());
		shape2 = ShapeFactory.createAxes(ShapeFactory.createPoint());
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
	public void testConstructor3() {
		IAxes axes = ShapeFactory.createAxes(ShapeFactory.createPoint(10, -20));

		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(10., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(-20., axes.getPtAt(0).getY());
		axes = ShapeFactory.createAxes(null);
		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getY());
		axes = ShapeFactory.createAxes(null);
		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getY());
		axes = ShapeFactory.createAxes(ShapeFactory.createPoint(Double.NaN, 0));
		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getY());
		axes = ShapeFactory.createAxes(ShapeFactory.createPoint(0, Double.NaN));
		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getY());
		axes = ShapeFactory.createAxes(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, 0));
		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getY());
	}
}
