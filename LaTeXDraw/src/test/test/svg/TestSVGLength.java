package test.svg;

import junit.framework.TestCase;

import net.sf.latexdraw.parsers.svg.parsers.SVGLength;
import net.sf.latexdraw.parsers.svg.parsers.SVGLength.LengthType;

import org.junit.Test;

public class TestSVGLength extends TestCase {
	@SuppressWarnings("unused")
	@Test
	public void testConsctrutor() {
		try {
			new SVGLength(1, null, "1");
			fail();
		}
		catch(IllegalArgumentException e){ /* */ }

		try {
			new SVGLength(1, LengthType.CM, null);
			fail();
		}
		catch(IllegalArgumentException e){ /* */ }
	}


	@Test
	public void testGetters() {
		SVGLength l = new SVGLength(1, LengthType.MM, "1");

		assertEquals(l.getValue(), 1.);
		assertEquals(l.getLengthType(), LengthType.MM);
		assertEquals(l.getValueAsString(), "1");
	}
}
