package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoQuadraticSmooth;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSVGPathSegCurvetoQuadraticSmooth {
	SVGPathSegCurvetoQuadraticSmooth seg;
	int cpt;

	@Before
	public void setUp() {
		seg = new SVGPathSegCurvetoQuadraticSmooth(-5.23e-10, 6.5, false);
		cpt = 0;
	}

	@Test
	public void testGetters() {
		assertEquals(-5.23e-10, seg.getX(), 0.0001);
		assertEquals(6.5, seg.getY(), 0.0001);
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
			assertTrue(pathSeg instanceof SVGPathSegCurvetoQuadraticSmooth);
			SVGPathSegCurvetoQuadraticSmooth seg2 = (SVGPathSegCurvetoQuadraticSmooth) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		parser.parse();
	}
}
