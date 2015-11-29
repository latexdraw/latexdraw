package test.glib.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIPlot;

public class TestLPlot extends TestIPlot<IPlot> {
	@Before
	public void setUp() {
		shape = ShapeFactory.createPlot(ShapeFactory.createPoint(), 1, 10, "x", false);
		shape2 = ShapeFactory.createPlot(ShapeFactory.createPoint(), 1, 10, "x", false);
	}

	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IPlot.class));
		assertTrue(shape.isTypeOf(IPlotProp.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
