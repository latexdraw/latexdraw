package test.parser.pst;

import static org.junit.Assert.*;

import java.awt.Color;
import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Ignore;
import org.junit.Test;

public class TestTextParsing extends TestPSTParser {
	@Test @Ignore public void testText_withDots() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("0..n").get().getShapeAt(0);
		assertEquals("0..n", txt.getText());
	}

	@Test @Ignore public void testText_withCommas() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("0,fi,n,,a , b").get().getShapeAt(0);
		assertEquals("0,fi,n,,a , b", txt.getText());
	}

	@Test public void testText_bugParsingSeveralRputCmds() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\rput(0.9,0.6){aa}"+
						"\\rput(7.4,0){bb}\\rput(1,0){cc}").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(3, gp.getShapes().size());
		assertEquals("aa", ((IText)gp.getShapeAt(0)).getText());
		assertEquals("bb", ((IText)gp.getShapeAt(1)).getText());
		assertEquals("cc", ((IText)gp.getShapeAt(2)).getText());
	}

	@Test public void testText_when2usefontsTheLatestIsUsed1() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\usefont{T1}{ptm}{m}{it}\\rput(0.9,0.6){aa}"+
						"\\rput(7.4,0.92373){bb}\\usefont{T1}{ptm}{b}{sc}\\rput(1.1121212,0.1244){cc}").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(3, gp.getShapes().size());
		assertTrue(((IText)gp.getShapeAt(0)).getText().contains("\\it"));
		assertTrue(((IText)gp.getShapeAt(0)).getText().contains("aa"));
		assertTrue(((IText)gp.getShapeAt(1)).getText().contains("\\it"));
		assertTrue(((IText)gp.getShapeAt(1)).getText().contains("bb"));
		assertTrue(((IText)gp.getShapeAt(2)).getText().contains("\\sc"));
		assertTrue(((IText)gp.getShapeAt(2)).getText().contains("\\bf"));
		assertTrue(((IText)gp.getShapeAt(2)).getText().contains("cc"));
	}

	@Test public void testText_when2usefontsTheLatestIsUsed2() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\usefont{T1}{ptm}{m}{n}\n aa"+
						"\\usefont{T1}{ptm}{b}{it}\n bb").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertTrue(txt.getText().contains("usefont{T1}{ptm}{m}{n}"));
		assertTrue(txt.getText().contains("usefont{T1}{ptm}{b}{it}"));
		assertTrue(txt.getText().contains("aa"));
		assertTrue(txt.getText().contains("bb"));
	}


	@Test public void testTextWithSpecialColour() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\definecolor{color0}{rgb}{0.5,0.5,0.5}\\color{color0}couocu").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertNotNull(txt);
		assertEquals(new Color(0.5f,0.5f,0.5f), txt.getLineColour());
	}


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


	@Test public void testParseText_footnote() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\footnotesize coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\footnotesize coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX(), 0.001);
		assertEquals(-2.*IShape.PPC, text.getPosition().getY(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseText_tiny() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\tiny coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\tiny coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_scriptsize() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\scriptsize coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\scriptsize coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_footnotesize() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\footnotesize coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\footnotesize coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_small() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\small coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\small coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseText_normalsize() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\normalsize coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\normalsize coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_large() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\large coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\large coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_Large() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\Large coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\Large coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_huge() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\huge coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\huge coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_Huge() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\Huge coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\Huge coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseTextLD2_bold_italic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{b}{it}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\it\\bf coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX(), 0.001);
		assertEquals(-2.*IShape.PPC, text.getPosition().getY(), 0.001);
	}


	@Test public void testParseTextLD2_bold() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{b}{n}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\bf coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX(), 0.001);
		assertEquals(-2.*IShape.PPC, text.getPosition().getY(), 0.001);
	}


	@Test public void testParseTextLD2_italic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{m}{it}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("\\it coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX(), 0.001);
		assertEquals(-2.*IShape.PPC, text.getPosition().getY(), 0.001);
	}


	@Test public void testParseTextLD2_basic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{m}{n}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  ((IText)group.getShapeAt(0));
		assertEquals("coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX(), 0.001);
		assertEquals(-2.*IShape.PPC, text.getPosition().getY(), 0.001);
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


	@Ignore
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


	@Test(expected=ParseException.class) public void testMathModeNotClosed() throws ParseException {
		parser.parsePSTCode("$foo");
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


	@Test(expected=ParseException.class) public void testMathModeNotClosedParenthesis() throws ParseException {
		parser.parsePSTCode("\\(foo");
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


	@Test(expected=ParseException.class) public void testMathModeNotClosedBrackets() throws ParseException {
		parser.parsePSTCode("\\[foo");
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
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertTrue(group.getShapeAt(1) instanceof IDot);
		assertEquals("foo bar foo $math formula_{r}$ bar", ((IText)group.getShapeAt(0)).getText());
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
