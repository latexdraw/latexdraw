package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.ICircleArc;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsarc extends TestParsingPswedge {
	@Test
	public void testArcsepParsed() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[arcsep=0.3cm](5,10){1}{30}{40}").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testArcsepBParsed() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[arcsepB=0.3cm](5,10){1}{30}{40}").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testArcsepAParsed() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"[arcsepA=0.3cm](5,10){1}{30}{40}").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowsArrowsNoneNone() throws ParseException {
		ICircleArc line = (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"[arrows=<->]{-}{1}{30}{40}").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testParamBarInSqureBracket() throws ParseException {
		ICircleArc line = (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{|-]}{1}{30}{40}").get().getShapeAt(0);
		assertEquals(ArrowStyle.BAR_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowsArrows() throws ParseException {
		ICircleArc line = (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"[arrows=<->]{1}{30}{40}").get().getShapeAt(0);
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test
	public void testErrorOnNoAngle2() throws ParseException {
		assertTrue(parser.parsePSTCode("\\"+getCommandName()+"(,){1}{30}{}").get().isEmpty());
		assertFalse(PSTParser.errorLogs().isEmpty());
	}


	@Override
	public String getCommandName() {
		return "psarc";
	}
}
