package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parsers.svg.path.SVGPathHandler;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoQuadratic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;

import org.junit.Test;

public class TestSVGPathSegCurvetoQuadratic implements SVGPathHandler {
	protected final SVGPathSegCurvetoQuadratic seg = new SVGPathSegCurvetoQuadratic(3e1, 4e-1, -5e1, 6.5, true);
	protected int cpt = 0;

	@Test
	public void testGetters() {
		assertEquals(seg.getX1(), -5e1, 0.0001);
		assertEquals(seg.getY1(), 6.5, 0.0001);
		assertEquals(seg.getX(), 3e1, 0.0001);
		assertEquals(seg.getY(), 4e-1, 0.0001);
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

		assertTrue(pathSeg instanceof SVGPathSegCurvetoQuadratic);

		SVGPathSegCurvetoQuadratic seg2 = (SVGPathSegCurvetoQuadratic)pathSeg;

		assertEquals(seg.getX(), seg2.getX(), 0.0001);
		assertEquals(seg.getX1(), seg2.getX1(), 0.0001);
		assertEquals(seg.getY(), seg2.getY(), 0.0001);
		assertEquals(seg.getY1(), seg2.getY1(), 0.0001);
		assertEquals(seg.isRelative(), seg2.isRelative());
	}
}
