package test.parser.pst;

import java.awt.Color;
import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestTextParsing extends TestPSTParser {
	@Test public void testBug722075_3() throws ParseException {
		// https://bugs.launchpad.net/latexdraw/+bug/722075
		IGroup group = parser.parsePSTCode("\\textcolor{blue}{xyz} foobar").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("foobar", text.getText());
		assertEquals(Color.BLACK, text.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testBug722075_2() throws ParseException {
		// https://bugs.launchpad.net/latexdraw/+bug/722075
		IGroup group = parser.parsePSTCode("\\textcolor{blue}{xyz}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("xyz", text.getText());
		assertEquals(Color.BLUE, text.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testBug722075_1() throws ParseException {
		// https://bugs.launchpad.net/latexdraw/+bug/722075
		IGroup group = parser.parsePSTCode("\\color{blue} xyz").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("xyz", text.getText());
		assertEquals(Color.BLUE, text.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testBug911816() throws ParseException {
		// https://bugs.launchpad.net/latexdraw/+bug/911816
		IGroup group = parser.parsePSTCode("\\psframebox{$E=mc^2$}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psframebox{$E=mc^2$}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseTextLD2_footnote() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\footnotesize coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\footnotesize coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX());
		assertEquals(-2.*IShape.PPC, text.getPosition().getY());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseTextLD2_bold_italic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{b}{it}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\it \\bf coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX());
		assertEquals(-2.*IShape.PPC, text.getPosition().getY());
	}


	@Test public void testParseTextLD2_bold() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{b}{n}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\upshape \\bf coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX());
		assertEquals(-2.*IShape.PPC, text.getPosition().getY());
	}


	@Test public void testParseTextLD2_italic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{m}{it}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\it \\mdseries coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX());
		assertEquals(-2.*IShape.PPC, text.getPosition().getY());
	}


	@Test public void testParseTextLD2_basic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{m}{n}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\upshape \\mdseries coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX());
		assertEquals(-2.*IShape.PPC, text.getPosition().getY());
	}


	@Test public void testBackSlashBackSlashIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\\\").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\\\", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashSharpIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\#").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\#", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashPercentIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\%").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\%", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashUnderscoreIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\_").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\_", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashAmperstampIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\&").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\&", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashRightBracketIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashLeftBracketIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\{").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\{", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashHatIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\^").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\^", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashDollarIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\$").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\$", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashTildeIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\~").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\~", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashDoubleQuoteIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\\"").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\\"", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashQuoteIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\'").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\'", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashMultIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\*").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\*", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashCommaIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\,").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\,", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashSlashIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\/").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\/", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashDotIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\.").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\.", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashArobasIsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\@").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\@", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashQuote2IsACommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\`").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\`", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashDoubleQuoteLetter() throws ParseException {
		IGroup group = parser.parsePSTCode("\\\"e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\\" e", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testUnknownCommandParsedAsText() throws ParseException {
		IGroup group = parser.parsePSTCode("\\thisisnotacommande").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\thisisnotacommande", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testBackSlashDoubleQuoteLetterBrackets() throws ParseException {
		IGroup group = parser.parsePSTCode("\\\"{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\\"{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testMathModeStd() throws ParseException {
		IGroup group = parser.parsePSTCode("$coucou$").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$coucou$", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testMathModeNotClosed() {
		try {
			assertTrue(parser.parsePSTCode("$foo").isDefined());
		}catch(ParseException ex) { /* Normal */}
	}



	@Test public void testMathModeWithSpecialCharacters() throws ParseException {
		IGroup group = parser.parsePSTCode("$foo_{test}$").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$foo_{test}$", ((IText)group.getShapeAt(0)).getText());

		group = parser.parsePSTCode("$\\|$").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$\\|$", ((IText)group.getShapeAt(0)).getText());

		group = parser.parsePSTCode("$\\mathcal{M}$").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$\\mathcal{M}$", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testMathModeStdParenthesis() throws ParseException {
		IGroup group = parser.parsePSTCode("\\(coucou\\)").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$coucou$", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testMathModeNotClosedParenthesis() {
		try {
			assertTrue(parser.parsePSTCode("\\(foo").isDefined());
		}catch(ParseException ex) { /* Normal */ }
	}


	@Test public void testMathModeWithSpecialCharactersParenthesis() throws ParseException {
		IGroup group = parser.parsePSTCode("\\(foo_{test}\\)").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$foo_{test}$", ((IText)group.getShapeAt(0)).getText());

		group = parser.parsePSTCode("\\(\\|\\)").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$\\|$", ((IText)group.getShapeAt(0)).getText());

		group = parser.parsePSTCode("\\(\\mathcal{M}\\)").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$\\mathcal{M}$", ((IText)group.getShapeAt(0)).getText());
	}



	@Test public void testMathModeStdBrackets() throws ParseException {
		IGroup group = parser.parsePSTCode("\\[coucou\\]").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$coucou$", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testMathModeNotClosedBrackets() {
		try {
			assertTrue(parser.parsePSTCode("\\[foo").isDefined());
		}catch(ParseException ex) { /* Normal */ }
	}


	@Test public void testMathModeWithSpecialCharactersBrackets() throws ParseException {
		IGroup group = parser.parsePSTCode("\\[foo_{test}\\]").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$foo_{test}$", ((IText)group.getShapeAt(0)).getText());

		group = parser.parsePSTCode("\\[\\|\\]").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$\\|$", ((IText)group.getShapeAt(0)).getText());

		group = parser.parsePSTCode("\\[\\mathcal{M}\\]").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("$\\mathcal{M}$", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testParse1WordBracketedInto1TextShape() throws ParseException {
		IGroup group = parser.parsePSTCode("{ foo }").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("foo", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testParse1WordInto1TextShape() throws ParseException {
		IGroup group = parser.parsePSTCode("foo").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("foo", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testParse2WordsInto1TextShape() throws ParseException {
		IGroup group = parser.parsePSTCode("foo bar").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("foo bar", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testParseMixedTextInto1TextShape() throws ParseException {
		IGroup group = parser.parsePSTCode("foo \\bloodyCmd $math formula_{r}$ bar").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("foo \\bloodyCmd $math formula_{r}$ bar", ((IText)group.getShapeAt(0)).getText());
	}



	@Test public void testParseFloatNumbersInto1TextShape() throws ParseException {
		IGroup group = parser.parsePSTCode("121.1248 -.1 ++1").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("121.1248 -.1 ++1", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testParseNumberInto1TextShape() throws ParseException {
		IGroup group = parser.parsePSTCode("121").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("121", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testParseUnknownCmdInto1TextShape() throws ParseException {
		IGroup group = parser.parsePSTCode("\\bloodyCmd").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\bloodyCmd", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testParseMixedTextAndShapeInto2TextShapesAnd1Shape() throws ParseException {
		IGroup group = parser.parsePSTCode("foo bar \\psdot(1,1) foo $math formula_{r}$ bar").get();
		assertEquals(3, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertTrue(group.getShapeAt(2) instanceof IDot);
		assertEquals("foo $math formula_{r}$ bar", ((IText)group.getShapeAt(0)).getText());
		assertEquals("foo bar", ((IText)group.getShapeAt(1)).getText());
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
