package net.sf.latexdraw.parsers.pst;

import java.text.ParseException;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TestParsingPsdiamond extends TestPSTParser {
	@Test
	public void testCoordinatesPt() throws ParseException {
		parser("\\psdiamond(0,0)(35pt,20pt)");
		IRhombus rh = getShapeAt(0);
		assertEquals(-35d * IShape.PPC / PSTricksConstants.CM_VAL_PT, rh.getPosition().getX(), 0.001);
		assertEquals(-20d * IShape.PPC / PSTricksConstants.CM_VAL_PT * -1d, rh.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2d, rh.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testCoordinatesMm() throws ParseException {
		parser("\\psdiamond(0,0)(350mm,200mm)");
		IRhombus rh = getShapeAt(0);
		assertEquals(-35d * IShape.PPC, rh.getPosition().getX(), 0.001);
		assertEquals(-20d * IShape.PPC * -1d, rh.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC * 2d, rh.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testCoordinatesInch() throws ParseException {
		parser("\\psdiamond(0,0)(35in,20in)");
		IRhombus rh = getShapeAt(0);
		assertEquals(-35d * IShape.PPC / 2.54, rh.getPosition().getX(), 0.001);
		assertEquals(-20d * IShape.PPC / 2.54 * -1d, rh.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC / 2.54 * 2d, rh.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC / 2.54 * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testCoordinatesCm() throws ParseException {
		parser("\\psdiamond(0,0)(35cm,20cm)");
		IRhombus rh = getShapeAt(0);
		assertEquals(-35d * IShape.PPC, rh.getPosition().getX(), 0.001);
		assertEquals(-20d * IShape.PPC * -1d, rh.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC * 2d, rh.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testParse1Coordinates() throws ParseException {
		parser("\\psdiamond(35,20)");
		IRhombus rh = getShapeAt(0);
		assertEquals(-35d * IShape.PPC, rh.getPosition().getX(), 0.001);
		assertEquals(-20d * IShape.PPC * -1d, rh.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC * 2d, rh.getWidth(), 0.001);
		assertEquals(20d * IShape.PPC * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testParse2CoordinatesInt() throws ParseException {
		parser("\\psdiamond(10,20)(35,50)");
		IRhombus rh = getShapeAt(0);
		assertEquals(10d * IShape.PPC - 35d * IShape.PPC, rh.getPosition().getX(), 0.001);
		assertEquals(20d * -IShape.PPC + 50d * IShape.PPC, rh.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC * 2d, rh.getWidth(), 0.001);
		assertEquals(50d * IShape.PPC * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testParse2CoordinatesFloatSigns2() throws ParseException {
		parser("\\psdiamond(-+.5,+-5)(+++35.5,--50.5)");
		IRhombus rh = getShapeAt(0);
		assertEquals(-.5 * IShape.PPC - 35.5 * IShape.PPC, rh.getPosition().getX(), 0.001);
		assertEquals(-5d * -IShape.PPC + 50.5 * IShape.PPC, rh.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2d, rh.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testParse2CoordinatesFloatSigns() throws ParseException {
		parser("\\psdiamond(-+-.5,+--.5)(+++35.5,--50.5)");
		IRhombus rh = getShapeAt(0);
		assertEquals(.5 * IShape.PPC - 35.5 * IShape.PPC, rh.getPosition().getX(), 0.001);
		assertEquals(.5 * -IShape.PPC + 50.5 * IShape.PPC, rh.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2d, rh.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testParse2CoordinatesFloat2() throws ParseException {
		parser("\\psdiamond(.5,.5)(35.5,50.5)");
		IRhombus rh = getShapeAt(0);
		assertEquals(.5 * IShape.PPC - 35.5 * IShape.PPC, rh.getPosition().getX(), 0.001);
		assertEquals(.5 * -IShape.PPC + 50.5 * IShape.PPC, rh.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2d, rh.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testParse2CoordinatesFloat() throws ParseException {
		parser("\\psdiamond(10.5,20.5)(35.5,50.5)");
		IRhombus rh = getShapeAt(0);
		assertEquals(10.5 * IShape.PPC - 35.5 * IShape.PPC, rh.getPosition().getX(), 0.001);
		assertEquals(20.5 * -IShape.PPC + 50.5 * IShape.PPC, rh.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2d, rh.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testParse2CoordinatesTwoFirstMissing() throws ParseException {
		parser("\\psdiamond(,)(35,50)");
		IRhombus rh = getShapeAt(0);
		assertEquals(IShape.PPC - 35 * IShape.PPC, rh.getPosition().getX(), 0.001);
		assertEquals(-IShape.PPC + 50d * IShape.PPC, rh.getPosition().getY(), 0.001);
		assertEquals(35d * IShape.PPC * 2d, rh.getWidth(), 0.001);
		assertEquals(50d * IShape.PPC * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testParse2CoordinatesTwoLastMissing() throws ParseException {
		parser("\\psdiamond(0,0)(,)");
		IRhombus rh = getShapeAt(0);
		assertEquals(-IShape.PPC, rh.getPosition().getX(), 0.001);
		assertEquals(IShape.PPC, rh.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC * 2d, rh.getWidth(), 0.001);
		assertEquals(IShape.PPC * 2d, rh.getHeight(), 0.001);
	}

	@Test
	public void testParse2WidthHeight0() throws ParseException {
		parser("\\psdiamond(0,0)(0,0)");
		IRhombus rh = getShapeAt(0);
		assertThat(rh.getWidth(), greaterThan(0d));
		assertThat(rh.getHeight(), greaterThan(0d));
	}

	@Test
	public void testGangle() throws ParseException {
		parser("\\psdiamond[gangle=90](0,0)(1,1)");
		final IRhombus rh = getShapeAt(0);
		assertEquals(-Math.PI/2d, rh.getRotationAngle(), 0.001);
	}
}
