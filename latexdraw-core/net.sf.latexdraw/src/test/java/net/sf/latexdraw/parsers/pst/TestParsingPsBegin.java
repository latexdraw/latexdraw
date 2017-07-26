package net.sf.latexdraw.parsers.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsBegin extends TestPSTParser {

	@Test
	public void testPspictureWithGridAndShapesBasic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\pspicture(0,0)(2,2)\n\\psgrid\n\\psframe(0,0)(2,2)\n\\psframe(1,1)(3,3)\n\\endpspicture").get(); //$NON-NLS-1$
		IGrid grid = (IGrid)group.getShapeAt(0);
		IRectangle rec1 = (IRectangle)group.getShapeAt(1);
		IRectangle rec2 = (IRectangle)group.getShapeAt(2);
		assertTrue(PSTParser.errorLogs().isEmpty());

		assertEquals(0., grid.getPosition().getX(), 0.001);
		assertEquals(0., grid.getPosition().getY(), 0.001);
		assertEquals(0., rec1.getPosition().getX(), 0.001);
		assertEquals(0., rec1.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, rec2.getPosition().getX(), 0.001);
		assertEquals(-(double)IShape.PPC, rec2.getPosition().getY(), 0.001);
	}

	@Test
	public void testPspictureWithGridAndShapesComplex() throws ParseException {
		IGroup group = parser.parsePSTCode("\\pspicture(-3,-3)(2,2)\n\\psgrid\n\\psframe(0,0)(2,2)\n\\psframe(1,1)(3,3)\n\\endpspicture").get(); //$NON-NLS-1$
		IGrid grid = (IGrid)group.getShapeAt(0);
		IRectangle rec1 = (IRectangle)group.getShapeAt(1);
		IRectangle rec2 = (IRectangle)group.getShapeAt(2);
		assertTrue(PSTParser.errorLogs().isEmpty());

		assertEquals(0.0, grid.getPosition().getX(), 0.001);
		assertEquals(0.0, grid.getPosition().getY(), 0.001);
		assertEquals(0., rec1.getPosition().getX(), 0.001);
		assertEquals(0., rec1.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, rec2.getPosition().getX(), 0.001);
		assertEquals(-(double)IShape.PPC, rec2.getPosition().getY(), 0.001);
	}

	@Test
	public void testPspictureWithGrid() throws ParseException {
		IGroup group = parser.parsePSTCode("\\pspicture\\psgrid\\endpspicture").get(); //$NON-NLS-1$
		IGrid grid = (IGrid)group.getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());

		assertEquals(10., grid.getGridMaxX(), 0.001);
		assertEquals(10., grid.getGridMaxY(), 0.001);
		assertEquals(0., grid.getGridMinY(), 0.001);
		assertEquals(0., grid.getGridMinX(), 0.001);
	}

	@Test
	public void testBeginPsPictureWithGrid() throws ParseException {
		IGroup group = parser.parsePSTCode("\\begin{pspicture}\\psgrid\\end{pspicture}").get(); //$NON-NLS-1$
		IGrid grid = (IGrid)group.getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());

		assertEquals(10., grid.getGridMaxX(), 0.001);
		assertEquals(10., grid.getGridMaxY(), 0.001);
		assertEquals(0., grid.getGridMinY(), 0.001);
		assertEquals(0., grid.getGridMinX(), 0.001);
	}

	@Test
	public void testBeginPsPictureWithGridAndShapesBasic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\begin{pspicture}(0,0)(2,2)\n\\psgrid\n\\psframe(0,0)(2,2)\n\\psframe(1,1)(3,3)\n\\end{pspicture}").get(); //$NON-NLS-1$
		IGrid grid = (IGrid)group.getShapeAt(0);
		IRectangle rec1 = (IRectangle)group.getShapeAt(1);
		IRectangle rec2 = (IRectangle)group.getShapeAt(2);
		assertTrue(PSTParser.errorLogs().isEmpty());

		assertEquals(0., grid.getPosition().getX(), 0.001);
		assertEquals(0., grid.getPosition().getY(), 0.001);
		assertEquals(0., rec1.getPosition().getX(), 0.001);
		assertEquals(0., rec1.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, rec2.getPosition().getX(), 0.001);
		assertEquals(-(double)IShape.PPC, rec2.getPosition().getY(), 0.001);
	}

	@Test
	public void testBeginPsPictureWithGridAndShapesComplex() throws ParseException {
		IGroup group = parser.parsePSTCode("\\begin{pspicture}(-3,-3)(2,2)\n\\psgrid\n\\psframe(0,0)(2,2)\n\\psframe(1,1)(3,3)\n\\end{pspicture}").get(); //$NON-NLS-1$
		IGrid grid = (IGrid)group.getShapeAt(0);
		IRectangle rec1 = (IRectangle)group.getShapeAt(1);
		IRectangle rec2 = (IRectangle)group.getShapeAt(2);
		assertTrue(PSTParser.errorLogs().isEmpty());

		assertEquals(0.0, grid.getPosition().getX(), 0.001);
		assertEquals(0.0, grid.getPosition().getY(), 0.001);
		assertEquals(0., rec1.getPosition().getX(), 0.001);
		assertEquals(0., rec1.getPosition().getY(), 0.001);
		assertEquals(IShape.PPC, rec2.getPosition().getX(), 0.001);
		assertEquals(-(double)IShape.PPC, rec2.getPosition().getY(), 0.001);
	}

	@Test
	public void testBeginPsPictureStar2Coord() throws ParseException {
		parser.parsePSTCode("\\begin{pspicture*}(0,0)(1,1)\n\n\\end{pspicture*}"); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testBeginPsPictureStar1Coord() throws ParseException {
		parser.parsePSTCode("\\begin{pspicture*}(1,1)\n\n\\end{pspicture*}"); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testBeginPsPicture2Coord() throws ParseException {
		parser.parsePSTCode("\\begin{pspicture}(0,0)(1,1)\n\n\\end{pspicture}"); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testBeginPsPicture1Coord() throws ParseException {
		parser.parsePSTCode("\\begin{pspicture}(1,1)\n\n\\end{pspicture}"); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test(expected = ParseException.class)
	public void testBeginPsPictureErrorOnNoEnd() throws ParseException {
		parser.parsePSTCode("\\begin{pspicture}(1,1)"); //$NON-NLS-1$
	}

	@Test(expected = ParseException.class)
	public void testBeginPsPictureErrorOnNoStart() throws ParseException {
		parser.parsePSTCode("\\end{pspicture}"); //$NON-NLS-1$
	}

	@Override
	public String getCommandName() {
		return ""; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return ""; //$NON-NLS-1$
	}
}
