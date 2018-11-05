package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSVGSkewXTransformation {
	@Test
	void testConstructorNULL() {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGSkewXTransformation(null));
	}

	@Test
	void testConstructorDouble() {
		final SVGTransform.SVGSkewXTransformation transfo = new SVGTransform.SVGSkewXTransformation("skewX(11.652)");
		assertEquals(SVGMatrix.createSkewX(11.652), transfo.matrix);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "iueozi", "\n \t skewX", "\n \t skewX(", "\n \t skewX(ds", "\n \t skewX(2 ", "\n \t skewX(2 ,", "\n \t skewX(2 , 2)"})
	void testBadStrings(final String code) {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGSkewXTransformation(code));
	}


	@Test
	void testTransfoOKWS() {
		final SVGTransform.SVGSkewXTransformation transfo = new SVGTransform.SVGSkewXTransformation("\n \t skewX (		2\n \n\t     )   \n");
		assertEquals(SVGMatrix.createSkewX(2d), transfo.matrix);
	}
}
