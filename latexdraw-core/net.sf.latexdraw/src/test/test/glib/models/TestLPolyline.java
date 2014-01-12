package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIPolyline;

public class TestLPolyline<T extends IPolyline> extends TestIPolyline<T> {
	@Before
	public void setUp() {
		shape  = (T) ShapeFactory.createPolyline(false);
		shape2 = (T) ShapeFactory.createPolyline(false);
		shape.addPoint(ShapeFactory.createPoint(1, 1));
		shape.addPoint(ShapeFactory.createPoint(2, 2));
		shape.addPoint(ShapeFactory.createPoint(3, 3));
		shape2.addPoint(ShapeFactory.createPoint(1, 1));
		shape2.addPoint(ShapeFactory.createPoint(2, 2));
		shape2.addPoint(ShapeFactory.createPoint(3, 3));
	}


	@Test public void testSyncAddPointAddArrow() {
		assertEquals(shape.getPoints().size(), shape.getNbArrows());
		shape.removePoint(null);
		assertEquals(shape.getPoints().size(), shape.getNbArrows());
		shape.removePoint(shape.getPtAt(shape.getPoints().size()-1));
		assertEquals(shape.getPoints().size(), shape.getNbArrows());
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
		IPoint pt1   = ShapeFactory.createPoint(1, 1);
		IPoint pt2   = ShapeFactory.createPoint(2, 2);
		IPolyline pol = ShapeFactory.createPolyline(pt1, pt2, true);

		pol = ShapeFactory.createPolyline(pt1, pt2, false);
		assertEquals(pt1, pol.getPtAt(0));
		assertEquals(pt2, pol.getPtAt(-1));

		pol = ShapeFactory.createPolyline(true);
		pol = ShapeFactory.createPolyline(false);

		try {
			pol = ShapeFactory.createPolyline(null, pt2, true);
			fail();
			pol = ShapeFactory.createPolyline(pt1, null, true);
			fail();
			pol = ShapeFactory.createPolyline(null, null, true);
			fail();
		}catch(IllegalArgumentException e){ /* */ }
	}
}
