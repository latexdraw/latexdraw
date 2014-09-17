package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.parsers.svg.SVGMatrix;
import net.sf.latexdraw.parsers.svg.SVGTransform;

import org.junit.Test;

public class TestSVGTransform{
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
			new SVGTransform(""); //$NON-NLS-1$
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
			t.setTransformation(""); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("iueozi"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t translate (		2\n \n\t	 4     )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_TRANSLATE);
		assertTrue(t.isTranslation());
		assertEquals(t.getTX(), 2., 0.0001);
		assertEquals(t.getTY(), 4., 0.0001);

		t.setTransformation("\n \t translate (		2\n \n\t ,	 4     )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_TRANSLATE);
		assertTrue(t.isTranslation());
		assertEquals(t.getTX(), 2., 0.0001);
		assertEquals(t.getTY(), 4., 0.0001);

		t.setTransformation("\n \t translate (		2\n \n\t )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_TRANSLATE);
		assertTrue(t.isTranslation());
		assertEquals(t.getTX(), 2., 0.0001);
		assertEquals(t.getTY(), 2., 0.0001);

		try {
			t.setTransformation("\n \t translate"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate("); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(ds"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(2 "); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(2 ,"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(2 , ds"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t translate(2 , 2"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t scale (		2\n \n\t	 4     )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SCALE);
		assertTrue(t.isScale());
		assertEquals(t.getXScaleFactor(), 2., 0.0001);
		assertEquals(t.getYScaleFactor(), 4., 0.0001);

		t.setTransformation("\n \t scale (		2\n \n\t ,	 4     )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SCALE);
		assertTrue(t.isScale());
		assertEquals(t.getXScaleFactor(), 2., 0.0001);
		assertEquals(t.getYScaleFactor(), 4., 0.0001);

		t.setTransformation("\n \t scale (		2\n \n\t )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SCALE);
		assertTrue(t.isScale());
		assertEquals(t.getXScaleFactor(), 2., 0.0001);
		assertEquals(t.getYScaleFactor(), 2., 0.0001);

		try {
			t.setTransformation("\n \t scale"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale("); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(ds"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(2 "); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(2 ,"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(2 , ds"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t scale(2 , 2"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t matrix (	2\n \n\t	 4  \t 5 \n 6 	7 	8    )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_MATRIX);
		m = t.getMatrix();
		assertEquals(m.getA(), 2., 0.0001);
		assertEquals(m.getB(), 4., 0.0001);
		assertEquals(m.getC(), 5., 0.0001);
		assertEquals(m.getD(), 6., 0.0001);
		assertEquals(m.getE(), 7., 0.0001);
		assertEquals(m.getF(), 8., 0.0001);

		t.setTransformation("\n \t matrix (	2 ,\n \n\t	 4   \t 5 ,\n 6  	7 ,	8     )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_MATRIX);
		m = t.getMatrix();
		assertEquals(m.getA(), 2., 0.0001);
		assertEquals(m.getB(), 4., 0.0001);
		assertEquals(m.getC(), 5., 0.0001);
		assertEquals(m.getD(), 6., 0.0001);
		assertEquals(m.getE(), 7., 0.0001);
		assertEquals(m.getF(), 8., 0.0001);

		try {
			t.setTransformation("\n \t matrix"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix("); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(ds"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 "); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 ,"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , ds"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , 2"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , 2 3"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , 2 3 5"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , 2 3  , 5  7"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t matrix(2 , 2 3 5  7 9"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t skewX (		2\n \n\t  )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SKEWX);
		assertTrue(t.isXSkew());
		assertEquals(t.getXSkewAngle(), 2., 0.0001);

		try {
			t.setTransformation("\n \t skewX"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewX("); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewX(ds"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewX(2 "); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t skewY (		3\n \n\t  )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SKEWY);
		assertTrue(t.isYSkew());
		assertEquals(t.getYSkewAngle(), 3., 0.0001);

		try {
			t.setTransformation("\n \t skewY"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewY("); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewY(ds"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t skewY(2 "); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		t.setTransformation("\n \t rotate (		3\n \n\t  )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_ROTATE);
		assertTrue(t.isRotation());
		assertEquals(t.getRotationAngle(), 3., 0.0001);

		t.setTransformation("\n \t rotate (		1\n \n\t , 4 	\n 6 \n \t )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_ROTATE);
		assertTrue(t.isRotation());
		assertEquals(t.getRotationAngle(), 1., 0.0001);
		m = t.getMatrix();

		try {
			t.setTransformation("\n \t rotate"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t rotate("); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t rotate(ds"); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t rotate(2 "); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }

		try {
			t.setTransformation("\n \t rotate(2 4 "); //$NON-NLS-1$
			fail();
		}
		catch(IllegalArgumentException e){ /**/ }
	}


	@Test
	public void testSetTranslate() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m = t.getMatrix();

		t.setTranslate(1, 2);
		assertEquals(m.getE(), 1., 0.0001);
		assertEquals(m.getF(), 2., 0.0001);
		assertTrue(t.isTranslation());
		assertFalse(t.isYSkew());
		assertFalse(t.isXSkew());
		assertFalse(t.isRotation());
		assertFalse(t.isScale());
		assertEquals(1., t.getTX(), 0.0001);
		assertEquals(2., t.getTY(), 0.0001);
		assertEquals(Double.NaN, t.getXScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getYScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getXSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getYSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getRotationAngle(), 0.0001);
		assertEquals(SVGTransform.SVG_TRANSFORM_TRANSLATE, t.getType());
	}


	@Test
	public void testSetScale() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m = t.getMatrix();

		t.setScale(3, 4);
		assertEquals(m.getA(), 3., 0.0001);
		assertEquals(m.getD(), 4., 0.0001);
		assertTrue(t.isScale());
		assertFalse(t.isYSkew());
		assertFalse(t.isXSkew());
		assertFalse(t.isRotation());
		assertFalse(t.isTranslation());
		assertEquals(Double.NaN, t.getTX(), 0.0001);
		assertEquals(Double.NaN, t.getTY(), 0.0001);
		assertEquals(3., t.getXScaleFactor(), 0.0001);
		assertEquals(4., t.getYScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getXSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getYSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getRotationAngle(), 0.0001);
		assertEquals(SVGTransform.SVG_TRANSFORM_SCALE, t.getType());
	}


	@Test
	public void testSetXSkew() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m = t.getMatrix();

		t.setSkewX(30);
		assertEquals(m.getC(), Math.tan(Math.toRadians(30)), 0.0001);
		assertEquals(t.getXSkewAngle(), 30., 0.0001);
		assertTrue(t.isXSkew());
		assertFalse(t.isScale());
		assertFalse(t.isYSkew());
		assertFalse(t.isRotation());
		assertFalse(t.isTranslation());
		assertEquals(Double.NaN, t.getTX(), 0.0001);
		assertEquals(Double.NaN, t.getTY(), 0.0001);
		assertEquals(Double.NaN, t.getXScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getYScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getYSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getRotationAngle(), 0.0001);
		assertEquals(SVGTransform.SVG_TRANSFORM_SKEWX, t.getType());
	}


	@Test
	public void testSetYSkew() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m = t.getMatrix();

		t.setSkewY(30);
		assertEquals(m.getB(), Math.tan(Math.toRadians(30)), 0.0001);
		assertEquals(t.getYSkewAngle(), 30., 0.0001);
		assertTrue(t.isYSkew());
		assertFalse(t.isScale());
		assertFalse(t.isXSkew());
		assertFalse(t.isRotation());
		assertFalse(t.isTranslation());
		assertEquals(Double.NaN, t.getTX(), 0.0001);
		assertEquals(Double.NaN, t.getTY(), 0.0001);
		assertEquals(Double.NaN, t.getXScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getYScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getXSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getRotationAngle(), 0.0001);
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
		assertEquals(Double.NaN, t.getTX(), 0.0001);
		assertEquals(Double.NaN, t.getTY(), 0.0001);
		assertEquals(Double.NaN, t.getXScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getYScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getXSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getYSkewAngle(), 0.0001);
		assertEquals(SVGTransform.SVG_TRANSFORM_ROTATE, t.getType());
		m = t.getMatrix();
		assertEquals(t.getRotationAngle(), 2., 0.0001);
		assertEquals(m.getE(), 0., 0.0001);
		assertEquals(m.getF(), 0., 0.0001);
		t.setRotate(2, 3, 4);
		assertEquals(t.getRotationAngle(), 2., 0.0001);
		m = t.getMatrix();
		assertEquals(m.getE(), 3., 0.0001);
		assertEquals(m.getF(), 4., 0.0001);
	}


	public void testSetMatrix() {
		SVGTransform t = new SVGTransform();
		SVGMatrix m = t.getMatrix();

		t.setMatrix(2, 3, 4, 5, 6, 7);
		assertEquals(m.getA(), 2., 0.0001);
		assertEquals(m.getB(), 3., 0.0001);
		assertEquals(m.getC(), 4., 0.0001);
		assertEquals(m.getD(), 5., 0.0001);
		assertEquals(m.getE(), 6., 0.0001);
		assertEquals(m.getF(), 7., 0.0001);
		assertEquals(SVGTransform.SVG_TRANSFORM_MATRIX, t.getType());
		assertEquals(Double.NaN, t.getTX(), 0.0001);
		assertEquals(Double.NaN, t.getTY(), 0.0001);
		assertEquals(Double.NaN, t.getXScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getYScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getXSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getYSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getRotationAngle(), 0.0001);
		assertFalse(t.isRotation());
		assertFalse(t.isScale());
		assertFalse(t.isXSkew());
		assertFalse(t.isYSkew());
		assertFalse(t.isTranslation());
	}
}
