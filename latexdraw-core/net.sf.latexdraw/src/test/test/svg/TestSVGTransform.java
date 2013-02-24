package test.svg;

import junit.framework.TestCase;

import net.sf.latexdraw.parsers.svg.SVGMatrix;
import net.sf.latexdraw.parsers.svg.SVGTransform;

import org.junit.Test;

public class TestSVGTransform extends TestCase {
	@SuppressWarnings("unused")
	@Test
	public void testConstructors() {
		SVGTransform t = new SVGTransform();

		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_UNKNOWN);
		assertNotNull(t.getMatrix());

		try {
			new SVGTransform(null);
			fail();
		}
		catch(IllegalArgumentException e){ /* */ }

		try {
			new SVGTransform("");
			fail();
		}
		catch(IllegalArgumentException e){ /* */ }
	}


	@Test
	public void testSetTransformation() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m;

		try {
			t.setTransformation(null);
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("iueozi");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t translate (		2\n \n\t	 4     )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_TRANSLATE);
		assertTrue(t.isTranslation());
		assertEquals(t.getTX(), 2.);
		assertEquals(t.getTY(), 4.);

		t.setTransformation("\n \t translate (		2\n \n\t ,	 4     )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_TRANSLATE);
		assertTrue(t.isTranslation());
		assertEquals(t.getTX(), 2.);
		assertEquals(t.getTY(), 4.);

		t.setTransformation("\n \t translate (		2\n \n\t )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_TRANSLATE);
		assertTrue(t.isTranslation());
		assertEquals(t.getTX(), 2.);
		assertEquals(t.getTY(), 2.);

		try {
			t.setTransformation("\n \t translate");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(ds");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(2 ");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(2 ,");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(2 , ds");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(2 , 2");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t scale (		2\n \n\t	 4     )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SCALE);
		assertTrue(t.isScale());
		assertEquals(t.getXScaleFactor(), 2.);
		assertEquals(t.getYScaleFactor(), 4.);

		t.setTransformation("\n \t scale (		2\n \n\t ,	 4     )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SCALE);
		assertTrue(t.isScale());
		assertEquals(t.getXScaleFactor(), 2.);
		assertEquals(t.getYScaleFactor(), 4.);

		t.setTransformation("\n \t scale (		2\n \n\t )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SCALE);
		assertTrue(t.isScale());
		assertEquals(t.getXScaleFactor(), 2.);
		assertEquals(t.getYScaleFactor(), 2.);

		try {
			t.setTransformation("\n \t scale");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(ds");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(2 ");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(2 ,");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(2 , ds");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(2 , 2");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t matrix (	2\n \n\t	 4  \t 5 \n 6 	7 	8    )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_MATRIX);
		m = t.getMatrix();
		assertEquals(m.getA(), 2.);
		assertEquals(m.getB(), 4.);
		assertEquals(m.getC(), 5.);
		assertEquals(m.getD(), 6.);
		assertEquals(m.getE(), 7.);
		assertEquals(m.getF(), 8.);

		t.setTransformation("\n \t matrix (	2 ,\n \n\t	 4   \t 5 ,\n 6  	7 ,	8     )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_MATRIX);
		m = t.getMatrix();
		assertEquals(m.getA(), 2.);
		assertEquals(m.getB(), 4.);
		assertEquals(m.getC(), 5.);
		assertEquals(m.getD(), 6.);
		assertEquals(m.getE(), 7.);
		assertEquals(m.getF(), 8.);

		try {
			t.setTransformation("\n \t matrix");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(ds");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 ");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 ,");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , ds");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , 2");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , 2 3");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , 2 3 5");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , 2 3  , 5  7");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , 2 3 5  7 9");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t skewX (		2\n \n\t  )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SKEWX);
		assertTrue(t.isXSkew());
		assertEquals(t.getXSkewAngle(), 2.);

		try {
			t.setTransformation("\n \t skewX");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewX(");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewX(ds");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewX(2 ");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t skewY (		3\n \n\t  )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SKEWY);
		assertTrue(t.isYSkew());
		assertEquals(t.getYSkewAngle(), 3.);

		try {
			t.setTransformation("\n \t skewY");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewY(");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewY(ds");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewY(2 ");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t rotate (		3\n \n\t  )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_ROTATE);
		assertTrue(t.isRotation());
		assertEquals(t.getRotationAngle(), 3.);

		t.setTransformation("\n \t rotate (		1\n \n\t , 4 	\n 6 \n \t )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_ROTATE);
		assertTrue(t.isRotation());
		assertEquals(t.getRotationAngle(), 1.);
		m = t.getMatrix();

		try {
			t.setTransformation("\n \t rotate");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t rotate(");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t rotate(ds");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t rotate(2 ");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t rotate(2 4 ");
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }
	}


	@Test
	public void testSetTranslate() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m = t.getMatrix();

		t.setTranslate(1, 2);
		assertEquals(m.getE(), 1.);
		assertEquals(m.getF(), 2.);
		assertTrue(t.isTranslation());
		assertFalse(t.isYSkew());
		assertFalse(t.isXSkew());
		assertFalse(t.isRotation());
		assertFalse(t.isScale());
		assertEquals(1., t.getTX());
		assertEquals(2., t.getTY());
		assertEquals(Double.NaN, t.getXScaleFactor());
		assertEquals(Double.NaN, t.getYScaleFactor());
		assertEquals(Double.NaN, t.getXSkewAngle());
		assertEquals(Double.NaN, t.getYSkewAngle());
		assertEquals(Double.NaN, t.getRotationAngle());
		assertEquals(SVGTransform.SVG_TRANSFORM_TRANSLATE, t.getType());
	}


	@Test
	public void testSetScale() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m = t.getMatrix();

		t.setScale(3, 4);
		assertEquals(m.getA(), 3.);
		assertEquals(m.getD(), 4.);
		assertTrue(t.isScale());
		assertFalse(t.isYSkew());
		assertFalse(t.isXSkew());
		assertFalse(t.isRotation());
		assertFalse(t.isTranslation());
		assertEquals(Double.NaN, t.getTX());
		assertEquals(Double.NaN, t.getTY());
		assertEquals(3., t.getXScaleFactor());
		assertEquals(4., t.getYScaleFactor());
		assertEquals(Double.NaN, t.getXSkewAngle());
		assertEquals(Double.NaN, t.getYSkewAngle());
		assertEquals(Double.NaN, t.getRotationAngle());
		assertEquals(SVGTransform.SVG_TRANSFORM_SCALE, t.getType());
	}


	@Test
	public void testSetXSkew() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m = t.getMatrix();

		t.setSkewX(30);
		assertEquals(m.getC(), Math.tan(Math.toRadians(30)));
		assertEquals(t.getXSkewAngle(), 30.);
		assertTrue(t.isXSkew());
		assertFalse(t.isScale());
		assertFalse(t.isYSkew());
		assertFalse(t.isRotation());
		assertFalse(t.isTranslation());
		assertEquals(Double.NaN, t.getTX());
		assertEquals(Double.NaN, t.getTY());
		assertEquals(Double.NaN, t.getXScaleFactor());
		assertEquals(Double.NaN, t.getYScaleFactor());
		assertEquals(Double.NaN, t.getYSkewAngle());
		assertEquals(Double.NaN, t.getRotationAngle());
		assertEquals(SVGTransform.SVG_TRANSFORM_SKEWX, t.getType());
	}


	@Test
	public void testSetYSkew() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m = t.getMatrix();

		t.setSkewY(30);
		assertEquals(m.getB(), Math.tan(Math.toRadians(30)));
		assertEquals(t.getYSkewAngle(), 30.);
		assertTrue(t.isYSkew());
		assertFalse(t.isScale());
		assertFalse(t.isXSkew());
		assertFalse(t.isRotation());
		assertFalse(t.isTranslation());
		assertEquals(Double.NaN, t.getTX());
		assertEquals(Double.NaN, t.getTY());
		assertEquals(Double.NaN, t.getXScaleFactor());
		assertEquals(Double.NaN, t.getYScaleFactor());
		assertEquals(Double.NaN, t.getXSkewAngle());
		assertEquals(Double.NaN, t.getRotationAngle());
		assertEquals(SVGTransform.SVG_TRANSFORM_SKEWY, t.getType());
	}


	@Test
	public void testSetRotate() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m;

		t.setRotate(2, 0, 0);
		assertTrue(t.isRotation());
		assertFalse(t.isScale());
		assertFalse(t.isXSkew());
		assertFalse(t.isYSkew());
		assertFalse(t.isTranslation());
		assertEquals(Double.NaN, t.getTX());
		assertEquals(Double.NaN, t.getTY());
		assertEquals(Double.NaN, t.getXScaleFactor());
		assertEquals(Double.NaN, t.getYScaleFactor());
		assertEquals(Double.NaN, t.getXSkewAngle());
		assertEquals(Double.NaN, t.getYSkewAngle());
		assertEquals(SVGTransform.SVG_TRANSFORM_ROTATE, t.getType());
		m = t.getMatrix();
		assertEquals(t.getRotationAngle(), 2.);
		assertEquals(m.getE(), 0.);
		assertEquals(m.getF(), 0.);
		t.setRotate(2, 3, 4);
		assertEquals(t.getRotationAngle(), 2.);
		m = t.getMatrix();
		assertEquals(m.getE(), 3.);
		assertEquals(m.getF(), 4.);
	}


	public void testSetMatrix() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m = t.getMatrix();

		t.setMatrix(2, 3, 4, 5, 6, 7);
		assertEquals(m.getA(), 2.);
		assertEquals(m.getB(), 3.);
		assertEquals(m.getC(), 4.);
		assertEquals(m.getD(), 5.);
		assertEquals(m.getE(), 6.);
		assertEquals(m.getF(), 7.);
		assertEquals(SVGTransform.SVG_TRANSFORM_MATRIX, t.getType());
		assertEquals(Double.NaN, t.getTX());
		assertEquals(Double.NaN, t.getTY());
		assertEquals(Double.NaN, t.getXScaleFactor());
		assertEquals(Double.NaN, t.getYScaleFactor());
		assertEquals(Double.NaN, t.getXSkewAngle());
		assertEquals(Double.NaN, t.getYSkewAngle());
		assertEquals(Double.NaN, t.getRotationAngle());
		assertFalse(t.isRotation());
		assertFalse(t.isScale());
		assertFalse(t.isXSkew());
		assertFalse(t.isYSkew());
		assertFalse(t.isTranslation());
	}
}
