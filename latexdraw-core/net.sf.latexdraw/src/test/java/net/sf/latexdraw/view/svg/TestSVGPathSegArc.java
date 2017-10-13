package net.sf.latexdraw.view.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegArc;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSVGPathSegArc {
	SVGPathSegArc seg;
	int cpt;

	@Before
	public void setUp() throws Exception {
		seg = new SVGPathSegArc(1, 2, 3, 4, 5, true, false, true);
		cpt = 0;
	}

	@Test
	public void testGettersConstructor() {
		assertEquals(5d, seg.getAngle(), 0.0001);
		assertEquals(3d, seg.getRX(), 0.0001);
		assertEquals(4d, seg.getRY(), 0.0001);
		assertEquals(1d, seg.getX(), 0.0001);
		assertEquals(2d, seg.getY(), 0.0001);
		assertTrue(seg.isLargeArcFlag());
		assertFalse(seg.isSweepFlag());
		assertTrue(seg.isRelative());
	}

	@Test
	public void testToString() throws ParseException {
		SVGPathSegMoveto m = new SVGPathSegMoveto(0, 0, false);
		SVGPathParser parser = new SVGPathParser(m.toString() + " " + seg.toString(), pathSeg -> {
			if(pathSeg instanceof SVGPathSegMoveto && cpt == 0) {
				cpt++;
				return;
			}
			assertTrue(pathSeg instanceof SVGPathSegArc);
			SVGPathSegArc seg2 = (SVGPathSegArc) pathSeg;
			assertEquals(seg.getAngle(), seg2.getAngle(), 0.0001);
			assertEquals(seg.getRX(), seg2.getRX(), 0.0001);
			assertEquals(seg.getRY(), seg2.getRY(), 0.0001);
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isLargeArcFlag(), seg2.isLargeArcFlag());
			assertEquals(seg.isRelative(), seg2.isRelative());
			assertEquals(seg.isSweepFlag(), seg2.isSweepFlag());
		});
		parser.parse();
	}
}
