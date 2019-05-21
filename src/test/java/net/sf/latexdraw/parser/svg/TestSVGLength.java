package net.sf.latexdraw.parser.svg;

import net.sf.latexdraw.parser.svg.SVGLength.LengthType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSVGLength {
	@Test
	void testGetters() {
		final SVGLength l = new SVGLength(1, LengthType.MM, "1");
		assertEquals(1d, l.getValue(), 0.000001);
		assertEquals(LengthType.MM, l.getLengthType());
		assertEquals("1", l.getValueAsString());
	}
}
