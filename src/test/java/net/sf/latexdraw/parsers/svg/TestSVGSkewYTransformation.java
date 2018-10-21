package net.sf.latexdraw.parsers.svg;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSVGSkewYTransformation {
	@Test
	void testConstructorNULL() {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGSkewYTransformation(null));
	}

	@Test
	void testConstructorDouble() {
		final SVGTransform.SVGSkewYTransformation transfo = new SVGTransform.SVGSkewYTransformation("skewY(12.62)");
		assertEquals(SVGMatrix.createSkewY(12.62), transfo.matrix);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "iueozi", "\n \t skewY", "\n \t skewY(", "\n \t skewY(ds", "\n \t skewY(2 ", "\n \t skewY(2 ,", "\n \t skewY(2 , 2)"})
	void testBadStrings(final String code) {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGSkewYTransformation(code));
	}


	@Test
	void testTransfoOKWS() {
		final SVGTransform.SVGSkewYTransformation transfo = new SVGTransform.SVGSkewYTransformation("\n \t skewY (		21.65\n \n\t     )   \n");
		assertEquals(SVGMatrix.createSkewY(21.65), transfo.matrix);
	}
}
