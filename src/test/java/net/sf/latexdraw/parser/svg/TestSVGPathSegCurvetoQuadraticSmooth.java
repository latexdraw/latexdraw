package net.sf.latexdraw.parser.svg;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.latexdraw.parser.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parser.svg.path.SVGPathSegCurvetoQuadraticSmooth;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGPathSegCurvetoQuadraticSmooth {
	SVGPathSegCurvetoQuadraticSmooth seg;

	@BeforeEach
	public void setUp() {
		seg = new SVGPathSegCurvetoQuadraticSmooth(-5.23e-10, 6.5, false);
	}

	@Test
	public void testGetters() {
		assertEquals(-5.23e-10, seg.getX(), 0.0001);
		assertEquals(6.5, seg.getY(), 0.0001);
		assertFalse(seg.isRelative());
	}

	@Test
	public void testToString() throws ParseException {
		final AtomicBoolean done = new AtomicBoolean(false);
		final SVGPathSegMoveto m = new SVGPathSegMoveto(0d, 0d, false);
		final SVGPathParser parser = new SVGPathParser(m.toString() + " " + seg.toString(), pathSeg -> {
			if(pathSeg instanceof SVGPathSegMoveto) {
				return;
			}
			done.set(true);
			final SVGPathSegCurvetoQuadraticSmooth seg2 = (SVGPathSegCurvetoQuadraticSmooth) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		parser.parse();
		assertTrue(done.get());
	}
}
