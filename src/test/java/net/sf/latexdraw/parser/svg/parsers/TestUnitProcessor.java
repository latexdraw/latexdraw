package net.sf.latexdraw.parser.svg.parsers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUnitProcessor {
	@Test
	void testToUserUnitNULL() {
		assertThrows(IllegalArgumentException.class, () -> UnitProcessor.INSTANCE.toUserUnit(1d, null));
	}

	@Test
	void testToUserUnitCM() {
		assertEquals(35.43307, UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.CM), 0.001);
	}

	@Test
	void testToUserUnitMM() {
		assertEquals(3.543307, UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.MM), 0.001);
	}

	@Test
	void testToUserUnitPT() {
		assertEquals(1.25, UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.PT), 0.001);
	}

	@Test
	void testToUserUnitPC() {
		assertEquals(15d, UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.PC), 0.001);
	}

	@Test
	void testToUserUnitIN() {
		assertEquals(90d, UnitProcessor.INSTANCE.toUserUnit(1d, SVGLength.LengthType.IN), 0.001);
	}
}
