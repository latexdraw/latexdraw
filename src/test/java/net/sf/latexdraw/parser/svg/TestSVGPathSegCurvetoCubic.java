package net.sf.latexdraw.parser.svg;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.latexdraw.parser.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parser.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGPathSegCurvetoCubic {
	SVGPathSegCurvetoCubic seg;

	@BeforeEach
	void setUp() {
		seg = new SVGPathSegCurvetoCubic(-1, -2, 3e1, 4e-1, -5e1, 6.5, true);
	}

	@Test
	void testConstructor() {
		assertEquals(-1d, seg.getX(), 0.0001);
		assertEquals(-2d, seg.getY(), 0.0001);
		assertTrue(seg.isRelative());
	}

	@Test
	void testToString() throws ParseException {
		final AtomicBoolean done = new AtomicBoolean(false);
		final SVGPathSegMoveto m = new SVGPathSegMoveto(0, 0, false);
		final SVGPathParser parser = new SVGPathParser(m.toString() + " " + seg.toString(), pathSeg -> {
			if(pathSeg instanceof SVGPathSegMoveto) {
				return;
			}
			done.set(true);
			final SVGPathSegCurvetoCubic seg2 = (SVGPathSegCurvetoCubic) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		parser.parse();
		assertTrue(done.get());
	}
}
