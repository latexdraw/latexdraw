package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow.ArrowStyle;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsarcn extends TestParsingPsarc {
	@Override
	@Test
	public void testAngle1Angle2() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{200}{100}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(Math.toRadians(100.), arc.getAngleStart(), 0.0000001);
		assertEquals(Math.toRadians(200.), arc.getAngleEnd(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
		arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{100}{200}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(Math.toRadians(200.), arc.getAngleStart(), 0.0000001);
		assertEquals(Math.toRadians(100.), arc.getAngleEnd(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test
	public void testAngle1() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{11}{20}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(Math.toRadians(20.), arc.getAngleStart(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
		arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{11}{-200.15}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(Math.toRadians(-200.15), arc.getAngleStart(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test
	public void testAngle2() throws ParseException {
		ICircleArc arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{10}{20}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(Math.toRadians(10.), arc.getAngleEnd(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
		arc =  (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{10}{-10.12}{20}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(Math.toRadians(-10.12), arc.getAngleEnd(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test
	public void testParamBarInSqureBracket() throws ParseException {
		ICircleArc line = (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"{|-]}{1}{30}{40}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(ArrowStyle.BAR_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	@Test
	public void testParamArrowsArrows() throws ParseException {
		ICircleArc line = (ICircleArc)parser.parsePSTCode("\\"+getCommandName()+"[arrows=<->]{1}{30}{40}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	public String getCommandName() {
		return "psarcn"; //$NON-NLS-1$
	}
}
