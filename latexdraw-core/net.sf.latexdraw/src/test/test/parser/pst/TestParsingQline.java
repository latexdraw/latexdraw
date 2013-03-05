package test.parser.pst;

import static org.junit.Assert.*;

import java.awt.Color;
import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingQline extends TestPSTParser {
	@Test
	public void testCoordinatesCm() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"(35cm,20cm)(11.12cm,-2cm)").get().getShapeAt(0);
		assertEquals(2, line.getNbPoints());
		assertEquals(35.*IShape.PPC, line.getPtAt(0).getX(), 0.001);
		assertEquals(20.*IShape.PPC*-1., line.getPtAt(0).getY(), 0.001);
		assertEquals(11.12*IShape.PPC, line.getPtAt(1).getX(), 0.001);
		assertEquals(-2.*IShape.PPC*-1., line.getPtAt(1).getY(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void testLineColourIsFillColour() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\psset{linecolor=green}\\"+getCommandName()+"(35cm,20cm)(11.12cm,-2cm)").get().getShapeAt(0);
		assertEquals(Color.GREEN, line.getFillingCol());
		assertEquals(Color.GREEN, line.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testLineStylePlain() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\psset{linestyle=dotted}\\"+getCommandName()+"(35cm,20cm)(11.12cm,-2cm)").get().getShapeAt(0);
		assertEquals(LineStyle.SOLID, line.getLineStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoDbleBord() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\psset{doubleline=true}\\"+getCommandName()+"(35cm,20cm)(11.12cm,-2cm)").get().getShapeAt(0);
		assertFalse(line.hasDbleBord());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void testNoShadow() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\psset{shadow=true}\\"+getCommandName()+"(35cm,20cm)(11.12cm,-2cm)").get().getShapeAt(0);
		assertFalse(line.hasShadow());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testMustNotBeFilled() throws ParseException {
		IPolyline line =  (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"(35cm,20cm)(11.12cm,-2cm)").get().getShapeAt(0);
		assertEquals(FillingStyle.NONE, line.getFillingStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testMustNotHaveParam() {
		try {
			parser.parsePSTCode("\\"+getCommandName()+"[fillstyle=gradient]"+getBasicCoordinates());
			fail();
		}catch(Exception e) { /* ok */ }
	}


	@Test
	public void testMustHaveTwoCoordinate() {
		try {
			parser.parsePSTCode("\\"+getCommandName()+"(35cm,20cm)");
			fail();
		}catch(Exception e) { /* ok */ }
	}


	@Override
	public String getCommandName() {
		return "qline";
	}


	@Override
	public String getBasicCoordinates() {
		return "(,){1}";
	}
}
