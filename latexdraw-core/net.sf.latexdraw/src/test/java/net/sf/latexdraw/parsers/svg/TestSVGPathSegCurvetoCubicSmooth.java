package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubicSmooth;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSVGPathSegCurvetoCubicSmooth {
	SVGPathSegCurvetoCubicSmooth seg;
	int cpt;

	@Before
	public void setUp() {
		seg = new SVGPathSegCurvetoCubicSmooth(0.8E2, -2d, -5.e-1, .5, false);
		cpt = 0;
	}

	@Test
	public void testConstructor() {
		assertEquals(0.8E2, seg.getX(), 0.0001);
		assertEquals(-2d, seg.getY(), 0.0001);
		assertFalse(seg.isRelative());
	}

	@Test
	public void testToString() throws ParseException {
		SVGPathSegMoveto m = new SVGPathSegMoveto(0d, 0d, false);
		SVGPathParser parser = new SVGPathParser(m.toString() + " " + seg.toString(), pathSeg -> {
			if(pathSeg instanceof SVGPathSegMoveto && cpt == 0) {
				cpt++;
				return;
			}
			assertTrue(pathSeg instanceof SVGPathSegCurvetoCubicSmooth);
			SVGPathSegCurvetoCubicSmooth seg2 = (SVGPathSegCurvetoCubicSmooth) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});

		parser.parse();
	}
}
