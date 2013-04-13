package test.glib.models;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IStandardGrid;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIAxes;

public class TestLAxes<T extends IAxes> extends TestIAxes<T> {
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createAxes(false, DrawingTK.getFactory().createPoint());
		shape2 = (T) DrawingTK.getFactory().createAxes(false, DrawingTK.getFactory().createPoint());
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
		IAxes axes = DrawingTK.getFactory().createAxes(false, DrawingTK.getFactory().createPoint(10, -20));

		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(10., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(-20., axes.getPtAt(0).getY());
		axes = DrawingTK.getFactory().createAxes(false, null);
		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getY());
		axes = DrawingTK.getFactory().createAxes(true, null);
		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getY());
		axes = DrawingTK.getFactory().createAxes(false, DrawingTK.getFactory().createPoint(Double.NaN, 0));
		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getY());
		axes = DrawingTK.getFactory().createAxes(true, DrawingTK.getFactory().createPoint(0, Double.NaN));
		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getY());
		axes = DrawingTK.getFactory().createAxes(false, DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY, 0));
		assertNotNull(axes.getPtAt(0));
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., axes.getPtAt(0).getY());
	}
}
