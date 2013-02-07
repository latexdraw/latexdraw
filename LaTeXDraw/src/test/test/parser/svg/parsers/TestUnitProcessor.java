package test.parser.svg.parsers;

import junit.framework.TestCase;

import net.sf.latexdraw.parsers.svg.parsers.SVGLength;
import net.sf.latexdraw.parsers.svg.parsers.UnitProcessor;

import org.junit.Test;

public class TestUnitProcessor extends TestCase {
	@Test
	public void testToUserUnit() {
		try {
			UnitProcessor.INSTANCE.toUserUnit(1, null);
			fail();
		}
		catch(IllegalArgumentException e){ /* */ }

		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1., SVGLength.LengthType.CM), 35.43307);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1., SVGLength.LengthType.MM), 3.543307);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1., SVGLength.LengthType.PT), 1.25);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1., SVGLength.LengthType.PC), 15.);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(1., SVGLength.LengthType.IN), 90.);
	}
}
