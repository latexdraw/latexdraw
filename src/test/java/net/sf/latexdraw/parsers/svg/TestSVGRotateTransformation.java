package net.sf.latexdraw.parsers.svg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSVGRotateTransformation {
	@Test
	void testConstructorNULL() {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGRotateTransformation(null));
	}

	@ParameterizedTest
	@ValueSource(strings = {"\n \t rotate(12.32, 2, 4)   \n", "rotate( \t12.32\n   2  4) \n"})
	void testTransfoOK(final String code) {
		final SVGTransform.SVGRotateTransformation transfo = new SVGTransform.SVGRotateTransformation(code);
		assertEquals(SVGMatrix.createRotate(Math.toRadians(12.32)).translate(2, 4), transfo.matrix);
	}

	@Test
	void testTransfoOK1Double() {
		final SVGTransform.SVGRotateTransformation transfo = new SVGTransform.SVGRotateTransformation(" rotate(12.32 )");
		assertEquals(SVGMatrix.createRotate(Math.toRadians(12.32)), transfo.matrix);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "iueozi", "\n \t rotate", "\n \t rotate(", "\n \t rotate(ds", "\n \t rotate(2 ", "\n \t rotate(2 ,", "\n \t rotate(2 , 2 , 1.2"})
	void testBadStrings(final String code) {
		assertThrows(IllegalArgumentException.class, () -> new SVGTransform.SVGRotateTransformation(code));
	}

	@Nested
	class WithDoubles {
		SVGTransform.SVGRotateTransformation transfo;

		@BeforeEach
		void setUp() {
			transfo = new SVGTransform.SVGRotateTransformation(11.2, 10.3, -23.5);
		}

		@Test
		void testConstructorDoubles() {
			assertEquals(SVGMatrix.createRotate(Math.toRadians(11.2)).translate(10.3, -23.5), transfo.matrix);
		}

		@Test
		void testGetAngle() {
			assertEquals(11.2, transfo.getRotationAngle());
		}

		@Test
		void testGetCx() {
			assertEquals(10.3, transfo.getCx());
		}

		@Test
		void testGetCy() {
			assertEquals(-23.5, transfo.getCy());
		}
	}
}
