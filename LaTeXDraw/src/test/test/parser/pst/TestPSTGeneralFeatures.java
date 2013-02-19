package test.parser.pst;

import java.text.ParseException;

import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestPSTGeneralFeatures extends TestPSTParser {

	@Test public void testBackSlackNotSeparatedFromCommand() {
		try {
			parser.parsePSTCode("\\ specialCoors");
			failShouldNotParse();
		}catch(ParseException ex) { /* Normal */}
	}


//	@Test public void testErrorOnCommandIncomplete() {
//		try {
//			parser.parsePSTCode("\\psframe[linewidth=0.04, dimen=o");
//			failShouldNotParse();
//		}catch(ParseException ex) { /* Normal */}
//	}


	@Test public void testNotCloseBracketShouldNotParse() {
		try {
			parser.parsePSTCode("{ ");
			failShouldNotParse();
		}catch(ParseException ex) { /* Normal */}
	}


	@Test public void testParseOrigin() {// throws ParseException {
		//TODO
//		parser.parsePSTCode("\\psframe[origin={1,2}](5,10)");
//		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseSwapaxes() throws ParseException {
		parser.parsePSTCode("\\psframe[swapaxes=true](5,10)");
		parser.parsePSTCode("\\psframe[swapaxes=false](5,10)");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseComment() throws ParseException {
		assertTrue(parser.parsePSTCode("%Foo").isDefined());
		assertTrue(parser.parsePSTCode("% Foo").isDefined());
		assertTrue(parser.parsePSTCode("% Foo \\test {").isDefined());
		assertTrue(parser.parsePSTCode("% Foo \n \t%Bar").isDefined());
	}


	@Test public void testClosedBracketsMustParse() throws ParseException {
		assertTrue(parser.parsePSTCode("{ }").isDefined());
		assertTrue(parser.parsePSTCode("{}").isDefined());
		assertTrue(parser.parsePSTCode("{\n}").isDefined());
		assertTrue(parser.parsePSTCode("{\n \n}").isDefined());
		assertTrue(parser.parsePSTCode("{\n%foo\n}").isDefined());
	}


	@Test public void testBackSlashBracketShouldBeIgnored() throws ParseException {
		assertTrue(parser.parsePSTCode("{ \\{ }").isDefined());
		assertTrue(parser.parsePSTCode("{ \\} }").isDefined());
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
