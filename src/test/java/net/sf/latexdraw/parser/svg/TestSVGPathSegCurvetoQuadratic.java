package net.sf.latexdraw.parser.svg;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.latexdraw.parser.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parser.svg.path.SVGPathSegCurvetoQuadratic;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestSVGPathSegCurvetoQuadratic {
	SVGPathSegCurvetoQuadratic seg;

	@BeforeEach
	void setUp() {
		seg = new SVGPathSegCurvetoQuadratic(3e1, 4e-1, -5e1, 6.5, true);
	}

	@Test
	void testGetters() {
		assertEquals(-5e1, seg.getX1(), 0.0001);
		assertEquals(6.5, seg.getY1(), 0.0001);
		assertEquals(3e1, seg.getX(), 0.0001);
		assertEquals(4e-1, seg.getY(), 0.0001);
		assertTrue(seg.isRelative());
	}

	@Test
	void testToString() throws ParseException {
		final AtomicBoolean done = new AtomicBoolean(false);
		final SVGPathSegMoveto m = new SVGPathSegMoveto(0d, 0d, false);
		final SVGPathParser parser = new SVGPathParser(m.toString() + " " + seg.toString(), pathSeg -> {
			if(pathSeg instanceof SVGPathSegMoveto) {
				return;
			}
			done.set(true);
			final SVGPathSegCurvetoQuadratic seg2 = (SVGPathSegCurvetoQuadratic) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getX1(), seg2.getX1(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.getY1(), seg2.getY1(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		parser.parse();
		assertTrue(done.get());
	}
}
