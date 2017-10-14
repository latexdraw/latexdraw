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
		assertEquals(35.43307, UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.CM), 0.001);
	}

	@Test
	public void testToUserUnitMM() {
		assertEquals(3.543307, UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.MM), 0.001);
	}

	@Test
	public void testToUserUnitPT() {
		assertEquals(1.25, UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.PT), 0.001);
	}

	@Test
	public void testToUserUnitPC() {
		assertEquals(15d, UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.PC), 0.001);
	}

	@Test
	public void testToUserUnitIN() {
		assertEquals(90d, UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.IN), 0.001);
	}
}
