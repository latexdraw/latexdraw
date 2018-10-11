package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestIDot implements HelperTest {
	IDot shape;
	IDot shape2;

	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		shape2 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
	}

	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testConstructor1() {
		IDot dot1 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());

		assertTrue(dot1.getDiametre() > 0);
		assertNotNull(dot1.getDotStyle());
		assertNotNull(dot1.getPosition());
		assertEqualsDouble(0., dot1.getPosition().getX());
		assertEqualsDouble(0., dot1.getPosition().getY());
	}

	@Test
	public void testConstructor3() {
		IDot dot1 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint(-1, 2));
		assertEqualsDouble(-1., dot1.getPosition().getX());
		assertEqualsDouble(2., dot1.getPosition().getY());
	}

	@Theory
	public void testGetSetDotStyle(final DotStyle style) {
		shape.setDotStyle(style);
		assertEquals(style, shape.getDotStyle());
	}

	@Test
	public void testGetSetDotStyleKO() {
		shape.setDotStyle(DotStyle.DOT);
		shape.setDotStyle(null);
		assertEquals(DotStyle.DOT, shape.getDotStyle());
	}

	@Theory
	public void testGetSetRadius(@DoubleData final double value) {
		assumeThat(value, greaterThan(0d));

		shape.setDiametre(value);
		assertEqualsDouble(value, shape.getDiametre());
	}

	@Theory
	public void testGetSetRadiusKO(@DoubleData(vals = {0d, -1d}, bads = true) final double value) {
		shape.setDiametre(10d);
		shape.setDiametre(value);
		assertEqualsDouble(10d, shape.getDiametre());
	}

	@Test
	public void testCopy() {
		shape2.setDotStyle(DotStyle.DIAMOND);
		shape2.setDiametre(31);
		shape.copy(shape2);
		assertEquals(shape2.getDotStyle(), shape.getDotStyle());
		assertEqualsDouble(shape2.getDiametre(), shape.getDiametre());
	}

	@Test
	public void testMirrorHorizontal() {
		shape.setPosition(-10, -20);
		shape.mirrorHorizontal(0d);
		assertEqualsDouble(10., shape.getPosition().getX());
		assertEqualsDouble(-20., shape.getPosition().getY());
	}

	@Theory
	public void testInvalidMirrorHorizontal(@DoubleData(bads = true, vals = {}) final double value) {
		shape.setPosition(100, 200);
		shape.mirrorHorizontal(value);
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}

	@Test
	public void testMirrorVertical() {
		shape.setPosition(-10, -20);
		shape.mirrorVertical(0d);
		assertEqualsDouble(-10., shape.getPosition().getX());
		assertEqualsDouble(20., shape.getPosition().getY());
	}

	@Theory
	public void testInvalidMirrorVertical(@DoubleData(bads = true, vals = {}) final double value) {
		shape.setPosition(100, 200);
		shape.mirrorVertical(value);
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}
}
