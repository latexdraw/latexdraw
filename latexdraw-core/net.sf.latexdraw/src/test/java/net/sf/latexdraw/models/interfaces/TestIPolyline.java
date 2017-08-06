package net.sf.latexdraw.models.interfaces;

import java.util.Arrays;
import java.util.Collections;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestIPolyline implements HelperTest {
	@Test
	public void testConstructor() {
		final IPoint pt1 = ShapeFactory.INST.createPoint(1, 1);
		final IPoint pt2 = ShapeFactory.INST.createPoint(2, 2);
		final IPolyline pol = ShapeFactory.INST.createPolyline(Arrays.asList(pt1, pt2));

		assertEquals(pt1, pol.getPtAt(0));
		assertEquals(pt2, pol.getPtAt(-1));
	}

	@Test
	public void testIsTypeOf() {
		final IPolyline shape = ShapeFactory.INST.createPolyline(Collections.emptyList());
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IModifiablePointsShape.class));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(IPolyline.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
