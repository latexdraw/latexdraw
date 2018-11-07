package net.sf.latexdraw.parser.svg;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.latexdraw.parser.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parser.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGPathSegLineto {
	SVGPathSegLineto seg;

	@BeforeEach
	void setUp() {
		seg = new SVGPathSegLineto(-1d, -2d, true);
	}

	@Test
	void testGetters() {
		assertEquals(-1d, seg.getX(), 0.0001);
		assertEquals(-2d, seg.getY(), 0.0001);
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
			final SVGPathSegLineto seg2 = (SVGPathSegLineto) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		parser.parse();
		assertTrue(done.get());
	}
}
