package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.parsers.svg.SVGMatrix;
import net.sf.latexdraw.parsers.svg.SVGTransform;

import org.junit.Before;
import org.junit.Test;

public class TestSVGTransform {
	SVGTransform t;
	SVGMatrix m;

	@Before
	public void setUp() {
		t = new SVGTransform();
		m = t.getMatrix();
	}

	@Test
	public void testConstructors() {
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_UNKNOWN);
		assertNotNull(t.getMatrix());
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNull() {
		new SVGTransform(null);
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorEmpty() {
		new SVGTransform("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationNull() {
		t.setTransformation(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationEmpty() {
		t.setTransformation("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid() {
		t.setTransformation("iueozi");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid1() {
		t.setTransformation("\n \t translate");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid2() {
		t.setTransformation("\n \t translate(");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid3() {
		t.setTransformation("\n \t translate(ds");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid4() {
		t.setTransformation("\n \t translate(2 ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid5() {
		t.setTransformation("\n \t translate(2 ,");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid6() {
		t.setTransformation("\n \t translate(2 , ds");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid7() {
		t.setTransformation("\n \t translate(2 , 2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid8() {
		t.setTransformation("\n \t scale");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid9() {
		t.setTransformation("\n \t scale(");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid10() {
		t.setTransformation("\n \t scale(ds");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid11() {
		t.setTransformation("\n \t scale(2 ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid12() {
		t.setTransformation("\n \t scale(2 ,");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid13() {
		t.setTransformation("\n \t scale(2 , ds");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid14() {
		t.setTransformation("\n \t scale(2 , 2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid15() {
		t.setTransformation("\n \t matrix");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid16() {
		t.setTransformation("\n \t matrix(");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid17() {
		t.setTransformation("\n \t matrix(ds");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid18() {
		t.setTransformation("\n \t matrix(2 ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid19() {
		t.setTransformation("\n \t matrix(2 ,");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid20() {
		t.setTransformation("\n \t matrix(2 , ds");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid21() {
		t.setTransformation("\n \t skewX(");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid22() {
		t.setTransformation("\n \t skewX");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid23() {
		t.setTransformation("\n \t skewY(");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid24() {
		t.setTransformation("\n \t skewY");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid25() {
		t.setTransformation("\n \t rotate");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransformationInvalid26() {
		t.setTransformation("\n \t rotate(");
	}

	@Test
	public void testSetTransformation() {
		t.setTransformation("\n \t translate (		2\n \n\t	 4     )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_TRANSLATE);
		assertTrue(t.isTranslation());
		assertEquals(t.getTX(), 2., 0.0001);
		assertEquals(t.getTY(), 4., 0.0001);
	}

	@Test
	public void testSetTransformation2() {
		t.setTransformation("\n \t translate (		2\n \n\t ,	 4     )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_TRANSLATE);
		assertTrue(t.isTranslation());
		assertEquals(t.getTX(), 2., 0.0001);
		assertEquals(t.getTY(), 4., 0.0001);
	}

	@Test
	public void testSetTransformation3() {
		t.setTransformation("\n \t translate (		2\n \n\t )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_TRANSLATE);
		assertTrue(t.isTranslation());
		assertEquals(t.getTX(), 2., 0.0001);
		assertEquals(t.getTY(), 2., 0.0001);
	}

	@Test
	public void testSetTransformation4() {
		t.setTransformation("\n \t scale (		2\n \n\t	 4     )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SCALE);
		assertTrue(t.isScale());
		assertEquals(t.getXScaleFactor(), 2., 0.0001);
		assertEquals(t.getYScaleFactor(), 4., 0.0001);
	}

	@Test
	public void testSetTransformation5() {
		t.setTransformation("\n \t scale (		2\n \n\t ,	 4     )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SCALE);
		assertTrue(t.isScale());
		assertEquals(t.getXScaleFactor(), 2., 0.0001);
		assertEquals(t.getYScaleFactor(), 4., 0.0001);
	}

	@Test
	public void testSetTransformation6() {
		t.setTransformation("\n \t scale (		2\n \n\t )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SCALE);
		assertTrue(t.isScale());
		assertEquals(t.getXScaleFactor(), 2., 0.0001);
		assertEquals(t.getYScaleFactor(), 2., 0.0001);
	}

	@Test
	public void testSetTransformation7() {
		t.setTransformation("\n \t matrix (	2\n \n\t	 4  \t 5 \n 6 	7 	8    )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_MATRIX);
		m = t.getMatrix();
		assertEquals(m.getA(), 2., 0.0001);
		assertEquals(m.getB(), 4., 0.0001);
		assertEquals(m.getC(), 5., 0.0001);
		assertEquals(m.getD(), 6., 0.0001);
		assertEquals(m.getE(), 7., 0.0001);
		assertEquals(m.getF(), 8., 0.0001);
	}

	@Test
	public void testSetTransformation8() {
		t.setTransformation("\n \t matrix (	2 ,\n \n\t	 4   \t 5 ,\n 6  	7 ,	8     )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_MATRIX);
		m = t.getMatrix();
		assertEquals(m.getA(), 2., 0.0001);
		assertEquals(m.getB(), 4., 0.0001);
		assertEquals(m.getC(), 5., 0.0001);
		assertEquals(m.getD(), 6., 0.0001);
		assertEquals(m.getE(), 7., 0.0001);
		assertEquals(m.getF(), 8., 0.0001);
	}

	@Test
	public void testSetTransformation9() {
		t.setTransformation("\n \t skewX (		2\n \n\t  )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SKEWX);
		assertTrue(t.isXSkew());
		assertEquals(t.getXSkewAngle(), 2., 0.0001);
	}

	@Test
	public void testSetTransformation10() {
		t.setTransformation("\n \t skewY (		3\n \n\t  )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SKEWY);
		assertTrue(t.isYSkew());
		assertEquals(t.getYSkewAngle(), 3., 0.0001);
	}

	@Test
	public void testSetTransformation11() {
		t.setTransformation("\n \t rotate (		3\n \n\t  )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_ROTATE);
		assertTrue(t.isRotation());
		assertEquals(t.getRotationAngle(), 3., 0.0001);
	}

	@Test
	public void testSetTransformation12() {
		t.setTransformation("\n \t rotate (		1\n \n\t , 4 	\n 6 \n \t )   \n"); //$NON-NLS-1$
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_ROTATE);
		assertTrue(t.isRotation());
		assertEquals(t.getRotationAngle(), 1., 0.0001);
	}

	@Test
	public void testSetTranslate() {
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

	@Test
	public void testSetMatrix() {
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
