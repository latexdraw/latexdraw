package net.sf.latexdraw.view.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoQuadratic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSVGPathSegCurvetoQuadratic {
	SVGPathSegCurvetoQuadratic seg;
	int cpt;

	@Before
	public void setUp() throws Exception {
		cpt = 0;
		seg = new SVGPathSegCurvetoQuadratic(3e1, 4e-1, -5e1, 6.5, true);
	}

	@Test
	public void testGetters() {
		assertEquals(-5e1, seg.getX1(), 0.0001);
		assertEquals(6.5, seg.getY1(), 0.0001);
		assertEquals(3e1, seg.getX(), 0.0001);
		assertEquals(4e-1, seg.getY(), 0.0001);
		assertTrue(seg.isRelative());
	}

	@Test
	public void testToString() throws ParseException {
		SVGPathSegMoveto m = new SVGPathSegMoveto(0d, 0d, false);
		SVGPathParser parser = new SVGPathParser(m.toString() + " " + seg.toString(), pathSeg -> {
			if(pathSeg instanceof SVGPathSegMoveto && cpt == 0) {
				cpt++;
				return;
			}
			assertTrue(pathSeg instanceof SVGPathSegCurvetoQuadratic);
			SVGPathSegCurvetoQuadratic seg2 = (SVGPathSegCurvetoQuadratic) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getX1(), seg2.getX1(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.getY1(), seg2.getY1(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		parser.parse();
	}
}
