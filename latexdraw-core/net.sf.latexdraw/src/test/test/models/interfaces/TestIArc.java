package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import test.HelperTest;
import test.data.ArcData;
import test.data.DoubleData;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class TestIArc implements HelperTest {
	@Theory
	public void testSetGetStyle(@ArcData final ICircleArc shape, final ArcStyle style) {
		shape.setArcStyle(style);
		assertEquals(style, shape.getArcStyle());
	}

	@Theory
	public void testSetGetAngleStart(@ArcData final ICircleArc shape, @DoubleData final double angle) {
		shape.setAngleStart(angle);
		assertEqualsDouble(angle, shape.getAngleStart());
	}

	@Theory
	public void testSetGetAngleStartKO(@ArcData final ICircleArc shape, @DoubleData(vals = {}, bads = true) final double angle) {
		shape.setAngleStart(1d);
		shape.setAngleStart(angle);
		assertEqualsDouble(1d, shape.getAngleStart());
	}

	@Theory
	public void testSetGetAngleEnd(@ArcData final ICircleArc shape, @DoubleData final double angle) {
		shape.setAngleEnd(angle);
		assertEqualsDouble(angle, shape.getAngleEnd());
	}

	@Theory
	public void testSetGetAngleEndKO(@ArcData final ICircleArc shape, @DoubleData(vals = {}, bads = true) final double angle) {
		shape.setAngleEnd(1d);
		shape.setAngleEnd(angle);
		assertEqualsDouble(1d, shape.getAngleEnd());
	}

	@Theory
	public void testCopy(@ArcData final ICircleArc shape, @ArcData final ICircleArc shape2) {
		shape2.setAngleEnd(-4d * Math.PI);
		shape2.setAngleStart(-2d * Math.PI);
		shape2.setArcStyle(ArcStyle.WEDGE);

		shape.copy(shape2);

		assertEqualsDouble(-4d * Math.PI, shape.getAngleEnd());
		assertEqualsDouble(-2d * Math.PI, shape.getAngleStart());
		assertEquals(ArcStyle.WEDGE, shape.getArcStyle());
	}
}
