package net.sf.latexdraw.parser.svg;

import net.sf.latexdraw.parser.svg.parsers.SVGLength;
import net.sf.latexdraw.parser.svg.parsers.SVGLength.LengthType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSVGLength {
	@Test
	public void testConstructor1() {
		assertThrows(IllegalArgumentException.class, () -> new SVGLength(1, null, "1"));
	}

	@Test
	public void testConstructor2() {
		assertThrows(IllegalArgumentException.class, () -> new SVGLength(1, LengthType.CM, null));
	}

	@Test
	public void testGetters() {
		final SVGLength l = new SVGLength(1, LengthType.MM, "1");
		assertEquals(1d, l.getValue(), 0.000001);
		assertEquals(LengthType.MM, l.getLengthType());
		assertEquals("1", l.getValueAsString());
	}
}
