package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPscurve extends TestPSTParser {
	@Test
	public void testCanParsePsecurveWithErrorsOnIncorrectLiftpen() throws ParseException {
		assertTrue(parser.parsePSTCode("\\psecurve[liftpen=3]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertEquals(2, PSTParser.errorLogs().size());
		PSTParser.errorLogs().clear();
		assertTrue(parser.parsePSTCode("\\psecurve[liftpen=1.2]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertEquals(2, PSTParser.errorLogs().size());
		PSTParser.errorLogs().clear();
		assertTrue(parser.parsePSTCode("\\psecurve[liftpen=foo]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertEquals(2, PSTParser.errorLogs().size());
		PSTParser.errorLogs().clear();
		assertTrue(parser.parsePSTCode("\\psecurve[liftpen=-1]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertEquals(2, PSTParser.errorLogs().size());
		PSTParser.errorLogs().clear();
		assertTrue(parser.parsePSTCode("\\psecurve[liftpen=1cm]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertEquals(2, PSTParser.errorLogs().size());
		PSTParser.errorLogs().clear();
	}


	@Test
	public void testCanParsePsecurveWithLiftpen2() throws ParseException {
		assertTrue(parser.parsePSTCode("\\psecurve[liftpen=2]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testCanParsePsecurveWithLiftpen1() throws ParseException {
		assertTrue(parser.parsePSTCode("\\psecurve[liftpen=1]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testCanParsePsecurveWithLiftpen0() throws ParseException {
		assertTrue(parser.parsePSTCode("\\psecurve[liftpen=0]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testCanParsePsecurve() throws ParseException {
		assertTrue(parser.parsePSTCode("\\psecurve[]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertFalse(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCanParsePsccurve() throws ParseException {
		assertTrue(parser.parsePSTCode("\\psccurve[]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertFalse(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCanParsePscurve() throws ParseException {
		assertTrue(parser.parsePSTCode("\\pscurve[]{<->}"+getBasicCoordinates()).get().isEmpty());
		assertFalse(PSTParser.errorLogs().isEmpty());
	}


	@Override
	public String getCommandName() {
		return "pscurve";
	}

	@Override
	public String getBasicCoordinates() {
		return "(0,1.3)(0.7,1.8)(3.3,0.5)(4,1.6)(0.4,0.4)";
	}
}
