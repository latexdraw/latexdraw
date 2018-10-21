package net.sf.latexdraw.parsers.svg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSVGTranslateTransformation {
	@Test
	void testConstructorNULL() {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGTranslateTransformation(null));
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "iueozi", "\n \t translate", "\n \t translate(", "\n \t translate(ds", "\n \t translate(2 ", "\n \t translate(2 ,",
		"\n \t translate(2 , ds", "\n \t translate(2 , 2"})
	void testBadStrings(final String code) {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGTranslateTransformation(code));
	}

	@ParameterizedTest
	@ValueSource(strings = {"\n \t translate (		2\n \n\t	 4     )   \n", "\n \t translate (		2\n \n\t ,	 4     )   \n"})
	void testTransfoOKWS(final String code) {
		final SVGTransform.SVGTranslateTransformation transfo = new SVGTransform.SVGTranslateTransformation(code);
		assertEquals(2d, transfo.getTx(), 0.0001);
		assertEquals(4d, transfo.getTy(), 0.0001);
	}

	@Test
	void testTransfOK() {
		final SVGTransform.SVGTranslateTransformation transfo = new SVGTransform.SVGTranslateTransformation("translate(1.3,7.33)");
		assertEquals(1.3, transfo.getTx(), 0.0001);
		assertEquals(7.33, transfo.getTy(), 0.0001);
	}

	@Test
	void testTransoOK1Value() {
		final SVGTransform.SVGTranslateTransformation transfo = new SVGTransform.SVGTranslateTransformation("\n \t translate (		11.542\n \n\t )   \n");
		assertEquals(11.542, transfo.getTx(), 0.0001);
		assertEquals(11.542, transfo.getTy(), 0.0001);
	}

	@Nested
	class WithDoubles {
		SVGTransform.SVGTranslateTransformation transfo;

		@BeforeEach
		void setUp() {
			transfo = new SVGTransform.SVGTranslateTransformation(10d, 11d);
		}

		@Test
		void testConstructorDoubles() {
			assertEquals(SVGMatrix.createTranslate(10d, 11d), transfo.matrix);
		}

		@Test
		void testGetTy() {
			assertEquals(11d, transfo.getTy());
		}

		@Test
		void testGetTx() {
			assertEquals(10d, transfo.getTx());
		}
	}
}
