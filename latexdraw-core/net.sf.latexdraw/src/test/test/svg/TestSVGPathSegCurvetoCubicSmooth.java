package test.svg;

import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubicSmooth;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class TestSVGPathSegCurvetoCubicSmooth implements SVGPathHandler {
	protected final SVGPathSegCurvetoCubicSmooth seg = new SVGPathSegCurvetoCubicSmooth(0.8E2, -2., -5.e-1, .5, false);
	protected int cpt = 0;

	@Test
	public void testGetters() {
		assertEquals(seg.getX(), 0.8E2, 0.0001);
		assertEquals(seg.getY(), -2., 0.0001);
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
		if(pathSeg instanceof SVGPathSegMoveto && cpt==0) {
			cpt++;
			return ;
		}

		assertTrue(pathSeg instanceof SVGPathSegCurvetoCubicSmooth);

		SVGPathSegCurvetoCubicSmooth seg2 = (SVGPathSegCurvetoCubicSmooth)pathSeg;

		assertEquals(seg.getX(), seg2.getX(), 0.0001);
		assertEquals(seg.getY(), seg2.getY(), 0.0001);
		assertEquals(seg.isRelative(), seg2.isRelative());
	}
}
