package net.sf.latexdraw.parsers.pst2;

import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TestParsingPscircle extends TestPSTParser {
	@Test
	public void testUnit() {
		parser("\\psset{unit=4}\\pscircle(2,3cm){5}");
		ICircle cir = getShapeAt(0);
		assertEquals(4d * 2d * IShape.PPC - 5d * IShape.PPC, cir.getX(), 0.0001);
		assertEquals(-3d * IShape.PPC + 5d * IShape.PPC, cir.getY(), 0.0001);
		assertEquals(2d * 5d * IShape.PPC, cir.getWidth(), 0.0001);
	}

	@Test
	public void testCoordinatesPt() {
		parser("\\pscircle(35pt,20pt){10pt}");
		ICircle cir = getShapeAt(0);
		assertEquals(35d * IShape.PPC / PSTricksConstants.CM_VAL_PT - 10d * IShape.PPC / PSTricksConstants.CM_VAL_PT, cir.getPosition().getX(), 0.001);
		assertEquals((20d * IShape.PPC / PSTricksConstants.CM_VAL_PT - 10d * IShape.PPC / PSTricksConstants.CM_VAL_PT) * -1d, cir.getPosition().getY(), 0.001);
		assertEquals(10d * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2d, cir.getWidth(), 0.0000001);
		assertEquals(10d * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2d, cir.getHeight(), 0.0000001);
	}

	@Test
	public void testCoordinatesMm() {
		parser("\\pscircle(350mm,200mm){10mm}");
		ICircle cir = getShapeAt(0);
		assertEquals(35d * IShape.PPC - 1d * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((20d * IShape.PPC - 1d * IShape.PPC) * -1d, cir.getPosition().getY(), 0.001);
		assertEquals(1d * IShape.PPC * 2d, cir.getWidth(), 0.0000001);
		assertEquals(1d * IShape.PPC * 2d, cir.getHeight(), 0.0000001);
	}

	@Test
	public void testCoordinatesInch() {
		parser("\\pscircle(35in,20in){1.2in}");
		ICircle cir = getShapeAt(0);
		assertEquals(35d * IShape.PPC / 2.54 - 1.2 * IShape.PPC / 2.54, cir.getPosition().getX(), 0.001);
		assertEquals((20d * IShape.PPC / 2.54 - 1.2 * IShape.PPC / 2.54) * -1d, cir.getPosition().getY(), 0.001);
		assertEquals(1.2 * IShape.PPC / 2.54 * 2d, cir.getWidth(), 0.0000001);
		assertEquals(1.2 * IShape.PPC / 2.54 * 2d, cir.getHeight(), 0.0000001);
	}

	@Test
	public void testCoordinatesCm() {
		parser("\\pscircle(35cm,20cm){.5cm}");
		ICircle cir = getShapeAt(0);
		assertEquals(35d * IShape.PPC - .5 * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((20d * IShape.PPC - .5 * IShape.PPC) * -1d, cir.getPosition().getY(), 0.001);
		assertEquals(.5 * IShape.PPC * 2d, cir.getWidth(), 0.0000001);
		assertEquals(.5 * IShape.PPC * 2d, cir.getHeight(), 0.0000001);
	}

	@Test
	public void testCoordinatesRadius() {
		parser("\\pscircle(35,20){10}");
		ICircle cir = getShapeAt(0);
		assertEquals(35d * IShape.PPC - 10d * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((20d * IShape.PPC - 10d * IShape.PPC) * -1d, cir.getPosition().getY(), 0.001);
		assertEquals(10d * IShape.PPC * 2d, cir.getWidth(), 0.0000001);
		assertEquals(10d * IShape.PPC * 2d, cir.getHeight(), 0.0000001);
	}

	@Test
	public void testFloatSigns() {
		parser("\\pscircle(+++35.5,--50.5){--+12}");
		ICircle cir = getShapeAt(0);
		assertEquals(35.5 * IShape.PPC - 12d * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((50.5 * IShape.PPC - 12d * IShape.PPC) * -1d, cir.getPosition().getY(), 0.001);
		assertEquals(12d * IShape.PPC * 2d, cir.getWidth(), 0.0000001);
		assertEquals(12d * IShape.PPC * 2d, cir.getHeight(), 0.0000001);
	}

	@Test
	public void testNegativeRadius() {
		parser("\\pscircle(0,0){-1}");
		ICircle cir = getShapeAt(0);
		assertThat(cir.getWidth(), greaterThan(0d));
		assertThat(cir.getHeight(), greaterThan(0d));
	}

	@Test
	public void test2CoordinatesFloat2() {
		parser("\\pscircle(35.5,50.5){1.25}");
		ICircle cir = getShapeAt(0);
		assertEquals(35.5 * IShape.PPC - 1.25 * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((50.5 * IShape.PPC - 1.25 * IShape.PPC) * -1d, cir.getPosition().getY(), 0.001);
		assertEquals(1.25 * IShape.PPC * 2d, cir.getWidth(), 0.0000001);
		assertEquals(1.25 * IShape.PPC * 2d, cir.getHeight(), 0.0000001);
	}

	@Test
	public void testCoordinatesMissing() {
		parser("\\pscircle(,){1}");
		ICircle cir = getShapeAt(0);
		assertEquals(IShape.PPC - 1d * IShape.PPC, cir.getPosition().getX(), 0.0000001);
		assertEquals((IShape.PPC - 1d * IShape.PPC) * -1d, cir.getPosition().getY(), 0.0000001);
		assertEquals(1d * IShape.PPC * 2d, cir.getWidth(), 0.0000001);
		assertEquals(1d * IShape.PPC * 2d, cir.getHeight(), 0.0000001);
	}
}
