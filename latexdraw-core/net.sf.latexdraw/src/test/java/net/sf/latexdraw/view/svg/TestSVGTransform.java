package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.SVGMatrix;
import net.sf.latexdraw.parsers.svg.SVGTransform;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNull() {
		new SVGTransform(null);
	}

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
		t.setTransformation("\n \t translate (		2\n \n\t	 4     )   \n");
		assertEquals(SVGTransform.SVG_TRANSFORM_TRANSLATE, t.getType());
		assertTrue(t.isTranslation());
		assertEquals(2d, t.getTX(), 0.0001);
		assertEquals(4d, t.getTY(), 0.0001);
	}

	@Test
	public void testSetTransformation2() {
		t.setTransformation("\n \t translate (		2\n \n\t ,	 4     )   \n");
		assertEquals(SVGTransform.SVG_TRANSFORM_TRANSLATE, t.getType());
		assertTrue(t.isTranslation());
		assertEquals(2d, t.getTX(), 0.0001);
		assertEquals(4d, t.getTY(), 0.0001);
	}

	@Test
	public void testSetTransformation3() {
		t.setTransformation("\n \t translate (		2\n \n\t )   \n");
		assertEquals(SVGTransform.SVG_TRANSFORM_TRANSLATE, t.getType());
		assertTrue(t.isTranslation());
		assertEquals(2d, t.getTX(), 0.0001);
		assertEquals(2d, t.getTY(), 0.0001);
	}

	@Test
	public void testSetTransformation4() {
		t.setTransformation("\n \t scale (		2\n \n\t	 4     )   \n");
		assertEquals(SVGTransform.SVG_TRANSFORM_SCALE, t.getType());
		assertTrue(t.isScale());
		assertEquals(2d, t.getXScaleFactor(), 0.0001);
		assertEquals(4d, t.getYScaleFactor(), 0.0001);
	}

	@Test
	public void testSetTransformation5() {
		t.setTransformation("\n \t scale (		2\n \n\t ,	 4     )   \n");
		assertEquals(SVGTransform.SVG_TRANSFORM_SCALE, t.getType());
		assertTrue(t.isScale());
		assertEquals(2d, t.getXScaleFactor(), 0.0001);
		assertEquals(4d, t.getYScaleFactor(), 0.0001);
	}

	@Test
	public void testSetTransformation6() {
		t.setTransformation("\n \t scale (		2\n \n\t )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SCALE);
		assertTrue(t.isScale());
		assertEquals(2d, t.getXScaleFactor(), 0.0001);
		assertEquals(2d, t.getYScaleFactor(), 0.0001);
	}

	@Test
	public void testSetTransformation7() {
		t.setTransformation("\n \t matrix (	2\n \n\t	 4  \t 5 \n 6 	7 	8    )   \n");
		assertEquals(SVGTransform.SVG_TRANSFORM_MATRIX, t.getType());
		m = t.getMatrix();
		assertEquals(2d, m.getA(), 0.0001);
		assertEquals(4d, m.getB(), 0.0001);
		assertEquals(5d, m.getC(), 0.0001);
		assertEquals(6d, m.getD(), 0.0001);
		assertEquals(7d, m.getE(), 0.0001);
		assertEquals(8d, m.getF(), 0.0001);
	}

	@Test
	public void testSetTransformation8() {
		t.setTransformation("\n \t matrix (	2 ,\n \n\t	 4   \t 5 ,\n 6  	7 ,	8     )   \n");
		assertEquals(SVGTransform.SVG_TRANSFORM_MATRIX, t.getType());
		m = t.getMatrix();
		assertEquals(2d, m.getA(), 0.0001);
		assertEquals(4d, m.getB(), 0.0001);
		assertEquals(5d, m.getC(), 0.0001);
		assertEquals(6d, m.getD(), 0.0001);
		assertEquals(7d, m.getE(), 0.0001);
		assertEquals(8d, m.getF(), 0.0001);
	}

	@Test
	public void testSetTransformation9() {
		t.setTransformation("\n \t skewX (		2\n \n\t  )   \n");
		assertEquals(SVGTransform.SVG_TRANSFORM_SKEWX, t.getType());
		assertTrue(t.isXSkew());
		assertEquals(2d, t.getXSkewAngle(), 0.0001);
	}

	@Test
	public void testSetTransformation10() {
		t.setTransformation("\n \t skewY (		3\n \n\t  )   \n");
		assertEquals(t.getType(), SVGTransform.SVG_TRANSFORM_SKEWY);
		assertTrue(t.isYSkew());
		assertEquals(3d, t.getYSkewAngle(), 0.0001);
	}

	@Test
	public void testSetTransformation11() {
		t.setTransformation("\n \t rotate (		3\n \n\t  )   \n");
		assertEquals(SVGTransform.SVG_TRANSFORM_ROTATE, t.getType());
		assertTrue(t.isRotation());
		assertEquals(3d, t.getRotationAngle(), 0.0001);
	}

	@Test
	public void testSetTransformation12() {
		t.setTransformation("\n \t rotate (		1\n \n\t , 4 	\n 6 \n \t )   \n");
		assertEquals(SVGTransform.SVG_TRANSFORM_ROTATE, t.getType());
		assertTrue(t.isRotation());
		assertEquals(1d, t.getRotationAngle(), 0.0001);
	}

	@Test
	public void testSetTranslate() {
		t.setTranslate(1d, 2d);
		assertEquals(1d, m.getE(), 0.0001);
		assertEquals(2d, m.getF(), 0.0001);
		assertTrue(t.isTranslation());
		assertFalse(t.isYSkew());
		assertFalse(t.isXSkew());
		assertFalse(t.isRotation());
		assertFalse(t.isScale());
		assertEquals(1d, t.getTX(), 0.0001);
		assertEquals(2d, t.getTY(), 0.0001);
		assertEquals(Double.NaN, t.getXScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getYScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getXSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getYSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getRotationAngle(), 0.0001);
		assertEquals(SVGTransform.SVG_TRANSFORM_TRANSLATE, t.getType());
	}

	@Test
	public void testSetScale() {
		t.setScale(3d, 4d);
		assertEquals(3d, m.getA(), 0.0001);
		assertEquals(4d, m.getD(), 0.0001);
		assertTrue(t.isScale());
		assertFalse(t.isYSkew());
		assertFalse(t.isXSkew());
		assertFalse(t.isRotation());
		assertFalse(t.isTranslation());
		assertEquals(Double.NaN, t.getTX(), 0.0001);
		assertEquals(Double.NaN, t.getTY(), 0.0001);
		assertEquals(3d, t.getXScaleFactor(), 0.0001);
		assertEquals(4d, t.getYScaleFactor(), 0.0001);
		assertEquals(Double.NaN, t.getXSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getYSkewAngle(), 0.0001);
		assertEquals(Double.NaN, t.getRotationAngle(), 0.0001);
		assertEquals(SVGTransform.SVG_TRANSFORM_SCALE, t.getType());
	}

	@Test
	public void testSetXSkew() {
		t.setSkewX(30d);
		assertEquals(Math.tan(Math.toRadians(30d)), m.getC(), 0.0001);
		assertEquals(30d, t.getXSkewAngle(), 0.0001);
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
		t.setSkewY(30d);
		assertEquals(Math.tan(Math.toRadians(30d)), m.getB(), 0.0001);
		assertEquals(30d, t.getYSkewAngle(), 0.0001);
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
		t.setRotate(2d, 0d, 0d);
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
		assertEquals(2d, t.getRotationAngle(), 0.0001);
		assertEquals(0d, m.getE(), 0.0001);
		assertEquals(0d, m.getF(), 0.0001);
		t.setRotate(2d, 3d, 4d);
		assertEquals(2d, t.getRotationAngle(), 0.0001);
		m = t.getMatrix();
		assertEquals(3d, m.getE(), 0.0001);
		assertEquals(4d, m.getF(), 0.0001);
	}

	@Test
	public void testSetMatrix() {
		t.setMatrix(2d, 3d, 4d, 5d, 6d, 7d);
		assertEquals(2d, m.getA(), 0.0001);
		assertEquals(3d, m.getB(), 0.0001);
		assertEquals(4d, m.getC(), 0.0001);
		assertEquals(5d, m.getD(), 0.0001);
		assertEquals(6d, m.getE(), 0.0001);
		assertEquals(7d, m.getF(), 0.0001);
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
