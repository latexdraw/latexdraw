package net.sf.latexdraw.parser.svg;

import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.parser.svg.path.SVGPathSegLinetoHorizontal;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(LatexdrawExtension.class)
public class TestSVGPathSegLinetoHorizontal {
	SVGPathSegLinetoHorizontal seg;

	@BeforeEach
	void setUp() {
		seg = new SVGPathSegLinetoHorizontal(-1d, false);
	}

	@Test
	void testGetters() {
		assertEquals(-1d, seg.getX(), 0.0001);
		assertFalse(seg.isRelative());
	}

	@Test
	void testToString() {
		final AtomicBoolean done = new AtomicBoolean(false);
		final SVGPathSegMoveto m = new SVGPathSegMoveto(0d, 0d, false);
		SVGParserUtils.INSTANCE.parseSVGPath(m.toString() + " " + seg.toString(), pathSeg -> {
			if(pathSeg instanceof SVGPathSegMoveto) {
				return;
			}
			done.set(true);
			final SVGPathSegLinetoHorizontal seg2 = (SVGPathSegLinetoHorizontal) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		assertTrue(done.get());
	}
}
