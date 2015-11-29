package test.parser.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsframe extends TestParsingShape {
	@Test
	public void test_psset_unit_yunit() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\psset{unit=2,yunit=3}\\" + getCommandName() + "(1,1)(5,5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(2. * IShape.PPC, rec.getX(), 0.000001);
		assertEquals(-2. * 3. * IShape.PPC, rec.getY(), 0.000001);
		assertEquals(2. * 4. * IShape.PPC, rec.getWidth(), 0.000001);
		assertEquals(2. * 4. * 3. * IShape.PPC, rec.getHeight(), 0.000001);
	}

	@Test
	public void test_psset_unit_xunit() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\psset{unit=2,xunit=3}\\" + getCommandName() + "(1,1)(5,5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(2. * 3. * IShape.PPC, rec.getX(), 0.000001);
		assertEquals(-2. * IShape.PPC, rec.getY(), 0.000001);
		assertEquals(2. * 4. * 3. * IShape.PPC, rec.getWidth(), 0.000001);
		assertEquals(2. * 4. * IShape.PPC, rec.getHeight(), 0.000001);
	}

	@Test
	public void test_psset_unit() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\psset{unit=2}\\" + getCommandName() + "(1,1)(5,5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(2. * IShape.PPC, rec.getX(), 0.000001);
		assertEquals(-2. * IShape.PPC, rec.getY(), 0.000001);
		assertEquals(2. * 4. * IShape.PPC, rec.getWidth(), 0.000001);
		assertEquals(2. * 4. * IShape.PPC, rec.getHeight(), 0.000001);
	}

	@Override
	public String getCommandName() {
		return "psframe"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return "(35,20)"; //$NON-NLS-1$
	}

	@Test
	public void testParamAddfillstyle() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "[addfillstyle=hlines]" + getBasicCoordinates()); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testParamBorder() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "[border=2.1in]" + getBasicCoordinates()); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testParamDotsep() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "[dotsep=2.1in]" + getBasicCoordinates()); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testParamDashNumNum() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "[dash=2.1 +0.3]" + getBasicCoordinates()); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testParamDashDimDim() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "[dash=2cm 0.3 pt]" + getBasicCoordinates()); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testParamDashDimNum() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "[dash=2cm 0.3]" + getBasicCoordinates()); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testParamFramearc() throws ParseException {
		IRectangle sh = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "[framearc=0]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0., sh.getLineArc(), 0.00001);
		assertTrue(PSTParser.errorLogs().isEmpty());
		sh = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "[framearc=1]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(1., sh.getLineArc(), 0.00001);
		assertTrue(PSTParser.errorLogs().isEmpty());
		sh = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "[framearc=0.5]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0.5, sh.getLineArc(), 0.00001);
		assertTrue(PSTParser.errorLogs().isEmpty());
		sh = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "[framearc=0.2, framearc=0.3]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0.3, sh.getLineArc(), 0.00001);
		assertTrue(PSTParser.errorLogs().isEmpty());
		sh = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "[framearc=-1]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(PSTParser.errorLogs().isEmpty());
		PSTParser.errorLogs().clear();
		sh = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "[framearc=0.5cm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(PSTParser.errorLogs().isEmpty());
		PSTParser.errorLogs().clear();
		sh = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "[framearc=2]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesPt() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(35pt,20pt)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0., rec.getPosition().getX(), 0.001);
		assertEquals(0., rec.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC / PSTricksConstants.CM_VAL_PT, rec.getWidth(), 0.001);
		assertEquals(20. * IShape.PPC / PSTricksConstants.CM_VAL_PT, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesMm() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(350mm,200mm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0., rec.getPosition().getX(), 0.001);
		assertEquals(0., rec.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(20. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesInch() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(35in,20in)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0., rec.getPosition().getX(), 0.001);
		assertEquals(0., rec.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC / 2.54, rec.getWidth(), 0.001);
		assertEquals(20. * IShape.PPC / 2.54, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesCm() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(35cm,20cm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0., rec.getPosition().getX(), 0.001);
		assertEquals(0., rec.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(20. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test1Coordinates() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(35,20)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0., rec.getPosition().getX(), 0.001);
		assertEquals(0., rec.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(20. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesIntOppositeAll() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(35,50)(10,20)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(10. * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(20. * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(25. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(30. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesIntOppositeX() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(35,20)(10,50)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(10. * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(20. * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(25. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(30. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesIntOppositeY() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(10,50)(35,20)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(10. * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(20. * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(25. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(30. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesInt() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(10,20)(35,50)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(10. * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(20. * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(25. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(30. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesFloatSigns2() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(-+.5,+-5)(+++35.5,--50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(-.5 * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(-5. * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(36. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(55.5 * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesFloatSigns() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(-+-.5,+--.5)(+++35.5,--50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(.5 * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(.5 * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(50. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesFloat2() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(.5,.5)(35.5,50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(.5 * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(.5 * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(35. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(50. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesFloat() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(10.5,20.5)(35.5,50.5)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(10.5 * IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(20.5 * -IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(25. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(30. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesTwoFirstMissing() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(,)(35,50)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(IShape.PPC, rec.getPosition().getX(), 0.001);
		assertEquals(-IShape.PPC, rec.getPosition().getY(), 0.001);
		assertEquals(34. * IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(49. * IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void test2CoordinatesTwoLastMissing() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\" + getCommandName() + "(0,0)(,)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(0., rec.getPosition().getX(), 0.001);
		assertEquals(0., rec.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, rec.getWidth(), 0.001);
		assertEquals(IShape.PPC, rec.getHeight(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
}
