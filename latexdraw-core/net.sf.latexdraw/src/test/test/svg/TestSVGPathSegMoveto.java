package test.svg;

import java.text.ParseException;

import junit.framework.TestCase;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;

import org.junit.Test;

public class TestSVGPathSegMoveto extends TestCase implements SVGPathHandler {
	protected final SVGPathSegMoveto seg = new SVGPathSegMoveto(-1, -2, false);

	@Test
	public void testGetters() {
		assertEquals(seg.getX(), -1.);
		assertEquals(seg.getY(), -2.);
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

		assertEquals(seg.getX(), seg2.getX());
		assertEquals(seg.getY(), seg2.getY());
		assertEquals(seg.isRelative(), seg2.isRelative());
	}
}
