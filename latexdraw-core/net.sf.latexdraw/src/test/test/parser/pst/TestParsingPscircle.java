package test.parser.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPscircle extends TestParsingShape {
	@Override
	public String getCommandName() {
		return "pscircle"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return "{1}"; //$NON-NLS-1$
	}

	@Test
	public void testUnit() throws ParseException {
		ICircle sh = (ICircle)parser.parsePSTCode("\\psset{unit=4}\\pscircle(2,3cm){5}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(4. * 2. * IShape.PPC - 5. * IShape.PPC, sh.getX(), 0.0001);
		assertEquals(-3. * IShape.PPC + 5. * IShape.PPC, sh.getY(), 0.0001);
		assertEquals(2. * 5. * IShape.PPC, sh.getWidth(), 0.0001);
	}

	@Test
	public void testCoordinatesPt() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(35pt,20pt){10pt}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(35. * IShape.PPC / PSTricksConstants.CM_VAL_PT - 10. * IShape.PPC / PSTricksConstants.CM_VAL_PT, cir.getPosition().getX(), 0.001);
		assertEquals((20. * IShape.PPC / PSTricksConstants.CM_VAL_PT - 10. * IShape.PPC / PSTricksConstants.CM_VAL_PT) * -1., cir.getPosition().getY(), 0.001);
		assertEquals(10. * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2., cir.getWidth(), 0.0000001);
		assertEquals(10. * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2., cir.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesMm() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(350mm,200mm){10mm}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(35. * IShape.PPC - 1. * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((20. * IShape.PPC - 1. * IShape.PPC) * -1., cir.getPosition().getY(), 0.001);
		assertEquals(1. * IShape.PPC * 2., cir.getWidth(), 0.0000001);
		assertEquals(1. * IShape.PPC * 2., cir.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesInch() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(35in,20in){1.2in}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(35. * IShape.PPC / 2.54 - 1.2 * IShape.PPC / 2.54, cir.getPosition().getX(), 0.001);
		assertEquals((20. * IShape.PPC / 2.54 - 1.2 * IShape.PPC / 2.54) * -1., cir.getPosition().getY(), 0.001);
		assertEquals(1.2 * IShape.PPC / 2.54 * 2., cir.getWidth(), 0.0000001);
		assertEquals(1.2 * IShape.PPC / 2.54 * 2., cir.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesCm() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(35cm,20cm){.5cm}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(35. * IShape.PPC - .5 * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((20. * IShape.PPC - .5 * IShape.PPC) * -1., cir.getPosition().getY(), 0.001);
		assertEquals(.5 * IShape.PPC * 2., cir.getWidth(), 0.0000001);
		assertEquals(.5 * IShape.PPC * 2., cir.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesRadius() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(35,20){10}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(35. * IShape.PPC - 10. * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((20. * IShape.PPC - 10. * IShape.PPC) * -1., cir.getPosition().getY(), 0.001);
		assertEquals(10. * IShape.PPC * 2., cir.getWidth(), 0.0000001);
		assertEquals(10. * IShape.PPC * 2., cir.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testFloatSigns() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(+++35.5,--50.5){--+12}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(35.5 * IShape.PPC - 12. * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((50.5 * IShape.PPC - 12. * IShape.PPC) * -1., cir.getPosition().getY(), 0.001);
		assertEquals(12. * IShape.PPC * 2., cir.getWidth(), 0.0000001);
		assertEquals(12. * IShape.PPC * 2., cir.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testNegativeRadius() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(0,0){-1}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(cir.getWidth() > 0);
		assertTrue(cir.getHeight() > 0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesFloat2() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(35.5,50.5){1.25}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(35.5 * IShape.PPC - 1.25 * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((50.5 * IShape.PPC - 1.25 * IShape.PPC) * -1., cir.getPosition().getY(), 0.001);
		assertEquals(1.25 * IShape.PPC * 2., cir.getWidth(), 0.0000001);
		assertEquals(1.25 * IShape.PPC * 2., cir.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesMissing() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(,){1}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(IShape.PPC - 1. * IShape.PPC, cir.getPosition().getX(), 0.0000001);
		assertEquals((IShape.PPC - 1. * IShape.PPC) * -1., cir.getPosition().getY(), 0.0000001);
		assertEquals(1. * IShape.PPC * 2., cir.getWidth(), 0.0000001);
		assertEquals(1. * IShape.PPC * 2., cir.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test(expected = ParseException.class)
	public void testErrorOnNoRadius() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "(,){}").get().isEmpty(); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
