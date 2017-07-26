package net.sf.latexdraw.parsers.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsellipse extends TestParsingShape {
	@Override
	public String getCommandName() {
		return "psellipse"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return "(1,2)"; //$NON-NLS-1$
	}

	@Test
	public void testUnit() throws ParseException {
		IEllipse sh = (IEllipse)parser.parsePSTCode("\\psset{unit=4}\\psellipse(2,3cm)(2cm,5cm)").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(4. * 2. * IShape.PPC - 2. * IShape.PPC, sh.getX(), 0.0001);
		assertEquals(-3. * IShape.PPC + 5. * IShape.PPC, sh.getY(), 0.0001);
		assertEquals(2. * 2. * IShape.PPC, sh.getWidth(), 0.0001);
		assertEquals(2. * 5. * IShape.PPC, sh.getHeight(), 0.0001);
	}

	@Test
	public void testCoordinatesPt() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(35pt,20pt)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-35. * IShape.PPC / PSTricksConstants.CM_VAL_PT, ell.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC / PSTricksConstants.CM_VAL_PT * -1., ell.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2., ell.getWidth(), 0.001);
		assertEquals(20. * IShape.PPC / PSTricksConstants.CM_VAL_PT * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesMm() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(350mm,200mm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-35. * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC * -1., ell.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC * 2., ell.getWidth(), 0.001);
		assertEquals(20. * IShape.PPC * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesInch() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(35in,20in)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-35. * IShape.PPC / 2.54, ell.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC / 2.54 * -1., ell.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC / 2.54 * 2., ell.getWidth(), 0.001);
		assertEquals(20. * IShape.PPC / 2.54 * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesCm() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(35cm,20cm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-35. * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC * -1., ell.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC * 2., ell.getWidth(), 0.001);
		assertEquals(20. * IShape.PPC * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test1Coordinates() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(35,20)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-35. * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(-20. * IShape.PPC * -1., ell.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC * 2., ell.getWidth(), 0.001);
		assertEquals(20. * IShape.PPC * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesInt() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(10,20)(35,50)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(10. * IShape.PPC - 35. * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(20. * -IShape.PPC + 50. * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC * 2., ell.getWidth(), 0.001);
		assertEquals(50. * IShape.PPC * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesFloatSigns2() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(-+.5,+-5)(+++35.5,--50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-.5 * IShape.PPC - 35.5 * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(-5. * -IShape.PPC + 50.5 * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2., ell.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesFloatSigns() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(-+-.5,+--.5)(+++35.5,--50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(.5 * IShape.PPC - 35.5 * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(.5 * -IShape.PPC + 50.5 * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2., ell.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesFloat2() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(.5,.5)(35.5,50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(.5 * IShape.PPC - 35.5 * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(.5 * -IShape.PPC + 50.5 * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2., ell.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesFloat() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(10.5,20.5)(35.5,50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(10.5 * IShape.PPC - 35.5 * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(20.5 * -IShape.PPC + 50.5 * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35.5 * IShape.PPC * 2., ell.getWidth(), 0.001);
		assertEquals(50.5 * IShape.PPC * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesTwoFirstMissing() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(,)(35,50)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(IShape.PPC - 35 * IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(-IShape.PPC + 50. * IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC * 2., ell.getWidth(), 0.001);
		assertEquals(50. * IShape.PPC * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesTwoLastMissing() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(,)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-IShape.PPC, ell.getPosition().getX(), 0.001);
		assertEquals(IShape.PPC, ell.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC * 2., ell.getWidth(), 0.001);
		assertEquals(IShape.PPC * 2., ell.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2WidthHeight0() throws ParseException {
		IEllipse ell = (IEllipse)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(0,0)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(ell.getWidth() > 0);
		assertTrue(ell.getHeight() > 0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
}
