package test.models.interfaces;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestIPolygon {
	IPolygon shape;
	IPolygon shape2;

	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createPolygon();
		shape2 = ShapeFactory.INST.createPolygon();
	}

	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IModifiablePointsShape.class));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testConstructor() {
		IPoint pt1 = ShapeFactory.INST.createPoint(1, 1);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 2);
		IPolygon pol = ShapeFactory.INST.createPolygon(pt1, pt2);
		assertEquals(pt1, pol.getPtAt(0));
		assertEquals(pt2, pol.getPtAt(-1));
	}
}
