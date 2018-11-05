package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.ArcData;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.CircleArc;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Theories.class)
public class TestArc implements HelperTest {
	@Theory
	public void testSetGetStyle(@ArcData final CircleArc shape, final ArcStyle style) {
		shape.setArcStyle(style);
		assertEquals(style, shape.getArcStyle());
	}

	@Theory
	public void testSetGetAngleStart(@ArcData final CircleArc shape, @DoubleData final double angle) {
		shape.setAngleStart(angle);
		assertEqualsDouble(angle, shape.getAngleStart());
	}

	@Theory
	public void testSetGetAngleStartKO(@ArcData final CircleArc shape, @DoubleData(vals = {}, bads = true) final double angle) {
		shape.setAngleStart(1d);
		shape.setAngleStart(angle);
		assertEqualsDouble(1d, shape.getAngleStart());
	}

	@Theory
	public void testSetGetAngleEnd(@ArcData final CircleArc shape, @DoubleData final double angle) {
		shape.setAngleEnd(angle);
		assertEqualsDouble(angle, shape.getAngleEnd());
	}

	@Theory
	public void testSetGetAngleEndKO(@ArcData final CircleArc shape, @DoubleData(vals = {}, bads = true) final double angle) {
		shape.setAngleEnd(1d);
		shape.setAngleEnd(angle);
		assertEqualsDouble(1d, shape.getAngleEnd());
	}

	@Theory
	public void testCopy(@ArcData final CircleArc shape, @ArcData final CircleArc shape2) {
		shape2.setAngleEnd(-4d * Math.PI);
		shape2.setAngleStart(-2d * Math.PI);
		shape2.setArcStyle(ArcStyle.WEDGE);

		shape.copy(shape2);
		CompareShapeMatcher.INST.assertEqualArcParams(shape, shape2);
	}

	@Theory
	public void testNotNullStartAngleProp(@ArcData final CircleArc shape) {
		assertNotNull(shape.angleStartProperty());
	}

	@Theory
	public void testNotNullEndAngleProp(@ArcData final CircleArc shape) {
		assertNotNull(shape.angleEndProperty());
	}

	@Theory
	public void testNotNullArcStyleProp(@ArcData final CircleArc shape) {
		assertNotNull(shape.arcStyleProperty());
	}
}
