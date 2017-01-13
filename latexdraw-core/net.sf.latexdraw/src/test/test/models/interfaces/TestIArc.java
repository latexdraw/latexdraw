package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class TestIArc<T extends IArc> extends TestIPositionShape<T> {
	@Test
	public abstract void testGetStartPoint();

	@Test
	public abstract void testGetEndPoint();

	@Test
	public void testSetGetStyle() {
		shape.setArcStyle(ArcStyle.CHORD);
		assertEquals(ArcStyle.CHORD, shape.getArcStyle());

		shape.setArcStyle(ArcStyle.ARC);
		assertEquals(ArcStyle.ARC, shape.getArcStyle());

		shape.setArcStyle(ArcStyle.WEDGE);
		assertEquals(ArcStyle.WEDGE, shape.getArcStyle());

		shape.setArcStyle(null);
		assertEquals(ArcStyle.WEDGE, shape.getArcStyle());
	}

	@Test
	public void testSetGetAngleStart() {
		shape.setAngleStart(Math.PI);
		assertEqualsDouble(Math.PI, shape.getAngleStart());

		shape.setAngleStart(0.);
		assertEqualsDouble(0., shape.getAngleStart());

		shape.setAngleStart(2. * Math.PI);
		assertEqualsDouble(2. * Math.PI, shape.getAngleStart());

		shape.setAngleStart(4. * Math.PI);
		assertEqualsDouble(4. * Math.PI, shape.getAngleStart());

		shape.setAngleStart(-2. * Math.PI);
		assertEqualsDouble(-2. * Math.PI, shape.getAngleStart());

		shape.setAngleStart(-4. * Math.PI);
		assertEqualsDouble(-4. * Math.PI, shape.getAngleStart());

		shape.setAngleStart(Double.NaN);
		assertEqualsDouble(-4. * Math.PI, shape.getAngleStart());

		shape.setAngleStart(Double.NEGATIVE_INFINITY);
		assertEqualsDouble(-4. * Math.PI, shape.getAngleStart());

		shape.setAngleStart(Double.POSITIVE_INFINITY);
		assertEqualsDouble(-4. * Math.PI, shape.getAngleStart());
	}

	@Test
	public void testSetGetAngleEnd() {
		shape.setAngleEnd(Math.PI);
		assertEqualsDouble(Math.PI, shape.getAngleEnd());

		shape.setAngleEnd(0.);
		assertEqualsDouble(0., shape.getAngleEnd());

		shape.setAngleEnd(2. * Math.PI);
		assertEqualsDouble(2. * Math.PI, shape.getAngleEnd());

		shape.setAngleEnd(4. * Math.PI);
		assertEqualsDouble(4. * Math.PI, shape.getAngleEnd());

		shape.setAngleEnd(-2. * Math.PI);
		assertEqualsDouble(-2. * Math.PI, shape.getAngleEnd());

		shape.setAngleEnd(-4. * Math.PI);
		assertEqualsDouble(-4. * Math.PI, shape.getAngleEnd());

		shape.setAngleEnd(Double.NaN);
		assertEqualsDouble(-4. * Math.PI, shape.getAngleEnd());

		shape.setAngleEnd(Double.NEGATIVE_INFINITY);
		assertEqualsDouble(-4. * Math.PI, shape.getAngleEnd());

		shape.setAngleEnd(Double.POSITIVE_INFINITY);
		assertEqualsDouble(-4. * Math.PI, shape.getAngleEnd());
	}

	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setAngleEnd(-4. * Math.PI);
		shape2.setAngleStart(-2. * Math.PI);
		shape2.setArcStyle(ArcStyle.WEDGE);

		shape.copy(shape2);

		assertEqualsDouble(-4. * Math.PI, shape.getAngleEnd());
		assertEqualsDouble(-2. * Math.PI, shape.getAngleStart());
		assertEquals(ArcStyle.WEDGE, shape.getArcStyle());
	}
}
