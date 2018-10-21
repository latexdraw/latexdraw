package net.sf.latexdraw.parsers.svg;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSVGScaleTransformation {
	@Test
	void testConstructorNULL() {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGScaleTransformation(null));
	}

	@Test
	void testConstructorDouble() {
		final SVGTransform.SVGScaleTransformation transfo = new SVGTransform.SVGScaleTransformation("scale(12.32)");
		assertEquals(SVGMatrix.createScale(12.32, 12.32), transfo.matrix);
	}

	@ParameterizedTest
	@ValueSource(strings = {"\n \t scale (		2.1\n \n\t	 4.32     )   \n", "\n \t scale (		2.1\n \n\t ,	 4.32     )   \n"})
	void testTransfoOKWS(final String code) {
		final SVGTransform.SVGScaleTransformation transfo = new SVGTransform.SVGScaleTransformation(code);
		assertEquals(2.1, transfo.getXScaleFactor(), 0.0001);
		assertEquals(4.32, transfo.getYScaleFactor(), 0.0001);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "iueozi", "\n \t scale", "\n \t scale(", "\n \t scale(ds", "\n \t scale(2 ", "\n \t scale(2 ,", "\n \t scale(2 , 2"})
	void testBadStrings(final String code) {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGScaleTransformation(code));
	}
}
