package net.sf.latexdraw.parsers.svg.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.sf.latexdraw.parsers.svg.parsers.SVGLength;
import net.sf.latexdraw.parsers.svg.parsers.UnitProcessor;

import org.junit.Test;

public class TestUnitProcessor {
	@Test
	public void testToUserUnit() {
		try {
			UnitProcessor.INSTANCE.toUserUnit(1, null);
			fail();
		}catch(IllegalArgumentException e) {
			/* */ }

		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1., SVGLength.LengthType.CM), 35.43307, 0.001);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1., SVGLength.LengthType.MM), 3.543307, 0.001);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1., SVGLength.LengthType.PT), 1.25, 0.001);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1., SVGLength.LengthType.PC), 15., 0.001);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1., SVGLength.LengthType.IN), 90., 0.001);
	}
}
