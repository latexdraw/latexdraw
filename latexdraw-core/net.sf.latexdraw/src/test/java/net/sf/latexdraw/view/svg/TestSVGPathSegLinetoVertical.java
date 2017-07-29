package net.sf.latexdraw.view.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLinetoVertical;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSVGPathSegLinetoVertical implements SVGPathHandler {
	protected final SVGPathSegLinetoVertical seg = new SVGPathSegLinetoVertical(-1, false);
	protected int cpt = 0;

	@Test
	public void testGetters() {
		assertEquals(seg.getY(), -1., 0.0001);
		assertFalse(seg.isRelative());
	}

	@Test
	public void testToString() throws ParseException {
		SVGPathSegMoveto m = new SVGPathSegMoveto(0, 0, false);
		SVGPathParser parser = new SVGPathParser(m.toString() + " " + seg.toString(), this); //$NON-NLS-1$

		parser.parse();
	}

	@Override
	public void onPathSeg(SVGPathSeg pathSeg) {
		if(pathSeg instanceof SVGPathSegMoveto && cpt == 0) {
			cpt++;
			return;
		}

		assertTrue(pathSeg instanceof SVGPathSegLinetoVertical);

		SVGPathSegLinetoVertical seg2 = (SVGPathSegLinetoVertical)pathSeg;

		assertEquals(seg.getY(), seg2.getY(), 0.0001);
		assertEquals(seg.isRelative(), seg2.isRelative());
	}
}
