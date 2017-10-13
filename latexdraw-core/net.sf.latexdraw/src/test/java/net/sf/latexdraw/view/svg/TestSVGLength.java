package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.parsers.SVGLength;
import net.sf.latexdraw.parsers.svg.parsers.SVGLength.LengthType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSVGLength {
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor1() {
		new SVGLength(1, null, "1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor2() {
		new SVGLength(1, LengthType.CM, null);
	}

	@Test
	public void testGetters() {
		SVGLength l = new SVGLength(1, LengthType.MM, "1");
		assertEquals(1d, l.getValue(), 0.000001);
		assertEquals(LengthType.MM, l.getLengthType());
		assertEquals("1", l.getValueAsString());
	}
}
