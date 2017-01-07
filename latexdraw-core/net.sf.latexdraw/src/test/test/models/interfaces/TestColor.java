package test.models.interfaces;

import static org.junit.Assert.*;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.Color;

import org.junit.Before;
import org.junit.Test;

public class TestColor {
	protected Color color;

	@Before
	public void setUp() {
		color = ShapeFactory.INST.createColor();
	}

	@Test
	public void testSetGetO() {
		color.setO(0.2);
		assertEquals(0.2, color.getO(), 0.0001);
		color.setO(0);
		assertEquals(0, color.getO(), 0.0001);
		color.setO(1);
		assertEquals(1, color.getO(), 0.0001);
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

	@Test
	public void testSetGetB() {
		color.setB(0.2);
		assertEquals(0.2, color.getB(), 0.0001);
		color.setB(0);
		assertEquals(0, color.getB(), 0.0001);
		color.setB(1);
		assertEquals(1, color.getB(), 0.0001);
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

	@Test
	public void testSetGetG() {
		color.setG(0.2);
		assertEquals(0.2, color.getG(), 0.0001);
		color.setG(0);
		assertEquals(0, color.getG(), 0.0001);
		color.setG(1);
		assertEquals(1, color.getG(), 0.0001);
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

	@Test
	public void testSetGetR() {
		color.setR(0.2);
		assertEquals(0.2, color.getR(), 0.0001);
		color.setR(0);
		assertEquals(0, color.getR(), 0.0001);
		color.setR(1);
		assertEquals(1, color.getR(), 0.0001);
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

	@Test
	public void testtoJFX() {
		color.setO(0.2);
		color.setB(0.1);
		color.setR(0.3);
		color.setG(0.7);
		assertEquals(0.1, color.toJFX().getBlue(), 0.0001);
		assertEquals(0.3, color.toJFX().getRed(), 0.0001);
		assertEquals(0.7, color.toJFX().getGreen(), 0.0001);
		assertEquals(0.2, color.toJFX().getOpacity(), 0.0001);
	}

	@Test
	public void testtoAWT() {
		color.setO(0.2);
		color.setB(0.1);
		color.setR(0.3);
		color.setG(0.7);
		assertEquals(Math.ceil(0.1 * 255.0), color.toAWT().getBlue(), 0.0001);
		assertEquals(Math.ceil(0.3 * 255.0), color.toAWT().getRed(), 0.0001);
		assertEquals(Math.ceil(0.7 * 255.0), color.toAWT().getGreen(), 0.0001);
		assertEquals(Math.ceil(0.2 * 255.0), color.toAWT().getAlpha(), 0.0001);
	}

	@Test
	public void testEqualsOK() {
		color.setO(0.2);
		color.setB(0.1);
		color.setR(0.3);
		color.setG(0.7);
		assertTrue(color.equals(color));
		assertEquals(color, ShapeFactory.INST.createColor(0.3, 0.7, 0.1, 0.2));
	}

	@Test
	public void testEqualsNOK() {
		color.setO(0.2);
		color.setB(0.1);
		color.setR(0.3);
		color.setG(0.7);
		assertNotEquals(color, ShapeFactory.INST.createColor(0.2, 0.7, 0.1, 0.2));
		assertNotEquals(color, ShapeFactory.INST.createColor(0.3, 0.8, 0.1, 0.2));
		assertNotEquals(color, ShapeFactory.INST.createColor(0.2, 0.7, 0, 0.2));
		assertNotEquals(color, ShapeFactory.INST.createColor(0.2, 0.7, 0.1, 0.3));
		assertNotEquals(color, new Object());
	}

	@Test
	public void testtoString() {
		assertNotNull(color.toString());
	}
}
