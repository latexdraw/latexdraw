package net.sf.latexdraw.parser.svg;

import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.parser.svg.path.SVGPathSegCurvetoCubicSmooth;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(LatexdrawExtension.class)
public class TestSVGPathSegCurvetoCubicSmooth {
	SVGPathSegCurvetoCubicSmooth seg;

	@BeforeEach
	public void setUp() {
		seg = new SVGPathSegCurvetoCubicSmooth(0.8E2, -2d, -5.e-1, .5, false);
	}

	@Test
	public void testConstructor() {
		assertEquals(0.8E2, seg.getX(), 0.0001);
		assertEquals(-2d, seg.getY(), 0.0001);
		assertFalse(seg.isRelative());
	}

	@Test
	public void testToString() {
		final AtomicBoolean done = new AtomicBoolean(false);
		final SVGPathSegMoveto m = new SVGPathSegMoveto(0d, 0d, false);
		SVGParserUtils.INSTANCE.parseSVGPath(m.toString() + " " + seg.toString(), pathSeg -> {
			if(pathSeg instanceof SVGPathSegMoveto) {
				return;
			}
			done.set(true);
			final SVGPathSegCurvetoCubicSmooth seg2 = (SVGPathSegCurvetoCubicSmooth) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		assertTrue(done.get());
	}
}
