package test.parser.pst;

import static org.junit.Assert.*;

import java.awt.Color;
import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Ignore;
import org.junit.Test;

public class TestTextParsing extends TestPSTParser {
	@Test
	public void testBugParenthesis() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("{( )}").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals("( )", txt.getText());
	}


	@Test public void test_bf1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\bf coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\bf coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_bf2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\bf {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\bf coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("\\bf haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_sc1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\sc coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\sc coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_sc2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\sc {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\sc coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("\\sc haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_sl1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\sl coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\sl coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_sl2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\sl {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\sl coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("\\sl haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_it1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\it coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\it coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_it2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\it {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\it coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("\\it haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_scshape1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\scshape coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\scshape coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_scshape2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\scshape {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\sc coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("\\sc haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_slshape1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\slshape coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\slshape coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_slshape2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\slshape {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\sl coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("\\sl haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_itshape1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\itshape coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\itshape coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_itshape2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\itshape {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\it coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("\\it haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_upshape1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\upshape coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\upshape coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_upshape2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\upshape {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_bfseries1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\bfseries coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\bfseries coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_bfseries2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\bfseries {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\bf coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("\\bf haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_mdseries1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\mdseries coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\mdseries coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_mdseries2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\mdseries {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_ttfamily1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\ttfamily coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\ttfamily coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_ttfamily2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\ttfamily {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\ttfamily coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("\\ttfamily haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_sffamily1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\sffamily coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\sffamily coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_sffamily2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\sffamily {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\sffamily coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("\\sffamily haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void test_rmfamily1() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rmfamily coucou").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\rmfamily coucou", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void test_rmfamily2() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rmfamily {coucou} {haha}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("coucou", ((IText)group.getShapeAt(0)).getText());
		assertTrue(group.getShapeAt(1) instanceof IText);
		assertEquals("haha", ((IText)group.getShapeAt(1)).getText());
	}

	@Test public void testtextsf() throws ParseException {
		IGroup group = parser.parsePSTCode("\\textsf{coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\textsf{coucou}", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testtextsc() throws ParseException {
		IGroup group = parser.parsePSTCode("\\textsc{coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\textsc{coucou}", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testtextsl() throws ParseException {
		IGroup group = parser.parsePSTCode("\\textsl{coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\textsl{coucou}", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testunderline() throws ParseException {
		IGroup group = parser.parsePSTCode("\\underline{coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\underline{coucou}", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testtexttt() throws ParseException {
		IGroup group = parser.parsePSTCode("\\texttt{coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\texttt{coucou}", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testEmph() throws ParseException {
		IGroup group = parser.parsePSTCode("\\emph{coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\emph{coucou}", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testTextbf() throws ParseException {
		IGroup group = parser.parsePSTCode("\\textbf{coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\textbf{coucou}", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testTextit() throws ParseException {
		IGroup group = parser.parsePSTCode("\\textit{coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\textit{coucou}", ((IText)group.getShapeAt(0)).getText());
	}

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
		IText text = (IText)group.getShapeAt(0);
		assertEquals("foobar", text.getText());
		assertEquals(Color.BLACK, text.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testBug722075_2() throws ParseException {
		// https://bugs.launchpad.net/latexdraw/+bug/722075
		IGroup group = parser.parsePSTCode("\\textcolor{blue}{xyz}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = (IText)group.getShapeAt(0);
		assertEquals("xyz", text.getText());
		assertEquals(Color.BLUE, text.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testBug722075_1() throws ParseException {
		// https://bugs.launchpad.net/latexdraw/+bug/722075
		IGroup group = parser.parsePSTCode("\\color{blue} xyz").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = (IText)group.getShapeAt(0);
		assertEquals("xyz", text.getText());
		assertEquals(Color.BLUE, text.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testBug911816() throws ParseException {
		// https://bugs.launchpad.net/latexdraw/+bug/911816
		IGroup group = parser.parsePSTCode("\\psframebox{$E=mc^2$}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = (IText)group.getShapeAt(0);
		assertEquals("\\psframebox{$E=mc^2$}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseText_footnote() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\footnotesize coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\footnotesize coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX(), 0.001);
		assertEquals(-2.*IShape.PPC, text.getPosition().getY(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseText_tiny() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\tiny coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\tiny coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_scriptsize() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\scriptsize coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\scriptsize coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_footnotesize() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\footnotesize coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\footnotesize coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_small() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\small coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\small coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseText_normalsize() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\normalsize coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\normalsize coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_large() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\large coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\large coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_Large() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\Large coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\Large coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_huge() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\huge coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\huge coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParseText_Huge() throws ParseException {
		IGroup group = parser.parsePSTCode("\\rput(1,2){\\Huge coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\Huge coucou", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParseTextLD2_bold_italic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{b}{it}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\it\\bf coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX(), 0.001);
		assertEquals(-2.*IShape.PPC, text.getPosition().getY(), 0.001);
	}


	@Test public void testParseTextLD2_bold() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{b}{n}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\bf coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX(), 0.001);
		assertEquals(-2.*IShape.PPC, text.getPosition().getY(), 0.001);
	}


	@Test public void testParseTextLD2_italic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{m}{it}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
		assertEquals("\\it coucou", text.getText());
		assertEquals(1.*IShape.PPC, text.getPosition().getX(), 0.001);
		assertEquals(-2.*IShape.PPC, text.getPosition().getY(), 0.001);
	}


	@Test public void testParseTextLD2_basic() throws ParseException {
		IGroup group = parser.parsePSTCode("\\usefont{T1}{ptm}{m}{n}\\rput(1,2){coucou}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text =  (IText)group.getShapeAt(0);
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


	@Test public void testBackSlashDoubleQuoteLetterBrackets() throws ParseException {
		IGroup group = parser.parsePSTCode("\\\"{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\\"{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentGraveBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\`{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\`{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentGrave() throws ParseException {
		IGroup group = parser.parsePSTCode("\\`e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\` e", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testAccentAcuteBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\'{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\'{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentAcute() throws ParseException {
		IGroup group = parser.parsePSTCode("\\'e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\' e", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testAccentcircumflexBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\^{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\^{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentcircumflex() throws ParseException {
		IGroup group = parser.parsePSTCode("\\^e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\^ e", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testAccentHumlautBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\H{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\H{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentHumlaut() throws ParseException {
		IGroup group = parser.parsePSTCode("\\H e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\H e", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testAccenttildeBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\~{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\~{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccenttilde() throws ParseException {
		IGroup group = parser.parsePSTCode("\\~e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\~ e", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testAccentcedillaBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\c{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\c{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentcedilla() throws ParseException {
		IGroup group = parser.parsePSTCode("\\c e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\c e", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testAccentogonekBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\k{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\k{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentogonek() throws ParseException {
		IGroup group = parser.parsePSTCode("\\k e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\k e", ((IText)group.getShapeAt(0)).getText());
	}

	@Test public void testAccentlBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\l").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\l", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentmacronBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\={ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\={ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentmacron() throws ParseException {
		IGroup group = parser.parsePSTCode("\\=e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\= e", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentbarUnderBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\b{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\b{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentbarUnder() throws ParseException {
		IGroup group = parser.parsePSTCode("\\b e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\b e", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentdotOverBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\.{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\.{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentdotOver() throws ParseException {
		IGroup group = parser.parsePSTCode("\\.e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\. e", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentdotUnderBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\d{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\d{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentDotUnder() throws ParseException {
		IGroup group = parser.parsePSTCode("\\d e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\d e", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentRingOverBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\r{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\r{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentRingOver() throws ParseException {
		IGroup group = parser.parsePSTCode("\\r e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\r e", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentbreveBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\u{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\u{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentbreve() throws ParseException {
		IGroup group = parser.parsePSTCode("\\u e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\u e", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentcaronBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\v{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\v{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccentcaron() throws ParseException {
		IGroup group = parser.parsePSTCode("\\v e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\v e", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccenttieBlock() throws ParseException {
		IGroup group = parser.parsePSTCode("\\t{ee}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\t{ee}", ((IText)group.getShapeAt(0)).getText());
	}


	@Test public void testAccenttie() throws ParseException {
		IGroup group = parser.parsePSTCode("\\t e").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		assertEquals("\\t e", ((IText)group.getShapeAt(0)).getText());
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
