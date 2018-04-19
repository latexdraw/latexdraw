package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Theories.class)
public class TestColor {
	Color color;

	@Before
	public void setUp() {
		color = ShapeFactory.INST.createColor();
	}

	@Theory
	public void testSetGetO(@DoubleData(vals = {0d, 0.2, 1d}) final double value) {
		color.setO(value);
		assertEquals(value, color.getO(), 0.0001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetOInvalidGreater() {
		color.setO(1.001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetOInvalidLower() {
		color.setO(-0.001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetOInvalidNaN() {
		color.setO(Double.NaN);
	}

	@Theory
	public void testSetGetB(@DoubleData(vals = {0d, 0.2, 1d}) final double value) {
		color.setB(value);
		assertEquals(value, color.getB(), 0.0001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetBInvalidGreater() {
		color.setB(1.001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetBInvalidLower() {
		color.setB(-0.001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetBInvalidNaN() {
		color.setB(Double.NaN);
	}

	@Theory
	public void testSetGetG(@DoubleData(vals = {0d, 0.2, 1d}) final double value) {
		color.setG(value);
		assertEquals(value, color.getG(), 0.0001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetGInvalidGreater() {
		color.setG(1.001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetGInvalidLower() {
		color.setG(-0.001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetGInvalidNaN() {
		color.setG(Double.NaN);
	}

	@Theory
	public void testSetGetR(@DoubleData(vals = {0d, 0.2, 1d}) final double value) {
		color.setR(value);
		assertEquals(value, color.getR(), 0.0001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetRInvalidGreater() {
		color.setR(1.001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetRInvalidLower() {
		color.setR(-0.001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetGetRInvalidNaN() {
		color.setR(Double.NaN);
	}

	@Theory
	public void testToJFX(@DoubleData(vals = {0d, 0.21, 0.51, 1d}) final double o,
						@DoubleData(vals = {0d, 0.22, 0.52, 1d}) final double b,
						@DoubleData(vals = {0d, 0.23, 0.53, 1d}) final double r,
						@DoubleData(vals = {0d, 0.24, 0.54, 1d}) final double g) {
		color.setO(o);
		color.setB(b);
		color.setR(r);
		color.setG(g);
		assertEquals(o, color.toJFX().getOpacity(), 0.0001);
		assertEquals(b, color.toJFX().getBlue(), 0.0001);
		assertEquals(r, color.toJFX().getRed(), 0.0001);
		assertEquals(g, color.toJFX().getGreen(), 0.0001);
	}

	@Theory
	public void testToAWT(@DoubleData(vals = {0d, 0.21, 0.51, 1d}) final double o,
						@DoubleData(vals = {0d, 0.22, 0.52, 1d}) final double b,
						@DoubleData(vals = {0d, 0.23, 0.53, 1d}) final double r,
						@DoubleData(vals = {0d, 0.24, 0.54, 1d}) final double g) {
		color.setO(o);
		color.setB(b);
		color.setR(r);
		color.setG(g);
		assertEquals(Math.round(o * 255d), color.toAWT().getAlpha(), 0.0001);
		assertEquals(Math.round(b * 255d), color.toAWT().getBlue(), 0.0001);
		assertEquals(Math.round(r * 255d), color.toAWT().getRed(), 0.0001);
		assertEquals(Math.round(g * 255d), color.toAWT().getGreen(), 0.0001);
	}

	@Theory
	public void testEqualsOK(@DoubleData(vals = {0d, 0.21, 0.51, 1d}) final double o,
							 @DoubleData(vals = {0d, 0.22, 0.52, 1d}) final double b,
							 @DoubleData(vals = {0d, 0.23, 0.53, 1d}) final double r,
							 @DoubleData(vals = {0d, 0.24, 0.54, 1d}) final double g) {
		color.setO(o);
		color.setB(b);
		color.setR(r);
		color.setG(g);
		assertEquals(color, ShapeFactory.INST.createColor(r, g, b, o));
	}

	@Theory
	public void testEqualsNOK(@DoubleData(vals = {0d, 0.21, 0.51, 1d}) final double o,
							@DoubleData(vals = {0d, 0.22, 0.52, 1d}) final double b,
							@DoubleData(vals = {0d, 0.23, 0.53, 1d}) final double r,
							@DoubleData(vals = {0d, 0.24, 0.54, 1d}) final double g) {
		color.setO(o);
		color.setB(b);
		color.setR(r);
		color.setG(g);
		assertNotEquals(ShapeFactory.INST.createColor(0.2, 0.7, 0.1, 0.2), color);
		assertNotEquals(ShapeFactory.INST.createColor(0.3, 0.8, 0.1, 0.2), color);
		assertNotEquals(ShapeFactory.INST.createColor(0.2, 0.7, 0, 0.2), color);
		assertNotEquals(ShapeFactory.INST.createColor(0.2, 0.7, 0.1, 0.3), color);
		assertNotEquals(new Object(), color);
	}

	@Test
	public void testToString() {
		assertNotNull(color.toString());
	}
}
