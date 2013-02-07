package test.parser.pst;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsBegin extends TestPSTParser {
	
	@Test
	public void testBeginPsPictureWithGridAndShapesBasic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\begin{pspicture}(0,0)(2,2)\n\\psgrid\n\\psframe(0,0)(2,2)\n\\psframe(1,1)(3,3)\n\\end{pspicture}").get();
		IGrid grid = (IGrid)group.getShapeAt(0);
		IRectangle rec1 = (IRectangle)group.getShapeAt(1);
		IRectangle rec2 = (IRectangle)group.getShapeAt(2);
		assertTrue(PSTParser.errorLogs().isEmpty());
		
		assertEquals(0., grid.getPosition().getX());
		assertEquals(0., grid.getPosition().getY());
		assertEquals(0., rec1.getPosition().getX());
		assertEquals(0., rec1.getPosition().getY());
		assertEquals((double)IShape.PPC, rec2.getPosition().getX());
		assertEquals(-(double)IShape.PPC, rec2.getPosition().getY());
	}
	
	
	
	@Test
	public void testBeginPsPictureWithGridAndShapesComplex() throws ParseException {
		IGroup group = parser.parsePSTCode("\\begin{pspicture}(-3,-3)(2,2)\n\\psgrid\n\\psframe(0,0)(2,2)\n\\psframe(1,1)(3,3)\n\\end{pspicture}").get();
		IGrid grid = (IGrid)group.getShapeAt(0);
		IRectangle rec1 = (IRectangle)group.getShapeAt(1);
		IRectangle rec2 = (IRectangle)group.getShapeAt(2);
		assertTrue(PSTParser.errorLogs().isEmpty());
		
		assertEquals(-3.*IShape.PPC, grid.getPosition().getX());
		assertEquals(3.*IShape.PPC, grid.getPosition().getY());
		assertEquals(0., rec1.getPosition().getX());
		assertEquals(0., rec1.getPosition().getY());
		assertEquals((double)IShape.PPC, rec2.getPosition().getX());
		assertEquals(-(double)IShape.PPC, rec2.getPosition().getY());
	}
	
	
	
	@Test
	public void testBeginPsPictureStar2Coord() throws ParseException {
		parser.parsePSTCode("\\begin{pspicture*}(0,0)(1,1)\n\n\\end{pspicture*}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	
	@Test
	public void testBeginPsPictureStar1Coord() throws ParseException {
		parser.parsePSTCode("\\begin{pspicture*}(1,1)\n\n\\end{pspicture*}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	

	@Test
	public void testBeginPsPicture2Coord() throws ParseException {
		parser.parsePSTCode("\\begin{pspicture}(0,0)(1,1)\n\n\\end{pspicture}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	

	@Test
	public void testBeginPsPicture1Coord() throws ParseException {
		parser.parsePSTCode("\\begin{pspicture}(1,1)\n\n\\end{pspicture}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}
	
	
	
	@Test
	public void testBeginPsPictureErrorOnNoCoord() {
		try {
			parser.parsePSTCode("\\begin{pspicture}\n\n\\end{pspicture}");
			failShouldNotParse();
		}catch(ParseException ex) { /* ok */ }
	}
	
	
	
	@Test
	public void testBeginPsPictureErrorOnNoEnd() {
		try {
			parser.parsePSTCode("\\begin{pspicture}(1,1)");
			failShouldNotParse();
		}catch(ParseException ex) { /* ok */ }
	}
	
	
	@Test
	public void testBeginPsPictureErrorOnNoStart() {
		try {
			parser.parsePSTCode("\\end{pspicture}");
			failShouldNotParse();
		}catch(ParseException ex) { /* ok */ }
	}
	
	
	
	
	@Override
	public String getCommandName() {
		return "";
	}

	@Override
	public String getBasicCoordinates() {
		return "";
	}
}
