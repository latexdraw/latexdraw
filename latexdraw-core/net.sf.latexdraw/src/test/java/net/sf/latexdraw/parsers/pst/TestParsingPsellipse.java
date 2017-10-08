package net.sf.latexdraw.parsers.pst;

import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestParsingPsellipse extends TestPSTParser {
	@Test
	public void testUnit() {
		parser("\\psset{unit=4}\\psellipse(2,3cm)(2cm,5cm)");
		IEllipse ell = getShapeAt(0);
		assertEquals(4d * 2d * IShape.PPC - 2d * IShape.PPC, ell.getX(), 0.0001);
		assertEquals(-3d * IShape.PPC + 5d * IShape.PPC, ell.getY(), 0.0001);
		assertEquals(2d * 2d * IShape.PPC, ell.getWidth(), 0.0001);
		assertEquals(2d * 5d * IShape.PPC, ell.getHeight(), 0.0001);
	}

	@Test
	public void testCoordinatesPt() {
		parser("\\psellipse(0,0)(35pt,20pt)");
		IEllipse ell = getShapeAt(0);
		assertEquals(-35d * IShape.PPC / PSTricksConstants.CM_VAL_PT, ell.getPosition().getX(), 0.001);
		assertEquals(-20d * IShape.PPC / PSTricksConstants.CM_VAL_PT * -1d, ell.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2d, ell.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void testCoordinatesMm() {
		parser("\\psellipse(0,0)(350mm,200mm)");
		IEllipse ell = getShapeAt(0);
		assertEquals(-35d * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(-20d * IShape.PPC * -1d, ell.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC * 2d, ell.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void testCoordinatesInch() {
		parser("\\psellipse(0,0)(35in,20in)");
		IEllipse ell = getShapeAt(0);
		assertEquals(-35d * IShape.PPC / 2.54, ell.getPosition().getX(), 0.001);
		assertEquals(-20d * IShape.PPC / 2.54 * -1d, ell.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC / 2.54 * 2d, ell.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC / 2.54 * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void testCoordinatesCm() {
		parser("\\psellipse(0,0)(35cm,20cm)");
		IEllipse ell = getShapeAt(0);
		assertEquals(-35d * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(-20d * IShape.PPC * -1d, ell.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC * 2d, ell.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void test1Coordinates() {
		parser("\\psellipse(35,20)");
		IEllipse ell = getShapeAt(0);
		assertEquals(-35d * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(-20d * IShape.PPC * -1d, ell.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC * 2d, ell.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesInt() {
		parser("\\psellipse(10,20)(35,50)");
		IEllipse ell = getShapeAt(0);
		assertEquals(10d * IShape.PPC - 35d * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(20d * -IShape.PPC + 50d * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC * 2d, ell.getWidth(), 0.001);
		assertEquals(50d * IShape.PPC * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesFloatSigns2() {
		parser("\\psellipse(-+.5,+-5)(+++35.5,--50.5)");
		IEllipse ell = getShapeAt(0);
		assertEquals(-.5 * IShape.PPC - 35.5 * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(-5d * -IShape.PPC + 50.5 * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2d, ell.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesFloatSigns() {
		parser("\\psellipse(-+-.5,+--.5)(+++35.5,--50.5)");
		IEllipse ell = getShapeAt(0);
		assertEquals(.5 * IShape.PPC - 35.5 * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(.5 * -IShape.PPC + 50.5 * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2d, ell.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesFloat2() {
		parser("\\psellipse(.5,.5)(35.5,50.5)");
		IEllipse ell = getShapeAt(0);
		assertEquals(.5 * IShape.PPC - 35.5 * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(.5 * -IShape.PPC + 50.5 * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2d, ell.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesFloat() {
		parser("\\psellipse(10.5,20.5)(35.5,50.5)");
		IEllipse ell = getShapeAt(0);
		assertEquals(10.5 * IShape.PPC - 35.5 * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(20.5 * -IShape.PPC + 50.5 * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2d, ell.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesTwoFirstMissing() {
		parser("\\psellipse(,)(35,50)");
		IEllipse ell = getShapeAt(0);
		assertEquals(IShape.PPC - 35 * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(-IShape.PPC + 50d * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC * 2d, ell.getWidth(), 0.001);
		assertEquals(50d * IShape.PPC * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void test2CoordinatesTwoLastMissing() {
		parser("\\psellipse(0,0)(,)");
		IEllipse ell = getShapeAt(0);
		assertEquals(-IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC * 2d, ell.getWidth(), 0.001);
		assertEquals(IShape.PPC * 2d, ell.getHeight(), 0.001);
	}

	@Test
	public void test2WidthHeight0() {
		parser("\\psellipse(0,0)(0,0)");
		IEllipse ell = getShapeAt(0);
		assertTrue(ell.getWidth() > 0);
		assertTrue(ell.getHeight() > 0);
	}
}
