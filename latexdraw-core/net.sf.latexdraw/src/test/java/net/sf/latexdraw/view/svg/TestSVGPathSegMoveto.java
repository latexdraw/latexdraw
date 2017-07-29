package net.sf.latexdraw.view.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSVGPathSegMoveto implements SVGPathHandler {
	protected final SVGPathSegMoveto seg = new SVGPathSegMoveto(-1, -2, false);

	@Test
	public void testGetters() {
		assertEquals(seg.getX(), -1., 0.0001);
		assertEquals(seg.getY(), -2., 0.0001);
		assertFalse(seg.isRelative());
	}

	@Test
	public void testToString() throws ParseException {
		SVGPathParser parser = new SVGPathParser(seg.toString(), this);

		parser.parse();
	}

	@Override
	public void onPathSeg(SVGPathSeg pathSeg) {
		assertTrue(pathSeg instanceof SVGPathSegMoveto);

		SVGPathSegMoveto seg2 = (SVGPathSegMoveto)pathSeg;

		assertEquals(seg.getX(), seg2.getX(), 0.0001);
		assertEquals(seg.getY(), seg2.getY(), 0.0001);
		assertEquals(seg.isRelative(), seg2.isRelative());
	}
}
