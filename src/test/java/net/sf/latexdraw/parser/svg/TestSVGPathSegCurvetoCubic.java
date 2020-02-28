package net.sf.latexdraw.parser.svg;

import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.parser.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(LatexdrawExtension.class)
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
	void testToString() {
		final AtomicBoolean done = new AtomicBoolean(false);
		final SVGPathSegMoveto m = new SVGPathSegMoveto(0, 0, false);
		SVGParserUtils.INSTANCE.parseSVGPath(m.toString() + " " + seg.toString(), pathSeg -> {
			if(pathSeg instanceof SVGPathSegMoveto) {
				return;
			}
			done.set(true);
			final SVGPathSegCurvetoCubic seg2 = (SVGPathSegCurvetoCubic) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		assertTrue(done.get());
	}
}
