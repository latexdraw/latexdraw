package test.svg;

import java.text.ParseException;

import junit.framework.TestCase;

import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegArc;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg.PathSeg;

import org.junit.Test;

public class TestSVGPathSegArc extends TestCase implements SVGPathHandler {
	protected final SVGPathSegArc seg = new SVGPathSegArc(1, 2, 3, 4, 5, true, false, true);
	protected int cpt = 0;

	@Test
	public void testGetters() {
		SVGPathSegArc seg2 = new SVGPathSegArc(1, 2, 3, 4, 5, false, true, false);

		assertEquals(seg.getAngle(), 5.);
		assertEquals(seg.getRX(), 3.);
		assertEquals(seg.getRY(), 4.);
		assertEquals(seg.getX(), 1.);
		assertEquals(seg.getY(), 2.);
		assertTrue(seg.isLargeArcFlag());
		assertFalse(seg.isSweepFlag());
		assertTrue(seg.isRelative());
		assertFalse(seg2.isLargeArcFlag());
		assertTrue(seg2.isSweepFlag());
		assertFalse(seg2.isRelative());
		assertEquals(seg.getType(), PathSeg.ARC_REL);
		assertEquals(seg2.getType(), PathSeg.ARC_ABS);
	}


	@Test
	public void testToString() throws ParseException {
		SVGPathSegMoveto m = new SVGPathSegMoveto(0, 0, false);
		SVGPathParser parser = new SVGPathParser(m.toString() + " " + seg.toString(), this);

		parser.parse();
	}


	@Override
	public void onPathSeg(SVGPathSeg pathSeg) {
		if((pathSeg instanceof SVGPathSegMoveto) && cpt==0) {
			cpt++;
			return ;
		}

		assertTrue(pathSeg instanceof SVGPathSegArc);

		SVGPathSegArc seg2 = (SVGPathSegArc)pathSeg;

		assertEquals(seg.getAngle(), seg2.getAngle());
		assertEquals(seg.getRX(), seg2.getRX());
		assertEquals(seg.getRY(), seg2.getRY());
		assertEquals(seg.getX(), seg2.getX());
		assertEquals(seg.getY(), seg2.getY());
		assertEquals(seg.isLargeArcFlag(), seg2.isLargeArcFlag());
		assertEquals(seg.isRelative(), seg2.isRelative());
		assertEquals(seg.isSweepFlag(), seg2.isSweepFlag());
	}
}
