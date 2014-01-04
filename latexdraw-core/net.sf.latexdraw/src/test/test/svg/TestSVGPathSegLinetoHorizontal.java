package test.svg;

import java.text.ParseException;

import junit.framework.TestCase;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLinetoHorizontal;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;

import org.junit.Test;

public class TestSVGPathSegLinetoHorizontal extends TestCase implements SVGPathHandler {
	protected final SVGPathSegLinetoHorizontal seg = new SVGPathSegLinetoHorizontal(-1, false);
	protected int cpt = 0;

	@Test
	public void testGetters() {
		assertEquals(seg.getX(), -1.);
		assertFalse(seg.isRelative());
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

		assertTrue(pathSeg instanceof SVGPathSegLinetoHorizontal);

		SVGPathSegLinetoHorizontal seg2 = (SVGPathSegLinetoHorizontal)pathSeg;

		assertEquals(seg.getX(), seg2.getX());
		assertEquals(seg.isRelative(), seg2.isRelative());
	}
}
