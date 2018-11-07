package net.sf.latexdraw.parser.svg;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.latexdraw.parser.svg.parsers.SVGPathParser;
import net.sf.latexdraw.parser.svg.path.SVGPathSegArc;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGPathSegArc {
	SVGPathSegArc seg;

	@BeforeEach
	void setUp() {
		seg = new SVGPathSegArc(1, 2, 3, 4, 5, true, false, true);
	}

	@Test
	void testGettersConstructor() {
		assertEquals(5d, seg.getAngle(), 0.0001);
		assertEquals(3d, seg.getRX(), 0.0001);
		assertEquals(4d, seg.getRY(), 0.0001);
		assertEquals(1d, seg.getX(), 0.0001);
		assertEquals(2d, seg.getY(), 0.0001);
		assertTrue(seg.isLargeArcFlag());
		assertFalse(seg.isSweepFlag());
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
			final SVGPathSegArc seg2 = (SVGPathSegArc) pathSeg;
			assertEquals(seg.getAngle(), seg2.getAngle(), 0.0001);
			assertEquals(seg.getRX(), seg2.getRX(), 0.0001);
			assertEquals(seg.getRY(), seg2.getRY(), 0.0001);
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isLargeArcFlag(), seg2.isLargeArcFlag());
			assertEquals(seg.isRelative(), seg2.isRelative());
			assertEquals(seg.isSweepFlag(), seg2.isSweepFlag());
		});
		parser.parse();
		assertTrue(done.get());
	}
}
