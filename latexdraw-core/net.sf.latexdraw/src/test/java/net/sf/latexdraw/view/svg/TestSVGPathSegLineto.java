package net.sf.latexdraw.view.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSVGPathSegLineto implements SVGPathHandler {
	protected final SVGPathSegLineto seg = new SVGPathSegLineto(-1, -2, true);
	protected int cpt = 0;

	@Test
	public void testGetters() {
		assertEquals(seg.getX(), -1., 0.0001);
		assertEquals(seg.getY(), -2., 0.0001);
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
		if(pathSeg instanceof SVGPathSegMoveto && cpt == 0) {
			cpt++;
			return;
		}

		assertTrue(pathSeg instanceof SVGPathSegLineto);

		SVGPathSegLineto seg2 = (SVGPathSegLineto)pathSeg;

		assertEquals(seg.getX(), seg2.getX(), 0.0001);
		assertEquals(seg.getY(), seg2.getY(), 0.0001);
		assertEquals(seg.isRelative(), seg2.isRelative());
	}
}
