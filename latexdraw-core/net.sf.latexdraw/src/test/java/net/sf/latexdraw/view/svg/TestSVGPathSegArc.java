package net.sf.latexdraw.view.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegArc;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSVGPathSegArc implements SVGPathHandler {
	protected final SVGPathSegArc seg = new SVGPathSegArc(1, 2, 3, 4, 5, true, false, true);
	protected int cpt = 0;

	@Test
	public void testGetters() {
		SVGPathSegArc seg2 = new SVGPathSegArc(1, 2, 3, 4, 5, false, true, false);

		assertEquals(seg.getAngle(), 5., 0.0001);
		assertEquals(seg.getRX(), 3., 0.0001);
		assertEquals(seg.getRY(), 4., 0.0001);
		assertEquals(seg.getX(), 1., 0.0001);
		assertEquals(seg.getY(), 2., 0.0001);
		assertTrue(seg.isLargeArcFlag());
		assertFalse(seg.isSweepFlag());
		assertTrue(seg.isRelative());
		assertFalse(seg2.isLargeArcFlag());
		assertTrue(seg2.isSweepFlag());
		assertFalse(seg2.isRelative());
	}

	@Test
	public void testToString() throws ParseException {
		SVGPathSegMoveto m = new SVGPathSegMoveto(0, 0, false);
		SVGPathParser parser = new SVGPathParser(m.toString() + " " + seg.toString(), this);

		parser.parse();
	}

	@Override
	public void onPathSeg(SVGPathSeg pathSeg) {
		if(pathSeg instanceof SVGPathSegMoveto && cpt == 0) {
			cpt++;
			return;
		}

		assertTrue(pathSeg instanceof SVGPathSegArc);

		SVGPathSegArc seg2 = (SVGPathSegArc)pathSeg;

		assertEquals(seg.getAngle(), seg2.getAngle(), 0.0001);
		assertEquals(seg.getRX(), seg2.getRX(), 0.0001);
		assertEquals(seg.getRY(), seg2.getRY(), 0.0001);
		assertEquals(seg.getX(), seg2.getX(), 0.0001);
		assertEquals(seg.getY(), seg2.getY(), 0.0001);
		assertEquals(seg.isLargeArcFlag(), seg2.isLargeArcFlag());
		assertEquals(seg.isRelative(), seg2.isRelative());
		assertEquals(seg.isSweepFlag(), seg2.isSweepFlag());
	}
}
