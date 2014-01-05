package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIPolyline;

public class TestLPolyline<T extends IPolyline> extends TestIPolyline<T> {
	@Before
	public void setUp() {
		shape  = (T) ShapeFactory.factory().createPolyline(false);
		shape2 = (T) ShapeFactory.factory().createPolyline(false);
		shape.addPoint(ShapeFactory.factory().createPoint(1, 1));
		shape.addPoint(ShapeFactory.factory().createPoint(2, 2));
		shape.addPoint(ShapeFactory.factory().createPoint(3, 3));
		shape2.addPoint(ShapeFactory.factory().createPoint(1, 1));
		shape2.addPoint(ShapeFactory.factory().createPoint(2, 2));
		shape2.addPoint(ShapeFactory.factory().createPoint(3, 3));
	}


	@Test public void testSyncAddPointAddArrow() {
		assertEquals(shape.getPoints().size(), shape.getArrows().size());
		shape.removePoint(null);
		assertEquals(shape.getPoints().size(), shape.getArrows().size());
		shape.removePoint(shape.getPtAt(shape.getPoints().size()-1));
		assertEquals(shape.getPoints().size(), shape.getArrows().size());
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IModifiablePointsShape.class));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(IPolyline.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructor() {
		IPoint pt1   = ShapeFactory.factory().createPoint(1, 1);
		IPoint pt2   = ShapeFactory.factory().createPoint(2, 2);
		IPolyline pol = ShapeFactory.factory().createPolyline(pt1, pt2, true);

		pol = ShapeFactory.factory().createPolyline(pt1, pt2, false);
		assertEquals(pt1, pol.getPtAt(0));
		assertEquals(pt2, pol.getPtAt(-1));

		pol = ShapeFactory.factory().createPolyline(true);
		pol = ShapeFactory.factory().createPolyline(false);

		try {
			pol = ShapeFactory.factory().createPolyline(null, pt2, true);
			fail();
			pol = ShapeFactory.factory().createPolyline(pt1, null, true);
			fail();
			pol = ShapeFactory.factory().createPolyline(null, null, true);
			fail();
		}catch(IllegalArgumentException e){ /* */ }
	}
}
