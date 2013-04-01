package test.parser.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestPSTGeneralFeatures extends TestPSTParser {
	@Test public void testBug756733() throws ParseException {
		// https://bugs.launchpad.net/latexdraw/+bug/756733
		IGroup gp = parser.parsePSTCode("\\psset{unit=5}\\psdot(1,1)\\psdot(1,10pt)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());

		IDot dot = (IDot)gp.getShapeAt(0);
		assertEquals(5.*IShape.PPC, dot.getX(), 0.000001);
		assertEquals(5.*-IShape.PPC, dot.getY(), 0.000001);

		dot = (IDot)gp.getShapeAt(1);
		assertEquals(5.*IShape.PPC, dot.getX(), 0.000001);
		assertEquals(10.*-IShape.PPC/PSTricksConstants.CM_VAL_PT, dot.getY(), 0.000001);
	}

	@Test public void test_bug_psset_setOfShapes() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\psframe(0.5,0.5)(1.5,1.5)\\psdot[linewidth=1cm,dotsize=1](1,1)\\psset{unit=2}\\psframe(0.5,0.5)(1.5,1.5)\\psdot(2,2)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		IRectangle rec = (IRectangle)gp.getShapeAt(0);
		assertEquals(0.5*IShape.PPC, rec.getX(), 0.000001);
		assertEquals(-0.5*IShape.PPC, rec.getY(), 0.000001);
		assertEquals(IShape.PPC, rec.getWidth(), 0.000001);
		assertEquals(IShape.PPC, rec.getHeight(), 0.000001);

		IDot dot = (IDot)gp.getShapeAt(1);
		assertEquals(IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-IShape.PPC, dot.getY(), 0.000001);

		rec = (IRectangle)gp.getShapeAt(2);
		assertEquals(0.5*2.*IShape.PPC, rec.getX(), 0.000001);
		assertEquals(-0.5*2.*IShape.PPC, rec.getY(), 0.000001);
		assertEquals(2.*IShape.PPC, rec.getWidth(), 0.000001);
		assertEquals(2.*IShape.PPC, rec.getHeight(), 0.000001);

		dot = (IDot)gp.getShapeAt(3);
		assertEquals(2.*2.*IShape.PPC, dot.getX(), 0.000001);
		assertEquals(-2.*2.*IShape.PPC, dot.getY(), 0.000001);
	}

	@Test public void test_psset_linewidth() throws ParseException {
		IRectangle rec = (IRectangle)parser.parsePSTCode("\\psset{linewidth=2cm}\\psframe(10,10)").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(2.*IShape.PPC, rec.getThickness(), 0.0001);
	}


	@Test public void testUnknownCommand() throws ParseException {
		parser.parsePSTCode("\\fuhfisduf");
		assertFalse(PSTParser.errorLogs().isEmpty());
	}

	@Test(expected=ParseException.class) public void test_BeginCenter_fail_no_begin() throws ParseException {
		parser.parsePSTCode("\\psline(1,1)(1,0)\\end{center}");
	}


	@Test(expected=ParseException.class) public void test_BeginCenter_fail_no_end() throws ParseException {
		parser.parsePSTCode("\\begin{center}\\psline(1,1)(1,0)");
	}


	@Test(expected=ParseException.class) public void test_BeginCenter_failWithBeginPsPictureInterlacing() throws ParseException {
		parser.parsePSTCode("\\begin{center}\\begin{pspicture}(1,1)\\psline(1,1)(1,0)\\end{center}\\end{pspicture}");
	}


	@Test public void test_BeginCenter_okWithBeginPsPicture() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\begin{center}\\begin{pspicture}(1,1)\\psline(1,1)(1,0)\\end{pspicture}\\end{center}").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(1, gp.getShapes().size());
	}


	@Test public void test_BeginCenter_ok() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\begin{center}\\psline(1,1)(1,0)\\end{center}").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(1, gp.getShapes().size());
	}


	@Test public void test_psscalebox() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\psscalebox{1 1}{\\psframe(2,3)(5,1)}").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(1, gp.getShapes().size());
	}


	@Test public void test_scalebox() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\scalebox{0.75}{\\psframe(2,3)(5,1)}").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(1, gp.getShapes().size());
	}

	@Test public void testTwoShapesDoNotShareTheirParameters() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\definecolor{color0b}{rgb}{0.6901960784313725,0.6745098039215687,0.9294117647058824}"+
				"\\definecolor{color0}{rgb}{0.00392156862745098,0.00392156862745098,0.00392156862745098}"+
				"\\psframe[linewidth=0.02,linecolor=color0,dimen=outer,fillstyle=solid,fillcolor=color0b](6.5836487,0.3584497)(4.591824,-1.2415503)"+
				"\\psbezier[linewidth=0.02](1.3918242,0.7584497)(2.0668242,0.95844966)(4.3168244,0.95844966)(4.991824,0.7584497)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(2, gp.getShapes().size());
		assertFalse(gp.getShapeAt(1).isFilled());
		assertEquals(Color.WHITE, gp.getShapeAt(1).getFillingCol());
	}


	@Test public void testDefineColor_hsb() throws ParseException {
		parser.parsePSTCode("\\definecolor{color0b}{hsb}{1,0, 0.5}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testDefineColor_HTML() throws ParseException {
		parser.parsePSTCode("\\definecolor{color0b}{HTML}{#001eff}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testDefineColor_gray() throws ParseException {
		parser.parsePSTCode("\\definecolor{color0b}{gray}{0.4}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testDefineColor_cmyk() throws ParseException {
		parser.parsePSTCode("\\definecolor{color0b}{cmyk}{0.2,0.6,0.5,0.3}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testDefineColor_cmy() throws ParseException {
		parser.parsePSTCode("\\definecolor{color0b}{cmy}{0.2,0.6,0.5}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testDefineColor_RGB() throws ParseException {
		parser.parsePSTCode("\\definecolor{color0b}{RGB}{100,50,200}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testDefineColor_rgb() throws ParseException {
		parser.parsePSTCode("\\definecolor{color0b}{rgb}{0.5,0.5,0.5}");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testrgbColorUsedInShape() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\definecolor{color0b}{rgb}{0.5,0.5,0.5}\\psframe[linecolor=color0b](10,10)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertTrue(gp.size()==1);
		assertEquals(new Color(0.5f,0.5f,0.5f), gp.getShapeAt(0).getLineColour());
	}


	@Test public void testHTMLColorUsedInShape() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\definecolor{color0b}{HTML}{#FF0064}\\psframe[linecolor=color0b](10,10)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertTrue(gp.size()==1);
		assertEquals(new Color(255,0,100), gp.getShapeAt(0).getLineColour());
	}


	@Test public void testgrayColorUsedInShape() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\definecolor{color0b}{gray}{0.8}\\psframe[linecolor=color0b](10,10)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertTrue(gp.size()==1);
		assertEquals(new Color(0.8f,0.8f,0.8f), gp.getShapeAt(0).getLineColour());
	}

	@Test public void testcmyColorUsedInShape() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\definecolor{color0b}{cmy}{0,1,0.5}\\psframe[linecolor=color0b](10,10)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertTrue(gp.size()==1);
		assertEquals(new Color(1f,0f,0.5f), gp.getShapeAt(0).getLineColour());
	}

	@Test public void testcmykColorUsedInShape() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\definecolor{color0b}{cmyk}{0,1,0.608,0}\\psframe[linecolor=color0b](10,10)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertTrue(gp.size()==1);
		assertEquals(new Color(255,0,100), gp.getShapeAt(0).getLineColour());
	}

	@Test public void testhsbColorUsedInShape() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\definecolor{color0b}{hsb}{0,0,0.95}\\psframe[linecolor=color0b](10,10)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertTrue(gp.size()==1);
		assertEquals(new Color(242,242,242), gp.getShapeAt(0).getLineColour());
	}

	@Test public void testRGBColorUsedInShape() throws ParseException {
		IGroup gp = parser.parsePSTCode("\\definecolor{c}{RGB}{200,100,0}\\psframe[linecolor=c](10,10)").get();
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertTrue(gp.size()==1);
		assertEquals(new Color(200,100,0), gp.getShapeAt(0).getLineColour());
	}


	@Test(expected=ParseException.class) public void testBackSlackNotSeparatedFromCommand() throws ParseException {
		parser.parsePSTCode("\\ specialCoors");
	}


	@Test(expected=ParseException.class) public void testErrorOnNotClosedParamsBlock() throws ParseException {
		parser.parsePSTCode("\\psframe[linewidth=0.04, dimen=o");
	}


	@Test(expected=ParseException.class) public void testNotCloseBracketShouldNotParse() throws ParseException {
		parser.parsePSTCode("{ ");
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
