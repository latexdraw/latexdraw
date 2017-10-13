package net.sf.latexdraw.view.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSVGPathSegMoveto {
	SVGPathSegMoveto seg;

	@Before
	public void setUp() throws Exception {
		seg = new SVGPathSegMoveto(-1d, -2d, false);
	}

	@Test
	public void testGetters() {
		assertEquals(-1d, seg.getX(), 0.0001);
		assertEquals(-2d, seg.getY(), 0.0001);
		assertFalse(seg.isRelative());
	}

	@Test
	public void testToString() throws ParseException {
		SVGPathParser parser = new SVGPathParser(seg.toString(), pathSeg -> {
			assertTrue(pathSeg instanceof SVGPathSegMoveto);
			SVGPathSegMoveto seg2 = (SVGPathSegMoveto) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		parser.parse();
	}
}
