package net.sf.latexdraw.parser.svg;

import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.NoBadaboomCheck;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(LatexdrawExtension.class)
public class TestSVGPathSegMoveto {
	SVGPathSegMoveto seg;

	@BeforeEach
	public void setUp() {
		seg = new SVGPathSegMoveto(-1d, -2d, false);
	}

	@Test
	public void testGetters() {
		assertEquals(-1d, seg.getX(), 0.0001);
		assertEquals(-2d, seg.getY(), 0.0001);
		assertFalse(seg.isRelative());
	}

	@Test
	@NoBadaboomCheck
	public void testToString() {
		final AtomicBoolean done = new AtomicBoolean(false);
		SVGParserUtils.INSTANCE.parseSVGPath(seg.toString(), pathSeg -> {
			done.set(true);
			final SVGPathSegMoveto seg2 = (SVGPathSegMoveto) pathSeg;
			assertEquals(seg.getX(), seg2.getX(), 0.0001);
			assertEquals(seg.getY(), seg2.getY(), 0.0001);
			assertEquals(seg.isRelative(), seg2.isRelative());
		});
		assertTrue(done.get());
	}
}
