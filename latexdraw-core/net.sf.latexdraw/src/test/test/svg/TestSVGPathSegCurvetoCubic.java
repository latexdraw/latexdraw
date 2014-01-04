package test.svg;

import java.text.ParseException;

import junit.framework.TestCase;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;

import org.junit.Test;

public class TestSVGPathSegCurvetoCubic extends TestCase implements SVGPathHandler {
	protected final SVGPathSegCurvetoCubic seg = new SVGPathSegCurvetoCubic(-1, -2, 3e1, 4e-1, -5e1, 6.5, true);
	protected int cpt = 0;

	@Test
	public void testGetters() {
		assertEquals(seg.getX(), -1.);
		assertEquals(seg.getX1(), 3e1);
		assertEquals(seg.getX2(), -5e1);
		assertEquals(seg.getY(), -2.);
		assertEquals(seg.getY1(), 4e-1);
		assertEquals(seg.getY2(), 6.5);
		assertTrue(seg.isRelative());
	}


	@Test
	public void testToString() throws ParseException {
		SVGPathSegMoveto m = new SVGPathSegMoveto(0, 0, false);
		SVGPathParser parser = new SVGPathParser(m.toString() + " " + seg.toString(), this);

		parser.parse();
	}



	@Override
	public void onPathSeg(SVGPathSeg pathSeg) {
		if(pathSeg instanceof SVGPathSegMoveto && cpt==0) {
			cpt++;
			return ;
		}

		assertTrue(pathSeg instanceof SVGPathSegCurvetoCubic);

		SVGPathSegCurvetoCubic seg2 = (SVGPathSegCurvetoCubic)pathSeg;

		assertEquals(seg.getX(), seg2.getX());
		assertEquals(seg.getX1(), seg2.getX1());
		assertEquals(seg.getX2(), seg2.getX2());
		assertEquals(seg.getY(), seg2.getY());
		assertEquals(seg.getY1(), seg2.getY1());
		assertEquals(seg.getY2(), seg2.getY2());
		assertEquals(seg.isRelative(), seg2.isRelative());
	}
}
