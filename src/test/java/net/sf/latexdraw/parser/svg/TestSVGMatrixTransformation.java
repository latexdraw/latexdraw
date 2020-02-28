package net.sf.latexdraw.parser.svg;

import net.sf.latexdraw.LatexdrawExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(LatexdrawExtension.class)
public class TestSVGMatrixTransformation {
	@Test
	void testConstructorNULL() {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGMatrixTransformation(null));
	}

	@ParameterizedTest
	@ValueSource(strings = {"\n \t matrix(12.32, 2, 4, 5 5.6 8.66)   \n", "matrix( \t12.32\n,   2,  4 ,  5   5.6\n  8.66  \t) \n"})
	void testTransfoOK(final String code) {
		final SVGTransform.SVGMatrixTransformation transfo = new SVGTransform.SVGMatrixTransformation(code);
		assertEquals(new SVGMatrix(12.32, 2, 4, 5, 5.6, 8.66), transfo.matrix);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "iueozi", "\n \t matrix", "\n \t matrix(", "\n \t matrix(ds", "\n \t matrix(2 ", "\n \t matrix(2 ,", "\n \t matrix(2 , 2"})
	void testBadStrings(final String code) {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGMatrixTransformation(code));
	}
}
