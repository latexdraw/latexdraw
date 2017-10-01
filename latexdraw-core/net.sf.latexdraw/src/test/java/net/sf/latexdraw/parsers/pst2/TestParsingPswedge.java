package net.sf.latexdraw.parsers.pst2;

import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Theories.class)
public class TestParsingPswedge extends TestPSTParser {
	@Theory
	public void testAngle1Angle2(@DoubleData(vals = {100d, 200d, 20d, -200.15d}) final double angle1,
								 @DoubleData(vals = {100d, 200d, 20d, -200.15d}) final double angle2) {
		parser("\\pswedge{10}{" + angle1 + "}{" + angle2 + "}");
		IArc arc = getShapeAt(0);
		assertEquals(Math.toRadians(angle1), arc.getAngleStart(), 0.0000001);
		assertEquals(Math.toRadians(angle2), arc.getAngleEnd(), 0.0000001);
	}

	@Test
	public void testMissingOrigin() {
		parser("\\pswedge{10}{10}{20}");
		IArc arc = getShapeAt(0);
		assertEquals(-10d * IShape.PPC, arc.getPosition().getX(), 0.0000001);
		assertEquals(-10d * IShape.PPC * -1d, arc.getPosition().getY(), 0.0000001);
		assertEquals(10d * IShape.PPC * 2d, arc.getWidth(), 0.0000001);
		assertEquals(10d * IShape.PPC * 2d, arc.getHeight(), 0.0000001);
	}

	@Test
	public void testCoordinatesPt() {
		parser("\\pswedge(35pt,20pt){10pt}{10}{20}");
		IArc arc = getShapeAt(0);
		assertEquals(35d * IShape.PPC / PSTricksConstants.CM_VAL_PT - 10d * IShape.PPC / PSTricksConstants.CM_VAL_PT, arc.getPosition().getX(), 0.0000001);
		assertEquals((20d * IShape.PPC / PSTricksConstants.CM_VAL_PT - 10d * IShape.PPC / PSTricksConstants.CM_VAL_PT) * -1d, arc.getPosition().getY(), 0.0000001);
		assertEquals(10d * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2d, arc.getWidth(), 0.0000001);
		assertEquals(10d * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2d, arc.getHeight(), 0.0000001);
	}

	@Test
	public void testCoordinatesMm() {
		parser("\\pswedge(350mm,200mm){10mm}{10}{20}");
		IArc arc = getShapeAt(0);
		assertEquals(35d * IShape.PPC - 1d * IShape.PPC, arc.getPosition().getX(), 0.0000001);
		assertEquals((20d * IShape.PPC - 1d * IShape.PPC) * -1d, arc.getPosition().getY(), 0.0000001);
		assertEquals(1d * IShape.PPC * 2d, arc.getWidth(), 0.0000001);
		assertEquals(1d * IShape.PPC * 2d, arc.getHeight(), 0.0000001);
	}

	@Test
	public void testCoordinatesInch() {
		parser("\\pswedge(35in,20in){1.2in}{10}{20}");
		IArc arc = getShapeAt(0);
		assertEquals(35d * IShape.PPC / 2.54 - 1.2 * IShape.PPC / 2.54, arc.getPosition().getX(), 0.0000001);
		assertEquals((20d * IShape.PPC / 2.54 - 1.2 * IShape.PPC / 2.54) * -1d, arc.getPosition().getY(), 0.0000001);
		assertEquals(1.2 * IShape.PPC / 2.54 * 2d, arc.getWidth(), 0.0000001);
		assertEquals(1.2 * IShape.PPC / 2.54 * 2d, arc.getHeight(), 0.0000001);
	}

	@Test
	public void testCoordinatesCm() {
		parser("\\pswedge(35cm,20cm){.5cm}{10}{20}");
		IArc arc = getShapeAt(0);
		assertEquals(35d * IShape.PPC - .5 * IShape.PPC, arc.getPosition().getX(), 0.001);
		assertEquals((20d * IShape.PPC - .5 * IShape.PPC) * -1d, arc.getPosition().getY(), 0.001);
		assertEquals(.5 * IShape.PPC * 2d, arc.getWidth(), 0.0000001);
		assertEquals(.5 * IShape.PPC * 2d, arc.getHeight(), 0.0000001);
	}

	@Test
	public void testCoordinatesRadius() {
		parser("\\pswedge(35,20){10}{10}{20}");
		IArc arc = getShapeAt(0);
		assertEquals(35d * IShape.PPC - 10d * IShape.PPC, arc.getPosition().getX(), 0.0000001);
		assertEquals((20d * IShape.PPC - 10d * IShape.PPC) * -1d, arc.getPosition().getY(), 0.0000001);
		assertEquals(10d * IShape.PPC * 2d, arc.getWidth(), 0.0000001);
		assertEquals(10d * IShape.PPC * 2d, arc.getHeight(), 0.0000001);
	}

	@Test
	public void testFloatSigns() {
		parser("\\pswedge(+++35.5,--50.5){--+12}{10}{20}");
		IArc arc = getShapeAt(0);
		assertEquals(35.5 * IShape.PPC - 12d * IShape.PPC, arc.getPosition().getX(), 0.001);
		assertEquals((50.5 * IShape.PPC - 12d * IShape.PPC) * -1d, arc.getPosition().getY(), 0.001);
		assertEquals(12d * IShape.PPC * 2d, arc.getWidth(), 0.0000001);
		assertEquals(12d * IShape.PPC * 2d, arc.getHeight(), 0.0000001);
	}

	@Test
	public void testNegativeRadius() {
		parser("\\pswedge(0,0){-1}{10}{20}");
		IArc arc = getShapeAt(0);
		assertThat(arc.getWidth(), greaterThan(0d));
		assertThat(arc.getHeight(), greaterThan(0d));
	}

	@Test
	public void test2CoordinatesFloat2() {
		parser("\\pswedge(35.5,50.5){1.25}{10}{20}");
		IArc arc = getShapeAt(0);
		assertEquals(35.5 * IShape.PPC - 1.25 * IShape.PPC, arc.getPosition().getX(), 0.001);
		assertEquals((50.5 * IShape.PPC - 1.25 * IShape.PPC) * -1d, arc.getPosition().getY(), 0.001);
		assertEquals(1.25 * IShape.PPC * 2d, arc.getWidth(), 0.0000001);
		assertEquals(1.25 * IShape.PPC * 2d, arc.getHeight(), 0.0000001);
	}
}
