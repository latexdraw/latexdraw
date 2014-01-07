package test.parser.pst;

import static org.junit.Assert.*;

import java.awt.Color;
import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.LineStyle;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingQdisk extends TestPSTParser {
	@Test
	public void testCoordinatesCm() throws ParseException {
		ICircle cir =  (ICircle)parser.parsePSTCode("\\"+getCommandName()+"(35cm,20cm){.5cm}").get().getShapeAt(0);
		assertEquals(35.*IShape.PPC-.5*IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((20.*IShape.PPC-.5*IShape.PPC)*-1., cir.getPosition().getY(), 0.001);
		assertEquals(.5*IShape.PPC*2., cir.getWidth(), 0.0000001);
		assertEquals(.5*IShape.PPC*2., cir.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void testLineColourIsFillColour() throws ParseException {
		ICircle cir =  (ICircle)parser.parsePSTCode("\\psset{linecolor=green}\\"+getCommandName()+"(35pt,20pt){10pt}").get().getShapeAt(0);
		assertEquals(Color.GREEN, cir.getFillingCol());
		assertEquals(Color.GREEN, cir.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testLineStylePlain() throws ParseException {
		ICircle cir =  (ICircle)parser.parsePSTCode("\\psset{linestyle=dotted}\\"+getCommandName()+"(35pt,20pt){10pt}").get().getShapeAt(0);
		assertEquals(LineStyle.SOLID, cir.getLineStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoDbleBord() throws ParseException {
		ICircle cir =  (ICircle)parser.parsePSTCode("\\psset{doubleline=true}\\"+getCommandName()+"(35pt,20pt){10pt}").get().getShapeAt(0);
		assertFalse(cir.hasDbleBord());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void testNoShadow() throws ParseException {
		ICircle cir =  (ICircle)parser.parsePSTCode("\\psset{shadow=true}\\"+getCommandName()+"(35pt,20pt){10pt}").get().getShapeAt(0);
		assertFalse(cir.hasShadow());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test
	public void testBorderMustBeInto() throws ParseException {
		ICircle cir =  (ICircle)parser.parsePSTCode("\\psset{dimen=middle}\\"+getCommandName()+"(35pt,20pt){10pt}").get().getShapeAt(0);
		assertEquals(BorderPos.INTO, cir.getBordersPosition());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testMustBeFilled() throws ParseException {
		ICircle cir =  (ICircle)parser.parsePSTCode("\\"+getCommandName()+"(35pt,20pt){10pt}").get().getShapeAt(0);
		assertEquals(FillingStyle.PLAIN, cir.getFillingStyle());
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
	public void testMustNotHaveCoordinate() {
		try {
			parser.parsePSTCode("\\"+getCommandName()+"{1}");
			fail();
		}catch(Exception e) { /* ok */ }
	}


	@Override
	public String getCommandName() {
		return "qdisk";
	}


	@Override
	public String getBasicCoordinates() {
		return "(,){1}";
	}
}
