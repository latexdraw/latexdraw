package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPspolygon extends TestParsingShape {
	@Test
	public void test2Coordinates() throws ParseException {
		IPolygon line = (IPolygon)parser.parsePSTCode("\\" + getCommandName() + "(5,10)(15,20)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(3, line.getNbPoints());
		assertEquals(0., line.getPtAt(0).getX(), 0.0001);
		assertEquals(0., line.getPtAt(0).getY(), 0.0001);
		assertEquals(5. * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-10. * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(15. * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(-20. * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesPt() throws ParseException {
		IPolygon line = (IPolygon)parser.parsePSTCode("\\" + getCommandName() + "(35pt,20pt)(10pt,5pt)(-10pt,-5pt)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(3, line.getNbPoints());
		assertEquals(35. * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20. * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(0).getY(), 0.0001);
		assertEquals(10. * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-5. * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-10. * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(2).getX(), 0.0001);
		assertEquals(5. * IShape.PPC / PSTricksConstants.CM_VAL_PT, line.getPtAt(2).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesMm() throws ParseException {
		IPolygon line = (IPolygon)parser.parsePSTCode("\\" + getCommandName() + "(350mm,200mm)(10mm, 30.3mm)(-10mm, -30.3mm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(3, line.getNbPoints());
		assertEquals(35. * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20. * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1. * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-3.03 * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-1. * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(3.03 * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesInch() throws ParseException {
		IPolygon line = (IPolygon)parser.parsePSTCode("\\" + getCommandName() + "(35in,20in)(1.2in,0.2in)(-1.2in,-0.2in)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(3, line.getNbPoints());
		assertEquals(35. * IShape.PPC / 2.54, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20. * IShape.PPC / 2.54, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1.2 * IShape.PPC / 2.54, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-0.2 * IShape.PPC / 2.54, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-1.2 * IShape.PPC / 2.54, line.getPtAt(2).getX(), 0.0001);
		assertEquals(0.2 * IShape.PPC / 2.54, line.getPtAt(2).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesCm() throws ParseException {
		IPolygon line = (IPolygon)parser.parsePSTCode("\\" + getCommandName() + "(35cm,20cm)(1.2cm,2cm)(-1.2cm,-2cm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(3, line.getNbPoints());
		assertEquals(35. * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-20. * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(1.2 * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-2. * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-1.2 * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(2. * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testFloatSigns() throws ParseException {
		IPolygon line = (IPolygon)parser.parsePSTCode("\\" + getCommandName() + "(+++35.5,--50.5)(--+12, -1)(---+12, ++1)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(3, line.getNbPoints());
		assertEquals(35.5 * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(12. * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(1. * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-12. * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(-1. * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testCoordinatesFloat2() throws ParseException {
		IPolygon line = (IPolygon)parser.parsePSTCode("\\" + getCommandName() + "(35.5,50.5)(12, 1)(-12, -1)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(3, line.getNbPoints());
		assertEquals(35.5 * IShape.PPC, line.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, line.getPtAt(0).getY(), 0.0001);
		assertEquals(12. * IShape.PPC, line.getPtAt(1).getX(), 0.0001);
		assertEquals(-1. * IShape.PPC, line.getPtAt(1).getY(), 0.0001);
		assertEquals(-12. * IShape.PPC, line.getPtAt(2).getX(), 0.0001);
		assertEquals(1. * IShape.PPC, line.getPtAt(2).getY(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test(expected = ParseException.class)
	public void testErrorOnNoPoint() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "").get().isEmpty(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test(expected = ParseException.class)
	public void testErrorOnOnePoint() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "(1,1)").get().isEmpty(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String getCommandName() {
		return "pspolygon"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return "(0,0)(1,1)(2,2)"; //$NON-NLS-1$
	}
}
