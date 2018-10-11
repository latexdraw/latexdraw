package net.sf.latexdraw.parsers.pst;

import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestParsingPspolygon extends TestPSTParser {
	@Test
	public void testParse2Coordinates() {
		parser("\\pspolygon(5,10)(15,20)");
		IPolygon line = getShapeAt(0);
		assertEquals(3, line.getNbPoints());
		assertEquals(0d, line.getPtAt(0).getX(), 0.0001);
		assertEquals(0d, line.getPtAt(0).getY(), 0.0001);
		assertEquals(5d * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-10d * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(15d * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesPt() {
		parser("\\pspolygon(35pt,20pt)(10pt,5pt)(-10pt,-5pt)");
		IPolygon line = getShapeAt(0);
		assertEquals(3, line.getNbPoints());
		assertEquals(35d * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(0).getY(), 0.0001);
		assertEquals(10d * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-5d * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-10d * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(2).getX(), 0.0001);
		assertEquals(5d * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(2).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesMm() {
		parser("\\pspolygon(350mm,200mm)(10mm, 30.3mm)(-10mm, -30.3mm)");
		IPolygon line = getShapeAt(0);
		assertEquals(3, line.getNbPoints());
		assertEquals(35d * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1d * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-3.03 * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-1d * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(3.03 * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesInch() {
		parser("\\pspolygon(35in,20in)(1.2in,0.2in)(-1.2in,-0.2in)");
		IPolygon line = getShapeAt(0);
		assertEquals(3, line.getNbPoints());
		assertEquals(35d * IShape.PPC / 2.54, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC / 2.54, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1.2 * IShape.PPC / 2.54, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-0.2 * IShape.PPC / 2.54, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-1.2 * IShape.PPC / 2.54, line.getPtAt(2).getX(), 0.0001);
		assertEquals(0.2 * IShape.PPC / 2.54, line.getPtAt(2).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesCm() {
		parser("\\pspolygon(35cm,20cm)(1.2cm,2cm)(-1.2cm,-2cm)");
		IPolygon line = getShapeAt(0);
		assertEquals(3, line.getNbPoints());
		assertEquals(35d * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20d * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1.2 * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-2d * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-1.2 * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(2d * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
	}

	@Test
	public void testFloatSigns() {
		parser("\\pspolygon(+++35.5,--50.5)(--+12, -1)(---+12, ++1)");
		IPolygon line = getShapeAt(0);
		assertEquals(3, line.getNbPoints());
		assertEquals(35.5 * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(12d * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(1d * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-12d * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(-1d * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
	}

	@Test
	public void testCoordinatesFloat2() {
		parser("\\pspolygon(35.5,50.5)(12, 1)(-12, -1)");
		IPolygon line = getShapeAt(0);
		assertEquals(3, line.getNbPoints());
		assertEquals(35.5 * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(12d * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-1d * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-12d * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(1d * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
	}
}
