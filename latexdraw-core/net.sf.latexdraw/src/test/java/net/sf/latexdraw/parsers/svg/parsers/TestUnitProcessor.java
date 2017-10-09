package net.sf.latexdraw.parsers.svg.parsers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUnitProcessor {
	@Test(expected = IllegalArgumentException.class)
	public void testToUserUnitNULL() {
		UnitProcessor.INSTANCE.toUserUnit(1d, null);
	}

	@Test
	public void testToUserUnitCM() {
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.CM), 35.43307, 0.001);
	}

	@Test
	public void testToUserUnitMM() {
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.MM), 3.543307, 0.001);
	}

	@Test
	public void testToUserUnitPT() {
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.PT), 1.25, 0.001);
	}

	@Test
	public void testToUserUnitPC() {
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.PC), 15d, 0.001);
	}

	@Test
	public void testToUserUnitIN() {
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.IN), 90d, 0.001);
	}
}
